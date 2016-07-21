package com.belle.yitiansystem.systemmgmt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.belle.infrastructure.log.component.ISystemLogComponent;
import com.belle.infrastructure.log.model.pojo.Systemlog;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.service.ISystemLogService;

@Service
public class SystemLogServiceImpl implements ISystemLogService{
    
    @Resource
    private ISystemLogComponent systemLogComponent;
    
    @Override
    public PageFinder<Systemlog> queryPageSysgtemLog(Systemlog systemlog, Query query) {
        return systemLogComponent.queryPageSysgtemLog(systemlog, query);
    }

}
