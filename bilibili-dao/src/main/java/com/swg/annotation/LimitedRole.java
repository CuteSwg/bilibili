package com.swg.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 19:11
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Component
public @interface LimitedRole {

    String[] limitedRoleCodeList() default {};
}
