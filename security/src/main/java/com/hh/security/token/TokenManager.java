package com.hh.security.token;

import com.hh.security.authorization.AuthorityPrincipal;
import com.hh.security.exception.AuthorityException;
import com.hh.security.http.AuthorityStatus;
import com.hh.utils.RsaUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
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

    public static String generate(AuthorityPrincipal principal, int expireMinutes, PrivateKey privateKey) {
        if (principal == null) {
            throw new AuthorityException(AuthorityStatus.ENC_DATA_NULL);
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.claim("id", principal.getId());
        LocalDateTime expire = LocalDateTime.now().plusMinutes(expireMinutes);
        jwtBuilder.setExpiration(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()));
        // 设置签名算法和私钥
        jwtBuilder.signWith(SignatureAlgorithm.RS256, privateKey);  // 使用 RS256 签名算法
        return jwtBuilder.compact();
    }

    /**
     * 通过公钥解析token
     * @param token 需要解析的数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static AuthorityPrincipal  getObjectFromToken(String token, PublicKey publicKey) throws Exception {
        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        Long id = body.get("id", Long.class);
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setId(id);
        return principal;
    }

    private static void  test01() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("private_key.pem");
        InputStream is = classPathResource.getInputStream();
        byte[] bytes = is.readAllBytes();
        PrivateKey privateKey = RsaUtils.getPrivateKey(bytes);
        AuthorityPrincipal authorityPrincipal = new AuthorityPrincipal();
        authorityPrincipal.setId(101L);
        String token = TokenManager.generate(authorityPrincipal, 100, privateKey);
        System.out.println(token);
    }

    private static void test02() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("public_key.pem");
        InputStream is = classPathResource.getInputStream();
        byte[] bytes = is.readAllBytes();
        PublicKey publicKey = RsaUtils.getPublicKey(bytes);
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTAxLCJleHAiOjE3MzA5NzMzMDl9.nFmEO6arj1Zob6agYp5oFMjuF8GvUBfXRonxjxTTz1inooeesvLTLOgyKLUyCdxWqP43iLZ1K4YQKzLp4k_viLVNvtxXqzEUva7obDPrmKIyDQBYPPAfRqGNqMRnvj3_HfKAi8XW8AgpJncEoj98f3UvsIJlclRksZARNQj7AYtb4EhrjJg9fRFWi2_iKplGKudHpZINYI8SIQYlIE9oSbSVrfYX7XSlpW6e4EbVW33qs4OGJNMFkpm1Mh6W8zSRvqFi2srkAcHPBZKTyiso8_XI7Wy5-QJ56fZRL5bndxl9PAFga1QTKXPgWVr_evwr-NcPOj27vo8Koon-4hbfkA";
        AuthorityPrincipal principal = TokenManager.getObjectFromToken(token, publicKey);
        System.out.println(principal.getId());
    }

}
