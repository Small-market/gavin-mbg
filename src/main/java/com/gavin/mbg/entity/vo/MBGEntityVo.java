package com.gavin.mbg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MBGEntityVo {
  /**
   * 要自动生成的表
   */
  private List<String> tableList;

  /**
   * 是否是微服务
   */
  private Boolean isCloud = false;

  /**
   * 是否加上 swagger 注解
   */
  private Boolean isOpenApi = false;

  /**
   * 是否加上 spring-security 注解
   */
  private Boolean isSecurity = false;

  /**
   * 是否生成前端页面的模板
   */
  private String frontType;
}
