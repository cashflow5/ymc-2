package com.yougou.api.mongodb;

import java.util.List;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.DBCollection;
import com.mongodb.DBEncoder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface GenericMongoDao {

	/**
	 * 校验文档集合是事存在
	 * 
	 * @param collectionName
	 * @return boolean
	 */
	boolean collectionExists(String collectionName);

	/**
	 * 创建文档集合
	 * 
	 * @param collectionName
	 * @param dbObject
	 * @return DBCollection
	 */
	DBCollection createCollection(String collectionName, DBObject dbObject);

	/**
	 * 获取文档集合
	 * 
	 * @param collectionName
	 * @return DBCollection
	 */
	DBCollection getCollection(String collectionName);
	
	/**
	 * 首个查询
	 * 
	 * @param collectionName
	 * @param condition
	 * @return DBObject
	 */
	DBObject uniqueDBObject(String collectionName, DBObject condition);
	
	/**
	 * 列表查询
	 * 
	 * @param collectionName
	 * @param condition
	 * @return List
	 */
	List<DBObject> getDBObject(String collectionName, DBObject condition);
	
	/**
	 * 列表查询
	 * 
	 * @param collectionName
	 * @param condition
	 * @param orderBy
	 * @return List
	 */
	List<DBObject> getDBObject(String collectionName, DBObject condition, DBObject orderBy);
	
	/**
	 * 分页查询
	 * 
	 * @param collectionName
	 * @param condition
	 * @param query
	 * @return PageFinder
	 */
	PageFinder<DBObject> getDBObject(String collectionName, DBObject condition, Query query);
	
	/**
	 * 分页查询
	 * 
	 * @param collectionName
	 * @param condition
	 * @param orderBy
	 * @param query
	 * @return PageFinder
	 */
	PageFinder<DBObject> getDBObject(String collectionName, DBObject condition, DBObject orderBy, Query query);
	
	/**
	 * 插入单个文档
	 * 
	 * @param collectionName
	 * @param dbObject
	 * @return WriteResult
	 */
	WriteResult insert(String collectionName, DBObject dbObject);

	/**
	 * 插入多个文档
	 * 
	 * @param collectionName
	 * @param dbObjects
	 * @return WriteResult
	 */
	WriteResult insert(String collectionName, DBObject[] dbObjects);

	/**
	 * 插入多个文档
	 * 
	 * @param collectionName
	 * @param dbObjects
	 * @return WriteResult
	 */
	WriteResult insert(String collectionName, List<DBObject> dbObjects);

	/**
	 * 插入多个文档
	 * 
	 * @param collectionName
	 * @param dbObjects
	 * @param writeConcern
	 * @return WriteResult
	 */
	WriteResult insert(String collectionName, DBObject[] dbObjects, WriteConcern writeConcern);

	/**
	 * 插入多个文档
	 * 
	 * @param collectionName
	 * @param dbObjects
	 * @param writeConcern
	 * @param dbEncoder
	 * @return WriteResult
	 */
	WriteResult insert(String collectionName, DBObject[] dbObjects, WriteConcern writeConcern, DBEncoder dbEncoder);
	
	/**
	 * groupBy 查询
	 * @return
	 */
	DBObject getDBObjectByGroup(String collectionName,DBObject key, DBObject cond,DBObject initial,String reduce);

}
