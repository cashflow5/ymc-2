<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购开放平台 测试版</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
</head>
<body>

<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:location.href='${BasePath}/apidoc/list_category.sc'">
				<span class="btn_l"></span><b class="ico_btn change"></b><span class="btn_txt">API分类列表</span><span class="btn_r"></span></div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API分类列表</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<#if categories ??>
				<#list categories as categorie>
					<h3>
						<a href="${BasePath}/apidoc/list_category_api.sc?categoryCode=${categorie.categoryCode}" style="color: blue; text-decoration: underline;">${categorie.categoryName}</a>
						<span>${categorie.categoryDescription}</span>
					</h3>
				</#list>
			<#else>
				<span>暂无数据</span>
			</#if>
			<!--列表end-->
			<div class="bottom clearfix">
			<br /><br />
				<pre>
<h3 style="color: #ff0000;">签名说明(调用API时需要对请求参数进行签名验证，优购服务器也会对该请求参数进行验证是否合法的。)</h3>
<b>算法</b>
	根据参数名称将你的所有请求参数按照字母先后顺序排序: key + value .... key + value，对除签名和图片外的所有请求参数按 key 做的升序排列, value 无需编码。
	
	例如：将 param1=3, param3=2, param2=3 排序为 param1=3, param2=3, param3=2，参数名和参数值链接后，得到拼装字符串param13param23param33
	
	系统同时支持MD5和SHA-1两种加密方式
		MD5：将 app_secret 同时拼接到参数字符串头部进行 MD5 加密后，格式是：byte_to_hex (md5(secretkey1value1key2value2...))。
		SHA-1：将 app_secret 同时拼接到参数字符串头部进行 SHA-1 加密后，格式是：byte_to_hex (sha-1(secretkey1value1key2value2...))。
	
	注：byte_to_hex 为自定义方法，加密结果是32位的字符串。图片参数不用加入签名中。向优购服务器发送 get 请求时如参数值包含中文需使用 encodeURIComponent 对参数值进行编码。 

<b>实例</b>
	调用API：yougou.order.get，使用系统默认 MD5 为例,因为各语言语法不一致，以下实例只体现逻辑,使用app_key=SP001 app_secret=123456
	
	输入参数为：
	      method=yougou.order.get
	      timestamp=2012-05-15 12:33:23
	      format=xml
	      app_key=SP001
	      app_version=1.0
	      sign_method=md5
	      order_sub_no=ORDER001
	      
	按照参数名称升序排列：
	      app_key=SP001
	      app_version=1.0
	      format=xml
	      method=yougou.order.get
	      order_sub_no=ORDER001
	      sign_method=md5
	      timestamp=2012-05-15 12:33:23
	      
	拼装字符串：
		连接参数名与参数值，并在头部加上 app_secret 值
		123456app_keySP001app_version1.0formatxmlmethodyougou.order.getorder_sub_noORDER001sign_methodmd5timestamp2012-05-15 12:33:23
		
	生成签名：
		32位 MD5 值 -> 1cf7372a8ceb574400538ebbd183ea5d
	
	请求URL：
		http://116.204.65.154:8086/api.sc?sign=1cf7372a8ceb574400538ebbd183ea5d&app_key＝SP001...
				</pre>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
</body>
</html>
