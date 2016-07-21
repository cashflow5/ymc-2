<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<title>网络营销系统-系统管理-系统配置基本信息</title>

<script type="text/javascript">
  	var config={form:"systemConfigForm",submit:submitForm,
 	fields:[
		{name:'configName',allownull:false,regExp:"name",defaultMsg:'请输入配置名称',focusMsg:'请输入配置名称',errorMsg:'配置名称不正确',rightMsg:'',msgTip:'configNameTip'},
		{name:'key',allownull:false,regExp:/^\s*(\S+)\s*$/,defaultMsg:'请输入配置键值',focusMsg:'请输入配置键值',errorMsg:'配置键值不正确',rightMsg:'',msgTip:'keyTip'}
	]}

  	Tool.onReady(function(){
		var f = new Fv(config);
		f.register();
	});

  	/**
  	 * 提交表单
  	 */
  	function submitForm(result){

  		if(result){

  			if(!checkLength()) return false;

	  		window.top.TB_remove();
	  		return true;
  		}
  		return false;
  	}

  	function checkLength(){
  		var obj = $("#remark").val();
  		if(obj.length> 100){
  			alert( '备注最多100字！ ');
  			return false;
  		}
  		return true;
  	}
</script>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr' ><span>添加系统配置</span></li>
			</ul>
		</div>
		<form action="c_addSystemConfig.sc" method="post" target="mbdif"  name="systemConfigForm" id="systemConfigForm" style="margin:0px;padding:0p;">
			<div class="modify">
			<table class="com_modi_table">
				<tr>
					<th>
						<span class="star">*</span>
						<label>配置名称：</label>
					</th>
					<td>
						<input name="configName" type="text" id="configName" size="30"  maxLength="40"/>&nbsp;&nbsp;
       			  		<span id="configNameTip"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span class="star">*</span>
						<label>配置键：</label>
					</th>
					<td>
						<input name="key" type="text" id="key" size="30"  maxLength="150"/>&nbsp;&nbsp;
       			  		<span id="keyTip"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span class="star">*</span>
						<label>配置值：</label>
					</th>
					<td>
						<input name="value" type="text" id="value" size="30"  maxLength="500"/>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						<label>备注：</label>
					</th>
					<td>
						<textarea id="remark" name="remark" style="width:180px;height:120px;"  ></textarea>
					</td>
				</tr>
				<tr>
					<th></th>
					<td>
						<input type="submit" class="btn-save" value="保存" />
						<input type="button" class="btn-back" value="取消" onclick="javascript:window.closewindow()"/>
					</td>
				</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 
</body>
<script>
	$("#configName").focus();
</script>
</html>
