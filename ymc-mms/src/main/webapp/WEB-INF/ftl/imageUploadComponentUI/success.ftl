<html>
<body>
success!!!
<table border="1">
<tr>
	<td>图片编号</td>
	<td>商品图片（原）路径</td>
	<td>商品图片（大）路径</td>
	<td>商品图片（小）路径</td>
	<td>商品图片（缩略图）路径</td>
</tr>
<#list productImageList as productImage>
<tr>
	<td>${productImage.id}</td>
	<td>${productImage.sourceProductImagePath}</td>
	<td>${productImage.bigProductImagePath}</td>
	<td>${productImage.smallProductImagePath}</td>
	<td>${productImage.thumbnailProductImagePath}</td>
</tr>
</#list>
</table>
</body>
</html>