package com.yougou.kaidian.notice.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.notice.model.pojo.MctNotice;

public interface MctNoticeMapper {

	public List<MctNotice> queryMctNoticeList(String merchant_type,RowBounds rowBounds);
	public List<MctNotice> queryMctNoticeListTop5(String merchant_type);
	public MctNotice queryMctNoticeById(String id);
	public int queryMctNoticeListCount(String merchant_type);
}
