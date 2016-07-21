<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
</head>
<style type="text/css">
	#select1,#select2{width:158px; margin:-4px;padding:5px;}
	.sel-div{border:1px solid #ccc;width:150px; overflow:hidden;}
	.a-up,.a-down{display: block;border: 1px solid #ccc;text-decoration:none;width:150px; background:#F2F2F2; text-align:center; height:25px; line-height:25px;cursor:pointer; letter-spacing:2px; zoom:1;}
	.a-up{border-bottom:none;}
	.a-down{border-top:none;}
	.a-right,.a-left{display:block; border:1px solid #ccc; width:100px; height:25px; line-height:25px; background:#F2F2F2;margin:15px; cursor:pointer;}
	.a-left{margin-top:20px;}
</style>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">分配权限</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
               <form action="${BasePath}/yitiansystem/merchants/businessorder/assign_Merchants_User_Authority.sc" name="querFrom" id="querFrom" method="post" style="padding:0px;margin:0px;">
		      		<input type="hidden" name="userId" id="userId" value="<#if userId??>${userId!''}</#if>">
		      		<input type="hidden" name="authority" id="authority">
		      		<div style="padding-left:20px;padding-right:20px;">
		      		<#if userId??>
				        <table cellpadding="0" cellspacing="0">
				            <tbody>
				            	<tr>
				            		<td align="center" style="height:20px;">所有权限</td>
				            		<td></td>
				            		<td align="center">已分配权限</td>
				            	</tr>
				                <tr>
					                <td valign="top">
										<a id="right_up" class="a-up">上移</a>
					               		<div class="sel-div">
					                		<select multiple id="select2" size="30" name="select2">
					                			<#if merchantsAuthorityList??>
					                				<#list merchantsAuthorityList as item>
					                					<option value="${item.id}">${item.authrityName!''}</option>
					                				</#list>
					                			</#if>
					                		</select>
					                	</div>
					                	<a id="right_down" class="a-down">下移</a>
					                </td>
					                <td align="center" valign="middle">
					                	<a id="remove" class="a-left">增加权限&gt;&gt;</a>
					                	<a id="add" class="a-right">&lt;&lt; 删除权限</a>
					                </td>
					                <td>
					                	<a  id="left_up" class="a-up">上移</a>
					                	<div class="sel-div">
					               			<select  name="allowRoleValues" size="30" multiple id="select1" >
					                			<#if authorityMap??>
					                				<#list authorityMap as item>
					                					<option value="${item['id']!''}" selected>${item['authrity_name']!''}</option>
					                				</#list>
					                			</#if>
					                		</select>
					               	 	</div>
					                	<a  id="left_down" class="a-down">下移</a>
					                </td>
				                </tr>	
				                <tr>
				                	<td colspan="3" align="center">
				                		<input  type="button" onclick="addRole()" class="btn-add-normal" value="保存" />   
				                		<input type="button" onclick="closewindow();" class="btn-add-normal" value="取消" />
				                	</td>
				                </tr>
				            </tbody>
				        </table>
				        <#else>
				          <span style="color:red;">商家登录帐号为空,请先添加商家登录帐号在分配权限！</span>
				       </#if>
			        </div>
		        </form>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript">
//保存
 function addRole(){  
   var aa=document.getElementById('select1');  
   var bb="";
    for (var i=0;i<aa.length;i++) {  
     bb+=aa.options[i].value+";";  
    }  
    bb=bb.substring(0,bb.length-1);
    $("#authority").val(bb);
     document.querFrom.submit();
}

 //角色分配相关JS
$(function(){
       //移到右边
       $('#add').click(function() {
            $('#select1 option:selected').remove().appendTo('#select2');
       });
       //移到左边
       $('#remove').click(function() {
            $('#select2 option:selected').remove().appendTo('#select1');
       });
       //双击选项
       $('#select1').dblclick(function(){
           $("option:selected",this).remove().appendTo('#select2');
       });
       //双击选项
       $('#select2').dblclick(function(){
           $("option:selected",this).remove().appendTo('#select1');
       });
       //左边向上按钮
       $('#left_up').click(function(){
          var index = $('#select1 option').index($('#select1 option:selected:first'));
        var $recent = $('#select1 option:eq('+(index-1)+')');
        if(index>0){
             var $options = $('#select1 option:selected').remove();
            setTimeout(function(){
                $recent.before($options );
            },10);
        }
       });
       //左边向下按钮
       $('#left_down').click(function(){
          var index = $('#select1 option').index($('#select1 option:selected:last'));
        var len = $('#select1 option').length-1;
        var $recent = $('#select1 option:eq('+(index+1)+')');
        if(index<len ){
             var $options = $('#select1 option:selected').remove();
            setTimeout(function(){
                $recent.after( $options );
            },10);
           }
       });
       //右边向上按钮
       $('#right_up').click(function(){
          var index = $('#select2 option').index($('#select2 option:selected:first'));
        var $recent = $('#select2 option:eq('+(index-1)+')');
        if(index>0){
             var $options = $('#select2 option:selected').remove();
            setTimeout(function(){
                $recent.before($options );
            },10);
        }
       });
       //右边向下按钮
       $('#right_down').click(function(){
          var index = $('#select2 option').index($('#select2 option:selected:last'));
        var len = $('#select2 option').length-1;
        var $recent = $('#select2 option:eq('+(index+1)+')');
        if(index<len ){
             var $options = $('#select2 option:selected').remove();
            setTimeout(function(){
                $recent.after( $options );
            },10);
           }
       });
});

</script>