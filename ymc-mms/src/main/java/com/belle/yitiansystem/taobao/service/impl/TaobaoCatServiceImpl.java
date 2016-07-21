package com.belle.yitiansystem.taobao.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.taobao.dao.mapper.TaobaoCatMapper;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValue;
import com.belle.yitiansystem.taobao.model.TaobaoItemPropValue;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatParentVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatPropVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatValueVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatVo;
import com.belle.yitiansystem.taobao.service.ITaobaoCatService;
import com.yougou.merchant.api.common.Query;

@Service
public class TaobaoCatServiceImpl implements ITaobaoCatService {
	@Autowired
	private TaobaoCatMapper taobaoCatMapper;

	/**
	 * TODO 查询淘宝所有分类
	 * 
	 * @see com.belle.yitiansystem.taobao.service.ITaobaoCatService#findTaobaoCat(com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatVo,
	 *      com.yougou.merchant.api.common.Query)
	 */
	@Override
	public PageFinder<Map<String, Object>> findTaobaoCat(TaobaoItemCatVo vo,
			Query query) {
		PageFinder<Map<String, Object>> pageFinder = null;
		RowBounds bounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<Map<String, Object>> list = taobaoCatMapper
				.selectAllTaobaoItemCat(vo, bounds);
		int count = taobaoCatMapper.selectTaobaoItemCatCount(vo);
		pageFinder = new PageFinder<Map<String, Object>>(query.getPage(),
				query.getPageSize(), count, list);
		return pageFinder;
	}

	/**
	 * 查找子节点
	 * 
	 * @see com.belle.yitiansystem.taobao.service.ITaobaoCatService#findChildrenById(long)
	 */
	@Override
	public List<Map<String, Object>> findChildrenById(long parentCid) {
		return taobaoCatMapper.findChildrenById(parentCid);
	}

	/**
	 * 查询所有的一级节点
	 * 
	 * @see com.belle.yitiansystem.taobao.service.ITaobaoCatService#selectAllFirstCat()
	 */
	@Override
	public List<Map<String, Object>> selectAllFirstCat() {
		return taobaoCatMapper.selectAllFirstCat();
	}

	/**
	 * 淘宝分类属性详情
	 * 
	 * @see com.belle.yitiansystem.taobao.service.ITaobaoCatService#viewCatPropDetail(long)
	 */
	@Override
	public Map<TaobaoItemCatPropVo, List<TaobaoItemCatValueVo>> findCatPropDetail(
			long cid) {
		List<Map<String, Object>> list = taobaoCatMapper.findCatPropDetail(cid);
		Map<TaobaoItemCatPropVo, List<TaobaoItemCatValueVo>> vMap = new HashMap<TaobaoItemCatPropVo, List<TaobaoItemCatValueVo>>();
		List<TaobaoItemCatValueVo> vList = null;
		TaobaoItemCatPropVo pvo = null;
		TaobaoItemCatValueVo vvo = null;
		for (Map<String, Object> resultMap : list) {
			pvo = new TaobaoItemCatPropVo();
			pvo.setMust((Boolean) resultMap.get("must"));
			pvo.setPid((Long) resultMap.get("pid"));
			pvo.setPname((String) resultMap.get("pname"));
			vvo = new TaobaoItemCatValueVo();
			vvo.setVid((Long) resultMap.get("vid"));
			vvo.setVname((String) resultMap.get("vname"));
			vvo.setIsArtificialInput((String) resultMap
					.get("isArtificialInput"));
			if (vMap.get(pvo) == null) {
				vList = new ArrayList<TaobaoItemCatValueVo>();
			} else {
				vList = vMap.get(pvo);
			}
			vList.add(vvo);
			vMap.put(pvo, vList);
		}
		return vMap;
	}

	/**
	 * 检验是否末节点
	 * 
	 * @see com.belle.yitiansystem.taobao.service.ITaobaoCatService#checkTaobaoCat(long)
	 */
	@Override
	public List<TaobaoItemCatParentVo> checkTaobaoCat(String[] ids) {
		return taobaoCatMapper.checkTaobaoCat(ids);
	}

	/**
	 * 批量删除（逻辑删除，修改状态status）
	 * 
	 * @see com.belle.yitiansystem.taobao.service.ITaobaoCatService#deleteTaobaoCat(java.lang.String[])
	 */
	@Override
	public void deleteTaobaoCat(String[] ids,HttpServletRequest request) {
		taobaoCatMapper.deleteTaobaoCat(ids);
		merchantOperationLogService.addMerchantOperationLog(
				"TAOBAO_CAT_DELETE", OperationType.TAOBAO_CAT,
				"批量删除淘宝分类成功(逻辑删除，修改状态),cid:"+ids.toString(), request);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public List<TaobaoItemCatPropValue> saveTaobaoPropValue(
			HttpServletRequest request, String cid) throws BusinessException {
		final String CODE = "code";
		final String NAME = "name";
		StringBuilder erroSb = new StringBuilder();
		if (StringUtils.isEmpty(cid)) {
			throw new BusinessException("参数错误");
		}
		List<TaobaoItemCatPropVo> propList = taobaoCatMapper
				.selectTaobaoItemCatPropByCid(cid);
		List<TaobaoItemCatPropValue> catPropValueList = new ArrayList<TaobaoItemCatPropValue>();
		String curUser = GetSessionUtil.getSystemUser(request).getLoginName();
		for (TaobaoItemCatPropVo vo : propList) {
			String[] codes = request.getParameterValues(vo.getPid() + CODE);
			String[] names = request.getParameterValues(vo.getPid() + NAME);
			if (codes == null || names == null) {
				continue;
			}
			if (codes.length != names.length) {
				throw new BusinessException("参数错误");
			}
			for (int i = 0, _len = codes.length; i < _len; i++) {
				if (StringUtils.isEmpty(codes[i])) {
					erroSb.append("属性【" + vo.getPname() + "】的第" + (i + 1)
							+ "个属性值编码不能为空<br>");
				}
				if (StringUtils.isEmpty(names[i])) {
					erroSb.append("属性【" + vo.getPname() + "】的第" + (i + 1)
							+ "个属性值编码不能为空<br>");
				}
				if (StringUtils.isEmpty(codes[i])
						|| StringUtils.isEmpty(names[i])) {
					continue;
				}
				int count = taobaoCatMapper
						.selectTaobaoItemCatePorpVlaueCountByVid(cid,
								vo.getPid() + "", codes[i]);
				if (count > 0) {
					erroSb.append("属性【" + vo.getPname() + "】的第" + (i + 1)
							+ "个属性值编码已经存在或者重复<br>");
					continue;
				}
				TaobaoItemCatPropValue catPropValue = new TaobaoItemCatPropValue();
				catPropValue.setCid(Long.valueOf(cid));
				catPropValue.setIsArtificialInput("1");
				catPropValue.setPid(vo.getPid());
				catPropValue.setVid(Long.valueOf(codes[i]));
				catPropValue.setvName(names[i]);
				taobaoCatMapper.insertItemCatPropValue(catPropValue);
				catPropValueList.add(catPropValue);
				TaobaoItemPropValue proValue = taobaoCatMapper
						.selectTaobaoItemPorpVlaueByVid(codes[i]);
				if (proValue == null) {
					TaobaoItemPropValue itemPropValue = new TaobaoItemPropValue();
					itemPropValue.setName(names[i]);
					itemPropValue.setVid(Long.valueOf(codes[i]));
					itemPropValue.setOperated(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(new Date()));
					itemPropValue.setOperater(curUser);
					taobaoCatMapper.insertItemPropValue(itemPropValue);
					merchantOperationLogService.addMerchantOperationLog(
							"TAOBAO_CAT_PRO_ADD", OperationType.TAOBAO_CAT,
							"新增淘宝分类属性值 ,cid:"+cid+",vid:" + Long.valueOf(codes[i]) + " 属性值："
									+ names[i] + " 成功", request);
				} else {
					catPropValue.setvName(proValue.getName());
				}
			}
		}

		if (!StringUtils.isEmpty(erroSb.toString())) {
			throw new BusinessException(erroSb.toString());
		}
		return catPropValueList;
	}

	@Resource
	private IMerchantOperationLogService merchantOperationLogService;

	public void delTaobaoProVal(Long cid, Long pid, Long vid,HttpServletRequest request)
			throws BusinessException {
		if (cid == null || pid == null || vid == null) {
			throw new BusinessException("参数错误");
		}
		int temCount = taobaoCatMapper
				.selectYougouTaobaoItemPropValueTempletCountByVid(cid, pid, vid);
		if (temCount > 0) {
			throw new BusinessException("该属性值已经绑定，不能删除");
		}

		int count = taobaoCatMapper.selectYougouTaobaoItemPropValueByVid(cid,
				pid, vid);

		if (count > 0) {
			throw new BusinessException("该属性值已经在使用，不能删除");
		}
		taobaoCatMapper.delTaobaoItemCatPropValue(cid, pid, vid);
		merchantOperationLogService.addMerchantOperationLog(
				"TAOBAO_CAT_PRO_DELETE", OperationType.TAOBAO_CAT,
				"删除淘宝分类属性值vid:" + vid+ " cid："
						+ cid + " 成功", request);
	}
}
