
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
}