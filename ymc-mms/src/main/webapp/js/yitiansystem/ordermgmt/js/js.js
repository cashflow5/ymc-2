//修改表单文本框样式
$(document).ready(function(){
//$('.wms-div input[type=text]').attr('style','border:1px solid #bebebe; height:22px; line-height:22px;');
//$('.wms-top input[type=text]').attr('style','border:1px solid #bebebe; height:20px; line-height:20px;');
$('.del-area input[type=radio]').attr('style','border:none;');
$('.del-area input[type=button]').attr('style','border:none;height:24px;line-height:24px;');
$('.wms-div input[type=radio]').attr('style','border:none;');
$(".wms-div select:not(.mult-select)").attr('style','height:20px; line-height:20px;border:1px solid #bebebe;');
});

//模态对话框初始化
(function(){
try
{
var d = art.dialog.defaults;
d.lock=true;
d.skin = ['aero'];// 预缓存皮肤，数组第一个为默认皮肤
d.plus = true;
d.lock=true;
d.height=30;
d.effect =false;	// 是否开启特效
d.showTemp = 0;
}catch(err){}
})();

//设置表格隔行换颜色
$(document).ready(function(){
$(".ytweb-table tr").mouseover(function(){
$(this).addClass("over");}).mouseout(function(){
$(this).removeClass("over");})
});

//show notice box
$(document).ready(function(){
						   
	$(".store-content>div:not(:first)").hide();     
	$(".tab a").click(function(){						
		$(".tab a").removeClass('selected');
		$(this).addClass('selected');	
		var index = $(".tab a").index(this);
		$(".store-content>div").eq(index).show().siblings().hide();
		$(".store-content>div").hide();
		$(".store-content>div").eq(index).fadeIn('');
	});
});

