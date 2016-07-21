package com.yougou.kaidian.commodity.convert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.kaidian.commodity.beans.ExcelColumnDefinition;
import com.yougou.kaidian.commodity.beans.TurePredicate;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.merchant.MerchantImportModel;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.pc.model.sizechart.SizeChart;

/**
 * 商品导入模式转换成XLS
 * 
 * @author yang.mq
 *
 */
public class DataModelToExcelConverter extends AbstractConverter<MerchantImportModel, XSSFWorkbook> {
	
	private static final  Logger LOGGER = LoggerFactory.getLogger(DataModelToExcelConverter.class);
	
	private static final String[] EXCLUDE_PROPERTIES = { "所在区域", "名厂直销品牌", "名厂直销分类" };
	
	private TurePredicate turePredicate = new TurePredicate();

	private Category category;
	
	public DataModelToExcelConverter(Category category) {
		this.category = category;
	}

	@Override
	public XSSFWorkbook convert(MerchantImportModel importModel) {
		// 创建 XLS
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建 XLS 必填样式
		XSSFFont requiredFont = workbook.createFont();
		requiredFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		requiredFont.setFontHeightInPoints((short) 12);
		XSSFCellStyle requiredCellStyle = workbook.createCellStyle();
		requiredCellStyle.setFont(requiredFont);
		requiredCellStyle.setLocked(true);
		// 创建 XLS 选填样式
		XSSFFont optionalFont = workbook.createFont();
		optionalFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		optionalFont.setFontHeightInPoints((short) 12);
		XSSFCellStyle optionalCellStyle = workbook.createCellStyle();
		optionalCellStyle.setFont(optionalFont);
		optionalCellStyle.setLocked(true);
		// 创建 XLS 页
		XSSFSheet writeSheet = workbook.createSheet(WRITE_SHEET_NAME);
		XSSFSheet readSheet = workbook.createSheet(READ_SHEET_NAME);
		// 列默认样式
		XSSFCellStyle stringCellStyle = workbook.createCellStyle();
		stringCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
		writeSheet.setDefaultColumnStyle(IDENTITY_COLUMN_INDEX, stringCellStyle);
		writeSheet.setDefaultColumnStyle(STYLENO_COLUMN_INDEX, stringCellStyle);
		//writeSheet.setDefaultColumnStyle(THIRDPRATYCODE_COLUMN_INDEX, stringCellStyle);
		writeSheet.setDefaultColumnStyle(INSIDECODE_COLUMN_INDEX, stringCellStyle);
		// 创建XLS页备注
		XSSFDrawing writeSheetDrawing = writeSheet.createDrawingPatriarch();
		XSSFDrawing readSheetDrawing = readSheet.createDrawingPatriarch();
		// 创建XLS页数据行
		XSSFRow writeSheetTitleRow = writeSheet.createRow(0);
		XSSFRow readSheetTitleRow = readSheet.createRow(0);
		// 填充XLS模板页
		try {
			// 列索引
			int writeCellIndex = 0, readCellIndex = 0;
			for (ExcelColumnDefinition columnDefinition : COLUMN_DEFINITIONS) {
				Object fieldValue = columnDefinition.isConstField() ? columnDefinition.getReadFieldName() : PropertyUtils.getProperty((IDENTIFIER_COMMODITY.equals(columnDefinition.getIdentifier()) ? importModel : importModel.getProductImportModel()), columnDefinition.getReadFieldName());
				XSSFCellStyle style = columnDefinition.isRequired() ? requiredCellStyle : optionalCellStyle;
				String text = columnDefinition.isRequired() ? REQUIRED_TEXT : OPTIONAL_TEXT;
				super.createCell(writeSheetTitleRow, writeCellIndex++, String.class.cast(fieldValue), style, writeSheetDrawing, text);
			}
			// 填充属性页
			super.createCell(readSheetTitleRow, readCellIndex++, BRAND_NAME, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			super.createCell(readSheetTitleRow, readCellIndex++, COLOR_NAME, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			readSheet.setColumnHidden(readCellIndex - 1, true);
			super.createCell(readSheetTitleRow, readCellIndex++, SIZE_NAME, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			super.createCell(readSheetTitleRow, readCellIndex++, CAT_NO, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			readSheet.setColumnHidden(readCellIndex - 1, true);
			super.createCell(readSheetTitleRow, readCellIndex++, STRUCT_NAME, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			readSheet.setColumnHidden(readCellIndex - 1, true);
			super.createCell(readSheetTitleRow, readCellIndex++, CAT_NAME, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			readSheet.setColumnHidden(readCellIndex - 1, true);
			super.createCell(readSheetTitleRow, readCellIndex++, SIZE_CHART, requiredCellStyle, readSheetDrawing, REQUIRED_TEXT);
			// 计算最大总行数
			int maxRowIndex = 0, maxPropValueSize = 0;
			int brandListSize = CollectionUtils.countMatches(importModel.getBrandList(), turePredicate);
			maxRowIndex = Math.max(maxRowIndex, brandListSize);
			int colorListSize = CollectionUtils.countMatches(importModel.getColorList(), turePredicate);
			maxRowIndex = Math.max(maxRowIndex, colorListSize);
			int sizeListSize = CollectionUtils.countMatches(importModel.getSizeList(), turePredicate);
			maxRowIndex = Math.max(maxRowIndex, sizeListSize);
			int propListSize = CollectionUtils.countMatches(importModel.getPropList(), turePredicate);
			maxRowIndex = Math.max(maxRowIndex, propListSize);
			int sizeChartListSize = CollectionUtils.countMatches(importModel.getSizeChartList(), turePredicate);
			maxRowIndex = Math.max(maxRowIndex, sizeChartListSize);
			int[] propValueSizes = new int[propListSize];
			for (int i = 0; i < propListSize; i++) {
				PropItem propItem = importModel.getPropList().get(i);
				boolean isRequired = NumberUtils.INTEGER_ZERO.equals(propItem.getIsShowMall());
				String comment = isRequired ? REQUIRED_TEXT : OPTIONAL_TEXT;
				XSSFCellStyle style = (XSSFCellStyle)(isRequired ? requiredCellStyle : optionalCellStyle).clone();
				propValueSizes[i] = CollectionUtils.countMatches(propItem.getPropValues(), turePredicate);
				// 计算最大总行数
				maxRowIndex = Math.max(maxRowIndex, propValueSizes[i]);
				// 计算最大属性大小
				maxPropValueSize = Math.max(maxPropValueSize, propValueSizes[i]);
				// 只生成不被排除的属性
				if (!ArrayUtils.contains(EXCLUDE_PROPERTIES, propItem.getPropItemName())) {
					//为可以多选的属性所在单元格增加底色和批注 add by Meijunfeng
					if(1==propItem.getValueType()){
						comment=comment+"；可多选";
						style.setFillPattern(HSSFCellStyle.BIG_SPOTS);
						style.setFillForegroundColor(HSSFColor.YELLOW.index);
						style.setFillBackgroundColor(HSSFColor.YELLOW.index);
					}
					
					// 写入属性参照页标题
					StringBuilder commentBuilder = new StringBuilder();
					commentBuilder.append(comment).append("\n");
					commentBuilder.append(StringUtils.trimToEmpty(propItem.getId())).append("\n");
					commentBuilder.append(StringUtils.trimToEmpty(propItem.getPropItemNo()));
					super.createCell(readSheetTitleRow, readCellIndex++, propItem.getPropItemName(), style, readSheetDrawing, commentBuilder.toString());
					
					// 写入商品页标题
					if(1==propItem.getValueType()){
						comment=comment+"，属性之间以英文半角分号“;”隔开";
					}
					super.createCell(writeSheetTitleRow, writeCellIndex++, propItem.getPropItemName(), style, writeSheetDrawing, comment);
				}
			}
			for (int i = 0, j = 0; i < maxRowIndex; i++, j = 0) {
				XSSFRow row = readSheet.createRow(i + 1);
				// 品牌
				if (i < brandListSize) {
					Brand brand = importModel.getBrandList().get(i);
					super.createCell(row, j++, brand.getBrandName(), optionalCellStyle, readSheetDrawing, brand.getBrandNo());
				} else {
					j += 1;
				}
				// 颜色
				if (i < colorListSize) {
					PropValue propValue = importModel.getColorList().get(i);
					super.createCell(row, j++, propValue.getPropValueName(), optionalCellStyle, readSheetDrawing, propValue.getPropValueNo());
				} else {
					j += 1;
				}
				// 尺码大小
				if (i < sizeListSize) {
					PropValue propValue = importModel.getSizeList().get(i);
					super.createCell(row, j++, propValue.getPropValueName(), optionalCellStyle, readSheetDrawing, propValue.getPropValueNo());
				} else {
					j += 1;
				}
				// 分类
				if (i == 0) {
					super.createCell(row, j++, category.getCatNo(), optionalCellStyle);
					super.createCell(row, j++, category.getStructName(), optionalCellStyle);
					super.createCell(row, j++, category.getCatName(), optionalCellStyle);
				} else {
					j += 3;
				}
				// 尺码对照表ID
				if (i < sizeChartListSize) {
					SizeChart sizeChart = importModel.getSizeChartList().get(i);
					super.createCell(row, j++, ObjectUtils.toString(sizeChart.getId()), optionalCellStyle, readSheetDrawing, sizeChart.getChartName());
				} else {
					j += 1;
				}
				// 扩展属性
				if (i < maxPropValueSize) {
					for (int k = 0; k < propListSize; k++) {
						PropItem propItem = importModel.getPropList().get(k);
						if (!ArrayUtils.contains(EXCLUDE_PROPERTIES, propItem.getPropItemName())) {
							if (i < propValueSizes[k]) {
								PropValue propValue = propItem.getPropValues().get(i);
								super.createCell(row, j++, propValue.getPropValueName(), optionalCellStyle, readSheetDrawing, propValue.getPropValueNo());
							} else {
								j += 1;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.warn("template formal error.", ex);
			throw new RuntimeException(ex);
		}
		
		// 设置列宽
		for (int i = readSheetTitleRow.getLastCellNum() - 1; i >= 0; i--) {
			readSheetTitleRow.getSheet().setColumnWidth(i, 12 * 256);
		}
		for (int i =  writeSheetTitleRow.getLastCellNum() - 1; i >= 0; i--) {
			writeSheetTitleRow.getSheet().setColumnWidth(i, 10 * 256);
		}
		
		// 设置保护工作表
		readSheet.protectSheet(SHEET_PASSWD);
		
		return workbook;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Brand> brandList = new ArrayList<Brand>();
		Brand brandSimpleVO = new Brand();
		brandSimpleVO.setBrandNo("PP-01");
		brandSimpleVO.setBrandName("中国共产党");
		brandList.add(brandSimpleVO);
		brandSimpleVO = new Brand();
		brandSimpleVO.setBrandNo("PP-02");
		brandSimpleVO.setBrandName("中国国民党");
		brandList.add(brandSimpleVO);
		brandSimpleVO = new Brand();
		brandSimpleVO.setBrandNo("PP-03");
		brandSimpleVO.setBrandName("大清");
		brandList.add(brandSimpleVO);
		List<PropValue> colorList = new ArrayList<PropValue>();
		PropValue propValue = new PropValue();
		propValue.setPropValueName("白色");
		propValue.setPropValueNo("white");
		colorList.add(propValue);
		propValue = new PropValue();
		propValue.setPropValueName("黑色色");
		propValue.setPropValueNo("blank");
		colorList.add(propValue);
		
		List<PropValue> sizeList = new ArrayList<PropValue>();
		propValue = new PropValue();
		propValue.setPropValueName("32");
		propValue.setPropValueNo("SE");
		sizeList.add(propValue);
		
		List<PropItem> propList = new ArrayList<PropItem>();
		PropItem propItem = new PropItem();
		propItem.setId("ssdfdfs");
		propItem.setPropItemName("材料");
		propItem.setPropItemNo("CL");
		List<PropValue> propValues = new ArrayList<PropValue>();
		propValue = new PropValue();
		propValue.setPropValueName("牛皮");
		propValue.setPropValueNo("NP");
		propValues.add(propValue);
		propValue = new PropValue();
		propValue.setPropValueName("狗皮");
		propValue.setPropValueNo("GP");
		propValues.add(propValue);
		propItem.setPropValues(propValues);
		propList.add(propItem);
		propItem = new PropItem();
		propItem.setId("sss");
		propItem.setPropItemName("人气");
		propItem.setPropItemNo("RQ");
		propItem.setIsShowMall(0);
		propValues = new ArrayList<PropValue>();
		propValue = new PropValue();
		propValue.setPropValueName("一级");
		propValue.setPropValueNo("YG");
		propValues.add(propValue);
		propValue = new PropValue();
		propValue.setPropValueName("二级");
		propValue.setPropValueNo("EG");
		propValues.add(propValue);
		propItem.setPropValues(propValues);
		propValue = new PropValue();
		propValue.setPropValueName("三级");
		propValue.setPropValueNo("SG");
		propValues.add(propValue);
		propItem.setPropValues(propValues);
		propValue = new PropValue();
		propValue.setPropValueName("四级");
		propValue.setPropValueNo("SG");
		propValues.add(propValue);
		propItem.setPropValues(propValues);
		propValue = new PropValue();
		propValue.setPropValueName("五级");
		propValue.setPropValueNo("WG");
		propValues.add(propValue);
		propItem.setPropValues(propValues);
		propList.add(propItem);
		propItem = new PropItem();
		propItem.setId("s222ss");
		propItem.setPropItemName("性别");
		propItem.setPropItemNo("SEX");
		propValues = new ArrayList<PropValue>();
		propValue = new PropValue();
		propValue.setPropValueName("男");
		propValue.setPropValueNo("N");
		propValues.add(propValue);
		propItem.setPropValues(propValues);
		propList.add(propItem);
		
		List<SizeChart> sizeChartList = new ArrayList<SizeChart>();
		SizeChart sizeChart = new SizeChart();
		sizeChart.setId(999);
		sizeChart.setChartName("999尺码对照表");
		sizeChartList.add(sizeChart);
		
		MerchantImportModel importModel = new MerchantImportModel();
		importModel.setBrandList(brandList);
		importModel.setColorList(colorList);
		importModel.setSizeList(sizeList);
		importModel.setPropList(propList);
		importModel.setSizeChartList(sizeChartList);
		
		Category category = new Category();
		category.setCatNo("NUMBER-ONE");
		category.setStructName("1-1-1");
		category.setCatName("第一");
		
		DataModelToExcelConverter converter = new DataModelToExcelConverter(category);
		converter.convert(importModel).write(new FileOutputStream("c:\\dsdfds.xlsx"));
	}
}
