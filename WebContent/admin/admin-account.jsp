<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/stile.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/responsive-admin.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/footer.css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>WorkXchange ADMIN</title>
</head>
<body onload="fillContent()">
		
	<script>
		
		function fillContent() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("content").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","/ProgettoWorkX/admin/adminaccountcontentfiller",true);
			xhr.setRequestHeader("connection","close");
			xhr.send();
			
			var xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				if(xhr2.readyState == 4 && xhr2.status == 200) {
					document.getElementById("delprofession").innerHTML = xhr2.responseText;
				}
			};
			xhr2.open("get","/ProgettoWorkX/selectfiller",true);
			xhr2.setRequestHeader("connection","close");
			xhr2.send();
		}
		
		function searchFunction() {
			var xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				if(xhr2.readyState == 4 && xhr2.status == 200) {
					var arr = JSON.parse(xhr2.responseText);
					var utenti = arr.utenti;
					var prof = arr.professioni;
					var cred = arr.credenziali;
					var text = "<h3 id='divisore'>Gestione account</h3>";
					var ruolo;
					var i;
					for(i = 0; i < utenti.length; i++) {
						text = text.concat("<div class='infoaccountin'>");
						if(utenti[i].ruolo == "1") {
							ruolo = "Amministratore";
						} else {
							ruolo = "Worker";
							text = text.concat("<form name='delete' id='delete' action='/ProgettoWorkX/admin/deleteaccount' method='post'>"
									+ "<input type='submit' id='dltbtn' value='X'>"
									+ "<input type='hidden' name='userID' id='userID' value='"+utenti[i].id+"'>"
									+ "</form>");
						}
						text = text.concat("<h3>Nome : "+utenti[i].nome+"<h3>"
								+ "<h3>Cognome : "+utenti[i].cognome+"</h3>"
								+ "<h4>Username : "+cred[i].username+"</h4>"
								+ "<h4>Professione : "+prof[i].nome+"</h4>"
								+ "<p>"+prof[i].descrizione+"</p>"
								+ "<h4>Ruolo : "+ruolo+"</h4>"
								+ "<h4>Saldo : "+utenti[i].saldo.toFixed(1)+" Xtokens</h4>"
								+ "<h4>Telefono : "+utenti[i].telefono+"</h4>");
						if(utenti[i].ruolo == "1") {
							text = text.concat("<form name='downgrade' id='downgrade' action='/ProgettoWorkX/admin/downgrade' method='post'>"
									+ "<input type='submit' class='button' id='declassa' value='Rimuovi privilegi'>"
									+ "<input type='hidden' name='userID' id='userID' value='"+utenti[i].id+"'>"
									+ "</form>");
						} else {
							text = text.concat("<form name='upgrade' id='upgrade' action='/ProgettoWorkX/admin/upgrade' method='post'>"
									+ "<input type='submit' class='button' id='eleva' value='Conferisci privilegi'>"
									+ "<input type='hidden' name='userID' id='userID' value='"+utenti[i].id+"'>"
									+ "</form>");
						}
						text = text.concat("</div>");
					}
			}
					document.getElementById("content").innerHTML = text;
		};
			xhr2.open("get","/ProgettoWorkX/searchfiller?subj=adminaccount&search="+document.getElementById("search").value,true);
			xhr2.setRequestHeader("connection","close");
			xhr2.send();
	}
	</script>
	<%@include file="header-admin.jsp" %>
	<div class="main-wrapper">
	<%@include file="left-side-admin.jsp" %>
	<div class="content" id="content" align="center"></div>
	<%@include file="right-side-admin.jsp" %>
	<%@include file="/footer.html" %>
	</div>
</body>
</html>