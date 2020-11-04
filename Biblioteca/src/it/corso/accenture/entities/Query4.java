package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public class Query4 {

	private String titoloLibro;
	private String autoreLibro;
	private Calendar dataPrestito;

	public Query4(String titoloLibro, String autoreLibro,
			Calendar dataPrestito) {
		super();
		this.titoloLibro = titoloLibro;
		this.autoreLibro = autoreLibro;
		this.dataPrestito = dataPrestito;
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

	public Calendar getDataPrestito() {
		return dataPrestito;
	}

	public void setDataPrestito(Calendar dataPrestito) {
		this.dataPrestito = dataPrestito;
	}

	@Override
	public String toString() {
		return "titoloLibro : " + titoloLibro
				+ ", autoreLibro : " + autoreLibro + ", dataPrestito : " + dataPrestitoSemplice();
	}

	public String dataPrestitoSemplice() {
		return new SimpleDateFormat("dd/MM/yyyy").format(dataPrestito.getTime());
	}

	// 4. Dare lo storico dei libri chiesti in prestito da un utente (a piacere tra
	// quelli inseriti) indicando il periodo di riferimento.
	public static List<Query4> listaQuery4(int idUtente,Calendar periodoInizio,Calendar periodoFine) {
		DBConnection dbConn = new DBConnection();

		String sql = "" +
				"select u.nome,u.cognome,l.titolo,l.autore,p.data_prestito " +
				"from " +
				"utenti u " +
				"join prestiti p using(id_utente) " +
				"join libri l using(id_libro) " +
				"where " +
				"u.id_utente=? " + 
				"and p.data_prestito between ? and ?;";

		ResultSet rs = dbConn.queryPrepStatement(sql,new Object[] {idUtente,periodoInizio,periodoFine});
		List<Query4> listaQuery4 = ricavaQuery4DaResultSet(rs);

		dbConn.close();
		return listaQuery4;
	}

	private static List<Query4> ricavaQuery4DaResultSet(ResultSet rs) {
		List<Query4> listaQuery4 = new ArrayList<Query4>();
		try {
			while (rs.next()) {

				String titoloLibro = rs.getString("titolo");
				String autoreLibro = rs.getString("autore");

				Calendar dataPrestito = new GregorianCalendar();
				dataPrestito.setTime(rs.getDate("data_prestito"));

				Query4 q = new Query4(titoloLibro, autoreLibro, dataPrestito);
				listaQuery4.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaQuery4;
	}
}
