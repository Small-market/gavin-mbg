package com.gavin.mbg.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Gavin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "information_schema.tables")
public class TableEntity {
  /**
   * 表的名称
   */
  private String tableName;

  /**
   * 存储引擎
   */
  private String engine;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 表的备注
   */
  private String tableComment;

  /**
   * 表的主键
   */
  @TableField(exist = false)
  private ColumnEntity pk;

  /**
   * 表的列名(不包含主键)
   */
  @TableField(exist = false)
  private List<ColumnEntity> columns;


  /**
   * 类名(第一个字母大写)，如：sys_user => SysUser
   */
  @TableField(exist = false)
  private String className;

  /**
   * 类名(第一个字母小写)，如：sys_user => sysUser
   */
  @TableField(exist = false)
  private String classname;

  public String getClassname() {
    return classname;
  }

  public void setClassname(String classname) {
    this.classname = classname;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }
}
