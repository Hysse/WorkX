<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header ADMIN</title>
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
			<li><a href="/ProgettoWorkX/admin/admin-annunci.jsp">Annunci</a></li>
			<li><a href="/ProgettoWorkX/admin/admin-trans.jsp">Transazioni</a></li>
			<li><a href="/ProgettoWorkX/admin/admin-account.jsp">Account</a></li>
			<li><a href="/ProgettoWorkX/logout">Logout</a></li>
		</ul>
		<div id="navbtns">
		<div id="cart"><a id="carrello" href="/ProgettoWorkX/user/carrello.jsp"><img id="cartpic" border="0" src="/ProgettoWorkX/Images/cartpic.png"></a></div>
		<div id="profilo"><a id="profilelink" href="/ProgettoWorkX/user/profilo.jsp"><img id="profilepic" border="0" src="/ProgettoWorkX/Images/propic.png"></a></div>	
		</div>
	</div>
</div>
</html>