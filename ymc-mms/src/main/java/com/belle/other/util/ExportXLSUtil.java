package com.belle.other.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportXLSUtil {
	
	public static String HIDDEN = "HIDDEN";
	
	public static String LOCKED = "LOCKED";
	
	public static String NORMAL = "NORMAL";
	
	/**
	    * @param title

	    *            Sheet标题名

	    * @param headers

	    *            表格属性列名数组

	    * @param dataset

	    *            需要显示的数据集合,属性的数据类型有基本数据类型及String,Date,byte[](图片数据)

	    * @param out

	    *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中

	    * @param pattern

	    *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"

	    */

	   public static boolean exportExcel(String title, String[] headers, Collection<Object[]> dataset, String[] heddenAndUpdateColumns, Boolean[] amounts, OutputStream out, String pattern) {

	      // 声明一个工作薄

	      HSSFWorkbook workbook = new HSSFWorkbook();

	      // 生成一个表格

	      HSSFSheet sheet = workbook.createSheet(title);

	      // 设置表格默认列宽度为15个字节

	      sheet.setDefaultColumnWidth(25);
	      
	      sheet.setDefaultRowHeight((short)20);
	      
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

	      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

	      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

	      style.setBorderRight(HSSFCellStyle.BORDER_THIN);

	      style.setBorderTop(HSSFCellStyle.BORDER_THIN);

	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	      // 生成一个字体

	      HSSFFont font = workbook.createFont();

	      font.setColor(HSSFColor.BLACK.index);

	      font.setFontHeightInPoints((short) 10);

	      font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

	      // 把字体应用到当前的样式

	      style.setFont(font);

	      // 生成并设置另一个样式

	      HSSFCellStyle style2 = workbook.createCellStyle();

	      //style2.setFillForegroundColor(HSSFColor.WHITE.index);

	      //style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	      //style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

	      //style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);

	      //style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

	      //style2.setBorderTop(HSSFCellStyle.BORDER_THIN);

	      //style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

	      //style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

	      // 生成另一个字体

	      HSSFFont font2 = workbook.createFont();
	      
	      font2.setColor(HSSFColor.GREY_80_PERCENT.index);

	      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

	      // 把字体应用到当前的样式

	      style2.setFont(font2);
	      
	      //文本样式设置
	      HSSFFont font3 = workbook.createFont();

          font3.setColor(HSSFColor.GREY_80_PERCENT.index);
	      
	      //设置数据格式样式
	      HSSFCellStyle style3 = workbook.createCellStyle();
	      
	      HSSFDataFormat df = workbook.createDataFormat();
	      
	      style3.setDataFormat(df.getFormat("###,##0.00"));
	      
	      //style3.setFillForegroundColor(HSSFColor.WHITE.index);
	      
	      //style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	      //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

	      //style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);

	      //style3.setBorderRight(HSSFCellStyle.BORDER_THIN);

	      //style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
	      
	      // 声明一个画图的顶级管理器

	      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

	      // 定义注释的大小和位置,详见文档

	      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));

	      // 设置注释内容

	      comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));

	      // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.

	      comment.setAuthor("AKim");

	 

	      //产生表格标题行

	      HSSFRow row = sheet.createRow(0);

	      for (int i = 0; i < headers.length; i++) {

	         HSSFCell cell = row.createCell(i);

	         cell.setCellStyle(style);

	         HSSFRichTextString text = new HSSFRichTextString(headers[i]);

	         cell.setCellValue(text);

	      }

	 

	      //遍历集合数据，产生数据行

	      Iterator<Object[]> it = dataset.iterator();

	      int index = 0;

	      while (it.hasNext()) {

	         index++;

	         row = sheet.createRow(index);
	         
	         row.setHeight((short)500);
	         
	         Object[] fields = it.next();
	         
	         for (int i = 0; i < fields.length; i++) {

	        	 //锁定单元格
	        	 
	        	 if (null != heddenAndUpdateColumns && heddenAndUpdateColumns.length > 0) {
	        	 
		        	 if (LOCKED.equals(heddenAndUpdateColumns[i])) {
		        		 
		        		 style2.setLocked(true);
		        		 
		        	 } else {
		        		 
		        		 style2.setLocked(false);
		        		 
		        	 }
		        	 
	        	 }
	        	 
	            HSSFCell cell = row.createCell(i);

	            cell.setCellStyle(style2);
	            
	            
	            Object value = fields[i];
	            
	            if(value==null) {
	            	continue;
	            }

	            try {

	                //判断值的类型后进行强制类型转换

	                String textValue = null;

	                if (value instanceof Date) {

	                   Date date = (Date) value;

	                   SimpleDateFormat sdf = new SimpleDateFormat(pattern);

	                    textValue = sdf.format(date);

	                }  else if (value instanceof byte[]) {

	                   // 有图片时，设置行高为60px;

	                   row.setHeightInPoints(60);

	                   // 设置图片所在列宽度为80px,注意这里单位的一个换算

	                   sheet.setColumnWidth(i, (short) (35.7 * 80));

	                   // sheet.autoSizeColumn(i);

	                   byte[] bsValue = (byte[]) value;

	                   HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,

	                         1023, 255, (short) 6, index, (short) 6, index);

	                   anchor.setAnchorType(2);

	                   patriarch.createPicture(anchor, workbook.addPicture(

	                         bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));

	                } else{

	                   //其它数据类型都当作字符串简单处理
	                	
	                   textValue = value.toString();

	                }

	                //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成

	                if(textValue!=null){

	                   Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");  

	                   Matcher matcher = p.matcher(textValue);

	                   if(matcher.matches()){

	                	  if(textValue.length() > 11) {
	                		  
	                		  //长度超过11位，按文本方式写入，防止出现科学计数法的现象。
	                		  
	                		  cell.setCellValue(textValue);
	                		  
	                	  }else{
	                		  
	                		  //如果设置此列为货币金额格式，则格式化此列
	                		  
	                		  if(amounts!=null && amounts.length>0 && amounts[i]) {
	                			  
	                			  cell.setCellStyle(style3);
	                			  
	                		  }
	                		  
	                		  //是数字当作double处理
	                		  
	                		  cell.setCellValue(Double.parseDouble(textValue));
	                	  }

	                   }else{

	                      HSSFRichTextString richString = new HSSFRichTextString(textValue);

	                      richString.applyFont(font3);

	                      cell.setCellValue(richString);

	                   }

	                }
	                
	            } catch (SecurityException e) {

	                // TODO Auto-generated catch block

	                e.printStackTrace();
	                return false;

	            } catch (IllegalArgumentException e) {

	                // TODO Auto-generated catch block

	                e.printStackTrace();
	                return false;

	            } finally {
	                //清理资源
	            }
	         }
	         
	      }

	      try {
	         workbook.write(out);
	      } catch (IOException e) {
	         e.printStackTrace();
	      } finally {
	    	  try {
	    		  if(out != null) {
	    			  out.close();
	    		  }
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	      }

	      return true;

	   }
	   
}
