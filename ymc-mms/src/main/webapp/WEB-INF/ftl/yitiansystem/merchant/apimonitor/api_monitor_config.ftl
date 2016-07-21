<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.7.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<title>API监控参数设置</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" id="btnDiv" onclick="saveConfig();">
				<span class="btn_l"></span>
	        	<b class="ico_btn save"></b>
	        	<span class="btn_txt" id="btn_save">保存</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>

  <div class="list_content">
    <div class="modify"> 
     <form action="${BasePath}/merchant/api/monitor/api_monitor_config_modify.sc" name="queryForm" id="queryForm" method="post">
              	<div class="add_detail_box">
              	<h3>模板基本信息</h3>
              	    <p>
						<span>
						  <span style="color:red;">*</span>流量锁定：单接口日调用次数超限
						  <input style="width:35px;" type="text" id="dataFlowRate" name="dataFlowRate" value="<#if vo??&&vo.dataFlowRate??>${vo.dataFlowRate}</#if>">
						  %，当天内锁定接口；
						      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						      总接口调用次数达：
						  <input style="width:50px;" type="text" id="appKeyFlowMax" name="appKeyFlowMax" value="${appKeyFlowMax!'' }">
						      次，当天内锁定接口；
						      达到：
						  <input style="width:50px;" type="text" id="appKeyFlowWarn" name="appKeyFlowWarn" value="${appKeyFlowWarn!'' }">
						        次，邮件预警；
						</span>
					</p>
					<p>
						<span>
                          <span style="color:red;">*</span>频率锁定：单接口频率超限
						  <input style="width:35px;" type="text" id="frequencyRate" name="frequencyRate" value="<#if vo??&&vo.frequencyRate??>${vo.frequencyRate}</#if>">
                          %，锁定接口
                          <input style="width:35px;" type="text" id="frequencyOutLockTime" name="frequencyOutLockTime" value="<#if vo??&&vo.frequencyOutLockTime??>${vo.frequencyOutLockTime}</#if>">
                                                                    小时。
						</span>
					</p>
				<h3>预警参数</h3>
					<p>
						<span>
                          <span style="color:red;">*</span>单接口日调用次数预警：实际日调用次数/日调用次数上限超过
						  <input style="width:35px;" type="text" id="simpleImplOneDayRate" name="simpleImplOneDayRate" value="<#if vo??&&vo.simpleImplOneDayRate??>${vo.simpleImplOneDayRate}</#if>">
                          %；
						</span>
					</p>
					
					<p>
						<span>
                          <span style="color:red;">*</span>单接口频率预警：实际频率/频率上限超过
						  <input style="width:35px;" type="text" id="simpleImplFrequencyRate" name="simpleImplFrequencyRate" value="<#if vo??&&vo.simpleImplFrequencyRate??>${vo.simpleImplFrequencyRate}</#if>">
                          %；
						</span>
					</p>
					
					<p>
						<span>
                         <span style="color:red;">*</span>调用成功率预警：调用成功率低于
						  <input style="width:35px;" type="text" id="successRate" name="successRate" value="<#if vo??&&vo.successRate??>${vo.successRate}</#if>">
                          %；
						</span>
					</p>
					
					<p>
						<span>
                          <span style="color:red;">*</span>AppKey日调用次数预警：实际日调用次数/日调用次数上限超过
						  <input style="width:35px;" type="text" id="appKeyCallFrequencyRate" name="appKeyCallFrequencyRate" value="<#if vo??&&vo.appKeyCallFrequencyRate??>${vo.appKeyCallFrequencyRate}</#if>">
                          %。
						</span>
					</p>
				<h3>可疑IP参数</h3>
					<p>
						<span>
                          <span style="color:red;">*</span>IP使用无效AppKey发送请求累计达
						  <input style="width:35px;" type="text" id="invalidAppKeyRequest" name="invalidAppKeyRequest" value="<#if vo??&&vo.invalidAppKeyRequest??>${vo.invalidAppKeyRequest}</#if>">
                                                                     次以上，则纳入可疑IP范围。
						</span>
					</p>
					<div class="blank20"></div>
				</div>
              	</form>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
if("${status}"=="true"){
  alert("保存成功！");
}else if("${status}"=="false"){
  alert("保存失败，请稍后再试！");
}
function saveConfig(){
   var appKeyFlowMax = $("#appKeyFlowMax").val(); 
   var appKeyFlowWarn = $("#appKeyFlowWarn").val(); 
   if(appKeyFlowMax == ""){
	   alert("总接口调用次数不能为空!");
	   return false;
   }
   if(appKeyFlowWarn == "" ){
	   alert("邮件预警调用次数不能为负数!");
	   return false;
   }
   if(appKeyFlowMax <0 ){
	   alert("总接口调用次数不能为负数!");
	   return false;
   }
   if(appKeyFlowWarn <0 ){
	   alert("邮件预警调用次数不能为负数!");
	   return false;
   }
   document.queryForm.submit();
}
</script>
