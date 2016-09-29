package presenter;
/**
 * The Solve program implements an application that realize the methods from CommonCommand.
 * This class apply the solveMaze method that solve the maze by some algorithm.
 * 
 * @author Reut sananes & Mor basson
 * @version 1.0
 * 
 */
public class Solve extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Solve(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		String nameMaze = tempArr[0];
		String nameAlgorithms = tempArr[1];
		presenter.getModel().solveMaze(nameMaze,nameAlgorithms);
	}
}