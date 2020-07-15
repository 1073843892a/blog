package com.lvlei.blog.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Before("log()")
    public void beforelog(JoinPoint joinPoint){
        //RequestContextHolder 用来保存的是requestservletattribute.后台任意地方均可以重RequestContext当中取值
        //httpServletRequest进一步封装再了servletRequestAttributes
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=servletRequestAttributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip= request.getRemoteAddr();
        String method=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,method, args);
        logger.info("Request Log:{}",requestLog);
    }

//分别是，权限修饰符，任何类，任何参数的任何方法
    @Pointcut("execution(* com.lvlei.blog.web.*.*(..))")
    public void log(){}

    @After("log()")
    public void Afterlog(){
        logger.info("----------After-----------");
    }
    @AfterReturning(returning ="result",pointcut = "log()")
    public void AfterReturn(Object result){
        logger.info("After:{}",result);
    }
    private class RequestLog{
        private String url;
        private String ip;
        private  String RequestMethod;
        private Object[] args;

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", RequestMethod='" + RequestMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

        public RequestLog(String url, String ip, String requestMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.RequestMethod = requestMethod;
            this.args = args;
        }
    }

}
