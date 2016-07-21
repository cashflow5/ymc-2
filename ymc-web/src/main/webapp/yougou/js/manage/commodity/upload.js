    var image_hander = 0;
    $("#filelist").dragsort({dragSelector: "li", dragBetween: true, placeHolderTemplate: "<li class='placeHolder'></li>" });
    $(function() {
        var $ = jQuery, // just in case. Make sure it's not an other libaray.
            $wrap = $('#uploader'),
            // 图片容器
            $queue = $(".filelist").eq(0),
            // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find('.statusBar'),
            // 文件总体选择信息。
            $info = $statusBar.find('.info'),
            // 上传按钮
            $upload = $wrap.find('.uploadBtn'),
            // 没选择文件之前的内容。
            $placeHolder = $wrap.find('.placeholder'),
            // 总体进度条
            //$progress = $statusBar.find('.progress').hide(),
            // 添加的文件数量
            fileCount = 0,
            // 添加的文件总大小
            fileSize = 0,
            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,
            // 缩略图大小
            thumbnailWidth = 110 * ratio,
            thumbnailHeight = 110 * ratio,
            // 可能有pedding, ready, uploading, confirm, done.
            state = 'pedding',

            // 所有文件的进度信息，key为file id
            percentages = {},
            //错误信息
            errorText = "",
            // 判断浏览器是否支持图片的base64
            isSupportBase64 = ( function() {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function() {
                    if( this.width != 1 || this.height != 1 ) {
                        support = false;
                    }
                }
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            } )(),
         // 检测是否已经安装flash，检测flash的版本
            flashVersion = ( function() {
                var flashVer = NaN;
                var ua = navigator.userAgent;

                if ( window.ActiveXObject ) {
                    var swf = new ActiveXObject( 'ShockwaveFlash.ShockwaveFlash' );

                    if ( swf ) {
                        flashVer = Number( swf.GetVariable( '$version' ).split(' ')[ 1 ].replace(/\,/g, '.').replace( /^(\d+\.\d+).*$/, '$1') );
                    }
                }
                else {
                    if ( navigator.plugins && navigator.plugins.length > 0 ) {
                        var swf = navigator.plugins[ 'Shockwave Flash' ];

                        if ( swf ) {
                            var arr = swf.description.split(' ');
                            for ( var i = 0, len = arr.length; i < len; i++ ) {
                                var ver = Number( arr[ i ] );

                                if ( !isNaN( ver ) ) {
                                    flashVer = ver;
                                    break;
                                }
                            }
                        }
                    }
                }

                return flashVer;
            } )(),
            supportTransition = (function(){
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                s = null;
                return r;
            })(),
         // WebUploader实例
            uploader; 
        if (!WebUploader.Uploader.support()) {
            ygdg.dialog.alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
            throw new Error('WebUploader does not support the browser you are using.');
        }
        // 实例化
        uploader = WebUploader.create({
            pick: {
                id: '#filePickerBatch',
                label: '点击选择图片'
            },
            formData:{
            	catalogId:"0"
            },
            dnd: '#dndArea',
            paste: '#uploader',
            accept: {
                title: 'Images',
                extensions: 'jpg',
                mimeTypes: 'image/*'
            },
            // swf文件路径
            swf: basePath+'/webuploader/Uploader.swf',
            //禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
            disableGlobalDnd: true,
            chunked: false,
            chunkSize: 512 * 1024,
            server: basePath+'/picture/imageManageUpload.sc',
            fileNumLimit: 100,
            fileSingleSizeLimit: 1024 * 1024, // 1 M
            compress:false  //压缩
        });
        
        // 拖拽时不接受 js, txt 文件。
        uploader.on( 'dndAccept', function( items ) {
            var denied = false,
                len = items.length,
                i = 0,
                // 修改js类型
                unAllowed = 'text/plain;application/javascript ';

            for ( ; i < len; i++ ) {
                // 如果在列表里面
                if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                    denied = true;
                    break;
                }
            }
            return !denied;
        });
        
        uploader.on( 'uploadBeforeSend', function( block, data ) {
            data.catalogId = imageManageUpload.catalogId;
        });
        
        // 添加“添加文件”的按钮，
        uploader.addButton({
            id: '#filePicker2',
            label: '继续添加'
        });
        
        uploader.on('ready', function() {
            window.uploader = uploader;
        });

        // 当有文件添加进来时执行，负责view的创建
        function addFile(file) {
        	var index = $(".filelist").eq(0).find("li").length;
            var $li = $('<li title="拖拽图片可以改变顺序" id="' + file.id + '" num="'+index+'">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>' +
                    '<p class="progress"><span></span></p>' +
                    '</li>'),
                    $btns = $('<div class="file-panel">' +
                    '<span class="cancel" title="删除">删除</span></div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find('p.imgWrap'),
                $info = $('<p class="error"></p>'),

                showError = function(code) {
                    switch (code) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text(text).appendTo($li);
                };

            if (file.getStatus() === 'invalid') {
                showError(file.statusText);
            } else {
                // @todo lazyload
                $wrap.text('预览中');
                uploader.makeThumb(file, function(error, src) {
                    if (error) {
                        $wrap.text('不能预览');
                        return;
                    }
                    if( isSupportBase64 ) {
                        img = $('<img src="'+src+'" class="desc-image">');
                        $wrap.empty().append( img );
                    }
                }, thumbnailWidth, thumbnailHeight);

                percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            }

            file.on('statuschange', function(cur, prev) {
                if (prev === 'progress') {
                    $prgress.hide().width(0);
                }
                // 成功
                if ( cur === 'error' || cur === 'invalid' ) {
                   // console.log( file.statusText );
                    showError( file.statusText );
                    percentages[ file.id ][ 1 ] = 1;
                } else if ( cur === 'interrupt' ) {
                    showError( 'interrupt' );
                } else if ( cur === 'queued' ) {
                    percentages[ file.id ][ 1 ] = 0;
                } else if ( cur === 'progress' ) {
                    $info.remove();
                    $prgress.css('display', 'block');
                } 
                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });

            $li.on('mouseenter', function() {
                //$btns.stop().animate({
                //    height: 30
                //});
            });

            $li.on('mouseleave', function() {
                //$btns.stop().animate({
                 //   height: 0
                //});
            });

            $btns.on('click', 'span', function() {
                var index = $(this).index(),
                    deg;
                var num = parseInt($li.attr("num"));
                switch (index) {
                case 0:
                    uploader.removeFile( file );
                    $li.remove();
                    if($(".filelist").find("li").length>0){
                   	 var $wraptemp = $('#uploader'),
                   	 $placeHolderTemp = $wraptemp.find('.placeholder' ),
               		 $statusBarTemp = $wraptemp.find( '.statusBar' );
               		 $placeHolderTemp.addClass( 'element-invisible' );
               		 $statusBarTemp.removeClass( 'element-invisible' );
               		 $statusBarTemp.show();
               		 $(".filelist").show();
               		 uploader.refresh();
                    }
	               	//file.setStatus("cancelled");
	               	//$li.remove();
                   return;
               case 1:
            	   if(num==0){
                   	return;
                   }
               	 	var curImg = $wrap.find("img").eq(0);
                    var curImgSrc = curImg.attr("src");
                    var curRealImgSrc = curImg.attr("realImg");
                    var preImg = $(".filelist").find("li").eq(num-1).find( 'p.imgWrap').find("img").eq(0);
                    curImg.attr("src",preImg.attr("src"));
                    curImg.attr("realImg",preImg.attr("realImg"));
                    preImg.attr("src",curImgSrc);
                    preImg.attr("realImg",curRealImgSrc);
                   break;
               case 2:
               		if(num==($(".filelist").find("li").length-1)){
                   	return;
                   }
               		var curImg = $wrap.find("img").eq(0);
                    var curImgSrc = curImg.attr("src");
                    var curRealImgSrc = curImg.attr("realImg");
                    var nextImg = $(".filelist").find("li").eq(num+1).find( 'p.imgWrap').find("img").eq(0);
                    curImg.attr("src",nextImg.attr("src"));
                    curImg.attr("realImg",nextImg.attr("realImg"));
                    nextImg.attr("src",curImgSrc);
                    nextImg.attr("realImg",curRealImgSrc);
                   break;
                }
                if (supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                	$wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                }
            });
            $li.appendTo($queue);
        }

        // 负责view的销毁
        function removeFile(file) {
            //var $li = $('#' + file.id);
           // delete percentages[file.id];
           // updateTotalProgress();
            //$li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            //var loaded = 0,
            //    total = 0,
            //    spans = $progress.children(),
            //    percent;

            //$.each(percentages, function(k, v) {
            //    total += v[0];
            //    loaded += v[0] * v[1];
            //});

        	//percent = total ? loaded / total : 0;

            //spans.eq(0).text(Math.round(percent * 100) + '%');
            //spans.eq(1).css('width', Math.round(percent * 100) + '%');
            //updateStatus();
        }

        function updateStatus() {
            /*        var text = '',
                        stats;

                    if (state === 'ready') {
                        text = '选中' + fileCount + '张图片，共' +
                            WebUploader.formatSize(fileSize) + '。';
                    } else if (state === 'confirm') {
                        stats = uploader.getStats();
                        if (stats.uploadFailNum) {
                            text = '已成功上传' + stats.successNum + '张照片至XX相册，' +
                                stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                        }

                    } else {
                        stats = uploader.getStats();
                        text = '共' + fileCount + '张（' +
                            WebUploader.formatSize(fileSize) +
                            '），已上传' + stats.successNum + '张';

                        if (stats.uploadFailNum) {
                            text += '，失败' + stats.uploadFailNum + '张';
                        }
                    }

                    $info.html(text);*/
        }

        function setState(val) {
            var file, stats;
            if (val === state) {
                return;
            }

            $upload.removeClass('state-' + state);
            $upload.addClass('state-' + val);
            state = val;

            switch (state) {
                case 'pedding':
                    $placeHolder.removeClass('element-invisible');
                    $queue.hide();
                    $statusBar.addClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'ready':
                    $placeHolder.addClass('element-invisible');
                    $('#filePicker2').removeClass('element-invisible');
                    $queue.show();
                    $statusBar.removeClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'uploading':
                    $('#filePicker2').addClass('element-invisible');
                    //$progress.show();
                    $upload.text('暂停上传');
                    break;

                case 'paused':
                    //$progress.show();
                    $upload.text('继续上传');
                    break;

                case 'confirm':
                    //$progress.hide();
                    $upload.text('开始上传').addClass('disabled');
                    stats = uploader.getStats();
                    if (stats.successNum && !stats.uploadFailNum) {
                        setState('finish');
                        return;
                    }
                    break;
                case 'finish':
                    stats = uploader.getStats();
                    if (stats.successNum) {
                        ygdg.dialog.alert('上传成功');
                    } else {
                        // 没有成功的图片，重设
                        state = 'done';
                        location.reload();
                    }
                    $placeHolder.addClass('element-invisible');
                    $('#filePicker2').removeClass('element-invisible');
                    $upload.text('开始上传').removeClass('disabled');
                    break;
            }

            updateStatus();
        }

        uploader.onUploadProgress = function(file, percentage) {
            //var $li = $('#' + file.id),
            //    $percent = $li.find('.progress span');

            //$percent.css('width', percentage * 100 + '%');
           // percentages[file.id][1] = percentage;
           // updateTotalProgress();
        };

        uploader.on('uploadSuccess', function(file, response) {
            $('#' + file.id).find(".progress").hide();
            if (response.success == true) {
                //为图片添加选中事件
                var span = $('<span class="check checked" checkType="true"></span>').appendTo($("#"+file.id));
                 span.click(function() {
                                if ($(this).attr("checkType") == "false") {
                                	$(this).removeClass("nocheck");
                    		  		$(this).addClass("checked");
                    		  		$(this).attr("checkType","true");
                                } else {
                                	$(this).removeClass("checked");
                    		  		$(this).addClass("nocheck");
                    		  		$(this).attr("checkType","false");
                                }
                            });
                var $img = $("#" + file.id).find(".imgWrap").eq(0).find("img");
                var imgUrl = response.message;
                var smallUrl = getSmallImage(imgUrl);
                $img.attr("src", smallUrl).attr("realImg", imgUrl).attr("uploadSuccess", true);
            } else {
                $("#" + file.id).attr("class", "state-error");
                $("#" + file.id).append('<p class="error">上传失败</p>');
                if (response.message == '1') {
                    //ygdg.dialog.alert(file.name+',request请求参数no为空值,请检查!');
                    errorText = errorText + file.name + ',request请求参数no为空值,请检查!<br>';
                } else if (response.message == '2') {
                    //ygdg.dialog.alert(file.name+',获取登录会话信息失败,请尝试重新登录操作!');
                    errorText = errorText + file.name + ',获取登录会话信息失败,请尝试重新登录操作!<br>';
                } else if (response.message == '3') {
                    //ygdg.dialog.alert(file.name+',商品图片大小超过了 500 KB');
                    errorText = errorText + file.name + ',商品图片大小超过了 1024 KB!<br>';
                } else if (response.message == '4') {
                    //ygdg.dialog.alert(file.name+',商品图片分辨率不符合  740-790px * 740-790px的规格');
                    errorText = errorText + file.name + ',商品图片分辨率不符合  740-790px * 10-9999px的规格<br>';
                } else if (response.message == '5') {
                    //ygdg.dialog.alert(file.name+',图片校验异常');
                    errorText = errorText + file.name + ',商品图片分辨率不符合  740-790px * 10-9999px的规格<br>';
                } else if (response.message == '6') {
                    //ygdg.dialog.alert(file.name+',上传图片失败, 请重新再试！');
                    errorText = errorText + file.name + ',上传图片失败, 请重新再试<br>';
                } else if (response.message == '7') {
                    //ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
                    errorText = errorText + file.name + ',上传图片失败,获取不到图像对象，请重试<br>';
                } else if (response.message == '8') {
                    //ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
                    errorText = errorText + file.name + ',上传图片失败,文件名不能超过64位，请重试<br>';
                } else {
                    //ygdg.dialog.alert(file.name+",图片上传失败，请重新上传，如有再问题请联系管理员！");
                    errorText = errorText + file.name + ',图片上传失败，请重新上传，如再有问题请联系管理员<br>';
                }
            }
        });

        uploader.on('uploadFinished', function() {
            if (errorText != "") {
                ygdg.dialog.alert(errorText);
            }
            errorText = "";
        });

        uploader.onFileQueued = function(file) {
            fileCount++;
            fileSize += file.size;

            if (fileCount === 1) {
                $placeHolder.addClass('element-invisible');
                $statusBar.show();
            }

            addFile(file);
            setState('ready');
            updateTotalProgress();
        };

        uploader.onFileDequeued = function(file) {
            fileCount--;
            fileSize -= file.size;

            if (!fileCount) {
                setState('pedding');
            }

            removeFile(file);
            updateTotalProgress();

        };

        uploader.on('all', function(type) {
            var stats;
            switch (type) {
                case 'uploadFinished':
                    setState('confirm');
                    break;

                case 'startUpload':
                    setState('uploading');
                    break;

                case 'stopUpload':
                    setState('paused');
                    break;

            }
        });

        uploader.onError = function(code) {
            //ygdg.dialog.alert('Eroor: ' + code);
            if (code == "F_EXCEED_SIZE") {
                ygdg.dialog.alert("图片超出指定大小");
            } else if (code == "F_DUPLICATE") {
                ygdg.dialog.alert("图片重复添加");
            } else {
                ygdg.dialog.alert('Eroor: ' + code);
            }
        };

        $upload.on('click', function() {
            if ($(this).hasClass('disabled')) {
                return false;
            }

            if (state === 'ready') {
                uploader.upload();
            } else if (state === 'paused') {
                uploader.upload();
            } else if (state === 'uploading') {
                uploader.stop();
            }
        });

        $info.on('click', '.retry', function() {
            uploader.retry();
        });

        $info.on('click', '.ignore', function() {
            ygdg.dialog.alert('todo');
        });

        $upload.addClass('state-' + state);
        updateTotalProgress();

        //全选
        $("#selectAll").live("click", function() {
            var lis = $(".filelist li");
            if ($(this).prop("checked")) {
                lis.find("span.check").removeClass("nocheck");
                lis.find("span.check").addClass("checked");
            } else {
                lis.find("span.check").removeClass("checked")
                lis.find("span.check").addClass("nocheck");
            }
        });

        //清空已经上传
        $("#cancelBtn").live('click', function() {
            var $lis = $(".filelist").find("li.state-complete");
            $lis.each(function(index, item) {
                $lis.eq(index).remove();
                uploader.removeFile($lis.eq(index).attr("id"), true);
            });
        });

        //插入图片
        $("#insertImgBtn").live('click', function() {
            var urlStrs = "";
            var images = $(".filelist li.state-complete");
            images.each(function(index, item) {
                var checkSpan = $(this).find("span.checked");
                if (checkSpan.length > 0) {
                    checkSpan.removeClass("checked").addClass("nocheck").attr("checkType", false);
                    if (urlStrs == '') {
                        urlStrs += $(this).find("p.imgWrap img").attr("realimg");
                    } else {
                        urlStrs += '&&&&&' + $(this).find("p.imgWrap img").attr("realimg");
                    }
                }
            });
            if (urlStrs == "") {
                ygdg.dialog.alert('请先上传到服务器,并选择要插入的图片');
                return;
            }
            onImgSelected.call(this, urlStrs);
            $("#selectAll").prop('checked', false);
        });

        $(document).live('.check', function() {
            var ckt = $(this).attr("checkType");
            if (ckt == undefined || ckt === 'false') {
                $(this).removeClass("nocheck").addClass("checked").attr("checkType", true);
            } else {
                $(this).removeClass("checked").addClass("nocheck").attr("checkType", false);
            }
        });
    });
