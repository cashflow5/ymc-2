/**
 *分类list的js
 */
/**
 * 分类删除的js判断
 */
function delCat(ids,lev,structName)
{	
	 $.ajax( {
			type : "POST",
			url : "checkHasCommodtiy.sc",
			data : {"value" : structName,"id":ids},
			dataType : "json",
			success : function(data) {
				if(data == "1"||undefined==data){
					alert("该分类正在使用中无法删除");
					return;
				}
				var message="确认删除吗？";
				if(confirm(message)){
					$("#catListForm").attr("action","d_cat_delCat.sc");
					$("#cid").remove();
					$("#catListForm").append("<input type='hidden' id='cid' name='id' value='"+ids+"'>");
					$("#catListForm").submit();
				}
			}
		});
}
//
//function del(ids,lev,){
//	if("3"!=lev){
//if(confirm("存在子分类，确认删除吗?")){
//	$("#catListForm").attr("action","d_cat_delCat.sc?id="+ids);
//	$("#catListForm").submit();
//}else{
//	return;
//}
//}
//}
