<#-- 新订单来源三级级联查询 start -->
<script type="text/javascript">
	//店铺和来源级联 一级级联二级
	function chooseSecondSource(){
		var source=$("#orderSourceId").val();
		$.getJSON("${BasePath}/yitiansystem/ordergmt/orderflow/getOrderSecondSourceListBySourceId.sc",{sourceId:source},function(data){
			var html="<option value='-1'>请选择</option>";
			if(data!=""){
				$.each(data["orderSecondSourceList"],function(i,item){
					html+="<option value="+item.no+">"+item.name+"</option>";
				});
			}
			$("#orderSecondSourceId").html(html);
		});
	}
	
	//店铺和来源级联 二级级联店铺
	function chooseOutShop(){
		var source=$("#orderSecondSourceId").val();
		$.getJSON("${BasePath}/yitiansystem/ordergmt/orderflow/getOrderSellerListBySourceId.sc",{sourceId:source},function(data){
			var html="<option value='-1'>请选择</option>";
			if(data!=""){
				$.each(data["orderSellerList"],function(i,item){
					html+="<option value="+item.no+">"+item.name+"</option>";
				});
			}
			$("#outShopId").html(html);
		});
	}
</script>

<span>
<label>一级来源：</label>
<select name="orderSourceId" id="orderSourceId" class="selecttxt" onchange="chooseSecondSource()">
	<option value='-1'>请选择</option>
	<#if orderSourceList??>
		<#list orderSourceList as item>
			<#if item.level=="1">
				<#if vo.orderSourceId??>
					<#if vo.orderSourceId?index_of(item.no)==0>
						<option value="${item.no}" selected>${item.name!""}</option>
					<#else>
						<option value="${item.no}">${item.name!""}</option>
					</#if>
				<#else>
					<option value="${item.no}">${item.name!""}</option>
				</#if>
			</#if>
		</#list>
	</#if>
</select>
</span>

<span>
<label>二级来源：</label>
<select name="orderSecondSourceId" id="orderSecondSourceId" class="selecttxt" onchange="chooseOutShop()">
	<option value='-1'>请选择</option>
	<#if orderSourceList??>
		<#list orderSourceList as item>
			<#if item.level=="2">
				<#if vo.orderSourceId?? && (vo.orderSourceId?index_of('-') gte 0)>
					<#if vo.orderSecondSourceId?? && vo.orderSourceId?? && item.no?index_of((vo.orderSourceId?substring(0,vo.orderSourceId?index_of('-'))))==0>
						<#if vo.orderSecondSourceId==item.no>
							<option value="${item.no}" selected>${item.name!""}</option>
						<#else>
							<option value="${item.no}">${item.name!""}</option>
						</#if>
					</#if>
				<#else>
					<#if vo.orderSecondSourceId?? && vo.orderSourceId?? && item.no?index_of(vo.orderSourceId)==0>
						<#if vo.orderSecondSourceId==item.no>
							<option value="${item.no}" selected>${item.name!""}</option>
						<#else>
							<option value="${item.no}">${item.name!""}</option>
						</#if>
					</#if>
				</#if>
			</#if>
		</#list>
	</#if>
</select>
</span>

<span>
<label>店铺名称：</label>
<select id="outShopId" name="outShopId" class="selecttxt">
	<option value='-1'>请选择</option>
	<#if orderSourceList??>
		<#list orderSourceList as item>
			<#if item.level=="3">
				<#if vo.outShopId?? && vo.orderSecondSourceId?? && item.no?index_of(vo.orderSecondSourceId)==0>
					<#if vo.outShopId==item.no>
						<option value="${item.no}" selected>${item.name!""}</option>
					<#else>
						<option value="${item.no}">${item.name!""}</option>
					</#if>
				</#if>
			</#if>
		</#list>
	</#if>
</select>
</span>
<#-- 新订单来源三级级联查询 end -->