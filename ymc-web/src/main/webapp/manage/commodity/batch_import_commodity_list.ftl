<form id="queryForm" action="#" method="post">
<input id="rowCount_${pageFinder.pageNo}" type="text" size="35" value="${pageFinder.rowCount}" class="inputtxt" readonly="readonly" style="display:none;"/>
<input id="pageSize_${pageFinder.pageNo}" type="text" size="35" value="${pageFinder.pageSize}" class="inputtxt" readonly="readonly" style="display:none;"/>
<table id="table_${pageFinder.pageNo}" class="list_table" style="width: 100%; margin-top: 0;">
	<thead>
		<tr>
	    	<th style="width:3%;"></th>
	        <th style="width:11%;">商品名称</th>
	        <th style="width:5%;">商品编码</th>
	        <th style="width:5%;">上传图片状态</th>
	        <th style="width:6%;">商品款号</th>
	        <th style="width:6%;">款色编码</th>
	        <th style="width:5%;">颜色</th>
	        <th style="width:5%;">品牌</th>
	        <th style="width:5%;">年份</th>
	        <th style="width:7%;">价格</th>
	        <th style="width:6%;">操作</th>
	    </tr>
	</thead>
	<tbody>
	<#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
		<#list pageFinder.data as item>
			<tr>
			    <td><input type="checkbox" name="commodityNo" code="${item.supplierCode!''}" value="${item.commodityNo!''}" <#if !item.uploadStatus?? || item.uploadStatus!=2>numbers="${item.pictures?size},${item.numbers}" disabled="disabled"</#if>/></td>
			    <td id="td_${item.commodityNo!''}" style="text-align:left;"><a href="${commodityPreviewUrl!''}?commodityNo=${item.commodityNo!''}" target="_blank">${item.commodityName!''}</a></td>
			    <td style="text-align:left;">${item.commodityNo!''}</td>
			    <td>
			    	<a href="javascript:;" class="picDetail" dtitle="${item.commodityName!''}(已上传商品图片)">
			    	<#if !item.uploadStatus?? || item.uploadStatus== 0>
			    	<span style="color:#EE4000;">未上传<span>
			    	<#elseif item.uploadStatus==1>
			    	<span style="color:#EEC900;">部分上传</span>
			    	<#else>
			    	<span style="color:#228B22;">已上传</span>
			    	</#if>
		    		<div style="display: none;">
		    			<#if item.pictures?? && item.pictures?size != 0>
		    				<#assign x = 0/>
		    				<div style="margin: 10px;">
		    					<b>已上传角度图片：</b><br/>
			    				<#list item.pictures as picture>
			    					<#-- if picture.picName?ends_with('_l.jpg')-->
			    					<#if picture.picName?matches('.*_l\\.jpg.*')>
			    						<#assign x = x + 1/>
			    						<span style="display: inline-block; width: 140px; height: 25px; line-height: 25px; text-align: center;">${picture.picName?replace("\\?[0-9]*", "","ri")}</span>
			    						<#if x % 4 == 0><br/></#if>
			    					</#if>
			    				</#list>
		    				</div>
		    				<hr style="border: dashed 1px #ccc;"/>
		    				<#assign x = 0/>
		    				<div style="margin: 10px;">
			    				<b>已上传描述图片：</b><br/>
			    				<#list item.pictures as picture>
			    					<#--if picture.picName?ends_with('_b.jpg')-->
			    					<#if picture.picName?matches('.*_b\\.jpg.*')>
			    						<#assign x = x + 1/>
			    						<span style="display: inline-block; width: 140px; height: 25px; line-height: 25px; text-align: center;">${picture.picName?replace("\\?[0-9]*", "","ri")}</span>
			    						<#if x % 4 == 0><br/></#if>
			    					</#if>
			    				</#list>
		    				</div>
		    			<#else>
		    				<div style="margin: 10px;">
		    					<span style="display: inline-block; width: 140px; height: 25px; line-height: 25px; text-align: center;">暂时没上传任何图片</span>
		    				</div>
		    			</#if>
		    		</div>
			    	</a>
			    </td>
			    <td style="text-align:left;">${item.styleNo!''}</td>
			    <td style="text-align:left;">${item.supplierCode!''}</td>
			    <td>${item.colorName!''}</td>
			    <td>${item.brandName!''}</td>
			    <td>${item.years!''}</td>
			    <td style="text-align:left;">市场价:${item.markPrice}<br>销售价:${item.sellPrice}</td>
			    <td>
			    	<#--<a href="${BasePath}/commodity/imported/preview.sc?commodityNo=${item.commodityNo!''}" target="_blank">预览</a>-->
			    	<a href="${BasePath}/commodity/preUpdateCommodity.sc?commodityNo=${item.commodityNo!''}" target="_blank">修改</a>
			    	<a href="javascript:;" onclick="javascript:singleImportPic('${item.commodityNo!''}');return false;">上传图片</a><br>
			    	<a href="javascript:;" onclick="javascript:deleteUnauditedCommodity('${item.commodityNo!''}', '${item.supplierCode!''}');return false;">删除</a>
			    	<#if item.uploadStatus?? && item.uploadStatus ==2>
			    	<a href="javascript:;" onclick="javascript:singleSubmitAudit('${item.commodityNo!''}');return false;">提交审核</a>
			    	<#else>
			    	<span style="color:#CAC8BB">提交审核</span>
			    	</#if>
			    </td>
			</tr>
		</#list>
		<script type="text/javascript">
		$('.checkedall').attr('disabled', false);
		$('.button.dis').removeClass('dis');
		
		$(".picDetail").hover(function() {
			var _this = $(this);
			var _top = _this.offset().top - $(document).scrollTop();
			var imgDiv = $(this).find('div');
			ygdg.dialog({
				padding : 0,
				title : '已上传商品图片'/*$(this).attr("dtitle")*/,
				content : '' + imgDiv.html() + '',
				id : 'detailBox',
				left : $(this).offset().left + $(this).width() + 10,
				top : _top - imgDiv.height(),
				closable : false,
				fiexed : true
			});
		}, function(){
			if(ygdg.dialog.list['detailBox']){
				ygdg.dialog.list['detailBox'].close();
			}
		});

	   var totalRows = '${pageFinder.rowCount}';
	   var pageSize = ${pageFinder.pageSize};
	  /**
	   *如果totalRows>=1000,则去除财务分隔符逗号
	   *否则转换为数字类型
	   */
	  if(ck(totalRows)) {
		totalRows = split(totalRows);
	  } else {
		totalRows = Number(totalRows);
	  }

	  /**
	   *pageNo!=0时，no为空，按照分页的数字页码按钮来调用
	   *pageNo=0时，no为当前页面标识，标示当前页面的转到按钮来调用本方法
	   */
		function queryPage(pageNo,no) {
		var toPage = pageNo;
		if (pageNo == 0) {
		    
			toPage = document.getElementById('query.page_'+no).value;
			if (isNaN(toPage) || toPage <= 0) {
				alert("请输入大于0的整数.");
				return;
			}
			var totalPage = Math.ceil(totalRows / pageSize);
			if (toPage > totalPage) {
				alert("没有当前页数");
				return;
			}
		  }
		var _pg=$('#table_'+toPage);
		if(_pg[0]){
		$('html,body').animate({scrollTop:_pg.offset().top},200);
		}
		loadUnauditedCommodity(toPage);
		}
		</script>
	</#if>
	</tbody>
</table>
</form>
<!--分页start-->
<div class="page_box">
	<#if pageFinder?? && pageFinder.data?? && (pageFinder.rowCount > 0)>
	<div class="page">
		<#if pageFinder ?? && pageFinder.rowCount ??>
			<span style="float:left;padding-right:15px;line-height:23px;">
			总记录行数：<font color ="red">${pageFinder.rowCount}</font>
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
			<input id="query.page_${pageFinder.pageNo}" name="query.page" value="${pageFinder.pageNo}"  onkeyup="value=value.replace(/[^\d]/g,'')"  class="inputtxt" type="text" value="1" title="输入页码后点击转到" style="float:left;height:20px;width:40px;text-align:center;" />
			<input class="btn_normal pagego_btn" type="button" name="" value="转到" onclick="queryPage(0,${pageFinder.pageNo});">
		</#if>
	</div>
</#if>
</div>
<!--分页end-->
