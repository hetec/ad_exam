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

import spiffy.core.util.*;

public class SpUtils {
	
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
		
//		for(int i = 0; i < content.size(); i++){
//			System.out.println(content.get(i)[0] + "     " + content.get(i)[1] + "     " + content.get(i)[2]);
//		}
		
		return content;
	}
	
	
	
	public static void print2dMap(TwoDHashMap<Integer, Integer, Integer> map, int dimension, String message){
		System.out.println("______________________________________");
		System.out.println(message);
		for(int i = 1; i <= dimension; i++){
			for(int j = 1; j <= dimension; j++){
				System.out.format("%5s", (map.get(i, j) == Integer.MAX_VALUE)?("oo"):(map.get(i, j)).toString());
			}
			System.out.println();
		}
	}
}


