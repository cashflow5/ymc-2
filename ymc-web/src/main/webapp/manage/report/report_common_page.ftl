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
		var toPage = pageNo;
		if (pageNo == 0) {
			toPage = document.getElementById("query.page").value;
			if (isNaN(toPage) || toPage <= 0) {
				alert("请输入大于0的整数.");
				return;
			}

			if (toPage > totalPage) {
				alert("没有当前页数");
				return;
			}
		}
		//校验是跳转页是否在记录有效范围内
		//取大于等于总页数的值
		if (toPage > totalPage) {
			alert("已经到最后一页");
			return;
		}
		//预处理分页提交条件
		if (typeof(onQueryPage) === 'function' && !onQueryPage.call(this, toPage)) {
			return;
		}

		var pageForm = (formId && formId != "") ? document
				.getElementById(formId) : document.forms[0];
		var arr = pageForm.elements;
		var flag = false;
		for ( var i = 0, j = arr.length; i < j; i++) {
			if (arr[i].getAttribute("name") == "query.page") {
				flag = true;
				break;
			}
		}
		if (!flag) {
			if(!(document.getElementById("pageInputId"))){
				var pageInput = document.createElement("input");
				pageInput.setAttribute("type", "hidden");
				pageInput.setAttribute("name", "page");
				pageInput.setAttribute("id","pageInputId");
				pageInput.setAttribute("value", toPage);
				pageForm.appendChild(pageInput);
			}
		}
		pageForm.submit();
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
	<#if pageFinder ?? && pageFinder.rowCount ??>
		<div class="page-row page-total">共${pageFinder.rowCount}项</div>
	</#if>
</#if>
</#macro>
