package com.yougou.api.interceptor.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.yougou.api.ImplementorInvocation;
import com.yougou.api.constant.BitWeight;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.InterceptionException;
import com.yougou.api.interceptor.AbstractInterceptor;
import com.yougou.dto.output.PageableOutputDto;

/**
 * 分页数据拦截器
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 1:17:37 PM
 */
public class PageInterceptor extends AbstractInterceptor {

	@Override
	public Object intercept(ImplementorInvocation invocation) throws InterceptionException {
		Long apiWeight = invocation.getImplementorProxy().getImplementorMapping().getApiWeight();
		
		// 分页鉴权
		if (BitWeight.PAGING.in(apiWeight)) {
			Map<String, Object> parameters = invocation.getImplementorContext().getParameters();
			Integer pageIndex = MapUtils.getInteger(parameters, "page_index");
			Integer pageSize = MapUtils.getInteger(parameters, "page_size");
			
			if (pageIndex == null) {
				throw new InterceptionException(YOPBusinessCode.API_PAGING_IS_INVALID, "page_index is invalid");
			}
			if (pageSize == null) {
				throw new InterceptionException(YOPBusinessCode.API_PAGING_IS_INVALID, "page_size is invalid");
			}
			
			// 计算页码并将结果重写
			parameters.put("page_index", (pageIndex * pageSize - pageSize));
			
			Object result = invocation.invoke();
			
			// 如返回结果为分页对象对重写的页码进行还原
			if (PageableOutputDto.class.isInstance(result)) {
				((PageableOutputDto) result).setPage_index(pageIndex);
			} else if (com.yougou.ordercenter.vo.merchant.output.PageableOutputDto.class.isInstance(result)) {
				//仅适用于订单返回 结果
				((com.yougou.ordercenter.vo.merchant.output.PageableOutputDto) result).setPage_index(pageIndex);
			}
			
			return result;
		}
		
		return invocation.invoke();
	}
}
