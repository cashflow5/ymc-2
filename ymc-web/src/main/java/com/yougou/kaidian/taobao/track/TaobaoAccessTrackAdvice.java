package com.yougou.kaidian.taobao.track;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.dao.TaobaoAccessTrackMapper;
import com.yougou.kaidian.taobao.model.TaobaoAccessTrackVo;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.tools.common.utils.DateUtil;

@Component
@Aspect
public class TaobaoAccessTrackAdvice {

	@Resource
	private TaobaoAccessTrackMapper mapper;
	private static final Logger logger = LoggerFactory.getLogger(TaobaoAccessTrackAdvice.class);

	@Pointcut("@annotation(com.yougou.kaidian.taobao.annotation.TaobaoApiMethod)")
	public void taobaoAccessPointcut() {
	}

	/**
	 * 对淘宝api调用进行轨迹记录
	 * 
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around("taobaoAccessPointcut()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("定义切面的方法执行start...");
		TaobaoAccessTrackVo vo = new TaobaoAccessTrackVo();
		Map<String, String> map = (Map<String, String>) pjp.getArgs()[1];
		vo.setId(UUIDGenerator.getUUID());
		// 记录tao_api
		final Method method = this.getMethod(pjp);
		if (method.isAnnotationPresent(TaobaoApiMethod.class)) {
			TaobaoApiMethod t = method.getAnnotation(TaobaoApiMethod.class);
			vo.setTaobaoApi(t.name());
		}
		vo.setAccessStart(System.currentTimeMillis());
		vo.setOperater(map.get("operater"));
		vo.setNickId(map.get("nickId")!=null?map.get("nickId"):"0");
		vo.setOperated(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 执行切面定义的方法体
		TaobaoApiReturnData<T> retVal = (TaobaoApiReturnData<T>) pjp.proceed();
		vo.setAccessEnd(System.currentTimeMillis());
		vo.setAccessResult(retVal.getBody());
		mapper.insertTaobaoAccessTrack(vo);
		logger.debug("定义切面的方法执行后的输出结果：" + retVal.getBody());
		logger.debug("定义切面的方法执行end...");
		return retVal;
	}

	/**
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getMethod(final ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
		final Signature sig = joinPoint.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new NoSuchMethodException("This annotation is only valid on a method.");
		}
		final MethodSignature msig = (MethodSignature) sig;
		final Object target = joinPoint.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}

}