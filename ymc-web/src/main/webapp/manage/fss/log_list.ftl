<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺装修-品牌旗舰店-优购网</title>
</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 我的店铺 &gt; 店铺日志</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>日志列表</span>
				</li>
			</ul>
			<div class="tab_content"> 
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath !}/mctfss/log/list.sc" method="post">
						<p>
							<span>
								<label>店铺名称：</label>
								<select id="storeId" name="storeId">
						            <#if (storeList?? && storeList?size>0) >
							            <option value="">选择店铺名称</option>
							            <#list storeList as item >
							            <option value="${(item.shopId) !}" <#if ((fssLog.storeId)?? && fssLog.storeId==item.shopId)>selected</#if>>${(item.shopName) !}</option>
							            </#list>
						            </#if>
						        </select>
							</span>
							<span>
								<label style="width:100px;">操作人：</label>
								<input type="text" name="logUserName" id="logUserName" value="${(fssLog.logUserName) !}" class="inputtxt" />
							</span>
							<span>
								<label>日志类型：</label>
								<select id="logType" name ="logType">
			    		 	    	<option value="">----全部----</option>
			    		 	        <option value="FSS_PUBLISH_ERROR" <#if (fssLog.logType)?default("") == "FSS_PUBLISH_ERROR"> selected</#if>>发布错误日志 </option>
			    		 	        <option value="FSS_ERROR" <#if (fssLog.logType)?default("") == "FSS_ERROR"> selected</#if>>一般错误日志 </option>
			    		 	        <option value="FSS_INFO"  <#if (fssLog.logType)?default("") == "FSS_INFO"> selected</#if>>用户操作日志 </option>
			    		 	    </select>
							</span>
							<span>
								<label>信息内容：</label>
								<input type="text" name="message" id="message" value="${(fssLog.message) !}" class="inputtxt" />
							</span>
							<span>
								<label>起始时间：</label>
								<input type="text" name="timeBefore" id="timeBefore" value="${(fssLog.timeBefore) !}" readonly="true" class="inputtxt" />
							</span>
							<span>
								<label>结束时间：</label>
								<input type="text" name="timeAfter" id="timeAfter" value="${(fssLog.timeAfter) !}" readonly="true" class="inputtxt" />
							</span>
							<span>
								<a class="button" id="mySubmit" onclick="searchSubmit();">
									<span>搜索</span>
								</a>
							</span>
						</p>
					</form>
				</div>
				<!--列表start-->
				<table class="list_table">
					<thead>
						<tr>
							<th width="20">NO.</th>
							<th width="80">店名</th>
							<th width="80">操作人</th>
							<th width="100">操作时间</th>
							<th width="80">操作类型</th>
							<th width="250">日志信息</th>
						</tr>
					</thead>
					<tbody>
						<#if pageFinder?? && (pageFinder.data)?? && pageFinder.data?size != 0> 
							<#list pageFinder.data as item>
							<tr>
								<td>${item_index+1}</td>
								<td>${(item.storeName) !}</td>
								<td>${(item.logUserName) !}</td>
								<td>${(item.logTime?string("yyyy-MM-dd HH:mm:ss")) !}</td>
								<td>
									<#if ((item.logType)?? && item.logType == "FSS_PUBLISH_ERROR")>
										发布错误日志
									<#elseif ((item.logType)?? && item.logType == "FSS_ERROR")>
										一般错误日志
									<#elseif ((item.logType)?? && item.logType == "FSS_INFO")>
										用户操作日志
									</#if>
								</td>
								<td  style="text-align:left;">
									<a href="javascript:;" onclick="javascript:toDetail('${(item.id) !}')">
										<#if (item.message)?length lt 40>${(item.message)?default('')}<#else> ${(item.message)?default('')[0..39]}... </#if>
									</a>
								</td>
							</tr>
							</#list>
						<#else>
							<tr>
								<td class="td-no" colspan="6">暂无数据!</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!--列表end--> 
				<#if pageFinder ?? && pageFinder.data??>
					<!--分页start-->
					     <div class="page_box">
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						 </div>
					<!--分页结束--> 
				</#if>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	$("#timeBefore").calendar({maxDate:'#timeAfter'});
	$("#timeAfter").calendar({minDate:'#timeBefore'});	
	
	//根据条件进行搜索
	 function searchSubmit(){
	     document.queryVoform.submit();
	 }
	 
	//显示详情
	 function toDetail(id){
	 	openwindow("${BasePath!}/mctfss/log/showLogDetail.sc?id="+id,1000,400,'日志详情');
	 };
</script>   
</html>
