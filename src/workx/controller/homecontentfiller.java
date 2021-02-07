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
import javax.servlet.http.HttpSession;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Professione;
import workx.model.ProfessioneDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class homecontentfiller
 */
@WebServlet("/homecontentfiller")
public class homecontentfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public homecontentfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Utente utente = (Utente)session.getAttribute("account");
		if(utente == null) {
			utente = new Utente();
			utente.setId("guest");
		}
		PrintWriter out = response.getWriter();
		out.println("<h3 id='divisore'>Annunci disponibili</h3>");
		String titolo = "", professione = "",costo = "",nome = "",cognome = "", descrizione = "";
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		TransazioneDAO transazionedao = new TransazioneDAO();
		UtenteDAO utentedao = new UtenteDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		try {
			Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
			Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
			for(Annuncio a : annunci) {
				boolean nonmostrare = false;
				for(Transazione t : transazioni) {
					if(t.getAnnuncio().equals(a.getId())) {
						nonmostrare = true;
					}
				}
				if(a.getUtente().equals(utente.getId()))
					nonmostrare = true;
				if(!nonmostrare) {
					costo = String.valueOf(a.getCosto());
					descrizione = a.getDescrizione();
					titolo = a.getTitolo();
					Professione p = professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione()));
					professione = p.getNome();
					Utente u = utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente()));
					nome = u.getNome();
					cognome = u.getCognome();
					out.println("<div class='annuncio'>"
							+ "<div class='annuncioin'>"
							+ "<h2>"+titolo+"</h2>"
							+ "<h2>Professione : "+professione+"</h2>"
							+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+u.getId()+"'>"+nome+" "+cognome+"</a></h4>"
							+ "<h4>Pagamento : "+costo+" Xtoken</h4>"
							+ "<p>"+descrizione+"</p>"
							+ "<form name='acceptannuncio' id='acceptannuncio' action='/ProgettoWorkX/user/acceptannuncio' method='post'>"
							+ "<input type='submit' class='button' id='acceptbtn' value='Accetta Annuncio'>"
							+ "<input type='submit' class='button' id='acceptbtn' value='Aggiungi ai Desideri' formaction='/ProgettoWorkX/user/aggiungicart'>"
							+ "<input type='hidden' name='annID' id='annID' value='"+a.getId()+"'>"
							+ "</form>"
							+ "</div>"
							+ "</div>");
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
