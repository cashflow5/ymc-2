package com.yougou.kaidian.user.model.vo;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class AuthorityComparator implements Comparator<String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(String o1, String o2) {
		if (o1 == null || o2 == null)
			return 0;
		if(o1==o2)
			return 0;
		int sortNo1=Integer.parseInt(StringUtils.substringAfterLast(o1, "@~"));
		int sortNo2=Integer.parseInt(StringUtils.substringAfterLast(o2, "@~"));
		if(!o1.equals(o2)&&(sortNo1-sortNo2)==0){
			return 1;
		}
		return sortNo1-sortNo2;
	}

}
