package com.hcl.dxnotification.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceLoggingAspect.class);

    @Around("@annotation(org.springframework.transaction.annotation.Transactional) || within(com.hcl.*)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long methodexecutionTime = System.currentTimeMillis() - startTime;

        logger.info("Method {} executed in {} ms", joinPoint.getSignature(), methodexecutionTime);
        return proceed;
    }
}
