package com.belle.yitiansystem.merchant.web.controller;

import java.io.BufferedOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAccessTrackVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAppkeyVo;
import com.belle.yitiansystem.merchant.model.vo.TaobaoAuthorizeVo;
import com.belle.yitiansystem.merchant.service.ITaobaoService;
import com.yougou.merchant.api.common.Query;
import com.yougou.tools.common.utils.UUIDUtil;

/**
 * 淘宝授权管理
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/yitiansystem/merchants/taobao")
public class TaobaoAuthorizeController {

	private final String BASE_PATH = "yitiansystem/merchants/taobao/";

	private static final Logger logger = LoggerFactory.getLogger(TaobaoAuthorizeController.class);
	@Resource
	private ITaobaoService taobaoService;

	/**
	 * 淘宝授权管理查询
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 */
	@RequestMapping("taobao_authorize")
	public String taobaoAuthorize(ModelMap model, TaobaoAuthorizeVo vo, Query query, HttpServletRequest request) {
		PageFinder<TaobaoAuthorizeVo> pageFinder = taobaoService.getTaobaoAuthorizeList(vo, query);
		model.addAttribute("vo", vo);
		TaobaoAppkeyVo useablelistvo = new TaobaoAppkeyVo();
		useablelistvo.setIsUseble(1);
		model.addAttribute("appKeyList", taobaoService.getTaobaoAppkeyList(useablelistvo));
		model.addAttribute("pageFinder", pageFinder);
		return BASE_PATH + "taobao_authorize_list";
	}

	/**
	 * 淘宝授权更新
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update_taobao_authorize")
	public String updateAuthorize(ModelMap model, TaobaoAuthorizeVo vo, Query query, HttpServletRequest request) {
		String operator = GetSessionUtil.getSystemUser(request).getLoginName();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setOperater(operator);
		vo.setOperated(format.format(new Date()));
		return String.valueOf(taobaoService.updateTaobaoAuthorize(vo));
	}

	/**
	 * 导出淘宝授权txt
	 * 
	 * @param modelMap
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("export_taobao_txt")
	public void exportTaobaoTxt(ModelMap model, TaobaoAuthorizeVo vo, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + vo.getTopAppkey() + ".txt");
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		String enter = "\r\n";
		ServletOutputStream outSTr = null;
		try {
			List<TaobaoAuthorizeVo> list = taobaoService.getTaobaoAuthorizedList(vo);
			outSTr = response.getOutputStream();
			buff = new BufferedOutputStream(outSTr);
			for (TaobaoAuthorizeVo taobaoAuthorizeVo : list) {
				write.append(taobaoAuthorizeVo.getNickName());
				write.append(enter);
			}
			buff.write(write.toString().getBytes("GBK"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 淘宝api调用轨迹信息查询
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("taobao_access_track")
	public String taobaoAccessTrack(ModelMap model, TaobaoAccessTrackVo vo, Query query, HttpServletRequest request) {
		// 默认设置查询起止时间，默认查一个月的
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isEmpty(vo.getStartTime()) && StringUtils.isEmpty(vo.getEndTime())) {
				vo.setStartTime(format.format(DateUtils.addDays(new Date(), -30)));
				vo.setEndTime(format.format(new Date()));
			} else if (StringUtils.isEmpty(vo.getStartTime())) {
				Date createTimeEnd = format.parse(vo.getEndTime());
				vo.setStartTime(format.format(DateUtils.addDays(createTimeEnd, -30)));
			} else if (StringUtils.isEmpty(vo.getEndTime())) {
				Date createTimeStart = format.parse(vo.getStartTime());
				vo.setEndTime(format.format(DateUtils.addDays(createTimeStart, 30)));
			}
		} catch (ParseException e) {
			logger.error("查询列表时，时间格式转换出现问题！");
		}
		PageFinder<TaobaoAccessTrackVo> pageFinder = taobaoService.getTaobaoAccessTrackList(vo, query);
		model.addAttribute("vo", vo);
		model.addAttribute("pageFinder", pageFinder);
		return BASE_PATH + "taobao_access_track";
	}

	/**
	 * 查询淘宝开放平台APPKEY信息
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 */
	@RequestMapping("taobao_appkey")
	public String taobaoAppkey(ModelMap model, TaobaoAppkeyVo vo, Query query, HttpServletRequest request) {
		PageFinder<TaobaoAppkeyVo> pageFinder = taobaoService.getTaobaoAppkeyList(vo, query);
		model.addAttribute("vo", vo);
		model.addAttribute("pageFinder", pageFinder);
		return BASE_PATH + "taobao_appkey_list";
	}

	/**
	 * 淘宝APPKEY更新
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update_taobao_appkey")
	public String updateAppkey(ModelMap model, TaobaoAppkeyVo vo, Query query, HttpServletRequest request) {
		String operator = GetSessionUtil.getSystemUser(request).getLoginName();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setOperater(operator);
		vo.setOperated(format.format(new Date()));
		return String.valueOf(taobaoService.updateTaobaoAppkey(vo));
	}

	/**
	 * 淘宝APPKEY插入
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add_taobao_appkey")
	public String add_taobao_appkey(ModelMap model, TaobaoAppkeyVo vo, Query query, HttpServletRequest request) {
		TaobaoAppkeyVo result = taobaoService.getTaobaoAppkeyByAppkey(vo);
		if (null != result) {
			return JSONObject.toJSONString("exist");
		}
		String operator = GetSessionUtil.getSystemUser(request).getLoginName();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setId(UUIDUtil.getUUID());
		vo.setOperater(operator);
		vo.setOperated(format.format(new Date()));
		return String.valueOf(taobaoService.addTaobaoAppkey(vo));
	}
	
	/**
	 * 淘宝APPKEY删除
	 * 
	 * @param modelMap
	 * @param vo
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete_taobao_authorize")
	public String deleteTaobaoAuthorize(ModelMap model, TaobaoAuthorizeVo vo, HttpServletRequest request) {
		TaobaoAuthorizeVo taobaoAuthorizeVo = taobaoService.getTaobaoAuthorizeById(vo);
		if (StringUtils.isNotBlank(taobaoAuthorizeVo.getNid())) {
			return JSONObject.toJSONString("warn");
		} else {
			int result = taobaoService.deleteTaobaoAuthorizeById(vo);
			if (result > 0) {
				return String.valueOf(Boolean.TRUE);
			}
		}
		return String.valueOf(Boolean.FALSE);
	}
}
