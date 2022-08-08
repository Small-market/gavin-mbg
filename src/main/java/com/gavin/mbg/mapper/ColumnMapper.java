package com.gavin.mbg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gavin.mbg.entity.ColumnEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ColumnMapper extends BaseMapper<ColumnEntity> {
  List<ColumnEntity> getColumnByTableName(String tableName);
}
