package com.yougou.kaidian.commodity.web;

import java.io.InputStream;
import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.common.constant.SystemConstant;
import com.yougou.kaidian.common.util.PictureUtil;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.commodityinfo.Commodity;

@Controller
@RequestMapping("/commodity")
public class CommodityImageUploadBatchController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommodityImageUploadBatchController.class);
	
	@Resource
	private ICommodityMerchantApiService commodityMerchantApiService;
	
	@Resource
	private CommoditySettings settings;
	
	@Resource
	private IImageService imageService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private ICommodityService commodityService;
	/**
	 * 加载图片上传控件
	 * 
	 * @param replacement 被替换图片名称
	 * @param editFilename 是否验证图片名称
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/pics/upload/ready")
	public ModelAndView readyUploadCommodityPic(String replacement, String editFilename, ModelMap modelMap, HttpServletRequest request) {
		modelMap.addAttribute("replacement", replacement);
		modelMap.addAttribute("editFilename", editFilename);
		modelMap.addAttribute("tagTab", "goods");
		modelMap.addAttribute("jsessionId", request.getSession().getId());
		return new ModelAndView("/manage_unless/commodity/commodity_pics_swfupload", modelMap);
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
	@RequestMapping("/pics/upload")
	public String startUploadCommodityPic(DefaultMultipartHttpServletRequest request, HttpServletResponse response) {
		CommoditySubmitVo submitVo = new CommoditySubmitVo();
		InputStream is = null;
		try {
			// 商品图片
			MultipartFile multipartFile = request.getFile("Filedata");
			
			// 商家编码
			String merchantCode = request.getParameter("merchantCode");
			
			//处理图片文件名
			String[] nameArray = PictureUtil.splitImageName(multipartFile.getOriginalFilename());
			if (!"jpg".equals(nameArray[3])) { return "文件后缀名必须为.jpg"; }
			if (ArrayUtils.isEmpty(nameArray) || nameArray.length != 4) { return "文件名称不符合规范"; }
			
			String supplierCode = nameArray[0];
			String commodityNo = commodityMerchantApiService.getNoBySupplierCode(supplierCode, merchantCode);
			if (StringUtils.isBlank(commodityNo)) { return MessageFormat.format("款色编码:{0}未建立商品资料", supplierCode); }
			
			submitVo.setMerchantCode(merchantCode);
			submitVo.setCommodityNo(commodityNo);
			
			//校验图片
			String imgType = nameArray[2];
			String validateResult = CommodityUtil.validateImage(multipartFile.getInputStream(), imgType);
			if (StringUtils.isNotBlank(validateResult)) {
				return validateResult;
			}
			
			String newFilename = new StringBuffer().append(commodityNo).append("_").append(nameArray[1]).append("_").append(imgType).append(".jpg").toString(); 
			//上传图片到临时目录
			String ftpServerPath = MessageFormat.format(settings.imageFtpPreTempSpace, merchantCode);
			boolean uploadResult = imageService.ftpUpload(multipartFile.getInputStream(), newFilename, ftpServerPath);
			if (!uploadResult) {
				return "上传图片失败!";
			}
			
			Commodity commodity = commodityBaseApiService.getCommodityByNo(commodityNo);
			if (null == commodity) { return "获取商品相关信息异常"; }
			
			submitVo.setBrandNo(commodity.getBrandNo());
			submitVo.setYears(commodity.getYears());
			submitVo.setProdDesc(newFilename);
			submitVo.setStructName(commodity.getCatStructName());
			String picUrl=settings.getCommodityPreviewDomain()+ftpServerPath+"/"+newFilename;
			//发送消息
			commodityService.sendJmsForBatch(submitVo, SystemConstant.IMG_TYPE_L.equals(imgType) ? SystemConstant._BATCH_L : SystemConstant._BATCH_B,picUrl);
			
			//merchantCommodityService.uploadMerchantCommodityPics(picName, is, merchantCode, replacement, editFilename, resizeDefinition);
			return "success";
		} catch (Exception ex) {
			LOGGER.error("上传商品图片报错信息", ex);
			return StringUtils.defaultIfEmpty(ex.getMessage(), "null");
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
