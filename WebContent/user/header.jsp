<%@page import="workx.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
</head>
<body>
<div class="header-wrapper">
	<div class="header">
		<img id="logoheader" border="0" src="/ProgettoWorkX/Images/LogoWorkXAutoHeader.png" align="left">
		<p>Time is money<p>
	</div>
	<div class="nav">
		<ul>
			<li><a href="/ProgettoWorkX/home.jsp">Home</a></li>
			<li><a href="/ProgettoWorkX/user/nuovo-annuncio.jsp">Nuovo</a></li>
			<li><a href="/ProgettoWorkX/user/annunci-accettati.jsp">Accettati</a>
			<li><a href="/ProgettoWorkX/logout">Logout</a></li>
		</ul>
	 	<div id="searchbar"><input type="text" name="search" id="search" placeholder="Cerca..." oninput="searchFunction()"></div>
	 	<div id="navbtns">
		<%if(session.getAttribute("account") != null) { Utente u = (Utente)session.getAttribute("account");
			if(u.getRuolo().equals("1")) {%>
			<div id="admin"><a href="/ProgettoWorkX/admin/admin-annunci.jsp"><img id="adminpic" border="0" src="/ProgettoWorkX/Images/admin-piccola.png"></a></div>
		<%}}%>
		<%if(session.getAttribute("account") != null) { %>
			<div id="cart"><a id="carrello" href="/ProgettoWorkX/user/carrello.jsp"><img id="cartpic" border="0" src="/ProgettoWorkX/Images/cartpic.png"></a></div>
		<%}%>
		<div id="profilo"><a id="profilelink" href="/ProgettoWorkX/user/profilo.jsp"><img id="profilepic" border="0" src="/ProgettoWorkX/Images/propic.png"></a></div>	
		</div>
	</div>
</div>
</html>