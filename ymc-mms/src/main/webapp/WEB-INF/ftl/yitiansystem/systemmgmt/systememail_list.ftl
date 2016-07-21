<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" />
<meta name="Description" content="" />
<title>B网络营销系统-系统管理-系统用户管理</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/systemEmail.js"></script>
<script type="text/javascript">
	//选择用户组织机构
	function toShowDetail(id){
		var param = "id="+id;
		openwindow("../../systemmgmt/systememail/querySystemEmailById.sc?"+param,850,650,'邮件明细');
	}
</script>

</head>

<body>
<div class="container" id="main_body">
	<div class="toolbar">
		<div class="t-content">
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr' ><span>邮件发送列表</span></li>
			</ul>
		</div>
		<div class="modify">
			<form action="querySystemEmailList.sc" name="queryForm" id="queryForm" method="post">
				<div>
			
				<span>收件地址：</span>
				<input name="receive_email" class="blakn-sl" value="${systemEmail.receive_email?default('')}"/>
				<input type="submit" class="btn-add-normal" value="搜索" />
				</div>
			</form>
			<table cellpadding="0" cellspacing="0" class="list_table">
            <thead>
            <tr>
	            <th>收件地址 </th>
	            <th>主题</th>
	            <th>邮件类型</th>
	            <th>发送时间 </th>
	            <th>发送状态</th>
	            <th>操作</th>
            </tr>              
            </thead>
            <tbody>
            
            
              <#if pageFinder?? && (pageFinder.data)?? >
	      		<#list pageFinder.result as item>		
	      		<tr id='Tr${item.id}'>
                    <td>
						${item.receive_email?default("")}                 
                    </td>
                    <td>
                    	${item.subject?default("")}
                    </td>
                    <td>
                    	<#if item.type == "0" >
                    		会员找回密码
                     	<#elseif item.type == "1" >
                     		订单发货
                     	<#elseif item.type == "2" >
                     		订单创建
                     	<#elseif item.type == "3" >
                     		订单付款
                     	<#elseif item.type == "4" >
                     		订单退货
                     	<#elseif item.type == "5" >
                     		订单退款
                     	<#elseif item.type == "6" >
                     		订单到货
                     	<#elseif item.type == "7" >
                     		会员注册
                     	<#elseif item.type == "8" >
                     		会员更改密码
                     	<#elseif item.type == "9" >
                     		订单作废
                     	<#elseif item.type == "10" >
                     		购物体验评价邀请
                 		<#elseif item.type == "11" >
                     		促销
                     	<#elseif item.type == "12" >
                     		货到付款
                     	<#else>
                     		其他
						</#if>                 
                    </td>
                    <td>
                    	${item.sendTime?default("")}
                    </td>
                     <td>
                     	<#if item.state == "0" >
                     		失败
                     	<#elseif item.state == "1" >
                     		成功
						</#if>                     	
                    </td>
                    
                    </td>
                    <td class="td0" style="text-align:left;">
                    	<a href="javascript:toShowDetail('${item.id}');" target="mbdif">查看内容</a>
                    	<a href="javascript:removeSystemEmail('${item.id}');" >删除</a>
                    </td>
                </tr>
	      		</#list>	
	      	<#else>
	      		<tr><td colspan="6"><div class="yt-tb-list-no">暂无内容</div></td></tr>
			</#if>
            </tbody>
            </table> 
		</div>
		<div class="bottom clearfix">
			<!-- 翻页标签 -->
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="queryForm" />
			
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 
</body>
</html>