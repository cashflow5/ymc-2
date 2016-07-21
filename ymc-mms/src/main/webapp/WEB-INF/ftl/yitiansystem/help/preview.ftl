<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购网上鞋城-帮助中心</title>
<link type="text/css" href="${BasePath}/css/base_help.css" rel="stylesheet" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
</head>

<body class="index">
	<div class="main_container">
		<div class="curr_site">
			当前位置：首页 &gt; <a href="javascript:void(0);">帮助中心</a> &gt; <#if menu??>${menu.menuName}<#else>${menuName}</#if>
		</div>
		<div id="helpNav" class="help_nav">
			<#if firstMap??>
				<#list firstMap?keys as key>
					<dl>
						<dt>${firstMap[key]}</dt>
						<#if menuMap??>
							<#list menuMap[key]?keys as k>
								<dd>
									<a id="${k}" href="javascript:void(0);" class="cblue">${menuMap[key][k]}</a>
								</dd>
							</#list>
						</#if>
					</dl>
				</#list>
			</#if>
			<#if menuName??>
				<dl>
					<dt>新增加菜单</dt>
					<dd class="curr">${menuName}</dd>
				</dl>
			</#if>
		</div>
		<div class="normal_box help fl">
			<div class="title site">
		      <a><#if menu??>${menu.menuName}<#else>${menuName}</#if></a>
		    </div>
		    <div class="help_con">
		      ${helpMenuContent!''}
		    </div>
		</div>
		<div class="footer">
			<div class="footer w">Copyright &copy 2011-2012 Yougou Technology Co., Ltd. </div>
		</div>
	</div>
	<script type="text/javascript">
		$(function($) {
			<#if menu??>
			$('#${menu.id}').parent().attr('class', 'curr');
			$('#${menu.id}').parent().html('${menu.menuName}');
			</#if>
		});
	</script>
</body>
</html>
