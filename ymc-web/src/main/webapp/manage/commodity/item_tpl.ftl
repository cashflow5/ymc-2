<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-淘宝商品导入</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/common.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.js?s=blue"></script>
	<style>
	.main_container{width:780px;}
	.normal_box{border:none;}
	.search_box{padding-top:0px;}
	</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<div class="tab_panel  relative">
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath}/taobao/goTaobaoItemList.sc" method="post">
						<table  width="100%">
							<tr style="height:30px;">
								<td class="tdtit" style="width:350px;">优购分类：${cateNames!''}</td>
								<td class="tdtit" style="width:50px;">关键字：</td>
								<td style="width:300px;"><input type="text" name="key" class="inputtxt" style="width:100%;" /></td>
								<td class="tdtit"><a class="button" id="mySubmit"><span onclick="search()">搜索</span></a></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="content_list"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
var curPage = 1;
var from = "${from!''}";
$(function(){
	loadData(curPage);
});
function search(){
	loadData(curPage);
}
function loadData(pageNo){
	curPage = pageNo;
	$("#content_list").html('<table class="list_table"><thead>'+
		'<tr><th width="10%">保存时间</th><th width="25%">商品名称</th><th>关键属性</th><th width="10%">操作</th></tr></thead>'+
		'<tbody id="tbody"><tr><td colspan="10">正在载入......</td></tr></tbody>'+
		'<table>');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:$("#queryVoform").serialize(),
		url : "${BasePath}/commodity/getItemTemplateList.sc?cateNo=${cateNo}&page="+pageNo,
		success : function(data) {
			$("#content_list").html(data);
		}
	});
}
function useTemplate(id){
	if("selcat"==from){
		$("#mainForm",parent.document).append("<input type='hidden' id='templateId' name='templateId' value='"+id+"'>");
		parent.submitMainForm();
		$("#mainForm #templateId",parent.document).remove();
		parent.ygdgDialog.list['itemTplDialog'].close();
	}else{
		parent.useTemplate(id);
	}
}
function delTemplate(id){
	ygdgDialog.confirm ("确定要删除？",function(){
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : "${BasePath}/commodity/delTemplate.sc?id="+id,
			success : function(data) {
				if(data.resultCode=="200"){
					loadData(curPage);
				}else{
					ygdg.dialog.alert(data.msg);
				}
			}
		});
	});
}
</script>