var curPage = 1;
var curPageSize = 20;
$(function(){
	
	loadData(1,20);
	//全选
	$("#selectAll").live("click",function(){
		var isCheckAll = true;
		if($(this).attr("checked")){
			isCheckAll = true;
		}else{
			isCheckAll = false;
		}
		$("input[name='catBindId']").attr("checked",isCheckAll);
	});
	
	//优购分类
	$("#sel1").change(function() {
		taobaoYougouItemCat.reinitializeOption($(this).val(),"sel2");
	});
	$("#sel2").change(function() {
		taobaoYougouItemCat.reinitializeOption($(this).val(),"sel3");
	});
	//淘宝分类
	$("#selt1").change(function() {
		taobaoYougouItemCat.reinitializeOption4Toabao($(this).val(),"selt2");
	});
	$("#selt2").change(function() {
		taobaoYougouItemCat.reinitializeOption4Toabao($(this).val(),"selt3");
	});
});

taobaoYougouItemCat.reinitializeOption = function(structName,nodeId){
	var tempOption =$("#"+nodeId).children().eq(0); 
	$("#"+nodeId).children().remove();
	tempOption.appendTo($("#"+nodeId));
	if(nodeId=="sel3"&&$("#sel2").val()==""){
		return;
	}
	if(nodeId=="sel2"&&$("#sel1").val()==""){
		return;
	}
	if(nodeId=="sel2"){
		$("#sel3").empty();
		$('<option value="" selected="selected">三级分类</option>').appendTo($("#sel3"));
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			structName:structName
		},
		url : taobaoYougouItemCat.basePath+"/yitiansystem/taobao/getYougouItemTree.sc",
		success : function(data) {
			var treeData = data.yougouTree;
			for(var i=0,_len=treeData.length;i<_len;i++){
				if(nodeId=="sel3"){
					$("<option value='"+treeData[i].id+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}else{
					$("<option value='"+treeData[i].structName+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}
			}
		}
	});
}

taobaoYougouItemCat.reinitializeOption4Toabao = function(cid,nodeId){
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
			id:cid
		},
		url : taobaoYougouItemCat.basePath+"/yitiansystem/taobao/getTaobaoItemTree.sc",
		success : function(data) {
			var treeData = data.taobaoTree;
			//如果有三级目录就添加三级下拉
			if("selt3"==nodeId&&treeData.length>0){
				var select = $("<select id='selt3' style='width:100px;'></select>").appendTo("#taobaoSelt");
				$("<option value='' selected='selected'>三级分类</option>").appendTo(select);
			}
			for(var i=0,_len=treeData.length;i<_len;i++){
				if("selt3"==nodeId){
					$("<option value='"+treeData[i].id+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}else{
					$("<option isParent="+treeData[i].isParent+" value='"+treeData[i].structName+"'>"+treeData[i].name+"</option>").appendTo($("#"+nodeId));
				}
			}
		}
	});
}

function loadData(pageNo,pageSize){
	if(pageNo==null||pageNo==""){
		pageNo=1;
	}
	curPageSize = pageSize;
	curPage = pageNo;
	$("#content_list").html('<div class="list-loading">正在载入......</div>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : "getTaobaoItemBindList.sc?page="+pageNo+"&pageSize="+pageSize,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}

function search(){
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
	loadData(curPage,curPageSize);
}

function deleteItem(id){
	var idsArray = [];
	
	if(id==null){
		var catIds = $("input[name='catBindId']:checked");
		if(catIds.length==0){
			ygdg.dialog.alert("请选择要删除的绑定分类");
			return;
		}
		for(var i=0,_len=catIds.length;i<_len;i++){
			idsArray.push(catIds.eq(i).val());
		}
		
	}else{
		idsArray.push(id);
	}
	
	 ygdg.dialog.confirm("确定要删除分类绑定吗?",function(){
		 $.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data:{
					ids:idsArray.join(",")
				},
				url : "deleteYougouTaobaoItemCat.sc",
				success : function(data) {
					if(data.resultCode=="200"){
						ygdg.dialog.alert("删除成功!");
						loadData(curPage,curPageSize);
					}else{
						ygdg.dialog.alert(data.msg);
					}
				}
			});
	 });
}