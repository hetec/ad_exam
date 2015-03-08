package a8;
/**
 * Klasse zum starten eines Kürzeste-Wege-Problems
 * @author hebner
 *
 */
public class Main {
	public static void main(String[] args) {
		/*
		 * Neues Problem erzeugen + Textdatei übergeben
		 * Datei muss im Projektverzeichnis liegen
		 * Daten müssen über den Tabulator getrennt sein
		 * 3 Spalten wie folgt: Startknoten, Endknoten, Gewicht
		 */
		ShortestPath sp = new ShortestPath("Daten8A.txt");
		sp.solve();
	}
}
