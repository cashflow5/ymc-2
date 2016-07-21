$(document).ready(function(){
	//----[revert.js] begin
	revert_rule();
	preferentialTypeBind();
	joinMemberBind();
	joinProductBind();
	brandCatB2cBind();
	//----[revert.js] end
});

function getIsDiscount()
{
	if($("#isDiscount").attr("checked"))
	{
		$('#isDiscount_').val(1);
	}
	else
	{
		$('#isDiscount_').val(0);
	}
}