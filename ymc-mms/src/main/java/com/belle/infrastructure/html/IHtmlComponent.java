package com.belle.infrastructure.html;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 *
 * @descript：网站静态化接口
 * @author  ：方勇
 * @email   ：fangyong@broadengate.com
 * @time    ： 2011-5-17 上午09:19:07
 */
@Component
public interface IHtmlComponent {


    /**
     * 根据Freemarker模板文件路径、Map数据生成HTML静态文件
     *
     * @param templateFilePath Freemarker模板文件路径
     * @param htmlFilePath 生成HTML静态文件存放路径
     * @param data Map数据
     *
     */
    public void buildHtml(String templateFilePath, String htmlFilePath, ModelMap data);

    /**
     * 防止重复生成Html
     * @param htmlFilePath
     * @param uuid
     * @return
     */
    public String replaceHtmlName(String htmlFilePath, String uuid) ;

    /**
     * 生成帮助HTML静态文件
     *
     */
    public void helpBuildHtml(ModelMap data ,String fileName);

    
    /**
     * 生成首页HTML静态文件
     *
     */
    public void indexBuildHtml(ModelMap data);

    /**
     * 生成登录HTML静态文件
     *
     */
    public void loginBuildHtml(ModelMap data);

    /**
     * 生成文章内容HTML静态文件
     *
     * data数据格式如下:
     * {content=测试, MAX_PAGE_CONTENT_COUNT=2000}
     *
     * @param data      基础数据+文章数据
     * @param uuid      编号
     */
    public void articleContentBuildHtml(ModelMap data,String uuid);


    /**
     * 生成商品内容HTML静态文件
     *
     * @param data                        基础数据+商品数据
     * @param uuid                        编号
     * @param htmlFilePath                生成Html路径
     */
    public String productContentBuildHtml(ModelMap data,String uuid);

    /**
     * 生成商品图片Flash静态文件
     * @param data
     * @param uuid
     * @return
     */
    public String productPicFlashBuildHtml(ModelMap data, String uuid) ;

    /**
     * 生成商品分类HTML静态文件
     *
     * @param data                        基础数据+商品分类数据
     * @param uuid                        编号
     * @param htmlFilePath                生成Html路径
     */
    public void productCategoryBuildHtml(ModelMap data,String uuid);

    /**
     * 生成文章分类HTML静态文件
     *
     * @param data                        基础数据+文章分类数据
     * @param uuid                        编号
     * @param htmlFilePath                生成Html路径
     */
    public void articleCategoryBuildHtml(ModelMap data,String uuid);

    /**
     * 通用js文件
     */
    public void baseJavascriptBuildHtml();

    /**
     * 错误页HTML静态文件
     */
    public void errorPageBuildHtml();

    /**
     * 权限错误页HTML静态文件
     */
    public void errorPageAccessDeniedBuildHtml();

    /**
     * 错误页500 HTML静态文件
     */
    public void errorPage500BuildHtml();

    /**
     * 错误页404 HTML静态文件
     */
    public void errorPage404BuildHtml();

    /**
     * 错误页403 HTML静态文件
     */
    public void errorPage403BuildHtml();

    /**
     * 错误页商品下架   HTML静态文件
     */
    public void errorPageDownBuildHtml();

    // 获取公共数据
    public ModelMap getCommonData();
    
    public void linkPageBuildHtml(ModelMap data);

}
