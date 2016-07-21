<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
</head>
<style type="text/css">
#select1,#select2 {
	width: 300px;
	height: 440px;
	margin: 0;
	padding: 5px;
}
.sel-div {
	border: 1px solid #ccc;
	width: 300px;
	overflow: hidden;
}
.a-up,.a-down {
	display: block;
	border: 1px solid #ccc;
	text-decoration: none;
	width: 300px;
	background: #F2F2F2;
	height: 25px;
	line-height: 25px;
	cursor: pointer;
	letter-spacing: 2px;
	zoom: 1;
	font-weight: bold;
}
.a-up {
	border-bottom: none;
}
.a-down {
	border-top: none;
}
.a-right,.a-left {
	display: block;
	border: 1px solid #ccc;
	width: 100px;
	height: 25px;
	line-height: 25px;
	background: #F2F2F2;
	margin: 15px;
	cursor: pointer;
}
.a-left {
	margin-top: 20px;
}
</style>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li id="tab1">
				  <span><a href="javascript:changetab1()" class="btn-onselc">授权API</a></span>
				</li>
				<li id="tab2">
				  <span><a href="javascript:changetab2()" class="btn-onselc">API标准</a></span>
				</li>
			</ul>
			<span id="noticeable" style="display: block; width: 180px; line-height: 25px; font-weight: bold; font-size: 14px; text-align: center; background-color: #ff0000; margin-left: 290px; visibility: hidden;"></span>
		</div>
		<div class="modify" id="authorizeApi"> 
			<form action="${BasePath}/openapimgt/apikey/authorizeApi.sc" name="querFrom" id="querFrom" method="post" style="padding:0px;margin:0px;">
				<input type="hidden" id="apiKeyId" name="apiKeyId" value="${apiKeyId}"/>
				<div style="padding: 5px 5px 0 5px;">
					<table cellpadding="0" cellspacing="0">
					    <tbody>
					        <tr>
						         <td valign="top">
						         	<span class="a-up">
						         		<font color="red" style="padding-left: 10px;">未授权API</font>
						         		<input class="searcher" source="select1" type="text" value="可搜索查找API..." style="color: #ccc;"/>
						         	</span>
									<div class="sel-div">
										<select id="select1" size="30" name="select1" multiple="multiple">
											<#if unownedListMap??>
												<#list unownedListMap?keys as key>
													<optgroup label="${key}">
													<#list unownedListMap[key] as item>
								 					<option value="${item.api_id}" title="可使用使鼠标双击进行操作">${item.api_name?default('')}</option>
													</#list>
													</optgroup>
												</#list>
											</#if>
										</select>
									</div>
								</td>
								<td align="center" valign="middle">
									<a id="add" left="select1" right="select2" class="a-left">增加授权&nbsp;&gt;&gt;</a>
									<a id="remove" left="select2" right="select1" class="a-right">&lt;&lt;&nbsp;删除授权</a>
								</td>
								<td>
									<span class="a-up">
										<font color="green" style="padding-left: 10px;">已授权API</font>
										<input class="searcher" source="select2" type="text" value="可搜索查找API..." style="color: #ccc;"/>
									</span>
									<div class="sel-div">
										<select name="select2" size="30" id="select2" multiple="multiple">
											<#if ownedListMap??>
												<#list ownedListMap?keys as key>
													<optgroup label="${key}">
													<#list ownedListMap[key] as item>
														<option value="${item.api_id}" title="可使用使鼠标双击进行操作">${item.api_name?default('')}</option>
													</#list>
													</optgroup>
												</#list>
											</#if>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
									<input  type="button" onclick="authorizeAPI()" class="btn-add-normal" value="授权" />   
									<input type="button" onclick="closewindow();" class="btn-add-normal" value="取消" />
								</td>
							</tr>
					     </tbody>
					</table>
				</div>
			</form>
		</div>
		<div class="modify" id="apiConfig"> 
			<label><span style="color:red;">*</span>API模式：&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<input type="radio" name="config" value="1" onclick="sysconfig();" checked>引用API模板：
			<select id="template" name="template" class="validate[required]" data-rel="请选择API模板">
			  <#list templateList as MonitorTemplate>
			    <option value="${MonitorTemplate.templateNo}">${MonitorTemplate.templateName?default('')}</option>
			  </#list>
			</select>
			<input type="radio" name="config" value="0" onclick="custom();">自定义设置
			<p class="blank20"></p>
			<table cellpadding="0" cellspacing="0" class="list_table">
              <thead>
              <tr>
               <th>序号</th>
               <th>API代码</th>
               <th>API名称</th>
               <th>API分类</th>
               <th colspan=2 style="text-align:center;">频率上限</th>
               <th colspan=2 style="text-align:center;">日调用次数上限</th>
               <th style="text-align:center;width:105px;">是否启用频率控制</th>
               <th style="text-align:center;width:105px;">是否启用日调用次数控制</th>
            </tr>
            </thead>
              <tbody id="content_sys">
                <tr id="content_null">
                  <td colSpan="11" style="text-align:center;">暂无数据！</td>
	            </tr>
	          </tbody>
	          <tbody id="content_custom" style="display:none;">
	          	<#if ownedListMap??>
					<#list ownedList as item>
					<tr id="${item.api_id}">
					  <td>${item_index + 1}</td>
					  <td id="${item.api_id}_0" style="display:none;">null</td>
					  <td id="${item.api_id}_1">${item.api_code?default('')}</td>
					  <td id="${item.api_id}_2">${item.api_name?default('')}</td>
					  <td id="${item.api_id}_3">${item.category_name?default('')}</td>
					  <td id="${item.api_id}_4" align="right"><input style="width:50px;text-align:right;" type="text" name="${item.api_id}_4_1" id="${item.api_id}_4_1" value=""/></td>
					  <td id="${item.api_id}_5" style="width:40px"><select name="${item.api_id}_5_1" id="${item.api_id}_5_1"><option value="1">次/小时</option><option value="2">次/分钟</option><option value="3">次/秒</option></select></td>
					  <td id="${item.api_id}_6" align="right"><input style="width:50px;text-align:right;" type="text" name="${item.api_id}_6_1" id="${item.api_id}_6_1" value=""/></td>
					  <td id="${item.api_id}_7" style="width:30px">次/天</td>
					  <td id="${item.api_id}_8" align="center"><input type="radio" name="${item.api_id}_8_1" id="${item.api_id}_8_1" value="0" checked>不开启<input type="radio" name="${item.api_id}_8_1" id="${item.api_id}_8_1" value="1">开启</td>
					  <td id="${item.api_id}_9" align="center"><input type="radio" name="${item.api_id}_9_1" id="${item.api_id}_9_1" value="0" checked>不开启<input type="radio" name="${item.api_id}_9_1" id="${item.api_id}_9_1" value="1">开启</td>
					</tr>
					</#list>
				</#if>
	          </tbody>
          </table>
          <p class="blank20"></p>
          <label style="font-weight:bold;">AppKey日调用次数上限：<span style="color:red;" id="callSum">0</span>&nbsp;次/天</label>
		  <p class="blank20"></p>
		  <span style="float:right;">
		  <input  type="button" onclick="saveAPPKeyTemplate()" class="btn-add-normal" value="保存" />   
		  <input type="button" onclick="closewindow();" class="btn-add-normal" value="取消" />
		  </span>
		</div> 
		<div class="blank20"></div>
	</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript">
$(document).ready(
   function() {
     getTempDetail($("#template").val());
    $('#template').change(function(){ 
      getTempDetail($(this).children('option:selected').val()); 
    })
    
    if($('input:radio[name="config"]:checked').val()==1){
     sysconfig();
    }else if($('input:radio[name="config"]:checked').val()==0){
     custom();
    }
});
var count=0;
var count_custom=0;
function getTempDetail(templateNo) {
   	$.ajax({
		type: 'post',
		url: '${BasePath}/merchant/api/template/api_template_config_appkey_detail.sc?',
		data: {templateNo:templateNo},
		success: function(data, textStatus) {
		  var details=eval(data);
		  content="";
		  count=0;
		  for(var i=0;i<details.length;i++){
              content+="<tr id=\""+details[i].id+"\" class=\""+(i%2==0?"odd":"odd even")+"\">";
              content+="<td align=\"center\">"+(i+1)+"</td>";
              content+="<td id=\""+details[i].id+"_1"+"\">"+details[i].apiCode+"</td>";
              content+="<td id=\""+details[i].id+"_2"+"\">"+details[i].apiName+"</td>";
              content+="<td id=\""+details[i].id+"_3"+"\">"+details[i].categoryName+"</td>";
		      content+="<td id=\""+details[i].id+"_4"+"\" align=\"right\">"+details[i].frequency+"</td>";
              content+="<td id=\""+details[i].id+"_5"+"\">"+unit[details[i].frequencyUnit]+"</td>";
              content+="<td id=\""+details[i].id+"_6"+"\" align=\"right\">"+details[i].callNum+"</td>";
              content+="<td id=\""+details[i].id+"_7"+"\">"+"次/天"+"</td>";
              content+="<td id=\""+details[i].id+"_8"+"\" align=\"center\">"+isOpen[details[i].isFrequency]+"</td>";
              content+="<td id=\""+details[i].id+"_9"+"\" align=\"center\">"+isOpen[details[i].isCallNum]+"</td>";
		      content+="</tr>";
		      if(details[i].isCallNum=='1'){
		        count=count+details[i].callNum;
		      }
		  }
		  $("#callSum").html(count);
		  $("#content_sys").html(content);
		  $("#content_custom").hide();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});
}
var unit = {"1":"次/小时","2":"次/分钟 ","3":"次/秒","次/小时":"1","次/分钟":"2 ","次/秒":"3"}
var isOpen = {"0":"不开启","1":"开启 ","不开启":"0","开启":"1"}
function sysconfig(){
  $("#template").removeAttr("disabled");
  $("#callSum").html(count);
  $("#content_sys").show();
  $("#content_custom").hide();
}
function custom(){
  $("#template").attr("disabled","disabled");
  $("#callSum").html(count_custom);
  $("#content_sys").hide();
  $("#content_custom").show(); 
  updatePage();
}
//保存模板
function saveAPPKeyTemplate(){
  var val=$('input:radio[name="config"]:checked').val();
  if(val==1){
    $.ajax({
	type: 'post',
	url: '${BasePath}/merchant/api/template/api_template_config_appkey_save.sc',
	data: {appkeyId:'${apiKeyId}',templateNo:$("#template").val()},
	success: function(data, textStatus) {
	  if("success"==data){
		alert("APPKeyId:${apiKeyId}与模板:"+$("#template").val()+"关联成功!");
	  }else{
	    alert("APPKeyId:${apiKeyId}与模板:"+$("#template").val()+"关联失败!");
      }
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
	  ygdg.dialog.alert(XMLHttpRequest.responseText);
	}
	});
  }else{
    save();
  }
}

//读取需要保存的详细行数据
function save(){
	var jsonData=[];
	var blank=false;
	$('#content_custom tr').each(function(){
	  var data={};
	  var _this=$(this);
	  data.apiid=_this.attr("id");
	  data.id=_this.find('td').eq(1).text();
	  data.apiCode=_this.find('td').eq(2).text();
	  data.apiName=_this.find('td').eq(3).text();
	  data.categoryName=_this.find('td').eq(4).text();
	  data.frequency=$("#"+_this.attr("id")+"_4_1").val();
	  if(data.frequency==""){
	     blank=true;
	     ygdg.dialog.alert("请设定频率上限！");
	  }
	  data.frequencyUnit=$("select[name="+_this.attr("id")+"_5_1"+"] option[selected]").val();
	  if(!data.frequencyUnit){
	    data.frequencyUnit=$("#"+_this.attr("id")+"_5_1").val();
	  }
	  data.callNum=$("#"+_this.attr("id")+"_6_1").val();
	  if(data.callNum==""){
	     blank=true;
	     ygdg.dialog.alert("请设定日调用次数上限！");
	  }
	  data.isFrequency=$("input[name='"+_this.attr("id")+"_8_1']:checked").val();
	  data.isCallNum=$("input[name='"+_this.attr("id")+"_9_1']:checked").val();
	  jsonData.push(data);
	});
	if(blank==false){
	var content="${apiKeyId!''};";
	for(var i=0;i<jsonData.length;i++){
	  content+=jsonData[i].apiid+",";
	  content+=jsonData[i].id+",";
	  content+=jsonData[i].apiName+",";
	  content+=jsonData[i].categoryName+",";
	  content+=jsonData[i].frequency.replace(/,/g, '')+",";
	  content+=jsonData[i].frequencyUnit+",";
	  content+=jsonData[i].callNum.replace(/,/g, '')+",";
	  content+=jsonData[i].isFrequency+",";
	  content+=jsonData[i].isCallNum+";";
	}
	$.ajax({
		type: 'post',
		url: '${BasePath}/merchant/api/template/api_template_config__appkey_modify.sc',
		data: {templateName:"${apiKeyId!''}",templateNo:"${apiKeyId!''}",templateDesc:"${apiKeyId!''}",id:content},
		success: function(data, textStatus) {
		    	if("success"==data){
				   ygdg.dialog.alert("保存成功!");
				}else{
				   ygdg.dialog.alert("保存失败!");
				}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});
	}
}
function updatePage(){
     $.ajax({
		type: 'post',
		url: '${BasePath}/merchant/api/template/api_template_config_appkey_detail.sc?',
		data: {templateNo:"${apiKeyId}"},
		success: function(data, textStatus) {
		  var details=eval(data);
		  count_custom=0;
		  for(var i=0;i<details.length;i++){
		    if($("#"+details[i].apiId).length>0){
		      $("#"+details[i].apiId+"_0").html(details[i].id);
		  	  $("#"+details[i].apiId+"_4_1").val(details[i].frequency);
		  	  $("#"+details[i].apiId+"_5_1").val(details[i].frequencyUnit);
		      $("#"+details[i].apiId+"_6_1").val(details[i].callNum);
		      $("input[name='"+details[i].apiId+"_8_1'][value="+details[i].isFrequency+"]").attr("checked",true); 
		      $("input[name='"+details[i].apiId+"_9_1'][value="+details[i].isCallNum+"]").attr("checked",true); 
		      if(details[i].isCallNum=='1'){
		        count_custom=count_custom+details[i].callNum;
		      }
		    }
		  }
		  $("#callSum").html(count_custom);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});
}

//保存
function authorizeAPI() {
	if (confirm('确定更新已授权API列表吗?')) {
		$.ajax({
			type: 'post',
			dataType: 'text',
			data: {
				'apiIds': $('#select2').find('option').map(function(){ return this.value; }).get().join('_'), 
				'apiKeyId': $('#apiKeyId').val(), 
				'rnd': Math.random() 
			},
			url: $('#querFrom').attr('action'),
			success: function(data, textStatus) {
				sendNotification(textStatus == 'success' ? '授权成功' : '授权失败');
				if(textStatus == 'success'){
				  window.location.reload();
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus.toUpperCase() + ': ' + XMLHttpRequest.responseText);
			}
		});
	}
}

function sendNotification(notice) {
	$('#noticeable').css('visibility', 'visible').html(notice);
	window.setTimeout('$("#noticeable").css("visibility", "hidden")', 2000);
}

 //角色分配相关JS
$(function(){
	// 左/右移动控制
	$('#add,#remove').click(function() {
		var leftSelect = $('#' + $(this).attr('left'));
		var rightSelect = $('#' + $(this).attr('right'));
		leftSelect.find('option:selected').each(function(){
			var selectedOption = $(this);
			var selectedOptionGroup = selectedOption.parent();
			var anotherOptionGroup = rightSelect.find('optgroup[label="' + selectedOptionGroup.attr('label') + '"]');
			if (anotherOptionGroup.size() == 0) {
				if (selectedOptionGroup.children().size() <= 1) {
					selectedOptionGroup.remove().appendTo(rightSelect);
				} else {
					rightSelect.append($('<optgroup label="' + selectedOptionGroup.attr('label') + '"></optgroup>').append(selectedOption.remove()));
				}
			} else {
				selectedOption.remove().appendTo(anotherOptionGroup);
			}
			if (selectedOptionGroup.children().size() == 0) {
				selectedOptionGroup.remove();
			}
		});
		$('.searcher').val('').blur();
		leftSelect.find('[background="red"]').css('background', 'transparent');
		rightSelect.find('[background="red"]').css('background', 'transparent');
	});
	// 左双击选项
	$('#select1').dblclick(function(){
		$('#add').click();
	});
	// 右双击选项
	$('#select2').dblclick(function(){
		$('#remove').click();
	});
	// 搜索过滤
	$('.searcher').focus(function(){
		if ($.trim($(this).val()) == $(this).attr('defaultValue')) {
			$(this).val('');
		}
	}).blur(function(){
		if ($.trim($(this).val()) == '') {
			$(this).val($(this).attr('defaultValue'));
			$(this).css('color', '#ccc');
		}
	}).keyup(function(){
		var inputText = $.trim($(this).css('color', '#000').val());
		var source = $('#' + $(this).attr('source'));
		// 处理分组元素
		source.find('option').each(function(){
			var bgColor = (inputText == '' || $(this).text().indexOf(inputText) == -1) ? 'transparent' : 'red';
			$(this).attr('background', bgColor).css('background', bgColor);
		});
		var highlightOptionSize = source.find('[background="red"]').size();
		if (highlightOptionSize > 0) {
			sendNotification('找到' + highlightOptionSize + '个相关的API');
		} else if (inputText != '') {
			sendNotification('抱歉，未找到相关的API');
		}
	});
});

changetab1();
function changetab1() {
$("#tab1").addClass("curr");
$("#tab2").removeClass("curr");
$("#authorizeApi").show();
$("#apiConfig").hide();
}
function changetab2() {
$("#tab2").addClass("curr");
$("#tab1").removeClass("curr");
$("#authorizeApi").hide();
$("#apiConfig").show();
}

</script>