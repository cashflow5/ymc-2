<!DOCTYPE html>
<!-- saved from url=(0038)http://10.0.20.199:8081/calendar.shtml -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<#include "../merchants-include.ftl">
<title>优购商城--商家后台</title>
</head>

<body>
<style>
.cal_div{width:1090px;}
.caltb{float:left;margin:8px 5px 0 0;height:140px;}
.cal_year{font-size:24px;color:#ff0000;float:left;padding:0 5px;}
.caltb th,.caltb td{width:24px;height:18px;line-height:18px;border:1px solid #ddd;text-align:center;color:#666;}
.caltb th{font-weight:normal;background:#ebf7fd;}
.caltb td input{width:24px;height:100%;font-weight:bold;color:#666;background:#fff;border:none;text-align:center;}
.caltb td.empty{background:#f0f0f0;}
.caltb caption{font-weight:bold;height:18px;line-height:18px;border:1px solid #ddd;border-bottom:none;}
.cal_tips{line-height:20px;padding:15px;border:1px solid #ddd;background:#f2f1f1;margin-top:10px;width:1050px;}
.cal_yybtn{font-size:20px;font-weight:bold;background:none;border:none;float:left;}
.caltb td input.red{color:#f00;}
.caltb td input.cblue{color:#06F;}
.caltb td.today,.caltb td.today input{background:#ddd;}
.caltb td.hover,.caltb td.hover input{background:#e9eaeb; cursor:pointer;}
</style>
<div class="container">
<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" id="btnDiv">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt" id="btn_save">保存</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
<form action="${BasePath}/yitiansystem/merchants/businessorder/to_shipmentDayList.sc" name="queryForm" id="queryForm" method="post">	
<input type="hidden" value="${isView!'' }" name="isView" id="isView"/>
<div style="padding-left:10px;">
	<div style="width:1120px;padding:20px 0;">
  	<span class="clearfix" style="width:150px;margin:0 auto;">
  	<input type="button" class="cal_yybtn jsYearBtn" value="&lt;&lt;">
    	<span id="spanYear" class="cal_year">${year?string("0") }</span>
    	<input type="hidden" name="year" id="year" value="${year?string("0") }" /> 
    <input type="button" class="cal_yybtn jsYearBtn" value="&gt;&gt;">
   </span>
  </div>
  <div class="clearfix cal_div">
    <#list 1..12 as month >
   	<table class="caltb">
          <caption>
          ${month }月
          </caption><thead>
              <tr>
                  <th>日</th>
                  <th>一</th>
                  <th>二</th>
                  <th>三</th>
                  <th>四</th>
                  <th>五</th>
                  <th>六</th>
              </tr>
          </thead>
          <#if dateMap??>
	      <#list 1..5 as i >
            	<tr>
                 	<#assign key= year?string("0") + month />
	          	  	<#list dateMap[key] as item >
          	  			<#if ((i -1 ) lte (item_index / 7)) && ((item_index / 7)  lt i ) >
          	  				<#if !(item.date)?? >
          	  					 <td></td>
          	  				<#else>
          	  					<td class="<#if (item.date)?string("yyyy-MM-dd") == now?string("yyyy-MM-dd")  >today</#if>" > 
          	  						<input <#if (item.date)?date lt now?date >disabled</#if> type="hidden" name="dayId[]" value="${(item.id)!'' }" />
          	  						<input <#if (item.date)?date lt now?date >disabled</#if> type="hidden" name="isShipmentDay[]" value="${(item.isShipmentDay)!'' }" />
          	  						<input <#if (item.date)?date lt now?date >disabled</#if> type="button" id="${(item.id)!'' }"  value="${(item.day)!'' }" class="<#if (item.isShipmentDay)?? && (item.isShipmentDay) ='0'>red</#if>">
          	  					</td>
          	  				</#if>
          	  				<!--  
          	  				<#if !(item_has_next)>
          	  					<td></td>
          	  				</#if> 
          	  				-->
         	  			</#if>
          	  		</#list>
               </tr>
         </#list> 	
	     </#if>
    </table>
    </#list>
  </div>
  <div class="cal_tips">
     提示：<br>
    1. 日期属性包括“发货日”和“非发货日”两种：<strong>黑色</strong>代表发货日，<strong class="cred">红色</strong>代表非发货日；<br>
	2. 可聚焦并单击某日期，修改其日期属性；只允许编辑从系统当前第二天起的日期；<br>
	3. 编辑完日历后，须点击【保存】按钮，以生效操作。
  </div>
</div>
</form>
</div>
<script>
var falg = false;
$('.jsYearBtn').click(function(){
	
	if(falg){
		alert("该年份日期已修改，请先保存");
		return false;
	}
	var _this=$(this);
	var _yy=$('#spanYear').text();
	var year;
	if(_this.val()==">>"){
		year = parseInt(_yy)+1;
	}else{
		year = parseInt(_yy)-1;
	}
	$("#year").val(year);
	$("#queryForm").submit();
	
});


$('.caltb td input[type="button"]').click(function(){
	var _this=$(this);
	var is_shipment_day_obj=_this.prev();
	var is_shipment_day = "1";
	falg = true;
	if(is_shipment_day_obj.val() =="0"){
		_this.removeClass('red');
		is_shipment_day = "1";
	}else{
		_this.addClass('red');
		is_shipment_day = "0";
	}
	is_shipment_day_obj.val(is_shipment_day);
	return false;
});

$('.caltb td input[type="button"]').hover(function(){
	var _this=$(this);
	if(_this.attr('disabled')){
		return false;
	}
	_this.parent().addClass('hover');
},function(){
	var _this=$(this);
	_this.parent().removeClass('hover');
});

var isView = $("#isView").val();
if(isView){
	$('.caltb td input[type="button"]').each(function(){
		 $(this).attr("disabled","true");
	});
	$("#btnDiv").remove();
}

$("#btn_save").click(function(){
	falg = false;
	var _this=$(this);
	$.ajax({
		   type: "POST",
		   url: "${BasePath}/yitiansystem/merchants/businessorder/save_shipmentDay.sc",
		   data:{
			   "idStr":JSON.stringify($("#queryForm").serializeObject().dayId),
			   "isShipmentDayStr":JSON.stringify($("#queryForm").serializeObject().isShipmentDay)
		   },
		   beforeSend:function(xhr){
			   if(!confirm("确定保存")){
				   return false;
			   }
		   },
		   async:false,
		   error:function(XMLHttpRequest, textStatus, errorThrown){
			   alert("保存失败，请联系管理员");
		   },
		   success: function(msg){
			   alert("保存成功");
			   _this.removeAttr("disabled");
		   }
		});
	
});

</script>


</body></html>