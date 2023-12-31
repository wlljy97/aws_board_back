package com.korit.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ArgsAop {

    @Pointcut("@annotation(com.korit.board.aop.annotation.ArgsAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around (ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        CodeSignature codeSignature = (CodeSignature) signature;

        String className = codeSignature.getDeclaringTypeName(); // class명
        String methodName = codeSignature.getName(); // method명

        String[] argNames = codeSignature.getParameterNames(); // 메소드의 파라미터 이름을 가져옴
        Object[] args = proceedingJoinPoint.getArgs(); // 메소드 호출시 전달된 인자를 가져옴

        System.out.println("======================================================================");
        System.out.println("클래스명: " + className + ", 메소드명: " + methodName);
        for(int i = 0; i < argNames.length; i++) {
            System.out.println(argNames[i] + ": " + args[i]);
        }
        System.out.println("======================================================================");

        return proceedingJoinPoint.proceed();
    }
}
