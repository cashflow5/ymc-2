<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">
<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchaseList.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/updateRemark.js"></script>
<title>无标题文档</title>
<script>
	var path="${BasePath}";
</script>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li >
				  <span><a href="">更新备注</a></span>
				</li>
			</ul>
		</div>
 <div class="modify">
	         <form action="${BasePath}/supply/supplier/PerchaseOrder/u_remark.sc" name="u_purchaseForm" id="u_purchaseForm" method="post">
	         	<div style="float:left;text-align:right;width:10%;">
	         		备注：
	         		<div style="float:left;border:1px;solid green;text-align:left;width:85%;">
	         		<textarea style="width:380px;height:100px;" name="remark" id="remark"></textarea>
	         	</div>
	         	</div>
	         	<div style="padding-top:10px;">
	         		<input type="hidden" name="prechaseId" id="prechaseId" value="${reVo.prechaseId?default(-1)}"/>
	         		<input type="hidden" name="perchaseDetialId" id="perchaseDetialId"  value="${reVo.perchaseDetialId?default(-1)}"/>
	         		<input type="hidden" name="currentPage" id="currentPage" value="${reVo.currentPage?default(-1)}"/>
	         	</div>
	         	<div></div>
	         	<div style="text-align:right;padding-top:150px;padding-left:42%;">
		         		<a class="btn-sh" href="#" onclick="submits()" >保&nbsp;存</a>
		         		<a class="btn-sh" href="#" onclick="cancel()" >取&nbsp;消</a>
	         	</div>
	         </form>
         </div>	
     </div>
</div>

</body>
</html>
