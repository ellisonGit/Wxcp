	var userId = getOpenIdFromCookie();
alert(userId);
	validateBind();

	/*//验证是否已经绑定过
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
				if(result.code=="000002"){
					$.toptip('已绑定，即将跳转到详情页', 'success');
					setTimeout(function(){
						window.location.href="info_1.html";
					},700);
				}
			}
		});
	}*/


	//绑定
	function banding(){
		var empcode = $("#gonghao").val();
		if(empcode == null || empcode==""){
			$.toptip('请输入您的工号', 'warning');
			document.getElementById("gonghao").focus();
			return;
		}

		var empname = $("#xingming").val();
		if(empname == null || empname == ""){
			$.toptip('请输入您的姓名', 'warning');
			document.getElementById("xingming").focus();
			return;
		}
		var url = "/api/forward/banding";
		$.ajax({
			url: url,
			data:{
				openid:userId,
				stuno:empcode,
				stuname:empname
			},
			type:'GET',
			success:function(data){
				var result = JSON.parse(data.data);
				if(result.code=="000002"){
					$.toptip('绑定成功，即将跳转到详情页', 'success');
					setTimeout(function(){
						window.location.href="info_1.html";
					},700);
				}else{
					$.toptip(result.desc, 'warning');
					return;
				}
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
				if(result.code=="100001"){
					window.location.href="http://llison.viphk.ngrok.org/api/wxCp/authOutUser";
				}
			}
		});
	}

