package com.org.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class SystemAspect {
    private final static Logger logger = LoggerFactory.getLogger("log-print");

    @Pointcut("@annotation(com.org.log.SystemServiceLog)")
    public void serviceAspect() {
    }

    @Pointcut("@annotation(com.org.log.SystemControllerLog)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession httpSession = request.getSession();
        String id = request.getRemoteAddr();
        try {
            System.out.println("=====前置通知开始=====");
            System.out.println(
                    "请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature()
                            .getName() + "()"));
            System.out.println("=====前置通知结束=====");
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }

    }


    @Around("serviceAspect()")
    public void logService(ProceedingJoinPoint joinPoint) {
        try {
            long start = System.currentTimeMillis();
            Object object = joinPoint.proceed();
            getServiceMthodDescription(joinPoint);
            logger.info("result  {}  cost {} ms ", object, (System.currentTimeMillis() - start));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public static String getServiceMthodDescription(ProceedingJoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        //  logger.info("targetName: {}", targetName);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = joinPoint.getSignature().getName();
        //   logger.info("methodName: {}", methodName);
        Method method = methodSignature.getMethod();
        //   logger.info("method: {}", method);
        Annotation annotation = method.getAnnotation(SystemServiceLog.class);
        String description = ((SystemServiceLog) annotation).description();
        //   logger.info("description {}", str);
        Object[] arguments = joinPoint.getArgs();
        //    logger.info("arguments: {}", arguments);
        Class targetClass = Class.forName(targetName);
        //    logger.info("targetClass: {}", targetClass);
        Method[] methods = targetClass.getMethods();
        //    logger.info("methods: {}", methods);

        logger.info("{} -- {} -- {}", description, method, arguments);

        return description;
    }
}
