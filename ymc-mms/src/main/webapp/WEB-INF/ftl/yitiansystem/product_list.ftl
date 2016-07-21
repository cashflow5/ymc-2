<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="优购网,b2c" />
<meta name="Description" content="优购网,b2c" />
<link rel="icon" href="${BasePath}/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<title>优购电子商务管理系统</title>
</head>

<body class="close_left" style="overflow-y:scroll;*overflow-y:inherit;">
 
<div class="continer" style="clear: both; display: block;">
	<!--公共头部开始-->
	<div class="header">
		<div class="headerNav" >
			<ul  class="headerNav-top" style="position:absolute;top:0;right:0;">
				<li class="shop"><a href="${jobHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-16">调度</a></li>
				<#-- <li><a href="javascript:void(0);" onclick="iframeSrc('product_manage_lfbar.sc?root_struc=root-7','${BasePath}/yitiansystem/systemmgmt/mainmanage/tomain.sc');">配置</a></li>-->
				<li><a href="${bmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-11">地区仓管</a></li>
				<li><a href="${bmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-14">在线支付数据分析</a></li>
			</ul>
			<div class="logo"> 
				<img src="${BasePath}/yougou/images/layout/logoin.png" alt="优购网" /> 
				<span>电子商务管理系统</span> 
			</div>
			<div class="container_nav" style="width:730px;height:89px;">
				<div style="width:730px;overflow:hidden;margin-bottom:8px;">
					<ul class="nav">
						<li id="root-0"><a class="main m0_curr" title="主面板" href="${bmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc">主面板</a> </li>
						<#--<li id="root-8"><a class="product" title="商品" href="${commodityHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-8">商品</a></li>-->
						<li id="root-1"><a class="inventory" title="会员" href="${omsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-1">会员</a></li>
						<li id="root-5"><a class="order" title="订单" href="${omsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-5">订单</a></li>
						<li id="root-6"><a class="customer" title="客服" href="${omsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-6">客服</a></li>
						<li id="root-12"><a class="tool" title="WMS" href="${wmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-12">WMS</a></li>
						<li id="root-10"><a class="report" title="财务" href="${fmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-10">财务</a></li>
						<li id="root-13"><a class="content" title="报表" href="${fmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-13">报表</a></li>
						<li id="root-18"><a class="dispatch"  title="外部平台"  href="${outsideHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-18">外部平台</a></li>
						<li id="root-17"><a class="business"  title="招商"  href="${mmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-17">招商</a></li>
						<li id="root-22"><a class="dispatch"  title="分销"  href="${dmsHost}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-22">分销</a></li>
						<li id="root-23"><a class="inventory"  title="工单"  href="${workorderhost}/index.sc">工单</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="admin" style="width:200px;">
			<div class="info_wrap relative">
				<span class="link"> 
					<a href="#">${systemUser.username?default("")}</a>|
					<#--<a href="#" onclick="redirectFrame();">密码修改</a>| -->
					<a title="退出" href="${BasePath}/logout" href="javascript:void(0);">退出</a> 
				</span> 
				<span class="info clearfix"> 
					<a title="未处理的订单 " href="#"><b class=s1></b><span >0</span></a> 
					<a title="未回复的商品咨询 " href="#"><b class=s2></b><span >0</span></a> 
					<a title="低库存货品" href="#"><b class=s3></b><span>0</span></a> 
				</span> 
			</div>
		</div>
	</div>
	<!--公共头部结束-->
	<div class="clearfix">
		<div class="lfbar" style="width:166px; height:auto; position:absolute; left:0;z-index:1;background:#f3f9ff;">
			<div id="load-left" style="position:absolute;left:10px;top:0;padding:20px 0 0 40px;background:#f3f9ff;height:100%;width:106px;">
			<img src="${BasePath}/yougou/images/layout/blue-loading.gif" /></div>
			<iframe  id="lframe" name="lframe" width="166"  height="100%" border="0"  frameborder="0" scrolling="no" ></iframe>
		</div>

		<div class="barico">
			<div class="sidebar"></div>
		</div>
		<div class="rfbar" style="width:100%;position:absolute; left:0;">
		<div class="content" style="margin-left:166px;">
		<div id="load" style="padding:20px;"><img src="${BasePath}/yougou/images/layout/blue-loading.gif" />&nbsp;loading</div>
			<iframe width="100%" height="100%" id="mbdif" style="width:100%;height:100%;" scrolling="no" frameborder="0" onload="this.height=mbdif.document.body.scrollHeight" src="${BasePath}/yitiansystem/systemmgmt/mainmanage/tomain.sc" border="0" name="mbdif" ></iframe>
		</div>
		</div>
	</div>
</div>
<!--底部内容开始-->
<div class="clear"></div>
<div class="footer"> ©2011 优购科技有限公司版权所有 </div>
<!--底部内容结束--> 
<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 
<script>
$(function(){
	
	if("${root_struc?default("")}" == ""){
	  iframeHomeSrc('${BasePath}/yitiansystem/systemmgmt/mainmanage/tomain.sc');
	}else{
	  iframeSrc('product_manage_lfbar.sc?root_struc=${root_struc?default("")}','${BasePath}/yitiansystem/systemmgmt/mainmanage/tomain.sc');
    $("#${root_struc?default("")} a").click();
	}

	$(".popup-nav").mouseup();
	$(".topnav-more").hover(function(){
	$(".popup-nav").slideDown(300);
	});
	
	$(".pop-span").hover(function(){
	},function(){
		$(".popup-nav").slideUp(300);
		});
	});
	
function redirectFrame(){
	var url = "${BasePath}/yitiansystem/systemmgmt/systemuser/toUpdateSystemUserPassword.sc?id=${systemUser.id}&fromifr=topFrame";
	 window.top.frames["mbdif"].document.location.href = url;
	 return true;
}

function testH()
{
	var bodyH=document.documentElement.clientHeight;
	
	var lH=300;
	try
	{
	
	 lH=$("#lframe").contents().find(".menu").height();
	 $("#lframe").height(lH);
	}catch(error){}
	
	var rH = 0;
	try {
		rH=$("#mbdif").contents().find(".container,.continer,.contentMain").height()+30;
		if (rH == null) {
			rH=$("#mbdif").contents().find(".continer").height()+30;
		}
	} catch(error) {
		rH = 1000;
	}
	
	if(rH<lH) rH=lH;
	$("#mbdif").height(rH)
	$(".continer").height($("#mbdif").height()+100)
	
	//alert('hH:'+lH+',rH:'+rH+',bH:'+bodyH)
	
	if(lH<bodyH && rH+150<bodyH)
	{
		$('.footer').css({'position':'absolute','bottom':'0'});
	}
	else
	{
		$('.footer').css({'position':'','bottom':''});
	}
}

var autoH=setInterval(testH,500);//自适应高度


//设置开关样式
$(".sidebar").mouseover(function(){
	$(this).addClass("menu-on")
}).mouseout(function(){
	$(this).removeClass("menu-on")
});

// 左侧菜单关闭展开
$(".barico").click(function(){
	try
	{
		var tw=$("#mbdif").contents().find(".tbox").width();
		if($("body").hasClass("close_left"))
		{
			$("#mbdif").contents().find(".tbox").width(tw+166);
		}
		else
		{
			$("#mbdif").contents().find(".tbox").width(tw-166);
		}
	}catch(error){}

})


//设置主菜单样式
$(".nav li a").click(function(){
	$(".nav li a").each(function(){
		$(".nav li a").removeClass("m"+$(".nav li a").index(this)+"_curr");
	});
	$(this).addClass("m"+$(".nav li a").index(this)+"_curr");
});


//左侧菜单开关
function fnToggleMenu(){
	if($("body").hasClass("close_left")){
		$("body").removeClass('close_left');
		opflag=0;
		$(".lfbar").show();
		$(".rfbar .content").css({'margin-left':'166px'});
		$(".rfbar").css({width:'100%'});
	}else{
		$("body").addClass('close_left');
		$(".lfbar").hide();
		$(".rfbar .content").css({'margin-left':'0'});
		$(".rfbar").css({width:'99.6%'});
		opflag=1;
	}
}

//顶部导航普通菜单跳转
function iframeSrc(x,y){
$("body").removeClass('close_left');
opflag=0;
$(".lfbar").show();
$(".rfbar .content").css({'margin-left':'166px'});
$(".rfbar").css({width:'100%'});
$(".barico").show();
$("#load-left").show();
$(".sidebar").unbind('click').bind('click',fnToggleMenu);
document.getElementById("lframe").src=x;
document.getElementById("mbdif").src=y;
}

//顶部导航《主面板》跳转
function iframeHomeSrc(url){
$("body").addClass('close_left');
$(".lfbar").hide();
$("#load-left").hide();
$(".rfbar .content").css({'margin-left':'0'});
$(".rfbar").css({width:'99.6%'});
$(".barico").hide();
opflag=1;
$(".sidebar").unbind('click');
document.getElementById("mbdif").src=url;
}

//顶部次要菜单导航
function iframeSrcSec(x,y){
$("body").removeClass('close_left');
opflag=0;
$(".lfbar").show();
$("#load-left").show();
$(".rfbar .content").css({'margin-left':'166px'});
$(".rfbar").css({width:'100%'});
$(".barico").show();
$(".sidebar").unbind('click').bind('click',fnToggleMenu);
document.getElementById("lframe").src=x;
document.getElementById("mbdif").src=y;
$(".nav li a").each(function(){
		$(".nav li a").removeClass("m"+$(".nav li a").index(this)+"_curr");
	});
}

//页面加载完成禁用菜单开关
$('body').addClass('close_left');
$(".lfbar").hide();
$("#load-left").hide();
$(".rfbar .content").css({'margin-left':'0'});
$(".rfbar").css({width:'99.6%'});
iframeHomeSrc('${BasePath}/yitiansystem/systemmgmt/mainmanage/tomain.sc')//F5刷新跳转到主面板
$("#mbdif").load(function(){           
//$('html, body').animate({scrollTop: '0px'});//滚动到顶部 
window.scrollTo(0,0);
});



function correctPNG() // correctly handle PNG transparency in Win IE 5.5 & 6.
{
    var arVersion = navigator.appVersion.split("MSIE")
    var version = parseFloat(arVersion[1])
    if ((version >= 5.5) && (document.body.filters))
    {
       for(var j=0; j<document.images.length; j++)
       {
          var img = document.images[j]
          var imgName = img.src.toUpperCase()
          if (imgName.substring(imgName.length-3, imgName.length) == "PNG")
          {
             var imgID = (img.id) ? "id='" + img.id + "' " : ""
             var imgClass = (img.className) ? "class='" + img.className + "' " : ""
             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
             var imgStyle = "display:inline-block;" + img.style.cssText
             if (img.align == "left") imgStyle = "float:left;" + imgStyle
             if (img.align == "right") imgStyle = "float:right;" + imgStyle
             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle
             var strNewHTML = "<span " + imgID + imgClass + imgTitle
             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
             + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>"
             img.outerHTML = strNewHTML
             j = j-1
          }
       }
    }    
}
if ($.browser.msie)
{
 if ($.browser.version == "6.0")
	window.attachEvent("onload", correctPNG); 
}
 
function forbiddenFunc(){
 	$('div.lfbar').html("");
    var url = "${BasePath}/forbidden_browser.html";
 	window.top.frames["mbdif"].document.location.href = url;
 }
</script>
<script type="text/javascript" src="${BasePath}/js/common/browserAnalysis.js"></script>
</body>
</html>
