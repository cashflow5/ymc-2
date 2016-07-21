<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/tree/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/tree/css/icon.css" />
<script type="text/javascript"  src="${BasePath}/js/jquery-1.4.2.min.js" ></script>
<script type="text/javascript" src="${BasePath}/js/common/tree/js/jquery.easyui.min.js"></script>

<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/tree.js?lyx20151008"></script>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/resources.js"></script>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/role.js"></script>

<script type="text/javascript" src="${BasePath}/js/common/historyperpage.js"></script>

<form action="u_allotRoleResource.sc" name="roleResources" id="roleResources" method="post">
	<script>document.write("<input type='hidden' name='parentSourcePage' value='"+getThickBoxUrl()+"'/>");</script>
	<input type="button" onclick="submitroleResourcesForm()" value="提交"/>
	<ul id="resourceTree"><script>onloadResourceTree('loadResourceDate.sc?id=${id}');</script></ul>
	<input type="hidden" name="id" value="${id?default('')}" />
	<input type="hidden" name="allCheckResources" id="allCheckResources"/>
</form>