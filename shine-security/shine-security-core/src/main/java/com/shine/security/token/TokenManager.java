package com.shine.security.token;

import com.shine.common.exception.SecurityException;
import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.security.authorization.Principal;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.http.SecurityStatus;
import com.shine.security.utils.RsaUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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
            throw new BaseException(SecurityStatus.ENC_DATA_NULL);
        }
        if (expireSeconds == null) {
            expireSeconds = TOKEN_EXPIRE_SECONDS;
        }
        // 使用预先缓存的字段信息
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.claim(Principal.CLIENT_ID_KEY, principal.getClientId());
        jwtBuilder.claim(Principal.USER_ID_KEY, principal.getId());
        jwtBuilder.claim(Principal.USERNAME_KEY, principal.getUsername());
        jwtBuilder.claim(Principal.ROLE_KEY, principal.getRoleList());
        jwtBuilder.claim(Principal.PERMISSION_KEY, principal.getPermissionList());
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
    public static AuthorityPrincipal parse(PublicKey publicKey, String token) throws Exception {
        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        String clientId = body.get(Principal.CLIENT_ID_KEY, String.class);
        Long userId = body.get(Principal.USER_ID_KEY, Long.class);
        String username = body.get(Principal.USERNAME_KEY, String.class);
        List role = body.get(Principal.ROLE_KEY, List.class);
        List permission = body.get(Principal.PERMISSION_KEY, List.class);
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setClientId(clientId);
        principal.setId(userId);
        principal.setUsername(username);
        principal.setPassword("你不想知道");
        principal.setRoleList(role);
        principal.setPermissionList(permission);
        return principal;
    }

    public static AuthorityPrincipal parse(String token) {
        ClassPathResource classPathResource = new ClassPathResource("public_key.pem");
        try (InputStream is = classPathResource.getInputStream()) {
            byte[] bytes = is.readAllBytes();
            PublicKey publicKey = RsaUtils.getPublicKey(bytes);
            return TokenManager.parse(publicKey, token);
        } catch (Exception e) {
            throw new SecurityException(ResponseStatus.UNAUTHORIZED);
        }
    }

}
