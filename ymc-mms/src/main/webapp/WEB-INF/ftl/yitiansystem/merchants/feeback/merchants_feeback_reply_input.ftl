<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<title>优购商城--商家后台</title>
<style>
.pad5{
	padding: 5px;
}
</style>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span>意见详情</span>
				</li>
			</ul>
		</div>
   <div class="modify"> 
			  <div class="wms-top pad5" >
                   <label>商家名称：</label>
                   ${(feeback.merchantName)!'' }
              </div>
			  <div class="wms-top pad5">
                   <label>商家编号：</label>
                   ${(feeback.merchantCode)!'' }
              </div>
			  <div class="wms-top pad5">
                   <label>电子邮箱：</label>
                   ${(feeback.email)!'' }
              </div>
			  <div class="wms-top pad5">
                   <label>手机号码：</label>
                   ${(feeback.phone)!'' }
              </div>
			  <div class="wms-top pad5">
                   <label>意见标题：</label>
                   ${(feeback.title)!'' }
              </div>
			  <div class="wms-top pad5">
                   <label>意见内容：</label>
                   ${(feeback.content)!'' }
              </div>
              <#if replyList??>
              <#list  replyList as item >
			  <div class="wms-top pad5" >
                   <label>优购回复：</label>
                   <span style="color:#696969">${(item.replyContent)!'' }</span>
                   <a href="javascript:;" class="replyDel blue" replyId="${item.id }">删除</a>
              </div>
              </#list>
              </#if>
              
              
              <form action="${BasePath}/yitiansystem/merchants/businessorder/save_feebackReply.sc" name="submitForm" id="submitForm" method="post">
              <input type="hidden" name="feebackId" value="${feeback.id }"/>
              <div class="wms-top pad5">
                   <label>优购回复：</label>
                   <textarea rows="10" cols="100"  name="replyContent" id="replyContent" ></textarea>
              </div>
  			 
  			  <div class="wms-top pad5">
  			  		<input type="button" id="btnSubmit" value="提交 "  class="yt-seach-btn" />
  			  		<input type="button" value="取消 "  class="yt-seach-btn" onclick="closewindow();" />
              </div>
              </form>
			  
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">

$(document).ready(function() {
	
	$(".replyDel").click(function(){
		var id = $(this).attr("replyId");
		//alert(id);
		var $this = $(this);
		$.ajax({
			type:"POST",
			url:"${BasePath}/yitiansystem/merchants/businessorder/del_feebackReply.sc",
			data:{
				id:id
			},
			success:function(msg){
				//alert(msg);
				if(msg == "true"){
					$this.parent().remove();
				}
			}
		});
		
	});
	
	$("#btnSubmit").click(function(){
		if(confirm("确定提交回复信息？")){
			$("#submitForm").submit();
		}
	});
	
});

</script>
