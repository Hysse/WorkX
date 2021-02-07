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
			xhr.open("get","/ProgettoWorkX/admin/adminannuncicontentfiller",true);
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
					var richiedenti = arr.richiedenti;
					var utrans = arr.utentitrans;
					var trans = arr.transazioni;
					var annunci = arr.annunci;
					var text = "<h3 id='divisore'>Annunci WorkXchange</h3>";
					var i;
					for(i = 0; i < annunci.length; i++) {
						text = text.concat("<div class='annuncio'>"
								+ "<div class='annuncioin'>"
								+ "<form name='delete' id='delete' action='/ProgettoWorkX/admin/admindeleteannuncio' method='post'>"
								+ "<input type='submit' id='dltbtn' value='X'>"
								+ "<input type='hidden' name='annID' id='annID' value='"+annunci[i].id+"'>"
								+ "</form>"
								+ "<h2>"+annunci[i].titolo+"</h2>"
								+ "<h2>Professione : "+annunci[i].professione+"</h2>"
								+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+richiedenti[i].id+"'>"+richiedenti[i].nome+" "+richiedenti[i].cognome+"</a></h4>"
								+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
								+ "<p>"+annunci[i].descrizione+"</p>");
						var k;
						var transazione = null;
						var utente = null;
						for(k = 0; k < trans.length; k++) {
							if(trans[k].annuncio == annunci[i].id) {
								transazione = trans[k];
								utente = utrans[k];
							}
						}
						if(transazione != null) {
							text = text.concat("<h4>Data transazione : "+transazione.data+"</h4>"
									+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+utente.id+"'>"+utente.nome+" "+utente.cognome+"</a></h4>");
							if(transazione.stato == "0") {
								text = text.concat("<h4>--Transazione pendente--</h4>");
							} else {
								text = text.concat("<h4>--Transazione completata--</h4>");
							}
						}
						text = text.concat("</div>"
								+ "</div>");
					}
			}
					document.getElementById("content").innerHTML = text;
		};
			xhr2.open("get","/ProgettoWorkX/searchfiller?subj=adminannunci&search="+document.getElementById("search").value,true);
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