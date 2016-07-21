/**
 * 批量上架的点击事件
 * 
 * 引入此JS文件时
 * 全选是一个复选框,取ID为:checkAll
 * 其他复选框取Name为:chk
 * 点击批量时调用函数:batchShelf();
 * 
 */

var basePath;

$(document).ready(function(){
	basePath = $("#basePath").val();
	
	/**
	 * 注册全选事件
	 */
	$("#checkAll").click(function(obj){
		if($(this).attr("checked")) {
			$(" :checkbox").each(function(){   
				$(this).attr("checked",true);   
			});
		}else{
			$(" :checkbox").each(function(){   
				$(this).attr("checked",false);   
			});
		}
	});
});


function batchShelf(){
	var ids = vhkValues();
	if(ids == ""){
		return ;
	}
	var url="";
	var data = {};
	
	$.ajax({
		url:url,
		type:'POST',
		dataType:'text',
		success:function(d){
			//上架
			$(":checkbox[name='chk']").each(function(){
				if($(this).attr("checked")) {
					//将上下架状态改为已上架
					$(this).parent('td').parent('tr').find('td').eq(7).text("上架"); 
					$(this).parent('td').parent('tr').find('td').eq(7).addClass("ft-wt-b ft-cl-r");
				}
			});
		}
	});
}


/**
* 获取checkbox的值
*/
function vhkValues(){
	var arr = new Array();
	$(" :checkbox[name='chk']").each(function(){   
		if($(this).attr("checked")) {
			arr.push($(this).val()); 
		}
	});
	if(arr.length == 0){
		return "";
	}
	return arr.join(";");
}

