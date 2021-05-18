package com.tongtech.tfw.backend.config.aspect;

import com.tongtech.tfw.backend.common.request.RequestUtil;
import com.tongtech.tfw.backend.common.response.ResponseUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * LogAspect
 *
 * @author Ivan
 * @version 1.0 Created by Ivan at 2020/3/18.
 */
@Aspect
public class LogAspect {

  @Pointcut(
      "(execution(public * com.tongtech..*Controller.*(..)))||(execution(public * com.tongtech..*Api.*(..))) || (execution(public * com.tongtech..*Docker.*(..)))")
  public void pointCut() {}

  @AfterReturning(returning = "result", pointcut = "pointCut()")
  public void doAfterReturning(Object result) {
    ResponseUtil.response(RequestUtil.getRequest(), null, result);
  }
}
