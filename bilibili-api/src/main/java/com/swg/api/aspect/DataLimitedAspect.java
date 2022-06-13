package com.swg.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 19:47
 **/
@Component
@Aspect
@Order(1)
public class DataLimitedAspect {

    @Pointcut("@annotation(com.swg.annotation.DataLimited)")
    public void check(){}

    @Before("check()")
    public void doBefore(JoinPoint joinPoint){}
}
