<#-- 自定义翻页标签宏 -->
<#macro queryForm formId>
<script>
	var formId = "${formId}";
	var totalRows = '${pageFinder.rowCount}';
	var pageSize = ${pageFinder.pageSize};

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
		if (isNaN(pageSize)) {
			alert('每页条数只能为数字');
			return;
		}
		var totalPage = Math.ceil(totalRows / pageSize);
		if (pageNo == 0) {
			pageNo = document.getElementById("query.page").value;
			if (isNaN(pageNo) || pageNo <= 0) {
				alert("请输入大于0的整数.");
				return;
			}

			if (pageNo > totalPage) {
				alert("没有当前页数");
				return;
			}
		}
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
		loadData(pageNo);
	}
</script>
<#setting number_format="0">
<#if pageFinder?? && pageFinder.data?? && (pageFinder.rowCount > 0)>
	<div class="page">
		<#if pageFinder ?? && pageFinder.rowCount ??>
			<span style="float:left;padding-right:15px;line-height:23px;">
			总数：<font color ="red">${pageFinder.rowCount}</font>
			</span>
		</#if>
		<#if (pageFinder.pageCount > 1) >
			<#if (pageFinder.hasPrevious == false) >
				<a class="prevdis" href="javascript:;">&nbsp;&nbsp;上一页</a>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo-1})" class="prev"  title="上一页">&nbsp;&nbsp;上一页</a>
			</#if>
			<#if (pageFinder.pageCount < 10) >
				<#list 1..(pageFinder.pageCount) as row>
					<#if pageFinder.pageNo == row >
						<!-- 选中时的样式 -->
						<a href="javascript:queryPage(${row})" class="curr" >${row}</a>
					<#else>
						<a href="javascript:queryPage(${row})" >${row}</a>
					</#if>
				</#list>
			<#elseif ((pageFinder.pageCount - pageFinder.pageNo) < 5) >
				<#list 1..9 as row>
					<#if ((pageFinder.pageCount - pageFinder.pageNo) == (9- row)) >
						<!-- 选中时的样式 -->
						<a href="javascript:queryPage(${pageFinder.pageCount - 9 + row})" class="curr"> ${pageFinder.pageCount  - 9 + row}</a>
					<#else>
						<a href="javascript:queryPage(${pageFinder.pageCount - 9 + row})"> ${pageFinder.pageCount  - 9 + row}</a>
						<!-- 默认的样式 -->
					</#if>
				</#list>
			<#else>
				<#list 1..10 as row>
					<#if (row == 8) >
					<a>	...</a>
					<#elseif (row == 9) >
						<a href="javascript:queryPage(${pageFinder.pageCount -1 })"  > ${pageFinder.pageCount -1}</a>
					<#elseif (row == 10) >
						<a href="javascript:queryPage(${pageFinder.pageCount})" > ${pageFinder.pageCount}</a>
					<#else>
						<#if (pageFinder.pageNo < 5) >
							<#if (pageFinder.pageNo == row) >
								<!-- 选中时的样式 -->
								<a href="javascript:queryPage(${row});" class="curr" > ${row}</a>
							<#else>
								<a href="javascript:queryPage(${row});" > ${row}</a>
								<!-- 默认的样式 -->
							</#if>
						<#else>
							<#if (row == 4) >
								<!-- 选中时的样式 -->
								<a href="javascript:queryPage(${pageFinder.pageNo-4+row})" class="curr" > ${pageFinder.pageNo-4+row}</a>
							<#else>
								<!-- 默认的样式 -->
								<a href="javascript:queryPage(${pageFinder.pageNo-4+row})"> ${pageFinder.pageNo-4+row}</a>
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
				<a class="nextdis" href="javascript:;">下一页</a>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo+1})" class="next"  title="下一页"  >下一页</a>
			</#if>
			
			<input id="query.page" name="query.page" value="${pageFinder.pageNo}"  onkeyup="value=value.replace(/[^\d]/g,'')"  class="inputtxt" type="text" value="1" title="输入页码后点击转到" style="float:left;height:20px;width:20px;text-align:center;padding:0px;" />
			<input class="btn_normal pagego_btn" type="button" name="" value="转到" onclick="queryPage(0);">

		</#if>
	</div>
</#if>
</#macro>