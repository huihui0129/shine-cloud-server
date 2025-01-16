package com.shine.system.request;

import com.shine.mybatis.request.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/12/16 10:08
 * @description UserPageRequest
 */
@Data
public class UserPageRequest extends BaseQueryRequest {

    @Schema(description = "用户名")
    private String username;

}
