package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public class Query1 {

	private String titoloLibro;
	private String autoreLibro;
	private Calendar dataPrestito;

	public Query1(String titoloLibro, String autoreLibro,
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

	// 1. Cercare tutti i libri prestati ad un utente specifico (a piacere tra
	// quelli inseriti) in ordine cronologico.
	public static List<Query1> listaQuery1(int idUtente) {
		DBConnection dbConn = new DBConnection();

		String sql = "" +
				"select l.titolo,l.autore,p.data_prestito " +
				"from " +
				"utenti u " +
				"join prestiti p using(id_utente) " +
				"join libri l using(id_libro) " +
				"where u.id_utente=? " +
				"order by p.data_prestito;";

		ResultSet rs = dbConn.queryPrepStatement(sql,new Object[] {idUtente});
		List<Query1> listaQuery1 = ricavaQuery1DaResultSet(rs);

		dbConn.close();
		return listaQuery1;
	}

	private static List<Query1> ricavaQuery1DaResultSet(ResultSet rs) {
		List<Query1> listaQuery1 = new ArrayList<Query1>();
		try {
			while (rs.next()) {

				String titoloLibro = rs.getString("titolo");
				String autoreLibro = rs.getString("autore");

				Calendar dataPrestito = new GregorianCalendar();
				dataPrestito.setTime(rs.getDate("data_prestito"));

				Query1 q = new Query1(titoloLibro, autoreLibro, dataPrestito);
				listaQuery1.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaQuery1;
	}

}
