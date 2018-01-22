package com.gx.dy.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gx.dy.config.DataSourceEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceTypeAnno {
    DataSourceEnum value() default DataSourceEnum.tx1;
}