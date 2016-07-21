<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/university.css" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.8.2.min.js"></script> 

<title>优购商城--商家后台--课程查看</title>
</head>

<body>
   <div class="container">
     	<div class="toolbar">
		    <div class="t-content">
				<div class="btn" onclick="javascript:goBack();">
					<span class="btn_l"></span>
		        	<b class="ico_btn back"></b>
		        	<span class="btn_txt">返回</span>
		        	
	        	</div> 
	        	<div class="btn"  >
		        	<span class="btn_txt">
		                 <#if merchantTraining??>
		                 &gt; 课程查看
		                <#else>
		                &gt; 该课程不存在或已删除
		                </#if>
		            </span>
	            </div>
			</div> 
		</div> 
        <!--工具栏start-->
     
        <dl class="upload-box clearfix">
            <dt class="title">&nbsp;</dt>
            <dd>
                <div class="upload-content">
                    <div class="up-image">
                        <div class="hd" id="dndArea1">
                            <img src="<#if headPath??>${headPath!""}</#if><#if merchantTraining??&&merchantTraining.picUrl??>${merchantTraining.picUrl!"../images/common/up-bg.png"}<#else>../images/common/up-bg.png</#if>"  class="main-image"></img>
                        </div>
                        <div id="dndArea" class="bd">
                            <div class="bd-content">
                                <div class="clearfix process-box">
                                    <div  >
                                           <a href='<#if headPath??>${headPath!""}</#if><#if merchantTraining??&&merchantTraining.fileUrl??>${merchantTraining.fileUrl!""}</#if>' 
                                           id="realFileName">课程文件：
                                           <#if merchantTraining??&&merchantTraining.fileName??>${merchantTraining.fileName!""}</#if>
                                           </a>
                                           <br>
                                           
                                    </div>
                                    <div><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;
                                    <a href='${BasePath}/yitiansystem/merchants/training/to_training_preview.sc?fileType=${merchantTraining.fileType!''}&previewUrl=${merchantTraining.previewUrl!''}' 
                                    		target="_Blank">点击预览课程内容</a></div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">标题：</dt>
            <dd>
                <div class="upload-content">
                    <#if merchantTraining??&&merchantTraining.title??>${merchantTraining.title!""}</#if>
                   
                </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">分类：</dt>
            <dd>
                <div class="upload-content">
                        <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='1'>新手报到</#if> 
                        <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='2'>商品管理</#if>
                    	<#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='3'>店铺管理</#if>
                    	<#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='4'>促销引流</#if>
                    	<#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='5'>客户服务</#if>
                    	<#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='6'>规则解读</#if>
                   
                </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">简介：</dt>
            <dd>
                <div class="upload-content">
                    <textarea readonly="readonly" class="upload-filed v-align-top" cols="30" rows="10" id="description" name="description" ><#if merchantTraining??&&merchantTraining.description??>${merchantTraining.description!""}</#if></textarea>
                    
                </div>
            </dd>
        </dl>
 
    </div>
<script type="text/javascript">
function goBack(){
	location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
}
</script>

</body>

</html>