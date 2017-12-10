package k_k211;

public class SquareThread extends Thread{
	private int indexInGrid;
	private int value;
	
	SquareThread(int index) {
		value = 0;
		indexInGrid = index;
	}
	
	public void run() {
		//each generation, check other squares and update
		for(int i = 0; i < Grid.generations; i++)
		{
			Grid.checkSquare(indexInGrid, Grid.grid);
		}
	}
	
	public void setValue(int input) {
		value = input;
	}
	
	public String getValue() {
		String valueStr = new String("");
		if(value == 0)
			valueStr = "X";
		if(value == 1)
			valueStr = "O";
		return valueStr;
	}
	
	public int getIndexInGrid() {
		return indexInGrid;
	}
}