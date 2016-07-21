package com.yougou.kaidian.taobao.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yougou.kaidian.commodity.dao.CommodityPropertyMapper;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.dao.TaobaoAuthinfoMapper;
import com.yougou.kaidian.taobao.dao.TaobaoBrandMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemCatMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemCatPropMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropValueMapper;
import com.yougou.kaidian.taobao.dao.TaobaoYougouItemPropMapper;
import com.yougou.kaidian.taobao.enums.BindType;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoAuthinfoDto;
import com.yougou.kaidian.taobao.model.TaobaoItemCat;
import com.yougou.kaidian.taobao.model.TaobaoItemCatPropDto;
import com.yougou.kaidian.taobao.model.TaobaoItemCatPropValueDto;
import com.yougou.kaidian.taobao.model.TaobaoYougouBrand;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemProp;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValue;
import com.yougou.kaidian.taobao.model.ZTree;
import com.yougou.kaidian.taobao.service.ITaobaoBasisService;
import com.yougou.kaidian.taobao.service.ITaobaoDataImportService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.tools.common.utils.DateUtil;

@Service
public class TaobaoBasisServiceImpl implements ITaobaoBasisService {
	
	private static final Logger log = LoggerFactory.getLogger(TaobaoAuthServiceImpl.class);
	@Resource
	private TaobaoBrandMapper taobaoBrandMapper;

	@Resource
	private TaobaoItemCatMapper taobaoItemCatMapper;
	
	@Resource
	private TaobaoItemCatPropMapper taobaoItemCatPropMapper;

	@Resource
	private TaobaoItemPropValueMapper taobaoItemPropValueMapper;

	@Resource
	private TaobaoYougouItemPropMapper taobaoYougouItemPropMapper;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;

	@Resource
	private ITaobaoDataImportService taobaoDataImportService;

	@Resource
	ICommodityMerchantApiService commodityApi;

	@Resource
	private IMerchantCommodityService merchantCommodityService;

	@Resource
	private TaobaoAuthinfoMapper taobaoAuthinfoMapper;
	
	@Resource
	private CommodityPropertyMapper catMapper;

	private static final String PROBANDID = "proBandId",
			PROPVALBINDID = "propValBindId",
			TAOBAOPORPITEMNO = "taobaoPorpItemNo",
			TAOBAOPROVALUENO = "taobaoProValueNo";

	public TaobaoYougouItemCat getTaobaoYougouItemCatById(String id) {
		return taobaoItemCatMapper.getTaobaoYougouItemCatById(id);
	}
	
	@Override
	public int findTaobaoYougouBrandCount(Map<String, Object> params) {
		return taobaoBrandMapper.selectTaobaoYougouBrandCount(params);
	}

	@Override
	public PageFinder<TaobaoYougouBrand> findTaobaoYougouBrandList(
			Map<String, Object> map, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(),
				query.getPageSize());
		List<TaobaoYougouBrand> list = taobaoBrandMapper
				.selectTaobaoYougouBrandList(map, rowBounds);
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<TaobaoYougouBrand>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		int count = this.findTaobaoYougouBrandCount(map);
		PageFinder<TaobaoYougouBrand> pageFinder = new PageFinder<TaobaoYougouBrand>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}

	@Override
	@Transactional
	public void saveTaobaoYougouBrand(String dataStr, String merchantCode)
			throws BusinessException {
		log.info("[淘宝导入]商家编码:{}-保存淘宝优购品牌。dataStr:{}", merchantCode, dataStr);
		if (StringUtils.isEmpty(dataStr)) {
			throw new BusinessException("请求参数错误!");
		}
		TaobaoYougouBrand brand = null;
		String[] datas = dataStr.split(",");
		for(String data:datas){
			if (StringUtils.isEmpty(data)) {
				throw new BusinessException("请求参数错误!");
			}
			brand = new TaobaoYougouBrand();
			String[] subDatas = data.split("\\|");
			brand.setId(subDatas[0]);
			brand.setYougouBrandNo(subDatas[1]);
			brand.setYougouBrandName(subDatas[2]);
			brand.setMerchantCode(merchantCode);
			taobaoBrandMapper.updateTaobaoYougouBrand(brand);
		}
	}

	@Override
	@Transactional
	public void delTaobaoYougouBrand(String idstr, String merchantCode)
			throws BusinessException {
		log.info("[淘宝导入]商家编码:{}-删除淘宝优购品牌。idstr:{}", merchantCode, idstr);
		if (StringUtils.isEmpty(idstr)) {
			throw new BusinessException("请求参数错误!");
		}
		TaobaoYougouBrand brand = null;
		String[] ids = idstr.split(",");
		for (String id : ids) {
			if (StringUtils.isEmpty(id)) {
				throw new BusinessException("请求参数错误!");
			}
			brand = new TaobaoYougouBrand();
			brand.setId(id);
			brand.setYougouBrandNo("");
			brand.setYougouBrandName("");
			brand.setMerchantCode(merchantCode);
			taobaoBrandMapper.updateTaobaoYougouBrand(brand);
		}
	}

	@Override
	public int findTaobaoYougouItemCatCount(Map<String, Object> params) {
		return taobaoItemCatMapper.selectTaobaoYougouItemCatCount(params);
	}

	@Override
	public PageFinder<TaobaoYougouItemCat> findTaobaoYougouItemCatList(
			Map<String, Object> map, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(),
				query.getPageSize());
		if (!StringUtils.isEmpty(map.get("catId"))) {
			log.info("[淘宝导入]商家编码:{}-调用商品接口查询品类。catId:{}", YmcThreadLocalHolder.getMerchantCode(), map.get("catId").toString());
			Category paCategory = commodityBaseApiService.getCategoryById(map
					.get("catId").toString());
			log.info("[淘宝导入]商家编码:{}-调用商品接口查询品类。Category:{}", YmcThreadLocalHolder.getMerchantCode(), paCategory);
			if (paCategory != null) {
				map.put("yougouCatNo", paCategory.getCatNo());
			}
		}
		int count = this.findTaobaoYougouItemCatCount(map);
		if (count == 0) {
			return new PageFinder<TaobaoYougouItemCat>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		List<TaobaoYougouItemCat> list = taobaoItemCatMapper
				.selectTaobaoYougouItemCatList(map, rowBounds);
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<TaobaoYougouItemCat>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		PageFinder<TaobaoYougouItemCat> pageFinder = new PageFinder<TaobaoYougouItemCat>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}
	@Override
	public List<ZTree> findTaobaoYougouItemCatZTree(String merchantCode, String nickId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		List<TaobaoItemCat> tList = taobaoItemCatMapper
				.selectAllTaobaoItemCat();
		params.put("merchantCode", merchantCode);
		params.put("bindType", BindType.NOBIND.getValue());
		params.put("nickId", nickId);
		List<TaobaoYougouItemCat> tyList = taobaoItemCatMapper
				.selectAllTaobaoYougouItemCatList(params);
		List<ZTree> nodes = new ArrayList<ZTree>();
		for (TaobaoYougouItemCat item : tyList) {
			getZtree(tList, item.getTaobaoCid(), item.getId(), nodes);
		}
		Collections.sort(nodes, new ComparatorZtree());
		return nodes;
	}
	/**
	 * 递归获取分类节点
	 * 
	 * @param tList
	 * @param taobaoCid
	 * @param itemId
	 * @param nodes
	 */
	private void getZtree(List<TaobaoItemCat> tList, Long taobaoCid,
			String itemId, List<ZTree> nodes) {
		Iterator<TaobaoItemCat> iterator = tList.iterator();
		while (iterator.hasNext()) {
			TaobaoItemCat cat = iterator.next();
			if (cat.getCid().intValue() == taobaoCid.intValue()) {
				ZTree node = new ZTree();
				node.setId(String.valueOf(cat.getCid()));
				node.setpId(String.valueOf(cat.getParentCid()));
				node.setName(cat.getName());
				node.setItemId(itemId);
				node.setSortOrder(cat.getSortOrder());
				nodes.add(node);
				if (cat.getParentCid() != 0
						&& !containsNode(nodes, cat.getParentCid())) {
					getZtree(tList, cat.getParentCid(), null, nodes);
				}
			}
		}
	}

	private boolean containsNode(List<ZTree> nodes, Long cid) {
		for (ZTree ztree : nodes) {
			if (Long.valueOf(ztree.getId()).intValue() == cid.intValue()) {
				return true;
			}
		}
		return false;
	}

	public List<ZTree> findYougouItemCatZTree(String merchantCode,
			String structName) {
		List<ZTree> list = new ArrayList<ZTree>();
		List<Cat> _cats = catMapper.querySupplierCatListByStructName(
				merchantCode, structName);
		ZTree zTree = null;
		if (CollectionUtils.isNotEmpty(_cats)) {
			Set<String> set = new HashSet<String>();
			// 查询first cat
			if (StringUtils.isEmpty(structName)) {
				for (Cat _cat : _cats) {
					if (StringUtils.isEmpty(_cat.getStructName())
							|| _cat.getStructName().length() != 8)
						continue;
					set.add(_cat.getStructName().substring(0, 2));
				}
			} else if (structName.trim().length() == 2) { // 查询second cat
				for (Cat _cat : _cats) {
					if (StringUtils.isEmpty(_cat.getStructName())
							|| _cat.getStructName().length() != 8)
						continue;
					set.add(_cat.getStructName().substring(0, 5));
				}
			} else if (structName.trim().length() == 5) { // 查询third cat
				for (Cat _cat : _cats) {
					if (StringUtils.isEmpty(_cat.getStructName())
							|| _cat.getStructName().length() != 8)
						continue;
					set.add(_cat.getStructName());
				}
			}
			if (CollectionUtils.isNotEmpty(set)) {
				for (String str : set) {
					log.info("[淘宝导入]商家编码:{}-调用商品接口查询品类。StructName:{}", merchantCode, str);
					Category category = commodityBaseApiService
							.getCategoryByStructName(str);
					log.info("[淘宝导入]商家编码:{}-调用商品接口查询品类。Category:{}", merchantCode, category);
					if (null != category && category.getDeleteFlag() == 1) {
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
		}
		return list;
	}

	@Override
	@Transactional
	public void bindYougouItemCate(Map<String, Object> params)
			throws BusinessException {
		log.info("[淘宝导入]商家编码:{}-绑定优购分类。入参:{}", YmcThreadLocalHolder.getMerchantCode(), params);
		String yougouItemId = String.valueOf(params.get("yougouItemId"));
		String nickId = String.valueOf(params.get("nickId"));
		String bindId = String.valueOf(params.get("bindId"));
		if (StringUtils.isEmpty(yougouItemId) || StringUtils.isEmpty(bindId)
				|| StringUtils.isEmpty(nickId)) {
			throw new BusinessException("请求参数错误");
		}
		String merchantCode = String.valueOf(params.get("merchantCode"));

		log.info("[淘宝导入]商家编码:{}-调用商品接口查询品类。Id:{}", merchantCode, yougouItemId);
		Category category = commodityBaseApiService.getCategoryById(yougouItemId);
		log.info("[淘宝导入]商家编码:{}-调用商品接口查询品类。Category:{}", merchantCode, category);
		if (category == null) {
			throw new BusinessException("优购分类不存在");
		}
		TaobaoYougouItemCat itemCat = new TaobaoYougouItemCat();
		itemCat.setId(bindId);
		itemCat.setMerchantCode(merchantCode);
		itemCat.setYougouCatNo(category.getCatNo());
		itemCat.setYougouCatStruct(category.getStructName());
		itemCat.setNickId(Long.valueOf(nickId));
		itemCat.setYougouCatFullName(String.valueOf(params
				.get("yougouCatFullName")));
		itemCat.setTaobaoCatFullName(String.valueOf(params
				.get("taobaoCatFullName")));
		taobaoItemCatMapper.updateTaobaoYougouItemCat(itemCat);
	}

	@Override
	@Transactional
	public void delTaobaoYougouItemCat(String idstr, String merchantCode)
			throws BusinessException {
		log.info("[淘宝导入]商家编码:{}-删除淘宝优购分类。idstr:{}", merchantCode, idstr);
		if (StringUtils.isEmpty(idstr)) {
			throw new BusinessException("请求参数错误!");
		}
		TaobaoYougouItemCat cat = null;
		String[] ids = idstr.split(",");
		for (String id : ids) {
			if (StringUtils.isEmpty(id)) {
				throw new BusinessException("请求参数错误!");
			}
			cat = new TaobaoYougouItemCat();
			cat.setId(id);
			cat.setYougouCatFullName("");
			cat.setTaobaoCatFullName("");
			cat.setYougouCatNo("");
			cat.setYougouCatStruct("");
			cat.setMerchantCode(merchantCode);

			// 删除所有属性
			List<String> propIds = taobaoYougouItemPropMapper
					.selectPropIdsByCatBindId(id, merchantCode);
			if (propIds.size() > 0) {
				taobaoYougouItemPropMapper.deletePropBatch(propIds, merchantCode);
			}

			// 删除所有属性值
			List<String> propValueIds = taobaoYougouItemPropMapper
					.selectPropValueIdsByCatBindId(id, merchantCode);
			if (propValueIds.size() > 0) {
				taobaoYougouItemPropMapper.deletePropValueBatch(propValueIds,
						merchantCode);
			}

			taobaoItemCatMapper.updateTaobaoYougouItemCat(cat);
		}
	}

	class ComparatorZtree implements Comparator<Object> {

		public int compare(Object arg0, Object arg1) {
			ZTree tree1 = (ZTree) arg0;
			ZTree tree2 = (ZTree) arg1;
			int flag = tree1.getSortOrder().compareTo(tree2.getSortOrder());
			return flag;
		}
	}

	/*********************** 分类属性设置 ****************************/

	/**
	 * 获取淘宝分类属性 获取淘宝分类属性，以及已经绑定的分类属性
	 * 
	 */
	@Override
	public List<TaobaoItemCatPropDto> selectTaobaoItemPro4Bind(String bindId,
			String merchantCode) {
		// 查询分类绑定ID 对应的淘宝分类ID下所有分类属性，以及已经绑定的分类属性对应的优购分类属性
		return taobaoItemCatPropMapper.selectItemProWidthBindYougouItemPro(
				bindId, merchantCode);
	}

	@Override
	public List<TaobaoItemCatPropValueDto> findTaobaoItemProVal(Long pid,
			Long cid, String merchantCode, String catBindId) {
		return taobaoItemPropValueMapper
				.selectTaobaoItemProValWidthYouItemProVal(pid, cid,
						merchantCode, catBindId);
	}

	@Override
	@Transactional
	public void bindTaobaoYougouItemProAndVal(HttpServletRequest request)
			throws BusinessException {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String operater = YmcThreadLocalHolder.getOperater();
		String catId = request.getParameter("catId");
		String taobaoCid = request.getParameter("taobaoCid");
		String catBandId = request.getParameter("catBandId");
		log.info("[淘宝导入]商家编码:{}-绑定淘宝优购属性值.catId:{},taobaoCid:{},catBandId:{}", merchantCode, catId, taobaoCid, catBandId);
		if (StringUtils.isEmpty(catId) || StringUtils.isEmpty(taobaoCid)) {
			throw new BusinessException("请求参数错误");
		}
		TaobaoYougouItemCat itemCat = this.getTaobaoYougouItemCatById(catBandId);

		List<PropItem> yougouPropList = merchantCommodityService
				.getPropMsgByCatIdNew(catId, true);
		Category category = commodityBaseApiService.getCategoryById(catId);
		// 根据优购分类属性获取淘宝分类属性，淘宝属性name是根究优购分类属性来的
		List<TaobaoYougouItemProp> propList4Add = new ArrayList<TaobaoYougouItemProp>();
		List<TaobaoYougouItemProp> propList4Update = new ArrayList<TaobaoYougouItemProp>();
		List<TaobaoYougouItemPropValue> propVlueList4Add = new ArrayList<TaobaoYougouItemPropValue>();
		List<TaobaoYougouItemPropValue> propVlueList4Update = new ArrayList<TaobaoYougouItemPropValue>();
		TaobaoYougouItemProp prop = null;
		TaobaoYougouItemPropValue propVal = null;
	
		for (PropItem item : yougouPropList) {
			String taobaoPorpItemNo = request.getParameter(item.getPropItemNo()
					+ TAOBAOPORPITEMNO);
			String proBandId = request.getParameter(item.getPropItemNo()
					+ PROBANDID);
					
			if (!StringUtils.isEmpty(taobaoPorpItemNo)) {
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
				prop.setMerchantCode(merchantCode);
				prop.setOperater(operater);
				prop.setOperated(DateUtil.getDateTime(new Date()));
				prop.setTaobaoCid(Long.valueOf(taobaoCid));
				prop.setTaobaoPid(Long.valueOf(taobaoPorpItemNo));
				prop.setYougouCatNo(category.getCatNo());
				prop.setYougouPropItemNo(item.getPropItemNo());
				prop.setNickId(itemCat.getNickId());

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
					propVal.setMerchantCode(merchantCode);
					propVal.setOperater(operater);
					propVal.setOperated(DateUtil.getDateTime(new Date()));
					propVal.setTaobaoCid(Long.valueOf(taobaoCid));
					propVal.setTaobaoPid(Long.valueOf(taobaoPorpItemNo));
					propVal.setTaobaoVid(taobaoProVal);
					propVal.setYougouCatNo(category.getCatNo());
					propVal.setYougouPropValueNo(val.getPropValueNo());
					propVal.setNickId(itemCat.getNickId());
				}
			}
		}		

		// 批量新增属性，属性值
		if (propList4Add.size() > 0) {
			taobaoYougouItemPropMapper
					.insertTaobaoYougouItemPropBatch(propList4Add);
		}
		if (propVlueList4Add.size() > 0) {
			taobaoYougouItemPropMapper
					.insertTaobaoYougouItemPropValueBatch(propVlueList4Add);
		}
		// 更新属性，属性值
		for (TaobaoYougouItemProp item : propList4Update) {
			taobaoYougouItemPropMapper.updateTaobaoYougouItemProp(item);
		}
		List<String> delList = new ArrayList<String>();
		for (TaobaoYougouItemPropValue val : propVlueList4Update) {
			if (StringUtils.isEmpty(val.getTaobaoVid())) {
				delList.add(val.getId());
			} else {
				taobaoYougouItemPropMapper.updateTaobaoYougouItemPropValue(val);
			}

		}
		// 删除属性值
		if (!delList.isEmpty()) {
			taobaoYougouItemPropMapper.deletePropValueBatch(delList,
					merchantCode);
		}
	}

	@Override
	public Map<String, Integer> downloadItem(String itemBindIdStr, String merchantCode,
			String operater) throws BusinessException, IllegalAccessException {
		log.info("[淘宝导入]商家编码:{}-下载明细。itemBindIdStr:{},operater:{}", merchantCode, itemBindIdStr, operater);
		if (StringUtils.isEmpty(itemBindIdStr)) {
			throw new BusinessException("参数错误");
		}
		Map<String,Integer> resultTotal = new HashMap<String,Integer>();
		resultTotal.put("itemTotal", 0);
		resultTotal.put("extendTotal", 0);
		
		String[] ids = itemBindIdStr.split(",");
		for (String id : ids) {
			if (StringUtils.isEmpty(ids)) {
				throw new BusinessException("参数错误");
			}
			List<TaobaoAuthinfoDto> list = taobaoAuthinfoMapper
					.selectAuthInfoByCatBandId(id);
			for (TaobaoAuthinfoDto dto : list) {
				Map<String,Integer> singleTotal = taobaoDataImportService
						.importTaobaoOnSalaItemByCidToYougou(
				dto.getTopAppkey(), dto.getAppSecret(),
				dto.getTopSession(), merchantCode, operater,
				dto.getNickId(), dto.getCid());
				resultTotal.put("itemTotal", resultTotal.get("itemTotal").intValue()+singleTotal.get("itemTotal").intValue());
				resultTotal.put("extendTotal", resultTotal.get("extendTotal").intValue()+singleTotal.get("extendTotal").intValue());
		
			}
		}
		return resultTotal;
	}
	
	/**
	 * 查询淘宝颜色分类属性值
	 * 
	 * @param cid
	 * @return
	 */
	public List<TaobaoItemCatPropValueDto> findTaobaoItemProVal4Color(Long cid) {
		return taobaoItemPropValueMapper.selectTaobaoItemProVal4Color(cid);
	}
}
