package com.yougou.kaidian.taobao.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 导入传参VO
 * @author le.sm
 *
 */
public class TaobaoImportVo {

	/**
	 * 统一改成线程安全
	 */
	private List<TaobaoCsvItemVO> voList = Collections.synchronizedList(new ArrayList<TaobaoCsvItemVO>());
	private List<ErrorVo> errorList =  Collections.synchronizedList(new ArrayList<ErrorVo>());
	private Map<String, Integer> map = new Hashtable<String, Integer>();
	private Map<String, Integer> resultTotal = new Hashtable<String, Integer>();
	
	private int packagFailTotal = 0;
	/**
	 * 淘宝品牌列表
	 */
	private Map<Long,String> taobaoBrandMap = new HashMap<Long,String>();
	/**
	 * 淘宝分类里列表
	 */
	private Map<Long,String[]>  taobaoCatMap =  new HashMap<Long,String[]>();

	public List<TaobaoCsvItemVO> getVoList() {
		return voList;
	}
	public void setVoList(List<TaobaoCsvItemVO> voList) {
		this.voList = voList;
	}
	public List<ErrorVo> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<ErrorVo> errorList) {
		this.errorList = errorList;
	}
	public Map<String, Integer> getMap() {
		return map;
	}
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public Map<String, Integer> getResultTotal() {
		return resultTotal;
	}
	public void setResultTotal(Map<String, Integer> resultTotal) {
		this.resultTotal = resultTotal;
	}
	public Map<Long, String> getTaobaoBrandMap() {
		return taobaoBrandMap;
	}
	public void setTaobaoBrandMap(Map<Long, String> taobaoBrandMap) {
		this.taobaoBrandMap = taobaoBrandMap;
	}
	public Map<Long, String[]> getTaobaoCatMap() {
		return taobaoCatMap;
	}
	public void setTaobaoCatMap(Map<Long, String[]> taobaoCatMap) {
		this.taobaoCatMap = taobaoCatMap;
	}
	public int getPackagFailTotal() {
		return packagFailTotal;
	}
	public void setPackagFailTotal(int packagFailTotal) {
		this.packagFailTotal = packagFailTotal;
	}
	/**
	 * packagFailTotal++
	 */
	public void setPackagFailTotalAdd(){
		this.packagFailTotal++;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
