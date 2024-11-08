package com.hh.security.token;

import com.hh.common.exception.BaseException;
import com.hh.security.authorization.AuthorityPrincipal;
import com.hh.security.http.AuthorityStatus;
import com.hh.common.utils.RsaUtils;
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

    /**
     * 根据私钥生成token
     *
     * @param principal
     * @param expireMinutes
     * @param privateKey
     * @return
     */
    public static String generate(AuthorityPrincipal principal, Integer expireMinutes, PrivateKey privateKey) {
        if (principal == null) {
            throw new BaseException(AuthorityStatus.ENC_DATA_NULL);
        }
        if (expireMinutes == null) {
            expireMinutes = TOKEN_EXPIRE_MINUTES;
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.claim("id", principal.getId());
        jwtBuilder.claim("username", principal.getUsername());
        LocalDateTime expire = LocalDateTime.now().plusMinutes(expireMinutes);
        jwtBuilder.setExpiration(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()));
        jwtBuilder.signWith(privateKey);
        return jwtBuilder.compact();
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

    public static void main(String[] args) throws Exception {
        String token = test01();
        test02(token);
    }

    private static String test01() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("private_key.pem");
        InputStream is = classPathResource.getInputStream();
        byte[] bytes = is.readAllBytes();
        PrivateKey privateKey = RsaUtils.getPrivateKey(bytes);
        AuthorityPrincipal authorityPrincipal = new AuthorityPrincipal();
        authorityPrincipal.setId(101L);
        authorityPrincipal.setUsername("张三");
        String token = TokenManager.generate(authorityPrincipal, 100, privateKey);
        System.out.println(token);
        return token;
    }

    private static void test02(String token) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("public_key.pem");
        InputStream is = classPathResource.getInputStream();
        byte[] bytes = is.readAllBytes();
        PublicKey publicKey = RsaUtils.getPublicKey(bytes);
        AuthorityPrincipal principal = TokenManager.parse(token, publicKey);
        System.out.println(principal.getId());
        System.out.println(principal.getUsername());
    }

}
