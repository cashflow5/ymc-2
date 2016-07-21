package com.yougou.kaidian.bi.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.yougou.kaidian.bi.service.IReportFavoriteService;
import com.yougou.kaidian.common.util.UUIDGenerator;

/** 
 * 商家中心数据报表-收藏夹操作服务类单元测试
 * @author zhang.f1
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class TestReportFavoriteServiceImpl extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Resource
	private IReportFavoriteService reportFavoriteService;
	
	
	@Test
	@Rollback(false)
	public void addFavoriteClassify()  {
		Map params = new HashMap();
		String id = UUIDGenerator.getUUID();
		System.out.println(id+"----------");
		params.put("id",id);
		params.put("classify_name", "单元测试归类4444");
		params.put("merchant_code", "shangjia001");
		params.put("login_name", "zhangfeng");
	
		try {
			reportFavoriteService.addFavoriteClassify(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void queryFavoriteClassify()  {
		Map params = new HashMap();
		params.put("merchant_code", "shangjia001");
		params.put("login_name", "zhangfeng");
	
		try {
			List<Map> list = reportFavoriteService.queryFavoriteClassify(params);
			System.out.println(list+"-------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void addFavoriteCommodity()  {
		Map params = new HashMap();
		String id = UUIDGenerator.getUUID();
		System.out.println(id+"----------");
		params.put("id",id);
		params.put("commodity_no", "sp002");
		params.put("merchant_code", "shangjia001");
		params.put("login_name", "zhangfeng");
	
		try {
			reportFavoriteService.addFavoriteCommodity(params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void queryFavoriteCommodity()  {
		Map params = new HashMap();
		params.put("merchant_code", "shangjia001");
		params.put("login_name", "zhangfeng");
	
		try {
			List<Map> list = reportFavoriteService.queryFavoriteCommodity(params);
			System.out.println(list+"-------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void addFavoriteCommodityClassify()  {
		Map params = new HashMap();
		params.put("fvr_commodity_id", "a3315dfefec84e12b5525364ad94615e");
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("id", UUIDGenerator.getUUID());
		map.put("classify_id","98c11c25c8334c568cdfe9a75d9980d4");
		list.add(map);
		Map map2 = new HashMap();
		map2.put("id", UUIDGenerator.getUUID());
		map2.put("classify_id","221a5f2fd3884160a917a59ea4245ed2");
		list.add(map2);
		params.put("classify_ids", list);
	
		try {
			reportFavoriteService.addFavoriteCommodityClassify(params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void batchAddFavoriteCommodityClassify()  {
		Map params = new HashMap();
		//批量收藏商品
		List<String> fvrList = new ArrayList<String>();
		fvrList.add("a3315dfefec84e12b5525364ad94615e");
		
		//批量归类
		List<String> claList = new ArrayList<String>();
		claList.add("221a5f2fd3884160a917a59ea4245ed2");
		claList.add("f50628c937ea40e1bdcefd7096f8a1ff");
		
		List<Map> list = new ArrayList<Map>();
		for(String fvr_id : fvrList){
			
			for(String cla : claList){
				Map map = new HashMap();
				map.put("id", UUIDGenerator.getUUID());
				map.put("classify_id",cla);
				map.put("fvr_commodity_id",fvr_id);
				list.add(map);
				
			}
		}
		params.put("comClaList", list);
		params.put("fvr_commodity_ids", fvrList);
	
		try {
			reportFavoriteService.batchAddFavoriteCommodityClassify(params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Test
	@Rollback(false)
	public void deleteFavoriteCommodity()  {
	
		try {
			reportFavoriteService.deleteFavoriteCommodityById("4cfe4c843f424c64888d9d2796bc26e0");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
