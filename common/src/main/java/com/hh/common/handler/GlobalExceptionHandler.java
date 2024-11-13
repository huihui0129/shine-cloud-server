package com.hh.common.handler;

import com.hh.common.exception.BaseException;
import com.hh.common.response.Result;
import com.hh.common.status.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author huihui
 * @date 2024/11/8 15:28
 * @description GlobalExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理未知错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("运行异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.error(ResponseStatus.ERROR, e.getMessage());
    }

    /**
     * 统一业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public Result<?> baseExceptionHandler(BaseException e) {
        log.error("业务处理异常：[{}]|[{}]|[{}]", e.getEnum().getCode(), e.getEnum().getName(), e.getMessage());
        e.printStackTrace();
        return Result.error(e.getEnum().getCode(), e.getMessage());
    }

}
