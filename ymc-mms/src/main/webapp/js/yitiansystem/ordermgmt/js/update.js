String.prototype.trim = function() {
	return this.replace(/(^[\s\t\xa0\u3000]+)|([\u3000\xa0\s\t]+$)/g, "");
};
//删除行
function DeleteRow(obj){
	if(window.confirm("确定删除？")){
		var rowObj = obj.parentNode.parentNode;
	    var targetTable = rowObj.parentNode;
	    targetTable.removeChild(rowObj);
	}
}

function pay(){
	$.ajax( {
		type : "POST",
		url : "/yitiansystem/ordergmt/orderdetail/payMoney.sc",
		data : {"payMethod" : $("#payMethod").val()},
		error: function(XmlHttpRequest, textStatus, errorThrown) { alert(errorThrown+"test: "+textStatus); },
		success : function(data) {
			var payMehtodNum = Number(data);
			payMehtodNum = isNaN(payMehtodNum) ? 0 : payMehtodNum;
			$("#expressMoney").val(payMehtodNum.toFixed(2));
		}
	});
}

function callBack(){
	document.creat.action="../orderflow/queryNewOrder.sc";
	document.creat.method="POST";
	document.creat.submit();
}

function member(){
	var memberId=$("#memberId").val();
	var buyName=$("#buyName").val();
	if(memberId==""){
		alert("会员账号不能为空！");
		return false;
	}else if(buyName="" && memberId==""){
		alert("该用户不存在！");
		return false;
	}
	$("#loginName").val(memberId);
	var param = "loginName="+memberId;
	Belle.do_request("memberListContainer","../orderdetail/queryMember.sc",param,null);
}
function load(){
	var memberId=$("#memberId").val();
	var buyName=$("#buyName").val();

	if(buyName == "" && memberId != ""){
		document.getElementById("member_id").style.display="block";
	}
}

function number(a){
	var tr=$(a).parent().parent();
	var money=tr.find("td>input:eq(0)").val();
	var productMoney=tr.find("td>input:eq(3)").val();
	var productPrivilege=tr.find("td>input:eq(4)").val();
	var productNumber=tr.find("td>input:eq(5)").val();
	productMoney=money*productNumber-productPrivilege;
	productPrivilege=money*productNumber-productMoney;

	tr.find("td>input:eq(2)").val(productMoney);
	tr.find("td>input:eq(3)").val(productPrivilege);
}
function productMoney(a) {
	var tr=$(a).parent().parent();
	var money=tr.find("td>input:eq(0)").val();
	var productMoney=tr.find("td>input:eq(3)").val();
	var productPrivilege=tr.find("td>input:eq(4)").val();
	var productNumber=tr.find("td>input:eq(5)").val();

	productPrivilege=money*productNumber-productMoney;
	tr.find("td>input:eq(3)").val(productPrivilege);
}

function selectName(){
	var name=$("#commodityName").val();
	openwindow('../../ordergmt/orderdetail/queryProduct.sc?commodityName='+name+'',800,495, '商品详情');
	//art.dialog.openwindow('../../ordergmt/orderdetail/queryProduct.sc?commodityName='+name+'',800,495,'no',{id:'open',title: '商品详情'});
}

function countmoney(){
	var detail="";
	var buyEmail=""
	buyEmail = $("#buyEmail").val();											//购买人邮箱
	if(buyEmail.trim()==""){
		alert("为了计算会员优惠，请先查询出会员信息！");
		return;
	}
	$("#tbody > tr").each(function(i,obj){

		var commodityNo=$(obj).find("td:eq(0)").html();								//商品编码
		var productId=$(obj).find("td:eq(1)").html();								//货品编号
		var productName=$(obj).find("td:eq(2)").html();							    //商品名称
		var catNo=$(obj).find("td:eq(3)").html();									//分类编码

		var productSpecification=$(obj).find("td:eq(4)").html();					//货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							//重量
		var productMoney=$("#"+productId+"salePrice").val();							//单价
		var commodityId=$("#"+productId+"commodityId").val();						//商品ID
		var productNumber=$("#"+productId+"productNumber").val();					//购买数量
		var commodityMoney=$("#"+productId+"productMoney").val();					//购买金额
		var commodityPicture = $("#"+productId+"commodityPicture").val();
		var productType = $("#"+productId+"productType").val();
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="thirdCategoriesNo:'"+catNo+"',";
		detail+="commoditySpec:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="commodityMoney:'"+commodityMoney+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="productPrice:"+productMoney+",";
		detail+="productType:'"+productType+"',";
		detail+="commodityPicture:'"+commodityPicture+"',";
		detail+="commodityDiscountAmt:0.00,";
		detail+="productNum:"+productNumber+"}";
	});
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#detail").val(detail);
	var money = $("#expressMoney").val();
	var payMethod = $("#payMethod").val();
	var param = "detail="+detail;
	$("#maskId").mask("Waiting...");
	Belle.do_request("commodityListContainer","../orderdetail/countMoney.sc?expressMoney="+money+"&shipMethod="+payMethod+"&buyEmail="+buyEmail+"",param,function(data){
		window.updateInnerHTML("commodityListContainer", data);
		$("#maskId").unmask();
	});
}


function sumMoney(){
	var detail="";
	var buyEmail="";
	buyEmail = $("#buyEmail").val();											//购买人邮箱
	if(buyEmail.trim()==""){
		alert("为了计算会员优惠，请先查询出会员信息！");
		return;
	}
	$("#tbody > tr").each(function(i,obj){
		var commodityNo=$(obj).find("td:eq(0)").html();								//商品编码
		var productId=$(obj).find("td:eq(1)").html();								//货品编号
		var productName=$(obj).find("td:eq(2)").html();							    //商品名称
		var catNo=$(obj).find("td:eq(3)").html();									//分类编码
		
		var productSpecification=$(obj).find("td:eq(4)").html();					//货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							//重量
		var productMoney=$("#"+productId+"salePrice").val();							//单价
		var productNumber=$(obj).find("input:eq(1)").val();							//购买数量
		var commodityMoney=$(obj).find("input:eq(2)").val();						//购买金额
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="thirdCategoriesNo:'"+catNo+"',";
		detail+="commoditySpec:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="buyAmount:'"+commodityMoney+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="productPrice:"+productMoney+",";
		detail+="commodityDiscountAmt:0.00,";
		detail+="productNum:"+productNumber+"}";
	});
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#detail").val(detail);
	var money = $("#expressMoney").val();
	var payMethod = $("#payMethod").val();
	var param = "detail="+detail;
	$("#maskId").mask("Waiting...");
	Belle.do_request("commodityListContainer","../orderdetail/sumMoney.sc?expressMoney="+money+"&shipMethod="+payMethod+"&buyEmail="+buyEmail+"",param,function(data){
		window.updateInnerHTML("commodityListContainer", data);
		$("#maskId").unmask();
	});
}

function createOrder(){
	var orderFlags=$("#orderFlag").val();
	var memberId=$("#memberId").val();        							//会员编号
	var buyName=$("#buyName").val();									//购买人姓名
	var province=$("#province").val();									//购买人     省
	var city=$("#city").val();											//购买人     市
	var hometown=$("#hometown").val();									//购买人     区
	var buyAddress=$("#buyAddress").val();								//购买人地址
	var buyZipCod=$("#buyZipCod").val();								//购买人邮编
	var buyPhone=$("#buyPhone").val();									//购买人电话
	var buyCellPhone=$("#buyCellPhone").val();							//购买人手机
	var buyEmail=$("#buyEmail").val();		//购买人邮箱
	var userId=$("#userId").val();	//购买人邮箱
	var attention=$("#attention").val();	//购买人邮箱
	if(attention==""){
		attention =1;
	}
	if(buyEmail.trim()==""){
		alert("购买人信息不能为空，请查询！");
		return;
	}
	var consigneeName=$("#consigneeName").val();	//收货人姓名
	if(consigneeName.trim()==""){
		alert("收货人姓名不能为空！");
		return;
	}
	var provinces=$("#provinces").val();								//收货人     省
	if(provinces.trim()==""){
		alert("收货人省份不能为空！");
		return;
	}
	var citys=$("#citys").val();										//收货人     市
	if(citys.trim()==""){
		alert("收货人城市不能为空！");
		return;
	}
	var hometowns=$("#hometowns").val();								//收货人     区
	if(hometowns.trim()==""){
		alert("收货人地区不能为空！");
		return;
	}
	var consigneeAddress=$("#consigneeAddress").val();					//收货人地址
	if(consigneeAddress.trim()==""){
		alert("收货人地址不能为空！");
		return;
	}
	var consigneeZcop=$("#consigneeZcop").val();						//收货人邮编
	if(consigneeZcop.trim()==""){
		alert("收货人邮编不能为空！");
		return;
	}
	var consigneePhone=$("#consigneePhone").val();						//收货人 电话
	if(consigneePhone.trim()==""){
		alert("收货人电话不能为空！");
		return;
	}
	var consigneeCellPhone=$("#consigneeCellPhone").val();				//收货人  手机
	if(consigneeCellPhone.trim()==""){
		alert("收货人手机不能为空！");
		return;
	}
	var consigneeEmail=$("#consigneeEmail").val();						//收货人邮箱
	var productTotalNumber=$("#productTotalNumber").val();				//总数量
	var productTotalMoney=$("#productTotalMoney").val();   				//总金额
	var productTotalWeight=$("#productTotalWeight").val();				//总重量
	var productTotalPrivailege=$("#productTotalPrivailege").val();		//总优惠
	var productTotalPrice=$("#productTotalPrice").val();				//原始金额
	var productTotalGivingScores=$("#productTotalGivingScores").val();	//赠送积分

//	if(productTotalMoney==0){
//		alert("请选择要购买的商品！");
//		return;
//	}
	var deliveryTime=$("input:radio[name='deliveryTime'][checked]").val();	//送货日期
	var payMethod=$("#payMethod").val();									//配送方式
	var expressMoney=$("#expressMoney").val();								//配送费用
	var defray=$("#defray").val();											//支付方式
	if(defray.trim()==""){
		alert("支付方式不能为空！");
		return;
	}
	
	var outShopId=$("#outShopId").val(); //来源店铺 ID
	var outShopName=$("#outShopId").find("option:selected").text();//来源店铺 
	var orderSourceId=$("#orderSourceId").val();  //订单来源
	var outOrderId=$("#outorder").val();
	if(orderFlags==3){
		if(outOrderId==""){
			alert("请输入来源的订单号！");
			return;
		}
	}
	var json="{";
		//购买人信息
		if(orderFlags!=2){
			json+="buyName:'"+buyName+"',";
			json+="province:'"+province+"',";
			json+="city:'"+city+"',";
			json+="hometown:'"+hometown+"',";
			json+="buyAddress:'"+buyAddress+"',";
			json+="buyZipCod:'"+buyZipCod+"',";
			json+="buyPhone:'"+buyPhone+"',";
			json+="buyCellPhone:'"+buyCellPhone+"',";
		}
		json+="buyEmail:'"+buyEmail+"',";
		if(orderFlags==2){
			json+="userId:'"+userId+"',";
			json+="attention:"+attention+",";
			json+="memberNo:'"+memberId+"',";
		}
		//收货人信息
		json+="consigneeName:'"+consigneeName+"',";
		json+="provinces:'"+provinces+"',";
		json+="citys:'"+citys+"',";
		json+="hometowns:'"+hometowns+"',";
		json+="consigneeAddress:'"+consigneeAddress+"',";
		json+="consigneeZipCod:'"+consigneeZcop+"',";
		json+="consigneePhone:'"+consigneePhone+"',";
		json+="consigneeCellPhone:'"+consigneeCellPhone+"',";
		json+="consigneeEmail:'"+consigneeEmail+"',";
		//商品信息
		json+="productTotalNumber:'"+productTotalNumber+"',";
		json+="productTotalMoney:'"+productTotalMoney+"',";
		json+="productTotalWeight:'"+productTotalWeight+"',";
		json+="productTotalPrivailege:'"+productTotalPrivailege+"',";
		json+="productTotalScore:"+productTotalGivingScores+",";
		json+="productTotalPrice:'"+productTotalPrice+"',";
		//其他信息
		json+="deliverDoodsDate:'"+deliveryTime+"',";
		json+="payMethod:'"+payMethod+"',";
		json+="expressMoney:'"+expressMoney+"',";
		json+="defray:'"+defray+"',";
		//订单相关信息
		if(orderFlags==2){
			json+="outShopId:'2',";
			json+="outShopName:'优购后台',";
			json+="orderSourceId:'1'";
		}else{
			json+="outShopId:'"+outShopId+"',";
			json+="outShopName:'"+outShopName+"',";
			json+="orderSourceId:'"+orderSourceId+"',";
			json+="outOrderId:'"+outOrderId+"'";
		}
		json+="}";

	var detail="";
	var count = 0 ;
	var submitFlag = false;
	var noProductName = "";
	$("#tbody > tr").each(function(i,obj){
		count ++;
		var commodityNo=$(obj).find("td:eq(0)").html();								//商品编码
		var productId=$(obj).find("td:eq(1)").html();								//货品编号
		var productName=$(obj).find("td:eq(2)").html();							    //商品名称
		var catNo=$(obj).find("td:eq(3)").html();									//分类编码

		var productSpecification=$(obj).find("td:eq(4)").html();					//货品规格
		var productWigth=$(obj).find("td:eq(5)").html();							//重量
		var givingScores=$(obj).find("td:eq(6)").html();							//积分
		var productMoney=$("#"+productId+"salePrice").val();							//单价
		var commodityDiscountAmt=$(obj).find("td:eq(8)").html();					//优惠金额
		var commodityId;
		var productNumber;
		var commodityMoney;
		var commodityImage ;
		var productType;
		var activeName ;
		var couponSchemeId;
		var couponNum;
		var activePrefAmount;
		var memberDiscPrefAmount;
		if(orderFlags==2){
			commodityId=$("#"+productId+"commodityId").val();						//商品ID
			productNumber=$("#"+productId+"productNumber").val();					//购买数量
			commodityMoney=$(obj).find("td:eq(10)").html();							//小计
			commodityImage=$("#"+productId+"commodityPicture").val();
			productType=$("#"+productId+"productType").val();
			activeName=$("#"+productId+"activeName").val();
			couponSchemeId=$("#"+productId+"couponSchemeId").val();	
			couponNum=$("#"+productId+"couponNum").val();
			activePrefAmount =$("#"+productId+"activePrefAmount").val();
			memberDiscPrefAmount =$("#"+productId+"memberDiscPrefAmount").val();
		}else{
			commodityId=$(obj).find("input:eq(0)").val();							//商品ID
			productNumber=$(obj).find("input:eq(1)").val();							//购买数量
			commodityMoney=$(obj).find("input:eq(2)").val();						//小计
			commodityImage=$(obj).find("input:eq(3)").val();
			productType=0;
			activeName="手动修改优惠金额";
			couponSchemeId="NULL";
			couponNum=0;
			activePrefAmount = "0.0";
			memberDiscPrefAmount = "0.0";
		}
		if(productNumber==0 || productNumber=="0" ){
			noProductName = productName;
			submitFlag = true;
		}
		detail+=",{productNo:'"+productId+"',";
		detail+="commodityNo:'"+commodityNo+"',";
		detail+="catNo:'"+catNo+"',";
		detail+="commoditystandrad:'"+productSpecification+"',";
		detail+="commodityWeight:'"+productWigth+"',";
		detail+="gvingScores:'"+givingScores+"',";
		detail+="commodityMoney:'"+commodityMoney+"',";
		detail+="commodityId:'"+commodityId+"',";
		detail+="commodityName:'"+productName+"',";
		detail+="commodityType:'"+productType+"',";
		detail+="commodityUnitPrice:'"+productMoney+"',";
		detail+="commodityDiscountAmt:'"+commodityDiscountAmt+"',";
		detail+="commodityImage:'"+commodityImage+"',";
		detail+="activeName:'"+activeName+"',";
		detail+="couponSchemeId:'"+couponSchemeId+"',";
		detail+="couponNum:'"+couponNum+"',";
		detail+="activePrefAmount:'"+activePrefAmount+"',";
		detail+="memberDiscPrefAmount:'"+memberDiscPrefAmount+"',";
		detail+="commodityNum:'"+productNumber+"'}";
	});
	if(submitFlag){
		alert(noProductName+"库存不足，不能购买！");
		return;
	}
	detail=detail.substring(1);
	detail="["+detail+"]";
	$("#json").val(json);
	$("#detail").val(detail);
	var mainNo=$("#loginName").val();
	var orderFlag=$("#orderFlag").val();
	$(".wms").mask("Waiting...");
	$.post("../orderdetail/u_order.sc",{json:json,detail:detail,mainNo:mainNo,orderFlag:orderFlag},function(data){
		if (data == "1") {
			$(".wms").unmask();
			alert("修改成功！");
		} else {
			$(".wms").unmask();
			alert("修改失败！");
		}
		if(orderFlag=="2"){
			window.location.href = "../orderflow/queryNewOrder.sc";
		}else{
			window.location.href = "../orderflow/queryNewOrders.sc";
		}
	});
}

var ii=1;
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