package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

public interface View {
	/**
	 * The view will be responsible for user input and display for
	 * 
	 * @author Mor Basson & Reut Sananes
	 * @version 1.0
	 */
	
	 public void start();
	 public void displayArr(String[] arr);
	 public void display(String message);
	 public void displayCrossSectionBy(int[][] maze2d);
	 public void displaySolution(Solution<Position> solution);
	 public void setCommand(HashMap<String, Command> commandMap);
	 public void displayMaze(Maze3d maze);
	 public void displayPosition(Position position);
	 public void exit();
	 public void setProperties(Properties prop);

		 
	
}
