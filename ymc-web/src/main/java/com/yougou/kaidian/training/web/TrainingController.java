package com.yougou.kaidian.training.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.order.model.TrainingInfoDto;
import com.yougou.kaidian.training.service.ITrainingService;

/**
 * 
 * @author zhang.f1
 * @directions:商家中心订单模块控制器
 * @author： daixiaowei
 * @create： 2012-3-9 下午12:00:57
 * @history：
 * @version:
 */
@Controller
@RequestMapping("training")
public class TrainingController extends BaseController {
	
	@Resource
	private ITrainingService trainingService;
	
	@Resource
	private Properties configProperties;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	private final String id="";
	
	/**
	 * 查询所有培训中心课件，按照发布时间降序排序
	 * @return
	 */
	@RequestMapping("/training_list")
	public String queryTranningList( Query query,HttpServletRequest request,ModelMap modelMap){
		
		String file_type = request.getParameter("file_type");
		String cat_name = request.getParameter("cat_name");
		
		Map params = new HashMap();
		params.put("file_type",file_type==null || "".equals(file_type) ? null : Integer.parseInt(file_type));
		params.put("cat_name", cat_name);	
		params.put("query", query);
		//默认20条每页
		query.setPageSize(20);
		//查询列表数据
		PageFinder<TrainingInfoDto> pageFinder =  trainingService.queryTrainingList(params);
		if (pageFinder != null){
			//循环迭代列表，从 redis中获取课程学习人数
			List<TrainingInfoDto> list = pageFinder.getData();
			if(list!=null && list.size()>0){
				//从applicaiton.properties配置文件中获取培训中心文件存放路径
				String readPath = configProperties.getProperty(Constant.TRAINING_FTP_READ_PATH, "http://10.0.30.193/pics/merchantpics/university/");
				for(TrainingInfoDto dto : list){
					// 从redis 缓存内获取总学习人数（学习人数累加操作缓存数据，定时持久至数据库中）
					String total_click = (String) redisTemplate.opsForHash().get(CacheConstant.C_TRIANING_TOTAL_CLICK_KEY, dto.getId());
					if(total_click != null && !"".equals(total_click)){
						dto.setTotal_click(total_click);
					
					}
					// 设置完整的主图路径
					String pic_url = dto.getPic_url();
					dto.setPic_url(readPath+pic_url);
				}
			}
		}
		
		modelMap.put("pageFinder", pageFinder);
		modelMap.put("file_type", file_type);
		modelMap.put("cat_name", cat_name);
		return "/manage/training/training_list" ; 
		
	}	
	
	
	/**
	 * 根据课程ID查询课程详情，返回详情页面播放课程;将课程学习人数+1
	 * @return
	 */
	@RequestMapping("/training_info")
	public String queryTranningInfoById(final TrainingInfoDto trainingDto,HttpServletRequest request,ModelMap modelMap){
		
		/*Runnable run = new Runnable() {					
			@Override
			public void run() {
				addTotalClick(trainingDto.getId());				
			}
		};
		new Thread(run).start();*/
		
		String readPath = configProperties.getProperty(Constant.TRAINING_FTP_READ_PATH, "http://10.0.30.193/pics/merchantpics/university/");
		
		TrainingInfoDto trainingInfo= trainingService.queryTrainingInfoById(trainingDto);
		// 设置完整主图，文件路径
		String pic_url = trainingInfo.getPic_url();
		String preview_url = trainingInfo.getPreview_url();
		trainingInfo.setPic_url(readPath+pic_url);
		trainingInfo.setPreview_url(readPath+preview_url);
		
		// 从redis 缓存内获取总学习人数（学习人数累加操作缓存数据，定时持久至数据库中）
		String total_click = (String) redisTemplate.opsForHash().get(CacheConstant.C_TRIANING_TOTAL_CLICK_KEY, trainingDto.getId());
		
		if(total_click==null || "".equals(total_click)){
			total_click = trainingInfo.getTotal_click()==null ? "0" : trainingInfo.getTotal_click();
		
		}else{
			trainingInfo.setTotal_click(total_click);
			
		}
		// 缓存该课程学习人数值，将数据库内值赋给缓存,并且+1
		redisTemplate.opsForHash().put(CacheConstant.C_TRIANING_TOTAL_CLICK_KEY, trainingDto.getId(),String.valueOf(Integer.parseInt(total_click)+1));
		
		modelMap.put("trainingInfo", trainingInfo);
		modelMap.put("readPath", readPath);
		return "/manage/training/training_info" ; 
		
	}	
	
	/**
	 * 根据课程ID将课程学习人数同步+1,避免脏数据
	 * @param trainingDto
	 */
	/*private synchronized void addTotalClick(String id){
		trainingService.updateTotalClick(id);
	}*/
	
}
