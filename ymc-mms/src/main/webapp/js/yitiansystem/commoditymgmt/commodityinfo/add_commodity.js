var hashMap = new HashMap();

var exitSpArray = new Array(); ///已存在的货品规格属性信息

var i = 0;
// 存储选中集合的val的structName部分(value="structName;id"的格式)
var arr = new Array();
// 存储选中的集合val的id部分
var idArr = new Array();
// 存储规格属性名称和区域的对应关系
var areaArr = new Array();
// 存储根路径
var basePath;

// ajax请求
function tomap(jsonlist){
	for(var i=0;i<jsonlist.length;i++){
		if (!hashMap.containsKey(jsonlist[i].key)) {
			hashMap.put(jsonlist[i].key,jsonlist[i].key);
			hasArray.push(jsonlist[i].key);
		}
	}
	
}

/**
 * 选中价格区间
 * @param result	fv验证框架验证结果
 * @returns {Boolean}
 */
function changePriceMiddle(result){
	var options = $("#catPrice option");  
	var salePrice = $("#salePrice").val();
	if(salePrice != "" && options.length <= 1){
		 alert('请选择分类!');
		 $("#salePrice").attr("value","");
		 return false;
	}else if(salePrice == ""){
		$("#catPrice option:first").attr("selected", true);
	}else{
		//验证通过 
		if(result){
			salePrice = Number($.trim(salePrice));		//将销售价格转为number型
			
			if(salePrice <= 0) return false;
			
			//是否在最小价格段
			var firstPrice = $.trim($(options[1]).val().replace(/[\u4E00-\u9FA5]/g, ""));
			if(firstPrice != "" && firstPrice.split("-").length == 1  && salePrice <= Number(firstPrice)){
				$(options[1]).attr("selected", true);
				return true;
			}
			
			//判断销售价格是否大于最大价格区间值
			var lastPrice = $.trim($("#catPrice option:last").val().replace(/[\u4E00-\u9FA5]/g, ""));
			if(lastPrice != "" &&  lastPrice.split("-").length == 1  && salePrice >=  Number(lastPrice)){
				$("#catPrice option:last").attr("selected", true);
				return true;
			}
			
			//选中最大价格区间和最小价格区间之间的相
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
}

/**
 * 获取子分类
 * @param selValue	分类结构字符串
 * @param selId		分类下拉框ID
 */
function get(selValue, selId) {
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
				var option = "<option value='" + data[i].structName + ";" + data[i].id + "'>" + data[i].catName + "</option>";
				$("#" + selId).append(option);
			}
		}
	});
}

/**
 * 加载一 二 三 级分类 并选中
 */
$(document).ready(function() {

	basePath = $("#basePath").val();
	$('#rootCattegory').change(function() {
		// 选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			get(value[0], "secondCategory");
		} else {
			$("#secondCategory").empty();// 清空下来框
			$("#secondCategory").append("<option value='0'>选择分类</option>");
		}
		$("#threeCategory").empty();// 清空下来框
		$("#threeCategory").append("<option value='0'>选择分类</option>");
	});
	$('#secondCategory').change(function() {
		// 选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			get(value[0], "threeCategory");
		} else {
			$("#threeCategory").empty();// 清空下来框
			$("#threeCategory").append("<option value='0'>选择分类</option>");
		}
	});
});


//删除分类行
function removeLiT(obj) {
	renameLi();
	var rowId = $(obj).parent().attr("id");
	$("#" + rowId).remove();
	// rowId后缀，从0开始，并转为数字
	var rowIndex = rowId.substring(3, rowId.length) * 1;
	// 行号和数组的索引号对应
	// 移除structName
	arr.splice(rowIndex, 1);
	// 移除id
	idArr.splice(rowIndex, 1);
	// 重新加载品牌
	$("#commodityCat").val(arr[arr.length - 1]);
	$("#commodityCatId").val(arr[idArr.length - 1]);
	ajaxBrand("selBrand");

	// 如果没有分类 则添加分类按扭可用
	var catSize = $("#showCat li").length;
	if (catSize == 0) {
		$("#addNewCatButton").removeAttr("disabled");
		$("#extendPropDiv").empty();
		$("#specPropDiv").empty();
		$("#showImage").empty();

		hashMap = new HashMap();
	}
}


//重新索引
function renameLi() {
	$("#showCat li").each(function(i) {
		this.id = "row" + i;
	});
}



/*
 * //提交按钮所在的表单 function postForm(formId, url) { $("#" + formId).attr("action",
 * url); // 添加分类的hidden到form //addHidden(formId); $("#" + formId).submit(); }
 */

/*
 * //在表单中添加hidden function addHidden(formId) { //遍历arr数组 var i = 0; for ( var v
 * in arr) { var tName = "structName"; var hdn = "<input type='hidden' name='" +
 * tName + "' value='" + arr[i] + "'/>"; i++; $("#" + formId).append(hdn); } }
 */

/**
 * 异步请求品牌
 * selId  品牌下拉框ID
 * value (arr[0])  分类结构字符串
 */
function ajaxBrand(selId) {
	var value = arr[0];
	if (arr.length != 0) {
		$.ajax({
			type : "POST",
			url : "getChildBrand.sc",
			data : {
				"value" : value
			},
			dataType : "json",
			success : function(data) {
				$("#" + selId).empty();// 清空下来框
				$("#" + selId).append("<option value='0'>品牌名称</option>");
				for ( var i = 0; i < data.length; i++) {
					var option = "<option value='" + data[i].id + "'>"+ data[i].brandName + "</option>";
					$("#" + selId).append(option);
				}
			}
		});
	} else {
		$("#" + selId).empty();// 清空下来框
		$("#" + selId).append("<option value='0'>品牌名称</option>");
	}
}
 
//异步请求分类价格区间
 function ajaxCatPriceScope(selId) {
 	if (arr.length != 0) {
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
 					var option = "<option value='" + data[i] + "'>"+ data[i]+ "</option>";
 					$("#catPrice").append(option);
 				}
 			}
 		});
 	} else {
 		$("#catPrice").empty();// 清空下来框
 		$("#catPrice").append("<option>价格区间</option>");
 	}
 }

// 异步请求扩展属性
 /**
  * contentId  容器ID
  * allowPropData  已经拥有的属性
  */
function ajaxExtendProp(value,contentId,allowPropData) {
	$.ajax({
		type : "POST",
		url : "getExtendProp.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			// 页面展示内容
			if (data != null) {
				showExtendProp(data,contentId,allowPropData);
			}
		}
	});
}

// 异步请求规格属性
function ajaxSpecProp(value) {
	$.ajax({
		type : "POST",
		url : "getSpecProp.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			// 页面展示内容
			if (data != null) {
				showSpecContent(data);
			}
		}
	});
}

function ajaxSpecProp1(value) {
	$.ajax({
		type : "POST",
		url : "getSpecProp.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			// 页面展示内容
			if (data != null) {
				showSpecContent1(data);
			}
		}
	});
}

$(document).ready(function() {
	$(".piclist").children(".btn-gd").hide();
	$.each($(".piclist"), function() {
		$(this).hover(function() {
			$(this).children(".btn-gd").show();
		}, function() {
			$(this).children(".btn-gd").hide();
		});
	});
});


$(document).ready(
	function(){
		$.each($(".rightgogr"), function() {
			$(this).click(
				function() {
					var curimg = $(this).parent().parent().children("img").attr("src");
					var neximg = $(this).parent().parent().next().children("img").attr("src");
					$(this).parent().parent().next().children("img").attr("src", curimg);
					$(this).parent().parent().children("img").attr("src", neximg);
				}
			);
		}
	);
});


$(document).ready(function() {
	$.each($(".leftgogr"), function() {
		$(this).click(function() {
			var curimg = $(this).parent().parent().children("img").attr("src");
			var neximg = $(this).parent().parent().prev().children("img").attr("src");
			$(this).parent().parent().prev().children("img").attr("src", curimg);
			$(this).parent().parent().children("img").attr("src", neximg);
		});
	});
});



// 删除一行
function removeThis(obj) {
	$("#" + obj).remove();
}


// 全选和反选
function allChk(obj, chkName) {
	var id = obj.id;
	if ($("#" + id).attr("checked")) {
		$("[name='" + chkName + "']").attr("checked", 'true');// 全选
	} else {
		$("[name='" + chkName + "']").removeAttr("checked");// 取消全选
	}
}

// 可编辑表格
var iDex = 0;
function edit(obj) {
	var id = "text" + iDex;
	var chk = $(obj);
	if ($(obj).attr("checked")) {
		var val = chk.val();
		var arr = val.split(";");
		var span = chk.next();
		if(!span){
			span.html("");
			// 设置文本框的值是保存起来的文本内容
			var input = "<input id='" + id + "' type='text' size='3' value='"+ arr[2] + "'/>";
			span.append(input);
			// 清除checkbox上面的点击事件
			chk.unbind("click");
			iDex++;
		}
		
	}
	
	/**
	 * 判断商品款号下颜色是否存在
	 */
	var itemType = obj.attributes.itemNo.value;   //自定义属性 记录属性项编号
	if(itemType.toLowerCase() == "color"){
		
		var  colorObj = $("#commodityProductColor");   //已选中的颜色对象
		var beforeNo = $("#commodityProductColor").val();  //已选中颜色
		
		var styleNo = $("#styleNo").val();   //商品款号
		if(styleNo == ""){
			alert('请输入商品款号');
			$(obj).attr("checked",false);
			return ;
		}
		
		var selectNo = obj.attributes.no.value;  //当前选中颜色
		
		if(selectNo != beforeNo){
			
			var id = $("#commodityId").val(); //商品ID  如果存在 则为修改  不存在则为增加
			if(id != null && id != "" && hashMap.size() > 0 && !beforeNo){  //如果商品下已存在货品 则不允许改变
				return ;
			}
			
			if(hashMap.size() > 0){
				$("#ajaxLoadImg").css("display","block");
			}
			
			//后台验证   如果验证通不过 则不允许添加货品
			var url="checkSpecCommodity.sc";
			ajaxRequest(url,{"styleNo":styleNo,"specNo":selectNo},function(result){
				
				if(!result) return ;
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				
				$("#ajaxLoadImg").css("display","none");
				
				
				//success 验证通过  允许添加货品标识 allowToAdd 为1
				if(result == "success"){
					$("#allowToAdd").attr("value","1");			
					$("#showTa").empty();
					 hashMap.clear();
				}else{
					$("#allowToAdd").attr("value","0");
					var oldColor = $("#commodityProductColor").val();
					$("input[itemNo='color'][no='"+oldColor+"']").attr("checked", true); 
					alert('当前颜色商品已存在，请重新选择!');
					return false;
				}
				
				
				if(!beforeNo){
					$("#commodityProductVector").append("<input type='hidden' id='commodityProductColor' />");
				}
				$("#commodityProductColor").attr("value",selectNo);
			});
		}
	}
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



// 删除tr
function removeTr(obj) {
	var tr = $(obj).parent().parent();
	var keys = tr.find("input[name='tableIndex']");
	$(tr).remove();
	hashMap.remove(keys[0].value);

	if (keys.length == 0) {
		$("#productTableIsValidator").attr("value", "0");
	}
	// 如果货品已初始验证过 则继续验证
	if ($("#productTableIsValidator").val() == "1") {
		validatorProduct();
	}
	
	var productRows = $("input[deleteRowFlag='rowdeleteflag']").size();
	if(productRows == 0){
		$("#saveButton").hide();
	}
}

// 扩展属性值改变事件
function valueChange(obj) {
	var val = $(obj).children('option:selected').val();
	var arr = val.split(";");
	var text = $(obj).children('option:selected').text();
	$("#vid").attr("value", arr[0]);
	$("#vNaId").attr("value", text);
	$("#vNo").attr("value", arr[1]);
	
	$("#vNovNaId").attr("value",arr[1]+":"+text+":"+arr[0]);
	
}

// 品牌改变事件
function brandChange() {
	var brandVal = $("#selBrand").children('option:selected').val();
	var brandName = $("#selBrand").children('option:selected').text();
	$("#brandId").val(brandVal);
	$("#brandName").val(brandName);
}

/**
 * 超链接保存
 * 
 * @return
 */
function saveSubmit() {
	$("#commodityForm").submit();
}

/**
 * Array数组中的float数据排序(升序)
 * 
 * @return
 */
function sortArray(arr) {
	return arr.sort(function(x, y) {
		return parseFloat(x) - parseFloat(y);
	});
}

function iterPrice() {
	var priceArray = new Array();
	$("input[validatorFile='sellPrice']").each(function(index, obj) {
		var price = obj.value;
		priceArray.push(price);
	});

	return sortArray(priceArray);
}


//添加新分类
function addNewCat() {
	// 一级分类的选中的值和文本
	var rootVal = $('#rootCattegory').children('option:selected').val();
	var rootText = $('#rootCattegory').children('option:selected').text();
	// 二级分类选中的值和文本
	var secondVal = $('#secondCategory').children('option:selected').val();
	var secondText = $('#secondCategory').children('option:selected').text();
	// 三级分类选中的值和文本
	var threeVal = $('#threeCategory').children('option:selected').val();
	var threeText = $('#threeCategory').children('option:selected').text();
	var rowid = "row" + i;
	var li = "<span><li id='" + rowid + "'><span>";
	var removeF = "removeLiT(this)";
	if (rootVal != "0") {
		li += rootText + "</span>";
		var rArr = rootVal.split(";");
		if (secondVal != "0") {
			li += "  >  " + "<span>" + secondText + "</span>";
			var sArr = secondVal.split(";");
			if (threeVal != "0") {
				li += "  >  " + "<span>" + threeText + "</span>";
				li += "&nbsp;&nbsp;<a onClick=" + removeF
						+ " style='cursor:pointer;'><img src='" + basePath
						+ "/images/yitiansystem/delete.gif' /></a></li>";
				li += "</span>";
				var tArr = threeVal.split(";");
				arr.push(tArr[0]);
				idArr.push((tArr[1]));
				$("#showCat").append(li);
			} else {
				arr.push(sArr[0]);
				idArr.push((sArr[1]));
				li += "&nbsp;&nbsp;<a onClick=" + removeF
						+ " style='cursor:pointer;' ><img src='" + basePath
						+ "/images/yitiansystem/delete.gif'/></a></li>";
				li += "</span>";
				$("#showCat").append(li);
			}
		} else {
			arr.push(rArr[0]);
			idArr.push((rArr[1]));
			li += "&nbsp;&nbsp;<a onClick=" + removeF
					+ " style='cursor:pointer;'><img src='" + basePath
					+ "/images/yitiansystem/delete.gif'/></a></li>";
			li += "</span>";
			$("#showCat").append(li);
		}
	}
	i++;
	$("#commodityCat").val(arr[arr.length - 1]);
	$("#commodityCatId").val(idArr[idArr.length - 1]);
	
	var catId = idArr[idArr.length - 1];
	// 加载品牌
	ajaxBrand("selBrand");
	//加载分类价格区间
	ajaxCatPriceScope(catId);
	// 加载扩展属性
	ajaxExtendProp(catId);
	// 加载规格属性
	ajaxSpecProp(catId);
	
	resetH();

	// 分类不允许添加多个
	var catSize = $("#showCat li").length;
	if (catSize != 0) {
		$("#addNewCatButton").attr("disabled", "disabled");
	}
}

/**
 * 当为输入框时，封装值
 * gg01:超高跟（大于8CM）:2c9481b130b56d310130b584df910218
 */

function chk(vNo,vid)
{
	var Invalue=$("#inputText").val();
	document.getElementById("inputText2").value=vNo+":"+Invalue+":"+vid;
}
/**
 * 
 * @param data
 * @param contentId  在动态列表展示时的动态ID值
 * @param allowPropData  已经拥有的属性
 */
function showExtendProp(data,contentId,allowPropData) {
	var prefix = "extendPropList";
	var contentHtml = "";
	if(contentId == null || contentId == ""){
		contentHtml = "<h3>商品属性</h3>";
	}
	
	for ( var i = 0; i < data.length; i++) {
		if (data[i] == null) {
			continue;
		}
		
		var name = data[i].propGroupName;		//属性组名称
		var item = data[i].group.item;			//属性项
		var pre = prefix + "[" + i + "]";		
		var divH = "<div style='width:100%;'>";
		if(contentId == null || contentId == ""){
			divH += "<div style='clear:both;'><label>";
			divH += name + ":</label></div>";
			divH +=	"<div style='clear:both;padding-left:90px;'>";
		}
		
		for ( var j = 0; j < item.length; j++) {
			var iName = item[j].propItemName;
			var iNo = item[j].propItemNo;
			var value = item[j].value;
			divH += "<span style='float:left;display:inline-block;'>";
			divH += "<span style='display:inline-block;padding:4px;width:80px;' >"+ iName + ":";			
			if($.trim(value[0].propValueNo).indexOf("cs") != -1){
				divH += "<font class='ft-cl-r'>*</font>";
			}
			divH += "</span>";
			
			var id = item[j].id;
			var dispaly = item[j].dispaly;
			var  boxType=  item[j].screenMethod;
			var selectInput = item[j].selectInput;
			//筛选方式
			boxType = (boxType == 0) ? "radio":"checkbox";
			divH += "<input type='hidden' name='" + pre + ".propItemId' value='" + id + "'>";
			divH += "<input type='hidden' name='" + pre + ".propItemValue' value='" + iName + "'>";
			divH += "<input type='hidden' name='" + pre + ".propItemNo' value='" + iNo + "'>";
			// 下拉单选
			if (dispaly == 0) {
				if (value.length > 0) {
					var vname = value[0].propValue;
					var vid = value[0].id;
					var vNo = $.trim(value[0].propValueNo);
				}
				if(selectInput==0)//选择框
				{
					divH += "<select class='input-select g-select' style='width:100px' propitemno='"+iNo+"' name='" + pre + ".childItemList["+j+"].specProvValue'  onChange='valueChange(this);'>";
				   
					if(vNo.indexOf("cs") == -1){
						divH += "<option value=''>请选择</option>";
					}
					for ( var k = 0; k < value.length; k++) {
						var vname = value[k].propValue;
						var vid = value[k].id;
						var vNo = value[k].propValueNo;
						var option = "<option  value='"+vNo+":"+ vname+":"+vid+"'>"+ vname + "</option>";
						divH += option;
					}
					divH += "</select>";
				
				}else if(selectInput==1)//输入框
				{
					divH += "<input propitemno='"+iNo+"'  id='inputText' type=text  style='width:150px;' onblur=chk('"+vNo+"','"+vid+"') />";
					divH += "<input propitemno='"+iNo+"'  id='inputText2' type=hidden name='" + pre + ".childItemList["+j+"].specProvValue' />";
				}
				
			}
			// 平铺单选
			if (dispaly == 1) {
				
				if(value == null) break;
				
				divH += "<div style='padding-left:20px;padding-top:5px;'>";
				for ( var k = 0; k < value.length; k++) {
					var vname = value[k].propValue;
					var vid = value[k].id;
					var vNo = value[k].propValueNo;
					var chkT = "<span style='display:inline-block;padding:4px;'>";
					if (k == 0) {
						chkT += "<input propitemno='"+iNo+"' type='"+boxType+"' name='" + pre + ".childItemList["+j+"].specProvValue' checked='checked' value='" + vNo+":"+ vname + "'>" + vname+"</input>";
					} else {
						chkT += "<input propitemno='"+iNo+"' type='"+boxType+"'  name='" + pre + ".childItemList["+j+"].specProvValue'  value='"+  vNo+":"+ vname  + "' >" + vname+"</input>";
					}
					chkT += "</span>";
					divH += chkT;
					
					divH += "&nbsp;&nbsp;";
				}
				divH += "</div>";
			}
			divH += "</span>";
		}
		if(contentId == null || contentId == ""){
			divH += "</div></div>";
		}
		contentHtml += divH;
	}
	contentId = (contentId == null || contentId == "") ? "extendPropDiv" : contentId;
	$("#"+contentId).append(contentHtml);
	
	if(allowPropData != null){
		//选中已有的属性
		for ( var i = 0; i < allowPropData.length; i++) {
			var prop = allowPropData[i];
			var propItem = $("#"+contentId).children().find("[propitemno='"+prop.propItemNo+"']");
			var propType = $(propItem).attr('type');
			if(propType == "select-one"){
				$(propItem).find("option[value^='"+prop.propValueNo+"']").attr("selected", true);
			}else if(propType == "checkbox" || propType == "radio"){
				for ( var j = 0; j < propItem.length; j++) {
					var childItem = propItem[j];
					 $(childItem).attr("checked",false);//取消掉默认选中 
					if($(childItem).val().indexOf(prop.propValueNo) != -1){
						 $(childItem).attr("checked",true);//打勾 
					}
				}
			}
		}
		
	}
	
	resetH();
}

//显示规格属性
function showSpecContent(data) {
	var isColor = false;
	
	var s = 0;
	var prefix = "productList";
	var namePre = "chk";
	var contentHtml = "<h3>商品规格</h3>";
	for ( var i = 0; i < data.length; i++) {
		// checkbox的name属性
		var chkName = namePre + s;
		var spanId = "span" + s;
		var name = data[i].propItemName;
		var itemNo = data[i].propItemNo;
		var  boxType=  data[i].screenMethod;
		//筛选方式
		boxType = (boxType == 0) ? "radio":"checkbox";
		// 属性项id
		var id = data[i].id;
		var divH = "";
		divH += "<li><label>" + name + ":</label></li>";
		var dispaly = data[i].dispaly;
		var dispalyType = data[i].displayType;
		var vArr = data[i].value;
		// 属性项
		var productId = prefix + "[" + s + "].itemId";
		var productValue = prefix + "[" + s + "].valueId";
		
		var viewController = (vArr.length>0) ? vArr[0].selectInput : 0;
		divH += "<li id='sAr"+i+"'  boxType='"+boxType+"' viewType="+dispaly+" viewItemType="+itemNo+" viewDisplayType="+dispalyType+" viewController="+viewController+"   class='yt-fm-sx'>";
		areaArr[s] = "sAr"+i+";" + name;
		
		
		// 平铺多选
		if (dispaly == 1) {
			
			divH += "<form  style='margin:0;padding:0;'>";
			
			// 文字
			if (dispalyType == 0 || dispalyType == 1) {
				
				for ( var j = 0; j < vArr.length; j++) {
					
					var vid = vArr[j].id;  // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var text = vArr[j].propValue;   // 文本值
					
					//如果已存在货品  则默认选中已有货品的规格
					if(exitSpArray.length > 0 && (exitSpArray[0] != "")){
						var isSelect = false;		//是否选中
						
						if(itemNo.toLowerCase() == "color" && isColor) continue;  //如果是颜色且已显示一个显示
						
						//遍历选中已有货品的规格
						for(var k = 0 ; k < exitSpArray.length ; k++){		
							var val = exitSpArray[k].split(":");
							
							if(itemNo.toLowerCase() == "color"){
								
								if(val[2] == vid){
									divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
									divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"' type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "' checked='checked' >&nbsp;";
									divH +=text;
									divH += "</input>";
									divH += "</span>";
									isColor = true;
									break;
								}
								
							}else{
								if(val[2] == vid){
									isSelect = true;
									break;
								}
								
							}
						}
						
						if(itemNo.toLowerCase() == "color") continue;  //如果是颜色且已显示一个显示
						
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						if(isSelect){
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"' type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "' checked='checked' >&nbsp;";
						}else{
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"' type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "'>&nbsp;";
						}
						
						divH +=text;
						divH += "</input>";
						divH += "</span>";
					}else{
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "'>&nbsp;";
						divH +=text;
						divH += "</input>";
						divH += "</span>";
					}
				}
				// Click的调用方法
				if(boxType == "checkbox"){
					var fun = "allChk(this," + "\"" + chkName + "\"" + ")";
					divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
					divH += "<input type='checkbox'  onClick='"+fun+"' name='all' id='textAllChk"+chkName+"' >全选</input>";
					divH += "</span>";
				}
			}
			
			// 图片
			if (dispalyType == -1) {
				
				for ( var j = 0; j < vArr.length; j++) {
					var vid = vArr[j].id;   // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var url =   vArr[j].propValue; // 图片url
					
					var boxValue = id+";"+vid+";"+url;
					
					
					//如果已存在货品  则默认选中已有货品的规格
					if(exitSpArray.length > 0){
						var isSelect = false;		//是否选中
						
						if(itemNo.toLowerCase() == "color" && isColor) continue;  //如果是颜色且已显示一个显示
						
						//遍历选中已有货品的规格
						for(var k = 0 ; k < exitSpArray.length ; k++){		
							var val = exitSpArray[k].split(":");
							
							if(itemNo.toLowerCase() == "color"){
								
								if(val[2] == vid){
									divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
									divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'  checked='checked'  >&nbsp;";
									divH += "<img src='" +basePath  +"/"+ url + "' alt='" + vname+ "' style='width:15px;height:15px;'/>";
									divH += "</input>";
									divH += "</span>";
									isColor = true;
									break;
								}
								
							}else{
								if(val[2] == vid){
									isSelect = true;
									break;
								}
								
							}
						}
						
						if(itemNo.toLowerCase() == "color") continue;  //如果是颜色且已显示一个显示
						
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						if(isSelect){
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'  checked='checked'  >&nbsp;";
						}else{
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'>&nbsp;";
						}
						
						divH += "<img src='" +basePath  +"/"+ url + "' alt='" + vname+ "' style='width:15px;height:15px;'/>";
						divH += "</input>";
						divH += "</span>";
						
					}else{
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'>&nbsp;";
						divH += "<img src='" +basePath  +"/"+ url + "' alt='" + vname+ "' style='width:15px;height:15px;'/>";
						divH += "</input>";
						divH += "</span>";
						
					}
				}
				// Click的调用方法
				var fun = "allChk(this," + "\"" + chkName + "\"" + ")";
				if(boxType != "radio"){
					divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
					divH += "&nbsp;&nbsp;<input type='checkbox' name='all' onClick='" + fun+ "' id='imgAllChk"+chkName+"' >全选";
					divH += "</input>";
					divH += "</span>";
				}
			}

			divH += "</form>";
		}
		// 下拉单选
		if (dispaly == 0) {
		}
		s++;
		contentHtml += divH;
		
		
		isColor = false;
	}
	contentHtml += '<li><input type="button" name="addBtn" class="btn-add-normal-4ft" onClick="addSpec()" id="addSpecButton"  value="添加货品"/></li>';
	
	var validatorSpan = "<input type='hidden' id='productTableIsValidator' value='0' />";
//	validatorSpan += "<li style='display: none' ><span id='productSellPriceTip'></span></li>";
//	validatorSpan += "<li style='display: none' ><span id='productCostPriceTip'></span></li>";
	validatorSpan += "<li style='display: none' >已存在或添加过的货品组合&nbsp;&nbsp;:&nbsp;&nbsp;<span id='productWarnTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='lengthTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='widthTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='heightTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='weightTip'></span></li>";
	contentHtml += validatorSpan;

	var divT = '<li id="commodityProductVector">';
	divT += '<input type="hidden" id="allowToAdd" value="1"/>';   //0  不允许添加     1允许添加
	divT += '<table cellpadding="0" cellspacing="0" class="list_table" style="width:99%;" >';
	divT += '<thead>';
	divT += '<tr>';
	for ( var i = 0; i < data.length; i++) {
		divT += '<th style="width:60px;">'+data[i].propItemName+'</th>';
	}
//	divT += '<th>销售价</th>';
//	divT += '<th>成本价</th>';
	divT += '<th >货品编码</th>';
	divT += '<th >供应商货品编码</th>';
	divT += '<th >货品条码</th>';
	divT += '<th >长(cm)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th >宽(cm)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th >高(cm)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th >重量(kg)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th >操作</th>';
	divT += '</tr>';
	divT += '<tr></tr>';
	divT += '</thead>';
	divT += '<tbody id="showTa"></tbody>';
	divT += '</table>';
	divT += '<center><br/><br/><img id="ajaxLoadImg" style="display:none" src="'+basePath+'/images/yitiansystem/ajax-loader.gif"/></center>';
	divT += '<br/></li>';
	
	contentHtml += divT;
	$("#specPropDiv").append(contentHtml);
	// 显示图片组
	// showCommodityImage();
	resetH();
	
	 var roleType = $("#roleType").val();
	 if(roleType == "readonly" || roleType == "readyAuditProdoct"){
		 $("#addSpecButton").hide();
	 }
}




//显示规格属性
function showSpecContent1(data) {
	var isColor = false;
	
	var s = 0;
	var prefix = "productList";
	var namePre = "chk";
	var contentHtml = "<h3>商品规格</h3>";
	for ( var i = 0; i < data.length; i++) {
		// checkbox的name属性
		var chkName = namePre + s;
		var spanId = "span" + s;
		var name = data[i].propItemName;
		var itemNo = data[i].propItemNo;
		var  boxType=  data[i].screenMethod;
		//筛选方式
		boxType = (boxType == 0) ? "radio":"checkbox";
		// 属性项id
		var id = data[i].id;
		var divH = "";
		divH += "<li><label>" + name + ":</label></li>";
		var dispaly = data[i].dispaly;
		var dispalyType = data[i].displayType;
		var vArr = data[i].value;
		// 属性项
		var productId = prefix + "[" + s + "].itemId";
		var productValue = prefix + "[" + s + "].valueId";
		
		var viewController = (vArr.length>0) ? vArr[0].selectInput : 0;
		divH += "<li id='sAr"+i+"'  boxType='"+boxType+"' viewType="+dispaly+" viewItemType="+itemNo+" viewDisplayType="+dispalyType+" viewController="+viewController+"   class='yt-fm-sx'>";
		areaArr[s] = "sAr"+i+";" + name;
		
		
		// 平铺多选
		if (dispaly == 1) {
			
			divH += "<form  style='margin:0;padding:0;'>";
			
			// 文字
			if (dispalyType == 0 || dispalyType == 1) {
				
				for ( var j = 0; j < vArr.length; j++) {
					
					var vid = vArr[j].id;  // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var text = vArr[j].propValue;   // 文本值
					
					//如果已存在货品  则默认选中已有货品的规格
					if(exitSpArray.length > 0 && (exitSpArray[0] != "")){
						var isSelect = false;		//是否选中
						
						if(itemNo.toLowerCase() == "color" && isColor) continue;  //如果是颜色且已显示一个显示
						
						//遍历选中已有货品的规格
						for(var k = 0 ; k < exitSpArray.length ; k++){		
							var val = exitSpArray[k].split(":");
							
							if(itemNo.toLowerCase() == "color"){
								
								if(val[2] == vid){
									divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
									divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"' type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "' checked='checked' >&nbsp;";
									divH +=text;
									divH += "</input>";
									divH += "</span>";
									isColor = true;
									break;
								}
								
							}else{
								if(val[2] == vid){
									isSelect = true;
									break;
								}
								
							}
						}
						
						if(itemNo.toLowerCase() == "color") continue;  //如果是颜色且已显示一个显示
						
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						if(isSelect){
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"' type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "' checked='checked' >&nbsp;";
						}else{
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"' type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "'>&nbsp;";
						}
						
						divH +=text;
						divH += "</input>";
						divH += "</span>";
					}else{
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' onClick='edit(this)' name='"+chkName+"' value='"+id+ ";"+vid+ ";"+text+ "'>&nbsp;";
						divH +=text;
						divH += "</input>";
						divH += "</span>";
					}
					
					
					
//					// 显示1，输入0
//					var vSelIn = vArr[j].selectInput;
//					if(vSelIn == 0){
//						divH +="<span>" + text + "</span>";
//					}else{
//						divH +=text;
//					}
				}
				// Click的调用方法
				if(boxType == "checkbox"){
					var fun = "allChk(this," + "\"" + chkName + "\"" + ")";
					divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
					divH += "<input type='checkbox'  onClick='"+fun+"' name='all' id='textAllChk"+chkName+"' >全选</input>";
					divH += "</span>";
				}
			}
			
			// 图片
			if (dispalyType == -1) {
				
				for ( var j = 0; j < vArr.length; j++) {
					var vid = vArr[j].id;   // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var url =   vArr[j].propValue; // 图片url
					
					var boxValue = id+";"+vid+";"+url;
					
					
					//如果已存在货品  则默认选中已有货品的规格
					if(exitSpArray.length > 0){
						var isSelect = false;		//是否选中
						
						if(itemNo.toLowerCase() == "color" && isColor) continue;  //如果是颜色且已显示一个显示
						
						//遍历选中已有货品的规格
						for(var k = 0 ; k < exitSpArray.length ; k++){		
							var val = exitSpArray[k].split(":");
							
							if(itemNo.toLowerCase() == "color"){
								
								if(val[2] == vid){
									divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
									divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'  checked='checked'  >&nbsp;";
									divH += "<img src='" +basePath  +"/"+ url + "' alt='" + vname+ "' style='width:15px;height:15px;'/>";
									divH += "</input>";
									divH += "</span>";
									isColor = true;
									break;
								}
								
							}else{
								if(val[2] == vid){
									isSelect = true;
									break;
								}
								
							}
						}
						
						if(itemNo.toLowerCase() == "color") continue;  //如果是颜色且已显示一个显示
						
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						if(isSelect){
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'  checked='checked'  >&nbsp;";
						}else{
							divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'>&nbsp;";
						}
						
						divH += "<img src='" +basePath  +"/"+ url + "' alt='" + vname+ "' style='width:15px;height:15px;'/>";
						divH += "</input>";
						divH += "</span>";
						
					}else{
						divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
						divH += "<input itemNo='"+itemNo+"' no='"+vArr[j].propValueNo+"'  type='"+boxType+"' name='"+chkName+"' value='"+boxValue+"'>&nbsp;";
						divH += "<img src='" +basePath  +"/"+ url + "' alt='" + vname+ "' style='width:15px;height:15px;'/>";
						divH += "</input>";
						divH += "</span>";
						
					}
				}
				// Click的调用方法
				var fun = "allChk(this," + "\"" + chkName + "\"" + ")";
				if(boxType != "radio"){
					divH +="<span style='display:inline-block;padding:4px;width:80px;'>";
					divH += "&nbsp;&nbsp;<input type='checkbox' name='all' onClick='" + fun+ "' id='imgAllChk"+chkName+"' >全选";
					divH += "</input>";
					divH += "</span>";
				}
			}

			divH += "</form>";
		}
		// 下拉单选
		if (dispaly == 0) {
		}
		s++;
		contentHtml += divH;
		
		
		isColor = false;
	}
	contentHtml += '<li><input type="button" name="addBtn" class="btn-add-normal-4ft" onClick="addSpec()" id="addSpecButton"  value="添加货品"/></li>';
	
	var validatorSpan = "<input type='hidden' id='productTableIsValidator' value='0' />";
//	validatorSpan += "<li style='display: none' ><span id='productSellPriceTip'></span></li>";
//	validatorSpan += "<li style='display: none' ><span id='productCostPriceTip'></span></li>";
	validatorSpan += "<li style='display: none' >已存在或添加过的货品组合&nbsp;&nbsp;:&nbsp;&nbsp;<span id='productWarnTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='lengthTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='widthTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='heightTip'></span></li>";
	validatorSpan += "<li style='display: none' ><span id='weightTip'></span></li>";
	contentHtml += validatorSpan;

	var divT = '<li id="commodityProductVector">';
	divT += '<input type="hidden" id="allowToAdd" value="1"/>';   //0  不允许添加     1允许添加
	divT += '<table cellpadding="0" cellspacing="0" class="list-table" style="width:99%;" >';
	divT += '<thead>';
	divT += '<tr>';
	for ( var i = 0; i < data.length; i++) {
		divT += '<th style="width:60px;">'+data[i].propItemName+'</th>';
	}
//	divT += '<th>销售价</th>';
//	divT += '<th>成本价</th>';
	divT += '<th>货品编码</th>';
	divT += '<th>供应商货品编码</th>';
	divT += '<th>货品条码</th>';
	divT += '<th>长(cm)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th>宽(cm)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th>高(cm)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	divT += '<th>重量(kg)&nbsp;&nbsp;<img src="../../../../images/yitiansystem/yewlloask.gif" alt="" title="最多支持3位小数" /></th>';
	//divT += '<th width="100">操作</th>';
	divT += '</tr>';
	divT += '<tr></tr>';
	divT += '</thead>';
	divT += '<tbody id="showTa"></tbody>';
	divT += '</table>';
	divT += '<center><br/><br/><img id="ajaxLoadImg" style="display:none" src="'+basePath+'/images/yitiansystem/ajax-loader.gif"/></center>';
	divT += '<br/></li>';
	
	contentHtml += divT;
	$("#specPropDiv").append(contentHtml);
	// 显示图片组
	// showCommodityImage();
	resetH();
	
	 var roleType = $("#roleType").val();
	 if(roleType == "readonly" || roleType == "readyAuditProdoct"){
		 $("#addSpecButton").hide();
	 }
}

var perIndex = 0;
// 添加规格属性的table
function addSpec() {
	
	if($('#allowToAdd').val() != "1"){
		alert('请选择当前商品款号下不存在的颜色!');
		return false;
	}
	$('#productWarnTip').empty();
	$('#productWarnTip').parent().css("display", "none");
	
	
	var propArray = new Array();
	
	var proGroups = $("li[id^='sAr']");
	for(var g = 0 ; g < proGroups.length ; g++){
		var propItem = new Array();
		
		var group = proGroups[g];
		var viewType = group.attributes["viewType"].value;
		var boxType = group.attributes["boxType"].value;
		
		//下拉单选
		if(viewType == "0"){
			
			
			
			
		}else if(viewType == "1"){ //平铺多选
			
			$("#"+group.attributes["id"].value+" :"+boxType+"[name^='chk']").each(function() {
				if (this.checked) {
					// 文字显示
					var value = $(this).val();
					var valueArr = value.split(";");
					
					//将值数组转成json对象
					var json = "({";
					for ( var i=0 ; i <valueArr.length ; i++ ) {
						json += "'"+i+"':'"+valueArr[i]+"',";
					}
					
					var nextid = "-1";  //不带输入
					if(group.attributes["viewController"].value == "1"){ //可以输入
						nextid = $($(this).next().html()).attr("id");
					}
					json += "'nextid':'"+nextid+"',";
					json += "'viewItemType':'"+group.attributes["viewItemType"].value+"',";
					//是否是图片显示
					json += "'viewDisplayType':'"+group.attributes["viewDisplayType"].value+"'";
					json +="})";
					
					propItem.push(eval(json));
				}
			});
		}
		
		propArray.push(propItem);
	}
	
	if(propArray.length > 0){
		
		var resultTableArr = buildTableRowDate(propArray);
		
		for ( var i = 0; i < resultTableArr.length; i++) {
			$("#showTa").append(addSingleTableTr(resultTableArr[i]));
		}
	}
	
	var productRows = $("input[deleteRowFlag='rowdeleteflag']").size();
	if(productRows > 0){
		$("#saveButton").show();
	}
	resetH();
}

function buildTableRowDate(propArray){
	var size = propArray.length;
	var result = new Array();
	
	var weight=new Array();
	var arrLen=1;
	for(var i=0;i<size;i++){
		
		if(propArray[i].length>0){
			arrLen=arrLen*propArray[i].length;
			weight[i]=propArray[i].length;
		}else{
		  weight[i]=1;
		}	
	}
	
	for(var i=0;i<arrLen;i++){
		
		var obj=new Array();
		var index=new Array();
		var temp=i;
		for(var k=size-1;k>=0;k--){
			
			index[k]=temp%weight[k];
			temp=(temp-index[k])/weight[k];
		}
		
		for(var j=0;j<size;j++){
			obj[j]=propArray[j][index[j]];
		}
		result[i]=obj;
	}
	return result;
}


function judgeTableKey(keyHashMap,tableIndexKey){
	
	var hasKey = false;
	
	if(keyHashMap.containsKey(tableIndexKey)){
		return true;
	}
	
	var keyArray = keyHashMap.values();
	for(var i = 0 ; i < keyArray.length ; i++){
		var tager = keyArray[i];
		var keys = tableIndexKey.split("&");
		var count = 0;
		for(var j = 0 ; j < keys.length ; j++){
			if(tager.indexOf(keys[j]) != -1){
				count ++;
			}
		}
		
		if(count == keys.lenght){
			hasKey = true;
		}
	}
	
	return hasKey;
	
}

/**
 * 向hashmap增加货品表键值
 * 
 * @param 传入的参数必须为数组
 */
function addSingleTableTr(rowDate){
	
	var paremLength = rowDate.length; // 获取被传递参数的数值
	if(paremLength < 1)  return;
	
	var tableIndexKey = "";
	for(var o = 0 ; o < paremLength; o++){
		var obj = rowDate[o];
		
		if(obj != null)
			tableIndexKey += obj["1"] + "&";
	}
	tableIndexKey = tableIndexKey.substring(0,tableIndexKey.length-1); //去掉最后的&符号
	
	if(tableIndexKey.length < 2) return ;
	
	if(tableIndexKey.split("&").length != paremLength){
		return false;
	}
	
	var prefix = "specPropList";
	var pre = prefix + "["+ perIndex + "]";
	
	if (judgeTableKey(hashMap,tableIndexKey)) {
		var warnStr = "";
		var keys = tableIndexKey.split("&"); 
		for(var i = 0 ; i < keys.length ; i++){
			var ele = $("input[value*='"+keys[i]+"']")[0];
			var value = ele.value.split(";")[2];
			warnStr += value +"&nbsp;&nbsp;-";
		}
		warnStr = warnStr.substring(0,warnStr.lastIndexOf("-"));
		warnStr += ";&nbsp;&nbsp;";
		
		$('#productWarnTip').parent().css("display", "block");
		$('#productWarnTip').append(warnStr);
		$('#productWarnTip').attr("class", "onerror");
		
	}else{
		hashMap.put(tableIndexKey,tableIndexKey);
		
		var tr = "<tr>";
		tr += "<input type='hidden' name='tableIndex' value='"+ tableIndexKey+ "'>";
		
		for(var o = 0 ; o < paremLength; o++){
			var obj = rowDate[o];
			
			if(obj == null){
				tr += "<td></td>";
				continue;
			}
			tr += "<td style='width:60px;'>";
			var itemId =obj["0"];
			var valueId =obj["1"];
			tr += "<input type='hidden' name='"+ pre+ ".specPropValueList["+o+"].itemId' value='"+ itemId + "'>";
			tr += "<input type='hidden' name='"+ pre+ ".specPropValueList["+o+"].specProvId' value='"+ valueId+ "'>";
			tr += "<input type='hidden' name='"+ pre+ ".specPropValueList["+o+"].itemType' value='"+ obj.viewItemType+ "'>";
			
//			if(obj.viewDisplayType == "1"){ //如果显示为图片
			if(obj.viewDisplayType == ""){ //如果显示为图片
				tr += "<img class='imgclo' src='"+basePath  +"/"+  obj["2"] + "' alt='' style='width:17px;height:17px;'/>";
				tr += "<input type='hidden' name='"+ pre+ ".specPropValueList["+o+"].color' value='"+ obj["2"] + "'>";
			}else if(obj.viewDisplayType == "2"){ //如果显示为颜色
				tr += "<span style='background-color:"+obj["2"]+"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
				tr += "<input type='hidden' name='"+ pre+ ".specPropValueList["+o+"].color' value='"+ obj["2"] + "'>";
			}else{ //显示文字
				var textValue =obj["2"];
				
				if(obj.nextid != -1){  //是否有输入框
					textValue= $("#" + obj["nextid"]).val();
				}
				
				tr += textValue;
				tr += "<input type='hidden' name='"+ pre+ ".specPropValueList["+o+"].specProvValue' value='"+ textValue + "'>";
			}
			tr +="</td>";
			
		}
		
//		tr += '<td><input type="text" name="'+ pre+ '.sellPrice" size="10" maxLength="15" validatorFile="sellPrice" >元</td>';
//		tr += '<td><input type="text" name="'+ pre+ '.costPrice" size="10" maxLength="15" validatorFile="costPrice" >元</td>';
		
		tr += '<td style="width:120px;">&nbsp;</td>';
		tr += '<td style="width:120px;"><input style="width:90%" type="text"  name="'+ pre+ '.thirdPartyCode"  maxLength="100"    validatorFile="thirdPartyCode" /></td>';
		tr += '<td style="width:120px;"><input style="width:90%" type="text"  name="'+ pre+ '.insideCode"  maxLength="100"  validatorFile="insideCode"/></td>';
		tr += '<td style="width:70px;"><input style="width:90%" type="text"  value="0" name="'+ pre+ '.length"  maxLength="10"  validatorFile="length"/></td>';
		tr += '<td style="width:70px;"><input style="width:90%" type="text"  value="0" name="'+ pre+ '.width"   maxLength="10"  validatorFile="width"/></td>';
		tr += '<td style="width:70px;"><input style="width:90%" type="text"  value="0" name="'+ pre+ '.height"  maxLength="10"  validatorFile="height"/></td>';
		tr += '<td style="width:70px;"><input style="width:90%" type="text"  value="0" name="'+ pre+ '.weight"  maxLength="10"  validatorFile="weight"/></td>';
		tr += "<td class='td0'>";
		tr += '<input type="hidden" deleteRowFlag="rowdeleteflag" value="0"/>';
		tr += "<img src='"+ basePath+ "/images/yitiansystem/del-class.gif' style='cursor:pointer;' onClick='removeTr(this)' alt=''/></td>" ;
		
		tr +="</tr>";
		
		perIndex ++;
		return tr;
	}
}


//异步请求brand
function loadBrand(selId,catStruct,brandNo) {
	$.ajax({
		type : "POST",
		url : "getChildBrand.sc",
		data : {
			"value" : catStruct
		},
		dataType : "json",
		success : function(data) {
			$("#" + selId).empty();// 清空下来框
			$("#" + selId).append("<option value='0'>品牌名称</option>");
			for ( var i = 0; i < data.length; i++) {
				
				var option = "";
				if(brandNo == data[i].brandNo){
					option = "<option  selected='selected'  value='" + data[i].id + "'>"+ data[i].brandName + "</option>";
					$("#brandId").val(data[i].id);
					$("#brandName").val(data[i].brandName);
				}else{
					option = "<option value='" + data[i].id + "'>"+ data[i].brandName + "</option>";
				}
				$("#" + selId).append(option);
			}
		}
	});
}


function validatorProduct() {

	var validatorResult = true;
	var mesg = "";

//	//货品销售价格验证
//	mesg = "销售价格格式不正确，销售价格必须是数字。";
//	var sellPriceRs = validatorInputFile('sellPrice',/^([+-]?)\d*\.?\d+$/,mesg,"productSellPriceTip");
//	validatorResult = sellPriceRs ? validatorResult : sellPriceRs;
//
//	//成本价格验证
//	mesg = "成本价格格式不正确，成本价格必须是数字。";
//	var costPriceRs = validatorInputFile('costPrice',/^([+-]?)\d*\.?\d+$/,mesg,"productCostPriceTip");
//	validatorResult = costPriceRs ? validatorResult : costPriceRs;
	
	//长度验证
	mesg = "长度格格式不正确，必须是数字。";
	var lengthRs = validatorInputFile('length',/^([+-]?)\d*\.?\d+$/,mesg,"lengthTip");
	validatorResult = lengthRs ? validatorResult : lengthRs;

	
	
	//宽度验证
	mesg = "宽度格式不正确，必须是数字。";
	var widthRs = validatorInputFile('width',/^([+-]?)\d*\.?\d+$/,mesg,"widthTip");
	validatorResult = widthRs ? validatorResult : widthRs;
	
	
	//高度验证
	mesg = "高度格式不正确，必须是数字。";
	var heightRs = validatorInputFile('height',/^([+-]?)\d*\.?\d+$/,mesg,"heightTip");
	validatorResult = heightRs ? validatorResult : heightRs;
	
	
	//重量验证
	mesg = "重量格式不正确，必须是数字且小数点前位数最大5位。";
	var weightRs = validatorInputFile('weight',/^([+-]?)\d*\.?\d+$/,mesg,"weightTip");
	validatorResult = weightRs ? validatorResult : weightRs;
	
	//验证货品条码不能为null
	mesg = "货品条码不能为null，如果条码暂时先不存在，请输入一个假条码，后续再入库";
	return validatorResult;

}

/**
 * 验证多行文本框信息
 * @param file  validatorFile 名称
 * @param reg	验证规则  正则表达式
 * @param errorMsg
 * @param mesTipId
 */
function validatorInputFile(file,reg,errorMsg,mesTipId){
	
	var validatorResult = true;

	// 货品销售价格验证
	var validatorNodes = $("input[validatorFile="+file+"]");
	var mesg = "";
	for ( var i = 0; i < validatorNodes.length; i++) {
		var value = validatorNodes[i].value;
		if (!reg.test(value)) {
			mesg += "第" + (i + 1) + "行 , ";
		}
	}

	$('#'+mesTipId).empty();
	if (mesg.length > 2) {
		$('#'+mesTipId).parent().css("display", "block");
		$('#'+mesTipId).append(mesg + errorMsg);
		$('#'+mesTipId).attr("class", "onerror");
		validatorResult = false;
	} else {
		$('#'+mesTipId).attr("class", "");
	}
	
	return validatorResult;
}

function addspecvalue(id){
	var commdityid=$("#commodityId").val();
	var catb2cid=$("#catb2cID").val();
	var params="id="+id+"&isSpec="+1+"&commodityId="+commdityid+"&catb2cID="+catb2cid;
	showThickBox("新增属性值","../../commoditymgmt/commodityinfo/productlist/toPropItemEdit.sc?TB_iframe=true&height=550&width=750",false,params);
}