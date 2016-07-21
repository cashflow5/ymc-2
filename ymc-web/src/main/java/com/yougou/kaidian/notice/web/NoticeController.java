package com.yougou.kaidian.notice.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.notice.model.pojo.MctNotice;
import com.yougou.kaidian.notice.service.IMctNoticeService;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.SupplierVo;



/**
 * @directions:公告
 * @author： lm
 * @create： 2012-3-9 下午12:00:57
 * @history：
 * @version:
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private final static Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Resource
	private IMctNoticeService mctNoticeService;
	@Resource
	private ISupplierService iSupplierService;
	
	/**
	 * 查询列表
	 * 
	 * @param orderSubExpand
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/queryAll")
	public String queryMctNoticeList(HttpServletRequest request, ModelMap map, Query query) throws Exception {
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		SupplierVo _vo = iSupplierService.getSupplierByMerchantCode(MapUtils.getString(mapMerchantUser, "supplier_code"));
		Integer identifier = _vo.getIsInputYougouWarehouse();
		query = (query == null ? new Query() : query);
		PageFinder<MctNotice> pageFinder=mctNoticeService.queryMctNoticeList(query,identifier.toString());
		map.put("pageFinder", pageFinder);
		return "manage/notice/notice_list";
	}
	
	@ResponseBody
	@RequestMapping("/querytop5")
	public String queryMctNoticeListTop5(HttpServletRequest request, ModelMap map) throws Exception {
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		SupplierVo _vo = iSupplierService.getSupplierByMerchantCode(MapUtils.getString(mapMerchantUser, "supplier_code"));
		Integer identifier = _vo.getIsInputYougouWarehouse();
		List<MctNotice> listMctNotice=mctNoticeService.queryMctNoticeListTop5(identifier.toString());
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id")||name.equals("title")||name.equals("createTime")||name.equals("noticeType")||name.equals("isRed")) {
					return false;
				}
				return true;
			}
		});
		config.registerJsonValueProcessor(Date.class,  new JsonValueProcessor() {
		    private SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd"); 
		    
			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				return dateFormat.format((Date)arg1); 
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return dateFormat.format((Date)arg0); 
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(listMctNotice, config);
		return jsonArray.toString();
	}
	
	@RequestMapping("/showdetail")
	public String queryMctNoticeById(HttpServletRequest request, ModelMap map,String id) throws Exception {
		MctNotice mctNotice=mctNoticeService.queryMctNoticeById(id);
		map.put("mctNotice", mctNotice);
		return "manage/notice/detail";
	}
}
