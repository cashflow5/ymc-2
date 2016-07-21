<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
   
   
    <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
    
    <!-- 排序样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/active/css/sortfilter.css"/>
    

    <link rel="stylesheet" href="${BasePath}/yougou/styles/active/css/university.css">
    <link rel="stylesheet" href="${BasePath}/yougou/styles/active/css/offActivit_list.css">
    <title>优购商城--商家后台-活动介绍</title>
</head>

<body>
    <div class="container">
        <!-- 合同信息开始 -->
        <div class="list_content">
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span><a href="${BasePath}/active/web/merchantOfficialActiveController/queryActiveRule.sc?activeId=${activeId}" class="btn-onselc">活动介绍</a></span>
                    </li>
                    <li>
                        <span><a href="${BasePath}/active/web/merchantOfficialActiveController/queryActiveIntroduce.sc?activeId=${activeId}" class="btn-onselc">活动规则</a></span>
                    </li>
                </ul>
            </div>
            <div class="modify">
                <div class="info-box-content">
                    <h1>${officialActive.activeName}</h1>
                    ${officialActive.activeDesc}
                    
                </div>
            </div>
        </div>
        <!-- 合同信息结束 -->
    </div>
    
    <script>
        function closewind(){
            $('.aui_close').click();
            return false;
        };
    </script>
	  <script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
    <script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
    <script  type="text/javascript" src="${BasePath}/js/supplier-contracts.js"></script>


<script>
    $(function(){
        $('#yg-del-cancle').click(function(){
              //$('.aui_close',window.parent.document).eq(0).click();
              //console.log($('.aui_close',window.parent.document).eq(0));
              window.parent.dialog_close();
        })


    })
</script>



</body></html>