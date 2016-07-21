//
//var path ;
//
//var i = 0;
//var subi = 0;
//	// 存储选中集合的val
//	var arr = new Array();
//	// ajax请求
//	function get(selValue,selId){
//	   	var value=selValue ;
//       	$.ajax({
//           type: "POST",
//           url: path+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
//           data: {"value":value},
//           dataType:"json", 
//           success: function(data){
//              $("#"+selId).empty();// 清空下来框
//              $("#"+selId).append("<option value='0'>请选择二级分类</option>");
//              for(var i=0; i<data.length ; i ++){
//               	var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
//               	$("#"+selId).append(option);
//              }
//           }
//         });
//      }
//	  $(document).ready(function(){
//		  path = $("#basepath").val();
//		  
//		  var cats = $("input[name='structName']");
//		  
//		  $.each(cats,function(i,n){
//			  arr.push(n.value);
//		  });
//		  
//		  
//		  $("#rootCattegory").change(function(){
//		  	//选中项的value值
//		  	var selValue = $(this).children('option:selected').val() ;
//		  	
//		  	
//		  	
//		  	// 选中项的text值
//		  	var selText = $(this).children('option:selected').text();
//		  	if(selValue !="0"){
//        		get(selValue,"secondCategory");
//        	}else{
//        		$("#secondCategory").empty();// 清空下来框
//                $("#secondCategory").append("<option value='0'>请选择二级分类</option>");
//        	}
//        	$("#threeCategory").empty();// 清空下来框
//            $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
//		  })
//		   $("#secondCategory").change(function(){
//		  	//选中项的value值
//		  	var selValue = $(this).children('option:selected').val() ;
//		  	// 选中项的text值
//		  	var selText = $(this).children('option:selected').text();
//		  	if(selValue !="0"){
//        		get(selValue,"threeCategory");
//        	}else{
//        		$("#threeCategory").empty();// 清空下来框
//                $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
//        	}
//		  })
//	  });
//	  // 添加新分类
//	  function addNewCat(){
//	  	//一级分类的选中的值和文本
//	  	var rootVal = $('#rootCattegory').children('option:selected').val();
//	  	var rootText = $('#rootCattegory').children('option:selected').text();
//	  	// 二级分类选中的值和文本
//	  	var secondVal = $('#secondCategory').children('option:selected').val();
//	  	var secondText = $('#secondCategory').children('option:selected').text();
//	  	// 三级分类选中的值和文本
//	  	var threeVal = $('#threeCategory').children('option:selected').val();
//	  	var threeText = $('#threeCategory').children('option:selected').text();
//	  	var rowid = "row" + i;
//	  
//	  	// var li = "<li id='"+rowid+"'><span>" ;
//	  	var removeF = "removeLiTX(this)";
//	  	// var removeF = "removeLi('"+rowid+"')";
//	  	var isNotAdd =false;
//		if(rootVal == "0"){
//			alert("请选择大分类");
//		}else if(rootVal != "0"&&secondVal=="0"){
//			alert("请选择二级分类");
//		}else if(secondVal != "0"&&threeVal=="0"){
//			alert("请选择三级分类");
//		}else{
//			if(null!=arr&&arr.length>0){
//				$.each(arr,function(i,n){
//					if(threeVal==n){
//						isNotAdd = true;
//					}
//				})
//				if(isNotAdd){
//					alert("已添加该项");
//				}else{
//					var li = "<span><li id="+threeVal+"><span>" ;
//					li += rootText +"</span>" ;
//					li += "  >  "+"<span>"+secondText+"</span>" ;
//					li += "  >  "+"<span>"+threeText+"</span>" ;
//					li += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
//					li +="</span>";
//					arr.push(threeVal);
//					$("#showCat").append(li);
//					
//				}
//			}else if(null!=arr&&arr.length==0){
//				var li = "<span><li id="+threeVal+"><span>" ;
//				li += rootText +"</span>" ;
//				li += "  >  "+"<span>"+secondText+"</span>" ;
//				li += "  >  "+"<span>"+threeText+"</span>" ;
//				li += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
//				li +="</span>";
//				arr.push(threeVal);
//				$("#showCat").append(li);
//			}
//			
//			
//			
//		}
//	  };
//	  
//	  // 批量添加子分类
//	  function addSubNewCat(){
//		  
//		//一级分类的选中的值和文本
//		  	var rootVal = $('#rootCattegory').children('option:selected').val();
//		  	var rootText = $('#rootCattegory').children('option:selected').text();
//		  // 二级分类的选中的值和文本
//		  	var secondVal = $('#secondCategory').children('option:selected').val();
//		  	var secondText = $('#secondCategory').children('option:selected').text();
//		  
//		  	 // 三级分类的选中的值和文本
//		  	var threeVal= $('#secondCategory').children('option:selected').val();
//		  	var threeText= $('#secondCategory').children('option:selected').text();
//		  // 一级分类的所有的的值和文本
//		  	var roots = $('#rootCattegory').children();
//		  	// 二级分类所有的值和文本
//		  	var seconds = $('#secondCategory').children();
//		  	// 三级分类所有的值和文本
//		  	var threes = $('#threeCategory').children();
//		  	var removeF = "removeLiTX(this)";
//		  	
//		  	
//		  	// 当三个分类都不选择的时候，批量添加1级分类
//		  	if(rootVal == "0"){
//				alert("请选择大分类");
//		  	}else if(rootVal != "0"&&secondVal=="0"){
//				alert("请选择二级分类");
//		  	}else{
//		  	//这种情况就添加所有的一级下的三级的所有列表
//		  		
//		  		
//		  		
//		  		if(null!=arr&&arr.length>0){
//		  			
//		  			$.each(threes, function(i, m){
//		  				var isNAdd = false ;
//		  				if(m.value!='0'){
//		  					$.each(arr,function(y,n){
//				  				if(m.value==n){
//				  					isNAdd = true;
//				  				}
//				  			})
//				  			
//				  			
//				  			if(!isNAdd){
//			  					var singleli = "<span><li id="+m.value+"><span>" ;
//			  					singleli += rootText +"</span>";
//			  					singleli += "  >  "+"<span>"+secondText+"</span>" ;
//			  					singleli += "  >  "+"<span>"+m.text+"</span>" ;
//			  					singleli  += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
//			  					singleli += "</span>";
//			  					arr.push(m.value);
//			  					$("#showCat").append(singleli);
//			  					
//			  				}
//				  			
//		  				}
//		  				
//		  				
//		  			})
//		  			
//		  		}else if(null!=arr&&arr.length==0){
//		  			$.each(threes, function(i, m){
//		  				if(m.value!='0'){
//		  					var singleli = "<span><li id="+m.value+"><span>" ;
//		  					singleli += rootText +"</span>";
//		  					singleli += "  >  "+"<span>"+secondText+"</span>" ;
//		  					singleli += "  >  "+"<span>"+m.text+"</span>" ;
//		  					singleli  += "<a onClick="+removeF+"><img src='"+path+"/images/delete.gif'/></a></li>";
//		  					singleli += "</span>";
//		  					arr.push(m.value);
//		  					$("#showCat").append(singleli);
//		  				}
//		  			})
//		  			
//		  		}
//		  	}
//		  	
//	  }
//	  
//	  
//	
//	 
//	//删除分类行
//		function  removeLiTX(obj){
//			var rowId = $(obj).parent().attr("id");
//			$("#"+rowId).remove();
//			$.each(arr,function(i,n){
//				if(n==rowId){
//					arr.splice(i,1);
//				}
//			});
//		}
//	  
//	  
//	  
//	//删除分类行
//	function  removeLiT(obj){
//		var rowId = $(obj).parent().attr("id");
//		$("#"+rowId).remove();
//		renameLi();
//		// rowId后缀，从0开始，并转为数字
//		var rowIndex = rowId.substring(3,rowId.length) * 1;
//		// 行号和数组的索引号对应
//		// alert("移除前"+arr);
//		// alert(rowIndex);
//		arr.splice(rowIndex,1);
//		// alert("移除后"+arr);
//	}
//	
//	//重新索引
//	function renameLi(){
//		$("#showCat li").each(function(i){
//			this.id = "row" + i ;
//		});
//	}
//
// 	//提交按钮所在的表单
//	function postForm(formId, url){
//		$("#"+formId).attr("action",url);
//		// 添加分类的hidden到form
//		addHidden(formId);
//		$("#"+formId).submit();
//	}
//	
//	//在表单中添加hidden
//	function addHidden(formId){
//		//遍历arr数组
//		var i = 0;
//		for(var v in arr){
//			var tName = "structName" ;
//			var hdn = "<input type='hidden' name='"+tName+"' value='"+arr[i]+"'/>";
//			i++;
//			$("#"+formId).append(hdn);
//		}
//	}
//	
//	
//	
//	
//	
//	/**
//	 * 根据单选按钮获取不同的属性组
//	 */
//	function propGroupList(isSpec){
//	   	var flag = isSpec ;
//	   	
//	   	
//	   	
//	   	
//	   	
//       	$.ajax({
//           type: "POST",
//           url: path+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
//           data: {"value":value},
//           dataType:"json", 
//           success: function(data){
//              $("#"+selId).empty();// 清空下来框
//              $("#"+selId).append("<option value='0'>请选择二级分类</option>");
//              for(var i=0; i<data.length ; i ++){
//               	var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
//               	$("#"+selId).append(option);
//              }
//           }
//         });
//      }
//	
///**
// * 属性组单选按扭点击事件
// */	
//function proGroupOnclick(url,param,selectId){
//	$("#propgroupid").show();
//	$.ajax({
//		type : "POST",
//		url : url,
//		data : {"type" : param},
//		dataType : "json",
//		cache : false,
//		success : function(data) {
//			 $("#"+selectId).empty();// 清空下来框
//			 $("#"+selectId).append("<option selected='selected'>请选择</option>");
//			 for(var i=0; i<data.length ; i ++){
//				$("#"+selectId).append("<option value='"+data[i].propGroupId+"'>"+data[i].propGroupName+"</option>");
//			}
//		}
//	});
//}
//
//function hiddenPropGroup(){
//	$("#propgroupid").hide();
//	
//}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
