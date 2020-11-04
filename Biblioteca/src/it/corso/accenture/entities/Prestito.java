package it.corso.accenture.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Prestito {

	private int idPrestito;
	private Calendar dataPrestito;
	private Calendar dataScadenza;
	private Calendar dataRestituito;
	private int idUtente;
	private int idLibro;

	public Prestito() {
		super();
	}

	public Prestito(int idPrestito, Calendar dataPrestito, Calendar dataScadenza, Calendar dataRestituito, int idUtente,
			int idLibro) {
		super();
		this.idPrestito = idPrestito;
		this.dataPrestito = dataPrestito;
		this.dataScadenza = dataScadenza;
		this.dataRestituito = dataRestituito;
		this.idUtente = idUtente;
		this.idLibro = idLibro;
	}

	public int getIdPrestito() {
		return idPrestito;
	}

	public void setIdPrestito(int idPrestito) {
		this.idPrestito = idPrestito;
	}

	public Calendar getDataPrestito() {
		return dataPrestito;
	}

	public void setDataPrestito(Calendar dataPrestito) {
		this.dataPrestito = dataPrestito;
	}

	public Calendar getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Calendar dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Calendar getDataRestituito() {
		return dataRestituito;
	}

	public void setDataRestituito(Calendar dataRestituito) {
		this.dataRestituito = dataRestituito;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPrestito);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Prestito))
			return false;
		Prestito other = (Prestito) obj;
		return idPrestito == other.idPrestito;
	}

	@Override
	public String toString() {
		return "Prestito idPrestito : " + idPrestito + ", dataPrestito : " + dataPrestitoSemplice() + ", dataScadenza : "
				+ dataScadenzaSemplice() + ", dataRestituito : " + dataRestituitoSemplice() + ", idUtente : " + idUtente + ", idLibro : "
				+ idLibro;
	}
	
	public String dataPrestitoSemplice() {
		return dataSemplice(dataPrestito);
	}
	
	public String dataScadenzaSemplice() {
		return dataSemplice(dataScadenza);
	}
	
	public String dataRestituitoSemplice() {
		return dataSemplice(dataRestituito);
	}
	
	private String dataSemplice(Calendar data) {
		if(data==null)
			return null;
		return new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());
	}

}
