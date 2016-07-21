jQuery(function() {
    var $ = jQuery,
       
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,
     
        // Web Uploader实例
        uploader;
       
       uploader = WebUploader.create({
        // 自动上传。
        auto: true,
      
       
        // swf文件路径
        swf: basePath+'/webuploader/Uploader.swf',

        // 文件接收服务端。
        server: basePath+'/yitiansystem/merchants/training/handleUploadFile.sc',

        // 选择文件的按钮。可选。
        pick: '#filePicker',
        //不去重
    	duplicate:1,  
    	timeout: 10 * 60 * 1000, 
        compress:false ,
        formData:{trainingId:$('#id').val()}
    });
       
    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
    	var name = file.name;
    	var type = name.substring(name.lastIndexOf(".")+1);
    /*	if( type!='pdf'&& type!='swf'&& type!='flv'){
     	   uploader.removeFile(file);
     	   ygdg.dialog.alert("课程文档格式只支持 pdf、swf，视频请先在本页面下载格式转换器转成flv格式再上传. 对ppt格式的支持日后实现!");
     	   return;
     	}*/
    	if( !( type=='ppt'|| type=='pptx'|| type=='pdf'|| type=='swf'|| type=='doc'|| type=='docx'|| type=='flv'
    		|| type=='PPT'|| type=='PPTX'|| type=='PDF'|| type=='SWF'|| type=='DOC'|| type=='DOCX'|| type=='FLV' ) ){
    	   uploader.removeFile(file);
    	   ygdg.dialog.alert("课程文档格式只支持 ppt、pptx、pdf、swf，视频请先在本页面下载格式转换器转成flv格式再上传.");
    	   return;
    	}
       $("#filePicker").hide();
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var val=parseInt(percentage*100);
        $('.process-bar-run').css('width', val + '%');
        $('.process-bar-text').html(val + '%');
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file,response  ) {
    	if(response.success==true){
    		  
//    		 $("#filePicker").attr('label','重新上传');
//    		 uploader.refresh(); 		
    		 $("#realFileName").html(file.name);
    		 
    	 	//给对象赋值
    	    var fileUrl = response.fileUrl;
    	    $("#realFileName").removeAttr('href');//置空，稍后上传
    	    var fileType =  response.fileType;
    	    $("#fileUrl").val(fileUrl);
    	    $("#updatePreviewUrlFlag").val("true");
    	    $("#fileType").val(fileType);
    	    $("#fileName").val(file.name);
    	    ygdg.dialog.alert("文件上传成功！");
    	 }else{
    	 	if (response.message == '0') ygdg.dialog.alert(file.name+',文件不符规格,只允许上传ppt,pptx,pdf,swf(最大20M),flv格式(最大不超过50M)!');
    	    else if (response.message == '1') ygdg.dialog.alert(file.name+',上传的文件为空 !');
    	    else if (response.message == '2') ygdg.dialog.alert(file.name+',文件上传到服务器本地失败 !');
    	    else  ygdg.dialog.alert('该课程的原文件还在处理中，请等待处理完毕后再开始上传新文件！');
    	  
    	 	 $('.process-bar-run').css('width', '0%');
        	 $('.process-bar-text').html('0%');
    	 }	
    	 $("#filePicker").show();
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
    	 ygdg.dialog.alert("上传失败");
    	 $("#filePicker").show();  
    	 $('.process-bar-run').css('width', '0%');
    	 $('.process-bar-text').html('0%');
    });

});