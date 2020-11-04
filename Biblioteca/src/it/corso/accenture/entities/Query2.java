package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public class Query2 {

	private String nomeUtente;
	private String cognomeUtente;
	private int libriLetti;

	public Query2(String nomeUtente, String cognomeUtente, int libriLetti) {
		super();
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.libriLetti = libriLetti;
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

	public int getLibriLetti() {
		return libriLetti;
	}

	public void setLibriLetti(int libriLetti) {
		this.libriLetti = libriLetti;
	}

	@Override
	public String toString() {
		return "nomeUtente : " + nomeUtente + ", cognomeUtente : " + cognomeUtente + ", libriLetti : " + libriLetti;
	}

	// 2. Individua i primi tre lettori (tra quelli inseriti) che hanno letto più
	// libri.
	public static List<Query2> listaQuery2() {
		DBConnection dbConn = new DBConnection();

		String sql = "" +
				"select u.nome,u.cognome,count(distinct p.id_libro) as libri_letti " +
				"from " +
				"utenti u " +
				"join prestiti p using(id_utente) " +
				"group by u.nome,u.cognome "+
				"order by libri_letti desc "+
				"limit 3;";

		ResultSet rs = dbConn.queryStatement(sql);
		List<Query2> listaQuery2 = ricavaQuery2DaResultSet(rs);

		dbConn.close();
		return listaQuery2;
	}

	private static List<Query2> ricavaQuery2DaResultSet(ResultSet rs) {
		List<Query2> listaQuery2 = new ArrayList<Query2>();
		try {
			while (rs.next()) {

				String nomeUtente = rs.getString("nome");
				String cognomeUtente = rs.getString("cognome");
				int libriLetti = rs.getInt("libri_letti");

				Query2 q = new Query2(nomeUtente, cognomeUtente, libriLetti);
				listaQuery2.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaQuery2;
	}

}
