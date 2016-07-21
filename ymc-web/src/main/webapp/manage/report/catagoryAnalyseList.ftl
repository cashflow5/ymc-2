<!-- 表格开始 -->
<table class="yg-table ${class !''}">
   <thead>
        <tr>
            <th>商品分类</th>
            <#list columnIndex as index>
        		<th>
	                <span>${(index.en_name)?contains("converse_rate")?string("品类","")}${(index.label)!'--' }</span>
					<input type="checkbox" data-ui-type="sort" name="displayCol" value="${(index.en_name)!'--' }" Nenabled="Nenabled">
	            </th>
        	</#list>
        </tr>
    </thead>
    		<tbody>
				<tr class="total">
					<td></td>
					<#list columnIndex as index>
                    	<td><span>
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
                </tr>
            </tbody>
			<#if (pageFinder.data)?? && (pageFinder.data)?size &gt; 0>
				
    			<#list pageFinder.data as item>
	       		<tbody>
	                <tr class="height-30 table-parent">
	                	<!-- 分类 -->
	                    <td class="tleft">
	                        <span id="c_${(item.COMMODITY_STRUCTNAME_TWO)!'' }" class="icon iconfont Blue table-click" onclick="loadCataSubTable(this)">
	                            <i class="parent-ico first">&#xe61a;</i>
	                        </span>
	                        <span>${(item.COMMODITY_CATNAME_TWO)!'--'}</span>
	                    </td>
	                    <#list columnIndex as index>
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
	                </tr>
             	</tbody>
       			</#list>
       		

   <#else>
		<tbody>
            <tr >
              <td colspan="10" class="td-no">暂无记录！</td>
            </tr>
        </tbody>
	</#if>
</table>   
<input type="hidden" value="${(commodityAnalysisVo.sortBy)!'' }" id="sortIndex" name="sortIndex"/>
   <!-- 分页代码 -->
	<#if pageFinder?? && pageFinder.data??>   
		<div class="yg-page">
		    <div class="page-list">
		       <#import "/manage/report/common_page_select_ajax.ftl" as page>
			   <@page.queryForm formId="queryForm"/>
		    </div>		 
		</div>
	</#if>
