package com.shine.file;

import com.shine.file.properties.LocalProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author huihui
 * @date 2025/1/9 14:05
 * @description LocalFileClient 本地文件处理
 */
@Slf4j
@Component
public class LocalFileClient {

    @Autowired
    private LocalProperties localProperties;

    public String upload(InputStream is, String filename) {
        String targetDirectory = localProperties.getLocalFilePath() + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()) + "/";
        String targetPath = Paths.get(targetDirectory, filename).toString();
        // 确保目标目录存在
        try {
            Files.createDirectories(Paths.get(targetDirectory));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建文件夹失败");
        }
        // 写入文件
        try (OutputStream os = new FileOutputStream(targetPath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            return targetPath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("保存文件失败");
        }
    }

    public String upload(File file, String filename) {
        try {
            return this.upload(new FileInputStream(file), filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
