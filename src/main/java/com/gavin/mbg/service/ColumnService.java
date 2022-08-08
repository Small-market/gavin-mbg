package com.gavin.mbg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavin.mbg.entity.ColumnEntity;

import java.util.List;

/**
 * service 层
 *
 * @author Gavin
 */
public interface ColumnService extends IService<ColumnEntity> {
  /**
   * 获取对应表名的列结构
   *
   * @param tableName 表名
   */
  List<ColumnEntity> getColumnsByTableName(String tableName);
}
