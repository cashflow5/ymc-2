$('.jsLoginForm .ipt_txt').placeholder({
	isUseSpan:true
});

/*记住账号复选框*/
$(".jsRemBox").click(function(){
	var checked=$("#remAccnt").is(":checked");
	var icob=$(".jsRemBox .ico_chkbox");
	if(checked){
		icob.addClass("ico_chkbox_on");
	}else{
		icob.removeClass("ico_chkbox_on");
	}
});

/*提交按钮鼠标经过替换背景*/
$(".jsLoginSmt").hover(function(){
	$(this).addClass("smt_brwn_hover");
},function(){
	$(this).removeClass("smt_brwn_hover");
});