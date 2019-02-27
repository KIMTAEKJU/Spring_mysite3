package com.douzone.mysite.aspect;

import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component
public class GlobalExceptionHandler 
{
	@AfterThrowing(value ="execution(* *..*.*.*(..))", throwing = "ex")  // *..* : 모든 패키지 /  글로벌 처리
	public void afterThrowingAdvice(Throwable ex)
	{
		System.out.println("call [afterThrowing acvice] : " + ex);
	}
}
