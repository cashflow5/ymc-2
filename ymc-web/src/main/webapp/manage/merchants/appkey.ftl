<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-APPKEY管理</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 设置模块&gt; APPKEY管理</p>
			<div class="box-contain">
                 <div class="c-h-2column-right">
                     <div class="c-h-right">
                         <input class="mt20" type="checkbox" onchange="changeStatus('${status!'0' }');" 
                          <#if status?? && status==1>checked="checked"</#if> data-ui-type="toggle" />
                     </div>
                     <div class="c-h-contain">
                         <p class="h3">优购开放API</p>
                         <p>优购API提供优购商城工具、订单、类目、库存、物流等数据接口，可对接商家ERP系统。</p>
                         <p>如贵公司使用的ERP尚未根据优购ERP文档完成开发对接，建议您联系优购运营负责人。</p>
                     </div>
                 </div>
                 <div class="appkey">
                     <div class="c-h-2column-left">
                         <div class="label">Appkey：</div>
                         <div class="c-h-contain pr">
                             <input id="appkey" class="input-default" type="text" value="${appkey }" disabled /><a href="javascript:;" class="btn-blue mlf1 copy">复制</a><span class="copymsg"></span></div>
                     </div>
                     <div class="c-h-2column-left">
                         <div class="label">Appkey secret：</div>
                         <div class="c-h-contain pr">
                             <input id="appkeysecret" class="input-default" type="text" value="${secret }" disabled /><a href="javascript:;" class="btn-blue mlf1 copy">复制</a><span class="copymsg"></span></div>
                     </div>
                 </div>
            </div>
		</div>
	 </div>
	<script type="text/javascript" src="${BasePath}/yougou/js/zclip/jquery.zclip.min.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/yguib.js?${style_v}"></script>
    <script>
    var result = "${result!'0'}";
    var apiId = "${apiId!''}";
    var flag = false;
    var status = "${status!'0'}";
    var appkey = "${appkey!''}";
    var appSecret = "${secret!''}";
    $(function() {
        if(parseInt(result)==500){
        	ygdg.dialog.alert("系统发生错误，APPKEY生成失败！");
        }
        $('.copy').zclip({
            path: '${BasePath}/yougou/js/zclip/ZeroClipboard.swf',
            copy: function() { //复制内容 
                return $(this).siblings('input').val();
            },
            afterCopy: function() { //复制成功 
                $(this).siblings('.copymsg').text('复制成功').show().delay(1000).fadeOut();
            }
        });
    });
    function changeStatus(status){
        if(status==0){
        	status = 1;
        }else{
			status = 0;
        }
        $(".mt20[type='checkbox']").attr("onchange","changeStatus('"+status+"')");
       	$.post("${BasePath}/merchants/login/changeApiStatus.sc",
       			{apiId:apiId,appkey:appkey,appSecret:appSecret,status:status},
       			function(json){
           	if(json.code==500){
           		$('.toggle-states',$(".mt20[type='checkbox']").parents('.yg-toggle')).removeClass('on');
           		ygdg.dialog.alert(json.msg);
           	}
           },"json");
    }
    </script>
</body>
</html>