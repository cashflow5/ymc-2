var curPage = 1;
$(function(){
	//taobaoYougouItemCatTemplet.reinitializeOption("0","sel1");
	$("#sel1").change(function() {
		taobaoYougouItemCatTemplet.reinitializeOption($(this).val(),"sel2");
	});
	$("#sel2").change(function() {
		taobaoYougouItemCatTemplet.reinitializeOption($(this).val(),"sel3");
	});
	
	$("#selt1").change(function() {
		taobaoYougouItemCatTemplet.reinitializeOption4Toabao($(this).val(),"selt2");
	});
	$("#selt2").change(function() {
		taobaoYougouItemCatTemplet.reinitializeOption4Toabao($(this).val(),"selt3");
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

taobaoYougouItemCatTemplet.reinitializeOption4Toabao = function(cid,nodeId){
	var tempOption =$("#"+nodeId).children().eq(0); 
	$("#"+nodeId).children().remove();
	tempOption.appendTo($("#"+nodeId));
	if(nodeId=="selt3"&&$("#selt2").val()==""){
		return;
	}
	if(nodeId=="selt2"&&$("#selt1").val()==""){
		return;
	}
	$("#selt3").remove();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			parentCid:cid
		},
		url : taobaoYougouItemCatTemplet.basePath+"/taobao/getTaobaoItemTempletTree.sc",
		success : function(data) {
			var treeData = data.taobaoTree;
			//如果有三级目录就添加三级下拉
			if("selt3"==nodeId&&treeData.length>0){
				var select = $("<select id='selt3' style='width:100px;'></select>").appendTo("#taobaoSelt");
				$("<option value='' selected='selected'>三级分类</option>").appendTo(select);
			}
			for(var i=0,_len=treeData.length;i<_len;i++){
				if("selt3"==nodeId){
					$("<option value='"+treeData[i].cid+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}else{
					$("<option isParent="+treeData[i].isParent+" value='"+treeData[i].cid+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}
			}
			$("#"+nodeId).change().reJqSelect();
		}
	});
}

taobaoYougouItemCatTemplet.subform  = function(){
	if($("#sel1").val()!=""&&$("#sel2").val()==""){
		ygdg.dialog.alert("请选择优购二级分类");
		return;
	}
	if($("#sel2").val()!=""&&$("#sel2").val()!=""&&$("#sel3").val()==""){
		ygdg.dialog.alert("请选择优购三级分类");
		return;
	}
	if($("#selt1").val()!=""&&$("#selt2").val()==""){
		ygdg.dialog.alert("请选择淘宝二级分类");
		return;
	}
	if($("#selt2").val()!=""&&$("#selt2").val()!=""&&$("#selt3").val()==""){
		ygdg.dialog.alert("请选择优购三级分类");
		return;
	}
	//设置淘宝三级分类
	$("#taobaoCid").val("");
	if($("#selt2").val()!=""&&$("#selt3").length==0){
		$("#taobaoCid").val($("#selt2").val());
	}else if($("#selt2").val()!=""&&$("#selt3").val()!=""){
		$("#taobaoCid").val($("#selt3").val());
	}
	loadData(curPage);
}
taobaoYougouItemCatTemplet.reinitializeOption = function(structName,nodeId){
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
		url : taobaoYougouItemCatTemplet.basePath+"/taobao/getYougouItemTree.sc",
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
taobaoYougouItemCatTemplet.del = function(id){
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
			url : taobaoYougouItemCatTemplet.basePath+"/taobao/delYougouTaobaoItemCat.sc",
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
taobaoYougouItemCatTemplet.downloadItem = function(id){
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
		url : taobaoYougouItemCatTemplet.basePath+"/taobao/downloadItem.sc",
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
	$("#content_list").html('<table class="list_table">'+
		'<tbody id="tbody"><tr><td colspan="10">正在载入......<td></tr></tbody>'+
		'<table>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : taobaoYougouItemCatTemplet.basePath+"/taobao/getYougouTaobaoItemCatTempletList.sc?page="+pageNo,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}

