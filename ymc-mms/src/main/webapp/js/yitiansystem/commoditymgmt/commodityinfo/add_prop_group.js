
/*
 * 检验属性组名称的可用行
 */
function checkPropGroupName(){
	if($("#showCat").text() == ""){
		alert("请添加当前分类");
		return false;
	}
	if($("#propGroupName").val() == ""){
		return false;
	}
	var propGroupName = $("#propGroupName").val();
	var isSpec = $("input[type='radio'][name='isSpec']:checked").val();
	var result = false;
	$.ajax({
        type: "POST",
        async:false,
        url: path+"/yitiansystem/commoditymgmt/commodityinfo/propgroup/checkPropGroupName.sc",
        data: {
			"isSpec":isSpec,
			"propGroupName":propGroupName
		},
        dataType:"json", 
        success: function(data){
        	if(data[0].length == 0){
        		result = true;
        	}else{
        		alert("该属性组名称已经存在，请重新输入");
        	}
        }
      });
	return result;
}

 /*
  * 前台验证
  */

var config={
	form:"propGroupFrom",submit:submitForm,
 	fields:[
		{name:'propGroupName',allownull:false,regExp:"notempty",defaultMsg:'属性组名称不能为空',focusMsg:'请输入属性组名称',rightMsg:'输入格式正确',errorMsg:'属性组名称不能为空',msgTip:'propGroupNameTip'},
		{name:'sortNo',allownull:true,regExp:"intege1",focusMsg:'请输入排序',rightMsg:'输入格式正确',errorMsg:'只能输入正整数',msgTip:'sortNoTip'}
	]
}
  
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});

 
function submitForm(){
	var result = checkPropGroupName();
	if(result){
		addHidden("propGroupFrom");
	}
	return result;
}
 
 
var path ;
var i = 0;
var subi = 0;
	// 存储选中集合的val
	var arr = new Array();
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
              if("threeCategory" == selId){
            	  $("#"+selId).append("<option value='0'>请选择三级分类</option>");
              }else{
            	  $("#"+selId).append("<option value='0'>请选择二级分类</option>");
              }
              for(var i=0; i<data.length ; i ++){
               	var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
               	$("#"+selId).append(option);
              }
           }
         });
      }
	  $(document).ready(function(){
		  path = $("#basepath").val();
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
	  
	  	var removeF = "removeLiTX(this)";
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

 	//提交按钮所在的表单
	function postForm(formId, url){
		$("#"+formId).attr("action",url);
		// 添加分类的hidden到form
		addHidden(formId);
		$("#"+formId).submit();
	}
	
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
	}
	