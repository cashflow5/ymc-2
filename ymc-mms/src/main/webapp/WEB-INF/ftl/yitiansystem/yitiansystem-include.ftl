<!-- 引用常用标签 -->
<#assign c=JspTaglibs["/WEB-INF/tlds/c.tld"]>
<#assign fn=JspTaglibs["/WEB-INF/tlds/fn.tld"]/>
<#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]/>
<!-- 引用Spring标签 -->
<#assign spring=JspTaglibs["/WEB-INF/tlds/spring.tld"]/>
<#assign form=JspTaglibs["/WEB-INF/tlds/spring-form.tld"]/>

<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<!-- 引入公共脚本 -->
<script type="text/javascript"  src="${BasePath}/js/jquery-1.4.2.min.js" ></script>
<script type="text/javascript"  src="${BasePath}/js/jquery.form.js" ></script>
<script type="text/javascript"  src="${BasePath}/js/common.js"></script>
<script type="text/javascript"  src="${BasePath}/js/common/form/finance.js"></script>
<script type="text/javascript"  src="${BasePath}/js/common/thickbox/thickbox.js"></script>  

<!--日期控件-->
<script type="text/javascript"  src="${BasePath}/js/common/form/ui.datepicker.js" charset="UTF-8" ></script>
<script type="text/javascript"  src="${BasePath}/js/common/form/ui.datepicker-zh-CN.js"  charset="UTF-8" ></script>

<!-- 引入前台验证 -->
<link rel="stylesheet" href="${BasePath}/js/common/validate/css/validator.css" type="text/css"></link>
<script type="text/javascript" src="${BasePath}/js/common/validate/Fv.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/validate/Fw.js"></script>


<script type="text/javascript" src="${BasePath}/js/common/historyperpage.js"></script>

<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>