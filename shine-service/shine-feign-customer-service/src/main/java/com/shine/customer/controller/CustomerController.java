package com.shine.customer.controller;

import com.shine.system.feign.TestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2025/1/17 16:26
 * @description CustomerService
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private TestFeign testFeign;

    @GetMapping("/callUser")
    public String callUser() {
        String res = testFeign.getUser(100L);
        return "调用结果是：" + res;
    }

}
