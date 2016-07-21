package com.belle.infrastructure.util;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JsonUtil {
	/*
	 * 过滤自身关联
	 */
	public static JsonConfig filterJsonConfig(final String ... params){
		JsonConfig config = new JsonConfig();
        config.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object arg0, String arg1, Object arg2) {
            	for(String par : params){
            		if(arg1.equals(par)){
            			return true;
            		}
            	}
                return false;
            }
        });
        return config;
	}
}
