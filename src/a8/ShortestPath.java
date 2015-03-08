package a8;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import spiffy.core.util.TwoDHashMap;
/**
 * Represents a shortest path problem (all-to-all)
 * The problem is constructed based on the data assigned
 * through a text file passed as a construcor parameter
 * 
 * The text file has to contain data in the order of
 * start vertex -- end vertex -- value of the edge separated by tabulator
 * 
 */
public class ShortestPath {
	
	private Integer dimensionOfMap;
	private TwoDHashMap<Integer, Integer, Integer> distances;
	private TwoDHashMap<Integer, Integer, Integer> predecessors;
	private List<Integer[]> changedFields = new ArrayList<>();
	private String file;
	
	public ShortestPath(String file){
		this.file = file;
	}
	
	public void solve(){
		List<Integer[]> sourceData = SpUtils.readFile(file);
		setDimensionOfMap(sourceData);
		initDistances(sourceData);
		SpUtils.print2dMap(distances, dimensionOfMap, "Initial dimensions with dimension: " + dimensionOfMap);
		initPedecessors(sourceData);
		SpUtils.print2dMap(predecessors, dimensionOfMap, "Initial predecessors with dimension: " + dimensionOfMap);
		System.out.println();
		System.out.println("Solution step by step:");
		solveShortestPathProblem();
		
	}
	
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
	
	private void setDimensionOfMap(List<Integer[]> sourceData){
		TreeSet<Integer> sortedVertexes = new TreeSet<Integer>();
		for(Integer[] row : sourceData){
			sortedVertexes.add(row[1]);
			sortedVertexes.add(row[0]);
		}
		dimensionOfMap = sortedVertexes.last();
	}
	/**
	 * Solves the shortest path problem through iterating over all fields of
	 * the dimensions map
	 * Updates the predecessor map 
	 */
	private void solveShortestPathProblem(){
		for(int k = 1; k <= dimensionOfMap; k++){
			for(int i = 1; i <= dimensionOfMap; i++){
				for(int j = 1; j <= dimensionOfMap; j++){
					int sum;
					// Handles the problem of calculation with infinity
					if(distances.get(i,k) == Integer.MAX_VALUE || distances.get(k, j) == Integer.MAX_VALUE){
						sum = Integer.MAX_VALUE;
					}else{
						sum = distances.get(i,k) + distances.get(k, j);
					}
					
					if(distances.get(i, j) > sum){
						predecessors.set(i, j, predecessors.get(k, j));
					}
					int d_ij = Math.min(distances.get(i,j), sum);
					if(d_ij < distances.get(i, j)){
						changedFields.add(new Integer[]{i,j,distances.get(i, j),d_ij});
					}
					distances.set(i, j, d_ij);
				}
			}
			if(k <= 3){
				SpUtils.printChangedValues(changedFields);
			}
			SpUtils.print2dMap(distances, dimensionOfMap, "dimenstions / k = " + k);
			SpUtils.print2dMap(predecessors, dimensionOfMap, "predecessors");
		}
		
	}
}
