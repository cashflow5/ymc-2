<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-商品修改日志</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/js/pagination/pagination.css"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/pagination/jquery.pagination.js"></script>
</head>
 
<body>
	<div id="Main">
		<div >
				<!--列表start-->
				<table class="list_table25">
					<thead>
						<tr>
							<th>操作类型</th>
							<th>操作时间</th>
							<th>操作人</th>
							<th style="width:250px;">操作内容</th>
						</tr>
					</thead>
					<tbody id="Searchresult">
					</tbody>
				</table>
				<!--列表end-->
				<!--分页start-->
				<div id="Pagination" class="pagination" style="text-align:center;margin-top:10px;"><!-- 这里显示分页 --></div>
		<!--分页end-->
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var arrays = [];
	<#list commodityUpdateLogs as item>
	arrays[arrays.length] = { logType:'${item.logType!""}', 
			operteTime:'${item.operteTime?string('yyyy-MM-dd HH:mm:ss')}', 
			userLoginName:'${item.userLoginName!"-"}', 
			remark:'${(((item.remark!"-")?replace("\'",""))?replace("\\",""))?replace("\n","")}' };
	</#list>
	
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
			items_per_page:5, //每页显示5项
			prev_text: "上一页",
			next_text: "下一页"
		});
	 }();
	 
	function pageselectCallback(page_index, jq){
		var start = page_index*5;
		var end = (page_index+1)*5 - 1;
		if (end > arrays.length - 1) { end = arrays.length - 1; }
		var new_content = "";
		for(var i = start; i <= end; i++) {
			new_content += "<tr><td style='text-align:center;'>" + typeConversion(arrays[i].logType) + "</td><td>" + arrays[i].operteTime + "</td><td>" + arrays[i].userLoginName + "</td><td style='text-align:left;'><div style='width:250px;float:left;word-break:break-all;'>" + arrays[i].remark + "</div></td></tr>";
		}
		$("#Searchresult").empty();
		$("#Searchresult").html(new_content);
		return false;
	}
	
	function typeConversion(logType) {
		if (1 == logType) {
			return "商品资料修改";
		} else if (2 == logType) {
			return "商品图片上传完成";
		} else if (3 == logType) {
			return "上架";
		} else if (4 == logType) {
			return "下架";
		} else if (12 == logType) {
			return "批量修改销售平台";
		} else {
			return "其他";			
		}
	}
});
</script>
</html>
