<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-发票查询</title>
<style>
.search_box label {
    width: 90px;
}
.list_table tr.on td .list_detail_table tr td{background:#fff;}
</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 首页&gt; 订单 &gt; 发票查询 </p>
			<div class="tab_panel">
			<!--搜索start-->
					<div class="search_box" style="padding-top: 0px;">
						<form id="queryForm" name="queryForm" action="${BasePath}/invoice/query.sc" method="post">
							<p>
							    <span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderMainNo" name="orderMainNo" value="${vo.orderMainNo!''}"/></span>
								<span><label>外部订单号：</label>
								<input type="text" class="inputtxt" id="outOrderNo" name="outOrderNo" value="${vo.outOrderNo!''}"/></span>
								<span><label>发票号：</label>
								<input type="text" class="inputtxt" id="invoiceNo" name="invoiceNo" value="${vo.invoiceNo!''}"/></span>
							</p>
							<p>
							    <span><label>发票类型：</label>
								<select id="invoiceType" name="invoiceType" style="width:126px;">
									<option <#if vo.invoiceType??&&vo.invoiceType==0>selected</#if> value="">全部</option>
									<option <#if vo.invoiceType??&&vo.invoiceType==1>selected</#if> value="1">普通发票</option>
									<option <#if vo.invoiceType??&&vo.invoiceType==2>selected</#if> value="2">增值税发票</option>
								</select>
								</span>
								
								<span><label>发票状态：</label>
								<select id="invoiceStatus" name="invoiceStatus" style="width:126px;">
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==-1>selected</#if> value="-1">全部</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==2>selected</#if> value="2">客服已审核</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==7>selected</#if> value="7">已配送</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==9>selected</#if> value="9">已取消</option>
									<option <#if vo.invoiceStatus??&&vo.invoiceStatus==11>selected</#if> value="11">发票拦截</option>
								</select>
								</span>
								<span><label>配送单号：</label>
								<input type="text" class="inputtxt" id="expressCode" name="expressCode" value="${vo.expressCode!''}"/></span>
								</p>
								<p>
								<span><label>创建时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="invoiceCreateTimeStart" name="invoiceCreateTimeStart" value="${vo.invoiceCreateTimeStart!''}"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="invoiceCreateTimeEnd" name="invoiceCreateTimeEnd" value="${vo.invoiceCreateTimeEnd!''}"/>
								</span>
								<span style="padding-left:105px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
								<span style="padding-left:95px;"><a href="javascript:;" onclick="javascript:doExportInvoice();">导出发票明细</a> </span>
								
							</p>
						</form>
					</div>
				<!--搜索end-->
			    <p class="blank20"></p>
			    <ul class="tab">
					<li onclick="location.href='${BasePath}/invoice/query.sc?invoiceStatus=2'" <#if vo.invoiceStatus?default(-1)==2>class="curr"</#if> ><span>待处理</span></li>
					<li onclick="location.href='${BasePath}/invoice/query.sc?invoiceStatus=-1'" <#if vo.invoiceStatus?default(-1)!=2>class="curr"</#if> ><span>全部</span></li>
				</ul>
				<div class="tab_content">
					<!--列表start-->
					<style>
						.common_proitm td{border:1px solid #f2f2f2;}
						.common_proitm .line_gap td{border:none;}
					</style>
					<table class="common_lsttbl mt10" id="table" >
						<thead>
							<tr>
							    <#if vo.invoiceStatus?default(-1)!=2><th>发票号</th></#if>
								<th>发票类型</th>
								<th>发票抬头</th>
								<th>开票金额</th>
								<#if vo.invoiceStatus?default(-1)!=2><th>配送方式（单号）</th></#if>
								<th>创建时间</th>
								<th>发票状态</th>
								<th>未处理时长</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody class="common_proitm">
						 <#if pageFinder?? && (pageFinder.data)?? && pageFinder.data?size gt 0> 
							<#list pageFinder.data as item>
							<tr class="line_gap">
                                	<td colspan="7"></td>
                            </tr>
                            <#if item.orderMainNo!=''>
                            <tr class="proitm_hd">
                              <td colspan="9">
                                  <span class="ml5 fl" style="width: 200px;">优购订单号：${item.orderMainNo?default('')}</span>
                                  <span class="ml20 fl" style="width: 180px;">下单时间：${item.orderCreateTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                             </td>
                            </tr>
                            </#if>
							<tr>
							    <#if vo.invoiceStatus?default(-1)!=2><td>${item.invoiceNo?default('')}</td></#if>
			                    <td>${item.invoiceTypeName?default('')}</td>
			                    <td>${item.invoiceTitle?default('')}</td>
			                    <td>${item.invoiceAmount?default('')}</td>
			                    <#if vo.invoiceStatus?default(-1)!=2><td><#if item.invoiceStatus==7><#if item.shipMethod??&&item.shipMethod==1>挂号信<#elseif item.shipMethod??&&item.shipMethod==2>${item.logisticsName?default('')}</#if>&nbsp;${item.expressCode?default('')}<#else>-</#if></td></#if>
			                    <td>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
			                    <td>${item.invoiceStatusName?default('')}</td>
                                <td <#if (item.hourNumRemain?length gt 2)&&item.hourNumRemain?substring(0,2)=='超时'>style="color:red;"</#if>>${item.hourNumRemain?default('')}</td>
                                <td><#if item.invoiceStatus?default(-1)==2><a onclick="inputExpressInfo('${item.id?default('')}')" href="javascript:void(0);">录入</a>&nbsp;&nbsp;</#if><a href="${BasePath}/invoice/detail.sc?id=${item.id?default('')}" target="_blank">查看</a></td>
                            </tr>
						  </#list>
						<#else>
							<tr>
								<td colspan="9" class="td-no">没有相关记录！</td>
							</tr>
						</#if>
						</tbody>
					</table>
					<!--列表end-->
				<!--分页start-->
				<#if pageFinder??&&pageFinder.data??>
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
$("#invoiceCreateTimeStart").calendar({maxDate:'#invoiceCreateTimeEnd',format:'yyyy-MM-dd'});
$("#invoiceCreateTimeEnd").calendar({minDate:'#invoiceCreateTimeStart',format:'yyyy-MM-dd'});
//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/invoice/query.sc";
	queryForm.submit();
}
function inputExpressInfo(id){
var content='<span><label style="color:red;">*</label><label>发&nbsp;票&nbsp;号：</label>&nbsp;&nbsp;<input type="text" style="width:120px;height:14px;"  id="invoice_no" name="invoice_no" value="" />&nbsp;<input type="checkbox" id="withBagPost" onchange="usePostWithBag();" name="withBagPost" value="">&nbsp;随货品寄出</span><br/><br/>'
           +'<span id="postStyle"><label style="color:red;">*</label><label>配送方式：</label>&nbsp;&nbsp;<input type="radio" id="post_style1" name="post_style" onchange="usePost();" value="1">&nbsp;挂号信&nbsp;&nbsp;<input type="radio" id="post_style2" name="post_style" onchange="usePost();" value="2">&nbsp;快递<br/><br/></span>'
           +'<span id="expressEnt"><label style="color:red;">*</label><label>物流公司：</label>&nbsp;&nbsp;<select name="express_ent" id="express_ent" style="width:120px;"><option value="">请选择</option><#if expressInfos??><#list expressInfos as item><option value="${item['id']!''}">${item['express_name']!""}</option></#list></#if></select><br/><br/></span>'
           +'<span id="expressCode"><label style="color:red;">*</label><label>配送单号：</label>&nbsp;&nbsp;<input type="text" style="width:120px;height:14px;" id="express_code" name="express_code" value="" /><br/><br/></span>'
           +'<span><label id="ygdg_warn" style="display:inline-block;color:#ff0000;"></label></span>';
    var dialog=ygdg.dialog({
	    title: '配送信息录入',
	    content: content,
	    width: 440,
        height: 200,
	    button: [
	    {
            name: '保存',
            callback: function () {
              if($("#invoice_no").val()==""){
                  this.shake && this.shake();
                  $("#ygdg_warn").show();
                  $("#ygdg_warn").html("发票号不能为空！");
                  $("#invoice_no").select();
                  $("#invoice_no").focus();
                  return false;
              }
              if($("#withBagPost").attr("checked")=="checked"||$("input[name='post_style']:checked").val()=='2'){
                  if($('select#express_ent option:selected').val()==""){
                     this.shake && this.shake();
                     $("#ygdg_warn").show();
                     $("#ygdg_warn").html("请选择物流公司！");
                     $("#express_ent").select();
                     $("#express_ent").focus();
                     return false;
                  }
                  if($("#express_code").val()==""){
                     this.shake && this.shake();
                     $("#ygdg_warn").show();
                     $("#ygdg_warn").html("快递单号不能为空！");
                     $("#express_code").select();
                     $("#express_code").focus();
                     return false;
                  }
              }else{
                  if($("input[name='post_style']:checked").val()==null){
                     this.shake && this.shake();
                     $("#ygdg_warn").show();
                     $("#ygdg_warn").html("请选择配送方式！");
                     $("#postStyle").select();
                     $("#postStyle").focus();
                     return false;
                  }else if($("input[name='post_style']:checked").val()=='1'){
                     if($("#express_code").val()==""){
                       this.shake && this.shake();
                       $("#ygdg_warn").show();
                       $("#ygdg_warn").html("配送单号不能为空！");
                       $("#express_code").select();
                       $("#express_code").focus();
                       return false;
                     }
                  }
              }
                save(id);
           },
              focus: true
        	},
        	{
            name: '关闭'
        }
    	]
		});
}

function save(id){
  var data='';
  var invoiceNo=$("#invoice_no").val();
  var expressCode=$("#express_code").val();
  if($("#withBagPost").attr("checked")=="checked"||$("input[name='post_style']:checked").val()=='2'){
     var logisticsCode=$('select#express_ent option:selected').val();
     var logisticsName=$('select#express_ent option:selected').text();
     data='invoiceId='+id+'&invoiceNo='+invoiceNo+'&shipMethod=2&logisticsCode='+logisticsCode+'&logisticsName='+logisticsName+'&expressCode='+expressCode;
   }else{
     data='invoiceId='+id+'&invoiceNo='+invoiceNo+'&shipMethod=1&expressCode='+expressCode;
   }
	      $.ajax({
			  type: 'post',
			  url: '${BasePath}/invoice/save.sc?param=' + new Date(),
			  dataType: 'json',
			  data: data,
			  beforeSend: function(XMLHttpRequest) {
		        dialog_submit=ygdg.dialog({
		          content: '保存中...'
			    }).show();
			  },
			  success: function(data, textStatus) {
			     if("true"==$.trim(data)){
			       ygdg.dialog.alert('保存成功!');
			       }
			  },
			  complete: function(XMLHttpRequest, textStatus) {
			       dialog_submit.close();
			       refreshpage();
		      },
			  error: function(XMLHttpRequest, textStatus, errorThrown) {
			    ygdg.dialog.alert(XMLHttpRequest.responseText);
			    
			  }
		});
	  } 
function usePostWithBag(){
  if($("#withBagPost").attr("checked")=="checked"){
     $("#expressEnt").show();
     $("#postStyle").hide();
  }else{
     usePost();
     $("#postStyle").show();
  }
}

function usePost(){
  if($("input[name='post_style']:checked").val()=='1'){
     $("#expressEnt").hide();
  }else{
     $("#expressEnt").show();
  }
}

//导出发票明细
function doExportInvoice() {
	$("#queryForm").attr("action","${BasePath}/invoice/doExportInvoice.sc");
	$("#queryForm").submit();
}
</script>
</html>
