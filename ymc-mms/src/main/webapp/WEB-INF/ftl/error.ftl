<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rev="stylesheet" rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css" />
<title>优购商城--商家后台</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
</head>
<body>
	<div class="contentMain">
		<div class="ytback-tt-1 ytback-tt">
    	<span>您当前的位置：</span>
        <a href="../index_content.html">首页</a> &gt; 错误提示</div>
		<div class="blank5"></div>
	    <div class="wms">
		    <div class="mb-btn-fd-bd">
				  <div class="mb-btn-fd relative">
					<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg"><a  class="btn-onselc">错误提示</a></li>
								<li class="pl-btn-rtbg"></li>
							</ul>
					</span>
				  </div>
	        </div>
		    <div class="wms-div">         
		    	<div class="err-info-div">
			        <h3>出错了：</h3>
			        <span>
			        	${errorMessageException?default('')}
			        </span>
			        <div class="blank15"></div>
			        <div class=""><input type="button" value="返回"  onclick="window.history.go(-1);" class="btn-add-normal" /></div>
			        <div class="blank20"></div>
		        </div>
		    </div>
	   </div>
	</div>
</div>  
</body>
</html>
