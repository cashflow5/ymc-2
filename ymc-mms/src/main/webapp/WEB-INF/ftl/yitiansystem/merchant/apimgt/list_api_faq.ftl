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
			<div class="btn" onclick="openwindow('${BasePath}/openapimgt/api/faq/view.sc',　800,　500,　'添加常见问题')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加常见问题</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>常见问题管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/api/faq/list.sc" method="post">
    		       	<label for="apiCode">标题：</label>
    		       	<input type="text" id="subject" name="subject" value="<#if apiFaq??>${apiFaq.subject?default('')}</#if>" />
    		       	<label for="apiCode">创建人：</label>
    		       	<input type="text" id="creator" name="creator" value="<#if apiFaq??>${apiFaq.creator?default('')}</#if>" />
    		       	<label for="apiCode">创建时间：</label>
    		       	<input type="text" id="fromCreated" name="fromCreated" readonly="readonly" value="<#if fromCreated??>${fromCreated?default('')?datetime}</#if>" />
    		       	至
    		       	<input type="text" id="toCreated" name="toCreated" readonly="readonly" value="<#if toCreated??>${toCreated?default('')?datetime}</#if>" />
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
				<thead>
                    <tr>
                    	<th width="40%">标题</th>
                    	<th width="20%">所属分类</th>
                    	<th width="20%">创建时间</th>
                    	<th width="10%">创建人</th>
                    	<th width="10%">操作</th>
                    </tr>
				</thead>
				<tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
						<#list pageFinder.data as item>
							<tr>
								<td>
									<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/api/faq/view.sc?id=${item.id?default('')}',　800,　500,　'修改常见问题')">${item.subject?default('')}</a>
								</td>
								<td>
									<#if item.apiCategory??>
									${item.apiCategory.categoryName?default('')}									
									<#else>
									其它
									</#if>
								</td>
								<td>${item.created?default('')}</td>
								<td>${item.creator?default('')}</td>
								<td>
									<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/api/faq/view.sc?id=${item.id?default('')}',　800,　500,　'修改常见问题')">修改</a>
									<a href="javascript:void(0);" onclick="deleteApiFaq('${item.id?default('')}')">删除</a>
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
$('#fromCreated').calendar({maxDate:'#datepicker_end',format:'yyyy-MM-dd HH:mm:ss' }); 
$('#toCreated').calendar({minDate:'#datepicker_start',format:'yyyy-MM-dd HH:mm:ss' });

function deleteApiFaq(id) {
	if (confirm('确定删除该常见问题?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/api/faq/delete.sc', dataType: 'text', data: 'id=' + id,
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
