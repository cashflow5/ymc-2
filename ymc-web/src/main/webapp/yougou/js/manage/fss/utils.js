    var Utils = {};
    Utils= {

        validator:{
	        isInt:function(_val){
	            var trmVal = $.trim(_val);
	            var len = trmVal.length ;
	            if(len != 0){
	                for( var loc=0; loc<len; loc++ ){
	                    if((trmVal.charAt(loc) < '0') || (trmVal.charAt(loc) > '9')) {
	                        return false;
	                    }
	                }   
	            }
	            return true;
	        }
        },

	    isIe6:function(){
	        return !-[1,]&&!window.XMLHttpRequest;
	    },

	    
	    changeCss:function(templateCode,styleCode){
             // var   path = "${BasePath}/assets/template/default/black/css/black.css";
    	    var path = '/fss/assets/template/'+templateCode+'/'+styleCode+'/css/'+styleCode+'.css';
    		var head = window.parent.document.getElementsByTagName('head')[0];
            var link = document.createElement('link');
            link.href = path;
            link.rel = 'stylesheet';
            link.type = 'text/css';
            head.appendChild(link);

	    },
	    
        alert: function(msg) {
            art.dialog({
                id: 'Alert',
                title: '提示',
                icon: 'warning',
                width: 300,
                height: 150,
                content: msg,
                cancel: true,
                cancelVal: '关闭'
            });
        },
        show:function(msg) {
    		art.dialog({
    			id: 'Alert',
    			title:'详情',
    			icon:'',
    			width:400,
    			height:200,
    			content:msg,
    			cancel:true,
    			cancelVal:'关闭'
    		});
    	},
        tips: function(msg, time) {
            var t = time || 1;
            artDialog.tips('<span class="cf00">' + msg + '</span>', t);
        },
        confirm: function(msg, yes, no) {
            artDialog.confirm(msg, yes, no);
        },

        updateMdl:function(data){
	        var mdl=parent.currMdl.find('.skin_box');
	        mdl.html(data);
	        var mdl_parent = mdl.parent();
	        if(mdl.hasClass("fss_custom_banner")){//店招调整
	        	//解决自定义内容会被撑大的问题
	        	var htmlOjb = $(data);
	        	if(htmlOjb.length > 0){
	        		var inputObj = htmlOjb.get(0);
	        		if(inputObj.value == "y"){
	        			mdl.css("overflow","");
	        		}else{
	        			mdl.css("overflow","hidden");
	        		}
	        	}
	        }else if(mdl_parent.hasClass("fss_pos_nav_mdl")){// 浮动侧边栏的位置调整
	            var alignClass = mdl.find(".skin_box_bd").attr("data-align");
	            if(alignClass){
	                mdl_parent.removeClass("fss_pos_lf").removeClass("fss_pos_rt").addClass(alignClass);
	                mdl_parent.attr("style","z-index: 2;");
	            }
	        }else if(mdl.hasClass("fss_main_slide")){
	            setTimeout(function(){
	                var heightVal = mdl.find("li img").eq(0).height();
	                ////console.info(mdl.find("li img").eq(0));
	//              if(heightVal.length == 0){
	//                  heightVal = 420;
	//              }
	                mdl.height(heightVal);
	                mdl.find(".js_slide_box").height(heightVal);    
	            },500);
	            //YGF.Module.Index.initLayout();
	        }else if(mdl.hasClass("fss_main_imgads")){
	        	var childDiv = mdl.find(".fss_img_superwide");
	        	if(childDiv.length > 0){
	        		var heightVal = childDiv.find("img").attr("height");
		        	if(heightVal){
		        		mdl.css("height", heightVal);
		        	}
	        	}
	        }
        },

        render: function(url, oper) {
            if (!url) {
                return;
            }
            var defaultOper = oper ||
            function(dataObj) {
                if (dataObj && dataObj.scode === "0") {
                	if (typeof(parent.rePublish) != 'undefined') {
    					parent.rePublish = true;
    				}
                    Utils.updateMdl(dataObj.data);
                    Utils.tips('处理成功[请记得重新发布该店铺]！', 3);
                    setTimeout(function() {
                        parent.parDesign.initLayout();
                        //YGF.Module.Index.initLayout();
                        art.dialog.close();
                    },
                    3000);
                } else {
                    Utils.utils.tips('处理失败，请重试或联系管理员', 3);
                }
            };
            $.getJSON(url + "&_t=" + (new Date()).getTime(), defaultOper);
        },

        //UUID
        uuid: function() {
            function S4() {
                return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
            };
            return S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4();
        },

        //关闭对话框
        closeDialog:function() {
            art.dialog.close();
        },

        //全选
        checkAll: function() {
            $('.ipt_check_all').live('click',
            function() {
                $(".ipt_check_one").attr("checked", $(this).attr("checked"));
            });
        },

        //是否显示标题
        displayTitle: function() {
            $('input[name=isTitleDisplay]').click(function() {
                var txt = $('#titleName');
                $(this).attr('id') == 'titleBlock' ? txt.show() : txt.hide();
            });
        },
      
        ajax_checkLayoutMax:function(storeId, callbackFun) {
            var result = null;
            var url =  "/fss/layout/checkMaxLayout.sc";
            $.post(url, {
                storeId: storeId
            },
            function(obj) {
                if (obj.scode && obj.scode == 0) {
                    result = "success";
                    // 回调插入方法
                    callbackFun(obj.data);
                } else {
                	alert(obj.smsg);
                    result = "failure";
                }
            },
            "json");
            return result;
         },

         //跳转  url请求链接  ，localurl需要跳转的链接
         skip:function(url,localurl){
        	// var localurl = window.location.href;
        	 localurl=localurl.replace(/\?/g,'&iexcl;').replace(/&/g,'&#38;').replace(/=/g,'&#61');
        	 if(url.indexOf('?')==-1){
        		 url+="?url="+localurl;
        	 }else{
        		 url+="&url="+localurl;
        	 }
        	 
        	 window.location= url;
        	 
        	 return false;
         },
      
        //insertLayout
        insertLayout: function() {
        	
        	art.dialog.data("act","add");
        	var storeId = $('#storeId').val();
         
        	Utils.ajax_checkLayoutMax(storeId,function(obj){
        		 art.dialog.open(global.base.baseRoot + '/layout/select.sc',{
         			width:450,
         			height:350,
         			title:'选择布局结构',
         			lock:true
         		});
		    });
        	
        	return false;
        },

        initUploadify: function() {
            moveImages = [];
            
            var uploadUrl = global.base.imgUploadUrl + "&cataLogId=" +  $("#js_upld_slt_fld").val() + "&t=" + (new Date()).getTime();
            
            $('#file_upload').uploadify({
                'swf': global.base.imgUploaderSWF,
                'uploader': uploadUrl,
                'auto': true,
                'debug': false,
                'progressData': 'speed',
                'buttonText': '选择文件',
                'cancelImg': global.base.imgUploaderCancelPng,
                'removeCompleted': false,
                //          'folder': '/uploads',
                'queueSizeLimit': 10,
                //          'simUploadLimit':5,
                //'fileSizeLimit':'301KB',
                'fileDataName ': "Filedata",
                'fileTypeExts': '*.jpg;*.png;*.gif',
                'fileTypeDesc': '图片格式',
                'multi': true,
                'onFallback': function() { //检测FLASH失败调用
                    alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
                },
                'onSelectError': function(file, errorCode, errorMsg) { //返回一个错误，选择文件的时候触发
                    switch (errorCode) {
                    case - 100 : alert("上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！");
                        break;
                        /*case -110:
            alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#file_upload').uploadify('settings','fileSizeLimit')+"大小！");
            break;*/
                    case - 120 : alert("文件 [" + file.name + "] 大小异常！");
                        break;
                    case - 130 : alert("文件 [" + file.name + "] 类型不正确！");
                        break;
                    }
                },
                'onSelect': function(queueDate) {
                    $('#file_upload-queue').addClass('sys_img_box');
                    var _id = queueDate.id;
                    var maxSize = 300;
                    var _size = queueDate.size / 1024;
                    var _fileNameHtml = $('#' + _id).find('.fileName').html();
                    if (_size > maxSize) {
                        //console.log("大小限制:"+_size);
                        var _closeBox = "<div class='cancel'>" + $('#' + _id).find('.cancel').html() + "</div>";
                        var _fileNameBox = "<span class='fileName'>" + _fileNameHtml + "</span>";
                        $('#' + _id).addClass('uploadify-error').empty().html(_closeBox + '<div class="uploadify-error-size">请将文件控制在300K内</div>' + _fileNameBox);
                    }
                    $('#' + _id).find('.fileName').attr('title', _fileNameHtml);

                },
                'onUploadSuccess': function(file, data, response) { //上传到服务器，服务器返回相应信息到data里
                    var dataObj = eval("(" + data + ")"); //转换为json对象
                    if (data) {
                        if (dataObj.scode && dataObj.scode == 0) {
                            var _data = dataObj.data;
                            var queueId = file.id;
                            var _box = $('#' + queueId).addClass('img_list');
                            //console.log(_data);
                            var _src = _data.fullPathMap.a;
                            var _title = _data.showName;
                            var _id = _data.id;
                            var _s = $('#uploadInfo').find('strong');
                            var _num = parseInt(_s.text());
                            _s.html(_num + 1);
                            $('#uploadInfo').show();
                            $('#sysMoveImg').show();
                            var _html = $('<a href="javascript:;" class="pic"><img id="' + _id + '" alt="" src="' + _src + '"></a>\<p class="ttl">' + _title + '</p>');
                            _box.html(_html);
                            moveImages.push(_id); //单个图片上传成功后加入到moveImages数组中,在切换tab时需要清空moveImages
                        } else if (dataObj.socde == -1) {}
                    }
                },
                'onQueueComplete': function(queueDate) //在队列中的文件上传完成后触发
                {
                    var _this = queueDate;
                    $('.go_upload').show();
                    $('.jsGoUpload').click(function() {
                    	 
                        $('#file_upload').css({
                            'visibility': 'visible',
                            'height': '100%',
                            'overflow': 'visible'
                        });
                        
                        $('.go_upload').hide();
                        $('#file_upload-queue').empty().removeClass('sys_img_box');
                        _this.files = {};
                    });
                     
                },
                'onUploadComplete': function(file) {
                  if(!$.browser.msie) {
                    $('#file_upload').css({
                      'visibility': 'hidden',
                      'height': '0',
                      'overflow': 'hidden'
                    });	 
                  }
                },
                'onUploadProgress': function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {},
                'onUploadStart': function(file) {}
            });

        },

        initKindEditor: function() {
            var editor;
            editor = KindEditor.create('textarea[name="content"]', {
                allowPreviewEmoticons: false,
                filterMode: true,
                imageUploadJson: 'kindeditor/php/upload_json.php',
                resizeType: 0,
                allowFileManager: true,
                useContextmenu: false,
                htmlTags: {
                    map: ['name', 'id'],
                    area: ['shape', 'coords', 'href', 'target', 'title'],
                    font: ['color', 'size', 'face', '.background-color'],
                    span: ['style'],
                    div: ['class', 'align', 'style'],
                    table: ['class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'style'],
                    'td,th': ['class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor', 'style'],
                    a: ['class', 'href', 'target', 'name', 'style', 'onclick'],
                    embed: ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', 'style', 'align', 'allowscriptaccess', '/'],
                    img: ['src', 'width', 'height', 'border', 'alt', 'title', 'align', 'style', '/', 'usemap'],
                    hr: ['class', '/'],
                    br: ['/'],
                    'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6': ['align', 'style'],
                    'tbody,tr,strong,b,sub,sup,em,i,u,strike': []
                },
                items: ['source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist', 'insertunorderedlist', 'table', '|', 'image', 'flash', 'link'],
                afterCreate: function() {

                }
            });
            setTimeout(function() {
                editor.focus();
            },
            500);
            KindEditor.plugin('image',
            function(K) {

                var editor = this,
                name = 'image';
                // 点击图标时执行
                editor.clickToolbar(name,
                function() {
                    module.exports.selectorImgs.select(function(data) {
                        editor.insertHtml('<img src=' + data.originalSrc + ' />');
                    });
                    return false;
                });
            });
            return editor;
        },

        sysTab: function() {
            $(".sys_tab .nav li").click(function() {
                var _index = $(this).index();
                $(this).addClass('curr').siblings().removeClass('curr');
                $(".sys_tab .pannel").addClass('none').eq(_index).removeClass('none');
                var _goUpload = $('.go_upload');
                if (_goUpload.length > 0) {
                    if (_index == 0) {
                        $('#file_upload').uploadify('cancel', '*');
                        $('#file_upload').css({
                            'visibility': 'visible',
                            'height': '100%',
                            'overflow': 'visible'
                        });
                        $('#file_upload-queue').empty().removeClass('sys_img_box');
                    }
                    _goUpload.hide();
                }
            });
        },

        selectorImgs: {
            select: function(f) {
                parent.imgSelectorCallBack = function(d) {
                    f(d);
                };
                art.dialog.open(global.base.imgWareHouseUrl, {
                    width: 950,
                    height: 700,
                    title: '选择图片',
                    lock: true
                });
            }
        },

        getSaleSiftGoodsList:function(param){
	        art.dialog.open(global.base.baseRoot + '/saleRank/select.sc?'+param,{
	            width:900,
	            height:650,
	            title:'筛选结果',
	            lock:true
	        });
         },

        //序列化表单为json
        serializeObject: function(form) {
            var obj = {};
            $.each(form.serializeArray(),
            function(index) {
                if (obj[this["name"]]) {
                    obj[this["name"]] = obj[this["name"]] + "," + this["value"];
                } else {
                    obj[this["name"]] = this["value"];
                }
            });
            return obj;
        },
        resetShopUrl: function(shopUrl) { // TODO 重置shopUrl
            $("#shopUrl").html(shopUrl);
        },
        resetShopUrl: function(pic_thumbnail, tplCode) { // TODO 重置模板图片
            $("#pic_thumbnail").attr("src", pic_thumbnail);
            $("#templateTypeCode").attr("value", tplCode);
        },
      /*  selectPic: function() {
            $('.img_list li').live('click',
            function() {
                var _this = $(this);
                _this.addClass('curr').siblings().removeClass('curr');
                return false;
            });
        }*/
    };

    module.exports=Utils;
