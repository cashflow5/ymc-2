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
				<li class="curr">
				  <span><a href="#" class="btn-onselc">绑定持有者</a></span>
				</li>
			</ul>
			<span id="noticeable" style="display: block; width: 180px; line-height: 25px; font-weight: bold; font-size: 14px; text-align: center; background-color: #ff0000; margin-left: 290px; visibility: hidden;"></span>
		</div>
		<div class="modify"> 
			<form action="${BasePath}/openapimgt/apikey/bindingApiKey.sc" name="querFrom" id="querFrom" method="post" style="padding:0px;margin:0px;">
				<input type="hidden" id="userId" name="userId" value="${userId}"/>
				<div style="padding: 5px 5px 0 5px;">
					<table cellpadding="0" cellspacing="0">
					    <tbody>
					        <tr>
						         <td valign="top">
						         	<span class="a-up">
						         		<font color="red" style="padding-left: 10px;">未绑定持有者</font>
						         		<input class="searcher" source="select1" type="text" value="可搜索查找消费者..." style="color: #ccc;"/>
						         	</span>
									<div class="sel-div">
										<select id="select1" size="30" name="select1" multiple="multiple">
											<optgroup label="招商">
											<#if listOut??>
													<#list listOut as item>
															<option value="${item.supplierCode}"  title="可使用使鼠标双击进行操作">${item.supplier}（${item.supplierCode}）</option>
													</#list>
											</#if>
											</optgroup>
										</select>
									</div>
								</td>
								<td align="center" valign="middle">
									<a id="add" left="select1" right="select2" class="a-left">增加绑定&nbsp;&gt;&gt;</a>
									<a id="remove" left="select2" right="select1" class="a-right">&lt;&lt;&nbsp;删除绑定</a>
								</td>
								<td>
									<span class="a-up">
										<font color="green" style="padding-left: 10px;">已绑定持有者</font>
										<input class="searcher" source="select2" type="text" value="可搜索查找消费者..." style="color: #ccc;"/>
									</span>
									<div class="sel-div">
										<select name="select2" size="30" id="select2" multiple="multiple">
											<optgroup label="招商">
											<#if listOut??>
													<#list listIn as item>
															<option value="${item.supplierCode}"  title="可使用使鼠标双击进行操作">${item.supplier}（${item.supplierCode}）</option>
													</#list>
											</#if>
											</optgroup>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
									<input  type="button" onclick="bindingSupplier()" id="bindbtn" class="btn-add-normal" value="绑定" />   
									<input type="button" onclick="closewindow();" class="btn-add-normal" value="取消" />
								</td>
							</tr>
					     </tbody>
					</table>
				</div>
			</form>
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
//保存
function bindingSupplier() {
	if (confirm('确定更新已绑定持有者列表吗?')) {
		var options = $('#select2').find('option');
		var supplierCode = [];
		options.each(function(){
			supplierCode.push($(this).val());
		}); 
		
		$("#bindbtn").attr("disabled",true);
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data :{
				supplierCode:supplierCode.join(","),
				userId:"${userId}"
			},
			url : "${BasePath}/yitiansystem/merchants/supplierContact/bindContact.sc",// 请求的action路径
			success : function(data) {
				$("#bindbtn").attr("disabled",false);
				if (data.resultCode == "200") {
					$("#sumitInfo").text("绑定成功");
					closewindow();
				}else{
					$("#sumitInfo").text(data.msg);
				}
			},error:function(data){
				$("#bindbtn").attr("disabled",false);
				$("#sumitInfo").text("绑定失败");
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
			sendNotification('找到' + highlightOptionSize + '个相关的持有者');
		} else if (inputText != '') {
			sendNotification('抱歉，未找到相关的持有者');
		}
	});
});
</script>