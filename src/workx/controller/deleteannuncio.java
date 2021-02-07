package workx.controller;

import java.io.IOException;
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
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class deleteannuncio
 */
@WebServlet("/user/deleteannuncio")
public class deleteannuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteannuncio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("annID");
		HttpSession session = request.getSession();
		UtenteDAO utentedao = new UtenteDAO();
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		try {
			Annuncio annuncio = annunciodao.doRetrieveByKey(Integer.parseInt(id));
			TransazioneDAO transazionedao = new TransazioneDAO();
			Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
			boolean trovato = false;
			for(Transazione t : transazioni) {
				if(t.getAnnuncio().equals(annuncio.getId()))
					trovato = true;
			}
			if(!trovato) {
				annunciodao.doDelete(Integer.parseInt(annuncio.getId()));
				Utente utente = new Utente();
				utente = utentedao.doRetrieveByKey(Integer.parseInt(annuncio.getUtente()));
				utente.setSaldo(utente.getSaldo()+annuncio.getCosto());
				utentedao.doUpdate(utente);
				session.setAttribute("account", utente);
				session.setAttribute("notifica", "1");
			} else {
				session.setAttribute("notifica", "2");
			}
			response.sendRedirect("/ProgettoWorkX/user/profilo.jsp");
		} catch (NumberFormatException e) {
			System.out.println("Errore formato : "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
