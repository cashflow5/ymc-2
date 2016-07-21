package com.belle.infrastructure.web.servlet;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
/**
 * <p>Title:过滤器 </p>
 * @version 1.0
 */
public class EncodingFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	
	private FilterConfig filterConfig;
    protected String encoding = null;
    protected boolean ignore = true;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("true")) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("yes")) {
            this.ignore = true;
        } else {
            this.ignore = false;
        }
    }
    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        try {
            if (ignore || (request.getCharacterEncoding() == null)) {
                String encoding = selectEncoding(request);
                if (encoding != null) {
                    request.setCharacterEncoding(encoding);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ServletException sx) {
            filterConfig.getServletContext().log(sx.getMessage());
        } catch (IOException iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        } catch (Exception ex) {
            filterConfig.getServletContext().log(ex.getMessage());
        }
    }
    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }
}

