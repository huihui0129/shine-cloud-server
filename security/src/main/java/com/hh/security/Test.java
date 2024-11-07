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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 将输入流中的数据写入 ByteArrayOutputStream
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        // 转换为 byte[]
        byte[] byteArray = outputStream.toByteArray();
        PrivateKey privateKey = RsaUtils.getPrivateKey(byteArray);
        AuthorityPrincipal authorityPrincipal = new AuthorityPrincipal();
        authorityPrincipal.setId(1L);
        String token = TokenManager.generate(authorityPrincipal, 100, privateKey);
        System.out.println(token);
    }

    private static void test02() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("public_key.pem");
        InputStream is = classPathResource.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 将输入流中的数据写入 ByteArrayOutputStream
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        // 转换为 byte[]
        byte[] byteArray = outputStream.toByteArray();
        PublicKey publicKey = RsaUtils.getPublicKey(byteArray);
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzMwOTY2NzY1fQ.NcQ-sUkI-h_B1FjT6v_FBh9X6qETGq_GWWCuGpQDOWk3l4mOJZ5_GIKWCANDj6XvcERG5NjLRM-4fbeyy9xGKE7Ahifn_HX3AD7lsHpD2vE7J4-ynui95a6ulGJRbSqlojdWgnTpt9MuucirHiSEyYOkcl8YMzGNeg4P1G2exFaN_AOqTWxCX84wDJBADdpKJiK5Gbwlp0mLH1qcQfkHxQcC3qBbif4cJk29c0DJs7-YcsS_YuiUJR-k0cnChCtEZ146mhjEk4nA1q9D9yQ0lxNsyd1TLmS7S2D_uNxJ_YvYx_dJMCLoQr1cnaumI9cuI77obx4mHE23ZI4wgh-qJQ";
        AuthorityPrincipal principal = TokenManager.getObjectFromToken(token, publicKey);
    }

    private static void test03() throws Exception {
        try {
            String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALkm0ePRwK0m37VOO2HObfVsqwZlPlKfStf/epTn8j5PFzRzTbdBoaaaTse4I8oG88o4MJ1LnfWuP6FquWmLnGfp/OcMaxkhdONnIQ7O9B4MxurntcLiLT/RopuagkeR1TorOXTvmmySrPZA/eNbwImYjKPFvk9849b/r8scN6avAgMBAAECgYBCEaIwI3cX0pldfy8ddaYzP7W/sH0OypzQeu/pFuOQbHShWMvQqvDeHI4kIodS3iw8atSeLI+PTcCS/xxul00WBC4d+5LcqTGVJWUWUADMqiw6ugYU27zVRrgiIDmPKCTPvdwN7dXfTomdVXhYr2P9cNtDYP1QwmGUFzcFEM9JIQJBAOAF0B0WTo6CAka/xZm40BDRGxvU8jER0vzsHZVYV+Jh+sbnGkDPIsQ1wiurhz9TKfHiY0ALIKQphXBOuGvUKecCQQDTlJbW+fmI6zwgdw0e0o8g4nhOtFyi/T6vN0KejX4VYuqXW4enMwHFySbp2EfAxh7b7JCP2dPh9pBt6fKd81P5AkBIL9dV6CA8L4yIO7dyx2jUYm031LCOO04fjf4iZ6s6GZRzj3iYNabgTupYpBcawrOba/XLZ0qHW5HSFKu8o0s1AkEArww3Q8LrFcFxLHbyrvaLnixgVtVX8y1MSFCS2HS9xNnitrjuYYGMzONgQ4ZBzxba/x8YdztD1qZU1esBZHalmQJBAIo0but2y+HYu3+817Xv0OqBd/uz3srkmevDZfLwhZgmRxO8FLvP4laUp/Q0r6XDKz4jXa+ji/lXXXQnmAKGEy4=";

            PrivateKey obj = RsaUtils.getPrivateKey(privateKey.getBytes(StandardCharsets.UTF_8));
            System.out.println("Private Key: " + obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
