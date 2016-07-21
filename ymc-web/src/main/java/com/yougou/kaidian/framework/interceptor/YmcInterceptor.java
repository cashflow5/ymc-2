package com.yougou.kaidian.framework.interceptor;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;

/**
 * 
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : YMC 拦截器
 * @AUTHOR : le.sm
 * @DATE :2015-6-1
 *       </p>
 **************************************************************** 
 */
 
public class YmcInterceptor  implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(YmcInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		 Map<String,Object> unionUser  = SessionUtil.getUnionUser(request);////
		 String merchantCode = "";
		 String operater = "";
		 String supplier = "";
		if(unionUser != null){
			
			merchantCode = MapUtils.getString(unionUser, "supplier_code");
			operater = MapUtils.getString(unionUser, "login_name");
			supplier = MapUtils.getString(unionUser, "supplier");
			YmcThreadLocalHolder.setMerchantCode(merchantCode);
			YmcThreadLocalHolder.setOperater(operater);
			YmcThreadLocalHolder.setMerchantName(supplier);
			logger.info(" ------YmcInterceptor- merchantcode:{},operater:{},supplier:{}", merchantCode, operater, supplier);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		YmcThreadLocalHolder.clearSimple(YmcThreadLocalHolder.MERCHANT_LOCAL);
//		YmcThreadLocalHolder.clearSimple(YmcThreadLocalHolder.OPERATER_LOCAL);
//		YmcThreadLocalHolder.clearSimple(YmcThreadLocalHolder.MERCHANT_NAME_LOCAL);
		YmcThreadLocalHolder.clearAll();  
	}

}
