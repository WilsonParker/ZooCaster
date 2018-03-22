package com.graction.developer.zoocaster.DataBase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JeongTaehyun
 */

/*
 * Query 생성 시 무시 할 Field 설정
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlIgnore {

}
