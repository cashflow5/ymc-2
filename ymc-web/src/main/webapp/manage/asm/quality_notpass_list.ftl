<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-质检查询</title>
<style>
.search_box label {
    width: 90px;
}
.list_table tr.on td .list_detail_table tr td{background:#fff;}
.squa{border:1px solid #ddd;padding:5px 10px;border-radius:5px;display:inline-block;height:20px;line-height:20px;}
.common_lst1{padding-left:18px;}
</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 售后质检 &gt; 质检不通过处理 </p>
			<div class="tab_panel  relative">
				<p style="position:absolute;top:0;right:0;z-index:101;"><span class="fl ml5" style="color:#0066CC;"><a onclick="showHelp();" href="javascript:void(0)">帮助文档</a></span></p>
			    <ul class="tab">
					<li id="tab_1" onclick="location.href='${BasePath}/quality/notPassList.sc?tab=1'"><span>维修</span></li>
					<li id="tab_2" onclick="location.href='${BasePath}/quality/notPassList.sc?tab=2'"><span>退回顾客</span></li>
					<li id="tab_3" onclick="location.href='${BasePath}/quality/notPassList.sc?tab=3'"><span>转退换货</span></li>
				</ul>
				<div class="tab_content">

				<p class="blank10"></p>
					<!--搜索start-->
					<div class="search_box" style="padding-top: 0px;">
						<form id="queryForm" name="queryForm" action="${BasePath}/qualityquery/qualityList.sc" method="post">
							<p>
								<span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderSubNo" name="orderSubNo" value="${vo.orderSubNo!''}"/>
								<input style="display:none;" type="text" class="inputtxt" id="tab" name="tab" value="${vo.tab!''}"/>
								</span>
								<span>
							    <label  style="width: 200px;">收货人：</label>
								<input type="text" class="inputtxt" id="userName" name="userName" value="${vo.userName!''}"/></span>
								<span><label  style="width: 200px;">联系手机：</label>
								<input type="text" class="inputtxt" id="mobilePhone" name="mobilePhone" value="${vo.mobilePhone!''}"/></span>
							</p>
							<#if (!vo.tab??)||vo.tab=='1'||vo.tab=='2'>
							<p>
								<span><label>寄出快递单号：</label>
								<input type="text" class="inputtxt" id="expressCode" name="expressCode" value="${vo.expressCode!''}"/></span>
								<span><label style="width: 200px;">寄出快递公司：</label>
								<select id="logisticsCode" name="logisticsCode" style="width:129px;">
									<option value="">请选择</option>
									<#list expressInfos as item>
										<option <#if item['id']??&&vo.logisticsCode??&&vo.logisticsCode==(item['id'])>selected</#if> value="${item['id']!""}">${item['express_name']?default('')}</option>
									</#list>
								</select>
								<span><label style="width: 198px;"><#if vo.tab??&&vo.tab=='2'>状态：<#else>维修状态：</#if></label>
								<select id="zjHandleStatus" name="zjHandleStatus" style="width:126px;">
									<option value="">全部</option>
									<#if vo.tab??&&vo.tab=='2'>
									<option <#if vo.zjHandleStatus??&&vo.zjHandleStatus=='4'>selected</#if> value="4">待退回</option>
									<#else>
									<option <#if vo.zjHandleStatus??&&vo.zjHandleStatus=='1'>selected</#if> value="1">待维修</option>
									<option <#if vo.zjHandleStatus??&&vo.zjHandleStatus=='3'>selected</#if> value="3">已维修待退回</option>
									</#if>
									<option <#if vo.zjHandleStatus??&&vo.zjHandleStatus=='5'>selected</#if> value="5">已退回</option>
								</select>
								</span>
						    </p>
							</#if>
							<p>
							<span><label>质检日期：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="qaTimeStart" name="qaTimeStart" value="${vo.qaTimeStart!''}" readonly="readonly"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="qaTimeEnd" name="qaTimeEnd" value="${vo.qaTimeEnd!''}" readonly="readonly"/>
							</span>
								<#if (!vo.tab??)||vo.tab=='1'>
								<span><label  style="width: 135px;">维修日期：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="repairTimeStart" name="repairTimeStart" value="${vo.repairTimeStart!''}" readonly="readonly"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="repairTimeEnd" name="repairTimeEnd" value="${vo.repairTimeEnd!''}" readonly="readonly"/>
							</span>
							</#if>
								
							<#if vo.tab??&&vo.tab=='3'>
							<span><label style="width: 136px;">状态：</label>
								<select id="zjHandleStatus" name="zjHandleStatus" style="width:126px;">
									<option value="">全部</option>
                                    <option <#if vo.zjHandleStatus??&&vo.zjHandleStatus=='6'>selected</#if> value="6">待转为正常质检</option>
									<option <#if vo.zjHandleStatus??&&vo.zjHandleStatus=='7'>selected</#if> value="7">已转为正常质检</option>
								</select>
							</span>
							</#if>
								<span style="padding-left:128px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->

					<!--列表start-->
					<style>
						.common_proitm td{border:1px solid #f2f2f2;}
						.common_proitm .line_gap td{border:none;}
					</style>
					<table class="list_table">
						<thead>
							<tr>
								<th>订单号</th>
								<#if vo.tab??&&(vo.tab=='2'||vo.tab=='3')>
								<th>实收货品条码</th>
								</#if>
								<th>收货人</th>
								<th>联系方式</th>
								<#if (!vo.tab??)||vo.tab!='3'>
								<th>物流公司</th>
								<th>寄出快递单</th>
								</#if>
								<th>质检日期</th>
								<#if (!vo.tab??)||vo.tab=='1'>
								<th>维修日期</th>
								</#if>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody class="common_proitm">
						 <#if pageFinder?? && (pageFinder.data)?? && pageFinder.data?size gt 0> 
							<#list pageFinder.data as detail>
							<tr>
			                    <td>${detail.orderSubNo?default('')}</td>
			                    <#if vo.tab??&&(vo.tab=='2'||vo.tab=='3')>
			                    <td>${detail.qaInsideCode?default('')}</td>
			                    </#if>
			                    <td>${detail.userName?default('')}</td>
			                    <td>${detail.mobilePhone?default('')}</td>
			                    <#if (!vo.tab??)||vo.tab!='3'>
			                    <td>${detail.returnLogisticsName?default('')}</td>
			                    <td>${detail.returnExpressCode?default('')}</td>
			                    </#if>
			                    <td>${detail.qaDate?string("yyyy-MM-dd HH:mm:ss")}</td>
			                    <#if (!vo.tab??)||vo.tab=='1'>
			                    <td>${(detail.repairDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
			                    </#if>
                                <td><#if detail.zjHandleStatus??&&detail.zjHandleStatus=='1'>待维修
                                <#elseif detail.zjHandleStatus??&&detail.zjHandleStatus=='2'>维修中
                                <#elseif detail.zjHandleStatus??&&detail.zjHandleStatus=='3'>已维修待退回
                                <#elseif detail.zjHandleStatus??&&detail.zjHandleStatus=='4'>待退回
                                <#elseif detail.zjHandleStatus??&&detail.zjHandleStatus=='5'>已退回
                                <#elseif detail.zjHandleStatus??&&detail.zjHandleStatus=='6'>待转为正常质检
                                <#elseif detail.zjHandleStatus??&&detail.zjHandleStatus=='7'>已转为正常质检</#if></td>
                                <td>
                                <#if detail.zjHandleStatus??&&detail.zjHandleStatus=='1'>
                                <a href="javascript:repairComplate('${detail.id?default('')}','${detail.applyNo?default('')}')">维修完成</a>&nbsp;&nbsp;
                                <#elseif detail.zjHandleStatus??&&(detail.zjHandleStatus=='3'||detail.zjHandleStatus=='4')>
                                <a href="javascript:returnGood('${detail.id?default('')}','${detail.applyNo?default('')}')">录入</a>&nbsp;&nbsp;
                                <#elseif detail.zjHandleStatus??&&(detail.zjHandleStatus=='6')>
                                <a href="javascript:changeToQA('${detail.id?default('')}')">转正常质检</a>&nbsp;&nbsp;
                                </#if>
                                <a href="${BasePath}/quality/returnDetail.sc?id=${detail.id?default('')}&orderSubNo=${detail.orderSubNo?default('')}&qaInsideCode=${detail.qaInsideCode?default('')}&csConfirmStatus=${vo.tab?default('')}" target="_blank">查看</a></td>
                            </tr>
						  </#list>
						<#else>
							<tr>
								<td colspan="12" class="td-no">暂无记录！</td>
							</tr>
						</#if>
						</tbody>
					</table>
					<!--列表end-->
				<!--分页start-->
				<#if pageFinder??&&pageFinder.data??&&pageFinder.data?size gt 0>
				<div class="page_box">
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryForm"/>
				</div>
				</#if>
				<!--分页end-->
			</div>
			</div>
		</div>
	</div>
</body>
<script>
$("#qaTimeStart").calendar({maxDate:'#qaTimeEnd'});
$("#qaTimeEnd").calendar({minDate:'#qaTimeStart'});
$("#repairTimeStart").calendar({maxDate:'#repairTimeEnd'});
$("#repairTimeEnd").calendar({minDate:'#repairTimeStart'});
if("${vo.tab?default('1')}"=="1"){
$("#tab_1").addClass('curr').siblings().removeClass('curr');
}else if("${vo.tab?default('1')}"=="2"){
$("#tab_2").addClass('curr').siblings().removeClass('curr');
}else if("${vo.tab?default('1')}"=="3"){
$("#tab_3").addClass('curr').siblings().removeClass('curr');
}

//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/quality/notPassList.sc";
	queryForm.submit();
}

function repairComplate(id,applyNo){
    if(id!="null"){
    if(confirm("您要确认维修完成吗？")){
		$.ajax({
			type: "post", 
			url: "${BasePath}/quality/repairComplate.sc?id=" + id +"&applyNo="+applyNo, 
			success: function(dt){
				if("success"==dt){
				   ygdg.dialog.alert("更新状态成功!");
				   refreshpage();
				}else{
				   ygdg.dialog.alert("更新状态失败!");
				}
			} 
		});
   }
   }
}

function changeToQA(id){
    if(id!="null"){
    if(confirm("您确认要转正常质检吗？")){
		$.ajax({
			type: "post", 
			url: "${BasePath}/quality/changeToQA.sc?id=" + id, 
			success: function(dt){
				if("success"==dt){
				   ygdg.dialog.alert("更新状态成功!");
				   refreshpage();
				}else{
				   ygdg.dialog.alert("更新状态失败!");
				}
			} 
		});
   }
   }
}

var dia ;
function returnGood(id,applyNo){
   var initopenContent = '<form id="saveForm" name="saveForm" action="${BasePath}/quality/returnGood.sc" method="post">'
   +'<div class="form_container">'
   +'<table class="form_table"><tbody>'
   +'<tr><th><span style="color:red;">*</span>寄出快递单号：</th><td><input type="text" name="returnExpressCode" id="returnExpressCode" class="ginput" style="width:120px;"></td></tr>'
   +'<tr><th><span style="color:red;">*</span>快递公司：</th><td><select name="returnLogisticsCode" id="returnLogisticsCode" style="width:124px;"><#if expressInfos??><option value="">请选择</option><#list expressInfos as item><option value="${item['id']!""}">${item['express_name']!""}</option></#list></#if></select>&nbsp;&nbsp;<span style="color:red;" id="contactError"></span></td></tr>'
   +'<tr><th><span style="color:red;">*</span>费用：</th><td><input type="text" style="width:120px;" class="ginput" id="retrunFee" name="retrunFee">&nbsp;&nbsp;<input type="checkbox" value="1" name="returnIsDelivery" id="returnIsDelivery"/>到付<span style="color:red;" id="contactError"></span></td></tr>'
   +'<tr><th>备注：</th><td><textarea name="returnRemark" style="width:300px;height:160px" id="returnRemark"></textarea></td></tr>'
   +'<tr><td style="text-align:left;">&nbsp;</td><td style="margin:10px 10px;text-align:left;"><a class="button" onclick="ajaxsaveReturn(\''+id+'\',\''+applyNo+'\');"><span>保存</span></a><a class="button" onclick="dia.close();"><span>返回</span></a></td></tr></tbody></table></div></form>';
   dia = ygdg.dialog({content:initopenContent,lock:true,id:'return_info_input',title:'退回信息录入'});
}
function ajaxsaveReturn(id,applyNo){
  $.ajax({
			type: "post", 
			url: "${BasePath}/quality/returnGood.sc?id=" + id+"&expressCode="+$("#returnExpressCode").val()+"&logisticsCode="+$("#returnLogisticsCode").val()+"&retrunFee="+$("#retrunFee").val()+"&returnIsDelivery="+($("#returnIsDelivery").attr("checked")=="checked"?"1":"0")+"&returnRemark="+$("#returnRemark").val()+"&applyNo="+applyNo, 
			success: function(dt){
				if("success"==dt){
				   ygdg.dialog.alert("保存成功!");
				   refreshpage();
				   dia.close();
				}else{
				   ygdg.dialog.alert("保存失败!");
				}
			} 
  });
}
function showHelp(){
	var _html='<div style="line-height:20px;width:600px;padding:10px;">'
	+'<strong>1.质检登记是否通过标准</strong><br/>'
	+'<ul class="common_lst1 clearfix"><li>收到商品超过10天（质量问题除外）。</li>'
	+'<li>商品有明显折痕、磨损、洗涤、影响二次销售。</li>'
	+'<li>无质量问题并已使用过、鞋子脏污、无法清洁、已修改或加工的商品。</li>'
	+'<li>附属配件或赠品短缺或已经使用，商品的外包装损坏（包含包裹填充物  及外包装盒），鞋盒上 有粘胶带类物品。</li></ul>'
	+'<strong>2.质检不通过转维修说明</strong><br/>'
	+'<ul class="common_lst1 clearfix"><li>商家在收到顾客退货后判断是否质检通过，如果不通过则不会给顾客进行退货或换货，但可以基于实际情况给予顾客维修。</li>'
	+'<li>在经过沟通协商后，优购客服会生成一条维修记录流入到商家中心，商家接收到信息后可以开始维修，维修完成后在系统中点击确认将该商品置为已维修待退回。</li>'
	+'<li>维修后将商品寄回给顾客，则商家需要录入商品的寄回物流信息及相关备注说明告知优购客服。</li>'
	+'<li><span class="squa">待维修</span>——><span class="squa">已维修待退回</span>——><span class="squa">已退回</span></li></ul>'
	+'<strong>3.质检不通过直接退回给顾客说明</strong><br/>'
	+'<ul class="common_lst1 clearfix"><li>商家录入质检不通过后，信息传递给优购客服，优购客服核实并与顾客、商家三方协商后同意将该商品直接寄回给顾客。</li>'
	+'<li>商家在寄回给顾客后需要在系统中将该信息置为已退回并录入相关退回物流信息。</li></ul>'
	+'<strong>4.质检不通过转退货处理说明</strong><br/>'
	+'<ul class="common_lst1 clearfix"><li>商家录入质检不通过后，信息传递给优购客服，优购客服核实并与顾客、商家三方协商后同意顾客的退货请求，转为退货处理，从而给顾客全额退款。</li></ul>'
	+'<strong>5.质检不通过转换货处理说明</strong><br/>'
	+'<ul class="common_lst1 clearfix"><li>商家录入质检不通过后，信息传递给优购客服，优购客服核实并与顾客、商家三方协商后同意顾客的换货请求，转为换货处理，客服会生成换货单，流入商家系统后商家将换货发出。</li></ul>';
    ygdgDialog({
       id:'test',
       title:'质检不通过帮助文档',
       content: _html
    });
}
</script>
</html>
