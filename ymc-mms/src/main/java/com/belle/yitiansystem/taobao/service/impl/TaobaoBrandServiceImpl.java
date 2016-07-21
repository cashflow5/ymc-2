package com.belle.yitiansystem.taobao.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.taobao.dao.mapper.TaobaoBrandMapper;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.belle.yitiansystem.taobao.service.ITaobaoBrandService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.common.UUIDGenerator;
@Service
public class TaobaoBrandServiceImpl implements ITaobaoBrandService {

	private static final Long PID = 20000l;
	private static final String PROPNAME = "品牌";
	private static final String ARTIFICIALINPUT = "1";
	 @Resource
	 private TaobaoBrandMapper taobaoBrandMapper;
	 @Resource
	 private IMerchantOperationLogService merchantOperationLogService;
	@Override
	public void addBrand(TaobaoBrand brand,String userName,HttpServletRequest request) throws BusinessException {
		if(brand.getVid()==null){
			throw new BusinessException("VID不能为空");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("vid",brand.getVid());
		int count = taobaoBrandMapper.selectBrandCount(map);
		if(count>0){
			throw new BusinessException("VID已经存在，不能重复添加");
		}
		brand.setBid(UUIDGenerator.getUUID());
		brand.setOperater(userName);
		brand.setPid(PID);
		brand.setPropName(PROPNAME);
		brand.setIsArtificialInput(ARTIFICIALINPUT);
		brand.setOperated(new SimpleDateFormat("yyyy-MM:dd HH:mm:ss").format(new Date()));
		taobaoBrandMapper.insertBrand(brand);
		merchantOperationLogService.addMerchantOperationLog("TAOBAO_BAND_ADD",  OperationType.TAOBAO_BAND, "新增淘宝品牌成功,品牌名称:"+brand.getName()+ ",vid:"+brand.getVid() , request);
	}

	@Override
	public PageFinder<TaobaoBrand> findTaoaoBrandList(Map<String, Object> map,
			Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(),
				query.getPageSize());
		int count = taobaoBrandMapper.selectBrandCount(map);
		if (count == 0) {
			return new PageFinder<TaobaoBrand>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		List<TaobaoBrand> list = taobaoBrandMapper.selectBrandList(map, rowBounds);
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<TaobaoBrand>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		PageFinder<TaobaoBrand> pageFinder = new PageFinder<TaobaoBrand>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}

	public List<TaobaoBrand>  findTaobaoBrandList(Map<String,Object> map){
		RowBounds rowBounds = new RowBounds(0,Integer.MAX_VALUE);
		List<TaobaoBrand> list = taobaoBrandMapper.selectBrandList(map, rowBounds);
		return list;
	}
	
	@Override
	@Transactional
	public void deleteTaobaoBrand(String bids,HttpServletRequest request) throws BusinessException{
		if(StringUtils.isEmpty(bids)){
			throw new BusinessException("参数错误");
		}
		String[] bidsArray  = bids.split(",");
		TaobaoBrand  brand =null;
		for(String bid:bidsArray){
			brand = taobaoBrandMapper.selectBrandById(bid);
			if(brand==null){
				throw new BusinessException("品牌【"+bid+"】不存在");
			}
			if(!ARTIFICIALINPUT.equals(brand.getIsArtificialInput())){
				throw new BusinessException("品牌【"+brand.getName()+"】非手动添加不能删除");
			}
			if(!StringUtils.isEmpty(brand.getYougouBrandNo())){
				throw new BusinessException("品牌【"+brand.getName()+"】已经绑定不能删除");
			}
			taobaoBrandMapper.deleteBrand(bid);
			merchantOperationLogService.addMerchantOperationLog("TAOBAO_BAND_DELETE",  OperationType.TAOBAO_BAND, "删除淘宝品牌："+brand.getName()+ " 成功" , request);
		}
	}

	@Override
	public void updateBrand(TaobaoBrand brand,HttpServletRequest request) {
		
	}

}
