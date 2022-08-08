package com.gavin.mbg.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @param <T>
 * @author gavin
 */
@Data
public class R<T> implements Serializable {

  /**
   * 返回 code 状态码
   */
  private final Integer code;

  /**
   * 详细信息
   */
  private final String msg;

  /**
   * 返回数据值
   */
  private T data;


  /**
   * 不带返回数据的成功
   */
  public static <T> R<T> ok() {
    return new R<>(RCodeEnum.SUCCESS);
  }

  /**
   * 带返回数据的成功
   *
   * @param t 详细数据
   */
  public static <T> R<T> ok(T t) {
    return new R<>(RCodeEnum.SUCCESS, t);
  }

  /**
   * 自定义返回消息和返回数据
   *
   * @param msg 详细消息
   * @param t   详细数据
   */
  public static <T> R<T> ok(String msg, T t) {
    return new R<>(RCodeEnum.SUCCESS.getCode(), msg, t);
  }


  /**
   * 直接返回错误
   */
  public static <T> R<T> fail() {
    return new R<>(RCodeEnum.FAIL);
  }

  /**
   * 自定返回结果枚举
   *
   * @param resultEnum 返回结果枚举
   */
  public static <T> R<T> fail(IRCode resultEnum) {
    return new R<>(resultEnum);
  }

  /**
   * 自定义返回信息
   *
   * @param msg 详细信息
   */
  public static <T> R<T> fail(String msg) {
    return new R<>(RCodeEnum.FAIL.getCode(), msg, null);
  }

  /**
   * 自定义返回枚举和数据
   *
   * @param resultEnum 返回结果枚举
   * @param t          返回数据
   */
  public static <T> R<T> fail(IRCode resultEnum, T t) {
    return new R<>(resultEnum, t);
  }

  /**
   * 返回结果，不带数据
   *
   * @param resultEnum 返回结果枚举
   */
  private R(IRCode resultEnum) {
    this.code = resultEnum.getCode();
    this.msg = resultEnum.getMsg();
  }

  /**
   * 全体的构造函数
   *
   * @param code 状态码
   * @param msg  详细信息
   * @param data 数据
   */
  private R(Integer code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  /**
   * 根据结果的枚举 返回结果
   *
   * @param resultEnum api结果对象
   * @param data       返回数据
   */
  private R(IRCode resultEnum, T data) {
    this.code = resultEnum.getCode();
    this.msg = resultEnum.getMsg();
    this.data = data;
  }
}
