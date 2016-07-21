<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="">
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">修改采购单数据</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/supply/vindicate/extend/to_updatePurchaseVindicate.sc" name="queryForm" id="queryForm" method="post"> 
  			  	 <input type="hidden" name="id" id="id" value="<#if id??>${id!""}</#if>"/>
  			  	<div class="wms-top">
                     <label>商品款号：</label>
                     <input type="text" name="styleNo" id="styleNo" value="<#if purchaseVo??&&purchaseVo.styleNo??>${purchaseVo.styleNo!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>商品编号：</label>
                     <input type="text" name="commodityNo" id="commodityNo" value="<#if purchaseVo??&&purchaseVo.commodityNo??>${purchaseVo.commodityNo!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryUpdatePurchaseVindicate();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                    <br/> <br/>
                    <input type="button" value="批量修改" onclick="batchPruchasePrice();" class="btn-add-normal-4ft" />
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th><input type="checkbox" name="allselect" id="allselect" onclick="checkAll();"></th>
                        <th>货品条码</th>
                        <th>款色编码</th>
                        <th>商品名称</th>
                        <th>商品款号</th>
                        <th>规格</th>
                        <th>单位</th>
                        <th>采购数量</th>
                        <th>库存数量</th>
                        <th>总价</th>
                        <th>采购单价</th>
                        <th>比例</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                            <td><input type="checkbox" name="boxs" value="${item['id']!''}"></td>
		                        <td>${item['product_no']!""}</td>
		                        <td>${item['supplier_code']!""}</td>
		                        <td>${item['commodity_name']!""}</td>
		                        <td>${item['style_no']!""}</td>
		                        <td>${item['specification']!""}</td>
		                        <td>${item['unit']!""}</td>
		                        <td>${item['purchase_quantity']!""}</td>
		                        <td>${item['intostore_quantity']!""}</td>
		                        <td>${item['purchase_total_price']!""}</td>
		                        <input type="hidden"  id="${item['id']!''}_purchaseType" value="<#if item['purchase_type']??>${item['purchase_type']!''}</#if>"/>
		                        <input type="hidden"  id="${item['id']!''}_purchaseQuantity" value="<#if item['purchase_quantity']??>${item['purchase_quantity']!''}</#if>"/>
		                         <input type="hidden"  id="${item['id']!''}_purchaseTotalPrice" value="<#if item['purchase_total_price']??>${item['purchase_total_price']?string("########.00")}</#if>"/>
		                        <td><input type="text" style="width:80px;" id="${item['id']!''}_purchasePrice" value="${item['purchase_price']?string("########.00")}"></td>
		                        <td><input type="text" style="width:50px;" id="${item['id']!''}_deductionRate" value="${item['deduction_rate']!""}"></td>
		                        <td>
		                            <a href="#" onclick="updatePurchaseVindicate('${item['id']!''}')">修改</a>
		                       </td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="13">
                        	抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//全选
function checkAll(th){
	var checked = $(":checkbox[name=allselect]").attr("checked");
	$(":checkbox[name=boxs]").attr("checked",checked);
}
//查询
function queryUpdatePurchaseVindicate(){
 document.queryForm.submit();
}
//单个修改采购单价和比例
function updatePurchaseVindicate(id){
var purchaseId=$("#id").val();
var purchaseType=$("#"+id+"_purchaseType").val();//类型
var purchaseQuantity=$("#"+id+"_purchaseQuantity").val();//数量
var purchasePrice=$("#"+id+"_purchasePrice").val();//单价价格
var purchaseTotalPrice=$("#"+id+"_purchaseTotalPrice").val();//总价
var deductionRate=$("#"+id+"_deductionRate").val();//比例
 if(purchasePrice!=""&&deductionRate!=""){
	 var priceStr=id+"-"+purchaseType+"-"+purchaseQuantity+"-"+purchasePrice+"-"+purchaseTotalPrice+"-"+deductionRate;
	  $.ajax({ 
		type: "post", 
		url: "${BasePath}/supply/vindicate/extend/update_purchaseVindicateSingle.sc?id=" + purchaseId+"&priceStr="+priceStr, 
		success: function(dt){
			if("success"==dt){
			   alert("修改成功！");
			    closewindow();
			   refreshpage('${BasePath}/supply/vindicate/extend/to_queryPurchaseVindicateList.sc');
			}else{
			   alert("修改失败！");
			}
		} 
	  });
  }else{
     alert("价格或比例不能为空,请填写完整!");
  }
}
//批量修改
function batchPruchasePrice(){
var purchaseId=$("#id").val();
//获取多个选择的checkbox值
var str="";
    var ids = document.getElementsByName("boxs");      
    for (var i = 0; i < ids.length; i++)       {             
	    if(ids[i].checked == true)       {  
	        var purchaseType=$("#"+ids[i].value+"_purchaseType").val();//类型
			var purchaseQuantity=$("#"+ids[i].value+"_purchaseQuantity").val();//数量
			var purchasePrice=$("#"+ids[i].value+"_purchasePrice").val();//价格
			var purchaseTotalPrice=$("#"+ids[i].value+"_purchaseTotalPrice").val();//总价
			var deductionRate=$("#"+ids[i].value+"_deductionRate").val();//比例    
	         str+=ids[i].value+"-"+purchaseType+"-"+purchaseQuantity+"-"+purchasePrice+"-"+purchaseTotalPrice+"-"+deductionRate+"_";
	    }                   
    } 
    //判断是否选择了数据
    if(str==""){
       alert("请选择数据再修改!");
       return;
    }else{ 
       //判断是否有空值
       str=str.substring(0,str.length-1);
       var buffer=str.split("_");
        for(var i=0;i<buffer.length;i++){
           var buffers=buffer[i];
           var bufferStr=buffers.split("-");
           var price=bufferStr[3];
           var rate=bufferStr[4];
           if(price==""||rate==""){
              alert("数据不完整,请重新输入!");	  
              return;     
           }
        }
    }
    //提交数据保存
    $.ajax({ 
		type: "post", 
		url: "${BasePath}/supply/vindicate/extend/update_purchaseVindicateMultiple.sc?id=" + purchaseId+"&priceStr="+str, 
		success: function(dt){
			if("success"==dt){
			   alert("修改成功！");
			   closewindow();
			   refreshpage('${BasePath}/supply/vindicate/extend/to_queryPurchaseVindicateList.sc');
			}else{
			   alert("修改失败！");
			}
		} 
	  });
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
