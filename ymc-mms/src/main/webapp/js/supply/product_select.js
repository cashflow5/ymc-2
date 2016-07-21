//全选反选
	$(document).ready(function(){
		$("#chktags").click(function(){			
			$(" :checkbox").each(function(){ 
				if($(this).attr("checked")) {
					$(this).attr("checked",false); 
				}else{
					$(this).attr("checked",true); 
				}
			});				
		})
		$("#chk").click(function(){
			if($(this).attr("checked")) {
				$(" :checkbox").each(function(){   
					$(this).attr("checked",true);   
				});
			}else{
				$(" :checkbox").each(function(){   
					$(this).attr("checked",false);   
				});
			}
		})
		
	});
	
	//复选框选中值的id
	function chkValues(){
		var chkVal="";
		$("input:checkbox[name=chkS]:checked'").each(function(){
			chkVal+=$(this).val()+" ";
		});
		return chkVal;
	}