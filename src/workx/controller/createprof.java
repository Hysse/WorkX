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

import workx.model.Professione;
import workx.model.ProfessioneDAO;

/**
 * Servlet implementation class createprof
 */
@WebServlet("/admin/createprof")
public class createprof extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createprof() {
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
		String nome = request.getParameter("nome");
		HttpSession session = request.getSession();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		try {
			Collection<Professione> professioni = professionedao.doRetrieveAll("");
			boolean trovato = false;
			for(Professione p : professioni) {
				if(p.getNome().equals(nome)) {
					trovato = true;
				}
			}
			if(trovato) {
				session.setAttribute("professione", "Professione già esistente!");
				response.sendRedirect(request.getHeader("referer"));
			} else {
				String descrizione = request.getParameter("desc");
				Professione professione = new Professione();
				professione.setNome(nome);
				professione.setDescrizione(descrizione);
				professionedao.doSave(professione);
				session.setAttribute("professione", "Professione aggiunta!");
				response.sendRedirect(request.getHeader("referer"));
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
