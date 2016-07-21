<#-- 自定义翻页标签宏 -->
<#macro queryForm formId>
<script>
	var formId = "${formId}";
	var totalRows = '${pageFinder.rowCount}';
	var pageSize = ${pageFinder.pageSize};
	var loadUrl = "${loadUrl}";

	/**
	*检查是否含有财务千分位分隔符
	*当totalRows>1000时默认格式会加财务千分位逗号
	*例如11,628，在js当做字符串处理
	*/
	function ck(txt){
	 	if(txt.indexOf(',')>-1){
	  		return true;
	 	}
	 	return false;
	}

	/**
	*以逗号进行字符串分割
	*返回去掉逗号的字符串
	*例如11,628->11628
	*/
	function split(datastr){
	  	var arr= new Array();
	  	var str = "";
	  	arr=datastr.split(",");
	    for (i=0;i<arr.length ;i++ ) {
	        str+=arr[i];
	    }
	    return str;
	}
	/**
	*如果totalRows>=1000,则去除财务分隔符逗号
	*否则转换为数字类型
	*/
	if(ck(totalRows)) {
		totalRows = split(totalRows);
	} else {
		totalRows = Number(totalRows);
	}

	function queryPage(pageNo) {
		pageSize = $("#pageSize").val();
		if (isNaN(pageSize)) {
			alert('每页条数只能为数字');
			return;
		}
		var totalPage = Math.ceil(totalRows / pageSize);
		//校验是跳转页是否在记录有效范围内
		//取大于等于总页数的值
		if (pageNo > totalPage) {
			alert("已经到最后一页");
			return;
		}
		//预处理分页提交条件
		if (typeof(onQueryPage) === 'function' && !onQueryPage.call(this, pageNo)) {
			return;
		}
		if(pageNo==0){
			pageNo = 1;
		}
		loadData(pageNo,pageSize,loadUrl);
	}
	
	function pageSizeChange(pageSize) {
		var pageNo = 1;
		loadData(pageNo,pageSize,loadUrl);
	}
</script>
<#setting number_format="0">
<#if pageFinder?? && pageFinder.data?? && (pageFinder.rowCount > 0)>
	<div class="page-list">
		<#if (pageFinder.pageCount > 1) >
			<#if (pageFinder.hasPrevious == false) >
				<a class="page-prev disable" href="javascript:;"><i class="icon iconfont">&#xe60e;</i></a>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo-1})" class="page-prev"  title="上一页"><i class="icon iconfont">&#xe60e;</i></a>
			</#if>
			<#if (pageFinder.pageCount < 8) >
				<#list 1..(pageFinder.pageCount) as row>
					<#if pageFinder.pageNo == row >
						<!-- 选中时的样式 -->
						<a href="javascript:queryPage(${row})" class="page-item curr" >${row}</a>
					<#else>
						<a href="javascript:queryPage(${row})" class="page-item">${row}</a>
					</#if>
				</#list>
			<#elseif ((pageFinder.pageCount - pageFinder.pageNo) < 4) >
				<#list 1..7 as row>
					<#if ((pageFinder.pageCount - pageFinder.pageNo) == (7- row)) >
						<!-- 选中时的样式 -->
						<a href="javascript:queryPage(${pageFinder.pageCount - 7 + row})" class="page-item curr"> ${pageFinder.pageCount  - 7 + row}</a>
					<#else>
						<a href="javascript:queryPage(${pageFinder.pageCount - 7 + row})" class="page-item"> ${pageFinder.pageCount  - 7 + row}</a>
						<!-- 默认的样式 -->
					</#if>
				</#list>
			<#else>
				<#list 1..8 as row>
					<#if (row == 6)>
					<a>	...</a>
					<#elseif (row == 7) >
						<a href="javascript:queryPage(${pageFinder.pageCount -1 })"  class="page-item"> ${pageFinder.pageCount -1}</a>
					<#elseif (row == 8) >
						<a href="javascript:queryPage(${pageFinder.pageCount})" class="page-item"> ${pageFinder.pageCount}</a>
					<#else>
						<#if (pageFinder.pageNo < 4) >
							<#if (pageFinder.pageNo == row) >
								<!-- 选中时的样式 -->
								<a href="javascript:queryPage(${row});" class="page-item curr" > ${row}</a>
							<#else>
								<a href="javascript:queryPage(${row});" class="page-item"> ${row}</a>
								<!-- 默认的样式 -->
							</#if>
						<#else>
							<#if (row == 3) >
								<!-- 选中时的样式 -->
								<a href="javascript:queryPage(${pageFinder.pageNo-3+row})" class="page-item curr" > ${pageFinder.pageNo-3+row}</a>
							<#else>
								<!-- 默认的样式 -->
								<a href="javascript:queryPage(${pageFinder.pageNo-3+row})" class="page-item"> ${pageFinder.pageNo-3+row}</a>
							</#if>
						</#if>
					</#if>
				</#list>
			</#if>
			<#if (pageFinder.pageNo == pageFinder.pageCount) >
				<!-- 选中时的样式 -->
			<#else>
				<!-- 默认的样式 -->
			</#if>
			<#if (pageFinder.hasNext == false) >
				<a class="page-next disable" href="javascript:;">下一页</a>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo+1})" class="page-next"  title="下一页"  >下一页</a>
			</#if>			
			<div class="page-row">共${pageFinder.pageCount!0}页</div>	
		</#if>
	</div>
    <select class="page-selet fr" id="pageSize" name="pageSize" data-ui-type="dropdown" autocomplete="off">
        <option value="10" <#if pageFinder.pageSize == 10> selected = selected </#if> >10</option>
        <option value="20" <#if pageFinder.pageSize == 20> selected = selected </#if>>20</option>
        <option value="50" <#if pageFinder.pageSize == 50> selected = selected </#if>>50</option>
        <option value="100" <#if pageFinder.pageSize == 100> selected = selected </#if>>100</option>
    </select>
	<div class="page-row">显示行数：</div>
	<#if pageFinder ?? && pageFinder.rowCount ??>
		<div class="page-row page-total">共${pageFinder.rowCount}项</div>
	</#if>
	<script>
		$('#pageSize').off('change').on('change',function(){
			pageSizeChange(this.value);
		}).dropdown();
	</script>
</#if>
</#macro>