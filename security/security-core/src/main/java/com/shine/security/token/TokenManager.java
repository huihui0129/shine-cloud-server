package com.shine.security.token;

import com.shine.common.exception.AuthException;
import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.security.authorization.Principal;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.http.AuthorityStatus;
import com.shine.security.utils.RsaUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author huihui
 * @date 2024/11/7 11:11
 * @description AuthorizationTokenManager
 */
public class TokenManager {

    /**
     * token默认过期时间 分钟
     */
    private static final Integer TOKEN_EXPIRE_SECONDS = 60;

    private static final Map<Class<? extends Principal>, List<Field>> fieldCache = new HashMap<>();

    static {
        cachePrincipal(AuthorityPrincipal.class);
    }

    private static void cachePrincipal(Class<? extends Principal> principalClass) {
        List<Field> fields = Arrays.asList(principalClass.getDeclaredFields());
        fieldCache.put(principalClass, fields);
    }

    /**
     * 根据私钥生成token
     *
     * @param principal
     * @param expireSeconds
     * @param privateKey
     * @return
     */
    public static String generate(Principal principal, Integer expireSeconds, PrivateKey privateKey) {
        if (principal == null) {
            throw new BaseException(AuthorityStatus.ENC_DATA_NULL);
        }
        if (expireSeconds == null) {
            expireSeconds = TOKEN_EXPIRE_SECONDS;
        }
        // 使用预先缓存的字段信息
        List<Field> fieldList = fieldCache.get(principal.getClass());
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new BaseException(AuthorityStatus.NO_CACHE_PRINCIPAL);
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                jwtBuilder.claim(field.getName(), field.get(principal));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        LocalDateTime expire = LocalDateTime.now().plusSeconds(expireSeconds);
        jwtBuilder.setExpiration(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()));
        jwtBuilder.signWith(privateKey);
        return jwtBuilder.compact();
    }

    /**
     * 生成token
     *
     * @param principal
     * @return
     */
    public static String generate(Principal principal, Integer expireSeconds) {
        ClassPathResource classPathResource = new ClassPathResource("private_key.pem");
        try (InputStream is = classPathResource.getInputStream()) {
            byte[] bytes = is.readAllBytes();
            PrivateKey privateKey = RsaUtils.getPrivateKey(bytes);
            return TokenManager.generate(principal, expireSeconds, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成token
     *
     * @param principal
     * @return
     */
    public static String generate(Principal principal) {
        return generate(principal, TOKEN_EXPIRE_SECONDS);
    }

    /**
     * 通过公钥解析token
     *
     * @param token     需要解析的数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static <T extends Principal> T parse(PublicKey publicKey, String token, Class<T> principalClass) throws Exception {
        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        T principal = principalClass.getDeclaredConstructor().newInstance();
        // 从缓存中获取字段并赋值
        for (Field field : fieldCache.get(principalClass)) {
            field.setAccessible(true);
            Object value = body.get(field.getName(), field.getType());
            if (value != null) {
                field.set(principal, value);
            }
        }
        return principal;
    }

    public static <T extends Principal> T parse(String token, Class<T> principalClass) {
        ClassPathResource classPathResource = new ClassPathResource("public_key.pem");
        try (InputStream is = classPathResource.getInputStream()) {
            byte[] bytes = is.readAllBytes();
            PublicKey publicKey = RsaUtils.getPublicKey(bytes);
            return TokenManager.parse(publicKey, token, principalClass);
        } catch (Exception e) {
            throw new AuthException(ResponseStatus.UNAUTHORIZED);
        }
    }

}
