package com.hh.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/4 09:39
 * @description TestModel
 */
@Data
public class TestModel {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

}
