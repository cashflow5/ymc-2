<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>招商--商家后台--API预警统计</title>
	<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/js/pagination/pagination.css"/>
	<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/pagination/jquery.pagination.js"></script>
</head>
 
<body>
	<div id="Main">
		<div>
			<label>AppKey持有者：</label><label>${appKeyHolder!'-'}</label>
		</div>
		<div >
				<!--列表start-->
				<table class="list_table2">
					<thead>
						<tr>
							<th style="width:40px;">序号</th>
							<th style="width:110px;">日期</th>
							<th style="width:150px;">AppKey日调用次数上限[次]</th>
							<th style="width:150px;">AppKey实际日调用次数[次]</th>
							<th style="width:150px;">实际日调用次数/日调用次数上限比例</th>
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
	<#list list as item>
	arrays[arrays.length] = { index: '${item_index + 1}', timeQuantum:'${item.timeQuantum!""}', warmAppkeyCallCount:'${item.warmAppkeyCallCount!""}', appkeyCallCount:'${item.appkeyCallCount!"-"}', rate:'${item.ratio?string("0.##")}%' };
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
			new_content += "<tr><td style='text-align:center;'>" + arrays[i].index + "</td><td>" + arrays[i].timeQuantum + "</td><td>" + arrays[i].warmAppkeyCallCount + "</td><td>" + arrays[i].appkeyCallCount + "</td><td>" + arrays[i].rate + "</td></tr>";
		}
		$("#Searchresult").empty();
		$("#Searchresult").html(new_content);
		return false;
	}
});
</script>
</html>
