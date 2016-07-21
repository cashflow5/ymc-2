package com.yougou.kaidian.taobao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Brand;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.PropImg;
import com.taobao.api.domain.PropValue;
import com.taobao.api.domain.SellerAuthorize;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemcatsAuthorizeGetRequest;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.request.ItempropsGetRequest;
import com.taobao.api.request.ItempropvaluesGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemcatsAuthorizeGetResponse;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.ItempropvaluesGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.yougou.kaidian.taobao.service.ITaobaoDataImportService;

@ContextConfiguration(locations = { "classpath:applicationContext-Test.xml" })
public class TaobaoTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	private static final Logger log = LoggerFactory.getLogger(TaobaoTest.class);
	
	@Resource
	private ITaobaoDataImportService taobaoDataImportService;
	
	private static final String url = "http://121.196.131.76/router/rest";//Constants.PRODUCT_CONTAINER_URL;//
	private static final String appkey = "12273709";//"12575262";
	private static final String secret = "35a0307451295936122dd8ccb7e74ae3";//"0c6095945537a6b7ec3404bb7f0398df";
	private static final String sessionKey = "ZXhwaXJlc19pbj0zMTUzNjAwMSZpZnJhbWU9MSZyMV9leHBpcmVzX2luPTMxNTM2MDAxJnIyX2V4cGlyZXNfaW49MzE1MzYwMDEmcmVfZXhwaXJlc19pbj0wJnJlZnJlc2hfdG9rZW49NjEwMDExODNmZjBiYzIwMTlmMTcyYWUzMzYxYzkyNmRjNmE5MTZmMWJlZWFkMWExNjU3ODQ3ODQyJnN1Yl90YW9iYW9fdXNlcl9pZD0xNjU3ODQ3ODQyJnN1Yl90YW9iYW9fdXNlcl9uaWNrPTE1t9bW08bsvaK16jq8vMr1JnRzPTE0MDk3OTU1MDI2MDgmdmlzaXRvcl9pZD0xNjA3ODI4MzI5JnZpc2l0b3Jfbmljaz0xNbfW1tPG7L2iteomdzFfZXhwaXJlc19pbj0zMTUzNjAwMSZ3Ml9leHBpcmVzX2luPTMxNTM2MDAx";//"6101322c1f3e419c1fa2bcfe0b4ef7198abaae7c6b0a444708668355";
//	private static final String appkey = "12575262";
//	private static final String secret = "0c6095945537a6b7ec3404bb7f0398df";
//	private static final String sessionKey = "6101322c1f3e419c1fa2bcfe0b4ef7198abaae7c6b0a444708668355";
	

	public static void main(String[] args) {
		
//		getItemcatsAuthorizeGet();
//		getRootItemcatsGet("30");
//		System.out.println(getItempropsGet(50025883L,20021l,null));
		System.out.println(getItempropvaluesGet(50010825L,"13021751:27119251"));
//		System.out.println(getItemsOnsale());
//		System.out.println(getItemGet(35549671733L));
	}
	/**
	 * 获取授权类目（分类、品牌）
	 * @return
	 */
	public static String getItemcatsAuthorizeGet(){
		String result = "";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ItemcatsAuthorizeGetRequest req = new ItemcatsAuthorizeGetRequest();
		req.setFields("brand.vid, brand.name, brand.pid, brand.prop_name, item_cat.cid, item_cat.name, item_cat.status,item_cat.sort_order,item_cat.parent_cid,item_cat.is_parent, xinpin_item_cat.cid, xinpin_item_cat.name, xinpin_item_cat.status, xinpin_item_cat.sort_order, xinpin_item_cat.parent_cid, xinpin_item_cat.is_parent");
		try {
			ItemcatsAuthorizeGetResponse response = client.execute(req , sessionKey);
			if(response.getErrorCode() != null){
				System.out.println(sessionKey + response.getErrorCode() + response.getMsg());
			} else {
			SellerAuthorize p = response.getSellerAuthorize();
			List<Brand> lstBrand = p.getBrands();
			if(lstBrand != null){
			for(Brand b : lstBrand){
//				System.out.format("品牌：%s\t%s\t%s\t%s\n",b.getVid(),b.getPid(),b.getName(),b.getPropName());
			}
			}
			List<ItemCat> lstItemCat = p.getItemCats();
			if(lstItemCat != null){
			for(ItemCat c : lstItemCat){
				System.out.format("授权分类：%s\t%s\t%s\t%s\t%s\n",c.getCid(),c.getName(),c.getParentCid(),c.getSortOrder(),c.getIsParent());
//				getItemcatsGet(c.getCid());
			}
			}
			}
//			result = response.getBody();
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取分类
	 * @return
	 */
	public static String getRootItemcatsGet(String cid){
		String result = "";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		ItemcatsGetRequest req = new ItemcatsGetRequest();
		req.setFields("cid,parent_cid,name,is_parent,sort_order,status");
		req.setCids(cid);
		try {
			ItemcatsGetResponse response = client.execute(req);
			if(response.getErrorCode() != null){
				System.out.println(sessionKey + response.getErrorCode() + response.getMsg());
			} else {
				result = response.getBody();
				List<ItemCat> lstItemCat = response.getItemCats();
				for(ItemCat i : lstItemCat) {
					if(i.getIsParent()) {
//						System.out.format("%s\t%s\t%s\n", i.getCid(), i.getName(), "");
						log.error("{}\t{}", i.getCid(), i.getName());
						getItemcatsGet(i.getCid());
					} else {
//						System.out.format("%s\t%s\n",i.getCid(),i.getName());
						getItempropsGet(i.getCid());
					}
				}
			}
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取分类
	 * @return
	 */
	public static String getItemcatsGet(long pid){
		String result = "";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		ItemcatsGetRequest req = new ItemcatsGetRequest();
		req.setFields("cid,parent_cid,name,is_parent,sort_order,status");
		req.setParentCid(pid);// 50011740,50006843,50006842,50023724
//		req.setCids("50012028");
		try {
			ItemcatsGetResponse response = client.execute(req);
			if(response.getErrorCode() != null){
				System.out.println(sessionKey + response.getErrorCode() + response.getMsg());
			} else {
				result = response.getBody();
				List<ItemCat> lstItemCat = response.getItemCats();
				String levelName = "";
//				String remark = "";
//				level ++;
//				if(level == 2) {
//					levelName = "\t\t";
//					remark = "【二级分类】";
//				} else if (level == 3){
//					levelName = "\t\t\t\t";
//					remark = "【三级分类】";
//				}
				for(ItemCat i : lstItemCat) {
					if(i.getIsParent()) {
//						System.out.format("%s\t%s\t%s\n", i.getCid(), i.getName(), "");
						log.error("\t");
						log.error("{}\t{}", i.getCid(), i.getName());
						getItemcatsGet(i.getCid());
					} else {
						levelName = i.getCid()+"\t"+i.getName()+"\t";
//						System.out.format("%s\t%s\t%s\n",i.getCid(),i.getName(), "");
//						log.error("{}\t{}", i.getCid(), i.getName());
						getItempropsGet(i.getCid());
					}
				}
			}
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 分类属性
	 * @return
	 */
	public static String getItempropsGet(Long cid){
		return getItempropsGet(cid, null, null);
	}
	/**
	 * 分类属性
	 * @return
	 */
	public static String getItempropsGet(Long cid, Long pid, String childPath){
		String result = "";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ItempropsGetRequest req=new ItempropsGetRequest();//status(状态。可选值:normal(正常),deleted(删除))
		req.setFields("pid,cid,parent_pid,must,multi,prop_values,status,sort_order,parent_vid,name,parent_vid,is_sale_prop,is_color_prop,is_enum_prop,is_item_prop,features,child_template,is_allow_alias,is_input_prop,is_parent");
		req.setCid(cid);
		if(pid != null) {
			req.setPid(pid);
		}
		if(childPath != null) {
			req.setChildPath(childPath);
		}
		try {
			ItempropsGetResponse response = client.execute(req);
			if(response.getErrorCode() != null){
				System.out.println(sessionKey + response.getErrorCode() + response.getMsg());
			} else {
				result = response.getBody();
				List<ItemProp> lstItemProp = response.getItemProps();
				String propName = "";
				if(lstItemProp != null) {
					for (ItemProp i : lstItemProp) {
						if(i.getPid() == 20000l) {
//							System.out.format("分类属性：%s\t%s\n",i.getPid(), i.getName());
//							List<PropValue> lstPropValue = i.getPropValues();
//							if (lstPropValue != null) {
//								int s = 0;
//								for (PropValue p : lstPropValue) {
//									System.out.format("品牌：%s\t%s\n", p.getVid(), p.getName());
//									s ++;
//								}
//								System.out.println("品牌数为："+s);
//							}
						} else {
//							System.out.format(levelName+"%s\t%s\t%s\n",i.getPid(), i.getName(), "");
							propName = i.getPid()+"\t"+i.getName()+"\t";
//							if(i.getPid().equals(20021l) || i.getPid() == 20021l) {
//								System.out.println(levelName + "\n");
//							}
							List<PropValue> lstPropValue = i.getPropValues();
							if (lstPropValue != null) {
								for (PropValue p : lstPropValue) {
									if(childPath != null) {
										log.error("\t\t"+propName+"{}\t{}", p.getVid(), p.getName());
									} else {
										log.error(propName+"{}\t{}", p.getVid(), p.getName());
									}
//									System.out.format(levelName+"%s\t%s\t%s\n", p.getVid(), p.getName(), "");
									if(p.getIsParent() != null && p.getIsParent()) {
										String childPath2 = i.getPid()+":"+p.getVid();
										getItempropsGet(cid, null, childPath2);
									}
								}
							}
						}
					}
				}
			}
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/**
	 * 分类属性值
	 * @return
	 */
	public static String getItempropvaluesGet(long cid, String pvs){
		String result = "";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ItempropvaluesGetRequest req=new ItempropvaluesGetRequest();
		req.setFields("cid,pid,prop_name,vid,name,name_alias");
		req.setCid(cid);
		req.setPvs(pvs);
//		if(pvs != null && "".equals(pvs)) {
//			req.setPvs("20000");
//		}
//		req.setType(1L);
		try {
			ItempropvaluesGetResponse response = client.execute(req);
			result = response.getBody();
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取商家在售商品
	 * @return
	 */
	public static long getItemsOnsale(long pageNo, long pageSize){
		long result = 0;
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ItemsOnsaleGetRequest req=new ItemsOnsaleGetRequest();
		req.setFields("num_iid,cid,title,price,nick");
		req.setPageNo(pageNo);
		req.setOrderBy("list_time:desc");
//		req.setIsTaobao(true);
		req.setPageSize(pageSize);
		try {
			ItemsOnsaleGetResponse response = client.execute(req , sessionKey);
			if(response.getErrorCode() != null){
				System.out.println(sessionKey + response.getErrorCode() + response.getMsg());
			} else {
				result = response.getTotalResults();
				List<Item> lstItem = response.getItems(); 
				for(Item i : lstItem){
					System.out.format("在售商品：%s\t%s\t%s\t%s\t%s\n",i.getNumIid(), i.getCid(), i.getTitle(), i.getPrice(), i.getNick());
				}
			}
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static String getItemGet(long numIid){
		String result = "";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ItemGetRequest req=new ItemGetRequest();
		String item_info = "cid,num_iid,title,price,desc_modules,props_name,props,item_imgs,prop_imgs,pic_url";
		String item_imgs = ",item_img.url,item_img.position,item_img.id";
		String prop_imgs = ",prop_img.id,prop_img.url,prop_img.properties,prop_img.position";
		String skus = ",sku.sku_id,sku.num_iid,sku.outer_id,sku.barcode,sku.properties,sku.properties_name,sku.quantity";
		req.setFields(item_info+item_imgs+prop_imgs+skus);
		req.setNumIid(numIid);
		try {
			ItemGetResponse response = client.execute(req , sessionKey);
			Item item = response.getItem();
			System.out.format("商品基本信息：%s\t%s\t%s\t%s\n",item.getCid(),item.getNumIid(),item.getTitle(),item.getPicUrl());
			List<ItemImg> lstItemImg = item.getItemImgs();
			for(ItemImg i : lstItemImg){
				System.out.format("商品图片：%s\t%s\t%s\n",i.getId(),i.getUrl(),i.getPosition());
			}
			List<PropImg> lstPropImg = item.getPropImgs();
			for(PropImg p : lstPropImg){
				System.out.format("商品属性图片：%s\t%s\t%s\t%s\n",p.getId(),p.getUrl(),p.getPosition(),p.getProperties());
			}
			List<Sku> lstSku = item.getSkus();
			for(Sku s : lstSku){
				System.out.format("货品信息：%s\t%s\t%s\t%s\t%s\n",s.getSkuId(),s.getNumIid(),s.getOuterId(),s.getPrice(),s.getQuantity());
			}
		} catch (ApiException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
}
