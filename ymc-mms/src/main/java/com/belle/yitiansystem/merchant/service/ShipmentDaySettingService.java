package com.belle.yitiansystem.merchant.service;

import java.util.Date;
import java.util.List;

import com.belle.yitiansystem.merchant.model.pojo.ShipmentDaySetting;


/**
 * 招商商家发货日期设置表
 * @author he.wc
 *
 */
public interface ShipmentDaySettingService {

	/**
	 * 查询发货日期设置列表
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ShipmentDaySetting> findShipmentDaySettingByYearAndMonth(Integer year,Integer month);
	
	/**
	 * 查询发货日期设置列表
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ShipmentDaySetting> findShipmentDaySettingByYear(Integer year);
	
	/**
	 *  查询发货日期设置
	 * @param date
	 * @return
	 */
	public ShipmentDaySetting findShipmentDaySettingByDate(Date date);
	
	/**
	 * 查询发货日期设置
	 * @param date
	 * @return
	 */
	public ShipmentDaySetting findShipmentDaySettingByDate(Integer year,Integer month,Integer day);
	
	/**
	 * 查询发货日期设置
	 * @param id
	 * @return
	 */
	public ShipmentDaySetting findShipmentDaySettingById(String id);
	

	/**
	 * 保存
	 * @param enity
	 */
	public void save(ShipmentDaySetting entity);
	
	/**
	 * 修改
	 * @param enity
	 */
	public void update(ShipmentDaySetting entity);
	
	/**
	 * 按时间删除发货时间
	 * @param startDate
	 * @param endDate
	 */
	public void deleteShipmentDaySetting(Integer startYear,Integer endYear);
}