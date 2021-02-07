<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/stile.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/responsive-search.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/footer.css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>WorkXchange Profilo</title>
</head>
<body onload="fillContent()">
	<%
		String notifica = (String)session.getAttribute("notifica");
		if(notifica != null) { 
			if(notifica == "1") {%>
				<script>
	        		alert("Desiderio eliminato con successo!");
				</script>
				<%} else {
					if(notifica == "2") {%>
						<script>
	        				alert("E' già presente una transazione per tale annuncio, eliminare il desiderio o attendere un annullamento di tale transazione!");
						</script>
					<%} else {%>
						<script>
	        				alert("Annuncio accettato con successo!");
						</script>
				<%}}session.removeAttribute("notifica"); } %>
		
	<script>
		function fillContent() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("content").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","/ProgettoWorkX/user/cartcontentfiller",true);
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
					var desideri = arr.desideri;
					var text = "<h3 id='divisore'>Lista desideri</h3>";
					var i;
					for(i = 0; i < annunci.length; i++) {
						text = text.concat("<div class='annuncio'>"
							+ "<div class='annuncioin'>"
							+ "<form name='delete' id='delete' action='/ProgettoWorkX/user/deletedeisderio' method='post'>"
							+ "<input type='submit' id='dltbtn' value='X'>"
							+ "<input type='hidden' name='annID' id='annID' value='"+desideri[i].id+"'>"
							+ "</form>"
							+ "<h2>"+annunci[i].titolo+"</h2>"
							+ "<h2>Professione : "+annunci[i].professione+"</h2>"
							+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+richiedenti[i].id+"'>"+richiedenti[i].nome+" "+richiedenti[i].cognome+"</a></h4>"
							+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
							+ "<p>"+annunci[i].descrizione+"</p>"
							+ "<form name='cartaccept' id='cartaccept' action='/ProgettoWorkX/user/acceptcart' method='post'>"
							+ "<input type='submit' class='button' id='accept' value='Accetta annuncio'>"
							+ "<input type='hidden' name='desID' id='desID' value='"+desideri[i].id+"'>"
							+ "</form>"
							+ "</div>"
							+ "</div>");
					}
			}
					document.getElementById("content").innerHTML = text;
		};
			xhr2.open("get","/ProgettoWorkX/searchfiller?subj=cart&search="+document.getElementById("search").value,true);
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