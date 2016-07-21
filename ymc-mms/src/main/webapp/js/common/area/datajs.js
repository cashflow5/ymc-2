function areaInit(obj_1,val_1,obj_2,val_2,obj_3,val_3){
	
	//定义默认数据
	var ar = ["请选择省份","请选择城市","请选择区县"];
	var pindex=-1;
	var cindex=-1;
	
	//初始化
	$("<option value='-1'>"+ar[0]+"</option>").appendTo($("#"+obj_1));
	$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#"+obj_2));
	$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
	
	//初始化obj_1
	//alert("EEEEEEEEEEEEEE"+val_1);
	for (i=0;i<mp.length;i++){
		
		if (i==(val_1-1)){
			pindex = i;
			$("<option selected value='"+(i+1)+"'>"+mp[i]+"</option>").appendTo($("#"+obj_1));
		}else{
			$("<option value='"+(i+1)+"'>"+mp[i]+"</option>").appendTo($("#"+obj_1));
		}
	}

	if (pindex!=-1){
		for (n=0;n<mc[pindex].length;n++){
			if (n==(val_2-1)){
				cindex = n;
				$("<option selected value='"+(n+1)+"'>"+mc[pindex][n]+"</option>").appendTo($("#"+obj_2));
			}else{						
				$("<option value='"+(n+1)+"'>"+mc[pindex][n]+"</option>").appendTo($("#"+obj_2));
			}			
		}		
	}

	if (cindex!=-1){
		for (m=0;m<mh[pindex][cindex].length;m++){
			if (m==(val_3-1)){
					$("<option selected value='"+(m+1)+"'>"+mh[pindex][cindex][m]+"</option>").appendTo($("#"+obj_3));
				}else{						
					$("<option value='"+(m+1)+"'>"+mh[pindex][cindex][m]+"</option>").appendTo($("#"+obj_3));
				}	
			}
	}
		
		
	//响应obj_1的change事件	
	$("#"+obj_1).change(function(){
		//获取索引
		pindex = $("#"+obj_1).get(0).selectedIndex-1;
		//清空c和h
		$("#"+obj_2).empty();
		//重新给c填充内容
		$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#"+obj_2));
			if (pindex!=-1){
				for (k=0;k<mc[pindex].length;k++){
					$("<option value='"+(k+1)+"'>"+mc[pindex][k]+"</option>").appendTo($("#"+obj_2));
				}
			}	
		//清空h
		$("#"+obj_3).empty();
		$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
	});
	
	//响应obj_2的change事件	
	$("#"+obj_2).change(function(){
		cindex = $("#"+obj_2).get(0).selectedIndex-1;
		//清空h
		$("#"+obj_3).empty();
		//重新给h填充内容
		$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#"+obj_3));
		if (cindex!=-1){
			for (j=0;j<mh[pindex][cindex].length;j++){
				$("<option value='"+(j+1)+"'>"+mh[pindex][cindex][j]+"</option>").appendTo($("#"+obj_3));
			}
		}
	});
	
}


