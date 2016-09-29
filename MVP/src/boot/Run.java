package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import guiProperties.BasicWindowGui;
import guiProperties.MessegeWindow;
import guiView.MyViewObservableGui;
import model.MyModelObservable;
import presenter.Presenter;
import presenter.Properties;
import view.AbstractViewObservable;
import view.MyViewObservableCli;

/**
 * The Run program implements an application that contains the main method.
 * The main method start the whole MVP project.
 * @author Mor Basson & Reut Sananes 
 * @version 1.0
 *
 */

public class Run {
	public static void main(String[] args) {

		Thread windowProperties = new Thread(new Runnable() {

			@Override
			public void run() {

				BasicWindowGui window = new MessegeWindow("Properties Game", 500, 400);
				window.run();
			}
		});

		Thread windowMain = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					windowProperties.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Properties properties = new Properties();
				try {
					XMLDecoder dXml = new XMLDecoder(new BufferedInputStream(new FileInputStream("Properties.xml")));
					properties = (Properties) dXml.readObject();
					dXml.close();
				} catch (FileNotFoundException e) {
					System.out.println("file not found, default properties will be loaded");
					properties = new Properties();
					properties.defaultProp();
				}
				MyModelObservable model = new MyModelObservable();
				//properties.defaultProp();
				model.setProperties(properties);
				AbstractViewObservable view = null;

				switch (properties.getChooseView()) {
				case "Gui":
					view = new MyViewObservableGui("maze3d", 800, 600,
							new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
					break;
				case "Cli":
					view = new MyViewObservableCli(new BufferedReader(new InputStreamReader(System.in)),
							new PrintWriter(System.out));
					break;
				default:
					view = new MyViewObservableGui("maze3d", 800, 600,
							new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
					break;
				}
				Presenter presenter = new Presenter(view, model);
				presenter.setProperties(properties);
				model.addObserver(presenter);
				view.addObserver(presenter);
				view.start();
			}
		});
		windowProperties.run();
		windowMain.run();

	}
}