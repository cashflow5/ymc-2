package com.belle.yitiansystem.systemmgmt.service;

import com.belle.infrastructure.log.model.pojo.Systemlog;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;

public interface ISystemLogService {
    
    /**
     * 查询通用日志
     * @param systemlog  查询条件
     * @param query      翻页条件
     * @return
     */
    public PageFinder<Systemlog> queryPageSysgtemLog(Systemlog systemlog,Query query);

}
