package com.renren.infra.xweb.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel的字段注解
 * 
 * @author yong.cao
 * @create-time 2013-10-24
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    //字段值
    String value() default "";

    //字段标题
    String title() default "";

    //字段导入导出, 0-导入导出, 1-导入, 2-导出
    int type() default 0;

    //字段对其, 0-居中, 1-左对齐, 2-右对齐
    int align() default 0;

    //字段排序, 0-不排序, 1-排序
    int sort() default 0;

    // 反射类型
    Class<?> fieldType() default Class.class;

}
