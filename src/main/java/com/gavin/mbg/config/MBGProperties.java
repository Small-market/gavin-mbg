//package com.gavin.mbg.config;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Data
//@PropertySource("classpath:mbg.yml")
//@NoArgsConstructor
//@AllArgsConstructor
//@ConfigurationProperties("mbg")
//@Component
//public class MBGProperties {
//
//  /**
//   * 主路径
//   */
//  private String mainPath;
//
//  /**
//   * 包名
//   */
//  private String packagePath ;
//
//  /**
//   * 模块名
//   */
//  private String moduleName = "sys";
//
//  /**
//   * 作者名字
//   */
//  private String author = "Gavin";
//
//  /**
//   * 表前缀
//   */
//  private String tablePrefix = "";
//
//  /**
//   * 模块名
//   */
//  private Map<String, String> jdbcToJavaMap;
//}
