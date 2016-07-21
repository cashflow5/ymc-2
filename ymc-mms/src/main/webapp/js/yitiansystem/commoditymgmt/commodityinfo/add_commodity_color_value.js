
//初始化基路径
var basePath;
var addColorHtml;

$(function(){
   basePath = $('#basepath').val() + '/yitiansystem/commoditymgmt/commodityinfo/colorvalue/';
   addColorHtml = '<span><li><div></div>颜色名称:<font class="ft-cl-r">*</font> <input name="colorNames" maxLength="25"/>'
	    + '颜色编码:<font class="ft-cl-r">*</font> <input name="colorNos" maxLength="25"/>'
	    + '排序： <input name="sortNos" maxLength="9" size="10"/><font class="ft-cl-r">*</font><input type="file" name="colorImgUrl" onchange="showpic(this)"/>'
	    + '<img onclick="deleteColor(this)" src="'+ $('#basepath').val() +'/images/delete.gif"/></li></span>';
});

//点击添加颜色按钮
function addColor(){
	$('#colorValueUl').append(addColorHtml);
}

//点击删除图片
function deleteColor(e){
	$(e).parent().remove();
}

//检查颜色的合法性
function checkColor(){
	if($('input[name^=color]').lenght == 0){
		alert('请点击添加颜色');
		return;
	}
	
	//存贮颜色名称跟编码的数组
	var nameArray = new Array();
	var noArray = new Array();
	
	var hasNull = false;
	$('input[name^=color]').each(function(){
		if($.trim($(this).val()) != ''){
			if($(this).hasClass('store-input-err')){
				$(this).removeClass('store-input-err');
			}
		}else{
			hasNull = true;
			if(!$(this).hasClass('store-input-err')){
				$(this).addClass('store-input-err');
			}
		}
		
		if($(this).attr('name') == 'colorNames'){
			nameArray.push($.trim($(this).val()));
		}else{
			noArray.push($.trim($(this).val()));
		}
		
	})
	
	if(hasNull){
		nameArray = null;
		noArray = null;
	}else{
		//前台验证名称及编码的唯一性
		if(nameArray.length == arrayToSet(nameArray.length)){
			if(noArray.length == arrayToSet(noArray).length){
				var colorValueName = nameArray.toString();  //颜色名称
				var colorValueNo = noArray.toString();  //颜色编码
				var result = checkNameAndNo(colorValueName, colorValueNo);
				if(result){
					$('#colorValueForm').submit();
				}else{
					nameArray = null;
					noArray = null;
				}
			}else{
				alert('颜色编码不能重复');
			}
		}else{
			alert('颜色名称不能重复');
		}
	}
}

//后台验证名称及编码的唯一性
function checkNameAndNo(names, nos){
	var result = false;
	$.ajax({
		type : "POST",
		url : basePath + "checkNameAndNo.sc",
		data : {
			"colorNames" : names,
			"colorNos" : nos
		},
		dataType : "json",
		async : false,
		success : function(data){
			console.log(data[0].result);
			if(data[0].result == '-1'){
				result = true;
			}else{
				alert(data[0].result);
			}
		}
	});
	return result;
}


//去除重复
function arrayToSet(array){
	var a = {};
	for(var i=0; i<array.length; i++){
		if(typeof a[array[i]] == "undefined")
			a[array[i]] = 1;
	}
	
	array.length = 0;
	for(var i in a)
		array[array.length] = i;
	return array;
} 


/*显示图片*/
function showpic(e){

	var dFile = $(e)[0];
	var div = $(e).parent().parent().find('div');
	if(!dFile.value.match(/.jpg|.gif|.png|.bmp/i)){
		alert('图片类型必须是: .jpg, .gif, .bmp or .png !');
		return;
	}
	if(dFile.files){
		div.empty();
		var image = $("<img>");
		var imgData = dFile.files[0].getAsDataURL();
		image.attr("src", imgData);
		image.attr("id", "imgid");
		image.attr("width","10");
		image.attr("height","10");
		div.append(image);
	}
	else{
		/*这步骤是用来在ie6,ie7中显示图片的*/
		div.empty();
		div[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dFile.value;
		div[0].style.width = "10px";
		div[0].style.height = "10px";
	}
}	