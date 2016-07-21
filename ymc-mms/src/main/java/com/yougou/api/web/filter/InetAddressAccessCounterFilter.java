package com.yougou.api.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.web.servlet.AbstractFilter;


/**
 * IP地址过滤器访问记数器
 * 
 * @author 杨梦清
 * 
 */
public class InetAddressAccessCounterFilter extends AbstractFilter {
	
	@Resource
	private GenericMongoDao genericMongoDao;
	
//	@Resource
//	private IApiVisitCounterService apiVisitCounterService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String inetAddress = getClientIp(request);
		DBCollection dbCollection = genericMongoDao.getCollection(MONGODB_COLLECTION_NAME);
		DBObject condition = new BasicDBObject(MONGODB_DBOBJECT_IDENTIFIER, inetAddress);
		DBObject dbObject = dbCollection.findOne(condition);
		if (dbObject == null) {
			DBObject variable = new BasicDBObject();
			variable.put(MONGODB_DBOBJECT_VARIABLE_1, System.currentTimeMillis());
			variable.put(MONGODB_DBOBJECT_VARIABLE_2, NumberUtils.INTEGER_ONE);
			dbObject = new BasicDBObject();
			dbObject.put(MONGODB_DBOBJECT_IDENTIFIER, inetAddress);
			dbObject.put(MONGODB_DBOBJECT_VARIABLE, variable);
			dbCollection.insert(dbObject);
		} else {
			dbCollection.update(condition, new BasicDBObject("$inc", new BasicDBObject(MONGODB_DBOBJECT_VARIABLE + "." + MONGODB_DBOBJECT_VARIABLE_2, getFilterRuleAsInt())));
		}
			
//		try {
//			apiVisitCounterService.createOrUpdateApiVisitCounter(getClientIp(request));
//		} catch (Exception ex) {
//			throw new YOPRuntimeException(YOPBusinessCode.ERROR, "Increment visit counter error.");
//		}
	
		chain.doFilter(request, response);
	}
}

