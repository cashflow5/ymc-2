package com.yougou.kaidian.framework.filter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;

import com.yougou.kaidian.framework.util.SessionUtil;

public class LoginFilter implements Filter {

	private String styleVersion="";
	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	//店铺请求不一样，后面带店铺uuid.sc，特别判断，否则没权限访问
	static String[] strArr = new String[]{"/fss/store/pageDesign/","/fss/store/pagePreview/"};
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//样式版本号
	    String path=filterConfig.getServletContext().getRealPath("/");
	    Date packageDate=null;
	    try {
	    	File file=new File(path+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"applicationContext.xml");
	    	packageDate=new Date((file).lastModified());
		} catch (Exception e) {
			packageDate=new Date();
		}
	    this.styleVersion=(new SimpleDateFormat("yyyyMMddHHmm")).format(packageDate);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		request.getSession().setAttribute("style_v", styleVersion);
		String url = StringUtils.trimToEmpty(request.getServletPath());
		session.setAttribute("BasePath", "");
		
		try {
			/** 严禁随意调整判断条件先后次顺 **/
			if (url.indexOf("to_login.sc") != -1) {// 跳转到登录页面
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchantsLogin.sc") != -1) {// 登录验证
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchantsCode.sc") != -1) {// 验证码验证
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("exitsLoginAccount.sc") != -1) {// 验证用户名
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("httptaskjob") != -1) {// 触发删除图片
				chain.doFilter(servletRequest, servletResponse);
			}/* else if (url.indexOf("httptaskjob/deltemppicfile") != -1) {// 触发删除图片
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("httptaskjob/imgJmsCount") != -1) {// 统计异步切图
				chain.doFilter(servletRequest, servletResponse);
			} */
			else if (url.matches("/commodity/(pics/)?(upload|import|uploaddescribe)\\.sc")) {// 默认允许商品图片上传，商品资料批量导入（通过SWFUpload上传），因Flash请求不能读取商家会话信息默认放行
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.matches("/picture/(upload|import|uploaddescribe)\\.sc")) {// 默认允许商品图片上传，商品资料批量导入（通过SWFUpload上传），因Flash请求不能读取商家会话信息默认放行
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchants/login/findpassword.sc") != -1) {// 找回密码
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchants/login/checkpassport.sc") != -1) {// 找回密码
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchants/login/setpassword.sc") != -1) {// 找回密码
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchants/login/resetpassword.sc") != -1) {// 找回密码
				chain.doFilter(servletRequest, servletResponse);
			} else if (url.indexOf("merchants/login/activatemail.sc") != -1) {// 激活邮箱
				chain.doFilter(servletRequest, servletResponse);
			} else if(url.indexOf("taobao/toTaobaoGetCatPropData.sc")!=-1){	//供mms调用获取淘宝分类、属性、属性值
				chain.doFilter(servletRequest, servletResponse);
			} else if (SessionUtil.getUnionUser(request) == null) {// 判断商家是否登录
				response.sendRedirect("/merchants/login/to_login.sc");
			} else if (url.indexOf("to_index.sc") != -1 || url.indexOf("to_Back.sc") != -1) {// 商家登录成功后，默认允许进入首页与退出登录
				chain.doFilter(servletRequest, servletResponse);
			} else {// 权限控制
				Set<String> authrityMap = (Set<String>) session.getAttribute("authorityList");
				Set<String> allResource = (Set<String>) session.getAttribute("allRes");
				Map<String, Object> mapMerchantUser = (Map<String, Object>)session.getAttribute("merchantUsers");
				for(String s : strArr){
					if(url.indexOf(s)!=-1){
						url = s;
					}
				}
				//权限资源包含url或者优购管理员或者优购业务管理员放行
				//如果限制优购管理员权限，把选择商品的权限给优购管理员即可
				if(allResource.contains(url)){
					if(("1".equals(mapMerchantUser.get("is_yougou_admin").toString())) || authrityMap.contains(url)){
						chain.doFilter(servletRequest, servletResponse);
					}else{
						//对于业务管理员，登录商家中心选择商家url放行
						if("2".equals(mapMerchantUser.get("is_yougou_admin").toString())){
							if(url.indexOf("merchants/login/toSetMerchant.sc")!=-1 || 
									url.indexOf("merchants/login/toSetPresentMerchant.sc")!=-1){
								chain.doFilter(servletRequest, servletResponse);
							}else{
								//无权限访问页面
								response.sendRedirect("/merchants/error/403.sc");
							}
						}else{
							//无权限访问页面
							response.sendRedirect("/merchants/error/403.sc");
						}
					}
				}else{
					chain.doFilter(servletRequest, servletResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof BindException){
				logger.error("spring mvc controller参数转换异常.");
			    Enumeration<String> enumerationHeader = request.getHeaderNames();
			    String headerStr="\n";
			    while(enumerationHeader.hasMoreElements()) {
			        String key = enumerationHeader.nextElement();
			        headerStr=headerStr+key+": "+request.getHeader(key)+"\r\n";
			    }
				logger.error("Request Header:{}",headerStr);
				Enumeration<String> enumerationParameter = request.getParameterNames();
				String parameterStr="\n";
			    while(enumerationParameter.hasMoreElements()) {
			        String key = enumerationParameter.nextElement();
			        parameterStr=parameterStr+key+": "+request.getParameter(key)+"\r\n";
			    }
			    logger.error("Request Parameter:{}",parameterStr);
			}

		}
	}

	@Override
	public void destroy() {

	}
}
