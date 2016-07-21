package com.belle.yitiansystem.mctnotice.service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.mctnotice.model.pojo.MctNotice;
import com.belle.yitiansystem.mctnotice.model.vo.QueryMctNoticeVo;

public interface IMctNoticeService {

	public PageFinder<MctNotice> queryMctNoticeList(QueryMctNoticeVo queryMctNoticeVo,Query query) throws Exception;
	
	public MctNotice getMctNoticeById(String id) throws Exception;
	
	public void saveMctNotice(MctNotice mctNotice) throws Exception;
	
	public void updateMctNotice(MctNotice mctNotice) throws Exception;
	
	public void deleteMctNotice(String id) throws Exception;
	
	public void updateMctNoticeTop(String id,String istop) throws Exception;
}
