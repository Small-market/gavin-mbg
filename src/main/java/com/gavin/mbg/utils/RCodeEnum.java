package com.gavin.mbg.utils;

/**
 * 常用API返回枚举对象
 *
 * @author gavin
 */
public enum RCodeEnum implements IRCode {
  //
  SUCCESS(200, "操作成功"),
  FAIL(500, "操作失败");

  /**
   * 状态码
   */
  private final Integer code;
  /**
   * 详细信息
   */
  private final String msg;

  RCodeEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  @Override
  public Integer getCode() {
    return code;
  }

  @Override
  public String getMsg() {
    return msg;
  }
}
