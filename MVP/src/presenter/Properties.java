package presenter;

import java.io.Serializable;

/**
 * The Properties program implements Serializable.
 * Properties consist from int, String and char.
 * @author Reut sananes & Mor basson  
 * @version 1.0 
 */
public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sizeY;
	private int sizeX;
	private int sizeZ;
	private int numOfThread;
	private String algorithm;
	private String typeOfMaze;
	private String nameMaze;
	private String chooseView;
	private char axis;

	/**
	 * Constructor
	 */
	public Properties() {
		super();
	}
	
	/**
	 * This method is used to update default properties data
	 */
	public void defaultProp(){
		this.sizeY = 2;
		this.sizeX = 2;
		this.sizeZ = 2;
		this.numOfThread = 5;
		this.algorithm = "AirDistance";
		this.typeOfMaze = "MyMaze3dGenerator";
		this.nameMaze = "mymaze";
		this.chooseView="Cli";
		this.axis='y';
	}

	/**
	 * This method is used to get the Y size 
	 * @return int
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * This method is used to set the Y size 
	 * @param sizeY
	 */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	/**
	 * This method is used to get the X size
	 * @return int
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * This method is used to set the X size
	 * @param sizeX
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	/**
	 * This method is used to get the Z size
	 * @return int
	 */
	public int getSizeZ() {
		return sizeZ;
	}

	/**
	 * This method is used to set the Z size
	 * @param sizeZ
	 */
	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}

	/**
	 * This method is used to get the number of thread
	 * @return int
	 */
	public int getNumOfThread() {
		return numOfThread;
	}

	/**
	 * This method is used to set the number of thread
	 * @param numOfThread
	 */
	public void setNumOfThread(int numOfThread) {
		this.numOfThread = numOfThread;
	}

	/**
	 * This method is used to get the algorithm
	 * @return String
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * This method is used to set the algorithm
	 * @param algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * This method is used to get the maze type
	 * @return String
	 */
	public String getTypeOfMaze() {
		return typeOfMaze;
	}

	/**
	 * This method is used to set the maze type
	 * @param typeOfMaze
	 */
	public void setTypeOfeMaze(String typeOfMaze) {
		this.typeOfMaze = typeOfMaze;
	}

	/**
	 * This method is used to get the maze name 
	 * @return String
	 */
	public String getNameMaze() {
		return nameMaze;
	}

	/**
	 * This method is used to set the maze name
	 * @param nameMaze
	 */
	public void setNameMaze(String nameMaze) {
		this.nameMaze = nameMaze;
	}

	/**
	 * This method is used to get the chosen view
	 * @return String
	 */
	public String getChooseView() {
		return chooseView;
	}

	/**
	 * This method is used to set the chosen view
	 * @param chooseView
	 */
	public void setChooseView(String chooseView) {
		this.chooseView = chooseView;
	}

	/**
	 * This method is used to get the axis
	 * @return char
	 */
	public char getAxis() {
		return axis;
	}

	/**
	 * This method is used to set the axis
	 * @param axis
	 */
	public void setAxis(char axis) {
		this.axis = axis;
	}
}