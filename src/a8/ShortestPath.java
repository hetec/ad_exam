package a8;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import spiffy.core.util.TwoDHashMap;
/**
 * Programm zur Berechung der kürzesten Wege nach Floyd Warshall
 * Valide Eingabedaten sind Vorraussetzung
 */
public class ShortestPath {
	private Integer dimensionOfMap;									//Speichert die Ausmaße des zu erzeugenden Array 
																	//Wird aus eingelesenen Daten ermittelt
	private TwoDHashMap<Integer, Integer, Integer> distances;		//Map zum speichern der Wege
	private TwoDHashMap<Integer, Integer, Integer> predecessors;	//Map zum speichern dre Vorgänger
	private List<Integer[]> changedFields = new ArrayList<>();		//Liste zum speichern der geänderten Werte
	private String file;
	
	public ShortestPath(String file){
		this.file = file;
	}
	/**
	 * Haupfunktion zum lösen des gegebenen Problems
	 * Ruft private Methoden auf
	 * Gibt Informationen aus
	 */
	public void solve(){
		List<Integer[]> sourceData = SpUtils.readFile(file);
		setDimensionOfMap(sourceData);
		initDistances(sourceData);
		SpUtils.print2dMap(distances, dimensionOfMap, "Initialbelgung für die Entfernungsmatrix. Dimension: " + dimensionOfMap);
		initPedecessors(sourceData);
		SpUtils.print2dMap(predecessors, dimensionOfMap, "Initialbelgung für die Vorgängermatrix. Dimension: " + dimensionOfMap);
		System.out.println();
		System.out.println("Schritt für Schritt Lösung:");
		System.out.println("===========================");
		solveShortestPathProblem();
		
	}
	/**
	 * Erstellt die Initialbelegung der Entfernungsmatrix
	 * Wird mit den Werten der eingelesenen Datei befüllt
	 * Unendlich wird durch den maximalen Integerwert repräsentiert
	 * @param sourceData
	 */
	private void initDistances(List<Integer[]> sourceData){
		distances = new TwoDHashMap<Integer, Integer, Integer>();
		for(int i = 1; i <= dimensionOfMap; i++){
			for(int j = 1; j <= dimensionOfMap; j++){
				if(i == j){
					distances.set(i,j,0);
				}else{
					distances.set(i,j,Integer.MAX_VALUE);
				}
			}
		}
		for(Integer[] row : sourceData){
			if(row[0] != row[1])
				distances.set(row[0], row[1], row[2]);
		}
	}
	
	/**
	 * Erstellt die Initialbelegung der Vorgängermatix
	 * Wird mit den Werten der eingelesenen Datei befüllt
	 * Nicht vorhandene Vorgänger werden durch Nullen repräsentiert
	 * @param sourceData
	 */
	private void initPedecessors(List<Integer[]> sourceData){
		predecessors = new TwoDHashMap<Integer, Integer, Integer>();
		for(int i = 1; i <= dimensionOfMap; i++){
			for(int j = 1; j <= dimensionOfMap; j++){
				predecessors.set(i,j,0);
			}
		}
		for(Integer[] row : sourceData){
			if(row[0] != row[1])
				predecessors.set(row[0], row[1], row[0]);
		}
	}
	
	/**
	 * Ermittelt die Ausmasse für die Matrix
	 * Wird für die Schleifen und die Ausgabe benötigt
	 * @param sourceData
	 */
	private void setDimensionOfMap(List<Integer[]> sourceData){
		TreeSet<Integer> sortedVertexes = new TreeSet<Integer>();
		for(Integer[] row : sourceData){
			sortedVertexes.add(row[1]);
			sortedVertexes.add(row[0]);
		}
		dimensionOfMap = sortedVertexes.last();
	}
	/**
	 * Löst das Problem = eigentlicher Algorithmus
	 * Drei Verschachtelte Schleifen:
	 * Zwei zum ablaufen des 2D Arrays benötigt
	 * Eine um die Anzahl der Zwischenkonten zu erhöhen
	 */
	private void solveShortestPathProblem(){
		for(int k = 1; k <= dimensionOfMap; k++){
			for(int i = 1; i <= dimensionOfMap; i++){
				for(int j = 1; j <= dimensionOfMap; j++){
					int sum;
					// Ermittlung der Summe der jeweils zu betrachtenden Matrixelemente
					// Wenn der Wert unendlich ist wird als Summe auch unendlich gesetzt - Vermeidung von Überlauffehlern
					if(distances.get(i,k) == Integer.MAX_VALUE || distances.get(k, j) == Integer.MAX_VALUE){
						sum = Integer.MAX_VALUE;
					}else{
						sum = distances.get(i,k) + distances.get(k, j);
					}
					// Festhalten der Vorgänger
					if(distances.get(i, j) > sum){
						predecessors.set(i, j, predecessors.get(k, j));
					}
					// Test ob aktuelles Element kleiner als ermittelte Summe --> Min()
					int d_ij = Math.min(distances.get(i,j), sum);
					// Nur zum Herrausschreiben der geänderten Werte für die Aufgabe
					if(d_ij < distances.get(i, j)){
						changedFields.add(new Integer[]{i,j,distances.get(i, j),d_ij});
					}
					// Setzen des neuen Wertes
					distances.set(i, j, d_ij);
				}
			}
			if(k <= 3){
				SpUtils.printChangedValues(changedFields, "Gekürzte Einträge bei k = " + k);
			}
			SpUtils.print2dMap(distances, dimensionOfMap, "Entfernungen / k = " + k);
			SpUtils.print2dMap(predecessors, dimensionOfMap, "Vorgänger / k = " + k);
		}
		
	}
}
