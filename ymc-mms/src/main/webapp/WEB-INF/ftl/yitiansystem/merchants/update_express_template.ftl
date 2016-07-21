<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<style>
.drag{border:1px solid #ff8c40;background:#fff5f6;padding:5px;color:#333;height:12px; line-height:12px;font-size:12px;display:inline-block;position:absolute;left:0px;top:0px;padding-right:15px;}
.drag i{font-style:normal;position:absolute;right:3px; z-index:9999; cursor:pointer;}
</style>

<title>优购商城--商家后台</title>

<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<script type="text/javascript" src="${BasePath}/js/Lodop.js?version=2.1.0"></script>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
					<span>
						<a href="#" class="btn-onselc">修改快递单模板</a>
					</span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/uploadImages.sc" name="queryForm" id="queryForm" method="post"   enctype="multipart/form-data">
		      <input type="hidden" name="logisticsId" id="logisticsId" value="<#if logisticsId??>${logisticsId!''}</#if>">
		      <textarea id="tbody" style="display:none" name="tbody"><#if template??&&template.tbody??>${template.tbody!''}</#if></textarea>
		      <input type="hidden" name="id" id="id" value="<#if template??&&template.id??>${template.id!''}</#if>">
		      <input type="hidden" name="backGroundImage" id="backGroundImage"  value="<#if template??&&template.backGroundImage??>${template.backGroundImage!''}</#if>">
		      <input type="hidden" name="fontSizeInits" id="fontSizeInits"  value="<#if template??&&template.fontSize??>${template.fontSize!''}</#if>">
                <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr>
                          <td>
                                                                                单据尺寸:宽:<input type="text" style="width:35px;" name="width" id="width" value="<#if template??&&template.width??>${template.width?c!''}</#if>">mm
                                  	      高:<input type="text" style="width:35px;" name="heigth" id="heigth" value="<#if template??&&template.heigth??>${template.heigth?c!''}</#if>">mm
                          </td>
                        </tr>
                        <tr>
                          <td>
                                                                              单据背景图:<input type="file" value="浏览..." name="filePath" id="filePath">
                              <input type="button" value="上传" onclick="uploadImage();">
                          </td>
                        </tr>
                        <tr>	
                          <td> 选择打印发货项:
                            <input type="checkbox" name="shipmentsName" id="shipmentsName" value="发货人姓名"  <#if template??&&template.shipmentsName??>checked</#if>>发货人姓名&nbsp;&nbsp;
                            <input type="checkbox" name="shipmentsOneArea" id="shipmentsOneArea" value="发货1级地区"  <#if template??&&template.shipmentsOneArea??>checked</#if>>发货人1级地区&nbsp;&nbsp;
                            <input type="checkbox" name="shipmentsTwoArea" id="shipmentsTwoArea" value="发货2级地区"  <#if template??&&template.shipmentsTwoArea??>checked</#if>>发货人2级地区&nbsp;&nbsp;
                            <input type="checkbox" name="shipmentsThreeArea" id="shipmentsThreeArea" value="发货3级地区"  <#if template??&&template.shipmentsThreeArea??>checked</#if>>发货人3级地区<br/>
                            <input type="checkbox" name="shipmentsAdress" id="shipmentsAdress" value="发货人地址"  style="margin-left:95px;" <#if template??&&template.shipmentsAdress??>checked</#if>>发货人地址&nbsp;&nbsp;
                            <input type="checkbox" name="shipmentsPhone" id="shipmentsPhone" value="发货人手机"  <#if template??&&template.shipmentsPhone??>checked</#if>>发货人手机&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="shipmentsTell" id="shipmentsTell" value="发货人电话" <#if template??&&template.shipmentsTell??>checked</#if>>发货人电话&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="shipmentsEmail" id="shipmentsEmail" value="发货人邮编"  <#if template??&&template.shipmentsEmail??>checked</#if>>发货人邮编&nbsp;&nbsp;
                          </td>
                       </tr>
                         <tr>
                          <td>
                                                                        选择打印收货项:
                            <input type="checkbox" name="orderSubNo" id="orderSubNo" value="订单号"  <#if template??&&template.orderSubNo??>checked</#if>>订单号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneeName" id="consigneeName" value="收货人姓名"  <#if template??&&template.consigneeName??>checked</#if>>收货人姓名&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneeOneArea" id="consigneeOneArea" value="收货人1级地区"  <#if template??&&template.consigneeOneArea??>checked</#if>>收货人1级地区&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneeTwoArea" id="consigneeTwoArea" value="收货人2级地区"  <#if template??&&template.consigneeTwoArea??>checked</#if>>收货人2级地区<br/>
	                        <input type="checkbox" name="consigneeThreeArea" id="consigneeThreeArea"  value="收货人3级地区"  style="margin-left:95px;" <#if template??&&template.consigneeThreeArea??>checked</#if>>收货人3级地区
	                        <input type="checkbox" name="consigneeAdress" id="consigneeAdress" value="收货人地址"  <#if template??&&template.consigneeAdress??>checked</#if>>收货人地址&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneePhone" id="consigneePhone" value="收货人手机"  <#if template??&&template.consigneePhone??>checked</#if>>收货人手机&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneeTell" id="consigneeTell" value="收货人电话"  <#if template??&&template.consigneeTell??>checked</#if>>收货人电话<br/>
	                        <input type="checkbox" name="consigneeEmail" id="consigneeEmail" value="收货人邮编"  style="margin-left:95px;" <#if template??&&template.consigneeEmail??>checked</#if>>收货人邮编&nbsp;&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneeYear" id="consigneeYear" value="当日日期-年"  <#if template??&&template.consigneeYear??>checked</#if>>当日日期-年&nbsp;
	                        <input type="checkbox" name="consigneeMonth" id="consigneeMonth" value="当日日期-月"  <#if template??&&template.consigneeMonth??>checked</#if>>当日日期-月&nbsp;&nbsp;&nbsp;&nbsp;
	                        <input type="checkbox" name="consigneeDay" id="consigneeDay" value="当日日期-日" <#if template??&&template.consigneeDay??>checked</#if>>当日日期-日<br/>
	                        <input type="checkbox" name="commodityNum" id="commodityNum" value="发货商家数量"  style="margin-left:95px;" <#if template??&&template.commodityNum??>checked</#if>>发货商家数量&nbsp;
	                        <input type="checkbox" name="orderSourceId" id="orderSourceId" value="订单来源"  <#if template??&&template.orderSourceId??>checked</#if>>订单来源&nbsp;&nbsp;&nbsp;&nbsp;
	                        <input type="checkbox" name="number" id="number" value="对号"  <#if template??&&template.number??>checked</#if>>对号-√&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                        <input type="checkbox" name="money" id="money" value="收款金额"  <#if template??&&template.money??>checked</#if>>收款金额<br/>
	                        <input type="checkbox" name="remark" id="remark" style="margin-left:95px;"  value="订单备注" <#if template??&&template.remark??>checked</#if>>订单备注
                          </td>
                          <tr>
	                          <td>
                                                                                            设置字体:
                                  <select id="fontSize" name="fontSize">
                                    <option value="10" <#if template??&&template.fontSize??&&template.fontSize=='10'>selected</#if>>10</option>                               
                                    <option value="11" <#if template??&&template.fontSize??&&template.fontSize=='11'>selected</#if>>11</option>
                                    <option value="12" <#if template??&&template.fontSize??&&template.fontSize=='12'>selected</#if>>12</option>
                                    <option value="13" <#if template??&&template.fontSize??&&template.fontSize=='13'>selected</#if>>13</option>
                                    <option value="14" <#if template??&&template.fontSize??&&template.fontSize=='14'>selected</#if>>14</option>
                                    <option value="15" <#if template??&&template.fontSize??&&template.fontSize=='15'>selected</#if>>15</option>
                                    <option value="16" <#if template??&&template.fontSize??&&template.fontSize=='16'>selected</#if>>16</option>
                                    <option value="17" <#if template??&&template.fontSize??&&template.fontSize=='17'>selected</#if>>17</option>
                                    <option value="18" <#if template??&&template.fontSize??&&template.fontSize=='18'>selected</#if>>18</option>
                                    <option value="19" <#if template??&&template.fontSize??&&template.fontSize=='19'>selected</#if>>19</option>
                                    <option value="20" <#if template??&&template.fontSize??&&template.fontSize=='20'>selected</#if>>20</option>
                                  </select>
	                          </td>
                         </tr>
                        <tr>
	                          <td >
	                          	是否加粗:<input type="radio" name="isBold" id="isBold" value="0" <#if template??&&template.isBold??&&template.isBold==0>checked</#if>>不加粗
	                                  <input type="radio" name="isBold" id="isBold"  value="1" <#if template??&&template.isBold??&&template.isBold==1>checked</#if>>加粗
	                          </td>
                       </tr>
                         <tr style="display:none">
	                          <td >
        						<div id="Box" style="position:relative;border:1px solid #ccc;width:900px;height:550px;background-repeat:no-repeat;background-image:url(<#if images??>${images!''}<#elseif templateImages??>${templateImages!''}</#if>)">
									<#if template??&&template.tbody??>${template.tbody!''}</#if>
								</div>
	                          </td>
                       	</tr>
                       	<tr>
                       		<td><input type="button" value="设计模板" class="yt-seach-btn" onclick="designTemp();"></td>
                       	</tr>
                        <tr>
                        	<td>
                         <input type="button" value="保存" class="yt-seach-btn" onclick="return saveExpressTemplate();">
                          <input type="button" value="关闭" class="yt-seach-btn" onclick="closewindow();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>


<script type="text/javascript">
var LODOP; 
var templateImages = "${templateImages}";
//窗体加载设置样式
$(document).ready(function(){
  //设置模版的高宽度
  var width=$("#width").val();
  var heigth=$("#heigth").val();
  if(width!=""){
    var w=document.getElementById("Box");
     w.style.width=width+"px";
  }
  if(heigth!=""){
    var h=document.getElementById("Box");
    h.style.height =heigth+"px";
  }
});

//luo.hl 6-12
function designTemp(){
	var backGroundImage = $("#backGroundImage").val();
	if(backGroundImage==""){
		alert("请先上传背景图片");
		return;
	}
	var tbody = $("#tbody").val();
	LODOP=getLodop();
	var checkeds = $("input[type=checkbox]:checked");	
	if(!contains(tbody,"LODOP",false)){ //首次设置
		LODOP.PRINT_INIT("快递单打印");
		LODOP.SET_PRINT_MODE("PROGRAM_CONTENT_BYVAR",true);//生成程序时，内容参数有变量用变量，无变量用具体值
		for(var i=0,length=checkeds.length;i<length;i++){
			if(i<15){
				LODOP.ADD_PRINT_TEXT(i*30,100,180,30,checkeds.eq(i).val());
			}else{
				LODOP.ADD_PRINT_TEXT((i-15)*30,280,180,30,checkeds.eq(i).val());
			}
		}
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='"+templateImages+"'>");
		LODOP.SET_PRINT_MODE("PRINT_SETUP_PROGRAM",true);
		$("#tbody").val(LODOP.PRINT_SETUP());
	}else{
		var tbodys = tbody.split(";");
		for(var i=0,length=tbodys.length;i<length;i++){
				if(contains(tbodys[i],"ADD_PRINT_SETUP_BKIMG",false)){
					tbodys[i]="";
				}
		}
		eval(tbodys.join(";"));
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='"+templateImages+"'>");
		$("#tbody").val(LODOP.PRINT_SETUP());
	}
};

function contains(string, substr, isIgnoreCase) {
	if (isIgnoreCase) {
		string = string.toLowerCase();
		substr = substr.toLowerCase();
	};
	var startChar = substr.substring(0,1);
	var strLen = substr.length;
	for(var j = 0; j < string.length - strLen + 1; j++) {
		if (string.charAt(j) == startChar){  
			if (string.substring(j, j + strLen) == substr){  
				return true;                    
			}                
		}          
	}          
	return false; 
}

//修改快递单模板
function updateExpressTemplate(){
	 document.queryForm.submit();
}

//上传图片
function uploadImage(){
  var files=$("#filePath").val();
  if(files==""){
    alert("请选中图片在上传!");
    return;
  }
  document.queryForm.submit();
}

//保存快递单模版
function saveExpressTemplate(){
 var files=$("#backGroundImage").val();
	if(files==""){
	    alert("模板背景图片不能为空!");
	    return;
	  }else{
	    document.queryForm.action="${BasePath}/yitiansystem/merchants/businessorder/saveExpressTemplate.sc";
	    /*var di=$("#Box").html();
	    $("#tbody").val(di);*/
	    document.queryForm.submit();
   }
}
//修改层的宽度
$("#width").change(function(){
   var width=$("#width").val();
   if(width!=""){
     var w=document.getElementById("Box");
     w.style.width=width+"px";
   }
}); 
//修改层的高度
$("#heigth").change(function(){
   var heigth=$("#heigth").val();
   if(heigth!=""){
     var h=document.getElementById("Box");
     h.style.height =heigth+"px";
   }
});  
 
(function($){
	// to track if the mouse button is pressed
	var isMouseDown    = false;

	// to track the current element being dragged
	var currentElement = null;

	// callback holders
	var dropCallbacks = {};
	var dragCallbacks = {};

	// global position records
	var lastMouseX;
	var lastMouseY;
	var lastElemTop;
	var lastElemLeft;
	
	// track element dragStatus
	var dragStatus = {};	

	// returns the mouse (cursor) current position
	$.getMousePosition = function(e){
		var posx = 0;
		var posy = 0;

		if (!e) var e = window.event;

		if (e.pageX || e.pageY) {
			posx = e.pageX;
			posy = e.pageY;
		}
		else if (e.clientX || e.clientY) {
			posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
			posy = e.clientY + document.body.scrollTop  + document.documentElement.scrollTop;
		}

		return { 'x': posx, 'y': posy };
	};

	// updates the position of the current element being dragged
	$.updatePosition = function(e) {
		var pos = $.getMousePosition(e);

		var spanX = (pos.x - lastMouseX);
		var spanY = (pos.y - lastMouseY);
		
		var maxL=$("#Box").width();
		var maxT=$("#Box").height();
		
		var posT=lastElemTop + spanY;
		var posL=lastElemLeft + spanX;
		var w=$("#"+currentElement.id).width()+30;
		var h=$("#"+currentElement.id).height()+20;
		if(posT>=maxT-h || posT<=0) return;
		if(posL>=maxL-w || posL<=0)return;

		$(currentElement).css("top",  posT);
		$(currentElement).css("left", posL);
	};

	// when the mouse is moved while the mouse button is pressed
	$(document).mousemove(function(e){
		if(isMouseDown && dragStatus[currentElement.id] == 'on'){
			// update the position and call the registered function
			$.updatePosition(e);
			if(dragCallbacks[currentElement.id] != undefined){
				dragCallbacks[currentElement.id](e, currentElement);
			}

			return false;
		}
	});

	// when the mouse button is released
	$(document).mouseup(function(e){
		if(isMouseDown && dragStatus[currentElement.id] == 'on'){
			isMouseDown = false;
			if(dropCallbacks[currentElement.id] != undefined){
				dropCallbacks[currentElement.id](e, currentElement);
			}

			return false;
		}
	});

	// register the function to be called while an element is being dragged
	$.fn.ondrag = function(callback){
		return this.each(function(){
			dragCallbacks[this.id] = callback;
		});
	};

	// register the function to be called when an element is dropped
	$.fn.ondrop = function(callback){
		return this.each(function(){
			dropCallbacks[this.id] = callback;
		});
	};
	
	// stop the element dragging feature
	$.fn.dragOff = function(){
		return this.each(function(){
			dragStatus[this.id] = 'off';
		});
	};
	
	
	$.fn.dragOn = function(){
		return this.each(function(){
			dragStatus[this.id] = 'on';
		});
	};

	// set an element as draggable - allowBubbling enables/disables event bubbling
	$.fn.easydrag = function(allowBubbling){

		return this.each(function(){

			// if no id is defined assign a unique one
			if(undefined == this.id || !this.id.length) this.id = "easydrag"+(new Date().getTime());

			// set dragStatus 
			dragStatus[this.id] = "on";
			
			// change the mouse pointer
			$(this).css("cursor", "move");

			// when an element receives a mouse press
			$(this).mousedown(function(e){

				// set it as absolute positioned
				$(this).css("position", "absolute");

				// set z-index
				$(this).css("z-index", "10000");

				// update track variables
				isMouseDown    = true;
				currentElement = this;

				// retrieve positioning properties
				var pos    = $.getMousePosition(e);
				lastMouseX = pos.x;
				lastMouseY = pos.y;

				lastElemTop  = this.offsetTop;
				lastElemLeft = this.offsetLeft;

				$.updatePosition(e);

				return allowBubbling ? true : false;
			});
		});
	};

})(jQuery);
    </script>
	
<script type="text/javascript">
$("input:checkbox").click(function(){
		var tbody = $("#tbody").val();
		if(tbody!=""){ //判断模板是否有内容
			var tbodys = tbody.split(";");
			//获取已经有的文本框
			var count = 0;
			for(var i=0,length=tbodys.length;i<length;i++){
					if(contains(tbodys[i],"ADD_PRINT_TEXT",false)){
						count++;
					}
			}
			if($(this).attr("checked")==true){
				if(count<15){
					tbodys.push('LODOP.ADD_PRINT_TEXT('+count*30+',100,180,30,"'+$(this).val()+'")');
				}else{
					tbodys.push('LODOP.ADD_PRINT_TEXT('+(count-15)*30+',280,191,30,"'+$(this).val()+'")');
				}
				
			}else{
				for(var i=0,length=tbodys.length;i<length;i++){
					if(contains(tbodys[i],$(this).val(),false)){
						tbodys[i]="";
					}
				}
			}
			$("#tbody").val(tbodys.join(";"));
		}
		
})

$(".drag .close").live('click',function(){
	var checkbox=$(this).parent().attr("id").split("-")[0];
	$("#"+checkbox).attr("checked",false);
	$(this).parent().remove();
	return false;
})
$(".drag").easydrag();

var caozuoId="";
//获取当前选中层的ID
$("#Box .drag").live('click',function(){
	caozuoId=$(this).attr("id");
})
//设置字体大小
$("#fontSize").click(function(){
   var fontSize=$("#fontSize").val();
   if(fontSize!=""){
      $("#"+caozuoId).css("fontSize",fontSize+"px");
   } 
}); 
//设置字体大小
$("#fontSize").change(function(){
   var fontSize=$("#fontSize").val();
   if(fontSize!=""){
      $("#"+caozuoId).css("fontSize",fontSize+"px");
   } 
}); 
//设置字体是否加粗
$("input:radio").click(function(){
    var isBold=$("input[type='radio']:checked").val();
	if(isBold==0){
	  $("#"+caozuoId).css("fontWeight","normal");
	}else{
	  $("#"+caozuoId).css("fontWeight","bold");
	}
});
</script>
