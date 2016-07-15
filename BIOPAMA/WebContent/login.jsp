<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Connexion</title>
		<link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="resources/css/app.css" rel="stylesheet" type="text/css" media="screen" />
		<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
	</head>

<body>
<form action="j_spring_security_check" method='POST'>

		<div style="position: fixed; top: 0; bottom: 0; left: 0; right: 0; border: solid 1px black;">
			<div class="login-container" style="position: absolute; width: 500px; height: 300px; top:0; bottom:0; left:0; right: 0; margin: auto; background-image: url(<%=request.getContextPath()%>/resources/images/bg_login2.png); background-repeat: no-repeat !important;">
				<div class="login-card" >
					<div class="login-form" >
						
						
							<div class="input-group input-sm" >
								<label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
								<input type="text" class="form-control" id="username" name='j_username' value='' placeholder=" Utilisateur" required>
							</div>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
								<input type="password" class="form-control" id="password" name='j_password' placeholder="Mot de passe" required>
							</div>
							
								
							<div class="form-actions" align="center" style="padding-top: 10px;">
								<input type="submit"
									class="btn btn-block btn-primary btn-default" value="Se connecter"  style="height: 30px; width: 150px;">
							</div>
						
					</div>
				</div>
			</div>
		</div>
</form>
	</body>
	
</html>