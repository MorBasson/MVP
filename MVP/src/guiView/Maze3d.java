package guiView;

import java.util.ArrayList;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;

/**
 * The Maze3d program implements an application that inherits MazeDisplayer.
 * The class is responsible to the view of the maze in the play window.
 * Maze3d consist from:
 * 1. int if: index, pos1, pos2, goal1, goal2, goal3. 
 * 2. char axis.
 * 3. Image of: imagePlayer, imageWin, imageTrace, imageEnd, imageBackground, imageWall.
 * 4. boolean bool
 * 
 * @author Reut sananes & Mor basson  
 * @version 1.0
 *
 */

public class Maze3d extends MazeDisplayer {

	protected int index;
	protected char axis;
	protected Image imagePlayer, imageWin, imageTrace, imageEnd, imageBackground, imageWalls;
	protected int pos1, pos2;
	protected int goal1, goal2, goal3;
	protected boolean bool;


	/**
	 * Constructor
	 * @param parent
	 * @param style
	 * @param axis
	 */
	public Maze3d(Composite parent, int style, char axis) {
		super(parent, style);
		this.axis = axis;
		this.imagePlayer = new Image(getDisplay(), "resurces/hyal.jpg");
		this.imageWin = new Image(getDisplay(), "resurces/newfly.png");
		this.imageTrace = new Image(getDisplay(), "resurces/trace.png");
		this.imageEnd = new Image(getDisplay(), "resurces/wins.png");
		this.imageBackground= new Image(getDisplay(), "resurces/2099117_759.jpg");
		this.imageWalls= new Image(getDisplay(), "resurces/הורד.jpg");
		// set a white background (red, green, blue)
		//setBackground(new Color(null, 238, 216, 174));
		setBackgroundImage(imageBackground);
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
//				e.gc.setForeground(new Color(null, 0, 0, 0));
//				e.gc.setBackground(new Color(null, 193, 205, 205));
				ArrayList<Position> Solution=null;
				if (solution != null) {
					bool = true;
					Solution = solution.getStates();
				} else {
					bool = false;
				}
				int width = getSize().x;
				int height = getSize().y;

				int[][] maze2d;
				if (myMaze != null && currPosition != null) {
					switch (axis) {

					case 'y':
						index = currPosition.getY();
						maze2d = myMaze.getCrossSectionByY(index);
						pos1 = currPosition.getX();
						pos2 = currPosition.getZ();
						goal1 = myMaze.getGoal().getX();
						goal2 = myMaze.getGoal().getZ();
						goal3 = myMaze.getGoal().getY();
						break;

					case 'x':
						index = currPosition.getX();
						maze2d = myMaze.getCrossSectionByX(index);
						pos1 = currPosition.getY();
						pos2 = currPosition.getZ();
						goal1 = myMaze.getGoal().getY();
						goal2 = myMaze.getGoal().getZ();
						goal3 = myMaze.getGoal().getX();
						break;

					case 'z':
						index = currPosition.getZ();
						maze2d = myMaze.getCrossSectionByZ(index);
						pos1 = currPosition.getY();
						pos2 = currPosition.getX();
						goal1 = myMaze.getGoal().getY();
						goal2 = myMaze.getGoal().getX();
						goal3 = myMaze.getGoal().getZ();
						break;

					default:
						index = currPosition.getY();
						maze2d = myMaze.getCrossSectionByY(index);
						pos1 = currPosition.getX();
						pos2 = currPosition.getZ();
						goal1 = myMaze.getGoal().getX();
						goal2 = myMaze.getGoal().getZ();
						goal3 = myMaze.getGoal().getY();
						break;
					}
					Position tempPos = new Position(0, 0, 0);
					int h = height / maze2d.length;
					int w = width / maze2d[0].length;
					if (bool == true && !Solution.isEmpty()) {
						for(int i=0;i<Solution.size();i++){
							tempPos = Solution.get(i);
						}
					}
					for (int i = 0; i < maze2d.length; i++) {
						for (int j = 0; j < maze2d[i].length; j++) {
							int y = i * h;
							int x = j * w;
							if (maze2d[i][j] == 0)
								 e.gc.fillRectangle(x,y,w,h);
							else{
								e.gc.drawImage(imageWalls, 0, 0,	imageWalls.getBounds().width, imageWalls.getBounds().height, x, y, w, h);
							}
							if (i == pos1 && j == pos2) {
								e.gc.drawImage(imagePlayer, 0, 0, imagePlayer.getBounds().width, imagePlayer.getBounds().height, x, y, w,
										h);
							}
							if (i == goal1 && j == goal2 && index == goal3) {
								e.gc.drawImage(imageWin, 0, 0, imageWin.getBounds().width, imageWin.getBounds().height,
										x, y, w, h);
							}
							if (bool == true) {
								Position tPos = new Position(tempPos.getY(),tempPos.getX(),tempPos.getZ());
								switch (axis) {
								case 'y':
									tPos = new Position(index, i, j);
									if (Solution.contains(tPos) && !(tPos.equals(myMaze.getGoal()))) {
										e.gc.drawImage(imageTrace, 0, 0, imageTrace.getBounds().width,
												imageTrace.getBounds().height, x, y, w, h);
									}
									break;
								case 'x':
									tPos = new Position(i, index, j);
									if (Solution.contains(tPos) && !(tPos.equals(myMaze.getGoal()))) {
										e.gc.drawImage(imageTrace, 0, 0, imageTrace.getBounds().width,
												imageTrace.getBounds().height, x, y, w, h);
									}
									break;
								case 'z':
									tPos = new Position(i, j, index);
									if (Solution.contains(tPos) && !(tPos.equals(myMaze.getGoal()))) {
										e.gc.drawImage(imageTrace, 0, 0, imageTrace.getBounds().width,
												imageTrace.getBounds().height, x, y, w, h);
									}
									break;

								}
							}

							if (currPosition.equals(myMaze.getGoal())) {
								e.gc.drawImage(imageEnd, 0, 0, imageEnd.getBounds().width, imageEnd.getBounds().height,
										0, 0, getSize().x, getSize().y);
							}
							
							}
						}
					}
				}
			});

		}

	}