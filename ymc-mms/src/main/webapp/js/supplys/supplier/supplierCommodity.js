$(document).ready(function(){
	$("#createDate").dateDisplay();
	$("#updateDate").dateDisplay();
	
});

//全选 /反选
function chooseAll(objs){
	$("#tbd tr input").each(function(i,obj){
		if(obj.type="checkbox"){
			obj.checked=objs.checked;
		}
	});
}
//批量删除
function delAll(){
	var str="";
	$("#tbd tr input").each(function(i,obj){
	
		if(obj.checked){
			str+=obj.value+" ";
		}
	});

	if(""==str){
		alert("请选择删除的商品……");
		return;
	}
	$("#hiddenid").val(str);
	$("#supplierCommodity").attr("action","d_Commodity.sc");
	$("#supplierCommodity").submit();
}
//批量审核
function batchAudit(){
	var str="";
	$("#tbd tr input").each(function(i,obj){
		
		if(obj.checked){
			str+=obj.value+" ";
		}
	});
	if(""==str){
		alert("请选择删除的商品……");
		return;
	}
	$("#hiddenid").val(str);
	$("#supplierCommodity").attr("action","u_batchAudit.sc");
	$("#supplierCommodity").submit();
}

//根据分类id 查询品牌
function changeBrand(obj){
	if(""==obj.value){
		$("#brandNo>option:gt(0)").remove();
	}
	$.post(path+"/supply/supplier/suppliercommotity/findBrandByCatId.sc",{val:obj.value},function(data){
		if(""==data||undefined==data){
			return;
		}
		var da=eval("("+data+")");
		$("#brandNo>option:gt(0)").remove();
		for(var i=0;i<da.length;i++){
			$('<option value='+da[i].id+'>'+da[i].brandName+'</option>').appendTo($("#brandNo"));
		}
	});
}
//保存品牌名称
function brandChange(){
	var brandName=$("#brandNo>option:selected").text();
	$("#brandNoIds").val(brandName);
}
//搜索提交
function seacher(){
	brandChange();
	$("#supplierCommodity").submit();
}

//自定义 改变，清空自定义文本框值
function clearText(obj){
	var selValue=$(obj).children("option:selected").val();
	if(""==selValue){
		$("#textValueId").val("");
		$("#textValueId").attr("readOnly",true);
		return;
	}
	$("#textValueId").attr("readOnly",false);
}
function clearAll(){
	
}
