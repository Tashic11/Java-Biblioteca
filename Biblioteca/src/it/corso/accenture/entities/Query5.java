package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public class Query5 {

	private String titoloLibro;
	private String autoreLibro;
	private int numeroPrestiti;

	public Query5(String titoloLibro, String autoreLibro, int numeroPrestiti) {
		super();
		this.titoloLibro = titoloLibro;
		this.autoreLibro = autoreLibro;
		this.numeroPrestiti = numeroPrestiti;
	}

	public String getTitoloLibro() {
		return titoloLibro;
	}

	public void setTitoloLibro(String titoloLibro) {
		this.titoloLibro = titoloLibro;
	}

	public String getAutoreLibro() {
		return autoreLibro;
	}

	public void setAutoreLibro(String autoreLibro) {
		this.autoreLibro = autoreLibro;
	}

	public int getNumeroPrestiti() {
		return numeroPrestiti;
	}

	public void setNumeroPrestiti(int numeroPrestiti) {
		this.numeroPrestiti = numeroPrestiti;
	}

	@Override
	public String toString() {
		return "titoloLibro : " + titoloLibro + ", autoreLibro : " + autoreLibro + ", numeroPrestiti : "
				+ numeroPrestiti;
	}

	// 5. Fai la classifica dei libri maggiormente prestati.
	public static List<Query5> listaQuery5() {
		DBConnection dbConn = new DBConnection();

		String sql = "" +
				"select l.titolo,l.autore,count(id_prestito) as num_prestiti " +
				"from " +
				"libri l "+
				"left outer join prestiti p using(id_libro) "+
				"group by l.titolo,l.autore "+
				"order by num_prestiti desc;";

		ResultSet rs = dbConn.queryStatement(sql);
		List<Query5> listaQuery5 = ricavaQuery5DaResultSet(rs);

		dbConn.close();
		return listaQuery5;
	}

	private static List<Query5> ricavaQuery5DaResultSet(ResultSet rs) {
		List<Query5> listaQuery5 = new ArrayList<Query5>();
		try {
			while (rs.next()) {

				String titoloLibro = rs.getString("titolo");
				String autoreLibro = rs.getString("autore");
				int numeroPrestiti = rs.getInt("num_prestiti");

				Query5 q = new Query5(titoloLibro, autoreLibro, numeroPrestiti);
				listaQuery5.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaQuery5;
	}

}
