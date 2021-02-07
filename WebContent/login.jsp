<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/login.css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
</head>
<body>
	<script>
		function showPassword() {
			var x = document.getElementById("password");
			if(x.type === "password") {
				x.type = "text";
			}
			else {
				x.type = "password";	
			}
		}
	</script>
	<div id="outer">
	<div id="middle">
	<div id="loginform">
		<div id="miniheader">
		<a href="/ProgettoWorkX/home.jsp"><img align="center" id="logoheader" border="0" src="/ProgettoWorkX/Images/LogoWorkXAutoHeader.png" align="left" style="clear: both"></a>
		<hr>
		</div>
		<p class="inserisci">Inserisci le tue credenziali</p>
		<form id="inputform" name="login" action="login" method="post">
			<label for="username">Username : </label>
			<input class="insert" type="text" name="user" id="username" placeholder="Username..." >
			<label for="password">Password : </label>
			<input class="insert" type="password" name="pass" id="password" placeholder="Password..." >
			<input type="checkbox" onclick="showPassword()" name="mostra" id="mostra">
			<label id="showlabel" for="mostra">Mostra Password</label>
			<div id="buttons">
			<input class="button" type="reset" value="Reset">
			<input class="button" type="submit" value="Enter">
			<input class="button" type="submit" value="Registrati" formaction="registration.jsp">
			</div>
		</form>
		<%! String nota = "";%>
		<% if(request.getAttribute("notifica") != null) { %>
			<% nota = (String)request.getAttribute("notifica"); %>
			<p id="notifica"><%=nota%><p>
		<% } %>
		</div>
		</div>
		</div>
</body>
</html>