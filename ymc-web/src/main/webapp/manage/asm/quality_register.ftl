<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-新质检登记</title>
<style>
.detail_box{padding:5px 10px;background:none;}
.detail_box ul li{float:left;width:220px;}
.detail_box:hover{background:none;}
table {
    border-collapse: collapse;
    border-spacing: 0;
}
.goodsDetailTb{width:100%;border:1px solid #ddd;border-right:none;}
.goodsDetailTb td,.goodsDetailTb th{text-align:center;}
.goodsDetailTb td{border-right:1px solid #ddd;padding:10px 0;border-top:1px solid #ddd;}
.goodsDetailTb th{background:#e8f2ff;height:30px; line-height:30px;}
.goodsDetailTb th.bdr{border-right:1px solid #ddd;} 
.submitBtn {background: url("${BasePath}/yougou/images/submitBtn.gif") no-repeat scroll 0 0 transparent;border: medium none;cursor: pointer;display: block;height: 36px;width: 127px;
}
</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 质检登记</p>
		<div class="tab_panel" style="margin-top:15px;">
			<div class="tab_content">
				<form id="returnForm" name="returnForm" method="post" action="${BasePath}/quality/to_queryOrderNoOrExpressNo.sc">
					<p><span class="fl icon_info"></span><span class="fl ml5" style="color:#999;">请输入订单号或发货快递单号或寄回快递单号，如属于退换货建议填写订单号查询，属于拒收可以填写发货快递单号查询，无头包裹可以填写顾客回寄快递单号查询。</span></p>
					<p class="blank20"></p>
					<p>
					   <label style="width:180px; text-align:right; display:inline-block;">扫描订单号\发货\寄回快递单号：</label>
					   <input type="text" style="width:180px;" class="ginput" id="keyword" name="keyword" value="<#if (vo.keyword)??>${vo.keyword}</#if>"/>
					   <input type="hidden" style="width:180px;" class="ginput" id="orderNo" name="orderNo" value="<#if (vo.orderNo)??>${vo.orderNo}</#if>"/>
					   <label id="message" style="width:480px; display:inline-block;color:#ff0000;"></label>
					</p>
					<p style="margin:10px 0 0 175px;"><a id="queryReturn" class="button"><span>查询</span></a></p>

					<p class="blank20"></p>
					<div style="background:#f2f2f2;border:1px solid #ccc;color:	#FFA07A;padding:10px;">
					<p>注意事项：1.如果包裹包含多个订单，则只需要填写其中一个订单号进行录入查询。</p>
					<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2.质检登记后，请检查质检信息是否正确，填写完毕后需保存，以确保质检信息录入到系统中。</p>
					<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.质检登记时，请录入实收货品的货品条码，如果包裹属于拒收，则质检商品数量需要与发货包裹商品数量一致。</p>
					</div>
                </form>
                    <#if orderProductInfo??><p class="blank20"></p></#if>
					<fieldset <#if !orderProductInfo??>style="display:none"</#if> class="x-fieldset x-fieldset-default">
					<legend class="x-fieldset-header">
					<a class="x-tool-toggle"></a>
					<span class="x-fieldset-header-text">顾客寄回货品</span>
					<div class="x-clear"></div>
					</legend>
					<div class="x-fieldset-body">

					<span class="fl">
					<label style="width:120px; text-align:right;display:inline-block;">扫描商家货品条码：</label><input type="text" style="width:180px;" id="insideCode" name="insideCode" class="ginput">
					</span>
					<span class="fl"><a id="inputReturn" class="button"><span>登记</span></a></span>&nbsp&nbsp
					<label id="tips_title" style="width:60px; text-align:right;display:inline-block;<#if saleType??&&saleType.isRejection=='false'&&saleType.isReturn=='true'>display:none;</#if>">小提示：</label>
					<span><label id="tips" style="width:350px;display:inline-block;color:#ff0000;<#if saleType??&&saleType.isRejection=='false'&&saleType.isReturn=='true'>display:none;</#if>">已质检0件，还有${(commodityNum)!""}件未质检！</label></span>

                    <#if orderProductInfo??>
                    <p><input type="text" style="display:none;" class="ginput" id="isReturn" name="isReturn" value="<#if saleType??>${(saleType.isReturn)!''}</#if>"/></p>
					<p><input type="text" style="display:none;" class="ginput" id="isRejection" name="isRejection" value="<#if saleType??>${(saleType.isRejection)!''}</#if>"/></p>
                    <div id="productQaInfos"></div>
					</#if>
					</div>
					</fieldset>

					<#if orderProductInfo??><p class="blank20"></p></#if>
					<fieldset <#if !orderProductInfo??>style="display:none"</#if> class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">订单货品明细</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
							<div class="detail_box normal">
								<ul>
									<li style="width:170px;">收货人姓名：${(orderDeliveryInfo.userName)!""}</li>
									<li style="width:200px;">收货人手机：${(orderDeliveryInfo.mobilePhone)!""}</li>
									<li style="width:250px;">订单发货时间：${(orderDeliveryInfo.shipTime)!""}</li>
									<li style="width:290px;">发货物流信息：${(orderDeliveryInfo.expressOrderId)!""}</li>
									<p><input type="text" style="width:150px;display:none;" class="ginput" id="expressName" name="expressName" value="<#if saleType??>${(orderDeliveryInfo.expressName)!""}</#if>"/></p>
								</ul>
							</div>
							
							<table id="rqDetails1" class="goodsDetailTb">
								<thead>
									<tr><th width="100">订单号</th>
									    <th width="270">商品名称</th>
										<th width="90">商家货品条码</th>
										<th width="80">商家款色编码</th>
										<!--th width="70">货品编号</th-->
										<th width="60">商品数量</th>
										<th width="90">售后申请单号</th>
										<th width="110">售后申请时间</th>
									</tr>
								</thead>
								<tbody>
									<#if orderProductInfo??>
            						<#list orderProductInfo as item >
										<tr id="${(item.levelCode)!""}_r">
											<td><a href="javascript:;" onclick="javascript:toDetail('${item.orderSubNo?default('')}')">${item.orderSubNo?default('')}</a></td>
											<td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><img style="padding-left:8px;" src="${item.picSmall?default('')}" align ="left" width="40" height="40" /><a href="${item.url?default('')}" target="_blank">${item.prodName?default('')}</a></div></td>
											<td>${(item.levelCode)!""}</td>
											<td>${(item.supplierCode)!""}</td>
											<!--td>${(item.prodNo)!""}</td-->
											<td orderSourceNum="${item.orderSubNo?default('')}">${(item.commodityNum)!""}</td>
											<td><#if item.applyNo=="">--<#else>${(item.applyNo)!""}</#if></td>
											<td><#if item.applyTime=="">--<#else>${(item.applyTime)!""}</#if></td>
										</tr>
									</#list>
									</#if>
								</tbody>
							</table>
						</div>
					</fieldset>

					<#if returnInfos??><p class="blank20"></p></#if>
					<fieldset <#if !(returnInfos??&&(returnInfos?size gt 0))>style="display:none"</#if> class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">顾客退换货说明</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
							<table class="goodsDetailTb">
								<thead>
								<tr><th width="100">货品条码</th>
										<th width="60">售后类型</th>
										<th width="560">顾客退换货说明</th>
								</tr>
								</thead>
								<tbody>
							     <#if returnInfos??>
            						<#list returnInfos as item >
									<tr>
										<td>${(item.levelCode)!""}</td>
										<td>${(item.saleType)!""}</td>
										<td style="text-align:left;padding-left:10px;">${(item.remark)!""}</td>
									</tr>
									</#list>
								</#if>
								</tbody>
							</table>
						</div>
					</fieldset>

					<#if orderProductInfo??><p class="blank10"></p></#if>
					<p>
					<input type="button" id="returnConfirm" style="<#if orderProductInfo??>display:block;<#else>display:none;</#if>margin:auto;" class="submitBtn">
					</p>
				
			</div>
		</div>
	</div>
	</div>
	
</body>
<script type="text/javascript">
var len=0;
var commodity_num_sum=0;
var _flag=true;
	$(document).ready(function(){
		focusInput();	
		$("#queryReturn").click(function(){
			var val=$("#keyword").val();   
			if(val==null || val==""){
				showMessage('请输入订单号\\发货\\寄回快递单号！');
				focusInput();
				return;
			} else{
				$("#returnForm").submit();
			}
		}); 
		
		$("#inputReturn").click(function(){
			showInputReturn();
		}); 
		
		$("#returnConfirm").click(function(){
		  if($('#qaDetails tbody').find('tr').length<1){
		    var error = "请登记商家货品条码";
            openDialog(error);
            return;
		  }
		  var _length = $('#qaDetails tbody').find('tr').length;
		  if($("#isRejection").val()=="true"){
		     if(_length != len){
		           ygdg.dialog({
						title:'提示信息',
						content:"您录入的收货快递单号与发货快递单号一致，系统将该质检判定为拒收类型，请质检完订单中剩余的商品。 <br /><br /> <p><span style='color:#FFA07A'>注：如确认，表示该订单为异常收货（如错发、漏发等），系统将整单退款给顾客，请谨慎处理。</span></p>",
						icon: 'warning',
						button: [
						          {
						              name: '返回修改',
						              callback: function () {
						            	  return;
						              },
						              focus: true
//modify by zhuang.rb at 2014-05-30 按照产品的需求去除未质检完成做质检提交						              
/* 						          },
						          {
						              name: '确认录入',
						              callback: function () {
		  		                                if(validateQueryForm()){
		  		                                  if(_flag){
		                                               saveResult();
		                                               _flag=false;
		                                               return;
		                                          }else{
		                                               ygdg.dialog.alert("请勿重复提交!");
		                                          }
		                                        }
						              }
*/						         }
						      ]
					});
		     } else {
			if(validateQueryForm()){
			   if(_flag){
		         saveResult();
		         _flag=false;
		       }else{
		           ygdg.dialog.alert("请勿重复提交!");
		       }
		    }
             }
		  }else{
			if(validateQueryForm()){
			  if(_flag){
			  	 if(_length != commodity_num_sum&&'${expressOrderId!""}'==$.trim($("#expressNo").val())&&'${logisticsName!""}'==$("#expressEnt option:selected").text()){
			  	        var content="";
			  	        if(commodity_num_sum>len){
			  	          content="您录入的收货快递单号与发货快递单号一致，系统将该质检判定为拒收类型，且系统检测到该订单所在包裹还存在其他订单，<br />请用该快递单号："+$.trim($("#expressNo").val())+"来做全包裹拒收质检，否则其他订单无法质检！ <br /><br /> <p><span style='color:#FFA07A'>注：如确认，表示该订单为异常收货（如错发、漏发等），系统将整单退款给顾客，请谨慎处理。</span></p>";
			  	        }else{
			  	          content="您录入的收货快递单号与发货快递单号一致，系统将该质检判定为拒收类型，请质检完订单中剩余的商品。 <br /><br /> <p><span style='color:#FFA07A'>注：如确认，表示该订单为异常收货（如错发、漏发等），系统将整单退款给顾客，请谨慎处理。</span></p>";
			  	        }
		  		        ygdg.dialog({
						title:'提示信息',
						content:content,
						icon: 'warning',
						button: [
						          {
						              name: '返回修改',
						              callback: function () {
						            	  return;
						              },
						              focus: true
//modify by zhuang.rb at 2014-05-30 按照产品的需求去除未质检完成做质检提交
/*						          },
						          {
						              name: '确认录入',
						              callback: function () {
		  		                                saveResult();
		                                        _flag=false;
		                                        return;
						              }
*/						         }
						      ]
					});
		          }else{
		             saveResult();
		             _flag=false;
		          }
		       }else{
		          ygdg.dialog.alert("请勿重复提交!");
		       }
		    }
		
		  }

		}); 
		
		$(document).keydown(function(e){
		 if(e.keyCode==13){ //回车
		  var act = document.activeElement.id;//当前获得焦点控件的ID  
		  switch(act){
		   case 'orderNo':  $("#queryReturn").click();break;
		   case 'insideCode':  $("#inputReturn").click();break;
		  }
		 }        
		});
	}); 
	
	//弹出对话框bug修复
	function openDialog(message) {
		setTimeout(function(){ygdg.dialog.alert(message);},200);
	}
	
	//质检登记订单号/快递单号输入框的错误信息展示
	function showMessage(message) {
		$("#message").html(message);
	}
	
	//质检登记货品条码输入框的错误信息展示
	function showTips(tip) {
	    $("#tips_title").show();
	    $("#tips").show();
		$("#tips").html(tip);
		focusInput();
	}
	
	//定位光标
	function focusInput() {
		setTimeout(function(){
			var input=$("#keyword").val();
			if(input==null || input==""){
				$("#keyword").focus();
			}else{
				input = $("#insideCode").val();
				if(input==null || input==""){
					$("#insideCode").focus();
				}
			}
		},500);
	}
	
	 //售后质检登记
	 var count=0;
	 //记录货品编码和订单号的关系
	 var insideCodeOrderMap = {};
	 function showInputReturn(){
	 	$("#tips_title").hide();
	 	$("#tips").html("");
	 	var orderNo = $("#orderNo").val();
	 	if(orderNo==null || orderNo==""){
	 		showTips("请输入订单号\\发货\\寄回快递单号！");
	 		return;
	 	}

	 	var insideCode = $("#insideCode").val();
	 	if(insideCode==null || insideCode==""){
	 	    showTips("请输入货品条码！");
	 		return;
	 	}	

	 	var url;
	 	var params;
	 	var tbl_expressNo;
	 	var tbl_express_name;
	 	var tbl_expressCharges;
	 	
	 	if($("#isRejection").val()=="true"){
	 	 url='${BasePath}/quality/rejectionCheckProduct.sc';
	 	 params = {'expressOrderId':orderNo,'thirdPartyCode':insideCode};
	 	 tbl_expressNo = '<td rowspan="1" style="text-align:left;padding-left:5px;">' + orderNo + '</td>';
	 	 tbl_express_name = '<td rowspan="1">'+$("#expressName").val()+'</td>';
	 	 tbl_expressCharges = '<td rowspan="1">0</td>';
	 	 
	 	}else{
	 	 url='${BasePath}/quality/returnCheckProduct.sc';
	 	 params = {'orderNo':orderNo,'insideCode':insideCode};
	 	 
	 	 tbl_expressNo = '<td rowspan="1" style="text-align:left;padding-left:5px;"><input type="text" style="width:95px;" id="expressNo" name="expressNo" class="ginput"></td>';
	 	 tbl_express_name = '<td rowspan="1"><select name="expressEnt" id="expressEnt" style="width:110px;"><option value="">请选择</option><#if expressInfos??><#list expressInfos as item><option value="${item['express_name']!""},${item['id']!""}">${item['express_name']!""}</option></#list></#if></select></td>';
	 	 tbl_expressCharges = '<td rowspan="1"><input type="text" style="width:40px;" class="ginput" onkeyup="value=value.replace(/[^0-9|.]/g,\'\')" id="expressCharges" name="expressCharges" value="15">&nbsp;&nbsp;<input type="checkbox" name="paytype" id="paytype"/ style="display:none;"><span style="color:red;" id="contactError"></span></td>';
	 	 
	 	}
	 	
	 	$.ajax({
			url: url+'?time='+(new Date()).valueOf(),
			async: false,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data[0]['status'] == '1'){
				  if($("#isReturn").val()=="true"){
				    commodity_num_sum=data[0]['commodity_num_sum'];
				   }
                    addDetail(data,orderNo,insideCode,tbl_expressNo,tbl_express_name,tbl_expressCharges,false);
				}else if(typeof(data) != "undefined" && data != null && data[0]['status'] == '2'){
					$("#insideCode").val("");
					 openDialog("异常质检，您扫描的商品不属于该包裹或者已另外做过质检！");
					 //暂时保留注释代码	
					//ygdg.dialog.confirm("异常质检，您扫描的商品不属于该包裹或者已另外做过质检，是否继续录入质检？",function(){
                     // addDetail(data,orderNo,insideCode,tbl_expressNo,tbl_express_name,tbl_expressCharges,true);
					//},function(){
					//  return;
					//});
				}else if(typeof(data) != "undefined" && data != null && data[0]['status'] == '3'){
				     openDialog("该货品已申请异常售后，无法录入质检！");
				}else{
					openDialog("您扫描的条码不存在，请确认是否条码已变更，如已变更需将新条码修改为旧条码才可进行质检。");
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				openDialog("服务器错误,请稍后再试!");
				return;
			}
		});
	 }
	 
	 function addDetail(data,orderNo,insideCode,tbl_expressNo,tbl_express_name,tbl_expressCharges,flag){
	                count++;
	                insideCodeOrderMap[insideCode] = qaToOrderList=data[0]['orderList'];
					var productQaInfos='';
					var qaToOrderList;
					var qaToOrder='<td><select name="qaToOrder" style="width:120px;">';
					var isException='NO';
					var tips='';
					if(count==1){
						var diff=data[0]['diff'];
						len=0;
		                for (var key in diff) {
		                  len=len+diff[key];
		                }
					}
					if($("#isRejection").val()=="true"){
					 //展示关联订单信息，如果有且仅有一个关联的，则展示，若多个，则下拉选择
					   qaToOrderList=data[0]['orderList'];
					   if(insideCodeOrderMap[insideCode]==undefined){
					       insideCodeOrderMap[insideCode] = qaToOrderList;
					   }			  
					   if(1==qaToOrderList.length){
					     qaToOrder='<td>'+qaToOrderList[0]['orderSub']+'<input type="text" style="width:120px;display:none;" name="qaToOrder" class="ginput" value="'+qaToOrderList[0]['orderSub']+'"></td>';
					   }else{
					     isException='YES';
					     var valueObj = geOrderSubNum(insideCodeOrderMap[insideCode]);
					    // for(var i=0;i<qaToOrderList.length;i++){
					    //   qaToOrder=qaToOrder+'<option value="'+qaToOrderList[i]['orderSub']+'">'+qaToOrderList[i]['orderSub']+'</option>';
					    // }
					     
					    // qaToOrder=qaToOrder+'</select></td>';
					    qaToOrder='<td>'+valueObj+'<input type="text" style="width:120px;display:none;" name="qaToOrder" class="ginput" value="'+valueObj+'"></td>';
					   }
					}else{
					   qaToOrderList=data[0]['levelCodeList'];
					   if(1==qaToOrderList.length){
					     qaToOrder='<td>'+qaToOrderList[0]['insideCode']+'<input type="text" style="width:120px;display:none;" id="qaToOrder" name="qaToOrder" class="ginput" value="'+qaToOrderList[0]['insideCode']+'"></td>';
					   }else{
					     isException='YES';
	                     for(var i=0;i<qaToOrderList.length;i++){
					      qaToOrder=qaToOrder+'<option value="'+qaToOrderList[i]['insideCode']+'">'+qaToOrderList[i]['insideCode']+'</option>';
					     }
					     qaToOrder=qaToOrder+'</select></td>';
					   }
					}
					if(data[0]['diff'][insideCode]>0||flag){
					if(1==count||$('#qaDetails tbody').find('tr').length==0){
					$("#productQaInfos").html('<p class="blank10"></p>'+
					'<form id="saveResult" name="saveResult" method="post" action="${BasePath}/quality/saveResult.sc">'+
					'<input type="text" style="width:120px;display:none;" id="orderNo" name="orderNo" class="ginput" value="'+orderNo+'">'+
					'<input type="text" style="width:120px;display:none;" id="isException" name="isException" class="ginput" value="'+isException+'">'+
					'<input type="text" style="width:120px;display:none;" id="isReturn" name="isReturn" class="ginput" value="'+$("#isReturn").val()+'">'+
					'<input type="text" style="width:120px;display:none;" id="isRejection" name="isRejection" class="ginput" value="'+$("#isRejection").val()+'">'+
					'<table id="qaDetails" class="goodsDetailTb">'+
								'<thead>'+
									'<tr><th width="120">'+(($("#isRejection").val()=="true")?'':'<span style="color:red;">*</span>')+'收货快递单号</th>'+
										'<th width="120">'+(($("#isRejection").val()=="true")?'':'<span style="color:red;">*</span>')+'物流公司</th>'+
										'<th width="60">'+(($("#isRejection").val()=="true")?'':'<span style="color:red;">*</span>')+'物流费用</th>'+
										'<th width="100">货品条码</th>'+
										'<th width="240">质检描述</th>'+
										($("#isRejection").val()=="true"?'':'<th width="80"><span class="fl">质检结果 </span><span class="fl ml3 mt5 icon_tips" id="jsTips"></span></th>')+
										'<th width="130">'+(($("#isRejection").val()=="true")?'关联订单号':'关联订单货品条码')+'</th>'+
										'<th width="50">操作</th>'+
									'</tr>'+
								'</thead>'+
								'<tbody id="productQaInfo">'+
										'<tr id="'+insideCode+'_row1">' +
											tbl_expressNo +
											tbl_express_name +
											tbl_expressCharges +
											'<td>'+insideCode+'<input type="text" style="width:120px;display:none;" id="insideCode" name="insideCode_input" class="ginput" value="'+insideCode+'"></td>'+
											'<td style="text-align:left;padding-left:5px;"><input type="text" style="width:230px;" id="qadsc" name="qadsc" class="ginput"></td>'+
											($("#isRejection").val()=="true"?'':'<td>通过</td>')+
											qaToOrder+
											'<td><a href="javascript:;" onclick="deleteItem(\''+insideCode+'_row1\')">删除</a></td>'+
										'</tr>'+
								'</tbody>'+
					'</table></form>');
					}else{
					  var _count;
					  if(flag){
					    _count=len-$('#qaDetails tbody').find('tr').length+2;
					  }else{
					    _count=data[0]['diff'][insideCode];
					  }
					  var add=false;
					  for(var _i=1;_i<_count+1;_i++){
					      if(!document.getElementById(insideCode+"_row"+_i)){
						    $("#productQaInfo").append('<tr id="'+insideCode+'_row'+_i+'">'+
							'<td>'+insideCode+'<input type="text" style="width:120px;display:none;" id="insideCode" name="insideCode_input" class="ginput" value="'+insideCode+'"></td>'+
							'<td style="text-align:left;padding-left:5px;"><input type="text" style="width:230px;" id="qadsc" name="qadsc" class="ginput"></td>'+
							($("#isRejection").val()=="true"?'':'<td>通过</option></td>')+
							qaToOrder+'<td><a href="javascript:;" onclick="deleteItem(\''+insideCode+'_row'+_i+'\')">删除</a></td>'+'</tr>');
						    changeRowspanLen();
						    add=true;
						    break;
					      }
					  }
					  if(!add&&$('#qaDetails tbody').find('tr').length!=len){
					      tips="该订单货品条码"+insideCode+"已录入完成,请勿重复录入！";
					  }
					}
				  }else{
				         if($('#qaDetails tbody').find('tr').length!=len){
				           tips="该订单货品条码"+insideCode+"已录入完成,请勿重复录入！";
					     }
				  }
				  if($('#qaDetails tbody').find('tr').length==len){
				     tips="该订单全部可质检货品条码数目已正确,请填写质检信息后保存！";
				  }else if($("#isRejection").val()=="true"){
				    tips=tips+"已登记"+$('#qaDetails tbody').find('tr').length+"件，还有"+(len-$('#qaDetails tbody').find('tr').length)+"件未登记！";
				  }
				  if(tips!='')showTips(tips);
	 }
	 /**
	  * @param insideCodeOrder 同一货品编码所关联的子订单号集合
	  * @return 返 回下一个子订单号
	  */
	 function geOrderSubNum(insideCodeOrder){
	 	 for(var i = 0;i< insideCodeOrder.length;i++){
	 	 	 var flag = true;
	 		 $("[name=qaToOrder]").each(function(){
	 		 	if(insideCodeOrder[i]['orderSub']==$(this).val()){
	 		 		var num = $("[name=qaToOrder][value="+$(this).val()+"]").size();
	 		 		var total = $("[orderSourceNum="+$(this).val()+"]").first().html()-0;
	 		 		if(num == total){
	 		 			flag = false;
	 		 		}
	 		 	}
	 		 });
	 		 if(flag){
	 		 	return insideCodeOrder[i]['orderSub'];
	 		 }
	 	 }
	 	 return null;
	 }
	 
     function deleteItem(id){
      if($('#qaDetails tbody').find('tr').length==1 || ($('#qaDetails tbody').find('tr').length==2 && $('#row1')[0] )){
       $('#qaDetails tbody').empty();
       return;
      }
	   if($('#'+id).find('td').length==8){
	    $('#'+id).find('td').eq(3).remove();
        $('#'+id).find('td').eq(3).remove();
        $('#'+id).find('td').eq(3).remove();
        $('#'+id).find('td').eq(3).remove();
        $('#'+id).find('td').eq(3).remove();
        $('#qaDetails tbody tr:first').attr('id','row1');
	   }else{
	 	 $('#'+id).remove();
         changeRowspanLen();
	    }
	 }
	 
	 function changeRowspanLen(){
	   var _len=$('#qaDetails tbody tr').length;
	   $('#qaDetails tbody tr:first td').eq(0).attr('rowspan',_len);
	   $('#qaDetails tbody tr:first td').eq(1).attr('rowspan',_len);
	   $('#qaDetails tbody tr:first td').eq(2).attr('rowspan',_len);
	 }

	function validateQueryForm() {
		var errMsgs = new Array("");
		var fieldError = false;
		var errors = false; 
		var form = $("#saveResult"); 
		if (document.getElementById('expressEnt')) {
			field = document.getElementById('expressEnt');
			var error = "请选择快递公司";
			if (field.value != null && (field.value == "" || field.value.replace(/^\s+|\s+$/g,"").length == 0)) {
				addError(field, errMsgs, error);
				errors = true;
			}else{
				clearError(field);
			}
		} 
/*		if (document.getElementById('qaToOrder')) {
			field = document.getElementById('qaToOrder');
			var error = "关联订单货品条码为空";
			if (field.value != null && (field.value == "" || field.value.replace(/^\s+|\s+$/g,"").length == 0 || field.value=="undefined")) {
				addError(field, errMsgs, error);
				errors = true;
			}else{
				clearError(field);
			}
		}
*/		if (document.getElementById('expressNo')) {
			field = document.getElementById('expressNo');
			var error = "请输入快递单号";
			if (field.value != null && (field.value == "" || field.value.replace(/^\s+|\s+$/g,"").length == 0)) {
				addError(field, errMsgs, error);
				errors = true;
			}else if(field.value.replace(/^\s+|\s+$/g,"").length < 6){
			    error="您录入的快递单号不符合规则，请填写正确的快递单号";
				addError(field, errMsgs, error);
				errors = true;
			}else{
			var url='${BasePath}/quality/expressNoCheck.sc';
	 	    var params = {'expressOrderId':field.value,'orderSubNo':$("#orderNo").val()};
	 	    var flag = false;
	 	    var message='';
		$.ajax({
			url: url+'?time='+(new Date()).valueOf(),
			async: false,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data[0]['status'] == '1'){
				    message=data[0]['message'];
                    flag=true;
				}else{
					flag=false;
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				openDialog("服务器错误,请稍后再试!");
				return;
			}
		});
			  if(flag){
				addError(field, errMsgs, message);
				errors = true;
			  }else{
			    clearError(field);
			  }
			}
		} 
		if (document.getElementById('expressCharges')) {
			field = document.getElementById('expressCharges');
			var error = "请输入快递费用";
			if (field.value != null && (field.value == "" || field.value.replace(/^\s+|\s+$/g,"").length == 0)) {
				addError(field, errMsgs, error);
				errors = true;
			}else{
				clearError(field);
			}
		}  
		/*if (document.getElementById('qaDescription')) {
			field = document.getElementById('qadsc');
			var error = "请输入质检描述";
			if (field.value != null && (field.value == "" || field.value.replace(/^\s+|\s+$/g,"").length == 0)) {
				addError(field, errMsgs, error);
				errors = true;
			}else{
				clearError(field);
			}
		} 
		*/
		if(errMsgs[0].length > 0){
			openDialog(errMsgs[0]);
		}
		return !errors; 
	}
	function saveResult(){
	  var orderNo=$("#orderNo").val();
	  var isException=$("#isException").val();
	  var isRejection=$("#isRejection").val();
	  var qaToOrder;
	  var expressNo=$("#expressNo").val();
	  var expressEnt=$("#expressEnt").val();
	  var expressCharges=(isRejection=="true"?0:$("#expressCharges").val());
	  var paytype=(isRejection=="true"?"NO":($("#paytype").attr("checked")=="checked"?"YES":"NO"));
	  var isPassed;
	  var insideCode;
	  var qadsc;
	  var _this
	  var index=0;
	  $("input[name='insideCode_input']").each(function(){
			_this=$(this);
			insideCode=(0==index?(_this.val()):(insideCode+"####"+_this.val()));
			isPassed=(0==index?("1"):(isPassed+"####1"));
            index++;
      });
      index=0;
	  $("input[name='qadsc']").each(function(){
			_this=$(this);
			qadsc=(0==index?(_this.val()):(qadsc+"####"+_this.val()));
            index++;
      });
      index=0;
	  $("[name='qaToOrder']").each(function(){
			_this=$(this);
			qaToOrder=(0==index?(_this.val()):(qaToOrder+"####"+_this.val()));
            index++;
      });
      $.ajax({
		  type: 'post',
		  url: '${BasePath}/quality/saveResult.sc?param=' + new Date(),
		  dataType: 'json',
		  data: 'orderNo='+orderNo+'&isException='+isException+'&isRejection='+isRejection+'&qaToOrder='+encodeURIComponent(qaToOrder)+'&expressNo='+expressNo+'&expressEnt='+expressEnt+'&expressCharges='+expressCharges+'&paytype='+paytype+'&isPassed='+isPassed+'&insideCode='+encodeURIComponent(insideCode)+'&qadsc='+qadsc,
		  beforeSend: function(XMLHttpRequest){
			var timer;
	        dialog=ygdg.dialog({
	        content: '提交中...',
	        init: function () {
	    	  var that = this, i = 5;
	          var fn = function () {
	            that.title(i + '秒后关闭');
	            !i && that.close();
	            i--;
	            };
	          timer = setInterval(fn, 1000);
	          fn();
	          },
	          close: function () {
	    	  clearInterval(timer);
	          }
	       }).show();
		  },
		  success: function(data, textStatus) {
			  dialog.close();
			   if(!($.isEmptyObject(data))){
				   if(data.success){
					   dialog.close();
				       ygdg.dialog({
					       id:'successFun',
					       title:'保存成功',
					       content:data.msg,
					       icon:'succeed',
					       okVal:'确定',
					       ok:function(){
					    	   location.href="${BasePath}/quality/to_qualityRegister.sc";
						   },
						   close:function(){
							   location.href="${BasePath}/quality/to_qualityRegister.sc";
						   }
					   });
				   }else{
					   ygdg.dialog({
					       id:'successFun',
					       title:'保存失败',
					       content:data.msg,
					       icon:'error',
					       okVal:'确定',
					       ok:function(){
					    	   location.href="${BasePath}/quality/to_qualityRegister.sc";
						   },
						   close:function(){
							   location.href="${BasePath}/quality/to_qualityRegister.sc";
						   }
					   });
				   }
			   }else{
				   ygdg.dialog({
				       id:'successFun',
				       title:'保存失败',
				       content:'保存失败，请联系管理员！',
				       icon:'error',
				       okVal:'确定',
				       ok:function(){
				    	   location.href="${BasePath}/quality/to_qualityRegister.sc";
					   },
					   close:function(){
						   location.href="${BasePath}/quality/to_qualityRegister.sc";
					   }
				   });
			   }
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
			dialog.close();
		    ygdg.dialog({
			       id:'successFun',
			       title:'保存失败',
			       content:'保存失败，请联系管理员！',
			       icon:'error',
			       okVal:'确定',
			       ok:function(){
			    	   location.href="${BasePath}/quality/to_qualityRegister.sc";
				   },
				   close:function(){
					   location.href="${BasePath}/quality/to_qualityRegister.sc";
				   }
			   });
		  }
		});
	}
	
	function addError(f, errStrs, err){
		if(errStrs[0].length > 0) errStrs[0] += "\n";
		errStrs[0] += err;
		if(f.length) return;
		f.style.borderWidth = "2px";
		f.style.borderStyle = "solid";
		f.style.borderColor = "#FFCC88";
	}
	function clearError(f){
		if(f.length) return;
		f.style.borderWidth = "";
		f.style.borderStyle = "";
		f.style.borderColor = "";
	} 
	<#if vo??&&vo.errorMessage??>
		showMessage('${vo.errorMessage}');
	</#if>
	<#if !orderProductInfo??>
		$("#orderNo").val('');
		$("#insideCode").attr('disabled','true');
		$("#inputReturn").attr('disabled','true');
	</#if>
	
	var dg;
	$('#jsTips').live('mouseover',function(){
		var _html='<div style="line-height:20px;width:400px;">1.符合退换货标准的商品，请商家在系统录入质检信息，优购将给顾客进行退换货处理。<br/>2.质检不符合退换货标准的商品，请商家联系优购运营进行线下处理。<br/></div>';
		var _this=$(this);
		var _top=_this.offset().top- $(document).scrollTop()+30;
		var _left=_this.offset().left-150;
		dg=ygdgDialog({
			id:'test',
			padding : 0,
			title:'质检结果说明',
			top:_top,
			left:_left,
			closable:false,
			content:_html
		});
	}).live('mouseout',function(){
	 	dg.close();
	});
//显示订单详情
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_ZJDJ",960,650,'订单详情');
};
</script>
</html>
