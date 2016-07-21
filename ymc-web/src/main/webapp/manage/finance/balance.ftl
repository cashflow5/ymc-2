<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-结算单查询</title>
<script type="text/javascript">	
</script>
</head>
<body>
	
	
	<div class="main_container">
		
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 财务 &gt; 结算单查询</p>
					<div class="tab_panel">
							<ul class="tab">
							<li class="curr"><span>结算单查询列表</span></li>
							</ul>
						<div class="tab_content">
					<!--搜索start-->
					<div class="search_box">
						<form action="" method="post" id="queryVoform" action="${BasePath}/finance/balancebill/queryAll.sc">
						<p>
						<span><label style="width:100px;">结算单编号：</label><input type="text" name="balanceBillNumber" id="balanceBillNumber" value="${balanceBill.balanceBillNumber?default('')}"  class="inputtxt" style="width:188px;" /></span>
						<span>
						<label style="width:100px;">结算单状态：</label>
						<select id="balanceBillStatus" name="balanceBillStatus" style="width:130px;">
							<option value="">请选择</option>
							<option  value="0" <#if balanceBill.balanceBillStatus??><#if balanceBill.balanceBillStatus=0>selected="selected"</#if></#if>>新建</option>
							<option  value="1" <#if balanceBill.balanceBillStatus??><#if balanceBill.balanceBillStatus=1>selected="selected"</#if></#if>>已结算</option>
							<option  value="2" <#if balanceBill.balanceBillStatus??><#if balanceBill.balanceBillStatus=2>selected="selected"</#if></#if>>已作废</option>
						</select>
						</span>
						<span><label>结算类型：</label>
						<select id="balanceType" name="balanceType" style="width:130px;">
							<option value="">请选择</option>
							<option  value="1" <#if balanceBill.balanceType??><#if balanceBill.balanceType=1>selected="selected"</#if></#if> >底价结算</option>
							<option  value="2" <#if balanceBill.balanceType??><#if balanceBill.balanceType=2>selected="selected"</#if></#if>>配折结算</option>
							<option  value="3" <#if balanceBill.balanceType??><#if balanceBill.balanceType=3>selected="selected"</#if></#if>>扣点结算</option>
							<option  value="4" <#if balanceBill.balanceType??><#if balanceBill.balanceType=4>selected="selected"</#if></#if>>促销结算</option>
						</select>
						</span>
						</p>
						<p>
						<span><label style="width:100px;">起止时间：</label>
						<input type="text" name="startTime" id="startTime"  class="inputtxt" style="width:80px;" value="${startTime?default('')}" /> 至
						<input type="text" name="endTime" id="endTime"  class="inputtxt" style="width:80px;" value="${endTime?default('')}" />
						</span>
						<span><a class="button" id="mySubmit"><span onclick="$('#queryVoform').submit()">搜索</span></a></span>
						</p>
						</form>
				</div>
				<!--搜索end-->
						<!--列表start-->
						<table class="list_table">
						<thead>
						<tr>
						<th>结算单编号</th>
						<th>结算单状态</th>
						<th>结算类型</th>
						<th>销售金额</th>
						<th>结算开始时间</th>
						<th>结算结束时间</th>
						<th>付款日期</th>
						<th>操作</th>
						</tr>
						</thead>
						<tbody>
							<#if balanceBillList??>
								<#list balanceBillList as item>
									<tr >
										<td>${item.balanceBillNumber?default('')}</td>
										<td> 
											<#if item.balanceBillStatus??&&item.balanceBillStatus==0>
													<span style="color:green">新建</span>
											<#elseif item.balanceBillStatus??&&item.balanceBillStatus==1>
													<span style="color:red">已结算</span>
											<#elseif item.balanceBillStatus??&&item.balanceBillStatus==2>
													<span style="color:blue">作废</span>
											</#if>
										</td>
										<td>
											<#if item.balanceType??&&item.balanceType==1>
													<span>底价结算</span>
											<#elseif item.balanceType??&&item.balanceType==2>
													<span>配折结算</span>
											<#elseif item.balanceType??&&item.balanceType==3>
													<span>扣点结算</span>
											<#elseif item.balanceType??&&item.balanceType==4>
													<span>促销结算</span>
											</#if>
										</td>
										<td>￥${item.balanceMoney?default('')}</td>
										<td><#if item.balanceStartDate??>${item.balanceStartDate?string("yyyy-MM-dd")}</#if></td>
										<td><#if item.balanceEndDate??>${item.balanceEndDate?string("yyyy-MM-dd")}</#if></td>
										<td></td>
										<td>
											<#if item.balanceType??&&item.balanceType==4>
												<a href="${BasePath}/finance/balancebill/cx_detail.sc?id=${item.id?default('')}">结算明细</a>
												<#else>
												<a href="${BasePath}/finance/balancebill/detail.sc?id=${item.id?default('')}">结算明细</a>
											</#if>
										</td>
									</tr>
								</#list>
							</#if>	
						
						
						</tbody>
						</table>
					<!--列表end-->
						<!--分页start-->
					<div class="page_box">
						<#if pageFinder ??>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						</#if>
					</div>
					<!--分页end-->
					</div>
				</div>
			</div>
		
			 
 </div>
	
	</div>
</body>
</html>
<script type="text/javascript">
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});
$("#startTime1").calendar({maxDate:'#endTime1'});
$("#endTime1").calendar({minDate:'#startTime1'});
</script>