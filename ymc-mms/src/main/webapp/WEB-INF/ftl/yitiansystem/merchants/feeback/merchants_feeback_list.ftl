<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span>意见反馈</span>
				</li>
			</ul>
		</div>
   <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_feebackList.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top" style="padding: 5px;">
                     <label>商家名称：</label>
                     <input type="text" name="merchantName" id="merchantName" value="${(feebackVo.merchantName)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>商家编号：</label>
                     <input type="text" name="merchantCode" id="merchantCode" value="${(feebackVo.merchantCode)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>意见分类：</label>
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
                </div>
  			  	<div class="wms-top" style="padding: 5px;">
  			  		<label>联系电话：</label>
                   	<input type="text" name="phone" id="phone" value="${(feebackVo.phone)!'' }"/>&nbsp;&nbsp;&nbsp;
                    <label>提交日期：</label>
                   	<input type="text" name=startCreateTime id="startCreateTime" value="<#if (feebackVo.startCreateTime) ??>${feebackVo.startCreateTime?datetime}</#if>"/>
                   	 至
                   	<input type="text" name="endCreateTime" id="endCreateTime" value="<#if (feebackVo.endCreateTime) ??>${feebackVo.endCreateTime?datetime}</#if>"/>
                   	<label>电子邮箱：</label>
                   	<input type="text" name="email" id="email" value="${(feebackVo.email)!'' }"/>
                   	<input type="checkbox" name="isRead" id="isRead" value="0" <#if (feebackVo.isRead)??>checked</#if> >未查看
                   	<input type="checkbox" name="isReply" id="isReply" value="0" <#if (feebackVo.isReply)??>checked</#if>  />未回复 
              	</div>
              	<dir class="wms-top" style="padding: 5px;">
              		<input type="submit" value="搜索"  class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                   	<input type="buttno" id="exportFeeback" value="导出"  class="yt-seach-btn" />
              	</dir>
        
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
	                        <th style="width:120px;">商家名称</th>
	                        <th style="width:80px;">商家编号</th>
	                        <th style="width:80px;">意见类别</th>
	                        <th style="width:120px;">意见标题</th>
	                        <th style="width:80px;">提交日期</th>
	                        <th style="width:80px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#if pageFinder?? && (pageFinder.data)?? >
	                   		<#list pageFinder.data as item >
	                        <tr >
		                        <td>${(item.merchantName)!'' }</td>
		                        <td>${(item.merchantCode)!'' } </td>
		                        <td>${(item.firstCate)!'' }</td>
		                        <td>${(item.title)!'' }</td>
		                        <td>${(item.createTime)!'' }</td>
	                        	<td>
	                        	<a href="javascript:;" class="readFeeback" feebackId="${item.id }" ><#if item.isRead == "0" >查看<#else>已查看</#if></a>&nbsp;
	                        	</td>
	                        </tr>
	                        </#list>
	                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">

var obj = $.parseJSON('{"订单管理":["单据打印","订单发货","销售查询","违规订单","异常订单"],"售后质检":["质检登记","质检查询","退换货流程","退款申请"],"财务报表":["销售报表","财务核算","售后报表"],"其他":["其他"],"商品管理":["单品上传","图片管理","在售商品","待售商品","库存管理","图片管理"]}');


$(document).ready(function() {
	$('#startCreateTime').calendar({maxDate:'#startCreateTime',format:'yyyy-MM-dd HH:mm:ss'});
	$('#endCreateTime').calendar({minDate:'#endCreateTime',format:'yyyy-MM-dd HH:mm:ss'});
	
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
		if(firstVal == "其他"){
			$("#secondCate").val("其他");
		}
	});
	
	$("#exportFeeback").click(function(){
		//alert("exportFeeback");
		$("#queryForm").attr("action","${BasePath}/yitiansystem/merchants/businessorder/export_feeback.sc");
		$("#queryForm").submit();
		$("#queryForm").attr("action","${BasePath}/yitiansystem/merchants/businessorder/to_feebackList.sc");
		
	});
	
	$(".readFeeback").click(function(){
		var id = $(this).attr("feebackId");
		var url = "${BasePath}/yitiansystem/merchants/businessorder/to_feebackReply.sc?id="+id;
		//openwindow(url,'','',"意见详情");
		var $this = $(this);
		ygdgDialog.open(url, {
			width: 800,
			height: 600,
			title: '意见详情',
			close: function(){
				//refreshpage();
				$this.text("已查看");
			}
		});
	});
	
});


</script>
