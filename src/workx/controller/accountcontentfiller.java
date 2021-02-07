package workx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Professione;
import workx.model.ProfessioneDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class accountcontentfiller
 */
@WebServlet("/user/accountcontentfiller")
public class accountcontentfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accountcontentfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Utente utente = (Utente) request.getSession().getAttribute("account");
		String username = "", ruolo = "";
		if(utente.getRuolo().equals("1"))
			ruolo = "Amministratore";
		else
			ruolo = "Worker";
		CredenzialiDAO credenzialidao = new CredenzialiDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		try {
				Professione professione = professionedao.doRetrieveByKey(Integer.parseInt(utente.getProfessione()));
				username = credenzialidao.doRetrieveByKey(Integer.parseInt(utente.getCredenziali())).getUsername();
				out.println("<h3 id='divisore'>Info account</h3>");
				out.println("<div class='infoaccountin'>"
						+ "<h3>Nome : "+utente.getNome()+"<h3>"
						+ "<h3>Cognome : "+utente.getCognome()+"</h3>"
						+ "<h4>Username : "+username+"</h4>"
						+ "<h4>Professione : "+professione.getNome()+"</h4>"
						+ "<p>"+professione.getDescrizione()+"</p>"
						+ "<h4>Ruolo : "+ruolo+"</h4>"
						+ "<h4>Saldo : "+Float.toString(utente.getSaldo())+" Xtokens</h4>"
						+ "<h4>Telefono : "+utente.getTelefono()+"</h4>"
						+ "</div>");
				out.println("<h3 id='divisore'>Annunci pubblicati</h3>");
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				TransazioneDAO transazionedao = new TransazioneDAO();
				String titolo = "", costo = "", descrizione = "";
				Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				for(Annuncio a : annunci) {
					if(a.getUtente().equals(utente.getId())) {
						
					descrizione = a.getDescrizione();
					titolo = a.getTitolo();
					costo = String.valueOf(a.getCosto());
					professione = professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione()));
					out.println("<div class='annuncio'>"
							+ "<div class='annuncioin'>"
							+ "<form name='delete' id='delete' action='/ProgettoWorkX/user/deleteannuncio' method='post'>"
							+ "<input type='submit' id='dltbtn' value='X'>"
							+ "<input type='hidden' name='annID' id='annID' value='"+a.getId()+"'>"
							+ "</form>"
							+ "<h2>"+titolo+"</h2>"
							+ "<h2>Professione : "+professione.getNome()+"</h2>"
							+ "<h4>Pagamento : "+costo+" Xtoken</h4>"
							+ "<p>"+descrizione+"</p>");
					Transazione trans = null;
					for(Transazione t : transazioni) {
						if(t.getAnnuncio().equals(a.getId()))
							trans = t;
					}
					if(trans != null) {
						if(trans.getStato().equals("0")) {
							UtenteDAO utentedao = new UtenteDAO();
							Utente u = utentedao.doRetrieveByKey(Integer.parseInt(trans.getUtente()));
							out.println("<h4>Data transazione : "+trans.getData()+"</h4>"
									+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+trans.getUtente()+"'>"+u.getNome()+" "+u.getCognome()+"</a></h4>"
									+ "<form name='complete' id='complete' action='/ProgettoWorkX/user/completetrans' method='post'>"
									+ "<input type='submit' class='button' id='accept' value='Completa transazione'>"
									+ "<input type='hidden' name='tranID' id='tranID' value='"+trans.getId()+"'>"
									+ "</form>");
							}
						else
						{
							UtenteDAO utentedao = new UtenteDAO();
							Utente u = utentedao.doRetrieveByKey(Integer.parseInt(trans.getUtente()));
							out.println("<h4>Data transazione : "+trans.getData()+"</h4>"
									+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+trans.getUtente()+"'>"+u.getNome()+" "+u.getCognome()+"</a></h4>"
									+ "<h4> --Transazione completata--");
						}
					}
					out.println("</div>"
							+"</div>");
					}
					
				}
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
