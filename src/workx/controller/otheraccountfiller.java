package workx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Professione;
import workx.model.ProfessioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class otheraccountfiller
 */
@WebServlet("/otheraccountfiller")
public class otheraccountfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public otheraccountfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		UtenteDAO utentedao = new UtenteDAO();
		Utente utente;
		try {
			utente = utentedao.doRetrieveByKey(Integer.parseInt(request.getHeader("otheraccount")));
			String ruolo = "";
			if(utente.getRuolo().equals("1"))
				ruolo = "Amministratore";
			else
				ruolo = "Worker";
			ProfessioneDAO professionedao = new ProfessioneDAO();
			Professione professione = professionedao.doRetrieveByKey(Integer.parseInt(utente.getProfessione()));
			out.println("<h3 id='divisore'>Info account</h3>");
			out.println("<div class='infoaccountout'>"
					+ "<div class='infoaccountin'>"
					+ "<h3>Nome : "+utente.getNome()+"<h3>"
					+ "<h3>Cognome : "+utente.getCognome()+"</h3>"
					+ "<h4>Professione : "+professione.getNome()+"</h4>"
					+ "<p>"+professione.getDescrizione()+"</p>"
					+ "<h4>Ruolo : "+ruolo+"</h4>"
					+ "<h4>Saldo : "+Float.toString(utente.getSaldo())+" Xtokens</h4>"
					+ "<h4>Telefono : "+utente.getTelefono()+"</h4>"
					+ "</div>"
					+ "</div>");
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
