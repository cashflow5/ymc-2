<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购时尚商城_商家培训中心_列表</title>  	
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/training_list.css?${style_v}"/>
    <script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
    <script type="text/javascript">
    	   
    	//根据热门标签类型查询
    	function queryTrainingByCatName(cat_name){
    		$("dd.first a").removeClass("a_click");
    		$("#"+cat_name).addClass("a_click"); 		
    		$("#cat_name").val(cat_name);

    		$("#queryVoform").submit();
    	}
    	
    	//根据文件类型查询
    	function queryTrainingByFileType(file_type){
    		
    		$("#fileTypes a").removeClass("a_click");
    		if(file_type=='0'){
    			$("#ppt").addClass("a_click"); 		
    		}else{
    			$("#video").addClass("a_click"); 
    		}
    		$("#file_type").val(file_type);

    		$("#queryVoform").submit();
    	}
    	
    	//查询所有
    	function queryAll(){
    		$("#file_type").val("");
    		$("#cat_name").val("");
    		$("#queryVoform").submit();
    	}
    	
    	//查询所有
    	function queryAll(){
    		$("#file_type").val("");
    		$("#cat_name").val("");
    		$("#queryVoform").submit();
    	}
    	
    	// 热门分类
    	var cat_name = "${cat_name!''}";
    	
    	// 文件类型
    	var file_type = "${file_type!''}";
    	
    	$(document).ready(function(){
    		
    		//选中热门分类
    		$("dd.first a").removeClass("a_click");
    		$("#"+cat_name).addClass("a_click");
    		
    		//选中文件类型
    		$("#fileTypes a").removeClass("a_click");
    		if(file_type=='0'){
    			$("#ppt").addClass("a_click"); 		
    		}else if(file_type=='1'){
    			$("#video").addClass("a_click"); 
    		}
    		
    	});
    </script>
</head>

<body>
  
 		<form action="training_list.sc" id="queryVoform" name="queryVoform" method="post">
        <div class="blank20"></div>
        <dl class="c-h-dl c-h-dl-text">
            <dt>课程类型：</dt>
            <dd class="first font-icon" id="fileTypes">
            	<span class="cuRed" ><a href="javascript:;" onclick="queryAll()"><font color="white">全部 </a></font> </span>
            	<a class="ml10" href="javascript:;" id="ppt" onclick="queryTrainingByFileType('0')"><em class="icon-bag"></em>PPT</a>
            	<a class="ml30" href="javascript:;" id="video" onclick="queryTrainingByFileType('1')"><em class="icon-video"></em>视频</a>
            </dd>
        </dl>
        <div class="blank10"></div>
        <dl class="c-h-dl c-h-dl-text">
            <dt>热门标签：</dt>
            <dd class="first">
                <a href="javascript:;" onclick="queryTrainingByCatName('1')" id="1">新手报到</a> |
                <a href="javascript:;" onclick="queryTrainingByCatName('2')" id="2">商品管理</a> |
                <a href="javascript:;" onclick="queryTrainingByCatName('3')" id="3">店铺管理</a> |
                <a href="javascript:;" onclick="queryTrainingByCatName('4')" id="4">促销引流</a> |
                <a href="javascript:;" onclick="queryTrainingByCatName('5')" id="5">客户服务</a> |
                <a href="javascript:;" onclick="queryTrainingByCatName('6')" id="6">规则解读</a>
            </dd>
        </dl>
        <div class="blank20"></div>
        	<input type="hidden" id="file_type" name="file_type" value="${file_type!''}">
        	<input type="hidden" id="cat_name" name="cat_name" value="${cat_name!''}">
        	
        </form>
        <div class="article-list">
            
            <#if pageFinder?? && pageFinder.data??>
            	<#list pageFinder.data as item>
            		 <dl class="article-item c-h-dl c-h-dl-article">
		                <dt>
		                    <a href="${BasePath}/training/training_info.sc?id=${item.id!''}" target="_blank"><img class="thum-img" src="${item.pic_url!''}" alt="" />
		                    <em class="<#if item.file_type == 1 >item-tag</#if><#if item.file_type == 0 >item-tag item-tag-p</#if>"></em>
		                    </a>
		                </dt>
		                <dd>
		                    <div class="article-item-content">
		                        <h1><a href="${BasePath}/training/training_info.sc?id=${item.id!''}" target="_blank">${item.title!''}</a></h1>
		                        <p>
		                        	<span class="Gray">课程简介：</span>
		                        	${item.description!''}
		                        </p>
		                        <p class="fr font-icon">
		                            <em class="icon-clock"></em>
		                            <span class="Gray">上传时间：</span>
		                            ${item.create_time!''}
		                            <em class="icon-user ml20"></em>
		                            <span class="Gray">课程浏览：</span>${item.total_click!0}次
		                        </p>
		                    </div>
		                </dd>
		            </dl>
            	</#list>
            </#if>
        
        <div class="blank30"></div>
       
        
            <div class="clearfix">
		         <div class="pagination fr">
		            <#if pageFinder??&&pageFinder.data??>
						<!--分页start-->						
							<#if pageFinder ??>
								<#import "/manage/widget/common.ftl" as page>
								<@page.queryForm formId="queryVoform"/>
							</#if>					
						<!--分页end-->
					</#if>
			     </div>
            </div>

</body>

</html>
