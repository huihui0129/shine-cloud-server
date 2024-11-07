package com.hh.security.token;

import com.hh.security.authorization.AuthorityPrincipal;
import com.hh.security.exception.AuthorityException;
import com.hh.security.http.AuthorityStatus;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

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
public class AuthorizationTokenManager {

    public static String generate(AuthorityPrincipal principal, int expireMinutes, PrivateKey privateKey) {
        if (principal == null) {
            throw new AuthorityException(AuthorityStatus.ENC_DATA_NULL);
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.claim("id", principal.getId());
        LocalDateTime expire = LocalDateTime.now().plusMinutes(expireMinutes);
        jwtBuilder.setExpiration(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()));
        return jwtBuilder.compact();
    }

    /**
     * 通过公钥解析token
     * @param token 需要解析的数据
     * @param publicKey 公钥
     * @param beanClass 封装的JavaBean
     * @return
     * @throws Exception
     */
    public static <T> T  getObjectFromToken(String token, PublicKey publicKey, Class<T> beanClass) throws Exception {
        //1 获得解析后内容
        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        //2 将内容封装到对象JavaBean
        T bean = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            // 获得属性名
            String name = propertyDescriptor.getName();
            // 通过属性名，获得对应解析的数据
            Object value = body.get(name);
            if(value != null) {
                // 将获得的数据封装到对应的JavaBean中
                BeanUtils.setProperty(bean,name,value);
            }
        }
        return bean;
    }

    public static void main(String[] args) {
        AuthorityPrincipal authorityPrincipal = new AuthorityPrincipal();
        authorityPrincipal.setId(1L);
        generate(authorityPrincipal, 100, )
    }

}
