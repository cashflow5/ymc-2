package com.yougou.kaidian.commodity.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

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
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.merchant.MerchantImportInfo;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.pc.vo.commodity.ProductVO;

/**
 * 
 * @author liang.yx
 *
 */
@Controller
@RequestMapping("/commodity")
public class CommodityPublishController {

	private static final Logger log = LoggerFactory.getLogger(CommodityPublishController.class);

	@Autowired
	private CommodityComponent commodityComponent;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private CommoditySettings settings;
	
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
	private ICommodityPublish commodityPublish;

	private static final String PRESTR = "merchant.recentBrdCatg:";
	
	//判断是否按尺码修改价格
	//深圳测试4s932
	//正式线上4y644
	protected static final String isSizePrice = "4y644";

	/**
	 * 跳转到发布单品的页面
	 * 
	 * @param fromTabId
	 *            来自于哪个列表tab点击进来的
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/publishCommodity")
	public String goAddCommodity(String fromTabId, ModelMap model,
			HttpServletRequest request, String brandNo, String catStructName) {
		 
		String step = request.getParameter("step");
		if ("1".equals(step)) {
			return "forward:/commodity/preAddCommodity.sc";
		} else if ("2".equals(step)) {
			return "forward:/commodity/publishSuccess.sc";
		}

		// 商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		 
		List<String[]> recentBrdCatg = (ArrayList<String[]>) redisTemplate.opsForValue().get(PRESTR + merchantCode);
		if(recentBrdCatg != null){
			Collections.reverse(recentBrdCatg);
			model.put("recentBrdCatg", recentBrdCatg);
		}
		 
		commodityComponent.initBrand(model, request);
		 
		if (org.apache.commons.lang.StringUtils.isNotBlank(brandNo)) {
			model.put("brandNo", brandNo);
		}

		if (org.apache.commons.lang.StringUtils.isNotBlank(catStructName)) {
			model.put("catStructName", catStructName);
		}
		log.debug(" -------goAddCommodity--------- ");
		return "/manage/commodity/selBrandAndCatg";

	}

	/**
	 * 缓存本次选择的品牌和分类
	 * @param merchantCode
	 */
	@SuppressWarnings("unchecked")
	private void storeCurrentBrandAndCatg(String merchantCode, CommoditySubmitVo submitVo) {
		List<String[]> recentBrdCatg = (ArrayList<String[]>) redisTemplate.opsForValue().get(PRESTR + merchantCode);
		if (recentBrdCatg == null) {
			recentBrdCatg = new ArrayList<String[]>();
		}

		String[] arr = new String[2];
		// recentBrdCatg.put("1", new String[] {
		// "琪熙&nbsp;&gt;&nbsp;包&nbsp;&gt;&nbsp;女包&nbsp;&gt;&nbsp;手提包",
		// "8a8a8a1835ec14560135ec976d45120c,14,14-11,14-11-13" });
		arr[0] = submitVo.getBrandName() + "&nbsp;&gt;&nbsp;"
				+ submitVo.getRootCatName() + "&nbsp;&gt;&nbsp;"
				+ submitVo.getSecondCatName() + "&nbsp;&gt;&nbsp;"
				+ submitVo.getCatName();
		String[] catArr = submitVo.getCatStructName().split("-");
		arr[1] = submitVo.getBrandId() + "," + catArr[0] + "," + catArr[0] + "-" + catArr[1] + "," + submitVo.getCatStructName();
        
		Iterator<String[]> it = recentBrdCatg.iterator();
		String[] arrTmp = null;
		while(it.hasNext()){
			arrTmp = it.next();
			if(arrTmp[1].equalsIgnoreCase(arr[1])){// 有重复的，把它先删除再增加， 这样可排到最前
				it.remove();
			}
		}
		 
		if(recentBrdCatg.size() == 10){
			recentBrdCatg.remove(0);
		}
		
		recentBrdCatg.add(arr);
		redisTemplate.opsForValue().set(PRESTR + merchantCode, recentBrdCatg);
	}

	@RequestMapping("/preAddCommodity")
	public String preAddCommodity(String fromTabId, ModelMap model,
			HttpServletRequest request, CommoditySubmitVo submitVo) {
		 
		// 商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();

		storeCurrentBrandAndCatg(merchantCode, submitVo);
		
		model.put("submitVo", submitVo);

		model.put("merchantCode", merchantCode);

		// 新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon(fromTabId, model, request);

		model.put("pageSourceId", 0);
		model.put("auditStatus", "");
		model.put("tagTab", "goods");

		return "/manage/commodity/add_commodity_ymc";
	}
	
	@RequestMapping("/publishSuccess")
	public String publishSuccess(ModelMap model, HttpServletRequest request) {
//		// 商家编号
//		String merchantCode = YmcThreadLocalHolder.getMerchantCode();

		return "/manage/commodity/publishSuccess";
	}
	
	/**
	 * 添加商品
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @param multiRequest 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertCommodity")
	public String addCommodity(
			CommoditySubmitVo submitVo,
			DefaultMultipartHttpServletRequest multiRequest,
			ModelMap model, HttpServletRequest request, HttpServletResponse response,
			String salePropArr[], String ImgFileIdArr[]) throws Exception {
		
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		CommoditySubmitResultVo resultVo = new CommoditySubmitResultVo();
		//新增或修改商品信息，初始化用户信息
		CommodityUtil.initMerchantUserInfoForAddOrUpdate(request, submitVo);
		//设置商品基础配置
		submitVo.setSettings(settings);
		
		//防止瞬时提交,2秒内
		if( !checkLastCommit(resultVo, merchantCode, submitVo) ){
			resultVo.setErrorMsg("请勿频繁提交!");
			return JSONObject.fromObject(resultVo).toString();
		} 
		
		handleImageFiled(ImgFileIdArr);
		
		// 多个商品循环发布
		
		resultVo = commodityPublish.insertCommodity(merchantCode, resultVo, submitVo, request);
		
		// 如果为预览
		if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
			if(resultVo.getErrorMsg() != null){
				CommodityUtil.getAlertThenCloseScript(response, resultVo.getErrorMsg());
				return null;
			}
			request.getRequestDispatcher("/commodity/import/preview.sc").forward(request, response);
			return null;
		}
		 
		if(CollectionUtils.isEmpty(resultVo.getErrorList()) && resultVo.getErrorMsg() == null){
			resultVo.setIsSuccess(true);
		}
		return JSONObject.fromObject(resultVo).toString();
	}
	
	/**
	 * 添加商品
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @param multiRequest 
	 * @param model
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping("/inertCommodity2")
	public String addCommodity(
			CommoditySubmitVo submitVo,
			DefaultMultipartHttpServletRequest multiRequest, String salePropArr[], String ImgFileIdArr[],
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		CommoditySubmitResultVo resultVo = new CommoditySubmitResultVo();
		
		if( !checkLastCommit(resultVo, merchantCode, submitVo) ){
			resultVo.setErrorMsg("请勿频繁提交!");
			return JSONObject.fromObject(resultVo).toString();
		}
		//判断是否按尺码修改价格
		//深圳测试4s932
		//正式线上4y644
		//List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(catId,isSizePrice);
		// 颜色对应的图片信息
		Map<String, String[]> handleImageFiled = handleImageFiled(ImgFileIdArr);
		
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		//新增或修改商品信息，初始化用户信息
		CommodityUtil.initMerchantUserInfoForAddOrUpdate(request, submitVo);
		//设置商品基础配置
		submitVo.setSettings(settings);
		
		//submitVo.validateAddCommodityFormNew(errorList, new String[]{submitVo.getRootCatName(), submitVo.getSecondCatName(), submitVo.getCatName()});
		
		//外链处理
		errorList=imageService.verifyCommodityProdDesc(submitVo, errorList);
		
		if (CollectionUtils.isNotEmpty(errorList)) {
			//如果操作是预览
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
				CommodityUtil.getAlertThenCloseScript(response, errorList.get(0).getErrMsg());
				return null;
			} else {
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();				
			}
		}
		
		//构造CommodityVO
		CommodityVO commodityVO = CommodityUtil.buildCommodityVO(submitVo);
		commodityComponent.fillCommodityVo(commodityVO);
		 
		//如果为预览
		if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, commodityVO);
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
			request.getRequestDispatcher("/commodity/import/preview.sc").forward(request, response);
			return null;
		}
		 
		try {
			// 商品信息插入货品系统的返回值
			List<MerchantImportInfo> returnVoList = new ArrayList<MerchantImportInfo>();
			MerchantImportInfo merchantImportInfo = null;
			
			if("1".equals(submitVo.getPubBySize())){// 按颜色+尺码发布商品
				// 循环插入商品
				if(salePropArr != null){
					ProductVO productVO = null;
					int len = salePropArr.length;
					 
					for(int i = 0; i < len; i++){
						//货品信息
						List<ProductVO> productMsgList = new ArrayList<ProductVO>();
						//str 是逗号分隔的货品内容：颜色,尺码编码,市场价,优购价,供应商条码,库存,重量
						String[] infoArr = salePropArr[i].split(",");
						commodityVO.setSpecName(infoArr[0]);// 颜色
						productVO = new ProductVO();
						// 尺码编号
						productVO.setSizeNo(infoArr[1]);
						// 市场价
						commodityVO.setPublicPrice(Double.parseDouble(infoArr[2]));
						// 优购价
						commodityVO.setSalePrice(Double.parseDouble(infoArr[3]));
						//供应商条码
						productVO.setThirdPartyCode(infoArr[4]);
						//库存信息  
						productVO.setStock(Integer.parseInt(infoArr[5]));
						//重量
						productVO.setWeight(Double.parseDouble(infoArr[6]));
						
						productMsgList.add(productVO);
						commodityVO.setProductMsgList(productMsgList);
						
						// 提交商品 , 每个颜色每个尺码都是一个商品
						merchantImportInfo = commodityApi.insertCommodityMerchant(commodityVO);
						returnVoList.add(merchantImportInfo);
						
						// 处理图片 TODO　多个商品可能会使用同一套图片，重复切图是否有问题？
						sendImgMsg(submitVo, merchantImportInfo, handleImageFiled.get(submitVo.getSpecName()));
					}
				}
			}else{// 按颜色发布商品, 一个颜色+多个尺码是一个商品
				// 循环插入商品
				if(salePropArr != null){
					commodityVO.setSpecName(null);// 商品颜色先重置为空
					
					ProductVO productVO = null;
					//货品信息
					List<ProductVO> productMsgList = new ArrayList<ProductVO>();
					for(String str: salePropArr){
						String[] infoArr = str.split(",");
						// 颜色不一样了，先把上个颜色的商品发布
						if(commodityVO.getSpecName() !=null && !infoArr[0].equalsIgnoreCase(commodityVO.getSpecName())){
							commodityVO.setProductMsgList(productMsgList);
							//提交商品 
							returnVoList.add(commodityApi.insertCommodityMerchant(commodityVO));
							// 处理图片 TODO　多个商品可能会使用同一套图片，重复切图是否有问题？
							sendImgMsg(submitVo, merchantImportInfo, handleImageFiled.get(submitVo.getSpecName()));
							productMsgList = new ArrayList<ProductVO>();
						}
						  
						commodityVO.setSpecName(infoArr[0]);// 颜色
						
						productVO = new ProductVO();
						// 尺码编号
						productVO.setSizeNo(infoArr[1]);
						// 市场价
						commodityVO.setPublicPrice(Double.parseDouble(infoArr[2]));
						// 优购价
						commodityVO.setSalePrice(Double.parseDouble(infoArr[3]));
						//供应商条码
						productVO.setThirdPartyCode(infoArr[4]);
						//库存信息  
						productVO.setStock(Integer.parseInt(infoArr[5]));
						//重量
						productVO.setWeight(Double.parseDouble(infoArr[6]));
						
						productMsgList.add(productVO);
					}
					 
					commodityVO.setProductMsgList(productMsgList);
					//提交最后一个颜色的商品
					returnVoList.add(commodityApi.insertCommodityMerchant(commodityVO));
					// 处理图片 TODO　多个商品可能会使用同一套图片，重复切图是否有问题？
					sendImgMsg(submitVo, merchantImportInfo, handleImageFiled.get(submitVo.getSpecName()));
					
				}
			}
			 
			// TODO 处理多个向货品系统提交商品资料的结果 
			/*
			if (returnVo.getResult().toUpperCase().indexOf(RESULT_SUCCESS) == -1) {
				log.info(COMMODITY_ADD_LOG_PREFIX + "result: " + returnVo.getErrorList());
				CommodityUtil.transformCommodityError(returnVo.getErrorList(), errorList);
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();
			} else {
				//标记商品资料提交成功
				resultVo.setIsCommoditySubmitSuccess(true);
				submitVo.setCommodityNo(returnVo.getCommodityNo());
			}
			*/ 
			
			storeCatStructName(commodityVO);
			  
			//统一抛出错误异常
			if (CollectionUtils.isNotEmpty(errorList)) {
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();
			}
			
			// 库存处理， 不入优购仓 
			// FIXME 要处理部分成功，部分失败的情形 
			/*
			if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "").equals(submitVo.getIsInputYougouWarehouse())) {
				for(MerchantImportInfo returnVo : returnVoList){
					String updateStockErrorMsg = commodityComponent.updateStockForAdd(submitVo, resultVo, returnVo.getProductCodes());
					if (StringUtils.isNotBlank(updateStockErrorMsg)) {
						resultVo.setErrorMsg(updateStockErrorMsg);
					}
				}
				if(resultVo.getErrorMsg() != null){
					return JSONObject.fromObject(resultVo).toString();
				}
			}
			*/
			
			//TODO 循环校验和提交审核, 处理部分成功，部分失败的情形
			//如果是保存并提交审核
			if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.equals(submitVo.getIsSaveSubmit())) {
				for(MerchantImportInfo returnVo: returnVoList){
					submitVo.setCommodityNo(returnVo.getCommodityNo());
					//校验图片完整性
					if (!merchantCommodityService.checkCommodityPicsIntegrality(
							submitVo.getCommodityNo(), submitVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
						resultVo.setErrorMsg("图片完整性校验异常");
						return JSONObject.fromObject(resultVo).toString();
					}
					//提交审核
					if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), submitVo.getMerchantCode())) {
						resultVo.setErrorMsg("提交审核失败");
						return JSONObject.fromObject(resultVo).toString();
					}
				}
				 
				//设置为保存并提交审核
				resultVo.setIsAddCommoditySaveSubmit(true);
			}
			
			//给旗舰店插入数据
			updateFSSCommodityStoreId(submitVo);
			
		} catch (RuntimeException e) {
			log.error("添加商品运行时异常:",e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				resultVo.setErrorMsg(e.getMessage());
			} else {
				resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			}
			return JSONObject.fromObject(resultVo).toString();
		} catch (Exception e) {
			log.error("添加商品产生异常:",e);
			resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			return JSONObject.fromObject(resultVo).toString();
		}
		resultVo.setIsSuccess(true);
		return JSONObject.fromObject(resultVo).toString();
	} 
	
	/**
	 * 防止频繁操作
	 * @return
	 */
	private boolean checkLastCommit(CommoditySubmitResultVo resultVo, String merchantCode, CommoditySubmitVo submitVo) {
		String addCommodity_key="merchant.addCommodity:"+merchantCode+":"+submitVo.getStyleNo();
		// 防止瞬时提交,2秒内
		boolean flag = true;
		Object addTime = redisTemplate.opsForValue().get(addCommodity_key);
		if (addTime != null) {
			Date existTime = (Date) addTime;
			Date now = new Date();
			if ((now.getTime() - existTime.getTime()) < 2000) {
				flag = false;
			}
		} else {
			redisTemplate.opsForValue().set(addCommodity_key, new Date());
			redisTemplate.expire(addCommodity_key, 4, TimeUnit.SECONDS);
		}
		return flag;
	}
	
	/**
	 * 缓存[通过款色编码存储分类]
	 */
	private void storeCatStructName(CommodityVO commodityVO){
		try {
			//缓存[通过款色编码存储分类]
			this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, commodityVO.getMerchantCode() + "_" + commodityVO.getSupplierCode(), commodityVO.getCatStructname());
			this.redisTemplate.expire(CacheConstant.C_COMMODITY_CATEGORY_KEY, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
		} catch (Exception e) {
			log.error("设置分类缓存异常.", e);
		}	
	}
	 
	/**
	 * 更新fss_db中的tbl_commodity_style的store_id字段
	 * @param submitVo
	 */
	private void updateFSSCommodityStoreId(CommoditySubmitVo submitVo){
		try {
			fssYmcApiService.updateStoreIdWhenPublishCommodity(submitVo.getMerchantCode(), submitVo.getBrandNo(), submitVo.getCatStructName());
		} catch (Exception e) {
			log.error("给旗舰店添加商品运行时异常({},{},{}):{}",
					new Object[]{submitVo.getMerchantCode(),submitVo.getBrandNo() , submitVo.getCatStructName(),e});
		}
	}
	
	/***
	 * 
	 * @param originImgFileIdArr 多个颜色图片的信息拼成的数组，内容格式：colorName,imgField1,imgField2,imgField3......
	 * @return
	 */
	private Map<String, String[]> handleImageFiled(String[] originImgFileIdArr){
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] imageFieldArr = null;
		for(String imageField: originImgFileIdArr){
			imageFieldArr = imageField.split(",");
			map.put(imageFieldArr[0], ArrayUtils.subarray(imageFieldArr, 1, imageFieldArr.length));
		}
		return map;
	}
	 
	private void sendImgMsg(CommoditySubmitVo submitVo, MerchantImportInfo merchantImportInfo, String[] imageFieldArr){
		submitVo.setCommodityNo(merchantImportInfo.getCommodityNo());
		submitVo.setImgFileId(imageFieldArr);
		commodityService.sendJmsForMaster(submitVo, 0);
	}
}
