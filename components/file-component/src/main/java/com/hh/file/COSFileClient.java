package com.hh.file;

import com.hh.file.properties.COSProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author huihui
 * @date 2024/11/5 13:33
 * @description CosClient
 */
@Component
public class COSFileClient {

    @Autowired
    private COSProperties cosProperties;

    @Autowired
    private COSClient cosClient;

    public String upload(InputStream is, ObjectMetadata objectMetadata, String filename) {
        String filetype = filename.substring(filename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString().replaceAll("-", "");
        String key = cosProperties.getPrefix() + "/" + today() + "/" + newFilename + filetype;
        PutObjectRequest request = new PutObjectRequest(cosProperties.getBucketName(), key, is, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(request);
        if (putObjectResult.getETag() != null) {
            return cosProperties.getUrl() + "/" + key;
        }
        return null;
    }

    public String upload(File file) {
        String filename = file.getName();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        try {
            return upload(new FileInputStream(file), objectMetadata, filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回yyyyMMdd格式当天日期
     *
     * @return
     */
    private String today() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(LocalDate.now());
    }

}
