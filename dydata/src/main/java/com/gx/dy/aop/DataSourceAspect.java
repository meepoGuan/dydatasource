package com.gx.dy.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.gx.dy.anno.DataSourceTypeAnno;
import com.gx.dy.config.DataSourceContextHolder;
import com.gx.dy.config.DataSourceEnum;

/**
 * @author MeepoGuan
 *
 * <p>Description: </p>
 *
 * 2018年1月22日
 *
 */
@Component
@Aspect
public class DataSourceAspect {
	//注解拦截失败@Pointcut("execution(public * com.gx.dy.mapper..*.*(..)) && @annotation(com.gx.dy.anno.DataSourceTypeAnno)")
	@Pointcut("execution(public * com.gx.dy.mapper..*.*(..))")
    public void dataSourcePointcut() {
    }

    @Around("dataSourcePointcut()")
    public Object doAround(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        DataSourceTypeAnno typeAnno = method.getAnnotation(DataSourceTypeAnno.class);
        if(null != typeAnno) {
    	   DataSourceEnum sourceEnum = typeAnno.value();
           if (sourceEnum == DataSourceEnum.tx1) {
               DataSourceContextHolder.setDataSourceType(DataSourceEnum.tx1);
           } else if (sourceEnum == DataSourceEnum.tx2) {
               DataSourceContextHolder.setDataSourceType(DataSourceEnum.tx2);
           } else if (sourceEnum == DataSourceEnum.tx3) {
          	  DataSourceContextHolder.setDataSourceType(DataSourceEnum.tx3);
           }

           Object result = null;
           try {
               result = pjp.proceed();
           } catch (Throwable throwable) {
               throwable.printStackTrace();
           } finally {
               DataSourceContextHolder.resetDataSourceType();
           }
           return result;
        } else {
        	return null;
        }
      
    }
}