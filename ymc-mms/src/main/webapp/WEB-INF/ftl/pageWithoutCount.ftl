<!-- 自定义翻页标签宏 -->
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
	    for (i=0;i<arr.length ;i++ )
	    {
	        str+=arr[i];
	    }
	    return str;
	}
	/**
	*如果totalRows>=1000,则去除财务分隔符逗号
	*否则转换为数字类型
	*/
	if(ck(totalRows)){
		totalRows = split(totalRows);
	}else{
		totalRows = Number(totalRows);
	}

	function queryPage(pageNo,size){
      var pageForm = (formId&&formId!="")?document.getElementById(formId):document.forms[0];
		if(size>=20)
		{
			var artionUrl = pageForm.getAttribute("action");
			var pageInput =	document.createElement("input");
			pageInput.setAttribute("type", "hidden");
			pageInput.setAttribute("name", "pageSize");
			pageInput.setAttribute("value", size);
			pageForm.appendChild(pageInput);
			
		}else{
			if(isNaN(pageSize)){
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
	// 				if(artionUrl.indexOf("?")>0){
	// 					artionUrl = artionUrl + "&page="+toPage;
	// 				}else{
	// 					artionUrl = artionUrl + "?page="+toPage;
	// 				}
	// 				pageForm.setAttribute("action",artionUrl);
	
	 				var pageInput =	document.createElement("input");
	 				pageInput.setAttribute("type", "hidden");
	 				pageInput.setAttribute("name", "page");
	 				pageInput.setAttribute("value", toPage);
					pageForm.appendChild(pageInput);
					
					var pageInput1 =	document.createElement("input");
	 				pageInput1.setAttribute("type", "hidden");
	 				pageInput1.setAttribute("name", "pageSize");
	 				pageInput1.setAttribute("value", pageSize);
					pageForm.appendChild(pageInput1);
				}
		 }
			pageForm.submit();
	}

</script>
<#setting number_format="0">
<#if pageFinder?? && pageFinder.data?? && (pageFinder.rowCount > 0)>
	<div class="page">
		<#if (pageFinder.pageCount > 1) >
			<#if (pageFinder.hasPrevious == false) >
				<span class="page-pre-no"></span>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo-1})" class="prev"  title="上一页">上一页</a>
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
				<span class="page-next-no"></span>
			<#else>
				<a href="javascript:queryPage(${pageFinder.pageNo+1})" class="next"  title="下一页"  >下一页</a>
			</#if>
			<input class="pager_input" id="query.page" style="width: 30px;" name="query.page" type="text" maxlength="5" value="${pageFinder.pageNo}"  class="pagenum" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			<input type="button" class="btn2" onClick="queryPage(0);" value="跳转">
		</#if>
	</div>
</#if>
</#macro>
<!-- fckeditor编辑器标签  name:标签名称 value:控件值 height:高度 width:宽度   	no：商品编号-->
<#macro fckeditor name value="" no="" height="200px" width="100%">
	<#if !height?ends_with("px") >
		<#assign height = height +"px" />
	</#if>
	<script type="text/javascript"  src="${BasePath}/js/common/kindeditor3/kindeditor-min.js"></script>
	<script>
      KE.show({id:'${name}', allowFileManager :false});
	  KE.configServer('','${name}','${no?default("")}');
  	</script>
	<textarea id="${name}" name="${name}" style="width:${width};height:${height};visibility:hidden;">${value?replace("&","&amp;")?replace("<","&lt;")?replace(">","&gt;")?replace("\"","&quot;")}</textarea>
</#macro>
