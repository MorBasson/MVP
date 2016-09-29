package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.MazeAdapter;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * The MyModelObservable program implements an application that realize the methods from
 * AbstractModelObservable.
 * MyModelObservable consist from HashMap.
 * 
 * @author Reut sananes & Mor basson  
 * @version 1.0 
 */

public class MyModelObservable extends AbstractModelObservable {

	private HashMap<Maze3d, Solution<Position>> mazeSolutionMap;
	private HashMap<String, Position> hashPosition;
	private HashMap<String, Maze3d> mazes;
	private ExecutorService executor=Executors.newFixedThreadPool(2);


	/**
	 * Constructor
	 */
	public MyModelObservable() {
		super();
		this.mazeSolutionMap = new HashMap<Maze3d, Solution<Position>>();
		this.hashPosition = new HashMap<String, Position>();
		this.mazes=new HashMap<String,Maze3d>();
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThread());

		try {
			loadFromZip();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to create maze3d by Recursive Backtracker algorithm.
	 * @param nameMaze
	 */
	@Override
	public void generate(String nameMaze) {
		String name = properties.getNameMaze();
		int floor = properties.getSizeY();
		int row = properties.getSizeY();
		int col= properties.getSizeY();
		Future<Maze3d> fCallMaze = threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d myMaze = new GrowingTreeGenerator().generate(floor, row, col);
				return myMaze;
			}
		});
		try {
			hashMaze.put(name, fCallMaze.get());
			hashPosition.put(name, fCallMaze.get().getStart());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		setNotifyObserversName("mazeIsReady", getMaze3d(name));
	}

	
	/**
	 * This method is used to get the maze3d.
	 * @param Maze3d
	 */
	@Override
	public Maze3d getMaze3d(String nameMaze) {
		Maze3d myMaze = hashMaze.get(nameMaze);
		return myMaze;

	}

	/**
	 * This method is used to get cross section by y / x / z axis.
	 * @param by
	 * @param nameMaze
	 * @param index
	 */
	@Override
	public void getMazeCrossSectionBy(String by, String nameMaze, int index) {
		String name = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);

		try {
			switch (by) {

			case "Y":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByY(index));
				break;
			case "y":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByY(index));
				break;
			case "X":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByX(index));
				break;
			case "x":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByX(index));
				break;
			case "Z":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByZ(index));
				break;
			case "z":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByZ(index));
				break;
			default:
				setChanged();
				notifyObservers("Invalid parametars");
				return;
			}
		} catch (IndexOutOfBoundsException e) {
			setChanged();
			notifyObservers("Invalid index");
			return;
		}
	}

	/**
	 * This method is use to save the maze into a file. 
	 * @param nameMaze
	 * @param fileName
	 */
	@Override
	public void saveMaze(String fileName) {
		String name = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(myMaze.toByteArray());
			out.close();
			setNotifyObserversName("saveMaze", fileName);
		} catch (FileNotFoundException e) {
			setNotifyObserversName("Invalid file", fileName);
		} catch (IOException e) {
			setNotifyObserversName("Invalid compress", name);
		}
	}

	/**
	 * This method is use to load the maze from a file.
	 * @param fileName
	 * @param nameMaze
	 */
	@Override
	public void loadMaze(String fileName, String nameMaze) {

		properties.setNameMaze(nameMaze);
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte[] bArr = new byte[34586];
			int numByte = in.read(bArr);
			in.read(bArr);
			in.close();
			byte[] newbArr = new byte[numByte];
			for (int i = 0; i < newbArr.length; i++) {
				newbArr[i] = bArr[i];
			}
			Maze3d myMaze = new Maze3d(bArr);
			hashMaze.put(nameMaze, myMaze);
			hashPosition.put(nameMaze, myMaze.getStart());
			setNotifyObserversName("loadMaze", getMaze3d(nameMaze));

		} catch (FileNotFoundException e) {
			setNotifyObserversName("Invalid file", fileName);

		} catch (IOException e) {
			setNotifyObserversName("Invalid maze", nameMaze);
		}
	}

	/**
	 * This method is use to solve the maze by some algorithm.
	 * @param nameMaze
	 */
	public void solveMaze(String mazeName, String algName) {
		
			//	Thread thread=new Thread(new Runnable() {

			//		@Override
			//		public void run() {

			Future<Solution<Position>> sol = executor.submit (new Callable<Solution<Position>> (){

				@Override
				public Solution<Position> call() throws Exception {

					if (!mazes.containsKey(mazeName)){
						setChanged();
						notifyObservers("The maze isn't exist\n");
					}else{
						if((!algName.equals("DFS"))&&(!algName.equals("BFS"))){
							setChanged();
							notifyObservers("The algorithm is not exist\n");

						}else{
							CommonSearcher<Position> searcher;
							Maze3d maze=mazes.get(mazeName);
							MazeAdapter m=new MazeAdapter(maze);
							ArrayList<Position> s;
							if(algName.equals("DFS")){
								searcher=new DFS<Position>();
								s=searcher.search(m).getStates();
								Solution<Position> temp=new Solution<Position>(s);
								//mazeSolutionMap.put(mazeName, temp);
								mazeSolutionMap.put(maze,temp);
								saveSolution(mazeSolutionMap.get(mazes.get(mazeName)));
								return temp;
								//							setChanged();
								//							notifyObservers("The solution of the maze "+mazeName+" is ready\n" );
							}
							if(algName.equals("BFS")){
								searcher=new BFS<Position>();
								s=searcher.search(m).getStates();
								Solution<Position> temp=new Solution<Position>(s);
								//solutions.put(mazeName, temp);
								mazeSolutionMap.put(maze,temp);
								return temp;
								//							setChanged();
								//							notifyObservers("The solution of thw maze "+mazeName+" is ready\n" );
							}
						}
					}

					return null;

				}
			});
			try {
				Solution<Position>temp=sol.get();
				if(temp.equals(null))
					return;
				else{
					setChanged();
					notifyObservers("The solution of the maze "+mazeName+" is ready\n");
				}

			} catch (InterruptedException | ExecutionException e) {

				e.printStackTrace();
			}


		}
		
		/*
		String name= properties.getNameMaze();
		String algo= properties.getAlgorithm();
		Maze3d myMaze = hashMaze.get(name);
		MazeAdapter ma= new MazeAdapter(myMaze);
	
		if (myMaze != null) {
			if (!(mazeSolutionMap.containsKey(myMaze))) {
				Future<Solution<Position>> fCallSolution = threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						searcher<Position> algorithms;
						Searchable<Position> mazeSearchable = new Searchable<Position>(ma);
						Solution<Position> solution = new Solution<Position>();

						switch (algo) {

						case "BFS":
							algorithms = new BFS<Position>();
							solution = algorithms.search(mazeSearchable);
							break;

						case "DFS":
							algorithms = new DFS<Position>();
							solution = algorithms.search(mazeSearchable);
							break;

						
						default:
							setChanged();
							notifyObservers("Invalid algorithm");
							return null;
						}
						return solution;
					}
				});

				try {
					mazeSolutionMap.put(myMaze, fCallSolution.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			setNotifyObserversName("solutionIsReady", name);
		} else {
			setNotifyObserversName("null", name);
		}
		
		*/
	

	/**
	 * This method is used to get maze solution 
	 * @param nameMaze
	 * @return solution
	 */
	@Override
	public Solution<Position> getMazeSolution(String nameMaze) {
		String name= properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);
		if (myMaze == null) {
			setNotifyObserversName("null", name);
			return null;
		} else {
			Solution<Position> solution = mazeSolutionMap.get(myMaze);
			return solution;
		}
	}

	/**
	 * This method is used to save the maze details to zip file
	 */
	@Override
	public void saveToZip() {
		try {
			ObjectOutputStream mazeOut = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream("MazeZip.zip")));
			mazeOut.writeObject(hashMaze);
			mazeOut.writeObject(mazeSolutionMap);
			mazeOut.flush();
			mazeOut.close();
			setNotifyObserversName("saveZip", "MazeZip.zip");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to load the maze details from zip file
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void loadFromZip() {

		try {

			FileInputStream fileMaze = new FileInputStream("MazeZip.zip");
			ObjectInputStream mazeIn = new ObjectInputStream(new GZIPInputStream(fileMaze));
			hashMaze = (HashMap<String, Maze3d>) mazeIn.readObject();
			mazeSolutionMap = (HashMap<Maze3d, Solution<Position>>) mazeIn.readObject();
			mazeIn.close();
			setNotifyObserversName("loadZip","MazeZip.zip");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * This method is use to close the project orderly.
	 */
	@Override
	public void exit() {
		saveToZip();
		threadPool.shutdownNow();
		try {
			while (!(threadPool.awaitTermination(10, TimeUnit.SECONDS)))
				;
			setChanged();
			notifyObservers("exit");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to get the maze solution from the HashMap
	 * @return HashMap
	 */
	public HashMap<Maze3d, Solution<Position>> getMazeSolutionMap() {
		return mazeSolutionMap;
	}

	/**
	 * This method is used to set the maze solution of the HashMap
	 * @param mazeSolutionMap
	 */
	public void setMazeSolutionMap(HashMap<Maze3d, Solution<Position>> mazeSolutionMap) {
		this.mazeSolutionMap = mazeSolutionMap;
	}

	/**
	 * This method is used to get the position from the HashMap
	 * @return HashMap
	 */
	public HashMap<String, Position> getHashPosition() {
		return hashPosition;
	}

	/**
	 * This method is used to set the position of the HashMap
	 * @param hashPosition
	 */
	public void setHashPosition(HashMap<String, Position> hashPosition) {
		this.hashPosition = hashPosition;
	}

	/**
	 * This method is used to update the position according to "up" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveUp() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Up") {
				position.setY(position.getY() - 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}
	}

	/**
	 * This method is used to update the position according to "down" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveDown() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Down") {
				position.setY(position.getY() + 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "left" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveLeft() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Left") {
				position.setZ(position.getZ() - 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "right" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveRight() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Right") {
				position.setZ(position.getZ() + 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "backward" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveBackward() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Backward") {
				position.setX(position.getX() - 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "forward" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveForward() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Forward") {
				position.setX(position.getX() + 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}
	}

	/**
	 * This method is used to get specific position from the hash
	 * @param nameMaze
	 * @return Position
	 */
	@Override
	public Position getPositionFromHash(String nameMaze) {
		return hashPosition.get(nameMaze);
	}

	public void saveSolution(Solution<Position>sol) throws FileNotFoundException, IOException{
		try{
			File file=new File("solutions");
			ObjectOutputStream output;
			output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
			output.writeObject(sol.toString());
			output.flush();
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}

	}



}