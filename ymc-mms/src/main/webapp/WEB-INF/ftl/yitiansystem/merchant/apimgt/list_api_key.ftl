<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--优购平台</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
</head>
<body>

<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:generateApiKey();">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">生成AppKey</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li><span>AppKey管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/apikey/queryApiKey.sc" method="post">
           			<label for="merchantName">持有者：</label><input type="text" id="metadataTag" name="metadataTag" value="<#if apiKeyMetadata??>${apiKeyMetadata.metadataTag?default('')}</#if>" />
    		       	<label for="merchantCode">标识符：</label><input type="text" id="metadataVal" name="metadataVal" value="<#if apiKeyMetadata??>${apiKeyMetadata.metadataVal?default('')}</#if>" />
	           		<label for="status">状态：</label><select id="status" name="apiKey.status">
			        	<option value="">请选择</option>
			        	<#list apiKeyStatuses as item>
			        	<option value="${item}" <#if apiKeyMetadata?? && apiKeyMetadata.apiKey?? && item == apiKeyMetadata.apiKey.status?default('')>selected="selected"</#if>>${item.description}</option>
			        	</#list>
			        </select>
	           		<label for="appType">类别：</label><select id="metadataKey" name="metadataKey">
	           			<option value="">请选择</option>
	           			<#list appTypes as item>
	           			<option value="${item}" <#if apiKeyMetadata?? && item == apiKeyMetadata.metadataKey?default('')>selected="selected"</#if>>${item.description}</option>
	           			</#list>
			        </select>
    		       	&nbsp;&nbsp;&nbsp;&nbsp;
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:35%;">持有者</th>
	                    <th style="width:10%;">AppKey</th>
	                    <th style="width:10%;">AppSecret</th>
	                    <th style="width:8%;">状态</th>
	                    <th style="width:10%;">最后更新人</th>
	                    <th style="width:12%;">最后更新时间</th>
	                    <th style="width:15%;">功能操作</th>
	                    </tr>              	
	                </thead>
                    <tbody>
                    <#if pageFinder??&&(pageFinder.data)??>
                    	<#list pageFinder.data as item >
                    		<tr>
                    			<td>
                    				<#if item.apiKeyMetadatas?? && item.apiKeyMetadatas?size != 0>
	                    				<#list item.apiKeyMetadatas as subitem>
		                    				<#if subitem.metadataTag??>
	                    					<font color="blue">${subitem.metadataTag}</font>（${subitem.metadataVal}）<br />
		                    				<#elseif (subitem.metadataKey)?? &&  subitem.metadataKey == "CHAIN">
		                    				<font color="blue">${(distributorMap[subitem.metadataVal])!'' }</font>（${subitem.metadataVal}）<br />
		                    				<#else>
		                    				<font color="red">Unknown</font>（${subitem.metadataVal}）<br />
		                    				</#if>
	                    				</#list>
                    				<#else>
	                    				<font color="red" style="font-weight: bold;">暂无</font>
                    				</#if>
                    			</td>
                    			<td>${item.appKey!""}</td>
                    			<td>${item.appSecret!""}</td>
                    			<td id="status_${item.id}">${item.status.description}</td>
                    			<td>${item.updateUser!""}</td>
                    			<td>${item.updateTime!""}</td>
                    			<td id="link_${item.id}" class="td0">
									<a href="javascript:void(0);" onclick="updateAppKeyStatus.call(this, '${item.id}')"><#if item.status?? && item.status == 'DISABLE'>开启<#else>关闭</#if></a>
                    				<a href="javascript:void(0);" onclick="preBindingApiKey('${item.id}', '${item.appKey}')">绑定</a>
									<a href="javascript:void(0);" onclick="preAuthorizeApi('${item.id}', '${item.appKey}')">授权</a>
									<a href="javascript:void(0);" onclick="toUpdateHistoryById('${item.appKey}')">日志</a>
 								</td>
                    		</tr>
                    	</#list>
                    <#else>
                		<tr>
                			<td colspan="7"></td>
                		</tr>
                    </#if>
                    </tbody>
				</table>
			</div>
			<div class="bottom clearfix">
				<#if pageFinder ??>
					<#import "../../../common.ftl" as page>
					<@page.queryForm formId="queryForm"/>
				</#if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript">
//跳转到日志界面
function toUpdateHistoryById(appKey){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/viewAppOperationLog.sc?appKey=" + appKey, 900, 700, "查看日志");
}
//跳转到分配API页面
function preAuthorizeApi(apiKeyId, appKey) {
	openwindow("${BasePath}/openapimgt/apikey/preAuthorizeApi.sc?apiKeyId=" + apiKeyId + '&rand=' + Math.random(), 960, 640, "授权AppKey（" + appKey + "）");
}
//跳转到绑定AppKey页面
function preBindingApiKey(apiKeyId, appKey) {
	ygdgDialog.open("${BasePath}/openapimgt/apikey/preBindingApiKey.sc?apiKeyId=" + apiKeyId + '&rand=' + Math.random(), {
		width: 800,
		height: 600,
		title: "绑定AppKey（" + appKey + "）",
		close: function(){
			refreshpage();
		}
	});
}

function generateApiKey() {
	if (confirm('您确定要生成一个新的AppKey吗？')){
		$.ajax({
			type: 'post',
			url: '${BasePath}/openapimgt/apikey/generateApiKey.sc',
			dataType: 'json',
			data: 'rand=' + Math.random(),
			beforeSend: function(XMLHttpRequest) {
			},
			success: function(data, textStatus) {
				if (data) {
					alert('恭喜生成 AppKey（' + data.appKey + '）成功!');
					$('#tabNum').val('3');
					$('#queryForm').submit();
				} else {
					alert(data);
				}
			},
			complete: function(XMLHttpRequest, textStatus) {
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert('ERROR：' + XMLHttpRequest.responseText);
			}
		});
	}
}

function updateAppKeyStatus(appKeyId){
	var thisObj = $(this);
	if (confirm('您确定要' + thisObj.text() + '当前AppKey吗？')){
		$.ajax({
			type: 'post',
			url: '${BasePath}/openapimgt/apikey/updateApiKeyStatus.sc',
			dataType: 'json',
			data: 'id=' + appKeyId + '&rand=' + Math.random(),
			beforeSend: function(XMLHttpRequest) {
			},
			success: function(data, textStatus) {
				if (data.status == 'ENABLE') {
					thisObj.text('关闭');
					$('#status_' + appKeyId).text('开启');
				} else {
					thisObj.text('开启');
					$('#status_' + appKeyId).text('关闭');
				}
			},
			complete: function(XMLHttpRequest, textStatus) {
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert('ERROR：' + XMLHttpRequest.responseText);
			}
		});
	}
}
</script>
</body>
</html>
