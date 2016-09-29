package guiView;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The MazeDisplayer program implements an application that inherits Canvas. The
 * class is responsible to call all the methods that connected to the view of
 * the maze. MazeDisplayer consist from Maze3d, Position and Solution.
 * 
 * @author Reut sananes & Mor basson
 * @version 1.0
 */
public class MazeDisplayer extends Canvas {

	protected Maze3d myMaze;
	protected Position currPosition;
	protected Solution<Position> solution;

	/**
	 * Constructor
	 * 
	 * @param parent
	 * @param style
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		this.currPosition = new Position(0, 0, 0);
	}

	/**
	 * This method is used to get Maze3d
	 * 
	 * @return myMaze
	 */
	public Maze3d getMyMaze() {
		return myMaze;
	}

	/**
	 * This method is used to set the Maze3d
	 * 
	 * @param maze
	 */
	public void setMyMaze(Maze3d maze) {
		this.myMaze = maze;
	}

	/**
	 * This method is used to get the Position
	 * 
	 * @return currPosition
	 */
	public Position getCurrPosition() {
		return currPosition;
	}

	/**
	 * This method is used to set the Position
	 * 
	 * @param Position
	 */
	public void setCurrentPosition(Position position) {
		this.currPosition = position;
	}

	/**
	 * This method is used to get the Solution
	 * 
	 * @return solution
	 */
	public Solution<Position> getSolution() {
		return solution;
	}

	/**
	 * This method is used to set the Solution
	 * 
	 * @param solution
	 */
	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
	}

}