<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/stile.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/responsive-search.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/footer.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>WorkXchange Annunci Accettati</title>
</head>
<body onload="fillContent()">
	<%
		String notifica = (String)session.getAttribute("notifica");
		if(notifica != null) { 
			if(notifica == "1") {%>
				<script>
	        		alert("Accettazione annullata!");
				</script>
			<%} %>
	<% session.removeAttribute("notifica"); } %>
	<script>
		function fillContent() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("content").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","/ProgettoWorkX/user/accettatifiller",true);
			xhr.setRequestHeader("connection","close");
			xhr.send();
		}
		
		function searchFunction() {
			var xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				if(xhr2.readyState == 4 && xhr2.status == 200) {
					var arr = JSON.parse(xhr2.responseText);
					var annunci = arr.annunci;
					var richiedenti = arr.richiedenti;
					var transazioni = arr.transazioni;
					var text = "<h3 id='divisore'>Annunci accettati</h3>";
					var i;
					for(i = 0; i < annunci.length; i++) {
						if(transazioni[i].stato == "0") {
						text = text.concat("<div class='annuncio'>"
							+ "<div class='annuncioin'>"
							+ "<form name='delete' id='delete' action='/ProgettoWorkX/user/deleteaccettato' method='post'>"
							+ "<input type='submit' id='dltbtn' value='X'>"
							+ "<input type='hidden' name='annID' id='annID' value='"+transazioni[i].id+"'>"
							+ "</form>"
							+ "<h2>"+annunci[i].titolo+"</h2>"
							+ "<h2>Professione : "+annunci[i].professione+"</h2>"
							+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+richiedenti[i].id+"'>"+richiedenti[i].nome+" "+richiedenti[i].cognome+"</a></h4>"
							+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
							+ "<p>"+annunci[i].descrizione+"</p>"
							+ "<h4>--Transazione pendente--</h4>");
						} else {
							text = text.concat("<div class='annuncio'>"
									+ "<div class='annuncioin'>"
									+ "<h2>"+annunci[i].titolo+"</h2>"
									+ "<h2>"+annunci[i].professione+"</h2>"
									+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+richiedenti[i].id+"'>"+richiedenti[i].nome+" "+richiedenti[i].cognome+"</a></h4>"
									+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
									+ "<p>"+annunci[i].descrizione+"</p>"
									+ "<h4>--Transazione completata--</h4>");
						}
						text = text.concat("</div>"
								+ "</div>");
					}
			}
					document.getElementById("content").innerHTML = text;
		};
			xhr2.open("get","/ProgettoWorkX/searchfiller?subj=accettati&search="+document.getElementById("search").value,true);
			xhr2.setRequestHeader("connection","close");
			xhr2.send();
	}
	</script>
	<%@include file="header.jsp" %>
	<div class="main-wrapper">
	<div class="content" id="content" align="center"></div>
	<%@include file="/footer.html" %>
	</div>
</body>
</html>