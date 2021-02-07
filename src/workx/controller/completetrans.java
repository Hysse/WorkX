package workx.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class completetrans
 */
@WebServlet("/user/completetrans")
public class completetrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public completetrans() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idtran = request.getParameter("tranID");
		TransazioneDAO transazionedao = new TransazioneDAO();
		HttpSession session = request.getSession();
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		UtenteDAO utentedao = new UtenteDAO();
		try {
			Transazione transazione  = transazionedao.doRetrieveByKey(Integer.parseInt(idtran));
			Annuncio annuncio = new Annuncio();
			annuncio = annunciodao.doRetrieveByKey(Integer.parseInt(transazione.getAnnuncio()));
			Utente utente = new Utente();
			utente = utentedao.doRetrieveByKey(Integer.parseInt(transazione.getUtente()));
			utente.setSaldo(utente.getSaldo()+annuncio.getCosto());
			transazione.setStato("1");
			utentedao.doUpdate(utente);
			transazionedao.doUpdate(transazione);
			session.setAttribute("notifica", "3");
			response.sendRedirect("/ProgettoWorkX/user/profilo.jsp");
		} catch (NumberFormatException e) {
			System.out.println("Errore formato : "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}

	}

}
