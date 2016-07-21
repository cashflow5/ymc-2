var curPage = 1; 
var msg="用英文逗号分隔，可查询多个商品";
function loadData(pageNo,showError){
	if($.trim($("#yougouStyleNo_").val())!=msg){
		$("#yougouStyleNo").val($.trim($("#yougouStyleNo_").val()));
	}else{
		$("#yougouStyleNo").val("");
	}
	if($.trim($("#yougouSupplierCode_").val())!=msg){
		$("#yougouSupplierCode").val($.trim($("#yougouSupplierCode_").val()));
	}else{
		$("#yougouSupplierCode").val("");
	}
	if($.trim($("#yougouThirdPartyCode_").val())!=msg){
		$("#yougouThirdPartyCode").val($.trim($("#yougouThirdPartyCode_").val()));
	}else{
		$("#yougouThirdPartyCode").val("");
	}
	curPage = pageNo;
	$("#content_list").html('<table class="list_table"><thead>'+
		'<tr><th>商品名称</th><th>所属店铺</th><th>颜色</th><th>价格</th><th>下载时间</th><th>操作</th></tr>'+
		'<tbody id="tbody"><tr><td colspan="6">正在载入......<td></tr></tbody>'+
		'<table>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : taobaoItem.basePath+"/taobao/getRecycleList.sc?page="+pageNo,
		success : function(data) {
			$("#content_list").html(data);
			if(showError){
				taobaoItem.showErrorMsgLink();
			}
		}
	});
}

$(function(){
	$("#checkAllRecycle").live("click",function() {
		if($("#checkAllRecycle").prop("checked")){
			$("input[name='extendId']:checkbox").prop("checked", true);
		}else{
			$("input[name='extendId']:checkbox").prop("checked", false);
		}
	});
	
	
	$("#yougouStyleNo_,#yougouSupplierCode_,#yougouThirdPartyCode_").focusin(function(){
		if($.trim($(this).val())==msg){
			$(this).val("");
		}
	});
	$("#yougouStyleNo_,#yougouSupplierCode_,#yougouThirdPartyCode_").focusout(function(){
		if($.trim($(this).val())==""){
			$(this).val(msg);
		}
	});
	
	if($.trim($("#yougouStyleNo_").val())==""){
		$("#yougouStyleNo_").val(msg);
	}
	if($.trim($("#yougouSupplierCode_").val())==""){
		$("#yougouSupplierCode_").val(msg);
	}
	if($.trim($("#yougouThirdPartyCode_").val())==""){
		$("#yougouThirdPartyCode_").val(msg);
	}
	
	loadData(curPage);
});

var recycle = function(id){
	var ids = [];
	if(id==null){
		var extendId = $("input[name='extendId']:checked");
		for(var i=0,length=extendId.length;i<length;i++){
			ids.push(extendId.eq(i).val());
		}
	}else{
		ids.push(id);
	}
	
	if(ids.length==0){
		ygdg.dialog.alert("请选择要彻底删除的商品");
		return;
	}
	ygdg.dialog.confirm("确定要删除吗？", function(){
		ymc_common.loading("show","正在删除......");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				ids:ids.join(",")
			},
			url : taobaoItem.basePath+"/taobao/deleteRecycle.sc",
			success : function(data) {
				ymc_common.loading();
				if(data.resultCode == "200"){
					ygdg.dialog.alert("删除成功");
					document.location.reload();
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
};

var reduct = function(id){
	var ids = [];
	if(id==null){
		var extendId = $("input[name='extendId']:checked");
		for(var i=0,length=extendId.length;i<length;i++){
			ids.push(extendId.eq(i).val());
		}
	}else{
		ids.push(id);
	}
	
	if(ids.length==0){
		ygdg.dialog.alert("请选择要还原的商品");
		return;
	}
	ygdg.dialog.confirm("确定要还原吗？", function(){
		ymc_common.loading("show","正在还原......");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				ids:ids.join(",")
			},
			url : taobaoItem.basePath+"/taobao/reductTaobao.sc",
			success : function(data) {
				ymc_common.loading();
				if(data.resultCode == "200"){
					ygdg.dialog.alert("还原成功");
					document.location.reload();
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
	
};

