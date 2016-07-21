<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />

<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
//查询
function queryAddshipments(){
  document.queryForm.action='${BasePath}/supply/purchase/addshipments/to_addshipmentsDetail.sc';
  document.queryForm.method="post";
  document.queryForm.submit();
}
//判断是否存在相同的数据
function existInsideCode(shipMentsId,str){
   var flag=false;
	$.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/supply/purchase/addshipments/existInsideCode.sc?id=" + shipMentsId+"&insideCodeStr="+str, 
		success: function(dt){
			if("success"==dt){
			   flag=true;
			}else{
			   var errorStr="";
			   if(dt!=""){
			     var buffer=dt.split("^");
			    for (var i = 0; i < buffer.length; i++)  {
			        errorStr+="["+buffer[i]+"],";
			     }
			   }
			   if(errorStr!=""){
			     errorStr=errorStr.substring(0,errorStr.length-1);
			   	 $("#error").html("货品编码"+errorStr+"已存在发货明细，不能再次添加");
			   }
			}
	  }
    });
    return flag;
}
//全选
function checkAll(){
	var checked = $(":checkbox[name=allselect]").attr("checked");
	$(":checkbox[name=boxs]").attr("checked",checked);
}
//全部确认
function sureAll(){
 //根据采购单主表Id查询所有的发货单信息
 var purchaseCode=$("#purchaseCode").val();
 if(purchaseCode!=""){
	 $.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/supply/purchase/addshipments/queryPurchaseDetailList.sc?purchaseCode="+purchaseCode, 
		success: function(dt){
			if(dt!=""){
			    var purchaseId="";   
	    		var insideCode="";
	    		var splitStr=dt.split("#");
	    		if(splitStr!=""){
	    		    var purchaseId=splitStr[1];
	    		    var insideCode=splitStr[0];
			        purchaseId=purchaseId.substring(0,purchaseId.length-1);
			        insideCode=insideCode.substring(0,insideCode.length-1);
				    var shipMentsId=$("#shipMentsId").val();
				    if(shipMentsId!=""){
					  var flag=existInsideCode(shipMentsId,insideCode);
					  if(flag){
					    saveShipments(purchaseId,shipMentsId);
					  }
				    }else{//之前没有选择数据
				        saveShipments(purchaseId,shipMentsId);
				    }
				 }
			}else{
			  alert("没有数据!");
			}
		} 
	  });      
 }else{
	   alert("采购单编号为空!");
 }
}
//勾选确认
function checkChoice(){
  var str="";   
  var insideCodeStr="";
  var ids = document.getElementsByName("boxs");      
    for (var i = 0; i < ids.length; i++)       {     
	    if(ids[i].checked == true && ids[i].disabled==false)       {  
	       var insideCode=$("#"+ids[i].value+"_insideCode").val();//货品编码
	        insideCodeStr+=insideCode+"^";
	       str+=ids[i].value+"-";
	    }                   
    } 
    if(str==""){
       alert("请选择数据！");
    }else{
        str=str.substring(0,str.length-1);
        insideCodeStr=insideCodeStr.substring(0,insideCodeStr.length-1);
	    //获取为发货采购单主表Id 判断是否保存过数据
	    var shipMentsId=$("#shipMentsId").val();
	    if(shipMentsId!=""){
	       var flag=existInsideCode(shipMentsId,insideCodeStr);
		   if(flag){
		    saveShipments(str,shipMentsId);
		   };
	    }else{
	      saveShipments(str,shipMentsId);
	    }
   }
}
//非勾选确认
function checkNoChoice(){
 	var ids = document.getElementsByName("boxs");   
 	var str="";   
	for (var i = 0; i < ids.length; i++)       {     
	    if(ids[i].checked == true)       {  
	       str+=ids[i].value+"-";
	    }                   
	} 
 //根据采购单主表Id查询所有的发货单信息
 var purchaseCode=$("#purchaseCode").val();
 if(purchaseCode!=""){
	 $.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/supply/purchase/addshipments/queryPurchaseDetailList.sc?flag=1&purchaseCode="+purchaseCode+"&purchaseId="+str, 
		success: function(dt){
			if(dt!=""){
			    var purchaseId="";   
	    		var insideCode="";
	    		var splitStr=dt.split("#");
	    		if(splitStr!=""){
	    		    var purchaseId=splitStr[1];
	    		    var insideCode=splitStr[0];
			        purchaseId=purchaseId.substring(0,purchaseId.length-1);
			        insideCode=insideCode.substring(0,insideCode.length-1);
				    var shipMentsId=$("#shipMentsId").val();
				    if(shipMentsId!=""){
					  var flag=existInsideCode(shipMentsId,insideCode);
					  if(flag){
					    saveShipments(purchaseId,shipMentsId);
					  }
				    }else{//之前没有选择数据
				        saveShipments(purchaseId,shipMentsId);
				    }
				 }
			}else{
			  alert("没有数据!");
			}
		} 
	  });      
 }else{
	   alert("采购单编号为空!");
 }
}

//封装保存数据的方法
function saveShipments(str,shipmentId){
  //获取采购单主表Id
  var purchaseId=$("#purchaseId").val();
  if(str!="" && purchaseId!=""){
	 //保存数据
	 $.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/supply/purchase/addshipments/saveShipments.sc?purchaseDetailIdBuffer="+str+"&purchaseId="+purchaseId+"&shipmentId="+shipmentId, 
		success: function(dt){
			if("fail"!=dt){
			    var id=dt;
			 	$("#shipMentsId").val(id);
			 	var purchaseCode=$("#purchaseCode").val();
			 	alert("添加成功！");
			    refreshpage('${BasePath}/supply/purchase/addshipments/to_addshipments.sc?shipmentsId='+id+'&purchaseCode='+purchaseCode);
			}else{
			   alert("添加失败！");
			}
		} 
	  });
  }else{
    alert("未选中数据或者采购单主表Id为空!");
  }
}

</script>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
			<div class="btn" id="toAddDetailBtn" onclick="sureAll();"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 全部确认</span> <span class="btn_r"> </span> </div>
			<div class="line"> </div>
		   	<div class="btn" id="doSaveBtn" onclick="checkChoice();"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 勾选确认</span> <span class="btn_r"> </span> </div>
	   		<div class="line"> </div>
		    <div class="btn" id="importBtn" onclick="checkNoChoice();"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 非勾选确认 </span> <span class="btn_r"> </span> </div>
	    	<div class="line"> </div>
		    <div class="btn" onclick="closewindow();"> <span class="btn_l"> </span> <b class="ico_btn back"> </b> <span class="btn_txt"> 关闭 </span> <span class="btn_r"> </span> </div>
		</div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span><a href="">新增明细</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		<!--主体start-->
		<div id="modify" class="modify">
			<form method="post" id="queryForm" name="queryForm"  action ="to_addshipmentsDetail.sc">
			<input type="hidden" name="shipmentId" id="shipmentId" value="<#if shipmentId??>${shipmentId!''}</#if>">
			 <input type="hidden" name="purchaseId" id="purchaseId" value="<#if purchaseSp??&&purchaseSp.id??>${purchaseSp.id!''}</#if>">
			 <input type="hidden" name="purchaseCode" id="purchaseCode" value="<#if purchaseSp??&&purchaseSp.purchaseCode??>${purchaseSp.purchaseCode!''}</#if>">
			 <input type="hidden" name="shipMentsId" id="shipMentsId" value="<#if shipmentId??>${shipmentId!''}</#if>">
			 <div style="clear:both;">
				 <p>
					<span>
						<label>采购单号：</label>
						<#if purchaseSp??&&purchaseSp.purchaseCode??>
					    	${purchaseSp.purchaseCode!''}
					    </#if>
					</span>
				</p>
				 <p>
					<span>
					<label>商品名称：</label>
					<input type="text" style="width:100px;" name="commdityName" id="commdityName" value="<#if vo??&&vo.commdityName??>${vo.commdityName!''}</#if>">
					</span>
					<span>
					<label style="margin-left:20px;">商品款号：</label>
					<input type="text" style="width:100px;" name="styleNo" id="styleNo" value="<#if vo??&&vo.styleNo??>${vo.styleNo!''}</#if>">
					</span>
					<span>
					<label style="margin-left:20px;">款色编码：</label>
					<input type="text" style="width:100px;" name="supplierCode" id="supplierCode" value="<#if vo??&&vo.supplierCode??>${vo.supplierCode!''}</#if>">
					</span>
					</p>
					 <p>
						<span>
							<label>货品条码：</label>
							<input type="text" style="width:200px;"  name="insideCode" id="insideCode" value="<#if vo??&&vo.insideCode??>${vo.insideCode!''}</#if>">多个货品条码","隔开
						</span>
					</p>
					<p>
						<span>
							<label>货品编码：</label>
							<input type="text" style="width:200px;"  name="productNo" id="productNo" value="<#if vo??&&vo.productNo??>${vo.productNo!''}</#if>">多个货品编码","隔开
						</span>
					</p>
			<input type="button" class="btn-add-normal" value="查询" onclick="queryAddshipments()">
			</div>
			</form>
			 <br/>
				 <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
					    <th><input type="checkbox" name="allselect" id="allselect" onclick="checkAll();"></th>
						<th>货品编码</th>
						<th>货品条码</th>
						<th>商品款号</th>
						<th>款色编码</th>
						<th>商品名称</th>
						<th>规格</th>
					</tr>
					<#if pageFinder??&&pageFinder.data??>
					<#list pageFinder.data as map>
						<tr class="div-pl-list">
						    <td>
						    <input type="checkbox" name="boxs" id="boxs" value="${map['id']!''}" <#if map['count']??&&map['count']!=0>disabled</#if>></td>
							<td>${map['product_no']!''}</td>
							<td>${map['inside_code']!''}</td>
							<td>${map['style_no']!''}</td>
							<td>${map['supplier_code']!''}</td>
							<td>${map['commodity_name']!''}</td>
							<td>${map['specification']!''}</td>
							<input type="hidden"  id="${map['id']!''}_insideCode" value="<#if map['inside_code']??>${map['inside_code']!''}</#if>"/>
						</tr>
					</#list>	
					<#else>
						<tr>
                        	<td colSpan="7">抱歉，没有您要找的数据 </td>
	                    </tr>
					</#if>
				</table>
			   </div>
                <div class="bottom clearfix">
			  	<#if pageFinder??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			   </div>
              <div class="blank20"></div>
				<div id="error" style="margin-top:20px;color:red;">
				</div>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
