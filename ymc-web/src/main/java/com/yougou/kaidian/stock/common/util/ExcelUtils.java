package com.yougou.kaidian.stock.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.kaidian.common.util.UUIDGenerator;


/**
 * EXCEL文档解析工具类 该工具能将EXCEL文档中的表解析为由JAVA基础类构成的数据集合 整个EXCEL表由多个行组成.每行用一个LIST表示.
 * EXCEL中的行由一个LIST表示,各列的数据索引从0开始一一对齐存放在这个LIST中; 多个行构成整个表,由一个LIST存放多个行. /**
 * 类描述：TODO 类实现描述
 * 
 * @author zhongwen
 * @date 2011-6-30 下午02:26:38
 * @email zw1004@163.com
 */
public class ExcelUtils {

	private Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	private HSSFWorkbook workbook;

	public ExcelUtils(InputStream s) throws IOException {
		workbook = new HSSFWorkbook(s);
	}
	
	public ExcelUtils(File excelFile) throws FileNotFoundException, IOException {
		workbook = new HSSFWorkbook(new FileInputStream(excelFile));
	}

	/**
	 * 获得表中的数据
	 * 
	 * @param sheetNumber
	 *            表格索引(EXCEL 是多表文档,所以需要输入表索引号)
	 * @return 由LIST构成的行和表
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Object[]> getDatasInSheet(int sheetNumber) throws FileNotFoundException, IOException {
		List<Object[]> result = new ArrayList<Object[]>();

		// 获得指定的表
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);

		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		logger.info("found excel rows count: " + rowCount);
		if (rowCount < 1) {
			return null;
		}

		// 逐行读取数据
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {

			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);

			if (row != null) {
				
				// 获得本行中单元格的个数
				int columnCount = row.getLastCellNum();
				Object[] rowData = new Object[columnCount];
				
				// 获得本行中各单元格中的数据
				for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					
					HSSFCell cell = row.getCell(columnIndex);

					// 获得指定单元格中数据
					Object cellStr = this.getCellString(cell);
					rowData[columnIndex] = (cellStr);

				}

				result.add(rowData);
			}
		}
		return result;
	}

	/**
	 * 获得表中的数据( flag=0 不增加id,flag=1自动增加id  )
	 * 
	 * @param sheetNumber
	 *            表格索引(EXCEL 是多表文档,所以需要输入表索引号)
	 *         flag=0 不增加id,flag=1自动增加id ，列总数 columnCount
	 * @return 由LIST构成的行和表
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Object[]> getNewDatasInSheet(int sheetNumber,int flag,int columnCount) throws FileNotFoundException, IOException {
		List<Object[]> result = new ArrayList<Object[]>();

		// 获得指定的表
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);

		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		//int rowCount=this.getRightRows(sheetAs);	
		int sjrsCols=0;
		logger.info("found excel rows count: " + rowCount);
		if (rowCount < 1) {
			return null;
		}

		// 逐行读取数据
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {

			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);

			if (row != null) {
				
				// 获得本行中单元格的个数(通过参数获取)
//				int columnCount=0;
//				if (flag == 0) {
//					columnCount = row.getLastCellNum();
//				} else {
//					columnCount = row.getLastCellNum() + 1;
//				}
				
				
				Object[] rowData = new Object[columnCount];
				
				// 获得本行中各单元格中的数据
				if (flag == 0) {
					
					for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {

						HSSFCell cell = row.getCell(columnIndex);

						// 获得指定单元格中数据
						Object cellStr = this.getCellString(cell);
						if(cellStr!=null){
							if(cellStr.toString().trim().length()>0){
								 rowData[columnIndex] = cellStr.toString().trim();
								 sjrsCols++;
							}
						}
						//rowData[columnIndex] = (cellStr);
					}
				} else {
					for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
						
						if(columnIndex==0){
						   rowData[0] = UUIDGenerator.getUUID();
						}else{
							// 获得指定单元格中数据
							HSSFCell cell = row.getCell(columnIndex-1);
							Object cellStr = this.getCellString(cell);
							if(cellStr!=null){
								if(cellStr.toString().trim().length()>0){
									 rowData[columnIndex] = cellStr.toString().trim();
									 sjrsCols++;
								}
							}//为空写入""
							else{
								 rowData[columnIndex] = "";
								 sjrsCols++;
							}
						}
						
					}
				}
				if(sjrsCols!=0){
				   result.add(rowData);
				}
				sjrsCols=0;
				
			}
		}
		return result;
	}
	
	/**
	 * 获得单元格中的内容
	 * 
	 * @param cell
	 * @return
	 */
	protected Object getCellString(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		Object result = null;
		if (cell != null) {

			int cellType = cell.getCellType();

			switch (cellType) {

			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString();
				
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				result = df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			}
		}
		return result;
	}
	
}