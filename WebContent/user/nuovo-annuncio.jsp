<%@page import="com.mysql.cj.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/stile.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/footer.css">
<link rel="stylesheet" type="text/css" href="/ProgettoWorkX/CSS/responsive-nosearch.css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>WorkXchange Nuovo Annuncio</title>
</head>
<body onload="fillSelect()">
	<%@page import="workx.model.*" %>
	<%	
		Utente utente = null;
		if(session.getAttribute("account") != null)
			utente = (Utente) session.getAttribute("account");
	%>
	<script>
	
		function fillSelect() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4 && xhr.status == 200) {
					document.getElementById("ambito").innerHTML = xhr.responseText;
				}
			};
			xhr.open("get","/ProgettoWorkX/selectfiller",true);
			xhr.setRequestHeader("connection","close");
			xhr.send();
		}
		
		function doPlus() {
			var costo = document.getElementById("costo").value;
			var floatcosto = parseFloat(costo)
			if(floatcosto != <%=utente.getSaldo()%>)
				document.getElementById("costo").value = floatcosto + 0.5;
		}
		
		function doMinus() {
			var costo = document.getElementById("costo").value;
			var floatcosto = parseFloat(costo);
			if(floatcosto != 0.5)
				document.getElementById("costo").value = floatcosto - 0.5;
		}
		
		function doTimes() {
			var costo = document.getElementById("costo").value;
			var floatcosto = parseFloat(costo)*2;
			if(floatcosto <= <%=utente.getSaldo()%>)
				document.getElementById("costo").value = floatcosto;
		}
		
		function doDiv() {
			var costo = document.getElementById("costo").value;
			var intcosto = parseInt(costo);
			if(intcosto != 0) {
				if(intcosto == 1)
					document.getElementById("costo").value = 0.5;
				else{
					if(intcosto % 2 == 0)
						document.getElementById("costo").value = intcosto/2;
					else {
						document.getElementById("costo").value = (intcosto-1)/2;
					}
				}
			}
		}
		
	</script>
	<%@include file="header-nosearch.jsp" %>
	<div class="main-wrapper">
	<div class="content" id="content" align="center">
		<h2>Inserire i dati riguardanti l'annuncio</h2>
		<form name="form-annuncio" id="form-annuncio" action="/ProgettoWorkX/user/creaannuncio" method="post">
			<label for="titolo" class="annlab">Titolo : </label>
			<input type="text" class="insert" name="titolo" id="titolo" placeholder="Titolo..." required>
			<label for="desc" class="annlab">Descrizione : </label>
			<textarea maxlength="200" class="insert" name="desc" id="desc" rows="6" cols="48" placeholder="Descrizione..." required></textarea>
			<label for="ambito" class="annlab">Ambito : </label>
			<select name="ambito" class="insert" id="ambito"></select>
			<p id="residuo">Conto personale : <%=utente.getSaldo()%> Xtoken</p>
			<div class="offering">
			<label for="costo">Offerta : </label>
			<input type="text" name="costo" id="costo" value="0.5" readonly>
			</div>
			<div id="operators">
			<input type="button" class='button' id= "plus" name="plus" value="+0.5" onclick="doPlus()">
			<input type="button" class='button' id= "minus" name="minus" value="-0.5" onclick="doMinus()">
			<input type="button" class='button' id= "times" name="times" value="x2" onclick="doTimes()">
			<input type="button" class='button' id= "division" name="division" value="/2" onclick="doDiv()">
			</div>
			<input type="submit" class='button' id= "create" value="Crea Annuncio">
		</form>
	</div>
	<%@include file="/footer.html" %>
	</div>
</body>
</html>