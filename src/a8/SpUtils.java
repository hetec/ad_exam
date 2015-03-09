package a8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.ImageIcon;

import spiffy.core.util.*;
/**
 * Klasse zur Unterstützung des Floy Warshall Algorithmus
 * Methoden zum einlesen einer Textdatei
 * und zum Ausgeben von Ergebnissen
 * @author hebner
 *
 */
public class SpUtils {
	/**
	 * Liest ein Textfile ein
	 * Daten müssen über den Tabulator getrennt sein
	 * 3 Spalten wie folgt: Startknoten, Endknoten, Gewicht
	 * @param path
	 * @return
	 */
	public static List<Integer[]> readFile(String path){
		
		final File file = new File(path);
		List<Integer[]> content = new ArrayList<>();
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
			
			String tmp = "";
			String[] row = new String[3];
			
			while((tmp = bufferedReader.readLine()) != null){
				row = tmp.split("\t");
				content.add(new Integer[]{
						Integer.valueOf(row[0]),
						Integer.valueOf(row[1]),
						Integer.valueOf(row[2])
				});
			}
		}catch (IOException e) {
			System.out.println("Problems while reading file " + path);
		}
		
		return content;
	}
	
	public static void printChangedValues(List<Integer[]> list, String message){
		System.out.println();
		System.out.println(message);
		System.out.println();
		for(Integer[] item : list){
			String old = (item[2] == Integer.MAX_VALUE)?("\u221E"):(item[2].toString());
			String curr = (item[3] == Integer.MAX_VALUE)?("\u221E"):(item[3].toString());
			System.out.format("Pos [%3d,%3d]:    %3s --> %3s%n", item[0], item[1], old, curr);
		}
		System.out.println();
	}
	
	public static void print2dMap(Integer[][] map, int dimension, String message){
		System.out.println();
		System.out.println("______________________________________");
		System.out.println();
		System.out.println(message);
		System.out.println();
		for(int i = 1; i <= dimension; i++){
			if(i == 1){
				System.out.format("[%3s]", "-");
				for(int columns = 1; columns <= dimension; columns++){
					System.out.format("[%3s]", columns);
				}
				System.out.println();
			}
			for(int j = 1; j <= dimension; j++){
				if(j == 1){
					System.out.format("[%3s]", i);
				}
				System.out.format("%5s", (map[i][j] == Integer.MAX_VALUE)?("\u221E"):(map[i][j]).toString());
			}
			System.out.println();
		}
	}
}


