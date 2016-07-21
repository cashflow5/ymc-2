$(function(){
	taobaoYougouItemCatProTemplet.initData();
});

taobaoYougouItemCatProTemplet.initData = function(){
	$("#subForm").html("正在初始化属性......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			bindId:taobaoYougouItemCatProTemplet.bindId,
			catId:taobaoYougouItemCatProTemplet.catId,
			taobaoCid:taobaoYougouItemCatProTemplet.taobaoCid
		},
		url : taobaoYougouItemCatProTemplet.basePath+ "/taobao/getYougouTaobaoProTemplet.sc",
		success : function(data) {
			$("#subForm").empty();
			if(data.resultCode == "200"){
				$("#nextBtn").show();
				var poroList=  data.yougouPropList;
				var taobaoProList = data.taobaoProList;
				if(taobaoProList.length==0){
					$("#tab_content").html("此分类下，没有淘宝属性");
					return;
				}
				
				//初始化颜色分类
				//linitColorTableHead(data.taobaoColorValues);
				//初始化属性属性值列表
				var table = linitTableHead();
				for(var i=0,length=poroList.length;i<length;i++){
					new ProRow(poroList[i],taobaoProList).tr.appendTo(table);
				}
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
}


function linitColorTableHead(taobaoColorValues){
	var table = $("<table width='100%' class='list_table'></table>").appendTo($("#subForm"));
	var thead = $("<thead></thead>").appendTo(table);
	$("<tr><th width='30%' class='line'>优购属性</th><th width='30%'>淘宝属性值</th><th width='30%' class='liner'>&nbsp;</th></tr>").appendTo(thead);
	var tr = $("<tr class='line'></tr>").appendTo(table);
	var td = $("<td style='font-weight:bold'>颜色</td>").appendTo(tr);
	var taobaoTd = $("<td></td>").appendTo(tr);
	var blanTd = $("<td><input type='hidden' name='colorTaobaoVname' id='colorTaobaoVname'/></td>").appendTo(tr);
	var select = $("<select name='colorTaobaoVid'></select>").appendTo(taobaoTd);
	$("<option value=''>==请选择==</option>").appendTo(select);
	var taobaoVal;
	var pid;
	for(var i=0,len=taobaoColorValues.length;i<len;i++){
		taobaoVal = taobaoColorValues[i];
		if(taobaoVal.propValBindId!=""){
			$("<option selected='selected' value='"+taobaoVal.vid+"'>"+taobaoVal.vName+"</option>").appendTo(select);
			$("<input type='hidden' name='colorBandId'value='"+taobaoVal.propValBindId+"'/>").appendTo(blanTd);
		}else{
			$("<option value='"+taobaoVal.vid+"'>"+taobaoVal.vName+"</option>").appendTo(select);
		}
		pid = taobaoVal.pid;
	}
	$("<input type='hidden' name='colorToobaoPid' value='"+pid+"'/>").appendTo(blanTd);
}


function linitTableHead(){
	var table = $("<table width='100%' class='list_table'></table>").appendTo($("#subForm"));
	var thead = $("<thead></thead>").appendTo(table);
	var tr = $("<tr><th width='30%' class='line'>优购属性</th><th width='30%'>淘宝属性</th><th width='30%' class='liner'>展开属性值</th></tr>").appendTo(thead);
	return table;
}

function ProRow(data,taobaoProList){
	this.tr = $("<tr class='line'></tr>");
	var tdcols = $("<td colspan='3'></td>").appendTo(this.tr);
	var table = $("<table width='100%'></table>").appendTo(tdcols);
	var proTr = $("<tr></tr>").appendTo(table);
	var mustClass = "";
	if(data.isShowMall==0){//显示是否是必填属性
		$("<td width='30%'>" +
				"<span  style='color:red'>*</span><span class='yougouPropItemName' style='font-weight:bold;padding-left:5px;'>"+data.propItemName+"<span>" +
				"<input type='hidden' name='yougouPropItemNo' value='"+data.propItemNo+"'>" +
		"</td>").appendTo(proTr);
		mustClass = "mustInput";
	}else{
		$("<td width='30%'>" +
				"<span class='yougouPropItemName'>"+data.propItemName+"<span>" +
				"<input type='hidden' name='yougouPropItemNo' value='"+data.propItemNo+"'>" +
		"</td>").appendTo(proTr);
	}
	/*$("<td width='30%'>" +
			"<span class='yougouPropItemName'>"+data.propItemName+"<span>" +
			"<input type='hidden' name='yougouPropItemNo' value='"+data.propItemNo+"'>" +
	"</td>").appendTo(proTr);*/
	var td2 = $("<td width='30%'></td>").appendTo(proTr);
	var taobaoRro;
	var taobaoPName = "";
	var taobaoPId = "";
	var taobaoProValues = "";//淘宝属性值
	for(var i=0,length=taobaoProList.length;i<length;i++){
		taobaoRro = taobaoProList[i];
		if(taobaoPName!=""&&taobaoPName!="-"){
			break;
		}
		//判断该属性是否已经绑定了属性
		if(taobaoRro.yougouPropItemNo==data.propItemNo){
			taobaoPName = taobaoRro.taobaoPName;
			taobaoPId = taobaoRro.taobaoPId;
			taobaoProValues = taobaoRro.valueList;
		}else{
			taobaoPName= "-";
		}
	}
	$("<span>"+taobaoPName+"</span>").appendTo(td2);
	
	var td3 = $("<td width='30%'></td>").appendTo(proTr);
	if(taobaoPName==""||taobaoPName=="-"){
		$("<span style='color:#BACBD7'>未设置属性对应关系</span>").appendTo(td3);
		return;
	}
	var opLink = $("<a href='javascript:void(0)' class='pro-close' status='close'>展开</a>").bind("click",{
		taobaoPId:taobaoPId
	},this.openOrClose).appendTo(td3);
	var proValTr = $("<tr style='display:none;bakcground:#ffffff'></tr>").appendTo(table);
	var proValColsTd = $("<td colspan='3'></td>").appendTo(proValTr);
	var proValTable = $("<table width='100%' class='propValueTable' yougouproname='"+data.propItemName+"'></table>").appendTo(proValColsTd);
	var proValList = data.propValues;
	var proTheadTr
	if(proValList.length>=2){
		$("<tr><th width='25%'>优购属性值</th><th width='25%'>淘宝属性值</th><th width='25%'>优购属性值</th><th width='25%'>淘宝属性值</th></tr>").appendTo(proValTable);
	}else{
		$("<tr><th width='50%'>优购属性值</th><th width='50%'>淘宝属性值</th></tr>").appendTo(proValTable);
	}
	
	var proBodyTr;
	for(var s=0,length=proValList.length;s<length;s++){
		var proVal = proValList[s];
		if(s%2==0){
			proBodyTr = $("<tr></tr>").appendTo(proValTable);
		}
		var proBodyTd = $("<td>"+proVal.propValueName+"<input type='hidden' name='yougouProValueNo' value='"+proVal.propValueNo+"'></td>").appendTo(proBodyTr);
		var proBodyTd2 = $("<td></td>").appendTo(proBodyTr);
		
		var taobaoProValueName = "-";
		if(taobaoProValues!=""){
			for(var v=0,_len=taobaoProValues.length;v<_len;v++){
				if(taobaoProValueName!="-"){
					break;
				}
				if(taobaoProValues[v].yougouPropValueNo==proVal.propValueNo){
					taobaoProValueName = taobaoProValues[v].taobaoVName;
				}
			}
		}
		
		$("<span>"+taobaoProValueName+"</span>").appendTo(proBodyTd2);
	}
	var endTr = $("<tr></tr>").appendTo(proValTable);
	var endTd = $("<td colspan='4' style='text-align:center'></td>").appendTo(endTr);
	var cancelBtn  =$("<a href='javascript:void(0)' class='closeIcon'><img src='"+taobaoYougouItemCatProTemplet.basePath+"/yougou/images/taobao_pro_close.png'></a>").appendTo(endTd).bind("click",this.cancel);
}
ProRow.prototype =  {
		openOrClose : function(event) {
			if($(this).attr("status")=="close"){//当前是关闭
				var taobaoPId = event.data.taobaoPId;
				//loadProVals(taobaoPId,$(this));
				showProValueTr($(this));
				$(this).attr("class","pro-open");
				$(this).text("收起");
				$(this).attr("status","open"); //改变当前状态为展开
			}else if($(this).attr("status")=="open"){
				closeProValueTr($(this));
				$(this).attr("class","pro-close");
				$(this).text("展开");
				$(this).attr("status","close"); //改变当前状态为展开
			}
		},
		cancel:function(){
			var aLink = $(this).parent().parent().parent().parent().parent().parent().parent().children().eq(0).find("a");
			closeProValueTr(aLink);
			aLink.attr("class","pro-close");
			aLink.text("展开");
			aLink.attr("status","close"); //改变当前状态为展开
		},
		savePro:function(event){
			var pTbody = $(this).parent().parent().parent().parent().parent().parent().parent();
			var proTr = pTbody.children().eq(0);
			var valTable = pTbody.children().eq(1).find("table");
		}
}

/**
 * 加载属性值
 */
function loadProVals(taobaoPId,aLink){
	var subTable = aLink.parent().parent().parent().find(".propValueTable");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			taobaoPid:taobaoPId
		},
		url : taobaoYougouItemCatProTemplet.basePath+ "/taobao/getYougouTaobaoProValueTemplet.sc",
		success : function(data) {
			if(data.resultCode == "200"){
				var taobaoProValueList=  data.taobaoProValueList;
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
}

function showProValueTr(curObj){
	curObj.parent().parent().parent().children().eq(1).show();
}

function closeProValueTr(curObj){
	curObj.parent().parent().parent().children().eq(1).hide();
}

/**
 * 保存
 */
taobaoYougouItemCatProTemplet.save = function(){
	//校验优购必填属性对应的淘宝属性是否为空
	var errorList = [];
	var youtouMustProps = $("table.list_table").eq(0).find(".mustInput");
	//必填属性不过滤
/*	for(var i=0,length=youtouMustProps.length;i<length;i++){
		var select = youtouMustProps.eq(i);
		if(select.val()==""){
			var yougouMustPropName = select.parent().parent().find(".yougouPropItemName").eq(0).text();
			//ygdg.dialog.alert("优购属性【<span style='color:red'>"+yougouMustPropName+"</span>】对应的淘宝属性不能为空");
			errorList.push("优购属性【<span style='color:red'>"+yougouMustPropName+"</span>】对应的淘宝属性不能为空");
		}
	}*/
	
	//校验淘宝属性是否有重复
	var taobaoPropSelects = $("table.list_table").eq(0).find(".taobaoPorpItemNo");
	var repeatSelects = getRepeat(taobaoPropSelects);
	var msgStr = "";
	for(var i=0,length=repeatSelects.length;i<length;i++){
		if(repeatSelects[i]!=""&&repeatSelects[i]!=null){
			msgStr =msgStr+"【<span style='color:red'>"+taobaoPropSelects.eq(0).find("option[value='"+repeatSelects[i]+"']").text()+"</span>】";
		}
	}
	if(msgStr!=""){
		//ygdg.dialog.alert("淘宝属性:"+msgStr+"重复");
		//return;
		errorList.push("淘宝属性:"+msgStr+"重复");
	}
	
	//校验属性值是否有重复
	var propValueTables = $("table.list_table").find(".propValueTable");
	for(var i=0,length =propValueTables.length;i<length;i++){
		var propValueTable = propValueTables.eq(i);
		var yougouPropName = propValueTable.attr("yougouproname");
		var propValueSelects = propValueTable.find("select");
		var repeatSelects = getRepeat(propValueSelects);
		var propValMsg = "";
		for(var j=0,_length=repeatSelects.length;j<_length;j++){
			if(repeatSelects[j]!=""&&repeatSelects[j]!=null){
				propValMsg =propValMsg+"【<span style='color:red'>"+propValueSelects.eq(0).find("option[value='"+repeatSelects[j]+"']").text()+"</span>】";
			}
		}
		if(propValMsg!=""){
			//ygdg.dialog.alert("优购属性【<span style='color:red'>"+yougouPropName+"</span>】对应的淘宝属性值:"+propValMsg+"重复");
			errorList.push("优购属性【<span style='color:red'>"+yougouPropName+"</span>】对应的淘宝属性值"+propValMsg+"重复");
			//return;
		}
	}
	
	if(errorList.length>0){
		var table=$("<table width='100%'></table>");
		for(var i=0,len =errorList.length;i<len;i++ ){
			error = errorList[i];
			$("<tr><td style='padding:2px'><img src='"+taobaoYougouItemCatProTemplet.basePath+"/yougou/images/error.gif' class='goods_error_image'>"+error+"</td></tr>").appendTo(table);
		}
		ygdgDialog.alert(table.html(),null,false);
		return;
	}
	
	//设置淘宝属性名称到隐藏域
	$("#colorTaobaoVname").val($("select[name='colorTaobaoVid']").find("option:selected").text());
	mms_common.loading("show","正在保存......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$('#subForm').serialize(),
		url : taobaoYougouItemCatProTemplet.basePath+ "/yitiansystem/taobao/bindTaobaoYougouItemProAndVal.sc?catId="+taobaoYougouItemCatProTemplet.catId+"&taobaoCid="+taobaoYougouItemCatProTemplet.taobaoCid+"&catBandId="+taobaoYougouItemCatProTemplet.bindId,
		success : function(data) {
			mms_common.loading();
			if(data.resultCode == "200"){
				document.location.href=taobaoYougouItemCatProTemplet.basePath+ "/yitiansystem/taobao/bindTaobaoYougouItemProAndValSuccess.sc";
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
	});
}


function getRepeat(tempary) {
	var ary = [];
	for (var i = 0; i < tempary.length; i++) {
		ary.push(tempary.eq(i).val());
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

function hiddenMsgBox(){
	$("#msgdiv").hide();
}

