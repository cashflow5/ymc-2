package com.belle.yitiansystem.merchant.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.yitiansystem.merchant.dao.mapper.MerchantTrainingFailLogMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantTrainingLogMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantTrainingMapper;
import com.belle.yitiansystem.merchant.exception.MerchantSystemException;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTraining;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingFailLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingLog;
import com.belle.yitiansystem.merchant.service.IMerchantTrainingService;
import com.belle.yitiansystem.merchant.util.DocConverter;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.util.UUIDGenerator;
import com.yougou.merchant.api.common.Query;
import com.belle.yitiansystem.merchant.enums.ClassificationEnum;

@Service
public class MerchantTrainingServiceImpl implements IMerchantTrainingService {
	
	private static Logger logger = Logger.getLogger(MerchantTrainingServiceImpl.class) ;
	
	@Resource
	private MerchantTrainingMapper trainingMapper;
	
	@Resource
	private MerchantTrainingLogMapper trainingLogMapper;
	
	@Resource
	private MerchantTrainingFailLogMapper trainingFailLogMapper;

	@Override
	public  PageFinder<MerchantTraining> queryTrainingList(Query query,
			MerchantTraining merchantTraining,RedisTemplate<String, Object> redisTemplate) {
		int count = 0;
		count = trainingMapper.countForQueryTrainingList(merchantTraining);
		if(count<1){
			return new PageFinder<MerchantTraining>(query.getPage(),query.getPageSize(),0,null);
		}else{
			List<MerchantTraining> list = trainingMapper.queryTrainingList(query,merchantTraining);
			buildListUseRedisCache(list,redisTemplate);
			return new PageFinder<MerchantTraining>(query.getPage(),query.getPageSize(),count,list);
		}
		
		
	}
	
	/** 从缓存中读取课程点击数 更新list */
	private void buildListUseRedisCache(List<MerchantTraining> list,RedisTemplate<String, Object> redisTemplate){
			Iterator<MerchantTraining> iter = list.iterator();
			MerchantTraining training = null;
			while(iter.hasNext()){
				training = iter.next();
				String totalClick = (String)redisTemplate.opsForHash().get(Constant.C_TRIANING_TOTAL_CLICK_KEY,training.getId());
				if(!StringUtils.isEmpty(totalClick)){
					training.setTotalClick(totalClick);
				}
			}
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = MerchantSystemException.class)
	public String saveTraining(MerchantTraining merchantTraining,String curUser) throws BusinessException {
		Short isPublish = merchantTraining.getIsPublish();
		String id = merchantTraining.getId();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		MerchantTrainingLog logVO = createLog(id,date,curUser);
		
		if(StringUtils.isEmpty(id)){//新增
			id = UUIDGenerator.getUUID();
			merchantTraining.setId(id);
			
			merchantTraining.setCreateTime( date );
			merchantTraining.setUpdateTime(date);
			merchantTraining.setCreator(curUser);
			merchantTraining.setOperator(curUser);
			merchantTraining.setTotalClick("0");
			merchantTraining.setOnTop(Constant.NO);
			merchantTraining.setOntopTime("");//重要：保证其值不为null，方便统一排序
			merchantTraining.setDeleteFlag(Constant.NO);
			merchantTraining.setIsPublish(Constant.NO);//先不发布
			try{
				trainingMapper.insertSelective(merchantTraining);
			}catch(Exception e){
				throw new BusinessException("新增课程插入数据库异常！"+e);
			}
			//  写日志
			logVO.setTrainingId(id);
			if( Constant.YES==isPublish ){
				logVO.setOperation("新增课程并发布");
			}else{
				logVO.setOperation("新增课程暂不发布");
			}
		}else{//修改
			merchantTraining.setUpdateTime(date);
			merchantTraining.setOperator(curUser);
			merchantTraining.setIsPublish(Constant.NO);//先不发布
			MerchantTraining merchantTrainingO = trainingMapper.selectByPrimaryKey(id);
			if( null!=merchantTrainingO ){
				try{
					trainingMapper.updateByPrimaryKeySelective(merchantTraining);
				}catch(Exception e){
					throw new BusinessException("更新课程操作数据库异常！"+e);
				}
				//  写日志
				if( Constant.YES==isPublish ){
					logVO.setOperation("更新课程并发布");
				}else{
					logVO.setOperation("更新课程暂不发布");
				}
				logVO.setOperationDesc( generateUpdateDesc(merchantTrainingO,merchantTraining) );
			}else{
				throw new BusinessException("数据异常：要更新的课程不存在！");
			}
		}
		
		try {
			trainingLogMapper.insert(logVO);
		} catch (Exception e) {
			logger.error("课程操作写入日志到数据库异常！");
			e.printStackTrace();
		}
		
		return id;
	}
	
	private MerchantTrainingLog createLog(String trainingId,String date,String curUser){
		MerchantTrainingLog logVO = new MerchantTrainingLog();
		logVO.setId(UUIDGenerator.getUUID());
		logVO.setTrainingId(trainingId);
		logVO.setOperated(date);
		logVO.setOperator(curUser);
		return logVO;
	}
	
	private String generateUpdateDesc(MerchantTraining merchantTrainingO,MerchantTraining merchantTrainingN){
		StringBuffer desc = new StringBuffer();
		if( null!=merchantTrainingN.getTitle() && !merchantTrainingN.getTitle().equalsIgnoreCase( merchantTrainingO.getTitle() )){
			desc.append(" 标题："+merchantTrainingO.getTitle() +" 更改为 "+ merchantTrainingN.getTitle());
		}
		
		if( null!=merchantTrainingN.getCatName() && !merchantTrainingN.getCatName().equalsIgnoreCase( merchantTrainingO.getCatName() )){
			String oldCat = ClassificationEnum.typeMap.get( merchantTrainingO.getCatName() );
			String newCat = ClassificationEnum.typeMap.get( merchantTrainingN.getCatName() );
			desc.append(" 分类："+oldCat +" 更改为 "+ newCat);
		}
		
		if( null!=merchantTrainingN.getFileName() && !merchantTrainingN.getFileName().equalsIgnoreCase( merchantTrainingO.getFileName() )){
			desc.append(" 文件："+merchantTrainingO.getFileName() +" 更改为 "+ merchantTrainingN.getFileName());
		}
		
		if( null!=merchantTrainingN.getDescription() && !merchantTrainingN.getDescription().equalsIgnoreCase( merchantTrainingO.getDescription() )){
			desc.append(" 简介更新 ");
		}
		
		if( null!=merchantTrainingN.getPicUrl() && !merchantTrainingN.getPicUrl().equalsIgnoreCase( merchantTrainingO.getPicUrl() )){
			desc.append(" 图片更新 ");
		}
		
		return desc.toString();
	}

	@Override
	public MerchantTraining selectByPrimaryKey(String id) {
		return trainingMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean transferFileForView(String filePath, String suffix)  {
		   if( suffix.endsWith("pdf")){
			   return transferPdfToSwf(filePath);
		   }else{
			   return transferToSwf(filePath);
		   }
			
	}
	
	private boolean transferPdfToSwf(String filePath) {
		DocConverter d = new DocConverter( filePath );
	     
	    try {
			d.converPdfToSwf();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用文件类型转换器时处理失败");
//			throw new BusinessException("调用文件类型转换器时处理失败");
			//异常信息写入专门的日志文件：/usr/local/ymc/transferFileLog/2015-05-log.log
		}
		
		return true;
	}


	private boolean transferToSwf(String filePath ) {
		
		DocConverter d = new DocConverter( filePath );
	     
	    try {
			d.conver();//调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;
		} catch (Exception e) {
			logger.error("调用文件类型转换器时处理失败");
//			throw new BusinessException("调用文件类型转换器时处理失败");
			//异常信息写入专门的日志文件：/usr/local/ymc/transferFileLog/2015-05-log.log
		}
		
		return true;
	}


	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = MerchantSystemException.class)
	public boolean deleteTraining(String id, String curUser) {
		MerchantTraining merchantTraining = new MerchantTraining();
		merchantTraining.setId(id);
		merchantTraining.setDeleteFlag(Constant.YES);
		if ( trainingMapper.updateByPrimaryKeySelective(merchantTraining)>0 ){
			//删除日志
			trainingLogMapper.deleteByTrainingId(id);
			return true;
		}else{
			logger.error("未找到要删除的课程记录");
			return false;
		}
	}


	@Override
	public boolean publishTraining(String id,Short flag, String curUser)  throws BusinessException{
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		MerchantTrainingLog logVO = createLog(id,date,curUser);
		MerchantTraining merchantTraining = trainingMapper.selectByPrimaryKey(id);
		merchantTraining.setUpdateTime(date);
		merchantTraining.setOperator(curUser);
		if( null!= merchantTraining){
			if( null!=flag && flag==Constant.YES ){
				String previewUrl = merchantTraining.getPreviewUrl();
				if( StringUtils.isEmpty(previewUrl)  ){
					throw new BusinessException("抱歉！文件未完成处理，请稍后发布，谢谢您的配合！");
				}else if( Constant.FAIL_TAG.equals( previewUrl.trim() )){
					throw new BusinessException("抱歉！文件后台处理失败不能发布该课程！请检查环境后，至编辑课程页面，重新上传课程文件！");
				}else {
					merchantTraining.setIsPublish(Constant.YES);
					logVO.setOperation("发布");
				}
			}else{
				merchantTraining.setIsPublish(Constant.NO);
				merchantTraining.setOnTop(Constant.NO);// 置顶失效
				merchantTraining.setOntopTime("");
				logVO.setOperation("取消发布");
			}
			
			trainingMapper.updateByPrimaryKeySelective(merchantTraining);
			//写日志
			trainingLogMapper.insert(logVO);
			return true;
		}else{
			throw new BusinessException("未找到操作置顶的课程记录!");
		}
	}


	@Override
	public boolean pushTraining(String id,Short flag, String curUser){
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		MerchantTrainingLog logVO = createLog(id,date,curUser);
		
		MerchantTraining merchantTraining = new MerchantTraining();
		merchantTraining.setId(id);
		merchantTraining.setOperator(curUser);
		merchantTraining.setUpdateTime(date);
		
		if( null!=flag && flag==Constant.YES ){
			merchantTraining.setOnTop(Constant.YES);
			merchantTraining.setOntopTime(date);
			logVO.setOperation("置顶");
		}else{
			merchantTraining.setOnTop(Constant.NO);
			merchantTraining.setOntopTime("");
			logVO.setOperation("取消置顶");
		}
		
		if ( trainingMapper.updateByPrimaryKeySelective(merchantTraining)>0 ){
			//写日志
			trainingLogMapper.insert(logVO);
			return true;
		}else{
			logger.error("未找到操作置顶的课程记录");
			return false;
		}
	}


	@Override
	public PageFinder<MerchantTrainingLog> queryTrainingOperationLog(
			String trainingId, Query query) {
		int count = 0;
		count = trainingLogMapper.countForQuery(trainingId);
		if(count<1){
			return new PageFinder<MerchantTrainingLog>(query.getPage(),query.getPageSize(),0,null);
		}else{
			List<MerchantTrainingLog> list = trainingLogMapper.selectByQuery(query, trainingId);
			return new PageFinder<MerchantTrainingLog>(query.getPage(),query.getPageSize(),count,list);
		}
	}


	@Override
	public void updateByPrimaryKeySelective(MerchantTraining record) {
		trainingMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int insertFailLog(MerchantTrainingFailLog record) {
		return trainingFailLogMapper.insert(record);
	}
	
	

}
