package com.hh.article.controller;

import com.hh.user.feign.UserFeign;
import com.hh.utils.response.Result;
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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private UserFeign userFeign;

    @GetMapping("/getUser")
    public Result<String> getUser() {
        Result<String> user = userFeign.getUser();
        log.info("远程调用获取到的信息：{}", user.getData());
        return user;
    }

}
