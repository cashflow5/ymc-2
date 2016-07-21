package com.belle.yitiansystem.merchant.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.merchant.api.common.Query;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTraining;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingLog;
import com.belle.yitiansystem.merchant.service.IMerchantTrainingService;
import com.belle.yitiansystem.merchant.thread.ProcessCallable;
import com.belle.yitiansystem.merchant.thread.ProcessPool;
import com.belle.yitiansystem.merchant.util.PropertiesUtilForConverter;
import com.belle.yitiansystem.merchant.util.SpringFTPUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.belle.yitiansystem.taobao.exception.BusinessException;

/**
 * 培训中心Controller
 * 
 * @author luo.qian
 *
 */
@Controller
@RequestMapping("/yitiansystem/merchants/training")
public class TrainingCenterController{
	
	private Logger logger = Logger.getLogger(TrainingCenterController.class);
    private final static String TRAINING_LIST_URL   = "yitiansystem/training/training_list";
	private final static String TO_ADD_TRAINING_URL = "yitiansystem/training/to_add_training";
	private final static String TRAINING_DETAIL_URL = "yitiansystem/training/training_detail";
	private final static String TRAINING_LOG_URL    = "yitiansystem/training/view_operation_log";
	private final static String TRAINING_PREVIEW_URL = "yitiansystem/training/training_preview";
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private MessageChannel ftpChannelTraining;
	
	@Resource
	private SysconfigProperties sysconfigProperties;
	
	@Resource
	private IMerchantTrainingService merchantTrainingService;
	
	@RequestMapping("to_training")
	public ModelAndView goToTrainingCenter(ModelMap modelMap,MerchantTraining merchantTraining,Query query,
			HttpServletRequest request){
		modelMap.addAttribute("merchantTraining",merchantTraining);
		PageFinder<MerchantTraining> pageFinder  = merchantTrainingService.queryTrainingList(query,merchantTraining,redisTemplate) ;
		modelMap.addAttribute("pageFinder", pageFinder );
		return new ModelAndView(TRAINING_LIST_URL, modelMap);
		
	}

	@RequestMapping("to_add_training")
	public String toAddOrUpdateTraining(ModelMap modelMap,String id,HttpServletRequest request){
		if(!StringUtils.isEmpty(id)){
			MerchantTraining merchantTraining = merchantTrainingService.selectByPrimaryKey(id);
			if(null!=merchantTraining){
				modelMap.addAttribute("merchantTraining",merchantTraining);
				String headPath = sysconfigProperties.getTrainingFtpPathRead();
				modelMap.addAttribute("headPath",headPath);
			}else{
				logger.error("您修改的课程记录不存在，请确认！");
			}
		}
		return TO_ADD_TRAINING_URL;
	}
	
	@RequestMapping("training_detail")
	public String queryDetail(ModelMap modelMap ,String id ){
		if(!StringUtils.isEmpty(id)){
			MerchantTraining merchantTraining = merchantTrainingService.selectByPrimaryKey(id);
			if(null!=merchantTraining){
				modelMap.addAttribute("merchantTraining",merchantTraining);
				String headPath = sysconfigProperties.getTrainingFtpPathRead();
				modelMap.addAttribute("headPath",headPath);
			}else{
				logger.error("您要查看的课程记录不存在！");
			}
		}
		return TRAINING_DETAIL_URL;
	}
	
	@RequestMapping("to_training_preview")
	public String toTrainingPreview(ModelMap modelMap ,String previewUrl,Short fileType ){
		MerchantTraining merchantTraining = new MerchantTraining();
		merchantTraining.setFileType(fileType);
		merchantTraining.setPreviewUrl(previewUrl);
		modelMap.addAttribute("merchantTraining",merchantTraining);
		String headPath = sysconfigProperties.getTrainingFtpPathRead();
		modelMap.addAttribute("headPath",headPath);
		return TRAINING_PREVIEW_URL;
	}
	
	@ResponseBody
	@RequestMapping("del_training")
	public String deleteTraining(ModelMap modelMap ,String id,HttpServletRequest request){
		
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		String curUser = user.getLoginName();
		// 逻辑删除
		boolean success = merchantTrainingService.deleteTraining(id,curUser);
		if(success){
			return "true";
		}else{
			return "false";
		}
	}
	
	@ResponseBody
	@RequestMapping("publish_training")
	public String publishTraining(ModelMap modelMap ,String id,Short flag,HttpServletRequest request){
		
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		String curUser = user.getLoginName();
		JSONObject result = new JSONObject();
		try {
			Map cache =(HashMap<String,String>) redisTemplate.opsForValue().get(Constant.PROCESS_POOL_REDIS_KEY);
			if( null!= cache && null!=cache .get(id) ) {
				result.put("resultCode", "500");
	    		result.put("msg","课程文件正在处理中，请稍后再试！The file of this course is under conversion, please try it later. ");
			}else{
				merchantTrainingService.publishTraining(id,flag,curUser);
				result.put("resultCode", "200");
			}
		} catch (BusinessException e) {
			result.put("resultCode", "500");
    		result.put("msg",e.getMessage());
		}
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("push_training")
	public String pushTraining(ModelMap modelMap ,String id,Short flag,HttpServletRequest request){
		
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		String curUser = user.getLoginName();
		boolean success = merchantTrainingService.pushTraining(id,flag,curUser);
		if(success){
			return "true";
		}else{
			return "false";
		}
	}
	
	/**
	 * 查询操作日志
	 * 
	 * @param id
	 * @param modelMap
	 * @param query
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("training_log")
	public ModelAndView viewMerchantOperationLog(String trainingId,
			ModelMap modelMap, Query query) throws Exception {
		PageFinder<MerchantTrainingLog> pageFinder = merchantTrainingService.queryTrainingOperationLog(trainingId, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("trainingId", trainingId);
		return new ModelAndView(TRAINING_LOG_URL, modelMap);
	}
	
	@ResponseBody
	@RequestMapping("save_training")
	public String saveTraining(ModelMap modelMap,MerchantTraining merchantTraining,String updatePreviewUrlFlag,HttpServletRequest request){
		
		JSONObject result = new JSONObject();
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		String curUser =user.getLoginName();
		String fileName = merchantTraining.getFileName();
		String type = merchantTraining.getFileName().substring(fileName.lastIndexOf("."));
		type = type.toLowerCase();//为了判断转小写
		String trainingFtpPath = sysconfigProperties.getTrainingFtpPath();
		String relativePath =  "";
		if( Constant.TRAINING_FILE_TYPE_DOC == merchantTraining.getFileType() ){
			relativePath = sysconfigProperties.getRelativeDocPath();
		}else{
			relativePath = sysconfigProperties.getRelativeVedioPath();
		}
		String tempFilePath = merchantTraining.getFileUrl();
		File tempFile = new File(tempFilePath);
		// ftp上传原文件 -- uploadSuccess 事件发生后，就得做ftp ，否则，文件未修改，则不用ftp处理
		if( "true".equalsIgnoreCase(updatePreviewUrlFlag) ){
			if ( !StringUtils.isEmpty( tempFilePath ) && tempFile.exists() ){
				try {
					SpringFTPUtil.ftpUpload(ftpChannelTraining, tempFile, trainingFtpPath+relativePath );
					merchantTraining.setFileUrl( relativePath + tempFilePath.substring(tempFilePath.lastIndexOf("/")+1));
				} catch (UnsupportedEncodingException e) {
					result.put("resultCode", "500");
		    		result.put("msg","文件ftp上传失败，不支持该文件编码，请重新选择文件上传！");
		    		logger.error("保存课程失败：文件ftp上传失败，不支持该文件编码，请确认环境后重新选择文件上传！");
		    		return result.toString();
				} catch (Exception e) {
					result.put("resultCode", "500");
		    		result.put("msg","文件ftp上传发生异常，请确认环境后重新选择文件上传！");
		    		logger.error("保存课程失败：文件ftp上传发生异常，请确认环境后重新选择文件上传！");
		    		return result.toString();
				}
			}else{
				result.put("resultCode", "500");
	    		result.put("msg","抱歉！未找到您上传的文件，请重新上传！");
	    		logger.error("保存课程失败：未找到上传到服务器临时目录的文件。请重新上传！");
	    		return result.toString();
			}
		}
		
		try{
			short isPublish = merchantTraining.getIsPublish();//是否发布
			
			String id = merchantTrainingService.saveTraining(merchantTraining, curUser);
			
    		result.put("resultCode", "200");
    		
    		merchantTraining.setId(id);
    		// 根据标志位确定是否处理previewUrl，即：做文件转换操作
    		if( "true".equalsIgnoreCase(updatePreviewUrlFlag) ){
    			saveTrainingStep2(modelMap,merchantTraining,isPublish,type,trainingFtpPath,relativePath);//根据isPublish来决定文件处理完成后更新发布状态
    		}
    	}catch(BusinessException e){
    		result.put("resultCode", "500");
    		result.put("msg",e.getMessage());
    		logger.error("保存课程失败："+e.getMessage());
    	}catch(Exception e){
    		result.put("resultCode", "500");
    		result.put("msg","系统异常，保存失败");
    		logger.error("保存课程失败："+e.getMessage(),e);
    	}
    	return result.toString();
	}

	public void saveTrainingStep2(ModelMap modelMap,MerchantTraining merchantTraining,short isPublish,
								 String type,	String trainingFtpPath,String relativePath ) throws BusinessException{
		
		
		// step1 不需要转换格式的，只用设置previewUrl
		String id = merchantTraining.getId();
		if( type.endsWith("flv")  || type.endsWith("swf") ){
			MerchantTraining merchantTrainingForUpdate = new MerchantTraining ();
			merchantTrainingForUpdate.setId(id);
			merchantTrainingForUpdate.setPreviewUrl( merchantTraining.getFileUrl() );
			merchantTrainingForUpdate.setIsPublish(isPublish);//真正发布
			merchantTrainingService.updateByPrimaryKeySelective(merchantTrainingForUpdate);
		}else if( type.endsWith("ppt") || type.endsWith("pptx") || type.endsWith("pdf") || type.endsWith("doc")|| type.endsWith("docx") ){
			// 设置previewUrl
			String tempPath = sysconfigProperties.getSpringMVCFilePathTemp();
			merchantTraining.setIsPublish(isPublish);
			//  开启线程池去做
			ProcessCallable callable = new ProcessCallable( merchantTraining,
															tempPath,
															trainingFtpPath,
															relativePath,
															type,
															redisTemplate,
															ftpChannelTraining );
			ProcessPool.processPool.submit( callable );
			
		}else{
			throw new BusinessException("文件格式不对！请重新选择文件上传！");
		}
				
	}

	/** 文件上传暂时保存到服务器本地临时目录 */
	@ResponseBody
	@RequestMapping("handleUploadFile")
	public String handleUploadFile(MultipartHttpServletRequest request,HttpSession session,String trainingId){
		JSONObject jsonObject = new JSONObject();
		//判断该课程的原文件是否在处理中状态，是，则提示"该课程文件还在处理中，请稍候上传新文件！"
		if( !StringUtils.isEmpty(trainingId) ) {
			Map<String,String> getMap = (Map<String,String>)redisTemplate.opsForValue().get( Constant.PROCESS_POOL_REDIS_KEY ) ;
			if( null!=getMap && null!= getMap.get(trainingId) ) {
				jsonObject.put("success", false);
				jsonObject.put("message", "3");//该课程的原文件还在处理中，请等待处理完毕后再开始上传新文件！
				return jsonObject.toString();
			}
		}
		
		final int MAX_FILE_SIZE = Integer.parseInt(PropertiesUtilForConverter.getValue("officeview.file.maxsize"));
		final int MAX_VEDIO_SIZE = Integer.parseInt(PropertiesUtilForConverter.getValue("videoview.file.maxsize"));
		String filePath = null;
		File file = null;//copy原文件
		FileInputStream fis  = null;
		try {
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile multipartFile = request.getFile(itr.next());
				
				String fileName = multipartFile.getOriginalFilename();//原文件名
				String type = fileName.substring(fileName.lastIndexOf("."));
				String tempName = System.currentTimeMillis()+type;
				
				filePath = sysconfigProperties.getSpringMVCFilePathTemp();
				
				filePath = filePath.replaceAll("\\\\", "/");
				// 文件格式校验
				short fileType = Constant.TRAINING_FILE_TYPE_DOC;
				
				type=type.toLowerCase();//为了判断转小写，只供判断用
				
				if( type.endsWith("ppt") || type.endsWith("pptx") || type.endsWith("pdf") || type.endsWith("swf") 
						|| type.endsWith("doc")|| type.endsWith("docx") ){
					fileType = Constant.TRAINING_FILE_TYPE_DOC;
				}else if(type.endsWith("flv")){
					fileType = Constant.TRAINING_FILE_TYPE_VEDIO;
				}else{
					jsonObject.put("success", false);
					jsonObject.put("message", "0");//文件格式不对,只允许上传ppt，pptx，pdf,swf，flv格式!
					return jsonObject.toString();
				}
			
				// 文件大小校验
				long size = multipartFile.getSize();
				if ( fileType==Constant.TRAINING_FILE_TYPE_DOC && size > MAX_FILE_SIZE) {
					jsonObject.put("success",false);
					jsonObject.put("message", "0");//文件最大不能超过配置大小
					return jsonObject.toString();
				}else if ( fileType==Constant.TRAINING_FILE_TYPE_VEDIO && size > MAX_VEDIO_SIZE) {
					jsonObject.put("success",false);
					jsonObject.put("message", "0");//文件最大不能超过配置大小
					return jsonObject.toString();
				}
				
				File dir  = new File(filePath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				filePath = filePath+ tempName;
				// 文件流复制
				file = new File(filePath);
				multipartFile.transferTo(file);
					
				jsonObject.put("success",true);
				jsonObject.put("fileUrl", filePath);//本地临时文件路径
				jsonObject.put("fileType", fileType);
				return jsonObject.toString();
				
			}else{
				jsonObject.put("success", false);
				jsonObject.put("message", "1");//上传的文件为空 !
				return jsonObject.toString();
			}
			
		} catch (Exception ex) {
			jsonObject.put("success", false);
			jsonObject.put("message", "2");//课程文件上传到服务器失败 !
			logger.error(ex.getMessage(),ex);
			return jsonObject.toString();
		}finally{
			
			if(null!=fis){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
//	@ResponseBody
//	@RequestMapping("handleUploadFile")
//	public String handleUploadFile(MultipartHttpServletRequest request,HttpSession session){
//		
//		final int MAX_FILE_SIZE = Integer.parseInt(PropertiesUtilForConverter.getValue("officeview.file.maxsize"));
//		final int MAX_VEDIO_SIZE = Integer.parseInt(PropertiesUtilForConverter.getValue("videoview.file.maxsize"));
//		StringBuffer ftpServerPath = new StringBuffer( sysconfigProperties.getTrainingFtpPath()+"course/" );
//		String filePath = null;
//		File file = null;//copy原文件
//		File afterFile = null;//格式转换后文件
//		JSONObject jsonObject = new JSONObject();
//		FileInputStream fis  = null;
//		String afterFilePath = "";
//		try {
//			Iterator<String> itr = request.getFileNames();
//			if (itr.hasNext()) {
//				MultipartFile multipartFile = request.getFile(itr.next());
//				
//				String fileName = multipartFile.getOriginalFilename();//原文件名
//				//String previewFileName = fileName.substring(0,fileName.lastIndexOf("."));//ftp上传后文件重命名
//				
//				String type = fileName.substring(fileName.lastIndexOf("."));
//				String tempName = System.currentTimeMillis()+type;
//				String previewFileName = "";
//				
//				filePath = session.getServletContext().getRealPath("/")+"/tempupload";
//				filePath = filePath.replaceAll("\\\\", "/");
//				// 文件格式校验
//				short fileType = Constant.TRAINING_FILE_TYPE_DOC;
//				
//				type=type.toLowerCase();//为了判断转小写，只供判断用
//				
//				if( type.endsWith("ppt") || type.endsWith("pptx") || type.endsWith("pdf") ){
//					fileType = Constant.TRAINING_FILE_TYPE_DOC;
//					previewFileName = tempName.substring(0,tempName.lastIndexOf("."))+".swf";
//					afterFilePath = filePath+ "/" + previewFileName;
//					ftpServerPath.append("doc/");
//				}else if(type.endsWith("swf")){
//					fileType = Constant.TRAINING_FILE_TYPE_DOC;
//					previewFileName = tempName;
//					afterFilePath = filePath+ "/" + previewFileName;
//					ftpServerPath.append("doc/");
//				}else if(type.endsWith("flv")){
//					fileType = Constant.TRAINING_FILE_TYPE_VEDIO;
//					previewFileName = tempName;
//					afterFilePath = filePath+ "/" + previewFileName;
//					ftpServerPath.append("vedio/");
//				}else{
//					jsonObject.put("success", false);
//					jsonObject.put("message", "0");//文件格式不对,只允许上传ppt，pptx，pdf,flv格式!
//					return jsonObject.toString();
//				}
//			
//				// 文件大小校验
//				long size = multipartFile.getSize();
//				if ( fileType==Constant.TRAINING_FILE_TYPE_DOC && size > MAX_FILE_SIZE) {
//					jsonObject.put("success",false);
//					jsonObject.put("message", "0");//文件最大不能超过配置大小
//					return jsonObject.toString();
//				}else if ( fileType==Constant.TRAINING_FILE_TYPE_VEDIO && size > MAX_VEDIO_SIZE) {
//					jsonObject.put("success",false);
//					jsonObject.put("message", "0");//文件最大不能超过配置大小
//					return jsonObject.toString();
//				}
//				
//				File dir  = new File(filePath);
//				if(!dir.exists()){
//					dir.mkdirs();
//				}
//				filePath = filePath+ "/" + tempName;
//				// 文件流复制
//				file = new File(filePath);
//				multipartFile.transferTo(file);
//				
//				// step 1:transfer
//				Map<String,File> uploadFileMap = new HashMap<String,File>();
//				uploadFileMap.put(tempName, file);
//				if( type.endsWith("ppt") ||  type.endsWith("pptx") || type.endsWith("pdf") ){
//					merchantTrainingService.transferFileForView(filePath,type);//若有异常，抛出的是BusinessException
//					afterFile = new File(afterFilePath);
//					uploadFileMap.put(previewFileName, afterFile);
//				}
//				
//				// step 2:ftp
//				Map<String,File> failFile = SpringFTPUtil.ftpUpload(ftpChannelTraining, uploadFileMap, ftpServerPath.toString());
//				
//				// step 3:response
//				if(null!=failFile && 0<failFile.size() ){
//					jsonObject.put("success", false);
//					jsonObject.put("message", "1");//一对文件部分上传失败,请检查!
//					return jsonObject.toString();
//				}else{
//					//~~~~~~~~~~~ 真正成功 进入这里~~~~~~~~~~~~~~
//					String headPath = "http://"+
//							sysconfigProperties.getFtpServerIp()+"/pics";
//					jsonObject.put("success",true);
//					jsonObject.put("fileUrl", headPath+ftpServerPath + tempName);
//					jsonObject.put("previewUrl",headPath+ ftpServerPath + previewFileName);
//					jsonObject.put("fileType", fileType);
////					jsonObject.put("fileName", fileName);
//					return jsonObject.toString();
//				}
//				
//			}else{
//				jsonObject.put("success", false);
//				jsonObject.put("message", "2");//上传的文件为空 !
//				return jsonObject.toString();
//			}
//			
//		} catch (BusinessException ex) {
//			jsonObject.put("success", false);
//			jsonObject.put("message", "3");//文件格式转换失败 !
//			logger.error(ex.getMessage(),ex);
//			return jsonObject.toString();
//		} catch (Exception ex) {
//			jsonObject.put("success", false);
//			jsonObject.put("message", "4");//文件处理失败 !
//			logger.error(ex.getMessage(),ex);
//			return jsonObject.toString();
//		}finally{
//			
//			if(null!=fis){
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if(null!=file){
//				file.delete();
//			}
//			if(null!=afterFile){
//				afterFile.delete();
//			}
//		}
//		
//		
//	}
	
	@ResponseBody
	@RequestMapping("upload")
	public String upload(MultipartHttpServletRequest request,HttpSession session){
		
		StringBuffer ftpServerPath = new StringBuffer(sysconfigProperties.getTrainingFtpPath())
										.append( sysconfigProperties.getRelativePicPath() );
		String filePath = null;
		File file = null;//copy原文件
		JSONObject jsonObject = new JSONObject();
		FileInputStream fis  = null;
		try {
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile multipartFile = request.getFile(itr.next());
				String fileName = multipartFile.getOriginalFilename();//原文件名，ftp之后重命名
				String tempName = System.currentTimeMillis()+".jpg";
				filePath = sysconfigProperties.getSpringMVCFilePathTemp();
				filePath = filePath.replaceAll("\\\\", "/");
				File dir  = new File(filePath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				filePath = filePath + fileName;
				// 文件流复制
				file = new File(filePath);
				multipartFile.transferTo(file);
	
				// step 2:ftp
				Map<String,File> uploadFileMap = new HashMap<String,File>();
				uploadFileMap.put(tempName, file);
				Map<String,File> failFile = SpringFTPUtil.ftpUpload(ftpChannelTraining, uploadFileMap, ftpServerPath.toString() );
				
				// step 3:response
				if(null!=failFile && 0<failFile.size() ){
					jsonObject.put("success", false);
					logger.error("课程主图上传：调用ftp上传图片失败");
					return jsonObject.toString();
				}else{
					StringBuffer previewUrl = new StringBuffer(sysconfigProperties.getRelativePicPath()).append(tempName);
					jsonObject.put("success",true);
					jsonObject.put("headPath",sysconfigProperties.getTrainingFtpPathRead() );
					jsonObject.put("src", previewUrl.toString());
					return jsonObject.toString();
				}
				
			}else{
				jsonObject.put("success", false);
				logger.error("课程主图上传：上传的文件未空");
				return jsonObject.toString();
			}
		
		} catch (Exception ex) {
			jsonObject.put("success", false);
			logger.error("课程主图上传：上传失败",ex);
			return jsonObject.toString();
		}finally{
			
			if(null!=fis){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=file){
				file.delete();
			}
		}
		
	}
	
//	@ResponseBody
//	@RequestMapping("upload")
//	public String upload(MultipartHttpServletRequest request,HttpSession session){
//		
//		final String ftpServerPath = sysconfigProperties.getTrainingFtpPath()+"pic/";
//		String filePath = null;
//		File file = null;//copy原文件
//		JSONObject jsonObject = new JSONObject();
//		FileInputStream fis  = null;
//		try {
//			Iterator<String> itr = request.getFileNames();
//			if (itr.hasNext()) {
//				MultipartFile multipartFile = request.getFile(itr.next());
//				
//				String fileName = multipartFile.getOriginalFilename();//原文件名，ftp之后重命名
//				
//			//	String type = fileName.substring(fileName.lastIndexOf("."));
//				String tempName = System.currentTimeMillis()+".jpg";
//				filePath = session.getServletContext().getRealPath("/")+"/tempupload";
//				filePath = filePath.replaceAll("\\\\", "/");
//			
//				File dir  = new File(filePath);
//				if(!dir.exists()){
//					dir.mkdirs();
//				}
//				filePath = filePath+ "/" + tempName;
//				// 文件流复制
//				file = new File(filePath);
//				multipartFile.transferTo(file);
//	
//				// step 2:ftp
//				Map<String,File> uploadFileMap = new HashMap<String,File>();
//				uploadFileMap.put(tempName, file);
//				Map<String,File> failFile = SpringFTPUtil.ftpUpload(ftpChannelTraining, uploadFileMap, ftpServerPath);
//				
//				// step 3:response
//				if(null!=failFile && 0<failFile.size() ){
//					jsonObject.put("success", false);
//					logger.error("课程主图上传：调用ftp上传图片失败");
//					return jsonObject.toString();
//				}else{
//					String headPath = "http://"+
//										sysconfigProperties.getFtpServerIp()+"/pics"
//										;
//					jsonObject.put("success",true);
//					jsonObject.put("src",headPath+ ftpServerPath + tempName);
//					return jsonObject.toString();
//				}
//				
//			}else{
//				jsonObject.put("success", false);
//				logger.error("课程主图上传：上传的文件未空");
//				return jsonObject.toString();
//			}
//		
//		} catch (Exception ex) {
//			jsonObject.put("success", false);
//			logger.error("课程主图上传：上传失败",ex);
//			return jsonObject.toString();
//		}finally{
//			
//			if(null!=fis){
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if(null!=file){
//				file.delete();
//			}
//		}
//		
//	}
}
