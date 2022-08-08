package com.gavin.mbg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavin.mbg.entity.ColumnEntity;
import com.gavin.mbg.entity.TableEntity;
import com.gavin.mbg.mapper.ColumnMapper;
import com.gavin.mbg.service.ColumnService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, ColumnEntity> implements ColumnService {

  @Override
  public List<ColumnEntity> getColumnsByTableName(String tableName) {
    return baseMapper.getColumnByTableName(tableName);
  }

}