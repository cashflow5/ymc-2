package com.yougou.kaidian.stock.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

/**
 * 导出excel
 * 
 * @author CYJ
 * 
 */
public class ExportHelper {

	/**
	 * 
	 * 方法描述：excel导出(POI)
	 * 
	 * @author CYJ
	 * @date May 4, 2011 4:22:24 PM
	 * @param templatePath
	 *            模板路径
	 * @param exportPath
	 *            导出文件路径
	 * @param fileName
	 *            文件名
	 * @param sheetName
	 * @param list
	 * @param listAmount
	 * @param paras
	 *            {(开始行),(列数)}
	 * @param numCol
	 *            数值列 {1,2,3,....}
	 * @param amountCol
	 *            "合计"标题 {(开始列),(结束列)}
	 * @param indexMap
	 *            指定索引值(0,3)....
	 * @param bIndex
	 *            是否有序号
	 * @param response
	 */
	public void doExport(List list, String templatePath, String fileName, String sheetName, Object[] listAmount,
			int[] paras, int[] numCol, int[] amountCol, Map<Integer, Integer> indexMap, boolean bIndex,
			HttpServletResponse response) {

		FileInputStream fileInputStream = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		OutputStream outputStream = null;
		try {

			fileInputStream = new FileInputStream(new File(templatePath));
			workbook = new HSSFWorkbook(fileInputStream);
			List<List<Object>> batchList = getListPage(list, 60000);
			if (null != batchList && batchList.size() > 0) {
				for (int i = 0; i < batchList.size(); i++) {
					List subList = batchList.get(i);
					workbook.setSheetName(i, sheetName + "_" + i);
					sheet = workbook.getSheetAt(i);
					writeExcel(subList, sheet, listAmount, paras, numCol, amountCol, indexMap, bIndex);
				}
			}
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
			outputStream = response.getOutputStream();
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != fileInputStream) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入excel(指定索引值写入列)(POI)
	 * 
	 * @param list
	 *            结果集
	 * @param sheet
	 * @param listAmount
	 *            合计结果集
	 * @param paras
	 *            {(开始行),(总列数)}
	 * @param numCol
	 *            数值列 {1,2,3,....}
	 * @param amountCol
	 *            "合计"标题 {(开始列),(结束列数)}
	 * @param indexMap
	 *            指定索引值(0,3)....
	 * @throws Exception
	 */
	public void writeExcel(List list, HSSFSheet sheet, Object[] listAmount, int[] paras, int[] numCol, int[] amountCol,
			Map<Integer, Integer> indexMap, boolean bIndex) {
		int row = paras[0];
		int column = paras[1];
		try {
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					HSSFRow hssfRow = ((HSSFSheet) sheet).createRow(row);

					if (bIndex) {
						for (int n = 0; n < column; n++) {
							Integer objIndex = indexMap.get(n);
							if (n == 0) {
								HSSFCell cell = hssfRow.createCell((short) n);
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								cell.setCellValue(i + 1);
							} else {
								if (compareInt(numCol, n)) {
									HSSFCell cell = hssfRow.createCell((short) n);
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									cell.setCellValue(Double.parseDouble(obj[objIndex] == null ? "0" : obj[objIndex]
											.toString()));
								} else if (!compareInt(numCol, n)) {
									HSSFCell cell = hssfRow.createCell((short) n);
									cell.setCellType(HSSFCell.CELL_TYPE_STRING);
									cell.setCellValue(obj[objIndex] == null ? "" : obj[objIndex].toString());
								}
							}
						}
					} else {
						for (int n = 0; n < column; n++) {
							Integer objIndex = indexMap.get(n);
							if (compareInt(numCol, n)) {
								HSSFCell cell = hssfRow.createCell((short) n);
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								cell.setCellValue(Double.parseDouble(obj[objIndex] == null ? "0" : obj[objIndex]
										.toString()));
							} else if (!compareInt(numCol, n)) {
								HSSFCell cell = hssfRow.createCell((short) n);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								cell.setCellValue(obj[objIndex] == null ? "" : obj[objIndex].toString());
							}
						}
					}
					row++;
				}
				if (null != listAmount && listAmount.length > 0 && null != amountCol) {
					int startCol = amountCol[0];
					int endCol = amountCol[1];

					HSSFRow hssfRow = ((HSSFSheet) sheet).createRow(row);
					HSSFCell cell = hssfRow.createCell((short) startCol);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					Region region = new Region(row, (short) startCol, row, (short) endCol);
					sheet.addMergedRegion(region);
					cell.setCellValue("合计");
					for (int i = 0; i < listAmount.length; i++) {

						HSSFCell amountCell = hssfRow.createCell((short) row);
						amountCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						amountCell.setCellValue(Double.parseDouble(listAmount[i] == null ? "0" : listAmount[i]
								.toString()));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean compareInt(int[] intArray, int initInt) {
		boolean ck = false;
		if (null != intArray) {
			for (int i = 0; i < intArray.length; i++) {
				if (initInt == intArray[i]) {
					ck = true;
					break;
				}
			}
		}
		return ck;
	}

	/**
	 * 导出excel
	 * 
	 * @param filePath
	 * @param fileName
	 * @param response
	 */
	public void export(String filePath, String fileName, HttpServletResponse response) {
		BufferedInputStream br = null;
		OutputStream outputStream = null;
		try {
			File file = new File(filePath);
			byte[] buf = new byte[1024];
			int len = 0;

			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			br = new BufferedInputStream(new FileInputStream(file));
			outputStream = response.getOutputStream();
			while ((len = br.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != br) {
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 组合List字段
	 * 
	 * @param list
	 * @param mixIndexMap
	 *            组合索引[(0,(2,4)),(1,(3,5))]:第0列是0,2,4列合并，第1列是1,3,5列合并
	 * @return
	 */
	public List<Object> mixField(List<Object> list, Map<Integer, Integer[]> mixIndexMap) {
		List<Object> listTmp = new ArrayList<Object>();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				for (int n = 0; n < obj.length; n++) {
					if (null != mixIndexMap.get(n)) {
						Integer[] mixIndex = mixIndexMap.get(n);
						String mixer = obj[n] == null ? "" : obj[n].toString();
						String mixTarget = "";
						for (int k = 0; k < mixIndex.length; k++) {
							mixTarget += obj[mixIndex[k]] == null ? "" : obj[mixIndex[k]].toString();
						}
						obj[n] = mixer + mixTarget;
					}
				}
				listTmp.add(obj);
			}
		}
		return listTmp;
	}

	/**
	 * 
	 * @description LIST分页
	 * @author CYJ
	 * @date Nov 28, 2011 10:46:01 AM
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public List<List<Object>> getListPage(List<Object> list, Integer pageSize) {
		List<List<Object>> batchList = new ArrayList<List<Object>>();
		// 总记录数
		Integer rowCount = 0;
		if (null != list && list.size() > 0) {
			rowCount = list.size();
		}
		// 记录分页数
		Integer listPage = 0;
		if (rowCount % pageSize == 0) {
			listPage = rowCount / pageSize;
		} else {
			listPage = rowCount / pageSize + 1;
		}
		Integer pageNo = 0;
		// 数据分页
		for (int n = 0; n < listPage; n++) {
			pageNo = n + 1;
			// 每页开始行
			Integer startIndex = pageNo * pageSize - pageSize;
			// 每页结束行
			Integer endIndex = 0;
			if (startIndex + pageSize > rowCount) {
				endIndex = rowCount;
			} else {
				endIndex = startIndex + pageSize;
			}
			// 子Map
			List<Object> tmpObjsList = new ArrayList<Object>();
			// 划分list
			for (int i = startIndex; i < endIndex; i++) {
				tmpObjsList.add(list.get(i));
			}
			batchList.add(tmpObjsList);
		}
		return batchList;
	}
}
