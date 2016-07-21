<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
</head>
<#include "../merchants-include.ftl">
<style type="text/css">
	.error{
		color:red;
	}
</style>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
		
		</div>
		<div class="modify"> 
          <form action="${BasePath}/yitiansystem/merchants/businessorder/save_punish_rule.sc" name="queryForm" id="queryForm" method="post" >
          	 <input type="hidden" id="id" name="id" value="${(punishRule.id)!'' }" />
          	 <input type="hidden" id="merchantCode" name="merchantCode" value="${(merchantsCode)!'' }" />
          	 <table cellpadding="0" cellspacing="0" class="list_table">
                 <tr>
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	<span>发货时效：</span>
                   	</td>
                   	<td>
                   		<input type="text"  name="shipmentHour" id="shipmentHour"  value="${(punishRule.shipmentHour)!'' }" class="inputtxt" />
                   		小时
                    </td>
                 </tr>
                 <tr>
                 	<td  style="width:120px;margin-left:1500px;text-align:right">
                    	超时效邮件通知商家：
                    </td>
                    <td>
                   		<input type="radio" value="1" name="isNotification"  <#if (punishRule.isNotification)?? && punishRule.isNotification == "1">checked</#if>/>是&nbsp;&nbsp;
                   		<input type="radio" value="0" name="isNotification"  <#if (punishRule.isNotification)?? && punishRule.isNotification == "0">checked</#if>/>否
                  	</td>
                 </tr>
                 <tr>
                 	<td  style="width:120px;margin-left:150px;text-align:right">
                    	邮箱地址：
                    </td>
                    <td>
                   		 <input type="text"  name="emails" id="emails" size="70"  value="${(punishRule.emails)!''}" class="tags" />
                  		 <span>（请输入邮箱地址，以enter键确认。最多只能输入10个邮件）<br/></span>
                  	</td>
                 </tr>
                 <tr>
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	延时发货处罚金额：
                   	</td>
                   	<td>
                   		<input type="radio" value="1" name="timeoutPunishType" <#if (punishRule.timeoutPunishType)?? && punishRule.timeoutPunishType =="1" >checked</#if> />按订单&nbsp;&nbsp;
                   		<input type="radio" value="0" name="timeoutPunishType" <#if (punishRule.timeoutPunishType)?? && punishRule.timeoutPunishType =="0" >checked</#if> />按订单金额比例
                    </td>
                 </tr>
                 <tr style="display:none"  id="timeoutPunishType1">
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	&nbsp;&nbsp;
                   	</td>
                   	<td>
                   		<input type="text"  name="timeoutPunishMoney" id="timeoutPunishMoney"  value="${(punishRule.timeoutPunishMoney)!''}" class="inputtxt" />
                   		/单
                    </td>
                 </tr>
                 <tr style="display:none" id="timeoutPunishType0">
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	&nbsp;&nbsp;
                   	</td>
                   	<td>
                   		订单金额的
                   		<input type="text"  name="timeoutPunishRate" id="timeoutPunishRate"  value="${(punishRule.timeoutPunishRate)!''}" class="inputtxt" />
                   		%
                    </td>
                 </tr>
                 	<#if (detail??) && (detail?size>0)>
	                    <#list detail as item>
                 <tr>
	                  	<td style="width:120px;margin-left:150px;text-align:right">
	                    	<#if item_index==0>缺货处罚金额：</#if>
	                   	</td>
	                   	<td class="cftd">
	                   		<input type="text" placeholder="0" name="begin_${item_index }" value="${(item.punishRateBegin)?int }" style="width: 40px;"/>%&nbsp;&lt;&nbsp;月缺货率&nbsp;&lt;=&nbsp;
	                   		<input type="text" placeholder="10" value="${(item.punishRateEnd)?int }" name="end_${item_index }" style="width: 40px;"/>%
	                   		，按商品成交价的<input name="cj_${item_index }" type="text" value="${(item.punishRule)?int }" placeholder="15" style="width: 40px;"/>%处罚&nbsp;&nbsp;
	                   		<input type="button" id="firstButton" class="btn-add-normal" name="button" onclick="insertRow(this);" value="插入区间"/>
	                   		<#if item_index!=0>&nbsp;&nbsp;<input type="button" class="btn-add-normal" name="button" onclick="deleteRow(this,'+i+');" value="删除"/></#if>
	                    </td>
                 		</#list>
                 </tr>
                 		<#else>
                 		<tr>
                 		<td style="width:120px;margin-left:150px;text-align:right">
	                    	缺货处罚金额：
	                   	</td>
	                   	<td class="cftd">
	                   		<input type="text" placeholder="0" name="begin_0" style="width: 40px;"/>%&nbsp;&lt;&nbsp;月缺货率&nbsp;&lt;=&nbsp;
	                   		<input type="text" placeholder="10" name="end_0" style="width: 40px;"/>%
	                   		，按商品成交价的<input name="cj_0" type="text" placeholder="15" style="width: 40px;"/>%处罚&nbsp;&nbsp;
	                   		<input type="button" id="firstButton" class="btn-add-normal" name="button" onclick="insertRow(this);" value="插入区间"/>
	                    </td>
	                    </tr>
	                 </#if>
                  <tr style="display:none" id="stockPunishType1">
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	&nbsp;&nbsp;
                   	</td>
                   	<td>
                   		<input type="text"  name="stockPunishMoney" id="stockPunishMoney"  value="${(punishRule.stockPunishMoney)!''}" class="inputtxt" />
                   		/单
                    </td>
                 </tr>
                 <tr style="display:none" id="stockPunishType0">
                  	<td style="width:120px;margin-left:150px;text-align:right">
                    	&nbsp;&nbsp;
                   	</td>
                   	<td>
                   		订单金额的
                   		<input type="text"  name="stockPunishRate" id="stockPunishRate"  value="${(punishRule.stockPunishRate)!''}" class="inputtxt" />
                   		%
                    </td>
                 </tr>
                 
                  <tr>
                   	<td> 
                   		&nbsp;&nbsp;
                 	</td>
                   	<td>
                        <input id="saveBtn" type="submit" value="提交"  class="btn-add-normal"  />&nbsp;&nbsp;
                        <input id="cancelBtn" type="button" value="取消"  class="btn-add-normal" onclick="closewindow();" />
                 	</td>
                  </tr>
             </table>           
          </form>
		</div>
 		<div class="blank20"></div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
jQuery.extend(jQuery.validator.messages, {
	  required: "必选字段",
	  remote: "请修正该字段",
	  email: "请输入正确格式的电子邮件",
	  url: "请输入合法的网址",
	  date: "请输入合法的日期",
	  dateISO: "请输入合法的日期 (ISO).",
	  number: "请输入合法的数字",
	  digits: "只能输入非负整数",
	  creditcard: "请输入合法的信用卡号",
	  equalTo: "请再次输入相同的值",
	  accept: "请输入拥有合法后缀名的字符串",
	  maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
	  minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
	  rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
	  range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	  max: jQuery.validator.format("请输入一个最大为{0} 的值"),
	  min: jQuery.validator.format("请输入一个最小为{0} 的值")
	});
$(document).ready(function(){
	/*email tag*/
	$('#emails').tagsInput({
		'height':'150px',
		'width':'600px',
		'defaultText':'请输入邮件',
		'onAddTag':function(tag){
			var reg = /^(?:\w+\.?)*\w+@(?:\S+\.?)*\w+$/;
			if(!reg.test(tag)){
				alert("请输入正确邮件");
				$('#emails').removeTag(tag);
			}
			var tagSize = $("#emails").val().split(",").length;
			if(tagSize>10){
				alert("最多只可输入10个邮箱");
				$('#emails').removeTag(tag);
			}
			
		}
	}); 
	
	var timeoutPunishType = $('input[type="radio"][name="timeoutPunishType"]:checked').val();
	var stockPunishType = $('input[type="radio"][name="stockPunishType"]:checked').val();
	
	if(timeoutPunishType){
		if(timeoutPunishType == "1"){
			$("#timeoutPunishType1").show();
			$("#timeoutPunishType0").hide();
		}else{
			$("#timeoutPunishType1").hide();
			$("#timeoutPunishType0").show();
		}
	}
	
	if(stockPunishType){
		if(stockPunishType == "1"){
			$("#stockPunishType1").show();
			$("#stockPunishType0").hide();
		}else{
			$("#stockPunishType1").hide();
			$("#stockPunishType0").show();
		}
	}

	
	$("#queryForm").validate({
		rules: {
			shipmentHour: {
				required:true,
				digits:true,
				min:12
			},
			timeoutPunishMoney:{
				number:true,
				min:0
			},
			timeoutPunishRate:{
				range:[0,100]   
			},
			stockPunishMoney:{
				number:true,
				min:0
			},
			stockPunishRate:{
				range:[0,100]   
			},
			"begin_0":{
				digits:true,
				min:0
			},
			"end_0":{
				digits:true,
				min:0
			},
			"cj_0":{
				digits:true,
				min:0,
				max:100
			}
		},
        messages: {
        	shipmentHour: {
        		required:"请输入时效",
        		digits:"请输入整数",
        		min:"时效不能低于12"
        	},
        	timeoutPunishMoney:{
        		number:"请输入正确数字",
				min:"最输入大于0的值"
        	},
        	timeoutPunishRate:{
        		range:"请输入值必须介于 0 和 100之间的值"
        	},
        	stockPunishMoney:{
        		number:"请输入正确数字",
				min:"最输入大于0的值"
        	},
        	stockPunishRate:{
        		range:"请输入值必须介于 0 和 100之间的值"
        	}
   		},
	  	submitHandler:function(form){
	  		var data = $("#queryForm").serialize();
	  		//alert(data);
	  		var flag = true;
	  		var isEnterloop = false;
	  		var emails = $("#emails").val().split(",");
	  		if(emails.length > 10){
	  			alert("最多只可输入10个邮箱");
	  			flag = false;
	  		}
	  		var bv = "";
	  		var ev = "";
	  		var cv = "";
	  		var count = 0;
	  		var lastev = "";
	  		$(".cftd").each(function(index,domEle){
	  			bv = $.trim($(this).children("input[name^='begin']").val());
	  			ev = $.trim($(this).children("input[name^='end']").val());
	  			cv = $.trim($(this).children("input[name^='cj']").val());
		  		if(1==$(".cftd").size()){
					if(""!=bv||""!=ev||""!=cv){
						isEnterloop = true;
						if(""!=cv){
							if((ev-bv)!=100){
								ygdg.dialog.alert("月缺货率起始值加结束值的和需等于100%");
								$(this).children("input[name^='begin']").addClass("error");
								$(this).children("input[name^='end']").addClass("error");
								$(this).children("input[name^='end']").focus();
								flag = false;
								return false;
							}else{
								count = 100;
							}
						}else{
							ygdg.dialog.alert("商品成交价百分比不能为空！");
							$(this).children("input[name^='cj']").addClass("error");
							$(this).children("input[name^='cj']").focus();
							flag = false;
							return false;
						}
					}
			  	}else{
			  		if(""!=bv||""!=ev||""!=cv){
			  			isEnterloop = true;
						if(""!=cv){
							if(""!=lastev){
								if(lastev!=bv){
									ygdg.dialog.alert("【缺货处罚金额】第【"+(index)+"】行月缺货率结束值与第【"+(index+1)+"】行起始值需相等！");
									$(".cftd").eq(index-1).children("input[name^='end']").addClass("error");
									$(this).children("input[name^='begin']").addClass("error");
									$(this).children("input[name^='begin']").focus();
									flag = false;
									return false;
								}else{
									count += (ev-bv);
								}
							}else{
								count += (ev-bv);
							}
							lastev = ev;
						}else{
							ygdg.dialog.alert("商品成交价百分比不能为空！");
							$(this).children("input[name^='cj']").addClass("error");
							$(this).children("input[name^='cj']").focus();
							flag = false;
							return false;
						}
					}
			    }
		  	});
			if(flag&&isEnterloop){
				if(count!=100){
					ygdg.dialog.alert("月缺货率各区间相加的和需为100%！");
					flag = false;
				}
			} 	
	  		if(flag){
	  			$.ajax({
	 			   type: "POST",
	 			   url: "${BasePath}/yitiansystem/merchants/businessorder/save_punish_rule.sc",
	 			   data: data,
	 			   cache: false,
	 			   beforeSend:function(XMLHttpRequest){
	 				   $("#saveBtn").val("提交中").attr("disabled","true");
	 			   },
	 			   success: function(result){
	 				  if(result == "true"){
	 					  alert("保存成功");
	 					  closewindow();
	 				  }else{
	 					  alert("保存失败");
	 					 $("#saveBtn").attr("disabled","false").val("提交");
	 				  }
	 			   },
	 			  complete:function(){
	 				 $("#saveBtn").attr("disabled","false").val("提交");
	 			  }
	 			});
	  		}
        } 
	});
	$(".cftd").each(function(){
		$(this).children("input[name^='begin']").rules("add",{digits:true,min:0});
		$(this).children("input[name^='end']").rules("add",{digits:true,min:0});
		$(this).children("input[name^='cj']").rules("add",{digits:true,min:1,max:100});
	});
});

$("input[name='timeoutPunishType']").click(function(){
	var index = $(this).val(); 
	if(index == "1"){
		$("#timeoutPunishType1").show();
		$("#timeoutPunishType0").hide();
	}else{
		$("#timeoutPunishType1").hide();
		$("#timeoutPunishType0").show();
	}
});

$("input[name='stockPunishType']").click(function(){
	var index = $(this).val(); 
	if(index == "1"){
		$("#stockPunishType1").show();
		$("#stockPunishType0").hide();
	}else{
		$("#stockPunishType1").hide();
		$("#stockPunishType0").show();
	}
});

function insertRow(obj){
	var i = new Date().getTime();
   	var str = '<tr><td style="width:120px;margin-left:150px;text-align:right"></td><td class="cftd">'+
   		'<input type="text" style="width: 40px;" name="begin_'+i+'"/>%&nbsp;&lt;&nbsp;月缺货率&nbsp;&lt;=&nbsp;&nbsp;'+
   		'<input type="text" style="width: 40px;" name="end_'+i+'"/>%'+
   		'&nbsp;，按商品成交价的<input type="text" style="width: 40px;" name="cj_'+i+'"/>%处罚&nbsp;&nbsp;&nbsp;'+
   		'<input type="button" class="btn-add-normal" name="button" onclick="insertRow(this,'+i+');" value="插入区间"/>'+
    '&nbsp;&nbsp;<input type="button" class="btn-add-normal" name="button" onclick="deleteRow(this,'+i+');" value="删除"/></td></tr>';
	$(str).insertAfter($(obj).parent().parent());
	$("input[name='begin_"+i+"']").rules("add",{digits:true,min:0});
	$("input[name='end_"+i+"']").rules("add",{digits:true,min:0});
	$("input[name='cj_"+i+"']").rules("add",{digits:true,min:1,max:100});
}
function deleteRow(obj,i){
	$(obj).parent().parent().remove();
}

</script>

