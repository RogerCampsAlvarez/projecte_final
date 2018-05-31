package inici;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	static Scene scene;
	public static boolean isSplashLoaded = false;

	@Override
	/**
	 * Mostra la pantalla splash
	 */
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Splash.fxml"));
			scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);

			new SplashController().initialize(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Scene getMainScene() {
		return scene;
	}
}