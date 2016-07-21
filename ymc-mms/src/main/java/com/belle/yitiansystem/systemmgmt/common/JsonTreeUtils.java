package com.belle.yitiansystem.systemmgmt.common;

import java.util.Set;

import com.belle.yitiansystem.systemmgmt.model.pojo.AuthorityResources;

public class JsonTreeUtils {
	
	/**
	 * 选中角色所拥有的
	 * @param jsonData
	 * @param roleResSet
	 * @return
	 */
	public static String selectRoleRes(String jsonData,Set<AuthorityResources> roleResSet){
		
		for (AuthorityResources res : roleResSet) {
			if("1".equals(res.getIsleaf())) {
				jsonData = replaceJsonData(jsonData,res.getId(),"checked","false","true");    //将角色所具有的角色选中
			}
		
		}
		return jsonData;
	}
	
	/**
	 * 根据指定字符 替换相隔最近的json属性的值
	 * @param json		
	 * @param target	相对字符
	 * @param key		json属性键
	 * @param value		要替换的属性值
	 * @param repalceValue	替换的数据
	 * @return
	 */
	public static String replaceJsonData(String json,String target,String key,String value,String repalceValue){
		
		int targetIndex = json.indexOf(target); //获取 相对字符的位置
		
		int keyIndex = json.indexOf(key, targetIndex + target.length());  //重相对字符会开始定位要替换属性索引
		
		int valueIndex = json.indexOf(value,keyIndex+key.length());  //获取要替换值的索引
		
		StringBuffer buffer = new StringBuffer().append(json);
		buffer.replace(valueIndex, valueIndex+value.length(), repalceValue);
		
		return buffer.toString();
	}

}
