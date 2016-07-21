function get(selValue,selId,arg){
	   	var value=selValue ;
       	$.ajax({
           type: "POST",
           url: "getChildCat.sc",
           data: {"value":value},
           dataType:"json", 
           success: function(data){
              $("#"+selId).empty();//清空下来框
              if(arg =='1'){
            	  $("#"+selId).append("<option value=''>二级分类&nbsp;</option>");
              }
              if(arg =='2'){
            	  $("#"+selId).append("<option value=''>三级分类&nbsp;</option>");
              }
              for(var i=0; i<data.length ; i ++){
               	var option = "<option value='"+data[i].structName+";"+data[i].id+"'>"+data[i].catName+"</option>";
               	$("#"+selId).append(option);
              }
           }
         });
      }
	  $(document).ready(function(){
		  $("#rootCattegory").change(function(){
		  	//选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		  	var arr = selValue.split(";");
		  	//选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !=""){
		  		$("#catId").val(arr[1]);
		  		$("#catId1").val(arr[1]);
		  		$("#structName1").val(arr[0]);
        		get(arr[0],"secondCategory",'1');
        	}else{
        		$("#catId").val("");
        		$("#secondCategory").empty();//清空下来框
                $("#secondCategory").append("<option value=''>二级分类&nbsp;</option>");
        	}
        	$("#threeCategory").empty();//清空下来框
            $("#threeCategory").append("<option value=''>三级分类&nbsp;</option>");
		  });
		  $('#secondCategory').change(function() {
			// 选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "") {
				$("#catId2").val(value[1]);
				$("#catId").val(value[1]);
				$("#structName2").val(value[0]);
				get(value[0], "threeCategory",'2');
			} else {
				$("#catId").val($("#catId1").val());
				$("#threeCategory").empty();// 清空下来框
				$("#threeCategory").append("<option value=''>三级分类&nbsp;</option>");
			}
		});
		  $('#threeCategory').change(function() {
				// 选中项的value值
				var selValue = $(this).children('option:selected').val();
				var value = selValue.split(";");
				// 选中项的text值
				var selText = $(this).children('option:selected').text();
				if (selValue != "") {
					$("#catId").val(value[1]);
					$("#catId3").val(value[1]);
					$("#structName3").val(value[0]);
				}else{
					$("#catId").val($("#catId2").val());
				}
			});
	  });

	//ajax请求  参数请求的url和状态值
	var macVal = "";
	function updateStatus(url,statusVal){
		var idValue= chkValues();
		if(idValue ==""){
			return;
		}
		if(""!=idValue){
       	$.ajax({
           type: "POST",
           url: url,
           data: {"value":idValue,"status":statusVal},
           dataType:"json", 
           success: function(data){
              macVal =  data.flag;
              if("SUCCESS" == macVal ){
              	if("u_brandRecommend.sc" == url){
              		if("1" == statusVal){
              			//遍历checkbox集合
						$("#div-table :checkbox[name='chkS']").each(function(){
						if($(this).attr("checked")){   
							//将推荐状态改为已推荐
							$(this).parent('td').parent('tr').find('td').eq(3).text("已推荐"); 
							$(this).parent('td').parent('tr').find('td').eq(3).addClass("ft-cl-r");
							}
						})
              		}
              		if("0" == statusVal){
              			$("#div-table :checkbox[name='chkS']").each(function(){
						if($(this).attr("checked")){   
							//将推荐状态改为普通
							$(this).parent('td').parent('tr').find('td').eq(3).text("普通"); 
							$(this).parent('td').parent('tr').find('td').eq(3).removeClass("ft-cl-r");
							}
						})
              		}
              	}
              	if("u_brandShow.sc" == url){
              		if("1" == statusVal){
              			$("#div-table :checkbox[name='chkS']").each(function(){
						if($(this).attr("checked")){
							//将显示状态改为显示
							$(this).parent('td').parent('tr').find('td').eq(4).text("显示"); 
							}
						})
              		}
              		if("0" == statusVal){
              			$("#div-table :checkbox[name='chkS']").each(function(){
						if($(this).attr("checked")){   
							//将显示状态改为隐藏
							$(this).parent('td').parent('tr').find('td').eq(4).text("隐藏"); 
							}
						})
              		}
              	}
              }
           }
         });
         }
      }

	//全选反选
	$(document).ready(function(){
		$("#chktags").click(function(){			
			$(" :checkbox").each(function(){ 
				if($(this).attr("checked")) {
					$(this).attr("checked",false); 
				}else{
					$(this).attr("checked",true); 
				}
			});				
		})
		$("#chk").click(function(){
			if($(this).attr("checked")) {
				$(" :checkbox").each(function(){   
					$(this).attr("checked",true);   
				});
			}else{
				$(" :checkbox").each(function(){   
					$(this).attr("checked",false);   
				});
			}
		})
		$("#recommendChk").click(function(){
			//1表示已推荐,0表示普通
			updateStatus("u_brandRecommend.sc","1");
		})
		$("#backoutChk").click(function(){
			updateStatus("u_brandRecommend.sc","0");
		})
		$("#showChk").click(function(){
			//1表示显示,0表示隐藏
			updateStatus("u_brandShow.sc","1");
		})
		$("#hiddenChk").click(function(){
			updateStatus("u_brandShow.sc","0");
		})
	});
	
	//复选框选中值的id
	function chkValues(){
		var chkVal="";
		$("input:checkbox[name=chkS]:checked'").each(function(){
			chkVal+=$(this).val()+" ";
		});
		//alert($.trim(chkVal));
		if($.trim(chkVal)==""){
			alert("请选择数据..");
			return "";
		}
		return chkVal;
	}
	
	//提交按钮所在的表单
	function postForm(formId, url){
		$("#"+formId).attr("action",url);
		//添加分类的hidden到form
		//一级分类的选中的值
	  	var rootVal = $('#rootCattegory').children('option:selected').val();
	  	//二级分类选中的值
	  	var secondVal = $('#secondCategory').children('option:selected').val();
	  	//三级分类选中的值
	  	var threeVal = $('#threeCategory').children('option:selected').val();
	  	
	  	if("" == secondVal && "" == rootVal && ""== threeVal){
			var hdn1 = "<input type='hidden' name='structName1' value=''/>";
			var hdn2 = "<input type='hidden' name='structName2' value=''/>";
			var hdn4 = "<input type='hidden' name='structName3' value=''/>";
			var hdn3 = "<input type='hidden' name='catId' value=''/>";
			$("#"+formId).append(hdn1);
			$("#"+formId).append(hdn2);
			$("#"+formId).append(hdn3);
			$("#"+formId).append(hdn4);
	  	}else if("" == secondVal && "" != rootVal && "" == threeVal){
	  		var rootArr = rootVal.split(";");
	  		var hdn1 = "<input type='hidden' name='structName1' value='"+rootArr[0]+"'/>";
	  		var hdn2 = "<input type='hidden' name='structName2' value=''/>";
	  		var hdn3 = "<input type='hidden' name='catId' value='"+rootArr[1]+"'/>";
	  		var hdn4 = "<input type='hidden' name='structName3' value=''/>";
	  		$("#"+formId).append(hdn1);
			$("#"+formId).append(hdn2);
			$("#"+formId).append(hdn3);
			$("#"+formId).append(hdn4);
	  	}else if("" != secondVal && "" != rootVal && "" == threeVal){
	  		var rootArr = rootVal.split(";");
	  		var secondArr = secondVal.split(";");
	  		var hdn1 = "<input type='hidden' name='structName1' value='"+rootArr[0]+"'/>";
	  		var hdn2 = "<input type='hidden' name='structName2' value='"+secondArr[0]+"'/>";
	  		var hdn3 = "<input type='hidden' name='catId' value='"+secondArr[1]+"'/>";
	  		var hdn4 = "<input type='hidden' name='structName3' value=''/>";
			$("#"+formId).append(hdn1);
			$("#"+formId).append(hdn2);
			$("#"+formId).append(hdn3);
			$("#"+formId).append(hdn4);
	  	}else if("" != secondVal && "" != rootVal && "" != threeVal){
	  		var rootArr = rootVal.split(";");
	  		var secondArr = secondVal.split(";");
	  		var threeArr = threeVal.split(";");
	  		var hdn1 = "<input type='hidden' name='structName1' value='"+rootArr[0]+"'/>";
	  		var hdn2 = "<input type='hidden' name='structName2' value='"+secondArr[0]+"'/>";
	  		var hdn3 = "<input type='hidden' name='catId' value='"+threeArr[1]+"'/>";
	  		var hdn4 = "<input type='hidden' name='structName3' value='"+threeArr[0]+"'/>";
			$("#"+formId).append(hdn1);
			$("#"+formId).append(hdn2);
			$("#"+formId).append(hdn3);
			$("#"+formId).append(hdn4);
	  	}
		$("#"+formId).submit();
	}
	
	//提交按钮所在的表单
	function submitForm(formId, url){
		$("#"+formId).attr("action",url);		//添加分类的hidden到form
		
		$("#"+formId).submit();
	}
	
	/**
	 * 判断该品牌下面是否存在商品
	 * @param id
	 * @return
	 */
	function delBrand(id,obj){
		$.ajax({
	           type: "POST",
	           url: "d_deleteBrand.sc",
	           data: {"id":id},
	           dataType:"json", 
	           success: function(data){
	        	  /* alert(data);
	              if(data == "SUCCESS"){
	            	  alert("删除成功");
	            	  //$(obj).parent().remove();
	            	  return ;
	              }else{
	            	 alert("删除失败");
	              }*/
	           }
	     });
	}
	/**
	 * 判断该品牌是否关联商品
	 * @param id
	 * @return
	 */
	function isHasCommodity(id,obj){
		$.ajax({
	           type: "POST",
	           url: "isHasCommodity.sc",
	           data: {"id":id},
	           dataType:"json", 
	           success: function(data){
	              if(data.hasCommodityFlag == "1"){
	            	  alert("该品牌与商品关联,无法删除");
	            	  return ;
	              }else if(data.productLineFlag=="1"){
	            	  alert("该品牌与产品线关联,无法删除");
	            	  return ;
	              }else{
	            	  if(confirm("确认删除吗?")){
	            		  window.location.href = "d_deleteBrand.sc?id="+id;
	            		  //delBrand(id,obj);
	            	  }
	              }
	           }
	     });
	}