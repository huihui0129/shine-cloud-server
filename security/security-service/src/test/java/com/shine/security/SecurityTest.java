package com.shine.security;

import com.alibaba.fastjson2.JSON;
import com.shine.common.response.Result;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.context.SecurityContext;
import com.shine.security.context.SecurityContextHolder;
import com.shine.security.controller.LoginController;
import jakarta.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huihui
 * @date 2024/12/30 14:02
 * @description SecurityTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SecurityTest {

    @Resource
    private LoginController loginController;

    @Before
    public void before() {
        SecurityContext context = new SecurityContext();
        context.setPrincipal(getCurrentPrincipal());
        SecurityContextHolder.setContext(context);
    }

    @Test
    public void test01() {
//        Result<String> test = loginController.test();
//        System.out.println(JSON.toJSONString(test));
    }

    public AuthorityPrincipal getCurrentPrincipal() {
        List<String> roleList = new ArrayList<>();
        roleList.add("system_user");
        roleList.add("system_admin");
        List<String> permissionList = new ArrayList<>();
        permissionList.add("system:user:add");
        permissionList.add("system:user:query");
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setClientId("system");
        principal.setId(1L);
        principal.setUsername("admin");
        principal.setPassword("aaa");
        principal.setRoleList(roleList);
        principal.setPermissionList(permissionList);
        return principal;
    }

}
