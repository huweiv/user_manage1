<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>数据 - AdminLTE2定制版 | Log in</title>

<meta
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"
	name="viewport">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionicons/css/ionicons.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/adminLTE/css/AdminLTE.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/iCheck/square/blue.css">
</head>

<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a><b>人事</b>后台管理系统</a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg">登录系统</p>

			<form>
				<div class="form-group has-feedback">
					<input type="text" id="username" name="username" autocomplete="off" class="form-control" value="${cookie.username.value}"
						placeholder="用户名"> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" id="password" name="password" autocomplete="off" class="form-control" value="${cookie.password.value}"
						   placeholder="密码"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="text" id="kaptchaCode" name="kaptchaCode" autocomplete="off" class="form-control"
						   placeholder="验证码">
					<img src="${pageContext.request.contextPath}/user/kaptcha" alt="点击换一张" id="id_captcha" width="110px" height="40px"><a href="#" id="captcha">看不清</a>
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label><input type="checkbox" id="remember" name="remember" value="1"> 记住 下次自动登录</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<input type="button" value="登录" onClick="login()"/>
					</div>
					<div class="col-xs-4">
						<input type="button" value="注册" onClick="register()"/>
					</div>
					<!-- /.col -->
				</div>
				<dd style="text-align:center">
					<span id="msg"><font id="msgg" size="4px" color="red"></font></span>
				</dd>
			</form>

			<a href="${pageContext.request.contextPath}/user/forgetPwd">忘记密码</a><br>


		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->

	<!-- jQuery 2.2.3 -->
	<!-- Bootstrap 3.3.6 -->
	<!-- iCheck -->
<%--	<script--%>
<%--		src="${pageContext.request.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>--%>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/iCheck/icheck.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jQuery3.6.0.js"></script>
	<script>
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
		});
	</script>

	<script>
		$(document).ready(function(){
			$('#captcha').click(function () {
				<%--document.getElementById("id_captcha").src = "${pageContext.request.contextPath}/user/kaptcha";--%>
				$('#id_captcha').prop('src', "${pageContext.request.contextPath}/user/kaptcha");
			});
		});
	</script>

	<script>
		function register(){
			window.location.href = "${pageContext.request.contextPath}/user/register";
		}
	</script>


	<script>
		function login(){
			var remember = 0;
			if ($("input[name='remember']").is(':checked'))
				remember = $("input[name='remember']").val();
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/user/doLogin",
				data: {
					"username":$("#username").val(),
					"password":$("#password").val(),
					"kaptchaCode":$("#kaptchaCode").val(),
					"remember":remember
				},
				success:function(data){
					if (data == 0)
						window.location.href = "${pageContext.request.contextPath}/user/index";
					else if(data == 1) {
						$("#msgg").html("验证码已过期");
						$('#id_captcha').prop('src', "${pageContext.request.contextPath}/user/kaptcha");
					} else if (data == 2){
						$("#msgg").html("验证码错误");
						$('#id_captcha').prop('src', "${pageContext.request.contextPath}/user/kaptcha");
					} else if (data == 3) {
						window.location.href = "${pageContext.request.contextPath}/user/index";
					} else if (data == 4) {
						$("#msgg").html("用户名或密码错误");
						$('#id_captcha').prop('src', "${pageContext.request.contextPath}/user/kaptcha");
					}
				},
				error:function(){
					alert("方法失败");
				}
			});
		}
	</script>
</body>

</html>