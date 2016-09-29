package presenter;

/**
 * The CommonCommand program implements an application that organize all
 * the common methods of command methods and implement the methods of the Command interface.
 * CommonCommand consist from: 1. Controller
 * 
 * @author Reut sananes & Mor Basson  
 * @version 1.0
 */

public abstract class CommonCommand implements Command {
	protected Presenter presenter;

	/**
	 * Constructor
	 * @param presenter
	 */
	public CommonCommand(Presenter presenter) {
		this.presenter = presenter;
	}

	public abstract void doCommand(String command);
}