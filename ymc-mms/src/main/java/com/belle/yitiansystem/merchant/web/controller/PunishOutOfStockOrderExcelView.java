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
/**
 * ClassName: PunishOutOfStockOrderExcelView
 * Desc: 缺货商品的导出
 * date: 2015-1-19 下午5:00:09
 * @author li.n1 
 * @since JDK 1.6
 */
public class PunishOutOfStockOrderExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "缺货商品_" + DateUtil.getCurrentDateTimeToStr() + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes(), "iso8859-1"));
		List<Map<String, Object>> list = (List<Map<String, Object>>) model.get("list");
		String punishOrderStatus = (String)model.get("punishOrderStatus");
		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFRow header = sheet.createRow(0);
		if("2".equals(punishOrderStatus)){
			//已审核
			//优购订单号、下单日期、商家名称、商家编号、品牌名称、
			//货品条码、置缺时间、成交价、审核状态（均为已审核）、结算状态、罚款金额。
			header.createCell(0).setCellValue("订单号");
			header.createCell(1).setCellValue("下单时间");
			header.createCell(2).setCellValue("商家名称");
			header.createCell(3).setCellValue("商家编号");
			header.createCell(4).setCellValue("品牌名称");
			header.createCell(5).setCellValue("货品条码");
			header.createCell(6).setCellValue("置缺时间");
			header.createCell(7).setCellValue("成交价");
			header.createCell(8).setCellValue("审核状态");
			header.createCell(9).setCellValue("结算状态");
			header.createCell(10).setCellValue("缺货数量");
			header.createCell(11).setCellValue("罚款金额");
			int rowNum = 1;
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				HSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(MapUtils.getString(map, "order_sub_no"));
				row.createCell(1).setCellValue(MapUtils.getString(map, "create_time"));
				row.createCell(2).setCellValue(MapUtils.getString(map, "supplier"));
				row.createCell(3).setCellValue(MapUtils.getString(map, "supplier_code"));
				row.createCell(4).setCellValue(MapUtils.getString(map, "brand_name"));
				row.createCell(5).setCellValue(MapUtils.getString(map, "level_code"));
				row.createCell(6).setCellValue(MapUtils.getString(map, "exception_time"));
				row.createCell(7).setCellValue(MapUtils.getString(map, "totalamt"));
				row.createCell(8).setCellValue("已审核");
				if("0".equals(MapUtils.getString(map, "is_settle"))){
					row.createCell(9).setCellValue("未结算");
				}else{
					row.createCell(9).setCellValue("已结算");
				}
				row.createCell(10).setCellValue(MapUtils.getString(map, "lack_num"));
				row.createCell(11).setCellValue(MapUtils.getString(map, "punish_price"));
			}
		}else{
			//待审核
			header.createCell(0).setCellValue("下单时间");
			header.createCell(1).setCellValue("订单号");
			header.createCell(2).setCellValue("商家名称");
			header.createCell(3).setCellValue("商家编号");
			header.createCell(4).setCellValue("品牌");
			header.createCell(5).setCellValue("商品名称");
			header.createCell(6).setCellValue("款号");
			header.createCell(7).setCellValue("款色编码");
			header.createCell(8).setCellValue("货品条码");
			header.createCell(9).setCellValue("置缺时间");
			header.createCell(10).setCellValue("成交价");
			header.createCell(11).setCellValue("缺货数量");
			header.createCell(12).setCellValue("审核状态");
			int rowNum = 1;
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				HSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(MapUtils.getString(map, "create_time"));
				row.createCell(1).setCellValue(MapUtils.getString(map, "order_sub_no"));
				row.createCell(2).setCellValue(MapUtils.getString(map, "supplier"));
				row.createCell(3).setCellValue(MapUtils.getString(map, "supplier_code"));
				row.createCell(4).setCellValue(MapUtils.getString(map, "brand_name"));
				row.createCell(5).setCellValue(MapUtils.getString(map, "prod_name"));
				row.createCell(6).setCellValue(MapUtils.getString(map, "style_no"));
				row.createCell(7).setCellValue(MapUtils.getString(map, "scode"));
				row.createCell(8).setCellValue(MapUtils.getString(map, "level_code"));
				row.createCell(9).setCellValue(MapUtils.getString(map, "exception_time"));
				row.createCell(10).setCellValue(MapUtils.getString(map, "totalamt"));
				row.createCell(11).setCellValue(MapUtils.getString(map, "lack_num"));
				row.createCell(12).setCellValue("未审核");
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
		sheet.setColumnWidth(11, 5 * 2 * 500);
		if(!("2".equals(punishOrderStatus))){
			sheet.setColumnWidth(12, 5 * 2 * 500);
		}
	}

}
