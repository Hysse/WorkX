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
import workx.model.ProfessioneDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class admintranscontentfiller
 */
@WebServlet("/admin/admintranscontentfiller")
public class admintranscontentfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admintranscontentfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		TransazioneDAO transazionedao = new TransazioneDAO();
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		UtenteDAO utentedao = new UtenteDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		String professione;
		out.println("<h3 id='divisore'>Transazioni WorkXchange</h3>");
		try {
			Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
			Annuncio annuncio;
			Utente richiedente,worker;
			for(Transazione t : transazioni) {
				annuncio = annunciodao.doRetrieveByKey(Integer.parseInt(t.getAnnuncio()));
				richiedente = utentedao.doRetrieveByKey(Integer.parseInt(annuncio.getUtente()));
				worker = utentedao.doRetrieveByKey(Integer.parseInt(t.getUtente()));
				professione = professionedao.doRetrieveByKey(Integer.parseInt(annuncio.getProfessione())).getNome();
				out.println("<div class='annuncio'>"
						+ "<div class='annuncioin'>"
						+ "<form name='delete' id='delete' action='/ProgettoWorkX/admin/admindeletetrans' method='post'>"
						+ "<input type='submit' id='dltbtn' value='X'>"
						+ "<input type='hidden' name='tranID' id='tranID' value='"+t.getId()+"'>"
						+ "</form>"
						+ "<h2>"+annuncio.getTitolo()+"</h2>"
						+ "<h2>Professione : "+professione+"</h2>"
						+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+richiedente.getId()+"'>"+richiedente.getNome()+" "+richiedente.getCognome()+"</a></h4>"
						+ "<h4>Pagamento : "+String.valueOf(annuncio.getCosto())+" Xtoken</h4>"
						+ "<p>"+annuncio.getDescrizione()+"</p>"
						+ "<h4>Data transazione : "+t.getData()+"</h4>"
						+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+worker.getId()+"'>"+worker.getNome()+" "+worker.getCognome()+"</a></h4>");
				if(t.getStato().equals("0")) {
					out.println("<h4>--Transazione pendente--</h4>");
				} else {
					out.println("<h4>--Transazione completata--</h4>");
				}
				out.println("</div>"
						+ "</div>");
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
