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
		 * 3 Spalten wie folgt: Startknoten <TAB> Endknoten <TAB> Gewicht
		 */
		String daten = "src/Daten8A.txt";
		if(args.length > 0){
			daten = args[0];
		}
		ShortestPath sp = new ShortestPath(daten);
		sp.solve();
	}
}
