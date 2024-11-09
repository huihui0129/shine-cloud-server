package com.hh.article.controller;

import com.hh.user.feign.UserFeign;
import com.hh.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2024/10/31 17:33
 * @description ArticleController
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserFeign userFeign;

    @GetMapping("/getUser")
    public Result<String> getUser() {
        Result<String> user = userFeign.getUser();
        log.info("远程调用获取到的信息：{}", user.getData());
        return user;
    }

    @GetMapping("/getUserException")
    public Result<String> getUserException() {
        return userFeign.getException();
    }

    @GetMapping("/getArticle")
    public Result<String> getArticle() {
        return Result.success("牛逼");
    }

    @GetMapping("/testConn")
    public Result<String> getConn() {
        return Result.success(userFeign.getConnTime().getData());
    }

}
