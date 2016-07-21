<style>
	td em.c3 input{display:none;width:40px;}
	td em.c3 a.edit4Coupon{margin-left:5px;display:inline-block;width:16px;height:16px;background:url(${BasePath}/yougou/images/icon_edit.png);}
	td em.c3 a.save4Coupon{margin-left:5px;display:inline-block;width:16px;height:16px;background:url(${BasePath}/yougou/images/icon_save.png);display:none}
	td em.c3 a.cancel4Coupon{margin-left:5px;display:inline-block;width:9px;height:9px;background:url(${BasePath}/yougou/images/del-class.gif);display:none}
	td em.c3 span.loadimg{display:none;}
	
</style>
<input type="hidden" id = "tatalCommodity" value="${tatalCommodity}"/>
<table class="list_table">
    <thead>
        <tr>
        	<#if activeCommodity.status == '1'>
            <th width="25">&nbsp;</th>
            </#if>
            <th width="66">商品编码</th>
            <th width="200">商品名称</th>
            <th width="36">款色</th>
            <th width="50">优购价</th>
            <#if activeCommodity.activeType == '2'>
            <th width="50">活动价</th>
            </#if>
			<#if activeCommodity.isSupportCoupons == '1'>
            <th width="80">最高可承担卡券金额</th>
			</#if>
			<#if activeCommodity.status == '1'>	
            <th width="36">操作</th>
            </#if>
        </tr>
    </thead>
    <tbody id="common_proitm">
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
		<#list pageFinder.data as item >
        <tr>
        	<#if activeCommodity.status == '1'>
            <td>
                <input type="checkbox" class="comodityNo" name="comodityNo"  value="${item.id}" />
            </td>
            </#if>
            <td>${item.comodityNo}</td>
            <td class="tl" style="text-align:center"><a target="_blank"  href="${item.prodUrl!''}">${item.comodityName!''}</a></td>
            <td>${item.specName!''}</td>
            <td><span class="yougouPrice">${item.salePrice!''}</span></td>
            <#if activeCommodity.activeType == '2'>
            <td>
			<em class="c3">
			<span class="curprice">${item.activePrice!''}</span>
			<#if activeCommodity.status == '1'>
			<input type="text" value="${item.activePrice!''}" />
			<a href="javascript:void(0)" class="edit4Coupon" title="修改"></a>
    		<a href="javascript:void(0)" class="save4Coupon" type="1" title="保存"></a>
    		<a href="javascript:void(0)" class="cancel4Coupon" title="取消修改"></a>
    		<span class="loadimg"><img src="${BasePath}/yougou/images/loading16.gif"></span>
    		</#if>
    		</em>
			</td>
            </#if>
			<#if activeCommodity.isSupportCoupons == '1'>
            <td>
			<em class="c3">
			<span class="curprice">${item.couponAmount!''}</span>
			<#if activeCommodity.status == '1'>
			<#if uniqueCoupon != '1'>
			<input type="text" value="${item.couponAmount!""}" />
			<a href="javascript:void(0)" class="edit4Coupon" title="修改"></a>
    		<a href="javascript:void(0)" class="save4Coupon" type="2" title="保存"></a>
    		<a href="javascript:void(0)" class="cancel4Coupon" title="取消修改"></a>
    		<span class="loadimg"><img src="${BasePath}/yougou/images/loading16.gif"></span>
    		</#if>
    		</#if>
    		</em>
			</td>
			</#if>
			<#if activeCommodity.status == '1'>
            <td><a href="javascript:deleteCommodity('${item.id}');">删除</a></td>
            </#if>
        </tr>        
        </#list>
		<#else>
			 	<tr><td colSpan="12" style="text-align:center">抱歉，没有您要找的数据</td></tr>
		</#if>
    </tbody>
</table>
<!--列表end-->
<div class="page_box">
    <div class="dobox">
    	<#if activeCommodity.status == '1' || activeCommodity.status == '4'>
        <input type="checkbox" id="checkAll"> 全选 <a href="javascript:deleteCommodity();"><span class="ml10">删除</span></a>
        </#if>
    </div>
    <div class="page">
        <#import "/manage/widget/common4ajax.ftl" as page>
		<@page.queryForm formId="queryVoform"/>
    </div>
</div>
<script>
//全选
$("#checkAll").click(function() {;
	$("#common_proitm").find("input[name='comodityNo']").attr("checked", this.checked);
});

function deleteCommodity(commodityId){
	var commodityIds = '';
	if(commodityId){
		commodityIds = commodityId;
	}else{
		commodityIds = $('input[name="comodityNo"]').filter(function(){
			return this.checked;
		}).map(function(){
			return this.value;
		}).get();
	}
	
	if (commodityIds.length <= 0) {
		ygdg.dialog.alert('请先选择需要删除的商品!');
		return false;
	}
	ygdg.dialog.confirm("确定删除选择的商品吗?", function() {
		$.ajax({
			type: 'post',
			url: '/active/deleteOfficialActiveCommodity.sc',
			dataType: 'json',
			data: 'rand=' + Math.random() + '&commodityIds=' + commodityIds,
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data.msg) == 'Success') {
					ygdg.dialog.alert("删除成功！",function(){
					loadData(1);
					});
				} else {
					// this.error(jqXHR, textStatus, data);
					ygdg.dialog.alert("删除失败，请重试！");
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				ygdg.dialog.alert('删除商品失败');
			}
		});
	});
}

	//修改价格
	$("td em.c3 a.edit4Coupon").click(function(){
		$(this).hide();
		var c3 = $(this).parent()
		c3.find(".curprice").hide();
		c3.find("input").show();
		c3.find("input").val(c3.find(".curprice").text());
		c3.find("input").focus();
		c3.find(".save4Coupon").css({display:"inline-block"});
		c3.find(".cancel4Coupon").css({display:"inline-block"});
	});

	$("td em.c3 a.cancel4Coupon").click(function(){
		$(this).hide();
		var c3 = $(this).parent()
		c3.find(".curprice").show();
		c3.find("input").hide();
		c3.find(".edit4Coupon").css({display:"inline-block"});
		c3.find(".save4Coupon").hide();
		c3.find(".cancel4Coupon").hide();
	});

	$("td em.c3 a.save4Coupon").click(function(){
		savePrice($(this));
	});
	
	function savePrice(curObj){
		var type = curObj.attr("type");
		var price = $.trim(curObj.parent().find("input").val());
		if(type=='2'){
			var pattern = /^[1-9][0-9]*$/;
	        var re = new RegExp(pattern);
	        if (!(price.match(re) != null && price%5==0)) {
	            ygdg.dialog.alert("请输入大于零的5的整数倍整数!");
	            return;
	        }else{
	        	var couponDefault = '${couponDefault}';
	        	var couponHighest = '${couponHighest}';
	        	if(parseInt(price) < parseInt(couponDefault) || parseInt(price) > parseInt(couponHighest)){
	        		ygdg.dialog.alert("请输入["+couponDefault+","+couponHighest+"]之间的5的整数倍整数!");
	            	return;
	        	}
	        }
		}else{
		 	var pattern = /^[1-9][0-9]*$/;
	        var re = new RegExp(pattern);
	        if (price.match(re) == null) {
	            alert("请输入大于零的整数!");
	            return;
	        }else{
	        	var yougouPrice = 0;
	        	yougouPrice = curObj.parent().parent().parent().find(".yougouPrice").text();
	        	if(parseFloat(price)>parseFloat(yougouPrice)){
					ygdg.dialog.alert("活动价不能大于优购价");
					return;
				}
	        }
		}		
		
		var commodityId = curObj.parent().parent().parent().find(".comodityNo").val();
		curObj.hide();
		curObj.parent().find(".cancel4Coupon").hide();
		var load = curObj.parent().find(".loadimg");
		load.show();
		$.ajax({
    		async : true,
    		cache : false,
    		type : 'POST',
    		dataType : "json",
    		data:{
    			commodityId:commodityId,
    			price:price,
    			type:type
    		},
    		url : "${BasePath}/active/updateOfficialActiveCommodity.sc",
    		success : function(data) {
    			load.hide();
    			if(data.msg=="Success"){
    				ygdg.dialog.alert("修改成功！");       				
    				var c3 = curObj.parent()
					c3.find(".curprice").show();
    				c3.find(".curprice").text(price) 	
					c3.find("input").hide();
					c3.find(".edit4Coupon").css({display:"inline-block"});
					c3.find(".save4Coupon").hide();
					c3.find(".cancel4Coupon").hide();			
    			}else{
    				curObj.css({display:"inline-block"});
    				curObj.parent().find(".cancel4Coupon").css({display:"inline-block"});
    				ygdg.dialog.alert("修改失败，请重试！");
    			}
    		}
		});
	}
</script>