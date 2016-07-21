/******************************************************************************************/
/*****************************订单（后台下单）相关的JS-非订单组同事请勿随便修改**************************/
/******************************************************************************************/
var BasePath="/bms";
//var BasePath="";
//清空空格的方法
String.prototype.trim = function() {
	return this.replace(/(^[\s\t\xa0\u3000]+)|([\u3000\xa0\s\t]+$)/g, "");
};

//判断是否为正整数
function isInteger(num) {
	if(num==undefined){
		return false;
	}
	num=parseInt(num);
	var patrn=/^[0-9]*[0-9][0-9]*$/;  
	if (patrn.test(num)){
	   return true;
	}else{
	   alert("请输入正整数！");
	   return false;
	}
}

//是否为价格
function isPirce(s){
	if(s==undefined){
		alert("不是价格类型！");
		return false;
	}
	s=formatFloat(s);
    var p =/^[+-]?[0-9](\d+(\.\d{1,2})?)?$/; 
    var p1=/^[+-]?[0-9](\.\d{1,2})?$/;
    return p.test(s)||p1.test(s);
}

//保留两位小数 四舍五入
function formatFloat(src)  {  
	var pos=2;
	return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);  
}  

//删除行
function DeleteRow(obj,count){
	ygdg.dialog.confirm("确定删除？",function(){
		var rowObj = obj.parentNode.parentNode;
	    var targetTable = rowObj.parentNode;
	    targetTable.removeChild(rowObj);
	    if(count!='undefined'&&(count==0)){
			countmoney();
		}
	});
}
	
function pay(){
	$.ajax( {
		type : "POST",
		url : BasePath+"/yitiansystem/ordergmt/orderdetail/payMoney.sc",
		data : {"payMethod" : $("#payMethod").val()},
		error: function(XmlHttpRequest, textStatus, errorThrown) { ygdg.dialog.alert("test: "+textStatus); },
		success : function(data) {
			var payMehtodNum = Number(data);
			payMehtodNum = isNaN(payMehtodNum) ? 0 : payMehtodNum;
			$("#expressMoney").val(payMehtodNum.toFixed(2));
		}
	});
}	

function callBack(){
	document.creat.action=BasePath+"/yitiansystem/ordergmt/orderflow/queryNewOrder.sc";
	document.creat.method="POST";
	document.creat.submit();
}

function member(){
	var memberId=$("#memberId").val();
	var buyName=$("#buyName").val();
	if(memberId==""){
		ygdg.dialog.alert("会员账号不能为空！");
		return false;
	}else if(buyName="" && memberId==""){
		ygdg.dialog.alert("该用户不存在！");
		return false;
	}
	$("#loginName").val(memberId);
	var param = "loginName="+memberId;
	Belle.do_request("memberListContainer",BasePath+"/yitiansystem/ordergmt/orderdetail/queryMember.sc",param,null);
}

function load(){
	var memberId=$("#memberId").val();
	var buyName=$("#buyName").val();
	
	if(buyName == "" && memberId != ""){
		//document.getElementById("member_id").style.display="block";
	}
}

function number(a){
	var tr=$(a).parent().parent();
	var money=tr.find("td>input:eq(0)").val();
	var productMoney=tr.find("td>input:eq(3)").val();
	var productPrivilege=tr.find("td>input:eq(4)").val();
	var productNumber=tr.find("td>input:eq(5)").val();
	productMoney=formatFloat(money*productNumber-productPrivilege);
	productPrivilege=formatFloat(money*productNumber-productMoney);
	tr.find("td>input:eq(2)").val(productMoney);
	tr.find("td>input:eq(3)").val(productPrivilege);
}

function productMoney(a) {
	var tr=$(a).parent().parent();
	var money=tr.find("td>input:eq(0)").val();
	var productMoney=tr.find("td>input:eq(3)").val();
	var productPrivilege=tr.find("td>input:eq(4)").val();
	var productNumber=tr.find("td>input:eq(5)").val();
	productPrivilege=formatFloat(money*productNumber-productMoney);
	tr.find("td>input:eq(3)").val(productPrivilege);
}

function selectName(){
	var name=$("#commodityName").val();
	openwindow(BasePath+'/yitiansystem/ordergmt/orderdetail/queryProduct.sc?commodityNo='+name+'',800,495, '商品详情');
}

function selectNameFenxiao(){
	var sellersId="";
	sellersId = $("#sellersId").val();											// 购买人邮箱
	if(sellersId==""){
		ygdg.dialog.alert("为了计算会员优惠，请先查询出会员信息！");
		return;
	}
	var flag="1";
	flag = $("#flag").val();													// 标识大宗订单1 分销订单2
	var name=$("#commodityName").val();
	openwindow(BasePath+'/yitiansystem/ordergmt/orderdetail/queryProductFenxiao.sc?flag='+flag+'&commodityNo='+name+'&sellersId='+sellersId,800,495, '商品详情');
}

function selectNameToOutOrder(){
	var sellersId="";
	sellersId = $("#sellersId").val();											// 购买人邮箱
	if(sellersId==""){
		ygdg.dialog.alert("为了计算会员优惠，请先查询出会员信息！");
		return;
	}
	var thirdPartyCode = $("#thirdPartyCode").val();
	if(thirdPartyCode=='') {
		ygdg.dialog.alert("供应商款色编码不能为空!");
		return;
	}
	openwindow(BasePath+'/yitiansystem/ordergmt/orderdetail/queryProductToOutOrder.sc?thirdPartyCode='+thirdPartyCode+'&sellersId='+sellersId,800,495, '选择货品');
}

function selectNameToOutOrderFenxiao(){
	var sellersId="";
	sellersId = $("#sellersId").val();											// 购买人邮箱
	if(sellersId==""){
		ygdg.dialog.alert("为了计算会员优惠，请先查询出会员信息！");
		return;
	}
	var flag="1";
	flag = $("#flag").val();													// 标识大宗订单1 分销订单2
	var thirdPartyCode = $("#thirdPartyCode").val();
	if(thirdPartyCode=='') {
		ygdg.dialog.alert("供应商款色编码不能为空!");
		return;
	}
	openwindow(BasePath+'/yitiansystem/ordergmt/orderdetail/queryProductToOutOrderFenxiao.sc?flag='+flag+'&thirdPartyCode='+thirdPartyCode+'&sellersId='+sellersId,800,495, '选择货品');
}

//计算金额
function countmoney(){
	var error=false;
	var detail="";
	var buyEmail=$("#buyEmail").val();												// 购买人邮箱
	$("#tbody > tr").each(function(i,obj){
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var catNo=$(obj).find("td:eq(3)").html();									// 分类编码
		var productSpecification=$(obj).find("td:eq(4)").html();					// 货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							// 重量
		var productMoney=$("#"+productId+"salePrice").val();						// 单价
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		if(!isInteger(productNumber)){
			error=true;
			return;
		}
		var commodityMoney=$("#"+productId+"productMoney").val();					// 购买金额
		var commodityPicture = $("#"+productId+"commodityPicture").val();	
		var productType = $("#"+productId+"productType").val();	
		var orderDistributionSide = $("#"+productId+"orderDistributionSide").val();	
		var merchantCode = $("#"+productId+"merchantCode").val();					// 商家编码
		var thirdPartyCode = $("#"+productId+"thirdPartyCode").val();				// 商家款色编码
		var commodityDiscountAmt=$("#"+productId+"discountamount").val();			// 优惠金额
		if(commodityDiscountAmt==undefined){
			commodityDiscountAmt="0";
		}
		if(productType==undefined){
			productType="0";
		}
		if(commodityMoney==undefined){
			commodityMoney="0";
		}
		if(!isInteger(commodityDiscountAmt)){
			error=true;
			return;
		}
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="thirdCategoriesNo:'"+catNo+"',";
		detail+="commoditySpec:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="commodityMoney:'"+commodityMoney+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="productPrice:"+productMoney+",";
		detail+="commodityPicture:'"+commodityPicture+"',";
		detail+="productType:'"+productType+"',";
		detail+="commodityDiscountAmt:"+commodityDiscountAmt+",";
		detail+="orderDistributionSide:'"+orderDistributionSide+"',";
		detail+="merchantCode:'"+merchantCode+"',";
		detail+="thirdPartyCode:'"+thirdPartyCode+"',";
		detail+="productNum:"+productNumber+"}";
	});
	if(error){
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#detail").val(detail);
	var money = $("#expressMoney").val();
	var payMethod = $("#payMethod").val();
	var param = "detail="+detail;
	$("#maskId").mask("Waiting...");
	Belle.do_request("commodityListContainer",BasePath+"/yitiansystem/ordergmt/orderdetail/countMoney.sc?expressMoney="+money+"&shipMethod="+payMethod+"&buyEmail="+buyEmail+"",param,function(data){
		window.updateInnerHTML("commodityListContainer", data);
		$("#maskId").unmask();
	});
}

//分销订单  计算金额
function countmoneyfenxiao(){
	var error=false;
	var detail="";
	var buyEmail=$("#buyEmail").val();											// 购买人邮箱
	$("#tbody > tr").each(function(i,obj){
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var productSpecification=$(obj).find("td:eq(3)").html();					// 货品规格
		var productMoney=$("#"+productId+"salePrice").val();						// 单价
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		var commodityMoney=$("#"+productId+"productMoney").val();					// 购买金额  商品行小计
		if(!isInteger(productNumber)){
			error=true;
			return;
		}
		var supplyPrice=$("#"+productId+"supplyPrice").val();					// 购买金额  供货价
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="commoditySpec:'"+productSpecification+"',";
		detail+="supplyPrice:'"+supplyPrice+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="productPrice:"+productMoney+",";
		detail+="commodityMoney:'"+commodityMoney+"',";
		detail+="productNum:"+productNumber+"}";
		
	});
	if(error){
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#detail").val(detail);
	var money = $("#expressMoney").val();
	var payMethod = $("#payMethod").val();
	var param = "detail="+detail;
	$("#maskId").mask("Waiting...");
	Belle.do_request("commodityListContainer",BasePath+"/yitiansystem/ordergmt/orderdetail/countMoney.sc?expressMoney="+money+"&shipMethod="+payMethod+"&buyEmail="+buyEmail+"",param,function(data){
		window.updateInnerHTML("commodityListContainer", data);
		$("#maskId").unmask();
	});
}

function sumMoney(){
	var error=false;
	var detail="";
	var buyEmail=$("#buyEmail").val();											// 购买人邮箱
	$("#tbody > tr").each(function(i,obj){
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var catNo=$(obj).find("td:eq(3)").html();									// 分类编码
		
		var productSpecification=$(obj).find("td:eq(4)").html();					// 货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							// 重量
		var productMoney=$("#"+productId+"salePrice").val();						// 单价
		if(!isPirce(productMoney)){
			ygdg.dialog.alert("单价非法！");
			return;
		}
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		if(!isInteger(productNumber)){
			error=true;
			return;
		}
		var commodityMoney=productMoney;											// 购买金额
		var commodityPicture = $("#"+productId+"commodityPicture").val();	
		var commodityDiscountAmt=$("#"+productId+"discountamount").val();			// 优惠金额 自定义
		if(!isPirce(commodityDiscountAmt)){
			error=true;
			return;
		}
		if(formatFloat(parseFloat(commodityMoney))<formatFloat(parseFloat(commodityDiscountAmt))){
			ygdg.dialog.alert("优惠金额不能大于商品金额！");
			return;
		}
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="thirdCategoriesNo:'"+catNo+"',";
		detail+="commoditySpec:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="buyAmount:'"+commodityMoney+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="productPrice:"+productMoney+",";
		detail+="commodityPicture:'"+commodityPicture+"',";
		detail+="commodityDiscountAmt:"+commodityDiscountAmt+",";
		detail+="productNum:"+productNumber+"}";
		// 计算小计
		var myPrice=formatFloat((parseFloat(productMoney)-parseFloat(commodityDiscountAmt))*parseFloat(productNumber));
		$("#"+productId+"productMoney").val(myPrice);
	});
	if(error){
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#detail").val(detail);
	var money = $("#expressMoney").val();
	if(!isPirce(money)){
		ygdg.dialog.alert("金额非法！");
		return;
	}
	var payMethod = $("#payMethod").val();
	var param = "detail="+detail;
	$("#maskId").mask("Waiting...");
	Belle.do_request("commodityListContainer",BasePath+"/yitiansystem/ordergmt/orderdetail/sumMoney.sc?expressMoney="+money+"&shipMethod="+payMethod+"&buyEmail="+buyEmail+"",param,function(data){
		window.updateInnerHTML("commodityListContainer", data);
		$("#maskId").unmask();
	});
}

// 计算分销订单总计
function sumMoneyFenxiao(){
	var error=false;
	var detail="";
	var buyEmail=$("#buyEmail").val();												// 购买人邮箱
	$("#tbody > tr").each(function(i,obj){
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var productSpecification=$(obj).find("td:eq(4)").html();					// 货品规格
		var productMoney=$("#"+productId+"salePrice").val();						// 单价
		if(!isPirce(productMoney)){
			ygdg.dialog.alert("单价非法！");
			return;
		}
		var supplyPrice=$("#"+productId+"supplyPrice").val();						// 供货价
		if(!isPirce(supplyPrice)){
			ygdg.dialog.alert("供货价非法！");
			return;
		}
		var commodityDiscountAmt=formatFloat(formatFloat(parseFloat(productMoney))-formatFloat(parseFloat(supplyPrice)));//优惠金额=单价-供货价
		
		if(!isPirce(commodityDiscountAmt)){
			commodityDiscountAmt="0";
		}
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		if(!isInteger(productNumber)){
			error=true;
			return;
		}
		detail+=",{productNo:'"+productId+"',";//货品编码
		detail+="commodityNo:'"+commodityNo+"',";//商品编码
		detail+="commoditySpec:'"+productSpecification+"',";//货品规格
		detail+="buyAmount:'"+supplyPrice+"',";//购买价格=供货价
		detail+="commodityId:'"+commodityId+"',";//商品id
		detail+="commodityName:'"+productName+"',";//商品名称
		detail+="productPrice:"+productMoney+",";//商品单价
		detail+="commodityDiscountAmt:"+commodityDiscountAmt+",";
		detail+="productNum:"+productNumber+"}";
		// 计算小计
		var myPrice=formatFloat((parseFloat(productMoney)-parseFloat(commodityDiscountAmt))*parseFloat(productNumber));
		$("#"+productId+"productMoney").val(myPrice);
		$("#"+productId+"discountamount").val(commodityDiscountAmt);
	});
	if(error){
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#detail").val(detail);
	var money = $("#expressMoney").val();
	if(!isPirce(money)){
		ygdg.dialog.alert("金额非法！");
		return;
	}
	var payMethod = $("#payMethod").val();
	var param = "detail="+detail;
	$("#maskId").mask("Waiting...");
	Belle.do_request("commodityListContainer",BasePath+"/yitiansystem/ordergmt/orderdetail/sumMoneyFenxiao.sc?expressMoney="+money+"&shipMethod="+payMethod+"&buyEmail="+buyEmail+"",param,function(data){
		window.updateInnerHTML("commodityListContainer", data);
		$("#maskId").unmask();
	});
}

//后台添加淘宝拍拍订单
function createOrder(){
	var buyName=$("#buyName").val();									// 购买人姓名
	if(buyName.trim()==""){
		ygdg.dialog.alert("会员帐号不能为空！");
		return;
	}
	var province=$("#province").val();									// 购买人 省
	var city=$("#city").val();											// 购买人 市
	var hometown=$("#hometown").val();									// 购买人 区
	var buyAddress=$("#buyAddress").val();								// 购买人地址
	var buyZipCod=$("#buyZipCod").val();								// 购买人邮编
	var buyPhone=$("#buyPhone").val();									// 购买人电话
	var buyCellPhone=$("#buyCellPhone").val();							// 购买人手机
	var buyEmail=$("#buyEmail").val();									// 购买人email
	var consigneeName=$("#consigneeName").val();						// 收货人姓名
	if(consigneeName.trim()==""){
		ygdg.dialog.alert("收货人姓名不能为空！");
		return;
	}
	var provinces=$("#provinces").val();								// 收货人 省
	if(provinces.trim()==""){
		ygdg.dialog.alert("收货人省份不能为空！");
		return;
	}
	var citys=$("#citys").val();										// 收货人 市
	if(citys.trim()==""){
		ygdg.dialog.alert("收货人城市不能为空！");
		return;
	}
	var hometowns=$("#hometowns").val();								// 收货人 区
	if(hometowns.trim()==""){
		ygdg.dialog.alert("收货人地区不能为空！");
		return;
	}
	var consigneeAddress=$("#consigneeAddress").val();					// 收货人地址
	if(consigneeAddress.trim()==""){
		ygdg.dialog.alert("收货人地址不能为空！");
		return;
	}
	var consigneeZcop=$("#consigneeZcop").val();						// 收货人邮编
	if(consigneeZcop.trim()==""){
		ygdg.dialog.alert("收货人邮编不能为空！");
		return;
	}
	var consigneePhone=$("#consigneePhone").val();						// 收货人手机
	if(consigneePhone.trim()==""){
		ygdg.dialog.alert("收货人手机不能为空！");
		return;
	}
	var consigneeCellPhone=$("#consigneeCellPhone").val();				// 收货人
																		// 电话
	if(consigneeCellPhone.trim()==""){
		consigneeCellPhone=consigneePhone;
	}
	var consigneeEmail=$("#consigneeEmail").val();						// 收货人邮箱
	var productTotalNumber=$("#productTotalNumber").val();				// 总数量
	var productTotalMoney=$("#productTotalMoney").val();   				// 总金额
	var productTotalWeight=$("#productTotalWeight").val();				// 总重量
	var productTotalPrivailege=$("#productTotalPrivailege").val();		// 总优惠
	var productTotalPrice=$("#productTotalPrice").val();				// 原始金额
	var productTotalGivingScores=$("#productTotalGivingScores").val();	// 赠送积分
	var deliveryTime=$("input:radio[name='deliveryTime'][checked]").val();	// 送货日期
	var payMethod=$("#payMethod").val();									// 配送方式
	var logisticsName=$("#logisticsName").val();							// 物流公司
	var expressMoney=$("#expressMoney").val();								// 配送费用
	var defray=$("#defray").val();											// 支付方式
	var payStatus=$("input:radio[name='payStatus'][checked]").val();
	if(defray.trim()==""){
		ygdg.dialog.alert("支付方式不能为空！");
		return;
	}
	var outShopName=$("#orderSourceId").find("option:selected").text();			// 来源店铺
	if(outShopName==""){
		ygdg.dialog.alert("请选择店铺！");
		return;
	}
	var orderSourceId=$("#orderSourceId").val();  								// 订单来源
	var outOrderId=$("#outorder").val();
	if(outOrderId==""){
		ygdg.dialog.alert("请输入原始订单号！");
		return;
	}
	var error=false;  // 错误标记
	var detail="";
	var count = 0 ;
	var submitFlag = false;
	var noProductName = "";
	$("#tbody > tr").each(function(i,obj){
		count ++;
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var catNo=$(obj).find("td:eq(3)").html();									// 分类编码
		
		var productSpecification=$(obj).find("td:eq(4)").html();					// 货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							// 重量
		var givingScores=$(obj).find("td:eq(6)").html();							// 积分
		var productMoney=$("#"+productId+"salePrice").val();						// 优购价
		var commodityDiscountAmt;													// 优惠金额
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		if(!isInteger(productNumber)){
			error=true;
			return;
		}
		var couponSchemeId=$("#"+productId+"couponSchemeId").val();	
		var productType=$("#"+productId+"productType").val();	
		var couponNum=$("#"+productId+"couponNum").val();	
		var orderDistributionSide = $("#"+productId+"orderDistributionSide").val();	
		var merchantCode = $("#"+productId+"merchantCode").val();	
		var thirdPartyCode = $("#"+productId+"thirdPartyCode").val();				// 商家款色编码
		var commodityPublicPrice=$(obj).find("td:eq(11)").html();					// 市场价格
		var discountamount=$("#"+productId+"discountamount").val();					// 优惠金额
																					// 自定义
		if(!isPirce(discountamount)){
			error=true;
			return;
		}
		discountamount=formatFloat(parseFloat(discountamount));
		var commodityMoney;
		var activePrefAmount;
		var memberDiscPrefAmount;
		commodityMoney=$(obj).find("input:eq(3)").val();
		activePrefAmount = "0.0";
		memberDiscPrefAmount = "0.0";
		commodityDiscountAmt=formatFloat(productNumber*discountamount);			// 订单明细优惠金额 单件优惠*数量
		if(productNumber==0 || productNumber=="0" ){
			noProductName = productName;
			submitFlag = true;
		}
		var commodityImage =$("#"+productId+"commodityPicture").val();	
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="catNo:'"+catNo+"',";
		detail+="commoditystandrad:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="gvingScores:'"+givingScores+"',";
		detail+="commodityMoney:'"+commodityMoney+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="commodityUnitPrice:'"+productMoney+"',";
		detail+="commodityDiscountAmt:'"+commodityDiscountAmt+"',";
		detail+="commodityImage:'"+commodityImage+"',";
		detail+="commodityType:'"+productType+"',";
		detail+="activeName:'',";
		detail+="couponSchemeId:'"+couponSchemeId+"',";
		detail+="couponNum:'"+couponNum+"',";
		detail+="activePrefAmount:'"+activePrefAmount+"',";
		detail+="memberDiscPrefAmount:'"+memberDiscPrefAmount+"',";
		detail+="commodityNum:'"+productNumber+"',";
		detail+="orderDistributionSide:'"+orderDistributionSide+"',";
		detail+="merchantCode:'"+merchantCode+"',";
		detail+="thirdPartyCode:'"+thirdPartyCode+"',";
		detail+="commodityPublicPrice:'"+commodityPublicPrice+"'}";
	});
	if(detail==""){
		alert("请选择货品！");
		return;
	}
	if(error){
		return;
	}
	var json="{";
		// 购买人信息
		if(buyName!=""){json+="buyName:'"+buyName+"',";}
		if(province!=""){json+="province:'"+province+"',";}
		if(city!=""){json+="city:'"+city+"',";}
		if(hometown!=""){json+="hometown:'"+hometown+"',";}
		if(buyAddress!=""){json+="buyAddress:'"+buyAddress+"',";}
		if(buyZipCod!=""){json+="buyZipCod:'"+buyZipCod+"',";}
		if(buyPhone!=""){json+="buyPhone:'"+buyPhone+"',";}
		if(buyCellPhone!=""){json+="buyCellPhone:'"+buyCellPhone+"',";}
		json+="buyEmail:'"+buyEmail+"',";
		//收货人信息
		json+="consigneeName:'"+consigneeName+"',";
		json+="provinces:'"+provinces+"',";
		json+="citys:'"+citys+"',";
		json+="hometowns:'"+hometowns+"',";
		json+="consigneeAddress:'"+consigneeAddress+"',";
		json+="consigneeZipCod:'"+consigneeZcop+"',";
		json+="consigneePhone:'"+consigneePhone+"',";
		json+="consigneeCellPhone:'"+consigneeCellPhone+"',";
		if(consigneeEmail.trim()!=""){
			json+="consigneeEmail:'"+consigneeEmail+"',";
		}
		
		//商品信息
		json+="productTotalNumber:'"+productTotalNumber+"',";
		json+="productTotalMoney:'"+productTotalMoney+"',";
		json+="productTotalWeight:'"+productTotalWeight+"',";
		json+="productTotalPrivailege:'"+productTotalPrivailege+"',";
		json+="productTotalScore:"+productTotalGivingScores+",";
		json+="productTotalPrice:'"+productTotalPrice+"',";
		// 其他信息
		json+="deliverDoodsDate:'"+deliveryTime+"',";
		json+="payMethod:'"+payMethod+"',";
		json+="logisticsName:'"+logisticsName+"',";
		json+="expressMoney:'"+expressMoney+"',";
		json+="defray:'"+defray+"',";
		// 订单相关信息
		json+="payStatus:'"+payStatus+"',";// 是否支付信息 用于淘宝的订单
		json+="outShopName:'"+outShopName+"',";
		json+="orderSourceId:'"+orderSourceId+"',";
		json+="outOrderId:'"+outOrderId+"'";
		json+="}";
	
	if(submitFlag){
		ygdg.dialog.alert(noProductName+"库存不足，不能购买！");
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#json").val(json);
	$("#detail").val(detail);
	$("#createOrderBtn").attr("disabled","disabled");
	var orderFlag=$("#orderFlag").val();
	$(".wms").mask("Waiting...");
	$.post(BasePath+"/yitiansystem/ordergmt/orderdetail/c_order.sc",{json:json,detail:detail,orderFlag:orderFlag},function(data){
		if (data == "1") {
			$(".wms").unmask();
			ygdg.dialog.alert("添加成功！");
		} else {
			$(".wms").unmask();
			ygdg.dialog.alert("添加失败！");
		}
		if(orderFlag=="2"){
			window.location.href = BasePath+"/yitiansystem/ordergmt/orderflow/queryNewOrder.sc";
		}else{
			window.location.href = BasePath+"/yitiansystem/ordergmt/orderflow/queryNewOrders.sc";
		}
	});
}

//后台添加大宗客户订单  
function createLargeCustomersOrder(){
	var orderFlags=$("#orderFlag").val();								// 判断后台下订单的来源
	var buyName=$("#buyName").val();									// 购买人姓名
	var buyEmail=$("#buyEmail").val();								    // 分销商名称
	var province=$("#province").val();									// 购买人 省
	var city=$("#city").val();											// 购买人 市
	var hometown=$("#hometown").val();									// 购买人 区
	var buyAddress=$("#buyAddress").val();								// 购买人地址
	var buyZipCod=$("#buyZipCod").val();								// 购买人邮编
	var buyPhone=$("#buyPhone").val();									// 购买人电话
	var buyCellPhone=$("#buyCellPhone").val();							// 购买人手机
	
	var consigneeEmail=$("#consigneeEmail").val();						// 收货人邮箱
	var consigneeName=$("#consigneeName").val();						// 收货人姓名
	if(consigneeName.trim()==""){
		ygdg.dialog.alert("收货人姓名不能为空！");
		return;
	}
	var provinces=$("#provinces").val();								// 收货人 省
	if(provinces.trim()==""){
		ygdg.dialog.alert("收货人省份不能为空！");
		return;
	}
	var citys=$("#citys").val();										// 收货人 市
	if(citys.trim()==""){
		ygdg.dialog.alert("收货人城市不能为空！");
		return;
	}
	var hometowns=$("#hometowns").val();								// 收货人 区
	if(hometowns.trim()==""){
		ygdg.dialog.alert("收货人地区不能为空！");
		return;
	}
	var consigneeAddress=$("#consigneeAddress").val();					// 收货人地址
	if(consigneeAddress.trim()==""){
		ygdg.dialog.alert("收货人地址不能为空！");
		return;
	}
	var consigneeZcop=$("#consigneeZipCod").val();						// 收货人邮编
	if(consigneeZcop.trim()==""){
		ygdg.dialog.alert("收货人邮编不能为空！");
		return;
	}
	var consigneePhone=$("#consigneePhone").val();						// 收货人手机
	var consigneeCellPhone=$("#consigneeCellPhone").val();				// 收货人电话
	if(consigneePhone.trim()==""&&consigneeCellPhone.trim()==""){
		ygdg.dialog.alert("收货人手机和电话至少填写一项！");
		return;
	}
	var productTotalNumber=$("#productTotalNumber").val();				// 总数量
	if(!isInteger(productTotalNumber)){
		ygdg.dialog.alert("总数量非法！");
		return;
	}
	var productTotalMoney=$("#productTotalMoney").val();   				// 总金额
	if(!isPirce(productTotalMoney)){
		ygdg.dialog.alert("总金额非法！");
		return;
	}
	var productTotalWeight=$("#productTotalWeight").val();				// 总重量
	var productTotalPrivailege=$("#productTotalPrivailege").val();		// 总优惠
	if(!isPirce(productTotalPrivailege)){
		ygdg.dialog.alert("总优惠非法！");
		return;
	}
	var productTotalPrice=$("#productTotalPrice").val();				// 原始金额
	if(!isPirce(productTotalPrice)){
		ygdg.dialog.alert("原始金额非法！");
		return;
	}
	var productTotalGivingScores=$("#productTotalGivingScores").val();	// 赠送积分
	if(!isInteger(productTotalGivingScores)){
		ygdg.dialog.alert("赠送积分非法！");
		return;
	}
	var logisticsName=$("#logisticsName").val();							// 物流公司
	var expressMoney=$("#expressMoney").val();								// 配送费用
	if(!isPirce(expressMoney)){
		ygdg.dialog.alert("配送费用非法！");
		return;
	}
	var deliveryTime="ALL_CAN_DELIVER";	// 送货日期
	var defray=$("#defray").val();											// 支付方式
	if(defray.trim()==""){
		ygdg.dialog.alert("支付方式不能为空！");
		return;
	}
	var outShopName="";			// 来源店铺
	var orderSourceId=$("#orderSourceId").val();  							// 订单来源
	var outOrderId=$("#outorder").val();									// 原始订单号
	var isPartDeliver;														// 是否允许部分发货
	if($("#isPartDeliver").attr("checked")==true){
		isPartDeliver=1;
	}else{
		isPartDeliver=0;
	}
	var error=false;  // 错误标记
	var detail="";
	var count = 0 ;
	var submitFlag = false;
	var noProductName = "";
	$("#tbody > tr").each(function(i,obj){
		count ++;
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var catNo=$(obj).find("td:eq(3)").html();									// 分类编码
		
		var productSpecification=$(obj).find("td:eq(4)").html();					// 货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							// 重量
		var givingScores=$(obj).find("td:eq(6)").html();							// 积分
		var productMoney=$("#"+productId+"salePrice").val();						// 优购价
		if(!isPirce(productMoney)){
			ygdg.dialog.alert("优购价非法，请重新填写！");
			return;
		}
		var commodityDiscountAmt;													// 优惠金额
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		if(!isInteger(productNumber)){
			ygdg.dialog.alert("购买数量非法，请重新填写！");
			return;
		}
		var couponSchemeId=$("#"+productId+"couponSchemeId").val();	
		var productType=$("#"+productId+"productType").val();	
		var couponNum=$("#"+productId+"couponNum").val();	
		var orderDistributionSide = $("#"+productId+"orderDistributionSide").val();	
		var merchantCode = $("#"+productId+"merchantCode").val();	
		var thirdPartyCode = $("#"+productId+"thirdPartyCode").val();				// 商家款色编码
		var commodityPublicPrice=$(obj).find("td:eq(11)").html();					// 市场价格
		var discountamount=$("#"+productId+"discountamount").val();					// 优惠金额
		if(!isPirce(discountamount)){
			error=true;
			return;
		}
		discountamount=formatFloat(parseFloat(discountamount));
		var commodityMoney;
		var activePrefAmount;
		var memberDiscPrefAmount;
		if(orderFlags==2){
			commodityMoney=$(obj).find("td:eq(10)").html();							// 小计
			activeName =$("#"+productId+"activeName").val();
			activePrefAmount =$("#"+productId+"activePrefAmount").val();
			memberDiscPrefAmount =$("#"+productId+"memberDiscPrefAmount").val();
			commodityDiscountAmt=$(obj).find("td:eq(8)").html();
		}else{
			commodityMoney=$(obj).find("input:eq(3)").val();
			activeName="手动修改优惠金额";
			activePrefAmount = "0.0";
			memberDiscPrefAmount = "0.0";
			commodityDiscountAmt=formatFloat(productNumber*discountamount);// 优惠金额*数量
		}
		commodityDiscountAmt=formatFloat(commodityDiscountAmt);
		if(!isPirce(commodityDiscountAmt)){
			ygdg.dialog.alert("优惠金额非法！");
		}
		if(productNumber==0 || productNumber=="0" ){
			noProductName = productName;
			submitFlag = true;
		}
		var commodityImage =$("#"+productId+"commodityPicture").val();	
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="catNo:'"+catNo+"',";
		detail+="commoditystandrad:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="gvingScores:'"+givingScores+"',";
		detail+="commodityMoney:'"+commodityMoney+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="commodityUnitPrice:'"+productMoney+"',";
		detail+="commodityDiscountAmt:'"+commodityDiscountAmt+"',";
		detail+="commodityImage:'"+commodityImage+"',";
		detail+="commodityType:'"+productType+"',";
		detail+="activeName:'',";
		detail+="couponSchemeId:'"+couponSchemeId+"',";
		detail+="couponNum:'"+couponNum+"',";
		detail+="activePrefAmount:'"+activePrefAmount+"',";
		detail+="memberDiscPrefAmount:'"+memberDiscPrefAmount+"',";
		detail+="commodityNum:'"+productNumber+"',";
		detail+="orderDistributionSide:'"+orderDistributionSide+"',";
		detail+="merchantCode:'"+merchantCode+"',";
		detail+="thirdPartyCode:'"+thirdPartyCode+"',";
		detail+="commodityPublicPrice:'"+commodityPublicPrice+"'}";
	});
	if(detail==""){
		alert("请选择货品！");
		return;
	}
	if(error){
		return;
	}
	var json="{";
		// 购买人信息
		if(orderFlags!=2){
			if(buyName!=""){json+="buyName:'"+buyName+"',";}
			if(buyEmail!=""){json+="buyEmail:'"+buyEmail+"',";}
			if(province!=""){json+="province:'"+province+"',";}
			if(city!=""){json+="city:'"+city+"',";}
			if(hometown!=""){json+="hometown:'"+hometown+"',";}
			if(buyAddress!=""){json+="buyAddress:'"+buyAddress+"',";}
			if(buyZipCod!=""){json+="buyZipCod:'"+buyZipCod+"',";}
			if(buyPhone!=""){json+="buyPhone:'"+buyPhone+"',";}
			if(buyCellPhone!=""){json+="buyCellPhone:'"+buyCellPhone+"',";}
		}
		//收货人信息
		json+="consigneeName:'"+consigneeName+"',";
		json+="consigneeEmail:'"+consigneeEmail+"',";
		json+="provinces:'"+provinces+"',";
		json+="citys:'"+citys+"',";
		json+="hometowns:'"+hometowns+"',";
		json+="consigneeAddress:'"+consigneeAddress+"',";
		json+="consigneeZipCod:'"+consigneeZcop+"',";
		json+="consigneePhone:'"+consigneePhone+"',";
		json+="consigneeCellPhone:'"+consigneeCellPhone+"',";
		//商品信息
		json+="productTotalNumber:'"+productTotalNumber+"',";
		json+="productTotalMoney:'"+productTotalMoney+"',";
		json+="productTotalWeight:'"+productTotalWeight+"',";
		json+="productTotalPrivailege:'"+productTotalPrivailege+"',";
		json+="productTotalScore:"+productTotalGivingScores+",";
		json+="productTotalPrice:'"+productTotalPrice+"',";
		// 其他信息
		json+="deliverDoodsDate:'"+deliveryTime+"',";
		json+="logisticsName:'"+logisticsName+"',";
		json+="expressMoney:'"+expressMoney+"',";
		json+="defray:'"+defray+"',";
		json+="isPartDeliver:"+isPartDeliver+",";
		// 订单相关信息
		if(orderFlags==2){
			json+="outShopName:'优购后台',";
			json+="orderSourceId:'YG-YGKF-YGKF'";
		}else{
			json+="outShopName:'"+outShopName+"',";
			json+="orderSourceId:'"+orderSourceId+"',";
			json+="outOrderId:'"+outOrderId+"'";
		}
		json+="}";
	
	if(submitFlag){
		ygdg.dialog.alert(noProductName+"库存不足，不能购买！");
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#json").val(json);
	$("#detail").val(detail);
	$("#createOrderBtn").attr("disabled","disabled");
	var orderFlag=$("#orderFlag").val();
	$(".wms").mask("Waiting...");
	$.post(BasePath+"/yitiansystem/ordergmt/orderdetail/c_order.sc",{json:json,detail:detail,orderFlag:orderFlag},function(data){
		if (data == "1") {
			$(".wms").unmask();
			ygdg.dialog.alert("添加成功！");
		} else {
			$(".wms").unmask();
			ygdg.dialog.alert(data);
		}
		window.location.href = BasePath+"/yitiansystem/ordergmt/largeCustomersOrder/addLargeCustomersOrders.sc";
	});
}

//后台添加大宗客户订单  分销订单
function createLargeCustomersAndFenxiaoOrder(){
	var orderFlags=$("#orderFlag").val();								// 判断后台下订单的来源
	var buyName=$("#buyName").val();									// 购买人姓名
	var buyEmail=$("#buyEmail").val();								    // 分销商名称
	var province=$("#province").val();									// 购买人 省
	var city=$("#city").val();											// 购买人 市
	var hometown=$("#hometown").val();									// 购买人 区
	var buyAddress=$("#buyAddress").val();								// 购买人地址
	var buyZipCod=$("#buyZipCod").val();								// 购买人邮编
	var buyPhone=$("#buyPhone").val();									// 购买人电话
	var buyCellPhone=$("#buyCellPhone").val();							// 购买人手机
	
	var consigneeEmail=$("#consigneeEmail").val();						// 收货人邮箱
	var consigneeName=$("#consigneeName").val();						// 收货人姓名
	if(consigneeName.trim()==""){
		ygdg.dialog.alert("收货人姓名不能为空！");
		return;
	}
	var provinces=$("#provinces").val();								// 收货人 省
	if(provinces.trim()==""){
		ygdg.dialog.alert("收货人省份不能为空！");
		return;
	}
	var citys=$("#citys").val();										// 收货人 市
	if(citys.trim()==""){
		ygdg.dialog.alert("收货人城市不能为空！");
		return;
	}
	var hometowns=$("#hometowns").val();								// 收货人 区
	if(hometowns.trim()==""){
		ygdg.dialog.alert("收货人地区不能为空！");
		return;
	}
	var consigneeAddress=$("#consigneeAddress").val();					// 收货人地址
	if(consigneeAddress.trim()==""){
		ygdg.dialog.alert("收货人地址不能为空！");
		return;
	}
	var consigneeZcop=$("#consigneeZipCod").val();						// 收货人邮编
	if(consigneeZcop.trim()==""){
		ygdg.dialog.alert("收货人邮编不能为空！");
		return;
	}
	var consigneePhone=$("#consigneePhone").val();						// 收货人手机
	var consigneeCellPhone=$("#consigneeCellPhone").val();				// 收货人电话
	if(consigneePhone.trim()==""&&consigneeCellPhone.trim()==""){
		ygdg.dialog.alert("收货人手机和电话至少填写一项！");
		return;
	}
	var productTotalNumber=$("#productTotalNumber").val();				// 总数量
	if(!isInteger(productTotalNumber)){
		ygdg.dialog.alert("总数量非法！");
		return;
	}
	var productTotalMoney=$("#productTotalMoney").val();   				// 总金额
	if(!isPirce(productTotalMoney)){
		ygdg.dialog.alert("总金额非法！");
		return;
	}
	var productTotalPrivailege=$("#productTotalPrivailege").val();		// 总优惠
	if(!isPirce(productTotalPrivailege)){
		ygdg.dialog.alert("总优惠非法！");
		return;
	}
	var productTotalPrice=$("#productTotalPrice").val();				// 原始金额
	if(!isPirce(productTotalPrice)){
		ygdg.dialog.alert("原始金额非法！");
		return;
	}
	var productTotalGivingScores=$("#productTotalGivingScores").val();	// 赠送积分
	if(!isInteger(productTotalGivingScores)){
		ygdg.dialog.alert("赠送积分非法！");
		return;
	}
	var logisticsName=$("#logisticsName").val();						// 物流公司
	var postageOnDelivery=0;											// 物流是否到付 默认否
	if(logisticsName=="WL998"){
		var item = $("input[name='postageOnDelivery']:checked");   
		var len=item.length;   
		if(len>0){   
			postageOnDelivery=$("input[name='postageOnDelivery']:checked").val();
		}else{
			alert("请选择物流费用是否到付！");
		}   
	}
	
	var expressMoney=$("#expressMoney").val();								// 配送费用
	if(!isPirce(expressMoney)){
		ygdg.dialog.alert("配送费用非法！");
		return;
	}
	var deliveryTime="ALL_CAN_DELIVER";										// 送货日期
	var warehouseCode=$("#warehouseCode").val();							// 发货仓库
	var defray=$("#defray").val();											// 支付方式
	if(defray.trim()==""){
		ygdg.dialog.alert("支付方式不能为空！");
		return;
	}
	var outShopName="";			// 来源店铺
	var orderSourceId=$("#orderSourceId").val();  							// 订单来源
	var isPartDeliver=$("#isPartDeliver").attr("checked")==true?1:0;		// 是否部分发货 按实际库存发货
	var flag=$("#flag").val();												// 区分大宗订单 分销订单
	var outOrderId=$("#outorder").val();									// 原始订单号
	// 分销订单原是订单号必填
	if(flag==2&&outOrderId.trim()==""){
		ygdg.dialog.alert("原始订单号不能为空！");
		return;
	}
	var detail="";
	var count = 0 ;
	$("#tbody > tr").each(function(i,obj){
		count ++;
		var commodityNo=$(obj).find("td:eq(0)").html();								// 商品编码
		var productId=$(obj).find("td:eq(1)").html();								// 货品编号
		var productName=$(obj).find("td:eq(2)").html();							    // 商品名称
		var productSpecification=$(obj).find("td:eq(4)").html();					// 货品规格
		var productMoney=$("#"+productId+"salePrice").val();						// 单价
		if(!isPirce(productMoney)){
			ygdg.dialog.alert("单价非法！");
			return;
		}
		var supplyPrice=$("#"+productId+"supplyPrice").val();						// 供货价
		if(!isPirce(supplyPrice)){
			ygdg.dialog.alert("供货价非法！");
			return;
		}
		var productNumber=$("#"+productId+"productNumber").val();					// 购买数量
		if(!isInteger(productNumber)){
			error=true;
			return;
		}
		var commodityDiscountAmt=formatFloat(formatFloat(parseFloat(productMoney))-formatFloat(parseFloat(supplyPrice)));//优惠金额=单价-供货价
		var commodityDiscountAmtTotal=formatFloat(commodityDiscountAmt*parseFloat(productNumber));//单价优惠价*货品数量
		var commodityId=$("#"+productId+"commodityId").val();						// 商品ID
		var commodityPublicPrice=$("#"+productId+"publicPrice").val();				// 市场价格
		detail+=",{productNo:'"+productId+"',";//货品编码
		detail+="commodityNo:'"+commodityNo+"',";//商品编码
		detail+="commoditySpec:'"+productSpecification+"',";//货品规格
		detail+="buyAmount:'"+supplyPrice+"',";//购买价格=供货价
		detail+="commodityId:'"+commodityId+"',";//商品id
		detail+="commodityName:'"+productName+"',";//商品名称
		detail+="commodityUnitPrice:"+productMoney+",";//商品单价
		detail+="commodityDiscountAmt:"+commodityDiscountAmtTotal+",";//一个订单明细优惠的总价
		detail+="commodityPublicPrice:'"+commodityPublicPrice+"',";
		detail+="commodityNum:"+productNumber+",";
		// 计算小计
		var myPrice=formatFloat((parseFloat(productMoney)-parseFloat(commodityDiscountAmt))*parseFloat(productNumber));
		$("#"+productId+"productMoney").val(myPrice);
		detail+="commodityMoney:'"+myPrice+"'}";
		$("#"+productId+"discountamount").val(commodityDiscountAmt);
	});
	if(detail==""){
		alert("请选择货品！");
		return;
	}
	var json="{";
	// 购买人信息
	if(orderFlags!=2){
		if(buyName!=""){json+="buyName:'"+buyName+"',";}
		if(buyEmail!=""){json+="buyEmail:'"+buyEmail+"',";}
		if(province!=""){json+="province:'"+province+"',";}
		if(city!=""){json+="city:'"+city+"',";}
		if(hometown!=""){json+="hometown:'"+hometown+"',";}
		if(buyAddress!=""){json+="buyAddress:'"+buyAddress+"',";}
		if(buyZipCod!=""){json+="buyZipCod:'"+buyZipCod+"',";}
		if(buyPhone!=""){json+="buyPhone:'"+buyPhone+"',";}
		if(buyCellPhone!=""){json+="buyCellPhone:'"+buyCellPhone+"',";}
	}
	//收货人信息
	json+="consigneeName:'"+consigneeName+"',";
	json+="consigneeEmail:'"+consigneeEmail+"',";
	json+="provinces:'"+provinces+"',";
	json+="citys:'"+citys+"',";
	json+="hometowns:'"+hometowns+"',";
	json+="consigneeAddress:'"+consigneeAddress+"',";
	json+="consigneeZipCod:'"+consigneeZcop+"',";
	json+="consigneePhone:'"+consigneePhone+"',";
	json+="consigneeCellPhone:'"+consigneeCellPhone+"',";
	//商品信息
	json+="productTotalNumber:'"+productTotalNumber+"',";
	json+="productTotalMoney:'"+productTotalMoney+"',";
	json+="productTotalPrivailege:'"+productTotalPrivailege+"',";
	json+="productTotalScore:"+productTotalGivingScores+",";
	json+="productTotalPrice:'"+productTotalPrice+"',";
	// 其他信息
	json+="deliverDoodsDate:'"+deliveryTime+"',";
	json+="logisticsName:'"+logisticsName+"',";
	json+="postageOnDelivery:'"+postageOnDelivery+"',";
	json+="expressMoney:'"+expressMoney+"',";
	json+="defray:'"+defray+"',";
	json+="isPartDeliver:"+isPartDeliver+",";
	json+="flag:"+flag+",";
	json+="warehouseCode:'"+warehouseCode+"',";
	// 订单相关信息
	if(orderFlags==2){
		json+="outShopName:'优购后台',";
		json+="orderSourceId:'YG-YGKF-YGKF'";
	}else{
		json+="outShopName:'"+outShopName+"',";
		json+="orderSourceId:'"+orderSourceId+"',";
		json+="outOrderId:'"+outOrderId+"'";
	}
	json+="}";
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#json").val(json);
	$("#detail").val(detail);
	$("#createOrderBtn").attr("disabled","disabled");
	var orderFlag=$("#orderFlag").val();
	$(".wms").mask("Waiting...");
	$.post(BasePath+"/yitiansystem/ordergmt/orderdetail/c_order.sc",{json:json,detail:detail,orderFlag:orderFlag},function(data){
		if (data == "1") {
			$(".wms").unmask();
			ygdg.dialog.alert("添加成功！");
		} else {
			$(".wms").unmask();
			ygdg.dialog.alert(data);
		}
		window.location.href = BasePath+"/yitiansystem/ordergmt/largeCustomersOrder/addFenxiaoOrders.sc?flag="+flag;
	});
}

var ii=1;
// 添加商品行
function Do(strhtml,id){
	var flag=false;
	$(".list_table2 tr:not(:first)").each(function(){
		if($(this).find("td:eq(1)").text()==id) flag=true;
	});
	if(flag) return;
	if(!flag) 
	{ii=ii+1;}
	$(".list_table2 tbody").append(strhtml);
	var orderFlag=$("#orderFlag").val();
	if(orderFlag=="2"){
		countmoney();
	}
}

// 分销订单 添加商品行
function Dofenxiao(strhtml,id){
	var flag=false;
	$(".list_table2 tr:not(:first)").each(function(){
		if($(this).find("td:eq(1)").text()==id) flag=true;
	});
	if(flag) return;
	if(!flag) 
	{ii=ii+1;}
	$(".list_table2 tbody").append(strhtml);
	var orderFlag=$("#orderFlag").val();
	if(orderFlag=="2"){
		countmoneyfenxiao();
	}
}

function DoTotal(strhtml)
{
	try	{
	$("#ttr").remove();
	}
	catch(err){};
	$(".list_table2 tbody").append(strhtml);
}

function DoSum()
{
	var productTotalMoney;
	$("#tab tr:gt(0)").each(function(i,obj){
		var wight=$(obj).find("td:eq(5)").val();
		var money=$(obj).find("td>input:eq(2)").val();
		var privingMoney=$(obj).find("td>input:eq(3)").val();
		var number=$(obj).find("td>input:eq(4)").val();
		productTotalMoney=eval(money);
	});
	$("#productTotalMoney").val();
}

