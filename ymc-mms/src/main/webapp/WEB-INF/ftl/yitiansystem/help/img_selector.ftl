<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>B网络营销系统-招商帮助中心-优购网</title>
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/yitiansystem/merchants/merchants-sys-global.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/pagination/pagination.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery-1.8.0.min.js"></script> 
<script type="text/javascript" src="${BasePath}/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js"></script> 

</head>

<body>
	<div style="padding: 5px;">
		<div class="tab_content">
			<!--搜索start-->
			<div class="search_box">
				<form id="queryForm" name="queryForm" method="post" action="${BasePath}/yitiansystem/merchants/help/img_selector.sc">
					<p>
						<span>
							<label style="width: 110px;">图片名称：</label>
							<input type="text" name="fileName" id="fileName" class="inputtxt" value="${fileName!''}" />
						</span>
						<span>
							<input id="mySubmit" type="button" value="搜索" onclick="javascript:$('#queryForm').submit()" class="yt-seach-btn">
						</span>
					</p>
				</form>
			</div>
			<!--搜索end-->
			<!--列表start-->
			<table class="list_table">
				<thead>
					<tr>
						<th style="text-align: left; padding-left: 15px;">
							<!--请点击图片选择-->
							<input type="checkbox" id="all_selected_box" style="border: none; width: 13px; margin: 0 0 0 5px;"/> 全选
						</th>
						<th style="text-align: right;">
						<input type="button" value="确认选择" onclick="javascript:confirmSelect();return false;" class="btn-add-normal-4ft">
						<input type="button" value="上传图片" onclick="javascript:uploadPic();return false;" class="btn-add-normal-4ft">
						</th>
					</tr>
				</thead>
			</table>
			<ul class="goodsPicsList clearfixed" id="goodsPicsList"></ul>
			<!--列表end-->
			<!--分页start-->
				<div id="Pagination" class="pagination" style="text-align:center;margin-bottom: 0px;"><!-- 这里显示分页 --></div>
			<!--分页end-->
		</div>
	</div>
</body>
<script type="text/javascript">
//选择
function doImgSelected(p) {
	if (typeof(window.parent[1].onImgSelected) === 'function') {
		window.parent[1].onImgSelected.call(this, { 'imgUrl': $(p).children('img').attr('src'), 'imgName': $(p).next().children('input').val() });
	} else {
		alert('请先注册onImgSelected函数!');
	}
}
//上传图片
function uploadPic() {
	var iframe = this;
	ygdgDialog.open('${BasePath}/yitiansystem/merchants/help/upload/ready.sc?editFilename=false', {
		width: 680,
		height: 500,
		title: '上传图片',
		close: function(){
			iframe.location.reload();
		}
	});
}

// 图片点击事件
function imgOnclick(itemId) {
	var check_box = $("#" + itemId + '_box');
	var flag = check_box.attr('checked');
	// 已经被选中
	if (flag) { 
		check_box.attr('checked', false);
	} else {
		check_box.attr('checked', 'checked')
	}
}

// 确认选中的图片
function confirmSelect() {
	if (typeof(window.parent[1].onImgSelected) === 'function') {
		var urlStrs = '';
		$('input[name="img_checkbox"]:checked').each(function(){
			if (urlStrs == '') {
				urlStrs += $(this).val();
			} else {
				urlStrs += '&&&&&' + $(this).val();
			}
			
		});
		window.parent[1].onImgSelected.call(this, urlStrs);
	} else {
		alert('请先注册onImgSelected函数!');
	}
}

$(function(){
	var arrays = new Array();
	<#if urls??>
		<#list urls as item>
			var url = '${item}';
			var h = url.split('/');
			var name = h[h.length - 1];
			arrays[arrays.length] = { url:url, name:name};
		</#list>
	</#if>
	//回调函数的作用是显示对应分页的列表项内容
	//回调函数在用户每次点击分页链接的时候执行
	//参数page_index{int整型}表示当前的索引页
	var initPagination = function() {
		var num_entries = arrays.length;
		// 创建分页
		$("#Pagination").pagination(num_entries, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 4, //主体页数
			callback: pageselectCallback,
			items_per_page: 20, //每页显示10项
			prev_text: "上一页",
			next_text: "下一页"
		});
	 }();
	 
	function pageselectCallback(page_index, jq){
		var start = page_index*20;
		var end = (page_index+1)*20 - 1;
		if (end > arrays.length - 1) { end = arrays.length - 1; }
		var new_content = "";
		for(var i = start; i <= end; i++) {
			new_content += "<li>" +
								"<p  class='img relative' onclick='javascript:imgOnclick(" + i + ");'>" +
									"<img class='lazy_loading' id='"+ i +"' src='${BasePath}/images/yitiansystem/blank.gif' data-original='" + arrays[i].url + "' width='128' height='128' style='cursor: pointer;'/>" +
								"</p>" +
								"<p>" +
									"<span style='float:left; width:18px; margin:0 0 0 -5px;'>" +
										"<input style='float:left;'  type='checkbox' name='img_checkbox' id='"+i+"_box' value='"+arrays[i].url+"' />" +
									"</span>" +
									"<span style='float:left;width:100px;overflow:hidden;'>" +
										"<input style='float:left;' readonly='readonly' value='"+arrays[i].name+"'/>" +
									"</span>" +
								"</p>" +
							"</li>";
		}
		$("#goodsPicsList").empty();
		$("#goodsPicsList").html(new_content);
		$("#goodsPicsList img").lazyload({
			effect : "fadeIn"
		});
		return false;
	}
	
	//创建全选点击事件
	$("#all_selected_box").click(function() {
	  	var check = $(this).attr('checked')
	  	if (check) { // 被选中
	  		$('input[name="img_checkbox"]').each(function(){    
				$(this).attr('checked', 'checked');
			});
	  	} else {
	  		$('input[name="img_checkbox"]:checked').each(function(){    
				$(this).removeAttr('checked');
			});
	  	}
	});
});
</script>
</html>
