package com.yougou.kaidian.commodity.thrift;



import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yougou.kaidian.commodity.dao.LayoutSettingMapper;
import com.yougou.kaidian.commodity.model.vo.LayoutTemplate;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.tools.common.utils.SpringContextHolder;


public class LayoutSettingThriftServiceImpl implements LayoutSettingThriftService.Iface {
	
	 /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory
            .getLogger(LayoutSettingThriftServiceImpl.class);
	

	@Override
	public String getLayoutSetting(String commodityNo) throws TException {
		// TODO Auto-generated method stub
		String layoutHtml = "";
		LayoutSettingMapper layoutSettingMapper = (LayoutSettingMapper)SpringContextHolder.getBean(LayoutSettingMapper.class);		
		
		if (StringUtils.isEmpty(layoutHtml)){			
			if(layoutSettingMapper!=null){
				LayoutTemplate template = layoutSettingMapper.querySettingHtmlByCommodityNo(commodityNo);
				if(template!=null){
					layoutHtml = template.getLayoutHtml();
				}else{
					String merchantCode = layoutSettingMapper.queryMerchantCodeByCommodityNo(commodityNo);
					if(merchantCode!=null){
						template = layoutSettingMapper.querySettingHtmlByMerchantCode(merchantCode);
						if(template!=null){
							layoutHtml = template.getLayoutHtml();
						}
					}else{
						logger.error("LayoutSettingThriftServiceImpl 商品编码：{}查询数据库表t_commodity_style未获取到商品数据...",commodityNo);
					}
				}
				
			}else{
				logger.error("LayoutSettingThriftServiceImplton 通过 SpringContextHolder.getBean(LayoutSettingMapper.class) 获取bean失败...");
			}
		}
		return layoutHtml;
	}


	@Override
	public String newGetLayoutSetting(String commodityNo) throws TException {
		// TODO Auto-generated method stub
		String layoutHtmlFile = "";
		LayoutSettingMapper layoutSettingMapper = (LayoutSettingMapper)SpringContextHolder.getBean(LayoutSettingMapper.class);		
		
		if (StringUtils.isEmpty(layoutHtmlFile)){			
			if(layoutSettingMapper!=null){
				LayoutTemplate template = layoutSettingMapper.querylayoutHtmlFileByCommodityNo(commodityNo);
				if(template!=null){
					layoutHtmlFile = template.getHtmlFilePath();
				}else{
					String merchantCode = layoutSettingMapper.queryMerchantCodeByCommodityNo(commodityNo);
					if(merchantCode!=null){
						template = layoutSettingMapper.querylayoutHtmlFileByMerchantCode(merchantCode);
						if(template!=null){
							layoutHtmlFile = template.getHtmlFilePath();
						}
					}else{
						logger.error("LayoutSettingThriftServiceImpl 商品编码：{}查询数据库表t_commodity_style未获取到商品数据...",commodityNo);
					}
				}
				
			}else{
				logger.error("LayoutSettingThriftServiceImplton 通过 SpringContextHolder.getBean(LayoutSettingMapper.class) 获取bean失败...");
			}
		}
		layoutHtmlFile = StringUtils.isNotBlank(layoutHtmlFile) ? (Constant.FILE_NAME_FOR_BROWER_URL_PATH+layoutHtmlFile):layoutHtmlFile;
		return layoutHtmlFile;
	}
	

}
