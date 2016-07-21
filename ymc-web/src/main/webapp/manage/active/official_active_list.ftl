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
            <p class="title site">当前位置：商家中心 &gt; 营销中心 &gt; 官方活动报名</p>
            <div class="tab_panel">
                <ul class="tab">
                    <li onclick="location.href='${BasePath}/active/officialActiveList.sc'" class="curr"><span>可参与报名</span></li>
                    <li onclick="location.href='${BasePath}/active/myOfficialActiveList.sc'"><span>我的报名</span></li>
                </ul>
                <div class="tab_content">
                    <!--搜索start-->
                    <div class="search_box">
                        <form action="${BasePath}/active/officialActiveList.sc" id="queryVoform" name="queryVoform" method="post">
                            <p>
                                <span>
                                    <label style="width:110px;">活动名称：</label>
                                    <input type="text" name="activeName" id="activeName" class="inputtxt" value="${params.activeName!''}" />
                                </span>
                                <span>
                                    <label style="width:110px;">报名开始时间：</label>
                                    <select style="width:130px;" name="signupBeginTime">
                                        <option value="30" <#if params.signupBeginTime??&&(params.signupBeginTime?number == 30)>selected</#if>>30天内</option>
                                        <option value="10" <#if params.signupBeginTime??&&(params.signupBeginTime?number == 10)>selected</#if>>10天内</option>
                                        <option value="3" <#if params.signupBeginTime??&&(params.signupBeginTime?number == 3)>selected</#if>>3天内</option>
                                    </select>
                                </span>
                                <span>
                                    <label style="width:110px;">活动类型：</label>
                                    <select style="width:130px;" name="activeType">
                                    	<option value="">请选择</option>
                                    	<#list activeType?keys as key>
										    <option value="${key}" <#if params.activeType??&&params.activeType == key>selected</#if>>${activeType[key]}</option>
										</#list>
                                    </select>
                                </span>
                                <span>
                                    <a class="button" id="mySubmit" onclick="mysubmit()"><span>搜索</span></a>
                                </span>
                            </p>
                        </form>
                    </div>
                    <!--搜索end-->
                    <!--列表start-->
                    <table class="list_table">
                        <thead>
                            <tr>
                                <th>活动名称</th>
                                <th>活动类型</th>
                                <th>报名时间</th>
                                <th>活动时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
							<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
							<#list pageFinder.data as item>
                            <tr>
                                <td><a href="#">${item.activeName!''}</a></td>
                                <td><#list activeType?keys as key>
										    <#if key?number == item.activeType>	
												${activeType[key]}				
											</#if>
										</#list></td>
                                <td>${item.signUpStartTime?string("yy年MM月dd日")} - ${item.signUpEndTime?string("yy年MM月dd日")}</td>
                                <td>${item.startTime?string("yy年MM月dd日")} - ${item.endTime?string("yy年MM月dd日")}</td>
                                <td><a href="${BasePath}/active/to_signupOfficialActive.sc?id=${item.id}">立即报名</a></td>
                            </tr>                            
                            </#list>
							<#else>
								<tr><td colSpan="12" style="text-align:center">抱歉，没有您要找的数据</td></tr>
							</#if>
                        </tbody>
                    </table>
                    <!--列表end-->
	            	<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
                    <div class="gray-box">
                    <div class="fr">
                    <#if pageFinder ??>
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryVoform"/>
					</#if>
                    </div>
                    </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
function mysubmit(){
	$("#queryVoform").submit();
}
</script>
</body>
</html>