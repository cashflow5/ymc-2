var path=$("#path").val();

/**
 * 增加角色
 */
function toAddRole(){
	//showThickBox("增加角色","../../systemmgmt/authorityRole/toAddRole.sc?TB_iframe=true&height=400&width=600&modal=true",false);
	//openwindow("../../systemmgmt/authorityRole/toAddRole.sc",600,400,"增加角色");
	window.location.href = "toAddRole.sc";
}


/**
* 增加角色
*/
function backList(){
	window.location.href = "queryRoleList.sc";
}

/**
 * 修改角色
 */
function toUpdateRole(id){
	//var params = "id="+id;
	//openwindow("../../systemmgmt/authorityRole/toUpdateRole.sc?id="+id,600,400,"修改角色");
	//showThickBox("增加角色","../../systemmgmt/authorityRole/toUpdateRole.sc?TB_iframe=true&height=400&width=600&modal=true",false,params);
	window.location.href = "toUpdateRole.sc?id="+id;
 }

/**
 * 删除角色
 */
function removeRole(id){
	if(confirm("确定要删除该角色")){
		window.location.href = "d_remove.sc?id="+id;
	}
}

/**
 *到用户角色资源分配
 * @param id
 */
function allotRoleResource(id){
	var params = "id="+id;
	openwindow("../../systemmgmt/authorityRole/toAllotRoleResource.sc?id="+id,600,400,"角色分配资源");
	//showThickBox("角色分配资源","../../systemmgmt/authorityRole/toAllotRoleResource.sc?TB_iframe=true&height=400&width=600",false,params);
}

/**
 *操作日志
 */
function queryLog(){
	window.location.href="../../systemmgmt/operaterlog/queryOperateLog.sc?url=operate";
}


/**
 * 提交资源分配表单
 */
function submitroleResourcesForm(){
	
	$("#allCheckResources").attr("value",getCheckedNodes());
	
	var form = document.forms[0];
	form.target="mbdif";		//表单提交后在父页面显示结果
	form.submit();
	window.top.TB_remove();
}


//获取选中的节点(选中的checked)
function getCheckedNodes() {
	var nodes = $('#resourceTree').tree('getChecked');
	var s = '';
	for (var i = 0; i < nodes.length; i++) {
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	return s;
}

/**
 * 提交表单
 */
function submitForm(){
	
	var form = document.forms[0];
	form.target="mbdif";		//表单提交后在父页面显示结果
	form.submit();
	window.top.TB_remove();
}


