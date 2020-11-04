package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public class Query3 {

	private String nomeUtente;
	private String cognomeUtente;
	private String titoloLibro;

	public Query3(String nomeUtente, String cognomeUtente, String titoloLibro) {
		super();
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.titoloLibro = titoloLibro;
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

	@Override
	public String toString() {
		return "nomeUtente : " + nomeUtente + ", cognomeUtente : " + cognomeUtente + ", titoloLibro : " + titoloLibro;
	}

	// 3. Individua tutti i possessori (tra quelli inseriti) dei libri non ancora
	// rientrati e il titolo degli stessi.
	public static List<Query3> listaQuery3() {
		DBConnection dbConn = new DBConnection();

		String sql = "" +
				"select u.nome,u.cognome,l.titolo " +
				"from " +
				"utenti u " +
				"join prestiti p using(id_utente) " +
				"join libri l using(id_libro) " +
				"where p.data_restituito is null;";

		ResultSet rs = dbConn.queryStatement(sql);
		List<Query3> listaQuery3 = ricavaQuery3DaResultSet(rs);

		dbConn.close();
		return listaQuery3;
	}

	private static List<Query3> ricavaQuery3DaResultSet(ResultSet rs) {
		List<Query3> listaQuery3 = new ArrayList<Query3>();
		try {
			while (rs.next()) {

				String nomeUtente = rs.getString("nome");
				String cognomeUtente = rs.getString("cognome");
				String titoloLibro = rs.getString("titolo");

				Query3 q = new Query3(nomeUtente, cognomeUtente, titoloLibro);
				listaQuery3.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaQuery3;
	}

}
