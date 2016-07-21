package com.yougou.api.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.VfsResource;

import com.yougou.api.Implementor;
import com.yougou.api.interceptor.Interceptor;
import com.yougou.api.validator.Validator;
import com.yougou.api.web.servlet.AbstractFilter;

/**
 * 
 * @author 杨梦清
 * 
 */
public abstract class StaticResourceResolver {
	
	private static final Logger LOGGER = Logger.getLogger(StaticResourceResolver.class);
	
	private static final Map<String, Set<Class<?>>> CLASSES_MAP = new HashMap<String, Set<Class<?>>>(); 
	
	/**
	 * 查找系统验证器类
	 * 
	 * @return Set
	 */
	public static Set<Class<Validator>> lookupValidatorClasses() {
		return lookupClasses(Validator.class, "com.yougou.api.validator.impl");
	}
	
	/**
	 * 查找系统拦截器类
	 * 
	 * @return Set
	 */
	public static Set<Class<Interceptor>> lookupInterceptorClasses() {
		return lookupClasses(Interceptor.class, "com.yougou.api.interceptor.impl");
	}
	
	/**
	 * 查找系统实现者类
	 * 
	 * @return Set
	 */
	public static Set<Class<Implementor>> lookupImplementorClasses() {
		return lookupClasses(Implementor.class, "com.yougou.api.adapter.impl");
	}
	
	/**
	 * 查找系统过滤器类
	 * 
	 * @return Set
	 */
	public static Set<Class<AbstractFilter>> lookupFilterClasses() {
		return lookupClasses(AbstractFilter.class, "com.yougou.api.web.filter");
	}
	
	/**
	 * 查找指定包名下的类
	 * 
	 * @param clazz
	 * @param packages
	 * @return T
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Set<Class<T>> lookupClasses(Class<T> clazz, String... packages) {
		if (ArrayUtils.isEmpty(packages)) {
			throw new IllegalStateException("Lookup " + clazz + " is not specified the package name.");
		}
		
		String hash = hash(clazz, packages);
		Set classes = CLASSES_MAP.get(hash);
		if (classes == null || classes.isEmpty()) {
			classes = new ClassesResolver<T>(clazz, packages).loadClasses();
			CLASSES_MAP.put(hash, classes);
		}
		return classes;
	}
	
	/**
	 * 哈希唯一的键
	 * 
	 * @param clazz
	 * @param packages
	 * @return String
	 */
	private static String hash(Class<?> clazz, String... packages) {
		StringBuilder hashBuilder = new StringBuilder('[');
		
		for (int i = packages.length - 1; i > 0; i--) {
			hashBuilder.append(packages[i]).append(',');
		}
		
		hashBuilder.append(packages[0]).append(',');
		hashBuilder.append(clazz.getName()).append(']');
		return hashBuilder.toString();
	}
	

	/**
	 * 类解析类
	 * 
	 * @author 杨梦清
	 *
	 */
	static class ClassesResolver<T> {
		
		static final String SUBPOINT = File.separator + "WEB-INF" + File.separator + "classes" + File.separator;
		
		/** 搜索类 **/
		private Class<T> clazz;
		/** 包名 **/
		private String[] packages;
		/** 类加载者 **/
		private ClassLoader classLoader;
		/** 搜索匹配列表 **/
		private Set<Class<T>> classes = new HashSet<Class<T>>();

		ClassesResolver(Class<T> clazz, String[] packages) {
			this.clazz = clazz;
			this.packages = packages;
			this.classLoader = Thread.currentThread().getContextClassLoader();
		}

		/**
		 * 加载类资源
		 * 
		 * @return Set
		 */
		public Set<Class<T>> loadClasses() {
			try {
				for (int i = packages.length - 1; i >= 0; i--) {
					// 将包路径转换为系统文件路径格式
					String pathname = packages[i].replace('.', File.separatorChar);
					if (!pathname.endsWith(File.separator)) {
						pathname += File.separator;
					}
					// 构建类匹配正则表达式
					String regex = "^(" + pathname.replace("\\", "\\\\") + ")(.)*(\\.class)$";
					// 开始加载类资源
					Enumeration<URL> resources = classLoader.getResources(pathname);
					while (resources.hasMoreElements()) {
						URL url = resources.nextElement();
						if ("jar".equalsIgnoreCase(url.getProtocol())) {
							deepLoadClasses(url, regex);
						} else if ("file".equalsIgnoreCase(url.getProtocol()) || "vfsfile".equalsIgnoreCase(url.getProtocol())) {
							deepLoadClasses(new File[] { new File(URLDecoder.decode(url.getFile(), "UTF-8")) }, regex);
						} else if ("vfszip".equalsIgnoreCase(url.getProtocol())) {
							Method vfsMethod = null;
							Object[] vfsMethodArgs = null;
							try {
								vfsMethod = classLoader.loadClass("org.jboss.virtual.VFS").getMethod("getRoot", URL.class);
								vfsMethodArgs = new Object[] { url };
							} catch (ClassNotFoundException ex) {
								LOGGER.debug("JBoss VFS packages for JBoss AS 6 not found; falling back to JBoss AS 5 packages");
								try {
									vfsMethod = classLoader.loadClass("org.jboss.vfs.VFS").getMethod("getChild", URI.class);
									vfsMethodArgs = new Object[] { url.toURI() };
								} catch (ClassNotFoundException e) {
									LOGGER.error("JBoss VFS packages (for both JBoss AS 5 and 6) were not found - JBoss VFS support disabled");
									throw new IllegalStateException("Cannot detect JBoss VFS packages", e);
								}
							}
							deepLoadClasses(new File[] { new VfsResource(vfsMethod.invoke(null, vfsMethodArgs)).getFile() }, regex);
						}
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			
			return classes;
		}

		/**
		 * 解析加载类
		 * 
		 * @param className
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		private void resolveClass(String className) throws Exception {
			Class<?> target = (Class<?>) classLoader.loadClass(className);
			int modifiers = target.getModifiers();
			if ((modifiers & Modifier.INTERFACE) == Modifier.INTERFACE) {
				LOGGER.debug("Ignore interface@" + className);
				return;
			}
			if ((modifiers & Modifier.ABSTRACT) == Modifier.ABSTRACT) {
				LOGGER.debug("Ignore abstract@" + className);
				return;
			}
			if ((modifiers & Modifier.PRIVATE) == Modifier.PRIVATE) {
				LOGGER.debug("Ignore private@" + className);
				return;
			}
			if ((modifiers & Modifier.PROTECTED) == Modifier.PROTECTED) {
				LOGGER.debug("Ignore protected@" + className);
				return;
			}
			if (clazz.isAssignableFrom(target)) {
				classes.add((Class<T>) target);
			}
		}
		
		/**
		 * 深度加载类
		 * 
		 * @param url
		 * @param regex
		 * @throws Exception
		 */
		private void deepLoadClasses(URL url, String regex) throws Exception {
			Enumeration<JarEntry> entries = ((JarURLConnection) url.openConnection()).getJarFile().entries();
			while (entries.hasMoreElements()) {
				String entryName = entries.nextElement().getName();
				if (entryName.matches(regex)) {
					resolveClass(entryName.replace(File.separatorChar, '.').substring(0, entryName.length() - 6));
				}
			}
		}

		/**
		 * 深度加载类
		 * 
		 * @param files
		 * @param regex
		 * @throws Exception
		 */
		private void deepLoadClasses(File[] files, final String regex) throws Exception {
			for (File file : files) {
				if (file.exists() && file.isDirectory()) {
					deepLoadClasses(file.listFiles(new FileFilter() {
						public boolean accept(File f) {
							return f.isDirectory() || f.getName().matches("^(.)*(\\.class)$");
						}
					}), regex);
				} else {
					String fileName = file.getPath();
					fileName = fileName.substring(fileName.lastIndexOf(SUBPOINT) + SUBPOINT.length());
					if (fileName.matches(regex)) {
						fileName = fileName.substring(0, fileName.length() - 6);
						resolveClass(fileName.replace(File.separatorChar, '.'));
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(lookupFilterClasses());
		System.out.println(lookupImplementorClasses());
		System.out.println(lookupInterceptorClasses());
		System.out.println(lookupValidatorClasses());
	}
}
