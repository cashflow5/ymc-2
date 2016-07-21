<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,栏目管理" />
<meta name="Description" content=" , ,B网络营销系统-栏目管理" />
<title>B网络营销系统-栏目管理-优购网</title>
<#include "../yitiansystem-include.ftl">
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/edit_integral_setting.js"></script>
</head>

<body>
<div class="contentMain">
<div class="ytback-tt-1 ytback-tt">
	<span>您当前的位置：</span>系统管理  &gt;  <a href="#">积分设置 </a> &gt; 编辑积分设置</div>

  	<div class="content"> 
	    <div class="mb-btn-fd-bd">
	      <div class="mb-btn-fd relative">
            <span class="btn-extrange absolute">
                <ul class="onselect">
                    <li class="pl-btn-lfbg"></li>
                    <li class="pl-btn-ctbg"><a  class="btn-onselc">编辑积分设置</a></li>
                    <li class="pl-btn-rtbg"></li>
                </ul>
            </span>
	      </div>
	    </div>
		<input id="basePath" value="${BasePath}" type="hidden" name="basePath"/>
	    <div class="blank15"></div>     	
		<form action="u_updateIntegralSetting.sc"  method="POST" id="integralForm" name="integralForm">
			<input type="hidden" name="id" id="id" value="${systemmgtIntegral.id!''}" />
		<script>document.write("<input type='hidden' name='parentSourcePage' value='"+getBackUrl()+"'/>");</script>
		    <ul class="ytweb-form">
		    	<li><label for="t1">序号：</label><input readonly value="${systemmgtIntegral.integralNo!''}" name="integralNo" type="text" id="integralNo" size="25" /><span id="integralNoMsg"></span></li>
		        <li><label for="t2">规则名称：</label>
		        	<input type="hidden" id="hidNames" value="${systemmgtIntegral.integralName!''}" />
		        	<input readonly value="${systemmgtIntegral.integralName!''}" name="integralName" type="text" id="integralName" size="25" /><span id="integralNameMsg"></span></li>
		        <li><label for="t3">积分：</label><input maxlength="8" value="${systemmgtIntegral.integral?string(0)}" name="integral" maxlenth="8" type="text" id="integral" size="25" /><span id="integralMsg"></span></li>
		        <li><label for="t4">描述：</label>
		        	<textarea name="remark" id="remark" rows="4" cols="30" onpropertychange="checkLength(this,499);" oninput="checkLength(this,499);">${systemmgtIntegral.remark!''}</textarea>
		        </li>
		    </ul>
			<div class="ytweb-form-btn">
		        <label></label><input type="submit" class="yt-form-btn-add" value="保存" />  
		        <input type="button" class="yt-form-btn-back" value="返回" onclick="backToList();"/>
	        </div>
        </form>
  	</div>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
function checkLength(obj,maxlength){
    if(obj.value.length > maxlength){
        obj.value = obj.value.substring(0,maxlength);
    }
}
</script>