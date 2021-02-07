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
			xhr.open("get","/ProgettoWorkX/admin/admintranscontentfiller",true);
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
					var annunci = arr.annunci;
					var workers = arr.workers;
					var richiedenti = arr.richiedenti;
					var trans = arr.transazioni;
					var text = "<h3 id='divisore'>Transazioni WorkXchange</h3>";
					var i;
					for(i = 0; i < trans.length; i++) {
						text = text.concat("<div class='annuncio'>"
								+ "<div class='annuncioin'>"
								+ "<form name='delete' id='delete' action='/ProgettoWorkX/admin/admindeletetrans' method='post'>"
								+ "<input type='submit' id='dltbtn' value='X'>"
								+ "<input type='hidden' name='tranID' id='tranID' value='"+trans[i].id+"'>"
								+ "</form>"
								+ "<h2>"+annunci[i].titolo+"</h2>"
								+ "<h2>Professione : "+annunci[i].professione+"</h2>"
								+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+richiedenti[i].id+"'>"+richiedenti[i].nome+" "+richiedenti[i].cognome+"</a></h4>"
								+ "<h4>Pagamento : "+annunci[i].costo.toFixed(1)+" Xtoken</h4>"
								+ "<p>"+annunci[i].descrizione+"</p>"
								+ "<h4>Data transazione : "+trans[i].data+"</h4>"
								+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+workers[i].id+"'>"+workers[i].nome+" "+workers[i].cognome+"</a></h4>");
							if(trans[i].stato == "0") {
								text = text.concat("<h4>--Transazione pendente--</h4>");
							} else {
								text = text.concat("<h4>--Transazione completata--</h4>");
							}
						text = text.concat("</div>"
								+ "</div>");
					}
					document.getElementById("content").innerHTML = text;
				}
		};
			xhr2.open("get","/ProgettoWorkX/searchfiller?subj=admintrans&search="+document.getElementById("search").value,true);
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