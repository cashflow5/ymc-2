package com.belle.yitiansystem.mctnotice.web.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.web.controller.BaseController;
import com.belle.other.model.vo.ResultMsg;
import com.belle.yitiansystem.mctnotice.model.pojo.MctNotice;
import com.belle.yitiansystem.mctnotice.model.vo.QueryMctNoticeVo;
import com.belle.yitiansystem.mctnotice.service.IMctNoticeService;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.ordercenter.common.DateUtil;

/**
 * 
 *@author lm
 * 
 * @version 创建时间：2014-3-30 下午05:49:43
 */
@Controller
@RequestMapping("/yitiansystem/notice")
public class NoticeController extends BaseController{

	@Resource
	private IMctNoticeService mctNoticeService;
	private Logger logger = Logger.getLogger(NoticeController.class);
	@Resource(name="sysconfigProperties")
    private SysconfigProperties settings;
	@Resource
	private MessageChannel ftpChannel;
    @RequestMapping("/notice_list")
    public ModelAndView noticeList(HttpServletRequest request,Map<String, Object> map,QueryMctNoticeVo queryMctNoticeVo,Query query) throws Exception{
        PageFinder<MctNotice> pageFinder=mctNoticeService.queryMctNoticeList(queryMctNoticeVo, query);
        map.put("vo", queryMctNoticeVo);
        map.put("pageFinder", pageFinder);
    	return new ModelAndView("yitiansystem/merchant/notice/notice_list",map);
    }
    
    @RequestMapping("/new_notice")
    public ModelAndView newNotice(HttpServletRequest request,Map<String, Object> map){
        return new ModelAndView("yitiansystem/merchant/notice/add_notice");
    }
    
    /*@ResponseBody
    @ExceptionHandler(Exception.class)  
	public String handleException(Exception ex,HttpServletRequest request) {   
		if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
			return getError("上传文件大小超过限制。");
		}
		return null;
	}*/
    
    @ResponseBody
    @RequestMapping(value="/uploadNoticePic",method=RequestMethod.POST)
    public String uploadNoticePic(MultipartHttpServletRequest request){
    	ResultMsg resultMsg = new ResultMsg();
    	try{
    		MultipartFile imgFile = request.getFile("imgFile");
    		//定义允许上传的文件扩展名
    		HashMap<String, String> extMap = new HashMap<String, String>();
    		extMap.put("image", "gif,jpg,jpeg,png,bmp");
    		//最大文件大小
    		//long maxSize = 20971520; //20M
    		if(!ServletFileUpload.isMultipartContent(request)){
    			return getError("请选择图片文件。");
    		}
    		//检查文件大小
    		//if(imgFile.getSize() > maxSize){
    		//	return getError("上传文件大小超过限制。");
    		//}
    		//检查扩展名
    		String fileName = imgFile.getOriginalFilename();
    		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    		if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
    			return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
    		}
    		resultMsg = uploadNoticePic2TemporarySpace(imgFile);
    	}catch(Exception ex){
    		logger.error(ex.getMessage(), ex);
            resultMsg.setSuccess(false);
            return JSONObject.fromObject(resultMsg).toString();
    	}
    	JSONObject obj = new JSONObject();
    	if(resultMsg.isSuccess()){
    		obj.put("error", 0);
    		obj.put("url", resultMsg.getReObj().toString());
    	}else{
    		obj.put("error", 1);
    		obj.put("message", "发生错误，上传失败！");
    	}
    	return JSONObject.fromObject(obj).toString();
    }
    
    private String getError(String message) {
    	JSONObject obj = new JSONObject();
    	obj.put("error", 1);
    	obj.put("message", message);
    	return JSONObject.fromObject(obj).toString();
    }
    
    public static String getExtname(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
	}
    
    /** 取得随机主文件名 */
   	public synchronized static String getRndFilename(){
   		return String.valueOf(System.currentTimeMillis());
   	}
   	
    private ResultMsg uploadNoticePic2TemporarySpace(MultipartFile multipartFile) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        // 处理图片文件名
        String picName = multipartFile.getOriginalFilename();
        picName = getRndFilename()+getExtname(picName);
        Date d = new Date();
        String temp = DateUtil.format(d, "yyyyMM");
        String domain = settings.imageAccessoryPreviewDomain;
        domain = domain.substring(0,domain.indexOf("/", domain.indexOf(":")+3)+1);
        resultMsg.setReObj(domain+"pics/merchantpics/notice/"+temp+"/"+picName);
        resultMsg.setReCode(picName);
        String innerFilePath = "/merchantpics/notice/"+temp+"/";
        /*// 固定写法
        FtpUtils ftpUtil = new FtpUtils();
        ftpUtil.connectServer("10.0.30.193", 21, "shop", "shop", settings.getImageFtpAccessoryNoticePic()+temp);
        boolean flag = ftpUtil.upload(multipartFile.getInputStream(), picName);
        ftpUtil.closeConnect();*/
        boolean flag = this.ftpUpload(multipartFile.getInputStream(), picName, innerFilePath);
        if (!flag) {
            logger.error(MessageFormat.format("upload file fail : {0}.", new Object[] { picName }));
        }
        resultMsg.setSuccess(flag);
        return resultMsg;
    }
    
    /**
     * 图片上传封装类
     * 
     * @param uploadFileMap
     * @param ftpServerPath
     * @return
     * @throws UnsupportedEncodingException
     */
    private boolean ftpUpload(InputStream inputStream,String ftpfilename, String ftpServerPath) throws Exception {
        if (StringUtils.isNotBlank(ftpServerPath) && ftpServerPath.startsWith("/"))
            ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
        Message<byte[]> message = null;
        message = MessageBuilder.withPayload(IOUtils.toByteArray(inputStream))
        		.setHeader("remote_dir", ftpServerPath)
                .setHeader("remote_filename", new String(ftpfilename.getBytes("UTF-8"), "ISO-8859-1")).build();
        return ftpChannel.send(message);
    }
    
	@RequestMapping("/add_notice")
    public String addNotice(HttpServletRequest request,ModelMap model,Map<String, Object> map,MctNotice mctNotice) throws Exception{
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	mctNotice.setAuthor(user == null ? null : user.getUsername());
        mctNotice.setIsRed((mctNotice.getIsRed()==null||"".equals(mctNotice.getIsRed()))?"0":mctNotice.getIsRed());
        mctNotice.setIsTop((mctNotice.getIsTop()==null||"".equals(mctNotice.getIsTop()))?"0":mctNotice.getIsTop());
    	mctNotice.setCreateTime(new Date());
    	mctNotice.setUpdateTime(new Date());
    	try {
    		mctNoticeService.saveMctNotice(mctNotice);
    		model.addAttribute("message", "添加公告成功!");
    		model.addAttribute("refreshflag", "1");
    		model.addAttribute("methodName","/yitiansystem/notice/notice_list.sc");
		} catch (Exception e) {
			logger.error("添加公告产生异常!",e);
        	model.addAttribute("refreshflag", "3");
        	model.addAttribute("message", "添加公告失败!");
		}
        return "yitiansystem/merchants/merchants_message";
    }
    
    @RequestMapping("/edit_notice")
    public ModelAndView editNotice(HttpServletRequest request,Map<String, Object> map,String id) throws Exception{
    	MctNotice mctNotice=mctNoticeService.getMctNoticeById(id);
    	map.put("mctNotice", mctNotice);
    	return new ModelAndView("yitiansystem/merchant/notice/edit_notice");
    }
    
    @RequestMapping("/update_notice")
    public String updateNotice(HttpServletRequest request,ModelMap model,Map<String, Object> map,MctNotice mctNotice) throws Exception{
        MctNotice mctNotice2=mctNoticeService.getMctNoticeById(mctNotice.getId());
        SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	mctNotice2.setAuthor(user == null ? null : user.getUsername());
        mctNotice2.setContent(mctNotice.getContent());
        mctNotice2.setIsRed((mctNotice.getIsRed()==null||"".equals(mctNotice.getIsRed()))?"0":mctNotice.getIsRed());
        mctNotice2.setIsTop((mctNotice.getIsTop()==null||"".equals(mctNotice.getIsTop()))?"0":mctNotice.getIsTop());
        mctNotice2.setMerchantType(mctNotice.getMerchantType());
        mctNotice2.setNoticeType(mctNotice.getNoticeType());
        mctNotice2.setTitle(mctNotice.getTitle());
        mctNotice2.setUpdateTime(new Date());
        
    	try {
    		mctNoticeService.updateMctNotice(mctNotice2);
    		model.addAttribute("message", "修改公告成功!");
    		model.addAttribute("refreshflag", "1");
    		model.addAttribute("methodName","/yitiansystem/notice/notice_list.sc");
		} catch (Exception e) {
			logger.error("修改公告产生异常!",e);
        	model.addAttribute("refreshflag", "3");
        	model.addAttribute("message", "修改公告失败!");
		}
        return "yitiansystem/merchants/merchants_message";
    }
    
    @ResponseBody
    @RequestMapping("/del_notice")
    public String delNotice(HttpServletRequest request,Map<String, Object> map,String id) throws Exception{
    	map.clear();
    	try {
    		mctNoticeService.deleteMctNotice(id);
    		map.put("success", "1");
		} catch (Exception e) {
			logger.error("删除公告产生异常!",e);
			map.put("success", "0");
		}
    	return JSONObject.fromObject(map).toString();
    }
    
    @ResponseBody
    @RequestMapping("/set_top")
    public String setNoticeTop(HttpServletRequest request,Map<String, Object> map,String id,String is_top) throws Exception{
    	map.clear();
    	try {
    		mctNoticeService.updateMctNoticeTop(id, is_top);
    		map.put("success", "1");
		} catch (Exception e) {
			logger.error("删除公告产生异常!",e);
			map.put("success", "0");
		}
    	return JSONObject.fromObject(map).toString();
    }
}
