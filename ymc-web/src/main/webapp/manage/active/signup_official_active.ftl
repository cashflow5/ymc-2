<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商家中心 - 营销中心 - 官方活动报名</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
<body>
<div id="newmain" class="main_bd fr">
    <div class="main_container">
        <div class="normal_box">
            <p class="title site">当前位置：商家中心 &gt; 营销中心 &gt; 官方活动报名 &gt; ${officialActive.activeName!''}</p>
            <div class="tab_panel">
                <div class="notice-box mt15 mb30">
                    <p>活动名称：<strong>${officialActive.activeName!''}</strong></p>
                    <p>活动类型：<#list activeType?keys as key>
										    <#if key?number == officialActive.activeType>	
												${activeType[key]}				
											</#if>
										</#list></p>
                    <p>报名时间： ${officialActive.signUpStartTime?string("yy年MM月dd日 HH:mm:ss")} - ${officialActive.signUpEndTime?string("yy年MM月dd日 HH:mm:ss")}</p>
                    <p>审核时间： ${officialActive.merchantAuditStartTime?string("yy年MM月dd日 HH:mm:ss")} - ${officialActive.merchantAuditEndTime?string("yy年MM月dd日  HH:mm:ss")}</p>
                    <p>活动时间： ${officialActive.startTime?string("yy年MM月dd日 HH:mm:ss")} - ${officialActive.endTime?string("yy年MM月dd日 HH:mm:ss")}</p>
                    <p>报名商品： 品牌：${brandNames} 品类：${catNames}</p>
                    <p>商品数量： 报名活动数量最少为${officialActive.minCommodityCount!'0'},最高为${officialActive.maxCommodityCount!'0'}</p>
                    <#if merchantActiveSignup??>
						<#if merchantActiveSignup.status?number == 1>
							<p>报名状态：<a href="javascript:signupOfficialActive('${officialActive.id}','already');" class="btn-blue-1 xmedium">查看报名</a></p>
						</#if>
						<#if merchantActiveSignup.status?number == 2>
							<p>报名状态：<a href="javascript:void(0);" class="btn-blue-1 xmedium">等待审核</a></p>
						</#if>
						<#if merchantActiveSignup.status?number == 3>
							<p>报名状态：<a href="javascript:void(0);" class="btn-blue-1 xmedium">审核通过</a></p>
						</#if>
						<#if merchantActiveSignup.status?number == 4>
							<p>报名状态：<a href="javascript:void(0);" class="btn-blue-1 xmedium">报名不通过</a></p>
						</#if>  
						<#if merchantActiveSignup.status?number == 5>
							<p>报名状态：<a href="javascript:void(0);" class="btn-blue-1 xmedium">报名结束</a></p>
						</#if>  
					<#else> 
					<p>报名状态：<a href="javascript:signupOfficialActive('${officialActive.id}');" class="btn-blue-1 xmedium">立即报名</a></p>
					</#if>
                </div>
                <ul class="tab">
                    <li onclick="location.href='${BasePath}/active/to_signupOfficialActive.sc?id=${officialActive.id}'" class="curr"><span>活动介绍</span></li>
                    <li onclick="<#if merchantActiveSignup??>location.href='${BasePath}/active/mySignupOfficialActive.sc?id=${officialActive.id}'<#else>javacript:void(0)</#if>"><span>活动报名</span></li>
                </ul>
                <div class="tab_content">
                    <!--信息内容start-->
                    <div class="info-box-content bluebox">
                        ${officialActive.activeDesc!'无'}
                    </div>
                    <!--信息内容end-->
                </div>
            </div>
        </div>
    </div>
</div>

<script>
function signupOfficialActive(activeId,state){
	if(state == 'already'){
		location.href = '${BasePath}/active/mySignupOfficialActive.sc?id='+activeId;
		return;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url :'${BasePath}/active/signupOfficialActive.sc?id='+activeId,
		success : function(data) {
			if(data.code == '200'){
				ygdg.dialog.alert("报名成功，请前往导入商品",function(){
					location.href = '${BasePath}/active/mySignupOfficialActive.sc?id='+activeId;
				});
			}else if(data.code == '300'){
				ygdg.dialog.alert("已报名，请勿重复报名",function(){
					location.href = '${BasePath}/active/mySignupOfficialActive.sc?id='+activeId;
				});
			}else{
				ygdg.dialog.alert("报名失败，请重试！");
			}
		}
	});
}
</script>
</body>
</html>