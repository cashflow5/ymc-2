
var path;
// 保存添加到明细信息中的产品线
var arrayObj= new Array();
// 选中的产品线对象
var _obj;
// 存储选中的集合val的id部分
var idArr = new Array();

function vo(catid,cid,brandid,catName,brandName){
	this.catid = catid;// 中间表id
	this.cid = cid;// 分类id
	this.brandid = brandid;// 品牌id
	this.catName = catName;// 分类名称
	this.brandName = brandName;// 品牌名称
}
// print Object
function inspect(obj) {
	var s = obj + "\n";
	for (var a in obj) {
		if (typeof obj[a] != "function") {
			s += a + "=" + obj[a] + ",\n";
		}
	}
	// alert("obj=" + s);
}

var newRow_ = null;

//初始化分类等级
$(document).ready(function(){
	path = $("#basePath").val();
	if(newRow_==null){
		newRow_ = $("#pro_sort_div>tbody>tr:eq(0)").clone(true);
	}
	//alert(newRow_);
	$("#pro_sort_div>tbody>tr:eq(0)").remove();
	// 第一等级改变事件
	  $("#belle1").change(function(){
		  	// 选中项的value值
		  	var selValue = $(this).children('option:selected').val();
		  	// 选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !="0"){
				get(selValue,"belle2");
			}else{
				$("#belle2").empty();// 清空下来框
		        $("#belle2").append("<option value='0'>请选择二级分类</option>");
			}
			$("#belle3").empty();// 清空下来框
		    $("#belle3").append("<option value='0'>请选择三级分类</option>");
		    
		    // 清空品牌
		    $("#belle4").empty();// 清空下来框
		    $("#belle4").append("<option value='0'>请选择品牌</option>");
	  });
	  // 第二等级改变事件
	   $("#belle2").change(function(){
	  	// 选中项的value值
	  	var selValue = $(this).children('option:selected').val() ;
	  	// 选中项的text值
	  	var selText = $(this).children('option:selected').text();
	  	if(selValue !="0"){
			get(selValue,"belle3");
		}else{
			$("#belle3").empty();// 清空下来框
	        $("#belle3").append("<option value='0'>请选择三级分类</option>");
		}
	  });
	   // 第三等级
	   $("#belle3").change(function(){
		  	// 第三类选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		    // 获取等级，查询品牌
		  	selValue=selValue.split(";");
		  	var url="";
		  	// 加载品牌
			$.ajax({
			       type: "POST",
			       url: path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/toRprpdLine_addCat.sc",
			       data: {"value":selValue[1]},
			       dataType:"json",
			       success: function(da){
			    	  // 清空原有的数据
			    	   $("#belle4").empty();
			    	  if(da==undefined||""==da){
			    		  var option = "<option value='0'>请选择品牌</option>";
		        		  $("#belle4").append(option);
			    		   return;
			    	   }
			    	  for(var i=0;i<da.length;i++){
			    		  var option = "<option value='"+da[i].id+"'>"+da[i].brandName+"</option>";
		        		  $("#belle4").append(option);
			    	  }
			       }
			});
	});
	   
	   
	   //$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
	   $('#rprodLineNameid').addClass("onfocus").html("请输入产品线名称!");
});

var i = 0;
var subi = 0;
// 存储选中集合的val
var arr = new Array();
// ajax请求,查询分类
function get(selValue,selId){
   	var value=selValue;
   	var url = path+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc";
   	// 级别等级编号如：10-10-10和分级的类别ID
   	var va = selValue.split(";");
   	//alert(va);
    var gradName="请选择二级分类";
    if("belle3"==selId){
 	   gradName="请选择三级分类";
    }
   	$.ajax({
       type: "POST",
       url: url,
       data: {"value":va[0]},
       dataType:"json",
       success: function(data){
          $("#"+selId).empty();// 清空下来框
          $("#"+selId).append("<option value='0'>"+gradName+"</option>");
          //alert(data);
      
    	  for(var i=0; i<data.length ; i ++){
    		  if(data[i]){
	    		  var option = "<option value='"+data[i].structName+";"+data[i].id+"'>"+data[i].catName+"</option>";
	    		  $("#"+selId).append(option);
    		  }
    	  }
       }
     });
  }

// 添加授权方法
function add_grant(){
	var rootVal = $('#belle2').children('option:selected').val();
  	var rootText = $('#belle2').children('option:selected').text();
  	var secondText = $('#belle3').children('option:selected').text();
  	var threeText = $('#belle4').children('option:selected').text();
  	// 第三级菜单的值
  	var catVal = $('#belle3').val();
  	var catArr = catVal.split(";");
  	var catid = catArr[0];// 级别等级编号10-10-10
  	var cid = catArr[1];// 编号
  	var brandid = $('#belle4').val();
    // 获取的第三级分类ID
	// 第三类选中项的value值
  	var selValue = $("#belle3").children('option:selected').val();
  	selValue=selValue.split(";");
    // 获取第品牌的ID
  	var product_val=$("#belle4").children('option:selected').val();
  	if(0==product_val){
  		alert("请选择品牌");
  		return;
  	}
  	if(0==catVal){
  		alert("请选择三级分类对象");
  		return;
  	}
  	// 实例化一个选中的产品线vo对象
  	 _obj = new vo(catid,cid,brandid,secondText,threeText);
  	 
  	 //alert(selValue[1]+product_val+"======验证产品线");
    //后台验证产品线是否存在
  	$.ajax({
	       type: "POST",
	       url: path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/validate_RprpdLine.sc",
	       data: {"value":selValue[1]+product_val},
	       dataType:"json",
	       success: function(da){
	    		if(da==0){
//	    	  		var str='<font id="b1" style="color:gray;font-size:12px;font-weight:bold;">'+rootText+'</font>'+'&nbsp;&gt;&nbsp;';
//	    	  		str+='<font id="b2" style="color:gray;font-size:12px;font-weight:bold;">'+secondText+'</font>'+'&nbsp;&gt;&nbsp;';
//	    	  		str+='<font id="b3" style="color:gray;font-size:12px;font-weight:bold;">'+threeText+'</font> ';
//	    	  		$("#add_sort_prod_span").html(str);
//	    	  		$(".add_power_listx").show();
					//直接添加到表格中
					add_detail();
	    	  	}else{
	    	  		alert("该产品线已存在!");
	    	  	}
	       }
	 });
}

// 验证添加表格中
function add_detail(){
	// alert(_obj.cid+";"+_obj.catid+";"+_obj.brandid+";"+_obj.catName+";"+_obj.brandName);
	if(arrayObj.length==0){
		add_detial_2(_obj);
		return;
	}
	for(var i=0;i<arrayObj.length;i++){
		// 集合产品线分类编号+产品系线编号
		var arrObj=arrayObj[i].cid+arrayObj[i].brandid;
		//选择对象产品线分类编号+产品系线编号
		var newObj=_obj.cid+_obj.brandid;
		if(arrObj==newObj){
			alert("不能添加重复项！");
			return;
		}
	}
	add_detial_2(_obj);
}



// 添加到表格
function add_detial_2(obj){
	// 添加新的元素
	
	var cats = "";
	var firstCat = $("#belle1>option:selected").text();
	var secondCat = $("#belle2>option:selected").text();
	//var thirdCat = $("#belle3").val();
	var catHtml = '<div style="text-align:left;padding-left:50px;">'+firstCat+"&nbsp;&nbsp;&gt;&nbsp;&nbsp;"+secondCat+"&nbsp;&nbsp;&gt;&nbsp;&nbsp;"+obj.catName+'</div>';
	arrayObj.push(obj);
	var new_row = newRow_.clone(true);
	new_row.attr("id",obj.catid);
	new_row.find("td:eq(0)").html(catHtml);
	new_row.find("td:eq(1)").html(obj.brandName);
	new_row.find("td:eq(2)").html("<a id='"+obj.cid+"' style='cursor:pointer;' onclick='remTr(this);'>删除</a>");

	$("#pro_sort_div > tbody").append(new_row);
	$(".yt-tb-list-no").hide();
}


// 删除明细信息表格中的产品线
function remTr(obj){
	if(!confirm("确认删除吗？")){
		return;
	}
	var catid = obj.id;
	//删除行
	$(obj).parent().parent().remove();
	for(var i=0;i<arrayObj.length;i++){
		if(arrayObj[i].cid==_obj.cid){
			arrayObj.splice(i,1);
		}
	}
	if($(".ytweb-table tbody>tr").size()<=0){
		$(".yt-tb-list-no").show();
	}
}

////隐藏添加授权初步选中的产品线
//function hidden_add_detail(){
//	$("#add_detail").hide();
//}
/*
 * 保存产品线及详细信息
 * */
function save(){
	//获得主表对象
	var rprodLineName=$("#rprodLineName").val();
	rprodLineName=rprodLineName.replace(/^\s*$/,"");
	if(""==rprodLineName){
		$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
		$('#rprodLineNameid').addClass("onerror").html("请输入产品线名称");
		
		return;
	}
	 var hiddenStr="";
	 var pre = "detialList";
	 
	 
	 if(arrayObj.length==0){
		 //alert("请选择产品线详细信息");
		 if(confirm("未添加详细记录,确定要提交吗?")){
			 
		 }else{
			 return;
		 }
	 }
	 
	 
	 
	 if(!checkIsExist){
		 
		 return;
	 }
	 for(var i=0;i<arrayObj.length;i++){
			//var obj=arrayObj[i];
			var name = pre + "[" +i+ "]";
			//alert(arrayObj[i].cid+"--"+arrayObj[i].brandid);
			hiddenStr+='<input type="hidden" name="'+name+'.catb2cId" value="'+arrayObj[i].cid+'" />';
			hiddenStr+='<input type="hidden" name="'+name+'.brandId" value="'+arrayObj[i].brandid+'" />';
	 }
	$("#catBrandForm").append(hiddenStr);
	$("#catBrandForm").submit();
}

/**
 * 初始化品牌（产品线显示界面）
 */
function changeThreeCategoryList(){
	var strutName=$("#belle3").children('option:selected').val();
	$.ajax({
		type: "POST",
		url: path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/toRprpdLine_addCat.sc",
		data: {"value":strutName},
		dataType:"json",
		success: function(da){
			$("#belle4").empty();
			if(da==undefined||""==da){
				var option = "<option value='0'>请重新选择品牌</option>";
				$("#belle4").append(option);
				return;
			}
			for(var i=0;i<da.length;i++){
				var option = "<option value='"+da[i].id+"'>"+da[i].brandName+"</option>";
				$("#belle4").append(option);
			}
		}
	});
}
 
//
/**
 *  搜索
 */
function searchBrandLine(obj){
	var rootValue = $("#belle1").children('option:selected').val();
	var secondValue = $("#belle2").children('option:selected').val();
	var thirdValue = $("#belle3").children('option:selected').val();
	var structName = "0";
	if(thirdValue != 0){
		structName = thirdValue;
	}else if(secondValue != 0){
		structName = secondValue;
	}else if(rootValue != 0){
		structName = rootValue;
	}
	$("#queryForm1").append("<input type='hidden' name='structName' value='"+ structName +"'>");
	
//	 //二级
//	 var catNameChina2=$("#belle2>option:selected").text();
//	 //三级
//	 var catNameChina3=$("#belle3>option:selected").text();
	//品牌
	 var brandNameChina=$("#belle4>option:selected").text();
//	 $("#catNameChina2").val(catNameChina2);
//	 $("#catNameChina3").val(catNameChina3);
	 $("#brandNameChinaId").val(brandNameChina);
	 $("#queryForm1").submit();
 }
 
 // 
/**
 * 删除详细表记录
 */
function delRprodLine(obj){
	var checkResult = false;
	var url = "deleteBeforeCheck.sc";
	var data = {"id":obj};
	$.ajax({
		url:url,
		data:data,
		async:false,
		dataType:'text',
		type:'POST',
		success:function(d){
			if("1" == d){
				alert("该产品线已被引用，不能删除！");
		 		checkResult = false;
		 		return;
		 	}else if("2" == d){
		 		alert("该产品线已被引用，不能删除！");
		 		checkResult = false;
		 		return;
		 	}else{
		 		checkResult = true;
		 	}
		}
	});

	if(!checkResult){
		return;
	}
	if(!confirm("确认删除吗？")){
		return;
	}
	 
	$("<input type='hidden' name='id' value="+obj+"></input>").appendTo($("#queryForm1"));
	$("#queryForm1").attr("action","d_RprodDetailLine.sc");
	$("#queryForm1").submit();

}
 /**
  *验证产品线名称
  * @return
  */
var checkIsExist = false;
function validateRprodName(){
	var exist = true;
	var rprodLineName=$("#rprodLineName").val();
	if(""==rprodLineName){
		$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
		$('#rprodLineNameid').addClass("onerror").html("请输入产品线名称");
		return;
	}
	$.post(path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/validateRprodName.sc",{val:rprodLineName},function(data){
		//var dat=eval("("+data+")");
		if("1"==data){
			$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
			$('#rprodLineNameid').addClass("onerror").html("该名称已经存在");
			exist = false;
			checkIsExist = false;
			disableSelect(true);
		}else{
			$('#rprodLineNameid').removeClass("onerror").removeClass("errorMsg");
			$('#rprodLineNameid').addClass("oncorrect").html("该名称可以使用");
			exist = true;
			checkIsExist = true;
			disableSelect(false);
		}
	});
}
 /**
  * 设置下拉框不可用
  * @param obj
  * @return
  */
 function disableSelect(obj){
	$("#add_grantId").get(0).disabled=obj;
	$("#saveId").get(0).disabled=obj;
 }
 
 /**
  * 从产品线主界面删除，产品线
  * @return
  */
 function deleteRpordLine(obj){
	 $.post(path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/d_RprodLines.sc",{val:obj},function(data){
		 if(""==data){
			 alert("删除失败");
			 $("#rprodFormId").submit();
		 }
		 var da=eval("("+data+")");
	     if("1"==da){
				 alert("删除成功");
				 $("#rprodFormId").submit();
		  }else{
				 alert("删除失败");
				 $("#rprodFormId").submit();
		 }
	 });
 }
