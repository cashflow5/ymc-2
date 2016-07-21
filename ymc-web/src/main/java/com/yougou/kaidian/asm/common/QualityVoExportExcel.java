/** 
 * Project Name:ymc-web 
 * File Name:QualityVoExportExcel.java 
 * Package Name:com.yougou.kaidian.asm.common 
 * Date:2014-9-5下午2:53:28 
 */   
package com.yougou.kaidian.asm.common;  

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yougou.kaidian.asm.vo.QualityExportVo;

/** 
 * ClassName: QualityVoExportExcel
 * Desc: 质检导出
 * date: 2014-9-5 下午2:53:28
 * @author li.n1 
 * @since JDK 1.6 
 */
public class QualityVoExportExcel extends ExportExcelTool<QualityExportVo> {

	/**
	 * @param dataset
	 * @param path
	 */
	public QualityVoExportExcel(Collection<QualityExportVo> dataset,
			String path, String fileName, String title) {
		super(dataset, path);
		/* 设置Excel基本数据 */  
        // 定义列名与类属性名的对应关系  
        //  
        List<ColnameToField> colNameToField = new ArrayList<ColnameToField>() {  
            {  
                //add(new ColnameToField("orderNo", "订单号")); 如果注释，此列将不会导出
            	add(new ColnameToField("orderNo", "订单号"));
                add(new ColnameToField("expressCode", "收货快递单号"));  
                add(new ColnameToField("expressName", "快递公司"));  
                add(new ColnameToField("insideCode", "货品条码"));  
                add(new ColnameToField("supplierCode", "款色编码"));
                add(new ColnameToField("prodName", "商品名称"));  
                add(new ColnameToField("status", "质检状态"));  
                add(new ColnameToField("userName", "收货人姓名"));  
                add(new ColnameToField("saleType", "售后类型"));  
                add(new ColnameToField("remark", "退换货原因")); 
            }  
        };
        super.setTitle(title);// 标题，以查询的起止日期作为标题  
        super.setFileName(fileName);// Excel文件名(以站点名作为文件名)  
        super.setDateFormat("yyyy-MM-dd HH:mm:ss");  
        // 设置列名对应的属性名集合(其顺序与表头列名属性一致)  
        super.setColNameToField(colNameToField);  
	}
	
	/**
	 * @param dataset
	 * @param path
	 */
	public QualityVoExportExcel(Collection<QualityExportVo> dataset,String title) {
		super(dataset);
		/* 设置Excel基本数据 */  
        // 定义列名与类属性名的对应关系  
        List<ColnameToField> colNameToField = new ArrayList<ColnameToField>() {  
            {  
                //add(new ColnameToField("orderNo", "订单号")); 如果注释，此列将不会导出
            	add(new ColnameToField("orderNo", "订单号"));
                add(new ColnameToField("expressCode", "收货快递单号"));  
                add(new ColnameToField("expressName", "快递公司"));  
                add(new ColnameToField("insideCode", "货品条码"));  
                add(new ColnameToField("supplierCode", "款色编码"));
                add(new ColnameToField("prodName", "商品名称"));  
                add(new ColnameToField("status", "质检状态"));  
                add(new ColnameToField("userName", "收货人姓名"));  
                add(new ColnameToField("saleType", "售后类型"));  
                add(new ColnameToField("remark", "退换货原因")); 
            }  
        };
        super.setTitle(title);// 标题，以查询的起止日期作为标题  
        super.setDateFormat("yyyy-MM-dd HH:mm:ss");  
        // 设置列名对应的属性名集合(其顺序与表头列名属性一致)  
        super.setColNameToField(colNameToField);  
	}

}
