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

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.ProfessioneDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class deleteprof
 */
@WebServlet("/admin/deleteprof")
public class deleteprof extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteprof() {
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
		String idprof = request.getParameter("profession");
		HttpSession session = request.getSession();
		UtenteDAO utentedao = new UtenteDAO();
		try {
			Collection<Utente> utenti = utentedao.doRetrieveAll("");
			boolean protetta = false;
			for(Utente u : utenti) {
				if(u.getProfessione().equals(idprof) && u.getRuolo().equals("1")) {
					protetta = true;
				}
			}
			if(protetta) {
				//se si eliminasse la professione verrebbe eliminato l'amministratore che ha chiamato tale funzione!!!
				session.setAttribute("delete", "Impossibile eliminare la professione di un amministratore!");
				response.sendRedirect(request.getHeader("referer"));
			} else {
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				TransazioneDAO transazionedao = new TransazioneDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				CredenzialiDAO credenzialidao = new CredenzialiDAO();
				Utente richiedente;
				Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				for(Utente u : utenti) {
					if(u.getProfessione().equals(idprof)) {
						credenzialidao.doDelete(Integer.parseInt(u.getCredenziali()));
					}
				}
				boolean completata;
				for(Annuncio a : annunci) {
					if(a.getProfessione().equals(idprof)) {
						richiedente = utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente()));
						completata = false;
						for(Transazione t : transazioni) {
							if(t.getAnnuncio().equals(a.getId()) && t.getStato().equals("1")) {
								completata = true;
							}
						}
						//Se non esiste una transazione sull'annuncio o se esiste una transazione rimborsa il richiedente
						if(!completata) {
							richiedente.setSaldo(richiedente.getSaldo()+a.getCosto());
							utentedao.doUpdate(richiedente);
						}
					}
				}
				professionedao.doDelete(Integer.parseInt(idprof));
				session.setAttribute("delete", "Professione eliminata!");
				response.sendRedirect(request.getHeader("referer"));
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+e.getMessage());
		}
	}
}
