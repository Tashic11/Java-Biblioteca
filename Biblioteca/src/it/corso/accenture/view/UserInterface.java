package it.corso.accenture.view;

import java.io.Closeable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class UserInterface implements Closeable {

	private Scanner scanner;
	
	public UserInterface() {
		scanner = new Scanner(System.in);
	}
	
	//scelta opzioni
	
	public int sceltaOpzioni(String messaggio, String[] opzioni) {
	
		for(String s : opzioni)
			messaggio+="\n"+s;

		return chiediIntRange(messaggio, 1, opzioni.length);
	}
	
	public int sceltaOpzioni(String messaggio, UIOpzione[] opzioni) {

		for(UIOpzione o : opzioni)
				messaggio+="\n"+o.getDescrizione();

		return chiediIntRange(messaggio, 1, opzioni.length);
	}

	// chiedi stringe

	public String chiediStringa(String messaggio) {
		System.out.println(messaggio);
		return scanner.nextLine();
	}

	// chiedi interi

	public int chiediInt(String messaggio) {
		return chiediInt(messaggio, i -> true);
	}

	public int chiediInt(String messaggio, Predicate<Integer> condizione) {
		do {
			System.out.println(messaggio);
			try {
				int scelta = scanner.nextInt();
				scanner.nextLine();

				if (condizione.test(scelta))
					return scelta;

			} catch (InputMismatchException e) {
				scanner.nextLine();
			}
		} while (true);
	}

	public int chiediIntPos(String messaggio) {
		return chiediInt(messaggio, i -> i > 0);
	}

	public int chiediIntRange(String messaggio, int min, int max) {
		return chiediInt(messaggio, i -> i >= min && i <= max);
	}

	// chiedi double

	public double chiediDouble(String messaggio) {
		return chiediDouble(messaggio, d -> true);
	}

	public double chiediDouble(String messaggio, Predicate<Double> condizione) {
		do {
			System.out.println(messaggio);
			try {
				double scelta = scanner.nextDouble();
				scanner.nextLine();

				if (condizione.test(scelta))
					return scelta;

			} catch (InputMismatchException e) {
				scanner.nextLine();
			}
		} while (true);
	}

	public double chiediDoublePos(String messaggio) {
		return chiediDouble(messaggio, d -> d > 0);
	}

	public double chiediDoubleRange(String messaggio, double min, double max) {
		return chiediDouble(messaggio, d -> d >= min && d <= max);
	}

	// chiedi data (calendar)

	public Calendar chiediCalendar(String messaggio, String format) {
		return chiediCalendar(messaggio, format, d -> true);
	}
	
	public Calendar chiediCalendar(String messaggio, String format, Predicate<Calendar> condizione) {
		do {
			System.out.println(messaggio);
			try {
				String toParse = scanner.nextLine();				
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Calendar scelta = new GregorianCalendar();
				scelta.setTime(sdf.parse(toParse));

				if (condizione.test(scelta))
					return scelta;

			} catch (ParseException e) {
			}
		} while (true);
	}

	// blocca schermata per l'utente

	public void aspettaUtente() {
		chiediStringa("Premi invio per continuare...");
	}

	// chiudi lo scanner
	
	public void close() {
		scanner.close();
	}
}
