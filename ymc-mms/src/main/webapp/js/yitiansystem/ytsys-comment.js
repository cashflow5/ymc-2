//修改表单文本框样式
$(document).ready(function(){
$('.contentMain input[type=text]').addClass('input-text');
//$('.wms input[type=button]').attr('style','border:none;height:24px;line-height:24px;');
$('.contentMain select:not(.mult-select)').addClass('input-select');
});


//设置表格隔行换颜色
$(document).ready(function(){
$(".ytweb-table tr").mouseover(function(){
$(this).addClass("over");}).mouseout(function(){
$(this).removeClass("over");})
});

//模态对话框初始化
(function(){
try
{
var d = art.dialog.defaults;
d.lock=true;
d.skin = ['aero'];// 预缓存皮肤，数组第一个为默认皮肤
d.plus = true;
d.height=30;
d.effect =false;	// 是否开启特效
d.showTemp = 0;
}catch(err){}
})();