
<div class="leftside">
	<h4 id="divisore">Aggiunta professioni : </h4>
	<form name="createprof" id="createprof" action="/ProgettoWorkX/admin/createprof" method="post">
		<label for="nome">Nome : </label>
		<input class="insert" type="text" name="nome" id="nome" placeholder="Nome..." required>
		<label for="desc">Descrizione : </label>
		<textarea class="insert" rows="7" cols="30" name="desc" id="desc" placeholder="Descrizione..." required></textarea>
		<input class="button" type="submit" value="Aggiungi Professione">
	</form>
	<%! String nota = "";%>
		<% if(session.getAttribute("professione") != null) { %>
			<% nota = (String)session.getAttribute("professione"); %>
			<p class="notifier"><%=nota%><p>
		<% session.removeAttribute("professione"); } %>
		
	<h4 id="divisorebordered">Eliminazione professioni : </h4>
	<form name="deleteprof" id="deleteprof" action="/ProgettoWorkX/admin/deleteprof" method="post">
		<label for="profession">Nome : </label>
		<select class="insert" name="profession" id="delprofession"></select>
		<input class="button" id="dltprof" type="submit" value="Elimina Professione">
		<% if(session.getAttribute("delete") != null) { %>
			<% nota = (String)session.getAttribute("delete"); %>
			<p class="notifier"><%=nota%><p>
		<% session.removeAttribute("delete"); } %>
	</form>
</div>
