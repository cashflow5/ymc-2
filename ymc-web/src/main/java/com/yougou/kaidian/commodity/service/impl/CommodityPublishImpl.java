package com.yougou.kaidian.commodity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitResultVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.commodity.service.ICommodityPublish;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.merchant.MerchantImportInfo;
import com.yougou.pc.vo.commodity.CommodityVO;

@Service
public class CommodityPublishImpl implements ICommodityPublish{
	
	@Resource
	private IImageService imageService;
	
	@Autowired
	private IFSSYmcApiService fssYmcApiService;
	
	@Resource
	private ICommodityMerchantApiService commodityApi;

	@Autowired
	private ICommodityService commodityService;
	
	@Autowired
	private IMerchantCommodityService merchantCommodityService;
	
	@Autowired
	private CommodityComponent commodityComponent;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	final static String COMMODITY_ADD_LOG_PREFIX = "addCommodity#-> ";
	

	private static final String RESULT_SUCCESS = "SUCCESS";
	
	private static final Logger log = LoggerFactory.getLogger(CommodityPublishImpl.class);

	@Override
	public CommoditySubmitResultVo insertCommodity(String merchantCode, 
			CommoditySubmitResultVo resultVo , CommoditySubmitVo submitVo,
			HttpServletRequest request) throws Exception {
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		  
		//submitVo.validateAddCommodityFormNew(errorList, new String[]{submitVo.getRootCatName(), submitVo.getSecondCatName(), submitVo.getCatName()});
		//外链处理
		errorList=imageService.verifyCommodityProdDesc(submitVo,errorList);
		if (CollectionUtils.isNotEmpty(errorList)) {
			//如果操作是预览
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
//				CommodityUtil.getAlertThenCloseScript(response, errorList.get(0).getErrMsg());
				resultVo.setErrorMsg(errorList.get(0).getErrMsg());
			} else {
				resultVo.setErrorList(errorList);
			}
			return resultVo;		
		}
		
		//构造CommodityVO
		CommodityVO commodityVO = CommodityUtil.buildCommodityVO(submitVo);
		commodityComponent.fillCommodityVo(commodityVO);
		
		//如果为预览
		if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, commodityVO);
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
			return null;
		}
		
		try {			
			//提交商品 
			MerchantImportInfo returnVo = commodityApi.insertCommodityMerchant(commodityVO);
			if (returnVo.getResult().toUpperCase().indexOf(RESULT_SUCCESS) == -1) {
				log.info("{} result: {}", COMMODITY_ADD_LOG_PREFIX, JSONArray.fromObject(returnVo.getErrorList()).toString());
				CommodityUtil.transformCommodityError(returnVo.getErrorList(), errorList);
				resultVo.setErrorList(errorList);
				return resultVo;
			} else {
				//标记商品资料提交成功
				resultVo.setIsCommoditySubmitSuccess(true);
				submitVo.setCommodityNo(returnVo.getCommodityNo());
			}
			try {
				//缓存[通过款色编码存储分类]
				this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, commodityVO.getMerchantCode() + "_" + commodityVO.getSupplierCode(), commodityVO.getCatStructname());
				this.redisTemplate.expire(CacheConstant.C_COMMODITY_CATEGORY_KEY, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
			} catch (Exception e) {
				log.error("设置分类缓存异常.", e);
			}
			
			//发送JMS消息通知Image处理图片
			commodityService.sendJmsForMaster(submitVo, 0);
			
			//统一抛出错误异常
			if (CollectionUtils.isNotEmpty(errorList)) {
				resultVo.setErrorList(errorList);
				return resultVo;
			}
			
			//库存
			if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "")
					.equals(submitVo.getIsInputYougouWarehouse())) {
				String updateStockErrorMsg = commodityComponent.updateStockForAdd(submitVo, resultVo, returnVo.getProductCodes());
				if (StringUtils.isNotBlank(updateStockErrorMsg)) {
					resultVo.setErrorMsg(updateStockErrorMsg);
					return resultVo;
				}
			}
			
			//如果是保存并提交审核
			if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE
					.equals(submitVo.getIsSaveSubmit())) {
				//校验图片完整性
				if (!merchantCommodityService.checkCommodityPicsIntegrality(
						submitVo.getCommodityNo(), submitVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
					resultVo.setErrorMsg("图片完整性校验异常");
					return resultVo;
				}
				
				//提交审核
				if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), submitVo.getMerchantCode())) {
					resultVo.setErrorMsg("提交审核失败");
					return resultVo;
				}
				//设置为保存并提交审核
				resultVo.setIsAddCommoditySaveSubmit(true);
			}
			
			//给旗舰店插入数据
			try {
				fssYmcApiService.updateStoreIdWhenPublishCommodity(submitVo.getMerchantCode(),commodityVO.getBrandNo(),commodityVO.getCatStructname());
			} catch (Exception e) {
				log.error("给旗舰店添加商品运行时异常({})",returnVo.getCommodityNo(),e);
			}
		} catch (RuntimeException e) {
			log.error("添加商品运行时异常:",e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				resultVo.setErrorMsg(e.getMessage());
			} else {
				resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			}
			return resultVo;
		} catch (Exception e) {
			log.error("添加商品产生异常:",e);
			resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			return resultVo;
		}
		resultVo.setIsSuccess(true);
		return resultVo;
	}
	

}
