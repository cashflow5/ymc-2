package com.yougou.kaidian.commodity.convert;

import java.awt.Color;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.kaidian.commodity.beans.BeanPropertyBeIncludedPredicate;
import com.yougou.kaidian.commodity.beans.BeanPropertyEqualsPredicate;
import com.yougou.kaidian.commodity.beans.ExcelColumnDefinition;
import com.yougou.kaidian.commodity.model.vo.ExcelErrorVo;
import com.yougou.pc.vo.commodity.CommodityPropVO;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.pc.vo.commodity.ProductVO;

/**
 * XLS转换成商品导入模型列表
 * 提示：转换失败商品不予加入列表
 * 
 * @author yang.mq
 *
 */
public class ExcelToDataModelConverter extends AbstractConverter<XSSFWorkbook, List<CommodityVO>> {
	
	private static final  Logger LOGGER = LoggerFactory.getLogger(ExcelToDataModelConverter.class);
	
	private static final String ERR_MSG_PATTERN = "{0}第【{1}】行【{2}】列内容填写错误：{3}";
	
	private static final String FORMAT_ERROR_MSG_PATTERN = "数据格式错误, ({0})"; 
	
	private static final int SELLINGPOINT_LENGTH = 25;

	@Override
	public List<CommodityVO> convert(XSSFWorkbook workbook) {
		try {
			
			// 创建错误样式
			XSSFCellStyle wrongCellStyle = workbook.createCellStyle();
			wrongCellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 130, 105)));
			wrongCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 默认样式
			XSSFCellStyle defaultCellStyle = workbook.createCellStyle();
			
			// 获取XLS页
			XSSFSheet writeSheet = workbook.getSheet(WRITE_SHEET_NAME);
			if (writeSheet == null) {
				throw new IllegalStateException("导入商品资料错误：文件不存在" + WRITE_SHEET_NAME);
			}
			XSSFSheet readSheet = workbook.getSheet(READ_SHEET_NAME);
			if (readSheet == null) {
				throw new IllegalStateException("导入商品资料错误：文件不存在" + READ_SHEET_NAME);
			}
			// 删除空数据行
			removeTailBlankDataRows(writeSheet, defaultCellStyle, wrongCellStyle);
			// 解析属性页
			Map<String, EntryList> properties = resolvePropertiesSheet(readSheet);
			if (CollectionUtils.isEmpty(properties.get(CAT_NO).value)) {
				throw new IllegalStateException("导入商品资料错误：商品分类编码不存在");
			}
			if (CollectionUtils.isEmpty(properties.get(CAT_NAME).value)) {
				throw new IllegalStateException("导入商品资料错误：商品分类名称不存在");
			}
			if (CollectionUtils.isEmpty(properties.get(STRUCT_NAME).value)) {
				throw new IllegalStateException("导入商品资料错误：商品分类结构不存在");
			}
			// 解析商品页标题
			String[] writeSheetTitles = resolveSheetTitles(writeSheet.getRow(0));
			if (COLUMN_DEFINITIONS.size() > writeSheetTitles.length) {
				throw new IllegalStateException("导入商品资料错误：" + WRITE_SHEET_NAME + "标题行异常请您下载最新的模板再进行导入");
			}
			
			// 处理 XLS 数据
			List<CommodityVO> commodityVOList = new ArrayList<CommodityVO>();
			
			for (int i = 1, j = writeSheet.getLastRowNum(), k; i <= j; i = k) {
				XSSFRow row = writeSheet.getRow(i);
				String horizontal = super.getCellValue(row, IDENTITY_COLUMN_INDEX);
				// 定位标识列结束行索引
				for (k = i + 1; k <= j; k++) {
					String vertical = super.getCellValue(writeSheet, k, IDENTITY_COLUMN_INDEX);
					if (StringUtils.isNotBlank(vertical) && !vertical.equals(horizontal)) {
						break;
					}
				}
				
				CommodityVO commodityVO = null;
				try {

					// 校验标识列
					if (StringUtils.isBlank(horizontal)) {
						throw new IllegalArgumentException(MessageFormat.format(ERR_MSG_PATTERN, WRITE_SHEET_NAME, i + HEADER_LINES, writeSheetTitles[IDENTITY_COLUMN_INDEX], "null"));
					}
					// 解析有效的数据行
					List<ExcelErrorVo> errorList = new ArrayList<ExcelErrorVo>();
					commodityVO = resolveEffectiveDataRow(writeSheet, writeSheetTitles, properties, i, k, defaultCellStyle, errorList);
					if (errorList.isEmpty()) {
						commodityVOList.add(commodityVO);
					} else {
						//标示异常
						for (ExcelErrorVo errorVo : errorList) {
							Cell _cell = super.getCell(writeSheet, errorVo.getRow(), errorVo.getColumn());
							_cell.setCellStyle(wrongCellStyle);
							_cell.removeCellComment();
							_cell.setCellComment(super.createComment(writeSheet.createDrawingPatriarch(), 3, 3, 6, 6, errorVo.getErrMsg()));
							LOGGER.error("导入excel在第{}行，在第{}列，发生错误信息：{}（行列从0开始）：",new Object[]{errorVo.getRow(),errorVo.getColumn(),errorVo.getErrMsg()});
						}
					}
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage());
					Cell cell = super.getCell(row, ERROR_COMMENT_COLUMN_INDEX);
					cell.setCellStyle(wrongCellStyle);
					cell.removeCellComment();
					cell.setCellComment(super.createComment(writeSheet.createDrawingPatriarch(), i, 0, i + 4, 6, ex.getMessage()));
				} finally {
					LOGGER.debug("{}",ToStringBuilder.reflectionToString(commodityVO, ToStringStyle.MULTI_LINE_STYLE));
				}
			}
			
			super.setErrorCellAsActive(writeSheet);
			
			return commodityVOList;
		} catch (Exception ex) {
			LOGGER.error("convert xls to import data model error.", ex);
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	/**
	 * 删除尾部的空数据行
	 * 
	 * @param writeSheet
	 */
	private void removeTailBlankDataRows(XSSFSheet writeSheet, XSSFCellStyle defaultCellStyle, XSSFCellStyle wrongCellStyle) {
		outerLoop:
		for (int i = writeSheet.getLastRowNum(); i >= 1; i--) {
			Row row = super.getRow(writeSheet, i);
			for (int j = row.getLastCellNum() - 1; j >= 0; j--) {
				if (StringUtils.isNotBlank(super.getCellValue(row, j))) {
					continue outerLoop; 
				}
			}
			ExcelToDataModelConverter.removeRow(row, defaultCellStyle, wrongCellStyle);
		}
	}
	
	/**
	 * 解析有效的数据行
	 * 
	 * @param sheet
	 * @param sheetTitles
	 * @param properties
	 * @param startRowIndex
	 * @param endRowIndex
	 * @param wrongCellStyle
	 * @return CommodityVO
	 * @throws Exception
	 */
	private CommodityVO resolveEffectiveDataRow(XSSFSheet sheet, String[] sheetTitles, Map<String, EntryList> properties, int startRowIndex, int endRowIndex, XSSFCellStyle defaultCellStyle, List<ExcelErrorVo> errorList) throws Exception {
		CommodityVO commodityVO = new CommodityVO();
		List<ProductVO> productVOList = new ArrayList<ProductVO>();
		List<CommodityPropVO> commodityPropVOList = new ArrayList<CommodityPropVO>();
		
		for (int i = startRowIndex; i < endRowIndex; i++) {
			XSSFRow row = sheet.getRow(i);
			ProductVO productVO = new ProductVO();
			
			for (int j = 0; j < sheetTitles.length; j++) {
				Cell cell = row.getCell(j);
				// 清空错误样式以及原先的错误批注
				if (null != cell) {
					cell.removeCellComment();
					cell.setCellStyle(defaultCellStyle);
				}
				
				String cellValue = super.getCellValue2(cell);
				// 填充商品基础属性
				if (j < COLUMN_DEFINITIONS.size()) {
					ExcelColumnDefinition definition = COLUMN_DEFINITIONS.get(j);
					boolean isCommodityColumn = IDENTIFIER_COMMODITY.equals(definition.getIdentifier());
					// 跳过第一行后的所有商品数据列(商品数据以第一行为准)
					if (i > startRowIndex && isCommodityColumn) {
						continue;
					}
					// 非空验证
					if (StringUtils.isBlank(cellValue)) {
						if (definition.isRequired()) {
							errorList.add(new ExcelErrorVo(sheetTitles[j], sheetTitles[j] + "为必填项", i, j));
						}
					} else {// 商品&货品数据处理
						if (definition.getValidator() != null && !definition.getValidator().matcher(cellValue).matches()) {
							String errorMsg = "数据格式错误.";
							if (j == SPECNAME_COLUMN_INDEX) {
								errorMsg = MessageFormat.format(FORMAT_ERROR_MSG_PATTERN, "商品颜色必须为中文汉字或/,格式参考\"蓝色\",\"蓝色/黄色\"");
							} else if (j == YEAR_COLUMN_INDEX) {
								errorMsg = MessageFormat.format(FORMAT_ERROR_MSG_PATTERN, "只能填写4位年份");
							} else if (j == IDENTITY_COLUMN_INDEX) {
								errorMsg = MessageFormat.format(
										FORMAT_ERROR_MSG_PATTERN, "款色编码不能包含中文");
							} else if (j == STYLENO_COLUMN_INDEX) {
								errorMsg = MessageFormat.format(
										FORMAT_ERROR_MSG_PATTERN, "款号不能包含中文");
							}
							errorList.add(new ExcelErrorVo(sheetTitles[j], errorMsg, i, j));
						}
						if (j == SELLINGPOINT_COLUMN_INDEX
								&& !StringUtils.isBlank(cellValue)
								&& cellValue.trim().length() > SELLINGPOINT_LENGTH) {// 商品卖点
							errorList.add(new ExcelErrorVo(sheetTitles[j],
									"商品卖点不能超过25个字", i, j));
						}
						if (j == IDENTITY_COLUMN_INDEX
								&& Pattern.compile("[\u4e00-\u9fa5]")
										.matcher(cellValue).find()) {
							errorList.add(new ExcelErrorVo(sheetTitles[j],
									"款色编码不能包含中文", i, j));
						}
						if (j == STYLENO_COLUMN_INDEX
								&& Pattern.compile("[\u4e00-\u9fa5]")
										.matcher(cellValue).find()) {
							errorList.add(new ExcelErrorVo(sheetTitles[j],
									"款号不能包含中文", i, j));
						}
						if (j == INSIDECODE_COLUMN_INDEX
								&& Pattern.compile("[\u4e00-\u9fa5]")
										.matcher(cellValue).find()) {
							errorList.add(new ExcelErrorVo(sheetTitles[j],
									"商品条码不能包含中文", i, j));
						}

						Object primary = isCommodityColumn ? commodityVO : productVO;
						BeanUtils.setProperty(primary, definition.getWritFieldName(), cellValue);
						for (ExcelColumnDefinition dependency : definition.getDependencies()) {
							CommodityPropVO item = (CommodityPropVO) CollectionUtils.find(properties.get(dependency.getReadFieldName()).value, new BeanPropertyEqualsPredicate("propValue", cellValue));
							if (item == null) {// 非空验证
								if (dependency.isRequired()) {
									errorList.add(new ExcelErrorVo(sheetTitles[j], sheetTitles[j] + "列填写错误,(请参照属性参照页填写)", i, j));
								}
							} else {
								BeanUtils.setProperty(primary, dependency.getWritFieldName(), item.getPropValueNo());
							}
						}
					}
				}
				// 填充商品扩展属性列表(以第一行为准)
				else if (i == startRowIndex) {
					EntryList entryList = properties.get(sheetTitles[j]);
					if (entryList == null) {
						errorList.add(new ExcelErrorVo(sheetTitles[j], sheetTitles[j] + "列获取属性页对照表失败,请重新下载模板", i, j));
					}
					Collection<CommodityPropVO> commodityPropVOs = CollectionUtils.select(entryList.value, new BeanPropertyBeIncludedPredicate("propValue", StringUtils.split(cellValue, ";")));
					if (commodityPropVOs.isEmpty() && (Boolean.TRUE.equals(entryList.key) || StringUtils.isNotBlank(cellValue))) {
						if (Boolean.TRUE.equals(entryList.key) && StringUtils.isBlank(cellValue)) {
							errorList.add(new ExcelErrorVo(sheetTitles[j], sheetTitles[j] + "列为必填项", i, j));
						} else {
							errorList.add(new ExcelErrorVo(sheetTitles[j], sheetTitles[j] + "列填写错误,(请参照属性参照页填写)", i, j));
						}
					} 
					if (!commodityPropVOs.isEmpty()) 
						commodityPropVOList.addAll(commodityPropVOs);
				}
				
				/*if (StringUtils.isNotBlank(productVO.getSizeName()) && !CollectionUtils.exists(properties.get(SIZE_NAME).value, new BeanPropertyEqualsPredicate("propValue", productVO.getSizeName()))) {
					errorList.add(new ExcelErrorVo(SIZE_NAME, SIZE_NAME + "列填写错误,(请参照属性参照页填写)", i, SIZENAME_COLUMN_INDEX));
				}*/
			}
			// 校验商品基础属性信息(品牌)
			if (StringUtils.isNotBlank(commodityVO.getBrandName()) && !CollectionUtils.exists(properties.get(BRAND_NAME).value, new BeanPropertyEqualsPredicate("propValue", commodityVO.getBrandName()))) {
				errorList.add(new ExcelErrorVo(BRAND_NAME, BRAND_NAME + "列填写错误,(请参照属性参照页填写)", i, BRANDNAME_COLUMN_INDEX));
			}
			
			productVOList.add(productVO);
		}

		if (CollectionUtils.isEmpty(productVOList)) {
			throw new IllegalArgumentException(WRITE_SHEET_NAME + "未存在款色编码为" + commodityVO.getSupplierCode() + "的数据行");
		}
		commodityVO.setProductMsgList(productVOList);
		// 填充商品扩展属性列表
		commodityVO.setCommodityPropVOList(commodityPropVOList);
		// 填充商品分类信息
		commodityVO.setCatNo(properties.get(CAT_NO).value.get(0).getPropValue());
		commodityVO.setCatStructname(properties.get(STRUCT_NAME).value.get(0).getPropValue());
		commodityVO.setCatName(properties.get(CAT_NAME).value.get(0).getPropValue());
		return commodityVO;
	}
	
	/**
	 * 解析属性页
	 * 
	 * @param sheet
	 * @return Map
	 * @throws Exception
	 */
	private Map<String, EntryList> resolvePropertiesSheet(XSSFSheet sheet) throws Exception {
		Map<String, EntryList> properties = new LinkedHashMap<String, EntryList>();
		// 解析标题
		XSSFRow titleRow = sheet.getRow(0);
		for (int k = 0; k < titleRow.getLastCellNum(); k++) {
			XSSFCell cell = titleRow.getCell(k);
			EntryList entryList = new EntryList();
			CommodityPropVO commodityPropVO = new CommodityPropVO();
			commodityPropVO.setPropItemName(super.getCellValue2(cell));
			XSSFComment cellComment = cell.getCellComment();
			if (cellComment != null) {
				Scanner scanner = new Scanner(cellComment.getString().getString());
				if (scanner.hasNextLine()) {
					entryList.key = REQUIRED_TEXT.equals(scanner.nextLine());
				}
				if (scanner.hasNextLine()) {
					commodityPropVO.setId(scanner.nextLine());
				}
				if (scanner.hasNextLine()) {
					commodityPropVO.setPropItemNo(scanner.nextLine());
				}
			}
			entryList.value = new ArrayList<CommodityPropVO>();
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				XSSFCell anotherCell = sheet.getRow(j).getCell(cell.getColumnIndex());
				if (anotherCell != null) {
					XSSFComment anotherCellComment = anotherCell.getCellComment();
					String anotherCellCommentContents = null;
					if (anotherCellComment != null) {
						anotherCellCommentContents = anotherCellComment.getString().getString();
						if (StringUtils.isBlank(anotherCellCommentContents)) {
							throw new IllegalArgumentException(MessageFormat.format(ERR_MSG_PATTERN, READ_SHEET_NAME, j + HEADER_LINES, commodityPropVO.getPropItemName(), "批注为空"));
						}
					}
					CommodityPropVO anotherCommodityPropVO = new CommodityPropVO();
					anotherCommodityPropVO.setId(commodityPropVO.getId());
					anotherCommodityPropVO.setPropItemName(commodityPropVO.getPropItemName());
					anotherCommodityPropVO.setPropItemNo(commodityPropVO.getPropItemNo());
					anotherCommodityPropVO.setPropValue(super.getCellValue(anotherCell));
					anotherCommodityPropVO.setPropValueNo(anotherCellCommentContents);
					entryList.value.add(anotherCommodityPropVO);
				}
			}
			properties.put(commodityPropVO.getPropItemName(), entryList);
		}
		return properties;
	}
	
	/**
	 * 解析商品页标题行
	 * 
	 * @param row
	 * @return String[]
	 * @throws Exception
	 */
	private String[] resolveSheetTitles(XSSFRow row) throws Exception {
		String[] titles = new String[row.getLastCellNum()];
		for (int i = 0; i < titles.length; i++) {
			titles[i] = super.getCellValue2(row.getCell(i));
		}
		return titles;
	}
	
	/**
	 * 内部商品类
	 * 
	 * @author yang.mq
	 *
	 */
	private class EntryList implements Map.Entry<Boolean, List<CommodityPropVO>> {

		private Boolean key;
		private List<CommodityPropVO> value;
		
		@Override
		public Boolean getKey() {
			return key;
		}

		@Override
		public List<CommodityPropVO> getValue() {
			return value;
		}

		@Override
		public List<CommodityPropVO> setValue(List<CommodityPropVO> value) {
			this.value = value;
			return value;
		}
	}
	
	public static void main(String[] args) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("C:\\dsdfds.xlsx"));
		ExcelToDataModelConverter converter = new ExcelToDataModelConverter();
		List<CommodityVO> list = converter.convert(workbook);
		for (CommodityVO commodityVO : list) {
			System.out.println(ToStringBuilder.reflectionToString(commodityVO, ToStringStyle.MULTI_LINE_STYLE));
			for (ProductVO productVO : commodityVO.getProductMsgList()) {
				System.out.println(ToStringBuilder.reflectionToString(productVO));
			}
			for (CommodityPropVO commodityPropVO : commodityVO.getCommodityPropVOList()) {
				System.out.println(ToStringBuilder.reflectionToString(commodityPropVO));
			}
		}
		
		//workbook.write(new FileOutputStream("C:\\dsdfds_err.xlsx"));
	}
}
