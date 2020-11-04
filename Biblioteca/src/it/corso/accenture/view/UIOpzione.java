package it.corso.accenture.view;

/*
 * Classe usata per abbinare la descrizione di un'opzione di un menu e la funzione che sarà usata per l'esecuzione
 */
public class UIOpzione {

	private String descrizione;
	private Runnable funzione;

	public UIOpzione(String descrizione, Runnable funzione) {
		super();
		this.descrizione = descrizione;
		this.funzione = funzione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Runnable getFunzione() {
		return funzione;
	}

	public void setFunzione(Runnable funzione) {
		this.funzione = funzione;
	}
	
	public void esegui() {
		funzione.run();
	}

}
