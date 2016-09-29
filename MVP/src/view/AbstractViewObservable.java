package view;
/*
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import controller.Command;
import controller.Controller;

public abstract class CommonView implements View{
	
	protected Controller controller;
	protected BufferedReader in;
	protected PrintWriter out;
	
	public CommonView(BufferedReader in, PrintWriter out) {
		this.in=in;
		this.out=out;		
	}
	
	public abstract void start();
	public abstract void displayArr(String[] string);
	public abstract void display(String message);	
	public abstract void displayByteArr(byte[] b);
	public abstract void setCommand(HashMap<String, Command> commandMap);
	public abstract void exit();


	public Controller getController() {
		return controller;
	}
	

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
*/
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

/**
 * The AbstractViewObservable program implements View and inherits Observable.
 * AbstractViewObservable consist from BufferedReader, PrintWriter and HashMap.
 * 
 * @author Reut Sananes & Mor Basson  
 * @version 1.0
 *
 */

public abstract class AbstractViewObservable extends Observable implements View {
	protected BufferedReader in;
	protected PrintWriter out;
	protected HashMap<String, Command> commandMap;
	protected HashMap<Command, Object> hashObj;

/**
 * Constructor
 * @param in
 * @param out
 */
	public AbstractViewObservable(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		this.commandMap = new HashMap<String, Command>();
		this.hashObj = new HashMap<Command, Object>();
	}

	public abstract void start();
	public abstract void displayArr(String[] string);
	public abstract void display(String message);
	public abstract void displayCrossSectionBy(int[][] maze2d);
	public abstract void displaySolution(Solution<Position> solution);
	public abstract void setCommand(HashMap<String, Command> commandMap);
	public abstract void exit();
	public abstract void setProperties(Properties prop);
}