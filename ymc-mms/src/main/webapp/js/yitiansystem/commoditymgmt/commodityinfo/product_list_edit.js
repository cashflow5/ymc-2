//商品 ID   供应商款色编码
function editTempProduct(id,supplierCode,styleNo,clickObj){
	
		
		ajaxRequest("ajaxQueryCatList.sc",{},function(result){
			if(!result) return ;
			var catList = eval("("+result+")");
// 					event.stopPropagation();
			$("#aa").remove();
			var html="<tr id='aa'>"
			html+="<td colspan='50' style='border:3px solid #ccc;'>";
			html+="<div class='blank10'></div>";
			html+="<form action='' method='post' id='editSimgleProductForm'>";
			html+="<table width='100%' align='center' cellpadding='0' cellspacing='0' class='list_mod_table'>";
			html+="<tbody>";
			html+="<tr>";
			html+="<td style='text-align:left;padding-left:5px;'>";
			html+="商品分类 &nbsp;<font class='ft-cl-r'>*</font>&nbsp;: ";
			html+='<select name="toplev"  id="rootCattegoryXXX" onchange="roottCattegoryChange(this)">';
			html+='		<option value="0" selected="selected">选择分类</option>';
			for(var i = 0 ; i < catList.length; i++){
				var tempObj = catList[i];
				var value = tempObj.structName+";"+tempObj.id;
				html+='<option value="'+value+'">'+tempObj.catName+'</option>';
			}
			html+='</select>&nbsp;&nbsp;';
			html+='<select name="seclev"  id="secondCategoryXXX" onchange="secondCategoryChange(this)">';
			html+='		<option value="0" selected="selected">选择分类</option>';
			html+='</select>&nbsp;&nbsp;';
			html+='<select name="commodityCat" id="threeCategoryXXX" onchange="catb2cSelectChange(this)">';
			html+='		<option value="0" selected="selected">选择分类</option>';
			html+='</select>&nbsp;&nbsp;';
			html+="</td>";
			
			html+="<td style='text-align:left;padding-left:5px;width:230px;'>";
			html+="供应商款色编码 : ";
			html+=supplierCode;
			html+="</td>";
			
			html+="<td style='text-align:left;padding-left:5px;width:230px;'>";
			html+="商品款号 : ";
			html+=styleNo;
			html+="</td>";
			
			html+="<td style='text-align:left;padding-left:5px;width:230px;'>";
			html+="商品类型 &nbsp;<font class='ft-cl-r'>*</font>&nbsp; : ";
			html+='<input type="radio" name="commodityType" value="1" checked="checked" />&nbsp;&nbsp;商品&nbsp;';
			html+='<input type="radio" name="commodityType" value="2" />&nbsp;&nbsp;赠品&nbsp;';
			html+=' <input type="radio" name="commodityType" value="3" />&nbsp;&nbsp;配件&nbsp;';
			html+="</td>";
			
			html+="</tr>";

			html+="<tr>";
			
			html+="<td style='text-align:left;padding-left:5px;'>";
			html+="商品品牌 &nbsp;<font class='ft-cl-r'>*</font>&nbsp;: ";
			html+='<select name="toplev" id="selBrand" onChange="brandChange();">';
			html+='		<option value="0" selected="selected">品牌名称</option>';
			html+='</select>&nbsp;';
			html+='<input type="hidden" name="brandId" id="brandId">';
			html+='<input type="hidden" name="brandName" id="brandName" value="">';
			html+="</td>";
			
			
			html+="<td style='text-align:left;padding-left:5px;'>";
			html+="市场价  &nbsp;<font class='ft-cl-r'>*</font>&nbsp;: ";
			html+='<input name="marketPrice" type="text" id="marketPrice" size="25" maxLength="12" />';
			html+='<span id="marketPriceTip"></span>';
			html+="</td>";
			
			html+="<td style='text-align:left;padding-left:5px;'>";
			html+="销售价 &nbsp;<font class='ft-cl-r'>*</font>&nbsp;: ";
			html+='<input name="salePrice" type="text" id="salePrice" size="25"  maxLength="12" onblur="changePriceMiddle(true);" />';
			html+='<span id="salePriceTip"></span>';
			html+="</td>";
			
			html+="<td style='text-align:left;padding-left:5px;'>";
			html+="成本价 &nbsp;<font class='ft-cl-r'>*</font>&nbsp;: ";
			html+='<input name="costPrice" type="text" id="costPrice" size="25"  maxLength="12"  />';
			html+='<span id="costPriceTip"></span>';
			html+="</td>";
		
			html+="</tr>";
			
			html+="<tr>";

			
			html+="<td style='text-align:left;padding-left:5px;'>";
			html+="价格区间 &nbsp;<font class='ft-cl-r'>*</font>&nbsp; : ";
			html+='<select name="priceScpe" id="catPrice">';
			html+='		<option  selected="selected" value="-1">价格区间</option>';
			html+='</select>&nbsp;';
			html+="</td>";
			
			html+="</tr>";
			
			html+="<tr>";
			html+="<td colspan='50' style='text-align:left;padding-left:5px;'>";
			html+=' <ul id="extendPropDiv"  class="ytweb-form"></ul>';
			html+="</td>";
			html+="</tr>";
			
			html+="<tr>";
			html+="<td colspan='50' style='text-align:left;padding-left:5px;'>";
			html+='<div id="productContent"></div>';
			html+="</td>";
			html+="</tr>";
			
			html+="<tr>";
			html+="<td colspan='50' style='text-align:left;padding-left:5px;'>";
			
			
			html+="<input  type='hidden' name='id' value='"+id+"' />";
			html+="<input type='submit' id='submitButton' class='yt-form-btn-add'  value='保存' />";
			html+='<input type="button" id="cancelButton" class="yt-form-btn-back" onclick=removeTempCommodity("'+id+'")  value="删除" />';
			html+='<input type="button" id="cancelButton" class="yt-form-btn-back" value="关闭" onclick="removeTempTr();"/>';
			
			html+="</td>";
			html+="</tr>";
			html+="</tbody>";
			html+="</table>";
			html+="</form>";
			html+="<div class='blank10'></div>";
			html+="</td>";
			html+="</tr>";
			
			$(clickObj).parent().parent().after(html);
// 					$(document).click(function(event){$("#aa").remove();}); 

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
	
	
	
			$('#editSimgleProductForm').submit(function() {
				$.ajax({
					url: 'u_updateTempProduct.sc',
					data: $('#editSimgleProductForm').serialize(),
					type: "POST",
					beforeSend: function(){  
						
						var catb2cIndex = $("#threeCategoryXXX").get(0).selectedIndex;
						if(catb2cIndex == 0){
							alert('请选择三级分类');
							return false;
						}
						
						var selBrandIndex = $("#selBrand").get(0).selectedIndex;
						if(selBrandIndex == 0){
							alert('请选择品牌');
							return false;
						}
						
						var catPriceIndex = $("#catPrice").get(0).selectedIndex;
						if(catPriceIndex == 0){
							alert('请选择价格区间');
							return false;
						}
						
						var numReg = /^([+]?)\d*\.?\d+$/;
						var marketPrice = $("#marketPrice").val();
						if(marketPrice == null || !numReg.test(marketPrice)){
							alert('市场价格必须为非负数字');
							return false;
						}
						
						var salePrice = $("#salePrice").val();
						if(salePrice == null || !numReg.test(salePrice)){
							alert('销售价格必须为非负数字');
							return false;
						}
						
						var costPrice = $("#costPrice").val();
						if(costPrice == null || !numReg.test(costPrice)){
							alert('成本价格必须为非负数字');
							return false;
						}
						
						$('#submitButton').hide();
						$('#cancelButton').hide();
						
					},
					success: function(date){
						if(date == "success"){
							alert('修改成功!');
							document.location.reload();
						}else{
							alert("修改失败!");
						}
						$('#submitButton').show();
						$('#cancelButton').show();
					}
				});
				return false;
			});
			
		});
}


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

function removeTempTr(){
	$("#aa").remove();
}



function roottCattegoryChange(obj){
	// 选中项的value值
	var selValue = $(obj).children('option:selected').val();
	var value = selValue.split(";");
	// 选中项的text值
	var selText = $(obj).children('option:selected').text();
	if (selValue != "0") {
		getCatStr(value[0], "secondCategoryXXX");
	} else {
		$("#secondCategoryXXX").empty();// 清空下来框
		$("#secondCategoryXXX").append("<option value='0'>选择分类</option>");
	}
	$("#threeCategoryXXX").empty();// 清空下来框
	$("#threeCategoryXXX").append("<option value='0'>选择分类</option>");
	$("#extendPropDiv").empty();
}

function secondCategoryChange(obj){
	// 选中项的value值
	var selValue = $(obj).children('option:selected').val();
	var value = selValue.split(";");
	// 选中项的text值
	var selText = $(obj).children('option:selected').text();
	if (selValue != "0") {
		getCatStr(value[0], "threeCategoryXXX");
	} else {
		$("#threeCategoryXXX").empty();// 清空下来框
		$("#threeCategoryXXX").append("<option value='0'>选择分类</option>");
	}
	$("#extendPropDiv").empty();
	
}

function catb2cSelectChange(){
	if($("#threeCategoryXXX").val() != "0"){
		$("#extendPropDiv").empty();				
		addNewCatXXX();
	}
}

function addNewCatXXX() {
	// 三级分类选中的值和文本
	var threeVal = $('#threeCategoryXXX').children('option:selected').val();
	
	var value = threeVal.split(";");
	// 加载品牌
	ajaxBrand("selBrand",value[0]);
	//加载分类价格区间
	ajaxCatPriceScope(value[1]);
	// 加载扩展属性
	ajaxExtendProp(value[1]);
}

function ajaxBrand(selId,structName) {
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
				var option = "<option value='" + data[i].id + "'>"
						+ data[i].brandName + "</option>";
				$("#" + selId).append(option);
			}
		}
	});
}

//异步请求分类价格区间
 function ajaxCatPriceScope(selId) {
	$.ajax({
		type : "POST",
		url : "getCatPriceScope.sc",
		data : {
			"value" : selId
		},
		dataType : "json",
		success : function(data) {
			$("#catPrice").empty();// 清空下来框
			$("#catPrice").append("<option>价格区间</option>");
			for ( var i = 0; i < data.length; i++) {
				var option = "<option value='" + data[i] + "'>"
						+ data[i]+ "</option>";
				$("#catPrice").append(option);
			}
		}
	});
 }

//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  success: callback
	});
}

function getCatStr(selValue, selId) {
	var value = selValue;
	$.ajax({
		type : "POST",
		url : "getChildCat.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			$("#" + selId).empty();// 清空下来框
			$("#" + selId).append("<option value='0'>选择分类</option>");
			for ( var i = 0; i < data.length; i++) {
				var option = "<option value='" + data[i].structName + ";"
						+ data[i].id + "'>" + data[i].catName + "</option>";
				$("#" + selId).append(option);
			}
		}
	});
}