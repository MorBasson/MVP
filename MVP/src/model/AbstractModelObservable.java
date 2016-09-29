package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * The AbstractModelObservable program implements Model and inherits Observable.
 * AbstractModelObservable consist from HashMap, Properties and ExecutorService - threadPool.
 * @author Reut sananes & Mor basson  
 * @version 1.0
 */
public abstract class AbstractModelObservable extends Observable implements Model {

	protected HashMap<String, Maze3d> hashMaze;
	private HashMap<String, Object> commandMap;
	protected Properties properties;
	protected ExecutorService threadPool;

	/**
	 * Constructor
	 */
	public AbstractModelObservable() {
		this.hashMaze = new HashMap<String, Maze3d>();
		this.commandMap = new HashMap<String, Object>();
		threadPool = Executors.newCachedThreadPool();
		this.properties= new Properties();
		properties.defaultProp();
	}

	public abstract void generate(String nameMaze);
	public abstract Maze3d getMaze3d(String nameMaze);
	public abstract void getMazeCrossSectionBy(String by, String nameMaze, int index);
	public abstract void saveMaze(String fileMaze);
	public abstract void loadMaze(String fileName, String nameMaze);
	public abstract Solution<Position> getMazeSolution(String nameMaze);
	public abstract void solveMaze(String mazeName, String algName);
	public abstract void saveToZip();
	public abstract void loadFromZip();
	public abstract void exit();
	public abstract void moveUp();
	public abstract void moveDown();
	public abstract void moveLeft();
	public abstract void moveRight();
	public abstract void moveBackward();
	public abstract void moveForward();
	
	public abstract Position getPositionFromHash(String nameMaze);

	/**
	 * This method is used to set the notifyObservers with message and object
	 * @param command
	 * @param obj
	 */
	protected void setNotifyObserversName(String command, Object obj) {
		if (obj != null) {
			commandMap.put(command, obj);
		}
		setChanged();
		notifyObservers(command);
	}

	/**
	 * This method is used to get user command
	 * @param command
	 * @return Object
	 */
	@Override
	public Object getUserCommand(String command) {
		return commandMap.get(command);
	}
	
	/**
	 * This method is used to get the properties
	 * @return properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * This method is used to set the properties
	 * @param p
	 */
	public void setProperties(Properties p) {
		this.properties = p;
		ExecutorService thread = threadPool;
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThread()) ;
		thread.shutdown();
	}
}
