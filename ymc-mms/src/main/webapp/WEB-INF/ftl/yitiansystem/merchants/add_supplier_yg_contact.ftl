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
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">保存货品负责人信息</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="" name="queryForm" id="subForm" method="post"> 
                <table cellpadding="0" cellspacing="0" class="list_table">
                  		<input type="hidden" name="userId" id="userId" value="<#if contact??>${contact.userId!""}</#if>">
                        <tr>
                        	<td style="text-align:right;">
                          		<label> <span style="color:red;">&nbsp;*</span>姓名：</label>
                          	</td>
                          	<td>
                          		<input type="text" name="userName" id="userName" value="<#if contact??>${contact.userName!""}</#if>"/>
                        		&nbsp;&nbsp;<span style="color:red;" id="userNameError" class="error" name="userNameError"></span>
                        	</td> 
                        </tr>
                        <tr>
                        	<td style="text-align:right;">
                          		<label> <span style="color:red;">&nbsp;*</span>直接领导：</label>
                          	</td>
                          	<td>
                          		<input type="text" name="leads" id="leads" value="<#if contact??>${contact.leads!""}</#if>"/>
                          		<span style="color:#D2D2D6">多个上级填写请用英文半角逗号隔开</span>
                        		&nbsp;&nbsp;<span style="color:red;" id="leadsError" class="error" name="userNameError"></span>
                        	</td> 
                        </tr>
                        <tr>
                        	<td style="text-align:right;">
                          		<label>电话：</label>
                          	</td>
                          	<td>
                          		<input type="text" name="telePhone" id="telePhone" value="<#if contact??>${contact.telePhone!""}</#if>"/>
                        		&nbsp;&nbsp;<span style="color:red;" id="telePhoneError" class="error" name="telePhoneError"></span>
                        	</td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;">
                          		<label>手机：</label>
                          	</td>
                          	<td><input type="text" maxlength="11" name="mobilePhone" id="mobilePhone" value="<#if contact??>${contact.mobilePhone!""}</#if>"/>
                        		&nbsp;&nbsp;<span style="color:red;" id="mobilePhoneError" class="error" name="mobilePhoneError"></span>
                       		</td>
                       </tr>
                        <tr>
                        	<td style="text-align:right;">
                          		<label><span style="color:red;">&nbsp;*</span>电子邮箱：</label>
                          	</td>
                          	<td>
                          		<input type="text" name="email" id="email" value="<#if contact??>${contact.email!""}</#if>"/>
                           		&nbsp;&nbsp;<span style="color:red;" id="emailError" class="error" name="emailError"></span>
                           	</td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;">
                          		<label>备注：</label>
                          	</td>
                          	<td>
	                          	<input type="text"  style="width:250px;" name="remark" id="remark" value="<#if contact??>${contact.remark!""}</#if>"/>
	                        	&nbsp;&nbsp;<span style="color:red;" class="error" id="remarkError" name="remarkError"></span>
                        	</td>
                        </tr>
                        <tr>
                        	<td></td>
                        	<td>
                         		<input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return addLinkmanList();">
                         		<span style="color:red;" class="error" id="sumitInfo"></span>
                        	</td>
                       </tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//选择供应商
function tosupper(){
  openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp.sc',600,500,'选择供应商');
}
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};
String.prototype.realLength = function() {
	var val = this;
	var len = 0;
	var arrayVal = val.split("");
	for (var i = 0; i < arrayVal.length; i++) {
		if (arrayVal[i].match(/[^\x00-\xff]/ig) != null) // 全角
		len += 2;
		else len += 1;
	}
	return len;
};
//添加联系人信息
function addLinkmanList(){
	var re = /[^\u4e00-\u9fa5]/;  
	var checkEmail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;// 验证email
	var checkNumber = /^[0-9]+$/;         //只能是数字，字母
   $(".error").text("");
   var userName = $("#userName").val();
   var leads = $("#leads").val();
   var telePhone = $("#telePhone").val();
   var mobilePhone = $("#mobilePhone").val();
   var email = $("#email").val();
   var remark = $("#remark").val();
   if(userName.trim()==""){
   		$("#userNameError").html("姓名不能为空");
   		return;
   }
  if(re.test(userName.trim())){
   		$("#userNameError").html("姓名只能是中文");
   		return;
   }
   if(userName.realLength()>32){
   		$("#userNameError").html("姓名不能超过32个字符");
   		return;
   }
   
   if(leads.trim()==""){
   		$("#leadsError").html("直接上级不能为空");
   		return;
   }
   if(leads.realLength()>300){
   		$("#leadsError").html("直接上级不能超过300个字符");
   		return;
   }
   
   if(telePhone.trim()!=""&&telePhone.realLength()>32){
   		$("#telePhoneError").html("电话不能超过32个字符");
   		return;
   }
   if(telePhone.trim()!=""&&!checkNumber.test(telePhone.trim())){
   		$("#telePhoneError").html("电话只能是数字");
   		return;
   }
   if(mobilePhone.trim()!=""&&mobilePhone.realLength()>32){
   		$("#mobilePhoneError").html("手机不能超过32个字符");
   		return;
   }
   if(mobilePhone.trim()!=""&&!checkNumber.test(mobilePhone.trim())){
   		$("#mobilePhoneError").html("手机只能是数字");
   		return;
   }
   if(email.trim()==""){
   		$("#emailError").html("邮箱不能为空");
   		return;
   }
   if(email.trim()!=""&&email.realLength()>32){
   		$("#emailError").html("邮箱不能超过32个字符");
   		return;
   }
   if(email.trim()!=""&&!checkEmail.test(email.trim())){
   		$("#emailError").html("邮箱不符合规则");
   		return;
   }
   if(remark.trim()!=""&&remark.realLength()>200){
   		$("#remarkError").html("备注不能超过200个字符");
   		return;
   }
   $("#btn").attr("disabled","disabled");
   $("#sumitInfo").text("正在保存信息......");
   $.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#subForm").serialize(),
		url : "${BasePath}/yitiansystem/merchants/supplierContact/saveContact.sc",// 请求的action路径
		success : function(data) {
			$("#btn").attr("disabled",false);
			if (data.resultCode == "200") {
				$("#sumitInfo").text("保存成功");
				closewindow();
			}else{
				$("#sumitInfo").text(data.msg);
			}
		},error:function(data){
			$("#btn").attr("disabled",false);
			$("#sumitInfo").text("保存失败");
		}
	});
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
