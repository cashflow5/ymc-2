package com.yougou.kaidian.commodity.service.impl;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.commodity.model.vo.MessageBean;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.common.util.HttpUtil;
import com.yougou.kaidian.common.util.PictureUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.merchant.api.pic.service.IPictureService;
import com.yougou.merchant.api.pic.service.vo.MerchantPicture;

@Service
public class ImageServiceImpl implements IImageService {

	private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
	@Resource
	private MessageChannel ftpChannel;

	@Resource
	private CommoditySettings commoditySettings;
	private final static String IMG_SUFFIX = ".jpg";
	private final static String IMG_JPG_SUFFIX = "(?im)\\.jpg$";
	@Resource
	private IPictureService pictureService;
	@Resource
	private IFSSYmcApiService fssYmcApiService;
	private final static Pattern yougou_pattern_invalid_img = Pattern.compile("(?im)(http://10\\.0\\.30\\.193/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://i1\\.ygimg\\.cn/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://i2\\.ygimg\\.cn/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://img\\.yougou\\.com/{1,}\\S*\\.jpg\\?{0,1}\\w*)");

	/**
	 * 校验图片
	 * @param imgfile
	 * @return
	 */
	public MessageBean checkImage(File imageFile,int imgtype,MessageBean message){
        if (!imageFile.exists()) {
        	message.setSucess(false);
        	message.setMessage("上传文件不存在");
            return message; 
        } 
        Image img = null; 
        try { 
            img = ImageIO.read(imageFile); 
            int width=img.getWidth(null);
            int height=img.getHeight(null);
            //可以判断是不是图片
            if (img != null && width > 0 && height > 0) { 
                if(imgtype==1){
                	//l角度图校验,长宽800~1000，大小小于1m
                	if((width>=800&&width<=1000)&&(height>=800&&height<=1000)&&imageFile.length()<=512000){
                		message.setSucess(true);
                		return message; 
                	}
                	message.setSucess(false);
                	message.setMessage("角度图大小和尺寸不符,长宽800-1000,文件大小小于500k，请检查");
                }else if(imgtype==2){
                	//b描述图校验,长740
					if (width >= 740 && width <= 790) {
                		message.setSucess(true);
                		return message; 
               	    }
		        	message.setSucess(false);
		        	message.setMessage("描述图宽度740-790，请检查");
                }
            } 
            return message; 
        } catch (Exception e) { 
        	message.setSucess(false);
        	message.setMessage("后台图片校验产生异常！");
        	log.error("图片校验异常", e);
            return message; 
        }
	}

	/**
	 * 图片上传封装类
	 * @param uploadFileMap
	 * @param ftpServerPath
	 * @return
	 */
	public boolean ftpUpload(File imgfile, String ftpServerPath) {
		Message<File> message = MessageBuilder.withPayload(imgfile)
				.setHeader("remote_dir", this.toTransformStr(ftpServerPath))
				.setHeader("remote_filename", imgfile.getName()).build();
		return ftpChannel.send(message);
	}
	
	public boolean ftpUpload(File imgfile, String ftpfilename, String ftpServerPath) {
		Message<File> message = MessageBuilder.withPayload(imgfile)
				.setHeader("remote_dir", this.toTransformStr(ftpServerPath))
				.setHeader("remote_filename", ftpfilename).build();
		return ftpChannel.send(message);
	}

	@Override
	public boolean ftpUpload(InputStream is, String ftpfilename, String ftpServerPath) throws Exception {
		Message<byte[]> message;
		try {
			message = MessageBuilder
					.withPayload(IOUtils.toByteArray(is))
					.setHeader("remote_dir", this.toTransformStr(ftpServerPath))
					.setHeader("remote_filename", ftpfilename).build();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return ftpChannel.send(message);
	}
	
	private String toTransformStr(String ftpServerPath) {
		if (StringUtils.isNotBlank(ftpServerPath)
				&& ftpServerPath.startsWith("/")) {
			return StringUtils.substringAfter(ftpServerPath, "/");
		}
		
		return ftpServerPath;
	}
	
	@Override
	@Transactional
	public boolean uploadCommodityDescPic2TemporarySpace(MultipartFile multipartFile, String merchantcode,String catalogId,String shopId) throws Exception {
		// 上传图片
		Map<String, File> uploadFileMap = new HashMap<String, File>();
		
		// 处理图片文件名
		String picName = multipartFile.getOriginalFilename();
		picName = StringUtils.strip(picName);// 去除特殊字符
		picName = StringUtils.deleteWhitespace(picName);// 去除空格
		// 假如为空name,指定一个文件名UUID去-
		String alias_name=UUIDGenerator.getUUID()+".jpg";
		picName = IMG_SUFFIX.equalsIgnoreCase(picName) ? alias_name : picName;
		picName = picName.replaceAll(IMG_JPG_SUFFIX, IMG_SUFFIX);
		String temporaryfilename = PictureUtil.createTemporaryPicname(commoditySettings.picDir, alias_name, merchantcode);
		File tempFile = new File(temporaryfilename);
		
		// 创建父级目录
		File parentFile = tempFile.getParentFile();
		if (!parentFile.exists()) parentFile.mkdirs();
		
		multipartFile.transferTo(tempFile);
		
		// 加入上传缩略图
		File thumbnai = PictureUtil.createThumbnailFile(temporaryfilename);
		uploadFileMap.put(tempFile.getName(), tempFile);
		uploadFileMap.put(thumbnai.getName(), thumbnai);
		
		// 生成内部临时路径
		String innerFilePath = "";
		if(shopId==null||"".equals(shopId)){
			innerFilePath=MessageFormat.format(commoditySettings.imageFtpTemporarySpace, merchantcode);
		}else{
			innerFilePath=fssYmcApiService.generateFSSImgPath(shopId);
		}
		
		// 上传失败的文件
		Map<String, File> failFile = this.ftpUpload(uploadFileMap, innerFilePath);
		if (MapUtils.isNotEmpty(failFile)) {
			log.error("mechantcode:{} upload temporary space fail : {}.", new Object[]{merchantcode, failFile.keySet()});
			return false;
		}

		MerchantPicture pic=new MerchantPicture();
		Image img =  ImageIO.read(tempFile); 
		pic.setId(UUIDGenerator.getUUID());
		pic.setCatalogId(catalogId==null||"".equals(catalogId)?null:catalogId);
		pic.setHeight(img.getHeight(null));
		pic.setWidth(img.getWidth(null));
		pic.setMerchantCode(merchantcode);
		pic.setPicPath(innerFilePath);
		pic.setPicSize(tempFile.length());
		pic.setPicType(shopId==null||"".equals(shopId)?10:20);
		pic.setShopId(shopId);
		pic.setSourcePicName(picName);
		pic.setPicName(tempFile.getName());
		pic.setThumbnaiPicName(thumbnai.getName());
		// 增加图片表数据
		pictureService.insertPic(pic);
		// 删除临时文件
		tempFile.delete();
		return true;
	}
	
	/**
	 * 图片上传封装类
	 * @param uploadFileMap
	 * @param ftpServerPath
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private Map<String,File> ftpUpload(Map<String,File> uploadFileMap,String ftpServerPath) throws UnsupportedEncodingException{
		if (StringUtils.isNotBlank(ftpServerPath) && ftpServerPath.startsWith("/")) 
			ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
		Message<File> message=null;
		Map<String,File> failFileMap=new HashMap<String,File>();
		boolean result=true;
		for(String uploadFileStr:uploadFileMap.keySet()){
			message = MessageBuilder.withPayload(uploadFileMap.get(uploadFileStr)).setHeader("remote_dir", ftpServerPath).setHeader("remote_filename", new String(uploadFileStr.getBytes("UTF-8"),"ISO-8859-1")).build();
			result=ftpChannel.send(message);
			if(!result){
				failFileMap.put(uploadFileStr, uploadFileMap.get(uploadFileStr));
			}
		}
		return failFileMap;
	}

	@Override
	public List<ErrorVo> verifyCommodityProdDesc(CommoditySubmitVo submitVo,List<ErrorVo> errorList) throws Exception {
		if(StringUtils.isBlank(submitVo.getProdDesc())){
			errorList.add(new ErrorVo("prodDesc", "商品描述为空!"));
			return errorList;
		}
		//解析商品描述html
		Document htmlDoc = Jsoup.parse(submitVo.getProdDesc());
		Elements imgTags = htmlDoc.select("img");
		String imgSrcStr="";
		String tempPath=commoditySettings.picDir+File.separator+submitVo.getMerchantCode()+File.separator+"prodDesc"+File.separator;
		File tempPathFile=new File(tempPath);
		if(!tempPathFile.exists()){
			tempPathFile.mkdirs();
		}
		int i=1;
		String picName="";
		List<ErrorVo> picErrorList=new ArrayList<ErrorVo>();
		List<MerchantPicture> merchantPictureList=new ArrayList<MerchantPicture>();
		Map<String, File> uploadFileMap = new HashMap<String, File>();
		String innerFilePath=MessageFormat.format(commoditySettings.imageFtpTemporarySpace, submitVo.getMerchantCode());
		
		for(Element imgTag:imgTags){
			String uuid=UUIDGenerator.getUUID();
			imgSrcStr=imgTag.attr("abs:src");
			
			//判断是不是优购的链接，是直接跳过
			if(imgSrcStr.indexOf("http://10.0.30.193")>-1||imgSrcStr.indexOf(".ygimg.cn")>-1||imgSrcStr.indexOf("img.yougou.com")>-1){
				continue;
			}
			//过滤删除相关非法的img
			if(StringUtils.isBlank(imgSrcStr)){//删除空的链接
				imgTag.remove();
				continue;
			}
			if(imgSrcStr.matches("http://.*gif$")){//删除gif的链接
				imgTag.remove();
				continue;
			}
			imgSrcStr=imgSrcStr.replaceAll(".jpg_.webp$", ".jpg");
			//下载图片到服务器本地
			File file = new File(tempPath, uuid+".jpg");
			try {
				OutputStream os = new FileOutputStream(file);
				InputStream is = HttpUtil.getInputStreamByURL(imgSrcStr);
				IOUtils.copy(is, os);
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);
			} catch (Exception e) {
				log.warn("商家:{} 不能下载图片{}",new Object[]{submitVo.getMerchantCode(),imgSrcStr,e});
				errorList.add(new ErrorVo("prodDesc", "图片不能下载,地址:" + imgSrcStr));
				continue;
			}

			//校验图片尺寸
			Image src = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
			BufferedImage image = null;
			if (src instanceof BufferedImage) {
				image = (BufferedImage) src;
			} else {
				src = new ImageIcon(src).getImage();
				try {
					image = new BufferedImage(src.getWidth(null), src.getHeight(null),
							BufferedImage.TYPE_INT_RGB);
					Graphics g = image.createGraphics();
					g.drawImage(src, 0, 0, null);
					g.dispose();
				} catch (IllegalArgumentException e) {
					log.error("商家:{} 获取图片长度和宽度失败:图片:{} URL:{}",new Object[]{submitVo.getMerchantCode(),file.getAbsolutePath(),imgSrcStr});
					errorList.add(new ErrorVo("prodDesc", "图片不能处理,地址:" + imgSrcStr));
					continue;
				}
			}

			int w = image.getWidth(null);
			int h = image.getHeight(null);
			
			if(w<740||w>790||h<10){
				log.error("商家:{} 图片不符合尺寸(宽:740-790,长:大于10),长:{}宽:{}地址:{}",new Object[]{submitVo.getMerchantCode(),h,w,imgSrcStr});
				imgTag.remove();
				continue;
			}
			
			picName=submitVo.getSupplierCode()+"_"+(i++)+".jpg";
			
			// 加入上传缩略图
			File thumbnai = PictureUtil.createThumbnailFile(file.getAbsolutePath());
			uploadFileMap.put(file.getName(), file);
			uploadFileMap.put(thumbnai.getName(), thumbnai);

			//插入数据库记录
			MerchantPicture pic=new MerchantPicture();
			pic.setId(uuid);
			pic.setCatalogId("0");
			pic.setHeight(src.getHeight(null));
			pic.setWidth(src.getWidth(null));
			pic.setMerchantCode(submitVo.getMerchantCode());
			pic.setPicPath(innerFilePath);
			pic.setPicSize(file.length());
			pic.setPicType(10);
			pic.setShopId(null);
			pic.setSourcePicName(picName);
			pic.setPicName(file.getName());
			pic.setThumbnaiPicName(thumbnai.getName());
			merchantPictureList.add(pic);
			
			//替换img新的str
			String imgUrl=commoditySettings.getCommodityPreviewDomain()+innerFilePath+file.getName();
			imgTag.attr("src", imgUrl);
			// 删除临时文件
			//file.delete();
		}
		
		if(picErrorList.size()==0){
			//合格ftp上传图片服务器
			 Map<String,File> failMap=this.ftpUpload(uploadFileMap,innerFilePath);
			 if(failMap.keySet().size()==0){
				for(MerchantPicture pic:merchantPictureList){
					pictureService.insertPic(pic);
				}
			 }else{
				errorList.add(new ErrorVo("prodDesc", "后台图片上传图片服务器失败!"));
			 }
		}
		submitVo.setProdDesc(htmlDoc.html());
		
		List<String> imgUrls = new ArrayList<String>();
		// 筛选合法图片链接
		Matcher matcher = yougou_pattern_invalid_img.matcher(submitVo.getProdDesc());
		while (matcher.find()) {
			imgUrls.add(PictureUtil.normalizeImageUrl(matcher.group()));
		}
		
		for (String url : imgUrls) {
			url = url.replaceAll("http://i[1-2].ygimg.cn/pics/", "http://10.10.10.181/pics/");
		}
		errorList.addAll(picErrorList);
		return errorList;
	}
}
