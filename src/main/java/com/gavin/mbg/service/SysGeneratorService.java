package com.gavin.mbg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavin.mbg.entity.TableEntity;
import com.gavin.mbg.entity.vo.MBGEntityVo;
import lombok.extern.slf4j.Slf4j;

/**
 * service 层
 *
 * @author Gavin
 */
public interface SysGeneratorService extends IService<TableEntity> {
  /**
   * 查寻本地数据库表的列表
   *
   * @return 数据库表信息
   */
  IPage<TableEntity> getTableList(IPage<TableEntity> page, String tableName);

  /**
   * 查寻本地数据库表
   *
   * @return 数据库表信息
   */
  TableEntity getTable(String tableName);

  /**
   * 生成代码
   *
   * @param mbg 相关配置
   * @return zip包
   */
  byte[] generatorCode(MBGEntityVo mbg);
}
