package com.belle.yitiansystem.merchant.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.merchant.dao.mapper.TaobaoMapper;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAccessTrackVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAppkeyVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAuthorizeVo;
import com.belle.yitiansystem.merchant.service.ITaobaoService;
import com.yougou.merchant.api.common.Query;

/**
 * 淘宝商品导入service接口实现
 */
@Service
public class TaobaoServiceImpl implements ITaobaoService {

	@Resource
	private TaobaoMapper taobaoMapper;

	/**
	 * 淘宝商家授权管理
	 */
	public PageFinder<TaobaoAuthorizeVo> getTaobaoAuthorizeList(TaobaoAuthorizeVo vo, Query query) {

		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<TaobaoAuthorizeVo> list = taobaoMapper.queryTaobaoAuthorizeList(vo, rowBounds);
		int count = taobaoMapper.queryTaobaoAuthorizeListCount(vo);
		PageFinder<TaobaoAuthorizeVo> pageFinder = new PageFinder<TaobaoAuthorizeVo>(query.getPage(), query.getPageSize(), count, list);
		return pageFinder;

	}
	public List<TaobaoAuthorizeVo> getTaobaoAuthorizeList(TaobaoAuthorizeVo vo){
		List<TaobaoAuthorizeVo> list = taobaoMapper.queryTaobaoAuthorizeList(vo);
		return list;
	}
	//获取已审核或者已授权的list
	public List<TaobaoAuthorizeVo> getTaobaoAuthorizedList(TaobaoAuthorizeVo vo){
		List<TaobaoAuthorizeVo> list = taobaoMapper.queryTaobaoAuthorizedList(vo);
		return list;
	}
	/**
	 * 根据id检索
	 */
	public TaobaoAuthorizeVo getTaobaoAuthorizeById(TaobaoAuthorizeVo vo) {
		return taobaoMapper.queryTaobaoAuthorizeById(vo);
	}
	
	/**
	 * 根据id删除
	 */
	public int deleteTaobaoAuthorizeById(TaobaoAuthorizeVo vo) {
		return taobaoMapper.deleteTaobaoAuthorizeById(vo);
	}
	/**
	 * 淘宝商家授权更新
	 */
	public Boolean updateTaobaoAuthorize(TaobaoAuthorizeVo vo) {
		int count = 0;
		count = taobaoMapper.updateTaobaoAuthorize(vo);
		return count > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * 淘宝api调用轨迹查询
	 */
	public PageFinder<TaobaoAccessTrackVo> getTaobaoAccessTrackList(TaobaoAccessTrackVo vo, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<TaobaoAccessTrackVo> list = taobaoMapper.queryTaobaoAccessTrackList(vo, rowBounds);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		//避免返回的数据带有html的标签而弄乱页面
		String searchList[] = { "<", ">" };
		String replacementList[] = { "&lt", "&gt" };
		for (TaobaoAccessTrackVo resultVo : list) {
			resultVo.setStartTime(format.format(new Date(resultVo.getAccessStart())));
			resultVo.setEndTime(format.format(new Date(resultVo.getAccessEnd())));
			if (null != resultVo && StringUtils.isNotEmpty(resultVo.getAccessResult())) {
				resultVo.setAccessResult(resultVo.getAccessResult().length() > 180 ? StringUtils.replaceEach(resultVo.getAccessResult().substring(0, 180), searchList, replacementList) + "..."
						: StringUtils.replaceEach(resultVo.getAccessResult(), searchList, replacementList));
			}
		}
		int count = taobaoMapper.queryTaobaoAccessTrackListCount(vo);
		PageFinder<TaobaoAccessTrackVo> pageFinder = new PageFinder<TaobaoAccessTrackVo>(query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}
	
	/**
	 * 淘宝topAppkey管理
	 */
	public PageFinder<TaobaoAppkeyVo> getTaobaoAppkeyList(TaobaoAppkeyVo vo, Query query) {

		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<TaobaoAppkeyVo> list = taobaoMapper.queryTaobaoAppkeyList(vo, rowBounds);
		int count = taobaoMapper.queryTaobaoAppkeyListCount(vo);
		PageFinder<TaobaoAppkeyVo> pageFinder = new PageFinder<TaobaoAppkeyVo>(query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}
	
	/**
	 * 淘宝topAppkey管理
	 */
	public List<TaobaoAppkeyVo> getTaobaoAppkeyList(TaobaoAppkeyVo vo) {
		return taobaoMapper.queryTaobaoAppkeyList(vo);
	}

	/**
	 * 根据appkey检索
	 */
	public TaobaoAppkeyVo getTaobaoAppkeyByAppkey(TaobaoAppkeyVo vo) {
		return taobaoMapper.queryTaobaoAppkeyByAppkey(vo);
	}
	
	/**
	 * 淘宝topAppkey更新
	 */
	public Boolean updateTaobaoAppkey(TaobaoAppkeyVo vo) {
		int count = 0;
		count = taobaoMapper.updateTaobaoAppkey(vo);
		return count > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * 淘宝topAppkey插入
	 */
	public Boolean addTaobaoAppkey(TaobaoAppkeyVo vo) {
		int count = 0;
		count = taobaoMapper.insertTaobaoAppkey(vo);
		return count > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
}