package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * The Presenter program implements Observer methods.
 * Presenter consist from View, Model, HashMap and Properties.
 * @author Reut sananes & Mor basson  
 * @version 1.0
 *
 */
public class Presenter implements Observer {

	private View view;
	private Model model;
	private HashMap<String, Command> commandMap;
	private Properties properties;

	/**
	 * Constructor
	 * @param view
	 * @param model
	 */
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
		this.properties = new Properties();
		properties.defaultProp();
		model.setProperties(properties);

		this.commandMap = new HashMap<String, Command>();
		commandMap.put("dir", new Dir(this));
		commandMap.put("generate", new Generate(this));
		commandMap.put("display", new Display(this));
		commandMap.put("save", new Save(this));
		commandMap.put("load", new Load(this));
		commandMap.put("solve", new Solve(this));
		commandMap.put("move", new Moves(this));
		commandMap.put("exit", new Exit(this));

		view.setCommand(commandMap);
	}

	/**
	 * This method is used to get the view
	 * @return View
	 */
	public View getView() {
		return view;
	}

	/**
	 * This method is used to set the view
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * This method is used to get the model
	 * @return Model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * This method is used to set the model
	 * @param model
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * This method is used to work according to the observable that received.
	 * @param observable
	 * @param obj 
	 */
	@Override
	public void update(Observable observable, Object obj) {
		if (observable == view) {
			if (((obj.getClass()).getName()).equals("presenter.Properties")) {
				Properties prop = (Properties) obj;
				model.setProperties(prop);
			} else {
				Command command;
				String line = (String) obj;
				command = commandMap.get(line.split(" ")[0]);
				if (command != null) {
					if (line.split(" ").length > 0) {
						command.doCommand(line.substring(line.indexOf(' ') + 1));
					} else {
						command.doCommand("");
					}
				} else {
					view.display("Invalid command");
				}
			}
		}

		else if (observable == model) {
			String line = (String) obj;
			String[] tempArr = line.split(" ");
			switch (tempArr[0]) {
			case "mazeIsReady":
				Maze3d maze = (Maze3d) model.getUserCommand(line);
				view.displayMaze(maze);
				view.displayPosition(maze.getStart());
				break;
			case "displayCrossSectionBy":
				view.displayCrossSectionBy((int[][]) model.getUserCommand(line));
				break;
			case "saveMaze":
				view.display("maze is saved successfully in file " + (String) model.getUserCommand(line));
				break;
			case "loadMaze":
				Maze3d myMaze = (Maze3d) model.getUserCommand(line);
				view.displayMaze(myMaze);
				view.displayPosition(myMaze.getStart());
				break;
			case "solutionIsReady":
				view.displaySolution(model.getMazeSolution((String) model.getUserCommand(line)));
				break;
			case "loadZip":
				view.display("The maze has loaded from "+ (String) model.getUserCommand(line));
				break;
			case "saveZip":
				view.display("file is saved to " + (String) model.getUserCommand(line));
				break;
			case "move":
				view.displayMaze(model.getMaze3d((String) model.getUserCommand(line)));
				view.displayPosition(model.getPositionFromHash((String) model.getUserCommand(line)));
				break;
			case "exit":
				view.display("The system is shutting down");
				break;
			case "null":
				view.display("Maze " + (String) model.getUserCommand(line) + " is not exist");
				break;

			case "Invalid":
				switch (tempArr[1]) {
				case "parameters":
					view.display("Invalid parameters");
					break;
				case "index":
					view.display("Invalid index");
					break;
				case "file":
					view.display("The file " + (String) model.getUserCommand(line) + " is not exist");
					break;
				case "compress":
					view.display("Cannot collapse the " + (String) model.getUserCommand(line));
					break;
				case "maze":
					view.display("There is error with the " + (String) model.getUserCommand(line) + " maze");
				case "algorithm":
					view.display("Invalid algorithm");
					break;
				case "solution":
					view.display("Solution for " + (String) model.getUserCommand(line) + " is not exist");
					break;
				default:
					view.display("Invalid parameters");
					break;
				}
				break;
			default:
				view.display("Invalid command");
				break;
			}
		}
	}

	/**
	 * This method is used to set the properties
	 * @param properties
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
		if (model != null)
			this.model.setProperties(properties);
		if (view != null) {
			this.view.setProperties(properties);
		}
	}

	/**
	 * This method is used to get the properties
	 * @return Properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * This method is used to close the project orderly according to view that start this project.
	 */
	public void exitView() {
		view.exit();

	}
}