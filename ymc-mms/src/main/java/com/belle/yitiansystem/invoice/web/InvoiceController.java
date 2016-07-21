package com.belle.yitiansystem.invoice.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.ordercenter.common.Query;
import com.yougou.ordercenter.api.invoice.IInvoiceNewApi;
import com.yougou.ordercenter.api.order.IOrderApi;
import com.yougou.ordercenter.model.order.OrderInvoiceNew;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
/**
 * 
 * @author zhang.wj
 *
 */
@Controller
@RequestMapping("/invoice/web/InvoiceController")
public class InvoiceController {
	@Autowired
	private SysconfigProperties sysconfigproperties;
    
	//发票接口
	@Resource
    private IInvoiceNewApi iInvoiceNewApi;
	
	 @Resource
	private IMerchantsApi iMerchantsApi;
	//定单接口
	 @Resource
	private IOrderApi  iOrderApi;
	 
	 @Resource
	private  IMerchantServiceNew  iMerchantServiceNew;
	 
	 

	 
	 //iMerchantServiceNew
	/**
	 * 查询发票信息
	 * @param modelMap
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryInvoice")
	public String queryInvoice(ModelMap modelMap, OrderInvoiceNew vo,HttpServletRequest request,Query query) throws Exception {
		Map<String, Object> params =new HashMap<String,Object>();
		query.setPageSize(20);
		this.setVoInfo(vo, request);
		if (null == vo.getInvoiceStatus()) {
	            vo.setInvoiceStatus(2);
	    } else if (-1 == vo.getInvoiceStatus()) {
	            vo.setInvoiceStatus(null);
	    }
		
		//从接口获取发票信息
		PageFinder<OrderInvoiceNew> pageFinder = null;
		pageFinder =iInvoiceNewApi.queryMerchantInvoiceByPageFinder(query, vo);
		modelMap.put("params", params);
		List<String>   list=new ArrayList<String>();
		
		modelMap.addAttribute("vo", vo);
		   String orderMainNo_old = "";
           if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
               for (OrderInvoiceNew orderInvoiceNew : pageFinder.getData()) {
            	   list.add(orderInvoiceNew.getMerchantCode());
                   if (orderMainNo_old.equals(orderInvoiceNew.getOrderMainNo())) {
                       orderMainNo_old = orderInvoiceNew.getOrderMainNo();
                       orderInvoiceNew.setOrderMainNo("");
                   } else {
                       orderMainNo_old = orderInvoiceNew.getOrderMainNo();
                   }

                   // 计算未处理时长
                   if (orderInvoiceNew.getInvoiceStatus() == 2) {
                       orderInvoiceNew.setHourNumRemain(ShipmentCountdownHour(iMerchantsApi.getShipmentCountdownHour(orderInvoiceNew.getCreateTime(), new Date())));
                   } else {
                       orderInvoiceNew.setHourNumRemain("-");
                   }
               }
           }
         if(list.size()>0){
        	//查询商家名称
             Map<String, List<String>>  queryMap=new HashMap<String, List<String>>();
             queryMap.put("commodity_codes", list);
             List<Map<String,Object>>   merchantList=iMerchantServiceNew.getMerchantInfo(queryMap);
             if(merchantList!=null){
             	for (Map<String, Object> map : merchantList) {
             		int flag=0;
             		 for (OrderInvoiceNew orderInvoiceNew : pageFinder.getData()) {
             			String merchantCode=orderInvoiceNew.getMerchantCode();
                   	   if(merchantCode!=null && merchantCode.equals(map.get("supplier_code"))){
                   		 orderInvoiceNew.setMerchantCode((String)map.get("supplier"));
                   	   }
                    }
     			}
             }
         }
        modelMap.addAttribute("supplier", request.getParameter("supplier"));
        modelMap.addAttribute("supplierSpId", request.getParameter("supplierSpId"));
        modelMap.addAttribute("omsHost", sysconfigproperties.getOmshost());
		modelMap.addAttribute("pageFinder", pageFinder);
		return "invoice/invoiceList";
		
	}
	private void  setVoInfo( OrderInvoiceNew vo,HttpServletRequest request){
		//下单时间
		vo.setOrderCreateTimeStart(request.getParameter("orderCreateTimeStart"));
		vo.setOrderCreateTimeEnd(request.getParameter("orderCreateTimeEnd"));
		//登记
		vo.setInvoiceCreateTimeStart(request.getParameter("invoiceCreateTimeStart"));
		vo.setInvoiceCreateTimeEnd(request.getParameter("invoiceCreateTimeEnd"));
		//订单号
		vo.setOrderMainNo(request.getParameter("orderMainNo"));
		//发票号
		vo.setInvoiceNo(request.getParameter("invoiceNo"));
		
		vo.setMerchantCode(request.getParameter("supplierSpId"));
		
		
	}
	private String ShipmentCountdownHour(Double hourNumRemain) {
	        int hour = (int) Math.floor(Math.abs(hourNumRemain) % 24);
	        return (hourNumRemain > 0 ? "" : "超时") + (int) Math.floor(Math.abs(hourNumRemain) / 24) + "天" + hour + "小时";
	}


}
