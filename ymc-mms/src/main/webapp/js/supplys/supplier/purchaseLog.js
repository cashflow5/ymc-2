//加载序号
//$(document).ready(function(){
//	//sequence();
//	
//});

//删除行的集合
var delTrs=[];
//生成行号
function sequence(){
	$("#tbd tr").each(function(i,obj){
		$(obj).find("td:eq(0)").html(i+1);
	});
}

function addRow(_a){
	//获得第一行
//	var trs=$("#tbd>tr:eq(0)").clone();
//	trs.find("td>input:eq(0)").val("");
//	trs.find("td>input:eq(1)").val("");
//	trs.find("td>input:eq(2)").val("");
//	trs.find("td>input:eq(3)").val("");
//	$(_a).parent().parent().after(trs);
	addNewRow($(_a).parent().parent());
	sequence();
}

function delRow(_a){
	var tr=$(_a).parent().parent();
	
	var flag=$(tr).find("td>input:eq(4)").val();
//	if(-1!=flag){
//		
//		//保存不是新增的 已经删除的行
//		delTrs.push($(_a).parent().parent());
//	}
	//删除行
	$(_a).parent().parent().remove();
	
	//从数据库中删除记录
	if(-1!=flag){
		delLaseRow(flag);
	}
	var legnth=$("#tbd tr");
	
	if(legnth.size()==0){
		var str='';
			str+='<tr class="div-pl-list even" id="dd">';
			str+='<td style="text-align:center;" colspan="6" ondblclick="dblAddRow(this)">';
			str+='<font style="color:red;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据,双击新增记录？</font>';
			str+='</td>';
			str+='</tr>';
			$(str).appendTo($("#tbd"));
			
			
			return;
	}
	sequence();
}
/**
 * 删除最后一行
 * @return
 */
function delLaseRow(id){
	var purchaseId=$("#purchaseId").val();
	var url=path+"/supply/supplier/PerchaseOrder/d_PurchaseLog.sc";
	$.post(url,{val:id},function(data){
		var da=eval("("+data+")");
		if("1"!=da.flag){
			alert("最后一条记录删除失败");
			window.location=path+"/supply/supplier/PerchaseOrder/queryPurchaseLog.sc?purchaseId="+purchaseId;
		}
	});
}
/**
 * 新建一行
 * @param _obj
 * @return
 */
function dblAddRow(_obj){
	//alert($(_obj).parent().get(0).id);
	addNewRow(_obj);
    $(_obj).parent().remove();
    sequence();
}
 //新增一行
 var index=0;
 var trcls="odd"
 function addNewRow(_obj){
	  if(parseInt(index)%2==0) trcls="even"
	  else trcls="odd"
	  index+=1;
	 var trs=$("<tr class="+trcls+"></tr>").appendTo($(_obj).parent().parent());
		$('<td></td>').appendTo(trs);
		$('<td><input type="text" value="" class="inputtxt" /></td>').appendTo(trs);				   
	    $('<td><input type="text" value="" class="inputtxt" /></td>').appendTo(trs);
	    $('<td><input type="text" value="" class="inputtxt" /></td>').appendTo(trs);
	    $('<td><input type="text" value="" class="inputtxt" /></td>').appendTo(trs);
	    $('<td><input type="hidden" value="-1" /> <a href="#" onclick="addRow(this)">添加</a>&nbsp;|&nbsp;<a href="#" onclick="delRow(this)">删除</a></td>').appendTo(trs);
 }
 
 function validate(obj){
	 if(""==obj||undefined==obj){
		 return true;
	 }else{
		 return false;
	 }
 }
/**
 * 获取所有值
 * @return
 */
function save(){
	var str="";
	var purchaseId=$("#purchaseId").val();
	//清除空行
	var boo=false;
	$("#tbd tr").each(function(i,obj){
		var consiqnment=$(obj).find("td>input:eq(0)").val();
		var storage=$(obj).find("td>input:eq(1)").val();
		var measure=$(obj).find("td>input:eq(2)").val();
		var upsupport=$(obj).find("td>input:eq(3)").val();
		if(validate(consiqnment)&&validate(consiqnment)&&validate(consiqnment)&&validate(consiqnment)){
			boo=true;
		}
	});
	if(boo){
		alert("请填写更新信息！");
		return;
	}
	$("#tbd tr").each(function(i,obj){
		var consiqnment=$(obj).find("td>input:eq(0)").val();
		var storage=$(obj).find("td>input:eq(1)").val();
		var measure=$(obj).find("td>input:eq(2)").val();
		var upsupport=$(obj).find("td>input:eq(3)").val();
		str+=",{";
		str+="purchase:";
		str+="{id:'"+purchaseId+"'}";
		str+=",consiqnment:'"+consiqnment+"'";
		str+=",storage:'"+storage+"'";
		str+=",measure:'"+measure+"'";
		str+=",upsupport:'"+upsupport+"'";
		str+=",deleteFlag:1";
		str+="}";
	});
//	//保存删除的数据
//	for(var i=0;i<delTrs.length;i++){
//		var trs=delTrs[i];
//		var consiqnment=$(trs).find("td>input:eq(0)").val();
//		var storage=$(trs).find("td>input:eq(1)").val();
//		var measure=$(trs).find("td>input:eq(2)").val();
//		var upsupport=$(trs).find("td>input:eq(3)").val();
//		str+=",{";
//		str+="purchase:";
//		str+="{id:'"+purchaseId+"'}";
//		str+=",consiqnment:'"+consiqnment+"'";
//		str+=",storage:'"+storage+"'";
//		str+=",measure:'"+measure+"'";
//		str+=",upsupport:'"+upsupport+"'";
//		str+=",deleteFlag:0";
//		str+="}";
//	}
	if(""==str){
		return;
	}
	str="["+str.substring(1)+"]";
	var url=path+"/supply/supplier/PerchaseOrder/c_addPurchaseLog.sc";
	$.post(url,{val:str},function(data){
		var da=eval("("+data+")");
		if("1"==da.flag){
			window.location=path+"/supply/supplier/PerchaseOrder/queryPurchaseLog.sc?purchaseId="+purchaseId;
		}else{
			alert("更新失败");
		}
	});
}