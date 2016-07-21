var uploader = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: basePath+'/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: basePath+"/img/upload.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker',
    // 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'jpg',
        mimeTypes: 'image/*'
    },
    formData:{
       no:1,
    },
    duplicate:1,   //不去重
    compress:false,  //压缩
    fileNumLimit:7,	//最多7张图片
    fileSingleSizeLimit:500*1024 //大小不超过500kb
});

uploader.on( 'filesQueued', function( files ) {
	var imagesbox = $(".pm-item .pm-box");
	//初始化所有图片
	//给上传图片加上ID
	for(var i=0,length=files.length;i<length;i++){ 
		var file = files[i];
		//var img = images.find("img").eq(i);
		var imghtml = imagesbox.eq(i);
		//img.attr("src","/yougou/images/unknow_img.png");
		//img.addClass(file.id);
		//img.parent().addClass("fileSelect");
		//img.parent().siblings("input[type='hidden']").val("-1");
		imghtml.addClass(file.id);
		imghtml.html("暂无图片");
		imghtml.siblings("input[type='hidden']").val("-1");
	}
});

uploader.on( 'uploadProgress', function( file, percentage ) {
    var $parent = $("."+file.id).eq(0);
    var $percent = $( '#'+file.id +'_percent');
    if(!$percent.length ){
         $percent = $('<div class="percent" id="'+file.id+'_percent"></div>').appendTo($parent);
    }
    $percent.css( 'width', percentage * 100 + '%' ); 
});

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploader.on( 'uploadSuccess', function( file,response) {
	if(response.success==true){
		var re=/.jpg$/;
		$("."+file.id).eq(0).siblings("input[type='hidden']").val(response.message);
		$("."+file.id).eq(0).html("<img style='width:88px;height:88px' width='88px' height='88px' src='"+response.message.replace(re,".png")+"'></img>");
		$( '#'+file.id +'_percent').remove();
		$("#pm_tip").addClass("none");
	}else{
    	if (response.message == '1') ygdg.dialog.alert(file.name+',request请求参数no为空值,请检查!');
    	else if (response.message == '2') ygdg.dialog.alert(file.name+',获取登录会话信息失败,请尝试重新登录操作!');
    	else if (response.message == '3') ygdg.dialog.alert(file.name+',商品图片大小超过了 500 KB');
    	else if (response.message == '4') ygdg.dialog.alert(file.name+',商品图片分辨率不符合 ( 800-1000px) * (800-1000px)的规格');
    	else if (response.message == '5') ygdg.dialog.alert(file.name+',图片校验异常');
    	else if (response.message == '6') ygdg.dialog.alert(file.name+',上传图片失败, 请重新再试！');
    	else if (response.message == '7') ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
    	else ygdg.dialog.alert(file.name+",图片上传失败，请重新上传，如再有问题请联系管理员！");
    	//$("."+file.id).eq(0).attr("src","/yougou/images/unknow_img.png");
    	$("."+file.id).eq(0).html("暂无图片");
    	$("."+file.id).eq(0).siblings("input[type='hidden']").val("-1");
    	$( '#'+file.id +'_percent').remove();
	}
});

// 文件上传失败，显示上传出错。
uploader.on( 'uploadError', function( file ) {
    ygdg.dialog.alert("上传失败");
    $( '#'+file.id +'_percent').remove();
});

uploader.on('error', function(type,file){
	if(type=="Q_EXCEED_NUM_LIMIT"){
		ygdg.dialog.alert("最多只能选择7张图片");
	}else if(type=="F_EXCEED_SIZE"){
		ygdg.dialog.alert(file.name+"文件大小已超500kb");
	}
});