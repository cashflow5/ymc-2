<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购时尚商城_商家培训中心_列表</title>  	
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/detail.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/training/flexpaper.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/training/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/training/swfobject.js" ></script>
    <script type="text/javascript">
    	
    </script>

</head>

<body>
  
 		<div class="blank20"></div>
        <div class="box-detail">
            <div class="hd">
            	<#-- PPT 在线播放 -->
            	<#if trainingInfo.file_type == 0>
	                <div class="flash" id="documentViewer" >
	           		
	           		</div> 
	                  <script type="text/javascript"> 
	
		        		 var startDocument = "Paper";
	                     $('#documentViewer').FlexPaperViewer(
				                { config : {
			
				                    SWFFile : "${trainingInfo.preview_url!''}",
			
				                    Scale : 0.6,
				                    ZoomTransition : 'easeOut',
				                    ZoomTime : 0.5,
				                    ZoomInterval : 0.2,
				                    FitPageOnLoad : true,
				                    FitWidthOnLoad : true,
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
                </#if>
                
                <#-- 视频 在线播放 -->
                <#if trainingInfo.file_type == 1>
	                <div class="video" >
	                 <div  id="altContentOneNewControls"></div>
	           		</div> 
	                	<script type="text/javascript">
			
							 var flashvarsVideoNewControls = {
								source: "${trainingInfo.preview_url!''}",
								type: "video",
								streamtype: "file",
								server: "",
								duration: "52",
								poster: "../yougou/images/player_poster.png",
								autostart: "true",
								logo: "../yougou/images/player_logo.png",
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
							
							swfobject.embedSWF("../yougou/js/manage/training/swf/JarisFLVPlayer.swf", "altContentOneNewControls", "740px", "100%", "10.0.0", "../yougou/js/manage/training/swf/expressInstall.swf", flashvarsVideoNewControls, params, attributes); 
							
						</script>
                </#if>
            </div>
            <div class="bd">
                <h1>${trainingInfo.title!''}</h1>
                <p class="blank30"></p>
                <p><strong>发布时间：</strong> ${trainingInfo.create_time!''}</p>
                <p><strong>课程浏览：</strong>${trainingInfo.total_click!'0'}次</p>
                <p><strong>课程介绍：</strong>${trainingInfo.description!''}</p>
               <#-- <p>步教你如何在优购商家中心发布新商品。</p>  -->
            </div>
        </div>

</body>

</html>
