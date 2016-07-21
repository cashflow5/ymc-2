package com.belle.memcached.core;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;

@SuppressWarnings("unchecked")
public class CacheBase {
	/** logger */
	protected final Log logger = LogFactory.getLog(CacheBase.class);

	protected ICacheManager<IMemcachedCache> cacheManager_parcel=Cache.getManager();

	/**
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected Method getMethod(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Signature sig = joinPoint.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new NoSuchMethodException("This annotation is only valid on a method.");
		}
		final MethodSignature msig = (MethodSignature) sig;
		final Object target = joinPoint.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}

	/**
	 * @param method
	 * @param assignCacheKey
	 * @return
	 * @throws IllegalArgumentException
	 */
	protected String getCacheKey(Object[] args, String cacheKeyExpression) throws NoSuchMethodException,
			IllegalArgumentException {
		if (cacheKeyExpression == null || cacheKeyExpression.trim().equals("")) {
			logger.error("This assignCacheKey is not valid on a method.");
			new IllegalArgumentException("This assignCacheKey is not valid on a method.");
		}

		// 解析assignCacheKey表达式,格式如： $(id)+ hello + $(coupon.name)
		StringBuffer sbCacheKey = new StringBuffer(128);
		String[] params = cacheKeyExpression.replaceAll(" ", "").split("[+]");
		for (int i = 0; i < params.length; i++) {
			if (params[i] == null || "".equals(params[i].trim())) {
				continue;
			}

			Pattern pattern = Pattern.compile("^([$][(]).*[)]$");
			Matcher matcher = pattern.matcher(params[i]);
			if (matcher.find()) {
				// 根据参数获取参数值：$(coupon.name)
				String param = params[i].substring(2, params[i].length() - 1);
				sbCacheKey.append(this.getArguValue(args, param));
			} else {
				sbCacheKey.append(params[i]);
			}
		}

		return sbCacheKey.toString();
	}

	/**
	 * 根据参数名获取参数值
	 *
	 * @param args
	 * @param param
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 */
	private String getArguValue(Object[] args, String param) throws NoSuchMethodException, IllegalArgumentException {
		String[] arrParam = param.split("[.]");
		if (arrParam[0] == null || "".equals(arrParam[0])) {
			logger.error("This assignCacheKey is not valid on a method.");
			new IllegalArgumentException("This assignCacheKey is not valid on a method.");
		}

		// 方法入参列表中匹配当前参数对象
		int index = Integer.parseInt(arrParam[0].replaceAll("param", ""));
		Object currObject = args[index];

		try {
			for (int i = 1; i < arrParam.length; i++) {
				currObject = BeanUtils.getProperty(currObject, arrParam[i]);
			}
		} catch (Exception ex) {
			logger.error("This assignCacheKey is not valid on a method.");
			new IllegalArgumentException("This assignCacheKey is not valid on a method.");
		}

		return currObject!=null? currObject.toString():"";
	}
}
