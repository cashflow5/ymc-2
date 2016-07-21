package com.yougou.kaidian.framework.taskjob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.bi.service.IReportManagementSurveyService;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.taskjob.job.DeleteOrderYGDataJob;
import com.yougou.kaidian.framework.taskjob.service.IImgTaskJobService;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.training.service.ITrainingService;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.ordercenter.vo.asm.QueryTraceSaleVo;
import com.yougou.ordercenter.vo.asm.TraceSaleQueryResult;

@Controller
@RequestMapping("/httptaskjob")
public class HttpTaskJobControll {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpTaskJobControll.class);
	@Resource
	private IImgTaskJobService imgTaskJobService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private CommoditySettings settings;
	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private MerchantOrderMapper merchantOrderMapper;
	@Resource
	private ICommodityService commodityService;
	@Resource
	private ITrainingService trainingService;
	@Resource
	private IReportManagementSurveyService reportManagementSurveyService;
	@Resource
    private IAsmApi asmApiImpl;
	@Resource
	private IMerchantCenterOperationLogService operationLogService;
	
	/**
	 * 删除临时文件夹图片
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET,value="/deltemppicfile")
	public void deleteTemporaryImg(){
		logger.warn(dateFormat.format(new Date())+":接收到执行删除临时图片任务");
		boolean isdeleted=imgTaskJobService.deleteFiles(settings.picDir, null, 7, false);
		if(!isdeleted) {
			logger.error(dateFormat.format(new Date())+":执行删除临时图片任务失败");
		}
		
		try {
			//清理图片处理缓存
			List<Object> masterMessages = this.redisTemplate.opsForHash().values(CacheConstant.C_IMAGE_MASTER_JMS_KEY);
			List<Object> batchMessages = this.redisTemplate.opsForHash().values(CacheConstant.C_IMAGE_BATCH_JMS_KEY);
			
			Date curentDate = new Date();
			if (CollectionUtils.isNotEmpty(masterMessages)) {
				for (Object object : batchMessages) {
					Image4SingleCommodityMessage _message = (Image4SingleCommodityMessage) object;
					if (null == _message.getCreateTime() || DateUtil.addDay2Date(7, _message.getCreateTime()).getTime() > curentDate.getTime()) {
						this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_MASTER_JMS_KEY, _message.getCommodityNo());
					}
				}
			}
			
			if (CollectionUtils.isNotEmpty(batchMessages)) {
				for (Object object : batchMessages) {
					Image4BatchUploadMessage _message = (Image4BatchUploadMessage) object;
					if (null == _message.getCreateTime() || DateUtil.addDay2Date(7, _message.getCreateTime()).getTime() > curentDate.getTime()) {
						this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_BATCH_JMS_KEY, _message.getCommodityNo());
					}
				}
			}
			logger.warn("清理图片缓存成功!");
		} catch (Exception e) {
			logger.error("清理图片处理缓存异常。", e);
		}
		
	}
	
	/**
	 * 删除非招商订单
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/delygorder")
	public void deleteOrderYGData(HttpServletRequest request) {
		String clientIP=this.getHttpRequestClientIp(request);
		boolean ipcheck=clientIP!=null&&(clientIP.indexOf("10.10.10")>-1||clientIP.indexOf("10.0.20")>-1);
		if(ipcheck&&Constant.FLAT_DELETE_YGORDER==0){
			Constant.FLAT_DELETE_YGORDER=1;
			int count=imgTaskJobService.getNotMerchantOrderCount();
			logger.warn("本次查询出来需要删除的优购订单数据:"+count);
			int pagesize=1000;
			taskExecutor.execute(new DeleteOrderYGDataJob(merchantOrderMapper,count,0,pagesize)); 
		}else{
			logger.warn("上一次删除非商家订单数据任务未结束或者有非法Ip执行该任务!");
		}
	}
	
	/**
	 * 统计每天jms的切图状况
	 * @param request
	 * @throws MessagingException 
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/imgJmsCount")
	public void imgJmsCount(HttpServletRequest request) throws MessagingException {
		String clientIP=this.getHttpRequestClientIp(request);
		boolean ipcheck=clientIP!=null&&(clientIP.indexOf("10.10.10")>-1||clientIP.indexOf("10.0.20")>-1);
		if(ipcheck){
			imgTaskJobService.ImgJmsCount(clientIP);
		}else{
			logger.warn("请求imgJmsCount非法IP："+clientIP);
		}
	}
	
	/**
	 * 获取请求客户端IP
	 * 
	 * @return String
	 */
	protected String getHttpRequestClientIp(HttpServletRequest request) {
			String clientIp = request.getHeader("X-Forwarded-For");
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = request.getHeader("Proxy-Client-Ip");
			}
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = request.getHeader("WL-Proxy-Client-Ip");
			}
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = request.getRemoteAddr();
			}
			if (StringUtils.isNotBlank(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
				String[] clientIps = StringUtils.split(clientIp, ",");
				clientIp = clientIps.length > 0 ? clientIps[0] : clientIp;
			}
		return StringUtils.trimToNull(clientIp);
	}
	
	/***
	 * 30天内，没有修改的商品，自动下架
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/commodityAutoOffShelves")
	public String commodityAutoOffShelves(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String clientIP=this.getHttpRequestClientIp(request);
		boolean ipcheck = clientIP!=null&&(clientIP.indexOf("10.10.10")>-1||clientIP.indexOf("10.0.20")>-1);
		if(ipcheck){
			try{
				commodityService.commodityAutoOffShelves();
				jsonObject.put("resultCode",ResultCode.SUCCESS.getCode());
				jsonObject.put("msg","操作成功");
			}catch(Exception e){
				jsonObject.put("resultCode",ResultCode.ERROR.getCode());
				jsonObject.put("msg","系统异常");
				logger.error(e.getMessage(),e);
			}
		}else{
			logger.warn("请求imgJmsCount非法IP："+clientIP);
			jsonObject.put("resultCode",ResultCode.ERROR.getCode());
			jsonObject.put("msg","请求imgJmsCount非法IP："+clientIP);
		}
		return jsonObject.toString();
	}
	
	/***
	 * 60天内，没有修改的商品，自动删除到回收站
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteCommodity2RecycleAuto")
	public String deleteCommodity2RecycleAuto(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String clientIP=this.getHttpRequestClientIp(request);
		boolean ipcheck=clientIP!=null&&(clientIP.indexOf("10.10.10")>-1||clientIP.indexOf("10.0.20")>-1);
		if(ipcheck){
			try{
				commodityService.deleteCommodity2RecycleAuto();
				jsonObject.put("resultCode",ResultCode.SUCCESS.getCode());
				jsonObject.put("msg","操作成功");
			}catch(Exception e){
				jsonObject.put("resultCode",ResultCode.ERROR.getCode());
				jsonObject.put("msg","系统异常");
				logger.error(e.getMessage(),e);
			}
		}else{
			logger.warn("请求imgJmsCount非法IP："+clientIP);
			jsonObject.put("resultCode",ResultCode.ERROR.getCode());
			jsonObject.put("msg","请求imgJmsCount非法IP："+clientIP);
		}
		return jsonObject.toString();
	}
	
	/***
	 * 培训中心，课程学习人数，每小时持久化一次到数据库
	 * @param request
	 * @return null
	 */
	@ResponseBody
	@RequestMapping("/saveTrainingTotalClick")
	public void saveTrainingTotalClick(HttpServletRequest request){
		logger.info("saveTrainingTotalClick job .............");
		HashOperations<String, Object, Object> opt = redisTemplate.opsForHash();
		
		if(opt != null ){
			Map<Object, Object> map = opt.entries(CacheConstant.C_TRIANING_TOTAL_CLICK_KEY);
			if(map!=null && map.size()>0){
				Set set = map.keySet();
				Iterator it = set.iterator();
				Map params = null;
				while(it.hasNext()){
					Object key = it.next();
					Object value = map.get(key);
					params = new HashMap();
					params.put("id", key);
					params.put("total_click", value);
					trainingService.updateTotalClick(params);
		
					logger.info("key==="+key+"====;value====="+value);
				}
			}
		}
	}
	
	/***
	 * 数据报表，统计和更新商品分析表Analysis 支付件数，支付金额
	 * @param request
	 * @return null
	 */
	@ResponseBody
	@RequestMapping("/updateAnalysisCommodityNumAndAmt")
	public void updateAnalysisCommodityNumAndAmt(HttpServletRequest request){
		logger.info("updateAnalysisCommodityNumAndAmt job .............start");
		try{
		reportManagementSurveyService.updateAnalysisCommodityNumAndAmt();
		}catch (Exception e) {
			logger.error("数据报表，统计和更新商品分析表Analysis 支付件数，支付金额JOB异常！",e);
		}
		logger.info("updateAnalysisCommodityNumAndAmt job .............end");
	}
	
	/***
	 * 赔付管理：3天内未处理工单自动同意赔付 （5分钟一次）(刨去周末)
	 * @param request
	 * @return null
	 */
	@ResponseBody
	@RequestMapping("/compensateAutoArrove")
	public void compensateAutoArrove(HttpServletRequest request){
		logger.info("CompensateAutoArrove job .............start.");
		if( !DateUtil2.isWeekDay( new Date() ) ){
			try{
				QueryTraceSaleVo vo = new QueryTraceSaleVo();
				vo.setTraceStatus( UserConstant.TRACE_STATUS_NEW );
				// 3天前的时间
				String edgeDate = DateUtil2.getEdgeDateTimeFromNow( UserConstant.AUTO_REPLY_DAYS );
				com.yougou.ordercenter.common.Query query = new com.yougou.ordercenter.common.Query();
				query.setPage(1);
				query.setPageSize(1000);
				vo.setEndTime(edgeDate);
				PageFinder<TraceSaleQueryResult> data = asmApiImpl.getCompensateTraceSaleList( vo, query );
				if( null!=data && data.getRowCount()>0 ){
					List<TraceSaleQueryResult> list = data.getData();
					List<String> ids = new ArrayList<String>();
					for(TraceSaleQueryResult result :list){
						ids.add( result.getId());
					}
					
					if(ids.size()>0){
						asmApiImpl.batchUpdateStatus(ids);
					}
				}
			}catch (Exception e) {
				logger.error("调用订单接口执行工单自动同意赔付JOB异常！");
			}
		}
		logger.info("CompensateAutoArrove job .............end.");
	}
	
	/**
	 * cleanLoginLogData:清理登录日志中的归属地信息，以及对未解析的归属地再次查询一遍
	 * @author li.n1 
	 * @param request 
	 * @since JDK 1.6 
	 * @date 2015-10-14 上午10:39:24
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/cleanLoginLog")
	public void cleanLoginLogData(HttpServletRequest request,Integer pageSize) {
		String clientIP=this.getHttpRequestClientIp(request);
		boolean ipcheck = clientIP!=null&&(clientIP.indexOf("10.10.10")>-1||clientIP.indexOf("10.0.20")>-1);
		//ipcheck = true;
		if(ipcheck){
			try{
				pageSize = (Integer)ObjectUtils.defaultIfNull(pageSize, 100);
				if(operationLogService.cleanLoginLog(pageSize)){
					logger.warn("更新归属地信息成功");
				}else{
					logger.warn("更新归属地信息成功");
				}
			}catch(Exception e){
				logger.warn("清理归属地信息成功",e);
			}
		}else{
			logger.warn("请求cleanLoginLogData非法IP："+clientIP);
		}
	}
	
	/***
	 * 数据报表,首页经营概况指标，统计所有商家昨日经营概况指标并插入至tbl_merchant_report_suppler_survey_index表
	 * 首页经营概况指标汇总，每日指标折线图，都从此表取数
	 * @param request
	 * @return null
	 */
	@ResponseBody
	@RequestMapping("/setYesterdaySurveyIndex")
	public void setYesterdaySurveyIndex(HttpServletRequest request){
		logger.info("setYesterdaySurveyIndex job .............start");
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			if(!StringUtils.isBlank(startDateStr) && !StringUtils.isBlank(endDateStr)){
				Date startDate = com.yougou.tools.common.utils.DateUtil.parseDate(startDateStr, "yyyy-MM-dd");
				Date endDate = com.yougou.tools.common.utils.DateUtil.parseDate(endDateStr, "yyyy-MM-dd");
				params.put("startDate", startDate);
				params.put("endDate", endDate);
			}
			reportManagementSurveyService.insertSupplierServeyIndex(params);
		}catch (Exception e) {
			logger.error("数据报表，统计所有商家昨日经营概况指标JOB异常！",e);
		}
		logger.info("setYesterdaySurveyIndex job .............end");
	}
	
	/***
	 * 数据报表,首页经营概况指标，统计所有商家昨日新上架商品数（tbl_merchant_report_suppler_survey_index表 newSaleCommodityNum 字段）
	 * @author zhangfeng
	 * @param request
	 * @return null
	 */
	@ResponseBody
	@RequestMapping("/updateNewSaleCommodityNum")
	public void updateNewSaleCommodityNum(HttpServletRequest request){
		logger.info("updateNewSaleCommodityNum job .............start");
		try{			
			reportManagementSurveyService.updateNewSaleCommodityNum();
		}catch (Exception e) {
			logger.error("数据报表，首页经营概况指标统计商家昨日新上架商品数JOB异常！",e);
		}
		logger.info("updateNewSaleCommodityNum job .............end");
	}
}
