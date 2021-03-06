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
 * Servlet implementation class admindeletetrans
 */
@WebServlet("/admin/admindeletetrans")
public class admindeletetrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admindeletetrans() {
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
		String trans = request.getParameter("tranID");
		TransazioneDAO transazionedao = new TransazioneDAO();
		try {
			Transazione transazione = transazionedao.doRetrieveByKey(Integer.parseInt(trans));
			transazionedao.doDelete(Integer.parseInt(transazione.getId()));
			response.sendRedirect("/ProgettoWorkX/admin/admin-trans.jsp");
		} catch (NumberFormatException e) {
			System.out.println("Errore formato : "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
