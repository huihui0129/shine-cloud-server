package com.hh.security;

import com.hh.security.authorization.AuthorityPrincipal;
import com.hh.security.token.TokenManager;
import com.hh.utils.RsaUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author huihui
 * @date 2024/11/7 13:42
 * @description Test
 */
public class Test {

    public static void main(String[] args) throws Exception {
        test02();
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
