<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>优购商城--商家后台</title>

</head>

<body>


<div class="container"> 
    <!--工具栏start--> 
    <div class="toolbar">
			<div class="t-content"> <!--操作按钮start-->
				<div class="btn" onclick="toadd();">
					<span class="btn_l"></span>
					<b class="ico_btn add"></b>
					<span class="btn_txt">添加小组</span>
					<span class="btn_r"></span> 
				</div>
				<div class="btn" onclick="clearAll();">
					<span class="btn_l"></span>
					<b class="ico_btn add"></b>
					<span class="btn_txt">批量删除</span>
					<span class="btn_r"></span> 
				</div>
			</div>
		</div>
    <!--工具栏end-->
    <div class="list_content"> 
        <!--当前位置start--> 
        <div class="top clearfix">
			    <ul class="tab">
			        <li class="curr"><span>数据处理列表</span></li>
			       
			    </ul>
			  </div>
        <!--当前位置end--> 




 <div class="modify">
    <form id="form1" name="form1" action="addAdvert.sc" method="post">
				<table class="list_table" cellspacing="0" cellpadding="0" border="0">
					  <tr>
					  <tbody>
					   <th><input type="checkbox" name="ch" id="ch" onclick="checkAll(this);" ></th>
					   <th style="width:60px;">小组名称</th>
					   <th style="width:70px;">小组说明</th>
					   <th>小组成员</th>
					   <th style="width:100px;">操作</th>
				 	 </tr>
				 	  <tbody>
				 	  <#if userPermissionGroupList??>
				 	    <#list userPermissionGroupList as item>
					     <tr>
							   <td><input type="checkbox" name="boxs" id="boxs" value="${item.id!''}"></td>
							     <td>${item.groupName!''}</td>
							   <td>${item.groupRemark!''}</td>
							    <td>
							    <#if item.systemmgtUsers??>
							 	    <#list item.systemmgtUsers as users>
							 	       ${users.loginName};
							 	    </#list>
							   </#if>
							   </td>
							    <td>
							       <a href="javascript:void(0);" onclick="updates('${item.id!''}');">编辑</a>&nbsp;
							       <a href="javascript:void(0);" onclick="clearSupper('${item.id!""}');">删除</a>
							       <a href="javascript:void(0);" onclick="operateLog('${item.id!""}');">操作日志</a>
							       </td>
						  </tr>
						  </#list>
					</#if>
					 </table>
          </form>
          </div>
   </div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript">
	//查看操作日志信息
	function operateLog(id){
		openwindow("${BasePath}/yitiansystem/systemmgmt/permission/queryUserPermissionGroupLog.sc?id="+id,'','',"查看操作日志");
	}
	//删除单个信息
	function clearSupper(id){
	   if(id!=""){
	     if(confirm("是否真的删除!")){
		   	 $.ajax({
		  			type : "POST",
		  			url : "${BasePath}/yitiansystem/systemmgmt/permission/delete_UserPermissionGroup.sc?id="+id,
		  			async : false,
		  			success : function(dt) {
		  		       if("success"==dt){
		  		        alert("操作成功！");
		  		    	window.location.reload();
					  }else{
						alert("操作失败！");
		  			  }
		  		   }
		  		});
          }
	  }
	}
	
	//批量删除信息<input type="button" class="btn-add-normal-4ft" value="修改" onclick="checkuser();">
	function clearAll(){
	   var checkids = $(":checkbox[name=boxs]");
		var ids = "";
		var flag = 0;
		for(var i=0; i<checkids.length; i++) {
			if(checkids[i].checked) {
				var id = checkids[i].value;
				ids += id + ",";
				flag++;
			}
		}
		if(flag==0){
		   alert("请选择要删除的数据!");
		   return;
		}
		
		if(confirm("是否真的删除!")){
		  $.ajax({
		  			type : "POST",
		  			url : "${BasePath}/yitiansystem/systemmgmt/permission/deleteBatch_UserPermissionGroup.sc?id="+ids,
		  			async : false,
		  			success : function(dt) {
		  		       if("success"==dt){
		  		        alert("操作成功！");
		  		    	window.location.reload();
					  }else{
						alert("操作失败！");
		  			  }
		  		   }
		  		});
	   }
	}
	//编辑
	function updates(id){
	   openwindow("${BasePath}/yitiansystem/systemmgmt/permission/toEditPermission.sc?userPermissionGroupId="+id,'','',"编辑用户");
	}
	
	//全选
	function checkAll(str){
	   var checkids = $(":checkbox[name=boxs]");
		for(var i=0; i<checkids.length; i++) {
			if(str.checked) {
				checkids[i].checked=true;
			}else{
			  checkids[i].checked=false;
			}
		}
	}
	function checkuser(){
	  openwindow('${BasePath}/yitiansystem/systemmgmt/permission/to_systementUser.sc','','','选择用户');
	}
	//跳转到添加小组页面
	function toadd(){
	  openwindow('${BasePath}/yitiansystem/systemmgmt/permission/toAdd.sc','','','添加用户');
	}
</script>
</body>
</html>


