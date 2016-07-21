
var basePath;
$(document).ready(function(){
	 basePath = $("#basepath").val() + '/yitiansystem/commoditymgmt/commodityinfo/colorsystem/';
});

/**
 * 删除色系
 * @param id
 * @return
 */
function d_colorSystem(id){
	if(confirm('是否确定删除')){
		window.location.href= basePath + 'd_ColorSystem.sc?id=' + id;
	}
}

/**
* 显示颜色，并标记颜色状态
*/
function showColorValue(id){
	var params = "id="+id;
	showThickBox("绑定颜色","../../../commoditymgmt/commodityinfo/colorsystem/toBindColorValue.sc?TB_iframe=true&height=400&width=600",false,params);
}

