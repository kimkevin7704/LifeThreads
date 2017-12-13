package k_k211;

public class SquareThread extends Thread{
	private int indexInGrid;
	private int value;
	
	SquareThread(int index) {
		value = 0;
		indexInGrid = index;
	}
	
	SquareThread(int index, int invalue) {
		value = invalue;
		indexInGrid = index;
	}
	
	public void run() {

			Grid.checkSquare(indexInGrid);

	}
	
	public void setValue(int input) {
		value = input;
	}
	
	public String getValue() {
		String valueStr = new String("");
		if(value == 0)
			valueStr = "O";
		if(value == 1)
			valueStr = "X";
		return valueStr;
	}
	
	public int getValueInt() {
		return value;
	}
	
	public int getIndexInGrid() {
		return indexInGrid;
	}
}