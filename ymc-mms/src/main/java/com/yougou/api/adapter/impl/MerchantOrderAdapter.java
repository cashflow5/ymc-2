package com.yougou.api.adapter.impl;

import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.MerchantOrderTarget;
import com.yougou.api.constant.Constants;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.service.BusinessLogger;
import com.yougou.api.util.SimpleHttpClientUtils;
import com.yougou.yop.api.IMerchantApiOrderService;

@Component
public class MerchantOrderAdapter implements MerchantOrderTarget {
	
	@Resource
	private IMerchantApiOrderService merchantOrderApi;
	@Resource
	private BusinessLogger businessLogger;
	
	@Override
	public Object queryOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.queryOrder(parameterMap);
	}

	@Override
	public Object queryIncrementOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.queryIncrementOrder(parameterMap);
	}

	@Override
	public Object getOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.getOrder(parameterMap);
	}

	@Override
	public Object getBarcodeOrder(Map<String, Object> parameterMap) throws Exception {
		String basePath = MapUtils.getString(parameterMap, "base_path");
		String code = MapUtils.getString(parameterMap, "order_sub_no");
		String mimeType = MapUtils.getString(parameterMap, "mime_type", "");
		String stream = MapUtils.getString(parameterMap, "stream", "");
		String uri = MessageFormat.format("{0}/mms/barcode?code={1}&ft={2}&stream={3}", basePath, code, mimeType, stream);
		return SimpleHttpClientUtils.executeMethod(new GetMethod(uri));
	}

	@Override
	public Object queryCanceledOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.queryCanceledOrder(parameterMap);
	}

	@Override
	public Object updateNondeliveryOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.updateNondeliveryOrder(parameterMap);
	}

	@Override
	public Object updateStockoutOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.updateStockoutOrder(parameterMap);
	}

	@Override
	public Object updateCompletedOrder(Map<String, Object> parameterMap) throws Exception {
		String merchantCode = MapUtils.getString(parameterMap, "merchant_code");
		String[] orderSubNos = StringUtils.split(MapUtils.getString(parameterMap, "order_sub_nos"), ",");
		String[] expressCodes = StringUtils.split(MapUtils.getString(parameterMap, "express_codes"), ",");
		String[] shipTimes = StringUtils.split(MapUtils.getString(parameterMap, "ship_times"), ",");
		String[] logisticsCompanyCodes = StringUtils.split(MapUtils.getString(parameterMap, "logistics_company_codes"), ",");

		if (ArrayUtils.isEmpty(orderSubNos)) {
			throw new YOPRuntimeException(YOPBusinessCode.PARAM_NOT_REQUIRED, "order_sub_nos is required");
		}
		if (!ArrayUtils.isSameLength(orderSubNos, expressCodes)) {
			throw new YOPRuntimeException(YOPBusinessCode.PARAM_GROUP_LENGTH_IS_INCONSISTENCY, "order_sub_nos and express_codes length is inconsistency");
		}
		if (!ArrayUtils.isSameLength(orderSubNos, logisticsCompanyCodes)) {
			throw new YOPRuntimeException(YOPBusinessCode.PARAM_GROUP_LENGTH_IS_INCONSISTENCY, "order_sub_nos and logistics_company_codes length is inconsistency");
		}
		if (!ArrayUtils.isSameLength(orderSubNos, shipTimes)) {
			throw new YOPRuntimeException(YOPBusinessCode.PARAM_GROUP_LENGTH_IS_INCONSISTENCY, "order_sub_nos and ship_times length is inconsistency");
		}

		for (int i = 0; i < orderSubNos.length; i++) {
			try {
                Boolean result = merchantOrderApi.orderOutStoreForMerchant(merchantCode, orderSubNos[i], logisticsCompanyCodes[i], expressCodes[i],
                        DateUtils.parseDate(shipTimes[i], Constants.DATE_PATTERNS));
			   if (!result) {
				   businessLogger.log("order.completed.update", YOPBusinessCode.ORDER_OUT_STORE_FAILURE, "出库失败", merchantCode);
				   return new YOPRuntimeException(YOPBusinessCode.ORDER_OUT_STORE_FAILURE, MessageFormat.format("订单 {0} 出库失败", orderSubNos[i]));
				}
			} catch (Exception e) {
				businessLogger.log("order.completed.update", YOPBusinessCode.ORDER_OUT_STORE_FAILURE, e.getMessage(), merchantCode);
				return new YOPRuntimeException(YOPBusinessCode.ORDER_OUT_STORE_FAILURE, e.getMessage());
			}
		}

		return true;
	}

	@Override
	public Object updateSynchronizedOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.updateSynchronizedOrder(parameterMap);
	}

	@Override
	public Object queryInterceptOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.queryInterceptOrder(parameterMap);
	}

	@Override
	public Object updateInterceptOrder(Map<String, Object> parameterMap) throws Exception {
		return merchantOrderApi.updateInterceptOrder(parameterMap);
	}

}
