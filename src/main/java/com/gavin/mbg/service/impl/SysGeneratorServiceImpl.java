package com.gavin.mbg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavin.mbg.entity.ColumnEntity;
import com.gavin.mbg.entity.TableEntity;
import com.gavin.mbg.entity.vo.MBGEntityVo;
import com.gavin.mbg.mapper.SysGeneratorMapper;
import com.gavin.mbg.service.ColumnService;
import com.gavin.mbg.service.SysGeneratorService;
import com.gavin.mbg.utils.GenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class SysGeneratorServiceImpl extends ServiceImpl<SysGeneratorMapper, TableEntity> implements SysGeneratorService {
  private final ColumnService columnService;

  /**
   * 获取数据库中存在的表
   *
   * @param page      分页数据 从 1 开始，最大页数为 100
   * @param tableName 表名：模糊查询
   */
  @Override
  public IPage<TableEntity> getTableList(IPage<TableEntity> page, String tableName) {
    QueryWrapper<TableEntity> queryWrapper = queryWrapper();
    if (StringUtils.isNotEmpty(tableName)) {
      queryWrapper.like("table_name", tableName);
    }
    queryWrapper.orderByDesc("create_time");
    return baseMapper.selectPage(page, queryWrapper);
  }

  /**
   * 获取表的详细信息
   *
   * @param tableName 表名
   */
  @Override
  public TableEntity getTable(String tableName) {
    QueryWrapper<TableEntity> queryWrapper = queryWrapper();
    if (StringUtils.isNotEmpty(tableName)) {
      queryWrapper.eq("table_name", tableName);
    }
    return baseMapper.selectOne(queryWrapper);
  }

  /**
   * 生成代码
   *
   * @param mbg 相关配置
   */
  @Override
  public byte[] generatorCode(MBGEntityVo mbg) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ZipOutputStream zip = new ZipOutputStream(outputStream);
    mbg.getTableList().forEach(i -> {
      //查询表信息
      TableEntity tableEntity = getTable(i);
      //查询列信息
      List<ColumnEntity> tableColumns = columnService.getColumnsByTableName(i);
      //生成代码
      GenUtils.generatorCode(tableEntity, tableColumns, zip, mbg);
    });

    // 关闭流
    IOUtils.closeQuietly(zip);
    return outputStream.toByteArray();
  }

  /**
   * 查询当前数据库
   *
   * @return 查询条件
   */
  private QueryWrapper<TableEntity> queryWrapper() {
    return new QueryWrapper<TableEntity>().apply("table_schema = (select database())");
  }
}