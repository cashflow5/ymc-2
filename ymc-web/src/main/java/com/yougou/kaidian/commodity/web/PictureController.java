package com.yougou.kaidian.commodity.web;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.fss.api.vo.FSSResult;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.common.util.PictureUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.pic.service.IPictureService;
import com.yougou.merchant.api.pic.service.vo.MerchantPicture;
import com.yougou.merchant.api.pic.service.vo.MerchantPictureCatalog;
import com.yougou.merchant.api.pic.service.vo.MerchantPictureVO;

/**
 *
 * @author li.m1
 */
@Controller
@RequestMapping("/picture")
public class PictureController {
    
	private static final Logger log = LoggerFactory.getLogger(PictureController.class);
	
	@Resource
	private IPictureService pictureService;
	@Resource
	private IFSSYmcApiService fssYmcApiService;
	@Resource
	private CommoditySettings settings;
	@Resource
	private IImageService imageService;
	
	@Resource
	private CommoditySettings commoditySettings;
	
	private final static String IMG_SUFFIX = ".jpg";
	private final static String IMG_JPG_SUFFIX = "(?im)\\.jpg$";
	
	private static final int minWidth2 = 740;
	private static final int maxWidth2 = 790;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/**
	 * 图片列表
	 * @param merchantPictureVO
	 * @param query
	 * @param modelMap
	 * @param treelevel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public ModelAndView listCommodityPicTiles(MerchantPictureVO merchantPictureVO, Query query, ModelMap modelMap, Integer treelevel,HttpServletRequest request) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		merchantPictureVO.setMerchantCode(merchantCode);
		String timeMark=request.getParameter("timeMark");
		if(StringUtil.isBlank(merchantPictureVO.getCatalogId())){
			 merchantPictureVO.setCatalogId(null);
			 
		}
		Date nowDate=new Date();
		if(StringUtils.isEmpty(timeMark)){
			timeMark="2";
		}
		
		if("1".equals(timeMark)){//当天
			merchantPictureVO.setCreatedStart(nowDate);
			merchantPictureVO.setCreatedEnd(nowDate);
		}else if("2".equals(timeMark)){//1个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);//当前时间的1个月前
			merchantPictureVO.setCreatedStart(cal.getTime());
			merchantPictureVO.setCreatedEnd(nowDate);
		}else if("3".equals(timeMark)){//3个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -3);//当前时间的三个月前
			merchantPictureVO.setCreatedStart(cal.getTime());
			merchantPictureVO.setCreatedEnd(nowDate);
		}else{
			timeMark="-1";
		}
		
		//查询图片目录
		List<MerchantPictureCatalog> treeModes = null;
		treeModes = pictureService.queryCommodityPicCatalogList(merchantCode);
		MerchantPictureCatalog rootCommodityCatalog=new MerchantPictureCatalog();
		rootCommodityCatalog.setId("0");
		rootCommodityCatalog.setParentId("-1");
		rootCommodityCatalog.setCatalogName("商品图片");
		rootCommodityCatalog.setMerchantCode(merchantCode);
		treeModes.add(rootCommodityCatalog);
			
			//查询旗舰店相关的图片目录
		try{
			FSSResult fssResult=fssYmcApiService.getMerchantShops(merchantCode);
			if(fssResult!=null&&fssResult.getData()!=null){
				List<ShopVO> shoplist=(List<ShopVO>)fssResult.getData();
				if(shoplist.size()>0){
					for(ShopVO vo:shoplist){
						List<MerchantPictureCatalog> treeModesFss=pictureService.queryShopPicCatalogList(merchantCode,vo.getShopId());
						MerchantPictureCatalog rootFssCatalog=new MerchantPictureCatalog();
						rootFssCatalog.setId(vo.getShopId());
						rootFssCatalog.setCatalogName("店铺装修("+vo.getShopName()+")");
						rootFssCatalog.setMerchantCode(merchantCode);
						rootFssCatalog.setParentId("-1");
						rootFssCatalog.setShopId(vo.getShopId());
						treeModesFss.add(rootFssCatalog);
						
						treeModes.addAll(treeModesFss);
					}
				}
			}
		}catch(Exception e){
			log.error("访问图片空间，调用旗舰店getMerchantShops接口异常！",e);
		}
		if(merchantPictureVO.getShopId()!=null&&"".equals(merchantPictureVO.getShopId())){
			merchantPictureVO.setShopId(null);
		}
		modelMap.addAttribute("treeModes", treeModes);
		//查询图片
		PageFinder<MerchantPicture> pageFinder = pictureService.getPicListByPage(merchantPictureVO, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("commodityPreviewDomain", settings.getCommodityPreviewDomain());
		modelMap.addAttribute("random", new Random().nextInt(100));
		modelMap.addAttribute("timeMark", timeMark);
		if(StringUtils.isNotBlank( merchantPictureVO.getCatalogId())){
		    modelMap.addAttribute("catalogId", merchantPictureVO.getCatalogId());
		}else{
			  modelMap.addAttribute("catalogId", "");
		}
		return new ModelAndView("/manage/pics/pics_list", modelMap);
	}
	
/**
 * 加载图片目录
 * @param request
 * @return
 */
	@ResponseBody
	@RequestMapping(value = "/loadPicCatalog")
	public String loadPicCatalog(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			List<MerchantPictureCatalog> treeModes=pictureService.queryCommodityPicCatalogList(merchantCode);
			MerchantPictureCatalog rootCommodityCatalog=new MerchantPictureCatalog();
			rootCommodityCatalog.setId("0");
			rootCommodityCatalog.setParentId("-1");
			rootCommodityCatalog.setCatalogName("商品图片");
			rootCommodityCatalog.setMerchantCode(merchantCode);
			treeModes.add(rootCommodityCatalog);
			jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
			jsonObject.put("treeModes", treeModes);
			return jsonObject.toString();
		} catch (Exception e) {
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			return jsonObject.toString();
		}
		
	}
	
	/**
	 * 加载图片
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadPiclist")
	public ModelAndView listCommodityPicTilesNew(MerchantPictureVO merchantPictureVO, Query query, ModelMap modelMap,String timeMark, Integer treelevel,HttpServletRequest request) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		merchantPictureVO.setMerchantCode(merchantCode);
		
		Date nowDate=new Date();
		if(StringUtils.isEmpty(timeMark)){
			timeMark="2";
		}
		
		if("1".equals(timeMark)){//当天
			merchantPictureVO.setCreatedStart(nowDate);
			merchantPictureVO.setCreatedEnd(nowDate);
		}else if("2".equals(timeMark)){//1个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);//当前时间的1个月前
			merchantPictureVO.setCreatedStart(cal.getTime());
			merchantPictureVO.setCreatedEnd(nowDate);
		}else if("3".equals(timeMark)){//3个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -3);//当前时间的三个月前
			merchantPictureVO.setCreatedStart(cal.getTime());
			merchantPictureVO.setCreatedEnd(nowDate);
		}else{
			timeMark="-1";
		}
		
		if(merchantPictureVO.getShopId()!=null&&"".equals(merchantPictureVO.getShopId())){
			merchantPictureVO.setShopId(null);
		}
		/*		String catalogId=merchantPictureVO.getCatalogId();
		if(treelevel!=null&&treelevel==0){
			if("0".equals(merchantPictureVO.getCatalogId())){
				merchantPictureVO.setPicType(10);
			}
			merchantPictureVO.setCatalogId(null);
		}*/
		//查询图片
		PageFinder<MerchantPicture> pageFinder = pictureService.getPicListByPage(merchantPictureVO, query);
		//merchantPictureVO.setCatalogId(catalogId);
		
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("commodityPreviewDomain", settings.getCommodityPreviewDomain());
		modelMap.addAttribute("random", new Random().nextInt(100));
		modelMap.addAttribute("timeMark", timeMark);
		return new ModelAndView("/manage_unless/commodity/add_update_commodity_pics_list", modelMap);
	}
	
    /**
     * 添加目录
     * @param request
     * @param id
     * @param catalogName
     * @param parentId
     * @param shopId
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/savePicCatalog", method = RequestMethod.POST)
    public String savePicCatalog(HttpServletRequest request,String id,String catalogName,String parentId,String shopId){
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	
    	if(id==null||"".equals(id)){id=UUIDGenerator.getUUID();};
    	MerchantPictureCatalog pictureCatalog=new MerchantPictureCatalog();
    	pictureCatalog.setId(id);
    	pictureCatalog.setCatalogName(catalogName);
    	pictureCatalog.setMerchantCode(merchantCode);
    	pictureCatalog.setParentId(parentId);
    	if(shopId!=null&&!"".equals(shopId.trim())){
        	pictureCatalog.setShopId(shopId);
    	}else{
        	pictureCatalog.setShopId(null);
    	}
    	
    	boolean isScuess=false;
    	try {
			pictureService.insertPicCatalog(pictureCatalog);
			isScuess=true;
		} catch (Exception e) {
			log.error("商家{}添加图片空间文件夹失败",new Object[]{merchantCode,e});
		}
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", isScuess);
		return jsonObject.toString();
    }
    
    /**
     * 修改目录
     * @param request
     * @param id
     * @param catalogName
     * @param type
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/updatePicCatalog", method = RequestMethod.POST)
    public String updatePicCatalog(HttpServletRequest request,String id,String catalogName,String type){
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	
    	boolean isScuess=false;
    	try {
        	MerchantPictureCatalog pictureCatalog=pictureService.getPicCatalog(id);
        	if(pictureCatalog==null){
        		log.error("商家{}更新图片空间文件夹失败,系统不存在该目录!",new Object[]{merchantCode});
        	}else{
            	pictureCatalog.setCatalogName(catalogName);
            	
    			pictureService.updatePicCatalog(pictureCatalog);
    			isScuess=true;
        	}
		} catch (Exception e) {
			log.error("商家{}更新图片空间文件夹失败",new Object[]{merchantCode,e});
		}
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", isScuess);
		return jsonObject.toString();
    }
    
    /**
     * 删除目录
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/delPicCatalog", method = RequestMethod.POST)
    public String delPicCatalog(HttpServletRequest request,String id){
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	boolean isScuess=false;
    	try {
    		pictureService.delPicCatalog(id);
			isScuess=true;
		} catch (Exception e) {
			log.error("商家{}删除图片空间文件夹失败",new Object[]{merchantCode,e});
		}
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", isScuess);
		return jsonObject.toString();
    }
    
    /**
     * 删除目录
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/movePics", method = RequestMethod.POST)
    public String movePics(HttpServletRequest request,String picId,String catalogId){
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	boolean isScuess=false;
    	try {
    		if(StringUtils.isEmpty(catalogId)){
    			catalogId = "0";
    		}
    		pictureService.movePics(StringUtils.split(picId, ","), catalogId);
			isScuess=true;
		} catch (Exception e) {
			log.error("商家{}移动图片失败",new Object[]{merchantCode,e});
		}
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", isScuess);
		return jsonObject.toString();
    }
    
	/**
	 * 加载描述图片上传控件
	 * 
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/upload/ready")
	public ModelAndView readyUploadCommodityDescribePic(ModelMap modelMap, HttpServletRequest request,Integer catalogtype,String catalogId) throws Exception{
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//查询图片目录
		List<MerchantPictureCatalog> treeModes=pictureService.queryCommodityPicCatalogList(merchantCode);
		MerchantPictureCatalog rootCommodityCatalog=new MerchantPictureCatalog();
		rootCommodityCatalog.setId("0");
		rootCommodityCatalog.setParentId("-1");
		rootCommodityCatalog.setCatalogName("商品图片");
		rootCommodityCatalog.setMerchantCode(merchantCode);
		treeModes.add(rootCommodityCatalog);
		
		//查询店铺目录
		try{
			if(catalogtype==null||catalogtype==2){
				FSSResult fssResult=fssYmcApiService.getMerchantShops(merchantCode);
				if(fssResult!=null&&fssResult.getData()!=null){
					List<ShopVO> shoplist=(List<ShopVO>)fssResult.getData();
					if(shoplist.size()>0){
						for(ShopVO vo:shoplist){
							List<MerchantPictureCatalog> treeModesFss=pictureService.queryShopPicCatalogList(merchantCode,vo.getShopId());
							MerchantPictureCatalog rootFssCatalog=new MerchantPictureCatalog();
							rootFssCatalog.setId(vo.getShopId());
							rootFssCatalog.setCatalogName("店铺装修("+vo.getShopName()+")");
							rootFssCatalog.setMerchantCode(merchantCode);
							rootFssCatalog.setParentId("-1");
							rootFssCatalog.setShopId(vo.getShopId());
							treeModesFss.add(rootFssCatalog);
							
							treeModes.addAll(treeModesFss);
						}
					}
				}
			}
		}catch(Exception e){
			log.error("图片空间调用旗舰店接口查询失败！",e);
		}
		modelMap.addAttribute("treeModes", treeModes);
		modelMap.addAttribute("jsessionId", request.getSession().getId());
		modelMap.addAttribute("catalogId",StringUtils.isEmpty(catalogId)?"0":catalogId);
		return new ModelAndView("/manage_unless/pics/pics_swfupload", modelMap);
	}
	
	/**
	 * 上传商品图片
	 * 
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploaddescribe")
	public String startUploadCommodityDescribePic(DefaultMultipartHttpServletRequest request, HttpServletResponse response) {
		try {
			// 商品图片
			MultipartFile multipartFile = request.getFile("Filedata");
			// 商家编码
			String merchantCode = request.getParameter("merchantCode");
			String catalogId = request.getParameter("catalogId");
			String shopId = request.getParameter("shopId");
			
			boolean result = imageService.uploadCommodityDescPic2TemporarySpace(multipartFile, merchantCode,catalogId,shopId);
			//boolean result=true;
			return result ? "success" : "failure";
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return StringUtils.defaultIfEmpty(ex.getMessage(), "null");
		}
	}
	
	 	@ResponseBody
		@RequestMapping(value = "/imageManageUpload")
		public String uploadDesc(MultipartHttpServletRequest request, HttpServletResponse response, String catalogId) throws Exception{
	 		final int fileNameLength  =64;
	 		// 获取商家编号
			String merchantCode = request.getParameter("merchantCode");
			if (merchantCode == null || "".equals(merchantCode)) {
				merchantCode = YmcThreadLocalHolder.getMerchantCode();
			}
			JSONObject jsonObject = new JSONObject();
			String number = "0";
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf;
			if (itr.hasNext()) {
				mpf = request.getFile(itr.next());
				try {
					String error = CommodityUtil
							.validateDescImage(
									mpf.getInputStream(),
									CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE_DESC * 1024,
									minWidth2, maxWidth2);
					if (StringUtils.isNotBlank(error)) {
						jsonObject.put("success", false);
						jsonObject.put("message", error);
						return jsonObject.toString();
					}
				} catch (IOException e1) {
					log.error("描述图:{}校验异常：{}",
									mpf.getOriginalFilename(), e1);
					jsonObject.put("success", false);
					jsonObject.put("message", "5");// 图片校验异常
					return jsonObject.toString();
				}

				String newFilenameBase = UUID.randomUUID().toString() + "_0"
						+ number + "_l";
				log.debug("mpf {} uploaded {}  file size is : {}.",
						new Object[]{mpf.getOriginalFilename(), newFilenameBase,
								mpf.getSize()});
				String originalFileExtension = mpf.getOriginalFilename()
						.substring(mpf.getOriginalFilename().lastIndexOf("."))
						.toLowerCase();
				// 兼容jpeg格式
				if (".jpeg".equalsIgnoreCase(originalFileExtension))
					originalFileExtension = ".jpg";

				String newFilename = newFilenameBase + originalFileExtension;
				String ftpServerPath = MessageFormat.format(
	commoditySettings.getImageFtpTemporarySpace(),
								merchantCode);
				try {
					boolean result = imageService.ftpUpload(mpf.getInputStream(),
							newFilename, ftpServerPath);
					if (result) {
						jsonObject.put("success", true);
						jsonObject.put("message",
								settings.getCommodityPreviewDomain()
										+ ftpServerPath + newFilename);
						String newPngFilename = newFilename.replaceAll("\\.jpg$",
								".png");
						File thumbnai = PictureUtil.createThumbnailFile(mpf
								.getInputStream(), PictureUtil
								.createTemporaryPicname(commoditySettings.picDir,
										newPngFilename, merchantCode));
						imageService.ftpUpload(thumbnai,
								newPngFilename, ftpServerPath);
						
						
						// 处理图片文件名
						String picName = mpf.getOriginalFilename();
						picName = StringUtils.strip(picName);// 去除特殊字符
						picName = StringUtils.deleteWhitespace(picName);// 去除空格
						if(picName.length()>fileNameLength){
							jsonObject.put("success", false);
							jsonObject.put("message", "8");// 上传图片失败, 请重新再试！
							return jsonObject.toString();
						}
						// 假如为空name,指定一个文件名UUID去-
						String alias_name=UUIDGenerator.getUUID()+".jpg";
						picName = IMG_SUFFIX.equalsIgnoreCase(picName) ? alias_name : picName;
						picName = picName.replaceAll(IMG_JPG_SUFFIX, IMG_SUFFIX);
						String temporaryfilename = PictureUtil.createTemporaryPicname(commoditySettings.picDir, alias_name, merchantCode);
						File tempFile = new File(temporaryfilename);
						
						// 创建父级目录
						File parentFile = tempFile.getParentFile();
						if (!parentFile.exists()) parentFile.mkdirs();
						
						mpf.transferTo(tempFile);
						String innerFilePath=MessageFormat.format(commoditySettings.imageFtpTemporarySpace,merchantCode);
						MerchantPicture pic=new MerchantPicture();
						Image img =  ImageIO.read(tempFile); 
						pic.setId(UUIDGenerator.getUUID());
						pic.setCatalogId(catalogId==null||"".equals(catalogId)?null:catalogId);
						pic.setHeight(img.getHeight(null));
						pic.setWidth(img.getWidth(null));
						pic.setMerchantCode(merchantCode);
						pic.setPicPath(innerFilePath);
						pic.setPicSize(tempFile.length());
						pic.setPicType(10);
						pic.setShopId(null);
						pic.setSourcePicName(picName);
						pic.setPicName(newFilename);
						pic.setThumbnaiPicName(newPngFilename);
						// 增加图片表数据
						pictureService.insertPic(pic);
						// 删除临时文件
						tempFile.delete();
						return jsonObject.toString();
					} else {
						jsonObject.put("success", false);
						jsonObject.put("message", "6");// 上传图片失败, 请重新再试！
						return jsonObject.toString();
					}


				} catch (Exception e) {
					log.error("upload picture:{} to {} failed：{}", new Object[]{
									newFilename, ftpServerPath, e});
					jsonObject.put("success", false);
					jsonObject.put("message", "6");// 上传图片失败, 请重新再试！
					return jsonObject.toString();
				}
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "7");// 上传图片失败,获取不到图像对象，请重试!
				return jsonObject.toString();
			}
		}
}
