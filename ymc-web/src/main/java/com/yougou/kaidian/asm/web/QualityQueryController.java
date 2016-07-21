package com.yougou.kaidian.asm.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yougou.kaidian.asm.common.QualityVoExportExcel;
import com.yougou.kaidian.asm.dao.QualityMapper;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.asm.vo.AsmQcDetailVo;
import com.yougou.kaidian.asm.vo.QualityDetailVo;
import com.yougou.kaidian.asm.vo.QualityExportVo;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.FileFtpUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.model.OrderSubExpand;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.kaidian.user.util.UserUtil;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.model.asm.SaleApplyBill;
import com.yougou.ordercenter.model.asm.SaleCancelGoodsInfo;
import com.yougou.ordercenter.model.order.OrderConsigneeInfo;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.product.Product;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;

/**
 * 新质检查询控制类
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/qualityquery")
public class QualityQueryController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(QualityQueryController.class);
	    
    @Resource
    private IQualityService iQualityService;

    @Resource
    private IOrderForMerchantApi orderForMerchantApi;

    @Resource
    private ICommodityBaseApiService commodityBaseApiService;

    @Resource
    private IAsmApi asmApiImpl;
    @Resource
    private QualityMapper qualityDao;
    @Resource
    private IImageService imageService;
    @Resource
	private CommoditySettings commoditySettings;
    @Resource
    private IMerchantCenterOperationLogService operationLogService;
    
    /**
     * 查询售后质检 creator huang.qm
     * 
     * @param model
     * @param vo
     * @return
     */
    @RequestMapping("/qualityList")
    public String qualityList(ModelMap model, QualityQueryVo vo, Query query, HttpServletRequest request) {
        query.setPageSize(15);
        if (null != vo.getQualityType() && vo.getQualityType().equals("1")) {
            vo.setQualityType("拒收");
        } else if (null != vo.getQualityType() && vo.getQualityType().equals("2")) {
            vo.setQualityType("退换货");
        }
        if (null != vo.getStatusName() && vo.getStatusName().equals("3")) {
            vo.setStatusName("已作废");
        }
        if (StringUtils.isBlank(vo.getQaTimeStart())) {
            vo.setQaTimeStart((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(DateUtils.addMonths(new Date(), -3)));
        }

        if (StringUtils.isBlank(vo.getQaTimeEnd())) {
            vo.setQaTimeEnd((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        }
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        PageFinder<Map<String, Object>> pageFinder = iQualityService.queryQualityListByVo(vo, query);
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        this.addCss(model);
        model.addAttribute("expressInfos", this.iQualityService.getExpressInfo());
        
        // 记录操作日志
        MerchantCenterOperationLog log = null;
        String menu = UserConstant.MENU_ZJCX;
        String operationNotes = "查看列表";
        String orderNo = "";
        if( null!=vo&&null!=vo.getOrderSubNo() ){
        	orderNo = vo.getOrderSubNo();
        }
        try {
        	log =  new MerchantCenterOperationLog(request,menu,operationNotes,"",orderNo,UserConstant.OPERATION_TYPE_READ);
			operationLogService.insertOperationLog(log);
		} catch (Exception e) {
			logger.error("qualityList菜单：{},{}时记录操作日志发生异常！",menu,operationNotes);
		}
        return "/manage/asm/quality_query";
    }
    
    /**
     * 质检查询快递公司模糊查询下拉数据 creator zhang.f
     * 
     * @param model
     * @param vo
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/queryExpress")
    @ResponseBody
    public String queryExpress(QualityQueryVo vo,HttpServletRequest request) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
    	String expressName =  request.getParameter("expressName");//vo.getExpressName();
    	//expressName = new String(expressName.getBytes("ISO-8859-1"),"UTF-8");
    	//logger.error("queryExpress快递公司输入参数：----"+expressName);
    	if(expressName!=null){
    		expressName = expressName.toLowerCase();
    	}
    	
    	List<Map<String, Object>> expressInfos = this.iQualityService.getExpressInfo();
    	List<Map<String, Object>> expressInfo= new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> otherInfo= new ArrayList<Map<String, Object>>();
    	//List<String> expNames = new ArrayList<String>();
    	if(expressName !=null && !"".equals(expressName) && expressInfos!=null && expressInfos.size()>0){
    		for (Map<String, Object> map : expressInfos ){
    			String express_name = (String) map.get("express_name");
    			String express_frtpinyin = (String) map.get("express_frtpinyin");
    			express_frtpinyin=express_frtpinyin.toLowerCase(); //统一转小写比较
    			//中文或者首字母以输入参数开始
    			if((express_name!=null && express_name.startsWith(expressName)) || (express_frtpinyin!=null && express_frtpinyin.startsWith(expressName))){
    				expressInfo.add(map);
    				//expNames.add(express_name);
    				continue;
    			}  			
    			otherInfo.add(map);
    		}
    		
    		for (Map<String, Object> map : otherInfo ){
    			String express_name = (String) map.get("express_name");
    			String express_frtpinyin = (String) map.get("express_frtpinyin");
    			express_frtpinyin=express_frtpinyin.toLowerCase(); //统一转小写比较
    			//中文或者首字母包含输入参数
    			if((express_name!=null && express_name.indexOf(expressName)>-1) || (express_frtpinyin!=null && express_frtpinyin.indexOf(expressName)>-1)){
    				expressInfo.add(map);
    				//expNames.add(express_name);
    			}  			
    		}
    	}else{
    		expressInfo.addAll(expressInfos);
    	}	
    	JSONObject obj = new JSONObject();
    	obj.put("expressInfo", expressInfo);
    	//obj.put("expNames", expNames);    	
    	String jsonVal = JSONUtils.valueToString(obj);
    	//logger.error("queryExpress根据入参查询到的快递公司结果集：----"+jsonVal);
        return jsonVal;
    }
    
    /**
     * qualityExport:质检明细导出功能 
     * @author li.n1 
     * @return String
     * @since JDK 1.6
     */
    
    @ResponseBody
    @RequestMapping("/qualityExport")
    public String qualityExport(ModelMap model, QualityQueryVo vo, Query query, 
    		HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(vo.getQaTimeStart())) {
            vo.setQaTimeStart((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(DateUtils.addMonths(new Date(), -3)));
        }
        if (StringUtils.isBlank(vo.getQaTimeEnd())) {
            vo.setQaTimeEnd((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        }
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        //List<Map<String, Object>>  list = iQualityService.queryQualityAllListByVo(vo);
        List<Map<String, Object>>  list = iQualityService.batchQuery(vo,500);
        List<QualityExportVo> exportList = prepareData(list);
        Map<String,Object> map = exportToExcel(exportList,request,response);
        return JSONObject.fromObject(map).toString();
    }
    
    /** 
	 * prepareCreate:命名文档跟导出 
	 * @author li.n1  
	 * @since JDK 1.6 
	 */  
	private Map<String,Object> exportToExcel(List<QualityExportVo> exportVoList,
			HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String,Object> hashMap = new HashMap<String,Object>();
		String title = "质检结果";
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        try {
        	String dateStr = DateUtil2.getCurrentDateTimeToStr();
        	QualityVoExportExcel excel = new QualityVoExportExcel(exportVoList,this.getAbsoluteFilepath(merchantCode),"qualityQuery_"+dateStr+".xls", title);
        	excel.exportExcel();
        	File excelFile = new File(this.getAbsoluteFilepath(merchantCode)+"qualityQuery_"+dateStr+".xls");
        	boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+merchantCode);
        	hashMap.put("result", result);
        	hashMap.put("url",dateStr+".xls");		//FTP下载
        	//hashMap.put("url", commoditySettings.imageFtpServer+"/pics/merchantpics/exceltemp/"+merchantCode+"/qualityQuery_"+dateStr+".xls");
		}catch(Exception e){
			logger.error( MessageFormat.format("质检结果导出发生异常{0}", merchantCode), e);
		}
        return hashMap;
	}
	
	/**
	 * qualityDownload:FTP下载文件 
	 * @author li.n1 
	 * @param name
	 * @param request
	 * @param response 
	 * @since JDK 1.6
	 */
	@RequestMapping("/qualityDownload")
	public void qualityDownload(@RequestParam String name,
			HttpServletRequest request,HttpServletResponse response){
		OutputStream outputStream = null;
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try{
			response.reset();
			//response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}", encodeDownloadAttachmentFilename(request, "质检明细_"+name)));
			outputStream = response.getOutputStream();
			FileFtpUtil ftpUtil = new FileFtpUtil(commoditySettings.imageFtpServer,commoditySettings.imageFtpPort, 
					commoditySettings.imageFtpUsername, commoditySettings.imageFtpPassword);
			ftpUtil.login();
			ftpUtil.downRemoteFile("/merchantpics/exceltemp/"+merchantCode+"/qualityQuery_"+name,outputStream);
			outputStream.flush();
			ftpUtil.logout();
		}catch(Exception e){
			logger.error( MessageFormat.format("IO流关闭异常！商家编码：{0}", merchantCode), e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error( MessageFormat.format("IO流关闭异常！商家编码：{0}", merchantCode), e);
				}
			}
		}
	}
	
	/**
	 * 编码中文文件名称(解决下载文件弹出窗体中文乱码问题)
	 * 
	 * @param request
	 * @param filename
	 * @return String
	 * @throws Exception
	 */
	private String encodeDownloadAttachmentFilename(HttpServletRequest request, String filename) throws Exception {
		return StringUtils.indexOf(request.getHeader("User-Agent"), "MSIE") != -1 ? URLEncoder.encode(filename, "UTF-8") : new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	}

	/** 
	 * prepareData:准备导出的数据 
	 * @author li.n1 
	 * @param list 
	 * @since JDK 1.6 
	 */  
	@SuppressWarnings("unchecked")
	private List<QualityExportVo> prepareData(List<Map<String, Object>> list) {
		List<AsmQcDetailVo> asmQcDetailVoList = null;
		List<QualityExportVo> exportVoList = new ArrayList<QualityExportVo>();
		QualityExportVo exportVo = null;
		List<SaleApplyBill> applyBills = null;
		Product product = null;
		SaleApplyBill bill = null;
		for(Map<String,Object> map : list){
			asmQcDetailVoList = (List<AsmQcDetailVo>) map.get("asmQcDetail");
			//根据订单号查询退换货申请单信息//有些订单没有退换货申请单信息
			applyBills = asmApiImpl.querySaleApplyBillListByOrderSubNo((String) map.get("order_sub_no"));
			for(AsmQcDetailVo asmQcDetailVo : asmQcDetailVoList){	//售后质检明细
				exportVo = new QualityExportVo();
				product = commodityBaseApiService.getProductByNo(asmQcDetailVo.getProdNo(), true);
				if(product!=null){
					exportVo.setSupplierCode(product.getCommodity().getSupplierCode());
				}
				exportVo.setExpressCode(asmQcDetailVo.getExpressCode());
				exportVo.setExpressName(asmQcDetailVo.getExpressName());
				exportVo.setInsideCode(asmQcDetailVo.getInsideCode());
				exportVo.setOrderNo((String) map.get("order_sub_no"));
				exportVo.setProdName(asmQcDetailVo.getProdName());
				exportVo.setUserName((String) map.get("user_name"));
				//已质检的信息，有申请单
					for (Iterator<SaleApplyBill> it = applyBills.iterator();it.hasNext();) {	
						bill = it.next();
						if(bill.getApplyNo().equals(asmQcDetailVo.getApplyNo())){
							exportVo.setRemark(bill.getRemark());
							exportVo.setSaleType("QUIT_GOODS".equals(bill.getSaleType()) ? "退货" : "换货");
							it.remove();
							break;
						}
					}
				if(null==exportVo.getSaleType()){
					exportVo.setSaleType(asmQcDetailVo.getQualityType());
				}
				exportVo.setStatus(asmQcDetailVo.getStatus());
				//exportVo.setSupplierCode(asmQcDetailVo.getSupplierCode());
				exportVoList.add(exportVo);
			}
		}
		return exportVoList;
	}



	final static String ASM_RETURN_DETAIL = "/manage/asm/quality_detail";

    /**
     * 查询售后质检 creator mei.jf
     * 
     * @param model
     * @param vo
     * @return
     * @throws Exception
     */
    @RequestMapping("/returnDetail")
    public String returnDetail(ModelMap model, HttpServletRequest request, QualityDetailVo vo) throws Exception {
//        Map<String, Object> loginUser = SessionUtil.getUnionUser(request);

        List<AsmQcDetailVo> asmQcDetailList = qualityDao.queryAsmQcDetailsByOrderNo(vo.getOrderSubNo());
        String merchantCode =YmcThreadLocalHolder.getMerchantCode();
        OrderSub orderSub = null;
        if (StringUtils.isBlank(merchantCode)) {
            vo.setErrorMessage("请先登录!");
        } else if (null == asmQcDetailList || asmQcDetailList.size() == 0) {
            vo.setErrorMessage("找不到该质检信息！");
        } else if (null == (orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(vo.getOrderSubNo(), merchantCode))) {
            vo.setErrorMessage("找不到该质检订单信息！");
        } else {
            // 注入订单号、商家货品条码、单品页URL
            List<Map<String, Object>> orderDetails = new ArrayList<Map<String, Object>>();
            for (OrderDetail4sub orderDetail4sub : orderSub.getOrderDetail4subs()) {
                Product product = commodityBaseApiService.getProductByNo(orderDetail4sub.getProdNo(), true);
                // 返回的订单货品明细
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("third_party_code", product.getThirdPartyInsideCode());
                map.put("supplier_code", product.getCommodity().getSupplierCode());
                map.put("commodity_name", product.getCommodity().getCommodityName());
                map.put("commodityNum", orderDetail4sub.getCommodityNum());
                map.put("static_url", commodityBaseApiService.getFullCommodityPageUrl(product.getCommodity().getCommodityNo()));
                map.put("price", product.getCommodity().getSellPrice());
                map.put("productNo", product.getProductNo());
                map.put("picUrl", product.getCommodity().getPicSmall());
                map.put("apply_no", "-");
                map.put("create_time", "-");
                orderDetails.add(map);
            }
            if (CollectionUtils.isNotEmpty(orderDetails)) {
                model.addAttribute("orderDetails", orderDetails);
            }
            // 顾客回寄货品信息
            model.addAttribute("qadetails", asmQcDetailList);

            // 返回的订单货品仓库信息
            OrderSubExpand orderDetail = new OrderSubExpand();
            orderDetail.setOrderSubNo(vo.getOrderSubNo());
            String userName = "";
           
            
            // 加密 Add by LQ.
            Date originalTime = orderSub.getShipTime();
            String phone = "";
            if( null!=orderSub.getOrderConsigneeInfo() ){
            	OrderConsigneeInfo info = orderSub.getOrderConsigneeInfo();
            	userName = info.getUserName();
            	phone = info.getMobilePhone();
	            if( UserUtil.checkEncryptOrNot(originalTime) ){//加密
	            	phone = UserUtil.encriptPhone(phone);
	            }
            }
            
            orderDetail.setConsigneeMobile( phone );//
            orderDetail.setUserName( userName );
            orderDetail.setShipTime( originalTime );
            orderDetail.setExpressOrderId(orderSub.getExpressOrderId());
            orderDetail.setLogisticsName(orderSub.getLogisticsName());
            model.addAttribute("orderDetail", orderDetail);
            // 申请单相关信息,获取顾客退换货信息
            List<Map<String, String>> saleAfterDetails = new ArrayList<Map<String, String>>();
            logger.info("调用售后接口，根据订单号查询退换货申请单信息，传入订单号{}",vo.getOrderSubNo());
            List<SaleApplyBill> applyBills = asmApiImpl.querySaleApplyBillListByOrderSubNo(vo.getOrderSubNo());
            for (SaleApplyBill applyBill : applyBills) {
                for (SaleCancelGoodsInfo goodInfos : applyBill.getGoodInfos()) {
                    Map<String, String> good = new HashMap<String, String>();
                    // good.put("levelCode", goodInfos.getLevelCode());
                    good.put("prodCode", goodInfos.getProdCode());
                    good.put("commodityNum", goodInfos.getCommodityNum().toString());
                    good.put("saleType", "QUIT_GOODS".equals(applyBill.getSaleType()) ? "退货" : "换货");
                    // 退换货备注
                    good.put("remark", applyBill.getRemark());
                    saleAfterDetails.add(good);
                }
            }
            model.addAttribute("saleAfterDetails", saleAfterDetails);
        }
        model.addAttribute("vo", vo);
        
        // 记录操作日志
        MerchantCenterOperationLog log = null;
        String menu = UserConstant.MENU_ZJCX;
        String operationNotes = "查看详情";
        String orderNo = "";
        if( null!=vo&&null!=vo.getOrderSubNo() ){
        	orderNo = vo.getOrderSubNo();
        }
        try {
        	log =  new MerchantCenterOperationLog(request,menu,operationNotes,"",orderNo,UserConstant.OPERATION_TYPE_READ);
			operationLogService.insertOperationLog(log);
		} catch (Exception e) {
			logger.error("菜单：{},{}时记录操作日志发生异常！",menu,operationNotes);
		}
        return ASM_RETURN_DETAIL;
    }

    /**
     * 质检查询明细 creator meijunfeng create time 2013-10-9 上午11:38:32
     * 
     * @param model
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping("/qualityListDetail")
    public String qualityListDetail(ModelMap model, HttpServletRequest request, QualityQueryVo vo) throws Exception {
        List<Map<String, Object>> lists = iQualityService.queryQualityDetail(vo);
        String product_no = null;
        for (Map<String, Object> list : lists) {
            product_no = list.get("product_no").toString();
            Commodity c = commodityBaseApiService.getCommodityByNo(StringUtils.substring(product_no, 0, product_no.length() - 3), false, true, false);
            list.put("picSmall", c.getPicSmall());
            list.put("url", commodityBaseApiService.getFullCommodityPageUrl(StringUtils.substring(product_no, 0, product_no.length() - 3)));
        }
        return JSONArray.fromObject(lists).toString();
    }

    private void addCss(ModelMap model) {
        model.put("tagTab", "asm-qc");
    }
    
    /**
     * 获取文件在临时文件夹的绝对路径
     * 
     * @param merchantCode
     * @param fileName
     * @param suffix
     * @return
     */
	private String getAbsoluteFilepath(String merchantCode) {
		return new StringBuffer(commoditySettings.picDir)
		.append(File.separator)
		.append("exceltemp")
		.append(File.separator)
		.append(merchantCode)
		.append(File.separator)
		.toString();
		//return new StringBuffer("D:")
		//.append(File.separator)
		//.append("exceltemp")
		//.append(File.separator)
		//.append(merchantCode)
		//.append(File.separator)
		//.toString();
	}
    
}
