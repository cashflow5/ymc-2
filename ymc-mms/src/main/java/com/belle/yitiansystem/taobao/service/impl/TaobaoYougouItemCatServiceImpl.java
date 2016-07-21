package com.belle.yitiansystem.taobao.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.taobao.dao.mapper.TaobaoItemCatPropMapper;
import com.belle.yitiansystem.taobao.dao.mapper.TaobaoYougouItemCatMapper;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoItemCat;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropDto;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValueDto;
import com.belle.yitiansystem.taobao.model.TaobaoItemProp;
import com.belle.yitiansystem.taobao.model.TaobaoItemPropValue;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemCat;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemProp;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemPropValue;
import com.belle.yitiansystem.taobao.model.ZTree;
import com.belle.yitiansystem.taobao.service.ITaobaoYougouItemCatService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;

@Service
public class TaobaoYougouItemCatServiceImpl implements ITaobaoYougouItemCatService{
	
	@Resource
	private TaobaoYougouItemCatMapper taobaoYougouItemCatMapper;
	
	@Resource
	private TaobaoItemCatPropMapper taobaoItemCatPropMapper;
	
	@Resource
	private ICommodityMerchantApiService commodityApi;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	private static final String PROBANDID = "proBandId",
			PROPVALBINDID = "propValBindId",
			TAOBAOPORPITEMNO = "taobaoPorpItemNo",
			TAOBAOPROVALUENO = "taobaoProValueNo";
	
	@Override
	public PageFinder<TaobaoYougouItemCat> findTaobaoYougouItemCatList(
			Map<String, Object> map, Query query) {
		if(map.get("catId")!=null&&!StringUtils.isEmpty(map.get("catId").toString())){
			Category category = commodityBaseApiService.getCategoryById(map.get("catId").toString());
			map.put("yougouCatNo", category.getCatNo());
		}
		RowBounds rowBounds = new RowBounds(query.getOffset(),
				query.getPageSize());
		int count = taobaoYougouItemCatMapper.selectTaobaoYougouItemCatCount(map);
		if (count == 0) {
			return new PageFinder<TaobaoYougouItemCat>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		List<TaobaoYougouItemCat> list = taobaoYougouItemCatMapper.selectTaobaoYougouItemCatList(map, rowBounds);
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<TaobaoYougouItemCat>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		PageFinder<TaobaoYougouItemCat> pageFinder = new PageFinder<TaobaoYougouItemCat>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}
	
	/**
	 * 获取优购没有绑定的分类
	 * @param structName
	 * @param merchantCode
	 * @return
	 */
	public List<ZTree> findNoBindYougouItemCatZTree(String structName, boolean filterBind) {
		List<ZTree> list = new ArrayList<ZTree>();
		Map<String,String> catMap = new HashMap<String,String>();
		if(filterBind){
			List<TaobaoYougouItemCat> templetList= taobaoYougouItemCatMapper.selectTaobaoYougouItemCatList(null);
			catMap = convertList2Map(templetList);
		}
		List<Category> _cats = new ArrayList<Category>();
		if(StringUtils.isEmpty(structName)){
			_cats = commodityBaseApiService.getAllCategories((short)1);
		}else{
			_cats =  commodityBaseApiService.getChildCategoryByStructName(structName);
		}
		//List<YougouCat> _catss = taobaoYougouItemCatMapper.selectYougouCatList(structName);
		ZTree zTree = null;
		if (CollectionUtils.isNotEmpty(_cats)) {
			for (Category category : _cats) {
				if (null != category && category.getDeleteFlag() == 1&&catMap.get(category.getCatNo())==null) {
					zTree = new ZTree();
					zTree.setId(category.getId());
					zTree.setpId(category.getParentId());
					zTree.setName(category.getCatName());
					zTree.setItemId(category.getId());
					zTree.setStructName(category.getStructName());
					if (category.getCatLeave() <= 2) {
						zTree.setParent(true);
						zTree.setHasChild(true);
					} else {
						zTree.setParent(false);
						zTree.setHasChild(false);
					}
					list.add(zTree);
				}
			}
		}
		/*
		if (CollectionUtils.isNotEmpty(_cats)) {
			Set<Category> set = new HashSet<Category>();
			// 查询first cat
			if (StringUtils.isEmpty(structName)) {
				for (Category _cat : _cats) {
					if (StringUtils.isEmpty(_cat.getStructName())
							|| _cat.getStructName().length() != 8)
						continue;
					set.add(_cat);
				}
			} else if (structName.trim().length() == 2) { // 查询second cat
				for (Category _cat : _cats) {
					if (StringUtils.isEmpty(_cat.getStructName())
							|| _cat.getStructName().length() != 8)
						continue;
					set.add(_cat);
				}
			} else if (structName.trim().length() == 5) { // 查询third cat
				for (Category _cat : _cats) {//过滤掉已经绑定的三级分类
					if (StringUtils.isEmpty(_cat.getStructName())|| _cat.getStructName().length() != 8||catMap.get(_cat.getCatNo())!=null)
						continue;
					set.add(_cat);
				}
			}
		}*/
		return list;
	}
	
	private Map<String,String> convertList2Map(List<TaobaoYougouItemCat> templetList){
		Map<String,String> map = new HashMap<String,String>();
		if(null!=templetList){
			for(TaobaoYougouItemCat temp:templetList){
				map.put(temp.getYougouCatNo(),temp.getYougouCatNo());
			}
		}
		return map;
	}
	
	public List<ZTree> findNoBindTaobaoItemCatList(Map<String,Object>map){
		List<TaobaoItemCat> itemList = taobaoYougouItemCatMapper.selectTaobaoItemCatList(map);
		List<ZTree> list = new ArrayList<ZTree>();
		if(itemList!=null){
			ZTree zTree = null;
			for(TaobaoItemCat cat:itemList){
				zTree = new ZTree();
				zTree.setId(cat.getCid().toString());
				zTree.setpId(cat.getParentCid().toString());
				zTree.setName(cat.getName());
				zTree.setItemId(cat.getCid().toString());
				zTree.setStructName(cat.getCid().toString());
				if (cat.getIsParent()) {
					zTree.setParent(true);
					zTree.setHasChild(true);
				} else {
					zTree.setParent(false);
					zTree.setHasChild(false);
				}
				list.add(zTree);
			}
		}
		return list;
	}
	
	
	@Override
	@Transactional
	public String bindYougouItemCate(Map<String, Object> params)
			throws BusinessException {
		String bindId="";
		String yougouItemId = String.valueOf(params.get("yougouItemId"));
		String taobaoCid = String.valueOf(params.get("taobaoCid"));
		if (StringUtils.isEmpty(yougouItemId)||StringUtils.isEmpty(taobaoCid)) {
			throw new BusinessException("请求参数错误");
		}

		Category category = commodityBaseApiService
				.getCategoryById(yougouItemId);
		if (category == null) {
			throw new BusinessException("优购分类不存在");
		}
		Map<String,Object> checkParm = new HashMap<String,Object>();
		checkParm.put("taobaoCid",taobaoCid);
		checkParm.put("yougouCatNo", category.getCatNo());
		int count = taobaoYougouItemCatMapper.selectTaobaoYougouItemCatCount(checkParm);
		if(count>0){
			throw new BusinessException("分类已经绑定");
		}
		TaobaoYougouItemCat itemCat = new TaobaoYougouItemCat();
		bindId = UUIDGenerator.getUUID();
		itemCat.setId(bindId);
		itemCat.setTaobaoCid(Long.valueOf(taobaoCid));
		itemCat.setTaobaoCatFullName(String.valueOf(params
				.get("taobaoCatFullName")));
		itemCat.setYougouCatNo(category.getCatNo());
		itemCat.setYougouCatStruct(category.getStructName());
		itemCat.setYougouCatFullName(String.valueOf(params
				.get("yougouCatFullName")));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		itemCat.setOperated(format.format(new Date()));
		itemCat.setOperater(String.valueOf(params
				.get("operater")));
		taobaoYougouItemCatMapper.insertYougouTaobaoItem(itemCat);
		return bindId;
	}

	@Override
	public TaobaoYougouItemCat findTaobaoYougouItemCatById(
			String id) {
		return taobaoYougouItemCatMapper.selectTaobaoYougouItemCatById(id);
	}
	
	
	@Override
	public List<PropItem> getPropMsgByCatIdNew(String catId, boolean showSize) {
		final String itemName1 = "所在区域";
		final String itemName2 = "名厂直销品牌";
		final String itemName3 = "名厂直销分类";
		final String itemName4 = "货品来源";
		final String SIZE = "size";
		final String SIZENAME = "尺码";
		List<PropItem> propItems =  commodityApi.getPropMsgByCatId(catId);
		if (showSize) {
			List<PropValue> sizeList = commodityApi.getSizeByCatId(catId);
			PropItem propItem = new PropItem();
			propItem.setIsShowMall(0);
			propItem.setPropItemNo(SIZE);
			propItem.setPropItemName(SIZENAME);
			propItem.setPropValues(sizeList);
			propItems.add(propItem);
		}
		Iterator<PropItem> iterator = propItems.iterator();
		PropItem item = null;
		String itemName = null;
		while (iterator.hasNext()) {
			item = iterator.next();
			itemName = item.getPropItemName();
			if (itemName.equals(itemName1) || itemName.equals(itemName2)
					|| itemName.equals(itemName3) || itemName.equals(itemName4)) {
				iterator.remove();
			}
		}
		// 根据必填属性排序
		Collections.sort(propItems, new ComparatorPropItem());

		return propItems;
	}

	class ComparatorPropItem implements Comparator {
		public int compare(Object arg0, Object arg1) {
			PropItem item1 = (PropItem) arg0;
			PropItem item2 = (PropItem) arg1;
			if (item1.getIsShowMall() == null || item2.getIsShowMall() == null) {
				item1.setIsShowMall(1);
				item2.setIsShowMall(1);
			}
			int flag = item1.getIsShowMall().compareTo(item2.getIsShowMall());
			return flag;
		}
	}

	@Override
	public List<TaobaoItemCatPropDto> selectItemProWidthBindYougouItemPro(
			String catBindId) {
		return taobaoItemCatPropMapper.selectItemProWidthBindYougouItemPro(catBindId);
	}

	/**
	 * 获取淘宝分类属性值
	 */
	@Override
	public List<TaobaoItemCatPropValueDto> selectTaobaoItemProValWidthYouItemProVal(Long pid, Long cid, String catBindId) {
		return taobaoItemCatPropMapper
				.selectTaobaoItemProValWidthYouItemProVal(pid, cid, catBindId);
	}
	
	
	@Override
	@Transactional
	public void bindTaobaoYougouItemProAndVal(HttpServletRequest request,String operater)
			throws BusinessException {
		String catId = request.getParameter("catId");
		String taobaoCid = request.getParameter("taobaoCid");
		String catBandId = request.getParameter("catBandId");
	    TaobaoYougouItemCat itemCat = this.taobaoYougouItemCatMapper.selectTaobaoYougouItemCatById(catBandId);
		if (StringUtils.isEmpty(catId) || StringUtils.isEmpty(taobaoCid)) {
			throw new BusinessException("请求参数错误");
		}
		
		
		TaobaoYougouItemProp prop = null;
		TaobaoYougouItemPropValue propVal = null;
	
		List<TaobaoYougouItemProp> propList4Add = new ArrayList<TaobaoYougouItemProp>();
		List<TaobaoYougouItemPropValue> propVlueList4Add = new ArrayList<TaobaoYougouItemPropValue>();
		
		StringBuilder errorMsg4Prop = new StringBuilder();
		StringBuilder errorMsg4PropValue = new StringBuilder();
		List<String> propNos = new ArrayList<String>();
		
		List<PropItem> yougouPropList = this.getPropMsgByCatIdNew(catId, true);
		Category category = commodityBaseApiService.getCategoryById(catId);
		String logStr ="";
		for (PropItem item : yougouPropList) {
			 
			String taobaoPorpItemNo = request.getParameter(item.getPropItemNo()
					+ TAOBAOPORPITEMNO);
			if (!StringUtils.isEmpty(taobaoPorpItemNo)) {
				if(propNos.contains(taobaoPorpItemNo)){
					TaobaoItemProp itemProp= this.taobaoItemCatPropMapper.selectTaobaoItemPropByPid(taobaoPorpItemNo);
					//重复的属性
					if(null!=itemProp){
						errorMsg4Prop.append("【<span style='color:red'>"+itemProp.getName()+"</span>】");
					}
					continue;
				}else{
					propNos.add(taobaoPorpItemNo);
				}
				prop = new TaobaoYougouItemProp();
				prop.setId(UUIDGenerator.getUUID()); // 新增
				if (item.getIsShowMall() != null
						&& item.getIsShowMall().intValue() == 0) { //必填
					prop.setIsYougouMust(1); 
				} else {      //非必填
					prop.setIsYougouMust(0);
				}
				prop.setOperater(operater);
				prop.setOperated(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(new Date()));
				prop.setTaobaoCid(Long.valueOf(taobaoCid));
				prop.setTaobaoPid(Long.valueOf(taobaoPorpItemNo));
				prop.setYougouCatNo(category.getCatNo());
				prop.setYougouPropItemNo(item.getPropItemNo());
				propList4Add.add(prop);
			
				
				// 获取属性值
				List<PropValue> propValues = item.getPropValues();
				
				List<String> taobaoPropValues = new ArrayList<String>();
				int index = 0;
				for (PropValue val : propValues) {
					String taobaoProVal = request.getParameter(val
							.getPropValueNo() + TAOBAOPROVALUENO);
					if(!StringUtils.isEmpty(taobaoProVal)){
						if(taobaoPropValues.contains(taobaoProVal)){
							TaobaoItemPropValue itemPropValue= this.taobaoItemCatPropMapper.selectTaobaoItemPropValueByVid(taobaoProVal);
							//重复的属性值
							if(null!=itemPropValue){
								if(index==0){
									TaobaoItemProp itemProp= this.taobaoItemCatPropMapper.selectTaobaoItemPropByPid(taobaoPorpItemNo);
									//重复的属性
									if(null!=itemProp){
										errorMsg4PropValue.append("<br>优购属性【<span style='color:red'>"+val.getPropItemName()+"</span>】对应的淘宝属性值");
									}
								}
								errorMsg4PropValue.append("【<span style='color:red'>"+itemPropValue.getName()+"</span>】");
								index++;
							}
							continue;
						}else{
							taobaoPropValues.add(taobaoProVal);
						}
						logStr+="【 淘宝Cid:"+Long.valueOf(taobaoCid)+" Pid:"+Long.valueOf(taobaoPorpItemNo)+" " +
								" 优购分类编号No: "+category.getCatNo()+" 优购属性No："+item.getPropItemNo() +
								" 属性值编号 ："+val.getPropValueNo()+"  属性值名称："+val.getPropValueName()+"   属性项编号："+val.getPropItemNo()+" 属性项名称："+val.getPropItemName()+" 】";
						propVal = new TaobaoYougouItemPropValue();
						propVal.setId(UUIDGenerator.getUUID());// 新增
						propVal.setYougouPropItemNo(item.getPropItemNo());
						propVal.setOperater(operater);
						propVal.setOperated(new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(new Date()));
						propVal.setTaobaoCid(Long.valueOf(taobaoCid));
						propVal.setTaobaoPid(Long.valueOf(taobaoPorpItemNo));
						propVal.setTaobaoVid(taobaoProVal);
						propVal.setYougouCatNo(category.getCatNo());
						propVal.setYougouPropValueNo(val.getPropValueNo());
						propVlueList4Add.add(propVal);
						
					}
				}
			}
		}
		
		if(!"".equals(errorMsg4Prop.toString())||!"".equals(errorMsg4PropValue.toString())){
			throw new BusinessException("淘宝属性"+errorMsg4Prop.toString()+"重复"+errorMsg4PropValue.toString()+"重复<br>请刷新页面");
		}
		//删除分类下的所有属性和属性值
		List<TaobaoYougouItemCat> list = new ArrayList<TaobaoYougouItemCat>();
		TaobaoYougouItemCat cat = new TaobaoYougouItemCat();
		cat.setTaobaoCid(Long.valueOf(taobaoCid));
		cat.setYougouCatNo(itemCat.getYougouCatNo());
		list.add(cat);
		this.taobaoItemCatPropMapper.deletePropBatchByCidAndCatNo(list);
		this.taobaoItemCatPropMapper.deletePropValueBatchByCidAndCatNo(list);
		
		if(!propList4Add.isEmpty()){
			taobaoItemCatPropMapper.insertTaobaoYougouItemPropBatch(propList4Add);
			merchantOperationLogService.addMerchantOperationLog(
					"TAOBAO_CAT_PRO_ADD", OperationType.TAOBAO_CAT_BIND,
					"淘宝分类与优购分类绑定新增： "+logStr, request);
		}
		if(!propVlueList4Add.isEmpty()){
			taobaoItemCatPropMapper.insertTaobaoYougouItemPropValueBatch(propVlueList4Add);
		}
		
		/*List<PropItem> yougouPropList = this.getPropMsgByCatIdNew(catId, true);
		Category category = commodityBaseApiService.getCategoryById(catId);
		// 根据优购分类属性获取淘宝分类属性，淘宝属性name是根究优购分类属性来的
		List<TaobaoYougouItemProp> propList4Add = new ArrayList<TaobaoYougouItemProp>();
		List<TaobaoYougouItemProp> propList4Update = new ArrayList<TaobaoYougouItemProp>();
		List<TaobaoYougouItemPropValue> propVlueList4Add = new ArrayList<TaobaoYougouItemPropValue>();
		List<TaobaoYougouItemPropValue> propVlueList4Update = new ArrayList<TaobaoYougouItemPropValue>();
		
		List<String> propList4del = new ArrayList<String>();
		
		TaobaoYougouItemProp prop = null;
		TaobaoYougouItemPropValue propVal = null;
	
		List<String> allTaobaoPropItemNo = new ArrayList<String>();
		Map<String,List<String>> allTaobaoPropValus = new HashMap<String,List<String>>();
		
		for (PropItem item : yougouPropList) {
			String taobaoPorpItemNo = request.getParameter(item.getPropItemNo()
					+ TAOBAOPORPITEMNO);
			String proBandId = request.getParameter(item.getPropItemNo()
					+ PROBANDID);
					
			if (!StringUtils.isEmpty(taobaoPorpItemNo)) {
				allTaobaoPropItemNo.add(taobaoPorpItemNo);
				prop = new TaobaoYougouItemProp();
				if (!StringUtils.isEmpty(proBandId)) {// 修改
					prop.setId(proBandId);
					propList4Update.add(prop);
				} else {
					prop.setId(UUIDGenerator.getUUID()); // 新增
					propList4Add.add(prop);
				}
				if (item.getIsShowMall() != null
						&& item.getIsShowMall().intValue() == 0) { //必填
					prop.setIsYougouMust(1); 
				} else {      //非必填
					prop.setIsYougouMust(0);
				}
				prop.setOperater(operater);
				prop.setOperated(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(new Date()));
				prop.setTaobaoCid(Long.valueOf(taobaoCid));
				prop.setTaobaoPid(Long.valueOf(taobaoPorpItemNo));
				prop.setYougouCatNo(category.getCatNo());
				prop.setYougouPropItemNo(item.getPropItemNo());

				// 获取属性值
				List<PropValue> propValues = item.getPropValues();
				
				
				
				for (PropValue val : propValues) {
					String taobaoProVal = request.getParameter(val
							.getPropValueNo() + TAOBAOPROVALUENO);
					String propValBindId = request.getParameter(val
							.getPropValueNo() + PROPVALBINDID);
					propVal = new TaobaoYougouItemPropValue();
					if (!StringUtils.isEmpty(propValBindId)) { // 修改
						propVal.setId(propValBindId);
						propVlueList4Update.add(propVal);
					} else if (StringUtils.isEmpty(propValBindId)
							&& !StringUtils.isEmpty(taobaoProVal)) {
						propVal.setId(UUIDGenerator.getUUID());// 新增
						propVlueList4Add.add(propVal);
					}
					propVal.setYougouPropItemNo(item.getPropItemNo());
					propVal.setOperater(operater);
					propVal.setOperated(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(new Date()));
					propVal.setTaobaoCid(Long.valueOf(taobaoCid));
					propVal.setTaobaoPid(Long.valueOf(taobaoPorpItemNo));
					propVal.setTaobaoVid(taobaoProVal);
					propVal.setYougouCatNo(category.getCatNo());
					propVal.setYougouPropValueNo(val.getPropValueNo());
				}
			}else if(StringUtils.isEmpty(taobaoPorpItemNo)&&!StringUtils.isEmpty(proBandId)){//删除属性
				propList4del.add(taobaoPorpItemNo);
			}
		}

		// 保存颜色属性
		
		 * String colorBandId = request.getParameter("colorBandId"); String
		 * colorTaobaoVid = request.getParameter("colorTaobaoVid"); String
		 * colorTaobaoVname = request.getParameter("colorTaobaoVname"); String
		 * colorToobaoPid = request.getParameter("colorToobaoPid"); propVal =
		 * new TaobaoYougouItemPropValue(); if
		 * (!StringUtils.isEmpty(colorBandId)) { // 修改
		 * propVal.setId(colorBandId); propVlueList4Update.add(propVal); } else
		 * if (!StringUtils.isEmpty(colorTaobaoVid)) {
		 * propVal.setId(UUIDGenerator.getUUID());// 新增
		 * propVlueList4Add.add(propVal); }
		 * propVal.setYougouPropItemNo("color");
		 * propVal.setMerchantCode(merchantCode); propVal.setOperater(operater);
		 * propVal.setOperated(formate.format(new Date()));
		 * propVal.setTaobaoCid(Long.valueOf(taobaoCid));
		 * propVal.setTaobaoPid(Long.valueOf(colorToobaoPid));
		 * propVal.setTaobaoVid(colorTaobaoVid);
		 * propVal.setYougouCatNo(category.getCatNo());
		 * propVal.setYougouPropValueNo(colorTaobaoVname);
		 * propVal.setNickId(itemCat.getNickId());
		 

		// 批量新增属性，属性值
		if (propList4Add.size() > 0) {
			taobaoItemCatPropMapper
					.insertTaobaoYougouItemPropBatch(propList4Add);
		}
		if (propVlueList4Add.size() > 0) {
			taobaoItemCatPropMapper
					.insertTaobaoYougouItemPropValueBatch(propVlueList4Add);
		}
		// 更新属性，属性值
		for (TaobaoYougouItemProp item : propList4Update) {
			taobaoItemCatPropMapper.updateTaobaoYougouItemProp(item);
		}
		List<String> delList = new ArrayList<String>();
		for (TaobaoYougouItemPropValue val : propVlueList4Update) {
			if (StringUtils.isEmpty(val.getTaobaoVid())) {
				delList.add(val.getId());
			} else {
				taobaoItemCatPropMapper.updateTaobaoYougouItemPropValue(val);
			}

		}
		//删除属性和属性值
		if(!propList4del.isEmpty()){
			
		}
		// 删除属性值
		if (!delList.isEmpty()) {
			taobaoItemCatPropMapper.deletePropValueBatch(delList);
		}*/
		
		
	}
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
	@Transactional
	public void deleteYougouTaobaoItemCat(String ids,HttpServletRequest request)throws BusinessException{
		if(StringUtils.isEmpty(ids)){
			throw new BusinessException("参数错误");
		}
		String[] idArray = ids.split(",");
		List<TaobaoYougouItemCat> list =new ArrayList<TaobaoYougouItemCat>();
		String logStr="";
		TaobaoYougouItemCat cat  = null;
		for(String id:idArray){
		    cat = taobaoYougouItemCatMapper.selectTaobaoYougouItemCatById(id);
			if(null!=cat){
				list.add(cat);
				logStr+=" 【 淘宝分类："+cat.getTaobaoCatFullName()+" 淘宝分类CID:"+cat.getTaobaoCid()+" 优购分类："+cat.getYougouCatFullName()+"优购分类CID"+cat.getTaobaoCid()+"】";
			}
		}
		if(!list.isEmpty()){
			//批量删除分类绑定
			taobaoYougouItemCatMapper.deleteYougouTaobaoItemCatBatch(list);
			//批量删除属性
			taobaoItemCatPropMapper.deletePropBatchByCidAndCatNo(list);
			//批量删除属性值
			taobaoItemCatPropMapper.deletePropValueBatchByCidAndCatNo(list);
		}
		
		merchantOperationLogService.addMerchantOperationLog(
				"TAOBAO_CAT_PRO_DELETE", OperationType.TAOBAO_CAT_BIND,
				"删除 淘宝优购分类绑定 "+logStr, request);
	}
}
