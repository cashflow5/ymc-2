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
			<div class="btn" onclick="save();">
				<span class="btn_l"></span>
	        	<b class="ico_btn save"></b>
	        	<span class="btn_txt">保存</span>
	        	<span class="btn_r"></span>
           </div>
    <div class="line"></div>
      <div class="btn" onclick="gotolink('${BasePath}/merchant/api/template/api_template_config.sc');">
				<span class="btn_l"></span>
	        	<b class="ico_btn back"></b>
	        	<span class="btn_txt">返回</span>
	        	<span class="btn_r"></span>
      </div>
     </div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">添加API权限模板</a></span>
				</li>
			</ul>
		</div>
 <div class="modify">
 <form action="${BasePath}/merchant/api/template/api_template_config_modify.sc" name="queryForm" id="queryForm" method="post"> 
  	<div class="add_detail_box">
  	<h3>模板基本信息</h3>
     <label>模板编号：</label>${template.templateNo!''}
     <label><span style="color:red;">*</span>模板名称：</label>
     <input type="text" name="templateName" id="templateName" value="<#if template??&&template.templateName??>${template.templateName!''}</#if>"/>&nbsp;&nbsp;&nbsp;
     <span>
     <label>描述：</label>
     <textarea style="width:380px;height:42px;" name="templateDesc" id="templateDesc"/><#if template??&&template.templateDesc??>${template.templateDesc!''}</#if></textarea>
     </span>
     </div>
    </form>
    <h3><span style="color:red;">*</span>API参数   <input type="button" value="添加API" onclick="addTemplate()" ></h3>
    <table cellpadding="0" cellspacing="0" class="list_table">
       <thead>
       <tr>
         <th>序号</th>
         <th>API代码</th>
         <th>API名称</th>
         <th>API分类</th>
         <th colspan=2 style="text-align:center;">频率上限</th>
         <th colspan=2 style="text-align:center;">日调用次数上限</th>
         <th style="text-align:center;">是否启用频率控制</th>
         <th style="text-align:center;">是否启用日调用次数控制</th>
         <th style="text-align:center;">操作</th>
       </tr>
       </thead>
       <tbody id="content">
         <#if pageFinder??&&pageFinder.data??>
         <#list pageFinder.data as item >
	     <tr id="${item.apiId!''}">
		   <td>${item_index + 1}</td>
		   <td id="${item.apiId!''}_0" style="display:none;">${item.id!""}</td>
		   <td id="${item.apiId!''}_1">${item.apiCode!""}</td>
           <td id="${item.apiId!''}_2">${item.apiName!""}</td>
           <td id="${item.apiId!''}_3">${item.categoryName!""}</td>
           <td align="right" id="${item.apiId!''}_4">${item.frequency!""}</td>
           <td id="${item.apiId!''}_5"><#if item.frequencyUnit==1>次/小时<#elseif item.frequencyUnit==2>次/分钟<#elseif item.frequencyUnit==3>次/秒</#if></td>
           <td align="right" id="${item.apiId!''}_6">${item.callNum!""}</td>
           <td id="${item.apiId!''}_7">次/天</td>
           <td align="center" id="${item.apiId!''}_8"><#if item.isFrequency==0>不开启<#elseif item.isFrequency==1>开启</#if></td>
           <td align="center" id="${item.apiId!''}_9"><#if item.isCallNum==0>不开启<#elseif item.isCallNum==1>开启</#if></td>
           <td id="${item.apiId!""}_10" align="center"><a href='#' onclick="updateRow('${item.apiId!""}');">修改</a>
               <a href='#' onclick="deleteRow('${item.apiId!""}');">删除</a></td>
	     </tr>
         </#list>
         <#else>
           <tr>
           <td colSpan="11">抱歉，没有您要找的数据  </td>
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

//添加资源列表
function addTemplate(){
var add=window.showModalDialog("${BasePath}/merchant/api/template/add_api_parameter.sc","","dialogWidth=800px;dialogHeight=680px;status=no;help=no;scrollbars=no");
   //var add=openwindow("${BasePath}/merchant/api/template/add_api_parameter.sc",800,680,"添加API参数信息");
   setInterval(function(){
   if(add){
   addApiList(add);
   add="";
   }
   }, 500);

}

var unit = {"1":"次/小时","2":"次/分钟 ","3":"次/秒","次/小时":"1","次/分钟":"2 ","次/秒":"3"}
var isOpen = {"0":"不开启","1":"开启 ","不开启":"0","开启":"1"}
function addApiList(add){
  var list=add.split(";");
  var size=list.length;
  var content="";
  var line="";
  var main=list[size-1].split(",");
  for(var i=0;i<size-1;i++){
    line=list[i].split(",");
    content+="<tr id=\""+line[0]+"\">";
    
    for(var j=0;j<line.length;j++){
      if(0==j){
       content+="<td>"+(i+1)+"</td>";
       content+="<td id=\""+line[0]+"_0"+"\" style=\"display:none;\">null</td>";
       $("#"+line[0]).remove();
      }else{
        content+="<td id=\""+line[0]+"_"+j+"\">"+line[j]+"</td>";
      }
    }
    content+="<td id=\""+line[0]+"_4"+"\" align=\"right\">"+main[0]+"</td>";
    content+="<td id=\""+line[0]+"_5"+"\">"+unit[main[1]]+"</td>";
    content+="<td id=\""+line[0]+"_6"+"\" align=\"right\">"+main[2]+"</td>";
    content+="<td id=\""+line[0]+"_7"+"\">"+"次/天"+"</td>";
    content+="<td id=\""+line[0]+"_8"+"\" align=\"center\">"+isOpen[main[3]]+"</td>";
    content+="<td id=\""+line[0]+"_9"+"\" align=\"center\">"+isOpen[main[4]]+"</td>";
    content+="<td id=\""+line[0]+"_10"+"\" align=\"center\"><a href='#' onclick=\"updateRow('"+line[0]+"');\">修改</a>"
               +"<a href='#' onclick=\"deleteRow('"+line[0]+"');\">删除</a></td>";
    content+="</tr>";
  }
  $("#content_null").remove();
  $("#content").prepend(content);
}

function deleteRow(id){
  var detailId=$("#"+id+"_0").text();
  if(detailId!="null"){
    if(confirm("确实要删除选中的记录吗!")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/merchant/api/template/api_template_config_delete_detail_byId.sc?id=" + detailId, 
			success: function(dt){
				if("success"==dt){
				   alert("删除成功!");
				   $("#"+id).remove();
				}else{
				   alert("删除失败!");
				}
			} 
		});
   }

  }

}


function updateRow(id){
var id_4=$("#"+id+"_4").html();
$("#"+id+"_4").html("<input style=\"width:70px;text-align:right;\" type=\"text\" name=\""+id+"_4_1"+"\" id=\""+id+"_4_1"+"\" value=\""+id_4+"\"/>");
var id_5=unit[$("#"+id+"_5").html()];
$("#"+id+"_5").html("次/<select name=\""+id+"_5_1"+"\" id=\""+id+"_5_1"+"\"><option value=\"1\" "+(id_5==1?"selected":"")+">小时</option><option value=\"2\" "+(id_5==2?"selected":"")+">分钟</option><option value=\"3\" "+(id_5==3?"selected":"")+">秒</option></select>");
var id_6=$("#"+id+"_6").html();
$("#"+id+"_6").html("<input style=\"width:70px;text-align:right;\" type=\"text\" name=\""+id+"_6_1"+"\" id=\""+id+"_6_1"+"\" value=\""+id_6+"\"/>");
var id_8=isOpen[$.trim($("#"+id+"_8").html())];
$("#"+id+"_8").html("<input type=\"radio\" name=\""+id+"_8_1"+"\" id=\""+id+"_8_1"+"\" value=\"0\" "+(id_8==0?"checked":"")+">不开启<input type=\"radio\" name=\""+id+"_8_1"+"\" id=\""+id+"_8_1"+"\" value=\"1\" "+(id_8==1?"checked":"")+">开启");
var id_9=isOpen[$.trim($("#"+id+"_9").html())];
$("#"+id+"_9").html("<input type=\"radio\" name=\""+id+"_9_1"+"\" id=\""+id+"_9_1"+"\" value=\"0\" "+(id_9==0?"checked":"")+">不开启<input type=\"radio\" name=\""+id+"_9_1"+"\" id=\""+id+"_9_1"+"\" value=\"1\" "+(id_9==1?"checked":"")+">开启");
$("#"+id+"_10").html("<td id=\""+id+"_10"+"\" align=\"center\"><a href='#' onclick=\"saveRow('"+id+"');\">保存</a><a href='#' onclick=\"saveRow('"+id+"');\">取消</a></td>");
}

function saveRow(id){
if($("#"+id+"_4_1").val()==""){
   alert("请设定频率上限！");
   return;
}
if($("#"+id+"_6_1").val()==""){
   alert("请设定日调用次数上限！");
   return;
}
$("#"+id+"_4").html($("#"+id+"_4_1").val());
$("#"+id+"_5").html(unit[$("#"+id+"_5_1").val()]);
$("#"+id+"_6").html($("#"+id+"_6_1").val());
$("#"+id+"_8").html(isOpen[$('input[name='+(id+"_8_1")+']:checked').val()]);
$("#"+id+"_9").html(isOpen[$('input[name='+(id+"_9_1")+']:checked').val()]);
$("#"+id+"_10").html("<td id=\""+id+"_10"+"\" align=\"center\"><a href='#' onclick=\"updateRow('"+id+"');\">修改</a><a href='#' onclick=\"deleteRow('"+id+"');\">删除</a></td>");
}
function cancel(id){

}
//读取需要保存的详细行数据
function save(){
	var jsonData=[];
	$('#content tr').each(function(){
	  var data={};
	  var _this=$(this);
	  data.apiid=_this.attr("id");
	  data.id=_this.find('td').eq(1).text();
	  data.apiCode=_this.find('td').eq(2).text();
	  data.apiName=_this.find('td').eq(3).text();
	  data.categoryName=_this.find('td').eq(4).text();
	  data.frequency=_this.find('td').eq(5).text();
	  data.frequencyUnit=_this.find('td').eq(6).text();
	  data.callNum=_this.find('td').eq(7).text();
	  data.isFrequency=_this.find('td').eq(9).text();
	  data.isCallNum=_this.find('td').eq(10).text();
	  jsonData.push(data);
	});
	var content="${template.id!''};";
	for(var i=0;i<jsonData.length;i++){
	  content+=jsonData[i].apiid+",";
	  content+=jsonData[i].id+",";
	  content+=jsonData[i].apiName+",";
	  content+=jsonData[i].categoryName+",";
	  content+=jsonData[i].frequency.replace(/,/g, '')+",";
	  content+=unit[jsonData[i].frequencyUnit]+",";
	  content+=jsonData[i].callNum.replace(/,/g, '')+",";
	  content+=jsonData[i].isFrequency+",";
	  content+=jsonData[i].isCallNum+";";
	}
	if($("#templateName").val()==""){
	   alert("模板名称为空，请填写！");
	   return;
	}
	$.ajax({
		type: 'post',
		url: '${BasePath}/merchant/api/template/api_template_config_modify_save.sc',
		data: {templateName:$("#templateName").val(),templateNo:'${template.templateNo!''}',templateDesc:$("#templateDesc").val(),id:content},
		success: function(data, textStatus) {
		    	if("success"==data){
				   ygdg.dialog.alert("保存成功!");
				   gotolink('${BasePath}/merchant/api/template/api_template_config.sc');
				}else{
				   ygdg.dialog.alert("保存失败!");
				}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});
}


//修改资源列表
function updateTemplate1(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchants_authority.sc?id="+id,600,400,"修改资源信息");
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
