package workx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class admindeleteannuncio
 */
@WebServlet("/admin/admindeleteannuncio")
public class admindeleteannuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admindeleteannuncio() {
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
		String idann = request.getParameter("annID");
		AnnuncioDAO annunciodao = new AnnuncioDAO();
		UtenteDAO utentedao = new UtenteDAO();
		TransazioneDAO transazionedao = new TransazioneDAO();
		try {
			Annuncio annuncio = annunciodao.doRetrieveByKey(Integer.parseInt(idann));
			Utente richiedente = utentedao.doRetrieveByKey(Integer.parseInt(annuncio.getUtente()));
			Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
			boolean completata = false;
			for(Transazione t : transazioni) {
				if(t.getAnnuncio().equals(annuncio.getId()) && t.getStato().equals("1")) {
					completata = true;
				}
			}
			//Se non esiste una transazione sull'annuncio o se esiste una transazione parziale elimina l'annuncio ma rimborsa il richiedente altrimenti elimina semplicemente l'annuncio
			if(!completata) {
				richiedente.setSaldo(richiedente.getSaldo()+annuncio.getCosto());
				utentedao.doUpdate(richiedente);
			}
			annunciodao.doDelete(Integer.parseInt(annuncio.getId()));
			response.sendRedirect("/ProgettoWorkX/admin/admin-annunci.jsp");
		} catch (NumberFormatException e) {
			System.out.println("Errore formato : "+e.getMessage());
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}

}
