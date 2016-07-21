<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include "../merchants-include.ftl">
	<title>优购商城--商家后台</title>
</head>
<body>
	<div class="continer">
		<!--工具栏START-->
		<div class="toolbar">
			<div class="t-content">
				<div class="btn" onclick="showAdd()">
					<span class="btn_l"></span> <b class="ico_btn add"></b>
					<span class="btn_txt">添加淘宝品牌</span>
					
				</div>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="btn"  align="right">
					&nbsp;&nbsp;
					<span class="btn_txt" >
					&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="viewLog();">查看日志</a>
				    </span>
				</div>
			</div>
		</div>
		<div class="list_content">
			<div class="top clearfix">
				<ul class="tab">
					<li  class="curr">
						<span>
							<a href="#" class="btn-onselc">淘宝品牌列表</a>
						</span>
					</li>
				</ul>
			</div>
			<div class="modify">
				<form action="" id="queryVoform" name="queryVoform" method="post">
					<table width="80%" class="bind-table">
						<tr class="form-table-tr" style="height:40px;">
							<td>品牌名称：</td>
							<td>
								<input type="text" name="name">
							</td>
							<td>VID：</td>
							<td>
								<input type="text" name="vid">
							</td>
							<td>是否手动添加：</td>
							<td>
								<select name="isArtificialInput">
									<option value="">全部</option>
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
							<td>是否已经绑定：</td>
							<td>
								<select name="yougouBrandNo">
									<option value="">全部</option>
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
							<td>
								<input type="button" value="搜索" onclick="search();" class="yt-seach-btn" />
							</td>
						</tr>
					</table>
				</form>
				<div id="content_list">
					
				</div>
			</div>
		</div>
</body>
<script>
	var curPage = 1;
	var curPageSize = 20;
	$(function(){
		loadData(1,20);
		//全选
		$("#selectAll").live("click",function(){
			var isCheckAll = true;
			if($(this).attr("checked")){
				isCheckAll = true;
			}else{
				isCheckAll = false;
			}
			var binds = $("input[name='bindId']");
			for(var i=0,_len=binds.length;i<_len;i++){
				if(binds.eq(i).attr("haveBind")=="0"&&binds.eq(i).attr("isArtificialInput")=="1"){
					binds.eq(i).attr("checked",isCheckAll)
				}
			}
		});
	});
	function loadData(pageNo,pageSize){
		if(pageNo==null||pageNo==""){
			pageNo=1;
		}
		curPageSize = pageSize;
		curPage = pageNo;
		$("#content_list").html('<div class="list-loading">正在载入......</div>');
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "html",
			data:$("#queryVoform").serialize(),
			url : "getTaobaoBindList.sc?page="+pageNo+"&pageSize="+pageSize,
			success : function(data) {
				$("#content_list").html(data);
			}
		});
	}
	
	function showAdd(){
		var content = '<table width="100%"><tr style="height:30px;"><td>VID:</td><td><input type="text" id="vid" style="height:20px;"><td></tr>'+
						'<tr style="height:30px;"><td>品牌名称:</td><td><input type="text" id="pname" style="height:20px;"><td></tr>'+
						'</table>';
		var d = ygdg.dialog({
	    content:content,
	    title: '新增淘宝品牌',
	    fixed: true,
	    id: 'Fm7',
	    okVal: '确定',
	    ok: function () {
	    	var vid = $("#vid").val(),pname = $("#pname").val();
	    	if($.trim(vid)==""){
	    		parent.ygdg.dialog({content:'vid不能为空',title:'提示',lock:true});
	    		return false;
	    	}
	    	
	    	var reg = /[0-9]+$/;
	    	if(!reg.test($.trim(vid))){
	    		parent.ygdg.dialog({content:'vid只能是数字',title:'提示',lock:true});
	    		return false;
	    	}
	    	
	    	if($.trim(pname)==""){
	    		parent.ygdg.dialog({content:'品牌名称不能为空',title:'提示',lock:true});
	    		return false;
	    	}
	    	$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data:{
					vid:vid,
					name:pname
				},
				url : "addBrand.sc",
				success : function(data) {
					if(data.resultCode=="200"){
						loadData(curPage,curPageSize);
						d.close();
					}else{
						parent.ygdg.dialog({content:data.msg,title:'提示',lock:true});
						return false;
					}
				}
			});
			return false;
	    },cancel: true
		});
	}
	
	function search(){
		loadData(curPage,curPageSize);
	}
	
	
	function viewLog(){
	    openwindow("${BasePath}/yitiansystem/taobao/log.sc?type=TAOBAO_BAND", 900, 700, "查看日志");
    }
	function  deleteItem(id){
		var idsArray = [];
		if(id==null){
			var catIds = $("input[name='bindId']:checked");
			if(catIds.length==0){
				ygdg.dialog.alert("请选择要删除的品牌");
				return;
			}
			for(var i=0,_len=catIds.length;i<_len;i++){
				idsArray.push(catIds.eq(i).val());
			}
			
		}else{
			idsArray.push(id);
		}
		 ygdg.dialog.confirm("确定要删除品牌吗?",function(){
			 $.ajax({
					async : true,
					cache : false,
					type : 'POST',
					dataType : "json",
					data:{
						ids:idsArray.join(",")
					},
					url : "delBrand.sc",
					success : function(data) {
						if(data.resultCode=="200"){
							ygdg.dialog.alert("删除成功!");
							loadData(curPage,curPageSize);
						}else{
							ygdg.dialog.alert(data.msg);
						}
					}
				});
		 });
	}
</script>
</html>