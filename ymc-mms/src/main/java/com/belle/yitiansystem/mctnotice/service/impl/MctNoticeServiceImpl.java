package com.belle.yitiansystem.mctnotice.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.mctnotice.dao.IMctNoticeDao;
import com.belle.yitiansystem.mctnotice.dao.impl.MctNoticeDao;
import com.belle.yitiansystem.mctnotice.model.pojo.MctNotice;
import com.belle.yitiansystem.mctnotice.model.vo.QueryMctNoticeVo;
import com.belle.yitiansystem.mctnotice.service.IMctNoticeService;

@Service
public class MctNoticeServiceImpl implements IMctNoticeService {

	@Resource
	private IMctNoticeDao mctNoticeDao;
	private SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public PageFinder<MctNotice> queryMctNoticeList(
			QueryMctNoticeVo queryMctNoticeVo, Query query) throws Exception {
		CritMap critMap = new CritMap();
		if(StringUtils.isNotBlank(queryMctNoticeVo.getTitle())){
			critMap.addLike("title", queryMctNoticeVo.getTitle());
		}
		if(StringUtils.isNotBlank(queryMctNoticeVo.getNoticeType())){
			critMap.addEqual("noticeType", queryMctNoticeVo.getNoticeType());
		}
		if(StringUtils.isNotBlank(queryMctNoticeVo.getAuthor())){
			critMap.addLike("author", queryMctNoticeVo.getAuthor());
		}
		if(StringUtils.isNotBlank(queryMctNoticeVo.getCreateTimeStart())){
			critMap.addGreatAndEq("createTime", dateformat1.parse(queryMctNoticeVo.getCreateTimeStart()));
		}
		if(StringUtils.isNotBlank(queryMctNoticeVo.getCreateTimeEnd())){
			critMap.addLessAndEq("createTime", dateformat1.parse(queryMctNoticeVo.getCreateTimeEnd()));
		}
		critMap.addDesc("createTime");
		return mctNoticeDao.pagedByCritMap(critMap, query.getPage(), query.getPageSize());
	}

	@Transactional
	public MctNotice getMctNoticeById(String id) throws Exception {
		return mctNoticeDao.getById(id);
	}

	@Transactional
	public void saveMctNotice(MctNotice mctNotice) throws Exception {
		mctNoticeDao.save(mctNotice);
	}

	@Transactional
	public void updateMctNotice(MctNotice mctNotice) throws Exception {
		mctNoticeDao.merge(mctNotice);
	}

	@Transactional
	public void deleteMctNotice(String id) throws Exception {
		mctNoticeDao.removeById(id);
	}

	@Transactional
	public void updateMctNoticeTop(String id,String istop) throws Exception {
		MctNotice mctNotice=mctNoticeDao.getById(id);
		mctNotice.setIsTop(istop);
		mctNoticeDao.merge(mctNotice);
	}
}
