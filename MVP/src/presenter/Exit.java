package presenter;
/**
 * The Exit program implements an application that realize the methods from CommonCommand.
 * This class apply the exit method close the project orderly.
 * 
 * @author Reut sananes & Mor basson  
 * @version 1.0
 *
 */
public class Exit extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Exit(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		presenter.getModel().exit();
		presenter.getView().exit();
	}

}