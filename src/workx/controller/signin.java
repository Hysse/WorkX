package workx.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;
import workx.utilities.Md5Encoder;

/**
 * Servlet implementation class signin
 */
@WebServlet("/signin")
public class signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		CredenzialiDAO creddao = new CredenzialiDAO();
		//Controllo delle presenza di uno username uguale già creato in passato (Presente nel DB).
		boolean trovato = false;
		try {
			Collection<Credenziali> collezione = creddao.doRetrieveAll("");
			for(Credenziali c : collezione) {
				if(c.getUsername().equals(username)) {
					trovato = true;
				}
			}
			if(trovato)	{
				//Salvataggio dei valori dei campi del form di signin per ritornare a quello stato del form dopo la ridirezione
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				String password = request.getParameter("pass");
				String telefono = request.getParameter("numero");
				
				String err = "Username già esistente!";
				request.setAttribute("notifica", err);
				request.setAttribute("nome", nome);
				request.setAttribute("cognome", cognome);
				request.setAttribute("pass", password);
				request.setAttribute("numero", telefono);
				RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
				rd.forward(request, response);
			} else {
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				String password = request.getParameter("pass");
				String select = request.getParameter("selection");
				String telefono = request.getParameter("numero");
				UtenteDAO utentedao = new UtenteDAO();
				Md5Encoder encoder = new Md5Encoder();
				password = encoder.encode(password);
				Credenziali credenziali = new Credenziali();
				credenziali.setPassword(password);
				credenziali.setUsername(username);
				creddao.doSave(credenziali);
				//Bisogna cercare ora la nuova tupla delle Credenziali aggiunte per scoprire il suo id (autogenerato)
				collezione = creddao.doRetrieveAll("");
				String idcred = "";
				for(Credenziali c : collezione) {
					if(c.getUsername().equals(username)) {
						idcred = c.getId();
					}
				}
				Utente utente = new Utente();
				utente.setNome(nome);
				utente.setCognome(cognome);
				utente.setSaldo(3);
				utente.setRuolo("2");
				utente.setProfessione(select);
				utente.setCredenziali(idcred);
				utente.setTelefono(telefono);
				utentedao.doSave(utente);
				String note = "Registrazione effettuata con successo";
				request.setAttribute("notifica", note);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL : "+ e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Errore Md5 encoding : "+ e.getMessage());
		}
		
		
	}

}
