
function brandInit(path,obj_1,val_1,obj_2,val_2,obj_3,val_3,obj_4,val_4){
	//定义默认数据
	var ar = ["请选择一级分类","请选择二级分类","请选择三级分类","请选择品牌"];
	//初始化
	$("<option value='-1'>"+ar[0]+"</option>").appendTo($("#"+ obj_1));
	$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#"+ obj_2));
	$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+ obj_3));
	$("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+ obj_4));
	var url = path + "/wms/basedatamanager/CommonGoods/querybrandcategorybyLevl.sc";
	var data={
		"no":'0'
	};
		
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');  
		var node = eval("("+result+")");	
		for (i=0;i<node.length;i++){
			var catb2c = node[i];			
			if (catb2c.no == val_1 || catb2c.no ==('root-'+val_1)){
				$("<option selected value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_1));
			}else{
				$("<option value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_1));
			}
		}        
       
        selectValue = $("#"+obj_1).val();
        if(selectValue != -1){
            ajaxRequest(url,{"no":selectValue},function(result){
                if(!result) return ;
                
                var node = eval("("+result+")");
                
                //清空c和h
                $("#"+obj_2).empty();
                //重新给c填充内容
                $("<option value='-1'>"+ar[1]+"</option>").appendTo($("#"+obj_2));
                for (i=0;i<node.length;i++){
                    var catb2c = node[i];
                    if (catb2c.no == val_2 || catb2c.no ==('root-'+val_2)){
                        $("<option selected value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_2));
                    }else{
                        $("<option value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_2));
                    }
                }
                
                selectValue = $("#"+obj_2).val();
                if(selectValue != -1){
                    ajaxRequest(url,{"no":selectValue},function(result){
                        if(!result) return ;
                        var node = eval("("+result+")");
                        
                        //清空c和h
                        $("#"+obj_3).empty();
                        //重新给c填充内容
                        $("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
                            
                        for (i=0;i<node.length;i++){
                            var catb2c = node[i];
                            
                            if (catb2c.no == val_3 || catb2c.no ==('root-'+val_3)){
                                $("<option selected value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_3));
                            }else{
                                $("<option value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_3));
                            }
                        }
                        selectValue = $("#"+obj_3).val();
		                if(selectValue != -1){
                            var urlchange = path + "/wms/basedatamanager/CommonGoods/querycommoditybrandbybrandCategoryno.sc";
		                    ajaxRequest(urlchange,{"no":selectValue},function(result){
		                        if(!result) return ;
		                        var node = eval("("+result+")");
		                        
		                        //清空c和h
		                        $("#"+obj_4).empty();
		                        //重新给c填充内容
		                        $("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+obj_4));
		                            
		                        for (i=0;i<node.length;i++){
		                            var brand = node[i];
 		                            if (brand.brandCode == val_4 || brand.brandCode ==('root-'+ val_4)){
		                                $("<option selected value='"+brand.brandCode+"'>"+brand.brandName+"</option>").appendTo($("#"+obj_4));
		                            }else{
		                                $("<option value='"+brand.brandCode+"'>"+brand.brandName+"</option>").appendTo($("#"+obj_4));
		                            }
		                        } 
		                    });
		                }
                    });
                }
            });
        }
	});

    
	
	//响应obj_1的change事件	
	$("#"+obj_1).change(function(){
		
		var selectValue = $("#"+obj_1).val();
		if(selectValue == -1){
			$("#"+obj_2).empty();
			$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#"+obj_2));
			$("#"+obj_3).empty();
			$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
            $("#"+obj_4).empty();
            $("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+obj_4));
			return false;
		}
				
		ajaxRequest(url,{"no":selectValue},function(result){
			if(!result) return ;
			
			var node = eval("("+result+")");
			
			//清空c和h
			$("#"+obj_2).empty();
			$("#"+obj_3).empty();
			$("#"+obj_4).empty();
			//重新给c填充内容
			$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#"+obj_2));
			$("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+obj_4));	
			for (i=0;i<node.length;i++){
				var catb2c = node[i];
				$("<option value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_2));
			}
			
			//清空h
			$("#"+obj_3).empty();
			$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
		});
		
		
	});
	
	//响应obj_2的change事件	
	$("#"+obj_2).change(function(){
		
		var selectValue = $("#"+obj_2).val();
		if(selectValue == -1){
			$("#"+obj_3).empty();
			$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
			return false;
		}
		
		ajaxRequest(url,{"no":selectValue},function(result){
			if(!result) return ;
			
			var node = eval("("+result+")");
			
			//清空c和h
			$("#"+obj_3).empty();
			$("#"+obj_4).empty();
			//重新给c填充内容
			$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
			$("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+obj_4));	
			for (i=0;i<node.length;i++){
				var catb2c = node[i];
				$("<option value='"+catb2c.no+"'>"+catb2c.catName+"</option>").appendTo($("#"+obj_3));
			}
			
			
		});
		
	});
	
	//响应obj_3的change事件	
	$("#"+obj_3).change(function(){
		var selectValue = $("#"+obj_3).val();
		if(selectValue == -1){
			$("#"+obj_4).empty();
			$("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+obj_4));
			return false;
		}
		var url = path + "/wms/basedatamanager/CommonGoods/querycommoditybrandbybrandCategoryno.sc";
		ajaxRequest(url,{"no":selectValue},function(result){
			if(!result) return ;		
			var node = eval("("+result+")");
			
			//清空c和h
			$("#"+obj_4).empty();
			//重新给c填充内容
			$("<option value='-1'>"+ar[3]+"</option>").appendTo($("#"+obj_4));
			for (i=0;i<node.length;i++){
				var brand = node[i];
				$("<option value='"+ brand.brandCode+"'>"+ brand.brandName+"</option>").appendTo($("#"+obj_4));
			}
			
			
		});
		
	});
	
}


//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  success: callback
	});
}
