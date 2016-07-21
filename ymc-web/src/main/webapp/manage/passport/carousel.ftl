
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购-商家中心-内容轮播</title>
    <link rel="stylesheet" href="${BasePath}/yougou/css/carousel/base.css" />
    <link rel="stylesheet" href="${BasePath}/yougou/css/carousel/style.css" />
    <link rel="stylesheet" href="${BasePath}/yougou/css/carousel/carousel.css" />
    <script  type="text/javascript" src="${BasePath}/yougou/js/jquery-1.9.1.min.js"></script>
    <script  type="text/javascript" src="${BasePath}/yougou/js/carousel/assets/js/slider.js"></script> 
   
    
</head>

<body>
<div class="slider clearfix">
  <div class="slider-icon fl"></div>
   <#if noteSum ?? && noteSum ==1>
     <input type="hidden" name="fign" id="fign" value="111"/>
     
      <div class="single-message fl">
      <#if contract_in_day??  >
		    尊敬的商家 <br/>
		    您的优购服务期限还有<font color="red">${contract_in_day?default(0)}</font>天到期，请于合同到期之前向优购运营提交续签申请，并完成续签，逾期未提交者视为主动放弃续签，您的店铺在服务期满后自动关闭。请您及时完成续签，以免造成不必要的损失。
	  </#if>
	   <#if contract_out_day??>
                          尊敬的商家 <br/>您的合同在<font color="red">${contract_fail_day?default('')}日已到期</font>，请联系优购运营续签合同后再进行操作！  
        </#if>
        <#if bussiness_out_day?? >
	      尊敬的商家 <br/>您的营业执照资质在<font color="red">${bussiness_fail_day?default('')}已经到期</font>，请及时提供新的营业执照给优购运营更新！ 
    	 </#if> 
    	 <#if institution_out_day?? >
	       尊敬的商家 <br/>您的组织机构资质在<font color="red">${institution_fail_day?default('')}已经到期</font>，请及时提供新的组织机构代码证给优购运营更新！ 
	     </#if>
	     <#if mark_out_day?? >
	       尊敬的商家 <br/>您的商标授权资质 其中有一个<font color="red">已经到期${mark_out_day?default('')}天</font>了，请及时提供新的商标授权给优购运营更新！ 
	     </#if>
	     
	  <#if bussiness_in_day?? >  
	       尊敬的商家 <br/>  您的营业执照资质还有<font color="red">${bussiness_in_day?default(0)}</font>天到期，请及时提供新的营业执照给优购运营更新！
	  </#if>
	  <#if institution_in_day?? >
	    尊敬的商家 <br/>  您的组织机构代码证还有<font color="red">${institution_in_day?default(0)}</font>天到期，请及时提供新的组织机构代码证给优购运营更新！
	   </#if>
	  <#if mark_in_day?? >
	    尊敬的商家 <br/>  您的商标授权资质还有<font color="red">${mark_in_day?default(0)}</font>天到期，请及时提供新的商标授权给优购运营更新！
	   </#if>
	   
	     
	  </div>
   <#else>
      <div class="slider-container fl">
      <div class="slider-wrapper">
      	 <#if contract_in_day??  >
	      	 <div class="slide"> 
	      	  
		                   尊敬的商家<br/>  
		       &nbsp; &nbsp; 您的优购 服务期限还有 <font color="red">${contract_in_day?default(0)}</font>天到期,请于合同到期前向优购提交续签申请并完成续签,逾期未提交者视为
		         主动放弃续签,您的店铺在服务器满后自动关闭,请您及时完成续签,以避免造成不必要的损失.     
			</div>
       	</#if>
        <#if contract_out_day??>
          <div class="slide">  尊敬的商家<br/>&nbsp;&nbsp; 您的合同在<font color="red">${contract_fail_day?default('')}日已到期</font>，请联系优购运营续签合同后再进行操作！  </div>
    	 </#if>
    	 <#if bussiness_out_day??>
	      <div class="slide"> 尊敬的商家<br/> &nbsp;&nbsp;您的营业执照资质在<font color="red">${bussiness_fail_day?default('')}已经到期</font>，请及时提供新的营业执照给优购运营更新！</div>
    	 </#if> 
    	 <#if institution_out_day??>
	      <div class="slide"> 尊敬的商家<br/> &nbsp;&nbsp;您的组织机构资质在<font color="red">${institution_fail_day?default('')}已经到期</font>，请及时提供新的组织机构代码证给优购运营更新！ </div>
	     </#if>
	     <#if mark_out_day??>
	      <div class="slide"> 尊敬的商家<br/> &nbsp;&nbsp;您的商标授权资质 其中有一个<font color="red">已经到期${mark_out_day?default('')}天</font>了，请及时提供新的商标授权给优购运营更新！ </div>
	     </#if> 
        
         <#if bussiness_in_day?? >  
	   <div class="slide">    尊敬的商家<br/>  &nbsp;&nbsp;您的营业执照资质还有<font color="red">${bussiness_in_day?default(0)}</font>天到期，请及时提供新的营业执照给优购运营更新！</div>
	  </#if>
	  <#if institution_in_day?? >
	 <div class="slide">   尊敬的商家<br/> &nbsp;&nbsp;您的组织机构代码证还有<font color="red">${institution_in_day?default(0)}</font>天到期，请及时提供新的组织机构代码证给优购运营更新！</div>
	   </#if>
	  <#if mark_in_day?? >
	 <div class="slide">   尊敬的商家<br/>  &nbsp;&nbsp;您的商标授权资质还有<font color="red">${mark_in_day?default(0)}</font>天到期，请及时提供新的商标授权给优购运营更新！</div>
	   </#if>
       </div>
      </div>
   </#if>
  
  
  
 
</div>
 
<div class="btn-box">
  <a href="#" id="btn_detail" class="btn btn_yellow">查看详情</a>
</div>
 
    <!-- 轮播js -->
   
 <script>
    

 (function() {
    //设置随机数，当输出1的时候显示单条信息
    var rand = parseInt(Math.random() * 10 % 2);
   
    
      //内容轮播插件
      Slider.init({
        target: $('.slider'),
        time: 5000,
      });
   

    //查看详情
    $('#btn_detail').click(function(){
     
     
       parent.location.href = "${BasePath}/merchants/login/to_MerchantsUser.sc";
      
      /**
       var slide=$('.slide'),
          bullet=$('.slider-nav li').find('.bullet'),
          single_message=$('.single-message '), 
          currCont;
      var  fign = $('#fign').val();
      if(fign=='111'){
          currCont=single_message.text();
	        alert(currCont);
      }else{
	       bullet.each(function(index){
	          if(bullet.eq(index).hasClass('active')){
	            currCont=slide.eq(index).text();
	            alert(currCont);
	          }
	        })
      }
      
      
      */
     
    })
  })();
 

  </script>
</body>

</html>
