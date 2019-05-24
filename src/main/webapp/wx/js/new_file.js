var money='';
var moneytoo='';
var openId='';
var  appId='';
var timeStamp='';
var nonceStr=''; //1随机串
var package='';//1
var paySign=''//签名
var Url_='';
$(function(){
	var sno='';
//获取openId
	openId = getOpenIdFromCookie();
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
				if(result=="-1"){
					window.location.href="http://llison.viphk.ngrok.org/api/hnjca/auth?returnUrl=http://llison.viphk.ngrok.org/api/wxPay.html";
				}
			}

		});

	}
	validateBind();
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
				sno=result.data.jobNo;
				$("#text1").html("工号:"+sno);
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
				"nonceStr":nonceStr, //1随机串
				"package":package,//1
				"signType":"MD5",         //微信签名方式：
				"paySign":paySign //1微信签名
			},
			function(res){
				WeixinJSBridge.log(res.err_msg);
				//alert(res.err_code+res.err_desc+res.err_msg);
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
//调用order微信公众号下单 最后一步
	function orderWx(){
		moneyVip = $('.moneyVip').text();
	 var body = '充值';
		$.ajax({
			async: true,
			type: "post",
			url: "/weixin/order",
			dataType:"json",
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
			//headers: {"token": localStorage.getItem('car_token')},
			/*beforeSend: function(xhr) {
				xhr.setRequestHeader("token",cx_names);
			},*/
			data:{
				"openid":openId,
				"totalFee":money,
				"body":body
			},
			success: function (data) {
				data_id =data.data.appId;
				console.log(data.data);
				sj = data.data.nonceStr; //随机数
				sj_id = data.data.packageStr; //1
				wx_name = data.data.paySign;   //1微信签名
				gltime = data.data.timeStamp;   //格林时间
				callpay();
			},
			error:function(err){
				return ;
			}
		});
	}


});
