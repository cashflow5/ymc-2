package com.belle.yitiansystem.merchant.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Controller
 * 
 * @history 2011-12-13
 */
public class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Map<String, Object> builderParams(HttpServletRequest request,
			boolean isDecode) throws UnsupportedEncodingException {
		
		Map params = request.getParameterMap();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (params != null && params.size() > 0) {
			for (Iterator iterator = params.entrySet().iterator(); iterator
					.hasNext();) {
				Entry p = (Entry) iterator.next();
				if (p.getValue() != null
						&& !StringUtils.isEmpty(p.getValue().toString())) {
					String values[] = (String[]) p.getValue();
					// p.setValue(values[0]);
					if (isDecode) {
						resultMap.put(String.valueOf(p.getKey()), URLDecoder
								.decode(values[0] == null ? null : values[0]
										.trim(), "UTF-8"));
					} else {
						resultMap.put(String.valueOf(p.getKey()),
								values[0] == null ? null : values[0].trim());
					}

				}
			}
		}
		return resultMap;
	}
}
