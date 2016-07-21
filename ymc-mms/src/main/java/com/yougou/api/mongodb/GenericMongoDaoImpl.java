package com.yougou.api.mongodb;

import java.util.List;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBEncoder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * 默认 MongoDB 数据访问对象
 * 
 * @author 杨梦清
 * 
 */
public class GenericMongoDaoImpl extends MongoDaoSupport implements GenericMongoDao {

	@Override
	public boolean collectionExists(String collectionName) {
		return getDb().collectionExists(collectionName);
	}

	@Override
	public DBCollection createCollection(String collectionName, DBObject dbObject) {
		return getDb().createCollection(collectionName, dbObject);
	}

	@Override
	public DBCollection getCollection(String collectionName) {
		return getDb().getCollection(collectionName);
	}

	@Override
	public DBObject uniqueDBObject(String collectionName, DBObject condition) {
		return getCollection(collectionName).findOne(condition);
	}

	@Override
	public List<DBObject> getDBObject(String collectionName, DBObject condition) {
		return getDBObject(collectionName, condition, (DBObject) null);
	}

	@Override
	public List<DBObject> getDBObject(String collectionName, DBObject condition, DBObject orderBy) {
		return getCollection(collectionName).find(condition).sort(orderBy).toArray();
	}

	@Override
	public PageFinder<DBObject> getDBObject(String collectionName, DBObject condition, Query query) {
		return getDBObject(collectionName, condition, (DBObject) null, query);
	}

	@Override
	public PageFinder<DBObject> getDBObject(String collectionName, DBObject condition, DBObject orderBy, Query query) {
		int skip = (Math.max(query.getPage() - 1, 0) * query.getPageSize());
		int limit = query.getPageSize();
		DBCursor dbCursor = getCollection(collectionName).find(condition);
		int count = 5000;//mongoDB有条件的count查询很慢。默认采用这种方法来避免有条件count问题
		if(query.isShowCount()){
			count = dbCursor.size();
		}
		List<DBObject> list = dbCursor.sort(orderBy).skip(skip).limit(limit).toArray();
		return new PageFinder<DBObject>(query.getPage(), query.getPageSize(), count, list);
	}
	
	@Override
	public WriteResult insert(String collectionName, DBObject dbObject) {
		return getCollection(collectionName).insert(dbObject);
	}

	@Override
	public WriteResult insert(String collectionName, DBObject[] dbObjects) {
		return getCollection(collectionName).insert(dbObjects);
	}

	@Override
	public WriteResult insert(String collectionName, List<DBObject> dbObjects) {
		return getCollection(collectionName).insert(dbObjects);
	}

	@Override
	public WriteResult insert(String collectionName, DBObject[] dbObjects, WriteConcern writeConcern) {
		return getCollection(collectionName).insert(dbObjects, writeConcern);
	}

	@Override
	public WriteResult insert(String collectionName, DBObject[] dbObjects, WriteConcern writeConcern, DBEncoder dbEncoder) {
		return getCollection(collectionName).insert(dbObjects, writeConcern, dbEncoder);
	}

	@Override
	public DBObject getDBObjectByGroup(String collectionName,DBObject key, DBObject cond, DBObject initial, String reduce) {
		return getCollection(collectionName).group(key, cond, initial, reduce);	
	
	}

}
