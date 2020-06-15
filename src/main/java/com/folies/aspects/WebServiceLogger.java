package com.folies.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {
    private static final Logger LOG = LoggerFactory.getLogger(WebServiceLogger.class);

    @Pointcut(value = "execution(public * com.folies.services.*.*(..))")
    public void serviceMethod(){}

    @Before(value = "serviceMethod()")
    public void logBeforeWebServiceCall(JoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getName();
        var className = joinPoint.getSignature().getDeclaringType();
        var methodArgs = joinPoint.getArgs();
        LOG.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs) + " from class " + className);
    }

    @AfterReturning(value = "serviceMethod()", returning = "result")
    public void logAfterWebServiceCall(JoinPoint joinPoint, Object result) {
        var methodName = joinPoint.getSignature().getName();
        LOG.info("Method " + methodName + " result " + result);
    }
}
