package workx.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import workx.model.AnnuncioDAO;
import workx.model.TransazioneDAO;

/**
 * Servlet implementation class deleteaccettato
 */
@WebServlet("/user/deleteaccettato")
public class deleteaccettato extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteaccettato() {
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
		String tranid = request.getParameter("tranID");
		HttpSession session = request.getSession();
		TransazioneDAO transazionedao = new TransazioneDAO();
		try {
			transazionedao.doDelete(Integer.parseInt(tranid));
			session.setAttribute("notifica", "1");
			response.sendRedirect("/ProgettoWorkX/user/annunci-accettati.jsp");
		} catch (NumberFormatException e) {
			System.out.println("Errore formato : "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
