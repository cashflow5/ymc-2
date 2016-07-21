G.Sale = {
    sidebarMenu: function() {
       var ul = $('.sub-menu'),
           editor = $('.editor'),
           save = $('.save');
        ul.on('mouseover mouseout', '.sub-menu-item', function(event) { //设置下拉框的hover事件，添加背景高亮显示
            if (event.type == "mouseover") {
                var $this=$(this),
                    a = $this.find('a'),
                    editor = $this.find('.editor'),
                    del = $this.find('.del');
                if(!a.hasClass('hide')){
                    editor.removeClass('hide');
                    del.removeClass('hide');
                }
            } else {
                var $this=$(this),
                    a = $this.find('a'),
                    editor = $this.find('.editor'),
                    del = $this.find('.del');
                if(!a.hasClass('hide')){
                    editor.addClass('hide');
                    del.addClass('hide');
                }      
            }
        }); 

        //编辑按钮
        ul.on('click','.editor',function(){
            var $this = $(this),
                save = $this.siblings('.save'),
                del = $this.siblings('.del'),
                input = $this.parent().siblings('.add_val'),
                a = $this.parent().siblings('a');
            save.removeClass('hide');
            del.addClass('hide');
            $this.addClass('hide');
            input.removeClass('hide').focus();
            a.addClass('hide');
            $this.parent().addClass('mt3');
        })

        //保存按钮
        ul.on('click','.save',function(event) {
        	
            var $this = $(this),
                input = $this.parent().siblings('.add_val'),
                new_val = input.val(),
                inputUpdate = $this.parent().siblings('#removeId'),
                updateId = inputUpdate.val();
            	if(updateId==undefined){
            		updateId='';
            	}
            	if(new_val=='' || new_val==undefined){
            		ygdg.dialog.alert("请入收藏夹名称");
            		return;
            	}
                a = $this.parent().siblings('a');
	            $.ajax({
					type: 'post',
					url: '/favoriteClassifyController/save.sc',
					dataType: 'html',
					data: 'favoriteId=' +updateId+'&favoriteName='+new_val ,
					beforeSend: function(jqXHR) {
					},
					success: function(data, textStatus, jqXHR) {
						 inputUpdate.val(data);
						 if(data==-1){
							 ygdg.dialog.alert("新增收藏夹不能超过10个");
						 }else{
							
					                $this.addClass('hide');
					                input.addClass('hide');
					                a.removeClass('hide');
					                a.html(new_val);
					                $this.parent().removeClass('mt3');   
							 ygdg.dialog.alert("保存收藏夹成功");
						 }
						
					},
					complete: function(jqXHR, textStatus) {
					},
					error: function(jqXHR, textStatus, errorThrown) {
						ygdg.dialog.alert('保存收藏夹失败');
					}
				});
           
            
        });

        //删除按钮
        ul.on('click', '.del', function(event) {
            var $this=$(this),
            input = $this.parent().siblings('#removeId'),
            new_val = input.val();
            
            var exDialog = ygdg.dialog({
        		id:'waitExport',
        		content:'确定要删除收藏夹吗？',
        		title:'提示',
        		button:[{
        				name:'确定',
        				callback:function(){ 
        					  $.ajax({
        							type: 'post',
        							url: '/favoriteClassifyController/delete.sc',
        							dataType: 'json',
        							async: false,
        							data: 'favoriteId=' +new_val ,
        							beforeSend: function(jqXHR) {
        							},
        							success: function(data) {
        								
        							
        								ygdg.dialog.alert("删除成功");
        								
        								
        							},
        							complete: function(jqXHR, textStatus) {
        							},
        							error: function(jqXHR, textStatus, errorThrown) {
        								alert('删除失败');
        							}
        						});
        					  $("#favorite_classify_id").val("");
        					  qcFormSubmit();
        			         //   $this.parent().parent().remove();
        			         

        				}
        			},{
        				name:'取消',
        				callback:function(){
        				}
        			}]
        	});
	          
        });

        //添加按钮
        ul.on('click','.add-sub-menu', function(event) {
            var $this = $(this);
            $this.before('<li class="sub-menu-item"><a class="hide" href="#">请输入名称</a><input class="add_val" type="text"   maxlength="15"  value=""/><input id="removeId" type="hidden" value=""/><span class="iconfont fr Gray mt3"><i class="save">&#xe61f;</i><i class="editor hide">&#xe620;</i><i class="del ml5 hide">&#xe629;</i></span></li>');
            $('.add_val').focus();
        });
    },
    allCheckbox:function(object,single_object){
        $(object).click(function(event) {
            var $this = $(this),
                single_checkbox = $(single_object),
                all_prop=$this.prop('checked');
            if(all_prop){
                single_checkbox.prop("checked",true);
            }else{
                single_checkbox.prop("checked",false);
            }  
        });
    },
    dataTime:function(id,id_val){
        $(id).click(function(event) {
            var $this=$(this),
                id_str=$this.attr("id"),
                arr=id_str.split('-');
            if($this.hasClass('starttime')){
                WdatePicker({
                    maxDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\')}'
                })
            }else{
                WdatePicker({
                    minDate:'#F{$dp.$D(\'starttime-'+arr[1]+'\')}'
                })                  
            }
        });
    },
    search:function(){//搜索框，按钮，聚焦的时候字体消失
        var yg_search=$('.yg-search-input');
       // alert("bbbbbbbbbbbbbbbbb");
        yg_search.focus(function(){
        //	alert("ssssssss"+$(this).val());
           if( $(this).val()==$(this)[0].defaultValue){
        	                    
        	  if($(this).val()=='请输入商品名称或商品编码'){
        		  $(this).val("");
        	  }else{
        		///  $(this).val($(this)[0].defaultValue);
        	  }
           }
        }).blur(function(event) {
            if($(this).val()==""){
                $(this).val("请输入商品名称或商品编码");
            }
        });
    }
}

$(function() {

    G.Sale.sidebarMenu();//左侧菜单栏，添加、删除、修改
    G.Sale.allCheckbox('#all_checkbox','.single_checkbox');//全选功能

    // 日期函数调用
    G.Sale.dataTime('.starttime');
    G.Sale.dataTime('.endtime');
    //点击搜索框按钮
    G.Sale.search();
});

/*function   qcFormSubmit1() {
	alert("bbbbbbbbbbbbbb");
    var categoryList=$("#brandNo").val()+','+$("#category1").val()+','+$("#category2").val()+','+$("#category3");
    
    $("#favorite_category_list").val(categoryList);
    
	var queryForm = $("#queryForm").attr("action", basePath + "/favoriteClassifyController/queryFavoriteClassifyInfo.sc");
	
	$("#commodityCode").val($("#commodityCode").val())
	queryForm.submit();
}*/
