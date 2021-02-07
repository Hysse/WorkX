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
	        		alert("Annuncio eliminato con successo!");
				</script>
				<%} else {
					if(notifica == "2") {%>
					<script>
	        			alert("E' già presente una transazione per tale annuncio, non è più possibile eliminarlo!");
					</script>
					<%} else {
						if(notifica == "3") {%>
						<script>
	        				alert("Transazione completata!");
						</script>
					<%} else { %>
						<script>
	        				alert("Annuncio creato con successo!");
						</script>
						<% } %>
		<% }} session.removeAttribute("notifica"); } %>
		
	<script>
		function fillContent() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("content").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","/ProgettoWorkX/user/accountcontentfiller",true);
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
					var account = arr.account;
					var credaccount = arr.credaccount;
					var prof = arr.prof;
					var transazioni = arr.transazioni;
					if(account.ruolo == "1")
						account.ruolo = "Amministratore";
					else
						account.ruolo = "Worker";
					var text = "<h3 id='divisore'>Info account</h3>"
							+ "<div class='infoaccountin'>"
							+ "<h3>Nome : "+account.nome+"<h3>"
							+ "<h3>Cognome : "+account.cognome+"</h3>"
							+ "<h4>Username : "+credaccount+"</h4>"
							+ "<h4>Professione : "+prof.nome+"</h4>"
							+ "<p>"+prof.descrizione+"</p>"
							+ "<h4>Ruolo : "+account.ruolo+"</h4>"
							+ "<h4>Saldo : "+account.saldo.toFixed(1)+" Xtokens</h4>"
							+ "<h4>Telefono : "+account.telefono+"</h4>"
							+ "</div>";
					text = text.concat("<h3 id='divisore'>Annunci pubblicati</h3>");
					var i;
					for(i = 0; i < annunci.length; i++) {
						text = text.concat("<div class='annuncio'>"
							+ "<div class='annuncioin'>"
							+ "<form name='delete' id='delete' action='/ProgettoWorkX/user/deleteannuncio' method='post'>"
							+ "<input type='submit' id='dltbtn' value='X'>"
							+ "<input type='hidden' name='annID' id='annID' value='"+annunci[i].id+"'>"
							+ "</form>"
							+ "<h2>"+annunci[i].titolo+"</h2>"
							+ "<h2>Professione : "+annunci[i].professione+"</h2>"
							+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
							+ "<p>"+annunci[i].descrizione+"</p>");
						var j;
						var transazione = null;
						for(j = 0; j < transazioni.length; j++) {
							if(transazioni[j].annuncio == annunci[i].id)
								transazione = transazioni[j];
						}
						if(transazione != null) {
							var utente;
							var z;
							for(z = 0; z < utenti.length; z++) {
								if(utenti[z].id == transazione.utente)
									utente = utenti[z];
							}
							if(transazione.stato == "0") {
								text = text.concat("<h4>Data transazione : "+transazione.data+"</h4>"
										+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+transazione.utente+"'>"+utente.nome+" "+utente.cognome+"</a></h4>"
										+ "<form name='complete' id='complete' action='/ProgettoWorkX/user/completetrans' method='post'>"
										+ "<input type='submit' class='button' id='accept' value='Completa transazione'>"
										+ "<input type='hidden' name='tranID' id='tranID' value='"+transazione.id+"'>"
										+ "</form>");
							} else {
								text = text.concat("<h4>Data transazione : "+transazione.data+"</h4>"
								+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+transazione.utente+"'>"+utente.nome+" "+utente.cognome+"</a></h4>"
								+ "<h4> --Transazione completata--");
							}
						}
						text = text.concat("</div>"
								+ "</div>");
					}
					document.getElementById("content").innerHTML = text;
				}
			};
			xhr2.open("get","/ProgettoWorkX/searchfiller?subj=account&search="+document.getElementById("search").value,true);
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