package com.yougou.kaidian.framework.permission;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.vo.AuthorityComparator;
import com.yougou.kaidian.common.vo.MerchantsAuthorityVo;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.XmlTool;
import com.yougou.kaidian.user.dao.MerchantUsersMapper;
import com.yougou.kaidian.user.service.impl.MerchantUsersServiceImpl;


/**
 * ClassName: ResourceMonitor
 * Desc: 容器启动时，查询数据库查询每个商家对应的菜单资源
 * date: 2015-6-9 下午5:46:49
 * @author li.n1 
 * @since JDK 1.6
 */
@Service
public class ResourceMonitor implements InitializingBean {
	
	@Resource
	private MerchantUsersMapper merchantUsersMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 属性设置之后调用
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		long enter = System.currentTimeMillis();
		Set<String> authrityUrls = null;
		Set<String> allAuthrityUrls = new HashSet<String>();
		List<String> userIdList = merchantUsersMapper.getAllMerchant();
		Map<String,Map<String, List<MerchantsAuthorityVo>>> authMap = new HashMap<String,Map<String, List<MerchantsAuthorityVo>>>();
		Document document = XmlTool.createDocument(MerchantUsersServiceImpl.class
 				.getClassLoader().getResource("authority.xml").getPath());
 		Element root = XmlTool.getRootElement(document);
 		Element indexElement = root.element("index");
 		Element menuEle = null;
 		String key="";
 		List<MerchantsAuthorityVo> childAuthorityList=null;
 		Map<String, List<MerchantsAuthorityVo>> _map = null;
 		Map<String,Set<String>> authReourecesMap = new HashMap<String,Set<String>>();;
 		List<MerchantsAuthorityVo> auths = null;
 		Map<String,Integer> userAuthCount = new HashMap<String,Integer>();;
		for(String userId : userIdList){
			auths = merchantUsersMapper.getMerchantAuthorityVoById(userId);
			_map = new TreeMap<String, List<MerchantsAuthorityVo>>(new AuthorityComparator());
			authrityUrls = new HashSet<String>();
			for(MerchantsAuthorityVo _authority : auths){
				if (StringUtils.isNotBlank(_authority.getAuthrityURL())){
	 				for(Iterator<Element> it = root.elementIterator();it.hasNext();){
	 					menuEle = it.next();
	 					if(_authority.getAuthrityURL().indexOf(XmlTool.getAttributeVal(menuEle, "url"))!=-1){
	 						for(Iterator<Element> eit = menuEle.elementIterator();eit.hasNext();){
	 							authrityUrls.add(XmlTool.getAttributeVal(eit.next(), "url"));
	 						}
	 						//it.remove();
	 						break;
	 	 				}
	 				}
	 				authrityUrls.add("/"+_authority.getAuthrityURL());
	 			}
				key=_authority.getParentAuthrityName()+"@~"+_authority.getSortNo();
	 			childAuthorityList=_map.get(key);
	 			if(childAuthorityList==null){
	 				childAuthorityList=new ArrayList<MerchantsAuthorityVo>();
	 	 			_map.put(key, childAuthorityList);
	 			}
	 			childAuthorityList.add(_authority);
			}
			
			for(Iterator<Element> indexIt = indexElement.elementIterator();indexIt.hasNext();){
				authrityUrls.add(XmlTool.getAttributeVal(indexIt.next(), "url"));
			}
			authReourecesMap.put(userId, authrityUrls);
			authMap.put(userId, _map);
			userAuthCount.put(userId, auths!=null?auths.size():0);
		}
		for(Iterator<Element> it = root.elementIterator();it.hasNext();){
			menuEle = it.next();
			allAuthrityUrls.add("/"+XmlTool.getAttributeVal(menuEle, "url"));
			for(Iterator<Element> childIt = menuEle.elementIterator();childIt.hasNext();){
				allAuthrityUrls.add(XmlTool.getAttributeVal(childIt.next(), "url"));
			}
		}
		redisTemplate.opsForHash().put(CacheConstant.C_USER_REOURCE_AUTH, "authReourecesMap", authReourecesMap);
		redisTemplate.opsForHash().put(CacheConstant.C_USER_AUTH, "authMap", authMap);
		redisTemplate.opsForHash().put(CacheConstant.C_All_RESOURCE, "allResource", allAuthrityUrls);
		redisTemplate.opsForHash().put(CacheConstant.C_USERS_AUTH_COUNT, "authCount", userAuthCount);
		System.out.println("======load auth resource file spent time(unit:s):======="+(System.currentTimeMillis()-enter)/1000.0);
	}
	
	
	/**
	 * loadAuthResource:修改单个用户的权限redis缓存
	 * @author li.n1 
	 * @param userId
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public void loadAuthResource(String userId) throws Exception{
		long enter = System.currentTimeMillis();
		Map<String,Set<String>> authReseMap = 
				(Map<String,Set<String>>)redisTemplate.opsForHash().get(CacheConstant.C_USER_REOURCE_AUTH, "authReourecesMap");
		Map<String,Map<String, List<MerchantsAuthorityVo>>> aMap = 
				(Map<String,Map<String, List<MerchantsAuthorityVo>>>)redisTemplate.opsForHash().get(CacheConstant.C_USER_AUTH, "authMap");
		Map<String,Integer> userAuthCountMap = 
				(Map<String,Integer>)redisTemplate.opsForHash().get(CacheConstant.C_USERS_AUTH_COUNT,"authCount");
		Set<String> authrityUrls = null;
		Document document = XmlTool.createDocument(MerchantUsersServiceImpl.class
 				.getClassLoader().getResource("authority.xml").getPath());
 		Element root = XmlTool.getRootElement(document);
 		Element indexElement = root.element("index");
 		Element menuEle = null;
 		String key="";
 		List<MerchantsAuthorityVo> childAuthorityList=null;
 		Map<String, List<MerchantsAuthorityVo>> _map = null;
 		List<MerchantsAuthorityVo> auths = merchantUsersMapper.getMerchantAuthorityVoById(userId);
		_map = new TreeMap<String, List<MerchantsAuthorityVo>>(new AuthorityComparator());
		authrityUrls = new HashSet<String>();
		for(MerchantsAuthorityVo _authority : auths){
			if (StringUtils.isNotBlank(_authority.getAuthrityURL())){
 				for(Iterator<Element> it = root.elementIterator();it.hasNext();){
 					menuEle = it.next();
 					if(_authority.getAuthrityURL().indexOf(XmlTool.getAttributeVal(menuEle, "url"))!=-1){
 						for(Iterator<Element> eit = menuEle.elementIterator();eit.hasNext();){
 							authrityUrls.add(XmlTool.getAttributeVal(eit.next(), "url"));
 						}
 						break;
 	 				}
 				}
 				authrityUrls.add("/"+_authority.getAuthrityURL());
 			}
			key=_authority.getParentAuthrityName()+"@~"+_authority.getSortNo();
 			childAuthorityList=_map.get(key);
 			if(childAuthorityList==null){
 				childAuthorityList=new ArrayList<MerchantsAuthorityVo>();
 	 			_map.put(key, childAuthorityList);
 			}
 			childAuthorityList.add(_authority);
		}
		
		for(Iterator<Element> indexIt = indexElement.elementIterator();indexIt.hasNext();){
			authrityUrls.add(XmlTool.getAttributeVal(indexIt.next(), "url"));
		}
		
		if(authReseMap!=null){
			authReseMap.remove(userId);
			authReseMap.put(userId, authrityUrls);
			redisTemplate.opsForHash().put(CacheConstant.C_USER_REOURCE_AUTH, "authReourecesMap", authReseMap);
		}
		if(aMap!=null){
			aMap.remove(userId);
			aMap.put(userId, _map);
			redisTemplate.opsForHash().put(CacheConstant.C_USER_AUTH, "authMap", aMap);
		}
		if(userAuthCountMap!=null){
			userAuthCountMap.remove(userId);
			userAuthCountMap.put(userId, auths!=null?auths.size():0);
			redisTemplate.opsForHash().put(CacheConstant.C_USERS_AUTH_COUNT, "authCount", userAuthCountMap);
		}
		System.out.println("======spent time(unit:s):======="+(System.currentTimeMillis()-enter)/1000.0);
	}
	
}
