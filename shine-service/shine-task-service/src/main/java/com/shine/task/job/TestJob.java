package com.shine.task.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2025/1/14 11:38
 * @description TestJob
 */
@Slf4j
@Component
public class TestJob {

    @XxlJob("testXxlJobHandler")
    public void testXxlJobHandler() {
        log.info("hello xxl-job");
    }

}
