var curPage = 1; 
var msg="用英文逗号分隔，可查询多个商品";
function loadData(pageNo,showError){
	curPage = pageNo;
	$("#content_list").html('<table class="list_table">'+
		'<tbody id="tbody"><tr><td colspan="10">正在载入......<td></tr></tbody>'+
		'<table>');
	
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
	
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : taobaoItem.basePath+"/taobao/getTaobaoItemList.sc?page="+pageNo,
		success : function(data) {
			$("#content_list").html(data);
			if(showError){
				taobaoItem.showErrorMsgLink();
			}
			taobaoItem.showExprotInfo();
		}
	});
}
$(function() {
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
});
function search(){
	var sel1Val = $("#sel1").val();
	var sel2Val = $("#sel2").val();
	var sel3Val = $("#sel3").val();
	if((sel1Val!=""||sel2Val!="")&&sel3Val==""){
		ygdg.dialog.alert("请选择三级分类");
		return;
	}
	loadData(1);
}
taobaoItem.updateStatus = function(id,status){
  	$.ajax({ 
		type: "post", 
		url: taobaoItem.basePath+"/taobao/updateTaobaoShopStatus.sc", 
		data:{"id":id,"status":status},
		dataType: "json",
			success: function(dt){
				if(true==dt.success){
				   location.reload(); 
				}else{
				    ygdg.dialog.alert(dt.message);
				}
			} 
	    });
};
/**
 * 展示导入结果
 */
taobaoItem.showExprotInfo = function(){
	var response = taobaoItem.exportResponse;
	if(null!=response){
		if(response.resultCode == '200'){			
			$("#downloadError").show();
			$("#successCount").text(response.result.successCount);
			$("#erroCount").text(response.result.errorCount);
			if(parseInt(response.result.errorCount)>0){
				$("#errorLink").show();
				$("#errorLink").attr("href",taobaoItem.basePath+"/taobao/excelDownload.sc?name="+response.url+"&_t="+new Date().getTime());
			}else{
				$("#errorLink").hide();
			}
			taobaoItem.exportResponse = null;
		}else{
			$("#downloadErrorInfo").show();
			$("#downloadErrorInfo").html(response.result);
		}
	}
};
$(function(){
	$("#checkAllImport").live("click",function() {
		var checkAllVal = $(this).attr("checked");
		$("#checkAllDel").attr("checked",false);
	    $("#tbody").find("input[name='extendId']").filter(function(){ 
		    if($(this).attr("checkstatus")=="1"){
		    	$(this).attr("disabled",false);
		    	if(checkAllVal=="checked"){
		    		$(this).attr("checked",true);
		    	}else{
		    		$(this).attr("checked",false);
		    	}
		    }else{
		    	$(this).attr("checked",false);
		    	if(checkAllVal=="checked"){
		    		$(this).attr("disabled",true);
		    	}else{
		    		$(this).attr("disabled",false);
		    	}
		    	
		    }
		});
	});
	
	$("#checkAllDel").live("click",function() {
		$("#checkAllImport").attr("checked",false);
		var checkAllVal = $(this).attr("checked");
	    $("#tbody").find("input[name='extendId']").filter(function(){ 
	    	$(this).attr("disabled",false);
	    	if(checkAllVal=="checked"){
	    		$(this).attr("checked",true);
	    	}else{
	    		$(this).attr("checked",false);
	    	}
		});
	});
	
	$("#checkAllExport").live("click",function() {
		$("#checkAllImport").attr("checked",false);
		var checkAllVal = $(this).attr("checked");
	    $("#tbody").find("input[name='extendId']").filter(function(){ 
	    	$(this).attr("disabled",false);
	    	if(checkAllVal=="checked"){
	    		$(this).attr("checked",true);
	    	}else{
	    		$(this).attr("checked",false);
	    	}
		});
	});
	
	$("#checkAllInitData").live("click",function() {
		$("#checkAllImport").attr("checked",false);
		var checkAllVal = $(this).attr("checked");
	    $("#tbody").find("input[name='extendId']").filter(function(){ 
	    	$(this).attr("disabled",false);
	    	if(checkAllVal=="checked"){
	    		$(this).attr("checked",true);
	    	}else{
	    		$(this).attr("checked",false);
	    	}
		});
	});
	
	loadData(curPage);
	
	$("#sel1").change(function() {
		taobaoItem.reinitializeOption($(this).val(),"sel2");
	});
	$("#sel2").change(function() {
		taobaoItem.reinitializeOption($(this).val(),"sel3");
	});
});

taobaoItem.reinitializeOption = function(structName,nodeId){
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
		url : taobaoItem.basePath+"/taobao/getYougouItemTree.sc",
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
taobaoItem.del = function(id){
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
		ygdg.dialog.alert("请选择要删除的选项");
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
			url : taobaoItem.basePath+"/taobao/deleteTaobaoItemExtend.sc",
			success : function(data) {
				ymc_common.loading();
				if(data.resultCode == "200"){
					ygdg.dialog.alert("删除成功");
					loadData(curPage,false);
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
};

/**
 * 导出excel
 */
taobaoItem.exportExcel = function(){
	var itemIds = [];
	var numIids = $("input[name='extendId']:checked");
	for(var i=0,length=numIids.length;i<length;i++){
		itemIds.push(numIids.eq(i).val());
	}
	if(itemIds.length==0){
		ygdg.dialog.alert("请选择要导出的选项");
		return;
	}
	
	var exDialog=ygdg.dialog({
		content:"正在导出中，请稍等片刻<img src="+taobaoItem.basePath+"'/yougou/images/loading.gif'/>",
		title:'提示',
		cancel:function(){exDialog=null;return true;},
		cancelVal:'取消',
		lock:true
	});
	$.ajax({
		type: "POST",
		url: taobaoItem.basePath+"/taobao/createItemExcel.sc",
		data: {
			"itemIds":itemIds.join(",")
		},
		dataType: "json",
		success: function(data) {
			if(data.resultCode=="200"){
				if(exDialog){
					location.href=taobaoItem.basePath+"/taobao/excelDownload.sc?name="+data.url+"&_t="+new Date().getTime();
					exDialog.close();
				}
			}else{
				exDialog.close();
				ygdg.dialog.alert(data.msg);
			}
   		}
	});
	
}

//导入
taobaoItem.importResult = {};
taobaoItem.importItem = function(id,checkStatus,isAudit){
	var ids = [];
	if(id==null){
		var numIids = $("input[name='extendId']:checked");
		for(var i=0,length=numIids.length;i<length;i++){
			ids.push(numIids.eq(i).val());
			if(numIids.eq(i).attr("checkstatus")!="1"){
				ygdg.dialog.alert("校验未通过的数据不能导入");
				return;
			}
		}
	}else{
		if(checkStatus!="1"){
			ygdg.dialog.alert("校验未通过的数据不能导入");
			return;
		}
		ids.push(id);
	}
	
	if(ids.length==0){
		ygdg.dialog.alert("请选择要导入的选项");
		return;
	}
	var warmStr="确定要导入吗？";
	if(isAudit==1){
		warmStr="确定要导入,并提交审核吗？";
	}
	ygdg.dialog.confirm(warmStr, function(){
		ymc_common.loading("show","正在导入......");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				extend_id:ids.join(",")
			},
			url : taobaoItem.basePath+"/taobao/toTaobaoItemImportYougou.sc?isAudit="+isAudit,
			success : function(data) {
				ymc_common.loading();
				if(data.code == '200'){
					//给出错误信息
					if(data.message){
						ygdg.dialog.alert(data.message);
					}
					//重新载入
					loadData(curPage,true);
				}else if(data.code == '100'){
					$.dialog({
					    id: 'testID',
					    title:'检测到敏感词提醒',
					    content: "<div style='width:450px; height:150px; OVERFLOW: auto;'>"+data.message+"</div>",
					    lock: true,
					    resize: false,
					    button: [
					        {
					            name: '仅导入优购',
					            callback: function () {
					            	ajaxImportToYougou(ids.join(","),3);
					            },
					            focus: true
					        },
					        {
					            name: '确认提交审核',
					            callback: function () {
					            	ajaxImportToYougou(ids.join(","),2);
					            }
					        }
					    ]
					});
				}else if(data.code == '300'){
					$.dialog({
					    id: 'testID',
					    title:'导入成功-检测到敏感词提醒',
					    content: "<div style='width:450px; height:150px; OVERFLOW: auto;'>"+data.message+"</div>",
					    lock: true,
					    resize: false,
					    button: [
					        {
					            name: '立即去修改',
					            callback: function () {
					            	window.location.href = taobaoItem.basePath+"/commodity/goWaitSaleSensitiveCommodity.sc";
					            },
					            focus: true
					        },
					        {
					            name: '关闭',
					            callback: function(){
					            	//重新载入
									loadData(curPage,true);
					            }
					        }
					    ]
					});
				}else{
					ygdg.dialog.alert(data.message);
				}
			}
		});
	});
};

function ajaxImportToYougou(extend_id,isAudit){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			extend_id:extend_id
		},
		url : taobaoItem.basePath+"/taobao/toTaobaoItemImportYougou.sc?isAudit="+isAudit,
		success : function(data) {
			ymc_common.loading();
			if(data.code == '200'){
				//给出错误信息
				if(data.message){
					ygdg.dialog.alert(data.message);
				}
				//重新载入
				loadData(curPage,true);
			}else{
				ygdg.dialog.alert(data.message);
			}
		}
		})
}


taobaoItem.toUpdateTaobaoItem = function(extendId,numIid){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			numIid:numIid
		},
		url : taobaoItem.basePath+"/taobao/checkBindInfo.sc",
		success : function(data) {
			ymc_common.loading();
			if(data.resultCode == "200"){
				document.location.href="toUpdateTaobaoItem.sc?numIid="+numIid+"&extendId="+extendId;
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
	
}

taobaoItem.showErrorMsgLink = function(){
	var messages = taobaoItem.importResult.errorMsgs;
	if(messages!=null){
		var message;
		if(messages!=null&&messages!=""&&messages.length>0){
			for(var i=0,len=messages.length;i<len;i++){
				message = messages[i];
				$("#"+message.id).show();
			}
		}
	}
}

String.prototype.startWith=function(str){    
	  var reg=new RegExp("^"+str);    
	  return reg.test(this);       
}  

taobaoItem.showErrorMsg = function(numIid){
	var messages = taobaoItem.importResult.errorMsgs;
	var message;
	var erros ="";
	for(var i=0,len=messages.length;i<len;i++){
		message = messages[i];
		if(numIid==message.id){
			var errorList =  message.errorList;
			var errorObj;
			for(var j=0,_len=errorList.length;j<_len;j++){
				errorObj = errorList[j];
				erros =erros+"<span style='color:red;'>"+(j+1)+"、</span>"+errorObj+"<br>";
			}
			break;
		}
	}
	if(erros!=null){
		ygdg.dialog.alert(erros);
	}
}

taobaoItem.initData = function(id){
	var ids = [];
	if(id==null){
		var extendId = $("input[name='extendId']:checked");
		for(var i=0,length=extendId.length;i<length;i++){
			ids.push(extendId.eq(i).attr("extendval"));
		}
	}else{
		ids.push(id);
	}
	if(ids.length==0){
		ygdg.dialog.alert("请选择要初始化的选项");
		return;
	}
	ygdg.dialog.confirm("确定要初始化吗？初始化后，之前的修改将失效", function(){
		ymc_common.loading("show","正在初始化......");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data:{
				ids:ids.join(",")
			},
			url : taobaoItem.basePath+"/taobao/initDataFromTemplate.sc",
			success : function(data) {
				ymc_common.loading();
				if(data.resultCode == "200"){
					ygdg.dialog.alert(data.msg);					
					loadData(curPage,false);
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
}