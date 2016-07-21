var basePath = null;
$(function(){
	basePath = $('#basePath').val();
	var catId = $('#catId').val();
	
	ajaxExtendProp(catId);
	ajaxSpecProp(catId);
	getHasSamePropValueCatId(catId);
})

// 异步请求扩展属性
function ajaxExtendProp(catId) {
	$.ajax({
		type : "POST",
		url : "getExtendProp.sc",
		data : {
			"catId" : catId,
			"isLimit" : '0'
		},
		dataType : "json",
		success : function(data) {
			// 页面展示内容
			if (data != null) {
				showExtendProp(data);
			}
		}
	});
}

// 异步请求规格属性
function ajaxSpecProp(catId) {
	$.ajax({
		type : "POST",
		url : "getSpecProp.sc",
		data : {
			"catId" : catId,
			"isLimit" : '0'
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

//根据当前id获取所有的与该分类有相同属性值的分类
function getHasSamePropValueCatId(catId){
	$.ajax({
		type : "POST",
		url : "getHasSamePropValueCatId.sc",
		data : {
			"catId" : catId
		},
		dataType : "json",
		success : function(data) {
			// 页面展示内容
			if (data != null) {
				showSameValueContent(data);
			}
		}
	});
}

function showExtendProp(data) {
	var prefix = "extendPropList";
	var contentHtml = "<h3>商品属性</h3>";
	for ( var i = 0; i < data.length; i++) {
		if (data[i] == null) {
			continue;
		}
		var divH = "<li><label>";
		var name = data[i].propGroupName;
		var item = data[i].items;
		var pre = prefix + "[" + i + "]";
		divH += name + ":</label></li>";
		for ( var j = 0; j < item.length; j++) {
			
			var iName = item[j].propItemName;
			var iNo = item[j].propItemNo;
			divH += "<li  class='yt-fm-sx'>";
			divH += iName + "：</li><li class='yt-fm-sx' style='padding-left:144px;'>";
			var value = item[j].values;
				if(value == null) break;
				
				for ( var k = 0; k < value.length; k++) {
					var vname = value[k].propValueName;
					var isChecked = value[k].isChecked;
					var vNo = value[k].propValueNo;
					var chkT = "";
					var propValue = iName + '_' + iNo + '_' + vname + '_' + vNo;
					
					if (isChecked == 0) {
						chkT = "<span style='display:inline-block;padding:4px;'><input type='checkbox' name='propValue' value='"+ propValue +"'>&nbsp;&nbsp;" + vname+"</input>";
					} else {
						chkT = "<span style='display:inline-block;padding:4px;'><input type='checkbox'  name='propValue' checked='checked' value='"+ propValue +"' >&nbsp;&nbsp;" + vname+"</input>";
					}
					divH += chkT + '</span>';
					divH += "&nbsp;&nbsp;";
				}
			}
			contentHtml += divH+'</li>';
		}
	$("#extendPropDiv").append(contentHtml);
	resetH();
}

//显示规格属性
function showSpecContent(data) {
	var s = 0;
	var prefix = "productList";
	var contentHtml = "<h3>商品规格</h3>";
	for ( var i = 0; i < data.length; i++) {
		// checkbox的name属性
		var name = data[i].propItemName;
		var no = data[i].propItemNo;
		// 属性项id
		var divH = "";
		divH += "<li><label>" + name + ":</label></li>";
		var dispalyType = data[i].displayType;
		var vArr = data[i].values;
		
		var viewController = (vArr.length>0) ? vArr[0].selectInput : 0;
		divH += "<li class='yt-fm-sx'>";	
			
			// 颜色
			if (dispalyType == 22) {
				for ( var j = 0; j < vArr.length; j++) {
					var vid = vArr[j].id;   // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var vno = vArr[j].propValueNo;  // 属性值编码
					var vurl = "#FFF000";  vArr[j].propValueUrl; // 颜色值
					var isChecked = vArr[j].isChecked;//是否选中
					var value = name + '_' + no + '_' + vname + '_' + vno + '_' + vurl;
					
					if(isChecked == 0){
						divH += "<span style='display:inline-block;padding:4px;width:80px;'><input type='checkbox' name='propValue' value='"+value+"'/></span>";
					}else{
						divH += "<span style='display:inline-block;padding:4px;width:80px;'><input type='checkbox' name='propValue' checked='checked' value='"+value+"'/></span>";
					}
					+"&nbsp;<span style='background-color:"+vurl+";'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;";
				}
			}
			
			// 图片
			if (dispalyType == 11) {
				for ( var j = 0; j < vArr.length; j++) {
					var vid = vArr[j].id;   // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var vno = vArr[j].propValueNo;  // 属性值编码
					var vurl =   vArr[j].propValueUrl; // 图片url
					var isChecked = vArr[j].isChecked;//是否选中
					var value = name + '_' + no + '_' + vname + '_' + vno + '_' + vurl;
					if(isChecked == 0){
						divH += "<span style='display:inline-block;padding:4px;width:80px;'><input type='checkbox' name='propValue' value='"+value+"'/></span>";
					}else{
						divH += "<span style='display:inline-block;padding:4px;width:80px;'><input type='checkbox' name='propValue' checked='checked' value='"+value+"'/></span>";
					}
					+"<img src='" +basePath  +"/"+ vurl + "' alt='" + vname+ "' style='width:15px;height:15px;'/>&nbsp;&nbsp;";
				}
			}

			// 文字(暂时同一显示一种方式)
			if (dispalyType == 0 || dispalyType == 1 || dispalyType ==2) {
				for ( var j = 0; j < vArr.length; j++) {
					var vid = vArr[j].id;  // 属性值id
					var vname = vArr[j].propValueName;  // 属性值名称
					var vno = vArr[j].propValueNo;  // 属性值编码
					var isChecked = vArr[j].isChecked;//是否选中
					var value = name + '_' + no + '_' + vname + '_' + vno;
					if(isChecked == 0){
						divH += "<span style='display:inline-block;padding:4px;width:80px;'><input type='checkbox' name='propValue' value='"+value+"'/>&nbsp;&nbsp;";
					}else{
						divH += "<span style='display:inline-block;padding:4px;width:80px;'><input type='checkbox' name='propValue' checked='checked' value='"+value+"'/>&nbsp;&nbsp;";
					}
					divH +=vname + '</span>';
					divH += "&nbsp;&nbsp";
				}
			}
		
		s++;
		contentHtml += divH;
	}

	$("#specPropDiv").append(contentHtml);
	resetH();
}

//显示与当前分类有相同的属性项的分类
function showSameValueContent(data){
	var divH = '';
	for(var i = 0; i < data.length; i++){
		if (data[i] == null) {
			continue;
		}
		divH += '<div style="padding-left:10px;padding-top:10px;">'
		for(var j = 0; j < data[i].length; j ++){
			if(j == 0){
				divH += '<font style="color:#6A6A6A;font-weight:bold;">'+data[i][j].propValue+'</font>';
				if(data[i][j].propValue == '已绑定'){
					divH += '<font class="ft-cl-r">&nbsp;&nbsp;&nbsp;( 选择已绑定的分类，则会覆盖以前所选 )</font>';
				}
				divH += '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			}
			divH += "<span style='display:inline-block;padding:4px;width:80px;'>"
				+ "<input type='checkbox' name='catId' value='"+data[i][j].catId+"'/>&nbsp;&nbsp;"+ data[i][j].catName +"</span>";
		}
		divH += '</div><br>';
	}
	var contentHtml = "<hr style='padding-top:20px; border:none; border-bottom:1px solid #eee; height:1px;'/><h4 style='padding-top:10px;'>该分类与以下分类有相同的属性值，是否应用于以下分类：</h4>";
	if(divH != ''){
		$("#catNameDiv").append(contentHtml + divH);
	}
}


//获取选中的分类名称，并提交表单
function c_catProp(){
	$('input[type=checkbox][name=catId]:checked').each(function(){
		
		var catName = $.trim($(this)[0].nextSibling.nodeValue);
		$('#catPropForm').append('<input type="hidden" name="catName" value='+ catName +'></input>');
	})
	$('#catPropForm').submit();
}