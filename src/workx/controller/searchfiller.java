package workx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Desideri;
import workx.model.DesideriDAO;
import workx.model.Professione;
import workx.model.ProfessioneDAO;
import workx.model.Transazione;
import workx.model.TransazioneDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;

/**
 * Servlet implementation class searchfiller
 */
@WebServlet("/searchfiller")
public class searchfiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchfiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			String input = request.getParameter("search").toLowerCase();
			String type = request.getParameter("subj");
			Utente utente = (Utente)session.getAttribute("account");
			if(utente == null) {
				utente = new Utente();
				utente.setId("guest");
			}
			if(type.equals("home")) {										//SearchBar della Home
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				TransazioneDAO transazionedao = new TransazioneDAO();
				UtenteDAO utentedao = new UtenteDAO();
				Collection<Utente> utenti = new ArrayList<Utente>();
				Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
				Collection<Annuncio> annuncitrovati = new ArrayList<Annuncio>();
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				for(Annuncio a : annunci) {
					a.setProfessione(professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome());
					boolean nonmostrare = false;
					for(Transazione t : transazioni) {
						if(t.getAnnuncio().equals(a.getId())) {
							nonmostrare = true;
						}
					}
					if(a.getUtente().equals(utente.getId()))
						nonmostrare = true;
					if(!nonmostrare) { 
						if((input == null) || (a.getTitolo().toLowerCase().contains(input)) || (a.getProfessione().toLowerCase().contains(input))) {
							utenti.add(utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente())));
							annuncitrovati.add(a);
						}
					}
				}
				Gson gson = new Gson();
				String jsonannunci = gson.toJson(annuncitrovati);
				String jsonutenti = gson.toJson(utenti);
				out.println("{ \"annunci\":");
				out.println(jsonannunci+",");
				out.println("\"utenti\":");
				out.println(jsonutenti);
				out.println("}");
			}
			if(type.equals("account")) {          							//SearchBar dell'account
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				TransazioneDAO transazionedao = new TransazioneDAO();
				CredenzialiDAO creddao = new CredenzialiDAO();
				UtenteDAO utentedao = new UtenteDAO();
				Collection<Utente> utenti = utentedao.doRetrieveAll("");
				Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
				Collection<Annuncio> annuncitrovati = new ArrayList<Annuncio>();
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				for(Annuncio a : annunci) {
					a.setProfessione(professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome());
					if(a.getUtente().equals(utente.getId())) {
						if((input == null) || (a.getTitolo().toLowerCase().contains(input)) || (a.getProfessione().toLowerCase().contains(input))) {
							annuncitrovati.add(a);
						}
					}
				}
				Professione professione = professionedao.doRetrieveByKey(Integer.parseInt(utente.getProfessione()));
				String credutente = creddao.doRetrieveByKey(Integer.parseInt(utente.getCredenziali())).getUsername();
				Gson gson = new Gson();
				String jsonaccount = gson.toJson(utente);
				String jsoncredaccount = gson.toJson(credutente);
				String jsonprof = gson.toJson(professione);
				String jsonannunci = gson.toJson(annuncitrovati);
				String jsontrans = gson.toJson(transazioni);
				String jsonutenti = gson.toJson(utenti);
				out.println("{ \"annunci\":");
				out.println(jsonannunci+",");
				out.println("\"account\":");
				out.println(jsonaccount+",");
				out.println("\"credaccount\":");
				out.println(jsoncredaccount+",");
				out.println("\"prof\":");
				out.println(jsonprof+",");
				out.println("\"utenti\":");
				out.println(jsonutenti+",");
				out.println("\"transazioni\":");
				out.println(jsontrans);
				out.println("}");
			}
			if(type.equals("accettati")) {
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				TransazioneDAO transazionedao = new TransazioneDAO();
				UtenteDAO utentedao = new UtenteDAO();
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				Collection<Utente> richiedenti = new ArrayList<Utente>();
				Collection<Annuncio> annuncitrovati = new ArrayList<Annuncio>();
				Collection<Transazione> transazionitrovate = new ArrayList<Transazione>();
				Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
				for(Annuncio a : annunci) {
					a.setProfessione(professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome());
					for(Transazione t : transazioni) {
						if(t.getAnnuncio().equals(a.getId()) && t.getUtente().equals(utente.getId())) {
							if((input == null) || (a.getTitolo().toLowerCase().contains(input)) || (a.getProfessione().toLowerCase().contains(input))) {
								annuncitrovati.add(a);
								transazionitrovate.add(t);
								richiedenti.add(utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente())));
							}
						}
					}
				}
				Gson gson = new Gson();
				String jsonannunci = gson.toJson(annuncitrovati);
				String jsontrans = gson.toJson(transazionitrovate);
				String jsonutenti = gson.toJson(richiedenti);
				out.println("{ \"annunci\":");
				out.println(jsonannunci+",");
				out.println("\"richiedenti\":");
				out.println(jsonutenti+",");
				out.println("\"transazioni\":");
				out.println(jsontrans);
				out.println("}");
			}
			if(type.equals("cart")) {
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				DesideriDAO desideridao = new DesideriDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				UtenteDAO utentedao = new UtenteDAO();
				Collection<Desideri> desideri = desideridao.doRetrieveAll("");
				Collection<Utente> richiedenti = new ArrayList<Utente>();
				Collection<Annuncio> annuncitrovati = new ArrayList<Annuncio>();
				Collection<Desideri> desideritrovati = new ArrayList<Desideri>();
				Annuncio a;
				for(Desideri d : desideri) {
					if(d.getUtente().equals(utente.getId())) {
						a = annunciodao.doRetrieveByKey(Integer.parseInt(d.getAnnuncio()));
						a.setProfessione(professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome());
						if((input == null) || (a.getTitolo().toLowerCase().contains(input)) || (a.getProfessione().toLowerCase().contains(input))) {
							annuncitrovati.add(a);
							richiedenti.add(utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente())));
							desideritrovati.add(d);
						}
					}
				}
				Gson gson = new Gson();
				String jsonannunci = gson.toJson(annuncitrovati);
				String jsonutenti = gson.toJson(richiedenti);
				String jsondes = gson.toJson(desideritrovati);
				out.println("{ \"annunci\":");
				out.println(jsonannunci+",");
				out.println("\"richiedenti\":");
				out.println(jsonutenti+",");
				out.println("\"desideri\":");
				out.println(jsondes);
				out.println("}");
			}
			if(type.equals("adminaccount")) {
				CredenzialiDAO credenzialidao = new CredenzialiDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				UtenteDAO utentedao = new UtenteDAO();
				Collection<Utente> utenti = utentedao.doRetrieveAll("");
				Collection<Utente> utentitrovati = new ArrayList<Utente>();
				Collection<Professione> professionitrovate = new ArrayList<Professione>();
				Collection<Credenziali> credenziali = new ArrayList<Credenziali>();
				for(Utente u : utenti) {
					if(!u.getId().equals(utente.getId())) {
						if((input == null) || (u.getNome().toLowerCase().contains(input)) || (u.getCognome().toLowerCase().contains(input)) || (u.getProfessione().toLowerCase().contains(input)) || (u.getTelefono().toLowerCase().contains(input)) || (credenzialidao.doRetrieveByKey(Integer.parseInt(u.getCredenziali())).getUsername().toLowerCase().contains(input)) || (professionedao.doRetrieveByKey(Integer.parseInt(u.getProfessione())).getNome().toLowerCase().contains(input))) {
							utentitrovati.add(u);
							credenziali.add(credenzialidao.doRetrieveByKey(Integer.parseInt(u.getCredenziali())));
							professionitrovate.add(professionedao.doRetrieveByKey(Integer.parseInt(u.getProfessione())));
						}
					}
				}
				Gson gson = new Gson();
				String jsonutenti = gson.toJson(utentitrovati);
				String jsonprof = gson.toJson(professionitrovate);
				String jsoncred = gson.toJson(credenziali);
				out.println("{ \"utenti\":");
				out.println(jsonutenti+",");
				out.println("\"professioni\":");
				out.println(jsonprof+",");
				out.println("\"credenziali\":");
				out.println(jsoncred);
				out.println("}");
			}
			if(type.equals("adminannunci")) {
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				TransazioneDAO transazionedao = new TransazioneDAO();
				UtenteDAO utentedao = new UtenteDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				Collection<Utente> richiedenti = new ArrayList<Utente>();
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				Collection<Utente> utentitrans = new ArrayList<Utente>();
				Collection<Annuncio> annunci = annunciodao.doRetrieveAll("");
				Collection<Annuncio> annuncitrovati = new ArrayList<Annuncio>();
				for(Annuncio a : annunci) {
					a.setProfessione(professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome());
					if((input == null) || (a.getTitolo().toLowerCase().contains(input)) || (a.getProfessione().toLowerCase().contains(input))) {
						annuncitrovati.add(a);
						richiedenti.add(utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente())));
					}
				}
				for(Transazione t : transazioni) {
					utentitrans.add(utentedao.doRetrieveByKey(Integer.parseInt(t.getUtente())));
				}
				Gson gson = new Gson();
				String jsonrichiedenti = gson.toJson(richiedenti);
				String jsonannunci = gson.toJson(annuncitrovati);
				String jsontrans = gson.toJson(transazioni);
				String jsonutentitrans = gson.toJson(utentitrans);
				out.println("{ \"annunci\":");
				out.println(jsonannunci+",");
				out.println("\"transazioni\":");
				out.println(jsontrans+",");
				out.println("\"utentitrans\":");
				out.println(jsonutentitrans+",");
				out.println("\"richiedenti\":");
				out.println(jsonrichiedenti);
				out.println("}");
			}
			if(type.equals("admintrans")) {
				TransazioneDAO transazionedao = new TransazioneDAO();
				UtenteDAO utentedao = new UtenteDAO();
				ProfessioneDAO professionedao = new ProfessioneDAO();
				AnnuncioDAO annunciodao = new AnnuncioDAO();
				Collection<Annuncio> annuncitrovati = new ArrayList<Annuncio>();
				Collection<Transazione> transtrovate = new ArrayList<Transazione>();
				Collection<Transazione> transazioni = transazionedao.doRetrieveAll("");
				Collection<Utente> workers = new ArrayList<Utente>();
				Collection<Utente> richiedenti = new ArrayList<Utente>();
				Annuncio a;
				Utente w,r;
				for(Transazione t : transazioni) {
					a = annunciodao.doRetrieveByKey(Integer.parseInt(t.getAnnuncio()));
					a.setProfessione(professionedao.doRetrieveByKey(Integer.parseInt(a.getProfessione())).getNome());
					w = utentedao.doRetrieveByKey(Integer.parseInt(t.getUtente()));
					r = utentedao.doRetrieveByKey(Integer.parseInt(a.getUtente()));
					if((input == null) || (a.getTitolo().toLowerCase().contains(input)) || (a.getProfessione().toLowerCase().contains(input))) {
						annuncitrovati.add(a);
						transtrovate.add(t);
						workers.add(w);
						richiedenti.add(r);
					}
				}
				Gson gson = new Gson();
				String jsonannunci = gson.toJson(annuncitrovati);
				String jsontrans = gson.toJson(transtrovate);
				String jsonworkers = gson.toJson(workers);
				String jsonrichiedenti = gson.toJson(richiedenti);
				out.println("{ \"annunci\":");
				out.println(jsonannunci+",");
				out.println("\"workers\":");
				out.println(jsonworkers+",");
				out.println("\"richiedenti\":");
				out.println(jsonrichiedenti+",");
				out.println("\"transazioni\":");
				out.println(jsontrans);
				out.println("}");
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
