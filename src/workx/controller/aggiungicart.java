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
import workx.model.Desideri;
import workx.model.DesideriDAO;
import workx.model.Utente;

/**
 * Servlet implementation class aggiungicart
 */
@WebServlet("/user/aggiungicart")
public class aggiungicart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public aggiungicart() {
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
		Utente loggato = (Utente)session.getAttribute("account");
		DesideriDAO desideridao = new DesideriDAO();
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		try {
			Annuncio annuncio = annunciodao.doRetrieveByKey(Integer.parseInt(annid));
			if(annuncio.getProfessione().equals(loggato.getProfessione())) {
				Collection<Desideri> desideri = desideridao.doRetrieveAll("");
				boolean trovato = false;
				for(Desideri d : desideri) {
					if(d.getUtente().equals(loggato.getId()) && d.getAnnuncio().equals(annid))
						trovato = true;
				}
				if(trovato) {
					session.setAttribute("notifica", "5");
					response.sendRedirect("/ProgettoWorkX/home.jsp");
				} else {
					Desideri desiderio = new Desideri();
					desiderio.setAnnuncio(annid);
					desiderio.setUtente(loggato.getId());
					desideridao.doSave(desiderio);
					session.setAttribute("notifica", "6");
					response.sendRedirect("/ProgettoWorkX/home.jsp");
				}
			} else {
				session.setAttribute("notifica", "3");
				response.sendRedirect("/ProgettoWorkX/home.jsp");
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
