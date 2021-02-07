<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/login.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="ISO-8859-1">
<title>Sign in</title>
</head>
<body  onload="fillSelect()">
	<%!String nome=""; String telefono=""; String cognome=""; String password=""; String select="1"; String notifica = ""; %>
	<%
		if(request.getAttribute("nome") != null) {
			nome = (String)request.getAttribute("nome");
			request.removeAttribute("nome");
		}
		if(request.getAttribute("cognome") != null) {
			cognome = (String)request.getAttribute("cognome");
			request.removeAttribute("cognome");
		}
		if(request.getAttribute("pass") != null){
			password = (String)request.getAttribute("pass");
			request.removeAttribute("pass");
		}
		if(request.getAttribute("notifica") != null) {
			notifica = (String)request.getAttribute("notifica");
			request.removeAttribute("notifica");
		}
		if(request.getAttribute("numero") != null) {
			telefono = (String)request.getAttribute("numero");
			request.removeAttribute("numero");
		}
	%>
	<script>
		function showPassword() {
			var x = document.getElementById("passwordreg");
			if(x.type === "password") {
				x.type = "text";
			}
			else {
				x.type = "password";	
			}
		}
		function fillSelect() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("profession").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","selectfiller",true);
			xhr.setRequestHeader("connection","close");
			xhr.send();
		}
	</script>
	<div id="outer">
	<div id="middle">
	<div id="loginform">
		<div id="miniheader">
		<a href="/ProgettoWorkX/home.jsp"><img align="center" id="logoheader" border="0" src="/ProgettoWorkX/Images/LogoWorkXAutoHeader.png" align="left" style="clear: both"></a>
		<br><hr>
		</div>
		<p class="inserisci">Inserisci i tuoi dati</p>
		<form id="signform" name="signform" action="signin" method="post">
			<label for="nome" class="reglab">Nome : </label>
			<input type="text" class="insert" name="nome" id="nome" placeholder="Nome..." value="<%=nome%>" required>
			<label for="cognome" class="reglab">Cognome : </label>
			<input type="text" class="insert" name="cognome" id="cognome" placeholder="Cognome..." value="<%=cognome%>" required>
			<label for="usernamereg" class="reglab">Username : </label>
			<input type="text" class="insert" name="user" id="usernamereg" placeholder="Username..." required>&nbsp<span id="notifica"><%=notifica%></span>
			<label for="passwordreg" id="passlab" class="reglab">Password : </label>
			<input type="password" class="insert" name="pass" id="passwordreg" placeholder="Password..." value="<%=password%>" required>
			<div class="mostrapwd"><input type="checkbox" id="mostrareg" onclick="showPassword()"><label for="mostrareg" id="showlab">Mostra Password</label></div>
			<label  id="numlab" for="numero" class="reglab">Telefono : </label>
			<input type="tel" class="insert" name="numero" id="numero" placeholder="Telefono..." value="<%=telefono%>" required>
			<label for="profession" id="proflab" class="reglab" >Professione : </label>
			<select name="selection" id="profession">
			</select>
			<div id="buttonsreg">
			<input type="submit" class="button" value="Enter">
			</div>
		</form>
	</div>
	</div>
	</div>
</body>
</html>