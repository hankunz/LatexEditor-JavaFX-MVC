package ch.latexCreator.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class provides connection between java codes and the GUI, it loads the
 * main GUI with javaFX framework.
 * 
 * Some codes are based on JavaFX tutorial:
 * http://code.makery.ch/library/javafx-8-tutorial/
 * 
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("zz LaTex Editor");

		initRootLayout();

		showGUI();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the Document overview inside the root layout.
	 */
	public void showGUI() {
		try {
			// Load Document overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/GUI.fxml"));
			AnchorPane GUI = (AnchorPane) loader.load();

			// Set Document overview into the center of root layout.
			rootLayout.setCenter(GUI);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}