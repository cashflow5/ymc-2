<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购商城--商家后台--课程查看--预览</title>  
    <link type="text/css" rel="stylesheet" href="${BasePath}/css/preview.css"/>	   
	<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/training/flexpaper.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/training/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/training/swfobject.js" ></script>
</head>
<body>
  
 		<div class="blank20"></div>
        <div class="box-detail" style="width:740px;">
            <div class="hd">
            	<#-- PPT 在线播放 -->
            	<#if merchantTraining.fileType == 0>
	                <div class="flash" id="documentViewer">
	           
	                  <script type="text/javascript"> 
	
		        		 var startDocument = "Paper";
	                     $('#documentViewer').FlexPaperViewer(
				                { config : {
			
				                    SWFFile : "${headPath!''}${merchantTraining.previewUrl!''}",
			
				                    Scale : 0.6,
				                    ZoomTransition : 'easeOut',
				                    ZoomTime : 0.5,
				                    ZoomInterval : 0.2,
				                    FitPageOnLoad : true,
				                    FitWidthOnLoad : false,
				                    FullScreenAsMaxWindow : false,
				                    ProgressiveLoading : false,
				                    MinZoomSize : 0.2,
				                    MaxZoomSize : 5,
				                    SearchMatchAll : false,
				                    InitViewMode : 'Portrait',
				                    RenderingOrder : 'flash',
				                    StartAtPage : '',
			
				                    ViewModeToolsVisible : true,
				                    ZoomToolsVisible : true,
				                    NavToolsVisible : true,
				                    CursorToolsVisible : true,
				                    SearchToolsVisible : true,
				                    WMode : 'window',
				                    localeChain: 'en_US'
				                }}
				        );
				   
				     </script>
	                </div>
                </#if>
                
                <#-- 视频 在线播放 -->
                <#if  merchantTraining.fileType == 1>
	                <div class="video" id="altContentOneNewControls">
	                	<script type="text/javascript">
							
							 var flashvarsVideoNewControls = {
								source: "${headPath!''}${merchantTraining.previewUrl!''}",
								type: "video",
								streamtype: "file",
								server: "",
								duration: "52",
								poster: "/mms/yougou/images/yitiansystem/player_poster.png",
								autostart: "true",
								logo: "/mms/yougou/images/yitiansystem/player_logo.png",
								logoposition: "top left",
								logoalpha: "30",
								logowidth: "130",
								logolink: "http://www.yougou.com",
								hardwarescaling: "false",
								darkcolor: "000000",
								brightcolor: "4c4c4c",
								controlcolor: "FFFFFF",
								hovercolor: "67A8C1",
								controltype: 1
							};
						
							var params = {
								menu: "false",
								scale: "noScale",
								allowFullscreen: "true",
								allowScriptAccess: "always",
								bgcolor: "#000000",
								quality: "high",
								wmode: "opaque"
							};
							var attributes = {
								id:"JarisFLVPlayer"
							};
							
							
							swfobject.embedSWF('/mms/yougou/js/training/swf/JarisFLVPlayer.swf', "altContentOneNewControls", "740px", "100%", "10.0.0", '/mms/yougou/js/training/swf/expressInstall.swf', flashvarsVideoNewControls, params, attributes); 
							
						</script>
	                
	                </div>
                </#if>
            </div>
        </div>

</body>

</html>
