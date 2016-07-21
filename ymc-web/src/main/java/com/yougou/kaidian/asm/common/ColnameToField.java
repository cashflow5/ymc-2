/** 
 * Project Name:ymc-web 
 * File Name:ColnameToField.java 
 * Package Name:com.yougou.kaidian.asm.common 
 * Date:2014-9-5下午1:56:58 
 * 
*/  
  
package com.yougou.kaidian.asm.common;  
/** 
 * ClassName:ColnameToField 
 * Date:     2014-9-5 下午1:56:60 
 * @author   li.n1 
 * @since    JDK 1.6 
 * @see       
 */
public class ColnameToField {
		private String colname;//列名
		
		private String fieldName;//类属性名
		
		public ColnameToField(String fieldName, String colname){
			this.colname = colname;
			this.fieldName = fieldName;
		}

		public String getColname() {
			return colname;
		}

		public void setColname(String colname) {
			this.colname = colname;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
}
