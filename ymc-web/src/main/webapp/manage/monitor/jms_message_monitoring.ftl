<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-jms消息监控</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
<script type="text/javascript">
	var basePath = "${BasePath}";
</script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; jms消息监控</p>
			<div class="tab_panel">
				<ul class="tab">
				<li class="curr"><span>消息监控</span></li>
					<li class="tab_fr">
					</li>
				</ul>
				<div class="tab_content">
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" method="post">
							<p>
								<span><label>商家编码：</label><input type="text" class="inputtxt" id="merchantCode" name="merchantCode" value="${vo.merchantCode!''}"/></span>
								<span><label>商品编码：</label><input type="text" class="inputtxt" id="commodityNo" name="commodityNo" value="${vo.commodityNo!''}"/></span>
								<span>
									<label>处理类型：</label>
									<select name="picType" id="picType">
										<option value="">请选择</option>
										<option value="l" <#if vo ?? && vo.picType ?? && vo.picType == 'l'>selected="selected"</#if>>批量处理(l)</option>
										<option value="p" <#if vo ?? && vo.picType ?? && vo.picType == 'p'>selected="selected"</#if>>单个l图(p)</option>
										<option value="m" <#if vo ?? && vo.picType ?? && vo.picType == 'm'>selected="selected"</#if>>单个描述(m)</option>
										<option value="t" <#if vo ?? && vo.picType ?? && vo.picType == 't'>selected="selected"</#if>>淘宝商品图片(t)</option>
									</select>
								</span>
								<span>
									<label>处理状态：</label>
									<select name="status" id="status">
										<option value="">请选择</option>
										<option value="0" <#if vo ?? && vo.status ?? && vo.status == 0>selected="selected"</#if>>未处理</option>
										<option value="1" <#if vo ?? && vo.status ?? && vo.status == 1>selected="selected"</#if>>已处理</option>
										<option value="2" <#if vo ?? && vo.status ?? && vo.status == 2>selected="selected"</#if>>已作废</option>
									</select>
								</span>
								<span>
									<label style="width: 80px;">创建时间：</label>
									<input type="text" name="startTime" id="startTime" value="${vo.startTime!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="endTime" id="endTime" value="${vo.endTime!''}" class="inputtxt" style="width:80px;" />
								</span>
								
								<span style="padding-left:10px;"><a id="mySubmit" class="button" onclick="javascript:formSubmit();"><span>搜索</span></a></span>
								<span style="padding-left:10px;"><a id="mySubmit" class="button" onclick="javascript:reSandChecked();"><span>重发所选</span></a></span>
								<span style="padding-left:10px;"><a id="mySubmit" class="button" onclick="javascript:reSandUntreated();"><span>全部重发未处理</span></a></span>
								<span style="padding-left:10px;"><a id="mySubmit" class="button" onclick="javascript:obsoleteChecked();"><span>作废所选</span></a></span>
								<span style="padding-left:10px;"><a id="mySubmit" class="button" onclick="javascript:delMessage();"><span>删除7天前数据(已完成和作废)</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
				
					<!--列表start-->
					<form id="formMessage">
					<table class="list_table" style="table-layout: fixed;">
						<thead>
							<tr>
							    <th style="width:30px;"><input type="checkbox" id="checkAll" class="checkall ml8"></th>
								<th>商家编码</th>
								<th>商品编码</th>
								<th style="width:60px;">处理类型</th>
								<th style="width:70px;">状态</th>
								<th>创建时间</th>
								<th>更新时间</th>
								<th>时间差</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="messTable">
						<#if pageFinder??&&(pageFinder.data)??>
							<#list pageFinder.data as item>
							<tr>
							    <td><input type="checkbox" class="checkone ml8" name="messageid" value="${item.id!''}"></td>
								<td>${item.merchantCode!"—"}</td>
								<td>${item.commodityNo!"—"}</td>
								<td>${item.picType!"—"}</td>
								<td>
									<#if item.status == 0>
										<span style="color:red;">未处理</span>
									<#elseif item.status == 1>
										<span style="color:green;">已处理</span>
									<#elseif item.status == 2>
										<span style="color:gray;">已作废</span>
									<#else>
										未知
									</#if>
								</td>
								<td style="width:65px;">
									<#if item.createTime??>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
								</td>
								<td style="width:65px;">
									<#if item.updateTime??>${item.updateTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
								</td>
								<td>
									<#if item.execTime??>${item.execTime!''}</#if>
								</td>
								<td style="text-align:center;width:80px;">
									<input type="hidden" id="image_id_${item.id}" value="${item.imageId!''}" />
									<input type="hidden" id="url_${item.id}" value="${item.urlFragment!''}" />
									<span style="display:none;">
										<textarea id="prodesc_${item.id}">${item.proDesc!''}</textarea>
									</span>
									<a href="javascript:void(0);" onclick="javascript:detailInfo('${item.id!''}');" >详情</a>
									<a href="javascript:void(0);" onclick="javascript:retryMessage('${item.id!''}');" >重发消息</a>
									<#if item.status == 0>
										<a href="javascript:void(0);" onclick="javascript:obsolete('${item.id!''}');" >作废</a>
									</#if>
								</td>
								
							</tr>
							</#list>
						<#else>
							<tr>
								<td colspan="8" class="td-no">没有相关数据</td>
							</tr>
						</#if>
						</tbody>
					</table>
					<!--列表end-->
				    </form>
			<!--分页start-->
			<#if pageFinder??&&pageFinder.data??>
				<div class="page_box">
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryForm"/>
				</div>
			</#if>
			<!--分页end-->
			</div>
			</div>
		</div>
		
		
	</div>

<script type="text/javascript">
//全选
$("#checkAll").click(function() {
	$("#messTable").find("input[name='messageid']").attr("checked", this.checked);
}); 
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});


//提交表单查询
formSubmit = function() {
	var queryForm = $("#queryForm").attr("action", basePath + "/image_jms/queryImageJms.sc");
	queryForm.submit();
}
function getChecked(){
    var arr = $("#messTable").find("input[name='messageid']:checkbox:checked");
    var arrs = new Array();
	for ( var i = 0; i < arr.length; i++) {
	       arrs[i] = arr[i].value;
    }
    return arrs;
}

function reSandChecked() {
	if (confirm("确定要重发消息吗？")) {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/retryJmsMessageChecked.sc",
	   		data: $("#formMessage").serialize(),
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('重发成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('重发失败！');
	     		}
	   		}
		});
	}
}
function reSandUntreated() {
	if (confirm("确定要重发未处理消息吗？")) {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/retryJmsMessageUntreated.sc",
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('重发成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('重发失败！');
	     		}
	   		}
		});
	}
}
function obsoleteChecked() {
	if (confirm("确定要作废消息吗？")) {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/jmsObsoleteChecked.sc",
	   		data: $("#formMessage").serialize(),
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('作废成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('作废失败！');
	     		}
	   		}
		});
	}
}
function delMessage() {
	if (confirm("确定要删除消息吗？")) {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/delMessage.sc",
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('删除成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('删除失败！');
	     		}
	   		}
		});
	}
}
function getQuMesCount() {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/getQuMesCount.sc",
	   		data: {id:id},
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('重发成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('重发失败！');
	     		}
	   		}
		});
}

/**
 * 显示详细系想你
 */
function detailInfo(id) {
	var imageId = $('#image_id_' + id).val();
	var proDesc = $('#prodesc_' + id).val();
	var urlFragment= $('#url_' + id).val();
	var html = '';
	if (imageId != '') imageId = imageId.replace(/,/g, '<br />');
	html += formatString(
		'<table>' + 
		'<tr>' + 
		'  <td>路径 ：</td>' + 
		'  <td>{#urlFragment}<td>'+
		'</tr>' + 
		'<tr>' + 
		'  <td>角度图 ：</td>' + 
		'  <td><div style="width:400px;">{#imageId}</div><br /><td>'+
		'</tr>' + 
		'<tr>' +
		'  <td>描述图 ：</td>' +
		'  <td><textarea style="width:400px;height:150px;">{#proDesc}</textarea></td>' +
		'</tr>' + 
		'</table>',
		{
		    urlFragment:urlFragment,
			imageId:imageId,
			proDesc:proDesc
		});
	var sets = { content:html, with:450, height:350, title:'详情'};
	ygdgDialog.loadDiv(sets);
}

/**
 * 重发Jms消息
 */
function retryMessage(id) {
	if (confirm("确定要重发消息吗？")) {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/retryJmsMessage.sc",
	   		data: {id:id},
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('重发成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('重发失败！');
	     		}
	   		}
		});
	}
}

/**
 * 作废消息（未处理完的就不会定时重发）
 */
function obsolete(id) {
	if (confirm("确定要废除消息吗？")) {
		$.ajax({
	   		type: "POST",
	   		url: basePath + "/image_jms/updateJmsStatus.sc",
	   		data: {id:id, status:2},
	   		dataType: "json",
	   		success: function(data) {
	   			if (data.result == "true") {
	     			ygdg.dialog.alert('作废成功！');
	     			formSubmit();
	     		} else {
	     			ygdg.dialog.alert('作废失败！');
	     		}
	   		}
		});
	}
}
</script>
</body>
</html>