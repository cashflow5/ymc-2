/*
 * 作者:Mrvoce
 **/
package com.belle.infrastructure.listener;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import com.belle.infrastructure.annotation.Attribute;
import com.belle.infrastructure.constant.ExtConstant;

public class PropertityReadListener implements ServletContextListener{

//	@Resource
//	private IMemberLoginAccountDao dao;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		
		
//		for(int i=0;i<10000;i++){
//			IMemberLoginAccountDao dao = ac.getBean(IMemberLoginAccountDao.class);
//			List list = dao.getAll();
//			System.out.println("执行"+list.size());
//		}
		
		try {
			loadConfig();
			readProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Properties propertie = null;
	
	public void loadConfig(){
		propertie = new Properties();
		String filePath = PropertityReadListener.class.getClassLoader().getResource("/").getFile();
		filePath = filePath.substring(0, filePath.length())+"extconfig.properties";
//		System.out.println(this.getClass().getClassLoader().getResource("/").getPath()+"||");
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));   
		   
//        System.out.println(PropertityReadListener.class.getClassLoader().getResource(""));   
  
//        System.out.println(ClassLoader.getSystemResource(""));   
      
//        System.out.println(PropertityReadListener.class.getResource("/")); 

//        System.out.println(new File("/").getAbsolutePath());   
//        System.out.println(System.getProperty("user.dir"));   

		
//		System.out.println("读取配置扩展文件extconfig.properties:"+filePath);
        try {
        	FileInputStream   inputFile = new FileInputStream(filePath);
            propertie.load(inputFile);
            inputFile.close();
        } catch (Exception ex){
//        	System.out.println("extconfig.properties被发布于JBoss Server,获取路径....");
        	  try {
				filePath = PropertityReadListener.class.getResource("").getFile();
				  filePath = filePath.substring(1, filePath.indexOf("classes"))+"classes/extconfig.properties";
				  FileInputStream   inputFile = new FileInputStream(filePath);
				  propertie.load(inputFile);
				  inputFile.close();
//				  System.out.println("extconfig.properties装载完毕...");
			} catch (Exception e) {
//				System.err.println("配置文件，无法被装载，错误路径:"+filePath);
				e.printStackTrace();
			}
        }
	}
	
	
	public void readProperties()throws Exception{
//		System.out.println("设定ExtConstant常量类的值...");
		ExtConstant scc = (ExtConstant)Class.forName("com.belle.infrastructure.constant.ExtConstant").newInstance();
		Field[] fields = scc.getClass().getDeclaredFields();
		for(Field field:fields){
			Attribute attr = field.getAnnotation(Attribute.class);
			if(null != attr){				
				String name = attr.name();
//				System.out.println("load:"+name+"<-->"+propertie.getProperty(name));
				field.setAccessible(true);
				field.set(scc, propertie.getProperty(name));
			}
		}
	}

}
