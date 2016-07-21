var msg="用英文逗号分隔，可查询多个商品";
var qc = {};
/**url相关*/
qc.url = {};
/**修改页面url*/
qc.url.showCommodityLogUrl = basePath + "/commodity/showCommodityLog.sc?commodityNo=";
/**提交成功图片url*/
qc.url.successImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/succeed.png";
/**操作相关*/
qc.opt = {};

$(function(){
	//销量
	$.post(basePath+"/commodity/loadSaleQuantity.sc",{commodityNos:commodityNos},function(json){
		var salenums = json.salenums;
		for(var key in salenums){
			$(".salenum_"+key).text(salenums[key]);
		}
	},"json");
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
	
	$(".fl p.mb3 a.edit").click(function(){
		$(this).parent().find("a.commodity-title").hide();
		var textarea = $(this).parent().find("textarea");
		textarea.val($(this).parent().find("a.commodity-title").text());
		textarea.show();
		textarea.focus();
		$(this).hide();
		$(this).parent().find("a.save").show();
		$(this).parent().find("a.cancel").show();
	});
	
	$(".fl p.mb3 a.cancel").click(function(){
		$(this).parent().find("a.commodity-title").show();
		$(this).parent().find("textarea").hide();
		$(this).hide();
		$(this).parent().find("a.save").hide();
		$(this).parent().find("a.edit").show();
	});
	
	$(".fl p.mb3 a.save").click(function(){
		$(this).hide();
		var commodityName = $(this).parent().find("textarea").val();
		var commodityNo = $(this).parent().find("input.commodity-no").val();
		saveCommodityName($(this).parent(),commodityName,commodityNo);
	});
	
	//修改价格
	$("p em.c3 a.edit4price").click(function(){
		$(this).hide();
		var c3 = $(this).parent();
		c3.find(".curprice").hide();
		c3.find("input").show();
		c3.find("input").val(c3.find(".curprice").text());
		c3.find("input").focus();
		c3.find(".save4price").css({display:"inline-block"});
		c3.find(".cancel4price").css({display:"inline-block"});
	});

	$("p em.c3 a.cancel4price").click(function(){
		$(this).hide();
		var c3 = $(this).parent();
		c3.find(".curprice").show();
		c3.find("input").hide();
		c3.find(".edit4price").css({display:"inline-block"});
		c3.find(".save4price").hide();
		c3.find(".cancel4price").hide();
	});

	$("p em.c3 a.save4price").click(function(){
		savePrice($(this));
	});
});
//提交表单查询
function mySubmit() {
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
 	//先验证
	var queryForm = document.getElementById("queryForm");
	queryForm.action =basePath+"/commodity/goQueryOnSaleCommodity.sc";
	queryForm.submit();
}
//导出数 据
function doExport() {
	var exDialog=ygdg.dialog({
		content:"正在导出中，请稍等片刻<img src='"+basePath+"/yougou/images/loading.gif'/>",
		title:'提示',
		cancel:function(){exDialog=null;return true;},
		cancelVal:'取消',
		lock:true
	});
	$.ajax({
		type: "POST",
		url: basePath+"/commodity/createOnSaleCommodity.sc",
		data: $("#queryForm").serialize(),
		dataType: "json",
		success: function(data) {
			if(data.result){
				if(exDialog){
					location.href=basePath+"/commodity/onSaleDownload.sc?name="+data.url+"&_t="+new Date().getTime();
					exDialog.close();
				}
			}else{
				exDialog.content("导出失败，服务器发生错误！");
			}
   		}
	});
}
function saveCommodityName(curP,commodityName,commodityNo){
	curP.find("a.edit").hide();
	curP.find("a.save").show();
	curP.find("a.cancel").show();
	//过滤特殊字符,排除回车、转义、英文引号
	commodityName = $.trim(commodityName.replace(/\"/g, "").replace(/\r\n/g, "").replace(/\n/g,"").replace(/\\/g, "").replace(/\s+/g," "));
	if(commodityName==""){
		ygdg.dialog.alert("商品名称不能为空");
		return;
	}
	if(commodityName.length>200){
		ygdg.dialog.alert("商品名称长度不能超过200");
		return;
	}
	if(commodityNo==""){
		ygdg.dialog.alert("商品编码不能为空");
		return;
	}
	curP.find("a.save").hide();
	curP.find("a.cancel").hide();
	curP.find("span").show();

	$.post(basePath+"/commodity/checkOnLineSensitiveWord.sc",{
		name:commodityName,
		commodityNo:commodityNo
		},function(json){
		var html = "";
		$.each(json.sensitiveResult,function(i,n){
			html+="款色编码："+n.supplierCode+"&nbsp;检测到敏感词：<span class='cred'>"+n.sensitiveWord+"</span><br/>";
		});
		if($.trim(html)!=''){
			$.dialog({
			    id: 'sensitiveDialog',
			    title:'检测到敏感词提醒',
			    content: "<p class='cred'><b>检测到敏感词，建议处理后再继续</b></p>"+html,
			    width:450,
			    lock: true,
			    resize: false,
			    cancel:false,
			    button: [
			        {
			            name: '取消',
			            callback: function () {
			            	curP.find("span").hide();
			            	$.post(basePath+"/commodity/logSensitiveWordByNo.sc",
			            			{followOperate:0,commodityName:commodityName,commodityNo:commodityNo,rand:Math.random()});
			            	curP.find("a.save").hide();
		    				curP.find("a.cancel").hide();
		    				curP.find("a.edit").show();
		    				curP.find("textarea").hide();
		    				curP.find("a.commodity-title").show();
			            },
			            focus: true
			        },{
			            name: '确认继续',
			            callback: function(){
			            	submitUpdateName(curP,commodityName,commodityNo,1);
				        }
			        }
			    ]
			});
		}else{
			submitUpdateName(curP,commodityName,commodityNo);
		}
	},"json");
	
}
function submitUpdateName(curP,commodityName,commodityNo,followOperate){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{commodityName:commodityName,commodityNo:commodityNo,followOperate:(followOperate?1:'')},
		url : basePath+"/commodity/updateCommodityName.sc",
		success : function(data) {
			curP.find("span").hide();
			if(data.resultCode=="200"){
				curP.find("a.save").hide();
				curP.find("a.cancel").hide();
				curP.find("a.edit").show();
				curP.find("textarea").val(commodityName).hide();
				curP.find("a.commodity-title").text(commodityName).show();
			}else{
				ygdg.dialog.alert(data.msg);
				curP.find("a.save").show();
				curP.find("a.cancel").show();
			}
		}
	});
}

function savePrice(curObj){
	var type = curObj.attr("type");
	var price = $.trim(curObj.parent().find("input").val());
	if(price==""){
		ygdg.dialog.alert("价格不能为空");
		return;
	}
	if(isNaN(price)){
		ygdg.dialog.alert("价格必须是数字");
		return;
	}
	if(parseFloat(price) <= 0.0){
		ygdg.dialog.alert("价格必须大于0");
		return;
	}
	
	var yougouPrice = 0,markPrice = 0;
	if(type=="1"){
		yougouPrice = price;
		markPrice = curObj.parent().parent().parent().find(".markprice").text();
	}else if(type=="2"){
		yougouPrice = curObj.parent().parent().parent().find(".yougouprice").text();
		markPrice = price;
	}
	if(parseFloat(yougouPrice)>parseFloat(markPrice)){
		ygdg.dialog.alert("优购价必须小于市场价");
		return;
	}
	var commodity_no = curObj.parent().parent().parent().find(".commodity-no").val();
	curObj.hide();
	curObj.parent().find(".cancel4price").hide();
	var load = curObj.parent().find(".loadimg");
	load.show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			commodityNo:commodity_no,
			price:price,
			type:type
		},
		url : basePath+"/commodity/updatePrice4OnSale.sc",
		success : function(data) {
			load.hide();
			if(data.resultCode=="200"){
				ygdg.dialog.alert("修改成功");
				$('tr[id="' + commodity_no + '-1"]').fadeOut(1000, function(){$(this).remove();});
				$('tr[id="' + commodity_no + '-2"]').fadeOut(1000, function(){$(this).remove();});
				$('tr[id="' + commodity_no + '-3"]').fadeOut(1000, function(){$(this).remove();});
			}else{
				curObj.css({display:"inline-block"});
				curObj.parent().find(".cancel4price").css({display:"inline-block"});
				ygdg.dialog.alert(data.msg);
			}
		}
	});
}
/**
 * 显示商品更新日志 点击事件
 * @param {String} commodityNo 商品编号
 */
qc.opt.showLog_OnClick = function(commodityNo) {
	ygdgDialog.open(qc.url.showCommodityLogUrl + commodityNo,{width:600,height:300,title:'商品修改日志'});
};

/**
 * 修改商品，先在后台下架再进入修改页面
 * @param {String} commodityNo 商品编号
 */
qc.opt.update_OnClick = function(commodityNo){
	ygdg.dialog.confirm("商品进行修改后，需先下架并重新提交审核，确定要修改吗？", function(){
		$("#tempForm").attr("action",basePath+"/commodity/preUpdateCommodity.sc");
		$("#tempForm input[name='commodityNo']").val(commodityNo);
		$("#tempForm").submit();
		$("#tempForm input[name='commodityNo']").val("");
		$("#tempForm").removeAttr("action");
	});
};
/**
 * 下架 点击事件
 * @param {String} commodityNo 商品编号
 */
qc.opt.down_OnClick = function(commodityNo) {
	ygdg.dialog.confirm("确定下架该商品吗? <br /><br /> <p><span style='color:#AAAAAA'>注：下架商品将返回到待提交审核商品中，修改后可重新提交审核。</span></p>", function() {
		$.ajax({
			type: 'post',
			url: basePath+'/commodity/downGoods.sc',
			dataType: 'html',
			data: 'rand=' + Math.random() + '&commodityNo=' + (commodityNo || ''),
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data) == 'SUCCESS') {
					$('tr[id="' + commodityNo + '-1"]').fadeOut(1000, function(){$(this).remove();});
					$('tr[id="' + commodityNo + '-2"]').fadeOut(1000, function(){$(this).remove();});
					$('tr[id="' + commodityNo + '-3"]').fadeOut(1000, function(){$(this).remove();});
				} else {
					// this.error(jqXHR, textStatus, data);
					ygdg.dialog.alert(data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('下架商品失败');
			}
		});
	});
};
//全选
$("#checkAll").click(function() {;
$("#common_proitm").find("input[name='onsale']").attr("checked", this.checked);
});
/**
 * 下架 点击事件（批量）
 * @param {String} commodityNo 商品编号
 */
qc.opt.downBatch_OnClick = function() {
var ids = $('input[name="onsale"]').filter(function(){
	return this.checked;
}).map(function(){
	return this.value;
}).get();

if (ids.length <= 0) {
	alert('请先选择需要下架的商品!');
	return false;
}
	ygdg.dialog.confirm("确定下架选择的商品吗? <br /><br /> <p><span style='color:#AAAAAA'>注：下架商品将返回到待提交审核商品中，修改后可重新提交审核。</span></p>", function() {
		$.ajax({
			type: 'post',
			url: '/commodity/downGoodsBatch.sc',
			dataType: 'html',
			data: 'rand=' + Math.random() + '&commodityNos=' + ids,
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data) == 'SUCCESS') {
					location.href = basePath+'/commodity/goQueryOnSaleCommodity.sc';
				} else {
					ygdg.dialog.alert(data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('下架商品失败');
			}
		});
	});
};

$("#minShowDate").calendar({maxDate:'#maxShowDate'});
$("#maxShowDate").calendar({minDate:'#minShowDate'});
//加载三级分类
setGoodsCattegory();
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
