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
.retb{margin-top:10px;width:100%;}
.retb th{text-align:center;height:25px;width:80px;}
.retb th,.retb td{padding:10px;border-bottom:1px solid #ddd;}
strong{font-weight:bold;}
</style>
</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 工单处理  </p>
		<div class="tab_panel" style="margin-top:0;">

			<div class="tab_content" style="padding:0 20px;"> 
				<p class="mt10">
				<div  class="detail_box normal">
							<p>
								<span style="width:180px;">工单号：<#if orderSaleTrace.orderTraceNo??>${orderSaleTrace.orderTraceNo}</#if></span>
								<span style="width:180px;">订单号：<#if orderSaleTrace.orderSubNo??>${orderSaleTrace.orderSubNo}</#if></span>
								<span style="width:180px;">工单状态：<#if orderSaleTrace.traceStatus?default("")==0>待客服处理<#elseif orderSaleTrace.traceStatus?default("")==1>处理中<#elseif orderSaleTrace.traceStatus?default("")==2>已完成<#elseif orderSaleTrace.traceStatus?default("")==3><em style="color:#FF0000;">待商家处理</em><#else>${orderSaleTrace.traceStatus?default('')}</#if></span>
								<span style="width:120px;"><#if orderBaseStatusName??>订单状态：${orderBaseStatusName}</#if></span>
							</p>
							<p>
								<span style="width:180px;">工单类型：<#if orderSaleTrace.problemName??>${orderSaleTrace.problemName}</#if></span>
								<span style="width:180px;">创建时间：<#if orderSaleTrace.createTime ??>${orderSaleTrace.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></span>
								<span style="width:180px;">最近处理：<#if orderSaleTrace.modifyTime ??>${orderSaleTrace.modifyTime?string('yyyy-MM-dd HH:mm:ss')}<#else>${orderSaleTrace.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></span>
							</p>
					<div class="clearfix" style="background:#f2f2f2;border:1px solid #ccc;color:#696969;padding:10px;">
						<strong  class="fl">优购客服：</strong>
						<div class="fl ml20">
							问题归属：<#if orderSaleTrace.secondProblemName??>${orderSaleTrace.secondProblemName}</#if><br/>
							<div class="mt10">
								<#if orderSaleTrace.issueDescription??>${orderSaleTrace.issueDescription}</#if>
							</div>
						</div>
					</div>
				</div>
				</p>

				<!--搜索start-->
				<form name="queryform" id="queryform" action="${BasePath}/dialoglist/detail.sc" method="post">
				<input type="text" style="width:180px;display:none;" class="ginput" id="id" name="id" value="<#if orderSaleTrace.id??>${orderSaleTrace.id}</#if>"/>
				<#if pageFinder?? && (pageFinder.data)?? > 
				
				<table class="retb">
				<tbody>
				<#list pageFinder.data as item>
						<tr>
							<th><#if item.operateType?default("")==2><strong>商家回复</strong><#elseif item.operateType?default("")==1><strong style="color:#FF0000;">优购回复</strong><#else>回复</#if><br /><#if item.operateType?default("")!=1>(${item.operateId})</#if></th>
							<td>
								${item.processRemark}
								<p class="mt5">
								${item.createTime?string('yyyy-MM-dd HH:mm:ss')}&nbsp;&nbsp;回复人：<#if item.operateType?default("")==1>yougou<#else>${item.operateUser}</#if>
								</p>
							</td>
						</tr>
						</#list>
				</tbody>
				</table>
				
				</#if>
				</form>
				<br/>
				<#if pageFinder??&&pageFinder.data??&&pageFinder.data?size gt 0>
					<div class="page_box">
						<!--分页start-->
								<#import "/manage/widget/common.ftl" as page>
								<@page.queryForm formId="queryform"/>
						<!--分页end-->
					</div>
				</#if>

				<#if orderSaleTrace.traceStatus??&&orderSaleTrace.traceStatus==3>
				<div class="search_box">
				<input type="text" style="width:180px;display:none;" class="ginput" name="orderTraceNo" value="<#if orderSaleTrace.orderTraceNo??>${orderSaleTrace.orderTraceNo}</#if>"/>
				<p>
				  <span>
				  <label><span class="detail_item_star">*</span>商家回复：</label>
				  <textarea style="width:500px;height:250px;"  name="processRemark" id="processRemark" class="contentDefault inputtxt" flag="true" >最多可输入500个字！</textarea>
				  </span>
				  <font style="color:red;display:none;" id="contentError">请输入回复内容</font>
				</p>
				<p style="margin:20px 0 0 70px;">
				  <span>
				    <a class="button fl" id="btnSubmit"><span>提交</span></a>
				    <a class="fl" id="btnReset" href="javascript:;" style="margin:5px 0 0 10px;">重置</a>
				  </span>
				</p>
				</div>
			    </#if>

			</div>
		 </div>
	   </div>
	 </div>
</body>
<script>
var checkSubmitFlg = false; 
$(function(){
	$("#btnSubmit").click(function(){
		var flag= $("#processRemark").attr("flag");
		var processRemark = encodeURIComponent($("#processRemark").val());
		var id ="${orderSaleTrace.id?default('')}";
		if(flag){
			$("#contentError").show();
			return false;
		}
		if(!processRemark){
			$("#contentError").show();
			return false;
		}
		var dialog;
		if(!checkSubmitFlg){
		checkSubmitFlg=true;
		$.ajax({
		  type: 'post',
		  url: '${BasePath}/dialoglist/save.sc?param=' + new Date(),
		  dataType: 'json',
		  data: 'id='+id+'&processRemark='+processRemark,
		  beforeSend: function(XMLHttpRequest) {
			var timer;
	        dialog=ygdg.dialog({
	          content: '提交中...,沟通愉快^@^..',
	          init: function () {
	    	     var that = this, i = 5;
	             var fn = function () {
	             that.title(i + '秒后关闭');
	             !i && that.close();
	             i--;
	            };
	            timer = setInterval(fn, 1000);
	            fn();
	          },
	          close: function () {
	    	    clearInterval(timer);
	          }
	       }).show();
		  },
		  success: function(data, textStatus) {
		       dialog.close();
		       location.href="${BasePath}/dialoglist/detail.sc?id="+id;
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
		    ygdg.dialog.alert(XMLHttpRequest.responseText);
		  }
		});
		}else{
		   ygdg.dialog.alert("请不要重复提交！");
		}
		
	});

	$("#processRemark").keyup(function(){
		 var t = this;
		 var count = parseInt(t.value.length);
		 var content = $("#processRemark").val();
		 if(count >= 500 ){
			 $("#processRemark").val(content.substring(0,500));
		 }
	});

	$("#processRemark").click(function(){
		$("#contentError").hide();
		var flag= $("#processRemark").attr("flag")||($("#processRemark").val()=="最多可输入500个字！");
		if(flag){
			$(this).val('');
			$(this).removeClass("contentDefault");
			$(this).removeAttr("flag");
		}
	});

	$("#btnReset").click(function(){
		$("#processRemark").val("最多可输入500个字！");
	});
});
</script>
</html>