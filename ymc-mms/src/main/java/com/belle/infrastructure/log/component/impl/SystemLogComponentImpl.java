/*
 * Copyright 2011 Belle.com All right reserved. This software is the
 * confidential and proprietary information of Belle.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Belle.com.
 */
package com.belle.infrastructure.log.component.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.log.component.ISystemLogComponent;
import com.belle.infrastructure.log.dao.ISystemLogDao;
import com.belle.infrastructure.log.model.pojo.Systemlog;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;


/**
 * 类SystemLogServiceImpl.java的实现描述：TODO 类实现描述 
 * @author yinhongbiao 2011-4-19 下午03:21:27
 */
@Service
public class SystemLogComponentImpl implements ISystemLogComponent {

    @Resource
    private ISystemLogDao logDao;
    
    @Transactional
    public String addSysteLog(Systemlog systemlog) throws Exception {
        systemlog = logDao.saveObject(systemlog);
        return systemlog.getId();
    }
    
    @Transactional
    public void addSysteLog(List<Systemlog> systemlog) throws Exception{
        for (Systemlog log : systemlog) {
            logDao.save(log);
        }
    }

    public PageFinder<Systemlog> queryPageSysgtemLog(Systemlog systemlog, Query query) {
        CritMap critMap = new CritMap();
        
        PageFinder<Systemlog> pageFilder = logDao.pagedByCritMap(critMap, query.getPage(), query.getPageSize());
        
        return pageFilder;
    }

}
