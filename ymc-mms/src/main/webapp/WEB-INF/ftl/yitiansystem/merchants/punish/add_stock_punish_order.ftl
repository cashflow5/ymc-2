<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../merchants-include.ftl">
<style type="text/css">
	.error{
		color:red;
	}
</style>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加缺货商品</a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
           <form action="#" name="submitForm" id="submitForm" method="post" >
          	 <table cellpadding="0" cellspacing="0" class="list_table">
                 <tr>
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	<span style="color:red;">&nbsp;*</span>订单号：
                   	</td>
                   	<td>
                   		<input type="text"  name="orderNo" id="orderNo"  value="" class="inputtxt" />
                   		&nbsp;&nbsp;&nbsp;&nbsp;
                   		<input type="button"  id="selectbtn" value="查询商品"  class="btn-add-normal"   />
                    </td>
                 </tr>
                 <tr style="display: none;" id="contentTr">
                 	<td colspan="2">
	                 	<table cellpadding="0" cellspacing="0" class="list_table">
	                 		<thead id="headTr">
	                 			<tr>
		                 			<th style="width:20px;"><input type="checkbox" id="allCheck" name="allCheck" onclick="selectAll(this,'oneCheck');"/></th>
		                 			<th style="width:80px;">货品条码</th>
		                 			<th>商品名称</th>
		                 			<th style="width:100px;">成交价</th>
		                 			<th style="width:80px;">数量</th>
	                 			</tr>
	                 		</thead>
	                 		<tbody id="bodyTr">
	                 		</tbody>
	                 	</table>
                 	</td>
                 </tr>
                   <tr>
                   	<td> 
                   		&nbsp;&nbsp;
                 	</td>
                   	<td>
                   		<input type="hidden" name="punishType" value="0"/>
                 		<input type="submit"  id="savebtn" value="保存"  class="btn-add-normal"   />
                 		&nbsp;&nbsp;<input type="checkbox" name="punishOrderStatus" value="2"/>审核通过
                 	</td>
                  </tr>
              </table>
            </form>
    	</div>
 		<div class="blank20"></div>
	</div>
</div>
</body>
</html>
<script>
$(document).ready(function(){
	$("#savebtn").attr("disabled","true");
	$("#submitForm").validate({
		rules: {
			orderNo: {
				required:true,
				remote: {
				    url: "${BasePath}/yitiansystem/merchants/businessorder/check_orderNo.sc?type=0",     
				    type: "post"            
				}
			}
		},
        messages: {
        	orderNo:{
        		required:"订单号不能为空",
        		remote:"该订单号不是商家订单或者已存在"
        	}
   		},
	  	submitHandler:function(form){
	  		var data = $("#submitForm").serialize();
	  		var checkedObj = $("input[name='oneCheck']:checked");
	  		var levelCodeStr = "";
	  		$.each(checkedObj, function(i, n){
		  		if(i>0){
		  			levelCodeStr = levelCodeStr+","+n.value;
			  	}else{
			  		levelCodeStr = n.value;
				}
	  		});
	  		$("#savebtn").val("操作中...").attr("disabled","true");
	  		$.ajax({
			   type: "POST",
			   url: "${BasePath}/yitiansystem/merchants/businessorder/save_punishOrder.sc",
			   data: "levelCodeStr="+levelCodeStr+"&"+data,
			   cache: false,
			   success: function(result){
				  if(result == "true"){
					  alert("保存成功");
					  $("#savebtn").attr("disabled","false").val("提交");
					  closewindow();
					  refreshpage("${BasePath}/yitiansystem/merchants/businessorder/to_punishValidList.sc");
				  }else{
					  alert("保存失败");
				  }
			   }
			});
        } 
	});
	$("#selectbtn").click(function(){
		var orderNo = $.trim($("#orderNo").val());
		if(!($("#orderNo").hasClass("error"))){
			$.post("${BasePath}/yitiansystem/merchants/businessorder/check_orderNo.sc",
					{'orderNo':orderNo,'type':0},
					function(html){
				if(html=="true"){
					$.getJSON('${BasePath}/yitiansystem/merchants/businessorder/getOrderDetailByOrderNo.sc',{
						'orderNo':orderNo,
					},function(json){
					if(json.length>0){
						$("#contentTr").show();
						var str = "";
						$("#bodyTr").html("");
						var style = "";
						$.each(json,function(i,n){
							if(i%2!=0){
								style="background-color: #e6eff5";
							}else{
								style = "";
							}
							if(!(n.levelCode)){
								n.levelCode = "--";
							}
							if(!(n.prodTotalAmt)){
								n.prodTotalAmt = 0.00;
							}
							str+="<tr><td style='"+style+"'><input type='checkbox' onclick='selectOne(this);' value='"+n.levelCode+"' name='oneCheck'/>"+
		                   	"</td><td style='"+style+"'>"+n.levelCode+"</td>"+
		                    "<td style='"+style+";width:200px;'>"+n.prodName+"</td>"+
		                   	"<td style='"+style+"'>"+(n.prodTotalAmt).toFixed(2)+"</td>"+
		                   	"<td style='"+style+"'>"+(n.commNum)+"</td></tr>";
						});
						$("#bodyTr").append(str);
					}else{
						$("#bodyTr").html("");
						$("#contentTr").hide();
						$("#savebtn").attr("disabled","true");
						parent.ygdg.dialog.alert("该订单未完成或暂未发货！");
					}
				});
				}
			});
		}
	});
});
//全选和反选
function selectAll(obj,chkName){
	var id = obj.id;
	if($("#"+id).prop("checked")) {
		 $("input[name='"+chkName+"']").prop("checked",true);//全选
		 if($("input[name='"+chkName+"']").size()>0){
			 $("#savebtn").removeAttr("disabled");
		}
	}else{
		$("input[name='"+chkName+"']").prop("checked",false);//取消全选
		$("#savebtn").attr("disabled","true");
	}
}

function selectOne(obj){
	if($(obj).prop("checked")){
		$("#savebtn").removeAttr("disabled");
	}else{
		 if($("input[name='oneCheck']:checked").size()<1){
			 $("#savebtn").attr("disabled","true");
		}
	}
}
</script>