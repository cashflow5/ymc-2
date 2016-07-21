<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?version=2.5"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/university.css" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.8.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?version=2.5"></script>
<script type="text/javascript">
var basePath="${BasePath}";
</script>
<script type="text/javascript" src="${BasePath}/webuploader/uploadForTraining.js"></script>

<title>优购商城--商家后台--课程</title>
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
			                  &nbsp;&nbsp;&nbsp;&gt;  课程修改
			                <#else>
			                &nbsp;&nbsp;&nbsp;&gt; 课程添加
                </#if>
            </span>
            </div>
		</div>
           
       
        </div>
        <!--工具栏start-->
        <form  name="queryForm" id="queryForm" method="post">
        <input type="hidden" id="id" name="id" value="<#if merchantTraining??&&merchantTraining.id??>${merchantTraining.id!""}</#if>" />
        <dl class="upload-box clearfix">
            <dt class="title">&nbsp;</dt>
            <dd>
            <div class="upload-content">
            	<div class="up-image clearfix">
            		 <input type="hidden" id="fileName" name="fileName" value="<#if merchantTraining??&&merchantTraining.fileName??>${merchantTraining.fileName!""}</#if>"></input>
                	<input type="hidden" id="fileType" name="fileType" value="<#if merchantTraining??&&merchantTraining.fileType??>${merchantTraining.fileType!""}</#if>" ></input>
                	<input type="hidden" id="fileUrl" name="fileUrl" value="<#if merchantTraining??&&merchantTraining.fileUrl??>${merchantTraining.fileUrl!""}</#if>"></input>
                	<input type="hidden" id="previewUrl" name="previewUrl" value="<#if merchantTraining??&&merchantTraining.previewUrl??>${merchantTraining.previewUrl!""}</#if>"></input>
                	<input type="hidden" id="picUrl" name="picUrl" value="<#if merchantTraining??&&merchantTraining.picUrl??>${merchantTraining.picUrl!""}</#if>"></input>
                	<input type="hidden" id="updatePreviewUrlFlag" name="updatePreviewUrlFlag" value=""></input><!-- 标志是否有文件变动，即是否需要重设PreviewUrl-->
                	<input type="hidden" id="headPath" name="headPath" value="<#if headPath??>${headPath!""}</#if>"></input><!-- PreviewUrl 的 头部-->
                        <div class="hd" id="uploader">
                            <img src="<#if headPath??>${headPath!""}</#if><#if merchantTraining??&&merchantTraining.picUrl??>${merchantTraining.picUrl!""}</#if>"  class="main-image"></img>
                            <div id="filePicker1"  class="main-click-upload"></div>
                        </div>
                        <div class="bd">
                            <div class="bd-content">
                                <div class="clearfix process-box">
                                    <div class="process-bar">
                                        <div class="process-bar-run"></div>
                                        <div class="process-bar-text"></div>
                                    </div>
                                    <a id="filePicker" class="up-btn up-btn-upload" href="javascript:;">上传课程</a>
                                    <span class="Gray fl pt6">支持上传PPT和视频  ; 视频格式为flv，最大支持50M</span>
                                </div>
                                <div class="process-bar-msg">
                                  <a href='<#if headPath??>${headPath!""}</#if><#if merchantTraining??&&merchantTraining.fileUrl??>${merchantTraining.fileUrl!""}</#if>' id="realFileName" target="_Blank">
                                    <#if merchantTraining??&&merchantTraining.fileName??>${merchantTraining.fileName!""}</#if>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
	                 <div class="upload-tip Gray pt15">封面图片格式为jpg,jpeg,bmp,png,尺寸为150*100px  
	                 	 <a href="http://down.pcgeshi.com/FormatFactory_setup.exe" class="gray"> &nbsp;&nbsp;&nbsp;&gt; 格式转换器官方下载链接</a>
	                 </div>
                    </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">标题：</dt>
            <dd>
                <div class="upload-content">
                    <input class="upload-filed" type="text" id="title" name="title" maxlength="50"
                    value="<#if merchantTraining??&&merchantTraining.title??>${merchantTraining.title!""}</#if>"/>
                    <span class="Gray">标题最多50个汉字</span>
                </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">分类：</dt>
            <dd>
                <div class="upload-content">
                    <select class="w170" name="catName" id="catName">
                        <option value="">--请选择--</option>
                        <option value="1" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='1'>selected</#if>>新手报到</option>
                        <option value="2" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='2'>selected</#if>>商品管理</option>
                    	<option value="3" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='3'>selected</#if>>店铺管理</option>
                    	<option value="4" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='4'>selected</#if>>促销引流</option>
                    	<option value="5" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='5'>selected</#if>>客户服务</option>
                    	<option value="6" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='6'>selected</#if>>规则解读</option>
                    </select>
                </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">简介：</dt>
            <dd>
                <div class="upload-content">
                    <textarea class="upload-filed v-align-top" cols="30" rows="10" id="description" name="description" maxlength="300" ><#if merchantTraining??&&merchantTraining.description??>${merchantTraining.description!""}</#if></textarea>
                    <span class="Gray">简介最多300个汉字</span>
                </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">发布时间：</dt>
            <dd>
                <div class="upload-content">
                    <label >
                        <input class="rb" type="radio" name="isPublish" id="isPublish" value="1" <#if merchantTraining??&&merchantTraining.isPublish??&&merchantTraining.isPublish==1>checked</#if>/> 立即</label>
                    <label >
                        <input class="rb ml20" type="radio" name="isPublish" id="isPublish" value="0" <#if merchantTraining??&&merchantTraining.isPublish??&&merchantTraining.isPublish==0>checked</#if>/> 暂不发布</label>
               </div>
            </dd>
        </dl>
        <dl class="upload-box clearfix">
            <dt class="title">&nbsp;</dt>
            <dd>
                <div class="upload-content">
                    <a class="up-btn up-btn-submit" href="javascript:saveCourse(this);">提交</a>
                </div>
            </dd>
        </dl>
        </form>
    </div>
<script type="text/javascript">

var uploader1 = WebUploader.create({
    
    auto: true,
    swf: '${BasePath}/webuploader/Uploader.swf',
     // 文件接收服务端。
    server: '${BasePath}/yitiansystem/merchants/training/upload.sc',
    
    pick: {
            id: '#filePicker1',
            label: '上传封面图片',
            multiple:false
    },
    
    timeout: 2 * 60 * 1000, 
   // accept:{
	//    title: 'Images',
	//    extensions: 'jpg,jpeg,bmp,png',
	//    mimeTypes: 'image/*'
	//},
	
	//拖拽区域
	//dnd:'#dndArea1',
	//disableGlobalDnd: true,
	duplicate:1,   //不去重
    compress:false,  //不压缩
    //fileSingleSizeLimit:45000
});

uploader1.on('fileQueued',function(file){
    var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type!='jpg'&& type!='jpeg'&& type!='bmp'&& type!='png'){
	   uploader1.removeFile(file);
	   ygdg.dialog.alert("图片格式只能是 jpg、jpeg、bmp、png");
	   return;
	}
	if(file.size>4500000){
	    uploader1.removeFile(file);
	    ygdg.dialog.alert("图片不符合规格，请上传100*150的小图");
	    return ;
   } 
 
});

uploader1.on('uploadSuccess', function( file,response ) {
if(response.success==true){
   $(".main-image").attr('src',response.headPath+response.src);
   $("#picUrl").val(response.src);
}else{
	 ygdg.dialog.alert("上传图片失败");
}
});

uploader1.on('uploadError', function( file ) {
   ygdg.dialog.alert("上传图片失败");
   
});

function saveCourse(curObj){
  curObj = $(curObj);
  var msg="";
  var picUrl = $.trim($("#picUrl").val());
  if(picUrl=="" ){
     msg=msg+"<br>请上传主图<br>";
  }
  var fileName = $.trim($("#fileName").val());
  var fileType = $.trim($("#fileType").val());
  if(fileName=="" || fileType==""){
     msg=msg+"<br>请上传文件<br>";
  }
  var title = $.trim($("#title").val());
  if(title=="" ){
     msg=msg+"<br>标题不能为空<br>";
  }
  var catName = $.trim($("#catName").val());
  if(catName=="" ){
     msg=msg+"<br>分类不能为空<br>";
  }
  var isPublish = $("input:radio[name=isPublish][checked]").val();
  if( undefined==isPublish || isPublish=="" ){
     msg=msg+"<br>请选择是否立即发布<br>";
  }
  if(msg!=""){
  	 ygdg.dialog.alert(msg);
  	 return;
  }
  
  curObj.attr("disabled",true);
  
  if(isPublish=="1" && fileType=="0" && fileName.indexOf(".swf")==-1 ){
  	 ygdg.dialog.alert("您确认发布吗？后台文档处理可能会耗费一些时间，请稍后刷新列表页面，查看发布结果。");
  }
  $.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$("#queryForm").serialize(),
		url : "${BasePath}/yitiansystem/merchants/training/save_training.sc",
		success : function(data) {
			curObj.attr("disabled",false);
			if(data.resultCode=="200"){
				ygdg.dialog.alert("保存成功");
				document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
   });
}

function goBack(){
	location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
}
</script>
</body>

</html>
