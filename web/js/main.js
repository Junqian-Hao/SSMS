$(function(){
	$("#announcement").css("display","none");
	var type = "";
	var userid = "";
	var subjectid = "";
	var _data = [];
	$.ajax({
		url: "/ssms/selectNowUser.action",
		type: "POST",
		dataType: "json",
		contentType: "application/json",
		success: function(data){
			$("body").attr("data-type",data.type);
			$("body").attr("data-id",data.userid);
			if(data.type == 2){
				$("#announcement").css("display","none");
				$("#admin_query").css("display","none");
				$("#admin_manage").css("display","none");
				$("#grade_analysis").css("display","none");
			}else if(data.type == 1){
				$("#announcement").css("display","none");
				$("#modify_grade").css("display","none");
				$("#my_grade").css("display","none");
			}else if(data.type == 0){
				$("#my_grade").css("display","none");
				$(".header_fun").css("margin_left","50%");
			}
		},
		error: function(xhr,text){
			console.log(text);
		}
	});
	$.ajax({
		url: "/ssms/Student/selectAllNotice.action",
		type: "POST",
		dataType: "json",
		contentType: "application/json",
		success: function(data){
			var len = data.length;
			$(".notice").text(data[len-1].message);
		}
	});
	setTimeout(function(){
		type = $("body").attr("data-type");
		userid = $("body").attr("data-id");
	},500);
	//查看我的个人信息
	$("#my_infor").on("click",function(){
		$(".item_").css("display","none");
		$(".left_side_infor").css("display","block");
		$.ajax({
			url: "/ssms/selectNowUser.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			success: function(data){
				if(type == 2){
					var newTime = new Date(data.birthday);
					var year = newTime.getFullYear();
					var month = newTime.getMonth()+1;
					$("#stu_name").attr("value",data.name);
					$("#stu_college").attr("value",data.college);
					$("#stu_class").attr("value",data.classname);
					$("#stu_address").attr("value",data.nativeplace);
					$("#stu_tel").attr("value",data.phonenumber);
					$("#stu_birth").attr("value",year+"-"+month);
					$("#stu_sex:first-child").html(data.sex);
				}else{
					$("#admin_name").attr("value",data.name);
					$("#admin_sex").attr("value",data.sex);
					$("#admin_tel").attr("value",data.phonenumber);
				}
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});
	$("#watch_infor").on("click",function(){
		if(type == 2) {
			$(".item_").css("display","none");
			$(".left_side_infor").css("display","block");
			$(".stu_infor").css("display","block");
		}else{
			$(".item_").css("display","none");
			$(".left_side_infor").css("display","block");
			$(".admin_infor").css("display","block");
		}
	});
	//修改个人信息
	$("#modify_infor").on("click",function(){
		if(type == 2){
			$(".item_").css("display","none");
			$(".left_side_infor").css("display","block");
			$(".modify_stu_infor").css("display","block");
		}else {
			$(".item_").css("display","none");
			$(".left_side_infor").css("display","block");
			$(".modify_admin_infor").css("display","block");
		}
	});
	$("#modify_admin_btn").on("click",function(){
		var data1 = $("#name").val();
		var data2 = $("#userpassword").val();
		var data3 = $("#sex").val();
		var data4 = $("#phonenum").val();
		var json1 = {
			"name":data1,
			"userpassword":data2,
			"sex":data3,
			"phonenum":data4
		}
		$.ajax({
			url: "/ssms/Administrator/updateAdministrator.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json1),
			success: function(data){
				console.log("提交成功");
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});
	$("#modify_stu_btn").on("click",function(){
		var data1 = $("#modify_stu_name").val();
		var data2 = $("#modify_stu_sex").val();
		var data3 = $("#modify_stu_college").val();
		var data4 = $("#modify_stu_class").val();
		var data5 = $("#modify_stu_address").val();
		var data6 = $("#modify_stu_birth").val();
		var data7 = $("#modify_stu_tel").val();
		var json2 = {
			"userid":userid,
			"name":data1,
			"sex":data2,
			"college":data3,
			"classname":data4,
			"nativeplace":data5,
			"birthday":data6,
			"phonenumber":data7
		}
		$.ajax({
			url: "/ssms/Student/updateStudent.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json2),
			success: function(data){
				console.log("提交成功");
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});
	//录入成绩
	$("#search_btn").on("click",function(){
		if(type != 2){
			var className = $("#search_input").val();
			var json3 = {
				"classname":className
			}
			$.ajax({
				url: "/ssms/Student/selectStudentCustomByClass.action",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json3),
				success: function(data){
					$(".first_tr").nextAll().remove();
					$(".first_tr").remove();
					$("#submit_grade").remove();
					var firsttr = $("<tr class='first_tr'></tr>");
					var tdid = $("<td></td>").html("学号");
					var tdname = $("<td></td>").html("姓名");
					firsttr.append(tdid,tdname);
					$("#stu_grade").append(firsttr);
					var len = data.length;
					var subjectnum = data[0].gradeVos.length;
					for(var i = 0;i<subjectnum;i++){
						console.log(data[0].gradeVos[i].subject);
						var td = $("<td></td>").html(data[0].gradeVos[i].subject);
						$(".first_tr").append(td);
					}
					$("#stu_grade").attr("data-num",subjectnum);
					var td = "";
					for(var j = 1;j<subjectnum+1;j++){
						td = td + "<td><input class='grade_table_input' type='text'></td>";
					}
					var teacher_subjectid = subjectid.charAt(subjectid.length-1);
					if(data[0].gradeVos[teacher_subjectid].grade == 0){
						$(".class_grade").css("visibility","visible");
						$("#form_submit").css("visibility","visible");
						for(var i = 0;i<len;i++){
							var tr = "<tr><td class='stu_grade_userid'>"+data[i].userid+"</td><td>"+data[i].name+"</td>"+td;
							var h = $.parseHTML(tr);
							$("#stu_grade").append(h);
						}
						var btncontent = "<button id='submit_grade'>提交</button>";
						var b = $.parseHTML(btncontent);
						$(".teacher_record_grade").append(b);
						for(var k = 0;k<subjectnum;k++){
							var subject = "subject"+k;
							var html1 = $(".grade_table_input")[k];
							var a = $.parseHTML(html1);
						}
						$("#submit_grade").on("click",function () {
							var len = $(".grade_table_input").length;
							var stunum = $(".stu_grade_userid").length;
							var data = [];
							var teacher_subjectid = subjectid.charAt(subjectid.length-1);
							var x = parseInt(teacher_subjectid);
							var num = parseInt($("#stu_grade").attr("data-num"));
							var html2 = "";
							var stuid = "";
							var grade = "";
							var id = "";
							for(var i = 0;i<stunum;i++){
								html2 = $(".grade_table_input")[x-1];
								stuid = $(".stu_grade_userid")[i];
								x = x+num;
								grade = $(html2).val();
								id = $(stuid).html();
								data[i] = {
									"subjectid":teacher_subjectid,
									"grade": grade,
									"userid": id
								}
							}
							$.ajax({
								url: "/ssms/Teacher/inputGrade.action",
								type: "POST",
								dataType: "json",
								contentType: "application/json",
								data: JSON.stringify(data),
								success: function(){
									console.log("成功");
								}
							});
						});
					}
				},
				error: function(shr,text){
					console.log(text);
				}
			});
		}
	})

	$("#admin_manage").on("click",function(){
		$(".item_").css("display","none");
		$(".left_side_manage").css("display","block");
	});

	$("#record_grade").on("click",function(){
		$(".item_").css("display","none");
		$(".left_side_manage").css("display","block");
		$(".teacher_record_grade").css("display","block");
		var json5 = {
			"userid":userid
		}
		if(type != 0){
			$.ajax({
				url:"/ssms/Student/selectSubjectByTeacher.action",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json5),
				success: function(data){
					$(".class_grade").attr("data-subjectid","teacher"+data[0].subjectid);
				}
			});
			setTimeout(function(){
				subjectid = $(".class_grade").attr("data-subjectid");
			},500)
		}
	});

	$("#admin_query").on("click",function(){
		$(".item_").css("display","none");
		$(".left_side_query").css("display","block");
	});

	$("#infor_query").on("click",function(){
		if(type != 2){
			$(".item_").css("display","none");
			$(".left_side_query").css("display","block");
			$(".infor_query").css("display","block");
		}
	});
	//老师管理员查询信息修改信息
	$("#btn_query_infor").on("click",function(){
		if(type != 2){
			var id = $("#query_content").val();
			var json = {
				"userid":id
			}
			$.ajax({
				url: "/ssms/Student/selectSupperVoById.action",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json),
				success: function(data){
					if(data.user){
						var infor1 = data.user.name;
						var infor2 = data.user.sex;
						var infor3 = data.user.phonenumber;
						var infor4 = data.subjects[0].subjectname;
						var name = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*姓&nbsp;&nbsp;名");
						var sex = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*性&nbsp;&nbsp;别");
						var phonenumber = $("<label class='two_label'></label>").html("*联系方式");
						var subject = $("<label class='two_label'></label>").html("*所交课程");
						var input_name = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor1);
						var input_sex = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor2);
						var input_phonenumber = $("<input type='text' class='two_label_input' disabled/>").val(infor3);
						var input_subject = $("<input type='text' class='two_label_input' disabled/>").val(infor4);
						var div1 = $("<div></div>").html(name);
						$(div1).append(input_name);
						var div2 = $("<div></div>").html(sex);
						$(div2).append(input_sex);
						var div3 = $("<div></div>").html(subject);
						$(div3).append(input_subject);
						var div4 = $("<div></div>").html(phonenumber);
						$(div4).append(input_phonenumber);
						$(".infor_query").append(div1,div2,div3,div4);
					}else{
						var newTime = new Date(data.studentCustom.birthday);
						var year = newTime.getFullYear();
						var month = newTime.getMonth()+1;
						var infor1 = data.studentCustom.name;
						var infor2 = data.studentCustom.sex;
						var infor3 = data.studentCustom.phonenumber;
						var infor4 = data.studentCustom.classname;
						var infor5 = data.studentCustom.college;
						var infor6 = year+"-"+month;
						var infor7 = data.studentCustom.nativeplace;
						var name = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*姓&nbsp;&nbsp;名");
						var sex = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*性&nbsp;&nbsp;别");
						var phonenumber = $("<label class='two_label'></label>").html("*联系方式");
						var classname = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*班&nbsp;&nbsp;级");
						var college = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*学&nbsp;&nbsp;院");
						var birthday = $("<label class='two_label'></label>").html("*出生日期");
						var nativeplace = $("<label class='two_label'></label>").html("&nbsp;&nbsp;*籍&nbsp;&nbsp;贯");
						var input_name = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor1);
						var input_sex = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor2);
						var input_classname = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor4);
						var input_college = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor5);
						var input_nativeplace = $("<input type='text' class='two_label_input t_input' disabled/>").val(infor7);
						var input_birthday = $("<input type='text' class='two_label_input' disabled/>").val(infor6);
						var input_phonenumber = $("<input type='text' class='two_label_input' disabled/>").val(infor3);
						var div1 = $("<div></div>").html(name);
						$(div1).append(input_name);
						var div2 = $("<div></div>").html(sex);
						$(div2).append(input_sex);
						var div3 = $("<div></div>").html(classname);
						$(div3).append(input_classname);
						var div4 = $("<div></div>").html(college);
						$(div4).append(input_college);
						var div5 = $("<div></div>").html(nativeplace);
						$(div5).append(input_nativeplace);
						var div6 = $("<div></div>").html(birthday);
						$(div6).append(input_birthday);
						var div7 = $("<div></div>").html(phonenumber);
						$(div7).append(input_phonenumber);
						$(".infor_query").append(div1,div2,div3,div4,div5,div6,div7);
						if(type == 0){
							$(".infor_query input").removeAttr("disabled");
							var btn = $("<button id='submit_infor'></button>").text("提交");
							$(".infor_query").append(btn);
							$("#submit_infor").on("click",function(){
								//老师
								if(id.length == 4){
									var html1 = $(".infor_query input")[0];
									var name = $(html1).val();
									var html2 = $(".infor_query input")[1];
									var sex = $(html2).val();
									var html3 = $(".infor_query input")[3];
									var phonenumber = $(html3).val();
									var json = {
										"userid": id,
										"name": name,
										"sex": sex,
										"phonenumber": phonenumber
									}
									$.ajax({
										url: "/ssms/Teacher/updateTeacher.action",
										type: "POST",
										dataType: "json",
										contentType: "application/json",
										data: JSON.stringify(json)
									});
								}else if(id.length == 10){
									var html1 = $(".infor_query input")[1];
									var html2 = $(".infor_query input")[2];
									var html3 = $(".infor_query input")[3];
									var html4 = $(".infor_query input")[4];
									var html5 = $(".infor_query input")[5];
									var html6 = $(".infor_query input")[6];
									var html7 = $(".infor_query input")[7];
									var name = $(html1).val();
									var sex = $(html2).val();
									var classname = $(html3).val();
									var college = $(html4).val();
									var nativeplace = $(html5).val();
									var birthday = $(html6).val();
									var phonenumber = $(html7).val();

									var json = {
										"name": name,
										"sex" : sex,
										"phonenumber": phonenumber,
										"calssname": classname,
										"college": college,
										"birthday": birthday,
										"nativeplace": nativeplace
									}
									console.log(json);
									$.ajax({
										url: "/ssms/Student/updateStudent.action",
										type: "POST",
										dataType: "json",
										contentType: "application/json",
										data: JSON.stringify(json)
									});

								}
							});
						}
					}
				},
				error: function(xhr,text){
					console.log(text);
				}
			});
		}
	});


	//公告管理
	$("#announcement").on("click",function(){
		$(".item_").css("display","none");
		$(".admin_announcement").css("display","block");
	});
	$("#release_announcement").on("click", function () {
		$(".item_").css("display","none");
		$(".admin_announcement").css("display","block");
		$(".announcement").css("display","block");
	});

	$("#submit_announcement").on("click", function () {
		var announcement = $("#announcement_content").val();
		var json = {
			"message":announcement
		}
		$.ajax({
			url: "/ssms/Administrator/insertNotice.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json),
			success: function(data){
				$(".notice").text(announcement);
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	})

	//成绩查询
	$("#query_grade").on("click",function(){
		$(".item_").css("display","none");
		$(".left_side_manage").css("display","block");
		$(".grade_query").css("display","block");
	});
	$("#submit_id").on("click", function () {
		$(".class_grade2").empty();
		var value = $("#class_stu_id").val();
		var choose = $(".choose").val();
		if(choose == "班级"){
			var json = {
				"classname":value
			}
			$.ajax({
				url: "/ssms/Student/selectStudentCustomByClass.action",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json),
				success: function(data){
					$(".newtr").nextAll().remove();
					$(".newtr").remove();
					var newtr = $("<tr class='newtr'></tr>");
					var tdid = $("<td></td>").html("学号");
					var tdname = $("<td></td>").html("姓名");
					$(newtr).append(tdid,tdname);
					$(".class_grade2").append(newtr);
					var len = data.length;
					var subjectnum = data[0].gradeVos.length;
					for(var i = 0;i<subjectnum;i++){
						var td = $("<td></td>").html(data[0].gradeVos[i].subject);
						$(".newtr").append(td);
					}
					for(var i = 0;i<len;i++){
						var td1 = $("<td></td>").html(data[i].name);
						var tr = $("<tr></tr>").html(td1);
						var td2 = $("<td></td>").html(data[i].userid);
						$(tr).append(td1,td2);
						for(var j = 0;j<subjectnum;j++){
							var td3 = $("<td></td>").html(data[i].gradeVos[j].grade);
							$(tr).append(td3);
						}
						$(".class_grade2").append(tr);
					}
					$(".class_grade2").css("visibility","visible");
				}
			});
		}else{
			var value = $("#class_stu_id").val();
			var choose = $(".choose").val();
			var json = {
				"userid":value
			};
			$.ajax({
				url: "/ssms/Student/selectSupperVoById.action",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(json),
				success: function (data) {
					var newtr = $("<tr class='newtr'></tr>");
					var second = $("<tr></tr>");
					var tdid = $("<td></td>").html("学号");
					var tdname = $("<td></td>").html("姓名");
					var stuid = $("<td></td>").html(data.studentCustom.userid);
					var stuname = $("<td></td>").html(data.studentCustom.name);
					$(newtr).append(tdid,tdname);
					$(second).append(stuid,stuname);
					$(".class_grade2").append(newtr);
					var subjectnum = data.studentCustom.gradeVos.length;
					for(var i = 0;i<subjectnum;i++){
						var td = $("<td></td>").html(data.studentCustom.gradeVos[i].subject);
						var grade = $("<td></td>").html(data.studentCustom.gradeVos[i].grade);
						$(".newtr").append(td);
						$(second).append(grade);
					}
					$(".class_grade2").append(newtr,second);
					$(".class_grade2").css("visibility","visible");
				},
				error: function(xhr,text){
					console.log(text)
				}
			});
		}
	});

	//修改成绩
	$("#modify_grade").on("click",function(){
		if(type == 0){
			$(".item_").css("display","none");
			$(".left_side_manage").css("display","block");
			$(".modify_grade").css("display","block");


		}
	});
	$("#search_modify").on("click", function () {
		var id = $("#input_modify_grade").val();
		var json = {
			"userid":id
		}
		$.ajax({
			url: "/ssms/Student/selectSupperVoById.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json),
			success: function(data){
				$(".modify_first_tr").nextAll().remove();
				$(".modify_first_tr").remove();
				$("#modify_grade_btn").remove();
				var firsttr = $("<tr class='modify_first_tr'></tr>");
				var secondtr = $("<tr class='modify_second_tr'></tr>");
				var subjectnum = data.studentCustom.gradeVos.length;
				var tdid = $("<td></td>").html("学号");
				var tdidnum = $("<td></td>").html(data.studentCustom.userid);
				var tdname = $("<td></td>").html("姓名");
				var tdnamenum = $("<td></td>").html(data.studentCustom.name);
				$(firsttr).append(tdid,tdname);
				$(secondtr).append(tdidnum,tdnamenum);
				for(var i = 0;i<subjectnum;i++){
					_data[i] = {
						"id": data.studentCustom.gradeVos[i].subjectid
					}
					var td = $("<td></td>").html(data.studentCustom.gradeVos[i].subject);
					var tdgrade = $("<td></td>");
					var input = $("<input class='grade_table_input2' type='text'>").val(data.studentCustom.gradeVos[i].grade);
					$(tdgrade).append(input);
					$(firsttr).append(td);
					$(secondtr).append(tdgrade);
				}
				$(".modify_grade_table").append(firsttr);
				$(".modify_grade_table").append(secondtr);
				var btn = $("<button id='modify_grade_btn'></button>").html("提交");
				$(".modify_grade").append(btn);
				$(".modify_grade_table").css("visibility","visible");
				$("#modify_grade_btn").on("click",function(){
					var data = [];
					console.log(_data[0].id);
					for(var i = 0;i<subjectnum;i++){
						var number = $(".grade_table_input2")[i];
						var grade = $(number).val();
						data[i] = {
							"grade": grade,
							"subjectid": _data[i].id,
							"userid": id
						}
					}
					$.ajax({
						url:"/ssms/Teacher/inputGrade.action",
						type: "POST",
						dataType: "json",
						contentType: "application/json",
						data: JSON.stringify(data),
						success: function(data){

						},
						error: function (xhr,text) {
							console.log(text);
						}
					});

				});
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});

	//我的成绩
	$("#my_grade").on("click",function(){
		$(".item_").css("display","none");
		$(".my_grade").css("display","block");
		console.log(userid);
		var json = {
			"userid": userid
		}
		console.log(json);
		$.ajax({
			url: "/ssms/Student/selectSupperVoById.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json),
			success: function(data){
				console.log(data);
				$(".my_grade").empty();
				var table = $("<table class='my_table'></table>");
				var firsttr = $("<tr></tr>");
				var secondtr = $("<tr></tr>");
				var tdid = $("<td></td>").html("学号");
				var tdname = $("<td></td>").html("姓名");
				var id = $("<td></td>").html(userid);
				var name = $("<td></td>").html(data.studentCustom.name);
				$(firsttr).append(tdid,tdname);
				$(secondtr).append(id,name);
				var subjectnum = data.studentCustom.gradeVos.length;
				for(var i = 0;i<subjectnum;i++){
					var td = $("<td></td>").html(data.studentCustom.gradeVos[i].subject);
					var grade = data.studentCustom.gradeVos[i].grade;
					var secondtd = $("<td></td>").html(grade);
					$(firsttr).append(td);
					$(secondtr).append(secondtd);
				}
				$(table).append(firsttr,secondtr);
				$(".my_grade").append(table);
				$(".my_table").css("visibility","visible");




			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});

	//班级成绩分析
	$("#grade_analysis").on("click",function(){
		$(".item_").css("display","none");
		$(".left_grade_analysis").css("display","block");
	});
	$("#my_class_grade").on("click",function(){
		$(".item_").css("display","none");
		$(".left_grade_analysis").css("display","block");
		$(".classgrade_analysis").css("display","block");
	});
	$("#class_analysis_btn").on("click",function(){
		var classname = $("#class_analysis_input").val();
		var json = {
			"className":classname
		}
		$.ajax({
			url: "/ssms/Student/analyseByClass.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json),
			success: function(data){
				var select = $("<select id='choose_subject'></select>");
				var num = data.length;
				var firstpic = data[0].subject;
				for(var i = 0;i<num;i++){
					var option = $("<option></option>").html(data[i].subject);
					select.append(option);
				}
				$(".select").append(select);
				var myCharts = echarts.init(document.getElementById("class_column"));
				var myCharts2 = echarts.init(document.getElementById("class_pie"));
				var option = {
					title: {
						text: '柱状图'
					},
					tooltip: {},
					legend: {
						data:['比列']
					},
					xAxis: {
						data: ["0~60","60~80","80~90","90~100"]
					},
					yAxis: {},
					series: [{
						name: '比列',
						type: 'bar',
						itemStyle: {
							normal: {
								color: function(params) {
									var colorList = [
										'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
										'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
										'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
									];
									return colorList[params.dataIndex]
								}
							}
						},
						data: [data[0].dispassPerentge,data[0].passPerentge,data[0].goodPerentge,data[0].perfectPerentge]
					}]
				};
				myCharts.setOption(option);

				myCharts2.setOption({
					title: {
						text: '饼状图'
					},
					tooltip: {
						trigger: 'item',
						formatter: "{a} <br/>{b}: {c} ({d}%)"
					},
					series : [
						{
							name: '成绩饼状图',
							type: 'pie',
							radius: '55%',
							data:[
								{value:data[0].dispassAmount, name:'不及格'+data[0].dispassPerentge},
								{value:data[0].passAmount, name:'及格'+data[0].passPerentge},
								{value:data[0].goodAmount, name:'良好'+data[0].goodPerentge},
								{value:data[0].perfectAmount, name:'优秀'+data[0].perfectPerentge}
							]
						},
					],
					itemStyle: {
						normal: {
							// 阴影的大小
							shadowBlur: 200,
							// 阴影水平方向上的偏移
							shadowOffsetX: 0,
							// 阴影垂直方向上的偏移
							shadowOffsetY: 0,
							// 阴影颜色
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					},
					labelLine: {
						normal: {
							lineStyle: {
								color: 'rgba(255, 255, 255, 0.3)'
							}
						}
					}
				})
			$("#choose_subject").on("change", function () {
					var choose = $("#choose_subject").val();
					for(var j = 0;j<num;j++){
						if(choose == data[j].subject){
							var myCharts = echarts.init(document.getElementById("class_column"));
							var myCharts2 = echarts.init(document.getElementById("class_pie"));
							var option = {
								title: {
									text: '柱状图'
								},
								tooltip: {},
								legend: {
									data:['比列']
								},
								xAxis: {
									data: ["0~60","60~80","80~90","90~100"]
								},
								yAxis: {},
								series: [{
									name: '比列',
									type: 'bar',
									itemStyle: {
										normal: {
											color: function(params) {
												var colorList = [
													'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
													'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
													'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
												];
												return colorList[params.dataIndex]
											}
										}
									},
									data: [data[j].dispassPerentge,data[j].passPerentge,data[j].goodPerentge,data[j].perfectPerentge]
								}]
							};
							myCharts.setOption(option);

							myCharts2.setOption({
								title: {
									text: '饼状图'
								},
								tooltip: {
									trigger: 'item',
									formatter: "{a} <br/>{b}: {c} ({d}%)"
								},
								series : [
									{
										name: '成绩饼状图',
										type: 'pie',
										radius: '55%',
										data:[
											{value:data[j].dispassAmount, name:'不及格'+data[j].dispassPerentge},
											{value:data[j].passAmount, name:'及格'+data[j].passPerentge},
											{value:data[j].goodAmount, name:'良好'+data[j].goodPerentge},
											{value:data[j].perfectAmount, name:'优秀'+data[j].perfectPerentge}
										]
									},
								],
								itemStyle: {
									normal: {
										// 阴影的大小
										shadowBlur: 200,
										// 阴影水平方向上的偏移
										shadowOffsetX: 0,
										// 阴影垂直方向上的偏移
										shadowOffsetY: 0,
										// 阴影颜色
										shadowColor: 'rgba(0, 0, 0, 0.5)'
									}
								},
								labelLine: {
									normal: {
										lineStyle: {
											color: 'rgba(255, 255, 255, 0.3)'
										}
									}
								}
							})



						}
					}


				})
			},
			error: function (xhr,text) {
				console.log(text);
			}
		});
	});

	//学院成绩
	$("#my_college_grade").on("click", function () {
		$(".item_").css("display","none");
		$(".left_grade_analysis").css("display","block");
		$(".collegegrade_analysis").css("display","block");
	});
	$("#college_analysis_btn").on("click", function () {
		var college = $("#college_analysis_input").val();
		console.log(college);
		var json = {
			"college":college
		}
		$.ajax({
			url: "/ssms/Student/analyseByCollage.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(json),
			success: function(data){
				console.log(data);
				var select = $("<select id='choose_subject'></select>");
				var num = data.length;
				var firstpic = data[0].subject;
				for(var i = 0;i<num;i++){
					var option = $("<option></option>").html(data[i].subject);
					select.append(option);
				}
				$(".select2").append(select);

				var myCharts = echarts.init(document.getElementById("college_grade_column"));
				var myCharts2 = echarts.init(document.getElementById("college_grade_pie"));
				var option = {
					title: {
						text: '柱状图'
					},
					tooltip: {},
					legend: {
						data:['比列']
					},
					xAxis: {
						data: ["0~60","60~80","80~90","90~100"]
					},
					yAxis: {},
					series: [{
						name: '比列',
						type: 'bar',
						itemStyle: {
							normal: {
								color: function(params) {
									var colorList = [
										'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
										'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
										'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
									];
									return colorList[params.dataIndex]
								}
							}
						},
						data: [data[0].dispassPerentge,data[0].passPerentge,data[0].goodPerentge,data[0].perfectPerentge]
					}]
				};
				myCharts.setOption(option);

				myCharts2.setOption({
					title: {
						text: '饼状图'
					},
					tooltip: {
						trigger: 'item',
						formatter: "{a} <br/>{b}: {c} ({d}%)"
					},
					series : [
						{
							name: '成绩饼状图',
							type: 'pie',
							radius: '55%',
							data:[
								{value:data[0].dispassAmount, name:'不及格'+data[0].dispassPerentge},
								{value:data[0].passAmount, name:'及格'+data[0].passPerentge},
								{value:data[0].goodAmount, name:'良好'+data[0].goodPerentge},
								{value:data[0].perfectAmount, name:'优秀'+data[0].perfectPerentge}
							]
						},
					],
					itemStyle: {
						normal: {
							// 阴影的大小
							shadowBlur: 200,
							// 阴影水平方向上的偏移
							shadowOffsetX: 0,
							// 阴影垂直方向上的偏移
							shadowOffsetY: 0,
							// 阴影颜色
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					},
					labelLine: {
						normal: {
							lineStyle: {
								color: 'rgba(255, 255, 255, 0.3)'
							}
						}
					}
				})
				$("#choose_subject").on("change", function () {
					var choose = $("#choose_subject").val();
					for(var j = 0;j<num;j++){
						if(choose == data[j].subject){
							var myCharts = echarts.init(document.getElementById("college_grade_column"));
							var myCharts2 = echarts.init(document.getElementById("college_grade_pie"));
							var option = {
								title: {
									text: '柱状图'
								},
								tooltip: {},
								legend: {
									data:['比列']
								},
								xAxis: {
									data: ["0~60","60~80","80~90","90~100"]
								},
								yAxis: {},
								series: [{
									name: '比列',
									type: 'bar',
									itemStyle: {
										normal: {
											color: function(params) {
												var colorList = [
													'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
													'#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
													'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
												];
												return colorList[params.dataIndex]
											}
										}
									},
									data: [data[j].dispassPerentge,data[j].passPerentge,data[j].goodPerentge,data[j].perfectPerentge]
								}]
							};
							myCharts.setOption(option);

							myCharts2.setOption({
								title: {
									text: '饼状图'
								},
								tooltip: {
									trigger: 'item',
									formatter: "{a} <br/>{b}: {c} ({d}%)"
								},
								series : [
									{
										name: '成绩饼状图',
										type: 'pie',
										radius: '55%',
										data:[
											{value:data[j].dispassAmount, name:'不及格'+data[j].dispassPerentge},
											{value:data[j].passAmount, name:'及格'+data[j].passPerentge},
											{value:data[j].goodAmount, name:'良好'+data[j].goodPerentge},
											{value:data[j].perfectAmount, name:'优秀'+data[j].perfectPerentge}
										]
									},
								],
								itemStyle: {
									normal: {
										// 阴影的大小
										shadowBlur: 200,
										// 阴影水平方向上的偏移
										shadowOffsetX: 0,
										// 阴影垂直方向上的偏移
										shadowOffsetY: 0,
										// 阴影颜色
										shadowColor: 'rgba(0, 0, 0, 0.5)'
									}
								},
								labelLine: {
									normal: {
										lineStyle: {
											color: 'rgba(255, 255, 255, 0.3)'
										}
									}
								}
							})
						}
					}
				})
			},
			error: function (xhr,text) {
				console.log(text);
			}
		});




	});

	//excel表提交
	$("#grade_submit_excel").on("click",function(){
		var formData = new FormData(document.getElementById("form_submit"));
		$.ajax({
			url: "/ssms/Teacher/inputGradeByEXCEL.action",
			type: "POST",
			data: formData,
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function (data) {
				console.log(data);
				if(data.code == 0){
					alert("提交成功");
				}else{
					alert("提交失败");
				}
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});

	//模糊查询
	$(function(){
		$.ajax({
			url: "/ssms/Student/selectAllClassName.action",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			success: function(data){
				var data = data;
				$("#search_input").autocomplete({
					source: data,
					minLength: 2
				});
			},
			error: function(xhr,text){
				console.log(text);
			}
		});
	});



})

