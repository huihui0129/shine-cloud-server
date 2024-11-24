package com.hh.generator.mapper;

import com.hh.generator.entity.Column;
import com.hh.generator.entity.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:47
 * @description DatabaseMapper
 */
@Mapper
public interface DatabaseMapper {

    Table getTableInfo(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    List<Column> getColumnInfo(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

}
