package com.belle.yitiansystem.merchant.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.belle.infrastructure.util.DateUtil;

public class PunishOrderExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "违规订单_" + DateUtil.getCurrentDateTimeToStr() + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes(), "iso8859-1"));

		List<Map<String, Object>> list = (List<Map<String, Object>>) model.get("list");
		Map<String, String> orderStatusMap = (Map<String, String>) model.get("orderStatusMap");
		Map<String, String> hourMap = (Map<String, String>) model.get("hourMap");
		String punishOrderStatus = (String)model.get("punishOrderStatus");
		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFRow header = sheet.createRow(0);
		header.createCell(1).setCellValue("订单号");
		header.createCell(0).setCellValue("下单时间");
		header.createCell(2).setCellValue("商家名称");
		header.createCell(3).setCellValue("商家编号");
		header.createCell(4).setCellValue("发货状态");
		header.createCell(5).setCellValue("订单状态");
		header.createCell(6).setCellValue("发货时间");
		header.createCell(7).setCellValue("超出时长");
		header.createCell(8).setCellValue("订单金额");
		header.createCell(9).setCellValue("罚款金额");
		header.createCell(10).setCellValue("审核状态");
		if(StringUtils.equals(punishOrderStatus, "2")){
			header.createCell(11).setCellValue("结算状态");
		}

		int rowNum = 1;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			HSSFRow row = sheet.createRow(rowNum++);
			String subOrderNo = MapUtils.getString(map, "order_no");
			row.createCell(0).setCellValue(subOrderNo);
			row.createCell(1).setCellValue(MapUtils.getString(map, "order_time"));
			row.createCell(2).setCellValue(MapUtils.getString(map, "supplier"));
			row.createCell(3).setCellValue(MapUtils.getString(map, "merchant_code"));
			String shipmentStatus = MapUtils.getString(map, "shipment_status");
			if(StringUtils.equals(shipmentStatus, "1")){
				shipmentStatus = "已发货";
			}else{
				shipmentStatus = "未发货";
			}
			row.createCell(4).setCellValue(shipmentStatus);
			row.createCell(5).setCellValue(orderStatusMap.get(subOrderNo));
			row.createCell(6).setCellValue(MapUtils.getString(map, "shipment_time"));
			row.createCell(7).setCellValue(MapUtils.getInteger(hourMap, subOrderNo));
			row.createCell(8).setCellValue(MapUtils.getString(map, "order_price"));
			row.createCell(9).setCellValue(MapUtils.getString(map, "punish_price"));
			String orderStatus = MapUtils.getString(map, "punish_order_status");
			if(StringUtils.equals(orderStatus, "1")){
				orderStatus = "未审核";
			}else if(StringUtils.equals(orderStatus, "2")){
				orderStatus = "已审核";
			}else{
				orderStatus = "----";
			}
			row.createCell(10).setCellValue(orderStatus);
			if(StringUtils.equals(punishOrderStatus, "2")){
				if("1".equals(MapUtils.getString(map, "is_settle"))){
					row.createCell(11).setCellValue("已结算");
				}else{
					row.createCell(11).setCellValue("未结算");
				}
			}
		}
		sheet.setColumnWidth(0, 5 * 2 * 500);
		sheet.setColumnWidth(1, 5 * 2 * 500);
		sheet.setColumnWidth(2, 5 * 2 * 500);
		sheet.setColumnWidth(3, 5 * 2 * 500);
		sheet.setColumnWidth(4, 5 * 2 * 500);
		sheet.setColumnWidth(5, 5 * 2 * 500);
		sheet.setColumnWidth(6, 5 * 2 * 500);
		sheet.setColumnWidth(7, 5 * 2 * 500);
		sheet.setColumnWidth(8, 5 * 2 * 500);
		sheet.setColumnWidth(9, 5 * 2 * 500);
		sheet.setColumnWidth(10, 5 * 2 * 500);
		if(StringUtils.equals(punishOrderStatus, "2")){
			sheet.setColumnWidth(11, 5 * 2 * 500);
		}
	}
}
