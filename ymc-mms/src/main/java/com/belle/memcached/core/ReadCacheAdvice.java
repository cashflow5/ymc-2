package com.belle.memcached.core;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;

@Aspect
@Component
public class ReadCacheAdvice extends CacheBase {
	@Pointcut("@annotation(com.belle.memcached.core.ReadCacheAnnotation)")
	public void methodCachePointcut() {
	}

	@Around("methodCachePointcut()")
	public Object methodCacheHold(final ProceedingJoinPoint joinPoint) throws Throwable {
		IMemcachedCache memcachedCache = null;
		Object result = null;
		String cacheKey;
		try {
			// 获取目标方法
			final Method method = this.getMethod(joinPoint);
			final ReadCacheAnnotation annotation = method.getAnnotation(ReadCacheAnnotation.class);
			memcachedCache = this.cacheManager_parcel.getCache(annotation.clientName().name());

			// 是否启用本地缓存
			boolean localCache = annotation.localCache();
			cacheKey = this.getCacheKey(joinPoint.getArgs(), annotation.assignCacheKey());
			if (localCache && annotation.expiration() > 0) {
				result = memcachedCache.get(cacheKey, annotation.expiration());
			} else {
				result = memcachedCache.get(cacheKey);
			}

			if (result != null) {
				return result;
			}
		} catch (Throwable ex) {
			logger.error("Caching on " + joinPoint.toShortString() + " aborted due to an error.", ex);
			return joinPoint.proceed();
		}

		// 缓存命中失败,执行方法从DB获取数据
		result = joinPoint.proceed();

		try {
			// 将数据缓存到缓存服务器
			if (result != null) {
				memcachedCache.put(cacheKey, result);
			}
		} catch (Throwable ex) {
			logger.error("Caching on " + joinPoint.toShortString() + " aborted due to an error.", ex);
		}
		return result;
	}
}
