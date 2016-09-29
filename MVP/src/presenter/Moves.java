package presenter;

/**
 * The Moves program implements an application that realize the methods from CommonCommand.
 * This class apply the moves methods that update the position according to the command that
 * receive from the method getPossibleMoves
 * 
 * @author Reut sananes & Mor basson  
 * @version 1.0
 */
public class Moves extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Moves(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
			switch (command) {
			case "up":
				presenter.getModel().moveUp();
				break;
			case "down":
				presenter.getModel().moveDown();
				break;
			case "left":
				presenter.getModel().moveLeft();
				break;
			case "right":
				presenter.getModel().moveRight();
				break;
			case "backward":
				presenter.getModel().moveBackward();
				break;
			case "forward":
				presenter.getModel().moveForward();
				break;

			default:
				presenter.getView().display("Error");
				break;
			}

		}
	}
