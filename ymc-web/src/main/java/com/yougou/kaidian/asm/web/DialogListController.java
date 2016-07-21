package com.yougou.kaidian.asm.web;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yougou.api.IWorkOrderApi;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.model.MerchantVo;
import com.yougou.model.ProcessLogVo;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.Query;
import com.yougou.ordercenter.model.asm.Problem;
import com.yougou.ordercenter.model.order.OrderSaleTrace;
import com.yougou.ordercenter.model.order.OrderSaleTraceProc;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.asm.QueryTraceSaleVo;
import com.yougou.ordercenter.vo.asm.TraceSaleQueryResult;

/**
 * 工单查询与处理控制类
 * 
 * @author huang.tao
 * 
 */
@Controller
@RequestMapping("/dialoglist")
public class DialogListController {

    private final static Logger logger = LoggerFactory.getLogger(DialogListController.class);

    @Resource
    private IAsmApi asmApiImpl;
    @Resource
    private IOrderForMerchantApi orderForMerchantApi;
    @Resource
    private IWorkOrderApi workOrderApi;
    /**
     * 工单列表查询 creator mei.jf
     * 
     * @param model
     * @param vo
     * @param Query
     * @param request
     * @return
     */
    @RequestMapping("/query")
    public String qualityList(ModelMap model, QueryTraceSaleVo vo, Query query, HttpServletRequest request) {
        query.setPageSize(20);
        // 去掉OrderSubNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderSubNo())) {
            vo.setOrderSubNo(StringUtils.deleteWhitespace(vo.getOrderSubNo()));
        }
        // 去掉OrderSubNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderTraceNo())) {
            vo.setOrderTraceNo(StringUtils.deleteWhitespace(vo.getOrderTraceNo()));
        }
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        com.yougou.ordercenter.common.PageFinder<TraceSaleQueryResult> pageFinder = null;
        List<Problem> problem_list = null;
        try {
            // 默认设置查询起止时间
            if (StringUtils.isEmpty(vo.getStartTime())) {
                vo.setStartTime(new java.text.SimpleDateFormat("yyyy-MM-dd")
                        .format(DateUtils.addDays(new Date(), -30)));
            }
            if (StringUtils.isEmpty(vo.getEndTime())) {
                vo.setEndTime(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            }
            // 查询问题类型的键值对
            problem_list = asmApiImpl.getMerchantProblemList();
            logger.info("调用商家工单下级工单类型接口-返回结果共{}条 " , problem_list==null?"":problem_list.size());
            logger.info("调用查询工单列表接口-传入条件{}", vo.toString());
            pageFinder = asmApiImpl.getTraceSaleQueryResults(vo, query);
            logger.info("调用查询工单列表接口-返回结果共{}条 " , pageFinder.getData().size());
        } catch (Exception e) {
        	 logger.error( MessageFormat.format("qualityList查询工单列表时异常.merchantCode:{0},异常信息:", vo.getMerchantCode()),e);
        }
        model.addAttribute("problem", problem_list);
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        return "/manage/asm/dialog_list";
    }

    /**
     * 工单详情查询 creator meijunfeng create time 2013-10-9 上午11:38:32
     * 
     * @param model
     * @param vo
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail")
	public String detail(ModelMap model, OrderSaleTrace vo, Query query, HttpServletRequest request) throws Exception {
		query.setPageSize(6);
		// 获取商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if (StringUtils.isBlank(merchantCode)) {
			throw new Exception("商家编号不能为空！"); 
		}
		if (StringUtils.isBlank(vo.getId())) {
			return qualityList(model, new QueryTraceSaleVo(), query, request);
		}
		// 获取当前工单号的所有list回复
		OrderSaleTrace orderSaleTrace = asmApiImpl.getMerchantOrderSaleTraceByOrderTraceId(vo.getId(), merchantCode);
		// 首次进来直接进最后一页
		if (request.getMethod().equals("GET")) {
			query.setPage((orderSaleTrace.getOrderSaleTraceProcs().size() - 1) / query.getPageSize() + 1);
		}
		logger.info("调用根据子订单号和商家编码获取订单详情信息接口-传入条件 订单编号:{}商家编码:{}", orderSaleTrace.getOrderSubNo(), merchantCode);
		//获取订单状态名称
		OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSaleTrace.getOrderSubNo(), merchantCode);
		model.addAttribute("orderSaleTrace", orderSaleTrace);
		model.addAttribute("orderBaseStatusName", orderSub.getOrderStatusName());
		model.addAttribute("pageFinder", getlistOnPage(query, orderSaleTrace.getOrderSaleTraceProcs()));
		return "/manage/asm/dialog_detail";
	}

    /**
     * 保存商家回复
     * 
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/save")
    public String save(ModelMap model, Query query, OrderSaleTraceProc vo, HttpServletRequest request) throws Exception {
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        String merchantName = YmcThreadLocalHolder.getMerchantName();
        String loginname = YmcThreadLocalHolder.getOperater();
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("请先登录！商家编号不能为空！");
        }
        if (StringUtils.isBlank(merchantName)) {
            throw new Exception("商家名称不能为空！");
        }
        // 添加商家评论
        return Boolean.toString(asmApiImpl.addOrderSaleTraceProcForMerchant(vo.getId(),
                vo.getProcessRemark(), new Date(), merchantCode, loginname, 2));
    }

    /**
     * 根据返回的数据写分页
     * 
     * @return 返回当前页的数据
     */
    private PageFinder<OrderSaleTraceProc> getlistOnPage(Query query, List<OrderSaleTraceProc> OrderSaleTraceProcs) {
        List<OrderSaleTraceProc> list = new ArrayList<OrderSaleTraceProc>();
        int from = ((query.getPage() == 0 ? 1 : query.getPage()) - 1) * query.getPageSize();
        int to = Math.min(query.getPage() * query.getPageSize(), OrderSaleTraceProcs.size());
        for (int i = from; i < to; i++) {
            list.add(OrderSaleTraceProcs.get(i));
        }
        return new PageFinder<OrderSaleTraceProc>(query.getPage(), query.getPageSize(), OrderSaleTraceProcs.size(),
                list);
    }
    
    /**
     * 工单列表查询 
     * 
     * @param model
     * @param vo
     * @param Query
     * @param request
     * @return
     */
    @RequestMapping("/queryTodo")
    public String qualityTodoList(ModelMap model, MerchantVo vo,  Query query) throws Exception{
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	if (StringUtils.isBlank(merchantCode)) {
    		logger.error("商家编码:{}-查询工单列表时异常.", "商家编号为空");
			throw new Exception("商家编号不能为空！"); 
		}
    	com.yougou.utils.Query queryVO = new com.yougou.utils.Query();
		queryVO.setPageNum(query.getPage()+"");
		queryVO.setPageSize("20");
        PageFinder<MerchantVo> pageFinder = null;
        List<String> workOrderNames = null;
        PageInfo<MerchantVo> pageInfo = null;
        try {
        	MerchantVo search = initParams(vo);
            // 查询问题类型
            workOrderNames = workOrderApi.queryWorkOrderName("MMS");
            logger.info("商家编码:{}-调用工单系统接口查询工单类型列表，返回结果:{}", merchantCode, workOrderNames);
            
            logger.info("商家编码:{}-调用工单系统接口查询工单列表，入参:MerchantVo:{},Query:{}", merchantCode, search, query);
            if(!CommodityConstant.WORK_ORDER_TYPE_SJ.equals(vo.getWorkOrderStatus())){
            	pageInfo = workOrderApi.queryAllMerchantOrderList(search, queryVO);
            }else{
            	pageInfo = workOrderApi.queryMerchantOrderList(search, queryVO);
            }
            logger.info("商家编码:{}-调用工单系统接口查询工单列表，返回结果:{}", merchantCode, pageInfo);
            List<MerchantVo> list = pageInfo.getList();
            for(MerchantVo merchant : list){
            	setHoursFromCreateTime(merchant);
            }
            pageFinder = new PageFinder<MerchantVo>(pageInfo.getPageNum(),pageInfo.getPageSize(),Long.valueOf(pageInfo.getTotal()).intValue(),list);
        } catch (Exception e) {
        	 logger.error("商家编码:{}-查询工单列表时异常.", merchantCode, e);
        	 throw new Exception("查询工单列表时异常");
        }
        model.addAttribute("workOrderNames", workOrderNames);
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        return "/manage/asm/dialog_work_list";
    }

    /**
     * 设置未处理时长
     * @param merchant
     */
	private void setHoursFromCreateTime(MerchantVo merchant) {
		if(merchant.getWorkOrderCreateTime() != null){
			long miniSeconds = System.currentTimeMillis() - merchant.getWorkOrderCreateTime().getTime();
			BigDecimal hours = new BigDecimal(miniSeconds).divide(new BigDecimal(3600*1000),2,BigDecimal.ROUND_HALF_UP);
			merchant.setDuration(String.valueOf(hours));
		}else{
			merchant.setDuration("0");
		}
	}

    /**
     * 初始化参数
     * @param vo
     * @return
     */
	private MerchantVo initParams(MerchantVo vo) {
		MerchantVo search = new MerchantVo();
		// 默认设置查询起止时间
		if (StringUtils.isEmpty(vo.getStartTime())) {
			vo.setStartTime(new SimpleDateFormat("yyyy-MM-dd")
		            .format(DateUtils.addDays(new Date(), -30)));
		}
		search.setStartTime(vo.getStartTime() + " 23:59:59");
		if (StringUtils.isEmpty(vo.getEndTime())) {
			vo.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		search.setEndTime(vo.getEndTime() + " 23:59:59");
		if(StringUtils.isBlank(vo.getWorkOrderStatus())){
			vo.setWorkOrderStatus(CommodityConstant.WORK_ORDER_TYPE_SJ);
		}
		search.setWorkOrderStatus(vo.getWorkOrderStatus());
		if(CommodityConstant.WORK_ORDER_TYPE_ALL.equals(vo.getWorkOrderStatus())){
			search.setWorkOrderStatus(null);
		}
		search.setEmergencyLevel(vo.getEmergencyLevel());
		search.setOrderNo(vo.getOrderNo());
		search.setWorkOrderNo(vo.getWorkOrderNo());
		search.setWorkOrderName(vo.getWorkOrderName());
		search.setWorkOrderSource("MMS");
		search.setMerchatcode(YmcThreadLocalHolder.getMerchantCode());
		return search;
	}
    
	/**
	 * 工单详情页面
	 * @param model
	 * @param vo
	 * @param query
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/detailTodo")
	public String detailTodo(ModelMap model, MerchantVo vo, Query query) throws Exception {
    	
		com.yougou.utils.Query queryVO = new com.yougou.utils.Query();
		queryVO.setPageNum(query.getPage()+"");
		queryVO.setPageSize("6");
		// 获取商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if (StringUtils.isBlank(merchantCode)) {
			logger.error("商家编码:{}-查询工单详情时异常.", "商家编号为空");
			throw new Exception("商家编号不能为空！"); 
		}
		logger.info("商家编码:{}-调用工单系统接口查询工单详情，入参 workOrderNo:{},workOrderStatus:{}",merchantCode,vo.getWorkOrderNo(),vo.getWorkOrderStatus());
		MerchantVo detail = workOrderApi.queryMerchantDetail(vo.getWorkOrderNo(),vo.getWorkOrderStatus());
		logger.info("商家编码:{}-调用工单系统接口查询工单详情，返回结果:{}",merchantCode,detail);
		if(detail != null){
			setHoursFromCreateTime(detail);
			detail.setLastDealTime(vo.getLastDealTime());
		}else{
			logger.error("商家编码:{}-查询工单详情异常，未找到工单详情.", merchantCode);
			throw new Exception("未找到工单详情");
		}
		// 获取当前工单号的所有list回复
		ProcessLogVo pv=new ProcessLogVo();
		pv.setInstanceId(detail.getProcessInstanceId());
		pv.setDealSuggestion("notNull");
		PageInfo<ProcessLogVo> pageInfo = null;
		pageInfo = workOrderApi.queryApproveHistory(queryVO,pv);
		PageFinder<ProcessLogVo> pageFinder = null;
		pageFinder = new PageFinder<ProcessLogVo>(pageInfo.getPageNum(),pageInfo.getPageSize(),Long.valueOf(pageInfo.getTotal()).intValue(),pageInfo.getList());
		
		//获取订单状态名称
		logger.info("商家编码:{}-调用订单接口查询订单信息,入参：OrderNo:{}", merchantCode, detail.getOrderNo());
		OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(detail.getOrderNo(), merchantCode);
		logger.info("商家编码:{}-调用订单接口查询订单信息,返回结果：OrderSub:{}", merchantCode, orderSub);
		model.addAttribute("merchantVO", detail);
		model.addAttribute("orderBaseStatusName", orderSub != null?orderSub.getOrderStatusName():"");
		model.addAttribute("pageFinder", pageFinder);
		return "/manage/asm/dialog_work_detail";
	}

    /**
     * 处理工单
     * @param model
     * @param vo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveTodo")
    public String saveTodo(ModelMap model, MerchantVo vo) throws Exception {
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        String loginname = YmcThreadLocalHolder.getOperater();
        if (StringUtils.isBlank(merchantCode)) {
        	logger.error("商家编码:{}-查询工单详情时异常.", "商家编号为空");
            throw new Exception("请先登录！商家编号不能为空！");
        }
        vo.setMerchatcode(merchantCode);
        vo.setUserName(loginname);
        // 添加商家评论
        try{
        	logger.info("商家编码:{}-调用工单系统接口处理待办任务-入参:MerchantVo:{}", merchantCode, vo);
        	String result = workOrderApi.dealWithWorkOrderForMerchant(vo);
        	if(!CommodityConstant.WORK_ORDER_TYPE_KF.equals(result)){
        		throw new Exception("回复工单出现异常");
        	}
        	logger.info("商家编码:{}-调用工单系统接口处理待办任务-返回结果:{}", merchantCode, result);
        	return result;
        }catch(Exception e){
        	logger.error("调用工单系统接口处理待办任务出现异常,输入参数:{}",vo , e);
        	return "回复工单出现异常";
        }
    }
    
}
