var curNum = 0;
$(function(){
	$("a.removeLine").live("click",function(){
		$(this).parent().parent().remove();
	});
	
	$("a.addYougouBrand").live("click",function(){
		curNum = $(this).parent().parent().attr("num");
		openwindow(taobaoYougouBrandBind.basePath+'/yitiansystem/taobao/goYougouBrand.sc',500,400,'选择优购品牌',1);
	});

	$("a.addTaobaoBrand").live("click",function(){
		curNum = $(this).parent().parent().attr("num");
		openwindow(taobaoYougouBrandBind.basePath+'/yitiansystem/taobao/goTaobaoBrand.sc',500,400,'选择淘宝品牌',1);
	});

	$("a.removeYougouBrand").live("click",function(){
		var divBrand = $(this).parent();
		divBrand.find("input[name='yougouBrandName']").val("");
		divBrand.find("input[name='yougouBrandNo']").val("");
		divBrand.hide();
		$(this).parent().parent().find(".addBtn").show();
	});
	$("a.removeTaobaoBrand").live("click",function(){
		var divBrand = $(this).parent();
		divBrand.find("input[name='taobaoBrandName']").val("");
		divBrand.find("input[name='taobaoBrandNo']").val("");
		divBrand.hide();
		$(this).parent().parent().find(".addBtn").show();
	});
});
taobaoYougouBrandBind.addLine = function() {
	var trs = $(".form-table tr");
	if(trs.length>10){
		ygdg.dialog.alert("一次最多只能添加10行");
		return;
	}
	var tr = $("<tr num="+(trs.length)+"></tr>").appendTo(".form-table");
	$("<td><div class='brand'><input type='text' readonly='true' name='yougouBrandName'><input type='hidden' name='yougouBrandNo'><a href='javascript: void(0)' class='removeYougouBrand'>" +
			"<i></i></a></div><a href='javascript: void(0)' class='addBtn addYougouBrand'>添加优购品牌</a></td>").appendTo(tr);
	$("<td><div class='brand'><input type='text' readonly='true' name='taobaoBrandName'><input type='hidden' readonly='true' name='taobaoBrandNo'><a href='javascript: void(0)' class='removeTaobaoBrand'>" +
	"<i></i></a></div><a href='javascript: void(0)' class='addBtn addTaobaoBrand'>添加优购品牌</a></td>").appendTo(tr);
	$("<td class='op'><a href='javascript:void(0)' title='删除行' class='removeLine'><i></i></a></td>").appendTo(tr);
};

function setYougouBrand(brandNo,brandName){
	var tr = $(".form-table tbody tr").eq(parseInt(curNum));
	var divBrand = tr.find("td").eq(0).find(".brand");
	divBrand.find("input[name='yougouBrandName']").val(brandName);
	divBrand.find("input[name='yougouBrandNo']").val(brandNo);
	divBrand.show();
	tr.find("td").eq(0).find(".addBtn").hide();
}

function setTaobaoBrand(brandNo,brandName){
	var tr = $(".form-table tbody tr").eq(parseInt(curNum));
	var divBrand = tr.find("td").eq(1).find(".brand");
	divBrand.find("input[name='taobaoBrandName']").val(brandName);
	divBrand.find("input[name='taobaoBrandNo']").val(brandNo);
	divBrand.show();
	tr.find("td").eq(1).find(".addBtn").hide();
}
taobaoYougouBrandBind.saveBind = function(){
	var trs = $(".form-table tbody tr");
	var yougouBrandNames = $(".form-table tbody tr td input[name='yougouBrandName']");
	var taobaoBrandNames = $(".form-table tbody tr td input[name='taobaoBrandName']");
	var yougouBrandNameArray = [];
	var taobaoBrandNameArray = [];
	var _len = trs.length;
	var yougouBrandName;
	var taobaoBrandName;
	for(var i=0;i<_len-1;i++){
		yougouBrandName = $.trim(yougouBrandNames.eq(i).val());
		taobaoBrandName = $.trim(taobaoBrandNames.eq(i).val());
		if(yougouBrandName==""){
			ygdg.dialog.alert("第"+(i+1)+"行优购品牌不能为空");
			return;
		}
		if(taobaoBrandName==""){
			ygdg.dialog.alert("第"+(i+1)+"行淘宝品牌不能为空");
			return;
		}
		yougouBrandNameArray.push(yougouBrandName);
		taobaoBrandNameArray.push(taobaoBrandName);
	}
	//判断重复
	var repeatYougouBrandNames = getRepeat(yougouBrandNameArray);
	if(repeatYougouBrandNames.length>0){
		var msg = "";
		for(var i=0,_len=repeatYougouBrandNames.length;i<_len;i++){
			msg = msg+"【"+repeatYougouBrandNames[i]+"】";
		}
		if(msg!=""){
			ygdg.dialog.alert("优购品牌"+msg+"重复");
			return;
		}
	}
	var repeatTaobaoBrandNames = getRepeat(taobaoBrandNameArray);
	if(repeatTaobaoBrandNames.length>0){
		var msg = "";
		for(var i=0,_len=repeatTaobaoBrandNames.length;i<_len;i++){
			msg = msg+"【"+repeatTaobaoBrandNames[i]+"】";
		}
		if(msg!=""){
			ygdg.dialog.alert("淘宝品牌"+msg+"重复");
			return;
		}
	}
	mms_common.loading("show","正在保存......");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$('#bindForm').serialize(),
		url : taobaoYougouBrandBind.basePath+ "/yitiansystem/taobao/bindBrand.sc",
		success : function(data) {
			mms_common.loading();
			if(data.resultCode=="200"){
				document.location.href=taobaoYougouBrandBind.basePath+ "/yitiansystem/taobao/goYougouTaobaoBrand.sc";
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
};

function getRepeat(tempary) {
	var ary = [];
	for (var i = 0; i < tempary.length; i++) {
		ary.push(tempary[i]);
	}
	var res = [];
	ary.sort();
	for (var i = 0; i < ary.length;) {
		var count = 0;
		for (var j = i; j < ary.length; j++) {
			if (ary[i] == ary[j]) {
				count++;
			}
		}
		if (count > 1) {
			res.push(ary[i]);
		}
		i += count;
	};
	return res;
};

