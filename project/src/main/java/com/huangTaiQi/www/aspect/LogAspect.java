package com.huangTaiQi.www.aspect;





import java.util.Arrays;

public class LogAspect {

    //在调用一个方法之前，执行before方法
//    public void before(JoinPoint joinPoint){
//        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(),System.currentTimeMillis());
//        //这个方法中的逻辑，是由我们自己写的
//        System.out.println(("Invoker Before Method!!!" +
//                "\nTargetObject:" +  joinPoint.getThis() +
//                "\nArgs:" + Arrays.toString(joinPoint.getArguments())));
//    }
//
//    //在调用一个方法之后，执行after方法
//    public void after(JoinPoint joinPoint){
//        System.out.println(("Invoker After Method!!!" +
//                "\nTargetObject:" +  joinPoint.getThis() +
//                "\nArgs:" + Arrays.toString(joinPoint.getArguments())));
//        long startTime = (Long) joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
//        long endTime = System.currentTimeMillis();
//        System.out.println("use time :" + (endTime - startTime));
//    }
//
//    public void afterThrowing(JoinPoint joinPoint, Throwable ex){
//        System.out.println(("出现异常" +
//                "\nTargetObject:" +  joinPoint.getThis() +
//                "\nArgs:" + Arrays.toString(joinPoint.getArguments()) +
//                "\nThrows:" + ex.getMessage()));
//    }
}
