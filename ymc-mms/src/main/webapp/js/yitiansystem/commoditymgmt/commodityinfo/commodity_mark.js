var flag = true;

function validateform() 
{
	return flag;
}

var config={
		  form:"form1",
		  submit:validateform,
	  		fields:[]

		}

Tool.onReady(function(){
	var f = new Fw(config);
  		f.register();
  });
  	

