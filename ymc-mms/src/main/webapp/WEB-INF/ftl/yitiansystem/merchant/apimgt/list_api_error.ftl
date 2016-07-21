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
			<div class="btn" onclick="openwindow('${BasePath}/openapimgt/api/error/view.sc',　800,　500,　'添加错误码')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加错误码</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>错误码表管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/api/error/list.sc" method="post">
    		       	<label for="apiCode">错误码：</label>
    		       	<input type="text" id="errorCode" name="errorCode" value="<#if apiError??>${apiError.errorCode?default('')}</#if>" />
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
				<thead>
                    <tr>
                    	<th width="10%">错误码</th>
                    	<th width="25%">错误描述</th>
                    	<th width="25%">解决方案</th>
                    	<th width="10%">排序号</th>
                    	<th width="10%">创建时间</th>
                    	<th width="10%">创建人</th>
                    	<th width="10%">操作</th>
                    </tr>
				</thead>
				<tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
						<#list pageFinder.data as apiError>
							<tr>
								<td>${apiError.errorCode?default('')}</td>
								<td>${apiError.errorDescription?default('')}</td>
								<td>${apiError.errorSolution?default('')}</td>
								<td>${apiError.orderNo?default('')}</td>
								<td>${apiError.created?default('')?datetime}</td>
								<td>${apiError.creator?default('')}</td>
								<td>
									<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/api/error/view.sc?id=${apiError.id?default('')}',　800,　500,　'修改错误码')">修改</a>
									<a href="javascript:void(0);" onclick="deleteApiError('${apiError.id}')">删除</a>
								</td>
							</tr>
						</#list>
					<#else>
						<tr>
							<td colspan="8" align="center" style="color: red;">暂无数据</td>
						</tr>
					</#if>
                </tbody>
				</table>
				</div>
			</div>
			<div class="bottom clearfix">
				<#if pageFinder??>
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
</body>
<script type="text/javascript">
function deleteApiError(id) {
	if (confirm('确定删除该错误码?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/api/error/delete.sc', dataType: 'text', data: 'id=' + id,
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					alert('删除成功!');
					$('.btn-add-normal').click();
				} else {
					alert(data);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
			}
		});
	}
}
</script>
</html>
