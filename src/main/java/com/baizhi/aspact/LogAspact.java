package com.baizhi.aspact;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.LogDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspact {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LogDao logDao;
    @Around("@annotation(com.baizhi.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        String username = admin.getUsername();
        Date date = new Date();
        String name = proceedingJoinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        String s = UUID.randomUUID().toString().replace("-", "");
        Log log = new Log().setId(s).setUsername(username).setTime_date(date).setMethodname(name).setVa(value);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status="success";
            log.setStatus(status);
            logDao.insert(log);
            return proceed;
        } catch (Throwable throwable) {
            String status = "error";
            log.setStatus(status);
            throwable.printStackTrace();
            return null;
        }


    }

}
