package it.corso.accenture.entities;

import java.util.Objects;

public class Utente {

	private int idUtente;
	private String nome;
	private String cognome;

	public Utente() {
		super();
	}

	public Utente(int idUtente, String nome, String cognome) {
		super();
		this.idUtente = idUtente;
		this.nome = nome;
		this.cognome = cognome;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
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

	@Override
	public int hashCode() {
		return Objects.hash(idUtente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Utente))
			return false;
		Utente other = (Utente) obj;
		return idUtente == other.idUtente;
	}

	@Override
	public String toString() {
		return "Utente idUtente : " + idUtente + ", nome : " + nome + ", cognome : " + cognome;
	}

}
