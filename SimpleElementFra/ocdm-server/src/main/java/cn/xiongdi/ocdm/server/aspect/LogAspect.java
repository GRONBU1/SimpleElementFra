package cn.xiongdi.ocdm.server.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @创建人 汪礼
 * @创建时间 2018-09-18
 * @描述  Controller日志输出
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* cn.xiongdi.ocdm.server.controller.*.*Controller.*(..))")
    public void logPointcut(){}

    @Before("logPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("▼▼　Restful Api Name　==> " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("[ARGS] : " + Arrays.toString(joinPoint.getArgs()));
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "ret", pointcut = "logPointcut()")
    public void doAfterReturning(Object ret) {
        log.info("[Result] : " + ret);
        log.info("▲▲　COST TIME　==> " + (System.currentTimeMillis() - startTime.get()));
    }

    @AfterThrowing(throwing="ex", pointcut="logPointcut()")
    public void doAfterThrowing(Throwable ex) {
        log.error("[EXCEPTION] : " + ex);
    }
}
