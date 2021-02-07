package workx.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class upgrade
 */
@WebServlet("/admin/upgrade")
public class upgrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public upgrade() {
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
		UtenteDAO utentedao = new UtenteDAO();
		try {
			Utente utente = utentedao.doRetrieveByKey(Integer.parseInt(request.getParameter("userID")));
			utente.setRuolo("1");
			utentedao.doUpdate(utente);
			response.sendRedirect("/ProgettoWorkX/admin/admin-account.jsp");
		} catch (NumberFormatException | SQLException e) {
			System.out.println("Errore formato o SQL : "+e.getMessage());
		}
	}

}
