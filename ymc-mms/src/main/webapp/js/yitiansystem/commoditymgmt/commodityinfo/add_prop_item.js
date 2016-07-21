
var config={
	form:"propItemFrom",submit:submitForm,
 	fields:[
		{name:'propItemName',allownull:false,regExp:"notempty",focusMsg:'请输入属性项名称',errorMsg:'属性项名称不正确',msgTip:'propItemNameTip'},
		{name:'sortNo',allownull:true,regExp:"intege1",focusMsg:'请输入排序',rightMsg:'输入格式正确',errorMsg:'排序只能位数字',msgTip:'sortNoTip'}
	]
}

Tool.onReady( function() {
	var f = new Fw(config);
	f.register();
});

function submitForm(){
	var result = checkPropItem();
	if(result){
		addHidden('propItemFrom');
	}else{
		propValue = '';
		propValueNo = '';
	}
	return result;
}

/**
 * 选分类时，判断是否先选属性组
 */
function selectPropGroupFirst(){
	if(!getPropGroupId || getPropGroupId() == 0){
		alert('请先选择属性组');
	}
}

/**
 * 获取属性组的id
 * @return
 */
function getPropGroupId(){
	var spec = $('#specPropGroup').is(":visible");
	var extend = $('#extendPropGroup').is(":visible");
	if(spec){
		return $('#specPropGroup').val();
	}else{
		return $('#extendPropGroup').val();
	}
}

/**
 * 根据属性组id，获取分类
 */ 
function getCatByPropGroup(isSpec, level, structName){
	var propGroupId = getPropGroupId();
	if(propGroupId != 0){
		
		$.ajax({
			type: "POST",
			url: path+"/yitiansystem/commoditymgmt/commodityinfo/propitem/getCatByPropGroup.sc",
			data:{
				"isSpec":isSpec,	
				"propGroupId":propGroupId,	
				"catLevel":level,	
				"structName":structName	
			},
			 dataType:"json", 
			 success: function(data){
				 //清空下拉框
				 if(level == 1){
					 $('#rootCattegory').empty();
					 $('#rootCattegory').append("<option value='0'>请选择大类</option>");
					 $('#secondCategory').empty();
					 $('#secondCategory').append("<option value='0'>请选择二级分类</option>");
					 $('#threeCategory').empty();
					 $('#threeCategory').append("<option value='0'>请选择三级分类</option>");
					 //添加值
					 for(var i=0; i<data.length ; i ++){
						 var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
		               	 $('#rootCattegory').append(option);
		             }
				 }else if(level == 2){
					 $('#secondCategory').empty();
					 $('#secondCategory').append("<option value='0'>请选择二级分类</option>");
					 $('#threeCategory').empty();
					 $('#threeCategory').append("<option value='0'>请选择三级分类</option>");
					 
					 for(var i=0; i<data.length ; i ++){
						 var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
		               	 $('#secondCategory').append(option);
		             }
				 }else if(level == 3){
					 $('#threeCategory').empty();
					 $('#threeCategory').append("<option value='0'>请选择三级分类</option>");
					 for(var i=0; i<data.length ; i ++){
						 var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
		               	 $('#threeCategory').append(option);
		             }
				 }
			 }
		});
	}
} 
 
/**
 * 显示规格或扩展属性组的下拉值
 * @param id1
 * @param id2
 * @return
 */

function showPropGroup(id1, id2){
	$('#' + id1).show();
	$('#' + id1).val("0");
	$('#' + id2).hide();
	$('#' + id2).val("0");
	if(lastIsSpec != id1){
		lastIsSpec = id1;
		$('#rootCattegory').empty();
		$('#rootCattegory').append("<option value='0'>请选择大类</option>");
		$('#secondCategory').empty();
		$('#secondCategory').append("<option value='0'>请选择二级分类</option>");
		$('#threeCategory').empty();
		$('#threeCategory').append("<option value='0'>请选择三级分类</option>");
		$('#showCat').empty();
		arr = new Array();
	}
	if('specPropGroup' == id1){
		$('#displayPic').attr('disabled',false);
	}else{
		if($('#displayPic').attr('checked')){
			$('#showPropValue').empty();
			$('#displayText').attr('checked',true);
		}
		$('#displayPic').attr('disabled',true);
	}
}

 //去除重复
function arrayToSet(array)
 {
   var a = {};
    for(var i=0; i<array.length; i++)
   {
     if(typeof a[array[i]] == "undefined")
       a[array[i]] = 1;
   }
   array.length = 0;
   for(var i in a)
	   array[array.length] = i;
   return array;
} 
 
 /*
  * 检查新增的属性值是否不为空，是则非法
  */
function checkItemValue(){
	var propGroupId = getPropGroupId();
	if(!propGroupId || propGroupId == 0){
		alert("属性组不能为空");
		return true;
	}
	 if($('#showCat li').length==0){
		 alert("分类不能为空，请选择分类！");
		 return true;
	 } 
	 var nameFlag = false;//名称为空验证
	 var picFlag = false;//图片为空验证
	 var noFlag = false;//编码为空验证
	 var propValueLength = $('#showPropValue input[name="propValue"]').length;
	 if(propValueLength == 0){
		 alert("属性值不能为空");	
		 return true;
	 }
	 
	 var nameArray = new Array(); 
	 var noArray = new Array(); 
	$('#showPropValue input[name="propValue"]').each(function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g,"") == ""){
			nameFlag = true;
		}
		propValue += $(this).val() + '`';
		nameArray.push($(this).val());
	})
	
	$('#showPropValue input[name="propValueImg"]').each(function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g,"") == ""){
			picFlag = true;
		}
	})
	$('#showPropValue input[name="propValueNo"]').each(function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g,"") == ""){
			noFlag = true;
		}
		propValueNo += $(this).val() + '`';
		noArray.push($(this).val());
	})
	if(nameFlag){
		alert("属性值名称不能为空");
		return true;
	}
	if(picFlag){
		alert("图片不能为空");
		return true;
	}
	if(noFlag){
		alert("属性值编码不能为空");
		return true;
	}
	if(arrayToSet(nameArray).length != propValueLength){
		alert("属性值名称不能重复");
		return true;
	}
	if(arrayToSet(noArray).length != propValueLength){
		alert("属性值编码不能重复");
		return true;
	}
	var resultVo = checkPropValueNo();
	return resultVo;
//	return false;
} 


  /*
  * 后台验证属性值编码的重复
  */ 
 function checkPropValueNo(){
 	var result1 = true;
 	var isSpec = $("input[type='radio'][name='isSpec']:checked").val();
 	 $.ajax({
       type: "POST",
       async:false,
       url: path+"/yitiansystem/commoditymgmt/commodityinfo/propitem/checkPropValueNo.sc",
       data:{
 		 	"isSpec":isSpec,
 			"propValueNos":propValueNo
 		},
       dataType:"json", 
       success: function(data){
       	if(data[0].flag == "-1"){
       		result1 = false;
       	}else{
       		alert("属性值编码   " + data[0].flag + " 已经存在，请重新输入新编码");
       	} 
       }
    });
 	return result1;
 }   
  
/*
 * 检验属性项名称的可用行
 */
function checkPropItem(){
	var propItemName = $("#propItemName").val();
	if(propItemName == ""){
		return false;
	}
	if(checkItemValue()){
		return false;
	} 
	var propGroupId = getPropGroupId();
	var isSpec = $("input[type='radio'][name='isSpec']:checked").val();
	return sendAjax(isSpec, propItemName, propGroupId, 'add');
}
 
function sendAjax(isSpec, propItemName, propGroupId, action){
	var result = false;
	 $.ajax({
        type: "POST",
        async:false,
        url: path+"/yitiansystem/commoditymgmt/commodityinfo/propitem/checkPropItem.sc",
        data:{
			"isSpec":isSpec,
			"propGroupId":propGroupId,
			"propItemName":propItemName
		},
        dataType:"json", 
        success: function(data){
        	if(data[0].nameLength == 0){
        		result = true;
        	}else if(data[0].nameLength == 1){
        		alert("该属性项名称已经存在，请重新输入");
        	} 
        }
      });
	 return result;
 }

/**
 * 删除已经添加的属性值
 * @return
 */
function deletePropValue(e){
	$(e).parent().remove();
}

/**
 * 显示类型改变时，清空内容
 */
var lastClickType = ''; //上一次点击的类型：0文字或1图片
function clickDisplayType(index){
	if(index != lastClickType){
		if(lastClickType == '1'){
			if(confirm('由图片类型改为文字类型将会清空已上传的图片，是否改变？')){
				//清空上传控件及上传图片
				$('input[type=file][name=propValueImg]').each(function(){
					$(this).parents('li').find('div').empty();
					$(this).remove();
				})
				lastClickType = index;
				
			}else{
				$('input[name=displayType][value='+ lastClickType +']').attr('checked',true);
			}
		}else{
			//增加上传控件
			$('input[name=propValueNo]').each(function(){
				$(uploadPicHtml).insertAfter($(this));
			})
			lastClickType = index;
		}
	 }
}

/**
* 新增添加的属性值
* @return
*/
function addPropValue(){
	var displayValue = $('input[name="displayType"]:checked').val();
	var canAdd = true;
	$('#showPropValue input[name="propValue"]').each(function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g,"") == ""){
			alert("添加的名称不能为空");
			canAdd = false;
		}
	})
	$('#showPropValue input[name="propValueNo"]').each(function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g,"") == ""){
			if(canAdd){
				alert("添加的编码不能为空");
				canAdd = false;
			}
		}
	})
	$('#showPropValue input[name="propValueImg"]').each(function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g,"") == ""){
			if(canAdd){
				alert("添加的图片不能为空");
				canAdd = false;
			}
		}
	})
	if(canAdd && displayValue == 0){
		$('#showPropValue').append(displayTextHtml);
	}else if(canAdd && displayValue == 1){
		$('#showPropValue').append(displayPictureHtml);
	}
	resetH();
}

var displayTextHtml = null;
var displayPictureHtml = null;
var uploadPicHtml = '&nbsp;&nbsp;&nbsp;<input type="file" name="propValueImg" onchange="showpic(this)"/>';
var propValue = '';//新增的属性值名称
var propValueNo = '';//新增的属性值编码 
var i = 0;
var subi = 0;
var lastIsSpec = null;
// 存储选中集合的val
var arr = new Array();

  $(document).ready(function(){
	  lastClickType = $('input[name="displayType"]:checked').val();
	  lastIsSpec = 'extendPropGroup';
	  $('#displayPic').attr('disabled',true);//默认隐藏是可扩展属性项应不可选显示图片
	  $('#specPropGroup').hide();//隐藏可选的规格属性组的值
	  path = $("#basepath").val();
	  displayTextHtml = '<span><li><div></div>属性值名称:<font class="ft-cl-r">*</font> <input name="propValue" type="text" maxLength="25"/>属性值编码:<font class="ft-cl-r">*</font> <input name="propValueNo" type="text" maxLength="25"/><img onclick="deletePropValue(this)" src="'+ path +'/images/delete.gif"/></li></span>';
	  displayPictureHtml = '<span><li><div></div>属性值名称:<font class="ft-cl-r">*</font> <input name="propValue" type="text" maxLength="25"/>属性值编码:<font class="ft-cl-r">*</font> <input name="propValueNo" type="text" maxLength="25"/><input type="file" name="propValueImg" onchange="showpic(this)"/><img onclick="deletePropValue(this)" src="'+ path +'/images/delete.gif"/></li></span>';

	  $("#rootCattegory").change(function(){
		  //选中项的value值
		  var selValue = $(this).children('option:selected').val() ;
		  var isSpec = $("input[type='radio'][name='isSpec']:checked").val();
		  if(selValue !="0"){
			  getCatByPropGroup(isSpec, 2, selValue);
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
		var isSpec = $("input[type='radio'][name='isSpec']:checked").val();
		if(selValue !="0"){
			getCatByPropGroup(isSpec, 3, selValue);
		}else{
			$("#threeCategory").empty();// 清空下来框
			$("#threeCategory").append("<option value='0'>请选择三级分类</option>");
		}
	})
	
	
	$('select[id$=PropGroup]').change(function(){
		var isSpec = $("input[type='radio'][name='isSpec']:checked").val();
		var selValue = $(this).val();
		if(selValue != 0){
			getCatByPropGroup(isSpec, 1, '');
		}else{
			$('#rootCattegory').empty();
			$('#rootCattegory').append("<option value='0'>请选择大类</option>");
			$('#secondCategory').empty();
			$('#secondCategory').append("<option value='0'>请选择二级分类</option>");
			$('#threeCategory').empty();
			$('#threeCategory').append("<option value='0'>请选择三级分类</option>");
		}
		$('#showCat').empty();
		arr = new Array();
	})
	  
});

  
	  // 添加新分类
	  function addNewCat(){
	  	//一级分类的选中的值和文本
	  	var rootVal = $('#rootCattegory').children('option:selected').val();
	  	var rootText = $('#rootCattegory').children('option:selected').text();
	  	// 二级分类选中的值和文本
	  	var secondVal = $('#secondCategory').children('option:selected').val();
	  	var secondText = $('#secondCategory').children('option:selected').text();
	  	// 三级分类选中的值和文本
	  	var threeVal = $('#threeCategory').children('option:selected').val();
	  	var threeText = $('#threeCategory').children('option:selected').text();
	  	var rowid = "row" + i;
	  
	  	// var li = "<li id='"+rowid+"'><span>" ;
	  	var removeF = "removeLiTX(this)";
	  	// var removeF = "removeLi('"+rowid+"')";
	  	var isNotAdd =false;
		if(rootVal == "0"){
			alert("请选择大分类");
		}else if(rootVal != "0"&&secondVal=="0"){
			alert("请选择二级分类");
		}else if(threeVal == "0"){
			alert("请选择三级分类");
		}else{
			var res = threeVal;
			if(threeVal == 0){
				res = secondVal;
			}
			if(null!=arr&&arr.length>0){
				$.each(arr,function(i,n){
					if(res==n){
						isNotAdd = true;
					}
				})
				if(isNotAdd){
					alert("已添加该项");
				}else{
					var li = "<li id="+res+">" ;
					li += rootText +"" ;
					li += "  >  "+secondText;
					if(threeVal != 0){
						li += "  >  "+threeText;
					}
					arr.push(res);
					li += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
					$("#showCat").append(li);
					
				}
			}else if(null!=arr&&arr.length==0){
				var li = "<li id="+res+">" ;
				li += rootText ;
				li += "  >  "+secondText;
				if(threeVal != 0){
					li += "  >  "+threeText;
				}
				arr.push(res);
				li += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
				$("#showCat").append(li);
			}
			resetH();
		}
	  };
	  
	  // 批量添加子分类
	  function addSubNewCat(){
		  
		//一级分类的选中的值和文本
		  	var rootVal = $('#rootCattegory').children('option:selected').val();
		  	var rootText = $('#rootCattegory').children('option:selected').text();
		  // 二级分类的选中的值和文本
		  	var secondVal = $('#secondCategory').children('option:selected').val();
		  	var secondText = $('#secondCategory').children('option:selected').text();
		  
		  	 // 三级分类的选中的值和文本
		  	var threeVal= $('#secondCategory').children('option:selected').val();
		  	var threeText= $('#secondCategory').children('option:selected').text();
		  // 一级分类的所有的的值和文本
		  	var roots = $('#rootCattegory').children();
		  	// 二级分类所有的值和文本
		  	var seconds = $('#secondCategory').children();
		  	// 三级分类所有的值和文本
		  	var threes = $('#threeCategory').children();
		  	var removeF = "removeLiTX(this)";
		  	
		  	
		  	// 当三个分类都不选择的时候，批量添加1级分类
		  	if(rootVal == "0"){
				alert("请选择大分类");
		  	}else if(rootVal != "0"&&secondVal=="0"){
				alert("请选择二级分类");
		  	}else{
		  	//这种情况就添加所有的一级下的三级的所有列表
		  		
		  		
		  		
		  		if(null!=arr&&arr.length>0){
		  			
		  			$.each(threes, function(i, m){
		  				var isNAdd = false ;
		  				if(m.value!='0'){
		  					$.each(arr,function(y,n){
				  				if(m.value==n){
				  					isNAdd = true;
				  				}
				  			})
				  			
				  			
				  			if(!isNAdd){
			  					var singleli = "<span><li id="+m.value+"><span>" ;
			  					singleli += rootText +"</span>";
			  					singleli += "  >  "+"<span>"+secondText+"</span>" ;
			  					singleli += "  >  "+"<span>"+m.text+"</span>" ;
			  					singleli  += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
			  					singleli += "</span>";
			  					arr.push(m.value);
			  					$("#showCat").append(singleli);
			  					
			  				}
		  				}
		  			})
		  		}else if(null!=arr&&arr.length==0){
		  			$.each(threes, function(i, m){
		  				if(m.value!='0'){
		  					var singleli = "<span><li id="+m.value+"><span>" ;
		  					singleli += rootText +"</span>";
		  					singleli += "  >  "+"<span>"+secondText+"</span>" ;
		  					singleli += "  >  "+"<span>"+m.text+"</span>" ;
		  					singleli  += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
		  					singleli += "</span>";
		  					arr.push(m.value);
		  					$("#showCat").append(singleli);
		  				}
		  			})
		  			
		  		}
		  	resetH();
		  	}
		  	
	  }
	  
	  
	
	 
	//删除分类行
		function  removeLiTX(obj){
			var rowId = $(obj).parent().attr("id");
			$("#"+rowId).remove();
			$.each(arr,function(i,n){
				if(n==rowId){
					arr.splice(i,1);
				}
			});
		}
	  
	  
	  
	//删除分类行
	function  removeLiT(obj){
		var rowId = $(obj).parent().attr("id");
		$("#"+rowId).remove();
		renameLi();
		// rowId后缀，从0开始，并转为数字
		var rowIndex = rowId.substring(3,rowId.length) * 1;
		// 行号和数组的索引号对应
		// alert("移除前"+arr);
		// alert(rowIndex);
		arr.splice(rowIndex,1);
		// alert("移除后"+arr);
	}
	
	//重新索引
	function renameLi(){
		$("#showCat li").each(function(i){
			this.id = "row" + i ;
		});
	}
//
// 	//提交按钮所在的表单
//	function postForm(formId, url){
//		$("#"+formId).attr("action",url);
//		// 添加分类的hidden到form
//		addHidden(formId);
//		$("#"+formId).submit();
//	}
//	
	//在表单中添加hidden
	function addHidden(formId){
		//遍历arr数组
		var i = 0;
		for(var v in arr){
			var tName = "structName" ;
			var hdn = "<input type='hidden' name='"+tName+"' value='"+arr[i]+"'/>";
			i++;
			$("#"+formId).append(hdn);
		}
		$("#"+formId).append('<input type="hidden" name="keywords" value="'+ propValue +'"/>');
		$("#"+formId).append('<input type="hidden" name="valueNos" value="'+ propValueNo +'"/>');
		$("#"+formId).append('<input type="hidden" name="propGroupId" value="'+ getPropGroupId() +'"/>');
	}	

	
/**
 * 属性组单选按扭点击事件
 */	
function proGroupOnclick(url,param,selectId){
	$("#propgroupid").show();
	$.ajax({
		type : "POST",
		url : url,
		data : {"type" : param},
		dataType : "json",
		cache : false,
		success : function(data) {
			 $("#"+selectId).empty();// 清空下来框
			 $("#"+selectId).append("<option selected='selected'>请选择</option>");
			 for(var i=0; i<data.length ; i ++){
				$("#"+selectId).append("<option value='"+data[i].propGroupId+"'>"+data[i].propGroupName+"</option>");
			}
		}
	});
}
 
	
/*显示图片*/
var imgData ;
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
		imgData = dFile.files[0].getAsDataURL();
		image.attr("src", imgData);
		image.attr("id", "imgid");
		image.attr("width","10");
		image.attr("height","10");
		div.append(image);
		resetH();
	}
	else{
		/*这步骤是用来在ie6,ie7中显示图片的*/
		div.empty();
		div[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dFile.value;
		div[0].style.width = "10px";
		div[0].style.height = "10px";
	    resetH();
	}
}	
	
	
	
	
	
	
	
	
