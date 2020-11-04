package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public class Query6 {

	private String nomeUtente;
	private String cognomeUtente;
	private String titoloLibro;
	private int giorniPrestito;

	public Query6(String nomeUtente, String cognomeUtente, String titoloLibro, int giorniPrestito) {
		super();
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.titoloLibro = titoloLibro;
		this.giorniPrestito = giorniPrestito;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getCognomeUtente() {
		return cognomeUtente;
	}

	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}

	public String getTitoloLibro() {
		return titoloLibro;
	}

	public void setTitoloLibro(String titoloLibro) {
		this.titoloLibro = titoloLibro;
	}

	public int getGiorniPrestito() {
		return giorniPrestito;
	}

	public void setGiorniPrestito(int giorniPrestito) {
		this.giorniPrestito = giorniPrestito;
	}

	@Override
	public String toString() {
		return "nomeUtente : " + nomeUtente + ", cognomeUtente : " + cognomeUtente + ", titoloLibro : " + titoloLibro
				+ ", giorniPrestito : " + giorniPrestito;
	}

	// 6. Individua tutti i prestiti la cui durata supera i 15gg.
	public static List<Query6> listaQuery6() {
		DBConnection dbConn = new DBConnection();

		String sql = "" +
				"select u.nome,u.cognome,l.titolo, datediff(ifnull(p.data_restituito,sysdate()) , p.data_prestito) as durata_prestito_giorni "
				+
				"from " +
				"utenti u " +
				"join prestiti p using(id_utente) " +
				"join libri l using(id_libro) " +
				"having durata_prestito_giorni > 15;";

		ResultSet rs = dbConn.queryStatement(sql);
		List<Query6> listaQuery6 = ricavaQuery6DaResultSet(rs);

		dbConn.close();
		return listaQuery6;
	}

	private static List<Query6> ricavaQuery6DaResultSet(ResultSet rs) {
		List<Query6> listaQuery6 = new ArrayList<Query6>();
		try {
			while (rs.next()) {

				String nomeUtente = rs.getString("nome");
				String cognomeUtente = rs.getString("cognome");
				String titoloLibro = rs.getString("titolo");
				int giorniPrestito = rs.getInt("durata_prestito_giorni");

				Query6 q = new Query6(nomeUtente, cognomeUtente, titoloLibro, giorniPrestito);
				listaQuery6.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaQuery6;
	}

}
