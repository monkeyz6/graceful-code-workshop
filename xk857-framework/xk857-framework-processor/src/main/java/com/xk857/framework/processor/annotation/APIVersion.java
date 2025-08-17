package com.xk857.framework.processor.annotation;

import com.xk857.framework.processor.enmu.APIVersionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface APIVersion {

    APIVersionEnum value();

}
