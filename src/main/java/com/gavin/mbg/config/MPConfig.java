package com.gavin.mbg.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置类
 */
@Configuration
@MapperScan("com.gavin.mbg.mapper")
public class MPConfig {

  /**
   * 注册 Mybatis-Plus 插件
   *
   * @return MybatisPlusInterceptor
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
    // 设置最大数量为50
    paginationInnerInterceptor.setMaxLimit(100L);
    interceptor.addInnerInterceptor(paginationInnerInterceptor);
    return interceptor;
  }

}
