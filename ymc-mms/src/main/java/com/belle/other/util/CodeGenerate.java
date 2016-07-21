package com.belle.other.util;

import java.util.Random;

import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.TimeUtils;

public class CodeGenerate {
	
	public static String getSupplierCode() {
		String supplierCode="";
		String prefix = "SP";
		String dateOrder = TimeUtils.getFormatDate(DateUtil.getCurrentDateTime(), "yyyyMMdd");
		Random rand = new Random();
		for (int j = 0; j < 6; j++) {
			supplierCode += rand.nextInt(10);
		}
		return prefix + dateOrder + supplierCode;
	}
	
	public static String getPurchaseCode(){
		String purchaseCode="";
		String prefix="PC";
		String dateOrder=TimeUtils.getFormatDate(DateUtil.getCurrentDateTime(), "yyyyMMdd");		
		Random rand = new Random();
		for (int j = 0; j < 6; j++) {
			purchaseCode+=rand.nextInt(10);
		}
		return prefix+dateOrder+purchaseCode;
	}
	
}
