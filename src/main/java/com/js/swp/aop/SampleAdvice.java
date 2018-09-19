package com.js.swp.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@AOP0918@@@@@@@@@@@@@@@@@@@@@@@@@@
@Component
@Aspect
public class SampleAdvice
{
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);	
	
	@Before("execution(* com.js.swp.service.MessageService*.*(..))")
	public void startlog(JoinPoint joinpoint)	 //Joinpoint 포인트 컷
	{
		logger.info("--------------startlog---------------");
		logger.info(" pointcut >> " + joinpoint.getSignature().getName());	//부르는 (실행하는) 메소드를 보여준다.
		logger.info(" args >> " + Arrays.toString(joinpoint.getArgs()));			//배열을 투스트링으로 바꿔서 보여진다.
	}
	
	@After("execution(* com.js.swp.service.MessageService*.*(..))")
	public void endlog(JoinPoint joinpoint)	
	{
		logger.info("--------------endlog---------------");
		logger.info(" pointcut >> " + joinpoint.getSignature().getName());	//부르는 (실행하는) 메소드를 보여준다.
		logger.info(" args >> " + Arrays.toString(joinpoint.getArgs()));			//배열을 투스트링으로 바꿔서 보여진다.
	}
	
	@Around("execution(* com.js.swp.service.MessageService*.*(..))")
	public Object timeLog(ProceedingJoinPoint proceedingJoinPoint)	 throws Throwable //Joinpoint 포인트 컷
	{
		logger.info("--------------timeLog---------------");
		
		long stime = System.currentTimeMillis();		//현재 시간을 밀리초로 stime에 담는다.?
		Object result = proceedingJoinPoint.proceed();		//?
		System.out.println("Signature : stime>> " + proceedingJoinPoint.getSignature().getName() + " : " + (System.currentTimeMillis() - stime));
		
		logger.info("--------------timeLog---------------");
		return result;		// 프록시가 받는다.
		
	}
}
