package com.belle.infrastructure.uploadImages;

import java.io.File;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;

/**
 * 工具类 - Spring
 */

public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static ApplicationContext applicationContext;

    /**
     * 根据Bean名称获取实例
     * 
     * @param name Bean注册名称
     * 
     * @return bean实例
     * 
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
//        if (null == applicationContext) {
//            ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
//            applicationContext = ctx;
//            ctx.getBean(name);
//        }
        Object bean = ContextLoader.getCurrentWebApplicationContext().getBean(name);
        return bean;
//        return applicationContext.getBean(name);
    }
    
    public static void main(String[] args) {
    	 FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) getBean("freemarkerConfig");
	}

    /**
     * 获取容器的上下文
     */
    public static ServletContext getServletContext() throws BeansException {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        if(null!=wac){
            
            return wac.getServletContext();
        }
        return null;
    }

    /**
     * 当前工程的相对路径
     * 
     * @return
     * @throws Exception
     */
    public static String getServletContextPath() {
        File rootFile = null;
        try {
//            rootFile = new File(
//                                new File(
//                                         Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent());
        	String configFilePath =  ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/WEB-INF/ftl/";
        	rootFile =new File(new File(configFilePath).getParent());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rootFile.getParent();

    }

    /**
     * 当前工程的上下文的URL
     * 
     * @return
     * @throws Exception
     */
    public static String getServletContextURL() {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return wac.getServletContext().getContextPath();
    }

    /**
     * 模板文件的相对路径
     * 
     * @return
     * @throws Exception
     */
    public static File getDirectoryForTemplateLoading() {

        File rootFile = null;
        rootFile = new File(getServletContextPath());
        return rootFile;

    }

    /**
     * 获取spring的FreeMarket上下文
     */
    public static Configuration getConfiguration() throws BeansException {

        FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) getBean("freemarkerConfig");
        return freeMarkerConfigurer.getConfiguration();
    }
}
