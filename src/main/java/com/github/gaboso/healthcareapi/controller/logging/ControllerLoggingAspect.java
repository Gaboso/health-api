package com.github.gaboso.healthcareapi.controller.logging;

import com.github.gaboso.healthcareapi.controller.MedicalRecordController;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    @Before("execution(* com.github.gaboso.healthcareapi.controller.MedicalRecordController.*(..))")
    public void logControllerMethodCall(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        log.info("[{}] Request method [{}] was called with args: {}", MedicalRecordController.class.getSimpleName(), method.getName(), joinPoint.getArgs());
    }

}