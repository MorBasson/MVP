package guiView;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The MazeWindow program implements an application that inherits BasicWindow.
 * The class is responsible to open the game window. MazeWindow consist from:
 * Position, Maze3d, Solution<Position>, SelectionListener, DisposeListener,
 * KeyListener, ArrayList<MazeDisplayer>, Menu, MenuItem, Button, String.
 * 
 * @author Reut sananes & Mor basson
 * @version 1.0
 *
 */

public class MazeWindow extends BasicWindow {

	protected Position currPosition;
	protected Maze3d myMaze;
	protected Solution<Position> solution;
	protected SelectionListener generateListener, solveListener, exitListener, saveListener, loadListener,
			propertiesListener;
	protected DisposeListener disposeExit;
	protected KeyListener keyListener;
	protected ArrayList<MazeDisplayer> widgetDisplayerList;
	protected Menu menuBar, fileMenu, gameMenu;
	protected MenuItem fileSaveItem, fileLoadItem, fileExitItem, filePropertiesItem, gameSolveItem, gameStartItem;
	protected MenuItem fileMenuHeader, gameMenuHeader;
	protected Button playButton;
	protected String file;

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param width
	 * @param height
	 */
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		this.widgetDisplayerList = new ArrayList<MazeDisplayer>();
		playButton = new Button(shell, 0);
		updateWidgets();
	}

	/**
	 * This method is used to insert widgets to the window.
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));

		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game");

		gameMenuHeader.setMenu(gameMenu);
		gameStartItem = new MenuItem(gameMenu, SWT.PUSH);
		gameStartItem.setText("&Start");
		gameSolveItem = new MenuItem(gameMenu, SWT.PUSH);
		gameSolveItem.setText("&Solve");

		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		filePropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		filePropertiesItem.setText("&Properties");

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("&Load");

		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");

		fileExitItem.addSelectionListener(exitListener);
		fileSaveItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Save");
				fd.setFilterPath("");
				String[] txtString = { "*.txt" };
				fd.setFilterExtensions(txtString);
				setFile(fd.open());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		fileSaveItem.addSelectionListener(saveListener);

		fileLoadItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Load");
				fd.setFilterPath("");
				String[] txtString = { "*.txt" };
				fd.setFilterExtensions(txtString);
				setFile(fd.open());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		fileLoadItem.addSelectionListener(loadListener);
		gameStartItem.addSelectionListener(generateListener);

		MazeDisplayer maze = new guiView.Maze3d(shell, SWT.BORDER, 'y');
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		widgetDisplayerList.add(maze);

		gameSolveItem.addSelectionListener(solveListener);
		playButton.addKeyListener(keyListener);
		filePropertiesItem.addSelectionListener(propertiesListener);

	}

	/**
	 * This method is used to update all the relevant data and redraw.
	 */
	public void updateWidgets() {
		for (MazeDisplayer widget : widgetDisplayerList) {
			if (myMaze != null) {
				widget.setMyMaze(myMaze);
			}
			if (currPosition != null) {
				widget.setCurrentPosition(currPosition);
			}
			if (solution != null) {
				widget.setSolution(solution);
				
			}
			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					widget.redraw();
				}
			});
			
		}
			
	}

	/**
	 * This method is used to set the position of the player
	 * 
	 * @param y
	 * @param x
	 * @param z
	 */
	public void movePlayer(int y, int x, int z) {
		if (y >= 0 && y < myMaze.getFloors() && x >= 0 && x < myMaze.getRows() && z >= 0 && z < myMaze.getCols()) {
			if (myMaze.getCell(y, x, z) == 0) {
				currPosition.setY(y);
				currPosition.setX(x);
				currPosition.setZ(z);
				updateWidgets();
			}
		}
	}

	/**
	 * This method is used to get the Position
	 * 
	 * @return Position
	 */
	public Position getCurrPosition() {
		return currPosition;
	}

	/**
	 * This method is used to set the Position
	 * 
	 * @param currPosition
	 */
	public void setCurrPosition(Position currPosition) {
		this.currPosition = currPosition;
		updateWidgets();
	}

	/**
	 * This method is used to get the Maze
	 * 
	 * @return Maze3d
	 */
	public Maze3d getMyMaze() {
		return myMaze;
	}

	/**
	 * This method is used to set the Maze
	 * 
	 * @param myMaze
	 */
	public void setMyMaze(Maze3d myMaze) {
		this.myMaze = myMaze;
		updateWidgets();
	}

	/**
	 * This method is used to get the Solution
	 * 
	 * @return Solution
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
		updateWidgets();
	}


	/**
	 * This method is used to display an error for the client when necessary.
	 * 
	 * @param string
	 */
	public void messegeBox(String string) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
				messageBox.setMessage(string);
				messageBox.setText("Pay Attention!");
				messageBox.open();

			}
		});
	}

	/**
	 * This method is used to set the generateListener
	 * 
	 * @param generateListener
	 */
	public void setGenerateListener(SelectionListener generateListener) {
		this.generateListener = generateListener;
	}

	/**
	 * This method is used to get the generateListener
	 * 
	 * @return generateListener
	 */
	public SelectionListener getGenerateListener() {
		return generateListener;
	}

	/**
	 * This method is used to set the solveListener
	 * 
	 * @param solveListener
	 */
	public void setSolveListener(SelectionListener solveListener) {
		this.solveListener = solveListener;
	}

	/**
	 * This method is used to get the solveListener
	 * 
	 * @return solveListener
	 */
	public SelectionListener getSolveListener() {
		return solveListener;
	}

	/**
	 * This method is used to get the disposeExit
	 * 
	 * @return disposeExit
	 */
	public DisposeListener getDisposeExit() {
		return disposeExit;
	}

	/**
	 * This method is used to set the disposeExit
	 * 
	 * @param disposeExit
	 */
	public void setDisposeExit(DisposeListener disposeExit) {
		this.disposeExit = disposeExit;
		shell.addDisposeListener(disposeExit);
	}

	/**
	 * This method is used to set the exitListener
	 * 
	 * @param exitListener
	 */
	public void setExitListener(SelectionListener exitListener) {
		this.exitListener = exitListener;
	}

	/**
	 * This method is used to get the exitListener
	 * 
	 * @return exitListener
	 */
	public SelectionListener getExitListener() {
		return exitListener;
	}

	/**
	 * This method is used to get the saveListener
	 * 
	 * @return saveListener
	 */
	public SelectionListener getSaveListener() {
		return saveListener;
	}

	/**
	 * This method is used to set the saveListener
	 * 
	 * @param saveListener
	 */
	public void setSaveListener(SelectionListener saveListener) {
		this.saveListener = saveListener;
	}

	/**
	 * This method is used to get the loadListener
	 * 
	 * @return loadListener
	 */
	public SelectionListener getLoadListener() {
		return loadListener;
	}

	/**
	 * This method is used to set the loadListener
	 * 
	 * @param loadListener
	 */
	public void setLoadListener(SelectionListener loadListener) {
		this.loadListener = loadListener;
	}

	/**
	 * This method is used to get the propertiesListener
	 * 
	 * @return propertiesListener
	 */
	public SelectionListener getPropertiesListener() {
		return propertiesListener;
	}

	/**
	 * This method is used to set the propertiesListener
	 * 
	 * @param propertiesListener
	 */
	public void setPropertiesListener(SelectionListener propertiesListener) {
		this.propertiesListener = propertiesListener;
	}

	/**
	 * This method is used to get the keyListener
	 * 
	 * @return keyListener
	 */
	public KeyListener getKeyListener() {
		return keyListener;
	}

	/**
	 * This method is used to set the keyListener
	 * 
	 * @param keyListener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	/**
	 * This method is used to set the file
	 * 
	 * @param file
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * This method is used to get the file
	 * 
	 * @return String
	 */
	public String getFile() {
		return file;
	}

}