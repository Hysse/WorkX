<%@page import="workx.model.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/stile.css">
<link rel="stylesheet" type="text/css" href="CSS/responsive-search.css">
<link rel="stylesheet" type="text/css" href="CSS/footer.css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>WorkXchange Homepage</title>
</head>
<body onload="fillContent()">
	<%
	String notifica = (String)session.getAttribute("notifica");
		if(notifica != null) { 
			if(notifica == "1") {%>
				<script>
	        		alert("Saldo esaurito, impossibile creare annunci!");
				</script>
				<%} else { 
					if(notifica == "2") {%>
					<script>
	        			alert("Annuncio creato con successo!");
					</script>
					<%} else {
						if(notifica == "3") {%>
						<script>
	        			alert("Professione non compatibile!");
						</script>
						<%} else {
							if(notifica == "4") {%>
								<script>
	        					alert("Annuncio accettato con successo!");
								</script>
							<%} else {
								if(notifica == "5") {%>
									<script>
	        						alert("Elemento già presente nella lista Desideri!");
									</script>	
								<%} else { %>
									<script>
	        						alert("Elemento aggiunto alla lista Desideri!");
									</script>
								<% } %>
		<%}}}} session.removeAttribute("notifica"); } %>
		
	<script>
		function fillContent() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("contenthome").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","homecontentfiller",true);
			xhr.setRequestHeader("connection","close");
			xhr.send();
		}
		
		function searchFunction() {
			var xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				if(xhr2.readyState == 4 && xhr2.status == 200) {
					var arr = JSON.parse(xhr2.responseText);
					var annunci = arr.annunci;
					var utenti = arr.utenti;
					var text = "<h3 id='divisore'>Annunci disponibili</h3>";
					var i;
					for(i = 0; i < annunci.length; i++) {
						text = text.concat("<div class='annuncio'>"
								+ "<div class='annuncioin'>");
						text = text.concat("<h2>Titolo : "+annunci[i].titolo+"</h2>"
								+ "<h2>Professione : "+annunci[i].professione+"</h2>"
								+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
								+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+utenti[i].id+"'>"+utenti[i].nome+" "+utenti[i].cognome+"</a></h4>"
								+ "<p>"+annunci[i].descrizione+"</p>"
								+ "<form name='acceptannuncio' id='acceptannuncio' action='/ProgettoWorkX/user/acceptannuncio' method='post'>"
								+ "<input type='submit' class='button' id='acceptbtn' value='Accetta Annuncio'>"
								+ "<input type='submit' class='button' id='acceptbtn' value='Aggiungi ai Desideri' formaction='/ProgettoWorkX/user/aggiungicart'>"
								+ "<input type='hidden' name='annID' id='annID' value='"+annunci[i].id+"'>"
								+ "</form>"
								+ "</div>"
								+ "</div>");
					}
					document.getElementById("contenthome").innerHTML = text;
				}
			};
			xhr2.open("get","searchfiller?subj=home&search="+document.getElementById("search").value,true);
			xhr2.setRequestHeader("connection","close");
			xhr2.send();
		}
	</script>
	<div class="container">
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
		<div id="searchbar"><input type="text" name="search" id="search" placeholder="Cerca..." oninput="searchFunction()"></div>
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
	<div class="content" id="contenthome" align="center"></div>
	<%@include file="footer.html" %>
	</div>
	</div>
</body>
</html>