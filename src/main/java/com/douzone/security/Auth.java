package com.douzone.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.METHOD, ElementType.TYPE}) // 이 어노테이션은 어디다 붙일수있느냐  이렇게 해주면 메소드에만 붙일수있음
@Retention( RetentionPolicy.RUNTIME) // 어느 시점에 사용할거냐   런타임에 사용
public @interface Auth 
{	
	public enum Role { ADMIN, USER}
	
	Role value() default Role.USER; // 하나의 속성만 사용하면 value이름이 좋다

	/*
	 * test
	 */
	//String value() default "USER";
	//int method() default 1;
}
