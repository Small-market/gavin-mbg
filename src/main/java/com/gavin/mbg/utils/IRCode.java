package com.gavin.mbg.utils;

/**
 * 常用API返回对象接口
 *
 * @author gavin
 */
public interface IRCode {
  /**
   * 获取状态码
   *
   * @return 状态码
   */
  Integer getCode();

  /**
   * 获取详细信息
   *
   * @return 详细信息
   */
  String getMsg();
}
