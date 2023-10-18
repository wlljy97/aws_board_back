package com.korit.board.aop;

import com.korit.board.exception.ValidException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 *  Aop는 Filter와 같은 역할을 한다. (메소드 사이사이의 Filter)
 *
 */

@Aspect // aop를 사용하면 꼭 써야하는 어노테이션
@Component // IoC에 등록하기 위한 어노테이션
public class ValidAop { // valid를 사용할 때 체크하는 Aop

    // pointCut 생성 과정 ↓
    // pointCut 표현식에서 (public)의 public는 생략이 가능하다.
//    @Pointcut("execution(* com.korit.board.controller.*.*(..))") // <-표현식은 모든영역에 쓰임
    @Pointcut("@annotation(com.korit.board.aop.annotation.ValidAop)") // pointcut에서 @annotation()에 오타가 나면 실행이 되지 않음
    private void pointCut() {}

    @Around("pointCut()") /* 포인트컷 */
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { // proceedingJoinPoint 이라는 매개변수를 가져옴
        // proceedingJoinPoint.proceed(); 에서 .proceed();는 doFilter 같은 역할을 한다.

        Object[] args = proceedingJoinPoint.getArgs(); // getArgs 배열로 return 되어 배열로 받을 수 있다.
        BeanPropertyBindingResult bindingResult = null;

        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg; // arg가 BeanPropertyBindingResult 타입일 때 다운캐스팅하여 대입
                break;
            }
        }

        if(bindingResult == null) {
            return proceedingJoinPoint.proceed(); // null 값이면 return, null이 아니면 error가 있는지 확인
        }

        // ValidAop 실행문
            if (bindingResult.hasErrors()) {
                Map<String, String> errorMap = new HashMap<>();
                bindingResult.getFieldErrors().forEach(fieldError -> { //getFieldErrors 에서 field에러들을 하나씩 꺼낸다.
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()); // errorMap에 key,value형태로 error가 만들어진다.
                });
                throw new ValidException(errorMap); // 예외가 생성, 예외가 있을 때 반응 할 수 있는 것을 만들어야한다.
            }

//           return proceedingJoinPoint.proceed(); // return에 proceedingJoinPoint.proceed(); 하게 되면 후처리가 필요가 없어진다.

        // 전처리 영역 /  .proceed() 메소드(함수)가 호출 되기 전이 전처리
        Object target =  proceedingJoinPoint.proceed(); // 메소드의 body, 여기서 target은 메소드의 return 값
        // 후처리 영역 /  .proceed() 메소드(함수)가 리턴된 후가 후처리
        System.out.println(target);

        return target;
    }

}
