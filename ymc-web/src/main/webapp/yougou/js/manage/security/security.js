
$(document).ready(function(){
	var cookie = getCookie("mobilephone");
	var pwdCookie = getCookie("pwdpower");
	if(cookie!="nophone"){
		$.get(basePath+"/merchants/security/checkMobilephone.sc",function(json){
			if(json.result==0 && json.master==1){
				ygdg.dialog({
					id:'dialog',
					title:'安全提示',
					content:'<div class="phoneslider clearfix">尊敬的商家：<div class="phone-message fl">为提高商家账户安全性，我们开启了短信身份验证模式，请您绑定手机后登录！</div></div><div class="btn-box">'+
							'<a href="'+basePath+'/merchants/security/bandingMobile.sc" class="btn btn_yellow">'+
							'去绑定手机</a></div>',
					lock:true,
					drag: false,
				    resize: false,
				    width:350,
				    cancel:false
				});
				//存入cookie
				setCookie("mobilephone","nophone",null);
				setCookie("pwdpower","lowStrength",null);
			}
			if(pwdCookie!="lowStrength"){
				$.get(basePath+"/merchants/security/checkPwdStrength.sc",function(json){
					if(json.yougou!=1 && json.yougou!=2 && json.strength==1){
						ygdg.dialog({
							id:'dialog',
							title:'安全提示',
							content:'<div class="phoneslider clearfix">尊敬的商家：<div class="phone-message fl">您的密码强度为低！为保障您的账号安全，请及时修改密码。</div></div><div class="btn-box">'+
									'<a href="'+basePath+'/merchants/security/accountSecurity.sc" class="btn btn_yellow">'+
									'去修改密码</a></div>',
							drag: false,
						    resize: false,
						    width:350
						});
						//存入cookie
						setCookie("pwdpower","lowStrength",null);
					}
				},"json");
			}
		},"json");
	}
	var overdaynotes = getCookie("overdaynotes");
	if(overdaynotes != 'isChecked'){
		$.get(basePath+"/merchants/login/checkOverDays.sc",function(json){
			if(json.needNotes== true ){
				 $.dialog.open(basePath+'/merchants/login/toShowNote.sc',{
			            title:"提醒",
			            max:false,
			            min:false,
			            width: '380px',
			            height: '245px',
			            lock:true
			        });
				//存入cookie
				setCookie("overdaynotes","isChecked",null);
			}
		},"json");
	}
});

function setCookie(c_name,value,expiredays){
  var exdate=new Date();
  exdate.setDate(exdate.getDate()+expiredays);
  document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function getCookie(c_name){
	  if (document.cookie.length>0){
	    var c_start=document.cookie.indexOf(c_name + "=");
	    if(c_start!=-1){ 
	      c_start=c_start + c_name.length+1;
	      var c_end=document.cookie.indexOf(";",c_start);
	      if (c_end==-1){c_end=document.cookie.length;}
	      return unescape(document.cookie.substring(c_start,c_end));
	    } 
	  }
	return "";
	}
