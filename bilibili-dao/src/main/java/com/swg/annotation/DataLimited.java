package com.swg.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 19:37
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Component
public @interface DataLimited {

}
