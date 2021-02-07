package workx.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import org.apache.tomcat.util.security.MD5Encoder;

import workx.model.Credenziali;
import workx.model.CredenzialiDAO;
import workx.model.Utente;
import workx.model.UtenteDAO;
import workx.utilities.Md5Encoder;


@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		Md5Encoder encoder = new Md5Encoder();
		try {
			password = encoder.encode(password);
		} catch (NoSuchAlgorithmException e1) {
			System.out.println("Errore md5 : "+ e1.getMessage());
		}
		CredenzialiDAO credenziali = new CredenzialiDAO();
		try {
			boolean trovato = false;
			Collection<Credenziali> collezione = credenziali.doRetrieveAll("");
			Credenziali loggato = null;
			for(Credenziali c : collezione) {
				if(c.getUsername().equals(username) && c.getPassword().equals(password)) {
					trovato = true;
					loggato = c;
				}
			}
			if(!trovato) {
				String err="Errore : username/password errati";
				request.setAttribute("notifica", err);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			} else {
				UtenteDAO utentedao = new UtenteDAO();
				Collection<Utente> utenti = utentedao.doRetrieveAll("");
				for(Utente c : utenti) {
					if(c.getCredenziali().equals(loggato.getId())) {
						HttpSession session = request.getSession();
						session.setAttribute("account", c);
					}
				}
				response.sendRedirect("home.jsp");
			}
		} catch (SQLException e) {
			System.out.println("Errore retrieveAll : "+ e.getMessage());
		}
	}

}
