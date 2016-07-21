var catNo =''; 
var selected_color = 'font-size:15px;cursor:hand;color:#F8B704;';
var uselected_color = 'font-size:15px;cursor:pointer;color:gray;';

var value = new Array();
$(document).ready(function(){
	
	clearCategory();
 	
	$("#rootCattegory").change(function(){
		clearCategory();
		$("select[id='threeCategory'] option:[value='0']").attr("selected",true);

		ajaxRequest("queryCommodityCategory.sc",
				$(this).children('option:selected').val(),
			function(data){
		
		$("#secondCategory").empty();// 清空下来框
        $("#secondCategory").append("<option value='0'>请选择二级分类</option>");
        
        if(data.length > 0){
			for(var i=0;i<data.length;i++){
					var str = $("<option value='"+data[i].structName+"'>"+data[i].catName+"</option>");
					
					$("#secondCategory").append(str);
				}	
			}
		});
	});
	
	$("#secondCategory").change(function(){

		ajaxRequest("queryCommodityCategory.sc",
					$(this).children('option:selected').val(),
				function(data){
		
		$("#threeCategory").empty();// 清空下来框
        $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
			
		if(data.length <= 0){
			 return;	
		}
		for(var i=0;i<data.length;i++){
				
				if(data[i]!=''){
					
					var str = $("<option value='"+data[i].no+"'>"+data[i].catName+"</option>");
					
					$("#threeCategory").append(str);
				}
			
			}	
		});
	});
	
	$("#threeCategory").change(function(){
		catNo = $(this).val();
	});
	
	$("#selectCommodity").click(function(){

         if( catNo ==''){
            alert("请选择商品分类");
            return;
         }
         
         var parms = "catNo="+catNo;
	
		showThickBox("选择导入商品","../../../yitiansystem/b2cmembersmgmt/CommodityReply/toProductSelectList.sc?&TB_iframe=true&height=458&width=750",true,parms);
	
	});
	
	var height = 0;
	
	if($("#scroceSize").val()==0){
		$("span[id='gradeLabel']").after('<font style="color:red;">对不起，暂时没有记录</font>');
	}
	
	if($("#meritSize").val()==0){
		$("span[id='merit']").after('<font style="color:red;">对不起，暂时没有记录</font>');
	}
	
	for(var i=0;i<$("#scroceSize").val();i++){
		
		var value = $("span[id='grade_"+i+"']").attr("value");
	
		createStar(i,value);
		
		$("#projectScore_"+i).val(value);
	
		height += ($("#gradeLabel").height()/2);
	}
	
	height = height + $("#gradeLabel").height();
	
	$("strong[name$='1']").attr("style",selected_color);
	$("strong[name$='1']").attr("title",1+"星");
	$("#gradeLabel").attr("style","height:"+height+"px;");
	$("span[id^='grade_']").append('');
	$("span[id^='grade_']").append('<input type="hidden" id="selectNum" name="selectNum" value=""/>');
	
	$("strong[name^='strong_']").mousemove(function(){
	
		var value = $(this).parent().attr("value");
		
		var currentNum = $(this).attr("value");
		
		showStar(value,currentNum,selected_color);
	});
	
	$("strong[name^='strong_']").click(function(){
		
		var selectNum = $("#selectNum").val();
			
		var str = $(this).parent().attr("id");
		
		var currentNum = $(this).attr("value");
		
		var value = $(this).parent().attr("value");
		
		var index = str.substring(str.length-1,str.length);
		
		$("#selectNum").val(currentNum);
		$("strong[name^='strong_"+value+"']").unbind( "mouseout" );
		$("strong[name^='strong_"+value+"']").unbind( "mousemove" );
		
		if(currentNum < selectNum){
			
			clearStar(value,selectNum,currentNum,uselected_color);
		}
		
		showStar(value,currentNum,selected_color);
		
		$("input[id='projectScore_"+index+"']").val(value);
		$("input[id='tempScore_"+index+"']").val(currentNum);
		
	});
	
	$("strong[name^='strong_']").mouseout(function(){
		
		var value = $(this).parent().attr("value");
		var currentNum = $(this).attr("value");
		
		for(var i=2;i<=currentNum;i++){
			
			$("strong[name='strong_"+value+i+"']").attr("style",uselected_color);
			$("strong[name='strong_"+value+i+"']").attr("title",i+"星");
		}
	});
	
});

function clearStar(value,selectNum,currentNum,style){
	
	for(var i=currentNum;i<=selectNum;i++){
		
		$("strong[name='strong_"+value+i+"']").attr("style",style);
		$("strong[name='strong_"+value+i+"']").attr("title",i+"星");
	}
}

function showStar(value,currentNum,style){

	for(var i=1;i<=currentNum;i++){
		
		$("strong[name='strong_"+value+i+"']").attr("style",style);
		$("strong[name='strong_"+value+i+"']").attr("title",i+"星");
	}
}

function createStar(gradeIndex,gradeValue){

	var html = '';

	for(var i=1;i<=5;i++){
	
		var str = "<strong name='strong_"+gradeValue+i+"' gradeName='"+gradeValue+"' value="+i+">★</strong>";
		
		html += str; 
		
	}
	$("span[id^='grade_']").attr("style","font-size:15px;cursor:pointer;color:gray;");
	$("span[id='grade_"+gradeIndex+"']").html(html);
}

function ajaxRequest(url,reqParam,callback){
	
	$.ajax({
		type : 'post',
		url : url,
		data : {'structName': reqParam},
		dataType:"json", 
		success : callback
	});
}

var config={
 	form:"myForm",
 	submit:function(){
 		var result = '';
 		KE.util.setData('myComment');
 		var title = $("#commentTitle").val();
 		var myComment = $("#myComment").val();
 		var commodityName = $("#commodityName").val();
 		var advantage=$("input:checkbox[id^='commodityAdvantage_']:checked").length;

 		if(myComment==null||myComment==""){
    		 $("#myComment_tip").addClass("onerror").html("商品评论内容不能为空！");
    		 return false;
    	}
    	if(myComment.length>500){
    		 $("#myComment_tip").addClass("onerror").html("商品评论内容文本输入过长！");
    		 return false;
    	}
 	     
 		if(commodityName == ''){
 		
 			alert("请导入商品 ");
			return false;
 		}

		if(!advantage>0){
 			
 			alert("请选择商品优点");
			return false;
 		}
		
 		if(title.length>0){
 			return true;
 		}else{
 			return false;
 		}
 	},fields:[{
 			name:'commentTitle',
 			allownull:false,
 			regExp:/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{4,20}$/,
 			defaultMsg:'点评标题',
 			focusMsg:'',
 			errorMsg:'可由长度为4-20位的汉字、数字、英文组成',
 			rightMsg:'',
 			msgTip:'commentTitle_tip'
 		}]
 };
 
 Tool.onReady(function(){
 		var f = new Fw(config);
 		f.register();
 	});
 	
 	function clearCategory(){
 	
	 	$("#secondCategory").empty();// 清空下来框
		$("#threeCategory").empty();// 清空下来框
	    $("#secondCategory").append("<option value='0'>请选择二级分类</option>");
	    $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
	   
 	}
