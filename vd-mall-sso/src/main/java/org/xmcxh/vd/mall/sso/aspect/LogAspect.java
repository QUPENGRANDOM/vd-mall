package org.xmcxh.vd.mall.sso.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一日志处理切面
 * Created by pengq on 2020/5/13 15:39.
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogAspect {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(public * org.xmcxh.vd.mall.sso.controller.*.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method.isAnnotationPresent(LogIgnore.class)) {
            return joinPoint.proceed();
        }

        LogInfo logInfo = new LogInfo();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation operation = method.getAnnotation(ApiOperation.class);
            logInfo.setOperation(operation.value());
        }
//
//        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        logInfo.setUser(userDetail.getUsername());
        Map<String, Object> params = parseParameters(method, joinPoint.getArgs());
        logInfo.setParams(params);
        log.info("入口信息：{}", objectMapper.writeValueAsString(logInfo));
        Object result = joinPoint.proceed();
        log.info("出口信息：{}", objectMapper.writeValueAsString(result));
        return result;
    }

    private Map<String, Object> parseParameters(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            if (requestBody != null) {
                map.put(parameter.getName(), args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if (requestParam != null) {
                map.put(parameter.getName(), args[i]);
            }

            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                map.put(parameter.getName(), args[i]);
            }
        }
        return map;
    }

    @Data
    private class LogInfo {
        private String operation;
        private String user;
        private Map<String, Object> params;
    }
}
