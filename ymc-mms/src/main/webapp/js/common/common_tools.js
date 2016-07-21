function clickShow(curObj){
		
		// 隐藏块展示和收起
			if($(curObj).attr("class")=="open"){
				$("#click_show").removeClass("open");
				$("#click_show").addClass("close");
				$("#click_show").text("[点此展开]");
				if($("#target_for_click_show") ){
					if( !$("#target_for_click_show").hasClass("hidden") ){
						$("#target_for_click_show").addClass("hidden");
					}
				}
				
			}else if($(curObj).attr("class")=="close"){
				$("#click_show").removeClass("close");
				$("#click_show").addClass("open");
				$("#click_show").text("[点此收起]");
				if($("#target_for_click_show") ){
					if( $("#target_for_click_show").hasClass("hidden") ){
						$("#target_for_click_show").removeClass("hidden");
					}
				}
			}
}