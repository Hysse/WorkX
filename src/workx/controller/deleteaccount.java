package workx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class deleteaccount
 */
@WebServlet("/admin/deleteaccount")
public class deleteaccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteaccount() {
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
		CredenzialiDAO credenzialidao = new CredenzialiDAO();
		try {
			Utente user = utentedao.doRetrieveByKey(Integer.parseInt(request.getParameter("userID")));
			credenzialidao.doDelete(Integer.parseInt(user.getCredenziali()));
			utentedao.doDelete(Integer.parseInt(user.getId()));
			response.sendRedirect(request.getHeader("referer"));
		} catch (NumberFormatException | SQLException e) {
			System.out.println("Errore formato o SQL : "+e.getMessage());
		}
	}

}
