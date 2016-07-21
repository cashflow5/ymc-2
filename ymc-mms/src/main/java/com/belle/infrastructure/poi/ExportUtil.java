package com.belle.infrastructure.poi;

import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.merchant.api.monitor.vo.AnalyzeInterfaceDay;

public class ExportUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);
	
	public static String HIDDEN = "HIDDEN";
	public static String LOCKED = "LOCKED";
	public static String NORMAL = "NORMAL";
	
	/**
	 * @param title Sheet标题名
	 * @param headers 表格属性列名数组
	 * @param dataset 需要显示的数据集合,属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd HH:mm:ss"
	 * @param percents 需要以百分比显示的
	 * 
	 * @throws Exception 
	 * @throws  
	 */
	public static boolean exportExcel(String title, String[] headers, List<?> dataset, String[] heddenAndUpdateColumns, Boolean[] amounts,
			OutputStream out, String pattern, Class<?> clazz, String[] cloumns, Boolean[] percents, byte[] bs) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeightInPoints(20);
		if (null != heddenAndUpdateColumns && heddenAndUpdateColumns.length > 0) {
			for (int i = 0; i < heddenAndUpdateColumns.length; i++) {
				if (HIDDEN.equals(heddenAndUpdateColumns[i])) {
					sheet.setColumnHidden(i, true);
				}
			}
		}

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
		Iterator<?> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			//row.setHeight((short) 500);
			Object obj = it.next();
			for (int i = 0; i < cloumns.length; i++) {
				// 锁定单元格
				if (null != heddenAndUpdateColumns && heddenAndUpdateColumns.length > 0) {
					if (LOCKED.equals(heddenAndUpdateColumns[i])) {
						style2.setLocked(true);
					} else {
						style2.setLocked(false);
					}
				}
				
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
						SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern);
						textValue = sdf.format(date);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
						if (percents != null && percents.length > 0 && percents[i]) {
							textValue += "%";
						}
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
					return false;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return false;
				} finally {
					// 清理资源
				}
			}
		}
		
		if (null != bs) {
			// 生成一个表格
			HSSFSheet _sheet = workbook.createSheet("报表图片");
			// 声明一个画图的顶级管理器
		    HSSFPatriarch patriarch = _sheet.createDrawingPatriarch();
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
	              1023, 255, (short) 0, 0, (short) 15, 15);
	        anchor.setAnchorType(2);
	        patriarch.createPicture(anchor, workbook.addPicture(bs, HSSFWorkbook.PICTURE_TYPE_JPEG));
		}
		
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 导出excel
	 * 
	 * @param response
	 * @param request
	 * @param title
	 * @param headers
	 * @param datas
	 * @param heddenAndUpdateColumns
	 * @param amounts
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static synchronized void exportEexcel(HttpServletResponse response, HttpServletRequest request, String fileName, String sheetName, String[] headers, List<?> datas,
			String[] heddenAndUpdateColumns, Boolean[] amounts, Class<?> clazz, String[] cloumns, Boolean[] percents) throws Exception {
		OutputStream out=null;
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setStatus(response.SC_OK);
			if (StringUtils.isNotBlank(fileName)) {
				fileName = StringUtils.indexOf(request.getHeader("User-Agent"), "MSIE") != -1 ? 
						URLEncoder.encode(fileName, "UTF-8") : new String(fileName.getBytes("UTF-8"), 
								"ISO-8859-1");
				//fileName = new String(fileName.getBytes(request.getCharacterEncoding()), "ISO8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
			} else {
				response.setHeader("Content-Disposition", "attachment; filename=SheetExcel.xls");
			}
			out = response.getOutputStream();
			ExportUtil.exportExcel(sheetName, headers, datas, heddenAndUpdateColumns, amounts, out, null, clazz, cloumns, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("导出excel产生异常:",ex);
		} finally {
			if(out != null) {
				out.close();
				out = null;
			}
		}
	}
	
	//测试用例
	public static void main(String[] args) throws Exception {
		List<AnalyzeInterfaceDay> list = new ArrayList<AnalyzeInterfaceDay>();
		AnalyzeInterfaceDay day = new AnalyzeInterfaceDay();
		day.setApiName("我是中国人");
		day.setCallCount(100);
		day.setSucessCallCount(100);
		day.setFailCallCount(0);
		day.setSuccessRate(100.0);
		day.setRankingCall(1);
		day.setAvgFrequency(0);
		day.setMaxFrequency(100);
		day.setRankingFrequency(1);
		day.setAvgExTime(12.5);
		day.setMaxAppkeyNums(10);
		list.add(day);
		
		//图片
		FileInputStream jpeg = new FileInputStream("d:\\123.png");  
		byte[] bytes = IOUtils.toByteArray(jpeg);  
		
		OutputStream os = new FileOutputStream("d:\\ceshi.xls");
		String[] headers = new String[]{"APiName", "callcount1", "successCallCount", "failcallCount", "successRate", "rankingcall", "avgfressdf", 
				"maxfrequency", "rankingFresdf", "avgextime", "maxappkeynums"};
		String[] heddenAndUpdateColumns = new String[] {"NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL"};
		Boolean[] amounts = new Boolean[]{false, false, false, false, false, false, false, false, false, false, false};
		String[] cloumns = new String[] {"apiName", "callCount", "sucessCallCount", "failCallCount", "successRate", "rankingCall", "avgFrequency", "maxFrequency", "rankingFrequency", "avgExTime", "maxAppkeyNums"};
		Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false, false, false, false};
		ExportUtil.exportExcel("我是测试文档", headers, list, heddenAndUpdateColumns, amounts, os, null, AnalyzeInterfaceDay.class, cloumns, percents, bytes);
		
		System.out.println("文件导出");
	}
}
