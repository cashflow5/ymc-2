/**
 * 
 */
package com.yougou.kaidian.image.service.impl;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.swing.ImageIcon;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.MessageChannel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.constant.SystemConstant;
import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.common.vo.ImageTaobaoMessage;
import com.yougou.kaidian.image.beans.BeanPropertyMatchesPredicate;
import com.yougou.kaidian.image.beans.CommodityPicEditor;
import com.yougou.kaidian.image.beans.LocalConfigBean;
import com.yougou.kaidian.image.beans.ResizeDefinition;
import com.yougou.kaidian.image.constant.ImageSetting;
import com.yougou.kaidian.image.service.IYmcWebImageService;
import com.yougou.kaidian.image.util.CommonUtil;
import com.yougou.kaidian.image.util.HttpUtil;
import com.yougou.kaidian.image.util.PictureUtil;
import com.yougou.kaidian.image.util.SpringFTPUtil;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.supplier.service.IMerchantImageService;
import com.yougou.merchant.api.supplier.vo.ImageJmsVo;
import com.yougou.merchant.api.supplier.vo.TaobaoImage;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.picture.Picture;

/**
 * @author li.m1
 *
 */
@Service
public class YmcWebImageServiceImpl implements IYmcWebImageService {

	private static final Logger logger = LoggerFactory.getLogger(YmcWebImageServiceImpl.class);
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSS");
	@Resource
	private ImageSetting imageSetting;
	@Resource
	private IMerchantImageService iMerchantImageService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private MessageChannel ftpChannel;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource
	private ICommodityBaseApiService commodityBaseApi;
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private LocalConfigBean localConfig;
	
	private String imageFtpPreTempSpace="/merchantpics/taobao/{0}/";
	
	// 优购合法图片链接Pattern （10.0.30.193为优购测试环境地址）
	private final static Pattern yougou_pattern_invalid_img = Pattern.compile("(?im)(http://10\\.0\\.30\\.193/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://i1\\.ygimg\\.cn/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://i2\\.ygimg\\.cn/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://img\\.yougou\\.com/{1,}\\S*\\.jpg\\?{0,1}\\w*)");
	private static Pattern YOUGOU_DESC_LINK_A = Pattern.compile("(?i)<a\\s+href=[\',\"]{1}(.*?)[\',\"]{1}\\s*(.*?)>(.*?)<\\s*/\\s*a\\s*>");
	private final static String YOUGOU_DESC_LINK = "(?i)http://(www\\.yougou\\.com(/\\w*)?(\\.\\w+)?)|(outlets\\.yougou\\.com(/\\w*)?(\\.\\w+)?)";
	private final static String YOUGOU_DESC_BR = "(?i)<br\\s*/>";
	private final static String YOUGOU_DESC_INVALID_IMG = "(?i)<img\\s+src=[\',\"]{1}\\s*[\',\"]{1}\\s*(.*?)/>";
	private final static String YOUGOU_DESC_P_START = "(?i)<p\\s*(.*?)>";
	private final static String YOUGOU_DESC_P_END = "(?i)</p\\s*(.*?)>";
	private final static String YOUGOU_DESC_NBSP = "(?i)&nbsp;";
	
	/**
	 * 处理单张图片-对应商家中心的批量发布商品
	 */
	@Override
	public void handlePhotoBySinglePic(Image4BatchUploadMessage message) throws Exception {
		File picDir = new File(this.createTemporaryPath(message.getMerchantCode(),message.getCommodityNo(), true));
		if (!picDir.exists()) { picDir.mkdirs(); }
		
		//下载图片
		File file = new File(picDir, message.getPicName());
		OutputStream os = new FileOutputStream(file);
		String imgUrl=message.getPicUrl().replaceAll("http://i[1-2].ygimg.cn/pics/", imageSetting.imageIpDownDomain);
		InputStream is =null;
		try {
			is = HttpUtil.getInputStreamByURL(imgUrl);
			IOUtils.copy(is, os);
		} catch (Exception e) {
			logger.error("商家编码:{} ,图片下载失败,URL:{} 异常信息:{}",new Object[]{message.getMerchantCode(),imgUrl,e});
			throw e;
		}finally{
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}

		//系统已存在的商品图片列表
		List<Picture> pictures_l = new ArrayList<Picture>();
		List<Picture> pictureList = commodityBaseApi.getPicturesByCommodityNo(message.getCommodityNo(), (short)1);
		CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", Pattern.compile("_0[1-7]_l\\.jpg", Pattern.MULTILINE), false), pictures_l);
		String picPath="";
		if(pictures_l.size()>0){
			picPath=pictures_l.get(0).getPicPath();
		}else{
			picPath=message.getUrlFragment();
		}
		//处理图片
		if (SystemConstant._BATCH_L.equals(message.getPicType())) {
			//角度图进行切图
			PictureUtil.createViewPics(file,ResizeDefinition.SCHEDULED_RESIZE, message.getSeqNo(), imageSetting.perlScripts, 
					new File(imageSetting.perlHome), imageSetting.imageMagickHome, imageSetting.imageMagickSharpen);
		} else {			
			//生成缩略图
			PictureUtil.createThumbnailFile_bt(file.getAbsolutePath());
		}
		
		//上传图片FTP
		List<String> pictureNames = new ArrayList<String>();
		File[] uploadFiles = picDir.listFiles();
		List<String> picResults = new ArrayList<String>();
		List<Picture> picListForUpload = new ArrayList<Picture>();//add by LQ.

		for (File uploadFile : uploadFiles) {
			SpringFTPUtil.ftpUpload(ftpChannel, uploadFile, picPath);
			// 处理商品图片信息
			Picture picture = null;
			picture = this.createPicture(message.getCommodityNo(), message.getMerchantCode(), uploadFile.getName(), picPath);
			picListForUpload.add(picture);
			
			pictureNames.add(uploadFile.getName());
/*			if(uploadFile.getName().toLowerCase().endsWith("_c.jpg")){
				//更新商品表default_pic和pic_small字段(等接口)
			}*/
		}	
		
		//Amend by LQ on 20150416
		if(null!=picListForUpload && 0<picListForUpload.size() ){
			
			// 打印日志 Add by LQ.
			logger.warn("image4BatchUploadReceive:切图处理完商品图片并且成功上传后，将所有上传的图片的信息批量更新到数据库,商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+").如下： ");
			for( int j=0;j<picListForUpload.size();j++ ){
				logger.warn("第"+j+"个图片的信息："+ ToStringBuilder.reflectionToString(picListForUpload.get(j)) );
			}
			picResults = commodityApi.insertPictureForBatch(picListForUpload);
			if( null!= picResults && 0<picResults.size() ){
				String picNames ="";
				for( int i=0;i<picResults.size();i++ ){
					picNames = picNames + " , "+picResults.get(i);
				}
				throw new Exception("商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+
						"),调用商品api-insertPictureForBatch失败，图片名称："+ picNames );
			}
		}
		
		//处理重复描述图和描述字符串
		if (SystemConstant._BATCH_B.equals(message.getPicType())) {
			/*
			 * 过滤商品描述图片
		    List<Picture> pictures = new ArrayList<Picture>();
			CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_B, false), pictures);
			CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor._XX_BT, false), pictures);
			 * List<Picture> delPictures = this.filtratePictures(pictures, pictureNames);
			if (CollectionUtils.isNotEmpty(delPictures)) 
				commodityApi.delCommodityPictures(delPictures);*/
			
			Commodity commodity = commodityApi.getCommodityByNo(message.getCommodityNo());
			//商品未待审核状态
			if (12 == commodity.getStatus()) {
				
				this.updateCommodityDescribeStr(message.getMerchantCode(), message.getCommodityNo(),pictureList);
			} else if (11 == commodity.getStatus()) {
				this.commodityDescRedisOptPre(message.getMerchantCode(), message.getCommodityNo());
			}
		}
		
		//删除临时图片文件夹
		FileUtils.deleteQuietly(picDir);
		//处理图片完成，删除缓存
		this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_BATCH_JMS_KEY, message.getCommodityNo());
		ImageJmsVo imageJmsVo=new ImageJmsVo();
		imageJmsVo.setId(message.getId());
		imageJmsVo.setStatus(1);
		iMerchantImageService.updateImageJmsStatus(imageJmsVo);
	}
	
	/**
	 * 处理商品级的图片-对应商家中心的单品发布
	 */
	@Override
	public void handlePhotoByCommodity(Image4SingleCommodityMessage message) throws Exception {
		//新建临时文件夹
		File picDir = new File(this.createTemporaryPath(message.getMerchantCode(),message.getCommodityNo(), false));
		if (!picDir.exists()) { picDir.mkdirs(); }
		
		//系统已存在的商品图片列表
		List<Picture> pictureList = commodityBaseApi.getPicturesByCommodityNo(message.getCommodityNo(), (short)1);
		List<Picture> pictures_l = new ArrayList<Picture>();
		String picPath="";
		CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", Pattern.compile("_0[1-7]_l\\.jpg", Pattern.MULTILINE), false), pictures_l);
		if(pictures_l.size()>0){
			picPath=pictures_l.get(0).getPicPath();
		}else{
			picPath=message.getUrlFragment();
		}
		
		//处理角度图
		File picFile=null;
		File btFile=null;
		OutputStream sourceOutputStream=null;
		InputStream picInputStream=null;
		List<File> picList=new ArrayList<File>();
		CommodityImage commodityImage=null;
		List<Picture> delPictures_l =null;
		String delPictures_l_Pattern="";
		String pic_l_url="";
		for(int i=0;i<message.getCommodityImages().length;i++){
			commodityImage = message.getCommodityImages()[i];
			//通过名称长度过滤0和-1的情况
			if(commodityImage.getPicName()!=null&&commodityImage.getPicName().length()>3){
				//下载图片
				picFile=new File(picDir,message.getCommodityNo()+"_"+CommonUtil.addZeroBeforeNumber((i+1), 2)+"_l.jpg");
				sourceOutputStream = new FileOutputStream(picFile);
				pic_l_url=commodityImage.getPicUrl().replaceAll("http://i[1-2].ygimg.cn/pics/", imageSetting.imageIpDownDomain);
				picInputStream=HttpUtil.getInputStreamByURL(pic_l_url);
				if(picInputStream!=null){
					IOUtils.copy(picInputStream, sourceOutputStream);
					IOUtils.closeQuietly(picInputStream);
					IOUtils.closeQuietly(sourceOutputStream);				
					picList.add(picFile);
				}else{
					throw new RuntimeException(MessageFormat.format("不能完成处理商品角度图.商家:{0} 商品:{1} 不能下载的图片:{2}",  new Object[]{message.getMerchantCode(),message.getCommodityNo(),pic_l_url}));
				}
			}else if("-1".equals(commodityImage.getPicUrl())&&i>=5){
				delPictures_l = new ArrayList<Picture>();
				delPictures_l_Pattern= "_0"+(i+1)+"_(l|t|m[bs]{0,1})\\.jpg";
				CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", Pattern.compile(delPictures_l_Pattern, Pattern.MULTILINE), false), delPictures_l);
				if(delPictures_l.size()>0){
					commodityApi.delCommodityPictures(delPictures_l);
				}
			}
		}
		//切图
		PictureUtil.createViewPics(picList, ResizeDefinition.SCHEDULED_RESIZE, message.getSeqNo(), imageSetting.perlScripts, new File(imageSetting.perlHome), imageSetting.imageMagickHome, imageSetting.imageMagickSharpen);
	
		//处理描述图-------------------------------------------
		String picDesc=message.getProDesc();
		Map<String,String> replacementDescPicsMap=new HashMap<String,String>();
		
		if (StringUtils.isNotBlank(picDesc)) {
			// 筛选合法图片链接
			Matcher matcher = yougou_pattern_invalid_img.matcher(picDesc);
			int i = 1;
			String picURL="";
			String _picURL="";
			String btPicURL="";
			List<String> downFailPicURLList=new ArrayList<String>();
			while (matcher.find()) {
				picURL=PictureUtil.normalizeImageUrl(matcher.group());
				//下载商品描述图片
				picFile=new File(picDir,message.getCommodityNo()+ "_" +CommonUtil.addZeroBeforeNumber((i), 2)+"_b.jpg");
				sourceOutputStream = new FileOutputStream(picFile);
				_picURL = picURL.replaceAll("http://i[1-2].ygimg.cn/pics/", imageSetting.imageIpDownDomain);

				picInputStream=HttpUtil.getInputStreamByURL(_picURL);
				if(picInputStream==null){
					//图片下载失败,加入失败List
					downFailPicURLList.add(_picURL);
				}else{
					IOUtils.copy(picInputStream, sourceOutputStream);
					IOUtils.closeQuietly(picInputStream);
					IOUtils.closeQuietly(sourceOutputStream);

	                //下载商品描述图片缩略图
					if(_picURL.indexOf("merchantpics")>-1){
						//临时空间图片
						btPicURL=_picURL.replaceAll("(\\.jpg)(?:\\?.*){0,1}$", ".png")+"?"+System.currentTimeMillis();
					}else{
						//临时空间图片
						btPicURL=_picURL.replaceAll("(_b\\.jpg)(?:\\?.*){0,1}$", "_bt.jpg")+"?"+System.currentTimeMillis();
					}
	                
	                picInputStream=HttpUtil.getInputStreamByURL(btPicURL);
	                if(picInputStream==null){
	    				//不能下载,本地生成缩略图
	                	logger.info("商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+"),不能下载,本地生成缩略图:" + btPicURL);
	    				PictureUtil.createThumbnailFile_bt(picFile.getAbsolutePath());
	                }else{
	                    btFile=new File(picDir,message.getCommodityNo()+ "_" +CommonUtil.addZeroBeforeNumber((i), 2)+"_bt.jpg");
	                    sourceOutputStream = new FileOutputStream(btFile);
	    				IOUtils.copy(picInputStream, sourceOutputStream);
	    				IOUtils.closeQuietly(picInputStream);
	    				IOUtils.closeQuietly(sourceOutputStream);
	                }
	                i=i+1;
					replacementDescPicsMap.put(picURL, imageSetting.imagePreviewDomain + picPath.substring(1, picPath.length()) + picFile.getName());
				}
			}
			
			if(downFailPicURLList.size()>0){
				//有不能下载的图片,抛出异常发邮件
				String downFailPicURLStr="";
				for(String downFailPicURL:downFailPicURLList){
					logger.error("商家({}),商品({})描述字符串不能下载该图片:{}",  new Object[]{message.getMerchantCode(),message.getCommodityNo(),downFailPicURL});
					downFailPicURLStr=downFailPicURLStr+downFailPicURL+";";
				}
				throw new RuntimeException(MessageFormat.format("不能完成处理商品描述图.商家:{0} 商品:{1} 不能下载的图片:{2}",  new Object[]{message.getMerchantCode(),message.getCommodityNo(),downFailPicURLStr}));
			}
		}
		
		if (this.checkFile4Upload(picDir,message.getCommodityImages()[message.getSeqNo()-1].getPicName().length()>3,message.getCommodityNo(),message.getMerchantCode())) {
			// 过滤商品描述图片
			List<Picture> pictures = new ArrayList<Picture>();
			CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor.XX_B, false), pictures);
			CollectionUtils.select(pictureList, new BeanPropertyMatchesPredicate("picName", CommodityPicEditor._XX_BT, false), pictures);

			List<String> pictureNames = new ArrayList<String>();
			//上传FTP--------------------------------------------
			File[] uploadFiles = picDir.listFiles();
			List<String> picResults = new ArrayList<String>();
			List<Picture> picListForUpload = new ArrayList<Picture>();//add by LQ.
			for (File uploadFile : uploadFiles) {
				SpringFTPUtil.ftpUpload(ftpChannel, uploadFile, picPath);
				// 处理商品图片信息
				Picture picture = null;
				picture = this.createPicture(message.getCommodityNo(),
						message.getMerchantCode(), uploadFile.getName(),
						picPath);
				picListForUpload.add(picture);//add by LQ.
				pictureNames.add(uploadFile.getName());
			}
			
			//Amend by LQ on 20150416
			if(null!=picListForUpload && 0<picListForUpload.size() ){
				// 打印日志 Add by LQ.
				logger.warn("image4SingleCommodityReceive:切图处理完商品图片并且成功上传后，将所有上传的图片的信息批量更新到数据库,商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+").如下： ");
				for( int j=0;j<picListForUpload.size();j++ ){
					logger.warn("第"+j+"个图片的信息："+ ToStringBuilder.reflectionToString(picListForUpload.get(j)) );
				}
				picResults = commodityApi.insertPictureForBatch(picListForUpload);
				if( null!= picResults && 0<picResults.size() ){
					String picNames ="";
					for( int i=0;i<picResults.size();i++ ){
						picNames = picNames + " , "+picResults.get(i);
					}
					throw new Exception("商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+
							"),调用商品api-insertPictureForBatch失败，图片名称："+ picNames );
				}
			}
			
			if (StringUtils.isNotBlank(picDesc)) {
				List<Picture> delPictures = this.filtratePictures(pictures, pictureNames);
				if (CollectionUtils.isNotEmpty(delPictures))
					commodityApi.delCommodityPictures(delPictures);
				 
				// 格式化商品宝贝描述
				String newDesc = this.descFormat(picDesc, replacementDescPicsMap);
				newDesc=newDesc.replaceAll("(?i)\\.png", ".jpg");
				String result = commodityApi.updateCommodityDescForMerchant(message.getCommodityNo(), message.getMerchantCode(), newDesc);
				if (!"SUCCESS".equalsIgnoreCase(result)) {
					throw new IllegalStateException("商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+"),更新商品宝贝描述失败：[" + result + "].");
				}else{//Add by LQ.
					logger.warn("商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+"),更新商品宝贝描述:"+newDesc);
				}
			}

			//删除临时图片文件夹--------------------------------------
			FileUtils.deleteQuietly(picDir);
			//处理图片完成，删除缓存
			this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_MASTER_JMS_KEY, message.getCommodityNo());
			ImageJmsVo imageJmsVo=new ImageJmsVo();
			imageJmsVo.setId(message.getId());
			imageJmsVo.setStatus(1);
			iMerchantImageService.updateImageJmsStatus(imageJmsVo);
			
			//更新图片处理完毕的状态
			String _result = commodityApi.updateCommodityPicFlag(message.getCommodityNo(), message.getMerchantCode(), picPath);
			logger.info("merchantCode ：{} | commodityNo : {} update picture status result : {}.", new Object[] { message.getMerchantCode(), message.getCommodityNo(), _result});
		}else{
			throw new Exception("商品编号:"+message.getCommodityNo()+"(商家编码:"+message.getMerchantCode()+"),图片完整性校验失败,请检查!");
		}
	}
	
	/**
	 * 图片完整性校验
	 * @param picDir
	 * @return
	 */
	private boolean checkFile4Upload(File picDir,boolean has_csu,String commodityNo,String merchantCode){
		Collection<File> l_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_l.jpg"), null);
		Collection<File> m_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_m.jpg"), null);
		Collection<File> mb_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_mb.jpg"), null);
		Collection<File> ms_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_ms.jpg"), null);
		Collection<File> t_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_t.jpg"), null);
		
		Collection<File> c_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_c.jpg"), null);
		Collection<File> s_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_s.jpg"), null);
		Collection<File> u_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_u.jpg"), null);
		
		Collection<File> b_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_b.jpg"), null);
		Collection<File> bt_pics=FileUtils.listFiles(picDir, FileFilterUtils.suffixFileFilter("_bt.jpg"), null);
		if(has_csu&&(c_pics.size()!=1||s_pics.size()!=1||u_pics.size()!=1)){
			logger.error("商品编号:"+commodityNo+"(商家编码:"+merchantCode+"),角度图c s u三张小图没有正确切图,请检查.");
			return false;
		}
		int number=l_pics.size();
		if(m_pics.size()!=number||mb_pics.size()!=number||ms_pics.size()!=number||t_pics.size()!=number){
			logger.error("商品编号:"+commodityNo+"(商家编码:"+merchantCode+"),角度图m mb ms t四张小图数量与l图不一致,请检查.");
			return false;
		}
		if(b_pics.size()!=bt_pics.size()){
			logger.error("商品编号:"+commodityNo+"(商家编码:"+merchantCode+"),描述图和描述图缩略图数量不一致,请检查.");
			return false;
		}
		return true;
	}

	/**
	 * 构造商品api的Picture对象
	 * @param commodityNo
	 * @param createUser
	 * @param picName
	 * @param picPath
	 * @return
	 */
	private Picture createPicture(String commodityNo,String createUser, String picName, String picPath) {
		Picture picture = new Picture();
		picture.setCommodityNo(commodityNo);
		picture.setCreateTime(new Date());
		picture.setCreateUser(createUser);
		picture.setPicName(picName);
		picture.setPicPath(picPath);
		Matcher matcher = CommodityPicEditor.COMMODITY_PIC_TYPE_PATTERN.matcher(picName);
		picture.setPicType(matcher.find() ? matcher.group() : null);
		return picture;
	}
	
	/**
	 * 宝贝描述格式化 HTML
	 * 
	 * @param submitVo
	 * @param replaceMap
	 * @return 格式化后标准的HTML
	 */
	private String descFormat(String rodDesc, Map<String, String> replaceMap) {
		// 替换上传后的图片地址
		String desc = descPicsReplace(rodDesc, replaceMap);
		// 处理宝贝描述中的<a href=""/>标签
		desc = descHrefReplace(desc);
		// 清理无效标签
		desc = deleteInvalidTag(desc);
		// 格式化HTML
		desc = "<p align=\"center\">" + desc + "</p>";
		return desc;
	}
	
	/**
	 * 替换商品描述图
	 * @param submitVo
	 * @param replaceMap
	 * @return
	 */
	private String descPicsReplace(String desc, Map<String, String> replaceMap) {
		if (replaceMap == null)
			return desc;
		
		// 防止同一序号的图片出现、采用后缀名替换法
		StringBuffer descbf = new StringBuffer(desc.replaceAll("(?i)\\.jpg", ".png"));
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> rep : replaceMap.entrySet()) {
			int start = descbf.indexOf(rep.getKey().replaceAll("(?i)\\.jpg", ".png"));
			if (start < 0) continue;
			
			sb.append(descbf.substring(0, start));
			sb.append(rep.getValue()).append("?").append(new Random().nextInt(100));
			sb.append(descbf.substring(start + rep.getKey().length()));
			descbf.delete(0, descbf.length());
			descbf.append(sb.toString());
			sb.delete(0, sb.length());
		}
		
		return descbf.toString();
	}
	
	/**
	 * 处理商品宝贝描述中的<a href="" />
	 * 	   只允许插入www.yougou.com或是outlets.yougou.com域名的超链接
	 * 
	 * @param prodDesc
	 * @return 格式化后的标准HTML
	 */
	private String descHrefReplace(String prodDesc) {
		List<String> aList = new ArrayList<String>();
		List<String> hrefList = new ArrayList<String>();
		List<String> aTextList = new ArrayList<String>();
		
		Matcher matcher = YOUGOU_DESC_LINK_A.matcher(prodDesc);
		while (matcher.find()) {
			aList.add(matcher.group());
			hrefList.add(matcher.group(1));
			aTextList.add(matcher.group(3));
		}
		
		Pattern p = Pattern.compile(YOUGOU_DESC_LINK);
		for (int i = 0; i < aList.size(); i++) {
			Matcher ygm = p.matcher(hrefList.get(i));
			boolean isFind = false;
			while (ygm.find()) {
				isFind = true;
			}
			
			if (!isFind) prodDesc = prodDesc.replace(aList.get(i), aTextList.get(i));
		}
		return prodDesc;
	}
	
	/**
	 * 删除描述字段中的无效tag
	 * 
	 * @param desc
	 * @return 格式化后的标准HTML
	 */
	private String deleteInvalidTag(String desc) {
		desc = desc.replaceAll(YOUGOU_DESC_P_START, "");
		desc = desc.replaceAll(YOUGOU_DESC_P_END, "");
		desc = desc.replaceAll(YOUGOU_DESC_BR, "\r\n");
		desc = desc.replaceAll(YOUGOU_DESC_INVALID_IMG, "");
		desc = desc.replaceAll(YOUGOU_DESC_NBSP, "");
		return desc;
	}
	
	private void updateCommodityDescribeStr(String merchantCode, String commodityNo,List<Picture> pictureList) {
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
		StringBuilder descBuilder = new StringBuilder();
		descBuilder.append("<p align=\"center\" generator=\"MERCHANTS\">");
		for (Picture picture : pictures) {
			descBuilder.append("<img src=\"").append(imageSetting.imagePreviewDomain).append(picture.getPicPath()).append(picture.getPicName()).append("\" border=\"0\"/>");
		}
		descBuilder.append("</p>");
		String commodityDesc = descBuilder.toString();
		
		if (logger.isInfoEnabled()) {
			logger.info("Generator commodity desc [" + commodityDesc + "] for '" + commodityNo + "'");
		}
		
		String returnValue = commodityApi.updateCommodityDescForMerchant(commodityNo, merchantCode, commodityDesc);
		
		if (!"SUCCESS".equalsIgnoreCase(returnValue)) {
			throw new IllegalStateException("商品编号:"+commodityNo+",更新商品宝贝描述失败：" + returnValue);
		}else{//Add by LQ.
			logger.warn("更新商品宝贝描述成功,商品编号:"+commodityNo+",(商家编码:"+merchantCode+"),commodityDesc= "+commodityDesc);
		}
	}
	
	//将未处理的商家编码记录到redis、后续一起进行处理
	@SuppressWarnings("unchecked")
	private void commodityDescRedisOptPre(String merchantCode, String commodityNo) {
		Set<String> commodityNos = (Set<String>) redisTemplate.opsForHash().get(CacheConstant.C_COMMODITY_PIC_DESC_KEY, merchantCode);
		if (CollectionUtils.isEmpty(commodityNos)) 
			commodityNos = new HashSet<String>();
		
		commodityNos.add(commodityNo);
		redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_PIC_DESC_KEY, merchantCode, commodityNos);
		logger.info("merchantCode:{} | put redis commodity description : {}.", new Object[]{merchantCode, commodityNos});
	}
	
	/**
	 * 筛选出已经存在的图片集合
	 * 
	 * @param pictures 
	 * @param pictureNames
	 * @return
	 */
	private List<Picture> filtratePictures(List<Picture> pictures, List<String> pictureNames) {
		List<Picture> delPictures = new ArrayList<Picture>();
		//查找被删除的b图
		for (Picture _obj : pictures) {
			boolean isFound = false;
			for (String pictureName : pictureNames) {
				if (_obj.getPicName().indexOf(pictureName) != -1) {
					isFound = true;
				}
			}
			if (!isFound) 
				delPictures.add(_obj);
		}
		
		return delPictures;
	}
	
	/**
	 * 创建相关临时目录
	 * 
	 * @param message
	 * @param isBatch 是否为批量传图
	 * @return
	 */
	private String createTemporaryPath(String merchantCode,String commodityNo,  boolean isBatch) {
		StringBuffer sb = new StringBuffer(imageSetting.commodityTemporaryPicdir);
		sb.append(File.separator);
		if (isBatch) sb.append("batch_");
		sb.append(merchantCode)
				.append(File.separator).append(commodityNo)
				.append("_").append(dateFormat.format(new Date()));
		if (isBatch) sb.append("_").append(new Random().nextInt(1000));
		return sb.toString();
	}
	
	/**
	 * 邮件发送处理图片失败信息
	 * @param message
	 */
	public void sandMail(String message,String title){
		SimpleMailMessage mail = new SimpleMailMessage();
		logger.info("切图不成功发送邮件.");
		try {
			if(localConfig.getReceiveMailAddress()!=null&&localConfig.getReceiveMailAddress().length>0){
				mail.setTo(localConfig.getReceiveMailAddress());// 接受者
				mail.setFrom("yougou_kaidian@163.com");// 发送者,这里还可以另起Email别名，不用和xml里的username一致
				mail.setSubject(title+"[服务器IP:"+this.getLocalIP()+"]");// 主题
				mail.setText(message);// 邮件内容
				mailSender.send(mail);
			}else{
				logger.error("邮件发送列表为空!");
			}
		} catch (Exception e) {
			logger.error("邮件发送失败:",e);
		}
	}

	@Override
	public String getLocalIP() {
		try {
			String ipStr="";
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address && !"127.0.0.1".equals(ip.getHostAddress())) {
						ipStr=ipStr+ip.getHostAddress()+",";
					}
				}
			}
			return ipStr;
		} catch (Exception e) {
			logger.error("获取本地IP失败:",e);
			return "127.0.0.1";
		}
	}

	@Override
	public void handleTaobaoPhoto(ImageTaobaoMessage message) throws Exception {
		//处理角度图
		String[] imgArry=message.getImgArry();
		String newUrl;
		if(imgArry!=null){
			int index=1;
			String defaultPic=null;
			StringBuilder anglePic=new StringBuilder();
			List<TaobaoImage> taobaoImages = new ArrayList<TaobaoImage>();
			TaobaoImage taobaoImage = null;
			for(int i=0;i<imgArry.length;i++){
				newUrl=this.verifyExternalCommodityPic(imgArry[i], UUIDGenerator.getUUID()+"_0"+index+"_l", message.getMerchantCode());
				if(newUrl!=null){
					if(index==1){
						defaultPic=newUrl.replaceAll("\\.jpg$", ".png");
					}
					index++;
					
					logger.warn( "image4TaobaoReceive：淘宝CSV导入角度图图片预处理完毕，更新淘宝图片转换为优购的地址,numIid = "+message.getNumIid()
							+", oldImgUrl= "+imgArry[i]+", newUrl="+newUrl+", thumbnailUrl="+newUrl.replaceAll("\\.jpg$", ".png") );
					
					//处理完图片，封装待更新数据集合
					taobaoImage = new TaobaoImage(Long.parseLong(message.getNumIid()),imgArry[i], 
							newUrl,newUrl.replaceAll("\\.jpg$", ".png")); 
					taobaoImages.add(taobaoImage);
					//iMerchantImageService.updateTaobaoItemImgURL(Long.parseLong(message.getNumIid()),imgArry[i], newUrl,newUrl.replaceAll("\\.jpg$", ".png"));
					anglePic.append(newUrl).append("序").append(i).append("图");
				}
			}
			//处理完图片，批量更新数据库字段
			if(CollectionUtils.isNotEmpty(taobaoImages)){
				iMerchantImageService.updateTaobaoItemImgURLBatch(taobaoImages);
			}
			iMerchantImageService.updateTaobaoItemImgAngle(Long.parseLong(message.getNumIid()), anglePic.toString());
			String newProDesc=this.verifyExternalCommodityDescPic(message.getProDesc(), message.getMerchantCode());
			//处理完图片，更新数据库字段
			logger.warn("image4TaobaoReceive：淘宝CSV导入描述图预处理完毕，更新描述转换为优购的描述图： numIid = "+message.getNumIid()+", newProDesc="+newProDesc+
						", defaultPic="+defaultPic);
			iMerchantImageService.updateTaobaoImgDesc(Long.parseLong(message.getNumIid()), newProDesc,defaultPic);
			//处理完图片，修改状态
			ImageJmsVo returnMessage=new ImageJmsVo();
			returnMessage.setId(message.getId());
			returnMessage.setStatus(1);
			iMerchantImageService.updateImageJmsStatus(returnMessage);
		}
	}
	
	/**
	 * 处理外部平台商品图片
	 * @param srcURL
	 * @param newName
	 * @param merchantCode
	 * @return
	 * @throws Exception
	 */
	private String verifyExternalCommodityPic(String srcURL,String newName,String merchantCode) {
		//下载图片到服务器本地
		String tempPath=imageSetting.commodityTemporaryPicdir+File.separator+merchantCode+File.separator+"taobaoPic"+File.separator;
		File tempPathFile=new File(tempPath);
		if(!tempPathFile.exists()){
			tempPathFile.mkdirs();
		}
		File file = new File(tempPath, newName+".jpg");
		try {
			OutputStream os = new FileOutputStream(file);
			InputStream is = HttpUtil.getInputStreamByURL(srcURL);
			IOUtils.copy(is, os);
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
			
			//校验图片尺寸
			Image src = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
			BufferedImage image = null;
			if (src instanceof BufferedImage) {
				image = (BufferedImage) src;
			} else {
				src = new ImageIcon(src).getImage();
				image = new BufferedImage(src.getWidth(null), src.getHeight(null),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = image.createGraphics();
				g.drawImage(src, 0, 0, null);
				g.dispose();
			}

			int w = image.getWidth(null);
			int h = image.getHeight(null);
			
			if(w<800||h<800){
				logger.error("商家编码({})处理外部商品图片不符合优购平台要求:URL:{} width:{} height:{}",new Object[]{merchantCode,srcURL,w,h});
				return null;
			}
			// 加入上传缩略图
			Map<String, File> uploadFileMap = new HashMap<String, File>();
			File thumbnai = PictureUtil.createThumbnailFile(file.getAbsolutePath());
			uploadFileMap.put(file.getName(), file);
			uploadFileMap.put(thumbnai.getName(), thumbnai);
			
			String innerFilePath=MessageFormat.format(imageFtpPreTempSpace, merchantCode);
			SpringFTPUtil.ftpUpload(ftpChannel, uploadFileMap, innerFilePath);
			return imageSetting.imagePreviewDomain+innerFilePath+file.getName();
		} catch (Exception e) {
			logger.error("商家编码({})处理外部商品图片产生异常:URL{} 异常信息：{} ",new Object[]{merchantCode,srcURL,e});
			return null;
		}
	}
	
	/**
	 * 处理外部平台商品描述图片
	 * @param descStr
	 * @param merchantCode
	 * @return
	 * @throws Exception
	 */
	private String verifyExternalCommodityDescPic(String descStr,String merchantCode) {
		if(StringUtils.isBlank(descStr)){
			return descStr;
		}
		//解析商品描述html
		Document htmlDoc = Jsoup.parse(descStr);
		Elements imgTags = htmlDoc.select("img");
		String imgSrcStr="";
		String tempPath=imageSetting.commodityTemporaryPicdir+File.separator+merchantCode+File.separator+"taobaoProdDesc"+File.separator;
		File tempPathFile=new File(tempPath);
		if(!tempPathFile.exists()){
			tempPathFile.mkdirs();
		}
		Map<String, File> uploadFileMap = new HashMap<String, File>();
		String innerFilePath=MessageFormat.format(imageFtpPreTempSpace, merchantCode);
		
		for(Element imgTag:imgTags){
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
			
			//下载图片到服务器本地
			File file = new File(tempPath, UUIDGenerator.getUUID()+".jpg");
			try {
				OutputStream os = new FileOutputStream(file);
				InputStream is = HttpUtil.getInputStreamByURL(imgSrcStr);
				IOUtils.copy(is, os);
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);

				//校验图片尺寸
				Image src = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
				BufferedImage image = null;
				if (src instanceof BufferedImage) {
					image = (BufferedImage) src;
				} else {
					src = new ImageIcon(src).getImage();
					image = new BufferedImage(src.getWidth(null), src.getHeight(null),
							BufferedImage.TYPE_INT_RGB);
					Graphics g = image.createGraphics();
					g.drawImage(src, 0, 0, null);
					g.dispose();
				}
	
				int w = image.getWidth(null);
				int h = image.getHeight(null);
				
				if(w<740||w>790||h<10){
					logger.error(merchantCode+"----图片不符合尺寸:"+imgSrcStr);
					imgTag.remove();
					continue;
				}
				
				// 加入上传缩略图
				File thumbnai = PictureUtil.createThumbnailFile(file.getAbsolutePath());
				uploadFileMap.put(file.getName(), file);
				uploadFileMap.put(thumbnai.getName(), thumbnai);
				
				//替换img新的str
				String imgUrl=imageSetting.imagePreviewDomain+innerFilePath+file.getName();
				imgTag.attr("src", imgUrl);
			} catch (Exception e) {
				logger.error(merchantCode+"----处理外部图片异常,地址::"+imgSrcStr);
				continue;
			}
		}
		
		//合格ftp上传图片服务器
		 Map<String, File> failMap;
		try {
			failMap = SpringFTPUtil.ftpUpload(ftpChannel, uploadFileMap, innerFilePath);
			 if(failMap.keySet().size()>0){
				 logger.error(merchantCode+"----图片上传ftp失败");
				 throw new RuntimeException(merchantCode+"----图片上传ftp失败");
			 }
		} catch (Exception e) {
			logger.error(merchantCode+"----图片上传ftp失败");
			 throw new RuntimeException(merchantCode+"----图片上传ftp失败");
		}
		return htmlDoc.html();
	}
}
