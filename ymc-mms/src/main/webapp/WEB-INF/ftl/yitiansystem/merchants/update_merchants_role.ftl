<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<title>优购商城--商家后台</title>
<style type="text/css">
div.content_wrap {
    width: 550px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	var setting = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			showIcon : false,
			expandSpeed : "fast"
		}
	};
	var zNodes = [];
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			var c = false;
			if ('${item.isChecked!''}' == 1) {
				c = true;
			}
			var o = false;
			if ('${item.isOpen!''}' == 1) {
				o = true;
			}
			if ('${item.id!''}' == 0) {
				o = true;
			}
			zNodes[zNodes.length] = {
				id: '${item.id!""}', 
				pId: '${item.parent_id!''}', 
				name: '${item.authrity_name!''}', 
				checked: c,
				open:o
			};
		</#list>
	</#if>
	
	$.fn.zTree.init($("#ztree"), setting, zNodes);
});
//修改商家角色名称
function updateMerchantUserRole(){
   //角色名称
	var roleName = $("#roleName").val();
	
	if(roleName=="" ){
		$("#roleNameError").html("权限组名称不能为空!");
		return false;
	}else{
	   $("#roleNameError").html("");
	}
    $("#roleName").val(roleName);
    
    var catStrs = [];
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			var nodeChildrens=node.children;
			if (!nodeChildrens&&node.checked) {
				catStrs[catStrs.length] = node.id;
			}
		}
	}
	$("#authrityNameHidden").val(catStrs.join('_'));
	if(catStrs.length <= 0 ){
		$("#roleNameError").html("请分配权限!");
		return false;
	}else{
		$("#roleNameError").html("");
	}
	document.queryForm.submit();
}

</script>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加权限组</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
               <form action="${BasePath}/yitiansystem/merchants/businessorder/update_merchants_role.sc" name="queryForm" id="queryForm" method="post" style="padding:0px;margin:0px;">
                <input type="hidden" id="id" name="id" value="<#if merchantsRole??&&merchantsRole.id??>${merchantsRole.id!''}</#if>">
		      		 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                            <label><span style="color:red;">*</span>权限组名称：</label>
                          </td><td>
                            <input type="text" name="roleName" id="roleName" value="<#if merchantsRole??&&merchantsRole.roleName??>${merchantsRole.roleName!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:red;" id="roleNameError" ></span></td></tr>
                        <tr><td>备注：</td>
                        <td><textarea name="remark" id="remark" cols=20"/>${merchantsRole.remark!''}</textarea>
                        </td></tr>
                        <tr><td width="100px">
                            <label><span style="color:red;"></span>分配权限：</label>
                          </td><td><div class="content_wrap"><ul id="ztree" class="ztree"></ul></div></td></tr>
                        <tr>
	                        <td>
	                        	<input type="hidden" name="authrityNameHidden" id="authrityNameHidden" />
	                        </td>
                        <td> <input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return updateMerchantUserRole();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>