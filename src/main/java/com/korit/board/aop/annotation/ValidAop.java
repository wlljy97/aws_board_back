package com.korit.board.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // 실행중에 어노테이션을 읽어라
@Target(ElementType.METHOD) // 메소드 위에 다는 어노테이션
public @interface ValidAop { // 이 어노테이션을 사용하면 pointcut이 실행된다.
}
