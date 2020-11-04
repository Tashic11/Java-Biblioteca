package it.corso.accenture.utils;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DBConnection implements Closeable {

	private Connection conn;

	//private String driver;

	private String dbHost;
	private String dbPort;

	private String dbUsername;
	private String dbPassword;
	private String dbName;
	private String dbOption;
	
	private ResultSet rs;
	private Statement st;
	private PreparedStatement prep;
	
	public DBConnection() {
		getConnection();
	}

	// apre connessione al DB
	private Connection getConnection() {

		//this.driver = "com.mysql.jdbc.Driver";

		this.dbHost = "127.0.0.1";
		this.dbPort = "3306";

		this.dbName = "Biblioteca";
		this.dbUsername = "root";
		this.dbPassword = "root";
		this.dbOption = "?serverTimezone=Europe/Rome";

		String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + dbOption;
		
		try {
			this.conn = DriverManager.getConnection(connectionString,dbUsername,dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	// esegue la query passata come parametro e restituisce il ResultSet con il risultato
	public ResultSet queryStatement(String sql) {

		try {
			closeStatementsAndResultSet();
			
			st = conn.createStatement();
			
			rs = st.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			System.out.println("Error: resultset error!");
			e.printStackTrace();
		}

		return null;
	}

	public int executePrepStatement(String sql, Object[] values) {
		
		try {	
			closeStatementsAndResultSet();
			
			prep = conn.prepareStatement(sql);

			populatePreparedStatement(prep,values);
			
			return prep.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error: preparedstatement error!");
			e.printStackTrace();
		} catch (ClassNotSupportedException e) {
			System.out.println("Error: class type error!");
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public ResultSet queryPrepStatement(String sql, Object[] values) {
		
		try {				
			closeStatementsAndResultSet();
			
			prep = conn.prepareStatement(sql);

			populatePreparedStatement(prep,values);
			
			rs= prep.executeQuery();
			return rs;

		} catch (SQLException e) {
			System.out.println("Error: preparedstatement error!");
			e.printStackTrace();
		} catch (ClassNotSupportedException e) {
			System.out.println("Error: class type can't be inserted in the vaules!");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void populatePreparedStatement(PreparedStatement prep,Object[] values) throws SQLException, ClassNotSupportedException {	
		for (int i = 0; i < values.length; i++) {
			Object o = values[i];
			int queryValue=i+1;

			if (o instanceof Integer || o instanceof Short || o instanceof Byte)				
				prep.setInt(queryValue, (int) o);
			
			else if (o instanceof Long)			
				prep.setLong(queryValue, (long) o);
			
			else if (o instanceof Float)				
				prep.setFloat(queryValue, (float) o);
			
			else if (o instanceof Double)				
				prep.setDouble(queryValue, (double) o);
			
			else if (o instanceof Boolean)				
				prep.setBoolean(queryValue, (boolean) o);
			
			else if (o instanceof Calendar) {	
				long timeInMillis =((Calendar)o).getTimeInMillis();
				java.sql.Date sqlDate = new java.sql.Date(timeInMillis);
				prep.setDate(queryValue, sqlDate);
				
			} else if (o instanceof Date) {						
				Date d = (Date) o;
				LocalDate lDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				java.sql.Date sqlDate = java.sql.Date.valueOf(lDate);
				prep.setDate(queryValue, sqlDate);
				
			} else if (o instanceof LocalDate) {				
				java.sql.Date sqlDate = java.sql.Date.valueOf((LocalDate) o);
				prep.setDate(queryValue, sqlDate);
				
			} else if (o instanceof java.sql.Date) {						
				prep.setDate(queryValue, (java.sql.Date)o);
				
			} else if (o instanceof String)				
				prep.setString(queryValue, (String) o);
			
			else if (o == null)				
				prep.setNull(queryValue,java.sql.Types.NULL);
			
			else
				throw new ClassNotSupportedException(o.getClass());
		}
	}
	
	private void closeStatementsAndResultSet() throws SQLException {
		if(rs!=null)rs.close();
		if(st!=null)st.close();
		if(prep!=null)prep.close();	
	}

	// chiude la connessione al DB e tutti gli oggetti collegati (ResultSet e Statement)
	@Override
	public void close() {
		// Chiusura oggetti connessione
		// gli oggetti vanno chiusi correttamente nell'ordine inverso a quello di apertura.
		try {
			closeStatementsAndResultSet();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			System.out.println("Error: close connection objects error!");
			e.printStackTrace();
		}
	}

}
