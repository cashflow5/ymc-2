package com.yougou.kaidian.commodity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yougou.kaidian.commodity.dao.LayoutSettingMapper;
import com.yougou.kaidian.commodity.model.vo.LayoutTemplate;
import com.yougou.kaidian.commodity.service.ILayoutSettingService;
import com.yougou.kaidian.commodity.thrift.CmsApi;
import com.yougou.kaidian.commodity.util.FSSFilePublisher;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;

/**
 * 商家中心版式设置服务层实现类
 * @author zhang.f1
 *
 */
@Service
public class LayoutSettingServiceImpl implements ILayoutSettingService {
	
	@Resource
	private LayoutSettingMapper layoutSettingMapper;
	
	
	
	@Resource
	private FSSFilePublisher fssFilePublisher;
	
	@Resource
	private Properties configProperties;
	
	 /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(LayoutSettingServiceImpl.class);

	@Override
	public void addLayoutTemplate(LayoutTemplate template) {
		// TODO Auto-generated method stub
		layoutSettingMapper.addLayoutTemplate(template);
	}

	@Override
	public void updateLayoutTemplate(LayoutTemplate template) {
		// TODO Auto-generated method stub
		layoutSettingMapper.updateLayoutTemplate(template);
	}
	
	@Transactional
	@Override
	public void deleteLayoutTemplate(String templateId) {
		// TODO Auto-generated method stub
		layoutSettingMapper.deleteLayoutSettingCommodity(templateId);
		layoutSettingMapper.deleteLayoutTemplate(templateId);
	}

	@Override
	public List<LayoutTemplate> queryLayoutTemplate(String merchantCode) {
		// TODO Auto-generated method stub
		return layoutSettingMapper.queryLayoutTemplate(merchantCode);
	}

	@Override
	public void deleteLayoutSettingByMerchantCode(String merchantCode) {
		// TODO Auto-generated method stub
		layoutSettingMapper.deleteLayoutSettingByMerchantCode(merchantCode);
	}

	@Override
	public void deleteLayoutSettingByCommodityNo(String commodityNo) {
		// TODO Auto-generated method stub
		layoutSettingMapper.deleteLayoutSettingByCommodityNo(commodityNo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void layoutSettingForSomeCommoditys(LayoutTemplate template, List<String> commodityNos) {
		// TODO Auto-generated method stub
		String type = String.valueOf(template.getType());
		//来源固定版式页面，没有版式ID，需要新增此条版式模板
		if(null!=type && Constant.LAYOUT_SET_TEMPLATE_TYPE_FIXED.equals(type) && template.getId() == null ){
			String layoutId = UUIDGenerator.getUUID();
			template.setId(layoutId);
			String now = DateUtils.formatDate(new Date(), "yyyy/MM/dd");
			template.setLayoutName(now+Constant.LAYOUT_SET_FIXED_TEMPLATE_SUFFIX);		
			//生成静态页
			String filePath = getHtmlFilePath(template,now);
			fssFilePublisher.setStaticHtml(template.getLayoutHtml(),filePath);
			//保存模板
			template.setHtmlFilePath(filePath);
			layoutSettingMapper.addLayoutTemplate(template);
		// 自定义版式，修改版式内容	
		}else{
			//生成静态页
			String filePath = getHtmlFilePath(template,null); 
			fssFilePublisher.setStaticHtml(template.getLayoutHtml(),filePath);
			//修改模板
			template.setHtmlFilePath(filePath);
			layoutSettingMapper.updateLayoutTemplate(template);
		}
		
		if(null != commodityNos && commodityNos.size()>0){
			
			//查出当前模板绑定的商品编码
			List<String> tempCommodityNos = layoutSettingMapper.queryCommodityNoByTemplateId(template.getId());
			//从当前设置绑定的商品编码中过滤掉已经绑定的商品编码
			if(tempCommodityNos != null && tempCommodityNos.size() > 0){
				 List<String> finalCommodityNos = new ArrayList<String>();				
				for(String setCommdityNo : commodityNos){
					boolean flag =false;
					for(String commodityNo : tempCommodityNos ){ 
						if(commodityNo.equals(setCommdityNo)){
							flag = true;
							break;
						}
					}
					if(!flag){
						finalCommodityNos.add(setCommdityNo);
					}
				}
				//保存版式设置绑定的商品编码，并重新生成单品页
				if(finalCommodityNos.size() > 0){
					saveLayOutSet(template, finalCommodityNos);			
					
					Map<String, String> params = commodityNosToParam(finalCommodityNos);
					buildPageByThreadPool(params);
				}
			}else{
				//保存版式设置绑定的商品编码，并重新生成单品页
				saveLayOutSet(template, commodityNos);
				
				Map<String, String> params = commodityNosToParam(commodityNos);
				buildPageByThreadPool(params);
			}			
		}				
	}
	
	/**
	 * 封装调用cms 接口重新生成单品页参数,commodityNos：商品编码1,商品编码2
	 * @param finalCommodityNos
	 * @return
	 */
	private Map<String, String> commodityNosToParam( List<String> finalCommodityNos) {
		Map<String,String> params = new HashMap<String,String>();
		String commodityStr = StringUtils.join(finalCommodityNos, ",");
		params.put("commodityNos", commodityStr);
		return params;
	}
	
	/**
	 * 另起线程调用cms 接口
	 * @param finalCommodityNos
	 */
	private void buildPageByThreadPool(final Map<String,String> params) {
		ExecutorService threadPool = null;
		try{
			// updated by zhangfeng 2016.5.22 直接异步线程处理，不用每次创建单个线程池处理	
			/*threadPool = Executors.newFixedThreadPool(1);
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						buildYmcPage(params);
					} catch (TException e) {
						// TODO Auto-generated catch block
						logger.error("buildPageByThreadPool，调用cms 接口异常，params={}",params, e);
						e.printStackTrace();
					}
				}
			});*/
			Runnable run = new Runnable() {			
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						logger.warn("线程{}，开始调用cms 接口buildYmcPage生成单品页", Thread.currentThread().getName());
						buildYmcPage(params);
						logger.warn("线程{}，结束调用cms 接口buildYmcPage生成单品页", Thread.currentThread().getName());
					} catch (TException e) {
						// TODO Auto-generated catch block
						logger.error("buildPageByThreadPool，调用cms 接口异常，params={}",params, e);
						e.printStackTrace();
					}
				}
			};
			Thread thread = new Thread(run);
			thread.setName("buildPage 版式设置调用cms 接口重新生成单品页");
			thread.start();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("buildPageByThreadPool调用cms接口生成单品页异常,params={}",params, e);
		}finally{
			if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
	}
	
	
	/**
	 * 保存版式设置：模板绑定的商品与之关联关系
	 * @param template
	 * @param settingList
	 * @param finalCommodityNos
	 */
	private void saveLayOutSet(LayoutTemplate template, List<String> finalCommodityNos) {
		// 保存版式设置参数
		ArrayList<Map<String,Object>> settingList = new ArrayList<Map<String,Object>>();
		//封装需要保存的模板与商品关联关系
		for(String commodityNo : finalCommodityNos){
			Map<String,Object> map = new HashMap<String,Object>();
			String id = UUIDGenerator.getUUID();
			map.put("id",id);
			map.put("layoutId", template.getId());
			map.put("commodityNo", commodityNo);		
			settingList.add(map);
		}
		
		//删除老的版式设置
		layoutSettingMapper.batchDeleteLayoutSettingByCommodityNo(finalCommodityNos);
		//保存新的版式设置
		layoutSettingMapper.batchAddLayoutSetting(settingList);
	}
	
	/**
	 * 调用cms接口重新生成单品页：传入商品编码则生成指定商品编码的单品页；传入商家编码则将此商家所有单品页重新生成
	 * @param params{'commodityNos' => ‘13001623,99874402', 'merchantCode' => 'SPxxxxxxxxx'}
	 * @return
	 * @throws TException
	 */
	private int buildYmcPage(Map<String,String> params) throws TException{
		String hostStr = configProperties.getProperty("thrift.cms.host", "10.10.20.19");
		String portStr = configProperties.getProperty("thrift.cms.port", "20883");
    	int port = Integer.parseInt(portStr);
    	JSONObject json = JSONObject.fromObject(params);
    	logger.warn("buildYmcPage thrift client connext server at {} port ;params={}",portStr,json);  
        TTransport transport = new TSocket(hostStr,port);
        transport.open();    
        TProtocol protocol = new TBinaryProtocol(transport);    
        CmsApi.Client client = new CmsApi.Client(protocol);    
        int result = client.buildYmcPagesByParams(params);    
        logger.warn("buildYmcPage result ==========={}",result);
        transport.close();    		
		return result;
	}
	
	/**
	 *拼装模板静态页shtml 文件路径 
	 * @param template
	 * @return
	 */
	private String getHtmlFilePath(LayoutTemplate template,String createTimeStr) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if(StringUtils.isBlank(createTimeStr)){
			LayoutTemplate temp = layoutSettingMapper.queryLayoutTemplateById(template.getId());		
			Date createTime = temp.getCreateTime();
		    createTimeStr = DateUtils.formatDate(createTime, "yyyy/MM/dd");
		}
		//组装模板静态页文件路径及名称
		String filePath =createTimeStr + "/" + merchantCode 
				+ Constant.LAYOUT_SET_FOR_SOME_COMMODITY_HTML_FILE_START+template.getId()+Constant.LAYOUT_SET_HTML_FILE_SUFFIX;
		return filePath;
	}
	
	/**
	 *拼装模板静态页shtml 文件路径 ，绑定所有商品模板
	 * @param template
	 * @return
	 */
	private String getHtmlFilePathForAll(LayoutTemplate template) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//组装模板静态页文件路径及名称
		String filePath = merchantCode + Constant.LAYOUT_SET_FOR_ALL_COMMODITY_HTML_FILE_START+merchantCode+Constant.LAYOUT_SET_HTML_FILE_SUFFIX;
		return filePath;
	}
	
	@Transactional
	@Override
	public void layoutSettingForAllCommoditys(LayoutTemplate template,String merchantCode) {
		// TODO Auto-generated method stub
		Integer type = template.getType();
		template.setMerchantCode(merchantCode);
		//查询当前商家绑定所有商品的模板
		LayoutTemplate temp = layoutSettingMapper.queryTemplateForAllByMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		//来源固定版式页面，没有版式ID，需要新增此条版式模板
		if(null!=type && type==0 && template.getId() == null ){
			String layoutId = UUIDGenerator.getUUID();
			template.setId(layoutId);
			template.setIsAll("Y");
			String now = DateUtils.formatDate(new Date(), "yyyy/MM/dd");
			template.setLayoutName(now+Constant.LAYOUT_SET_FIXED_TEMPLATE_SUFFIX);	
			//生成静态页
			String filePath = getHtmlFilePathForAll(template);
			fssFilePublisher.setStaticHtml(template.getLayoutHtml(),filePath);
			//保存模板
			template.setHtmlFilePath(filePath);
			layoutSettingMapper.addLayoutTemplate(template);			
		// 自定义版式，修改版式内容	
		}else{	
			updateTemplateToAll(template);
		}
		// 修改当前商家除当前模板，所有版式模板 isAll = N		
		layoutSettingMapper.updateLayoutTempalteIsAllNo(template);		
		
		if(temp != null){
			String forAllFilePath = temp.getHtmlFilePath();			
			//当前绑定所有商品的模板静态页路径为旧路径，如：2015/12/23/SP20150617439912/layout_template_all_SP20150617439912.shtml
			if(StringUtils.isNotBlank(forAllFilePath) && forAllFilePath.indexOf("/") == 4){
				// 删除当前商家所有版式设置绑定的商品编码
				layoutSettingMapper.deleteLayoutSettingByMerchantCode(merchantCode);
				// 当前商家所有商品重新生成单品页
				Map<String,String> params = new HashMap<String,String>();
				params.put("merchantCode", merchantCode);
				buildPageByThreadPool(params);
			//当前绑定所有商品的模板静态页路径为新路径，如：SP20150617439912/layout_template_all_SP20150617439912.shtml
			}else{
				//查出覆盖了该模板指向所有商品操作的版式设置，绑定的商品编码，
				List<String> commodityNos = layoutSettingMapper.queryLayoutSetCommodityNosByMerchantCode(merchantCode);				
				// 删除当前商家所有版式设置绑定的商品编码
				layoutSettingMapper.deleteLayoutSettingByMerchantCode(merchantCode);	
				//将覆盖了指向所有商品的版式设置，绑定的商品编码重新生成单品页，绑定到当前模板
				if(commodityNos !=null && commodityNos.size() > 0){					
					Map<String, String> params = commodityNosToParam(commodityNos);
					buildPageByThreadPool(params);
				}
			}
		}else{
			// 删除当前商家所有版式设置绑定的商品编码
			layoutSettingMapper.deleteLayoutSettingByMerchantCode(merchantCode);
			// 当前商家所有商品重新生成单品页
			Map<String,String> params = new HashMap<String,String>();
			params.put("merchantCode", merchantCode);
			buildPageByThreadPool(params);
		}	
	}
	
	/**
	 * 生成指向所有商品的静态页，并修改数据库中指向所有商品标记为Y
	 * @param template
	 */
	private void updateTemplateToAll(LayoutTemplate template) {
		//将当前模板绑定所有商品
		template.setIsAll("Y");
		//生成静态页
		String filePath = getHtmlFilePathForAll(template);
		fssFilePublisher.setStaticHtml(template.getLayoutHtml(),filePath);
		template.setHtmlFilePath(filePath);
		layoutSettingMapper.updateLayoutTemplate(template);
	}

	@Override
	public LayoutTemplate queryLayoutTemplateById(String id) {
		// TODO Auto-generated method stub
		return layoutSettingMapper.queryLayoutTemplateById(id);
	}

	@Override
	public List<String> queryCommodityNoByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		return layoutSettingMapper.queryCommodityNoByTemplateId(templateId);
	}

	@Override
	public void initAllUsingLayoutTemplate() {
		// TODO Auto-generated method stub
		List<LayoutTemplate> list = layoutSettingMapper.queryAllUsingLayoutTemplate();
		if(list != null && list.size() > 0){
			for(LayoutTemplate template : list){
				String isAll = template.getIsAll();
				String merchantCode = template.getMerchantCode();
				Date createTime = template.getCreateTime();
				String createTimeStr = DateUtils.formatDate(createTime, "yyyy/MM/dd");
				String filePath = null;
				if("Y".equals(isAll)){					
					//组装模板静态页文件路径及名称
				    filePath ="/"+ merchantCode 
				    		+ Constant.LAYOUT_SET_FOR_ALL_COMMODITY_HTML_FILE_START+merchantCode+Constant.LAYOUT_SET_HTML_FILE_SUFFIX;
					//生成静态页
				    fssFilePublisher.setStaticHtml(template.getLayoutHtml(),filePath);
					//修改模板静态页地址
					template.setHtmlFilePath(filePath);
					layoutSettingMapper.updateHtmlFilePath(template);
				}else{
					//组装模板静态页文件路径及名称
					filePath =createTimeStr +"/"+  merchantCode 
							+ Constant.LAYOUT_SET_FOR_SOME_COMMODITY_HTML_FILE_START+template.getId()+Constant.LAYOUT_SET_HTML_FILE_SUFFIX;
					fssFilePublisher.setStaticHtml(template.getLayoutHtml(),filePath);
					//修改模板静态页地址
					template.setHtmlFilePath(filePath);
					layoutSettingMapper.updateHtmlFilePath(template);
				}
			}
		}
		
	}

	

}
