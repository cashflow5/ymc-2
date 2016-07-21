package com.belle.yitiansystem.merchant.web.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.other.model.vo.ResultMsg;
import com.belle.yitiansystem.asm.service.IAsmService;
import com.belle.yitiansystem.merchant.model.vo.QueryAbnormalSaleApplyVoLocal;
import com.belle.yitiansystem.merchant.service.IOrder4MerchantLocalService;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
//import com.yougou.cms.api.ICMSApi;
import com.yougou.merchant.api.order.vo.QueryAbnormalSaleApplyVo;
import com.yougou.merchant.api.supplier.service.IBrandCatApi;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.merchant.api.supplier.vo.BrandVo;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderApi;
import com.yougou.ordercenter.common.DateUtil;
import com.yougou.ordercenter.common.RespResultEnum;
import com.yougou.ordercenter.common.RespReuslt;
import com.yougou.ordercenter.common.SaleStatusEnum;
import com.yougou.ordercenter.model.asm.AbnoramalSaleApplyBill;
import com.yougou.ordercenter.model.asm.SaleApplyBill;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderLog;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;

/**
 * 售后质检
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/yitiansystem/merchants/aftersale")
public class AfterSaleController {

    private final String BASE_PATH = "yitiansystem/merchants/aftersale/";

    private static final Logger logger = Logger.getLogger(AfterSaleController.class);

    @Autowired
    private SysconfigProperties sysconfigproperties;

    @Resource
    private IAsmApi asmApiImpl;
    @Resource
    private IOrderApi orderApi;
    @Resource
    private MessageChannel ftpChannel;

    @Resource
    private SysconfigProperties commoditySettings;
    @Resource
    private IMerchantsApi iMerchantsApi;
    
    @Resource
    private MessageChannel toRemoveChannel;
    
    @Resource
    private IAsmService iAsmService;
    
    @Resource
	private IBrandCatApi brandcatApi;

	@Resource
	private IOrder4MerchantLocalService order4MerchantLocalService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
    
    /**
     * 异常售后审核列表
     * 
     * @param modelMap
     * @param id
     * @param isShipmentDay
     * @return
     */
	@RequestMapping("abnormal_after_sale")
	public String abnormal_after_sale(ModelMap model, QueryAbnormalSaleApplyVoLocal vo, Query query, HttpServletRequest request) {

		query.setPageSize(20);
		// 去掉OrderSubNo前后带的空格
		if (StringUtils.isNotEmpty(vo.getOrderNo())) {
			vo.setOrderNo(StringUtils.deleteWhitespace(vo.getOrderNo()));
		} else {
			vo.setOrderNo(null);
		}

		if (StringUtils.isEmpty(vo.getMerchantCode())) {
			vo.setMerchantCode(null);
		}

		PageFinder<Map<String, Object>> pageFinder = null;
		try {
			// 默认设置查询起止时间
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isEmpty(vo.getCreateTimeStart()) && StringUtils.isEmpty(vo.getCreateTimeEnd())) {
				vo.setCreateTimeStart(DateUtil.format1(DateUtils.addDays(new Date(), -30)));
				vo.setCreateTimeEnd(DateUtil.format1(new Date()));
			} else if (StringUtils.isEmpty(vo.getCreateTimeStart())) {
				Date createTimeEnd = format.parse(vo.getCreateTimeEnd());
				vo.setCreateTimeStart(DateUtil.format1(DateUtils.addDays(createTimeEnd, -30)));
			} else if (StringUtils.isEmpty(vo.getCreateTimeEnd())) {
				Date createTimeStart = format.parse(vo.getCreateTimeStart());
				vo.setCreateTimeEnd(DateUtil.format1(DateUtils.addDays(createTimeStart, 30)));
			}

			// pageFinder =
			// orderForMerchantService.getAbnormalSaleApplyList(query, vo);
			pageFinder = order4MerchantLocalService.getAbnormalSaleApplyList(vo, query);
			if (null != pageFinder && CollectionUtils.isNotEmpty(pageFinder.getData())) {
				DateFormat format_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				for (Map<String, Object> data : pageFinder.getData()) {
					data.put("exceptionTypeName", getExceptionTypeName(String.valueOf(data.get("exceptiontype"))));
					//data.put("statusName", getApplyStatusName(String.valueOf(data.get("status"))));
					data.put("statusName", SaleStatusEnum.valueOf(String.valueOf(data.get("status"))).getName());
					List<String> supplyIds = new ArrayList<String>();
					supplyIds.add(String.valueOf(data.get("supplyid")));
					List<BrandVo> _brands = brandcatApi.queryLimitBrandBysupplyId(supplyIds);
					StringBuilder brandNameStr = new StringBuilder();
					if (CollectionUtils.isNotEmpty(_brands)) {
						for (BrandVo _brandVo : _brands) {
							Brand temp = commodityBaseApiService.getBrandByNo(_brandVo.getBrandNo());
							brandNameStr.append(temp.getBrandName() + "<br>");
						}
					}
					data.put("brand_name", brandNameStr);

					if ("SALE_APPLY".equals(data.get("status"))) {
						 // data.setHourNumRemain(ShipmentCountdownHour(iMerchantsApi.getShipmentDayHour(format_s.format(data.getCreateTime()),format_s.format(new Date()))));
						data.put("HourNumRemain", ShipmentCountdownHour(iMerchantsApi.getShipmentDayHour(format_s.format(data.get("create_time")), format_s.format(new Date()))));
					} else if ("SALE_COMFIRM".equals(data.get("status"))) {
						 //data.setHourNumRemain(ShipmentCountdownHour(iMerchantsApi.getShipmentDayHour(format_s.format(data.getAuditTime()),format_s.format(new Date()))));
						data.put("HourNumRemain", ShipmentCountdownHour(iMerchantsApi.getShipmentDayHour(format_s.format(data.get("audittime")), format_s.format(new Date()))));
					} else if ("SALE_CANCEL".equals(data.get("status"))) {
						 // data.setHourNumRemain(ShipmentCountdownHour(iMerchantsApi.getShipmentDayHour(format_s.format(data.getUpdateTime()),format_s.format(new Date()))));
						data.put("HourNumRemain", ShipmentCountdownHour(iMerchantsApi.getShipmentDayHour(format_s.format(data.get("update_time")), format_s.format(new Date()))));
					} else {
						// data.setHourNumRemain("-");
						data.put("HourNumRemain", "-");
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询异常售后列表时产生异常:", e);
		}
		model.addAttribute("vo", vo);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("omsHost", sysconfigproperties.getOmshost());
		return BASE_PATH + "abnormal_after_sale";
	}

    private String ShipmentCountdownHour(Double hourNumRemain) {
		int day = (int) Math.floor(Math.abs(hourNumRemain) / 24);
        int hour = (int) Math.floor(Math.abs(hourNumRemain) % 24);
        int min = (int) Math.rint((Math.abs(hourNumRemain) % 24 - hour) * 60);
		return (hourNumRemain > 0 ? "" : "超时") + (day != 0 ? day + "天" : "") + (hour != 0 ? hour + "小时" : "") + (min != 0 ? min + "分钟" : "");
    }
    
    /**
     * 异常售后审核明细
     * 
     * @param modelMap
     * @param id
     * @param isShipmentDay
     * @return
     */
    @RequestMapping("abnormal_after_sale_detail")
    public String abnormal_after_sale_detail(ModelMap model, QueryAbnormalSaleApplyVo vo, HttpServletRequest request) {

        SaleApplyBill saleApplyBill = asmApiImpl.querySaleApplyBillListByApplyNo(vo.getApplyNo());

        OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(vo.getOrderNo(), 1);
        List<Map<String, Object>> goodList = new ArrayList<Map<String, Object>>();
        for (OrderDetail4sub orderDetail : orderSub.getOrderDetail4subs()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("prodNo", orderDetail.getProdNo());
            map.put("prodName", orderDetail.getProdName());
            map.put("commoditySpecificationStr", orderDetail.getCommoditySpecificationStr());
            map.put("levelCode", orderDetail.getLevelCode());
            map.put("commodityNum", orderDetail.getCommodityNum());
            map.put("usableNum", orderDetail.getUsableNum());
            map.put("url", commodityBaseApiService.getFullCommodityPageUrl(StringUtils.substring(orderDetail.getProdNo(), 0, orderDetail.getProdNo().length() - 3)));
            if (orderDetail.getProdNo().equals(saleApplyBill.getGoodInfos().get(0).getProdCode())) {
                map.put("flag", "1");
                map.put("applyNum", saleApplyBill.getGoodInfos().get(0).getCommodityNum());
            } else {
                map.put("flag", "2");
            }
            goodList.add(map);
        }

        List<OrderLog> log = orderApi.getOrderLogsByOrderSubNo(vo.getOrderNo());
        model.addAttribute("log", log);
		// 如果有补发，显示补发订单和物流信息
		if (saleApplyBill != null && StringUtils.isNotEmpty(saleApplyBill.getNewOrderNo())) {
			OrderSub newOrderSub = orderApi.getOrderSubByOrderSubNo(saleApplyBill.getNewOrderNo(), 4);
			model.addAttribute("newOrderNo", saleApplyBill.getNewOrderNo());
			model.addAttribute("logisticsName", newOrderSub.getLogisticsName());
			model.addAttribute("expressCode", newOrderSub.getExpressOrderId());
		}

        model.addAttribute("saleApplyBill", saleApplyBill);
        model.addAttribute("goodList", goodList);
        try {
            model.addAttribute("qaNum", iAsmService.getApplyCount(vo.getApplyNo()));
        } catch (Exception e) {
            logger.error("查询售后该申请单"+vo.getApplyNo()+"商品的质检数量时时产生异常:", e);
        }
        
        if (!CollectionUtils.isEmpty(saleApplyBill.getAbnoramalBill())) {
            model.addAttribute("exceptionType", saleApplyBill.getAbnoramalBill().get(0).getExceptionType());
            if("QUALITY_GOODS".equals(saleApplyBill.getAbnoramalBill().get(0).getExceptionType())){
            	model.addAttribute("isFreeOrder", saleApplyBill.getAbnoramalBill().get(0).getIsFreeOrder());
            }
            model.addAttribute("auditRemark", saleApplyBill.getAbnoramalBill().get(0).getAuditRemark());
            model.addAttribute("descException", saleApplyBill.getAbnoramalBill().get(0).getDescException());
            model.addAttribute("cancelNum", saleApplyBill.getAbnoramalBill().get(0).getCancelNum());
            model.addAttribute("picCertificate", saleApplyBill.getAbnoramalBill().get(0).getPicCertificate());
        }
        return BASE_PATH + "abnormal_after_sale_detail";
    }

    /**
     * 上传审核图片
     * 
     * @param request
     * @param response
     * @return String
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/startUploadCommodityDescribePic")
    public String startUploadCommodityDescribePic(MultipartHttpServletRequest request, HttpServletResponse response) {
        ResultMsg resultMsg = new ResultMsg();
        try {
            MultipartFile multipartFile = request.getFile("imgMaps");
            resultMsg = uploadCommodityDescPic2TemporarySpace(multipartFile);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultMsg.setSuccess(false);
            return JSONObject.fromObject(resultMsg).toString();
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    private ResultMsg uploadCommodityDescPic2TemporarySpace(MultipartFile multipartFile) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(true);
        // 上传图片
        Map<String, File> uploadFileMap = new HashMap<String, File>();
        // 处理图片文件名
        String picName = multipartFile.getOriginalFilename();
        picName = StringUtils.strip(picName);// 去除特殊字符
        picName = StringUtils.deleteWhitespace(picName);// 去除空格
        File tempFile = new File(picName);
        multipartFile.transferTo(tempFile);
        uploadFileMap.put(tempFile.getName(), tempFile);
        Date d = new Date();
        String temp = DateUtil.format(d, "yyyyMMdd");
        // 生成内部临时路径
        String innerFilePath = commoditySettings.imageFtpAccessoryAbnormal+temp+"/";
//        String path = "http://" + commoditySettings.imageFtpServer + ":" + commoditySettings.imageFtpPort + commoditySettings.imageFtpAccessoryAbnormal
//                + temp+"/"+picName;
        resultMsg.setReObj(commoditySettings.getImageAccessoryPreviewDomain()+ temp+"/"+picName);
        resultMsg.setReCode(picName);
        // 上传失败的文件
        Map<String, File> failFile = this.ftpUpload(uploadFileMap, innerFilePath);

        if (MapUtils.isNotEmpty(failFile)) {
            logger.error(MessageFormat.format("upload temporary space fail : {0}.", new Object[] { failFile.keySet() }));
            resultMsg.setSuccess(false);
            return resultMsg;
        }
        // 删除临时文件
        tempFile.delete();
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
    private Map<String, File> ftpUpload(Map<String, File> uploadFileMap, String ftpServerPath) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(ftpServerPath) && ftpServerPath.startsWith("/"))
            ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
        Message<File> message = null;
        Map<String, File> failFileMap = new HashMap<String, File>();
        boolean result = true;
        for (String uploadFileStr : uploadFileMap.keySet()) {
            message = MessageBuilder.withPayload(uploadFileMap.get(uploadFileStr)).setHeader("remote_dir", ftpServerPath)
                    .setHeader("remote_filename", new String(uploadFileStr.getBytes("UTF-8"), "ISO-8859-1")).build();
            result = ftpChannel.send(message);
            if (!result) {
                failFileMap.put(uploadFileStr, uploadFileMap.get(uploadFileStr));
            }
        }
        return failFileMap;
    }

    /*
     * 调订单组更新申请单
     */
    @ResponseBody
    @RequestMapping("/save")
    public String save(AbnoramalSaleApplyBill vo, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        try {
            vo.setReviewer(operator);
            vo.setAuditTime(new Date());
            vo.setAuditPlatform("MERCHANT");
            boolean result = asmApiImpl.updateAbnoramalSaleApplyBill(vo);
            resultMsg.setSuccess(result);
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    /*
     * 调订单组取消申请单,该接口目前未使用
     */
    @ResponseBody
    @RequestMapping("/cancel")
    public String cancel(String applyNo, String remark, ModelMap modelMap, HttpServletRequest request) throws Exception {

        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        try {
        	logger.error("[AFTER_SALE]:applyNo="+applyNo+",operator="+operator+",remark="+remark);
            boolean result = asmApiImpl.cancelSaleApplyBillByApplyNo(applyNo, operator, remark);
            resultMsg.setSuccess(result);
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    /*
     * 调订单组拒绝申请单
     */
    @ResponseBody
    @RequestMapping("/refues")
    public String refues(String applyNo, String remark, String picCertificate, ModelMap modelMap, HttpServletRequest request) throws Exception {
    	String operator = GetSessionUtil.getSystemUser(request).getUsername();
    	ResultMsg resultMsg = new ResultMsg();
        try {
        	logger.error("[AFTER_SALE]:applyNo="+applyNo+",operator="+operator+",remark="+remark);
           // boolean result = asmApiImpl.cancelSaleApplyBillByApplyNo(applyNo, operator, remark);
        	RespReuslt respReuslt=asmApiImpl.updateAbnoramalSaleApplyBill2Refues(applyNo, operator, remark, picCertificate);
			resultMsg.setSuccess(respReuslt.getResultCode().equals(RespResultEnum.SUCCESS.toString()));
			resultMsg.setMsg(respReuslt.getResultDesc());
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    @ResponseBody
    @RequestMapping("/deleteImg")
    public String deleteImg(ModelMap modelMap,String filename) {
        ResultMsg resultMsg = new ResultMsg();
        try {
            String temp = DateUtil.format(new Date(), "yyyyMMdd");
            String remoteDisectory = commoditySettings.getImageFtpAccessoryAbnormal()+ temp;
            // 删除FTP图片
            final Message<String> messageJPG = MessageBuilder.withPayload(filename)
                    .setHeader("file_remoteDirectory", remoteDisectory).setHeader("file_remoteFile", new String(filename.getBytes("UTF-8"),"ISO-8859-1")).build();
            try {
                boolean resultJPG = toRemoveChannel.send(messageJPG);
                resultMsg.setSuccess(resultJPG);
            } catch (Exception e) {
                if (e instanceof MessagingException&&((MessagingException)e).getMessage().indexOf("java.io.IOException")>=0){
            
                }else{
                    throw e;
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultMsg.setSuccess(false);
             return JSONObject.fromObject(resultMsg).toString();
        }
         return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * 转换异常类型
     * 
     * @param saleType
     * @return
     */
    private String getExceptionTypeName(String saleType) {
        if (StringUtils.isBlank(saleType))
            return "未知";
        if ("LOST_GOODS".equals(saleType))
            return "丢件";
        else if ("DRAIN_GOODS".equals(saleType))
            return "漏发";
        else if ("ERROR_GOODS".equals(saleType))
            return "错发";
        else if ("QUALITY_GOODS".equals(saleType))
            return "质量问题投诉";
        else if ("REJECT_GOODS".equals(saleType))
            return "拒收未质检";
        return "";
    }
}
