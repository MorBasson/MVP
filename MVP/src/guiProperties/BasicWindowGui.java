package guiProperties;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The BasicWindowGui program implements Runnable. The class is responsible to open a basic window.
 * BasicWindowGui consist from display and shell.
 * 
 * @author Reut sananes & Mor basson  
 * @version 1.0
 *
 */

public abstract class BasicWindowGui implements Runnable {
	
	protected Display display;
	protected Shell shell;
	
	/**
	 * Constructor
	 * @param title
	 * @param width
	 * @param height
	 */
	public BasicWindowGui(String title, int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}

	abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		while (!shell.isDisposed()) { // while window isn't closed

			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!display.readAndDispatch()) { // if the queue is empty
				display.sleep(); // sleep until an event occurs
			}
		} // shell is disposed
		display.dispose(); // dispose OS components
	}

}