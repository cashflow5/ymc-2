<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-实际安全库存查询</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
<script type="text/javascript">
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/wms/supplyStockInput/getSafeStock.sc";
	queryForm.submit();
}
</script>
<style>
a.icon_cancel{margin-left:5px;}
.icon_cancel{width:9px;height:9px;display:inline-block;background:url(${BasePath}/yougou/images/del-class.gif) no-repeat;margin-top:4px;}
</style>
</head>
<body>
	<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 库存 &gt; 库存查询</p>
				<div class="tab_content">
				<!--搜索start-->
				<div class="search_box">
				    <form name="queryForm" id="queryForm" method="post">
					<p>
					<span><label>货品编码：</label><input type="text"  class="inputtxt"  name="productNo" id="productNo" value="<#if productNo??>${productNo}</#if>"/></span>
					<span style="margin-left: 100px;"><input type="text" style="width:50px;" class="inputtxt" name="safeStockQuantityGe" value="${safeStockQuantityGe!'' }"/>&nbsp;&le;&nbsp;实际安全库存&nbsp;&le;&nbsp;
					<input type="text" style="width:50px;" class="inputtxt" name="safeStockQuantityLe" value="${safeStockQuantityLe!'' }"/></span>
					<span style="margin-left: 20px;"><a class="button" id="mySubmit" onclick="doQuery()" ><span>搜索</span></a></span> 
					</p>
				</form>
				</div>
				<!--搜索end-->
				<!--列表start-->
				<table cellpadding="0" cellspacing="0" class="list_table">
					<thead>
						<tr>
						<th></th>
						<th style="width:300px;">货品编码</th>
						<th >实际安全库存</th>
						<th>创建时间</th>
						<th>修改日期</th>
						<th>操作</th>
					    </tr>
				    </thead>
				    <tbody class="common_lsttbl_cz">
                            	<tr class="cz_box">
                                	<td colspan="6">
                                    	<div class="cz_bd cz_bd_1 fl">
                                    	     &nbsp;全选<input type="checkbox" class="checkall ml8" id="checkAll_delete">
                                            <a href="javascript:;" onclick="javascript:batchDel();">批量删除</a>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
				    <tbody id="common_proitm">
				       <#if pageFinder?? && (pageFinder.data)?? && (((pageFinder.data)?size)>0) > 
					       <#list pageFinder.data as item >
					      <tr id="safeStockTr_${item.id }">
					      <td><input type="checkbox" name="safeStockCheck" value="${item.id}"/></td>
					      <td style="padding:2px">
	      		              ${item.product_no?default("")}
					      </td>
						  <td id="safeStockTd_${item.id }">${item.safe_stock_quantity?default("0")}</td>
						  <td >${item.create_date}</td>
						  <td>${item.modity_date?default("")}</td>
						  <td><a href="javascript:modifySafeStock('${item.id }');">修改</a>&nbsp;&nbsp;&nbsp;
						  <a href="javascript:delSafeStock('${item.id }');">删除</a></td>
					      </tr>
							</#list> 
						<#else>
						<tr>
							<td colspan="5">没有相关记录！</td>
						</tr>
						</#if>
					</tbody>
				</table>
				<input type="hidden" id="setSafeStockQuantity" value="${setSafeStockQuantity }"/>
			<!--列表end-->
			<!--分页start-->
			<#if pageFinder??&&pageFinder.data??>
					<div class="page_box">
						<div class="dobox">
						</div>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryForm"/>
					</div>
			</#if>		
			<!--分页end-->
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$("#checkAll_delete").click(function() {
		if(this.checked){
		    $("#common_proitm").find("input[name='safeStockCheck']").attr("checked", true);
		}else{
			 $("#common_proitm").find("input[name='safeStockCheck']").attr("checked", false);
		}
	});
		function modifySafeStock(id){
			ygdg.dialog.prompt('修改实际安全库存为：',function(data){
				var r = /^-?[1-9]\d*$/;
				if(!(r.test(data))){
					ygdg.dialog.alert("实际安全库存数量应为整数！");
					return false;
				}else if(parseInt(data)>parseInt($("#setSafeStockQuantity").val())){
					ygdg.dialog.alert("实际安全库存数量不应大于商家设置的安全库存"+parseInt($("#setSafeStockQuantity").val())+"！");
					return false;
				}else if(parseInt(data)==parseInt($("#safeStockTd_"+id).text())){
					ygdg.dialog.alert("实际安全库存没有改变！");
				}else{
					$.post("${BasePath}/wms/supplyStockInput/modifySafeStock.sc",{"id":id,"safeStockQuantity":$.trim(data)},function(json){
						if(json!=null&&json.result){
							$("#safeStockTd_"+id).text(parseInt(data));
							ygdg.dialog.alert("修改成功！");
						}else{
							ygdg.dialog.alert("修改失败！");
						}
					},"json");
				}
			},$("#safeStockTd_"+id).text());
		}

		function delSafeStock(ids){
			if($.trim(ids)==""){
				ygdg.dialog.alert("请选择行！");
			}else{
				var idsParam ="";
				if(ids.constructor  === Array ){
					$.each(ids, function(i, field){
						if(i==0){
							idsParam = idsParam + "id="+field.value;
						}else{
							idsParam = idsParam + "&id="+field.value;
						}
					});
				}else{
					idsParam = "id=" + ids;
				}
				ygdg.dialog.confirm('你确认删除操作？', function(){
					$.post("${BasePath}/wms/supplyStockInput/delSafeStock.sc",idsParam,function(json){
						if(json!=null&&json.result){
							if(ids.constructor  === Array ){
								$.each(ids, function(i, field){
									$("#safeStockTr_"+field.value).remove();
								});
							}else{
								$("#safeStockTr_"+ids).remove();
							}
							ygdg.dialog.alert("删除成功！");
						}else{
							ygdg.dialog.alert("删除失败！");
						}
					},"json");
				});
			}
		}
		//批量删除
		function batchDel(){
			var checkedSafe = $("#common_proitm").find("input[name='safeStockCheck']:checked");
			if(checkedSafe.size()==0){
				ygdg.dialog.alert("请选择行！");
			}else{
				var ids = checkedSafe.serializeArray();
				delSafeStock(ids);
			}
		}
	</script>
</body>
</html>

