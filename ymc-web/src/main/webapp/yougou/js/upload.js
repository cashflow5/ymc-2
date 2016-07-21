
jQuery(function() {
    var $ = jQuery,
        $list = $('#fileList'),
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 40 * ratio,
        thumbnailHeight = 40 * ratio,

        // Web Uploader实例
        uploader;

    // 初始化Web Uploader
    uploader = WebUploader.create({
        // 自动上传。
        auto: true,

        // swf文件路径
        swf:'js/webuploader/Uploader.swf',

        // 文件接收服务端。
        server: 'http://mer.yougou.com/server/fileupload.php',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePickerBatch',

        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'jpeg,png,jpg,gif'
            //mimeTypes: 'image/*'
            //gif,jpg,jpeg,bmp,png,
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        var $li = $('<li id="' + file.id + '" class="file-item thumbnail">' + '<img>' + '<div class="del-btn hide">删除</div>' + '<div class="info">' + file.name + '</div>' +'</li>'),
            $img = $li.find('img');
        $list.append( $li );

        // 创建缩略图
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).addClass('upload-state-done');
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');
        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败');
    });

    //console.log(uploader);
});