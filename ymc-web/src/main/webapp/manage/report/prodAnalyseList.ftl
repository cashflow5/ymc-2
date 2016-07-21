<!-- 表格开始 -->
<table class="yg-table ${class !''}">
    <thead>
        <tr>
            <th>商品图片</th>
            <th>商品编码</th>
            <th>商品状态</th>
            <#list columnIndex as index>
        		<th>
	                <span>${(index.en_name)?contains("converse_rate")?string("单品","")}${(index.label)!'--' }</span>
					<input type="checkbox" data-ui-type="sort" name="displayCol" value="${(index.en_name)!'--' }" Nenabled="Nenabled">
	            </th>
        	</#list>
            <th class="operate-th">操作</th>  
        </tr>
    </thead>
    <tbody>
    	<tr class="total">        	
	            <!-- 商品图片 -->
	            <td></td>
	            <!-- 商品编码 -->
	            <td></td>
	            <!-- 商品状态 -->
	            <td></td>
	            <#list columnIndex as index>
	            	<td ><span>
	            	<#if index.digit_decimal=='0'>
	            		<#if index.sum_index=='1'>
	            		${((sumData[(index.en_name)])!0)?string(",##0")}
	            		<#else>
                    	--
	            		</#if>
	            	<#elseif index.percent=='1'>
	            		${(((sumData[(index.en_name)])!0)*100)?string("#.##") }%
	            	<#else>
	            		${((sumData[(index.en_name)])!0)?string('#.##') }
	            	</#if>
	            	</span></td>
	            </#list>
	           <#-- <td class="operate-th"></td> -->
	        </tr>
    	<#if (pageFinder.data)?? && (pageFinder.data)?size &gt; 0>
	    	<#list pageFinder.data as item>
		        <tr class="height-30">
		        <!-- 商品图片 -->
		        <td><a href="${(item.COMMODITY_LINK)!'' }" target="_blank"><img src="${(item['COMMODITY_PIC_URL'])!'' }"/></a></td>
		        <!-- 商品编码 -->
	            <td>${(item['COMMODITY_NO'])!'--' }</td>
	            <!-- 商品状态 -->
	            <td>
	            	<#if item['COMMODITY_STATUS']==1>
	            	下架
	            	<#elseif item['COMMODITY_STATUS']==2>
	            	在售
	            	<#elseif item['COMMODITY_STATUS']==4>
	            	 待上架(无库存)
	            	<#elseif item['COMMODITY_STATUS']==5>
	            	 待上架
	            	<#elseif item['COMMODITY_STATUS']==11>
	            	 待提交
	            	<#elseif item['COMMODITY_STATUS']==12>
	            	 待审核
	            	<#elseif item['COMMODITY_STATUS']==13>
	            	 审核拒绝
	            	 <#else>
	            	 --
	            	</#if>
	            </td>
	            <#list columnIndex as index>
	            	<!-- 访次 -->
	            	<td>
	            	<#if index.digit_decimal=='0'>
	            		${((item[(index.en_name)])!0)?string(",##0")}
	            	<#elseif index.percent=='1'>
	            		${(((item[(index.en_name)])!0)*100)?string("#.##") }%
	            	<#else>
	            		${((item[(index.en_name)])!0)?string('#.##') }
	            	</#if>
	            	</td>
	            </#list>
	            
	            <td>
	                <div class="operate fr">
	                    <div class="collect collect-two mb3">
	                    	<#if (item.fcount)?? && (item.fcount &gt; 0 )>
	                    		<span class="star"><i class="iconfont Gray Blue">&#xe605;</i></span>
	                    	<#else>	
	                    		<span class="star"><i class="iconfont Gray">&#xe605;</i></span>
	                    	</#if>
	                        
	                        <span class="collect-btn collect-click first">收藏至<i class="up iconfont Gray">&#xe610;</i></span>
	                        <input id="ommodityCode_favorite" type="hidden" value="${(item.COMMODITY_NO)!'--'}"/>
	                         <div class="collect-list-wrap">
			                    <ul class="collect-list"></ul>
			                </div>
	                    </div>
	                    <a class="table-analyse btn-blue" target="_blank" href="${BasePath}/report/singleCommodityAnalysis.sc?commodityNo=${(item.COMMODITY_NO)!'--'}">单品分析</a>
	                </div>
	            </td>
	            
	        </tr>
	       </#list>
       <#else>
			<tr>
				<td colspan="${(columnIndex?size)+3 }" class="td-no">暂无记录！</td>
			</tr>
		</#if>
    </tbody>
</table>
<input type="hidden" value="${(commodityAnalysisVo.sortBy)!'' }" id="sortIndex" name="sortIndex"/>
   <!-- 分页代码 -->
	<#if pageFinder?? && pageFinder.data??>   
		<div class="yg-page">
		       <#import "/manage/report/common_page_select_ajax.ftl" as page>
			   <@page.queryForm formId="queryForm"/>
		    <!--
		    <div class="page-row">显示行数：</div>
		    <div class="page-row page-total">共520项</div> 
		    --> 
		</div>
	</#if>
   
