<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../merchants-include.ftl">
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加违规订单</a></span>
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
                    </td>
                 </tr>
                <tr>
                 	<td  style="width:120px;margin-left:1500px;text-align:right">
                    	<span style="color:red;">&nbsp;*</span>违规类型：
                    </td>
                    <td>
                   		<input type="radio" value="1" name="punishType" checked />超时效&nbsp;&nbsp;
                   		<input type="radio" value="0" name="punishType" />缺货
                  	</td>
                 </tr>
                 <tr>
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	<span style="color:red;">&nbsp;*</span>金额：
                   	</td>
                   	<td>
                   		<input type="text"  name="punishPrice" id="punishPrice"  value="" class="inputtxt" />
                    </td>
                 </tr>
                 <tr>
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	<span style="color:red;">&nbsp;*</span>备注：
                   	</td>
                   	<td>
                   		<textarea cols="64" rows="6" name="remark" id="remark" style="font-size:12px;" maxlength="500"></textarea>
                    </td>
                 </tr>
                   <tr>
                   	<td> 
                   		&nbsp;&nbsp;
                 	</td>
                   	<td>
                 		<input type="submit"  id="savebtn" value="提交"  class="btn-add-normal"   />
	                    <input type="button"  id="cancelbtn" value="取消" onClick="closewindow();" class="btn-add-normal" />
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
	
	$("#submitForm").validate({
		rules: {
			orderNo: {
				required:true,
				remote: {
				    url: "${BasePath}/yitiansystem/merchants/businessorder/check_orderNo.sc",     
				    type: "post",               
				    data: {                     
				        username: function() {
				            return $("#orderNo").val();
				        }
				    }
				}
			},
			punishPrice: {
				required:true,
				number:true,
				min:0
			},
			remarker: {
				required:true
			}
		},
        messages: {
        	orderNo:{
        		required:"订单号不能为空",
        		remote:"该订单号不是商家订单或者已存在"
        	},
        	punishPrice:{
        		required:"金额不能为空",
        		number:"请输入正确的金额",
				min:"金额不能为负数"
        	},
        	remarker:{
        		required:"备注不能为空"
        	}
   		},
	  	submitHandler:function(form){
	  		var data = $("#submitForm").serialize();
	  		//alert(data);
	  		//alert(data);
	  		$("#savebtn").val("操作中...").attr("disabled","true");
	  		$.ajax({
			   type: "POST",
			   url: "${BasePath}/yitiansystem/merchants/businessorder/save_punishOrder.sc",
			   data: data,
			   cache: false,
			   success: function(result){
				  if(result == "true"){
					  alert("保存成功");
					  $("#savebtn").attr("disabled","false").val("提交");
					  closewindow();
					  refreshpage("${BasePath}/yitiansystem/merchants/businessorder/to_punishList.sc");
				  }else{
					  alert("保存失败");
				  }
			   }
			});
        } 
	});
	
});
</script>