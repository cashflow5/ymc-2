/**
 * Project Name:ymc-web
 * File Name:ExportExcelTool.java
 * Package Name:com.yougou.kaidian.asm.common
 * Date:2014-9-5下午1:51:10
 *
 */

package com.yougou.kaidian.asm.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:ExportExcelUtil Desc: POI导出到Excel工具类 Date: 2014-9-5 下午1:51:10
 * 
 * @author li.n1
 * @since JDK 1.6
 * @see
 */
public class ExportExcelTool<T> {
	/**
	 * xls文件存放路径
	 */
	public static final String EXCEL_FILE_PATH = "xls";
	/**
	 * xls数据映射类
	 */
	private Class<T> classType;

	private Collection<T> dataset;

	private String fileName;// 文件名

	private String title;// 文档标题

	private String dateFormat = "yyyy-MM-dd";// 默认日期格式

	private String path;// 项目运行物理路径

	private List<ColnameToField> colNameToField;// 列名与类属性名的对应关系

	private Map<String, Map<String, String>> colValueToChange;// 将列值转换为另外的值
	
	private Logger logger = LoggerFactory.getLogger(ExportExcelTool.class);
	
	/**
	 * 构造方法，初始化数据
	 * 
	 * @param dataset
	 *            数据集合
	 * @param path
	 *            Tomcat项目运行路径
	 */
	@SuppressWarnings("unchecked")
	public ExportExcelTool(Collection<T> dataset, String path) {
		try {
			// 获取泛型的实际类型
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				// 转换成泛型类
				ParameterizedType pType = (ParameterizedType) type;
				this.classType = (Class<T>) pType.getActualTypeArguments()[0];
			}
			this.path = path;
			this.dataset = dataset;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造方法，初始化数据
	 * 
	 * @param dataset
	 *            数据集合
	 */
	@SuppressWarnings("unchecked")
	public ExportExcelTool(Collection<T> dataset) {
		try {
			// 获取泛型的实际类型
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				// 转换成泛型类
				ParameterizedType pType = (ParameterizedType) type;
				this.classType = (Class<T>) pType.getActualTypeArguments()[0];
			}
			this.dataset = dataset;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成Excel文件
	 */
	public void exportExcel() throws Exception{
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 获取文件输出流
		OutputStream out = null;
		try {
			out = new FileOutputStream(path+fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// 设置表格标题行的列名数组并生成Excel
		String[] header = new String[colNameToField.size()];
		int index = 0;
		for (ColnameToField colField : colNameToField) {
			header[index] = colField.getColname();
			index++;
		}
		// 导出
		exportExcel(title, header, out, dateFormat);
	}
	
	/**
	 * 生成Excel文件
	 * 传递输出流，弹窗口导出
	 */
	public void exportExcel(OutputStream out) throws Exception {
		// 设置表格标题行的列名数组并生成Excel
		String[] header = new String[colNameToField.size()];
		int index = 0;
		for (ColnameToField colField : colNameToField) {
			header[index] = colField.getColname();
			index++;
		}
		// 导出
		exportExcel(title, header, out, dateFormat);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
	 */
	private void exportExcel(String title, String[] headers, OutputStream out,
			String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.WHITE.index);
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
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		//HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		//		0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		//comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		//comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(18);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();// 取出每个对象
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			short cellIndex = 0;// Excel列索引
			for (ColnameToField colField : colNameToField) {
				String fieldName = colField.getFieldName();// 取属性名
				HSSFCell cell = row.createCell(cellIndex);
				cell.setCellStyle(style2);
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					// Class tCls = classType.getClass();
					Method getMethod = classType.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value == null) {

						textValue = "";
					} else if (value instanceof Boolean) {

						/*
						 * boolean bValue = (Boolean) value; textValue = "男"; if
						 * (!bValue) { textValue = "女"; }
						 */
					} else if (value instanceof Date) {

						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {

						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(cellIndex, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {

						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();

						// 判断属性值是否需要转换为其他的内容
						if (this.colValueToChange != null) {

							// 取出属性值转换Map
							Map<String, String> changeMap = colValueToChange
									.get(fieldName);
							if (changeMap != null && changeMap.size() > 0) {
								textValue = changeMap.get(textValue);
							}
						}

					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {

						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							//HSSFRichTextString richString = new HSSFRichTextString(
							//		textValue);
							//HSSFFont font3 = workbook.createFont();
							//font3.setColor(HSSFColor.BLUE.index);
							//richString.applyFont(font3);
							//cell.setCellValue(richString);
							cell.setCellValue(textValue);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
					cellIndex++;
				}
			}

		}
		
		try {
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			logger.error("IO异常，导出失败！",e);
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error("IO异常，导出失败！",e);
				}
			}
		}
		
	}

	/**
	 * 设置日期格式
	 * 
	 * @param dateFormat
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setColNameToField(List<ColnameToField> colNameToField) {
		this.colNameToField = colNameToField;
	}

	public void setColValueToChange(
			Map<String, Map<String, String>> colValueToChange) {
		this.colValueToChange = colValueToChange;
	}

}
