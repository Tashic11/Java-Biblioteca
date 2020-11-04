package it.corso.accenture.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.corso.accenture.utils.DBConnection;

public abstract class BibliotecaFunzioni {

	// ----------Query Utenti---------------

	public static List<Utente> listaUtenti() {
		DBConnection dbConn = new DBConnection();
		ResultSet rs = dbConn.queryStatement("select * from utenti;");
		List<Utente> listaUtenti = ricavaUtentiDaResultSet(rs);

		dbConn.close();
		return listaUtenti;
	}

	public static int inserisciUtente(String nome, String cognome) {
		int idGenerato = -1;

		DBConnection dbConn = new DBConnection();
		String sql = "insert into utenti(nome,cognome) values(?,?);";
		Object[] valori = new Object[] { nome, cognome };

		if (dbConn.executePrepStatement(sql, valori) > 0)
			idGenerato = trovaUltimoIDInserito(dbConn);

		dbConn.close();
		return idGenerato;
	}

	// ----------Query Libri---------------

	public static List<Libro> listaLibri() {
		DBConnection dbConn = new DBConnection();
		ResultSet rs = dbConn.queryStatement("select * from libri;");
		List<Libro> listaLibri = ricavaLibriDaResultSet(rs);

		dbConn.close();
		return listaLibri;
	}

	public static int inserisciLibro(String titolo, String autore, int annoUscita) {
		int idGenerato = -1;

		DBConnection dbConn = new DBConnection();
		String sql = "insert into libri(titolo,autore,anno_uscita) values(?,?,?);";
		Object[] valori = new Object[] { titolo, autore, annoUscita };

		if (dbConn.executePrepStatement(sql, valori) > 0)
			idGenerato = trovaUltimoIDInserito(dbConn);

		dbConn.close();
		return idGenerato;
	}

	public static boolean modificaLibro(int idLibro, String titolo, String autore, int annoUscita) {
		boolean modificaEffettuata = false;

		DBConnection dbConn = new DBConnection();
		String sql = "update libri set titolo = ?, autore = ?, anno_uscita = ? where id_libro = ?;";
		Object[] valori = new Object[] { titolo, autore, annoUscita, idLibro };

		modificaEffettuata = dbConn.executePrepStatement(sql, valori) > 0;

		dbConn.close();
		return modificaEffettuata;
	}

	// ----------Query Prestiti---------------

	public static List<Prestito> listaPrestiti() {
		DBConnection dbConn = new DBConnection();
		ResultSet rs = dbConn.queryStatement("select * from prestiti;");
		List<Prestito> listaPrestiti = ricavaPrestitiDaResultSet(rs);

		dbConn.close();
		return listaPrestiti;
	}

	public static int inserisciPrestito(Calendar dataPrestito, Calendar dataScadenza, int idUtente, int idLibro) {
		int idGenerato = -1;

		DBConnection dbConn = new DBConnection();
		String sql = "insert into prestiti(data_prestito,data_scadenza,id_utente,id_libro) values(?,?,?,?);";
		Object[] valori = new Object[] { dataPrestito, dataScadenza, idUtente, idLibro };

		if (dbConn.executePrepStatement(sql, valori) > 0)
			idGenerato = trovaUltimoIDInserito(dbConn);

		dbConn.close();
		return idGenerato;
	}

	// ----------Query Utility---------------

	private static int trovaUltimoIDInserito(DBConnection dbConn) {
		ResultSet rs = dbConn.queryStatement("select last_insert_id() as id;");
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// ----------Crea Oggetti Da ResultSet---------------

	private static List<Utente> ricavaUtentiDaResultSet(ResultSet rs) {
		List<Utente> listaUtenti = new ArrayList<Utente>();
		try {
			while (rs.next()) {
				Utente u;
				u = new Utente(
						rs.getInt("id_utente"),
						rs.getString("nome"),
						rs.getString("cognome"));
				listaUtenti.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUtenti;
	}

	private static List<Libro> ricavaLibriDaResultSet(ResultSet rs) {
		List<Libro> listaLibri = new ArrayList<Libro>();
		try {
			while (rs.next()) {
				Libro l;
				l = new Libro(
						rs.getInt("id_libro"),
						rs.getString("titolo"),
						rs.getString("autore"),
						rs.getInt("anno_uscita"));
				listaLibri.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaLibri;
	}

	private static List<Prestito> ricavaPrestitiDaResultSet(ResultSet rs) {
		List<Prestito> listaPrestiti = new ArrayList<Prestito>();
		try {
			while (rs.next()) {
				Prestito p;

				int idPrestito = rs.getInt("id_prestito");

				Calendar dataPrestito = new GregorianCalendar();
				dataPrestito.setTime(rs.getDate("data_prestito"));

				Calendar dataScadenza = new GregorianCalendar();
				dataScadenza.setTime(rs.getDate("data_scadenza"));

				Date dataRestDate = rs.getDate("data_restituito");
				Calendar dataRestituito = null;

				if (dataRestDate != null) {
					dataRestituito = new GregorianCalendar();
					dataRestituito.setTime(rs.getDate("data_restituito"));
				}

				int idUtente = rs.getInt("id_utente");
				int idLibro = rs.getInt("id_libro");

				p = new Prestito(idPrestito, dataPrestito, dataScadenza, dataRestituito, idUtente, idLibro);

				listaPrestiti.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPrestiti;
	}

}
