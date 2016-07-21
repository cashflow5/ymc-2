package com.belle.infrastructure.spring;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * 类DataSourceAdviceFilter.java的实现描述：TODO 类实现描述 
 * @author 黄斌
 * @date 2011-12-26 下午12:08:17
 */
public class DataSourceAdviceForFilter implements MethodBeforeAdvice, ThrowsAdvice {

    private Logger log = Logger.getLogger(this.getClass().getName());
    

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("切入点: " + target.getClass().getName() + "类中"
                + method.getName() + "方法");
            log.info("操作切换到: master");
            
            DataSourceSwitcher.setMaster();
    }
    
    public void afterThrowing(Method method, Object[] args, Object target,
            Exception ex) throws Throwable {
        log.info("抛出异常数据操作切换到: master");
        DataSourceSwitcher.setMaster();
    }
}
