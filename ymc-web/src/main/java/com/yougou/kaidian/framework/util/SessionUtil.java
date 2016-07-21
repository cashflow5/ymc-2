package com.yougou.kaidian.framework.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yougou.kaidian.framework.core.session.redis.HttpSessionSidRedisWrapper;


/**
 * 获取保存在Session的所有信息
 *
 * @author wang.M
 */
public final class SessionUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SessionUtil.class);

	/**
	 * 不建议使用该方法从session获取商家编码，因为在拦截器中已经将MerchantCode、merchantName和loginName存入YmcThreadLocalHolder中，
     * 可以直接使用YmcThreadLocalHolder拿到MerchantCode、merchantName和loginName。
     * 
	 * 获取保存session的用户实体信息，登录用户示例数据如下：<br/><br/>
	 * 
	 * supplier_code = SP20120412312125<br/>
	 * is_administrator = 1<br/>
	 * virtual_warehouse_code = 01121211999768<br/>
	 * inventory_code = 01121211999768<br/>
	 * supplierId = 8a8a8a1736a28db90136a2b754920001<br/>
	 * status = 1<br/>
	 * is_yougou_admin = 0<br/>
	 * is_input_yougou_warehouse = 0<br/>
	 * virtual_warehouse_name = 测试商家<br/>
	 * warehouseId = 8a809ec83b88efb4013b8976b0580019<br/>
	 * merchant_code = SP20120412312125<br/>
	 * is_valid = 1<br/>
	 * id = 8a809ec83873b080013873bbc799006d<br/>
	 * login_name = wu.w<br/>
	 * delete_flag = 1<br/>
	 * supplierFlag = 1<br/>
	 * warehouse_code = 01121211999768<br/>
	 * supplier = 测试商家（非入优发）<br/>
	 */
    @SuppressWarnings("unchecked")
	public static Map<String,Object> getUnionUser(HttpServletRequest request) {
    	Map<String,Object> unionUser = (Map<String,Object>) request.getSession().getAttribute("merchantUsers");
        if (MapUtils.isNotEmpty(unionUser)) {
            return unionUser;
        } else {
        	try {
				HttpSessionSidRedisWrapper httpSessionSidRedisWrapper = ((HttpSessionSidRedisWrapper)(request.getSession()));
				logger.warn("从session中获取不到merchantUsers，sid:" + httpSessionSidRedisWrapper.getSid());
			} catch (Exception e) {
				e.printStackTrace();
			}
            return null;
        }
    }

    /**
     * 设置保存在session中的信息
     */
    public static void setSaveSession(String keys, Object value, HttpServletRequest request) {
        request.getSession().setAttribute(keys, value);
    }
    
    /**
     * 移除session中的信息
     */
    public static void removeSession(HttpServletRequest request) {
        request.getSession().removeAttribute("merchantUsers");
        request.getSession().removeAttribute("isAuthentication");
    }

    /**
     * 获取输出信息
     *
     * @throws IOException
     * @Param obj 要输出到界面的信息
     */
    public static void getOutPut(String obj, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print(obj);
    }
    
    // 判断浏览器类型
    public static Integer getBrowingType(HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            return 1;
        } else if (null != agent && -1 != agent.indexOf("Firefox")) {
            return 2;
        } else if (null != agent && -1 != agent.indexOf("Safari")) {
            return 3;
        } else {
            return 4;
        }
    }

    // 获取来源头信息，获取域名
    public static String getDomain(HttpServletRequest request) {
        String domain = null;

        String domainreferer = request.getHeader("referer"); // request.getRequestURL().toString(); //获取头信息（原请求的URL)
        if (domainreferer != null) {
            String replaceReferer = domainreferer.replace("http://", "");
            if (domainreferer != null && replaceReferer.indexOf("/") > 0) {
                domain = replaceReferer.substring(0, replaceReferer.indexOf("/"));// 获取原URL域名
            } else {
                domain = replaceReferer.substring(0, replaceReferer.length());
            }

        }
        return domain;
    }

  
    /**
     * 获取服务器路径
     *
     * @param request
     * @return
     */
    public static String getServiceName(HttpServletRequest request) {
        String path = request.getContextPath();
        String servicePath = request.getServerName() + ":" + String.valueOf(request.getServerPort());
        return "http://" + servicePath + path + "/";
    }

    /**
     * 封装转码方法
     *
     * @throws UnsupportedEncodingException
     */
    public static String getEncoding(String EncondingName) throws UnsupportedEncodingException {
        return URLEncoder.encode(URLEncoder.encode(EncondingName, "UTF-8"), "UTF-8");
    }

    /**
     * 不建议使用该方法从session获取商家编码，因为在拦截器中已经将MerchantCode和loginName存入YmcThreadLocalHolder中，
     * 可以直接使用YmcThreadLocalHolder拿到MerchantCode和loginName。
     * 
     * @param request
     * @return eg:SP20120412312125
     */
    public static String getMerchantCodeFromSession(HttpServletRequest request) {
    	Map<String, Object> unionUser = getUnionUser(request);
    	if (MapUtils.isEmpty(unionUser)) 
    		return null;
    	return MapUtils.getString(unionUser, "supplier_code");
    }
    
    /**
     * 从session获取关联仓库编码
     * 
     * @param request
     * @return
     */
    public static String getWarehouseCodeFromSession(HttpServletRequest request) {
    	Map<String, Object> unionUser = getUnionUser(request);
    	if (MapUtils.isEmpty(unionUser)) 
    		return null;
    	
    	Integer isInputYougouWarehouse = MapUtils.getInteger(unionUser, "is_input_yougou_warehouse", 1);
    	if (isInputYougouWarehouse == 0 || isInputYougouWarehouse == 2) //不入优购仓,获取虚拟仓编码
    		return MapUtils.getString(unionUser, "warehouse_code");
    	else 
    		return null;
    }
    
	/**
	 * <p>查询Session属性</p>
	 * 
	 * @param request
	 * @param name 属性名称  <code>eg. supplier_code</code>
	 * @return 属性值 <code>eg. [SP20120412312125]</code>
	 */
	public static String getSessionProperty(HttpServletRequest request, String propertyName) {
		if (StringUtils.isBlank(propertyName))
			return null;
		
		Map<String, Object> unionUser = getUnionUser(request);
		if (MapUtils.isEmpty(unionUser)) 
    		return null;

		return MapUtils.getString(unionUser, propertyName);
	}
}
