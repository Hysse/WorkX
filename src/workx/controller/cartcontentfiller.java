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

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Desideri;
import workx.model.DesideriDAO;
import workx.model.ProfessioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class cartcontentfiller
 */
@WebServlet("/user/cartcontentfiller")
public class cartcontentfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cartcontentfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Utente utente = (Utente)session.getAttribute("account");
		AnnuncioDAO annuncioDAO = new AnnuncioDAO();
		DesideriDAO desideridao = new DesideriDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		UtenteDAO utentedao = new UtenteDAO();
		out.println("<h3 id='divisore'>Lista desideri</h3>");
		String titolo = "", costo = "", descrizione = "", professione = "", nome ="", cognome = "";
		Annuncio annuncio;
		Utente u;
		try {
			Collection<Desideri> desideri = desideridao.doRetrieveAll("");
			for(Desideri d : desideri) {
				if(d.getUtente().equals(utente.getId())) {
					annuncio = annuncioDAO.doRetrieveByKey(Integer.parseInt(d.getAnnuncio()));
					titolo = annuncio.getTitolo();
					descrizione = annuncio.getDescrizione();
					costo = String.valueOf(annuncio.getCosto());
					professione = professionedao.doRetrieveByKey(Integer.parseInt(annuncio.getProfessione())).getNome();
					u = utentedao.doRetrieveByKey(Integer.parseInt(annuncio.getUtente()));
					nome = u.getNome();
					cognome = u.getCognome();
					out.println("<div class='annuncio'>"
							+ "<div class='annuncioin'>"
							+ "<form name='delete' id='delete' action='/ProgettoWorkX/user/deletedesiderio' method='post'>"
							+ "<input type='submit' id='dltbtn' value='X'>"
							+ "<input type='hidden' name='desID' id='desID' value='"+d.getId()+"'>"
							+ "</form>"
							+ "<h2>"+titolo+"</h2>"
							+ "<h2>Professione : "+professione+"</h2>"
							+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+u.getId()+"'>"+nome+" "+cognome+"</a></h4>"
							+ "<h4>Pagamento : "+costo+" Xtoken</h4>"
							+ "<p>"+descrizione+"</p>"
							+ "<form name='cartaccept' id='cartaccept' action='/ProgettoWorkX/user/acceptcart' method='post'>"
							+ "<input type='submit' class='button' id='accept' value='Accetta annuncio'>"
							+ "<input type='hidden' name='desID' id='desID' value='"+d.getId()+"'>"
							+ "</form>"
							+ "</div>"
							+ "</div>");
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
