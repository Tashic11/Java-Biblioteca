package it.corso.accenture.entities;

import java.util.Objects;

public class Libro {

	private int idLibro;
	private String titolo;
	private String autore;
	private int annoUscita;

	public Libro() {
		super();
	}

	public Libro(int idLibro, String titolo, String autore, int annoUscita) {
		super();
		this.idLibro = idLibro;
		this.titolo = titolo;
		this.autore = autore;
		this.annoUscita = annoUscita;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public int getAnnoUscita() {
		return annoUscita;
	}

	public void setAnnoUscita(int annoUscita) {
		this.annoUscita = annoUscita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Libro))
			return false;
		Libro other = (Libro) obj;
		return titolo.equalsIgnoreCase(other.titolo);
	}

	@Override
	public String toString() {
		return "Libro idLibro : " + idLibro + ", titolo : " + titolo + ", autore : " + autore + ", annoUscita : "
				+ annoUscita;
	}

}
