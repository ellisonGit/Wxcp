
var icardUrl = "http://llison.viphk.ngrok.org/api";
//获取cookie
function getCookie(name){
	var strcookie = document.cookie;//获取cookie字符串
	var arrcookie = strcookie.split("; ");//分割
	//遍历匹配
	for ( var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0] == name){
			return arr[1];
		}
	}
	return "";
}

//从cookie里面解析openId
function getOpenIdFromCookie(){
	//测试值
	//return "123" 
	var cookieStr = decodeURIComponent(getCookie("wx_user_info"));
	if(cookieStr != null && cookieStr != "undefined" && cookieStr !=""){
		cookieStr = JSON.parse(cookieStr);
		return cookieStr.openid;
	}else{
		return "";
	}	
}

//初始化方法
function init(){
	if(openId== null || openId == ""){
		window.location.href=icardUrl+"/hnjca/auth?returnUrl="+icardUrl+"/banding.html";
	}
}

//获取url后面参数的值
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象 
	var r = window.location.search.substr(1).match(reg); //匹配目标参数 
	if (r != null) return decodeURIComponent(r[2]); 
	return null; //返回参数值 
} 

//优化日期显示
function youhuaDateStr(dateStr){
	var myDate = new Date();
	var year = myDate.getFullYear()+"";
	var month = myDate.getMonth()+1+"";
	if(month < 10){
		month = "0"+month;
	}
	var day = myDate.getDate()+"";
	if(day < 10){
		day = "0"+day;
	}
	
	var todayStr = year + month+day;
	
	if(todayStr == dateStr){
		return "今天";
	}else{
		return dateStr.substr(4,2)+"-"+dateStr.substr(6,2);
	}	
}

//将时间戳转日期格式
function timestampToTime(timestamp) {
	var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	var Y = date.getFullYear() + '-';
	var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	var D = date.getDate() < 10 ?  '0'+date.getDate()+ ' ' : date.getDate()+ ' ';
	var h = date.getHours() < 10 ? '0'+date.getHours()+ ':' : date.getHours()+ ':';
	var m = date.getMinutes() < 10 ? '0'+date.getMinutes()+ ':' : date.getMinutes()+ ':';
	var s = date.getSeconds()< 10 ? '0'+date.getSeconds() : date.getSeconds();
	return M+D;
}
//将时间戳转日期格式
function timestampToTime2(timestamp) {
	var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	var Y = date.getFullYear() + '-';
	var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	var D = date.getDate() < 10 ?  '0'+date.getDate()+ ' ' : date.getDate()+ ' ';
	var h = date.getHours() < 10 ? '0'+date.getHours()+ ':' : date.getHours()+ ':';
	var m = date.getMinutes() < 10 ? '0'+date.getMinutes()+ ':' : date.getMinutes()+ ':';
	var s = date.getSeconds()< 10 ? '0'+date.getSeconds() : date.getSeconds();
	return h+m+s;
}

//优化时间显示
function youhuaTimeStr(timeStr){
	return timeStr.substr(0,2)+":"+timeStr.substr(2,2)+":"+timeStr.substr(4,2);
}

//获取当天的月份格式 yyyyMM
function getNowMonth(){		
	var myDate = new Date();
	var year = myDate.getFullYear()+"";
	var month = myDate.getMonth()+1+"";
	if(month < 10){
		month = "0"+month;
	}

	return year+month;
}

//获取当天的月份格式 yyyyMM
function getTime(){
	var myDate = new Date();
	var year = myDate.getFullYear()+"";
	var month = myDate.getMonth()+1+"";
	if(month < 10){
		month = "0"+month;
	}

	return year+"-"+month;
}

//将金额保留两位小数
function toDecimal2(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return false;
	}
	var f = Math.round(x*100)/100;
	var s = f.toString();
	var rs = s.indexOf('.');
	if (rs < 0) {
		rs = s.length;
		s += '.';
	}
	while (s.length <= rs + 2) {
		s += '0';
	}
	return s;
}

//优化金额显示
function youhuaMoney(moneyStr){
	var idx = moneyStr.indexOf(".");
	if(idx == -1){
		return moneyStr+".00";
	} 
	var str = moneyStr.substr(idx);
	if(str.length == 2){
		return moneyStr+"0";
	}
	if(str.length == 3){
		return moneyStr;
	}
	if(str.length == 4){
		return  (moneyStr * 1000)/1000;
	}
	if(str.length == 5){
		return moneyStr-00;
	}
}