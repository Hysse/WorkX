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
import javax.servlet.http.HttpSession;

import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Professione;
import workx.model.ProfessioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class adminaccountcontentfiller
 */
@WebServlet("/admin/adminaccountcontentfiller")
public class adminaccountcontentfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminaccountcontentfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Utente admin = (Utente)session.getAttribute("account");
		PrintWriter out = response.getWriter();
		UtenteDAO utentedao = new UtenteDAO();
		CredenzialiDAO credenzialidao = new CredenzialiDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		String ruolo,username;
		Professione professione;
		out.println("<h3 id='divisore'>Gestione account</h3>");
		try {
			Collection<Utente> utenti = utentedao.doRetrieveAll("");
			for(Utente u : utenti) {
				if(!u.getId().equals(admin.getId())) {
					professione = professionedao.doRetrieveByKey(Integer.parseInt(u.getProfessione()));
					if(u.getRuolo().equals("1"))
						ruolo = "Amministratore";
					else
						ruolo ="Worker";
					username = credenzialidao.doRetrieveByKey(Integer.parseInt(u.getCredenziali())).getUsername();
					out.println( "<div class='infoaccountin'>");
					if(!u.getRuolo().equals("1")) {
						out.println("<form name='delete' id='delete' action='/ProgettoWorkX/admin/deleteaccount' method='post'>"
								+ "<input type='submit' id='dltbtn' value='X'>"
								+ "<input type='hidden' name='userID' id='userID' value='"+u.getId()+"'>"
								+ "</form>");
					}
					out.println("<h3>Nome : "+u.getNome()+"<h3>"
							+ "<h3>Cognome : "+u.getCognome()+"</h3>"
							+ "<h4>Username : "+username+"</h4>"
							+ "<h4>Professione : "+professione.getNome()+"</h4>"
							+ "<p>"+professione.getDescrizione()+"</p>"
							+ "<h4>Ruolo : "+ruolo+"</h4>"
							+ "<h4>Saldo : "+Float.toString(u.getSaldo())+" Xtokens</h4>"
							+ "<h4>Telefono : "+u.getTelefono()+"</h4>");
					if(u.getRuolo().equals("1")) {
						out.println("<form name='downgrade' id='downgrade' action='/ProgettoWorkX/admin/downgrade' method='post'>"
								+ "<input type='submit' class='button' id='declassa' value='Rimuovi privilegi'>"
								+ "<input type='hidden' name='userID' id='userID' value='"+u.getId()+"'>"
								+ "</form>");
					} else {
						out.println("<form name='upgrade' id='upgrade' action='/ProgettoWorkX/admin/upgrade' method='post'>"
								+ "<input type='submit' class='button' id='eleva' value='Conferisci privilegi'>"
								+ "<input type='hidden' name='userID' id='userID' value='"+u.getId()+"'>"
								+ "</form>");
					}
					out.println("</div>");
						
				}
			}
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
