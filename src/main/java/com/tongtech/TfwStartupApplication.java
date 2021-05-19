package com.tongtech;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.tongtech.tfw.workflow.config.AppDispatcherServletConfiguration;
import com.tongtech.tfw.workflow.config.ApplicationConfiguration;
import com.tongtech.tfw.workflow.config.DatabaseAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(
    exclude = {DruidDataSourceAutoConfigure.class, SecurityAutoConfiguration.class})
@MapperScan({"com.tongtech.**.dao"})
@EnableSwagger2
@EnableKnife4j
@Import(
    value = {
      // 引入修改的配置 12qwe
      ApplicationConfiguration.class,
      AppDispatcherServletConfiguration.class,
      DatabaseAutoConfiguration.class
    })
public class TfwStartupApplication {

  public static void main(String[] args) {
    SpringApplication.run(TfwStartupApplication.class, args);
  }
}
