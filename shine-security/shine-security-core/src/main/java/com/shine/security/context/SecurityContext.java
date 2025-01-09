package com.shine.security.context;

import com.shine.security.authorization.Principal;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/22 14:27
 * @description SecurityContext
 */
@Data
public class SecurityContext<T extends Principal> {

    private T principal;

}
