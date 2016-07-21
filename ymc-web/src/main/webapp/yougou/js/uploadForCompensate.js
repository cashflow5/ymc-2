
jQuery(function() {
    var $ = jQuery,
        $list = $('#fileList'),
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1;

    // 初始化Web Uploader
    uploader = WebUploader.create({
        // 自动上传。
        auto: true,
        // swf文件路径
        swf:basePath+'/yougou/js/webuploader/Uploader.swf',
        // 文件接收服务端。
        server: basePath+'/img/uploadForCompensate.sc',
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick:{ id:'#filePickerBatch',
        	 multiple:true
        },
        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'bmp,jpeg,jpg,gif,png',
            mimeTypes: 'image/*'
        },
        duplicate:true,
        fileSingleSizeLimit:5*1024*1024
    });

//    // 当有文件添加进来的时候
//    uploader.on( 'fileQueued', function( file ) {
//    	// 已经有7张了，就不让再上传了
//    	var length = $('#fileList').find('li').length;
//    	if( length >6 ){
//    		
//    		 uploader.removeFile( file ); //将文件移除，否则会继续上传
//    		 var $ul = $('#fileList' ),
//             $error = $ul.parent().find('div.cred');
//	         // 避免重复创建
//	         if ( !$error.length ) {
//	             $error = $('<div class="cred"></div>').insertAfter( $ul );
//	         }
//	
//	         $error.text('最多只支持上传7张！');
//	      
//    		 return;
//    	}
//    	
//        var $li = $('<li id="' + file.id + '" class="file-item thumbnail">' + '<img>' + '<span class="del-btn hide">×</span> <input type="hidden" id="file_'+file.id +'" /></li>'),
//            $img = $li.find('img');
//        $list.append( $li );
//
//        // 创建缩略图
//        uploader.makeThumb( file, function( error, src ) {
//            if ( error ) {
//                $img.replaceWith('<span>不能预览</span>');
//                return;
//            }
//
//            $img.attr( 'src', src );
//        }, thumbnailWidth, thumbnailHeight );
//    });
    // 当有文件添加进来的时候
    uploader.on( 'filesQueued', function( files ) {
    	// 已经有7张了，就不让再上传了
    	var index = $('#fileList').find('li').length;
    	var left = 7-index;
    	var length = files.length;
    	if( left<1 ){
    		for( var i=0;i<length;i++ ){
    			uploader.removeFile( files[i] ); 
    		}
    		
    		var $ul = $('#fileList' ),
            $error = $ul.parent().find('div.cred');
	         // 避免重复创建
	         if ( !$error.length ) {
	             $error = $('<div class="cred"></div>').insertAfter( $ul );
	         }
	         $error.text('最多只支持上传7张！');
    		 return;
    	}else{
    		for( var j=0;j<length;j++ ){
    			if(j>=left){//多余的删除
    				uploader.removeFile( files[j] ); 
    			}else{//上传
    				var file = files[j];
    				var $li = $('<li id="' + file.id + '" class="file-item thumbnail">' + '<img style="width:40px;height:40px;">' + '<div class="del-btn hide">删除</div>' + ' <input type="hidden" id="file_'+file.id +'" /></li>'),
    		            $img = $li.find('img');
    		        $list.append( $li );
    			}
    		}
    	}
    });

 
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ,response) {
    	if(response.success==true){
    		$( '#'+file.id ).addClass('upload-state-done');
    		$("#file_"+file.id).eq(0).val(response.message);
    		// 缩略图
    		var $img = $('li#'+file.id).find('img');
            $img.attr( 'src', response.message );
    	}else{
        	if (response.message == '6') ygdg.dialog.alert(file.name+',上传图片失败, 请重新再试！');
        	else if (response.message == '7') ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
        	else ygdg.dialog.alert(file.name+",图片上传失败，请重新上传，如再有问题请联系管理员！");
        	$("#"+file.id).remove();
    	}
        
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
    	 var $ul = $('#fileList' ),
         $error = $ul.find('div.cred');
         // 避免重复创建
         if ( !$error.length ) {
             $error = $('<div class="cred"></div>').appendTo( $ul );
         }
        $error.text('有图片上传失败，请重新上传');
        $("#"+file.id).remove();
    });
    
    //add lijunfang 20150924
    $('.upload_list').on('mouseover','.upload_list li',function(){
        $(this).find('.del-btn').removeClass('hide');
    });
    $('.upload_list').on('mouseout','.upload_list li',function(){
        $(this).find('.del-btn').addClass('hide');
    });

    $('.upload_list').on('click', '.del-btn', function(event) {
        $(this).parent().remove();
    });
});

