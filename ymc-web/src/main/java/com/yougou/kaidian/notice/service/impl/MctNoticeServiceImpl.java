package com.yougou.kaidian.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.notice.dao.MctNoticeMapper;
import com.yougou.kaidian.notice.model.pojo.MctNotice;
import com.yougou.kaidian.notice.service.IMctNoticeService;

@Service
public class MctNoticeServiceImpl implements IMctNoticeService {

	@Resource
	private MctNoticeMapper mctNoticeMapper;
	
	@Override
	public PageFinder<MctNotice> queryMctNoticeList(Query query,String merchant_type) {
		int count=mctNoticeMapper.queryMctNoticeListCount(merchant_type);
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<MctNotice> data=mctNoticeMapper.queryMctNoticeList(merchant_type,rowBounds);
		PageFinder<MctNotice> pageFinder = new PageFinder<MctNotice>(query.getPage(), query.getPageSize(), count);
		pageFinder.setData(data);
		return pageFinder;
	}

	@Override
	public List<MctNotice> queryMctNoticeListTop5(String merchant_type) {
		// TODO Auto-generated method stub
		return mctNoticeMapper.queryMctNoticeListTop5(merchant_type);
	}

	@Override
	public MctNotice queryMctNoticeById(String id) {
		// TODO Auto-generated method stub
		return mctNoticeMapper.queryMctNoticeById(id);
	}

}
