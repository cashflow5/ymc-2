
/**
 * 删除系统用户
 */
function removeConfig(id){
	if(confirm("该配置删除后，系统相关功能将不能使用,确定要删除该系统配置？")){
		
		var url = "d_remove.sc";
		var data={
			"id":id
		};
		
		ajaxRequest(url,data,function(result){
			if(!result) return ;
			result = result.replace(/(^\s*)|(\s*$)/g,'');
			if(result == "success"){
				alert("删除成功");
				$("#Tr"+id).remove(); 
			}else{
				alert("删除失败");
			}
		});
	}
}


/**
* 一键更新缓存
*/
function updateCache(){
	if(confirm("一键更新缓存,所有配置项都将重新读入缓存中!")){
		var url = "updateCache.sc";
		var data={};
		ajaxRequest(url,data,function(result){
			if(!result) return ;
			result = result.replace(/(^\s*)|(\s*$)/g,'');
			if(result == "success"){
				alert("一键更新缓存成功");
			}else{
				alert("一键更新缓存失败");
			}
		});
	}
}
/**
* 选择用户组织机构
*/
function toUpdateConfig(id){
	var param = "id="+id;
	//showThickBox("短信明细","../../systemmgmt/systemconfig/toUpdateSystemConfig.sc?TB_iframe=true&height=550&width=750",false,param);
	openwindow("../../systemmgmt/systemconfig/toUpdateSystemConfig.sc?id="+id,750,550,"短信明细");
}

/**
* 选择用户组织机构
*/
function toAddConfig(){
	//showThickBox("短信明细","../../systemmgmt/systemconfig/toAddSystemConfig.sc?TB_iframe=true&height=550&width=750",false);
	openwindow("../../systemmgmt/systemconfig/toAddSystemConfig.sc",750,550,"短信明细");
}


//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  success: callback
	});
}