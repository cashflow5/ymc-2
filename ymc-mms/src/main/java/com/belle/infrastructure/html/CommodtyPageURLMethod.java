package com.belle.infrastructure.html;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.tools.common.utils.ServiceLocator;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 获取商品页url
 * @author daixiaowei
 *
 */
public class CommodtyPageURLMethod implements TemplateMethodModel, Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(CommodtyPageURLMethod.class);

	/**
	 * 改为用新的URL规则，UUID用commodityNo代替 modified by tanfeng. ${url(model, key,
	 * uuid, url)} model 模式(开发500\测试200) key 模板类型 uuid url 原始路径
	 */
	@SuppressWarnings("unchecked")
	public Object exec(List args) throws TemplateModelException {
		if(args!=null&&args.size()>0){
			try {
				ICommodityBaseApiService service = (ICommodityBaseApiService) ServiceLocator.getInstance().getBeanFactory().getBean("commodityBaseApiService");
				String result = "http://www.yougou.com/"+service.getCommodityPageUrlWithExtension(args.get(0).toString());
				return result;
			} catch (Exception e) {
				logger.error("获取单品页url dubbo接口异常：",e);
			}
			return "";
		}else{
			return "";
		}
	}
	
}
