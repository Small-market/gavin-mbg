package com.gavin.mbg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理除了index之外的页面
 */
@Controller
public class HtmlController {

  /**
   * 返回生成代码页面
   */
  @GetMapping({"page/code.html"})
  public String codeHtml() {
    return "page/code";
  }

  /**
   * 返回关于我们页面
   */
  @GetMapping({"page/about.html"})
  public String aboutHtml() {
    return "page/about";
  }

  /**
   * 返回选择二表单页面
   */
  @GetMapping({"page/mbg/select_form.html"})
  public String selectHtml() {
    return "page/mbg/select_form";
  }

//  @ApiOperation("查询订单")
//  @RequestMapping(value = "/list", method = RequestMethod.GET)
//  @ResponseBody


}
