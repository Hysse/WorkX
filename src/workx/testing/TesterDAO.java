package workx.testing;

import java.sql.SQLException;
import java.util.Collection;

import workx.model.Annuncio;
import workx.model.AnnuncioDAO;
import workx.model.Desideri;
import workx.model.DesideriDAO;

public class TesterDAO {

	public static void main(String[] args) {
	AnnuncioDAO annunciodao = new AnnuncioDAO();
	try {
		Annuncio annuncio  = annunciodao.doRetrieveByKey(1);
		System.out.println("ID_Annuncio : "+ annuncio.getId());
		System.out.println("Costo : "+ annuncio.getCosto());
		System.out.println("Descrizione : "+ annuncio.getDescrizione());
		System.out.println("ID_Professione : "+ annuncio.getProfessione());
		System.out.println("ID_Utente : "+ annuncio.getUtente());
	} catch (SQLException e) {
		System.out.println("Errore DesideriDAO : " + e.getMessage());
	}
	}

}
