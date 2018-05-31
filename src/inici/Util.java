package inici;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Facilita el canvi de pantalles en l'aplicació.
 */
public class Util {
	static final int[] RESOLUCIO = { 1080, 720 };

	public static void openGUI(Scene scene, Stage stage, String title) {
		Main.scene = scene;
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}
	
	public static void openGUI(Scene scene, Stage stage, StageStyle style, String title) {
		Main.scene = scene;
		stage.setScene(scene);
		stage.setTitle(title);
		stage.initStyle(style);
		stage.show();

	}
}
