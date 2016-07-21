function registerDaysSelect_onchange(obj){
	$("#register_days").val(obj.options[obj.selectedIndex].value);
}

function searchSubmit(){
	$("#search_value").val($("#search_value_tmp").val());
	$("#queryForm_").submit();
}

//屏蔽用户账户
function editStatus_limit(hrefObj,id){
	//alert(1);
	_editStatus(id,0);
	$("#status_"+id).html("<a href=javascript:editStatus_open(this,'"+id+"')>解锁</a>");
}

//开放用户账户
function editStatus_open(hrefObj,id){
	//alert(2);
	_editStatus(id,1);
	$("#status_"+id).html("<a href=javascript:editStatus_limit(this,'"+id+"')>锁定</a>");
}

//内部改变状态的Ajax请求
function _editStatus(id,state){
	$.ajax({
	   type: "POST",
	   url: "u_editStatus.sc",
	   data: "id="+id+"&state="+state,
	   success: function(){ 
	   }
	}); 
}