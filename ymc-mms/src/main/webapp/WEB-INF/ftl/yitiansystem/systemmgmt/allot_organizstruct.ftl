
<#include "../orderCss.ftl"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/tree/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/tree/css/icon.css" />
<script type="text/javascript" src="${BasePath}/js/common/tree/js/jquery.easyui.min.js"></script>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/tree.js?lyx20151008"></script>
<script>
	function getCheckedStruc(){
		var node = $('#resourceTree').tree('getSelected');
		
		if(node == null){
			alert('请选择一个节点');
			return false;
		}
		
		var organizNo = "";
		var organizName = "";
		
		try{
			organizNo = node.attributes.no;
		}catch(e){
			organizNo ="";
		}
		organizName =node.text ;
		window.top.frames["mbdif"].initOrganizStruct(organizNo,organizName);
		closewindow();
	}
	
	function loadNodeData(){};
</script>
<div style="padding:10px;">
	<input type="button" onclick="getCheckedStruc()" value="确定" class="btn-add-normal"/>
	<br/>
	<br/>
	<ul id="resourceTree">
		<script>onloadResourceTree('loadOrganizStructDate.sc',false);</script>
	</ul>
</div>
<#include "../orderJs.ftl"/>