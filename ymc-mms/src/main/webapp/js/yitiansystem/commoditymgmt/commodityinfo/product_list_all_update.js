$(document).ready(function() {
	var basePath = $("#basePath").val();
});

function clickURL(id){
	var referrer =  document.location.href;
	if(referrer.indexOf("&") != -1)
    	referrer = referrer.replace(eval("/&/g"),"@38@");
	
	 location.href = "toUpdateCommodity.sc?id="+id+"&parentSourcePage="+referrer;
}
	
/**
 * 列表修改 年份选中
 * @param selectYear	当前年份
 * @returns {String}
 */
function createYearSelect(selectYear){
	var yearHtml = "";
	var date = new Date();
	var currentYear = date.getFullYear();
	
	selectYear = (selectYear == null ) ? "" : selectYear;
	
	var yearCount = 30;
	
	for(var i = 0 ; i < yearCount ; i++){
		var value = currentYear - i + 1;
		if(value == selectYear){
			yearHtml += "<option value='"+value+"'  selected='selected'>"+value+"</option>";
		}else{
			yearHtml +=  "<option value='"+value+"'>"+value+"</option>";
		}
	}
	return yearHtml;
}

/**
 * 异步加载品牌 并选中当前品牌
 * @param selId	   		品牌下拉框 ID
 * @param structName	分类结构字符串
 * @param brandNo		品牌编号
 */
function ajaxBrand(selId,structName,brandNo) {
	$.ajax({
		type : "POST",
		url : "getChildBrand.sc",
		data : {
			"value" : structName
		},
		dataType : "json",
		success : function(data) {
			$("#" + selId).empty();// 清空下来框
			$("#" + selId).append("<option value='0'>品牌名称</option>");
			for ( var i = 0; i < data.length; i++) {
				var option = "";
				if(data[i].brandNo == brandNo){
					option = "<option selected='selected' value='" + data[i].brandNo + "'>"+ data[i].brandName + "</option>";
				}else{
					option = "<option value='" + data[i].brandNo + "'>"+ data[i].brandName + "</option>";
				}
				
				$("#" + selId).append(option);
			}
		}
	});
}
	
/**
 * 根据分类 异步加载相关数据(子分类 ，品牌，价格区间，属性)
 * @param commodityId		商品ID
 * @param catNo				分类编号
 * @param brandNo			品牌编号
 * @param allowPropData		已有的属性 结构如(propItemNo0,propValueNo0|propItemNo1,propValueNo1)
 */
function loadCommoditySelectCat(commodityId,catNo,brandNo,allowPropData){
	var nos = catNo.split("-");
	ajaxRequest("getChildCat.sc",{"value":nos[0]},function(data){// 根据一级分类 加载二级子分类
		data = eval("("+data+")");			//分类json字符串转json对象
		//加载二级分类
		$("#"+commodityId+"secondCategoryXXX").empty();// 清空下来框
		$("#"+commodityId+"secondCategoryXXX").append("<option value='0'>选择分类</option>");
		for ( var i = 0; i < data.length; i++) {
			var option = "";
			if(data[i].structName == (nos[0]+"-"+nos[1])){
				option = "<option selected='selected' value='" + data[i].structName+"'>" + data[i].catName + "</option>";
			}else{
				option = "<option value='" + data[i].structName+"'>" + data[i].catName + "</option>";
			}
			$("#"+commodityId+"secondCategoryXXX").append(option);
		}
		
		//加载三级分类
		ajaxRequest("getChildCat.sc",{"value":(nos[0]+"-"+nos[1])},function(data){
			data = eval("("+data+")");
			$("#"+commodityId+"threeCategoryXXX").empty();// 清空下来框
			$("#"+commodityId+"threeCategoryXXX").append("<option value='0'>选择分类</option>");
			for ( var i = 0; i < data.length; i++) {
				var option = "";
				if(data[i].structName == catNo){
					option = "<option selected='selected' value='" + data[i].structName+"'>" + data[i].catName + "</option>";
				}else{
					option = "<option value='" + data[i].structName+"'>" + data[i].catName + "</option>";
				}
				$("#"+commodityId+"threeCategoryXXX").append(option);
			}
			
			//加载品牌
			ajaxBrand(commodityId+"selBrand",catNo,brandNo);
			
			//根据分类编号 查询分类  获取分类ID
			ajaxRequest("getCatb2cByStruct.sc",{"struct":catNo},function(dtResult){
				dtResult = eval("("+dtResult+")");
				if(allowPropData != ""){
					allowPropData = formatProp2Array(allowPropData);  //将已有的属性转为js对象数组
				}else{
					allowPropData = null;
				}
				
				//加载商品分类属性
				ajaxExtendProp(dtResult.id,commodityId+"extendPropDiv",allowPropData);   
				
				//加载商品分类价格区间
				ajaxQueryPriceScope(dtResult.id,commodityId);			
			});
		});
	});
}
	
/**
 * 将已有的属性转为js对象数组  {propItemNo:'',propValueNo:''}
 * @param allowPropData   
 * @returns {Array}
 */
function formatProp2Array(allowPropData){
	var obj = new Array();
	var items = allowPropData.split("|");
	for(var i = 0 ; i < items.length ; i++ ){
		var item = items[i].split(",");
		var propObj = {};
		propObj.propItemNo = item[0];
		propObj.propValueNo = item[1];
		obj.push(propObj);
	}
	return obj;
}
	
	
/**
 * 异步查询分类下得价格区间
 * @param catid
 * @param commodityId
 */	
function ajaxQueryPriceScope(catid,commodityId) {
	ajaxRequest("ajaxQueryCatb2cPriceScope.sc",{"catid":catid},function(data){
		data = eval("("+data+")");
		data = data.items;
		var selId = commodityId+"catPrice";
		$("#" + selId).empty();
		$("#" + selId).append("<option value=''>请选择</option>");
		for ( var i = 0; i < data.length; i++) {
			var option = "";
			option = "<option value='" + data[i].scope + "'>"+ data[i].scope + "</option>";
			$("#" + selId).append(option);
		}
		//根据销售价格 选中价格区间
		changePriceMiddle(commodityId);
	});
}
	
	
/**
 * 异步查询商品下得货品
 * @param id			商品 ID 
 * @param supplierCode	供应商款色编码
 * @param styleNo		款号
 * @param clickObj		当前点击对象
 */
function editTempProduct(id,supplierCode,styleNo,clickObj){
	
	$("#aa").remove();
	var html="<tr id='aa'>";
	html+="<td colspan='50' style='border:3px solid #ccc;'>";
	html+="<div class='blank10'></div>";
	html+="<div style='float:right;'>";
	html+="<table style='border:1px solid #ccc;' width='800px' align='center' cellpadding='0' cellspacing='0' class='list_mod_table'>";
	html+="<tbody>";
	html+="<tr>";
	html+="<td colspan='50' style='text-align:left;padding-left:5px;'>";
	html+='<div id="productContent"></div>';
	html+="</td>";
	html+="</tr>";
	
	html+="<tr>";
	html+="<td colspan='50' style='text-align:right;padding-right:5px;'>";
	
	html+="<input  type='hidden' name='id' value='"+id+"' />";
	html+='<input type="button" id="cancelButton" class="yt-form-btn-back" onclick=removeTempCommodity("'+id+'")  value="删除" />';
	html+='<input type="button" id="cancelButton" class="yt-form-btn-back" value="关闭" onclick="removeTempTr();"/>';
	
	html+="</td>";
	html+="</tr>";
	html+="</tbody>";
	html+="</table>";
	html+="</div>";
	html+="<div class='blank10'></div>";
	html+="</td>";
	html+="</tr>";
	$(clickObj).parent().parent().after(html);
		
	ajaxRequest("ajaxQueryProductList.sc",{"commodityId":id},function(date){
		if(!date) return ;
		var productList = eval("("+date+")");
		
		var productHtml = "<table style='width:98%;'>";
		productHtml +="<tr>";
		productHtml +="<td>货品编码</td>";
		productHtml +="<td>供应商货品编码</td>";
		productHtml +="<td>货品条码</td>";
		productHtml +="<td colspan='20'></td>";
		productHtml +="</tr>";
		for(var i = 0 ; i < productList.length; i++){
			var tempProduct = productList[i];
			productHtml +="<tr>";
			
				productHtml +="<td>";
				productHtml +=tempProduct.productNo;
				productHtml +="</td>";
				productHtml +="<td>";
				productHtml +=tempProduct.thirdPartyCode;
				productHtml +="</td>";
				productHtml +="<td>";
				productHtml +=tempProduct.insideCode;
				productHtml +="</td>";
				
				var proSpecVoList = tempProduct.proSpecVoList;
				for(var j = 0 ; j < proSpecVoList.length; j++){
					productHtml +="<td>";
					productHtml +="<input type='hidden' value='"+proSpecVoList[j].id+"' />";
					productHtml +="<input type='hidden' value='"+proSpecVoList[j].propValueNo+"' />";
					productHtml +=proSpecVoList[j].propValue;
					productHtml +="</td>";
				}
			productHtml +="</tr>";
		}
		
		productHtml += "</table>";
		
		$("#productContent").append(productHtml);
	});
}

/**
 * 删除商品
 * @param id
 */
function removeTempCommodity(id){
	if(confirm('确认删除当前商品?')){
		ajaxRequest("removeTempCommodity.sc",{"commodityId":id},function(date){
			if(date == "success"){
				alert('删除成功!');
				document.location.reload();
			}else{
				alert('删除失败!');
			}
			return;
		});
	}
	
}

/**
 * 关闭货品查看层
 */
function removeTempTr(){
	$("#aa").remove();
}

/**
 * 根据一级分类加载二级分类
 * @param obj	当前一级分类下拉框
 * @param id	商品ID
 */
function roottCattegoryChange(obj,id){
	// 选中项的value值
	var selValue = $(obj).children('option:selected').val();
	var value = selValue.split(";");
	// 选中项的text值
	var selText = $(obj).children('option:selected').text();
	if (selValue != "0") {
		get(value[0], id+"secondCategoryXXX");
	} else {
		$("#"+id+"secondCategoryXXX").empty();// 清空下来框
		$("#"+id+"secondCategoryXXX").append("<option value='0'>选择分类</option>");
	}
	$("#"+id+"threeCategoryXXX").empty();// 清空下来框
	$("#"+id+"threeCategoryXXX").append("<option value='0'>选择分类</option>");
}

/**
 * 根据二级分类加载三级分类
 * @param obj	当前二级分类下拉框
 * @param id	商品ID
 */
function secondCategoryChange(obj,id){
	// 选中项的value值
	var selValue = $(obj).children('option:selected').val();
	var value = selValue.split(";");
	// 选中项的text值
	var selText = $(obj).children('option:selected').text();
	if (selValue != "0") {
		get(value[0], id+"threeCategoryXXX");
	} else {
		$("#"+id+"threeCategoryXXX").empty();// 清空下来框
		$("#"+id+"threeCategoryXXX").append("<option value='0'>选择分类</option>");
	}
}

/**
 * 根据三级分类加载 商品属性 价格区间
 * @param obj  三级分类对象
 * @param id	商品ID
 */
function catb2cSelectChange(obj,id){
	if($(obj).val() != "0"){
		ajaxBrand(id+"selBrand",$(obj).val());
		$("#"+id+"extendPropDiv").empty();
		
		ajaxRequest("getCatb2cByStruct.sc",{"struct":$(obj).val()},function(dtResult){
			dtResult = eval("("+dtResult+")");
			ajaxExtendProp(dtResult.id,id+"extendPropDiv");
			
			//加载商品分类价格区间
			ajaxQueryPriceScope(dtResult.id,id);			
		});
	}
}

/**
 * 商品列表修改 异步提交表单
 * @param formId		//表单ID
 * @returns {Boolean}
 */
function formAjaxSubmit(formId){
	if(!confirm('确认保存当前商品?')){
		return false;
	}
	$.ajax({
		url: 'u_updateListTempProduct.sc',
		data: $('#'+formId).serialize(),
		type: "POST",
		beforeSend: function(){    //提交前验证数据
			var emptyReg = /^\S+$/;
			var submitForm = $('#'+formId)[0];
			
			var styleNo = submitForm["styleNo"].value;   //款号
			if(styleNo == null || !emptyReg.test(styleNo)){
				alert("商品款号不能为空或空字符!");
				$(submitForm["styleNo"]).focus();
				return false;
			}
			
			
			var supplierCode = submitForm["supplierCode"].value;   //供应商款色编码
			if(supplierCode == null || !emptyReg.test(supplierCode) ){
				alert("供应商款色编码不能为空或空字符!");
				$(submitForm["supplierCode"]).focus();
				return false;
			}
					 
			var num4=/^([+]?)\d*\.?\d+$/;
			var costPrice = submitForm["costPrice"].value;   //成本价
			if(costPrice == null || !num4.test(costPrice) ){
				alert("成本价必须是非负数字!");
				$(submitForm["costPrice"]).focus();
				return false;
			}
			
			var publicPrice = submitForm["publicPrice"].value;   //市场价
			if(publicPrice == null || !num4.test(publicPrice) ){
				alert("市场价必须是非负数字!");
				$(submitForm["publicPrice"]).focus();
				return false;
			}
			
			var salePrice = submitForm["salePrice"].value;   //销售价
			if(salePrice == null || !num4.test(salePrice) ){
				alert("销售价必须是非负数字!");
				$(submitForm["salePrice"]).focus();
				return false;
			}
			
			
			var catNo = $(submitForm["catNo"]).get(0).selectedIndex;
			if(catNo == 0){
				alert('请选择分类');
				$(submitForm["catNo"]).focus();
				return false;
			}
			
			var brandNo = $(submitForm["brandNo"]).get(0).selectedIndex;
			if(brandNo == 0){
				alert('请选择品牌');
				$(submitForm["brandNo"]).focus();
				return false;
			}
			
			var priceScpe = $(submitForm["priceScpe"]).get(0).selectedIndex;
			if(priceScpe == 0){
				alert('请选择价格区间');
				$(submitForm["priceScpe"]).focus();
				return false;
			}
		},
		success: function(date){
			if(date == "success"){
				alert('修改成功!');
				$("#"+formId+"Tr td").css("background","#fbe2e2");
// 					document.location.reload();
			}else if(date == "suppliercodefail"){
				alert("供应商款色编码已存在!");
				$($('#'+formId)[0]["supplierCode"]).focus();
				return false;
			}else{
				alert("修改失败!");
			}
		}
	});
}
	

/**
 * 批量审核的ajax
 * @param selValue
 * @param selId
 * @return
 */
function batchAudit(id,state) {
	
	if(confirm("确认该选中商品?")){
		$.ajax( {type : "POST",
			url : "batchAudit.sc",
			data : {"value" : id,"state" : state},
			success : function(data) {
	  			if(data = "success"){
		  			alert("操作成功!");
		  			document.location.reload();
	  			}else{
	  				alert("操作失败!");
	  			}
	  		}
		});
	}
}
	  
	  
	  
/**
 * 自动选择价格区间(列表修改时用)
 * @param commodityId	商品ID
 * @returns {Boolean}
 */
  function changePriceMiddle(commodityId){
		var options = $("#"+commodityId+"catPrice option");   		
		var salePrice = $("#"+commodityId+"salePrice").val();
		if(salePrice != "" && options.length <= 1){
			 alert('请选择分类!');
			 $("#salePrice").attr("value","");
			 return false;
		}else if(salePrice == ""){
			$("#"+commodityId+"catPrice option:first").attr("selected", true);
		}else{
			//验证通过 
			salePrice = Number($.trim(salePrice));
			
			if(salePrice <= 0) return false;
			
			//是否在最小价格段
			var firstPrice = $.trim($(options[1]).val().replace(/[\u4E00-\u9FA5]/g, ""));
			if(firstPrice != "" && firstPrice.split("-").length == 1  && salePrice <= Number(firstPrice)){
				$(options[1]).attr("selected", true);
				return true;
			}
			
			
			var lastPrice = $.trim($("#"+commodityId+"catPrice option:last").val().replace(/[\u4E00-\u9FA5]/g, ""));
			if(lastPrice != "" &&  lastPrice.split("-").length == 1  && salePrice >=  Number(lastPrice)){
				$("#"+commodityId+"catPrice option:last").attr("selected", true);
				return true;
			}
			
			
			for(var i= 0 ; i < options.length ; i++){
				var option = options[i];
				var priceValue = $(option).val();
				priceValue = priceValue.replace(/[\u4E00-\u9FA5]/g, "");
				var priceM = priceValue.split("-");
				if(priceM.length != 2) continue;
				var minprice = Number($.trim(priceM[0]));
				var maxprice = Number($.trim(priceM[1]));
				
				if(salePrice >=minprice && salePrice <= maxprice ){
					$(option).attr("selected", true);
					return;
				}
			}
				
				
		}
		
	}