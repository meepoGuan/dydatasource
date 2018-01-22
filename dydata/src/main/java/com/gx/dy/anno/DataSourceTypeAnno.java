package com.gx.dy.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gx.dy.config.DataSourceEnum;

/**
 * @author MeepoGuan
 *
 * <p>Description: </p>
 *
 * 2018年1月22日
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceTypeAnno {
    DataSourceEnum value() default DataSourceEnum.tx1;
}