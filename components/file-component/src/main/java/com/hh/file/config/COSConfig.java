package com.hh.file.config;

import com.hh.file.properties.COSProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huihui
 * @date 2024/11/5 13:26
 * @description COSConfig
 */
@Configuration
public class COSConfig {

    @Autowired
    private COSProperties cosProperties;

    @Bean
    public COSClient cosClient() {
        BasicCOSCredentials cred = new BasicCOSCredentials(cosProperties.getAppId(), cosProperties.getSecretId(), cosProperties.getSecretKey());
        Region region = new Region(cosProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }

}
