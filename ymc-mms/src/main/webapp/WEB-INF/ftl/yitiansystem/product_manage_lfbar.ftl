<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<#include "yitiansystem-include.ftl" >

<script type="text/javascript" src="${BasePath}/js/common/tree/js/jquery.easyui.min.js"></script>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/tree.js?lyx20151008"></script>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/resources.js"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
</style>
</head>

<body>
	
	<div class="menu" >
	<div class="menu_wrap">
		<ul id="resourceTree">
		<script>
		leftControllerMenu('loadResourceDate.sc?root_struc=${root_struc?default('')}');
		</script>
		</ul>
	</div>
	</div>
<script>
parent.document.getElementById('load-left').style.display='none';
</script>
</body>
</html>

