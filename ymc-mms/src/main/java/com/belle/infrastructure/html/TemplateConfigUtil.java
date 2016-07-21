package com.belle.infrastructure.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.context.ContextLoader;

import com.belle.infrastructure.uploadImages.SpringUtil;

/**
 * 
 * @descript：工具类 - 模板配置 
 * @author  ：方勇
 * @email   ：fangyong@broadengate.com
 * @time    ： 2011-5-18 上午03:09:03
 */
public class TemplateConfigUtil {

    public static final String CONFIG_FILE_NAME = "template.xml"; // 模板配置文件名称
    
    

    /**
     * 获取动态模板配置
     * 
     * @return DynamicConfig集合
     */
    @SuppressWarnings("unchecked")
    public static List<DynamicConfig> getDynamicConfigList() {

        File configFile = null;
        Document document = null;
        try {
//            String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent()+ "/ftl/template/" + CONFIG_FILE_NAME;
        	String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/template/" + CONFIG_FILE_NAME;
        	configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element htmlConfigElement = (Element) document.selectSingleNode("belle/dynamicConfig");
        Iterator<Element> iterator = htmlConfigElement.elementIterator();
        List<DynamicConfig> dynamicConfigList = new ArrayList<DynamicConfig>();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            String description = element.element("description").getTextTrim();
            String templateFilePath = element.element("templateFilePath").getTextTrim();
            DynamicConfig dynamicConfig = new DynamicConfig();
            dynamicConfig.setName(element.getName());
            dynamicConfig.setDescription(description);
            dynamicConfig.setTemplateFilePath(templateFilePath);
            dynamicConfigList.add(dynamicConfig);
        }

        return dynamicConfigList;
    }

    /**
     * 根据动态模板配置名称获取DynamicConfig对象
     * 
     * @return DynamicConfig对象
     */
    public static DynamicConfig getDynamicConfig(String name) {
        Document document = null;
        try {
//            String configFilePath = new File(
//                                             Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent()
//                                    + "/ftl/template/" + CONFIG_FILE_NAME;
        	String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/template/" + CONFIG_FILE_NAME;
            File configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element element = (Element) document.selectSingleNode("belle/dynamicConfig/" + name);
        String description = element.element("description").getTextTrim();
        String templateFilePath = element.element("templateFilePath").getTextTrim();
        DynamicConfig dynamicConfig = new DynamicConfig();
        dynamicConfig.setName(element.getName());
        dynamicConfig.setDescription(description);
        dynamicConfig.setTemplateFilePath(templateFilePath);
        return dynamicConfig;
    }

    /**
     * 根据DynamicConfig对象读取模板文件内容
     * 
     * @return 模板文件内容
     */
    public static String readTemplateFileContent(DynamicConfig dynamicConfig) {
        String templateFileContent = null;
        try {
            File templateFile = new File(SpringUtil.getServletContextPath() + dynamicConfig.getTemplateFilePath());

            templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templateFileContent;
    }

    /**
     * 写入模板文件内容
     * 
     */
    public static String writeTemplateFileContent(DynamicConfig dynamicConfig, String templateFileContent) {
        try {
            File templateFile = new File(SpringUtil.getServletContextPath() + dynamicConfig.getTemplateFilePath());

            FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templateFileContent;
    }

    /**
     * 获取生成静态模板配置
     * 
     * @return HtmlConfig集合
     */
    @SuppressWarnings("unchecked")
    public static List<HtmlConfig> getHtmlConfigList() {
        List<HtmlConfig> htmlConfigList = new ArrayList<HtmlConfig>();
        File configFile = null;
        Document document = null;
        try {
//            String configFilePath = new File(
//                                             Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent()
//                                    + "/ftl/template/" + CONFIG_FILE_NAME;
        	String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/template/" + CONFIG_FILE_NAME;
            configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element htmlConfigElement = (Element) document.selectSingleNode("belle/htmlConfig");
        Iterator<Element> iterator = htmlConfigElement.elementIterator();
        htmlConfigList = new ArrayList<HtmlConfig>();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            String description = element.element("description").getTextTrim();
            String templateFilePath = element.element("templateFilePath").getTextTrim();
            String htmlFilePath = element.element("htmlFilePath").getTextTrim();
            HtmlConfig htmlConfig = new HtmlConfig();
            htmlConfig.setName(element.getName());
            htmlConfig.setDescription(description);
            htmlConfig.setTemplateFilePath(templateFilePath);
            htmlConfig.setHtmlFilePath(htmlFilePath);
            htmlConfigList.add(htmlConfig);
        }
        return htmlConfigList;
    }

    /**
     * 根据生成静态模板配置名称获取HtmlConfig对象
     * 
     * @return HtmlConfig对象
     */
    public static HtmlConfig getHtmlConfig(String name) {
        Document document = null;
        try {
        	String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/template/" + CONFIG_FILE_NAME;
            File configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element element = (Element) document.selectSingleNode("belle/htmlConfig/" + name);
        if(element == null){
        	return null;
        }
        String description = element.element("description").getTextTrim();
        String templateFilePath = element.element("templateFilePath").getTextTrim();
        String htmlFilePath = element.element("htmlFilePath").getTextTrim();
        HtmlConfig htmlConfig = new HtmlConfig();
        htmlConfig.setName(element.getName());
        htmlConfig.setDescription(description);
        htmlConfig.setTemplateFilePath(templateFilePath);
        htmlConfig.setHtmlFilePath(htmlFilePath);
        return htmlConfig;
    }

    /**
     * 根据HtmlConfig对象读取模板文件内容
     * 
     * @return 模板文件内容
     */
    public static String readTemplateFileContent(HtmlConfig htmlConfig) {
        String templateFileContent = null;
        try {
            File templateFile = new File(SpringUtil.getServletContextPath() + htmlConfig.getTemplateFilePath());
            templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templateFileContent;
    }

    /**
     * 写入模板文件内容
     * 
     */
    public static String writeTemplateFileContent(HtmlConfig htmlConfig, String templateFileContent) {
        try {
            File templateFile = new File(SpringUtil.getServletContextPath() + htmlConfig.getTemplateFilePath());
            FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templateFileContent;
    }

    /**
     * 获取邮件模板配置
     * 
     * @return MailConfig集合
     */
    @SuppressWarnings("unchecked")
    public static List<MailConfig> getMailConfigList() {
        Document document = null;
        try {
//            String configFilePath = new File(
//                                             Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent()
//                                    + "/ftl/template/" + CONFIG_FILE_NAME;
            String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/template/" + CONFIG_FILE_NAME;
            File configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element mailConfigElement = (Element) document.selectSingleNode("belle/mailConfig");
        Iterator<Element> iterator = mailConfigElement.elementIterator();
        List<MailConfig> mailConfigList = new ArrayList<MailConfig>();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            String description = element.element("description").getTextTrim();
            String subject = element.element("subject").getTextTrim();
            String templateFilePath = element.element("templateFilePath").getTextTrim();
            MailConfig mailConfig = new MailConfig();
            mailConfig.setName(element.getName());
            mailConfig.setDescription(description);
            mailConfig.setSubject(subject);
            mailConfig.setTemplateFilePath(templateFilePath);
            mailConfigList.add(mailConfig);
        }
        return mailConfigList;
    }

    /**
     * 根据邮件模板配置名称获取MailConfig对象
     * 
     * @return MailConfig对象
     */
    public static MailConfig getMailConfig(String name) {
        Document document = null;
        try {
//            String configFilePath = new File(
//                                             Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent()
//                                    + "/ftl/template/" + CONFIG_FILE_NAME;
        	String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/template/" + CONFIG_FILE_NAME;
            File configFile = new File(configFilePath);
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element element = (Element) document.selectSingleNode("belle/mailConfig/" + name);
        String description = element.element("description").getTextTrim();
        String subject = element.element("subject").getTextTrim();
        String templateFilePath = element.element("templateFilePath").getTextTrim();
        MailConfig mailConfig = new MailConfig();
        mailConfig.setName(element.getName());
        mailConfig.setDescription(description);
        mailConfig.setSubject(subject);
        mailConfig.setTemplateFilePath(templateFilePath);
        return mailConfig;
    }

    /**
     * 根据MailConfig对象读取模板文件内容
     * 
     * @return 模板文件内容
     */
    public static String readTemplateFileContent(MailConfig mailConfig) {
        // ServletContext servletContext = SpringUtil.getServletContext();
        // File templateFile = new File(servletContext.getRealPath(mailConfig.getTemplateFilePath()));
        String templateFileContent = null;
        try {
            File templateFile = new File(SpringUtil.getServletContextPath() + mailConfig.getTemplateFilePath());
            templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templateFileContent;
    }

    /**
     * 写入模板文件内容
     * 
     */
    public static String writeTemplateFileContent(MailConfig mailConfig, String templateFileContent) {
        try {
            File templateFile = new File(SpringUtil.getServletContextPath() + mailConfig.getTemplateFilePath());
            FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return templateFileContent;
    }

}
