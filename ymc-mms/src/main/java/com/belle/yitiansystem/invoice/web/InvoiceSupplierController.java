package com.belle.yitiansystem.invoice.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belle.yitiansystem.merchant.model.pojo.SupplierSp4MyBatis;
import com.belle.yitiansystem.merchant.service.ISupplierContractService;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.ordercenter.common.Query;
import com.yougou.purchase.model.Supplier;

@Controller
@RequestMapping("/invoice/web/invoiceSupplierController")
public class InvoiceSupplierController {
	
	 @Resource
	 private ISupplierContractService supplierContractService; 
	
	@RequestMapping("/to_invoiceSupplier")
	public String to_invoiceSupplier(HttpServletRequest request,ModelMap model, Query query, Supplier supplier) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoiceFlag", request.getParameter("invoiceFlag"));
		params.put("supplier", supplier.getSupplier());
		params.put("supplierCode", supplier.getSupplierCode());
		if("1".equals(supplier.getSupplierType())){
			params.put("supplierType", "招商供应商");
			params.put("isOld", "2");
		}else{
			params.put("supplierType", "普通供应商");
		}
		model.addAttribute("invoiceFlag",  request.getParameter("invoiceFlag"));
		model.addAttribute("supplier", supplier.getSupplier());
		model.addAttribute("supplierCode", supplier.getSupplierCode());
		model.addAttribute("supplierType", supplier.getSupplierType());
		
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		
		PageFinder<SupplierSp4MyBatis> pageFinder = supplierContractService.selectSupplier4Contact(params,_query);
		model.addAttribute("pageFinder", pageFinder);
		return "invoice/supplierspList";
	}
}
