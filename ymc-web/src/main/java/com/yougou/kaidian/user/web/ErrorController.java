package com.yougou.kaidian.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 针对商家可能出现的403/404/500处理
 * @author li.n1
 *
 */

@Controller
@RequestMapping("merchants/error")
public class ErrorController {
	@RequestMapping("/403")
    public String return_403() {
		return "manage_unless/error/forbidden";
	}
	
	@RequestMapping("/404")
    public String return_404() {
		return "manage_unless/error/notfound";
	}
	
	@RequestMapping("/500")
    public String return_500() {
		return "manage_unless/error/error";
	}
}
