package com.yougou.kaidian.notice.service;

import java.util.List;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.notice.model.pojo.MctNotice;

public interface IMctNoticeService {
	public PageFinder<MctNotice> queryMctNoticeList(Query query,String merchant_type);
	public List<MctNotice> queryMctNoticeListTop5(String merchant_type);
	public MctNotice queryMctNoticeById(String id);
}
