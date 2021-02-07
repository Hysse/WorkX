package workx.model;

public class Utente {
	String id,nome,cognome,ruolo,professione,credenziali,telefono;
	float saldo;
	
	public Utente() {
		id = null;
		nome = null;
		cognome = null;
		ruolo = null;
		professione = null;
		credenziali = null;
		telefono = null;
		saldo = 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getProfessione() {
		return professione;
	}
	public void setProfessione(String professione) {
		this.professione = professione;
	}
	public String getCredenziali() {
		return credenziali;
	}
	public void setCredenziali(String credenziali) {
		this.credenziali = credenziali;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
