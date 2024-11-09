package com.hh.mybatis.eneity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huihui
 * @date 2024/11/9 21:57
 * @description BaseEntity
 */
@Data
public class BaseEntity {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "upate_time")
    private LocalDateTime upateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
