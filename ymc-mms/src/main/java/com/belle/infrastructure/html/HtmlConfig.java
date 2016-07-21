package com.belle.infrastructure.html;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.belle.infrastructure.util.UUIDUtil;


/**
 *
 * @descript：Bean类 - 静态生成模板配置
 * @author  ：方勇
 * @email   ：fangyong@broadengate.com
 * @time    ： 2011-5-18 上午06:12:36
 */
public class HtmlConfig {

    public static HtmlConfig htmlConfig = null;

    public static final String MAX_PAGE_CONTENT_COUNT = "2000";// 新闻最大字符长度
    public static final String PRODUCT_KEY = "bellep";// 商品详情KEY
    public static final String PRODUCT_CATEGORY_KEY = "bellepc";// 商品分类KEY
    public static final String ARTICLE_KEY = "bellea";// 文章详情KEY
    public static final String ARTICLE_CATEGORY_KEY = "belleac";// 文章分类KEY
    public static final String PREFIX = "id";// 按ID值生成html文件名
    public static final String CHANNEL_lIST = "channelList";//菜单栏目
	public static final String REPLACE_UUID = "{uuid}";// 随机UUID字符串替换
	public static final String REPLACE_DATE_YY = "{date_yyyy}";// 当前日期字符串替换(年)
	public static final String REPLACE_DATE_MM = "{date_MM}";// 当前日期字符串替换(月)
	public static final String REPLACE_DATE_DD = "{date_dd}";// 当前日期字符串替换(日)
	public static final String REPLACE_DATE_HH = "{date_HH}";// 当前日期字符串替换(时)

	public static final String BASE_JAVASCRIPT = "baseJavascript";// baseJavascript
	public static final String INDEX = "index";// 首页
	public static final String LOGIN = "login";// 登录
	public static final String BRAND_TEMPLATE = "brandTemplate";// 店铺模板
	public static final String BRAND_SERIES = "brandSeries";// 品牌分类
	public static final String BRAND_DAQO = "brandDaqo";// 品牌大全
	public static final String CHANNEL_DETAIL = "channelDetail";//菜单栏目
	public static final String ACTIVITIES_TOPICS = "activitiesTopics";//活动专题
	public static final String TUAN_FORWARD = "tuanForward";//栏目跳转团购页
	public static final String TUAN_PROMOTION = "tuanPromotion";//团购活动项
	public static final String TUAN_PREV = "tuanPrev";//往期团购
	public static final String TUAN_NEXT = "tuanNext";//下期团购
	public static final String TUAN_HELP = "tuanHelp";//团购帮助
	public static final String TUAN_DETAIL = "tuanDetail";//团购帮助
	public static final String ARTICLE_CONTENT = "articleContent";// 文章内容
	public static final String ARTICLE_LIST = "articleList";// 文章分类
	public static final String PRODUCT_CONTENT = "productContent";// 商品内容
	public static final String PRODUCT_PIC = "productPicContent";// 商品图片Flash
	public static final String HEADER = "header";
	public static final String FRIEND_LINK = "friendLink";//友情链接
	public static final String HELP_MESSAGE = "helpMessage";//友情链接
	public static final String GOODS_DAQO = "goodsDaqo";//




	public static final String BRAND_INDEX = "brandindex";// 品牌首页

	public static final String PRODUCT_PICTURE_LIST = "productPictureList";// 商品分类
	public static final String ERROR_PAGE = "errorPage";// 错误页
	public static final String ERROR_PAGE_ACCESS_DENIED = "errorPageAccessDenied";// 权限错误页
	public static final String ERROR_PAGE_500 = "errorPage500";// 错误页500
	public static final String ERROR_PAGE_404 = "errorPage404";// 错误页404
	public static final String ERROR_PAGE_403 = "errorPage403";// 错误页403
	public static final String ERROR_PAGE_DOWN = "errorPageDown";// 错误页商品下架

	private String name;// 配置名称
	private String description;// 描述
	private String templateFilePath;// Freemarker模板文件路径
	private String htmlFilePath;// 生成HTML静态文件存放路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	// 获取生成HTML静态文件存放路径
	public String getHtmlFilePath() {
		htmlFilePath = htmlFilePath.replace(REPLACE_UUID, UUIDUtil.getUUID());
		SimpleDateFormat yyDateFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat mmDateFormat = new SimpleDateFormat("MM");
		SimpleDateFormat ddDateFormat = new SimpleDateFormat("dd");
		SimpleDateFormat hhDateFormat = new SimpleDateFormat("HH");
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_YY, yyDateFormat.format(new Date()));
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_MM, mmDateFormat.format(new Date()));
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_DD, ddDateFormat.format(new Date()));
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_HH, hhDateFormat.format(new Date()));
		return htmlFilePath;
	}
    /**
     * 单例
     * @return
     */
    public synchronized static HtmlConfig getInstance(){
        if(null == htmlConfig){
            htmlConfig = new HtmlConfig();
        }
        return htmlConfig;

    }
    /**
     * 防止重复生成Html
     * @param htmlFilePath
     * @param uuid
     * @return
     */
    public String getHtmlFilePath(String htmlFilePath, String uuid) {
        StringBuffer buffer = new StringBuffer();
        int beginIndex = htmlFilePath.indexOf("/");
        int endIndex = htmlFilePath.lastIndexOf("/");
        buffer.append(htmlFilePath.substring(beginIndex, endIndex + 1));
        buffer.append(uuid).append(".html");
        return buffer.toString();
    }
}
