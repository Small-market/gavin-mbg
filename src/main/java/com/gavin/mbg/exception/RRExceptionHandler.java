package com.gavin.mbg.exception;

import com.gavin.mbg.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器
 *
 * @author Gavin
 */
@Slf4j
@RestControllerAdvice
public class RRExceptionHandler {

  @ExceptionHandler(MBGException.class)
  public ModelAndView resolveException(MBGException e) {
    ModelAndView modelAndView = new ModelAndView("404.html");

    return modelAndView;
  }


}
