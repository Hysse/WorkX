package workx.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class crea_annuncio
 */
@WebServlet("/user/creaannuncio")
public class creaannuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public creaannuncio() {
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
		HttpSession session = request.getSession();
		Utente utente = (Utente)session.getAttribute("account");
		String titolo = request.getParameter("titolo");
		String descrizione = request.getParameter("desc");
		String ambito = request.getParameter("ambito");
		float costo = Float.parseFloat(request.getParameter("costo"));
		utente.setSaldo(utente.getSaldo()-costo);
		Annuncio annuncio = new Annuncio();
		annuncio.setTitolo(titolo);
		annuncio.setCosto(costo);
		annuncio.setUtente(utente.getId());
		annuncio.setDescrizione(descrizione);
		annuncio.setProfessione(ambito);
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		UtenteDAO utentedao = new UtenteDAO();
		try {
			annunciodao.doSave(annuncio);
			utentedao.doUpdate(utente);
			session.setAttribute("notifica", "4");
			response.sendRedirect("/ProgettoWorkX/user/profilo.jsp");
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
