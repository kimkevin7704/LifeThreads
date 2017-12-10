package k_k211;

import java.util.*;

public class Grid {
	static int dimensions;
	static int generations;
	static ArrayList<SquareThread> grid;
	
	Grid(int dimension) {
		dimensions = dimension;
		grid = new ArrayList<SquareThread>(dimensions * dimensions);
		for(int i = 0; i < dimensions * dimensions; i++) {
			grid.add(new SquareThread(i));
		}
		System.out.println("Enter how many generations to run: ");
		Scanner reader = new Scanner(System.in);
		generations = reader.nextInt();
		
		populateGrid();
		
		for(int i = 0; i < dimensions * dimensions; i++) {
			grid.get(i).run();
		}
		
		System.out.println("\nAfter " + generations + " generations...");
		this.printGrid();
	}
	
	public void populateGrid() {
		for(int i = 0; i < dimensions; i++)
		{
			for(int k = 0; k < dimensions; k++)
			{
				if(Math.random() < 0.5)
				{					
					grid.get(i * 20 + k).setValue(0);
				}
				else
				{
					grid.get(i * 20 + k).setValue(1);
				}
			}	
		}
		System.out.println("Grid has been populated...");
		this.printGrid();
	}
	
	public static void checkSquare(int indexInGrid, ArrayList<SquareThread> grid) {
		int aliveNeighbors = findNeighbors(grid.get(indexInGrid));
		//if square is alive
		if(grid.get(indexInGrid).getValue() == "X")
		{
			//if less than 2 live neighbors, DIE
			if(aliveNeighbors < 2)
				grid.get(indexInGrid).setValue(1);
			//if 2 or 3 live neighbors, LIVE
			if(aliveNeighbors == 2 || aliveNeighbors == 3)
				grid.get(indexInGrid).setValue(0);
			//if more than 3, DIE
			if(aliveNeighbors > 3)
				grid.get(indexInGrid).setValue(1);
		}
		//if square is dead, if 3 alive neighbors, LIVE
		else
		{
			if(aliveNeighbors == 3)
				grid.get(indexInGrid).setValue(0);
		}
	}
	
	public static int findNeighbors(SquareThread currentSquare) {
		boolean isRight = false,
				isLeft = false,
				isTop = false,
				isBot = false;
		int liveNeighbors = 0;
		int currentIndex = currentSquare.getIndexInGrid();
		//if on the very left of grid, dont check left
		if(currentIndex % dimensions == 0) {
			isLeft = true;
		}
		//if on the very right, dont check right
		if(currentIndex % dimensions == dimensions - 1) {
			isRight = true;
		}
		//if on the very top, dont check top
		if(currentIndex / dimensions < 1) {
			isTop = true;
		}
		//if on the very bottom, dont check bottom
		if(currentIndex / dimensions >= 19) {
			isBot = true;
		}
		
		//check left top corner
		if(!isLeft && !isTop)
		{
			if(grid.get(currentIndex - 21).getValue() == "X")
				liveNeighbors++;
		}
		//check left
		if(!isLeft)
		{
			if(grid.get(currentIndex - 1).getValue() == "X")
				liveNeighbors++;
		}
		//check left bot
		if(!isRight && !isBot)
		{
			if(grid.get(currentIndex + 19).getValue() == "X")
				liveNeighbors++;
		}
		//check top
		if(!isTop)
		{
			if(grid.get(currentIndex - 20).getValue() == "X")
				liveNeighbors++;
		}
		//check bot
		if(!isBot)
		{
			if(grid.get(currentIndex + 20).getValue() == "X")
				liveNeighbors++;
		}
		//check right top
		if(!isTop && !isRight)
		{
			if(grid.get(currentIndex - 19).getValue() == "X")
				liveNeighbors++;
		}
		//check right
		if(!isRight)
		{
			if(grid.get(currentIndex + 1).getValue() == "X")
				liveNeighbors++;
		}
		//check right bot
		if(!isRight && !isBot)
		{
			if(grid.get(currentIndex + 21).getValue() == "X")
				liveNeighbors++;
		}
		
		return liveNeighbors;
	}
	
	public void printGrid() {
		for(int i = 0; i < dimensions; i++)
		{
			for(int k = 0; k < dimensions; k++)
			{
				System.out.print(grid.get(i * 20 + k).getValue());
			}
			System.out.print("\n");
		}
	}
}
