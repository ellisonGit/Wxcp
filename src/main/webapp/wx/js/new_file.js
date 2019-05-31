var money='';
var moneys='';
var moneytoo='';
var openId='';
var  appId='';
var timeStamp='';
var nonceStr=''; //1随机串
var packa='';//1
var paySign=''//签名
var Url_='http://llison.viphk.ngrok.org';
var JobNo='';//工号
$(function(){
//获取openId
	openId = getOpenIdFromCookie();

	var a=10;

	$(".person_wallet_recharge .ul li").click(function(e){
		$(this).addClass("current").siblings("li").removeClass("current");
		$(this).children(".sel").show(0).parent().siblings().children(".sel").hide(0);
		$("#txt").val('');

	});



	$(".botton").click(function(e){

			money=$(".person_wallet_recharge .ul li.current").text()

			moneytoo=$(".person_wallet_recharge #txt").val();
			//alert(money);


		var txt = $("#txt").val();
		if(!$(".person_wallet_recharge .ul li").hasClass('current') && txt ==''){
		layer.open({
            content: '请输入或选择金额',
            style: 'background:rgba(0,0,0,0.6); color:#fff; border:none;',
            time:3
           });
           return false;
		}
		if(!$(".person_wallet_recharge .ul li").hasClass('current')){
			if( txt < a){
				layer.open({
	            content: '金额要10元以上',
	            style: 'background:rgba(0,0,0,0.6); color:#fff; border:none;',
	            time:3
	           });
	           return false;
			}
		}
		$("#txt").val();
		$(".f-overlay").show();
		$(".addvideo").show();
		orderWx();//支付下单方法
	})

	$(".cal").click(function(e){
		$(".f-overlay").hide();
		$(".addvideo").hide();
	})

//该事件是隐藏选中金额的样式
	$(".pic").click(function() {
		$(".person_wallet_recharge .ul li").removeClass("current");
		$(".person_wallet_recharge .ul li").children(".sel").show(0).parent().siblings().children(".sel").hide(0);
	});


	//查询用户详细信息
	function loadUserInfo(){
		var url = "/api/forward/queryCardInfo";
		$.ajax({
			async: true,
			url: url,
			data:{
				openId:openId
			},
			type:'GET',
			success:function(data){
				var result = JSON.parse(data.data);
				JobNo=result.data.jobNo;
				$("#text1").html("工号:"+JobNo);
			}
		});
	}
	loadUserInfo();



//调用微信的方法
	function jsApiCall() {
		WeixinJSBridge.invoke(
			'getBrandWCPayRequest',
			{
				"appId":appId,     //公众号名称，由商户传入
				"timeStamp":timeStamp,         //1时间戳，自1970年以来的秒数
				"nonceStr":nonceStr, //随机串
				"package":packa,//
				"signType": "MD5",        //微信签名方式：
				"paySign":paySign //微信签名
			},
			function(res){
				if (res.err_msg == "get_brand_wcpay_request:ok") {
					alert("支付成功");
					console.log('支付成功');
					//支付成功后跳转的页面
				} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
					alert("支付取消");
					console.log('支付取消');
				} else if (res.err_msg == "get_brand_wcpay_request:fail") {
					alert("支付失败");
					console.log('支付失败');
					WeixinJSBridge.call('closeWindow');
				}
			}
			);
	}
//调用微信JS api 支付
	function callpay() {
		if (typeof WeixinJSBridge == "undefined"){
			if( document.addEventListener ){
				document.addEventListener('WeixinJSBridgeReady', jsApiCall(), false);
			}else if (document.attachEvent){
				document.attachEvent('WeixinJSBridgeReady', jsApiCall());
				document.attachEvent('onWeixinJSBridgeReady', jsApiCall());
			}
		}else{
			jsApiCall();
		}
	}
//调用order微信公众号下单
	function orderWx(){

		moneytoo=$(".person_wallet_recharge .ul li.current").text().replace(/(^\s*)|(\s*$)/g, "").substr(1);
		if(moneytoo!==null && moneytoo!=''){
			money = moneytoo;
		}
		moneys=$(".person_wallet_recharge #txt").val();
		if(moneys!==null && moneys!=''){
			money=moneys;
		}
		//alert(money);
	 var body = '充值费';
		$.ajax({
			async: true,
			type: "post",
			url: "/api/weixin/order",
			dataType:"json",
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
			//headers: {"token": localStorage.getItem('car_token')},
			/*beforeSend: function(xhr) {
				xhr.setRequestHeader("token",cx_names);
			},*/
			data:{
				"openId":openId,
				"money":money,
				"body":body,
				"JobNo":JobNo
			},
			success: function (data) {
				appId =data.appid;
				nonceStr = data.nonce_str; //随机数
				packa = data.package_str; //1
				paySign = data.pay_sign;   //1微信签名
				timeStamp = data.time_stamp;   //格林时间
				callpay();
			},
			error:function(err){
				return ;
			}
		});
	}

//验证是否已经绑定过
	function validateBind(){
		var url = "/api/forward/validateBanding";
		$.ajax({
			url: url,
			data:{
				openId:openId
			},
			type:'GET',
			success:function(data){
				var result = JSON.parse(data.data);
				//  alert(result);
				if(result.data=="-1"){
					//window.location.href=Url_+"/api/hnjca/auth?returnUrl="+Url_+"/api/wxPay.html";
					window.location.href=Url_+"/api/hnjca/auth?returnUrl="+Url_+"/api/banding.html";

				}
			}

		});

	}
	validateBind();
});
