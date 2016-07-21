var msg="用英文逗号分隔，可查询多个商品";
var qc = {};
/**url相关*/
qc.url = {};
/**提交审核url*/
qc.url.submitAuditUrl = basePath + "/commodity/submitAudit.sc";
/**提交成功图片url*/
qc.url.successImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/succeed.png";
/**操作相关*/
qc.opt = {};

$(function(){
	//库存
	$.post(basePath+"/commodity/loadInventoryAndAuditDate.sc",{commodityNos:commodityNos},function(json){
		$.each(json,function(i,n){
			$("#"+n.commodityNo+"-3").find(".c3").text(n.canSalesInventoryNum);
		});
	},"json");
	//加载三级分类
	setGoodsCattegory();
	$("#commodityNo_,#supplierCode_,#styleNo_,#thirdPartyCode_").focusin(function() {
  		if($.trim($(this).val())==msg){
			$(this).val("");
		}
	});
	$("#commodityNo_,#supplierCode_,#styleNo_,#thirdPartyCode_").focusout(function() {
		if($.trim($(this).val())==""){
			$(this).val(msg);
		}
	});
	
	if($.trim($("#commodityNo_").val())==""){
		$("#commodityNo_").val(msg);
	}
	if($.trim($("#supplierCode_").val())==""){
		$("#supplierCode_").val(msg);
	}
	if($.trim($("#styleNo_").val())==""){
		$("#styleNo_").val(msg);
	}
	if($.trim($("#thirdPartyCode_").val())==""){
		$("#thirdPartyCode_").val(msg);
	}

	var fileid='';
	var uploadDialog;
	var uploaders = WebUploader.create({
		// 选完文件后，是否自动上传。
	    auto: true,
	    // swf文件路径
	    swf: basePath+'/webuploader/Uploader.swf',
	    // 文件接收服务端。
	    server: basePath+"/commodity/importXlsToDb.sc",
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: {
	    	id: '#importpicker',
	    	multiple:false
	    },
	    // 只允许选择excel。
	    accept: {
	        title: 'excels',
	        extensions: 'xls,xlsx',
	        mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
	    },
	    fileSingleSizeLimit:10*1024*1024,
	    duplicate:1   //不去重
	});
	// 当有文件添加进来的时候
    uploaders.on( 'fileQueued', function( file ) {
    	uploadDialog = ygdg.dialog({
			id:'uploadDialog',
			title:'提示',
			content:'上传处理中，请稍等片刻<img src="'+basePath+'/yougou/images/loading.gif"/>',
			cancelVal:'取消',
			width:450,
			height:150,
			lock:true
		});
    });
	uploaders.on('beforeFileQueued', function(file) {
	    if(''!=fileid){
	        uploaders.removeFile(fileid,true);
	    }
		var fileType = file.ext;
		if(("xls".toUpperCase()!=fileType.toUpperCase())
				&&("xlsx".toUpperCase()!=fileType.toUpperCase())){
			ygdg.dialog.alert("请上传后缀为.xls或.xlsx的数据文件");
			return false;
		}
		return true;
	});
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploaders.on('uploadSuccess', function(file,response) {
		var htmlContent = "";
		if(response.resultCode=="500"){
			htmlContent+="<span style='color:red;'>"+response.msg+"！</span></br>";
			htmlContent+=(response.uuid ? '<a class="f_blue" id="linker" href="'+basePath+'/commodity/import/error/download.sc?isImport=1&type='+response.type+'&uuid=' + response.uuid + '" target="_blank">失败明细下载</a>' : '请联系优购技术支持人员！');
			uploadDialog.content(htmlContent).unlock();
		}else{
			htmlContent += response.warnInfo;
			if(htmlContent){
				htmlContent+="<br/><br/>";
			}
			htmlContent += ('总导入数：'+response.allCount+'条，成功导入<span id="succeed" style="color:green;">&nbsp;' + 
					(response.allCount-response.errorCount) + 
					'&nbsp;</span>条记录，失败<span id="fail" style="color:red;">&nbsp;' + response.errorCount + 
					'&nbsp;</span>条。<br/>');
			htmlContent += (response.uuid ? '<a class="f_blue" id="linker" href="'+basePath+'/commodity/import/error/download.sc?isImport=1&type='+response.type+'&uuid=' + response.uuid + '" target="_blank">失败明细下载</a>' : '请刷新页面查看！');
			uploadDialog.content("<div style='width:450px; height:150px; OVERFLOW: auto;'>"+htmlContent+"</div>").unlock();
		}
	});
});

//删除全选
$("#checkAll_delete").click(function() {
    $("#checkAll_submitAudit").removeAttr("checked");
    $("#common_proitm").find("input[name='waitsale']").filter(function(){ 
        var val=$("#vb-"+this.value).val();
        $(this).removeAttr("disabled");
        $(this).removeAttr("checked");
	    if("true"==val){
	    	if($("#checkAll_delete").attr('checked')=='checked'){
	        	$(this).attr("checked",true);
	        }
	    }else{
	       	if($("#checkAll_delete").attr('checked')=='checked'){
	        	$(this).attr("disabled",true);
	        }
	    }
	});
});
//审核全选
$("#checkAll_submitAudit").click(function() {
    $("#checkAll_delete").removeAttr("checked");
	$("#common_proitm").find("input[name='waitsale']").filter(function(){ 
        var val=$("#sb-"+this.value).val();
        $(this).removeAttr("disabled");
        $(this).removeAttr("checked");
	    if("true"==val){
	    	if($("#checkAll_submitAudit").attr('checked')=='checked'){
	        	$(this).attr("checked",true);
	        }
	    }else{
	       	if($("#checkAll_submitAudit").attr('checked')=='checked'){
	        	$(this).attr("disabled",true);
	        }
	    }
	});
});
/**
 * 删除 点击事件
 * @param {String} commodityNo 商品编号
 */ 
qc.opt.delete_OnClick = function(commodityNo, supplierCode) {
	if (confirm('确定删除该商品吗？')) {
		$.ajax({
			type: 'post',
			url: '/commodity/import/delete.sc',
			dataType: 'html',
			data: 'rand=' + Math.random() + '&commodityNo=' + (commodityNo || '') + '&supplierCode=' + (supplierCode || ''),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data) == 'true') {
						$('tr[id="' + commodityNo + '-1"]').fadeOut(1000, function(){$(this).remove();});
						$('tr[id="' + commodityNo + '-2"]').fadeOut(1000, function(){$(this).remove();});
						$('tr[id="' + commodityNo + '-3"]').fadeOut(1000, function(){$(this).remove();});
				} else {
					this.error(jqXHR, textStatus, data);
				}
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('删除商品失败');
			}
		});
	}
};
qc.opt.deleteBatch_OnClick = function(url) {
    var ids = $('input[name="waitsale"]').filter(function(){
		return this.checked;
	}).map(function(){
	    var val=$("#vb-"+this.value).val();
	    if("true"==val){
	    	return this.value;
	    }else{
	        return null;
	    }
	}).get();
	if (ids.length <= 0) {
		alert('请先选择需要删除的商品或者有商品不允许删除!');
		return false;
	}
		if (confirm('确定删除选中商品吗？')) {
			$.ajax({
				type: 'post',
				url: '/commodity/import/deletebatch.sc',
				dataType: 'html',
				data: 'rand=' + Math.random() + '&commodityNos=' + ids,
				beforeSend: function(jqXHR) {
				},
				success: function(data, textStatus, jqXHR) {
				    var reg=new RegExp("&otimes;","g"); //创建正则RegExp对象 
				    data=data.replace(reg,"失败");
					ygdg.dialog.alert(data);
					location.href = basePath+url;
				},
				complete: function(jqXHR, textStatus) {
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('删除商品失败');
				}
			});
		}
};

/**
 * 提交审核 点击事件
 * @param {String} commodityNo 商品编号
 * @param {String} supplierCode 供应商款色编码
 */
qc.opt.submitAudit_OnClick = function(commodityNo, supplierCode) {
	if (confirm('您确定将该商品提交审核吗？')) {
	var auditUrl = qc.url.submitAuditUrl + "?commodityNo=" + commodityNo + 
			"&supplierCode=" + supplierCode;
	$.ajax({
		url: auditUrl,
		cache: false,
		type: "GET",
		dataType: "json",
		success: function(data) {
			if(data == null || typeof(data) != "object" ||
					typeof(data.success) != "string")
				return; 
			var errMsg = data.msg || "该商品提交审核失败";
			if("true" != data.success) {
				ygdg.dialog.alert(errMsg);
				return;
			}
			ygdg.dialog.tips('该商品提交审核成功', 1, qc.url.successImgUrl);
			setTimeout(function() {location.reload(true);}, 1000);
		},
		error: function() {
			alert("网络异常，请刷新后重试!");
		}
	});
	}
};
qc.opt.submitAuditBatch_OnClick = function(url) {
	    var ids = $('input[name="waitsale"]').filter(function(){
			return this.checked;
		}).map(function(){
		    var val=$("#sb-"+this.value).val();
		    if("true"==val){
		    	return this.value;
		    }else{
		        return null;
		    }
		}).get();
		if (ids.length <= 0) {
			alert('请先选择需要提交审核的商品或者有商品不允许提交审核!');
			return false;
		}
	if (confirm('您确定将商品提交审核吗？')) {
		$.ajax({
			url: basePath+"/commodity/submitAuditBatch.sc?commodityNos=" + ids,
			cache: false,
			type: "GET",
			dataType: "json",
			success: function(data) {
			        var backstr=data.back;
			        var reg=new RegExp("&otimes;","g"); //创建正则RegExp对象 
				    backstr=backstr.replace(reg,"失败");
					ygdg.dialog.alert(backstr);
					location.href = basePath+url;
			},
			error: function() {
				alert("网络异常，请刷新后重试!");
			}
		});
	}
};

//提交表单查询
qc.formSubmit = function(url) {
	if($.trim($("#commodityNo_").val())!=msg){
		$("#commodityNo").val($.trim($("#commodityNo_").val()));
	}else{
		$("#commodityNo").val("");
	}
	if($.trim($("#supplierCode_").val())!=msg){
		$("#supplierCode").val($.trim($("#supplierCode_").val()));
	}else{
		$("#supplierCode").val("");
	}
	if($.trim($("#styleNo_").val())!=msg){
		$("#styleNo").val($.trim($("#styleNo_").val()));
	}else{
		$("#styleNo").val("");
	}
	if($.trim($("#thirdPartyCode_").val())!=msg){
		$("#thirdPartyCode").val($.trim($("#thirdPartyCode_").val()));
	}else{
		$("#thirdPartyCode").val("");
	}
	var queryForm = $("#queryForm").attr("action", basePath + url);
	queryForm.submit();
};

// 导出数据
function doExport() {
    $("#progressBar").css('visibility','visible');
	ygdg.dialog({
		id:'tempDg',
		title:"提示",
		content:"正在导出中，请稍等片刻<img src='"+basePath+"/yougou/images/loading.gif'/>",
		show:false
	});
	if($.trim($("#commodityNo_").val())!=msg){
		$("#commodityNo").val($.trim($("#commodityNo_").val()));
	}else{
		$("#commodityNo").val("");
	}
	if($.trim($("#supplierCode_").val())!=msg){
		$("#supplierCode").val($.trim($("#supplierCode_").val()));
	}else{
		$("#supplierCode").val("");
	}
	if($.trim($("#styleNo_").val())!=msg){
		$("#styleNo").val($.trim($("#styleNo_").val()));
	}else{
		$("#styleNo").val("");
	}
	ygdg.dialog({
		id:'waitExport',
		content:'请选择待售商品导出方式！',
		title:'提示',
		button:[{
				name:'按商品导出',
				callback:function(){
					exportCommExcel();
				
				}
			},{
				name:'按尺码导出',
				callback:function(){
					exportSizeExcel();
				}
			}]
	});
}

//记忆分类
var memoryRootCat = $('#memoryRootCat').val();
var memorySecondCat = $('#memorySecondCat').val().split(';');
var memoryThreeCat = $('#memoryThreeCat').val().split(';');
if (memoryRootCat != '0' && memoryRootCat != '') {
	$("select:[id='rootCattegory'] option").each(function() {
		var val = $(this).val();
		if (memoryRootCat == val) {
			$(this).attr('selected', 'selected');
			getGoodsCattegory(val, "secondCategory");
		}
	});
}
if (memorySecondCat.length > 1) {
	setSecondCategory(memorySecondCat[0]);
}
if (memoryThreeCat.length > 1) {
	setThreeCategory(memoryThreeCat[0]);
}

function setSecondCategory(cat2) {
	/**二级分类的条件*/
	function cat2Condition() {
		return $("#secondCategory>option").length > 1;
	}
	/**二级分类目标函数*/
	function cat2TargetFun() {
		setCatSelectValue("secondCategory", cat2);
		$("#secondCategory").reJqSelect();
		$("#secondCategory").change();
	}
	createDetector("secondcat2Detector", cat2Condition, cat2TargetFun, 200);
};

var checkStatus = function(commodityNo,sensitive){
	$.ajax({
		url:basePath+'/commodity/checkCommodityStatus.sc',
		async:false,
		data:"commodityNo="+commodityNo,
		dataType:'json',
		success:function(json){
			if(json){
				if(!json['result']){
					ygdg.dialog.alert('商品已经上架，请刷新页面并到“在售商品”页面下架修改');
				}else{
					$("#tempForm").attr("action",basePath+"/commodity/preUpdateCommodity.sc");
					$("#tempForm input[name='commodityNo']").val(commodityNo);
					if(sensitive){
						$("#tempForm input[name='isSensitive']").val(sensitive);
					}
					$("#tempForm").submit();
					$("#tempForm input[name='commodityNo']").val("");
					$("#tempForm").removeAttr("action");
					return false;
				}
			}else{
				ygdg.dialog.alert('商品信息有误，请联系优购技术支持！');
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			ygdg.dialog.alert('商品信息有误，请联系优购技术支持！');
		}
	});
};

function setThreeCategory(cat3) {
	/**二级分类的条件*/
	function cat3Condition() {
		return $("#threeCategory>option").length > 1;
	}
	/**二级分类目标函数*/
	function cat3TargetFun() {
		setCatSelectValue("threeCategory", cat3);
		$("#threeCategory").reJqSelect();
		$("#threeCategory").change();
	}
	createDetector("threecat3Detector", cat3Condition, cat3TargetFun, 200);
};

/**
 * 设置分类下拉框的值
 * @param {String} id 下拉框id
 * @param {String} curStructName 要选中的分类结构名称
 */
setCatSelectValue = function(id , curStructName) {
	var $options = $("#" + id + ">option");
	var option = null;
	for (var i = 0, len = $options.length; i < len; i++) {
		option = $options[i];
		var catInfo = option.value.split(";");
		if(catInfo != null && catInfo.length >= 1) {
			var structName = catInfo[0];
			if(structName == (curStructName)) {
				document.getElementById(id).selectedIndex = i;
			}
		}
	}
};

//拒绝原因
$(".refuse_reason").hover(function() {
	var _this = $(this);
	var data = eval('(' + $(this).attr("data-attr") + ')');
	var _top = _this.offset().top - $(document).scrollTop();
	ygdg.dialog({
		title : data.title,
		content : '<p class="picDetail">拒绝原因：' + data.reason + '</p>',
		id : 'detailBox',
		left : $(this).offset().left - 250,
		top : _top,
		width : 240,
		closable : false
	});
}, function(){
	ygdg.dialog.list['detailBox'].close();
});

//导出商品
function exportCommExcel(){
	//根据时间跨度导出
	$.ajax({
		type: "POST",
		url: basePath+"/commodity/createWaitSaleCommodityByCommRunnable.sc",
		data: $("#queryForm").serialize(),
		dataType: "html",
		success: function(data) {
			if(data=='success'){
				$("#exportData").attr("href","javascript:void(0);");
				$("#exportData").css("color","#555555");
				//$("#progressBar").removeClass("hide");
				//$("#progressBar").show()
				//$("#progressBar").css('visibility','hidden');
			}else if(data=="loading"){
				  ygdg.dialog.alert("上一次导出数据正在执行，请稍后再试！");
			}
			setId = setInterval(function() {
		      	getExportResultComm();
		     }, 5000);
   		}
	});
}
var getExportResultComm = function(){
	$.get(basePath+"/commodity/getExportResultComm.sc",function(data){
		if(data.result=="true"){
			$("#exportData").css("color","#0b80ed");
			$("#exportData").attr("href","javascript:exportCommExcel();");
			$("#progressBar span").text(0);
			//$("#progressBar").addClass("hide");
			 //$("#progressBar").hide();
			 $("#progressBar").css('visibility','hidden');
			window.clearInterval(setId);
			location.href=basePath+"/commodity/waitSaleDownload.sc?name="+data.url+"&_t="+new Date().getTime();
		}else{
			//进度条的值变化
			if(data.progress<0){
				$("#exportData").css("color","#0b80ed");
				$("#exportData").attr("href","javascript:exportCommExcel();");
				$("#progressBarSpan").text(0);
				//$("#progressBar").addClass("hide");
				 //$("#progressBar").hide();
				 $("#progressBar").css('visibility','hidden');
				window.clearInterval(setId);
				ygdg.dialog.alert("导出失败，泪奔呀！");
			}else{
				$("#progressBarSpan").text(data.progress);
				
			}
		}
	},"json");
};


//导出商品-尺寸
function exportSizeExcel(){
	//根据时间跨度导出
	$.ajax({
		type: "POST",
		url: basePath+"/commodity/createWaitSaleCommodityBySizeRunnable.sc",
		data: $("#queryForm").serialize(),
		dataType: "html",
		success: function(data) {
			if(data=='success'){
				$("#exportData").attr("href","javascript:void(0);");
				$("#exportData").css("color","#555555");
				//$("#progressBar").removeClass("hide");
				//$("#progressBar").show();
				//$("#progressBar").css('visibility','hidden');
				
			}else if(data=="loading"){
				  ygdg.dialog.alert("上一次导出数据正在执行，请稍后再试！");
			}
			setId = setInterval(function() {
		      	getExportResultSize();
		     }, 5000);
   		}
	});
}
var getExportResultSize = function(){
	$.get(basePath+"/commodity/getExportResultSize.sc",function(data){
		if(data.result=="true"){
			$("#exportData").css("color","#0b80ed");
			$("#exportData").attr("href","javascript:exportSizeExcel();");
			$("#progressBar span").text(0);
			//$("#progressBar").addClass("hide");
			// $("#progressBar").hide();
			$("#progressBar").css('visibility','hidden');
			window.clearInterval(setId);
			location.href=basePath+"/commodity/waitSaleDownload.sc?name="+data.url+"&_t="+new Date().getTime();
		}else{
			//进度条的值变化
			if(data.progress<0){
				$("#exportData").css("color","#0b80ed");
				$("#exportData").attr("href","javascript:exportSizeExcel();");
				$("#progressBarSpan").text(0);
				//$("#progressBar").addClass("hide");
				// $("#progressBar").hide();
				$("#progressBar").css('visibility','hidden');
				window.clearInterval(setId);
				ygdg.dialog.alert("导出失败，泪奔呀！");
			}else{
				var dataVal=data.progress+0;
				$("#progressBarSpan").text(data.progress);
			}
		}
	},"json");
};