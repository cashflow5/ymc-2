<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include "../merchants-include.ftl">
	<script type="text/javascript" src="${BasePath}/js/mms.common.js?${version}"></script>
	<style type="text/css">
	.continermain{width: 480px;}
	.list_table tr.datatr{cursor: pointer;}
	</style>
	<script type="text/javascript">
	function search(){
		if($("input[name='yougouBrandName']").val()==""){
			ygdg.dialog.alert("请输入品牌名称");
			return;
		}
		$("#content_list").html('<div class="list-loading">正在载入......</div>');
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "html",
			data:$("#queryVoform").serialize(),
			url : "getTaobaoBrandList.sc",
			success : function(data) {
				$("#content_list").html(data);
			}
		});
	}
	$(function(){
		$(".list_table tr.datatr").live("click",function(){
			$(this).find("input").attr("checked",true);
			var brandNo = $(this).children().eq(1).text();
			var brandName = $(this).children().eq(2).text();
			dg.curWin.setTaobaoBrand(brandNo,brandName);
			closewindow();
		});
		
		document.onkeydown = function(e){
	    	var ev = document.all ? window.event : e;  
		    if(ev&&ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
		    	search();
		    }  
		};
	})
	</script>
</head>
<body>
<div class="continermain" >
	<div class="list_content">
		<div class="top clearfix">
				<ul class="tab">
					<li  class="curr">
						<span>
							<a href="#" class="btn-onselc">淘宝品牌</a>
						</span>
					</li>
				</ul>
		</div>
		<div class="modify">
			<form action="" id="queryVoform" name="queryVoform" method="post">
					<table>
						<tr class="form-table-tr" style="height:35px;">
							<td>品牌名称：</td>
							<td>
								<input type="text" name="taobaoBrandName">
								<input type="text" name="taobaoBrandNames" style="display:none;"><span style="color:red;padding:0px 10px;font-size:12px;">请输入品牌名牌查询</span>
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
</div>
</body>
</html>