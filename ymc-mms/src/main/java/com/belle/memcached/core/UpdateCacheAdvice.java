package com.belle.memcached.core;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;

@Aspect
@Component
public class UpdateCacheAdvice extends CacheBase {
	@Pointcut("@annotation(com.belle.memcached.core.UpdateCacheAnnotation)")
	public void updateCachePointcut() {
	}

	@AfterReturning(pointcut = "updateCachePointcut()", returning = "retVal")
	public Object methodCacheHold(final JoinPoint joinPoint, final Object retVal) throws Throwable {
		try {
			// 获取目标方法
			final Method method = this.getMethod(joinPoint);
			final UpdateCacheAnnotation annotation = method.getAnnotation(UpdateCacheAnnotation.class);
			String cacheKey = this.getCacheKey(joinPoint.getArgs(), annotation.assignCacheKey());
			IMemcachedCache memcachedCache = this.cacheManager_parcel.getCache(annotation.clientName().name());
			memcachedCache.put(cacheKey, retVal);
		} catch (Throwable ex) {
			logger.error("Updating caching via " + joinPoint.toShortString() + " aborted due to an error.", ex);
		}

		return retVal;
	}
}
