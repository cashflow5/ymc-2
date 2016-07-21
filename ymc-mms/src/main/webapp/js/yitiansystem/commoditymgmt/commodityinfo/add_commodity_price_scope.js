
/**
* 获得类别ID
*/
function getCatId(idAndStruct){
	return idAndStruct.substring(0,idAndStruct.indexOf(";"));
}

/**
* 获得类别结构字符串
*/
function getCatStruct(idAndStruct){
	return idAndStruct.substring(idAndStruct.indexOf(";")+1);
}

/**
 * 默认显示....以上的字样
 */
var defaultPriceValue = "---";
var basePath;

/**
* 加载事件
*/
$(document).ready(function(){
	
	basePath = $("#basePath").val();
	
	// 注册只能输入数字....
	$(":input:[type='text']:not(:input:[name='remark'])").keydown(function(event){
		if(event.keyCode>=48&&event.keyCode<=57||event.keyCode==8||event.keyCode==9||event.keyCode==46||event.keyCode>=37&&event.keyCode<=40||event.keyCode>=96&&event.keyCode<=105){
			return true;
		}
		return false;
	});
	
	if(newRow == null){
		$("#priceScopes>tbody>tr:eq(0) input").val("");
		newRow = $("#priceScopes>tbody>tr:eq(0)").clone(true);
	}
	$("#priceScopes>tbody>tr").remove();
	

	$("#cats").change(function(){
		if(this.value=="0"){
			$("#priceScopes>tbody>tr").remove();
			$(".yt-tb-list-no").show();
			return;
		}
		var catId = getCatId(this.value);
		var catStruct = getCatStruct(this.value);
		/*
		alert(getCatId(this.value));
		alert(getCatStruct(this.value));
		*/
		
		var url = basePath + "/yitiansystem/commoditymgmt/commodityinfo/pricescope/findByCatStruct.sc";
		var data = {'structName':catStruct};
		// alert(data);
		$.ajax({
			url:url,
			data:data,
			dataType:'json',
			type:'POST',
			success:function(d){
				// alert(d);
				loadPriceScope(d);
			}
		});
	});
});

/**
 * 当点击右上角按钮时添加行
 * 添加到最后一行
 * @return
 */
function addToLastRow(){
	if($("#cats").val()=="0"){
		alert("请选择类别进行操作");
		return;
	}
	if($("#cats>option").size()<=1){
		alert("没有找到类别,请先添加类别!");
		return;	
	}
	var createedNewRow = newRow.clone(true);
	$("#priceScopes>tbody").append(createedNewRow);
	$("#addRow_div").hide();
}

/**
 * 显示或隐藏添加按钮的方法
 * @return
 */
function showOrHidBtnAction(){
	$("#addRow_div").show();
	$("#showOrHidBtn").show();
}

var newRow = null;
/**
* 加载表格数据
*/
function loadPriceScope(list){
	if(newRow == null){
		newRow = $("#priceScopes>tbody>tr:eq(0)").clone(true);
	}
	$("#priceScopes>tbody>tr").remove();
	if(list.length<=0){
		$("#addRow_div").show();
		// $("#showOrHidBtn").show();
		return;
	}
	
	for(var i = 0; i < list.length; i ++){
		var newRow_ = newRow.clone(true);
		// newRow_.find("td:eq(0)>span:eq(0)").text(list[i].catName);
		newRow_.find("td:eq(0)>input:[type='hidden']:eq(0)").val(list[i].id);
		newRow_.find("td:eq(1)>input:eq(0)").val(list[i].minPrice);
		
		newRow_.find("td:eq(2)>input:eq(0)").val((list[i].maxPrice=="0"||list[i].maxPrice==null||list[i].maxPrice=="0"||list[i].maxPrice=="")?defaultPriceValue:list[i].maxPrice);
		newRow_.find("td:eq(3)>input:eq(0)").val(list[i].remark);
		$("#priceScopes>tbody").append(newRow_);
	}
	$("#addRow_div").hide();
}

function createNewRow(){
	var createedNewRow = newRow.clone(true);
	// createedNewRow.find("td:eq(0)>span:eq(0)").text($("#cats>option:selected").text());
	$("#priceScopes>tbody").append(createedNewRow);
	$("#addRow_div").hide();
}

/**
* 增加行 
*/
function fn_addRows(current){
	var index = current.parentNode.parentNode.rowIndex;
	var addRow = newRow.clone(true);
	// addRow.find("td:eq(0)>span:eq(0)").text($("#cats>option:selected").text());
	$("#priceScopes>tbody>tr:eq("+(index-1)+")").after(addRow);
}

/**
* 删除行 
*/
function fn_delRows(current){
	var index = current.parentNode.parentNode.rowIndex;
	var currentId = $.trim($(current).parent().parent().find("td:eq(0)>input:eq(0):[type='hidden']").val());
	//alert(currentId);
	if(currentId!=""){
		if(confirm("真的要删除该项吗?")){
			// alert("删除成功!");
			var url = basePath+"/yitiansystem/commoditymgmt/commodityinfo/pricescope/d_deletePriceScope.sc";
			var data = {priceScopeId:currentId};
			
			$.ajax({
				url:url,
				data:data,
				dataType:'JSON',
				type:'POST',
				success:function(d){
					var o = eval("("+d+")");
					if(o.result=="1"){
						alert("删除成功!");
						$("#priceScopes>tbody>tr:eq("+(index-1)+")").remove();
						var rowSize = $("#priceScopes>tbody>tr").size();
						
						if(rowSize<=0){
							$(".yt-tb-list-no").show();
							// $(".yt-tb-list-no").find("span").remove();
							// $(".yt-tb-list-no").append("<span><a href='javascript:void(0);' onclick='createNewRow()' >新增</a></span>");
						}
					}
				}
			});
			return;
		}
		return ;
	}
	
	$("#priceScopes>tbody>tr:eq("+(index-1)+")").remove();
	var rowSize = $("#priceScopes>tbody>tr").size();
	
	if(rowSize<=0){
		$("#addRow_div").show();
	}
}

/**
 * 验证非空
 * @return
 */
function registNotNull(){
	//alert(10);
	var result = false;
	
	$(":input:[type='text']:not(:input:[name='remark'])").each(function(){
		if($(this).val()==""){
			//alert(i);
			$(this).addClass('store-input-err');
			result = true;
		}else{
			$(this).removeClass('store-input-err');
		}
	});	
	return result;
}

/**
 * 保存前的验证
 * 验证价格段不能重叠
 * 
 * @return
 */
function saveDataValidate(){
	var trs = $("#priceScopes>tbody");
	//var lastResult = true;
	for(var i = 0; i < trs.find("tr").size(); i ++){
		for(var j = 0; j < trs.find("tr").size(); j++){
			for(var a = 1; a < 3; a++){
				var price_ = trs.find("tr:eq("+i+")>td:eq("+a+")>input:eq(0)").val();
				if(i!=j){
					var otherMin = trs.find("tr:eq("+j+")>td:eq(1)>input:eq(0)").val();
					var otherMax = trs.find("tr:eq("+j+")>td:eq(2)>input:eq(0)").val();
					if(otherMax==defaultPriceValue){
						otherMax = 9999999;
					}
					var result = checkPriceInOtherPrice(parseInt(price_),parseInt(otherMin),parseInt(otherMax)); //trs.find("tr:eq("+i+")>td").size()
					if(!result){
						alert("价格段不能有重叠，请检查!");
						return false;
						// alert("当前"+price_+"跟"+otherMin+","+otherMax+"比较,比较结果是:"+result);
					}
				}
			}
		}
	}
	return true;
}

/**
 * 检测一个价格是否在其它两价格之间
 * @param current
 * @param otherMin
 * @param otherMax
 * @return 如果重叠:返回false,如果不重叠:返回true;
 */
function checkPriceInOtherPrice(current,otherMin,otherMax){
	var result = true;
	
	if(current>=otherMin&&current<=otherMax){
		result = false; // 说明current这个价格在otherMin和otherMax这个范围内
	}
	
	return result;
}
 
/**
 * 保存前验证是不是数字
 * @return  true:表示存在非数字
 */
function saveBeforeValidateNull(){
	var result = false;
	// 验证是不是数字....
	$(":input:[type='text']:not(:[name='remark'])").each(function(){
		var registIsNumber = /^[0-9]\d*$/.test(this.value);
		if(this.value != defaultPriceValue ){
			if(!registIsNumber){
				result = true;
				$(this).addClass('store-input-err');
			}else{
				$(this).removeClass('store-input-err');
			}
		}
	});
	return result;
}

/**
 * 保存前验证价格在某个价格以上的操作
 * @return  true:表示存在多个以上的价格字样
 */
function saveBeforeValidateMaxPriceAbove(){
	var trs = $("#priceScopes>tbody");
	
	var abovePriceStyle = $(":input:[type='text']:[value='"+defaultPriceValue+"']:not(:[name='remark'])"); 
	
	if(abovePriceStyle.size()>1){
		abovePriceStyle.each(function(){
			$(this).addClass('store-input-err');
		});
		return true;// 表示存在多个以上的价格字样
	}else{
		abovePriceStyle.each(function(){
			$(this).removeClass('store-input-err');
		});
		return false;
	}
}

/**
 * 保存前验证价格段
 * 最高价格不能低于最低价格
 * @return  返回true 表示存在 有价格不规范的---有价格最大值小于等于最小值的数据
 */
function saveBeforValidateMinPriceAndMaxPrice(){
 	var trs = $("#priceScopes>tbody");
 	
 	var result = false;
 	for(var i = 0; i < trs.find("tr").length; i ++){
 		var minPrice = trs.find("tr:eq("+i+")>td:eq(1)>input:eq(0)");
 		var maxPrice = trs.find("tr:eq("+i+")>td:eq(2)>input:eq(0)");
 		if(maxPrice.val()==defaultPriceValue){
 			continue;
 		}
 		if(parseInt(minPrice.val())>=parseInt(maxPrice.val())){
 			result = true;
 			minPrice.addClass("store-input-err");
 			maxPrice.addClass("store-input-err");
 		}else{
 			minPrice.removeClass("store-input-err");
 			maxPrice.removeClass("store-input-err");
 		}
 	}
 	return result;
}

/**
 * 保存数据
 */
function savePriceScopes(){
	if($("#cats").val()=="0"){
		alert("请选择类别再进行操作!");
		$("#cats").focus();
		return ;
	}
	
	var trs = $("#priceScopes>tbody");
	if(trs.find("td").size()<=0){
		alert("请添加价格段!");
		return;
	}
	
	var catName = $("#cats>option:selected").text();
	var catId = getCatId($("#cats").val());
	var catStructName = getCatStruct($("#cats").val());
	var priceScopes = [];
	// 验证非空
	if(registNotNull()){
		return;
	}
	
	// 验证是否是数字
	if(saveBeforeValidateNull()){
		return;
	}
	if(saveBeforeValidateMaxPriceAbove()){
		return;
	}
	// 输入的价格段不规范的数据 
	if(saveBeforValidateMinPriceAndMaxPrice()){
		alert("最低价格不能大于或等于最高价格!");
		return;
	}
	
	// 检测价格段重叠问题
	if(!saveDataValidate()){
		return;
	}
	
	for(var i = 0; i < trs.find("tr").length; i ++){
		var id = trs.find("tr:eq("+i+")>td:eq(0)>input:[type='hidden']:eq(0)").val();
		var minPrice =  trs.find("tr:eq("+i+")>td:eq(1)>input:eq(0)").val();
		var maxPrice =  trs.find("tr:eq("+i+")>td:eq(2)>input:eq(0)").val();
		var priceScope = minPrice+"-"+maxPrice+"元";
		if(minPrice==0){
			priceScope = maxPrice+"元以下";
		}
		var scopeNo = catStructName+"-"+minPrice+"to"+maxPrice;
		var catb2c = {id:catId};
		var deleteFlag = 1;
		var remark = trs.find("tr:eq("+i+")>td:eq(3)>input:eq(0)").val();
		if(maxPrice==defaultPriceValue){
			maxPrice=null;
			priceScope =minPrice + "元以上";
		}
		var priceScope = {
			id:id,
			catName:catName,
			catStructName:catStructName,
			minPrice:minPrice,
			maxPrice:maxPrice,
			priceScope:priceScope,
			scopeNo:scopeNo,
			catb2c:catb2c,
			deleteFlag:deleteFlag,
			remark:remark
		};
		priceScopes.push(priceScope);
	}
	
	//alert(JSON.stringify(priceScopes));
	var url = basePath+"/yitiansystem/commoditymgmt/commodityinfo/pricescope/c_savePriceScope.sc";
	var data = {obj:JSON.stringify(priceScopes)};
	//  异步请求保存数据 
	// alert("保存成功");
	//  测试,先把保存功能注释....
	
	$.ajax({
		url:url,
		data:data,
		dataType:'JSON',
		type:'POST',
		success:function(d){
			//alert(d);
			var o = eval("("+d+")");
			//alert(o.result);
			if(o.result==1){
				alert("保存成功!");
				// fn_toEditPriceScope(catId,catName,catStructName);
				saveSuccessRefresh(catStructName);
			}
		}
	});	
}

/**
 * 保存成功后刷新本分类下的价格段
 * @param catStruct
 * @return
 */
function saveSuccessRefresh(catStruct){
	var url = basePath + "/yitiansystem/commoditymgmt/commodityinfo/pricescope/findByCatStruct.sc";
	var data = {'structName':catStruct};
	//alert(data);
	$.ajax({
		url:url,
		data:data,
		dataType:'json',
		type:'POST',
		success:function(d){
			//alert(d);
			loadPriceScope(d);
		}
	});
}

/**
 * 返回的方法
 * @return
 */
function backToListPage(){
	window.location=basePath+"/yitiansystem/commoditymgmt/commodityinfo/pricescope/toPriceScopeList.sc";
}

/**
 * 最低价格的离开事件
 * onblur给最高价格默认添加 defaultPriceValue
 * 表示该价格段以上
 * @param current
 * @return
 */
function setMaxPriceStyle(current){
	var index = current.parentNode.parentNode.rowIndex;
	var trs = $("#priceScopes>tbody");
	if(trs.find("tr:eq("+(index-1)+")>td:eq(2)>input:eq(0)").val()==""){
		trs.find("tr:eq("+(index-1)+")>td:eq(2)>input:eq(0)").val(defaultPriceValue)
	}
}

/**
 * 得到焦点时清除文本框的内容,使用户输入 
 * onfocus
 * @param current
 * @return
 */
function setAndCheck(current){
	var index = current.parentNode.parentNode.rowIndex;
	if(current.value==""||current.value==defaultPriceValue){
		current.value="";
	}
	current.select();
}

/**
 * 失去焦点时如果为空则给它赋上一个 defaultPriceValue表示该价格以上
 * onblur
 * @param current
 * @return
 */
function setAndCheckStyle(current){
	var index = current.parentNode.parentNode.rowIndex;
	if(current.value==""||current.value==defaultPriceValue){
		current.value=defaultPriceValue;
	}
}

/**
* 同时添加/移除样式 
* @param obj1
* @param obj2
* @param action
* @return
*/
function sameTimeAddOrRemoveClass(obj1,obj2,action){
	if(action=="1"){
		obj1.addClass('store-input-err');
		obj2.addClass('store-input-err');
	}else if(action="0"){
		obj1.removeClass('store-input-err');
		obj2.removeClass('store-input-err');
	}
}

/**
* 最低价格的Change事件
* 与最高价格比较
* @param current
* @return
*/
function compareMaxPrice(current){
	var index = current.parentNode.parentNode.rowIndex;
	var currentMinPrice = current.value;
	var trs = $("#priceScopes>tbody");
	var currentMaxPrice = trs.find("tr:eq("+(index-1)+")>td:eq(2)>input:eq(0)");
	
	if($.trim(currentMaxPrice.val())==""){
		return true;
	}
	
	var isNumbers = /^[1-9]\d*$/.test(current.value);
	if(!isNumbers){
		$(current).addClass('store-input-err');
		return false;
	}else{
		$(current).removeClass('store-input-err');
	}
	
	if(parseInt(currentMinPrice) > parseInt(currentMaxPrice.val())){
		alert("最低价格不能大于最高价格!");
		sameTimeAddOrRemoveClass($(current),currentMaxPrice,'1');
		return false;
	}else if(parseInt(currentMinPrice) == parseInt(currentMaxPrice.val())){
		alert("最低价格不能等于最高价格!");
		sameTimeAddOrRemoveClass($(current),currentMaxPrice,'1');
		return false;
	}else{
		sameTimeAddOrRemoveClass($(current),currentMaxPrice,'0');
		return true;
	}
}

/**
 * 最高价格的Change事件
 * 与最低价格比较
 * @param current
 * @return
 */
function compareMinPrice(current){
	var index = current.parentNode.parentNode.rowIndex;
	var currentMaxPrice = current.value;
	var trs = $("#priceScopes>tbody");
	var currentMinPrice = trs.find("tr:eq("+(index-1)+")>td:eq(1)>input:eq(0)");
	
	if($.trim(currentMaxPrice)==""){
		return true;
	}
	var isNumbers = /^[1-9]\d*$/.test(current.value);
	if(!isNumbers){
		$(current).addClass('store-input-err');
		return false;
	}else{
		$(current).removeClass('store-input-err');
	}
	
	if(parseInt(currentMinPrice.val()) > parseInt(currentMaxPrice)){
		alert("最低价格不能大于最高价格!");
		sameTimeAddOrRemoveClass($(current),currentMinPrice,'1');
		return false;
	}else if(parseInt(currentMinPrice.val()) == parseInt(currentMaxPrice)){
		alert("最低价格不能等于最高价格!");
		sameTimeAddOrRemoveClass($(current),currentMinPrice,'1');
		return false;
	}else{
		sameTimeAddOrRemoveClass($(current),currentMinPrice,'0');
		return true;
	}
}

/**
 * 跳转到编辑页面
 */
function fn_toEditPriceScope(catId,catName,catStructName){
   	$("#catId").val(catId);
   	$("#catName").val(catName);
   	$("#catStructName").val(catStructName);
   	$("#priceScopeFrm").submit();
}


/**
 * 跳转到编辑页面
 */
function fn_toEditPriceScopeByEntry(catId,catName,catStructName,id){
   	$("#catId").val(catId);
   	$("#catName").val(catName);
   	$("#catStructName").val(catStructName);
   	$("#priceScopeId").val(id);
   	$("#priceScopeFrm").submit();
}
