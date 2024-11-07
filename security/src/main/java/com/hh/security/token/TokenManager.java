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

}
