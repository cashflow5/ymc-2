<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/common.js"></script>
<title>优购商城--商家后台</title>
<style type="text/css">
	label{
		height: 20px;
	    margin: 2px 5px 2px 10px;
	    overflow: hidden;
	    text-align: left;
	    white-space: nowrap;
	    width: 150px;
	    float: left;
    	padding-right: 10px;
	}
</style>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:goBack();">
				<span class="btn_l"></span>
	        	<b class="ico_btn back"></b>
	        	<span class="btn_txt">返回</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">淘宝分类属性明细</a></span>
				</li>
			</ul>
		</div>
		 <div class="modify"> 
		 <br/>
		 <br/>
		 	<b>分类明细：</b><br/>
		 	<table class="com_modi_table">
		 	<tr><th style="width: 200px;">淘宝分类：</th><td>${vo.name }</td></tr>
		 	<tr><th>淘宝一级分类编码：</th><td>${vo.rootCatCode }</td></tr>
		 	<tr><th>淘宝二级分类编码：</th><td>${vo.secondCatCode }</td></tr>
		 	</table>
		 	<br/>
		 	<b>属性明细：</b><br/>
		 	<table class="com_modi_table" style="border: 1px solid #e3e3e3;">
		 	<#if pVList??&&(pVList?keys)?size gt 0>
			 	<#list pVList?keys as pkey>
			 	<tr style="border-bottom: 1px dotted #cccccc;">
			 	<th style="width: 200px;border-right: 1px dotted #cccccc;background: none repeat scroll 0 0 #f6f6f6;">
			 			<span class="star"><#if mmList[pkey]>*</#if></span>${pkey}：</th>
			 			<td>
			 			<#list pVList[pkey] as vItem>
			 				<label title="${vItem.vname }">${vItem.vname } </label>
			 			</#list>
			 			</td>
			 	</tr>
			 	</#list>
		 	<#else>
		 		<tr style="border-bottom: 1px dotted #cccccc;">
		 		<th>
		 		</th>
		 		<td>
		 			<span style="color:red;">暂无属性明细！</span>
		 		</td>
		 	</#if>
		 	</table>
		</div>
              <div class="blank20"></div>
          </div>
</div>
<script type="text/javascript">
	function goBack(){
		location.href="${BasePath}/yitiansystem/taobao/goTaobaoCat.sc";
	}
</script>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 

