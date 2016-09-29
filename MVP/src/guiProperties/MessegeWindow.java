package guiProperties;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import presenter.Properties;

/**
 * The MessegeWindow program implements an application that inherits BasicWindowGui.
 * The class is responsible to open a properties window of the maze.
 * MessegeWindow consist from properties and okButton.
 * 
 * @author Reut sananes & Mor basson  
 * @version 1.0
 *
 */

public class MessegeWindow extends BasicWindowGui {

	protected Properties properties;
	protected Button okButton;

	/**
	 * Constructor
	 * @param title
	 * @param width
	 * @param height
	 */
	public MessegeWindow(String title, int width, int height) {
		super(title, width, height);
		properties = new Properties();
		//properties.defaultProp();

	}

	/**
	 * This method is used to insert widgets to the window.
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));

		new Label(shell, SWT.NONE).setText("Enter a name maze:");
		final Text nameMaze = new Text(shell, SWT.BORDER);
		nameMaze.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("Enter number of thread:");
		final Text numOfThread = new Text(shell, SWT.BORDER);
		numOfThread.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("Choose number of floors");
		final Text numOfFloors = new Text(shell, SWT.BORDER);
		numOfFloors.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("Choose number of lines:");
		final Text numOfLines = new Text(shell, SWT.BORDER);
		numOfLines.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("Choose number of columns:");
		final Text numOfColumns = new Text(shell, SWT.BORDER);
		numOfColumns.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(shell, SWT.NONE).setText("Choose type solving algorithm:");
		final Combo algo = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
		algo.add("BFS");
		algo.add("DFS");
		algo.select(0);
		
		new Label(shell, SWT.NONE).setText("Choose axis:");
		final Combo axis = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
		axis.add("y");
		axis.add("x");
		axis.add("z");
		axis.select(0);
		
		new Label(shell, SWT.NONE).setText("Choose view display:");
		final Combo view = new Combo(shell, SWT.DROP_DOWN | SWT.PUSH | SWT.BORDER);
		view.add("Gui");
		view.add("Cli");
		view.select(0);
		
		okButton = new Button(shell, SWT.PUSH);
		okButton.setText("OK");
		okButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				properties.setNameMaze(nameMaze.getText());
				
				if ((Integer.parseInt(numOfFloors.getText()) > 0)) {
					properties.setSizeY(Integer.parseInt(numOfFloors.getText()));
				} else {
					messegeError("Invalid 'y' value");
					properties.setSizeY(5);
				}
				
				if ((Integer.parseInt(numOfLines.getText()) > 0)) {
					properties.setSizeX(Integer.parseInt(numOfLines.getText()));
				} else {
					messegeError("Invalid 'x' value");
					properties.setSizeX(5);
				}
				
				if ((Integer.parseInt(numOfColumns.getText()) > 0)) {
					properties.setSizeZ(Integer.parseInt(numOfColumns.getText()));
				} else {
					messegeError("Invalid 'z' value");
					properties.setSizeZ(5);
				}
				
				if ((Integer.parseInt(numOfThread.getText()) > 0)) {
					properties.setNumOfThread(Integer.parseInt(numOfThread.getText()));
				} else {
					messegeError("Invalid number of threads value");
					properties.setNumOfThread(10);
				}
				
				properties.setAxis(axis.getText().charAt(0));
				
				properties.setChooseView(view.getText());

				properties.setAlgorithm(algo.getText());
				try {
					XMLEncoder eXml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
					eXml.writeObject(properties);
					eXml.flush();
					eXml.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				shell.dispose();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	/**
	 * This method is used to display an error for the client when necessary.
	 * @param messege
	 */
	public void messegeError(String messege) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
		    public void run() {
		    	MessageBox messageBox =  new MessageBox(shell, SWT.ICON_INFORMATION); 
		    	messageBox.setMessage(messege);
		    	messageBox.setText("Pay Attention!");
		    	messageBox.open();		
		    	
		    }
		});
	}
	
	public void open(){
		shell.open();
	}

}