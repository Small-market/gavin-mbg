package com.gavin.mbg.exception;


import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author Gavin
 */
@Getter
@Setter
public class MBGException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String msg;
  private int code = 500;

  public MBGException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public MBGException(String msg, Throwable e) {
    super(msg, e);
    this.msg = msg;
  }

  public MBGException(String msg, int code) {
    super(msg);
    this.msg = msg;
    this.code = code;
  }

  public MBGException(String msg, int code, Throwable e) {
    super(msg, e);
    this.msg = msg;
    this.code = code;
  }

}
