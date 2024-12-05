package com.shine.article.controller;

import com.shine.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2024/11/1 22:58
 * @description RemarkController
 */
@Slf4j
@RestController
@RequestMapping("/remark")
public class RemarkController {

    @GetMapping("/getRemark")
    public Result<String> getRemark() {
        return Result.success("获取到评论");
    }

}
