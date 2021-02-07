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

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.ProfessioneDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class adminannuncicontentfiller
 */
@WebServlet("/admin/adminannuncicontentfiller")
public class adminannuncicontentfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminannuncicontentfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		ProfessioneDAO professionedao = new ProfessioneDAO();
		TransazioneDAO transazionidao = new TransazioneDAO();
		UtenteDAO utentedao = new UtenteDAO();
		String nome,cognome,titolo,costo,descrizione,professione;
		out.println("<h3 id='divisore'>Annunci WorkXchange</h3>");
		try {
			Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
			Collection<Transazione> transazioni = transazionidao.doRetrieveAll("");
			for(Annuncio a : annunci) {
				nome = utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente())).getNome();
				cognome = utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente())).getCognome();
				titolo = a.getTitolo();
				descrizione = a.getDescrizione();
				costo = String.valueOf(a.getCosto());
				professione = professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome();
				out.println("<div class='annuncio'>"
						+ "<div class='annuncioin'>"
						+ "<form name='delete' id='delete' action='/ProgettoWorkX/admin/admindeleteannuncio' method='post'>"
						+ "<input type='submit' id='dltbtn' value='X'>"
						+ "<input type='hidden' name='annID' id='annID' value='"+a.getId()+"'>"
						+ "</form>"
						+ "<h2>"+titolo+"</h2>"
						+ "<h2>Professione : "+professione+"</h2>"
						+ "<h4>Richiedente : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+a.getUtente()+"'>"+nome+" "+cognome+"</a></h4>"
						+ "<h4>Pagamento : "+costo+" Xtoken</h4>"
						+ "<p>"+descrizione+"</p>");
						Transazione trans = null;
						for(Transazione t : transazioni) {
							if(t.getAnnuncio().equals(a.getId()))
								trans = t;
						}
						if(trans != null) {
							Utente u = utentedao.doRetrieveByKey(Integer.parseInt(trans.getUtente()));
							out.println("<h4>Data transazione : "+trans.getData()+"</h4>"
									+ "<h4> XWorker transazione : <a href='/ProgettoWorkX/profilo-altrui.jsp?other="+trans.getUtente()+"'>"+u.getNome()+" "+u.getCognome()+"</a></h4>");
							if(trans.getStato().equals("0")) {
								out.println("<h4>--Transazione pendente--</h4>");
								}
							else
							{
								out.println("<h4>--Transazione completata--</h4>");
							}
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
