<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
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

               <form action="${BasePath}/merchants/login/assign_Merchants_User_Authority.sc" name="querFrom" id="querFrom" method="post" style="padding:0px;margin:0px;">
		      		<input type="hidden" name="userId" id="userId" value="<#if userId??>${userId!''}</#if>">
		      		<input type="hidden" name="authority" id="authority">
		      		<div style="padding-left:20px;padding-right:20px;">
			        <table cellpadding="0" cellspacing="0">
			            <tbody>
			            	<tr>
			            		<td align="center" style="height:20px;">所有权限</td>
			            		<td></td>
			            		<td align="center">已分配权限</td>
			            	</tr>
			                <tr>
				                <td align="top">
									<a id="right_up" class="a-up">上移</a>
				               		<div class="sel-div"  style="height:300px">
				                		<select style="height:310px"  multiple="multiple" id="select2" name="select2">
				                			<#if userAuthorityList??>
				                				<#list userAuthorityList as item>
				                					<option value="${item['id']!''}">${item['authrity_name']!''}</option>
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
				                	<div class="sel-div"  style="height:300px">
				               			<select style="height:310px" multiple id="select1" name="select1" >
				                			<#if authorityList??>
				                				<#list authorityList as item>
				                					<option value="${item['id']!''}" selected>${item['authrity_name']!''}</option>
				                				</#list>
				                			</#if>
				                		</select>
				               	 	</div>
				                	<a  id="left_down" class="a-down">下移</a>
				                </td>
			                </tr>	
			            </tbody>
			        </table>
			        </div>
		        </form>
</body>
</html>
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