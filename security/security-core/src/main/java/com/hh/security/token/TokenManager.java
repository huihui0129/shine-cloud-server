package com.hh.security.token;

import com.hh.common.exception.BaseException;
import com.hh.security.authorization.Principal;
import com.hh.security.authorization.impl.AuthorityPrincipal;
import com.hh.security.authorization.impl.ClientAuthorityPrincipal;
import com.hh.security.http.AuthorityStatus;
import com.hh.security.utils.RsaUtils;
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
    private static final Integer TOKEN_EXPIRE_MINUTES = 1440;

    private static final Map<Class<? extends Principal>, List<Field>> fieldCache = new HashMap<>();

    static {
        cachePrincipal(AuthorityPrincipal.class);
        cachePrincipal(ClientAuthorityPrincipal.class);
    }

    private static void cachePrincipal(Class<? extends Principal> principalClass) {
        List<Field> fields = Arrays.asList(principalClass.getDeclaredFields());
        fieldCache.put(principalClass, fields);
    }

    /**
     * 根据私钥生成token
     *
     * @param principal
     * @param expireMinutes
     * @param privateKey
     * @return
     */
    public static String generate(Principal principal, Integer expireMinutes, PrivateKey privateKey) {
        if (principal == null) {
            throw new BaseException(AuthorityStatus.ENC_DATA_NULL);
        }
        if (expireMinutes == null) {
            expireMinutes = TOKEN_EXPIRE_MINUTES;
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
        LocalDateTime expire = LocalDateTime.now().plusMinutes(expireMinutes);
        jwtBuilder.setExpiration(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()));
        jwtBuilder.signWith(privateKey);
        return jwtBuilder.compact();
    }

    /**
     * 生成token
     * @param principal
     * @return
     */
    public static String generate(Principal principal) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("private_key.pem");
            InputStream is = classPathResource.getInputStream();
            byte[] bytes = is.readAllBytes();
            PrivateKey privateKey = RsaUtils.getPrivateKey(bytes);
            return TokenManager.generate(principal, TOKEN_EXPIRE_MINUTES, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过公钥解析token
     *
     * @param token     需要解析的数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static AuthorityPrincipal parse(String token, PublicKey publicKey) throws Exception {
        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        Long id = body.get("id", Long.class);
        String username = body.get("username", String.class);
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setId(id);
        principal.setUsername(username);
        return principal;
    }

    public static AuthorityPrincipal parse(String token) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("public_key.pem");
            InputStream is = classPathResource.getInputStream();
            byte[] bytes = is.readAllBytes();
            PublicKey publicKey = RsaUtils.getPublicKey(bytes);
            return TokenManager.parse(token, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
