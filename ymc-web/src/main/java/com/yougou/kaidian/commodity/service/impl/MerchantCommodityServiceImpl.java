package com.yougou.kaidian.commodity.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yougou.fss.api.IFSSWebApiService;
import com.yougou.fss.api.vo.FSSResult;
import com.yougou.kaidian.commodity.beans.BeanPropertyEqualsPredicate;
import com.yougou.kaidian.commodity.beans.BeanPropertyMatchesPredicate;
import com.yougou.kaidian.commodity.beans.CommodityPicEditor;
import com.yougou.kaidian.commodity.beans.ResizeDefinition;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.dao.MerchantCommodityMapper;
import com.yougou.kaidian.commodity.model.pojo.MerchantCommodityPics;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.picture.MerchantPictureInfo;
import com.yougou.pc.model.picture.MerchantPictureInfoDto;
import com.yougou.pc.model.picture.Picture;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;

/**
 * 
 * @author yang.mq
 *
 */
@Service
public class MerchantCommodityServiceImpl implements IMerchantCommodityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantCommodityServiceImpl.class);

	@Resource
	private MerchantCommodityMapper merchantCommodityPicsMapper;
	
	@Resource
	private ICommodityMerchantApiService commodityMerchantApiService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private CommoditySettings commoditySettings;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
//	@Resource
//	private MessageChannel toRemoveChannel;
	
	@Resource
	private ICommodityMerchantApiService commodityApi;

//	@Resource
//	private ICommodityClientApiService commodityClientApi;
	
	@Resource
	private IFSSWebApiService  fSSWebApiService ;

	private static final int LATEXT_IMAGE_COUNT_5 = 5;
	private static final int LATEXT_IMAGE_COUNT_7 = 7;
	private static final int LATEXT_IMAGE_COUNT_5_ALL = 28;// 角度图最少5张，总张数最少5*5+3=28;
	private static final int LATEXT_IMAGE_COUNT_7_ALL = 38;// 角度图最少7张，总张数最少7*5+3=38;
	private static final String IMG_TYPE_B = "b";// 描述b图
	private static final String IMG_TYPE_BT = "bt";// 描述bt图

	@Override
	@Transactional
	public boolean insertMerchantCommodityPic(MerchantCommodityPics merchantCommodityPics) {
		return NumberUtils.INTEGER_ONE.equals(merchantCommodityPicsMapper.insertMerchantCommodityPics(merchantCommodityPics));
	}

	@Override
	@Transactional
	public boolean deleteMerchantCommodityPicsWithinImage(String... ids) throws Exception {
		for (String id : ids) {
			// 获取图片信息
			MerchantCommodityPics merchantCommodityPics = (MerchantCommodityPics) ObjectUtils.defaultIfNull(getPermanentMerchantCommodityPics(id), getTemporaryMerchantCommodityPics(id));
			if (merchantCommodityPics.getOuterPicId() != null) {
				throw new IllegalStateException("禁止删除商品图片.");
			}
			FSSResult result=fSSWebApiService.deleteImageFlag(id, null);
			if("0".equals(result.getCode())){
				// 删除图片信息
				merchantCommodityPicsMapper.deleteMerchantCommodityPics(merchantCommodityPics.getId());
			}else{
				return false;
			}
			
			// 删除FTP图片
/*			String picPath=StringUtils.substringAfter(merchantCommodityPics.getInnerPicPath(), "/");
			final Message<String> messageJPG = MessageBuilder.withPayload(merchantCommodityPics.getInnerPicName())
					.setHeader("file_remoteDirectory", picPath).setHeader("file_remoteFile", new String(merchantCommodityPics.getInnerPicName().getBytes("UTF-8"),"ISO-8859-1")).build();
			try {
				boolean resultJPG = toRemoveChannel.send(messageJPG);
			} catch (Exception e) {
				if (e instanceof MessagingException&&((MessagingException)e).getMessage().indexOf("java.io.IOException")>=0){
					LOGGER.warn("ftp jpg file is not exist", e);
				}else{
					throw e;
				}
			}
			
			if(merchantCommodityPics.getThumbnaiPicName()!=null){
				final Message<String> messagePNG = MessageBuilder.withPayload(merchantCommodityPics.getThumbnaiPicName())
						.setHeader("file_remoteDirectory", picPath).setHeader("file_remoteFile", new String(merchantCommodityPics.getThumbnaiPicName().getBytes("UTF-8"),"ISO-8859-1")).build();
				try {
					boolean resultPNG = toRemoveChannel.send(messagePNG);
				} catch (Exception e) {
					LOGGER.warn("ftp png imgfile is not exist", e);
				}
			}*/
		}
		return true;
	}
	
	@Override
	@Transactional
	public boolean deleteMerchantCommodityPicsAndCommodityPics(Long outPicId,String innerPicName,String commodityNo,String merchantCode) throws Exception {
			
			MerchantCommodityPics merchantCommodityPics = merchantCommodityPicsMapper.getPermanentMerchantCommodityPicsByName(innerPicName, merchantCode);
			if (merchantCommodityPics == null) {
				throw new IllegalStateException("商品图片信息为空。");
			}
			// 删除商家商品图片信息
			merchantCommodityPicsMapper.deleteMerchantCommodityPics(merchantCommodityPics.getId());
			//删除商品图片
			List<Picture> picList = commodityBaseApiService.getPicturesByCommodityNo(commodityNo, (short)1);
			Long picId = Long.valueOf(merchantCommodityPics.getOuterPicId().toString());
			Picture pic = (Picture)CollectionUtils.find(picList,  new BeanPropertyEqualsPredicate("id",picId));
			List<Picture> delpicList = new ArrayList<Picture>();
			delpicList.add(pic);
			commodityMerchantApiService.delCommodityPictures(delpicList);
			
		return true;
	}
	
	

	@Override
	@Transactional
	public boolean deleteMerchantCommodityPicsWithoutImage(String... ids) throws Exception {
		if (ArrayUtils.isEmpty(ids)) {
			return false;
		} else {
			merchantCommodityPicsMapper.deleteMerchantCommodityPics(ids);
			return true;
		}
	}

	@Override
	@Transactional
	public boolean deleteMerchantCommodityAllPics(String supplierCode, String merchantCode) throws Exception {
		merchantCommodityPicsMapper.deleteMerchantCommodityAllPics(supplierCode, merchantCode);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteMerchantCommodityPicsByOuterPicId(Long... outerPicIds) throws Exception {
		if (ArrayUtils.isEmpty(outerPicIds)) {
			return false;
		} else {
			merchantCommodityPicsMapper.deleteMerchantCommodityPicsByOuterPicId(outerPicIds);
			return true;
		}
	}

	@Override
	public MerchantCommodityPics getPermanentMerchantCommodityPics(String id) {
		return merchantCommodityPicsMapper.getPermanentMerchantCommodityPicsById(id);
	}

	@Override
	public MerchantCommodityPics getPermanentMerchantCommodityPics(String innerPicName, String merchantCode) {
		return merchantCommodityPicsMapper.getPermanentMerchantCommodityPicsByName(innerPicName, merchantCode);
	}

	@Override
	public MerchantCommodityPics getTemporaryMerchantCommodityPics(String id) {
		return merchantCommodityPicsMapper.getTemporaryMerchantCommodityPicsById(id);
	}

	@Override
	public MerchantCommodityPics getTemporaryMerchantCommodityPics(String innerPicName, String merchantCode) {
		return merchantCommodityPicsMapper.getTemporaryMerchantCommodityPicsByName(innerPicName, merchantCode);
	}

	@Override
	public PageFinder<MerchantPictureInfo> queryMerchantCommodityPics(MerchantCommodityPics merchantCommodityPics, Date start, Date end, Query query) {
		String startDateStr= null, endDateStr = null;
		if (start != null) {
			startDateStr = DateFormatUtils.ISO_DATE_FORMAT.format(start) + " 00:00:00";
		}
		if (end != null) {
			endDateStr = DateFormatUtils.ISO_DATE_FORMAT.format(end) + " 23:59:59";
		}
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("merchantCode", merchantCommodityPics.getMerchantCode());
		params.put("startUpdateTime", startDateStr);
		params.put("endUpdateTime", endDateStr);
		params.put("likePicName", merchantCommodityPics.getInnerPicName());
		List<String> picType=new ArrayList<String>();
		picType.add("l");
		picType.add("b");
		params.put("picTypeList", picType);
		
		MerchantPictureInfoDto  merpic=commodityMerchantApiService.getMerchantPictureInfoList(params, query.getPage(), query.getPageSize());
		Integer count=merpic.getCount();
		PageFinder<MerchantPictureInfo> pageFinder = new PageFinder<MerchantPictureInfo>(query.getPage(), query.getPageSize(), count);
		if (count != null && count.intValue() > 0) {
			List<MerchantPictureInfo> data=merpic.getMerchantPictureInfoList();
			pageFinder.setData(data);
		}
		return pageFinder;
	}

	@Override
	public PageFinder<MerchantCommodityPics> queryMerchantTemporaryPics(MerchantCommodityPics merchantPics, Date start, Date end, String orderBy, Query query) {
		String startDateStr= null, endDateStr = null;
		if (start != null) {
			startDateStr = DateFormatUtils.ISO_DATE_FORMAT.format(start) + " 00:00:00";
		}
		if (end != null) {
			endDateStr = DateFormatUtils.ISO_DATE_FORMAT.format(end) + " 23:59:59";
		}
		Integer count = merchantCommodityPicsMapper.queryMerchantTemporaryPicscount(merchantPics.getMerchantCode(), merchantPics.getInnerPicName(), startDateStr, endDateStr);
		PageFinder<MerchantCommodityPics> pageFinder = new PageFinder<MerchantCommodityPics>(query.getPage(), query.getPageSize(), count);
		if (count != null && count.intValue() > 0) {
			RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
			List<MerchantCommodityPics> data = merchantCommodityPicsMapper.queryMerchantTemporaryPics(merchantPics.getMerchantCode(), merchantPics.getInnerPicName(), startDateStr, endDateStr, orderBy, rowBounds);
			pageFinder.setData(data);
		}
		return pageFinder;
	}
	
	@Override
	public List<MerchantCommodityPics> queryMerchantCommodityPicsBySupplierCode(String supplierCode, String merchantCode) throws Exception {
		return merchantCommodityPicsMapper.queryMerchantCommodityPicsBySupplierCode(supplierCode, merchantCode);
	}
	
	/**
	 * 统一处理缓存中未处理的商品描述字符串
	 * 
	 * @param merchantCode
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void commodityDescRedisOpt(String merchantCode) throws Exception {
		Set<String> commodityNos = (Set<String>) redisTemplate.opsForHash().get(CacheConstant.C_COMMODITY_PIC_DESC_KEY, merchantCode);
		if (CollectionUtils.isEmpty(commodityNos)) 
			return;
		
		LOGGER.info("merchantCode:{} | bach processing commodity description start.", merchantCode);
		for (String commodityNo : commodityNos) {
			try {
				this.updateCommodityDescriptionUseUploadedPics(commodityNo, merchantCode);
			} catch (Exception e) {
				LOGGER.error("更新商品:{}描述异常" ,commodityNo, e);
			}
			LOGGER.info("merchantCode:{} | processing commodity description for commodityNo:{}", new Object[]{merchantCode, commodityNo});
		}
		LOGGER.info("merchantCode:{} | bach processing commodity description end.", merchantCode);
		// 处理完后，清理缓存
		redisTemplate.opsForHash().delete(CacheConstant.C_COMMODITY_PIC_DESC_KEY, merchantCode);
	}
	
	@Override
	public Integer getMerchantStorageType(String merchantCode) throws Exception {
		return merchantCommodityPicsMapper.getMerchantStorageType(merchantCode);
	}
	
	/**
	 * 获取最少需要突破数量
	 * @param commodityNo
	 * @param pictureList
	 * @return
	 * @throws Exception
	 */
	private int getCommodityImageLeastNumbersByCommodityNo(String commodityNo,List<Picture> pictureList) throws Exception {
		Commodity commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
		
		String[] catNames = this.getCommodityCatNamesByCommodityNo(commodity.getMerchantCode(), commodity.getSupplierCode(),commodity.getBrandNo()); 
		
		// 商品放大镜图片最少张数
		int magnifierImageLeastNumbers = CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,catNames);
		
		// 商品图片最少张数
		int numbers = ResizeDefinition.SCHEDULED_RESIZE.getResizeEntrySet().size()
				+ ResizeDefinition.SCHEDULED_RESIZE.getDynamicResizeEntrySet().size()
				+ CommoditySettings.COMMODITY_DESCRIPTION_IMAGE_LEAST_NUMBERS
				- ((CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS - magnifierImageLeastNumbers) * CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DERIVE_LEAST_NUMBERS);
		
		LOGGER.info("Statistics {} commodity pics least numbers is {}" , Arrays.toString(catNames), numbers);
		
		// 如用户上传的放大镜图片张数超出许可的最低张数
		int existingImageNumbers = pictureList.size();
		if (existingImageNumbers > numbers) {
			// 是否已上传宝贝放大镜图片
			int magnifierImageRealNumbers = CollectionUtils.countMatches(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_L, false));
			if (magnifierImageRealNumbers < magnifierImageLeastNumbers) {
				LOGGER.info("Commodity '{}' image number '{}' greater than image least numbers '{}', but magnifier image numbers less than '{}' then set least number to Integer.MAX_VALUE",
						new Object[]{commodityNo,existingImageNumbers,numbers,magnifierImageLeastNumbers});
				throw new RuntimeException(MessageFormat.format("宝贝放大镜图片数量{0},少于最少需要数量{1}",new Object[]{magnifierImageRealNumbers,magnifierImageLeastNumbers}));
			}
			// 是否已上传宝贝描述图片
			boolean existingDescriptionImage = CollectionUtils.exists(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_B, false));
			if (!existingDescriptionImage) {
				LOGGER.info("Commodity '{}' image number '{}' greater than image least numbers '{}', but no description image then set least number to Integer.MAX_VALUE",
						new Object[]{commodityNo,existingImageNumbers,numbers });
				numbers = Integer.MAX_VALUE-1;
				throw new RuntimeException("没有上传描述图");
			}
		}
		
		return numbers;
	}
	
	@Override
	public int getCommodityImageLeastNumbersByCommodityNo(String commodityNo) throws Exception {
		List<Picture> pictureList = commodityBaseApiService.getPicturesByCommodityNo(commodityNo, (short)1);
		Commodity commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
		
		String[] catNames = this.getCommodityCatNamesByCommodityNo(commodity.getMerchantCode(), commodity.getSupplierCode(),commodity.getBrandNo()); 
		
		// 商品放大镜图片最少张数
		int magnifierImageLeastNumbers = CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,catNames);
		
		// 商品图片最少张数
		int numbers = ResizeDefinition.SCHEDULED_RESIZE.getResizeEntrySet().size()
				+ ResizeDefinition.SCHEDULED_RESIZE.getDynamicResizeEntrySet().size()
				+ CommoditySettings.COMMODITY_DESCRIPTION_IMAGE_LEAST_NUMBERS
				- ((CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS - magnifierImageLeastNumbers) * CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DERIVE_LEAST_NUMBERS);
		
		LOGGER.info("Statistics {} commodity pics least numbers is {}" , Arrays.toString(catNames) , numbers);
		
		// 如用户上传的放大镜图片张数超出许可的最低张数
		int existingImageNumbers = pictureList.size();
		if (existingImageNumbers > numbers) {
			// 是否已上传宝贝放大镜图片
			int magnifierImageRealNumbers = CollectionUtils.countMatches(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_L, false));
			if (magnifierImageRealNumbers < magnifierImageLeastNumbers) {
				LOGGER.info("Commodity '{}' image number '{}' greater than image least numbers '{}', but magnifier image numbers less than '{}' then set least number to Integer.MAX_VALUE",
						new Object[]{commodityNo,existingImageNumbers,numbers, magnifierImageLeastNumbers });
				numbers = Integer.MAX_VALUE;
			}
			// 是否已上传宝贝描述图片
			boolean existingDescriptionImage = CollectionUtils.exists(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_B, false));
			if (!existingDescriptionImage) {
				LOGGER.info("Commodity '{}' image number '{}' greater than image least numbers '{}', but no description image then set least number to Integer.MAX_VALUE",
						new Object[]{commodityNo, existingImageNumbers,numbers  });
				numbers = Integer.MAX_VALUE;
			}
		}
		
		return numbers;
	}

	public int getCommodityImageUploadStatus(Commodity commodity)
			throws Exception {
		List<Picture> pictureList = commodity.getPictures();
		// pictureList.get(index)
		if (pictureList == null || pictureList.size() == 0) {
			return 0; // 未上传
		}
		// 判断角度图张数以及顺序是否是按照顺序上传
		int latestUploadPicNumbers = this.getLatestPicNumbers(commodity.getMerchantCode(),commodity.getSupplierCode(),commodity.getBrandNo());

		// 角度图
		List<Picture> l_pic_list = new ArrayList<Picture>();
		// 描述图
		List<Picture> b_pic_list = new ArrayList<Picture>();
		// 过滤掉b图和bt图
		// 角度图的总数
		Map<String, Integer> lpicMap = new HashMap<String, Integer>();
		for (Picture p : pictureList) {
			if (IMG_TYPE_B.equals(p.getPicType())
					|| IMG_TYPE_BT.equals(p.getPicType())) {// 描述图
				b_pic_list.add(p);
			} else {
				l_pic_list.add(p); // 角度图
				String picName = p.getPicName();
				int lastIndex_ = picName.lastIndexOf("_");
				if (lastIndex_ > 2) {
					String count = picName
							.substring(lastIndex_ - 2, lastIndex_);
					if (lpicMap.get(count) == null) {
						lpicMap.put(count, 1);
					} else {
						lpicMap.put(count, lpicMap.get(count) + 1);
					}
				}
			}
		}
		if (b_pic_list.size() == 0 || b_pic_list.size() == 0) {
			return 1; // 部分上传
		}
		if (latestUploadPicNumbers == LATEXT_IMAGE_COUNT_5) {// 最少5张角度图
			if (l_pic_list.size() < LATEXT_IMAGE_COUNT_5_ALL) {
				return 1;// 部分上传
			}
			for (int i = 1; i <= LATEXT_IMAGE_COUNT_5; i++) {
				String count = "0" + i;
				if (lpicMap.get(count) == null) {
					return 1;
				}
				if (i == 1 && lpicMap.get(count) < 8) {// 第一个必须是8张
					return 1;
				}
				if (lpicMap.get(count) < LATEXT_IMAGE_COUNT_5) {
					return 1;
				}
			}
		}
		
		if (latestUploadPicNumbers == LATEXT_IMAGE_COUNT_7) {// 最少7张角度图
			if (l_pic_list.size() < LATEXT_IMAGE_COUNT_7_ALL) {
				return 1;// 部分上传
			}
		}
		// 判断描述图
		if (b_pic_list == null || b_pic_list.size() == 0) {
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public int getCommodityImageLeastNumbersByCatNames(String[] catNames) throws Exception {
		int numbers = ResizeDefinition.SCHEDULED_RESIZE.getResizeEntrySet().size()
				+ ResizeDefinition.SCHEDULED_RESIZE.getDynamicResizeEntrySet().size()
				+ CommoditySettings.COMMODITY_DESCRIPTION_IMAGE_LEAST_NUMBERS
				- ((CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS - CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,catNames)) * CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DERIVE_LEAST_NUMBERS);
		LOGGER.info("Statistics {} commodity pics least numbers is {} " ,Arrays.toString(catNames), numbers);
		return numbers;
	}

	@Override
	public boolean checkCommodityPicsIntegrality(String commodityNo, String supplierCode, String merchantCode,String brandNo) throws Exception {
		int numbers = 0;
		int leastNumbers = CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,this.getCommodityCatNamesByCommodityNo(merchantCode, supplierCode,brandNo));
		//校验图片缓存
		Image4SingleCommodityMessage message = null;
		try {
			message = (Image4SingleCommodityMessage) this.redisTemplate.opsForHash().get(CacheConstant.C_IMAGE_MASTER_JMS_KEY, commodityNo);
			if (null != message) {	//图片未处理完，校验图片Id即可
				CommodityImage[] imgFileIds = message.getCommodityImages();
				for (CommodityImage imgFileId : imgFileIds) {
					if ("0".equals(imgFileId.getPicUrl()) || CommodityPicEditor._XX_L.matcher(imgFileId.getPicUrl()).find()) {
						numbers++;
					}
				}
				//图片未处理前只对角度图做校验
				if (numbers < leastNumbers) {
					throw new RuntimeException("需上传" + leastNumbers + "张商品图片，已上传" + numbers + "张。");
				}
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Picture> pictureList = new ArrayList<Picture>();
		try{			
			List<Picture> pictures = commodityBaseApiService.getPicturesByCommodityNo(commodityNo, (short)1);
			if(CollectionUtils.isNotEmpty(pictures)){
				pictureList.addAll(pictures);
			}
		}catch(Exception e){
			LOGGER.error("商家编码:{} 商品编号:{}  异常原因 ", new Object[]{merchantCode,commodityNo,e});
			throw e;
		}
		for (Picture picture : pictureList) {
			if (CommodityPicEditor.XX_L.matcher(picture.getPicName()).find()) {
				numbers++;
			}
		}
		
		if (numbers < leastNumbers) {
			throw new RuntimeException("需上传" + leastNumbers + "张商品图片，已上传" + numbers + "张。");
		}
		int needNumber = this.getCommodityImageLeastNumbersByCommodityNo(commodityNo, pictureList);
		if (pictureList.size() < needNumber) {
			LOGGER.warn("商家编码:{} 商品编号:{}  现有图片数:{} 需要的图片数:{}", new Object[]{merchantCode,commodityNo,pictureList.size(),needNumber});
			throw new RuntimeException(MessageFormat.format("商品图片少于最少需要图片数量。现有图片数:{} 需要的图片数:{}", new Object[]{pictureList.size(),needNumber}));
		}
		String returnValue = commodityMerchantApiService.updateCommodityPicFlag(commodityNo, merchantCode, pictureList.get(0).getPicPath());
		LOGGER.warn("Update commodity pic flag {} with '{}', server return data {}",
				new Object[]{commodityNo,merchantCode,returnValue});
		if (!"success".equalsIgnoreCase(returnValue)) {
			throw new RuntimeException("商品图片确认失败：" + returnValue);
		}
		
		return true;
	}


	@Override
	public boolean updateCommodityDescriptionUseUploadedPics(String commodityNo, String merchantCode) throws Exception {
		List<Picture> pictureList = commodityBaseApiService.getPicturesByCommodityNo(commodityNo, (short)1);
		
		// 过滤商品备注图片
		List<Picture> pictures = new ArrayList<Picture>();
		CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_B, false), pictures);
		// 按商品图片名称排序
		Collections.sort(pictures, new Comparator<Picture>() {
			public int compare(Picture o1, Picture o2) {
				return o1.getPicName().compareToIgnoreCase(o2.getPicName());
			}
		});
		// 拼接商品备注
		StringBuilder commodityDescBuilder = new StringBuilder();
		commodityDescBuilder.append("<p align=\"center\" generator=\"MERCHANTS\">");
		for (Picture picture : pictures) {
			commodityDescBuilder.append("<img src=\"").append(commoditySettings.getCommodityPreviewDomain()).append(picture.getPicPath()).append(picture.getPicName()).append("\" border=\"0\"/>");
		}
		commodityDescBuilder.append("</p>");
		String commodityDesc = commodityDescBuilder.toString();
		
		LOGGER.info("Generator commodity desc [{}] for '{}'",commodityDesc,commodityNo);
		
		String returnValue = commodityMerchantApiService.updateCommodityDescForMerchant(commodityNo, merchantCode, commodityDesc);
		if (!"SUCCESS".equalsIgnoreCase(returnValue)) {
			throw new IllegalStateException("更新商品宝贝描述失败：" + returnValue);
		}
		return true;
	}
	
	@Override
	public String[] getCommodityCatNamesByCommodityNo(String merchantCode, String supplierCode,String brandNo) throws Exception {
		String catStructName = this.getCommodityCategoryRetry(merchantCode, supplierCode,brandNo);
		if(StringUtils.isNotBlank(catStructName)){
			return this.getCommodityCatNamesByCatStructName(catStructName);
		}else{
			return new String[]{};
		}
	}
	
	@Override
	public String[] getCommodityCatNamesByCatStructName(String catStructName) throws Exception {
		List<Category> categories = commodityMerchantApiService.getCategoryTreeByStruct(catStructName);
		LOGGER.info("Get commodity cat names '{}', server return data {}",catStructName , categories);
		String[] catNames = new String[3];
		catNames[0] = ((Category) CollectionUtils.find(categories, new BeanPropertyEqualsPredicate("catLeave", 1))).getCatName();
		catNames[1] = ((Category) CollectionUtils.find(categories, new BeanPropertyEqualsPredicate("catLeave", 2))).getCatName();
		catNames[2] = ((Category) CollectionUtils.find(categories, new BeanPropertyEqualsPredicate("catLeave", 3))).getCatName();
		return catNames;
	}
	
	@Override
	public String[] getCommodityCatNamesByCatID(String catID) throws Exception {
		List<Category> categories = new ArrayList<Category>();
		Category category=null;
		do{
			category=commodityBaseApiService.getCategoryById(catID);
			if(category!=null){
				catID=category.getParentId();
				categories.add(category);
			}
		}while(category!=null&&!"0".equals(category.getParentId()));
		String[] catNames = new String[3];
		catNames[0] = ((Category) CollectionUtils.find(categories, new BeanPropertyEqualsPredicate("catLeave", 1))).getCatName();
		catNames[1] = ((Category) CollectionUtils.find(categories, new BeanPropertyEqualsPredicate("catLeave", 2))).getCatName();
		catNames[2] = ((Category) CollectionUtils.find(categories, new BeanPropertyEqualsPredicate("catLeave", 3))).getCatName();
		return catNames;
	}

	
	/**
	 * 获取商品分类、 如果未获取到则调用接口重试
	 * 
	 * @param merchantCode 商家编码
	 * @param supplierCode 款色编码
	 * @return Object
	 */
	private String getCommodityCategoryRetry(String merchantCode, String supplierCode,String brandNo) {
		String catStructName = null;
		Commodity commodity = null;
		//先从redis中获取
		catStructName = (String) redisTemplate.opsForHash().get(CacheConstant.C_COMMODITY_CATEGORY_KEY, merchantCode + "_" + supplierCode);
		if (null == catStructName) {
			LOGGER.warn("从redis通过merchantCode:{},supplierCode:{}未获取到分类缓存. ", new Object[]{merchantCode, supplierCode});
			String commodityNo = commodityMerchantApiService.getNoBySupplierCode(supplierCode, merchantCode,brandNo);
			if(StringUtils.isNotEmpty(commodityNo)){
				commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
			}else{
				LOGGER.warn("commodityMerchantApiService.getNoBySupplierCode接口未查到数据，supplierCode={},merchantCode={},brandNo={}",
						new Object[]{supplierCode,merchantCode,brandNo});
			}
			catStructName = (null == commodity) ? null : commodity.getCatStructName();
			this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, merchantCode + "_" + supplierCode, catStructName);
		}
		return catStructName;
	}
	
	/**
	 * 获取最少上传角度图张数
	 * 
	 * @param merchantCode
	 * @param supplierCode
	 * @return
	 * @throws Exception
	 */
	public int getLatestPicNumbers(String merchantCode, String supplierCode,String brandNo)
			throws Exception {
		int leastNumbers = CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,this
				.getCommodityCatNamesByCommodityNo(merchantCode, supplierCode,brandNo));
		return leastNumbers;
	}

	/**
	 * 获取分类属性，过滤 所在区域 名厂直销品牌 名厂直销分类 货品来源
	 */

	@Override
	public List<PropItem> getPropMsgByCatIdNew(String catId, boolean showSize) {
	
		List<PropItem> propItems =  commodityApi.getPropMsgByCatId(catId);
		if(CollectionUtils.isEmpty(propItems)){
			return Collections.emptyList();
		}
		if (showSize) {
			List<PropValue> sizeList = commodityApi.getSizeByCatId(catId);
			PropItem propItem = new PropItem();
			propItem.setIsShowMall(0);
			propItem.setPropItemNo(CommodityConstant.SIZE);
			propItem.setPropItemName(CommodityConstant.SIZENAME);
			propItem.setPropValues(sizeList);
			propItems.add(propItem);
		}
		Iterator<PropItem> iterator = propItems.iterator();
		PropItem item = null;
		String itemName = null;
		while (iterator.hasNext()) {
			item = iterator.next();
			itemName = item.getPropItemName();
			if (CommodityConstant.ITEMNAME1.equals(itemName) || 
				CommodityConstant.ITEMNAME2.equals(itemName) ||
				CommodityConstant.ITEMNAME3.equals(itemName) ||
				CommodityConstant.ITEMNAME4.equals(itemName) ) {
				iterator.remove();
			}
		}
		// 根据必填属性排序
		Collections.sort(propItems, new ComparatorPropItem());

		return propItems;
	}

	class ComparatorPropItem implements Comparator<PropItem> {
		public int compare(PropItem item1, PropItem item2) {
			if (item1.getIsShowMall() == null || item2.getIsShowMall() == null) {
				item1.setIsShowMall(1);
				item2.setIsShowMall(1);
			}
			int flag = item1.getIsShowMall().compareTo(item2.getIsShowMall());
			return flag;
		}
	}
}
