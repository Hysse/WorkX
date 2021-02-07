package workx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Desideri;
import workx.model.DesideriDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;

/**
 * Servlet implementation class acceptcart
 */
@WebServlet("/user/acceptcart")
public class acceptcart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public acceptcart() {
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
		String desid = request.getParameter("desID");
		HttpSession session = request.getSession();
		DesideriDAO desideridao = new DesideriDAO();
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		TransazioneDAO transazionedao = new TransazioneDAO();
		try {
			Desideri desiderio = desideridao.doRetrieveByKey(Integer.parseInt(desid));
			Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
			boolean trovato = false;
			for(Transazione t : transazioni) {
				if(t.getAnnuncio().equals(desiderio.getAnnuncio())) {
					trovato = true;
				}	
			}
			if(trovato) {
				session.setAttribute("notifica", "2");
				response.sendRedirect("/ProgettoWorkX/user/carrello.jsp");
			} else {
				Transazione transazione = new Transazione();
				transazione.setAnnuncio(desiderio.getAnnuncio());
				transazione.setUtente(desiderio.getUtente());
				transazione.setStato("0");
				LocalDateTime data = LocalDateTime.now();
				transazione.setData(data.toString());
				transazionedao.doSave(transazione);
				desideridao.doDelete(Integer.parseInt(desiderio.getId()));
				session.setAttribute("notifica", "3");
				response.sendRedirect("/ProgettoWorkX/user/carrello.jsp");
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
