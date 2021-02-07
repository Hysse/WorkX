<%@page import="workx.model.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/stile.css">
<link rel="stylesheet" type="text/css" href="CSS/responsive-nosearch.css">
<link rel="stylesheet" type="text/css" href="CSS/footer.css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>WorkXchange Profile</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body onload="fillContent()">
	<%String otheraccount = (String)request.getParameter("other");%>
	<script>
		function fillContent() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("content").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","/ProgettoWorkX/otheraccountfiller",true);
			xhr.setRequestHeader("otheraccount", "<%=otheraccount%>");
			xhr.setRequestHeader("connection","close");
			xhr.send();
		}
	</script>
	<div class="header-wrapper">
	<div class="header">
		<img id="logoheader" border="0" src="Images/LogoWorkXAutoHeader.png" align="left">
		<p>Time is money<p>
	</div>
	<div class="nav">
		<ul>
			<li><a href="home.jsp">Home</a></li>
			<%if(session.getAttribute("account") != null) { %>
			<li><a href="logout">Logout</a></li>
			<%} else {%>
			<li><a href="login.jsp">Login</a></li>
			<%} %>
		</ul>
		<div id="navbtns">
		<%if(session.getAttribute("account") != null) { Utente utente = (Utente)session.getAttribute("account");
			if(utente.getRuolo().equals("1")) {%>
			<div id="admin"><a href="/ProgettoWorkX/admin/admin-annunci.jsp"><img id="adminpic" border="0" src="Images/admin-piccola.png"></a></div>
		<%}}%>
		<%if(session.getAttribute("account") != null) { %>
			<div id="cart"><a id="carrello" href="/ProgettoWorkX/user/carrello.jsp"><img id="cartpic" border="0" src="Images/cartpic.png"></a></div>
		<%}%>
		<div id="profilo"><a id="profilelink" href="user/profilo.jsp"><img id="profilepic" border="0" src="Images/propic.png"></a></div>	
		</div>
	</div>
	</div>
	<div class="main-wrapper">
	<div class="content" id="content" align="center"></div>
	<div class="leftside"></div>
	<div class="rightside"></div>
	</div>
	<%@include file="footer.html" %>
</body>
</html>