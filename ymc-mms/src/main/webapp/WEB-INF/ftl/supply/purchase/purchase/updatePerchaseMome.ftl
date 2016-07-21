<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchaseList.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/updateRemark.js"></script>
<title>无标题文档</title>
<script>
	var path="${BasePath}";
	function submits() {
		document.getElementById("u_purchaseForm").submit();
	}
	function cancel() {
		document.getElementById("memo").value= "";
	}
</script>
</head>
<body>
<div class="contentMain" style="width:600px;height:300px;">
      <div class="ytback-tt-1 ytback-tt">
           <span>您当前的位置：</span>采购管理 &gt; 更新备注
       </div>
            
     <div class="content" style="float:left;">
           <div class="mb-btn-fd-bd">
               <div class="mb-btn-fd relative">
                  <span class="btn-extrange absolute">
                      <ul class="onselect">
                          <li class="pl-btn-lfbg"></li>
                          <li class="pl-btn-ctbg"><a  class="btn-onselc">更新备注</a></li>
                          <li class="pl-btn-rtbg"></li>
                      </ul>
                  </span>
              </div>
          </div>
          <div style="padding-top:20px;padding-left:20px;">
	         <form action="addMome.sc" name="u_purchaseForm" id="u_purchaseForm" method="post">
	         	<input type="hidden" name="purchaseId" value="${purchase.id}" />
	         	<div style="float:left;text-align:right;width:10%;">
	         		备注：
	         	</div>
	         	<div style="float:left;border:1px;solid green;text-align:left;width:85%;">
	         		<textarea style="width:380px;height:100px;" name="memo" id="memo">${purchase.memo?default("")}</textarea>
	         	</div>
	         	<div></div>
	         	<div style="text-align:right;padding-top:10px;padding-left:30%;">
		         		<a class="btn-sh" href="#" onclick="submits()" >保&nbsp;存</a>
		         		<a class="btn-sh" href="#" onclick="cancel()" >取&nbsp;消</a>
	         	</div>
	         </form>
         </div>	
     </div>
</div>
</body>
</html>
