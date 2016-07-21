package com.belle.yitiansystem.merchant.util;

public class StatusUtil {

	// 店铺：状态解析
	public static String getStatusName(String access,int status){
		if( "N".equalsIgnoreCase(access) ){
			return "已关闭";
		}else{
			if ( 0==status ){
				return "新建";
			}else if ( 1==status ){
				return "已发布";
			}else if ( 2==status ){
				return "装修中";
			}else{
				return "其它";
			}
		}
	}
	// 店铺：审核状态解析
	public static String getAuditName(String auditFlag){
		if("1".equals(auditFlag)){
			return "不需要审核";
		}else{
		    return "需要审核";
		}
	}
}
