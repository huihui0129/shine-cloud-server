//package com.shine.mybatis.engine;
//
//import com.baomidou.mybatisplus.generator.config.OutputFile;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.io.File;
//import java.util.Map;
//
///**
// * 代码生成器支持自定义[DTO\VO等]模版
// */
//public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
//
//    protected void writer(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
//        String entityName = tableInfo.getEntityName();
//        String otherPath = this.getPathInfo(OutputFile.other);
//        customFile.forEach((key, value) -> {
//            String fileName = String.format(otherPath + File.separator + entityName + "%s", key);
//            this.outputFile(new File(fileName), objectMap, value, false);
//        });
//    }
//}