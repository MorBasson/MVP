package guiView;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import guiProperties.MessegeWindow;
import presenter.Command;
import presenter.Properties;

/**
 * The MyViewObservableGui program implements an application that inherits AbstractViewObsevableGui.
 * MyViewObservableGui consist from MazeWindow and Properties
 * @author Reut sananes & Mor basson  
 * @version 1.0 
 */
public class MyViewObservableGui extends AbstractViewObsevableGui {

	protected MazeWindow mazeWindow;
	protected Properties properties;

	/**
	 * Constructor
	 * @param title
	 * @param width
	 * @param height
	 * @param in
	 * @param out
	 */
	public MyViewObservableGui(String title, int width, int height, BufferedReader in, PrintWriter out) {
		super(in, out);
		mazeWindow = new MazeWindow(title, width, height);
		this.properties = new Properties();
		//properties.defaultProp();
		
		//Sets the generateListener that responsible to generate the maze
		mazeWindow.setGenerateListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate 3d maze ");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		//Sets the solveListener that responsible to solve the maze
		mazeWindow.setSolveListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("solve ");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		//Sets the saveListener that responsible to save some maze
		mazeWindow.setSaveListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String file = mazeWindow.getFile();
				setChanged();
				notifyObservers("save " + file);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		//Sets the loadListener that responsible to load some maze
		mazeWindow.setLoadListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String file = mazeWindow.getFile();
				if (file == null) {
					display("You didn't choose a file, please try again");
					return;
				}
				file = file.replace("\\", "/");
				String[] fileString = (file.split("/"));
				setChanged();
				notifyObservers("load " + file + " " + fileString[fileString.length - 1]);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		//Sets the exitListener that responsible to close program orderly
		mazeWindow.setExitListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mazeWindow.shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		//Sets the disposeExit 
		mazeWindow.setDisposeExit(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				setChanged();
				notifyObservers("exit");

			}
		});
		
		//Sets the propertiesListener that responsible to update the game properties
		mazeWindow.setPropertiesListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						MessegeWindow mWindow = new MessegeWindow("Properties Game", 500, 400);
						mWindow.run();
						Properties prop = new Properties();
						try {
							XMLDecoder dXml = new XMLDecoder(
									new BufferedInputStream(new FileInputStream("Properties.xml")));
							prop = (Properties) dXml.readObject();
							dXml.close();
							setChanged();
							notifyObservers(prop);

						} catch (FileNotFoundException e) {

							e.printStackTrace();
						}

					}
				}).start();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		//Sets the keyListener that responsible to the player movements
		mazeWindow.setKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent key) {
				switch (key.keyCode) {
				case SWT.PAGE_UP:
					setChanged();
					notifyObservers("move up");
					System.out.println("up");
					break;
				case SWT.PAGE_DOWN:
					setChanged();
					notifyObservers("move down");
					System.out.println("down");
					break;
				case SWT.ARROW_LEFT:
					setChanged();
					notifyObservers("move left");
					System.out.println("left");
					break;
				case SWT.ARROW_RIGHT:
					setChanged();
					notifyObservers("move right");
					System.out.println("right");
					break;
				case SWT.ARROW_UP:
					setChanged();
					notifyObservers("move backward");
					System.out.println("backward");
					break;
				case SWT.ARROW_DOWN:
					setChanged();
					notifyObservers("move forward");
					System.out.println("forward");
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * This method is used to start the game
	 */
	@Override
	public void start() {
		mazeWindow.run();

	}

	/**
	 * This method is used to display the messegeBox
	 * @param messege
	 */
	@Override
	public void display(String message) {
		mazeWindow.messegeBox(message);
	}


	@Override
	public void displayCrossSectionBy(int[][] maze2d) {

	}

	@Override
	public void setCommand(HashMap<String, Command> commandMap) {

	}

	/**
	 * This method is used to display the maze
	 * @param maze
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		mazeWindow.setMyMaze(maze);

	}

	/**
	 * This method is used to display the position
	 * @param position
	 */
	@Override
	public void displayPosition(Position position) {
		mazeWindow.setCurrPosition(position);

	}

	/**
	 * This method is used to display the solution
	 * @param solution
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		mazeWindow.setSolution(solution);
	}

	@Override
	public void exit() {

	}

	/**
	 * This method is used to set the properties
	 * @param prop
	 */
	@Override
	public void setProperties(Properties prop) {
		if (properties.getChooseView() != null)
			if (!properties.getChooseView().equals(prop.getChooseView())) {
				setChanged();
				notifyObservers("replaceUserInterface replace");
			} else
				this.properties = prop;

	}

	@Override
	public void displayArr(String[] string) {
		// TODO Auto-generated method stub
		
	}

}