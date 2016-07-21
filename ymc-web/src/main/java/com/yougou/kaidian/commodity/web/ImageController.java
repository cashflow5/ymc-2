package com.yougou.kaidian.commodity.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.PictureUtil;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;

/**
 *
 * @author lm
 */
@Controller
@RequestMapping("/img")
public class ImageController {
    
	private static final Logger log = LoggerFactory.getLogger(ImageController.class);
	
	@Resource
	private CommoditySettings commoditySettings;
	@Resource
	private IImageService imageService;
	
	private static final int minWidth = 800,minWidth2 = 740;
	private static final int maxWidth = 1000, maxWidth2 = 790;
	
    @RequestMapping(value = "/upload")
    public String getPic(HttpServletRequest request, HttpServletResponse response){
    	return null;
    }
    
    /**
     * 上传图片，返回json
     * @param file
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 获取商家编号
		String merchantCode = request.getParameter("merchantCode");
		if(merchantCode==null||"".equals(merchantCode)){
			merchantCode = YmcThreadLocalHolder.getMerchantCode();
		}
		JSONObject jsonObject = new JSONObject();

		String number = request.getParameter("no");
		if (StringUtils.isBlank(number)) {
			jsonObject.put("success", false);
			jsonObject.put("message", "1");//request请求参数no为空值,请检查!
			return jsonObject.toString();
		}

		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		if (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			try {
				String error = CommodityUtil
						.validateImage(
								mpf.getInputStream(),
								CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE * 1024,
								minWidth, maxWidth, minWidth, maxWidth);
				if (StringUtils.isNotBlank(error)) {
					jsonObject.put("success", false);
					jsonObject.put("message", error);
					return jsonObject.toString();
				}
			} catch (IOException e1) {
				log.error("角度图:{}校验异常", mpf.getOriginalFilename(), e1);
				jsonObject.put("success", false);
				jsonObject.put("message", "5");//图片校验异常
				return jsonObject.toString();
			}
			
			String newFilenameBase = UUID.randomUUID().toString() + "_0" + number + "_l";
			log.debug("mpf {} uploaded {}  file size is : {}.", new Object[] { mpf.getOriginalFilename(), newFilenameBase, mpf.getSize() });
			String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf(".")).toLowerCase();
			// 兼容peg格式
			if (".jpeg".equalsIgnoreCase(originalFileExtension)) originalFileExtension = ".jpg";
			
			String newFilename = newFilenameBase + originalFileExtension;
			String ftpServerPath = MessageFormat.format(commoditySettings.imageFtpPreTempSpace, merchantCode);
			try {
				String newPngFilename=newFilename.replaceAll("\\.jpg$", ".png");
				boolean result = imageService.ftpUpload(mpf.getInputStream(), newFilename, ftpServerPath);
				boolean result2 = imageService.ftpUpload(PictureUtil.createThumbnailFile(mpf.getInputStream(),PictureUtil.createTemporaryPicname(commoditySettings.picDir, newPngFilename, merchantCode)), newPngFilename, ftpServerPath);
				if (result&&result2) {
					jsonObject.put("success", true);
					jsonObject.put("message", commoditySettings.getCommodityPreviewDomain()+ftpServerPath+newFilename);
					return jsonObject.toString();
				} else {
					jsonObject.put("success", false);
					jsonObject.put("message", "6");//上传图片失败, 请重新再试！
					return jsonObject.toString();
				}
			} catch (Exception e) {
				log.error("upload picture:{} to {} failed", new Object[] { newFilename, ftpServerPath , e});
				jsonObject.put("success", false);
				jsonObject.put("message", "6");//上传图片失败, 请重新再试！
				return jsonObject.toString();
			}
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "7");//上传图片失败,获取不到图像对象，请重试!
			return jsonObject.toString();
		}
	}
    
    /**
     * 获取图片
     * @param file
     * @return
     */
    @RequestMapping(value = "/picture/{fileName}", method = RequestMethod.GET)
    public void picture(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) {
        // 获取商家编号
		String merchant_code = YmcThreadLocalHolder.getMerchantCode();
        File imageFile = new File(this.getAbsoluteFilepath(merchant_code, fileName, ".jpg"));
        String mimetype = getMimeType(imageFile);
        response.setContentType(mimetype);
        response.setContentLength((int)imageFile.length());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            log.error("Could not show picture "+fileName, e);
        }
    }
    
    /**
     * 获取图片缩略图
     * @param file
     * @return
     */
    @RequestMapping(value = "/thumbnail/{fileName}", method = RequestMethod.GET)
    public void thumbnail(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) {
        // 获取商家编号
		String merchant_code = YmcThreadLocalHolder.getMerchantCode();
    	
    	// String absoluteFilepath = PictureUtil.createTemporaryFilename(fileName + ".png", merchant_code);
    	String absoluteFilepath = this.getAbsoluteFilepath(merchant_code, fileName, ".png");
    	File imageFile = new File(absoluteFilepath);
        String mimetype = getMimeType(imageFile);
        response.setContentType(mimetype);
        response.setContentLength((int)imageFile.length());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
			log.error("Could not show thumbnail:{}{}", new Object[]{fileName, ".png", e});
        }
    }
    
    /**
     * 删除图片
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{fileName}", method = RequestMethod.DELETE)
    public String delete(HttpServletRequest request, @PathVariable String fileName) {
        // 获取商家编号
		String merchant_code = YmcThreadLocalHolder.getMerchantCode();
    	// String absoluteFilepath = PictureUtil.createTemporaryFilename(fileName + ".jpg", merchant_code);
    	String absoluteFilepath = this.getAbsoluteFilepath(merchant_code, fileName, ".jpg");
    	// String absoluteThumbnailpath = PictureUtil.createTemporaryFilename(fileName + ".png", merchant_code);
    	String absoluteThumbnailpath = this.getAbsoluteFilepath(merchant_code, fileName, ".png");
    	
    	File imageFile = new File(absoluteFilepath);
        imageFile.delete();
        File thumbnailFile = new File(absoluteThumbnailpath);
        thumbnailFile.delete();
        return new JSONObject().toString();
    }
    
	@RequestMapping(value = "/uploaddesc")
	public String uploadDesc(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	
    /**
     * 批量通过jquery-upload上传商品描述图
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/uploaddesc", method = RequestMethod.POST)
	public String uploadDesc(MultipartHttpServletRequest request, HttpServletResponse response, String commodityNo) throws Exception{
    	final int fileNameLength  =64;
    	// 获取商家编号
		String merchantCode = request.getParameter("merchantCode");
		if (merchantCode == null || "".equals(merchantCode)) {
			merchantCode = YmcThreadLocalHolder.getMerchantCode();
		}
		JSONObject jsonObject = new JSONObject();

		String number = request.getParameter("no");
		if (StringUtils.isBlank(number)) {
			jsonObject.put("success", false);
			jsonObject.put("message", "1");// request请求参数no为空值,请检查!
			return jsonObject.toString();
		}

		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		if (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			try {
				String picName = mpf.getOriginalFilename();
				if(picName.length()>fileNameLength){
					jsonObject.put("success", false);
					jsonObject.put("message", "8");// 上传图片失败, 请重新再试！
					return jsonObject.toString();
				}
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
							commoditySettings.getCommodityPreviewDomain()
									+ ftpServerPath + newFilename);
					String newPngFilename = newFilename.replaceAll("\\.jpg$",
							".png");
					imageService.ftpUpload(PictureUtil.createThumbnailFile(mpf
							.getInputStream(), PictureUtil
							.createTemporaryPicname(commoditySettings.picDir,
									newPngFilename, merchantCode)),
							newPngFilename, ftpServerPath);
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
    
    /**
     * 获取图片类型
     * @param file
     * @return
     */
    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
    
    /**
     * 获取文件在临时文件夹的绝对路径
     * 
     * @param merchantCode
     * @param fileName
     * @param suffix
     * @return
     */
	private String getAbsoluteFilepath(String merchantCode, String fileName, String suffix) {
		return new StringBuffer(commoditySettings.picDir).append(File.separator).append(merchantCode).append(File.separator).append(fileName)
				.append(suffix).toString();
	}
	
	
	/**
     * 多图片一次上传--赔付管理的凭证上传  
     * @param file
     * @return success,message
     */
	@ResponseBody
	@RequestMapping(value = "/uploadForCompensate", method = RequestMethod.POST)
	public String uploadForCompensate(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 获取目录规范
		String filePath = DateUtil2.initPicPathForCompensate();
		JSONObject jsonObject = new JSONObject();
		StringBuffer message = null;
		boolean success = false;
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		if (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			String ftpServerPath = "";
			String newFileName = "";
			try {
				newFileName = System.currentTimeMillis()+".jpg";
				log.debug("uploadForCompensate: mpf {} uploaded {}  file size is : {}.", new Object[] { mpf.getOriginalFilename(), newFileName, mpf.getSize() });
				ftpServerPath = MessageFormat.format(commoditySettings.compensateImageFtpSpace, filePath);
				boolean result = imageService.ftpUpload(mpf.getInputStream(), newFileName, ftpServerPath);
				if ( result ) {
					success = true ;
					message = new StringBuffer();
					message.append( commoditySettings.compensatePreviewDomain+ftpServerPath+newFileName );
				} else {
					jsonObject.put("success", false);
					jsonObject.put("message", "6");//上传图片失败, 请重新再试！
					return jsonObject.toString();
				}
			} catch (Exception e) {
				log.error("uploadForCompensate: upload picture:{} to {} failed", new Object[] { newFileName, ftpServerPath , e});
				jsonObject.put("success", false);
				jsonObject.put("message", "6");//上传图片失败, 请重新再试！
				return jsonObject.toString();
			}
			jsonObject.put("success", success);
			jsonObject.put("message", message.toString() );
			return jsonObject.toString();
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "7");//上传图片失败,获取不到图像对象，请重试!
			return jsonObject.toString();
		}
	}
	
}
