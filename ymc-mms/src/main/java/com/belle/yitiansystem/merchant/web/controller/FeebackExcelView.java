package com.belle.yitiansystem.merchant.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.belle.infrastructure.util.DateUtil;
import com.belle.yitiansystem.merchant.model.pojo.Feeback;

public class FeebackExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "feeback_"+DateUtil.getCurrentDateTimeToStr()+".xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName );
		
		List<Feeback> feebackList = (List<Feeback>)model.get("feebackList");
		
		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("商家名称");
		header.createCell(1).setCellValue("商家编号");
		header.createCell(2).setCellValue("意见分类");
		header.createCell(3).setCellValue("联系电话");
		header.createCell(4).setCellValue("电子邮箱");
		header.createCell(5).setCellValue("意见标题");
		header.createCell(6).setCellValue("意见内容");
		header.createCell(7).setCellValue("提交日期");
		
		int rowNum = 1;
		for(int i = 0;i<feebackList.size();i++){
			Feeback feeback = feebackList.get(i);
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(feeback.getMerchantName());
			row.createCell(1).setCellValue(feeback.getMerchantCode());
			row.createCell(2).setCellValue(feeback.getFirstCate() + "-" +feeback.getSecondCate());
			row.createCell(3).setCellValue(feeback.getPhone());
			row.createCell(4).setCellValue(feeback.getEmail());
			row.createCell(5).setCellValue(feeback.getTitle());
			row.createCell(6).setCellValue(feeback.getContent());
			row.createCell(7).setCellValue(DateUtil.getDateTime(feeback.getCreateTime()));
			
		}
		sheet.setColumnWidth(0, 5*2*500);
		sheet.setColumnWidth(1, 5*2*500);
		sheet.setColumnWidth(2, 5*2*500);
		sheet.setColumnWidth(3, 5*2*500);
		sheet.setColumnWidth(4, 5*2*500);
		sheet.setColumnWidth(5, 5*2*500);
		sheet.setColumnWidth(6, 5*2*500);
		sheet.setColumnWidth(7, 5*2*500);
		
		
	}

}
