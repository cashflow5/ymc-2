var fo = new Fo();

//活动名称
function activeNameValidate(obj)
{
	fo.setConfig({"input":obj,"vType":1,"vLength":[1,30]});
	fo.validate();
	
	$("#activeName").blur(function()
	{
			fo.setConfig({"input":getByID('activeName'),"vType":2,"format":FoReg.activeName});
			fo.validate();
	});
}

//购买限制
function payLimitValidate(obj)
{
	$("#onePayCount").blur(function()
	{
			fo.setConfig({"input":getByID('onePayCount'),"vType":2,"format":FoReg.number});
			fo.validate();
	});
	
	$("#oneManyCount").blur(function()
	{
			fo.setConfig({"input":getByID('oneManyCount'),"vType":2,"format":FoReg.number});
			fo.validate();
	});
	
	$("#manyPayCount").blur(function()
	{
			fo.setConfig({"input":getByID('manyPayCount'),"vType":2,"format":FoReg.number});
			fo.validate();
	});
}

//团购价类型
function activeRuleValidate(obj)
{
	if(obj == 1)
	{
		$("#groupPrice").blur(function()
		{
			fo.setConfig({"input":getByID('groupPrice'),"vType":2,"format":FoReg.number});
			fo.validate();
			
		});
	}
	else
	{
		setClassName("old_lessPayNumber","fo_border_need");
		setClassName("old_morePayNumber","fo_border_need");
		setClassName("old_manyGroupPrice","fo_border_need");
		
		$("#old_lessPayNumber").blur(function()
		{
			fo.setConfig({"input":getByID('old_lessPayNumber'),"vType":2,"format":FoReg.number});
			fo.validate();
		});
		
		$("#old_morePayNumber").blur(function()
		{
			fo.setConfig({"input":getByID('old_morePayNumber'),"vType":2,"format":FoReg.number});
			fo.validate();
		});
		
		$("#old_manyGroupPrice").blur(function()
		{
			fo.setConfig({"input":getByID('old_manyGroupPrice'),"vType":2,"format":FoReg.number});
			fo.validate();
		});
	}
}

function groupNumberValidate()
{
	$("#groupNumber").blur(function()
	{
		fo.setConfig({"input":getByID('groupNumber'),"vType":2,"format":FoReg.number});
		fo.validate();
	});
}

//其他优惠
function otherActiveRuleValidate()
{
	$("#chk_syhq").click(function()
	{
			if(getByID("chk_syhq").checked)
			{
				setClassName("couponsId_","fo_border_need");
				$("#couponsId_").blur(function()
				{
						fo.setConfig({"input":getByID('couponsId_'),"vType":2,"format":FoReg.couponNumber});
						fo.validate();
				});
			}
			else
			{
				setClassName("couponsId_","fo_border_success");
			}
	});
	
	$("#chk_sjf").click(function()
	{
			if(getByID("chk_sjf").checked)
			{
				setClassName("sendIntegralNumber_","fo_border_need");
				$("#sendIntegralNumber_").blur(function()
				{
						fo.setConfig({"input":getByID('sendIntegralNumber_'),"vType":2,"format":FoReg.number});
						fo.validate();
				});
			}
			else
			{
				setClassName("sendIntegralNumber_","fo_border_success");
			}
	});
	
	$("#chk_jfbs").click(function()
	{
			if(getByID("chk_jfbs").checked)
			{
				setClassName("integralMultiples_","fo_border_need");
				$("#integralMultiples_").blur(function()
				{
						fo.setConfig({"input":getByID('integralMultiples_'),"vType":2,"format":FoReg.number});
						fo.validate();
				});
			}
			else
			{
				setClassName("integralMultiples_","fo_border_success");
			}
	});
}

//参与对象 
function participationObjectValidate(obj)
{
	if(obj == 2)
	{
		$("#levelId").click(function()
		{
				if(getByID("levelId").checked)
				{
					setClassName("levelId","fo_border_success");
				}
				else
				{
					setClassName("levelId","fo_border_need");
				}
		});
		
		setClassName("levelId","fo_border_need");
	}
	else
	{
		setClassName("levelId","fo_border_success");
	}
}
	
	