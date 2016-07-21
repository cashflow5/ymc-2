package com.belle.yitiansystem.merchant.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.yitiansystem.merchant.dao.ShipmentDaySettingDao;
import com.belle.yitiansystem.merchant.model.pojo.ShipmentDaySetting;
import com.belle.yitiansystem.merchant.service.ShipmentDaySettingService;

@Service
public class ShipmentDaySettingServiceImpl implements ShipmentDaySettingService {

	@Resource
	private ShipmentDaySettingDao shipmentDaySettingDao;

	public List<ShipmentDaySetting> findShipmentDaySettingByYearAndMonth(Integer year, Integer month) {
		CritMap critMap = new CritMap();
		critMap.addEqual("year", year);
		critMap.addEqual("month", month);
		critMap.addAsc("day");
		return shipmentDaySettingDao.findByCritMap(critMap, false);
	}
	

	public List<ShipmentDaySetting> findShipmentDaySettingByYear(Integer year) {
		CritMap critMap = new CritMap();
		critMap.addEqual("year", year);
		return shipmentDaySettingDao.findByCritMap(critMap, false);
	}



	@SuppressWarnings("deprecation")
	public ShipmentDaySetting findShipmentDaySettingByDate(Date date) {
		return shipmentDaySettingDao.findUniqueBy("date", new java.sql.Date(date.getYear(),date.getMonth(),date.getDay()) , false);
	}
	
	public ShipmentDaySetting findShipmentDaySettingByDate(Integer year, Integer month, Integer day) {
		CritMap critMap = new CritMap();
		critMap.addEqual("year", year);
		critMap.addEqual("month", month);
		critMap.addEqual("day", day);
		List<ShipmentDaySetting> list = shipmentDaySettingDao.findByCritMap(critMap, false);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public ShipmentDaySetting findShipmentDaySettingById(String id) {
		return shipmentDaySettingDao.getById(id);
	}

	@Transactional
	public void save(ShipmentDaySetting entity) {
		shipmentDaySettingDao.getTemplate().save(entity);

	}

	@Transactional
	public void update(ShipmentDaySetting entity) {
		shipmentDaySettingDao.getTemplate().update(entity);

	}


	@Transactional
	public void deleteShipmentDaySetting(Integer startYear, Integer endYear) {
		
		CritMap critMap = new CritMap();
		critMap.addLessAndEq("year",  endYear);
		critMap.addGreatAndEq("year", startYear);
		List<ShipmentDaySetting>  list = shipmentDaySettingDao.findByCritMap(critMap, false);
		for(ShipmentDaySetting entity:list){
			shipmentDaySettingDao.getTemplate().delete(entity);
		}
		
	}
	
	

}
