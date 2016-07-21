
	
	<h1 class="record-title">单品销售排行</h1>
	<table  cellpadding="0" cellspacing="0" class="sales-rank">
		<tr>
			<th>图片</th>
			<th>编码</th>
			<th>支付件数</th>
			<th>支付均价</th>
			<th>库存</th>
			<th class="w107 tleft">最近七天支付件数</th>
            <th class="w107">操作</th> 
		</tr>
		<#if (pageFinder.data)??  && (pageFinder.data)?size &gt; 0> 
			<#list pageFinder.data as item>
		<tr>
			<td><a target="_blank" href="${(item.prodUrl)!''}"><img src="${(item.imageUrl)!''}" alt=""></a></td>
			<td><#if item.productNo??>${item.productNo?default('')}</#if></td>
			<td><#if item.payedProduct??>${item.payedProduct?default('0')}</#if></td>
			<td><#if item.payedAveragePrice??>${item.payedAveragePrice?default('0')}</#if></td>
			<td><#if item.storge??>${item.storge?default('0')}</#if></td>
			<td class="tleft">
				<div class="number-paid"></div>
			</td>
			
            <td>            	
               <div class="operate">
                    <div class="collect mr10 fl">
                        <span class="star collect-click first">
                        	<#if (item.fcount)?? && (item.fcount &gt; 0 )>
	                    		<i class="iconfont Blue">&#xe605;</i>
	                    	<#else>	
	                    		<i class="iconfont Gray">&#xe605;</i>
	                    	</#if>                      	
                           <#-- <i class="iconfont Gray">&#xe605;</i> -->
                        </span>
                        <div class="collect-list-wrap">
                            <ul class="collect-list"></ul>
                        </div>
						<input id="ommodityCode_favorite" type="hidden" value="${(item.productNo)!'--'}"/>
                    </div>

                   <a class="table-analyse btn-blue" target="_blank" href="${BasePath}/report/singleCommodityAnalysis.sc?commodityNo=${(item.productNo)!'--'}">单品分析</a>
                </div>
                             
                <#--  
            	 <div class="operate fr">
                    <div class="collect collect-two mb3">
                    	<#if (item.fcount)?? && (item.fcount &gt; 0 )>
                    		<span class="star"><i class="iconfont Gray Blue">&#xe605;</i></span>
                    	<#else>	
                    		<span class="star"><i class="iconfont Gray">&#xe605;</i></span>
                    	</#if>                      
                        <span class="collect-btn collect-click first">收藏至<i class="up iconfont Gray">&#xe610;</i></span>
                        <input id="ommodityCode_favorite" type="hidden" value="${(item.productNo)!'--'}"/>
                         <div class="collect-list-wrap">
		                    <ul class="collect-list"></ul>
		                </div>
                    </div>
                    <a class="table-analyse btn-blue" target="_blank" href="${BasePath}/report/singleCommodityAnalysis.sc?commodityNo=${(item.productNo)!'--'}">单品分析</a>
                </div>
                 -->
            </td>           
		</tr>
        </#list>
		<#else>
			<tr>
				<td colspan="7" class="td-no">暂无记录！</td>
			</tr>
		</#if>
	</table>
     	<#if pageFinder?? && pageFinder.data??>
     		<div class="yg-page">
				<#import "/manage/report/common_page_ajax.ftl" as page>
				<@page.queryForm formId="queryForm"/>
			</div>
		</#if>

<script>
 	

$(function(){
	
	 var categories4 = ${lastServenDays};
	
	 var series4 = [];
    <#if pageFinder?? && (pageFinder.data)??> 
		<#list pageFinder.data as item>
			var seriesObj = {};
			seriesObj.name="支付件数";
			var array = [seriesObj];
			<#if item.lastSevenDayPayedProduct??>
			seriesObj.data = ${item.lastSevenDayPayedProduct};
			<#else>
			seriesObj.data = [];
			</#if>
			series4.push(array);
		</#list>
	</#if>
	 $('.number-paid').each(function(index){
        var obj=$('.number-paid').eq(index);
       // alert(series4[index][0].data);
         G.Detail.bindChartArea(obj,categories4, '元', series4[index]);//订单均价数据曲线图
    })
    G.Detail.progressWidth();//进度条的函数
});


//计算日期加减 并返回 yyyy-mm-dd 格式日期字符串
function addDate(date,days){  
	var d=new Date(date); 
	d.setDate(d.getDate()+days);  
	var month=d.getMonth()+1; 
	var day = d.getDate(); 
	if(month<10){  
	   month = "0"+month;  
	}  
    if(day<10){
       day = "0"+day;  
    }  
	var val = d.getFullYear()+"-"+month+"-"+day; 
    return val; 
} 

</script>
