var uploader;
(function( $ ){
    // 当domReady的时候开始初始化
    $(function() {
        var $wrap = $('#uploader'),

            // 图片容器
            $queue = $(".filelist").eq(0),

            // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find( '.statusBar' ),

            // 文件总体选择信息。
            $info = $statusBar.find( '.info' ),

            // 上传按钮
            $upload = $wrap.find( '.uploadBtn' ),

            // 没选择文件之前的内容。
            $placeHolder = $wrap.find( '.placeholder' ),

          //  $progress = $statusBar.find( '.progress' ).hide(),

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
            errorText = "";
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

        if ( !WebUploader.Uploader.support() ) {
            alert( 'Web Uploader 不支持您的浏览器！');
            throw new Error( 'WebUploader does not support the browser you are using.' );
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
            swf: basePath+'/webuploader/Uploader.swf',
            chunked: false,
            chunkSize: 512 * 1024,
            // runtimeOrder: 'flash',
           // sendAsBinary: true,
            server: basePath+'/picture/imageManageUpload.sc',
            // server: 'http://liaoxuezhi.fe.baidu.com/webupload/fileupload.php',
            // server: 'http://www.2betop.net/fileupload.php',
            //

             accept: {
                 title: 'Images',
                 extensions: 'jpg,png',
                 mimeTypes: 'image/*'
             },

            // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
            disableGlobalDnd: true,
            fileNumLimit: 100,
            fileSingleSizeLimit: 1024 * 1024,    // 1M
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
        
        // uploader.on('filesQueued', function() {
        //     uploader.sort(function( a, b ) {
        //         if ( a.name < b.name )
        //           return -1;
        //         if ( a.name > b.name )
        //           return 1;
        //         return 0;
        //     });
        // });

        // 添加“添加文件”的按钮，
        uploader.addButton({
            id: '#filePicker2',
            label: '继续添加'
        });

        uploader.on('ready', function() {
            window.uploader = uploader;
        });

        // 当有文件添加进来时执行，负责view的创建
        function addFile( file ) {
        	var index = $(".filelist").eq(0).find("li").length;
            var $li = $( '<li title="拖拽图片可以改变顺序" id="' + file.id + '" num="'+index+'">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>'+
                    '<p class="progress"><span></span></p>' +
                    '</li>' ),
                 /*
                $btns = $('<div class="file-panel">' +
                    '<span class="cancel" title="删除">删除</span>' +
                    '<span class="oper_lf" title="向右移">向右移</span>' +
                    '<span class="oper_rt" title="向左移">向左移</span></div>').appendTo( $li ),
                    */
                    $btns = $('<div class="file-panel">' +
                            '<span class="cancel" title="删除">删除</span></div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>'),

                showError = function( code ) {
                    switch( code ) {
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

                    $info.text( text ).appendTo( $li );
                };

            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                // @todo lazyload
                $wrap.text( '预览中' );
                uploader.makeThumb( file, function( error, src ) {
                    var img;

                    if ( error ) {
                        $wrap.text( '不能预览' );
                        return;
                    }

                    if( isSupportBase64 ) {
                        img = $('<img src="'+src+'" class="desc-image">');
                        $wrap.empty().append( img );
                    }
                }, thumbnailWidth, thumbnailHeight );
                percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;
            }

            file.on('statuschange', function( cur, prev ) {
                if ( prev === 'progress' ) {
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
                /*
                else if ( cur === 'complete' ) {
                    $li.append( '<span class="success"></span>' );
                }*/

                $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            });

            $li.on( 'mouseenter', function() {
            	/*if($(this).attr("class")=="state-complete"){
            		$btns.find(".oper_lf").show();
            		$btns.find(".oper_rt").show();
            	}else{
            		$btns.find(".oper_lf").hide();
            		$btns.find(".oper_rt").hide();
            	}
            	//找到最后一个已经上传的图片
            	var lis = $(".filelist").find("li");
            	for(var i=0,_len=lis.length;i<_len;i++){
            		var uploadType = lis.eq(i).find(".success");
            		if(uploadType.length<=0){//找到第一个没上传的图片
            			if(i-1>=0){
            				lis.eq(i-1).find(".file-panel").eq(0).find(".oper_rt").hide();
            			}
            			break;
            		}
            	}*/
                //$btns.stop().animate({height: 30});
            });
            
            
            $li.on( 'mouseleave', function() {
               // $btns.stop().animate({height: 0});
            });

            $btns.on( 'click', 'span', function() {
                var index = $(this).index(),
                    deg;
                var num = parseInt($li.attr("num"));
                switch ( index ) {
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

                if ( supportTransition ) {
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

            $li.appendTo( $queue );
        }

        // 负责view的销毁
        function removeFile( file ) {
           var $li = $('#'+file.id);

           // delete percentages[ file.id ];
           // updateTotalProgress();
           // $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
           /* var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;

            $.each( percentages, function( k, v ) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            } );

            percent = total ? loaded / total : 0;

            spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
            spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
            updateStatus();*/
        }

        function updateStatus() {
            /*var text = '', stats;

            if ( state === 'ready' ) {
                text = '选中' + fileCount + '张图片，共' +
                        WebUploader.formatSize( fileSize ) + '。';
            } else if ( state === 'confirm' ) {
                stats = uploader.getStats();
                if ( stats.uploadFailNum ) {
                    text = '已成功上传' + stats.successNum+ '张图片，'+
                        stats.uploadFailNum + '张图片上传失败，<a class="retry" href="#">重新上传</a>失败图片'
                }

            } else {
                stats = uploader.getStats();
                text = '共' + fileCount + '张（' +
                        WebUploader.formatSize( fileSize )  +
                        '），已上传' + stats.successNum + '张';

                if ( stats.uploadFailNum ) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }

            $info.html( text );*/
        }

        function setState( val ) {
            var file, stats;

            if ( val === state ) {
                return;
            }

            $upload.removeClass( 'state-' + state );
            $upload.addClass( 'state-' + val );
            state = val;

            switch ( state ) {
                case 'pedding':
                    $placeHolder.removeClass( 'element-invisible' );
                    $queue.hide();
                    $statusBar.addClass( 'element-invisible' );
                    uploader.refresh();
                    break;

                case 'ready':
                    $placeHolder.addClass( 'element-invisible' );
                    $( '#filePicker2' ).removeClass( 'element-invisible');
                    $queue.show();
                    $statusBar.removeClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'uploading':
                    $( '#filePicker2' ).addClass( 'element-invisible' );
                   // $progress.show();
                    $upload.text( '暂停上传' );
                    break;

                case 'paused':
                   // $progress.show();
                    $upload.text( '继续上传' );
                    break;

                case 'confirm':
                    //$progress.hide();
                    $upload.text( '开始上传' ).addClass( 'disabled' );

                    stats = uploader.getStats();
                    if ( stats.successNum && !stats.uploadFailNum ) {
                        setState( 'finish' );
                        return;
                    }
                    break;
                case 'finish':
                    stats = uploader.getStats();
                    if ( stats.successNum ) {
                        ygdg.dialog.alert("上传成功");
                    } else {
                        // 没有成功的图片，重设
                        state = 'done';
                        location.reload();
                    }
                    $placeHolder.addClass( 'element-invisible' );
                    $( '#filePicker2' ).removeClass( 'element-invisible');
                    $upload.text( '开始上传' ).removeClass( 'disabled' );
                    break;
            }
            updateStatus();
        }
        uploader.onUploadProgress = function( file, percentage ) {
           /* var $li = $('#'+file.id),
                $percent = $li.find('.progress span');

            $percent.css( 'width', percentage * 100 + '%' );
            percentages[ file.id ][ 1 ] = percentage;
            updateTotalProgress();*/
        };
        uploader.on( 'uploadSuccess', function( file,response) {
        	$( '#'+file.id).find(".progress").hide();
        	if(response.success==true){
        		//为图片添加选中事件
        		var span = $('<span class="check checked" checkType="true"></span>').appendTo($("#"+file.id));
        		span.click(function(){
        			if($(this).attr("checkType")=="false"){
        				$(this).removeClass("nocheck");
        		  		$(this).addClass("checked");
        		  		$(this).attr("checkType","true");
        		  	}else{
        		  		$(this).removeClass("checked");
        		  		$(this).addClass("nocheck");
        		  		$(this).attr("checkType","false");
        		  	}
        		});
        		var $img = $("#"+file.id).find(".imgWrap").eq(0).find("img");
        		var imgUrl = response.message;
        		var smallUrl = getSmallImage(imgUrl);
        		$img.attr("src",smallUrl);
        		$img.attr("realImg",imgUrl);
        		$img.attr("uploadSuccess",true);
        	}else{
        		$("#"+file.id).attr("class","state-error");
        		$("#"+file.id).append( '<p class="error">上传失败</p>' );
            	if (response.message == '1') {
            		//ygdg.dialog.alert(file.name+',request请求参数no为空值,请检查!');
            		errorText =errorText+file.name+',request请求参数no为空值,请检查!<br>';
            	}else if (response.message == '2') {
            		//ygdg.dialog.alert(file.name+',获取登录会话信息失败,请尝试重新登录操作!');
            		errorText =errorText+file.name+',获取登录会话信息失败,请尝试重新登录操作!<br>';
            	}else if (response.message == '3'){ 
            		//ygdg.dialog.alert(file.name+',商品图片大小超过了 500 KB');
            		errorText =errorText+file.name+',商品图片大小超过了 1024 KB!<br>';
            	}else if (response.message == '4') {
            		//ygdg.dialog.alert(file.name+',商品图片分辨率不符合  740-790px * 740-790px的规格');
            		errorText =errorText+file.name+',商品图片分辨率不符合  740-790px * 10-9999px的规格<br>';
            	}else if (response.message == '5') {
            		//ygdg.dialog.alert(file.name+',图片校验异常');
            		errorText =errorText+file.name+',商品图片分辨率不符合  740-790px * 10-9999px的规格<br>';
            	}else if (response.message == '6') {
            		//ygdg.dialog.alert(file.name+',上传图片失败, 请重新再试！');
            		errorText =errorText+file.name+',上传图片失败, 请重新再试<br>';
            	}else if (response.message == '7') {
            		//ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
            		errorText =errorText+file.name+',上传图片失败,获取不到图像对象，请重试<br>';
            	}else if (response.message == '8') {
            		//ygdg.dialog.alert(file.name+',上传图片失败,获取不到图像对象，请重试!');
            		errorText =errorText+file.name+',上传图片失败,文件名不能超过64位，请重试<br>';
            	}else{
            		//ygdg.dialog.alert(file.name+",图片上传失败，请重新上传，如有再问题请联系管理员！");
            		errorText =errorText+file.name+',图片上传失败，请重新上传，如再有问题请联系管理员<br>';
            	}
            }
        });
        uploader.on( 'uploadFinished', function() {
        	if(errorText!=""){
        		ygdg.dialog.alert(errorText);
        	}
        	errorText = "";
        });
        
        uploader.onFileQueued = function( file ) {
            fileCount++;
            fileSize += file.size;

            if ( fileCount === 1 ) {
                $placeHolder.addClass( 'element-invisible' );
                $statusBar.show();
            }

            addFile( file );
            setState( 'ready' );
            updateTotalProgress();
        };

        uploader.onFileDequeued = function( file ) {
            fileCount--;
            fileSize -= file.size;

            if ( !fileCount ) {
                setState( 'pedding' );
            }

            removeFile( file );
            updateTotalProgress();

        };

        uploader.on( 'all', function( type ) {
            var stats;
            switch( type ) {
                case 'uploadFinished':
                    setState( 'confirm' );
                    break;

                case 'startUpload':
                    setState( 'uploading' );
                    break;

                case 'stopUpload':
                    setState( 'paused' );
                    break;

            }
        });
        uploader.onError = function( code ) {
        	if(code=="F_EXCEED_SIZE"){
        		ygdg.dialog.alert("图片超出指定大小");
        	}else if(code=="F_DUPLICATE"){
        		ygdg.dialog.alert("图片重复添加");
        	}else{
        		ygdg.dialog.alert( 'Eroor: ' + code );
        	}
        };

        $upload.on('click', function() {
            if ( $(this).hasClass( 'disabled' ) ) {
                return false;
            }

            if ( state === 'ready' ) {
                uploader.upload();
            } else if ( state === 'paused' ) {
                uploader.upload();
            } else if ( state === 'uploading' ) {
                uploader.stop();
            }
        });

        $info.on( 'click', '.retry', function() {
            uploader.retry();
        } );

        $info.on( 'click', '.ignore', function() {
            alert( 'todo' );
        } );

        $upload.addClass( 'state-' + state );
        updateTotalProgress();
    });
})( jQuery );