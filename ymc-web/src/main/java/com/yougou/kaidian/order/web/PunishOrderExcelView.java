package com.yougou.kaidian.order.web;

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
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.order.model.OrderPunish;

public class PunishOrderExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "违规订单_" + DateUtil2.getCurrentDateTimeToStr() + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));

		List<OrderPunish> list = (List<OrderPunish>) model.get("list");
		Map<String, String> hourMap = (Map<String, String>) model.get("hourMap");

		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("订单号");
		header.createCell(1).setCellValue("外部订单号");
		header.createCell(2).setCellValue("下单日期");
		header.createCell(3).setCellValue("扣款状态");
		header.createCell(4).setCellValue("结算单号");
		header.createCell(5).setCellValue("结算周期");
		header.createCell(6).setCellValue("订单金额");
		header.createCell(7).setCellValue("违规类型");
		header.createCell(8).setCellValue("超出时长");
		header.createCell(9).setCellValue("罚款金额");

		int rowNum = 1;
		for (int i = 0; i < list.size(); i++) {
			OrderPunish orderPunish = list.get(i);
			HSSFRow row = sheet.createRow(rowNum++);
			String subOrderNo = orderPunish.getOrderNo();
			row.createCell(0).setCellValue(subOrderNo);
			row.createCell(1).setCellValue(orderPunish.getThirdOrderNo());
			row.createCell(2).setCellValue(DateUtil2.getDateTime(orderPunish.getOrderTime()));
			String isSettle = orderPunish.getIsSettle();
			if(StringUtils.equals(isSettle, "1")){
				isSettle = "已扣款";
			}else {
				isSettle = "未扣款";
			}
			row.createCell(3).setCellValue(isSettle);
			row.createCell(4).setCellValue(orderPunish.getSettleOrderNo());
			row.createCell(5).setCellValue(orderPunish.getSettleCycle());
			row.createCell(6).setCellValue(orderPunish.getOrderPrice());
			String punishType = orderPunish.getPunishType();
			if(StringUtils.equals(punishType, "1")){
				punishType = "超时效";
			}else{
				punishType = "缺货";
			}
			row.createCell(7).setCellValue(punishType);
			row.createCell(8).setCellValue(MapUtils.getIntValue(hourMap, subOrderNo));
			row.createCell(9).setCellValue(orderPunish.getPunishPrice());
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

	}

}
