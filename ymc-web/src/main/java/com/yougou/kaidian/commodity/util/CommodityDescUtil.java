package com.yougou.kaidian.commodity.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitResultVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.common.util.HttpUtil;
import com.yougou.kaidian.common.util.PictureUtil;

/**
 * 商品描述处理工具类
 * 
 * @author huang.tao
 * 
 */
public class CommodityDescUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CommodityDescUtil.class);
	
	private static Pattern YOUGOU_DESC_LINK_A = Pattern.compile("(?i)<a\\s+href=[\',\"]{1}(.*?)[\',\"]{1}\\s*(.*?)>(.*?)<\\s*/\\s*a\\s*>");
	private final static String YOUGOU_DESC_LINK = "(?i)http://(www\\.yougou\\.com(/\\w*)?(\\.\\w+)?)|(outlets\\.yougou\\.com(/\\w*)?(\\.\\w+)?)";
	private final static String YOUGOU_DESC_BR = "(?i)<br\\s*/>";
	private final static String YOUGOU_DESC_INVALID_IMG = "(?i)<img\\s+src=[\',\"]{1}\\s*[\',\"]{1}\\s*(.*?)/>";
	private final static String YOUGOU_DESC_P_START = "(?i)<p\\s*(.*?)>";
	private final static String YOUGOU_DESC_P_END = "(?i)</p\\s*(.*?)>";
	private final static String YOUGOU_DESC_NBSP = "(?i)&nbsp;";
	
	// 优购合法图片链接Pattern （10.0.30.71为优购测试环境地址）
	private final static Pattern yougou_pattern_invalid_img = Pattern.compile("(?im)(http://10\\.0\\.30\\.193/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://i1\\.ygimg\\.cn/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://i2\\.ygimg\\.cn/{1,}\\S*\\.jpg\\?{0,1}\\w*)|(http://img\\.yougou\\.com/{1,}\\S*\\.jpg\\?{0,1}\\w*)");
	
	/**
	 * 下载商品描述图片
	 * 
	 * @return true | false
	 * @throws Exception
	 */
	public static boolean downloadCommodityDescImgs(CommoditySubmitVo submitVo, CommoditySubmitResultVo resultVo) throws Exception {
		List<String> imgUrls = new ArrayList<String>();
		// 筛选合法图片链接
		Matcher matcher = yougou_pattern_invalid_img.matcher(submitVo.getProdDesc());
		while (matcher.find()) {
			imgUrls.add(PictureUtil.normalizeImageUrl(matcher.group()));
		}
		submitVo.setDescImgUrls(imgUrls.toArray(new String[imgUrls.size()]));
		// 判断是否需要全部下载（根据commodityNo是否为空）
		if (StringUtils.isNotBlank(submitVo.getCommodityNo())) {
			StringBuffer sb = new StringBuffer(submitVo.getCommodityNo());
			sb.append("/").append(submitVo.getCommodityNo());
			sb.append("_").append("\\d{2}").append("_b\\.jpg");
			
			Pattern p = Pattern.compile(sb.toString());
			List<String> temp = new ArrayList<String>();
			for (String string : imgUrls) {
				if (p.matcher(string).find()) temp.add(string);
			}
			// 要么全下载、要么不下载
			if (imgUrls.size() == temp.size()) { imgUrls.removeAll(temp); }
		}
		submitVo.setDownloadImgUrls(imgUrls.toArray(new String[imgUrls.size()]));
		
		// 图片下载
		List<InputStream> imgStreamList = new ArrayList<InputStream>();
		for (String url : imgUrls) {
			InputStream stream = HttpUtil.getInputStreamByURL(url);
			if (null == stream) {
				resultVo.setErrorMsg(url + " 该商品描述图片不存在");
				return false;
			}
			imgStreamList.add(stream);
		}
		submitVo.setDescImgInputStreams(imgStreamList.toArray(new InputStream[imgStreamList.size()]));
		log.info("download commodity:[{}]({}) Desc image success count :{}.", new Object[]{submitVo.getSupplierCode(), submitVo.getCommodityNo(), imgStreamList.size()});
		
		return true;
	}
	
	/**
	 * 宝贝描述格式化 HTML
	 * 
	 * @param submitVo
	 * @param replaceMap
	 * @return 格式化后标准的HTML
	 */
	public static String descFormat(CommoditySubmitVo submitVo, Map<String, String> replaceMap) {
		if (submitVo == null || submitVo.getProdDesc() == null) {
			return "";
		}
		
		// 替换上传后的图片地址
		String desc = descPicsReplace(submitVo.getProdDesc(), replaceMap);
		
		// 处理宝贝描述中的<a href=""/>标签
		desc = descHrefReplace(desc);
		
		// 清理无效标签
		desc = deleteInvalidTag(desc);
		
		// 格式化HTML
		desc = "<p align=\"center\">" + desc + "</p>";
		log.info("commodity:[{}]({}) Desc Format end.", new Object[]{submitVo.getSupplierCode(), submitVo.getCommodityNo()});
		
		return desc;
	}
	
	/**
	 * 替换商品描述图
	 * @param submitVo
	 * @param replaceMap
	 * @return
	 */
	public static String descPicsReplace(String desc, Map<String, String> replaceMap) {
		if (replaceMap == null)
			return desc;
		
		// 防止同一序号的图片出现、采用后缀名替换法
		StringBuffer descbf = new StringBuffer(desc.replaceAll("(?i)\\.jpg", ".png"));
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> rep : replaceMap.entrySet()) {
			int start = descbf.indexOf(rep.getKey().replaceAll("(?i)\\.jpg", ".png"));
			if (start < 0) continue;
			
			sb.append(descbf.substring(0, start));
			sb.append(rep.getValue()).append("?").append(new Random().nextInt(100));
			sb.append(descbf.substring(start + rep.getKey().length()));
			descbf.delete(0, descbf.length());
			descbf.append(sb.toString());
			sb.delete(0, sb.length());
		}
		
		return descbf.toString();
	}
	
	/**
	 * 删除描述字段中的无效tag
	 * 
	 * @param desc
	 * @return 格式化后的标准HTML
	 */
	public static String deleteInvalidTag(String desc) {
		desc = desc.replaceAll(YOUGOU_DESC_P_START, "");
		desc = desc.replaceAll(YOUGOU_DESC_P_END, "");
		desc = desc.replaceAll(YOUGOU_DESC_BR, "\r\n");
		desc = desc.replaceAll(YOUGOU_DESC_INVALID_IMG, "");
		desc = desc.replaceAll(YOUGOU_DESC_NBSP, "");
		return desc;
	}
	
	/**
	 * 处理商品宝贝描述中的<a href="" />
	 * 	   只允许插入www.yougou.com或是outlets.yougou.com域名的超链接
	 * 
	 * @param prodDesc
	 * @return 格式化后的标准HTML
	 */
	public static String descHrefReplace(String prodDesc) {
		List<String> aList = new ArrayList<String>();
		List<String> hrefList = new ArrayList<String>();
		List<String> aTextList = new ArrayList<String>();
		
		Matcher matcher = YOUGOU_DESC_LINK_A.matcher(prodDesc);
		while (matcher.find()) {
			aList.add(matcher.group());
			hrefList.add(matcher.group(1));
			aTextList.add(matcher.group(3));
		}
		
		Pattern p = Pattern.compile(YOUGOU_DESC_LINK);
		for (int i = 0; i < aList.size(); i++) {
			Matcher ygm = p.matcher(hrefList.get(i));
			boolean isFind = false;
			while (ygm.find()) {
				isFind = true;
			}
			
			if (!isFind) prodDesc = prodDesc.replace(aList.get(i), aTextList.get(i));
		}
		return prodDesc;
	}
	
	/**
	 * 生成商品描述图在数据库商家商品图中的新名称(新增时用)
	 * 
	 * @param submitVo
	 * @param startInddex 图片起始序号
	 * @return
	 */
	public static String[] getDescImgDBNamesForCreate(CommoditySubmitVo submitVo) {
		String prefix = "getDescImgDBNamesForCreate#-> ";
		if (submitVo == null || submitVo.getDescImgUrls() == null || submitVo.getDescImgInputStreams() == null) {
			log.info("{} submitVo or descImgUrls or descImgInputStreams is empty", prefix);
			return null;
		}

		String[] descImgUrls = submitVo.getDescImgUrls();
		int length = descImgUrls.length;
		if (length == 0) {
			log.info("{} descImgUrls.length is zero", prefix);
			return null;
		} else if (length > CommodityConstant.DESC_IMG_MAX_COUNT) {
			log.info("{} desc img count exceed max {}" , prefix, CommodityConstant.DESC_IMG_MAX_COUNT);
			throw new RuntimeException("商品描述图片最多只能为 " + CommodityConstant.DESC_IMG_MAX_COUNT + " 张");
		}
		String fileName = null;
		String[] dbFileNames = new String[length];
		for (int i = 0; i < length; i++) {
			fileName = submitVo.getSupplierCode() + "_" + CommonUtil.addZeroBeforeNumber((i + 1), 2)
					+ CommodityConstant.DESC_IMG_NAME_SUFFIX + CommodityConstant.IMG_NAME_EXT_TYPE;
			log.info("{} merchant_commodity_pic_name: {}", prefix, fileName);
			dbFileNames[i] = fileName;
		}
		return dbFileNames;
	}
}
