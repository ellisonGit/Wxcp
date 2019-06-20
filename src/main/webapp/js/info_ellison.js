var yu='';
var e='';
$(function() {
	FastClick.attach(document.body);
});

//获取openId
var userId = getOpenIdFromCookie();
var xiaofeiType = 0;
//初始化方法，判断是否成功获取openId
/*init();*/
//判断是否已经绑定
validateBind();
//loadUserInfo();
var userInfoArr = ["",""] ;
var name="";
var sno="";
//判断是否页面有参数值，用于决定显示哪一个tab
var tabValue = getQueryString("t");
if(tabValue == null){
	tabValue = 1;
}

//页码
var pageSize = 10;
//当前月份 yyyyMM
var month = getNowMonth();



var monthForButton = month.substr(0,4)+" "+month.substr(4,2);
$("#rili").val(monthForButton);
$("#month_value").val(monthForButton);

$("#rili").html(monthForButton.replace(" ","-"));
$("#month_value").html(monthForButton.replace(" ","-"));

//调用切换tab页方法
qiehuan(tabValue);
loadChuruRecord(0,pageSize,month);
loadXiaofeiRecord();
//loadYuE();
loadSum();

//切换tab页
function qiehuan(index){
	for(var i=1;i<=2;i++){
		$("#tiaojian"+i).removeClass("on");
	}
	$("#tiaojian"+index).addClass("on");

	for(var i=1;i<=2;i++){
		$("#tab"+i).addClass("hide");
	}
	$("#tab"+index).removeClass("hide");

	//加载对应的tab数据

	if(index == 1){
		loadChuruRecord(1);
	}else if(index == 2){
		loadXiaofeiRecord(0);
	}


	//设置滚动层的高度
	getHeight(index);
}

//tab2的中记录数
var totalCount2 = 0;
//tab2的下一页起始位置
var lastRecordNum2 = 0;
//记录tab2的滚动状态
var tab2ScrollStatus = 0;

//data_list2的滚动事件
$("#data_list2").scroll(function(){
	var nScrollHight = $(this)[0].scrollHeight;
	var nScrollTop = $(this)[0].scrollTop;
	var nDivHight = $(this).height();

	if(nScrollTop + nDivHight >= nScrollHight){
		if(tab2ScrollStatus == 1){
			return ;
		}
		tab2ScrollStatus = 1;
		$("#load_more2").removeClass("hide");
		if(totalCount2 <= lastRecordNum2){
			$("#load_more2").addClass("hide");
			$("#no_more2").removeClass("hide");
			return;
		}

		setTimeout(function(){
			loadXiaofeiRecord(lastRecordNum2,pageSize,month);
			tab2ScrollStatus = 0;
		},500);
	}
});

//加载消费数据
function loadXiaofeiRecord(type,paramMonth){
	if(type==1){
		if(paramMonth == null || paramMonth == ""){
			paramMonth = getTime();
			//alert(paramMonth);
		}
		$('#data_list2_inner').html("");
		var url = "/api/forward/getXList";

		$.ajax({
			async: true,
			url: url,
			data:{
				openId:userId,
				type : type,
				month:paramMonth
			},
			type:'GET',
			beforeSend: function(){
				($('#load_more2').show());
			},

			success:function(data){
				$('#data_list2_inner').text('');
				$('#load_more2').hide();
				var result = JSON.parse(data.data);
				if(result.code=="000001"){
					var length = result.data.length;
					for(var i=0;i < length;i++){
						var data1 = result.data[i];
						var xiaof="消费";
						$("#data_list2_inner").append("<div class=\"weui-cell\"><div class=\"weui-cell__hd xiaofei_word1\">"
							+"<span>"+xiaof+"</span><div class=\"clear5\"></div><text>"+timestampToTime(data1.createTime)+"</text>"
							+"&nbsp;&nbsp;&nbsp;<text>"+timestampToTime2(data1.createTime)+"</text></div><div class=\"weui-cell__bd xiaofei_word2\"><span>"+toDecimal2(data1.money)+"</span>元</div>"
							+"</div>");
					}
				}
			}
		});
	}

	if(type==2){ //充值
		$('#data_list2_inner').html("");
		var url = "/api/forward/getXList";
		if(paramMonth == null || paramMonth == ""){
			paramMonth = getTime();
		}
		$.ajax({
			url: url,
			data:{
				openId:userId,
				type : type,
				month:paramMonth
			},
			type:'GET',
			beforeSend: function(){
				($('#load_more2').show());
			},
			success:function(data){
				$('#data_list2_inner').text('');
				$('#load_more2').hide();
				var result = JSON.parse(data.data);
				if(result.code=="000001"){
					var length = result.data.length;
					for(var i=0;i < length;i++){
						var data1 = result.data[i];
						var xiaof="充值";
						$("#data_list2_inner").append("<div class=\"weui-cell\"><div class=\"weui-cell__hd xiaofei_word1\">"
							+"<span>"+xiaof+"</span><div class=\"clear5\"></div><text>"+timestampToTime(data1.opDate)+"</text>"
							+"&nbsp;&nbsp;&nbsp;<text>"+timestampToTime2(data1.opDate)+"</text></div><div class=\"weui-cell__bd xiaofei_word2\"><span>"+toDecimal2(data1.chargeMoney)+"</span>元</div>"
							+"</div>");
					}
				}
			}
		});
	}

	if(type==0){ //加载全部
		$('#data_list2_inner').html("");
		var url = "/api/forward/getXList";
		if(paramMonth == null || paramMonth == ""){
			paramMonth = getTime();
		}
		$.ajax({
			url: url,
			data:{
				openId:userId,
				type : type,
				month:paramMonth
			},
			type:'GET',
			beforeSend: function(){
				($('#load_more2').show());
			},

			success:function(data){
				$("#load_more2").addClass("hide");
				var result = JSON.parse(data.data);
				var xiaof="消费";
				var chongz="充值";
				if(result.code=="000001"){
					var length = result.data.length;
					for(var i=0;i < length;i++) {
						var data1 = result.data[i];
						var le = data1.length;
						for (var j = 0; j < le; j++) {
							var data2 = data1[j];
							if(data2.money!=null){
								$("#data_list2_inner").append("<div class=\"weui-cell\"><div class=\"weui-cell__hd xiaofei_word1\">"
									+"<span>"+xiaof+"</span><div class=\"clear5\"></div><text>"+timestampToTime(data2.createTime)+"</text>"
									+"&nbsp;&nbsp;&nbsp;<text>"+timestampToTime2(data2.createTime)+"</text></div><div class=\"weui-cell__bd xiaofei_word2\"><span>"+toDecimal2(data2.money)+"</span>元</div>"
									+"</div>");
							}else {
								$("#data_list2_inner").append("<div class=\"weui-cell\"><div class=\"weui-cell__hd xiaofei_word1\">"
									+"<span>"+chongz+"</span><div class=\"clear5\"></div><text>"+timestampToTime(data2.opDate)+"</text>"
									+"&nbsp;&nbsp;&nbsp;<text>"+timestampToTime2(data2.opDate)+"</text></div><div class=\"weui-cell__bd xiaofei_word2\"><span>"+toDecimal2(data2.chargeMoney)+"</span>元</div>"
									+"</div>");
							}
						}
					}
				}
			}

		});

	}
}

//tab1的中记录数
var totalCount1 = 0;
//tab1的下一页起始位置
var lastRecordNum1 = 0;
//记录tab1的滚动状态
var tab1ScrollStatus = 0;

//data_list1的滚动事件
$("#data_list1").scroll(function(){
	var nScrollHight = $(this)[0].scrollHeight;
	var nScrollTop = $(this)[0].scrollTop;
	var nDivHight = $(this).height();

	if(nScrollTop + nDivHight >= nScrollHight){
		if(tab1ScrollStatus == 1){
			return ;
		}
		tab1ScrollStatus = 1;
		$("#load_more1").removeClass("hide");
		if(totalCount1 <= lastRecordNum1){
			$("#load_more1").addClass("hide");
			$("#no_more1").removeClass("hide");
			return;
		}

		setTimeout(function(){
			loadChuruRecord(lastRecordNum1,pageSize,month);
			tab1ScrollStatus = 0;
		},500);
	}
});

//加载出入数据
function loadChuruRecord(start,pageSize,month){
	var url = "/api/forward/queryDoorByPage";
	if(month == null || month == ""){
		month = "now";
	}
	$.ajax({
		url: url,
		data:{
			openId:userId,
			start : start,
			pageSize : pageSize,
			month : month
		},
		type:'GET',
		success:function(data){
			var result = JSON.parse(data.data);
			if(result.rcode=="1"){

				totalCount1 = result.totalCount;
				lastRecordNum1 = result.lastRecordNum;

				if(start == 0){
					$("#data_list1_inner").empty();
					$("#load_more1").removeClass("hide");
					$("#no_more1").addClass("hide");
					tab1ScrollStatus = 0;
				}

				var length = result.data.length;
				if(length == 0){
					$("#no_more1 .weui-loadmore__tips").html("没有了");
					$("#load_more1").addClass("hide");
					$("#no_more1").removeClass("hide");
					return ;
				}

				if(result.totalCount <= result.lastRecordNum){
					$("#load_more1").addClass("hide");
					$("#no_more1").removeClass("hide");
				}

				for(var i=0;i < length;i++){
					var data1 = result.data[i];
					var jmTime = data1.JYTIME.replace(/-/g,"");
					var imgUrl = data1.PHOTO;
					if(imgUrl == null || imgUrl == "" || imgUrl == "undefined"){
						$("#data_list1_inner").append("<div class=\"weui-cell\"><div class=\"weui-cell__hd info1\">"
							+"<img class=\"cell_img\" src=\"img/morentupian.jpg\" /></div><div class=\"weui-cell__bd info2\">"
							+"<p>"+youhuaDateStr(jmTime.substr(0,8))+"</p></div><div class=\"weui-cell__bd info3\"><p>"+jmTime.substr(8)+"</p></div>"
							+"<div class=\"weui-cell__ft info4\">"+data1.ACTION+"</div></div>");
					}else{
						$("#data_list1_inner").append("<div class=\"weui-cell\"><div class=\"weui-cell__hd info1\">"
							+"<img class=\"cell_img\" src=\""+imgUrl+"\" /></div><div class=\"weui-cell__bd info2\">"
							+"<p>"+youhuaDateStr(jmTime.substr(0,8))+"</p></div><div class=\"weui-cell__bd info3\"><p>"+jmTime.substr(8)+"</p></div>"
							+"<div class=\"weui-cell__ft info4\">"+data1.ACTION+"</div></div>");

						$('img').error(function(){
							$(this).attr('src',"img/morentupian.jpg");
						})

						$(".cell_img").load(function(){
							$(".cell_img").click(function(){
								if(this.src.indexOf("morentupian.jpg") > 0){
									return ;
								}
								showBigImg(this);
							});
						});
					}
				}
			}
		}
	});
}

//加载支出，充值数据
function loadSum(paramMonth) {
	var url = "/api/forward/getSum";
	if(paramMonth == null || paramMonth == ""){
		paramMonth = getTime();
	}
	$.ajax({
		async: true,
		url: url,
		data: {
			openId: userId,
			month:paramMonth
		},
		type: 'GET',
		success: function (data) {
			var result = JSON.parse(data.data);
			if (result.code == "000001") {
				//alert(result.data.money);
				$("zhichu").html(toDecimal2(result.data.money)== null ? 0 : toDecimal2(result.data.money));
				$("chongzhi").html(toDecimal2(result.data.remainMoney) == null ? 0 : toDecimal2(result.data.remainMoney));
				yu=	$(".my_yue span").html(toDecimal2(result.data.remainMoney - result.data.money));//余额
				e=toDecimal2(result.data.remainMoney - result.data.money);
				numRunFun(0,e);//动态金额
			}
		}
	});
}

function numRunFun(num, maxNum){
	var numBox = document.getElementById("numBox");
	var num = num;
	var maxNum = maxNum;
	var timer = setInterval(function(){
		num++; // 调节速度
		if(num >= maxNum){
			numBox.innerHTML = maxNum;
			clearInterval(timer);
		} else {
			numBox.innerHTML = ~~(num);
		}
	},1); // 也可以调节速度
}

//验证是否已经绑定过
function validateBind(){
	var url = "/api/forward/validateBanding";
	$.ajax({
		url: url,
		data:{
			openId:userId
		},
		type:'GET',
		success:function(data){
			var result = JSON.parse(data.data);
			if(result.code=="100001"){
				window.location.href="http://llison.viphk.ngrok.org/api/wxCp/authOutUser?returnUrl=http://llison.viphk.ngrok.org/api/banding.html";
			}
		}
	});
}

//设置对应div的高度
function getHeight(index){
	var height = $("#data_list"+index).offset().top;
	var allHeight = $(window).height();
	var listHeight = allHeight - height;
	$("#data_list"+index).height(listHeight);
}

//初始化日历选择器
$("#rili").picker({
	title: "选择月份",
	cols: [
		{
			textAlign: 'center',
			values: ['2018', '2019']
		},
		{
			textAlign: 'center',
			values: ['01', '02', '03', '04', '05', '06','07','08','09','10','11','12']
		}
	],
	onClose: function(p) {
		var result = p.value[0]+","+p.value[1];
		var newMonth = result.replace(",","-");

		$("#rili").html(newMonth);
		var paramMonth = result.replace(",","");

		loadChuruRecord(0,pageSize);
		tab1ScrollStatus = 0;
	}
});

//初始化日历选择器
$("#month_value").picker({
	title: "选择月份",
	cols: [
		{
			textAlign: 'center',
			values: ['2018', '2019']
		},
		{
			textAlign: 'center',
			values: ['01', '02', '03', '04', '05', '06','07','08','09','10','11','12']
		}
	],
	onClose: function(p) {
		var result = p.value[0]+","+p.value[1];
		var newMonth = result.replace(",","-");

		$("#month_value").html(newMonth);
		var paramMonth = result.replace(",","-");
		loadXiaofeiRecord(1,paramMonth);
		loadSum(paramMonth);
		tab2ScrollStatus = 0;
	}
});

//查看大图
function showBigImg(e){
	var imgSrc = $(e).attr("src");
	//$.modal({
	//  title: " ",
	//  text: "<img class='img_detail' src='"+imgSrc+"'/>"
	//});

	$(".big_img img").attr("src",imgSrc);
	$(".tanchukuang").show();
}

//弹出框的形式显示用户详细信息
function showUserInfo(){
	loadUserInfo();

}

//查询用户详细信息
function loadUserInfo(){
	var url = "/api/forward/queryCardInfo";
	$.ajax({
		url: url,
		data:{
			openId:userId
		},
		type:'GET',
		success:function(data){
			var result = JSON.parse(data.data);
			name=result.data.sname;
			sno=result.data.jobNo;
			if(name!=null){
				$.modal({
					title: "用户基础信息",
					text: "<div class=\"user_info_css\">姓名："+name+"<br/>工号："+sno+"<br/></div>"
				});
			}
		}
	});
}

//选择消费分类
function changeXiaofeiType(type){
	xiaofeiType = type;
	var paramMonth = $("#month_value").val();
	paramMonth = paramMonth.replace(" ","-");

	$("#xialalan").hide();
	loadXiaofeiRecord(type,paramMonth);
}