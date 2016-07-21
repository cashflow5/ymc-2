<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--优购平台</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
</head>
<body>

<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API缓存管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="submitForm" name="submitForm" action="" method="post">
            		<input type="button" id="updateApiCache"  value="刷新API基本配置缓存" />
            		<br/><br/>
            		<input type="button" id="updateAppKeyApiAuthCache"  value="刷新API授权缓存" />
            		<span>缓存数量：<lable style="color:red" id="apiCount">${ apiCacheSize!''}</lable></span>
            		<span>数据库记录数量：<lable style="color:red" id="apiDbCount">${ apiDbSize!''}</lable></span>
            	
            		<br/><br/>
            		<input type="button" id="updateAppKeyCache"  value="刷新appKey验证缓存" />
            		<span>缓存数量：<lable style="color:red" id="appkeyCount">${ appkeyCacheSize!''}</lable></span>
            		<span>数据库记录数量：<lable style="color:red" id=appkeyDbSize>${ appkeyDbSize!''}</lable></span>
            		
            		<!--Add by LQ.-->
            		<br/><br/>
            		<input type="button" id="updateAppKeyApiRelevanceCache"  value="刷新API统计中名称与key的对应关系缓存" />
            		<span>缓存数量：<lable style="color:red" id="apiRelevanceCount">${ apiRelevanceSize!''}</lable></span>
            		
            		<!-- add by li.n -->
            		<br/><br/>
            		<br/><br/>
            		<br/><br/>
            		<input type="text" id="keyVal" style="width: 300px;" placeholder="根据key删除缓存！" />
            		<span><input type="button" id="deleteRelevanceCache" value="删除缓存"/></span>
              	</form>
              	<br/>
              	<br/>
              	<p style="color:red" >说明：</p>
              	<p ><span style="color:blue" >刷新API基本配置缓存：</span>用于刷新API配置缓存，一般当API基本信息、API输入参数、API输出参数、API验证器链、API拦截器链等配置改变时，手动刷新。（本地缓存+分布式通知更新缓存）</p>
              	<p ><span style="color:blue" >刷新API授权缓存：</span>用于刷新API权限缓存，一般API禁用/启用时、授权API,手动刷新。（redis集中式缓存,,redis结构：Set,值：apiId#appkey,如 8a8094093dfd6bd6013dfd7028a60001#5ca355de_13d9039a673__79f7）</p>
              	<p ><span style="color:blue" >刷新appKey验证缓存：</span>用于刷新appKey（即API密匙管理）配置缓存，一般当appKey开启/禁用、 绑定用户 时，手动刷新。（redis集中式缓存,,redis结构：Hash,键：appkey,值：secret#metadata 如 978be1c733ca6ae775aea13c49a8b25a#8a809ec8394c0cfd01394c172ea90001）</p>
              	<p ><span style="color:blue" >刷新API统计中名称与key的对应关系缓存：</span>用于刷新API统计中名称与key的对应关系缓存，一般当appKey开启/禁用、 绑定用户 时，手动刷新。（redis集中式缓存,,redis结构：Hash,键：appkey,值：secret#metadata 如 978be1c733ca6ae775aea13c49a8b25a#8a809ec8394c0cfd01394c172ea90001）</p>            
              	<br/>
              	<p style="color:red" >常用场景：</p>
              	<p >1.修改API基本配置：请使用“刷新API基本配置缓存”。</p>
              	<p >2.新建appkey并授权某些API：请使用“刷新API授权缓存”+“刷新appKey验证缓存”。</p>
              	<p >3.API禁用 或 授权API：请使用“刷新API授权缓存”</p>
              	<p >4.禁用appKey：请使用“刷新appKey验证缓存”。</p>
              	<p >5.根据key删除缓存：请慎重使用！</p>
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
<script type="text/javascript">
	$("#updateApiCache").click(function(){
		
		$.ajax({
			   type: "POST",
			   url: "${BasePath}/openapimgt/api/cahe/updateApiCache.sc",
			   beforeSend:function(xhr){
				   $("#updateApiCache").attr("disabled","true");
			   },
			   success: function(msg){
			     alert("刷新成功");
			   },
			   complete:function(){
				   $("#updateApiCache").removeAttr("disabled");
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert("刷新失败，错误："+errorThrown);
			   }
			});
		
	});
	$("#updateAppKeyApiAuthCache").click(function(){
		
		$.ajax({
			   type: "POST",
			   url: "${BasePath}/openapimgt/api/cahe/updateAppKeyApiAuthCache.sc",
			   success: function(size){
			     alert("刷新成功");
			     $("#apiCount").text(size);
			   },
			   beforeSend:function(xhr){
				   $("#updateAppKeyApiAuthCache").attr("disabled","true");
			   },
			   complete:function(){
				   $("#updateAppKeyApiAuthCache").removeAttr("disabled");
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert("刷新失败，错误："+errorThrown);
			   }
			});
		
	});

	$("#deleteRelevanceCache").click(function(){
		var keyVal = $.trim($("#keyVal").val());
		if(keyVal!=''){
			$.ajax({
				   type: "POST",
				   url: "${BasePath}/yitiansystem/merchants/businessorder/deleteCacheByKey.sc",
				   data:'key='+keyVal,
				   success: function(size){
				     alert("删除缓存数量"+size+"个成功");
				   },
				   beforeSend:function(xhr){
					   $("#deleteRelevanceCache").attr("disabled","true");
				   },
				   complete:function(){
					   $("#deleteRelevanceCache").removeAttr("disabled");
				   },
				   error:function(XMLHttpRequest, textStatus, errorThrown){
					   alert("删除缓存失败，错误："+errorThrown);
				   }
				});
		}else{
			alert("key为空！");
		}
	});
	
	$("#updateAppKeyCache").click(function(){
		
		$.ajax({
			   type: "POST",
			   url: "${BasePath}/openapimgt/api/cahe/updateAppKeyCache.sc",
			   success: function(size){
			     alert("刷新成功");
			     $("#appkeyCount").text(size);
			   },
			   beforeSend:function(xhr){
				   $("#updateAppKeyCache").attr("disabled","true");
			   },
			   complete:function(){
				   $("#updateAppKeyCache").removeAttr("disabled");
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert("刷新失败，错误："+errorThrown);
			   }
			});
		
	});
	
	
	$("#updateAppKeyApiRelevanceCache").click(function(){
		
		$.ajax({
			   type: "POST",
			   url: "${BasePath}/apiJob/createAppKeyUserCache.sc",
			   success: function(size){
			     alert("刷新成功");
			     $("#apiRelevanceCount").text(size);
			   },
			   beforeSend:function(xhr){
				   $("#updateAppKeyApiRelevanceCache").attr("disabled","true");
			   },
			   complete:function(){
				   $("#updateAppKeyApiRelevanceCache").removeAttr("disabled");
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert("刷新失败，错误："+errorThrown);
			   }
			});
		
	});
	
</script>
</html>
