<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-添加账号</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript">
  function addTaobaoShop(){
     var taobaoShopName=$("#taobaoShopName").val();
     var taobaoShopUrl=$("#taobaoShopUrl").val();
     var nickName=$("#nickName").val();
     if(taobaoShopName==""){
        $("#taobaoShopNameError").html("淘宝店铺名称不能为空!");
        return false;
     }else{
       $("#taobaoShopNameError").html("");
     }
     if(taobaoShopUrl==""){
        $("#taobaoShopUrlError").html("淘宝店铺地址(URL)不能为空!");
        return false;
     }else{
       $("#taobaoShopUrlError").html("");
     }
     if(nickName==""){
        $("#nickNameError").html("店铺主账号不能为空!");
        return false;
     }else{
       $("#nickNameError").html("");
     }
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/taobao/checkTaobaoShop.sc?taobaoShopName="+taobaoShopName, 
		dataType: "json",
		success: function(dt){
			if(true==dt.success){
			   $("#taobaoShopNameError").html("淘宝店铺名称,请重新输入!");
			   return false;
			}else{
			   //提交表单数据
				formSubmit();
			}
		} 
	});
  }
  
  function formSubmit(){
	  	$.ajax({ 
			type: "post", 
			url: "${BasePath}/taobao/saveTaobaoShop.sc", 
			data:$("#newShopForm").serialize(),
			dataType: "json",
			success: function(dt){
				if(true==dt.success){
				   parent.location.href="${BasePath}/taobao/goTaobaoShop.sc"; 
				}else{
					$("#pageError").html(dt.message);
				}
			} 
		});
  }
</script>
</head>

<body>
	<div class="form_container">
		<form id="newShopForm" name="newShopForm" action="${BasePath}/taobao/saveTaobaoShop.sc" method="post">
		<table class="form_table">
			<tbody>
				<tr>
					<th><span class="star">*</span>淘宝店铺名称：</th>
					<td><input type="text" id="taobaoShopName" name="taobaoShopName"  class="ginput" style="width:180px;"/>
					&nbsp;&nbsp;<span style="color:red;" id="taobaoShopNameError"></span>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>店铺主账号：</th>
					<td><input type="text" id="nickName" name="nickName"  class="ginput"  style="width:180px;"/>
					&nbsp;&nbsp;<span style="color:red;" id="nickNameError"></span>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>淘宝店铺地址：</th>
					<td>
					<input type="text" id="taobaoShopUrl" name="taobaoShopUrl"  class="ginput" style="width:280px;"/>
					&nbsp;&nbsp;<span style="color:red;" id="taobaoShopUrlError"></span>
					</td>
				</tr>
				<tr>
					<th></th>
					<td><span style="color:red;" id="pageError"></span>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
</body>
</html>

