
var path;
var newRow_ = null;


//确定修改并验证是否已存在此产品线
function readyUpdate(){
	var catId=$("#belle3").children("option:selected").val().split(";");
	var catName=$("#belle3").children("option:selected").text();
	var brandId=$("#belle4").children("option:selected").val();
	var brandName=$("#belle4").children("option:selected").text();
	var chooseName=catId[1]+";"+brandId;
	if(0==catId){
		alert("请选择分类");
		return;
	}
	if(0==brandId){
		alert("请选择品牌");
		return;
	}
	var boo=false;
	$("#pro_sort_div>tbody>tr").each(function(i,obj){
		var strutsName=$(obj).find("td:eq(2)>input:eq(1)").val();
		//alert(strutsName+"=="+chooseName);
		if(strutsName==chooseName){
			boo=true;
			alert("不能添加重复项！");
			return;
		}
	});
	
	if(boo){return;}
	var newRow__ = newRow_.clone(true);
	var firstCat = $("#belle1>option:selected").text();
	var secondCat = $("#belle2>option:selected").text();
	var catHtml = '<div style="text-align:left;padding-left:50px;">'+firstCat+"&nbsp;&nbsp;&gt;&nbsp;&nbsp;"+secondCat+"&nbsp;&nbsp;&gt;&nbsp;&nbsp;"+catName+'</div>';
	newRow__.find("td:eq(0)").html(catHtml);
	newRow__.find("td:eq(1)").html(brandName);
	
	newRow__.find("td:eq(2)").html('<input type="hidden" value="-1" /><input type="hidden" value="'+chooseName+'" /><a href="#" onclick="delTrs(this)">删除</a></td>');

	$(newRow__).appendTo($("#pro_sort_div>tbody"));
	
	$(".yt-tb-list-no").hide();
}
// 
/**
 * 修改产品线
 */
function udpate(){
	// 获得主表对象
	var rprodLineName=$("#rprodLineName").val();
	if(""==rprodLineName){
		$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
		$('#rprodLineNameid').addClass("onerror").html("请输入产品线名称");
		return;
	}
	//alert(isTrue);

	if(rprodLineName__!=rprodLineName){
		if(!checkIsExist){
			return;
		}
	}
	
	
	var hiddenStr="";
	//alert($("#pro_sort_div>tbody>tr:[class='row_']").size()); 
	var pre = "detialList";
	if($("#pro_sort_div>tbody>tr:[class='row_']").size()<=0){
	
		//alert("请选择产品线详细信息");
		if(confirm("未添加详细记录,确定要提交吗?")){
			 
		}else{
			 return;
		}
	 }
	 
	 
	 $("#pro_sort_div>tbody>tr:[class='row_']").each(function (i,obj){
		    var name = pre + "[" +i+ "]"	;
		    var detailId=$(obj).find("td:eq(2)>input[type='hidden']:eq(0)").val();
			var catIdAndBrandId=$(obj).find("td:eq(2)>input[type='hidden']:eq(1)").val();
			catIdAndBrandId=catIdAndBrandId.split(";");
			hiddenStr+='<input type="hidden" name="'+name+'.detialId" value="'+detailId+'" />';
			hiddenStr+='<input type="hidden" name="'+name+'.catb2cId" value="'+catIdAndBrandId[0]+'" />';
			hiddenStr+='<input type="hidden" name="'+name+'.brandId" value="'+catIdAndBrandId[1]+'" />';
	 });
	$("#catBrandForm").append(hiddenStr);
	$("#catBrandForm").submit();
}
//  删除详细记录
function delTrs(obj){
	if(!confirm("确认删除吗？")){
		return;
	}
	var trs=$(obj).parent().parent();
	var detialId=trs.find("td:eq(2)>input:eq(0)").val();
	// -1,代表新添加的行
	if("-1"==detialId){
		trs.remove();
		if($("#pro_sort_div>tbody>tr:[class='row_']").size()<=0){
			$(".yt-tb-list-no").show();
		}
		return;
	}
	var url=path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/d_RprodDetailLineAjax.sc";
	$.ajax({
	       type: "POST",
	       url: url,
	       data: {"val":detialId},
	       dataType:"text",
	       async:false,
	       success: function(data){
	    	   	//alert(data);
		        if("1"==data){
		        	trs.remove();
		        }
	       }
	});
	//alert($("#pro_sort_div>tbody>tr:[class='row_']").size());
	if($("#pro_sort_div>tbody>tr:[class='row_']").size()<=0){
		$("#pro_sort_div").after('<div class="yt-tb-list-no">暂无内容</div>');
		//alert($("#pro_sort_div>tbody>tr").find("#yt-tb-list-no").html());
	}
}
var rprodLineName__ ="";
$(document).ready(function(){
	rprodLineName__ = $("#rprodLineName").val();
	$('#rprodLineNameid').addClass("oncorrect").html("该名称可以使用");
});

var isTrue = false;

/**
 *  保存前验证名称是否存在
 * @return
 */

function checkIsExist(){
	
	var exist = true;
	var rprodLineName=$("#rprodLineName").val();
	if(""==rprodLineName){
		$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
		$('#rprodLineNameid').addClass("onerror").html("请输入产品线名称");
		return exist;
	}
	if(rprodLineName__==rprodLineName){
		return exist;
	}
	var url = path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/validateRprodName.sc";
	var data = {val:rprodLineName};
	
	$.ajax({
		url : url,
		data : data,
		dataType : 'text',
		type : 'POST',
		async : false,
		success : function(d){
			if("1"==d){
				// alert("此产品线名称已存在");
				$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
				$('#rprodLineNameid').addClass("onerror").html("该名称已经存在");
				isTrue = false;
				exist = false;
			}else{
				$('#rprodLineNameid').removeClass("onerror").removeClass("errorMsg");
				$('#rprodLineNameid').addClass("oncorrect").html("该名称可以使用");
				isTrue = true;
				exist = true;
			}
		}
	});
	return exist;
}



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
		checkIsExist = false;
		return;
	}
	$.post(path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/validateRprodName.sc",{val:rprodLineName},function(data){
		//var dat=eval("("+data+")");
		if("1"==data){
			$('#rprodLineNameid').removeClass("oncorrect").removeClass("errorMsg");
			$('#rprodLineNameid').addClass("onerror").html("该名称已经存在");
			exist = false;
			checkIsExist = false;
			//disableSelect(true);
		}else{
			$('#rprodLineNameid').removeClass("onerror").removeClass("errorMsg");
			$('#rprodLineNameid').addClass("oncorrect").html("该名称可以使用");
			exist = true;
			checkIsExist = true;
			//disableSelect(false);
		}
	});
}