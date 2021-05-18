package com.tongtech.tfw.backend.config;

import com.tongtech.tfw.backend.common.request.RequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * WebFilterConfiguration \
 *
 * @author Ivan
 * @version 1.0 Created by Ivan at 2020/3/18.
 */
@SpringBootConfiguration
public class WebFilterConfiguration {

  @Value("${tfw.basePath}")
  private List<String> basePath;

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    // 允许访问的客户端域名
    // config.setAllowedOriginPatterns(basePath);
    config.addAllowedOrigin("*");
    config.setAllowCredentials(false);
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
    configSource.registerCorsConfiguration("/**", config);
    return new CorsFilter(configSource);
  }

  @Bean
  public FilterRegistrationBean<RequestFilter> xssFilterFilterRegistrationBean() {
    FilterRegistrationBean<RequestFilter> filterFilterRegistrationBean =
            new FilterRegistrationBean<>();
    filterFilterRegistrationBean.setFilter(getRequestFilter());
    filterFilterRegistrationBean.addUrlPatterns("/*");
    filterFilterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 2);
    return filterFilterRegistrationBean;
  }

  /**
   * @author Created by Ivan at 2020/5/9.
   * @return com.tongtech.backend.framework.support.web.request.RequestFilter
   */
  @Bean
  public RequestFilter getRequestFilter() {
    return new RequestFilter();
  }
}
