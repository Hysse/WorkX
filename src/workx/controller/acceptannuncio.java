package workx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * Servlet implementation class acceptannuncio
 */
@WebServlet("/user/acceptannuncio")
public class acceptannuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public acceptannuncio() {
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
		String annid = request.getParameter("annID");
		HttpSession session = request.getSession();
		DesideriDAO desideridao = new DesideriDAO();
		Utente loggato = (Utente)session.getAttribute("account");
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		Desideri trovato;
		try {
			Annuncio annuncio = annunciodao.doRetrieveByKey(Integer.parseInt(annid));
			Collection<Desideri> desideri = desideridao.doRetrieveAll("");
			if(annuncio.getProfessione().equals(loggato.getProfessione())) {
				trovato = null;
				for(Desideri d : desideri) {
					if(d.getUtente().equals(loggato.getId()) && d.getAnnuncio().equals(annuncio.getId()))
						trovato = d;
				}
				if(trovato != null) {
					desideridao.doDelete(Integer.parseInt(trovato.getId()));
				}
				TransazioneDAO transazionedao = new TransazioneDAO();
				Transazione transazione = new Transazione();
				transazione.setUtente(loggato.getId());
				transazione.setAnnuncio(annuncio.getId());
				LocalDateTime data = LocalDateTime.now();
				transazione.setData(data.toString());
				transazione.setStato("0");
				transazionedao.doSave(transazione);
				session.setAttribute("notifica", "4");
			} else {
				session.setAttribute("notifica", "3");
			}
			response.sendRedirect("/ProgettoWorkX/home.jsp");
		} catch (NumberFormatException e) {
			System.out.println("Errore formato : "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
