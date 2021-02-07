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
 * Servlet implementation class accettatifiller
 */
@WebServlet("/user/accettatifiller")
public class accettatifiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accettatifiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3 id='divisore'>Annunci accettati</h3>");
		HttpSession session = request.getSession();
		String titolo = "", descrizione = "",costo = "";
		Professione professione;
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		Utente utente = (Utente) session.getAttribute("account");
		TransazioneDAO transazionedao = new TransazioneDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		try {
			Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
			Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
			for(Annuncio a : annunci) {
				for(Transazione t : transazioni) {
					if(t.getAnnuncio().equals(a.getId()) && t.getUtente().equals(utente.getId())) {
						UtenteDAO utentedao = new UtenteDAO();
						Utente richiedente = utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente()));
						descrizione = a.getDescrizione();
						titolo = a.getTitolo();
						costo = Float.toString(a.getCosto());
						professione = professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione()));
						if(t.getStato().equals("0")) {
							out.println("<div class='annuncio'>"
								+ "<div class='annuncioin'>"
								+ "<form name='delete' id='delete' action='/ProgettoWorkX/user/deleteaccettato' method='post'>"
								+ "<input type='submit' id='dltbtn' value='X'>"
								+ "<input type='hidden' name='tranID' id='tranID' value='"+t.getId()+"'>"
								+ "</form>"
								+ "<h2>"+titolo+"</h2>"
								+ "<h2>Professione : "+professione.getNome()+"</h2>"
								+ "<h4>Richiedente : "+richiedente.getNome()+" "+richiedente.getCognome()+"</h2>"
								+ "<h4>Pagamento : "+costo+" Xtoken</h4>"
								+ "<p>"+descrizione+"</p>"
								+ "<h4>--Transazione pendente--</h4>"
								+ "</div>"
								+ "</div>");
						} else {
							out.println("<div class='annuncio'>"
									+ "<div class='annuncioin'>"
									+ "<h2>"+titolo+"</h2>"
									+ "<h2>"+professione.getNome()+"</h2>"
									+ "<h4>Richiedente : "+richiedente.getNome()+" "+richiedente.getCognome()+"</h2>"
									+ "<h4>Pagamento : "+costo+" Xtoken</h4>"
									+ "<p>"+descrizione+"</p>"
									+ "<h4>--Transazione completata--</h4>"
									+ "</div>"
									+ "</div>");
						}
					}
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
