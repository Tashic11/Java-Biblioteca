package it.corso.accenture.view;

import java.io.Closeable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.corso.accenture.entities.BibliotecaFunzioni;
import it.corso.accenture.entities.Libro;
import it.corso.accenture.entities.Prestito;
import it.corso.accenture.entities.Query1;
import it.corso.accenture.entities.Query2;
import it.corso.accenture.entities.Query3;
import it.corso.accenture.entities.Query4;
import it.corso.accenture.entities.Query5;
import it.corso.accenture.entities.Query6;
import it.corso.accenture.entities.Utente;

public class Menu implements Closeable {

	UserInterface ui;

	public Menu() {
		ui = new UserInterface();
	}

	public void mostra() {

		for (boolean esci = false; !esci;) {

			String messaggioMenu = "--Effettua operazioni sul database della biblioteca--\n";

			UIOpzione[] opzioni = new UIOpzione[] {
					new UIOpzione("1)Visualizza lista utenti,", () -> visualizzaUtenti()),
					new UIOpzione("2)Visualizza lista libri,", () -> visualizzaLibri()),
					new UIOpzione("3)Visualizza lista prestiti,", () -> visualizzaPrestiti()),
					new UIOpzione("4)Inserisci un nuovo utente,", () -> inserisciUtente()),
					new UIOpzione("5)Inserisci un nuovo libro,", () -> inserisciLibro()),
					new UIOpzione("6)Inserisci un nuovo prestito,", () -> inserisciPrestito()),
					new UIOpzione("7)Visualizza query,", () -> scegliQuery()),
					new UIOpzione("8)Modifica libro,", () -> modificaLibro()),
					new UIOpzione("9)Esci", () -> esci())
			};

			int scelta = ui.sceltaOpzioni(messaggioMenu, opzioni);
			opzioni[scelta - 1].esegui();

			esci = scelta == opzioni.length;

			ui.aspettaUtente();
		}
	}

	public void visualizzaUtenti() {
		System.out.println();

		List<Utente> listaUtenti = BibliotecaFunzioni.listaUtenti();
		if (listaUtenti.size() > 0) {
			System.out.println("Utenti memorizzati nel database:");
			listaUtenti.forEach(u -> System.out.println(u));
		} else
			System.out.println("Non sono ancora stati inseriti utenti nel database.");

		System.out.println();
	}

	public void visualizzaLibri() {
		System.out.println();

		List<Libro> listaLibri = BibliotecaFunzioni.listaLibri();
		if (listaLibri.size() > 0) {
			System.out.println("Libri memorizzati nel database:");
			listaLibri.forEach(l -> System.out.println(l));
		} else
			System.out.println("Non sono ancora stati inseriti libri nel database.");

		System.out.println();
	}

	public void visualizzaPrestiti() {
		System.out.println();

		List<Prestito> listaPrestiti = BibliotecaFunzioni.listaPrestiti();
		if (listaPrestiti.size() > 0) {
			System.out.println("Prestiti memorizzati nel database:");
			listaPrestiti.forEach(l -> System.out.println(l));
		} else
			System.out.println("Non sono ancora stati inseriti prestiti nel database.");

		System.out.println();
	}

	public void inserisciUtente() {
		System.out.println();

		String nome = ui.chiediStringa("Inserisci il nome dell'utente.");
		String cognome = ui.chiediStringa("Inserisci il cognome dell'utente.");

		int nuovoID = BibliotecaFunzioni.inserisciUtente(nome, cognome);

		System.out.println();

		if (nuovoID >= 0)
			System.out.println("L'utente è stato inserito nel database con id " + nuovoID + ".");
		else
			System.out.println("Inserimento non effettuato. Non è stato possibile inserire l'utente nel database.");

		System.out.println();
	}

	public void inserisciLibro() {
		System.out.println();

		String titolo = ui.chiediStringa("Inserisci il titolo del libro.");
		String autore = ui.chiediStringa("Inserisci l'autore del libro.");
		int annoUscita = ui.chiediIntPos("Inserisci l'anno di uscita del libro.");

		int nuovoID = BibliotecaFunzioni.inserisciLibro(titolo, autore, annoUscita);

		System.out.println();

		if (nuovoID >= 0)
			System.out.println("Il libro è stato inserito nel database con id " + nuovoID + ".");
		else
			System.out.println("Inserimento non effettuato. Non è stato possibile inserire il libro nel database.");

		System.out.println();
	}

	public void inserisciPrestito() {
		System.out.println();

		int idUtente = ui.chiediInt("Inserisci l'id dell'utente.");
		int idLibro = ui.chiediInt("Inserisci l'id del libro.");
		int numGiorni = ui.chiediInt("Inserisci il numero di giorni del prestito.");

		Calendar dataPrestito = new GregorianCalendar();

		Calendar dataScadenza = new GregorianCalendar();
		dataScadenza.add(Calendar.DAY_OF_YEAR, numGiorni);

		int nuovoID = BibliotecaFunzioni.inserisciPrestito(dataPrestito, dataScadenza, idUtente, idLibro);

		System.out.println();

		if (nuovoID >= 0)
			System.out.println("Il prestito è stato inserito nel database con id " + nuovoID + ".");
		else
			System.out.println("Inserimento non effettuato. Non è stato possibile inserire il prestito nel database.");

		System.out.println();
	}

	public void scegliQuery() {

		System.out.println();

		String messaggio = "Scegli la query da eseguire e visualizzare:\n";

		UIOpzione[] opzioni = new UIOpzione[] {
				new UIOpzione(
						"1)Cercare tutti i libri prestati ad un utente specifico (a piacere tra quelli inseriti) in ordine cronologico.",
						() -> visualizzaQuery1()),
				new UIOpzione(
						"2)Individua i primi tre lettori (tra quelli inseriti) che hanno letto più libri.",
						() -> visualizzaQuery2()),
				new UIOpzione(
						"3)Individua tutti i possessori (tra quelli inseriti) dei libri non ancora rientrati e il titolo degli stessi.",
						() -> visualizzaQuery3()),
				new UIOpzione(
						"4)Dare lo storico dei libri chiesti in prestito da un utente (a piacere tra quelli inseriti) indicando il periodo di riferimento.",
						() -> visualizzaQuery4()),
				new UIOpzione(
						"5)Fai la classifica dei libri maggiormente prestati.",
						() -> visualizzaQuery5()),
				new UIOpzione(
						"6)Individua tutti i prestiti la cui durata supera i 15gg.",
						() -> visualizzaQuery6())
		};

		int scelta = ui.sceltaOpzioni(messaggio, opzioni);
		opzioni[scelta - 1].esegui();

	}

	public void visualizzaQuery1() {
		System.out.println();
		int idUtente = ui.chiediIntPos("Inserisci l'id dell'utente.");		
		System.out.println();
		System.out.println("Libri prestati all'utente:");
		Query1.listaQuery1(idUtente).forEach(q -> System.out.println(q));		
		System.out.println();
	}

	public void visualizzaQuery2() {
		System.out.println();
		System.out.println("I tre lettori che hanno letto più libri sono:");
		Query2.listaQuery2().forEach(q -> System.out.println(q));		
		System.out.println();
	}

	public void visualizzaQuery3() {
		System.out.println();
		System.out.println("Lista dei libri non restituiti:");
		Query3.listaQuery3().forEach(q -> System.out.println(q));		
		System.out.println();
	}

	public void visualizzaQuery4() {
		System.out.println();
		int idUtente = ui.chiediIntPos("Inserisci l'id dell'utente.");		
		Calendar inizioPeriodo = ui.chiediCalendar("Inserisci l'inizio del periodo di riferimento. (dd/mm/yyyy)","dd/mm/yyyy");	
		Calendar finePeriodo = ui.chiediCalendar("Inserisci la fine del periodo di riferimento. (dd/mm/yyyy)","dd/mm/yyyy");		
		System.out.println();
		System.out.println("Libri prestati all'utente nel periodo specificato:");
		List<Query4> lista;
		
		if(inizioPeriodo.compareTo(finePeriodo)<=0)
			lista=Query4.listaQuery4(idUtente,inizioPeriodo,finePeriodo);
		else
			lista=Query4.listaQuery4(idUtente,finePeriodo,inizioPeriodo);
		
		lista.forEach(q -> System.out.println(q));		
		System.out.println();
	}

	public void visualizzaQuery5() {
		System.out.println();
		System.out.println("Classifica dei libri maggiormente prestati:");
		Query5.listaQuery5().forEach(q -> System.out.println(q));		
		System.out.println();
	}

	public void visualizzaQuery6() {
		System.out.println();
		System.out.println("Libri in prestito da più di 15 giorni:");
		Query6.listaQuery6().forEach(q -> System.out.println(q));		
		System.out.println();
	}

	public void modificaLibro() {
		System.out.println();

		int idLibro = ui.chiediInt("Inserisci l'id del libro.");

		String titolo = ui.chiediStringa("Inserisci il nuovo titolo del libro.");
		String autore = ui.chiediStringa("Inserisci il nuovo autore del libro.");
		int annoUscita = ui.chiediIntPos("Inserisci il nuovo anno di uscita del libro.");

		boolean modificaEffettuata = BibliotecaFunzioni.modificaLibro(idLibro, titolo, autore, annoUscita);

		System.out.println();

		if (modificaEffettuata)
			System.out.println("Il libro è stato modificato.");
		else
			System.out.println("Modifica non effettuata. Non è stato possibile modificare il libro nel database.");

		System.out.println();
	}

	public void esci() {
		System.out.println("\nFine Programma\n");
	}

	@Override
	public void close() {
		ui.close();
	}

}