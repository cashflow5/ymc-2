package com.belle.infrastructure.freemarker.functions;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.belle.infrastructure.log.factory.AppLogFactory;
import com.belle.infrastructure.log.model.vo.AppLog;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 用于生成商品链接地址(仅给获取不到商品分类No、商品No情况使用) param顺序 : 商品Id、商品No
 *
 * @author 吴阳
 * @history 2011-7-12 新建
 */
public class GoodsUrlFunction implements TemplateMethodModel {

	private final AppLog logger = AppLogFactory.getLog(GoodsUrlFunction.class);

	/** 商品id */
	private String commodityId;

	/** 商品No */
	private String commodityNo;

	/** 环境参数 */
	private String model;

	/** 开发环境商品地址基础链接 */
	private final String COMMODITY_BASE_URL = "/yitianmall/commodityshow/commoditydetail/goods.sc?commodityId=";

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		StringBuffer sbHtml = new StringBuffer(200);
		// 校验参数
		if (!this.checkParam(arg0)) {
			return "";
		}
		String strCommodityId = this.getCommodityId();
		String strCommodityNo = this.getCommodityNo();
		String strModel = this.getModel();
		try {
			// 开发环境
//			if (StringUtils.equals("500", strModel)) {
//				sbHtml.append(COMMODITY_BASE_URL).append(strCommodityId);
//			} else {
//				// 生产环境
//				ServiceLocator serviceLocator = ServiceLocator.getInstance();
//				// 商品Service
//				ICommodityService commodityService = serviceLocator.getBeanFactory().getBean(CommodityServiceImpl.class);
//				Commodity commodity = null;
//				if(StringUtils.isNotEmpty(strCommodityId)){
//					commodity = commodityService.findCommodityById(strCommodityId);
//				}else if(StringUtils.isNotEmpty(strCommodityNo)){
//					commodity = commodityService.findCommodityByNo(strCommodityNo);
//				}
//				if (commodity == null) {
//					return "";
//				}
//				String commodityNo = commodity.getNo();
//				String catNo = commodity.getCatNo();
//				sbHtml.append("/").append("c_").append(catNo).append("/p_").append(commodityNo).append(".html");
//			}
		} catch (Exception ex) {
			logger.error("查询商品[" + strCommodityId + "]链接地址发生异常", ex);
			return "";
		}
		return sbHtml.toString();
	}

	/**
	 * 校验参数
	 *
	 * @param arg
	 */
	private boolean checkParam(List arg) {
		try {
			Object objCommodityId = arg.get(0);
			if (objCommodityId != null && StringUtils.isNotBlank((String) objCommodityId)) {
				this.setCommodityId((String) objCommodityId);
			}
			Object objCommodityNo = arg.get(1);
			if (objCommodityNo != null && StringUtils.isNotBlank((String) objCommodityNo)) {
				this.setCommodityNo((String) objCommodityNo);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
}
