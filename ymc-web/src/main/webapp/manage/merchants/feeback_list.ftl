<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-意见反馈</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodity.css"/>
<style>
.contentDefault{
 color:#696969
}
.search_box p{height:auto;margin-top:5px;}
.search_box p:after{clear:both;height:0;overflow:hidden;display:block;visibility:hidden;content:"."}
.retb{border:1px solid #ddd;background:#F5F5F5;margin-top:10px;width:100%;}
.retb th{text-align:right;height:25px;width:80px;}
</style>

</head>

<body>
 

<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：意见反馈 </p>
		<div class="tab_panel" style="margin-top:0;">

			<div class="tab_content" style="padding:0 20px;"> 
				<p class="mt10">
				如果您在使用商家中心时遇到什么困难或者有什么好的建议，欢迎在此页面留下您宝贵的建议。
				</p>
				<!--搜索start-->
				<div class="search_box">
					<form name="submitForm" id="submitForm" action="${BasePath}/merchants/feeback/save.sc" method="post">
					<input type="hidden" name="merchantCode" id="merchantCode" />	
						<p>
							<span>
							<label><span class="detail_item_star">*</span>意见类型：</label>
							<select name="firstCate" id="firstCate">
								<option value="">一级分类</option>
								<option value="商品管理">商品管理</option>
								<option value="订单管理">订单管理</option>
								<option value="售后质检">售后质检</option>
								<option value="财务报表">财务报表</option>
								<option value="其他">其他</option>
							</select>
							<select name="secondCate" id="secondCate">
								<option value="">二级分类</option>
							</select>
							<font style="color:red;display:none;" id="firstCateError">请选择分类</font>
							<font style="color:red;display:none;" id="secondCateError">请选择二级分类</font>
							</span>
						</p>
						<p>
							<span>
							<label><span class="detail_item_star">*</span>意见标题：</label>
							<input type="text" name="title" id="title" style="width:400px;"  class="inputtxt" />
							还可输入<font style="color:red" id="count">30 </font>个字
							<font style="color:red;display:none;" id="titleError">请输入意见标题</font>
							</span>
						</p>
						<p>
							<span>
							<label><span class="detail_item_star">*</span>意见内容：</label>
							<textarea style="width:500px;height:250px;"  name="content" id="content" class="contentDefault inputtxt" falg="true" >请在这里留下您宝贵的建议，我们将认真倾听，不断优化商家中心。（500个字）</textarea>
							</span>
							<font style="color:red;display:none;" id="contentError">请输入意见反馈内容</font>
						</p>
						<p>
							<span>
							<label><span class="detail_item_star">*</span>电子邮箱：</label>
							<input type="text" name="email" id="email" style="width:200px;" class="inputtxt"  />
							<font style="color:red;display:none;" id="emailError">请输入电子邮箱</font>
							</span>
						</p>
						<p>
							<span>
							<label>联系电话：</label>
							<input type="text" name="phone" id="phone" style="width:200px;" class="inputtxt"  />
							<font class="contentDefault" >请尽量留下您的联系方式，以便我们及时与您沟通</font>
							<font style="color:red;display:none;" id="phoneError">请输入正确的联系电话</font>
							</span>
						</p>
						<p style="margin:20px 0 0 70px;">
							<span>
								<a class="button fl" id="btnSubmit" ><span>提交</span></a>
								<a class="fl" id="btnReset" href="javascript:;" style="margin:5px 0 0 10px;">重置</a>
								
							</span>
						</p>
					</form>
				</div>
				<br/>
				<form name="queryform" id="queryform" action="${BasePath}/merchants/feeback/list.sc" method="post">
				<#if pageFinder?? && (pageFinder.data)?? > 
				<#list pageFinder.data as item>
				<table class="retb">
				<tbody>
						<tr>
							<th>商家反馈：</th><td>${item.content }</td>
						</tr>
						<#if (item.replyList)?? >
							<#list item.replyList as reply >
							<tr>
							<th><font style="color:red;font-weight:bold;">优购回复：</font></th>
							<td>${reply}</td>
							</tr>	
							</#list>
						</#if>
						
				</tbody>
				</table>
				</#list>
				</#if>
				</form>
				
				<#if pageFinder ??>
					<div class="page_box">
						<!--分页start-->
								<#import "/manage/widget/common.ftl" as page>
								<@page.queryForm formId="queryform"/>
						<!--分页end-->
					</div>
				</#if>
			</div>
		</div>
	</div>
	 
	 </div>
</body>
<script>

var obj = $.parseJSON('{"订单管理":["单据打印","订单发货","销售查询","违规订单","异常订单"],"售后质检":["质检登记","质检查询","退换货流程","退款申请"],"财务报表":["销售报表","财务核算","售后报表"],"其他":["其他"],"商品管理":["单品上传","图片管理","在售商品","待售商品","库存管理","图片管理"]}');


$(function(){
	
	//导航控制
	$(".nav_entity ul li").hover(function()
	{
		var popuNav=$(this).find(".popu_nav");
		if(typeof(popuNav[0])=="undefined") return;//普通无下拉菜单
		$(".popu_nav").hide();
		$(this).addClass("active").siblings().removeClass("active");
		popuNav.show();
	},function(){
		$(this).removeClass("active");
		$(".popu_nav").hide();
	});
	
	
	$("#firstCate").change(function(){
		var firstVal = $(this).val();
		//alert(firstVal);
		$("#secondCate").html('<option value="">二级分类</option>');
		$.each( obj, function(i, n){
			if(firstVal == i){
				$.each(n,function(key,value){
					$("#secondCate").append($("<option></option>").attr("value",value).text(value));
				});
			}
		});
		if(firstVal == "其他" ){
			$("#secondCate").val("其他");
		}
		if(firstVal != ""){
			$("#firstCateError").hide();
		}
		$("#secondCate").reJqSelect();
	});
	
	$("#secondCate").change(function(){
		var secondVal = $(this).val();
		if(secondVal != ""){
			$("#secondCateError").hide();
		}
	});
	
	$("#btnSubmit").click(function(){
		var firstCate = $("#firstCate").val();
		var secondCate = $("#secondCate").val();
		var email = $("#email").val();
		var falg= $("#content").attr("falg");
		var content = $("#content").val();
		var title = $("#title").val();
		var phone = $("#phone").val();
		//alert(contentClass);
		//alert(content);
		if(!firstCate){
			$("#firstCateError").show();
			return false;
		}
		if(!secondCate){
			$("#secondCateError").show();
			return false;
		}
		if(!title){
			$("#titleError").show();
			return false;
		}
		if(falg){
			$("#contentError").show();
			return false;
		}
		if(!content){
			$("#contentError").show();
			return false;
		}
		if(!email){
			$("#emailError").show();
			$("#emailError").text("请输入电子邮箱");
			return false;
		}
		if(email.indexOf("@") < 0){
			$("#emailError").show();
			$("#emailError").text("请输入正确的电子邮箱");
			return false;
		}
		
		
		if(phone){
			   if(!/^[(0-9)|-]*$/.test(phone)){
				   	$("#phoneError").show();
			        return false;
			    }
		}
		
		alert("您的建议已提交，感谢您对优购商家中心的支持。");
		$("#submitForm").submit();
	
	});
	
	$('#title').keyup(function(){
	    var t = this;
	    var count = 30 - parseInt(t.value.length);
	    var title = $("#title").val();
	    if(count <= 0){
	    	$("#title").val(title.substring(0,30));
	    	$('#count').text(0);
	    }else{
	    	 $('#count').text(count);
	 	}
	});
	
	$("#content").keyup(function(){
		 var t = this;
		 var count = parseInt(t.value.length);
		 var content = $("#content").val();
		 if(count >= 500 ){
			 $("#content").val(content.substring(0,500));
		 }
	});
	
	$("#content").click(function(){
		$("#contentError").hide();
		var falg= $("#content").attr("falg");
		if(falg){
			$(this).val('');
			$(this).removeClass("contentDefault");
			$(this).removeAttr("falg");
		}
	});
	
	$("#email").focus(function(){
		$("#emailError").hide();
	});
	
	$("#title").focus(function(){
		$("#titleError").hide();
	});
	

	
	$("#phone").focus(function(){
		$("#phoneError").hide();
	});
	
	
	$("#btnReset").click(function(){
		$("#submitForm")[0].reset();
	});
	
});
</script>
</html>