package com.gavin.mbg.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "information_schema.columns")
public class ColumnEntity {

  /**
   * 列名
   */
  private String columnName;

  /**
   * 列名类型
   */
  private String dataType;

  /**
   * 列名备注
   */
  private String columnComment;

  /**
   * 是否是主键
   */
  private String columnKey;

  /**
   * 属性类型
   */
  private String extra;

  /**
   * 是否允许为空
   */
  private String isNullable;

  /**
   * 字段最大长度
   */
  private String characterMaximumLength;

  /**
   * 是否允许为空
   */
  @TableField(exist = false)
  private Boolean nullable;

  /**
   * 属性类型
   */
  @TableField(exist = false)
  private String attrType;

  /**
   * 属性名称(第一个字母大写)，如：user_name => UserName
   */
  @TableField(exist = false)
  private String pascalAttrName;

  /**
   * 属性名称(第一个字母小写)，如：user_name => userName
   */
  @TableField(exist = false)
  private String camelAttrName;

}
