package com.belle.other.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * @author Akim
 *
 */
public class CalculateUtils {
	
	private static CalculateUtils calculateUtils = new CalculateUtils();
	
	private CalculateUtils() {}
	
	public static CalculateUtils getInstance() {
		return calculateUtils;
	}
	
	public static double add(double one, double two, int num) {
		
		BigDecimal bdOne = new BigDecimal(one);
		BigDecimal bdTwo = new BigDecimal(two);
		BigDecimal total = bdOne.add(bdTwo);
		
		BigDecimal divisor = new BigDecimal(1);
		BigDecimal result = total.divide(divisor, num, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
		
	}
	
	public static double subtract(double one, double two, int num) {
		
		BigDecimal bdOne = new BigDecimal(one);
		BigDecimal bdTwo = new BigDecimal(two);
		BigDecimal total = bdOne.subtract(bdTwo);
		
		BigDecimal divisor = new BigDecimal(1);
		BigDecimal result = total.divide(divisor, num, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
		
	}
	
	public static double multiply(double one, double two, int num) {
		
		BigDecimal bdOne = new BigDecimal(one);
		BigDecimal bdTwo = new BigDecimal(two);
		BigDecimal total = bdOne.multiply(bdTwo);
		
		BigDecimal divisor = new BigDecimal(1);
		BigDecimal result = total.divide(divisor, num, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
		
	}
	
	public static double divide(double one, double two, int num) {
		
		BigDecimal bdOne = new BigDecimal(one);
		BigDecimal bdTwo = new BigDecimal(two);
		
		BigDecimal total = bdOne.divide(bdTwo, num, BigDecimal.ROUND_HALF_UP);
		
		return total.doubleValue();
		
	}
	
	public static double convertStringToDouble(String d) throws ParseException {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();  
        dfs.setDecimalSeparator('.');  
        dfs.setGroupingSeparator(',');  
        dfs.setMonetaryDecimalSeparator('.');  
        DecimalFormat df = new DecimalFormat("###,###.##",dfs);  
          
        Number num = df.parse(d);  
        return num.doubleValue(); 
	}

}
