<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link TYPE="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" TYPE="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" TYPE="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script TYPE="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" TYPE="text/javascript"></script>
</head>
<script TYPE="text/javascript">
</script>
<body>
<DIV class="container">
	<!--工具栏START--> 
	<DIV class="toolbar">
		<DIV class="t-content">
			<DIV class="btn" onclick="addSupplierContactsp('<#if supplierSpId??>${supplierSpId!""}</#if>');">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加联系人</span>
	        	<span class="btn_r"></span>
        	</DIV> 
		</DIV>
	</DIV>
	<DIV class="list_content">
		<DIV class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">联系人列表</a></span>
				</li>
			</ul>
		</DIV>
 <DIV class="modify"> 
     <form ACTION="${BasePath}/yitiansystem/merchants/businessorder/to_linkmanList.sc" NAME="queryForm" id="queryForm" method="post"> 
    	<!--<input type="hidden" value="<#if supplierSpId??>${supplierSpId!""}</#if>" name="supplierSpId" id="supplierSpId"> -->
  			  	<DIV class="wms-top">
                 <p>     
                  	  <label width="80px;">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
                      <input TYPE="text" NAME="contact" id="contact"  value="<#if merchantsVo??&&merchantsVo.contact??>${merchantsVo.contact!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>联系电话：</label>
                      <input TYPE="text" NAME="telePhone" id="telePhone" value="<#if merchantsVo??&&merchantsVo.telePhone??>${merchantsVo.telePhone!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>邮箱地址：</label>
                      <input TYPE="text" NAME="email" id="email" value="<#if merchantsVo??&&merchantsVo.email??>${merchantsVo.email!""}</#if>"/>&nbsp;&nbsp;&nbsp;
               		  <label> <span style="color:red;">&nbsp;*</span>类型：</td><td></label>
	                     <select name="type" id="type">
                           <option value="0">请选择类型</option>
                           <option value="1" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==1>selected</#if>>业务</option>
                           <option value="2" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==2>selected</#if>>售后</option>
                           <option value="6" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==6>selected</#if>>店铺负责人</option>
                           <option value="4" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==4>selected</#if>>财务</option>
                           <option value="5" <#if merchantsVo??&&merchantsVo.type??&&merchantsVo.type==5>selected</#if>>技术</option>
                         </select>
	                     
	                     &nbsp;&nbsp;&nbsp;
	                   <input TYPE="button" VALUE="搜索" onclick="querySupplierContactsp();" class="yt-seach-btn" />
                 </p>
              	</DIV>
              	</form>
                <TABLE cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th width="6%">姓名</th>
                        <th width="5%">类型</th>
                        <th width="8%">电话号码</th>
                        <th width="20%">地址</th>
                        <th width="20%">货品负责人</th>
                        <th width="5%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item['contact']!""}</td>
		                          <td><#if item['type']??&&item['type']==1>业务
		                            <#elseif item['type']??&&item['type']==2>售后
		                            <#elseif item['type']??&&item['type']==3>仓储
		                            <#elseif item['type']??&&item['type']==4>财务
		                            <#elseif item['type']??&&item['type']==5>技术
		                            <#elseif item['type']??&&item['type']==6>店铺负责人
		                            </#if></td>
		                        <td>${item['tele_phone']!""}</td>
		                        <td>${item['address']!""}</td>
		                        <td>${item['yccontact']!""}</td>
		                        <td><a href="#" onclick="updateContactSp('${item['id']!''}','${item['supply_id']!''}');">修改</a></td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">
                        	抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </TABLE>
              </DIV>
               <DIV class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </DIV>
              <DIV class="blank20"></DIV>
          </DIV>
</DIV>
</body>
</html>
<script TYPE="text/javascript">
//根据条件查询联系人信息
function querySupplierContactsp(){
   document.queryForm.method="post";
   document.queryForm.submit();
}

//根据联系人ID加载修改信息页面
function updateContactSp(id,supplyId){
  //var supplierSpId=$("#supplierSpId").val();
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/initial_linkmanList.sc?id="+id+"&supplierSpId="+supplyId,600,500,"修改联系人");
}
//跳转到添加联系人页面
function addSupplierContactsp(supplierId){
  openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_SupplierContactt.sc?supplierId="+supplierId,600,500,"添加联系人");
}
</script>
<script TYPE="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script TYPE="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>