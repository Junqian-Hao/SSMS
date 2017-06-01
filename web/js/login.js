$(function(){
	$("#statuscode").on("click",function(){
		$("#statuscode").attr("src","../ssms/getCaptchaImage.action");
	});
	$("#verify").on("keyup",function(){
		var data3 = $("#verify").val();
		if (data3.length >= 4) {
			var json2 = {
				"code": data3
			}
			$.ajax({
				url: "checkCaptcha.action",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json2),
				success: function (data) {

				},
				error: function (xhr, text) {
					console.log(text);
				}
			});
		}
	});
    $(".btn").on("click",function(){
        var data1 = $("#num").val();
        var data2 = $("#pwd").val();
        if(data1.length == 10 || data1.length == 4){
			var json1 = {
				"userid":data1,
				"userpassword":data2
			};
			$.ajax({
				url: '/ssms/login.action',
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json1),
				success: function(data){
					if(data.code == 0){
						window.location = "index.html";
					}else{
						alert("密码错误");
					}
				},
				error: function(xhr,text){
					console.log(text);
				}
			}) ;
		}
    });
})
