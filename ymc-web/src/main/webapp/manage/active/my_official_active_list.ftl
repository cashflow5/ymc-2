<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商家中心 - 营销中心 - 官方活动报名</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
</head>
<body>

<div id="newmain" class="main_bd fr">
    <div class="main_container">
        <div class="normal_box">
            <p class="title site">当前位置：商家中心 &gt; 营销中心 &gt; 官方活动报名</p>
            <div class="tab_panel">
                <ul class="tab">
                    <li onclick="location.href='${BasePath}/active/officialActiveList.sc'"><span>可参与报名</span></li>
                    <li onclick="location.href='${BasePath}/active/myOfficialActiveList.sc'" class="curr"><span>我的报名</span></li>
                </ul>
                <div class="tab_content">
                    <!--搜索start-->
                    <div class="search_box">
                        <form action="${BasePath}/active/myOfficialActiveList.sc" id="queryVoform" name="queryVoform" method="post">
                            <p>
                                <span>
                                    <label>活动名称：</label>
                                    <input type="text" name="activeName" id="activeName" class="inputtxt" value="${params.activeName!''}"/>
                                </span>
                                <span>
                                    <label>活动类型：</label>
                                     <select style="width:100px;" name="activeType">
                                    	<option value="0">请选择</option> 
                                    	<#list activeType?keys as key>
										    <option value="${key}" <#if params.activeType??&&params.activeType == key>selected</#if>>${activeType[key]}</option>
										</#list>
                                    </select>
                                </span>
                                <span>
                                    <label>报名状态：</label>
                                     <select style="width:130px;" name="status">
                                    	<option value="0">请选择</option>
                                    	<option value="1" <#if params.status??&&(params.status?number == 1)>selected</#if>>新建</option>
                                        <option value="2" <#if params.status??&&(params.status?number == 2)>selected</#if>>等待审核</option>
                                        <option value="3" <#if params.status??&&(params.status?number == 3)>selected</#if>>审核通过</option>
                                        <option value="4" <#if params.status??&&(params.status?number == 4)>selected</#if>>审核不通过</option>
                                        <option value="5" <#if params.status??&&(params.status?number == 5)>selected</#if>>报名结束</option>
                                    </select>
                                </span>
                                <span>
                                    <a class="button" id="mySubmit" onclick="javascript:mysubmit()"><span>搜索</span></a>
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
                                <th>报名状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
							<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
							<#list pageFinder.data as item>
                            <tr>
                                <td><a href="#">${item.activeName}</a></td>
                                <td><#list activeType?keys as key> 
										    <#if key?number == item.activeType>	   
												${activeType[key]}				
											</#if>
										</#list></td> 
                                <td>${item.signUpStartTime?string("yy年MM月dd日")} - ${item.signUpEndTime?string("yy年MM月dd日")}</td>
                                <td>${item.startTime?string("yy年MM月dd日")} - ${item.endTime?string("yy年MM月dd日")}</td>
                                <td>
									<#if item.status?number == 1>新建</#if>
									<#if item.status?number == 2>审核中</#if>
									<#if item.status?number == 3>审核通过</#if>  
									<#if item.status?number == 4>审核不通过</#if>  
									<#if item.status?number == 5>报名已结束</#if>  
								</td>
                                <td>
                                <#if item.status?number != 1 && item.status?number != 4> 
                                <a href="${BasePath}/active/mySignupOfficialActive.sc?id=${item.activeId}">查看</a> 
                                </#if>
								<#if item.status?number == 1 || item.status?number == 4 > 
                                <a href="${BasePath}/active/mySignupOfficialActive.sc?id=${item.activeId}">编辑</a> 
                                </#if>
                                <#if item.status?number == 2> 
                                <a href="javascript:updateOfficialActiveSignup('${item.id}')">撤消报名</a>                                
							 	</#if>
								</td>
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

function updateOfficialActiveSignup(signupId){
	ygdg.dialog.confirm('确定撤消报名吗?', function() {
		$.ajax({
			type: 'get',
			url: '/active/updateOfficialActiveSignup.sc?type=2&signupId='+signupId, 
			dataType: 'json',
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data.msg) == 'Success') {
					ygdg.dialog.alert("操作成功！",function(){
						mysubmit();
					});
				} else if($.trim(data.msg) == 'Failure'){
					// this.error(jqXHR, textStatus, data);
					ygdg.dialog.alert("操作失败，请重试！");
				} else{
					ygdg.dialog.alert(data.msg);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				ygdg.dialog.alert('操作失败，请重试！');
			}
		});
	});
}
</script>
</body>
</html>