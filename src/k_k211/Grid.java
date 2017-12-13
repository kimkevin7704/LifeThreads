package k_k211;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Grid extends Thread{
	static int dimensions;
	static int generations;
	static ArrayList<SquareThread> grid;
	static ArrayList<SquareThread> temp;
	
	Grid(int dimension) {
		dimensions = dimension;
		System.out.println("Enter how many generations to run: ");
		Scanner reader = new Scanner(System.in);
		generations = reader.nextInt();
		grid = new ArrayList<SquareThread>(dimensions);
		temp = new ArrayList<SquareThread>();
		
	}
	
	public void run()
	{
		grid = new ArrayList<SquareThread>(dimensions * dimensions);
		for(int i = 0; i < dimensions * dimensions; i++) {
			grid.add(new SquareThread(i));
		}
		populateGrid();
		System.out.println("Initial generation");
		printGrid();
		for(int i = 0; i < generations; i++)
		{
			if(i > 0)
			{
				System.out.println("\nAfter " + (i + 1) + " generations...");
				printGrid();
				grid.clear();
				for(int m = 0; m < grid.size(); m++)
				{
					grid.add(new SquareThread(m, temp.get(m).getValueInt()));
				}
			}
			for(int k = 0; k < grid.size() ; k++)
			{
				grid.get(k).start();
				for(int j = 0; j < grid.size(); j++)
				{
					try {
						grid.get(k).join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			for(int f = 0; f < grid.size(); f++)
			{
				temp.add(new SquareThread(f, grid.get(f).getValueInt()));
			}
			
			if(i == 0)
			{
				System.out.println("\nAfter " + (i + 1) + " generations...");
				printGrid();
			}
			
		}
	}
	
	public void populateGrid() {
		try {
			FileInputStream inputFile = new FileInputStream("grid.txt");
			Scanner input = new Scanner(inputFile);
			
			String inputValues = null;
			
			for(int i = 0; i < dimensions; i++)
			{
				inputValues = input.nextLine();
				for(int k = 0; k < dimensions; k++)
				{
					if(inputValues.charAt(k) == 'O')
						grid.get(i * 20 + k).setValue(0);
					else
						grid.get(i * 20 + k).setValue(1);
				}	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Grid has been populated...");
	}
	
	public static void checkSquare(int indexInGrid) {
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
		if(currentIndex <= 19) {
			isTop = true;
		}
		//if on the very bottom, dont check bottom
		if(currentIndex > 379 && currentIndex <= 399) {
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
			if(grid.get(currentIndex + 21).getValue() == "X")
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
		for(int i = 0; i < grid.size(); i++)
		{
			System.out.print(grid.get(i).getValue());
			if(i % 20 == 19)
				System.out.print('\n');
		}
		System.out.print('\n');
	}
}
