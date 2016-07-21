<#macro add_update_commodity_selector>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div>
		<div class="search_box" style="height:50px;" id="queryForm">
			<p>
				<span class='timeMarkLabel' style="margin-left:10px;display:inline-block;">
					<a href="javascript:void(0)" num="1">今天</a>
					<a href="javascript:void(0)" class="msg-active" num="2">最近一个月</a>
					<a href="javascript:void(0)" num="3">最近三个月</a>
					<a href="javascript:void(0)" num="-1">全部</a>
				</span>
				<br>
				<br>
				<span>
					<label style="width: 70px;">上传时间：</label>
					<input type="text" name="createdStart" id="startTime" class="inputtxt" style="width: 80px;" value="<#if createdStart??>${createdStart?date}</#if>" />
					至
					<input type="text" name="createdEnd" id="endTime" class="inputtxt" style="width: 80px;" value="<#if createdEnd??>${createdEnd?date}</#if>" />
				</span>
				<span> 
					<label style="width: 70px;">图片名称：</label>
					<input type="text" name="srcPicName" id="innerPicName" class="inputtxt" value="" />
				</span>
				
				<span>
					<a class="button" onclick="javascript:searchData()"><span>搜索</span></a>
				</span>
			</p>
		</div>
		<table class="list_table">
			<thead>
				<tr>
					<th style="text-align: left; padding-left: 15px;">
						<span class="fl" style="margin-top: 5px;"><input type="checkbox" id="checkAll"/>&nbsp;<label for="checkAll">全选</label></span>
						<span class="fl"><a class="button" onclick="javascript:deleteSelectedPic()" id="remover"><span>删除</span></a></span>
						<span class="fl"><a class="button" onclick="javascript:moveSelectedPic();" id="picmove"><span>移动</span></a></span>
						<span class="fl"><a class="button" onclick="javascript:insertImage();return false;" id="insertImg"><span>插入图片</span></a></span>
						<span class="fl" style="margin-left:5px;margin-top:5px;"><label><input type="radio" name="orderBy" value='2' checked="checked"  onclick="loadData('1')">时间</label></span>
						<span class="fl" style="margin-left:5px;margin-top:5px;"><label><input type="radio" name="orderBy" value='1'  onclick="loadData('1')">名称</label></span>
					</th>
				</tr>
			</thead>
		</table>
		<div id="pic-list">
			
		</div>
	</div>
</body>
<script>
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});

function searchData(){
	$(".timeMarkLabel a").removeClass("msg-active");
	$(".timeMarkLabel a").eq(3).addClass("msg-active");
	loadData(1);
}

var curPage = 1;
function loadData(pageNo){
	curPage = pageNo;
	var timeMark  = getTimeMark();
	var orderBy = getOrderBy();
	var data = {};
	data.page = pageNo;
	data.timeMark = timeMark==""?"-1":timeMark;
	data.createdStart = $("#startTime").val();
	data.createdEnd = $("#endTime").val();
	data.srcPicName = $("#innerPicName").val().trim();
	data.catalogId = imageManageUpload.catalogId;
	if(orderBy!=null){
		data.orderBy =orderBy;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:data,
		url : "${BasePath}/picture/loadPiclist.sc",
		success : function(data) {
			$("#pic-list").html(data);
		}
	});
}

function getTimeMark(){
	var timeMark= "";
	var active = $("span.timeMarkLabel a.msg-active");
	if(active.length>0){
		timeMark = active.attr("num");
	}
	return timeMark;
}

function getOrderBy(){
	var orderBy=  $("input[name='orderBy']:checked").val();
	return orderBy;
}

$(function(){
	$(".timeMarkLabel a").live("click",function(){
		$(".timeMarkLabel a").removeClass("msg-active");
		$(this).addClass("msg-active");
		$("#innerPicName").val("");
		loadData(1);
	});
	$("#checkAll").attr("disabled",false);
	$("#checkAll").attr("checked",false);
})

</script>
</html>
</#macro>