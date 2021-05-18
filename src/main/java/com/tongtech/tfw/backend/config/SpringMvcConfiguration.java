package com.tongtech.tfw.backend.config;

import com.tongtech.tfw.backend.admin.apis.biz.auth.interceptor.AuthInterceptor;
import com.tongtech.tfw.backend.admin.apis.biz.auth.interceptor.DataSignatureInterceptor;
import com.tongtech.tfw.backend.admin.apis.biz.auth.interceptor.SanyuInterceptor;
import com.tongtech.tfw.backend.common.config.HttpMessageConverterWrapper;
import com.tongtech.tfw.backend.common.config.ParameterConverters;
import com.tongtech.tfw.backend.common.exception.GeneralExceptionHandler;
import com.tongtech.tfw.backend.common.server.UndertowServerFactoryCustomizer;
import com.tongtech.tfw.backend.config.aspect.LogAspect;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SpringMvcConfiguration
 *
 * @author Ivan
 * @version 1.0 Created by Ivan at 2020/3/18.
 */
@SpringBootConfiguration
public class SpringMvcConfiguration implements WebMvcConfigurer {
  @Bean
  public LogAspect getLogAspect() {
    return new LogAspect();
  }

  @Bean
  @ConditionalOnClass(ServerProperties.Undertow.class)
  public UndertowServerFactoryCustomizer undertowServerFactoryCustomizer() {
    return new UndertowServerFactoryCustomizer();
  }

  @Override
  public void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(new GeneralExceptionHandler());
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.forEach(HttpMessageConverterWrapper.objectMapperWrapper());
  }

  @Bean
  @ConditionalOnMissingBean(RequestContextListener.class)
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  @Bean
  public ParameterConverters parameterConverters() {
    return new ParameterConverters();
  }

  /**
   * @param registry :
   * @return void
   * @author Created by Ivan at 2020/5/9.
   *     <p>拦截器配置
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    /*registry
            .addInterceptor(this.getApiAuthInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(
                    "/auth/**",
                    "/*.html",
                    "/*.ico",
                    "/webjars/**",
                    "/swagger-resources/**",
                    "/ureport/**",
                    "/druid/**",
                    "/excel/**",
                    "/app/**",
                    "/error/**",
                    "/flowable/**",
                    "/v2/api-docs/**",
                    "/v3/api-docs/**");
    registry
            .addInterceptor(this.getDataSignatureInterceptor())
            .addPathPatterns("/area/**")
            .addPathPatterns("/dict/**")
            .addPathPatterns("/org/**")
            .addPathPatterns("/resource/**")
            .addPathPatterns("/role/**")
            .addPathPatterns("/user/**")
            .addPathPatterns("/auth/**")
            .excludePathPatterns(
                    "/*.html", "/webjars/**", "/swagger-resources/**", "/ureport/**", "/druid/**");
    registry.addInterceptor(this.getSanyuInterceptor()).addPathPatterns("/**");*/
  }

  @Bean
  public AuthInterceptor getApiAuthInterceptor() {
    return new AuthInterceptor();
  }

  @Bean
  public DataSignatureInterceptor getDataSignatureInterceptor() {
    return new DataSignatureInterceptor();
  }

  @Bean
  public SanyuInterceptor getSanyuInterceptor() {
    return new SanyuInterceptor();
  }
}
