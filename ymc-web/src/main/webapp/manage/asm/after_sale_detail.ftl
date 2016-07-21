<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-售后单详情</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<style>
.search_box label {
    width: 90px;
}
</style>
</head>
 
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 售后单详情 </p>
			<div class="tab_panel">
				<ul class="tab">
				<li class="curr"><span>售后单详情</span></li>
				<div class="tab_fr">
				  <div class="fl afterSale_search_div">
					售后申请单号：
						<input type="text" class="inputtxt" style="height:20px;" id="applyNo" name="applyNo" value="输入售后申请单号..." onblur="loseValue(this.value);" onfocus="getValue();"/>
						</div>
						<a class="button fl" onclick="onSearch();"><span>查询</span></a>
					
				</div>
				</ul>
				<div class="tab_content">
					<div class="afterSale_detail clearfix">
        				<div class="afterSale_apply2">
        	   				<#if saleApplyMap??>
        	   					<input type="hidden" name="applyId" id="applyId" value="${saleApplyMap['id']!''}">
            					<div class="afterSale_dt">
                	              <h4 class="fl">售后申请单</h4>
                                 <span>申请单号：<em>${saleApplyMap['apply_no']!''}</em></span>
                                 <span>售后申请单状态：<em>${saleApplyMap['status_name']!''}</em></span>
                                </div>
                         <div class="afterSale_dc afterSale_dc1">
	                	     <div class="dc1_box fl">
		                    	 <h5>售后申请单基本信息</h5>
		                         <table class="afterSale_table afterSale_table1">
		                            <tbody>
		                                <tr>
		                                    <th width="100">优购订单号：</th>
		                                    <td>${saleApplyMap['order_sub_no']!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>外部订单号：</th>
		                                    <td>${saleApplyMap['out_order_id']!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>售后类型：</th>
		                                    <td style="color:red;">${saleApplyMap['sale_type']!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>售后原因：</th>
		                                    <td>${saleApplyMap['sale_reason']!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>售后说明：</th>
		                                    <td>${saleApplyMap['remark']!''}</td>
		                                </tr>
		                                <#--tr>
		                                    <th>附件：</th>
		                                    <td></td>
		                                </tr-->
		                                <tr>
		                                    <th>申请人：</th>
		                                     <td>${saleApplyMap['createor']!''}</td>
		                                </tr>
		                                <tr>
		                                    <th>申请时间：</th>
		                                   <td>${saleApplyMap['create_time']!''}</td>
		                                </tr>
		                            </tbody>
		                             </#if>
		                        </table>
                    		</div>
		                    <div class="dc1_box fl" style="border:none;">
		                    	<h5>顾客寄回物流信息</h5>
		                        <table class="afterSale_table afterSale_table1">
		                            <#if logistics??>
			                            <tbody>
			                                <tr>
			                                    <th width="100">物流公司：</th>
			                                    <td>${logistics['expressName']!''}</td>
			                                </tr>
			                                <tr>
			                                    <th>快递单号：</th>
			                                    <td>${logistics['expressCode']!''}</td>
			                                </tr>
		                            </tbody>
		                             </#if>
		                        </table>
		                    </div>
                    	<div class="clear"></div>
                	</div>
				<div class="afterSale_dc" style="padding-top:0;">
                	<div>
                    	<h5>退回商品信息</h5>
                        <table class="afterSale_table afterSale_table2">
                        	<thead>
                            	<tr>
                            		<th width="5%"></th>
                                	<th width="25%">商品名称</th>
                                    <th>商品规格</th>
                                    <th>货品编码</th>
                                    <th>供应商款色编码</th>
                                    <th>优购价</th>
                                    <th>退回数量</th>
                                    <th>商品类型</th>
                                    <th>发货仓库</th>
                                </tr>
                            </thead>
                             <#if returnProductList??>
	                            <tbody>
	                            	<#list returnProductList as item>
	                                <tr>
	                                	<td><img src="${item['picSmall']!''}" alt="${item['commodity_name']!''}" width="40px;" height="40px;" /></td>
	                                	<td style="text-align:left;">
	                                		<a class="blue" href="${item['url']!''}" target="_blank" >${item['commodity_name']!''}</a>
	                                	</td>
	                                    <td>${item['specification']!''}</td>
	                                    <td>${item['prod_code']!''}</td>
	                                    <td>${item['supplier_code']!''}</td>
	                                    <td>${item['sale_price']!''}</td>
	                                    <td>${item['commodity_num']!''}</td>
	                                    <td>${item['prod_type']!''}</td>
	                                    <td>${item['warehouse_name']!''}</td>
	                                </tr>
	                                </#list>
                          	  </tbody>
                          	</#if>
                        </table>
                    </div>
                    </div>
                    <div class="afterSale_dc" style="padding-top:0;">
                	<div>
                    	<h5>质检信息</h5>
                        <table class="afterSale_table afterSale_table2">
                        	<thead>
                            	<tr>
                            		<th width="5%"></th>
                            	    <th width="25%">商品名称</th>
                                    <th>商品规格</th>
                                	<th>问题类型</th>
                                    <th>问题描述</th>
                                    <th>快递单号</th>
                                    <th>配送费用</th>
                                    <!--th>入库类型</th-->
                                    <th>质检描述</th>
                                    <th width="14%">质检时间</th>
                                    <th>质检人</th>
                                    <th>备注</th>
                                </tr>
                            </thead>
                             <#if returnQaDetailList??>
                                <#list returnQaDetailList as item>
	                               <tbody>
	                                <tr>
	                                	<td><img src="${item['picSmall']!''}" alt="${item['qa_goods_name']!''}" width="40px;" height="40px;" /></td>
	                                    <td style="text-align:left;"><a class="blue" href="${item['url']!''}" target="_blank">${item['qa_goods_name']!''}</a></td>
	                                    <td>${item['specification']!''}</td>
	                                    <td>${item['question_type']!''}</td>
	                                    <td>${item['question_description']!''}</td>
	                                    <td>${item['express_code']!''}</td>
	                                    <td>${item['express_charges']!''}</td>
	                                    <#--td>${item['storage_type']!''}</td-->
	                                    <td>${item['qa_description']!''}</td>
	                                    <td>${item['qa_date']!''}</td>
	                                    <td>${item['qa_person']!''}</td>
	                                    <td>${item['remark']!''}</td>
	                                </tr>
                          	     </tbody>
                          	  </#list>
                          	</#if>
                        </table>
                    </div>
                </div>
            </div>	
						
				
				</div>
			</div>
		</div>
		</div>
	</div>
</body>
<script>
 //如果为空，默认值
function loseValue(str){
  if(str==""){
    $("#applyNo").val("输入售后申请单号...");
  }
}
//得到焦点
function getValue(){
   $("#applyNo").val("");
}


//根据申请单号重新刷新改页面数据
function onSearch() {
   var applyNo = $("#applyNo").val();
   if(applyNo=='' || applyNo=='输入售后申请单号...'){
      ygdg.dialog.alert("请输入售后申请单号再查询!");
   }else{
   	  $.ajax({
		url: '${BasePath}/afterSale/checkApplyNoIsExist.sc?rnd=' + Math.random(),
		data: {'applyNo':  applyNo},
		dataType: "html",
		success: function(data, textStatus) {
			if($.trim(data) == 'true'){
				window.location.href = "${BasePath}/afterSale/afterSaleDetail.sc?applyNo="+applyNo;
			} else{
				ygdg.dialog.alert("输入的售后单号不存在，请重新输入");
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert('服务器异常:' + XMLHttpRequest.responseText);
		}
	  });  	  
   }
}
</script>
</html>
