
//判断是否已经存在分类了
function checkCategory(thirdCategoriesId){
	var thirdCategoriesIdVar = document.getElementsByName("thirdCategoriesId"+thirdCategoriesId);;
	if( thirdCategoriesIdVar.length >0){
	    return false;
	}
	return true;
}


//删除分类
function deleteCategory(thirdCategoriesId){
	$('#category_'+thirdCategoriesId).remove();
	var categroryTR = $("#categorylist tr").length;
	if(categroryTR == 0){
		$("#categorylistDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
}

//增加分类
function showCategory(categoriesName,secondCategoriesName,thirdCategoriesName,secondCategoriesId,thirdCategoriesId){
	if(checkCategory(thirdCategoriesId)){
		var htmls ='<tr id="category_'+thirdCategoriesId+'" class="odd even">'
		        +"<td ><input type='checkbox' name='category' value='"+thirdCategoriesId+"'></td>"
		        + '<td>'+categoriesName+'->'+secondCategoriesName+'->'+thirdCategoriesName+'<input type="hidden" id="categoryName'+thirdCategoriesId+'" name="categoryName" value="'+categoriesName+'->'+secondCategoriesName+'->'+thirdCategoriesName+'"/>'
			    +'<input type="hidden" id="thirdCategoriesId'+thirdCategoriesId+'" name="thirdCategoriesId" value="'+thirdCategoriesId+'"/></td><td><a class="del delrow" href="javascript:deleteCategory(\''+thirdCategoriesId+'\');"></a></td>'+
			'</tr>';
		$("#categorylist").append(htmls);
		$("#categorylistDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	}
	$("#category").removeAttr("checked");

}



//删除品牌分类
function deleteBrandAndCategory(thirdCategoriesId,brandId){
	$('#brandAndCategory_'+thirdCategoriesId+brandId).remove();
	var categroryTR = $("#brandAndCategorylist tr").length;
	if(categroryTR == 0){
		$("#BrandAndcategoryDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
}

//增加品牌分类
function showBrandAndCategory(bradnName,brandId,categoriesName,secondCategoriesName,thirdCategoriesName,secondCategoriesId,thirdCategoriesId){
	if(checkBrandAndCategory(brandId,thirdCategoriesId)){
		var htmls ='<tr id="brandAndCategory_'+thirdCategoriesId+brandId+'" class="odd even">'
		        +"<td ><input type='checkbox' name='brandAndCategory' value='"+thirdCategoriesId+";"+brandId+"'></td>"
		        + '<td>'+bradnName+'   '+categoriesName+'->'+secondCategoriesName+'->'+thirdCategoriesName+'<input type="hidden" id="brandAndCategory_categroyName'+thirdCategoriesId+brandId+'" name="categoryName" value="'+categoriesName+'->'+secondCategoriesName+'->'+thirdCategoriesName+'"/>'
			    +'<input type="hidden" id="brandAndCategory_brandName'+thirdCategoriesId+brandId+'" name="brandName" value="'+bradnName+'"/>'
			    +'<input type="hidden" id="brandAndCategory_brandId'+thirdCategoriesId+brandId+'" name="brandId" value="'+brandId+'"/>'
			    +'<input type="hidden" id="brandAndCategory_categoryId'+thirdCategoriesId+brandId+'" name="thirdCategoriesId" value="'+thirdCategoriesId+'"/>'
			    +'</td><td><a class="del delrow" href="javascript:deleteBrandAndCategory(\''+thirdCategoriesId+'\',\''+brandId+'\');"></a></td>'+
			'</tr>';
		$("#brandAndCategorylist").append(htmls);
		$("#BrandAndcategoryDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	}
	$("#brandAndCategory").removeAttr("checked");

}

//判断是否已经存在品牌分类了
function checkBrandAndCategory(brandId,thirdCategoriesId){
	var thirdCategoriesIdVar = document.getElementById("brandAndCategory_brandName"+thirdCategoriesId+brandId);
	if(thirdCategoriesIdVar ==null){
	    return true;
	}
	return false;
}

//删除分销商
function deleteChainSeller(chainSellerId){
	$('#chainSeller_'+chainSellerId).remove();
	var chainSellerTR = $("#chainSellerList tr").length;
	if(chainSellerTR == 0){
		$("#chainSellerTable").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
}


//增加分销商
function showChainSeller(chainSellerId,sellerName){
	var htmls ='<tr id="chainSeller_'+chainSellerId+'" class="odd even">'
	       +"<td ><input type='checkbox' name='chainSeller' value='"+chainSellerId+"'></td>"
	       +'<td>'+sellerName+'<input type="hidden" id="sellerName'+chainSellerId+'" name="sellerName" value="'+sellerName+'"/>'
		   +'<input type="hidden" id="chainSellerId'+chainSellerId+'" name="chainSellerId" value="'+chainSellerId+'"/></td><td><a class="del delrow" href="javascript:deleteChainSeller(\''+chainSellerId+'\');"></a></td>'+
		'</tr>';

	$("#chainSellerList").append(htmls);
	$("#chainSellerTable").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	$("#chainSeller").removeAttr("checked");
}

//删除品牌
function deleteBrand(brandId){
	$('#brand_'+brandId).remove();
	var brandTR = $("#brandlist tr").length;
	if(brandTR == 0){
		$("#brandlistDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
}

//增加品牌
function showBrand(brandId,brandNo,brandName){
	var htmls ='<tr id="brand_'+brandId+'" class="odd even">'
	       +"<td ><input type='checkbox' name='brand' value='"+brandId+"'></td>"
	       +'<td>'+brandName+'<input type="hidden" id="brandName'+brandId+'" name="brandName" value="'+brandName+'"/>'
		   +'<input type="hidden" id="brandId'+brandId+'" name="brandId" value="'+brandId+'"/></td><td><a class="del delrow" href="javascript:deleteBrand(\''+brandId+'\');"></a></td>'+
		'</tr>';
	$("#brandlist").append(htmls);
	$("#brandlistDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	$("#brand").removeAttr("checked");
}

//判断是否已经存在商品了
function checkCommodity(commodityNo){
	var commodityNoVar = document.getElementById("commodityNo"+commodityNo);;
	if( commodityNoVar != null){
	    return false;
	}
	return true;
}


//删除商品
function deleteCommodity(commodityNo){
	$('#commodity_'+commodityNo).remove();
	var commodityTR = $("#commoditylist tr").length;
	if(commodityTR == 0){
		$("#commoditylistDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	$("#chkb").removeAttr("checked");
	
}


//增加商品
function showCommodity(commodityName,commodityNo){
	if(checkCommodity(commodityNo)){
		var htmls ='<tr id="commodity_'+commodityNo+'" class="odd even">'
			    +'<td>'+commodityName+'<input type="hidden" id="commodityName'+commodityNo+'" name="commodityName" value="'+commodityName+'"/>'
			    +'<input type="hidden" id="commodityNo'+commodityNo+'" name="commodityNo" value="'+commodityNo+'"/><a class="del delrow" href="javascript:deleteCommodity(\''+commodityNo+'\');"></a></td>'
			    +'</tr>';
		$("#commoditylist").append(htmls);
		$("#commoditylistDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	}
}

//将添加的商品显示到列表中
function fillCommodity(){
	if(DATA_MAP_commodity.length<=0){
		return false;
	}
	//封装数据
	var html="";
	var commodityStatus="";
	for(var i = 0 ; i < DATA_MAP_commodity.length ; i++){
			var commodityNoVar = document.getElementById("commodityNo"+DATA_MAP_commodity[i].no);;
			if( commodityNoVar == null){				
				if(DATA_MAP_commodity[i].commodityStatus==2){
					commodityStatus="上架";	
				}else{
					commodityStatus="下架";	
				}
				html+="<tr>"+
						"<td ><input type='checkbox' name='chk' value='"+DATA_MAP_commodity[i].no+"'></td>"+
						"<td>限定商品</td>"+
						"<td><img src="+DATA_MAP_commodity[i].picSmall+" height='40px' width='40px' /><input type='hidden' id='commodityNo"+DATA_MAP_commodity[i].no+"' name='commodityNo' value="+DATA_MAP_commodity[i].no+"></td>"+
						"<td>"+DATA_MAP_commodity[i].commodityName+"<input type='hidden' name='commodityName' value="+DATA_MAP_commodity[i].commodityName+"></td>"+
						"<td>"+DATA_MAP_commodity[i].no+"</td>"+
						"<td>"+DATA_MAP_commodity[i].catName+"</td>"+
						"<td>"+DATA_MAP_commodity[i].publicPrice+"</td>"+
						"<td>"+DATA_MAP_commodity[i].salePrice+"</td>"+
						"<td id='activePrice"+DATA_MAP_commodity[i].no+"'>"+DATA_MAP_commodity[i].costPrice+"</td>"+
						"<td>"+commodityStatus+"</td>"+
						"<td>"+DATA_MAP_commodity[i].productNum+"</td>"+
				        "<td><input type='button' class='btn-add-normal' onclick='deleteMyComm(this,"+DATA_MAP_commodity[i].no+")' value='删除' /></td></tr>"; 
			}
	}
	$("#commodityShow").append(html);
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	$("#commodityNumber").empty();
	$("#commodityNumber").append("<td colspan='13'>已添加限定商品："+commodityTR+"件."+"排除商品："+excludeCommodityTR+"件</td>");
	$("#chkb").removeAttr("checked");
	DATA_MAP_commodity = new Array(); 
	$("#activeCommodityList").show();
}


//将添加的商品显示到列表中
function putCommoditys(){
	if(DATA_MAP_commodity.length<=0){
		return false;
	}
	//封装数据
	var html="";
	var commodityStatus="";
	for(var i = 0 ; i < DATA_MAP_commodity.length ; i++){
			var commodityNoVar = document.getElementById("commodityNo"+DATA_MAP_commodity[i].no);;
			if( commodityNoVar == null){				
				if(DATA_MAP_commodity[i].commodityStatus==2){
					commodityStatus="上架";	
				}else{
					commodityStatus="下架";	
				}
				html+="<tr>"+
						"<td ><input type='checkbox' name='chk' value='"+DATA_MAP_commodity[i].no+"'></td>"+
						"<td>"+DATA_MAP_commodity[i].no+"<input type='hidden' name='commodityNo' value="+DATA_MAP_commodity[i].no+"></td>"+
						"<td width='20%'>"+DATA_MAP_commodity[i].commodityName+"<input type='hidden' name='commodityName' value="+DATA_MAP_commodity[i].commodityName+"></td>"+
						"<td>"+DATA_MAP_commodity[i].specName+"</td>"+
						"<td>"+DATA_MAP_commodity[i].brandName+"</td>"+
						"<td><input type='text' style='width:40px' value='"+DATA_MAP_commodity[i].activePrice+"' name='activePrice' size='8'/></td>"+
						"<td>"+DATA_MAP_commodity[i].publicPrice+"</td>"+
						"<td>"+DATA_MAP_commodity[i].salePrice+"</td>"+
						"<td>"+DATA_MAP_commodity[i].costPrice+"</td>"+
						"<td>"+commodityStatus+"</td>"+
						"<td>"+DATA_MAP_commodity[i].productNum+"</td>"+
				        "<td><input type='button' class='btn-add-normal' onclick='deleteMyComm(this,"+DATA_MAP_commodity[i].no+")' value='删除' /></td></tr>"; 
			}
	}
	$("#commodityShow").append(html);
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	$("#commodityNumber").empty();
	$("#commodityNumber").append("<td colspan='13'>已添加限定商品："+commodityTR+"件.</td>");
	$("#chkb").removeAttr("checked");
	DATA_MAP_commodity = new Array(); 
	$("#activeCommodityList").show();
}

//删除当前栏目的商品
function deleteMyComm(obj,no){
	$(obj).parent().parent().remove();
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	
	$("#commodityNumber").empty();
	$("#commodityNumber").append("<td colspan='13'>已添加限定商品："+commodityTR+"件.</td>");
	$("#chkb").removeAttr("checked");
	
}
//将添加的商品显示到列表中
function fillExcludeCommodity(){
	if(DATA_MAP_excludecommodity.length<=0){
		return false;
	}
	//封装数据
	var html="";
	var commodityStatus="";
	for(var i = 0 ; i < DATA_MAP_excludecommodity.length ; i++){
		var commodityNoVar = document.getElementById("excludeCommodityNo"+DATA_MAP_excludecommodity[i].no);;
			if( commodityNoVar == null){				
				if(DATA_MAP_excludecommodity[i].commodityStatus==2){
					commodityStatus="上架";	
				}else{
					commodityStatus="下架";	
				}
				html+="<tr>"+
						"<td ><input type='checkbox' name='chk' value='"+DATA_MAP_excludecommodity[i].no+"'></td>"+
						"<td style='color:red'>排除商品</td>"+
						"<td><img src="+DATA_MAP_excludecommodity[i].picSmall+" height='40px' width='40px' /><input type='hidden' id='excludeCommodityNo"+DATA_MAP_excludecommodity[i].no+"' name='excludeCommodityNo' value="+DATA_MAP_excludecommodity[i].no+"></td>"+
						"<td>"+DATA_MAP_excludecommodity[i].commodityName+"<input type='hidden' name='excludeCommodityName' value="+DATA_MAP_excludecommodity[i].commodityName+"></td>"+
						"<td>"+DATA_MAP_excludecommodity[i].no+"</td>"+
						"<td>"+DATA_MAP_excludecommodity[i].catName+"</td>"+
						"<td>"+DATA_MAP_excludecommodity[i].publicPrice+"</td>"+
						"<td>"+DATA_MAP_excludecommodity[i].salePrice+"</td>"+
						"<td style='color:red' id='activePrice"+DATA_MAP_excludecommodity[i].no+"'>0</td>"+
						"<td>"+commodityStatus+"</td>"+
						"<td>"+DATA_MAP_excludecommodity[i].productNum+"</td>"+
				        "<td><input type='button' class='btn-add-normal' onclick='deleteMyComm(this,"+DATA_MAP_excludecommodity[i].no+")' value='删除' /></td></tr>"; 
			}
	}
	$("#excludeCommodityShow").append(html);
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	$("#commodityNumber").empty();
	$("#commodityNumber").append("<td colspan='13'>已添加限定商品："+commodityTR+"件.</td>");
	$("#chkb").removeAttr("checked");
	DATA_MAP_excludecommodity = new Array();  
}
//判断是否已经存在排除商品了
function checkExcludeCommodity(commodityNo){
	var commodityNoVar = document.getElementById("excludeCommodityNo"+commodityNo);
	if( commodityNoVar != null){
	    return false;
	}
	return true;
}

//删除排除商品
function deleteExcludeCommodity(commodityNo){
	$('#excludeCommodity_'+commodityNo).remove();
	var commodityTR = $("#excludeCommoditylist tr").length;
	if(commodityTR == 0){
		$("#excludeCommodityDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		$("#checkboxlist").attr("style","display:none;");
	}
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	$("#chkb").removeAttr("checked");
	DATA_MAP_excludecommodity = new Array();  
}


//增加排除商品
function showExcludeCommodity(commodityName,commodityNo){
	if(checkExcludeCommodity(commodityNo)){
		var htmls ='<tr id="excludeCommodity_'+commodityNo+'" class="odd even">'
			    +'<td>'+commodityName+'<input type="hidden" id="excludeCommodityName'+commodityNo+'" name="excludeCommodityName" value="'+commodityName+'"/>'
			    +'<input type="hidden" id="excludeCommodityNo'+commodityNo+'" name="excludeCommodityNo" value="'+commodityNo+'"/><a class="del delrow" href="javascript:deleteExcludeCommodity(\''+commodityNo+'\');"></a></td>'
			    +'</tr>';
		$("#excludeCommoditylist").append(htmls);
		$("#excludeCommodityDiv").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	}
}


//选择优惠券回调函数
function selectCoupon(){
	couponSelector.open({},function(json){
	// 通过约定格式获取选定数据
		$.each(json,function(n,value) {
			if(!addCouponsCheck(value.id)){
				var htmls ='<tr id="coupon_'+value.id+'" class="odd even">'+
						    '<td>'+value.couponName+'<input type="hidden" name="couponName" value="'+value.couponName+'"/><input type="hidden" name="couponsId" value="'+value.id+'"/></td>'+
						    '<td>'+		
						      '<input type="text" style="width:35px;" name="couponSchemeCounts"  maxLength="3" value="1" onkeyup="onkeyNumberTxt(this)"/></td>'+
						    '<td><center><a class="del delrow" href="javascript:deleteCoupons(\''+value.id+'\',\''+value.couponName+'\');"></a></center></td>'+
						'</tr>';
				$("#couponContainer_").append(htmls);
			}
		})
		var length = $("#couponContainer_ tr").length;
		if(length==0){
			$("#container_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		}else{
			$("#container_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
		}
	})
}


//删除优惠券
function deleteCoupons(id,name){
	if(confirm("确定删除优惠券："+name)){
		DATA_MAP_Coupon = DATA_MAP_Coupon.del(DATA_MAP_Coupon.indexOf(id));
		$('#couponContainer_ > #coupon_'+id).remove();
		var length = $("#container_ table tbody tr").length;
		if(length==0){
			$("#container_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		}
	}
}


//增加优惠券模板前验证 true 为存在:false 不存在
function addCouponsCheck(id){
	 var couponLength = $("#couponContainer_").children("tr[id='coupon_"+id+"']").length;
	 if(couponLength == 0){
		return false;
	}
	return true;
}



//子窗体选择赠品调函数
function chooseGift(commodityName,productNo,count){
	if(checkGift(productNo)){
	}else{
	var length = $("#giftcontainer_ table tbody tr").length;
	if(length==0){
		$("#giftcontainer_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;");
	}
	 var htmls ='<tr id="gift_'+productNo+'" class="odd even">'+
				    '<td>'+commodityName+'<input type="hidden" name="giftName_" value="'+commodityName+'"/>'+
				    '<td>'+productNo+'<input type="hidden" name="productNo_" value="'+productNo+'"/>'+
				    '<td>'+		
				      '<input type="text" style="width:35px;" name="giftNumber_" value="'+count+'"  onkeyup="onkeyNumberTxt(this)"/></td>'+
				    '<td><center><a class="del delrow" href="javascript:deleteGift(\''+productNo+'\');"></a></center></td>'+
				'</tr>';
		$("#giftListContainner_").append(htmls);
		DATA_MAP_GIFT.push(productNo);
	}
}
//删除赠品
function deleteGift(productNo){
	if(confirm("确定删除赠品："+productNo)){
		DATA_MAP_GIFT = DATA_MAP_GIFT.del(DATA_MAP_GIFT.indexOf(productNo));
		$('#giftListContainner_ > #gift_'+productNo).remove();
		var length = $("#giftcontainer_ table tbody tr").length;
		if(length==0){
			$("#giftcontainer_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		}
	}
}
//增加赠品验证
function checkGift(productNo){
	 var giftLength = $("#giftcontainer_").children("tr[id='gift_"+productNo+"']").length;
	 if(giftLength == 0){
		return false;
	}
	return true;
}

var discountDetailNumber=0;
//增加多级优惠
function addMoreRank(){
	var lessConsumpAmount_ = $("#lessConsumpAmount0_").val();
	var moreConsumpAmount_ = $("#moreConsumpAmount0_").val();
	var decreaseAmount_ =$("#decreaseAmount0_").val();
	var discount_ =$("#discount0_").val();
	var sendIntegralNumber_ =$("#sendIntegralNumber0_").val();
	var integralMultiples_ =$("#integralMultiples0_").val();

	
	if(!checkQutoData()){
		return false;
	}
	
	$("#disCountlistTR").removeAttr("style");
	var html = '';
	html +='<div id="superDiscount_'+RULE+'" style="padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;">'
			+'<table width="50%" class="list_table">'
			+'	<tbody id="discountDetail_'+RULE+'">'
			+'		<tr>'
			+'			<td>'
			+'				<label></label>'
			+'				<input type="button" id="deleteRank'+RULE+'" name="deleteRank'+RULE+'" class="btn-add-normal4" value="删除该层级" onclick="deleteRank('+RULE+');">'
			+'			</td>'
			+'		</tr>'
			+'		<tr id="favourableTerms'+RULE+'" >'
			+'			<td>'
			+'			   <div >'
			+'					<label>&nbsp;&nbsp;&nbsp;优惠条件：</label>'
		    +'                   <input type="text"  name="lessConsumpAmount_'+RULE+'" id="lessConsumpAmount_'+RULE+'" disabled="disabled" maxlength="5" value="'+lessConsumpAmount_+'"/>'
		    +'                    <label> ≤ 订单金额 < </label>'
		    +'                   <input type="text" name="moreConsumpAmount_'+RULE+'" id="moreConsumpAmount_'+RULE+'" disabled="disabled" maxlength="5" value="'+moreConsumpAmount_+'"/>元'
		    +'                </div>'
			+'			</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label style="margin-left:35px">直减：</label>'
			+'				<input  type="text"  name="decreaseAmount_'+RULE+'" disabled="disabled" id="decreaseAmount_'+RULE+'" value="'+decreaseAmount_+'" size="5"  />'
			+'				<label>元</label>'
			+'			</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label style="margin-left:35px">折扣：</label>'
			+'				<input type="text"  name="discount_'+RULE+'" disabled="disabled"  value="'+discount_+'" id="discount_'+RULE+'"  size="5"  />'
			+'				<font class="ft-cl-Exp">折</font>'
			+'			</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label style="margin-left:25px">送积分：</label>'
			+'				<input type="text"  name="sendIntegralNumber_'+RULE+'" disabled="disabled"  value="'+sendIntegralNumber_+'" id="sendIntegralNumber_'+RULE+'"  size="5"  />'
			+'				<label>分</label>'
			+'			</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label>加倍送积分：</label>'
			+'				<input  type="text"  name="integralMultiples_'+RULE+'" disabled="disabled" value="'+integralMultiples_+'" id="integralMultiples_'+RULE+'"  size="5"  />'
			+'				<label></label>'
			+'			</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label>支持折上折   ：</label>';
			if($("input[type='checkbox'][name='isDiscount0_']:checked").attr("checked")){
				html +='<input type="checkbox" disabled ="disabled" checked="checked" name="isDiscount_'+RULE+'" value="1" style="margin-right:5px;"/>';
				$("input[type='checkbox'][name='isDiscount0_']").removeAttr("checked");
			}else{
				html +='<input type="checkbox" disabled ="disabled"  name="isDiscount_'+RULE+'" value="0" style="margin-right:5px;"/>';
			}
		     		
	   html +='</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label>优惠券列表   ：</label>'
			+'				<input type="button" class="btn-add-normal4" style="display:none" value="添加优惠券" onclick="openwindow(\'system/promotionQutoController/queryCouponSchemeList.sc\',\'\',\'\',\'\');"  >'
			+'				<div id="container_'+RULE+'" style="padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;">'
			+'					<table class="list_table">'
			+'						<tbody id="couponContainer_'+RULE+'">'
    		+'						 </tbody>'
			+'					  </table>'
			+'				 </div>'
			+'			</td>'
			+'		</tr>'
			+'		<tr>'
			+'			<td>'
			+'				<label>赠品列表   ：</label>'
			+'				<input type="button" class="btn-add-normal4" style="display:none" value="添加赠品" onclick="openwindow(\'system/promotionQutoController/loadProductGift.sc\',\'\',\'\',\'\');"  >'
			+'				<div id="giftcontainer_'+RULE+'" style="padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;">'
			+'					<table class="list_table">'
			+'						<tbody id="giftListContainner_'+RULE+'">'
    		+'						 </tbody>'
			+'					  </table>'
			+'				 </div>'
			+'			</td>'
			+'		</tr>'
			+'	 </tbody>'
			+' </table>'
		 +'</div>';
    $("#discountDetaildiv2").append(html);    
    $("#couponContainer_"+RULE+"").append(($("#couponContainer_").children()).clone());
 
   $("#couponContainer_"+RULE+" >tbody").attr("id","couponContainer_"+RULE+"");
   $("#couponContainer_").empty();
   var couponCount =0;
   $("#couponContainer_"+RULE+" tr").each(function(i){
	   couponCount ++;
	   $(this).children("td:eq(1)").children("input").attr("disabled","disabled");
	   $(this).children("td:last").empty();
	 }); 
   
   var coupoCountHtml ='';
   coupoCountHtml +='<input  type="hidden"  name="couponCount'+RULE+'" value="'+couponCount+'" id="couponCount'+RULE+'"  size="5"  />';
  $("#container_"+RULE+"").append(coupoCountHtml);
  
  //赠品处理
  $("#giftListContainner_"+RULE+"").append(($("#giftListContainner_").children()).clone());
  $("#giftListContainner_"+RULE+" >tbody").attr("id","giftListContainner_"+RULE+"");
  $("#giftListContainner_").empty();
  var giftCount =0;
  $("#giftListContainner_"+RULE+" tr").each(function(i){
	  giftCount ++;
	   $(this).children("td:eq(2)").children("input").attr("disabled","disabled");
	   $(this).children("td:last").empty();
	 }); 
  
  var giftHtml ='';
  giftHtml +='<input  type="hidden"  name="giftCount_'+RULE+'" value="'+giftCount+'" id="giftCount_'+RULE+'"  size="5"  />';
 $("#giftcontainer_"+RULE+"").append(giftHtml);
  
   
	 //当新增多级优惠没有优惠券时，不显示边框
	var length = $("#couponContainer_"+RULE+" tr").length;
	if(length==0){
		$("#container_"+RULE+"").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		$("#container_"+RULE+"").prev().prev().attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		$("#container_"+RULE+"").prev().attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
   $("#container_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
   
   //当多级优惠没有同赠品时,不显示边框
	length = $("#giftcontainer_"+RULE+" tr").length;
	if(length==0){
		$("#giftcontainer_"+RULE+"").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		$("#giftcontainer_"+RULE+"").prev().prev().attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
		$("#giftcontainer_"+RULE+"").prev().attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
	}
  $("#giftcontainer_").attr("style","padding:5px;padding-top:0px;margin:10px;border:dotted 1px blue;display:none;");
 
  
    
   $("#lessConsumpAmount0_").val("");
   $("#moreConsumpAmount0_").val("");
   $("#fullConsumpAmount").val("");
   $("#decreaseAmount0_").val("");
   $("#discount0_").val("");
   $("#sendIntegralNumber0_").val("");
   $("#integralMultiples0_").val("");
   $("#icappedMoney0_").val("");
   RULE++;
   
}

function deleteRule(ruleNumber){
	$("#superDiscount_"+ruleNumber+"").remove();
}

function deleteRank(ruleNumber){
 $("#superDiscount_"+ruleNumber+"").remove();
}


function checkQutoData(){

	//获取订单金额范围
	var lessConsumpAmount_ = $("#lessConsumpAmount0_").val();
	var moreConsumpAmount_ = $("#moreConsumpAmount0_").val();
	var decreaseAmount_ =$("#decreaseAmount0_").val();
	var discount_ =$("#discount0_").val();
	var sendIntegralNumber_ =$("#sendIntegralNumber0_").val();
	var integralMultiples_ =$("#integralMultiples0_").val();

	$("#lessConsumpAmount0_").val(lessConsumpAmount_);
	$("#moreConsumpAmount0_").val(moreConsumpAmount_);
	$("#decreaseAmount0_").val(decreaseAmount_);
	$("#discount0_").val(discount_);
	$("#sendIntegralNumber0_").val(sendIntegralNumber_);
	$("#integralMultiples0_").val(integralMultiples_);
	
	if($("input:radio[name='prefType_']:checked").val() ==1){
		if(lessConsumpAmount_ ==null || lessConsumpAmount_ ==''){
			alert("订单金额范围区间最小值设定错误!");
			return false;
		}
		if(moreConsumpAmount_ ==null || moreConsumpAmount_ ==''){
			alert("订单金额范围区间最大值设定错误!");
			return false;
		}
		
		var val1 = parseFloat(lessConsumpAmount_);
		var val2 = parseFloat(moreConsumpAmount_);

		if(val1>=val2){
			alert("订单金额范围区间设定错误!");
			return false;
		}
	
		var lessConsumpAmountlist = $("input[name*='lessConsumpAmount_']");
		var moreConsumpAmountlist = $("input[name*='moreConsumpAmount_']");
		
		for(var i=0;i<lessConsumpAmountlist.length;i++){
			if(lessConsumpAmountlist[i].value <=val1 && val1<moreConsumpAmountlist[i].value){
				alert("订单金额范围区间最小值设定错误!");
				return false;
			}
			if(lessConsumpAmountlist[i].value <val2 && val2<=moreConsumpAmountlist[i].value){
				alert("订单金额范围区间最大值设定错误!");
				return false;
			}
		}
		
		if(decreaseAmount_ != null || decreaseAmount_ !=''){
			var val3 = parseFloat(decreaseAmount_);
			if(val3 >= lessConsumpAmount_){
				alert("直减金额大于订单金额最小金额");
				return false;
			}
		}
	}
	
	if($("input:radio[name='prefType_']:checked").val() ==0){
		var fullConsumpAmount = $("#fullConsumpAmount").val();
		if(fullConsumpAmount == null || fullConsumpAmount ==''){
			alert("请填写订单金额");
			return false;
		}

		if(parseFloat(fullConsumpAmount) <=0){
			alert("订单金额不能为负数");
			return false;
		}
		
		var icappedMoney0_ = $("#icappedMoney0_").val();
		if(icappedMoney0_ != null && icappedMoney0_ != ''){
			if(parseFloat(fullConsumpAmount) >parseFloat(icappedMoney0_)){
				alert("封顶金额填写错误");
				return false;
			}
		}
		
		if(parseFloat(icappedMoney0_) <=0){
			alert("订单金额不能为负数");
			return false;
		}
		if(decreaseAmount_ != null && decreaseAmount_ != ''){
			if(parseFloat(decreaseAmount_) >=parseFloat(fullConsumpAmount)){
				alert("直减金额填写错误");
				return false;
			}
		}	
	}
	
	var couponNameStr = $("input[name='couponName']");
	var couponSchemeCountsStr = $("input[name='couponSchemeCounts']");
	for(var i = 0 ; i < couponNameStr.length ; i++){
		if(couponSchemeCountsStr[i].value=='' || couponSchemeCountsStr[i].value ==null){
			alert(couponNameStr[i].value+"的赠送数量设置错误");
			return false;
		}
	}
	
	if(discount_ != null && discount_!='' && (discount_ <=0 || discount_ >=10)){
		alert("折扣设定错误");
		return false;
	}
	return true;
}


//删除优惠券
function deleteDiscountDetail(number){
    $("#discountDetailId"+number).remove();
}

function batchDeleteCommodity(){
	var checkObj=$("input[name='chk']:checked");
	checkObj.each(function(index, element) {
	   var vals=$(this).val().split(';');
	   deleteMyComm(this,vals[0]);
    });
	
	var excludeCommodityTR = $("#excludeCommodityShow tr").length;
	var commodityTR = $("#commodityShow tr").length;
	if(commodityTR + excludeCommodityTR !=0){
		$("#commodityCheckboxlist").show();
	}else{
		$("#commodityCheckboxlist").hide();
	}
	$("#chkb").removeAttr("checked");
	DATA_MAP_excludecommodity = new Array();  
	DATA_MAP_commodity = new Array();  
}


function batchDeleteBrandOrCategory(chkName){
	var checkObj=$("input[name='"+chkName+"']:checked");
	checkObj.each(function(index, element) {
	   var vals=$(this).val().split(';');
	   if(chkName=="brand"){
		   deleteBrand(vals[0]);
	   }else if(chkName=="category"){
		   deleteCategory(vals[0]);
	   }else if(chkName=="brandAndCategory"){
		   deleteBrandAndCategory(vals[0],vals[1]);
	   }
    });
	$("#"+chkName).removeAttr("checked");
}

function allChk(obj,chkName){
	var id = obj.id;
	if($("#"+id).attr("checked")) {
		 $("input[name='"+chkName+"']").attr("checked",'true');//全选
	}else{
		$("input[name='"+chkName+"']").removeAttr("checked");//取消全选
	}
}

/**
 * 免运费品牌选择器，点击保存后，展示到主页面
 * @author huang.wq
 * @param {Array} brandList 选中品牌的数组，数组元素类型为Object{id,brandName}
 */
function showFreeShippingBrands(brandList) {
	var html = '';
	var brandObj = null;
	for (var i = 0, len = brandList.length; i < len; i++) {
		brandObj = brandList[i];
		html += formatString(
			'<tr class="odd even free_brand_tr" id="free_brand_tr_#{id}">' +
			'	<td><input type="checkbox" class="free_brand_chk" id="free_brand_chk_#{id}" value="#{id}" name="brandChk" /><td>' + 
			'	<td>#{brandName}</td>' +
			'	<td><a class="del delrow" href="javascript:deleteFreeShippingBrand(\'#{id}\')"></a></td>' +
			'</tr>', 
			brandObj);
	}
	
	$("#freeShip_brand_list").html(html);
	$("#freeShip_brand_chk_all").removeAttr("checked");
	//绑定通用全选方法
	bindCommonCheckAll("free_brand_chk", "freeShip_brand_chk_all");
}

/**
 * 删除免运费活动中的品牌
 * @author huang.wq
 * @param {String} brandId 品牌id
 */
function deleteFreeShippingBrand(brandId) {
	$("#free_brand_tr_" + brandId).remove();
}

/**
 * 批量删除免运费活动中的品牌
 */
function batchDeleteFreeShippingBrands() {
	$(".free_brand_chk:checked").parent().parent().remove();
//	$(".free_brand_tr").remove();
}

/**
 * 绑定通用全选方法
 * @param {String} checkBoxClass checkbox的class值
 * @param {String} checkAllBoxId 全选的checkbox的id
 */
function bindCommonCheckAll(checkBoxClass, checkAllBoxId) {
	$("#" + checkAllBoxId).unbind("click");
	$("." + checkBoxClass).unbind("click");
	//全选
	$("#" + checkAllBoxId).click(function() {
		//console.log("all click");
		if(this.checked) {
			$("." + checkBoxClass).attr("checked", true);
		} else {
			$("." + checkBoxClass).removeAttr("checked");
		}
	});
	//单个checkbox
	$("." + checkBoxClass).click(function() {
		//console.log("single click");
		var checkBoxCount = $("." + checkBoxClass).length;
		var checkedCount = $("." + checkBoxClass + ":checked").length;
		if(this.checked) {
			if(checkBoxCount == checkedCount) {
				$("#" + checkAllBoxId).attr("checked", true);
			}
		} else {
			if (checkBoxCount != checkedCount) {
				$("#" + checkAllBoxId).removeAttr("checked");
			}
		}
	});
}

/**
 * 对目标字符串进行格式化
 * @author huang.wq
 * @param {string} source 目标字符串
 * @param {Object|string...} opts 提供相应数据的对象或多个字符串
 * @remark
 * opts参数为“Object”时，替换目标字符串中的#{property name}部分。<br>
 * opts为“string...”时，替换目标字符串中的#{0}、#{1}...部分。	
 * @shortcut format
 * @returns {string} 格式化后的字符串
 * 例：
    (function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('#{0}-#{1}-#{2}',["2011年","5月","1日"]);
	(function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('#{year}-#{month}-#{day}', {year: 2011, month: 5, day: 1});   
 */
function formatString(source, opts) {
    source = String(source);
    var data = Array.prototype.slice.call(arguments,1), toString = Object.prototype.toString;
    if(data.length){
	    data = data.length == 1 ? 
	    	/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
	    	(opts !== null && (/\[object Array\]|\[object Object\]/.test(toString.call(opts))) ? opts : data) 
	    	: data;
    	return source.replace(/#\{(.+?)\}/g, function (match, key){
	    	var replacer = data[key];
	    	// chrome 下 typeof /a/ == 'function'
	    	if('[object Function]' == toString.call(replacer)){
	    		replacer = replacer(key);
	    	}
	    	return ('undefined' == typeof replacer ? '' : replacer);
    	});
    }
    return source;
};
