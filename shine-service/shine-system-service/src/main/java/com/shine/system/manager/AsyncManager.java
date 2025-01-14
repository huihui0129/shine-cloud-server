package com.shine.system.manager;

import com.shine.async.contsant.AsyncConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/26 14:31
 * @description AsyncManager
 */
@Slf4j
@Component
public class AsyncManager {

    @Async(AsyncConstant.DEFAULT_ASYNC_EXECUTOR)
    public void asyncTask() {
        log.info("执行异步任务");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("异步任务执行成功");
    }

}
