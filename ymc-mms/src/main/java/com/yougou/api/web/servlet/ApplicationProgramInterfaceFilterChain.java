package com.yougou.api.web.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.mongodb.MongoException;

/**
 * API 过滤器链
 * 
 * @author yang.mq
 *
 */
public class ApplicationProgramInterfaceFilterChain implements FilterChain {

	private static final Logger LOGGER = Logger.getLogger(ApplicationProgramInterfaceFilterChain.class);

	private static final boolean IGNORE_MONGO_EXCEPTION = true;
	
	private static final String ALREADY_FILTERED_SUFFIX = ".FILTERED";
	
	private Filter filterExporter;
	
	private Iterator<Filter> filterParticipators;
	
	private FilterConfig filterConfig;
	
	public ApplicationProgramInterfaceFilterChain(Filter filterExporter, Collection<Filter> filterParticipators, FilterConfig filterConfig) {
		this.filterExporter = filterExporter;
		this.filterParticipators = filterParticipators.iterator();
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		Filter filter = filterParticipators.hasNext() ? filterParticipators.next() : filterExporter;
		
		String filterName = filter.getClass().getName();
		String alreadyFilteredAttributeName = filterName + ALREADY_FILTERED_SUFFIX;
		
		if (request.getAttribute(alreadyFilteredAttributeName) != null) {
			throw new ServletException("Filter '" + filterName + "' already executed.");
		} else {
			LOGGER.info("Proceeding invoking this filter '" + filterName + "'.");
			
			// 标记当前过滤器
			request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);
			
			try {
				filter.init(filterConfig);
				filter.doFilter(request, response, this);
				filter.destroy();
			} catch (MongoException ex) {
				if (filter == filterExporter) {
					throw new ServletException(ex);
				} else if (!IGNORE_MONGO_EXCEPTION) {
					throw ex;
				}
				
				LOGGER.error("Proceeding invoking next filter because 'IgnoreMongoException' option is enabled for the current environment.", ex);
				this.doFilter(request, response);
			} finally {
				// 清除当前过滤器标记
				request.removeAttribute(alreadyFilteredAttributeName);
			}
		}
	}
}
