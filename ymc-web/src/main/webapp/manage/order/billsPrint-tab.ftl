<ul class="tab">
	<li onclick="location.href='${tempBillsPrintTabUrl!}?flag=1'" <#if flag??&&flag='1'>class="curr"</#if> ><span>未打印</span></li>
	<li onclick="location.href='${tempBillsPrintTabUrl!}?flag=2'" <#if flag??&&flag='2'>class="curr"</#if> ><span>已打印未发货</span></li>
	<li onclick="location.href='${tempBillsPrintTabUrl!}?flag=3'" <#if flag??&&flag='3'>class="curr"</#if> ><span>已发货</span></li>
	<li onclick="location.href='${tempBillsPrintTabUrl!}?flag=4'" <#if flag??&&flag='4'>class="curr"</#if> ><span>缺货订单</span></li>
	<li onclick="location.href='${tempBillsPrintTabUrl!}?flag=5'" <#if flag??&&flag='5'>class="curr"</#if> ><span>全部</span></li>
</ul>
