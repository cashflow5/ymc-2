package com.belle.infrastructure.freemarker.functions;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.belle.infrastructure.log.factory.AppLogFactory;
import com.belle.infrastructure.log.model.vo.AppLog;
import com.belle.infrastructure.util.EncodeUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 分类位置Freemarker函数(适用于商品详情页、点评页等) ，Param顺序：商品Id
 * 生成效果：首页 > 女鞋 > 凉鞋 > 纯凉鞋 > BELLE/百丽2011年春黑绵羊女凉鞋3BG15D
 * @author 吴阳
 * @history 2011-7-11 新建
 */
public class WebsiteFunction implements TemplateMethodModel{

	private final AppLog logger = AppLogFactory.getLog(WebsiteFunction.class);

	/** 商品Id */
	private String commodityId;

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		StringBuffer sbHtml = new StringBuffer(200);
		// 校验参数
		if(!this.checkParam(arg0)){
			return "";
		}
		try {
//			String strCommodityId = this.getCommodityId();
//			ServiceLocator serviceLocator = ServiceLocator.getInstance();
//			// 商品Service
//			ICommodityService commodityService = serviceLocator.getBeanFactory().getBean(CommodityServiceImpl.class);
//			Commodity commodity = commodityService.findCommodityById(strCommodityId);
//			// 商品详情Component
//			IProductDetailComponent productDetailComponent = serviceLocator.getBeanFactory().getBean(ProductDetailComponentImpl.class);
//			List<Catb2c> lstCatb2c = productDetailComponent.findParentAndSelfByCommodtiyId(commodity.getId());
//			if(lstCatb2c == null || lstCatb2c.isEmpty()){
//				logger.error("商品[" + commodity.getNo() + "]未查询到分类信息");
//				return "";
//			}
//			String strLevel1CatNo = null;
//
//			String strLevel2CatNo = null;
//
//			String strLevel3CatNo = null;
//			for(Catb2c catb2c : lstCatb2c){
//				if(catb2c.getLevel() == 1){
//					strLevel1CatNo = catb2c.getNo();
//				}else if(catb2c.getLevel() == 2){
//					strLevel2CatNo = catb2c.getNo();
//				}else if(catb2c.getLevel() == 3){
//					strLevel3CatNo = catb2c.getNo();
//				}
//			}
//			for(Catb2c catb2c : lstCatb2c){
//				if(catb2c.getLevel() == 1){
////					sbHtml.append("<a href=\"/s/search.sc?param0=").append(strLevel1CatNo).append("&param1=").append(urlEncode(strLevel1CatName)).append("\">");
//					sbHtml.append("<a href=\"/search/").append(strLevel1CatNo).append("~1~~~~~~~~~").append("\">");
//					sbHtml.append(" ").append(catb2c.getCatName()).append("</a> &gt;");
//				}else if(catb2c.getLevel() == 2){
////					sbHtml.append(" <a href=\"/s/search.sc?param0=").append(strLevel1CatNo).append("&param1=").append(urlEncode(strLevel1CatName)).append("&param2=").append(urlEncode(strLevel2CatName)).append("\">");
//					sbHtml.append(" ").append("<a href=\"/search/").append(strLevel1CatNo).append("~1~").append(strLevel2CatNo).append("~~~~~~~~").append("\">");
//					sbHtml.append(catb2c.getCatName()).append("</a> &gt;");
//				}else if(catb2c.getLevel() == 3){
////					sbHtml.append(" <a href=\"/s/search.sc?param0=").append(strLevel1CatNo).append("&param1=").append(urlEncode(strLevel1CatName)).append("&param2=").append(urlEncode(strLevel2CatName)).append("&param4=").append(urlEncode(strLevel3CatName)).append("\">");
//					sbHtml.append("<a href=\"/search/").append(strLevel1CatNo).append("~1~").append(strLevel2CatNo).append("~").append(strLevel3CatNo).append("~~~~~~~").append("\">");
//					sbHtml.append(" ").append(catb2c.getCatName()).append("</a> &gt;");
//				}
//			}
//			sbHtml.append(" ").append(commodity.getCommodityName());
		} catch (Exception e) {
			return "";
		}
		return sbHtml.toString();
	}

	private String urlEncode(String str){
		return EncodeUtils.urlEncode(EncodeUtils.urlEncode(str));
	}

	/**
	 * 校验参数
	 * @param arg
	 */
	private boolean checkParam(List arg) {
		try{
			Object objCommodityId = arg.get(0);
			if (objCommodityId != null && StringUtils.isNotBlank((String) objCommodityId)) {
				this.setCommodityId((String) objCommodityId);
			}
			return true;
		}catch(Exception ex){
			return false;
		}
	}
}
