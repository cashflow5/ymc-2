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
		if(isNaN(pageSize)) {
			alert('每页条数只能为数字');
			return;
		}
		var totalPage=Math.ceil(totalRows/pageSize);
		var toPage=pageNo;
		if(pageNo==0){
			toPage=document.getElementById("query.page").value;
			if(isNaN(toPage) || toPage<=0){
				alert("请输入大于0的整数.");
				return;
			}

			if(toPage>totalPage){
				alert("没有当前页数");
				return;
			}
		}
		//校验是跳转页是否在记录有效范围内
		//取大于等于总页数的值
		if(toPage>totalPage){
			alert("已经到最后一页");
			return;
		}

		var pageForm = (formId&&formId!="")?document.getElementById(formId):document.forms[0];
		var arr = pageForm.elements;
		var flag = false;
		for(var i=0,j=arr.length;i<j;i++){
			if(arr[i].getAttribute("name")=="query.page"){
				flag = true;
				break;
			}
		}
		if(!flag){
			var artionUrl = pageForm.getAttribute("action");
			var pageInput =	document.createElement("input");
			pageInput.setAttribute("type", "hidden");
			pageInput.setAttribute("name", "page");
			pageInput.setAttribute("value", toPage);
			pageForm.appendChild(pageInput);
		}
		pageForm.submit();
	}
</script>
<#setting number_format="0">
<#if pageFinder?? && pageFinder.data?? && (pageFinder.rowCount > 0)>
	<div class="page">
		<#if (pageFinder.pageCount > 1) >
			<#if (pageFinder.hasPrevious == false) >
				<a class="prevdis top-prevdis" href="javascript:;">上一页</a>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo-1})" class="prev top-prev"  title="上一页">上一页</a>
			</#if>
			
			<#if (pageFinder.hasNext == false) >
				<span class="page-next-no"></span>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo+1})" class="next"  title="下一页"  >下一页</a>
			</#if>
		</#if>
	</div>
</#if>
</#macro>