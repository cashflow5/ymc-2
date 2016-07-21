package com.yougou.kaidian.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.user.dao.FeebackMapper;
import com.yougou.kaidian.user.model.pojo.Feeback;
import com.yougou.kaidian.user.model.pojo.HelpMenu;
import com.yougou.kaidian.user.model.vo.FeebackVo;
import com.yougou.kaidian.user.service.FeebackService;

/**
 * 用户信息service类
 * 
 * @author wang.m
 * @Date 2012-03-12
 * 
 */
@Service
public class FeebackServiceImpl implements FeebackService {

	@Resource
	private FeebackMapper feebackMapper;
	
	public int addFeeback(Feeback feeback) {
		feebackMapper.addFeeback(feeback);
		return 0;
	}

	public PageFinder<FeebackVo> queryFeeback(String merchantCode, Query query) {
		int count = 0;
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<Feeback> feebackList =  feebackMapper.queryFeebackList(merchantCode, rowBounds);
		List<FeebackVo> feebackVoList = new ArrayList<FeebackVo>();
		count = feebackMapper.queryFeebackCount(merchantCode);
		if(count > 0){
			PageFinder<FeebackVo> feebackPage = new PageFinder<FeebackVo>(query.getPage(), query.getPageSize(), count);
			for(Feeback feeback:feebackList){
				FeebackVo vo = new FeebackVo();
			    List<String>	feebackReplyList = feebackMapper.queryFeebackReplyList(feeback.getId());
			    vo.setReplyList(feebackReplyList);
			    vo.setContent(feeback.getContent());
			    feebackVoList.add(vo);
			}
			feebackPage.setData(feebackVoList);
			return feebackPage;
		}
		return null;
		
	}

	@Override
	public Map<String,List<HelpMenu>> queryHelpMenuListByObj() {
		HelpMenu obj = new HelpMenu();
		obj.setIsLeaf(1);
		List<HelpMenu> helps = feebackMapper.queryHelpMenuListByObj(obj);
		List<HelpMenu> commodityMenu=new ArrayList<HelpMenu>();
		List<HelpMenu> orderMenu=new ArrayList<HelpMenu>();
		List<HelpMenu> apiMenu=new ArrayList<HelpMenu>();
		for(HelpMenu menu:helps){
			if(menu.getParentName().indexOf("商品")>=0){
				commodityMenu.add(menu);
			}else if(menu.getParentName().indexOf("订单")>=0){
				orderMenu.add(menu);
			}else if(menu.getParentName().indexOf("对接")>=0||menu.getParentName().indexOf("入驻")>=0){
				apiMenu.add(menu);
			}
		}
		Map<String,List<HelpMenu>> menuMap=new HashMap<String,List<HelpMenu>>();
		menuMap.put("comm", commodityMenu);
		menuMap.put("order", orderMenu);
		menuMap.put("api", apiMenu);
		return menuMap;
	}
	
	
	
	

	
	
}
