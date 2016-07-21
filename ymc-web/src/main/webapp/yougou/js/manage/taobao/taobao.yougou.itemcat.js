var curPage = 1;
$(function(){
	//taobaoYougouItemCat.reinitializeOption("0","sel1");
	$("#sel1").change(function() {
		taobaoYougouItemCat.reinitializeOption($(this).val(),"sel2");
	});
	$("#sel2").change(function() {
		taobaoYougouItemCat.reinitializeOption($(this).val(),"sel3");
	});
	loadData(curPage);
	
	$(".chkall").live("click",function(){
		var checked=$(this).attr("checked");
		if (typeof(checked)=="undefined") checked=false;
		$('.list_table input[type=checkbox],.page_box input[type=checkbox]').attr('checked',checked); 
		if(checked)$('.list_table input[type=checkbox]').parent().parent().parent().addClass("curr");
		else
		$('.list_table input[type=checkbox]').parent().parent().parent().removeClass("curr");
	});
	
});
taobaoYougouItemCat.subform  = function(){
	var sel1Val = $("#sel1").val();
	var sel2Val = $("#sel2").val();
	var sel3Val = $("#sel3").val();
	if((sel1Val!=""||sel2Val!="")&&sel3Val==""){
		ygdg.dialog.alert("请选择三级分类");
		return;
	}
	loadData(curPage);
}
taobaoYougouItemCat.reinitializeOption = function(structName,nodeId){
	var tempOption =$("#"+nodeId).children().eq(0); 
	$("#"+nodeId).children().remove();
	tempOption.appendTo($("#"+nodeId));
	$("#"+nodeId).change().reJqSelect();
	if(nodeId=="sel3"&&$("#sel2").val()==""){
		return;
	}
	if(nodeId=="sel2"&&$("#sel1").val()==""){
		return;
	}
	
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			structName:structName
		},
		url : taobaoYougouItemCat.basePath+"/taobao/getYougouItemTree.sc",
		success : function(data) {
			var treeData = data.yougouTree
			for(var i=0,_len=treeData.length;i<_len;i++){
				if(nodeId=="sel3"){
					$("<option value='"+treeData[i].id+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}else{
					$("<option value='"+treeData[i].structName+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}
			}
			$("#"+nodeId).change().reJqSelect();
		}
	});
}
/**
 * 删除
 */
taobaoYougouItemCat.del = function(id){
	var ids = [];
	if(id==null){
		var bindIds = $("input[name='bindId']:checked");
		for(var i=0,length=bindIds.length;i<length;i++){
			ids.push(bindIds.eq(i).val());
		}
	}else{
		ids.push(id);
	}
	
	if(ids.length==0){
		ygdg.dialog.alert("请选择要删除的选项");
		return;
	}
	ygdg.dialog.confirm("确定要删除吗？", function(){
		//ymc_common.loading("show","正在删除......");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				ids:ids.join(",")
			},
			url : taobaoYougouItemCat.basePath+"/taobao/delYougouTaobaoItemCat.sc",
			success : function(data) {
				//ymc_common.loading();
				if(data.resultCode == "200"){
					ygdg.dialog.alert("删除成功");
					loadData(curPage);
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
};

/**
 * 下载淘宝商品
 */
taobaoYougouItemCat.downloadItem = function(id){
	var ids = [];
	if(id==null){
		var bindIds = $("input[name='bindId']:checked");
		for(var i=0,length=bindIds.length;i<length;i++){
			ids.push(bindIds.eq(i).val());
		}
	}else{
		ids.push(id);
	}
	
	if(ids.length==0){
		ygdg.dialog.alert("请选择要下载商品的分类");
		return;
	}
	ymc_common.loading("show","正在下载商品......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			ids:ids.join(",")
		},
		url : taobaoYougouItemCat.basePath+"/taobao/downloadItem.sc",
		success : function(data) {
			ymc_common.loading();
			if(data.resultCode == "200"){
				ygdg.dialog.alert("成功下载了<span style='color:red'>"+data.resultTotal.itemTotal+"</span>件商品,共<span style='color:red'>"+data.resultTotal.extendTotal+"</span>款,<a href='goTaobaoItemList.sc'>去看看</a>");
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
};

function loadData(pageNo){
	curPage = pageNo;
	$("#content_list").html('<table class="list_table"><thead>'+
		'<tr><th width="8%"></th><th width="10%">淘宝店铺</th><th width="25%">淘宝分类</th><th width="25%">优购分类</th><th width="15%">保存时间</th><th>操作</th></tr>'+
		'<tbody id="tbody"><tr><td colspan="10">正在载入......<td></tr></tbody>'+
		'<table>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : taobaoYougouItemCat.basePath+"/taobao/getYougouTaobaoItemCatList.sc?page="+pageNo,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}

