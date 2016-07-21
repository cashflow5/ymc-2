package com.yougou.api.monitor;

import java.beans.PropertyDescriptor;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ApiReportExcelView extends AbstractExcelView {
	
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = (String) model.get("fileName");
		if (StringUtils.isNotBlank(fileName)) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}.xls", fileName));
		
		String title = (String) model.get("title");
		String[] headers = (String[]) model.get("headers");
		String[] cloumns = (String[]) model.get("cloumns");
		//需要百分比处理的字段
		Boolean[] percents = (Boolean[]) model.get("percents");
		//是否需要让金额处理
		Boolean[] amounts = (Boolean[]) model.get("amounts");
		Class<?> clazz = (Class<?>) model.get("clazz");
		String pattern = (String) model.get("pattern"); //时间格式化 default "yyy-MM-dd HH:mm:ss"
		List<?> list = (List<?>)model.get("list");
		//是否需要导出报表（svg）
		byte[] bytes = (byte[]) model.get("bytes");
		
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeightInPoints(20);

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();

		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.GREY_80_PERCENT.index);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 文本样式设置
		HSSFFont font3 = workbook.createFont();
		font3.setColor(HSSFColor.GREY_80_PERCENT.index);
		// 设置数据格式样式
		HSSFCellStyle style3 = workbook.createCellStyle();
		HSSFDataFormat df = workbook.createDataFormat();
		style3.setDataFormat(df.getFormat("###,##0.00"));
				
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<?> it = list.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			//row.setHeight((short) 500);
			Object obj = it.next();
			for (int i = 0; i < cloumns.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Object value = new PropertyDescriptor(cloumns[i], clazz).getReadMethod().invoke(obj);
				if (value == null) {
					continue;
				}

				try {
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyy-MM-dd HH:mm:ss" : pattern);
						textValue = sdf.format(date);
					} else if (value instanceof Double) {
						if (percents != null && percents.length > 0 && percents[i]) {
							textValue = String.format("%2.2f%%", value);
						} else {
							textValue = String.format("%2.2f", value);
						}
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							if (textValue.length() > 11) {
								// 长度超过11位，按文本方式写入，防止出现科学计数法的现象。
								cell.setCellValue(textValue);
							} else {
								// 如果设置此列为货币金额格式，则格式化此列
								if (amounts != null && amounts.length > 0 && amounts[i]) {
									cell.setCellStyle(style3);
								}
								// 是数字当作double处理
								cell.setCellValue(Double.parseDouble(textValue));
							}
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		
		if (null != bytes) {
			// 生成一个表格
			HSSFSheet _sheet = workbook.createSheet("图表");
			// 声明一个画图的顶级管理器
		    HSSFPatriarch patriarch = _sheet.createDrawingPatriarch();
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
	              1023, 255, (short) 0, 0, (short) 15, 15);
	        anchor.setAnchorType(2);
	        patriarch.createPicture(anchor, workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG));
		}
	}

}
