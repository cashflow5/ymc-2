/*定义 一个集合,用于子窗体传值*/
var commodityList=[];

/*打开窗体的方法*/
function getChooseCommoditys(){
	javascript:art.dialog.openwindow(
		basePath+'/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/toCommodityList.sc',
		'','','yes',
		{id:'open',title: '选择商品'});
}

var basePath="";
/*设置一个空行对象*/
var newRow=null;

/*加载表格的方法,同时还要异步请求添加到类别里面*/
function addCommodityToTable(){
	if(newRow==null){
		$("#hidTR").show();
		var newRow = $("#hidTR").clone(true);
	}
	$("#commoditys_tbl>tbody").empty();
	var ids = "";
	for(var i = 0;i<commodityList.length;i++){
		newRow_ = newRow.clone(true);
		newRow_.find("td:eq(0)").html(commodityList[i].commodityId.html());
		newRow_.find("td:eq(1)").html(commodityList[i].commodityName.html());
		newRow_.find("td:eq(2)").html("");
		newRow_.find("td:eq(3)").html("添加所属分类");
		newRow_.find("td:eq(4)").html(commodityList[i].prodDesc.html());
		ids=commodityList[i].commodityId.find("input:eq(0)").val()+";"+ids;
		$("#commoditys_tbl>tbody").append(newRow_);
	}
	//选择商品后直接进行向数据库添加数据-----异步添加
	var data_={'commodityIds':ids,'shopCommodityTypeId':'','shopId':$("#shopId").val()};
	$.ajax({
		url:basePath+'/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/c_addCheckedCommodityToType.sc',
		data:data_,
		type:'POST',
		dataType:'text',
		success:function(d){
			//alert(d);
			$("#addToType").val("0");
		}
	});
}

/*删除当前所属的类别*/
function fn_deleteThisType(current,shopId,typeId){
	var obj = $(current).parent();
	var url=basePath+"/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/u_removeShopCommodityByType.sc?typeId=";
	var commodityId = $(current).parent().parent().parent().parent().find("td:eq(0)>input[type=checkbox]").val();
	var data = {commodityIds:commodityId,shopCommodityTypeId:typeId,shopId:shopId};
	
	//异步删除所属的类别
	$.ajax({url:url,
		data:data,
		dataType:'text',
		type:'post',
		success:function(d){
			var result = eval("("+d+")");
			if(result.reg=="1"){
				//alert("删除成功");
				//显示删除当前对象
				obj.remove();
			}
		}
	});
}

/*定义类别数组,用来存储所有的类别*/
var types = [];
//添加到类别的得支焦点事件
//当得到焦点时给当前的下拉框赋值..
function fn_addToType_focus(current,shopId,commodityId){
	var index = current.parentNode.parentNode.parentNode.rowIndex;

	var typeSource = $("#addToType_hid>option");
	if(types.length<=0){
		for(var i = 1;i<(typeSource.size());i++){
			//alert($(typeSource[i]).val());
			var type = {
					typeId:getIdAndStructNameAndIsLeaf($(typeSource[i]).val(),'id'),
					typeName:getIdAndStructNameAndIsLeaf($(typeSource[i]).val(),'typeName'),
					structName:getIdAndStructNameAndIsLeaf($(typeSource[i]).val(),'struct'),
					isLeaf:getIdAndStructNameAndIsLeaf($(typeSource[i]).val(),'isLeaf')
				};
			//alert(type.typeId+"****"+type.typeName);
			types.push(type);
		}
	}
	var alreadyHaveTypes = $(current).parent().parent().prev().find("li");
	//console.dir(alreadyHaveTypes);
	$(current).empty();
	
	$(current).append("<option value='0'>添加所属分类</option>");
	//循环所有的类别
	// alert(JSON.stringify(types));
	for(var i = 0;i<types.length;i++){
		var obj = "<option value='"+types[i].typeId+"'>∟"+types[i].typeName+"</option>";
		//alert(types[i].isLeaf+"========="+types[i].typeName);
		if(types[i].structName.split("-").length==2 && types[i].isLeaf=="0"){
			obj = "<optgroup style='color:gray;' label='"+types[i].typeName+"'></option>";
		}else if (types[i].structName.split("-").length==2&&types[i].isLeaf=="1"){
			obj = "<option value='"+types[i].typeId+"'>"+types[i].typeName+"</option>";
			alreadyHaveTypes.each(function(o,objs){
				if($(objs).find("input:eq(0)").val()==types[i].typeId){
					//禁用已经存在的所属类别
					//alert(types[i].structName);
					obj = "<optgroup  style='color:gray;' label='"+types[i].typeName+"'></option>";
				}
			});
		}else{
			//循环所有已经属于的类别 
			
			alreadyHaveTypes.each(function(o,objs){
				if($(objs).find("input:eq(0)").val()==types[i].typeId){
					//禁用已经存在的所属类别
					//alert(types[i].structName);
					obj = "<optgroup  style='color:gray;' label='∟"+types[i].typeName+"'></option>";
				}
			});
		}
		$(current).append(obj);
	}
}

/**
 * 获得截取后的ID或StructName
 * @param idAndStruct
 * @param option
 * @return
 */
function getIdAndStructNameAndIsLeaf(id_struct_isLeaf,option){
	 var obj_ =  id_struct_isLeaf.split("`");
	 
	if(option=="id"){
		return obj_[0];
	}else if(option=="struct"){
		return obj_[1];
	}else if(option=="isLeaf"){
		return obj_[2];
	}else if(option=="typeName"){
		return obj_[3];
	}
}


/**
 * 添加到分类
 * @param current
 * @param shopId
 * @param commodityId
 * @return
 */
function fn_addToType_change(current,shopId,commodityId){
	//var typeId = this.value;//获得类别编号
	var typeId = current.value;
	if(typeId=="0"){
		return;
	}
	var ids="";
	ids=commodityId+";"+ids;
	$("#commodityIds").val(ids);
	$("#shopCommodityTypeId").val(typeId);
	$("#shopCommodityFrm").attr("action",basePath+"/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/c_addCommodityToType.sc");
	$("#shopCommodityFrm").submit();
}

/*查询店内商品的方法----改变事件*/
function fn_searchCommodityByType(current,shopId){
	var typeId = current.value;
	$("#typeId").val(typeId);
	$("#shopId").val(shopId);
	
	$("#shopGoodsFrm").submit();//
}



/*查询的点击事件*/
function fn_searchShopCommodity(shopId){
	//$("#searchContent").val($("#searchContents").val());
	
	//$("#shopGoodsFrm").submit();//
}

$(document).ready(function(){
	basePath = $("#basepath").val();
	$("#rootCattegory").change(function(){
		//选中项的value值
		var selValue = $(this).children('option:selected').val() ;

		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if(selValue !="0"){
			get(selValue,"secondCategory");
		}else{
			$("#secondCategory").empty();// 清空下来框
			$("#secondCategory").append("<option value='0'>请选择二级分类</option>");
		}
		$("#threeCategory").empty();// 清空下来框
		$("#threeCategory").append("<option value='0'>请选择三级分类</option>");
	})
	
	$("#secondCategory").change(function(){
		//选中项的value值
		var selValue = $(this).children('option:selected').val() ;
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if(selValue !="0"){
			get(selValue,"threeCategory");
		}else{
			$("#threeCategory").empty();// 清空下来框
			$("#threeCategory").append("<option value='0'>请选择三级分类</option>");
		}
	})
});

function get(selValue,selId){
	var value=selValue ;
	$.ajax({
		type: "POST",
		url: basePath+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
		data: {"value":value},
		dataType:"json", 
		success: function(data){
			$("#"+selId).empty();// 清空下来框
			if("threeCategory" == selId){
				$("#"+selId).append("<option value='0'>请选择三级分类</option>");
			}else{
				$("#"+selId).append("<option value='0'>请选择二级分类</option>");
			}
			for(var i=0; i<data.length ; i ++){
				var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
				$("#"+selId).append(option);
			}
		}
	});
}