package com.yougou.kaidian.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 以事务为边界进行读写分离
 * 
 * @author luo.zj
 * @since 2012-08-21 16:00
 */
@Aspect
@Component
@Order(100)
public class DataSourceAdvice {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void transactionalPointcut() {
	}

	/**
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getMethod(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Signature sig = joinPoint.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new NoSuchMethodException("This annotation is only valid on a method.");
		}
		final MethodSignature msig = (MethodSignature) sig;
		final Object target = joinPoint.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}

	@Before("transactionalPointcut()")
	public void before(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Method method = this.getMethod(joinPoint);
		System.out.println(method.getName() + "遇到事务开始边界");
		//如果拥有事务上下文，则将连接绑定到事务上下文中 
		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
//			System.out.println("执行" + method.getName() + "前没有事务");
			DataSourceSwitcher.setDataSource("master");
//			System.out.println("执行" + method.getName() + "前切换到主库");
		} else {
//			System.out.println("执行" + method.getName() + "前有事务");
		}
	}

	@After("transactionalPointcut()")
	public void after(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Method method = this.getMethod(joinPoint);
//		System.out.println(method.getName() + "遇到事务结束边界");
		//如果拥有事务上下文，则将连接绑定到事务上下文中 
		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
//			System.out.println("退出方法" + method.getName() + "前没有事务");
			DataSourceSwitcher.clearDataSource();
//			System.out.println("退出方法" + method.getName() + "前切换到从库");
		} else {
//			System.out.println("退出方法" + method.getName() + "前有事务");
		}
	}

	@AfterThrowing("transactionalPointcut()")
	public void afterThrowing(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Method method = this.getMethod(joinPoint);
//		System.out.println("执行方法" + method.getName() + "发送异常，切换成从库");
		DataSourceSwitcher.clearDataSource();
	}
	
	@Before("execution(* com.yougou.kaidian.bi.dao.Report*.*(..))")
	//@Before("execution(* com.yougou.kaidian.bi.service.impl.Report*.*(..))")
	public void beforeReport(JoinPoint joinPoint) throws NoSuchMethodException{
		Method method = this.getMethod(joinPoint);
		String methodName = method.getName();		
		DataSourceSwitcher.setReport();
		logger.warn("Before执行---"+methodName+"---将数据源切换至数据报表数据源:"+DataSourceSwitcher.getDataSource());
	}
	
	@After("execution(* com.yougou.kaidian.bi.dao.Report*.*(..))")
	public void afterReport(JoinPoint joinPoint) throws NoSuchMethodException{
		Method method = this.getMethod(joinPoint);
		String methodName = method.getName();		
		DataSourceSwitcher.clearDataSource();
		logger.warn("After执行---"+methodName+"---将数据源切换至默认数据源:"+DataSourceSwitcher.getDataSource());
	}
	
	@AfterThrowing("execution(* com.yougou.kaidian.bi.dao.Report*.*(..))")
	public void afterThrowingReport(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Method method = this.getMethod(joinPoint);
		DataSourceSwitcher.clearDataSource();
		logger.warn("Exception执行---"+method.getName()+"---出现异常后将数据源切换至默认数据源:"+DataSourceSwitcher.getDataSource());
	}

}
