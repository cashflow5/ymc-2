package com.belle.infrastructure.log.component;

import java.util.List;

import com.belle.infrastructure.log.model.pojo.Systemlog;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;

/**
 * 
 * 类ISystemLogService.java的实现描述：TODO 通用日志管理接口
 * @author yinhongbiao 2011-4-19 下午03:18:16
 */
public interface ISystemLogComponent {
    
    /**
     * 增加通用日志
     * @param systemlog
     * @return
     * @throws Exception 
     */
    public String addSysteLog(Systemlog systemlog) throws Exception;
    
    /**
     * 批量增加通用日志
     * @param systemlog
     * @return
     * @throws Exception 
     */
    public void addSysteLog(List<Systemlog> systemlog) throws Exception;
    
    /**
     * 查询通用日志
     * @param systemlog  查询条件
     * @param query      翻页条件
     * @return
     */
    public PageFinder<Systemlog> queryPageSysgtemLog(Systemlog systemlog,Query query);

}
