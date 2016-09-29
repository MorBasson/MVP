package view;
/*

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import controller.Command;

public class MyView extends CommonView {

	private CLI cli;

	public MyView(BufferedReader in, PrintWriter out) {
		super(in, out);
	}

	@Override
	public void start() {
		cli.start();
	}


	@Override
	public void displayArr(String[] string) {
		if (string != null) {
			for (int i = 0; i < string.length; i++) {
				out.println(string[i] + " ");
			}
			out.flush();
		} else {
			out.println("null");
			out.flush();
		}
	}


	@Override
	public void display(String message) {
		if (message != null) {
			out.println(message);
			out.flush();
		} else {
			out.println("Error");
			out.flush();
		}
	}


	@Override
	public void displayByteArr(byte[] b) {
		ByteArrayInputStream bArr = new ByteArrayInputStream(b);
		DataInputStream data = new DataInputStream(bArr);
		try {
			int floors = data.readInt();
			int rows = data.readInt();
			int cols = data.readInt();
			
			System.out.println("The start position: " + data.readInt() + "," + data.readInt() + "," + data.readInt());
			System.out.println("The goal position: " + data.readInt() + "," + data.readInt() + "," + data.readInt());
			System.out.println("Maze size: " + floors + "," + rows + "," + cols);
			System.out.println();
			
			for (int i = 0; i <floors; i++) {
				for (int j = 0; j < rows; j++) {
					for (int k = 0; k < cols; k++) {
						System.out.print(data.read());
					}
					System.out.println();
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void setCommand(HashMap<String, Command> commandMap) {
		cli = new CLI(in, out, commandMap);
	}


	@Override
	public void exit() {
		out.println("Everything successfully closed");
		out.flush();
	}
}
*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

/**
 * The MyViewObservableCli program implements an application that realize the methods from
 * AbstractViewObservable. MyViewObservableCli consist from ExecutorService- threadPool.
 * 
 * @author Reut Sananes & Mor Basson  
 * @version 1.0
 *
 *
 */

public class MyViewObservableCli extends AbstractViewObservable {

	private ExecutorService threadPool;

	/**
	 * Constructor
	 * @param in
	 * @param out
	 */
	public MyViewObservableCli(BufferedReader in, PrintWriter out) {
		super(in, out);
		this.threadPool = Executors.newCachedThreadPool();
	}

	/**
	 * This method is use to begin the project. This method will run until we
	 * get "exit"
	 */
	@Override
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					out.println("Please enter command");
					out.flush();
					String line = in.readLine();
					while (!(line.equals("exit"))) {
						setChanged();
						notifyObservers(line);
						try {
							threadPool.awaitTermination(3, TimeUnit.SECONDS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						out.println("Please enter a new command");
						out.flush();
						line = in.readLine();
					}
					setChanged();
					notifyObservers(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();

	}

	/**
	 * This method is use to display an array of Strings. 
	 * @param string[]
	 */
	@Override
	public void displayArr(String[] string) {
		if (string != null) {
			for (String s : string) {
				out.println(s);
			}
			out.flush();
		} else {
			out.println("null");
			out.flush();
		}
	}

	/**
	 * This method is use to display string massage. 
	 * @param massage
	 */
	@Override
	public void display(String message) {
		if (message != null) {
			out.println(message);
			out.flush();
		} else {
			out.println("error");
			out.flush();
		}
	}


	/**
	 * This method is use to display the maze by cross section according to some index. 
	 * @param maze2d[][]
	 */
	@Override
	public void displayCrossSectionBy(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[i].length; j++) {
				out.print(maze2d[i][j]);
			}
			out.println();
			out.flush();
		}
	}

	/**
	 * This method is use to display the solution.
	 * @param solution
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		
		ArrayList<Position> pos=new ArrayList<>();
		pos=solution.getStates();
		
		for(int i=0;i<pos.size();i++)
				{
					System.out.println(pos.get(i));
				}
				
	
	}

	/**
	 * This method is use to update the HashMap.
	 * @param commandMap
	 */
	@Override
	public void setCommand(HashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}

	/**
	 * This method is use to display the maze.
	 * @param maze
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		maze.print();
	}

	/**
	 * This method is use to display the position.
	 * @param position
	 */
	@Override
	public void displayPosition(Position position) {
		out.println(position);
		out.flush();
	}
	
	/**
	 * This method is use to close the project orderly.
	 */
	@Override
	public void exit() {
		out.println("Everything successfully closed");
		out.flush();
	}

	/**
	 * This method is used to set properties
	 * @param prop
	 */
	@Override
	public void setProperties(Properties prop) {
		if (!prop.getChooseView().equals("Command line"))
		{
			setChanged();
			notifyObservers("replaceUserInterface");
		}
		
	}
}