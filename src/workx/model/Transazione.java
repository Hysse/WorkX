package workx.model;

public class Transazione {
	String id,utente,annuncio,data,stato;

	public Transazione() {
		id = null;
		utente = null;
		annuncio = null;
		stato = null;
		data = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getAnnuncio() {
		return annuncio;
	}

	public void setAnnuncio(String annuncio) {
		this.annuncio = annuncio;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
