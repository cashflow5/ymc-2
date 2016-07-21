package com.belle.yitiansystem.taobao.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.belle.yitiansystem.taobao.model.TaobaoItemCat;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValue;
import com.belle.yitiansystem.taobao.model.TaobaoItemPropValue;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatParentVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatPropVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatVo;


public interface TaobaoCatMapper {

	/**
	 * 新增淘宝类目
	 * @param taobaoItemcat
	 */
	public void insertTaobaoItemCat(TaobaoItemCat taobaoItemcat);
	
	/**
	 * 批量新增淘宝类目
	 * @param lstTaobaoItemcat
	 */
	public void insertTaobaoItemCatList(List<TaobaoItemCat> lstTaobaoItemcat);
	
	/**
	 * 根据淘宝cid获取淘宝类目信息
	 * @param cid
	 * @return
	 */
	public TaobaoItemCat getTaobaoItemCatByCid(@Param("cid")Long cid);
	

	/**
	 * 查询淘宝所有分类
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectAllTaobaoItemCat(TaobaoItemCatVo vo,RowBounds bounds);
	/**
	 * selectAllFirstCat:查询所有的一级分类 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> selectAllFirstCat();
	
	/**
	 * 查询淘宝优购分类数量
	 * 
	 * @param prams
	 * @return
	 */
	public int selectTaobaoYougouItemCatCount(
			@Param("params") Map<String, Object> prams);

	/**
	 * 查询供应商对应的所有优购分类
	 * 
	 * @param merchantCode
	 * @return
	 */
	public List<String> selectAllYougouCatNoByMerchantCode(
			@Param("merchantCode") String merchantCode);

	/** 
	 * selectTaobaoItemCatCount:淘宝分类总数 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int selectTaobaoItemCatCount(TaobaoItemCatVo vo);

	/** 
	 * findChildrenById:通过父节点查找所有可用的子节点 
	 * @author li.n1 
	 * @param parentId
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<Map<String, Object>> findChildrenById(long parentId);

	/** 
	 * findCatPropDetail:查找淘宝分类属性详细 
	 * @author li.n1 
	 * @param cid
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<Map<String,Object>> findCatPropDetail(long cid);

	/** 
	 * checkTaobaoCat:检验是否末节点
	 * @author li.n1 
	 * @param cid
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<TaobaoItemCatParentVo> checkTaobaoCat(String[] ids);

	/** 
	 * deleteTaobaoCat:批量删除（逻辑删除，修改状态status） 
	 * @author li.n1 
	 * @param ids
	 * @return 
	 * @since JDK 1.6 
	 */  
	public void deleteTaobaoCat(String[] ids);

	public List<TaobaoItemCatPropVo> selectTaobaoItemCatPropByCid(@Param("cid") String cid);
	
	public int selectTaobaoItemCatePorpVlaueCountByVid(@Param("cid") String cid,@Param("pid") String pid,@Param("vid") String vid);
	
	public TaobaoItemPropValue selectTaobaoItemPorpVlaueByVid(@Param("vid") String vid);
	
	public void insertItemCatPropValue(TaobaoItemCatPropValue value);
	
	public void insertItemPropValue(TaobaoItemPropValue value);
	
	public int selectYougouTaobaoItemPropValueTempletCountByVid(@Param("cid") Long cid,@Param("pid") Long pid,@Param("vid") Long vid);
	
	public int selectYougouTaobaoItemPropValueByVid(@Param("cid") Long cid,@Param("pid") Long pid,@Param("vid") Long vid);
	
	public void delTaobaoItemCatPropValue(@Param("cid") Long cid,@Param("pid") Long pid,@Param("vid") Long vid);
}
