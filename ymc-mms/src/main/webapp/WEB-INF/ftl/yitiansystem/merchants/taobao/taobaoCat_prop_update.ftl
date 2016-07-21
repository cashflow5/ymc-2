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
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
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
	.prop-item{float: left;display:inline-block;height:20px;margin: 2px 5px 2px 10px}
	.prop-item .label-input{width:110px;height:18px;border:1px solid #cccccc;}
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
				  <span><a href="#" class="btn-onselc">修改淘宝分类属性明细</a></span>
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
		 	<form id="subform">
		 	<input type="hidden" name="cid" value="${vo.cid}">
		 	<table class="com_modi_table" style="border: 1px solid #e3e3e3;">
		 	<#if pVList??&&(pVList?keys)?size gt 0>
			 	<#list pVList?keys as pkey>
			 	<tr style="border-bottom: 1px dotted #cccccc;">
			 	<th style="width: 200px;border-right: 1px dotted #cccccc;background: none repeat scroll 0 0 #f6f6f6;">
		 			<span class="star"><#if mmList[pkey]>*</#if></span>${pkey}：</th>
		 			<td id="${pidMap[pkey]}">
			 			<#list pVList[pkey] as vItem>
			 				<label title="${vItem.vname }">${vItem.vname }
			 				<#if vItem.isArtificialInput=='1'>
			 					<a href="javascript:void(0)" class="del-prop-label" pid="${pidMap[pkey]}" vid="${vItem.vid?c}"><img src="${BasePath}/images/del-class.gif"></a>
			 				</#if>
			 				</label>
			 			</#list>
			 			<label  style="height:25px;width:70px;"><input type="button" pid="${pidMap[pkey]}" pname="${pkey}" value="+添加属性" class="add-prop-btn"></label>
		 			</td>
			 	</tr>
			 	</#list>
			 	<tr>
			 		<td style="text-align:center;padding:10px;" colspan="2"><input type="button" id="saveBtn" value="保存" style="font-size:16px"></td>
			 	</tr>
		 	<#else>
		 		<tr style="border-bottom: 1px dotted #cccccc;">
		 		<th>
		 		</th>
		 		<td>
		 			<span style="color:red;">暂无属性明细！</span>
		 		</td>
		 	</#if>
		 	</table>
		 	</form>
		</div>
              <div class="blank20"></div>
          </div>
</div>
<script type="text/javascript">
	function goBack(){
		location.href="${BasePath}/yitiansystem/taobao/goTaobaoCat.sc";
	}
	var prop_code_msg = "输入属性值编码";
	var prop_code_name = "输入属性值名称";
	$(function(){
		$(".add-prop-btn").click(function(){
			var pid = $(this).attr("pid");
			var pname = $(this).attr("pname");
			$(this).parent().before(new propItem($(this),pid,pname));
		});
		
		$(".del-prop").live("click",function(){
			$(this).parent().remove();
		});
		
		$("#saveBtn").click(function(){
			saveProp();
		});
		
		$(".del-prop-label").live("click",function(){
			var curObj = $(this);
			var vid = curObj.attr("vid");
			var pid = curObj.attr("pid");
			var load = ygdg.dialog({
				id:'addTaobaoCat',
				content:'正在删除<img src="${BasePath}/images/loading.gif"/>',
				lock:true,
				title:'提示'
		});
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				vid:vid,
				pid:pid,
				cid:"${vo.cid}"
			},
			url : "${BasePath}/yitiansystem/taobao/delPropValue.sc",
			success : function(data) {
				load.close();
				if(data.resultCode=="200"){
					curObj.parent().remove();
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
		});
	});
	
	function propFocus(obj,type){
		var msg = prop_code_msg;
		if(type==2){
			msg = prop_code_name;
		}
		obj = $(obj);
		if($.trim(obj.val())==msg){
				obj.val("");
		}
	}
	
	function propBlur(obj,type){
		var msg = prop_code_msg;
		if(type==2){
			msg = prop_code_name;
		}
		obj = $(obj);
		if($.trim(obj.val())==""){
				obj.val(msg);
		}
	}
	
	function propItem(curObj,pid,pname){
		var index = curObj.parent().parent().find(".prop-code").length+1;
		var item = $('<div class="prop-item"></div>');
		$('<input type="text" name="'+pid+'code" pname="'+pname+'" index="'+index+'" onfocus = "propFocus(this,1)" onblur="propBlur(this,1)" class="label-input prop-code" value="输入属性值编码"/>&nbsp;&nbsp;<input onfocus = "propFocus(this,2)" onblur="propBlur(this,2)" type="text" value="输入属性值名称" name="'+pid+'name" pname="'+pname+'" index="'+index+'" class="label-input prop-name" /><a href="javascript:void(0)" class="del-prop"><img src="${BasePath}/images/del-class.gif"></a>').appendTo(item);
		return item;
	}
	
	function saveProp(){
		
		var propCode ="";
		var propName = "";
		var msg = "";
		var allPropCodes = $(".prop-code")
		if(allPropCodes.length==0){
			ygdg.dialog.alert("请先添加属性值编码和属性值名称");
			return;
		}
		var reg = /[0-9]+$/;
		var trs = $(".com_modi_table tr");
		for(var j=0,_trlen =trs.length;j<_trlen;j++ ){
			var propCodes = trs.eq(j).find(".prop-code");
			var propNames =  trs.eq(j).find(".prop-name");
			var codeArray = [];
			var pname =  propCodes.eq(0).attr("pname");
			for(var i=0,_len=propCodes.length;i<_len;i++){
				propCode = $.trim(propCodes.eq(i).val());
				propName = $.trim(propNames.eq(i).val());
				if(propCode==""||propCode==prop_code_msg){
					msg = msg+"属性【"+pname+"】的第"+propNames.eq(i).attr("index")+"个属性值编码不能为空<br>";
				}
				if(!reg.test($.trim(propCode))){
					msg = msg+"属性【"+pname+"】的第"+propNames.eq(i).attr("index")+"个属性值编码必须是数字<br>";
				}
				if(propName==""||propCode==prop_code_msg){
					msg = msg+"属性【"+pname+"】的第"+propNames.eq(i).attr("index")+"个属性值名称不能为空<br>";
				}
				if(propCode!=""&&propCode!=prop_code_msg&&propName!=""&&propName!=prop_code_msg){
					codeArray.push(propCode);
				}
			}
			if(codeArray.length>0){
				var repeatCode =  getRepeat(codeArray);
				for(var i=0,_len=repeatCode.length;i<_len;i++){
					msg = msg+"属性【"+pname+"】的属性值编码【"+repeatCode[i]+"】重复<br>";
				}
			}
		}
		if(msg!=""){
			ygdg.dialog.alert(msg);
			return;
		}
		var load = ygdg.dialog({
				id:'addTaobaoCat',
				content:'正在保存<img src="${BasePath}/images/loading.gif"/>',
				lock:true,
				title:'提示'
		});
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:$('#subform').serialize(),
			url : "${BasePath}/yitiansystem/taobao/saveTaobaoPropValue.sc",
			success : function(data) {
				load.close();
				if(data.resultCode=="200"){
					var catPropValueList = data.catPropValueList;
					for(var i=0,_len=catPropValueList.length;i<_len;i++){
						var propValue = catPropValueList[i];
						$(".prop-item").remove();
						var label = $('<label title="'+propValue.vName+'">'+propValue.vName+'<a href="javascript:void(0)" pid="'+propValue.pid+'" vid="'+propValue.vid+'" class="del-prop-label"><img src="/mms/images/del-class.gif"></a></label>')
					    $("#"+propValue.pid).find(".add-prop-btn").parent().before(label);
					}
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	}
	
	function getRepeat(tempary) {
		var ary = [];
		for (var i = 0; i < tempary.length; i++) {
			ary.push(tempary[i]);
		}
		var res = [];
		ary.sort();
		for (var i = 0; i < ary.length;) {
			var count = 0;
			for (var j = i; j < ary.length; j++) {
				if (ary[i] == ary[j]) {
					count++;
				}
			}
			if (count > 1) {
				res.push(ary[i]);
			}
			i += count;
		};
		return res;
	}
</script>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 

