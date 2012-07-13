package client.core.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Super-class map, in a grid way.
 * @author Mefu
 */

public class Map {

	private int[][] grid;
	private int x; 
	private int y;
	
	public Map(int x, int y){
		grid = new int[x][y];
		this.setX(x);
		this.setY(y);
		for(int i = 0 ; i < x ; i++){
			for(int j = 0 ; j < y ; j++){
				grid[i][j] = 0;
			}
		}
	}
	
	public int get(int i, int j){
		return grid[i][j];
	}
	
	public void readMap(String mapFilePath) throws IOException{
		BufferedReader bufRead = new BufferedReader(new FileReader(new File(mapFilePath)));
		String currentLine = bufRead.readLine();
		String[] stringSize = currentLine.split(" ");
		int[] size = new int[2];
		stringToIntArray(stringSize, size);
		grid = new int[size[0]][size[1]];
		this.setX(size[0]);
		this.setY(size[1]);
		int i = 0;
		while((currentLine = bufRead.readLine()) != null){
			String[] stringLine = currentLine.split(" ");
			int[] intLine = new int[getY()];
			stringToIntArray(stringLine, intLine);
			grid[i] = intLine;
			i++;
		}
	}
	
	public void stringToIntArray(String[] from, int[] to){
		if(from.length != to.length)
			return;
		for(int i = 0 ; i < from.length ; i++){
			to[i] = Integer.parseInt(from[i]);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean checkCollision(int i, int j){
		if(this.get(i, j) > 3){
			return false;
		}else{
			return true;
		}
	}
	
}
