package presenter;
/**
 * The Display program implements an application that realize the methods from CommonCommand.
 * This class apply the relevant application according to command.
 * 
 * @author Reut sananes & Mor basson 
 * @version 1.0
 */
public class Display extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Display(Presenter presenter) {
		super(presenter);
	}

	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		String nameMaze;
		if (tempArr.length > 5) {
			if (tempArr[0].equals("cross")) {
				int index = 0;
				try {
					index = Integer.parseInt(tempArr[4]);
				} catch (NumberFormatException e) {
					presenter.getView().display("Invalid parameters");
				}
				String by = tempArr[3];
				nameMaze = tempArr[5];
				presenter.getModel().getMazeCrossSectionBy(by, nameMaze, index);
			}
		} else if (tempArr.length > 1) {
			if (tempArr[0].equals("solution")) {
				nameMaze = tempArr[1];
				presenter.getView().displaySolution(presenter.getModel().getMazeSolution(nameMaze));
			}
		} else {
			nameMaze = tempArr[0];
			presenter.getView().displayMaze(presenter.getModel().getMaze3d(nameMaze));
			presenter.getView().displayPosition(presenter.getModel().getMaze3d(nameMaze).getStart());
		}
	}
}