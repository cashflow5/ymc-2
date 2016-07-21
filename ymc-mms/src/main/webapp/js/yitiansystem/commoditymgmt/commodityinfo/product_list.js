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

/**
 * 商品列表的js
 * @return
 */
function searchSubmit(){
	$("#queryForm").submit();
}

/**
 * 全选和反选
 * @param obj
 * @param chkName
 * @return
 */
function allChk(obj,chkName){
	var id = obj.id;
	if($("#"+id).attr("checked")) {
		 $("input[name='"+chkName+"']").attr("checked",'true');//全选
	}else{
		$("input[name='"+chkName+"']").removeAttr("checked");//取消全选
	}
}
 
 /**
  * 批量删除
  * @param chkName
  * @return
  */
function batchDel(chkName){
	var checkVal ="";
	var count = 0;//计数器，统计已经选中的个数
	var commodityStateJquery = null;//记住第一个选中商品的Jquery对象，如果是上架状态，则提示，并取消选中
	$("input[name='"+chkName+"']").each(function(){
		if(this.checked){
			checkVal += $(this).val() +" ";
			if(count == 0){
				commodityStateJquery = $(this).parents('tr');
			}
			count ++;
		}
	});
	if(checkVal == ""){
		alert('请选择您要删除的商品');
		return ;
	}
	if(count == 1){
		var commodityState = commodityStateJquery.find('td').eq(5).text();
		if(commodityState.indexOf('上架') > -1){
			alert('该商品已经上架不能被直接删除');
			commodityStateJquery.find('input[type=checkbox]').attr('checked', false);
			return;
		}
	}
	if(confirm("确认要删除?")){
		ajaxDel(checkVal.toString(),chkName);
	}
	
}
/**
 * 批量导出图片名称的ajax
 * @param selValue
 * @param selId
 * @return
 */
function ajExport() {
	var checkVal ="";
	$("input[name='commName']").each(function(){
		if(this.checked){
			checkVal += $(this).val() +" ";
		}
	});
	$("#value").remove();
	$("#queryForm").attr("action","exportExcel.sc");
	$("#queryForm").append("<input type='hidden' id='value' name='value' value='"+checkVal+"'/>");
	$("#queryForm").submit();
	$("#queryForm").attr("action","findListBySearchVo.sc");
//	window.location.href = "exportExcel.sc?value="+checkVal;
}
 
 /**
  * 商品导出
  * @return
  */
 function commodityExport(){
	 $("#queryForm").attr("action","commodityExport.sc");
	 $("#queryForm").submit();
	 $("#queryForm").attr("action","findListBySearchVo.sc");
 }

 /**
  * 批量删除的ajax
  * @param selValue
  * @param selId
  * @return
  */
 function ajaxDel(chkVal,chkName) {
 	$.ajax( {
 		type : "POST",
 		url : "d_batchCommodity.sc",
 		data : {
 			"value" : chkVal
 		},
 		dataType : "json",
 		success : function(data) {
 			$("input[name='"+chkName+"']").each(function(){
 				if(this.checked){
 					var obj = $(this).parent().parent();
 					$(obj).remove();
 				}
 			});
 			alert('删除成功!');
 		}
 	});
 }
  
  /**
   * 批量审核的ajax
   * @param selValue
   * @param selId
   * @return
   */
  function batchAudit(state) {
	  var checkVal ="";
	  var count = 0;//计数器，统计已经选中的个数
	  var isAuditJquery = null;//记住第一个选中商品的Jquery对象，如果是审核状态，则提示已经审核，并取消选中
		$("input[name='commName']").each(function(){
			if(this.checked){
				checkVal += $(this).val() +" ";
				if(count == 0){
					isAuditJquery = $(this).parents('tr');
				}
				count++;
			}
		});
		if(checkVal ==""){
			alert('请选择您要审核的商品');
			return ;
		}
		if(count == 1){
			var isAudit = isAuditJquery.find('td').eq(6).text();
			if(isAudit.indexOf('已审核') > -1){
				alert('该商品已经被审核');
				isAuditJquery.find('input[type=checkbox]').attr('checked', false);
				return;
			}
		}
	
	var msg = "";
	if(state == 1){
		msg = "提交审核";
	}else if(state == 2){
		msg = "审核";
	}
		
	if(confirm("确认"+msg+"选中商品?")){
	  	$.ajax( {
	  		type : "POST",
	  		url : "batchAudit.sc",
	  		data : {
	  			"value" : checkVal,
	  			"state" : state
	  		},
	  		success : function(data) {
	  			alert(msg+"成功!");
	  			document.location.reload();
//	  			if(data == "SUCCESS"){
//		  			$("input[name='commName']").each(function(){
//		  				if(this.checked){
//		  					$(this).parent('td').parent('tr').find('td').eq(6).text("已审核");
//		  				}
//		  			});
//		  			
//		  			alert("审核成功!");
//	  			}
	  		}
	  	});
	}
  }
 
 /**
  * 批量选中  将选中值传回到父页面  2011-05-05 yinhongbiao
  * @param chkName
  * @return
  */
function batchSelect(chkName){
	var checkVal ="";
	$("input[name='"+chkName+"']").each(function(){
		if(this.checked){
			checkVal += $(this).val() +",";
		}
	});
	window.top.mbdif.saveSelectCommoditys(checkVal,"1");
	window.top.TB_remove();
	
}

  var path ;
  var i = 0;
  // 存储选中集合的val
  // ajax请求
  function get(selValue,selId){
     var value=selValue ;
      $.ajax({
          type: "POST",
          url: path+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
          data: {"value":value},
          dataType:"json", 
          success: function(data){
             $("#"+selId).empty();// 清空下来框
             var isThree = false;
             if("threeCategory" == selId){
          	   isThree = true;
           	  $("#"+selId).append("<option value='0'>请选择</option>");
             }else{
           	  $("#"+selId).append("<option value='0'>请选择</option>");
             }
             for(var i=0; i<data.length ; i ++){
            	if(data[i]){
            		var option = '';
            		if(isThree){
            			option = "<option value='"+data[i].structName +"'>"+data[i].catName+"</option>";
            		}else{
            			option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
            		}
            		$("#"+selId).append(option);
            	} 
             }
          }
      });
  }

//  //根据分类id获取品牌
//  function getBrandByCatId(){
//  	var catId = $('#threeCategory').val().split('_')[1];
//  	 $.ajax({
//          type: "POST",
//          url: path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/toRprpdLine_addCat.sc",
//          data: {"value":catId},
//          dataType:"json", 
//          success: function(data){
//             $('#brand').empty();// 清空下来框
//             $('#brand').append("<option value='0'>请选择品牌</option>");
//             for(var i=0; i<data.length ; i ++){
//              	var option = "<option value='"+data[i].brandNo+"'>"+data[i].brandName+"</option>";
//              	$('#brand').append(option);
//             }
//          }
//      });
//  }

    $(document).ready(function(){
  	 path = $("#basepath").val();
  	 $('#queryForm input[name$=Date]').each(function(){
  		 //$(this).dateDisplay(path);
  	 })
  	 
  	 if($('input[type=hidden][id^=isAdvanced]').val() == 'NO'){
		  $('#div-suppersear').hide();
	 }else{
		  $('#div-suppersear').show(); 
	 }
  	  
  	  $("#rootCattegory").change(function(){
  	  	//选中项的value值
  	  	var selValue = $(this).children('option:selected').val() ;
  	  	// 选中项的text值
  	  	var selText = $(this).children('option:selected').text();
  	  	
  	  	if(selValue !="0"){
       		get(selValue,"secondCategory");
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
  	  	// 选中项的text值
  	  	var selText = $(this).children('option:selected').text();
  	  	if(selValue !="0"){
       		get(selValue,"threeCategory");
       	}else{
       		$("#threeCategory").empty();// 清空下来框
               $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
       	}
  	  })
  	  //四级联动查品牌
//  	  $("#threeCategory").change(function(){
//  		    //选中项的value值
//  		  	var selValue = $(this).children('option:selected').val() ;
//  		  	// 选中项的text值
//  		  	var selText = $(this).children('option:selected').text();
//  		  	if(selValue !="0"){
//  		  		getBrandByCatId();
//  	     	}else{
//  	     		$("#brand").empty();// 清空下来框
//  	            $("#brand").append("<option value='0'>请选择品牌</option>");
//  	     	}
//  	  })
});
  
/**
 * 图片预览
 */
function viewCommodityPic(no){
	var params = "no="+no+"&imgType=0";
	openwindow("../../commoditymgmt/commodityinfo/productlist/queryCommodityPic.sc?no="+no+"&imgType=0",900,600,"商品图片预览");
	//showThickBox("","TB_iframe=true&height=600&width=900",false,params);
}

//控制div的显示与隐藏
function controlDiv(){
	var isShow = $('#div-suppersear').is(":visible");
	if(isShow){
		$('input[type=hidden][id^=isAdvanced]').val('NO');
		$('#div-suppersear').hide();
		//隐藏时，清空高级搜索条件
		$('input[id^=hidden_]').each(function(){
			$(this).val('');
		})
		$("#type1").attr("checked","checked");
	}else{
		$('input[type=hidden][id^=isAdvanced]').val('YES');
		$('#div-suppersear').show();
	}
}

function commodityUpdate(){
	var checkVal ="";
	$("input[name='commName']").each(function(){
		if(this.checked){
			checkVal += $(this).val() +" ";
		}
	});
	if(checkVal==""){
		alert("请选择需要更新的商品");
		return;
	}
	
	$.ajax({
        type: "POST",
        url: "productUpdate.sc",
        data: {"commodityId":checkVal.trim()},
        dataType:"json", 
        error:function(){alert('操作失败');},
        success: function(data){
           if(data != null){
        	   alert("成功生成"+data.buildTotal+"条");
           }
        }
    });
	
}

