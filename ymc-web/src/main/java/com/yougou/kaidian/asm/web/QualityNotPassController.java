package com.yougou.kaidian.asm.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.asm.dao.QualityMapper;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.asm.vo.QualityNotPassQueryVo;
import com.yougou.kaidian.asm.vo.QualityNotPassResultVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.kaidian.user.util.UserUtil;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.common.SaleStatusEnum;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.wms.wpi.returnqaproduct.service.IReturnQaProductDetailDomainService;
import com.yougou.wms.wpi.returnqaproduct.service.IReturnQaProductDomainService;

/**
 * 质检不通过后续流程控制类
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/quality")
public class QualityNotPassController {

    @Resource
    private IQualityService iQualityService;

    @Resource
    private ICommodityBaseApiService commodityBaseApiService;

    @Resource
    private IReturnQaProductDomainService returnQaProductDomainService;

    @Resource
    private IReturnQaProductDetailDomainService returnQaProductDetailDomainService;

    @Resource
    private QualityMapper qualityDao;

    @Resource
    private IAsmApi asmApiImpl;
    
    @Resource
    private IMerchantCenterOperationLogService operationLogService;
    
    Logger logger =  LoggerFactory.getLogger(QualityNotPassController.class);

    /**
     * 查询售后质检 creator mei.jf
     * 
     * @param model
     * @param vo
     * @return
     */
    @RequestMapping("/notPassList")
    public String qualityList(ModelMap model, QualityNotPassQueryVo vo, Query query, HttpServletRequest request) {
        query.setPageSize(30);

        if (StringUtils.isBlank(vo.getQaTimeStart())) {
            vo.setQaTimeStart((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(DateUtils.addMonths(new Date(), -3)));
        }

        if (StringUtils.isBlank(vo.getQaTimeEnd())) {
            vo.setQaTimeEnd((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        }
        if ("1".equals(vo.getTab()) || StringUtils.isEmpty(vo.getTab())) {
            vo.setTab("1");
            vo.setCsConfirmStatus("2");
        } else if ("2".equals(vo.getTab())) {
            vo.setCsConfirmStatus("1");
        } else if ("3".equals(vo.getTab())) {
            vo.setCsConfirmStatus("3");
        }
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        PageFinder<QualityNotPassResultVo> pageFinder = iQualityService.queryQualityNotPassListByVo(vo, query);// 已处理加密 add by LQ.
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("expressInfos", this.iQualityService.getExpressInfo());
        // 记录操作日志
        MerchantCenterOperationLog log = null;
        String menu = UserConstant.MENU_ZJBTG;
        String operationNotes = "查看列表";
        try {
        	log =  new MerchantCenterOperationLog(request,menu,operationNotes,"","",UserConstant.OPERATION_TYPE_READ);
			operationLogService.insertOperationLog(log);
		} catch (Exception e) {
			logger.error("菜单："+menu+" ，"+operationNotes+"时记录操作日志发生异常！");
		}
        
        return "/manage/asm/quality_notpass_list";
    }

    /**
     * 维修完成确认 creator mei.jf
     * 
     * @param model
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping("/repairComplate")
    public String repairComplate(ModelMap model, QualityNotPassQueryVo vo, Query query, HttpServletRequest request) {

        boolean isSuccesss = asmApiImpl.updateSaleApplyBillStatusByApplyNo(vo.getApplyNo(), SaleStatusEnum.SALE_REPAIR_COMPLETE);
        if (!isSuccesss) {
            throw new RuntimeException("call order dubbo interface update order applyNo status exception");
        }
        return returnQaProductDomainService.rewriteMaintenanceReturn(vo.getId(), YmcThreadLocalHolder.getMerchantCode()) && isSuccesss ? "success" : "failed";
    }

    /**
     * 退回顾客确认 creator mei.jf
     * 
     * @param model
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping("/returnGood")
    public String returnGood(ModelMap model, QualityNotPassQueryVo vo, Query query, HttpServletRequest request) {
        boolean isSuccesss = asmApiImpl.updateSaleApplyBillStatusByApplyNo(vo.getApplyNo(), SaleStatusEnum.SALE_REPAIR_RETURN);
        if (!isSuccesss) {
            throw new RuntimeException("call order dubbo interface update order applyNo status exception");
        }
        return returnQaProductDomainService.rewriteReturnExt(vo.getExpressCode(), vo.getLogisticsCode(), vo.getRetrunFee(), vo.getReturnIsDelivery(), vo.getReturnRemark(),
                YmcThreadLocalHolder.getMerchantCode(), vo.getId()) && isSuccesss ? "success" : "failed";
    }

    /**
     * 转正常质检确认 creator mei.jf
     * 
     * @param model
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping("/changeToQA")
    public String changeToQA(ModelMap model, QualityNotPassQueryVo vo, Query query, HttpServletRequest request) {
        return returnQaProductDomainService.updateExtStatusForQa(vo.getId(), YmcThreadLocalHolder.getMerchantCode()) ? "success" : "failed";
    }

    /**
     * 返回明细 creator mei.jf
     * 
     * @param model
     * @param vo
     * @return
     */
    @RequestMapping("/returnDetail")
    public String returnDetail(ModelMap model, QualityNotPassResultVo vo,Query query, HttpServletRequest request) {

    	
        // 获取退换货质检扩展表的信息
        List<Map<String, Object>> list = returnQaProductDomainService.getReturnQaExt(vo.getId());
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, Object> returnQaExt = list.get(0);
            // 获取快递公司名称
            Map<String, String> expresss = iQualityService.getExpressInfoMap();
            returnQaExt.put("return_logistics_name", expresss.get(returnQaExt.get("return_logistics_code")));
            model.addAttribute("returnQaExt", returnQaExt);
        }
        // 获取退换货质检明细信息
        List<Map<String, Object>> returnQaList = returnQaProductDetailDomainService.getReturnQaProductDetailsByOrderSubNoInfo(vo.getOrderSubNo());
        for (Map<String, Object> returnQaDetail : returnQaList) {
            if (returnQaDetail.get("id").equals(vo.getId())) {
                String product_no = returnQaDetail.get("product_no").toString();
                Commodity c = commodityBaseApiService.getCommodityByNo(StringUtils.substring(product_no, 0, product_no.length() - 3), false, true, false);
                if( null!=c && null!= c.getPicSmall() ){
                	returnQaDetail.put("picSmall", c.getPicSmall());
                }else{
                	 returnQaDetail.put("picSmall", "");
                }
                returnQaDetail.put("url", commodityBaseApiService.getFullCommodityPageUrl(StringUtils.substring(product_no, 0, product_no.length() - 3)));
               
                // 加密 . Amend by LQ
                String phone =  (String) returnQaDetail.get("consignee_phone");
                String address = (String) returnQaDetail.get("consignee_address");
                Date originalTime = (Date)returnQaDetail.get("apply_date");
                if( ( !StringUtils.isEmpty(vo.getMobilePhone()) && -1<vo.getMobilePhone().indexOf("*") )
                		|| UserUtil.checkEncryptOrNot(originalTime) ){//加密
                	
                		returnQaDetail.put("consignee_phone",UserUtil.encriptPhone( phone) );
	                	returnQaDetail.put("consignee_address",UserUtil.encriptAddress(address));
                }
                
                model.addAttribute("returnQaDetail", returnQaDetail);
                break;
            }
        }

        String changeOrderSubNo = qualityDao.queryChangeOrderSubNoByOldOrderSubNo(vo.getOrderSubNo());
        if (StringUtils.isNotBlank(changeOrderSubNo)) {
            model.addAttribute("changeOrderSubNo", changeOrderSubNo);
        }
        model.addAttribute("vo", vo);
        
        // 记录操作日志
        MerchantCenterOperationLog log = null;
        String menu = UserConstant.MENU_ZJBTG;
        String operationNotes = "查看详情";
        String orderNo = "";
        if( null!=vo&&null!=vo.getOrderSubNo() ){
        	orderNo = vo.getOrderSubNo();
        }
        try {
        	log =  new MerchantCenterOperationLog(request,menu,operationNotes,"",orderNo,UserConstant.OPERATION_TYPE_READ);
			operationLogService.insertOperationLog(log);
		} catch (Exception e) {
			logger.error("菜单："+menu+" ，"+operationNotes+"时记录操作日志发生异常！");
		}
        return "/manage/asm/quality_notpass_detail";
    }
}
