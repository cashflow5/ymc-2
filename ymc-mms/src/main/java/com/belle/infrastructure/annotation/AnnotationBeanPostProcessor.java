package com.belle.infrastructure.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.ReflectionUtils;

/**
 *<P>
 * 2011-4-6下午03:27:30
 *<P>
 * 
 * @author liudi
 */
public class AnnotationBeanPostProcessor extends PropertyPlaceholderConfigurer
		implements BeanPostProcessor, InitializingBean {

	private Logger logger = Logger.getLogger(AnnotationBeanPostProcessor.class);

	private java.util.Properties pros;

	@SuppressWarnings("unchecked")
	private Class[] enableClassList = { String.class };

	private Resource[] locations;
	

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	@SuppressWarnings("unchecked")
	public void setEnableClassList(Class[] enableClassList) {
		this.enableClassList = enableClassList;
	}

	@Override
	public void loadProperties(Properties props) {
		if (this.locations != null) {
			//默认LINUX路径
			String systemBasePath = "/etc/yougouhtconf";
			
			File configDir = new File(systemBasePath);
			if (!configDir.exists()) {
				logger.error("配置文件目录不存在！"+systemBasePath);
				systemBasePath = "c:/yougouhtconf";
			}
	
			
			String	tempSystemBasePath = this.getClass().getResource("/").getFile().toString() ;
			PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
			for (int i = 0; i < this.locations.length; i++) {
				Resource location = this.locations[i];
				String  filePath = systemBasePath + File.separator + location.getFilename();
				File isFile = new File(filePath);
				if(!isFile.exists()){
					logger.error("加载配置文件出错！"+filePath);
					filePath = tempSystemBasePath+ File.separator + "config" +File.separator + location.getFilename();
				}
				InputStream is = null;
				try {
					is = new FileInputStream(filePath);
					propertiesPersister.load(props, is);
					pros = props;
				} catch (Exception e) {
					logger.error("加载配置文件出错！", e);
				} finally {
					try {
						if (is != null)
							is.close();
					} catch (IOException e) {

					}
				}
			}
		}

	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1)
			throws BeansException {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Property.class)) {
				if (filterType(field.getType().toString())) {
					Property p = field.getAnnotation(Property.class);
					try {
						ReflectionUtils.makeAccessible(field);
						field.set(bean, pros.getProperty(p.name()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	private boolean filterType(String type) {
		if (type != null) {
			for (Class c : enableClassList) {
				if (c.toString().equals(type)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1)
			throws BeansException {
		return bean;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//pros = mergeProperties();
	}


	public java.util.Properties getPros() {
		return pros;
	}

}
