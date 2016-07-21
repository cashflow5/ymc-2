<#macro add_update_commodity_image_manage>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="${BasePath}/yougou/js/jquery.dragsort-0.5.1.js?${style_v}"></script>
<head>
</head>
<script>
    var imageManageUpload = {};
    var imageManageSelect = {};
    imageManageUpload.basePath = "${BasePath}";
    imageManageSelect.basePath = "${BasePath}";
    $(function(){
		$("#image-tab").find("li").bind("click",function(){
			$("#image-tab").find("li").removeClass("curr");
			$(this).addClass("curr");
			if($(this).attr("num")=="0"){
			    $(".image-select").hide();
				$(".image-upload").show();
			}else if($(this).attr("num")=="1"){
				$(".image-upload").hide();
				$(".image-select").show();
				loadData(1);
			}
		});
		
		$("#open-bottom,#open-top").live("click",function(){
			if($(this).attr("class")=="open"){
				$(".image-con").show();
				$("#open-bottom,#open-top").removeClass("open");
				$("#open-bottom,#open-top").addClass("close");
				$("#open-bottom,#open-top").text("收起");
				$("#image-tab").show();
				$(".open-bottom").show();
				
			}else if($(this).attr("class")=="close"){
				$(".image-con").hide();
				$("#open-bottom,#open-top").removeClass("close");
				$("#open-bottom,#open-top").addClass("open");
				$("#open-bottom,#open-top").text("展开");
				$("#image-tab").hide();
				$(".open-bottom").hide();
			}
		});
	});
</script>
<body>
	<div style="position:relative;">
		<a href="javascript:void(0)" class="close" id="open-top">收起</a>
		<ul class="tab" id="image-tab">
			<li class="curr" num=0 style="margin-left:20px;">
				<span>本地上传</span>
			</li>
			<li num=1>
				<span>图片空间上传</span>
			</li>
			<span class="fr">图片格式为jpg，图片宽790px，高度1-9999px，单张图片小于1M，图片不允许带水印和外链。</span>
		</ul>
	</div>
	<div class="image-con">
	    <#-- 分类 -->
		<div class="catelog">
			<#import "/manage/commodity/add_update_commodity_catalog.ftl" as p>
			<@p.add_update_commodity_catalog/>
		</div>
		 <#-- 图片上传 -->
		<div class="image-upload">
			<#import "/manage/commodity/add_update_commodity_imageupload.ftl" as p>
			<@p.add_update_commodity_imageupload/>
		</div>
		 <#-- 图片选择 -->
		<div class="image-select">
			<#import "/manage/commodity/add_update_commodity_selector.ftl" as p>
			<@p.add_update_commodity_selector/>
		</div>
		<div style="clear:left"></div>
	</div>
	<div class="open-bottom">
		<a href="javascript:void(0)" class="close" id="open-bottom">收起</a>
	</div>
</body>
</html>
</#macro>