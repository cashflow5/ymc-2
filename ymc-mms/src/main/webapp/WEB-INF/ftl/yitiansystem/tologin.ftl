<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE></TITLE>
 </HEAD>
 <BODY>
  	<form action="toIndex.sc" name="toIndexForm" method="post" target="_top">
  		<#if rootStruc ??>
 		<input type ="hidden" name = "root_struc" value = "${rootStruc?default("")}"/>
 		</#if>
  	</form>
 </BODY>
 
 <script type="text/javascript">
	document.toIndexForm.submit();
</script>
</HTML>
