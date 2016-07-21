	$(function() {
		
			// 初始化所有的备注和小红旗
			var  $remarkTh = $('.orderSubNo');
			if($remarkTh){
				var orderNOs ;
				orderNOs = $('.orderSubNo').map(function(){ return $(this).val(); }).get().join(',');
				$.ajax({
					    async : true,
				        cache : false,
				        type : 'GET',
				        data:{orderNOs:orderNOs},
				        dataType : "json",
				        url :basePath+"/order/getRemarkDetailListAjax.sc",
				        success : function(data) {
				        	if( data && data.merchantOrderExpandList ){
						            var merchantOrderExpandList = data.merchantOrderExpandList;
						            for(var i=0,_len=merchantOrderExpandList.length;i<_len;i++){
						            	var merchantOrderExpand = merchantOrderExpandList[i];
						            	var markColor = merchantOrderExpand.markColor;
						            	var markNote = merchantOrderExpand.markNote;
						            	var order = merchantOrderExpand.orderSubId;
						            	var $this = $('#'+order);
						            	if($this){
						            		$this.find('.flag').addClass(markColor);
						            		$this.find('.flag_tip').html(markNote);
						            		$this.find('.markColor').val(markColor);
						            		$this.find('.markNote').val(markNote);
						            	}
						            }
					        }else{
					        	return;
				        	}
				        }
				       });
			}
		
	        $('.flag').click(function(){
	            var $this=$(this);
	            var orderSubId =    $this.siblings('.orderSubNo').val();
	            var originalColor = $this.siblings('.markColor').val();
           	    var originalNote  = $this.siblings('.markNote').val();
           	    
           	    var stringTemp = '<dl class="flagForm_dl clearfix"><dt class="label">标记：</dt><dd class="pt3">';
           	    // color 1
           	    stringTemp = stringTemp +  '<input type="radio" id="mark1" class="flagradio" name="mark" value="flag_red" ';
           	    if( originalColor&&originalColor=='flag_red' ){
           	    	stringTemp = stringTemp+'checked=checked /><label class="flaglabel flag flag_red" for="mark1"></label>';
           	    }else{
           	    	stringTemp = stringTemp+'/><label class="flaglabel flag flag_red" for="mark1"></label>';
           	    }
           	    
           	    // color 2
           	    stringTemp = stringTemp + '<input type="radio" id="mark2" class="flagradio" name="mark" value="flag_yellow" ';
           	    if( originalColor&&originalColor=='flag_yellow' ){
           	    	stringTemp = stringTemp+'checked=checked /><label class="flaglabel flag flag_yellow" for="mark2"></label>';
           	    }else{
           	    	stringTemp = stringTemp+'/><label class="flaglabel flag flag_yellow" for="mark2"></label>';
           	    }
           	    
           	    // color 3
           	    stringTemp = stringTemp +  '<input type="radio" id="mark3" class="flagradio" name="mark" value="flag_green" ';
           	    if( originalColor&&originalColor=='flag_green' ){
           	    	stringTemp = stringTemp+'checked=checked /><label class="flaglabel flag flag_green" for="mark3"></label>';
           	    }else{
           	    	stringTemp = stringTemp+'/><label class="flaglabel flag flag_green" for="mark3"></label>';
           	    }
           	    
           	    // color 4
           	    stringTemp = stringTemp +  '<input type="radio" id="mark4" class="flagradio" name="mark" value="flag_blue" ';
           	    if( originalColor&&originalColor=='flag_blue' ){
           	    	stringTemp = stringTemp+'checked=checked /><label class="flaglabel flag flag_blue" for="mark4"></label>';
           	    }else{
           	    	stringTemp = stringTemp+'/><label class="flaglabel flag flag_blue" for="mark4"></label>';
           	    }
           	    
           	    // color 5
           	    stringTemp = stringTemp +  '<input type="radio" id="mark5" class="flagradio" name="mark" value="flag_purple" ';
           	    if( originalColor&&originalColor=='flag_purple' ){
           	    	stringTemp = stringTemp+'checked=checked /><label class="flaglabel flag flag_purple" for="mark5"></label>';
           	    }else{
           	    	stringTemp = stringTemp+'/><label class="flaglabel flag flag_purple" for="mark5"></label>';
           	    }
           	    stringTemp = stringTemp + '</dd></dl>';
           	   
           	    stringTemp = stringTemp + '<dl class="flagForm_dl clearfix"><dt class="label">内容：</dt><dd><textarea class="flag_textarea" maxlength="200">'+originalNote+'</textarea></dd></dl>';
	            
           	    ygdg.dialog({
	                title:"添加备注信息",
	                content: stringTemp,
	                max:false,
	                min:false,
	                width: '420px',
	                height: '205px',
	                lock:true,
	                ok: function() {
		                var markColor= $('input[name="mark"]:checked').val();
		            	var markNote = $('.flag_textarea').val();
		            	 // 必填项校验
	            	    if( markColor==undefined  || markColor=="" ){
	            	    	ygdg.dialog.alert("请选择红旗");
	            	    	return false;
	            	    }
	            	    if( markNote==''||$.trim(markNote)=='' ){
	            	    	ygdg.dialog.alert("请输入备注");
	            	    	return false;
	            	    }
	            	    markNote =   $.trim(markNote);  
	                    //	ajax提交
	                    $.ajax({ type:"post",
	                   			 data:{orderSubId:orderSubId,
	                   			 		markColor:markColor,
	                   			 		markNote:markNote
	                   			 	   },
	                   			dataType : "json",
	                    		url:basePath+"/order/remarkOrder.sc", 
	                    		success:function(response){
	                    			if(response.result=='true'){
	                    				 $this.attr('class',"flag "+response.markColor);
	                    				 $this.siblings('.flag_tip').html(response.markNote);
	                    				 $this.siblings('.markColor').val(response.markColor);
	                    				 $this.siblings('.markNote').val(response.markNote);
	                    			}else{
	                    				ygdg.dialog.alert("备注失败");
	                    			}
	                   			}
	                    });
	                }
	            });
	        }).hover(function(){
	            var flag_class=$(this).attr('class');
	            var markNoteVal = $(this).siblings('.markNote').val();
	            if(flag_class.length!=4 && markNoteVal!='' && $.trim(markNoteVal)!=''){
	                $(this).siblings('.flag_tip').removeClass('hide');
	            }
	        },function(){
	            $(this).siblings('.flag_tip').addClass('hide');
	        })
	    });
	    