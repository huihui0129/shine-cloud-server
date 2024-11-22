package com.hh.security.context;

import com.hh.security.authorization.Principal;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/22 14:27
 * @description SecurityContext
 */
@Data
public class SecurityContext {

    private Principal principal;

}
