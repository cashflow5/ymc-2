package com.yougou.kaidian.commodity.convert;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.yougou.kaidian.commodity.beans.BeanPropertyEqualsPredicate;
import com.yougou.kaidian.commodity.beans.ExcelColumnDefinition;
import com.yougou.kaidian.commodity.beans.ExcelColumnDefinition.ColumnDefinitionBuilder;

abstract class AbstractConverter<S, T> implements Converter<S, T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConverter.class);
	
	private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\.[0]*$");
	// private static final Pattern ROW_INDEX_PATTERN = Pattern.compile("第【[0-9]+】行", Pattern.MULTILINE);
	protected static final String IDENTIFIER_COMMODITY = "Commodity";
	protected static final String IDENTIFIER_PRODUCT = "Product";
	
	protected static final List<ExcelColumnDefinition> COLUMN_DEFINITIONS = new ArrayList<ExcelColumnDefinition>();
	
	protected static final String REQUIRED_TEXT = "必填";
	protected static final String OPTIONAL_TEXT = "选填";
	protected static final String BRAND_NAME = "品牌名称";
	protected static final String COLOR_NAME = "颜色";
	protected static final String SIZE_NAME = "尺码";
	protected static final String CAT_NO = "分类编号";
	protected static final String STRUCT_NAME = "分类结构名";
	protected static final String CAT_NAME = "分类名称";
	protected static final String SIZE_CHART = "尺码对照表id";
	protected static final String INVENTORY_QUANTITY = "库存数量";

	/** 异常备注列索引 **/
	public static final int ERROR_COMMENT_COLUMN_INDEX = 0;
	
	/** 标题行数 **/
	public static final int HEADER_LINES = 1;
	
	/** 工作表密码 **/
	public static final String SHEET_PASSWD = new java.rmi.server.UID().toString();
	
	/** 工作表名称 **/
	public static final String WRITE_SHEET_NAME = "填写商品资料";
	public static final String READ_SHEET_NAME = "属性参照页(禁止修改)";
	
	/** 标识列索引(款色编码) **/
	public static final int IDENTITY_COLUMN_INDEX;
	/** 款号列索引 **/
	public static final int STYLENO_COLUMN_INDEX;
	/** 供应商条码列索引 **/
	//public static final int THIRDPRATYCODE_COLUMN_INDEX;
	/** 条码列索引 **/
	public static final int INSIDECODE_COLUMN_INDEX;
	/** 品牌名称列索引 */
	public static final int BRANDNAME_COLUMN_INDEX;
	/** 尺码列索引 **/
	public static final int SIZENAME_COLUMN_INDEX;
	/** 商品名称列索引 **/
	public static final int COMMODITYNAME_COLUMN_INDEX;
	/********* 商品卖点索引列 ************/
	public static final int SELLINGPOINT_COLUMN_INDEX;
	/** 市场价列索引 **/
	public static final int PUBLICPRICE_COLUMN_INDEX;
	/** 销售价列索引 **/
	public static final int SALEPRICE_COLUMN_INDEX;
	/** 颜色价列索引 **/
	public static final int SPECNAME_COLUMN_INDEX;
	/** 年份列索引 **/
	public static final int YEAR_COLUMN_INDEX;
	
	static {
		//商品名称
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("commodityName").writFieldName("commodityName").get());
		//商品别名
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("alias").writFieldName("alias").required(false).get());
		//商品卖点
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("sellingPoint").writFieldName("sellingPoint").required(false).get());
		//品牌名称
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("brandName").writFieldName("brandName").dependencies(Arrays.asList(ColumnDefinitionBuilder.start().identifier(READ_SHEET_NAME).readFieldName(BRAND_NAME).writFieldName("brandNo").get())).get());
		//市场价
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("publicPrice").writFieldName("publicPrice").get());
		//销售价
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("salePrice").writFieldName("salePrice").get());
		//年份
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("year").writFieldName("year").validator(Pattern.compile("^[0-9]{4}$")).get());
		//款色编码
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start()
				.identifier(IDENTIFIER_COMMODITY).readFieldName("supplierCode")
				.writFieldName("supplierCode").get());
		//款号
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start()
				.identifier(IDENTIFIER_COMMODITY).readFieldName("styleNo")
				.writFieldName("styleNo").get());
		//颜色
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("specName").writFieldName("specName").validator(Pattern.compile("^[\u4E00-\u9FA5]+(/[\u4E00-\u9FA5]+)*$"))/*.dependencies(Arrays.asList(ColumnDefinitionBuilder.start().identifier(READ_SHEET_NAME).readFieldName(COLOR_NAME).writFieldName("specNo").get()))*/.get());
		//尺码
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_PRODUCT).readFieldName("sizeName").writFieldName("sizeName").dependencies(Arrays.asList(ColumnDefinitionBuilder.start().identifier(READ_SHEET_NAME).readFieldName(SIZE_NAME).writFieldName("sizeNo").get())).get());
		//供应商条码
		//COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_PRODUCT).readFieldName("thirdPratyCode").writFieldName("thirdPartyCode").get());
		//条码
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start()
				.identifier(IDENTIFIER_PRODUCT).readFieldName("insideCode")
				.writFieldName("insideCode").get());
		//库存数量
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_PRODUCT).readFieldName(INVENTORY_QUANTITY).writFieldName("stock").constField(true).required(false).get());
		//商品描述
		COLUMN_DEFINITIONS.add(ColumnDefinitionBuilder.start().identifier(IDENTIFIER_COMMODITY).readFieldName("prodDesc").writFieldName("prodDesc").required(false).get());
		//获取标识列索引
		IDENTITY_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "supplierCode")));
		STYLENO_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "styleNo")));
		//THIRDPRATYCODE_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "thirdPratyCode")));
		SELLINGPOINT_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils
				.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate(
						"readFieldName", "sellingPoint")));
		INSIDECODE_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "insideCode")));
		BRANDNAME_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "brandName")));
		SIZENAME_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "sizeName")));
		COMMODITYNAME_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "commodityName")));
		PUBLICPRICE_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "publicPrice")));
		SALEPRICE_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "salePrice")));
		SPECNAME_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "specName")));
		YEAR_COLUMN_INDEX = COLUMN_DEFINITIONS.indexOf(CollectionUtils.find(COLUMN_DEFINITIONS, new BeanPropertyEqualsPredicate("readFieldName", "year")));
	}
	
	/**
	 * 创建单元格
	 * 
	 * @param row 行
	 * @param columnIndex 单元格列索引
	 * @param cellValue 单元格值
	 * @return Cell
	 */
	public static Cell createCell(Row row, int columnIndex, String cellValue) {
		return createCell(row, columnIndex, cellValue, null);
	}
	
	/**
	 * 创建单元格带样式
	 * 
	 * @param row 行
	 * @param columnIndex 单元格列索引
	 * @param cellValue 单元格值
	 * @param cellStyle 单元格样式
	 * @return Cell
	 */
	public static Cell createCell(Row row, int columnIndex, String cellValue, CellStyle cellStyle) {
		return createCell(row, columnIndex, cellValue, cellStyle, null, null);
	}
	
	/**
	 * 创建单元格带样式备注
	 * 
	 * @param row 行
	 * @param columnIndex 单元格列索引
	 * @param cellValue 单元格值
	 * @param cellStyle 单元格样式
	 * @param patriarch 备注创办人
	 * @param cellComment 单元格备注
	 * @return Cell
	 */
	public static Cell createCell(Row row, int columnIndex, String cellValue, CellStyle cellStyle, Drawing drawing, String cellComment) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellValue(row.getSheet().getWorkbook().getCreationHelper().createRichTextString(cellValue));
		if (drawing != null) {
			cell.setCellComment(createComment(drawing, columnIndex, 0, columnIndex + 2, 3, cellComment));
		}
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}
	
	/**
	 * 创建备注
	 * 
	 * @param patriarch 备注创办人
	 * @param col1 列1
	 * @param row1 行1
	 * @param col2 列2
	 * @param row2 行2
	 * @param text 备注
	 * @return Comment
	 */
	public static Comment createComment(Drawing drawing, int col1, int row1, int col2, int row2, String text) {
		ClientAnchor clientAnchor = drawing.createAnchor(0, 0, 0, 0, col1, row1, col2, row2);
		Comment comment = drawing.createCellComment(clientAnchor);
		comment.setString(drawing instanceof XSSFDrawing ? new XSSFRichTextString(text) : new HSSFRichTextString(text));
		comment.setAuthor("Yougou");
		return comment;
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param sheet
	 * @param rowIndex 单元格行索引
	 * @param columnIndex 单元格列索引
	 * @return String
	 */
	public static String getCellValue(Sheet sheet, int rowIndex, int columnIndex) {
		return getCellValue2(getCell(getRow(sheet, rowIndex), columnIndex));
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param row
	 * @param columnIndex 单元格列索引
	 * @return String
	 */
	public static String getCellValue(Row row, int columnIndex) {
		return getCellValue2(getCell(row, columnIndex));
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return String
	 */
	public static String getCellValue(Cell cell) {
		switch (cell == null ? Integer.MIN_VALUE : cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return StringUtils.trim(cell.getStringCellValue());
		case Cell.CELL_TYPE_FORMULA:
			CellValue cellValue = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator().evaluate(cell);
			switch (cellValue.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return Boolean.toString(cellValue.getBooleanValue());
			case Cell.CELL_TYPE_NUMERIC:
			    //return NUMERIC_PATTERN.matcher(DECIMAL_FORMAT.format(cellValue.getNumberValue())).replaceAll("").trim();
				//return NUMERIC_PATTERN.matcher(String.valueOf(cell.getNumericCellValue())).replaceAll("").trim();
				return String.valueOf(new DecimalFormat("#").format(cell.getNumericCellValue()));
			case Cell.CELL_TYPE_STRING:
				return StringUtils.trim(cellValue.getStringValue());
			case Cell.CELL_TYPE_BLANK:
			case Cell.CELL_TYPE_ERROR:
				return "";
			case Cell.CELL_TYPE_FORMULA:// CELL_TYPE_FORMULA will never happen
				break;
			}
			return StringUtils.trim(cell.getCellFormula());
		case Cell.CELL_TYPE_BOOLEAN:
			return Boolean.toString(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_NUMERIC:
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                return DateFormatUtils.ISO_DATE_FORMAT.format(cell.getDateCellValue());
            } else {
                //return NUMERIC_PATTERN.matcher(DECIMAL_FORMAT.format(cell.getNumericCellValue())).replaceAll("").trim();
            	return NUMERIC_PATTERN.matcher(String.valueOf(cell.getNumericCellValue())).replaceAll("").trim();
            }
		case Cell.CELL_TYPE_BLANK:
		case Cell.CELL_TYPE_ERROR:
		default:
			return "";
		}
	}
	
	
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @param flag  false:对数值格式的数据做文本处理；
	 * 				true:对数值格式的数据不做处理，保留原来的格式（有小数点则保留最多小数点后两位，无小数点则保留整数部分）；
	 * 				不传该值，默认为false，对数值格式做文本处理
	 * 				传值如多个，只判断第一个
	 * @return String
	 */
	public static String getCellValue2(Cell cell,boolean... flag) {
		//System.out.println("cell value ="+cell+"==cell=="+cell.getCellType());
		switch (cell == null ? Integer.MIN_VALUE : cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return StringUtils.trim(cell.getStringCellValue());
		case Cell.CELL_TYPE_NUMERIC:
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				return DateFormatUtils.ISO_DATE_FORMAT.format(cell.getDateCellValue());
			} else {
				if(flag.length>0){	//传值
					if(!flag[0]){	//false
				           //return NUMERIC_PATTERN.matcher(String.valueOf(cell.getNumericCellValue())).replaceAll("").trim();
				           return NUMERIC_PATTERN.matcher(String.valueOf(new DecimalFormat("#").
				        		   format(cell.getNumericCellValue()))).replaceAll("").trim();
					}else{			//true
						return NUMERIC_PATTERN.matcher(
								String.valueOf(new DecimalFormat("0.##")
								.format(cell.getNumericCellValue()))).replaceAll("").trim();
					}
				}else{				//不传值，默认false
					if((cell.getColumnIndex()==PUBLICPRICE_COLUMN_INDEX||
							cell.getColumnIndex()==SALEPRICE_COLUMN_INDEX||cell.getColumnIndex()==SIZENAME_COLUMN_INDEX)){
						return NUMERIC_PATTERN.matcher(String.valueOf(new DecimalFormat("0.##")
						.format(cell.getNumericCellValue()))).replaceAll("").trim(); 
					}
				
					return NUMERIC_PATTERN.matcher(String.valueOf(new DecimalFormat("#").
			        		   format(cell.getNumericCellValue()))).replaceAll("").trim();
				}
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return Boolean.toString(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_BLANK:
		case Cell.CELL_TYPE_ERROR:
		default:
			return "";
		}
	}
	
	/**
	 * 获取单元格备注
	 * 
	 * @param sheet
	 * @param rowIndex 单元格行索引
	 * @param columnIndex 单元格列索引
	 * @return String
	 */
	public static String getCellComment(Sheet sheet, int rowIndex, int columnIndex) {
		return getCellComment(getCell(getRow(sheet, rowIndex), columnIndex));
	}
	
	/**
	 * 获取单元格备注
	 * 
	 * @param row
	 * @param columnIndex 单元格列索引
	 * @return String
	 */
	public static String getCellComment(Row row, int columnIndex) {
		return getCellComment(getCell(row, columnIndex));
	}
	
	/**
	 * 获取单元格备注
	 * 
	 * @param cell
	 * @return String
	 */
	public static String getCellComment(Cell cell) {
		Comment comment = cell.getCellComment();
		return comment == null ? null : StringUtils.trim(comment.getString().getString());
	}
	
	/**
	 * 设置错误列为选中的
	 * 
	 * @param sheet
	 */
	public static void setErrorCellAsActive(Sheet sheet) {
		sheet.setSelected(true);
		getCell(getRow(sheet, 1), 0).setAsActiveCell();
	}
	
	/**
	 * 获取行(没有自动创建)
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @return Row
	 */
	public static Row getRow(Sheet sheet, int rowIndex) {
		return CellUtil.getRow(rowIndex, sheet);
	}

	/**
	 * 删除行
	 * 
	 * @param row
	 */
	public static void removeRow(Row row, XSSFCellStyle defaultCellStyle, XSSFCellStyle errorStyle) {
		removeRow(row.getSheet(), row.getRowNum(), defaultCellStyle, errorStyle);
	}

	/**
	 * 删除行
	 * 
	 * @param sheet
	 * @param rowIndex 单元格行索引
	 * @param defaultCellStyle 默认样式
	 * @param errorStyle 异常单元格样式
	 */
	public static void removeRow(Sheet sheet, int rowIndex, CellStyle defaultCellStyle, CellStyle errorStyle) {
		int lastRowNum = sheet.getLastRowNum();
		Row removingRow = sheet.getRow(rowIndex);
		if (removingRow != null) {
			LOGGER.info("prepare invoke 'sheet.removeRow({})'", rowIndex);
			sheet.removeRow(removingRow);
		}
		// 重建异常注备(修复所有列)
		// 获取总列数
		int columnCount = sheet.getRow(0).getLastCellNum();

		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			Map<Integer, Map<Integer, String>> _rowsComments = new HashMap<Integer, Map<Integer, String>>();
			Map<Integer, String> _coumnComments = null;
			for (int i = rowIndex + 1, j = 0; i <= lastRowNum; i++) {
				_coumnComments = new HashMap<Integer, String>();
				for (int j2 = 0; j2 < columnCount; j2++) {
					Cell cell = getCell(sheet, i, j2);
					String _comment = getCellComment(cell);
					cell.removeCellComment();
					cell.setCellStyle(defaultCellStyle);
					if (StringUtils.isNotBlank(_comment)) {
						_coumnComments.put(j2, _comment);
					}
				}
				_rowsComments.put(j++, _coumnComments);
			}

			LOGGER.info("prepare invoke 'sheet.shiftRows({}, {}, {})'", new Object[]{rowIndex + 1, lastRowNum, -1});
			sheet.shiftRows(rowIndex + 1, lastRowNum--, -1);
			// 重建异常备注
			Drawing drawing = sheet.createDrawingPatriarch();
			for (int i = rowIndex, j = 0; i <= lastRowNum; i++, j++) {
				if (MapUtils.isNotEmpty(_rowsComments.get(j))) {
					for (Integer column : _rowsComments.get(j).keySet()) {
						String content = _rowsComments.get(j).get(column);
						if (StringUtils.isBlank(content))
							continue;
						Cell cell = getCell(sheet, i, column);
						cell.removeCellComment();
						cell.setCellComment(createComment(drawing, 3, 3, 6, 6, content));
						cell.setCellStyle(errorStyle);
					}
				}
			}
		}
	}
	
	/**
	 * logCellComment:记录excel的备注信息
	 * @author li.n1 
	 * @param sheet
	 * @param rowIndex 从第几行开始（以0坐标）
	 * @return 返回未成功导入修改的商品数量
	 * @since JDK 1.6
	 */
	public static int logCellComment(Sheet sheet,int rowIndex){
		int errorCount = 0;
		int lastRowNum = sheet.getLastRowNum();
		Row row = sheet.getRow(rowIndex);
		// 获取总列数
		int columnCount = row.getLastCellNum();
		for (int i = rowIndex; i <= lastRowNum; i++) {
			for (int col = 0; col < columnCount; col++) {
				Cell cell = getCell(sheet, i, col);
				String content = getCellComment(cell);
				if (StringUtils.isBlank(content))
					continue;
				LOGGER.error("批量导入修改资料在第{}行，第{}列，有备注信息：{}",new Object[]{i+1,col+1,content});
			}
			if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(sheet, i, 0))){
				errorCount++;
			}
		}
		return errorCount;
	}
	
	/**
	 * 获取列(没有自动创建)
	 * 
	 * @param row
	 * @param columnIndex 单元格列索引
	 * @return Cell
	 */
	public static Cell getCell(Sheet sheet, int rowIndex, int columnIndex) {
		return getCell(getRow(sheet, rowIndex), columnIndex);
	}
	
	/**
	 * 获取列(没有自动创建)
	 * 
	 * @param row
	 * @param columnIndex 单元格列索引
	 * @return Cell
	 */
	public static Cell getCell(Row row, int columnIndex) {
		return CellUtil.getCell(row, columnIndex);
	}
}
