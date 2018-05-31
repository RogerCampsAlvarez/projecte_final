package inici;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Pantalla de logueig
 *
 */
public class LoginController implements Initializable{
	ResultSet rs;
	ConnexioBD con;
	Pane root;
	Scene scene;
	Stage stage;
    Alert alert = new Alert(Alert.AlertType.ERROR);


	@FXML
	private Button bContinuar;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField userField;


	@FXML
	/**
	 * Inicia sessió del usuari entrat
	 *
	 */
	void cmdContinuar(ActionEvent event){
		int cont = 0;

		try {
			rs = con.queryDB("select * from usuaris");

			while (rs.next()){
                if (rs.getString("usuari").equals(userField.getText()) && rs.getString("contrasenya").equals(passwordField.getText())){
                	cont ++;
				}
            }

			//usuari o contrasenya incorrecta
			if (cont == 0){
				alert.setTitle("Login");
				alert.setHeaderText("Usuari o contrasenya incorrecta");
				alert.show();
			}
			else {
                ResultSet rs = con.queryDB("Select count(*) as count from usuaris where usuari = '" + userField.getText() + "' and administracio = true");

                //si te configurat com a administració
                if (rs.next()) {
                    if (rs.getInt("count") != 0) {
                        root = FXMLLoader.load(getClass().getResource("/administracio/MenuAdministracio.fxml"));
                        scene = new Scene(root);
                        stage = (Stage) bContinuar.getScene().getWindow();
                        Util.openGUI(scene, stage, "Administracio");
                    } else {
                        root = FXMLLoader.load(getClass().getResource("/administracio/MenuTreballador.fxml"));
                        scene = new Scene(root);
                        stage = (Stage) bContinuar.getScene().getWindow();
                        Util.openGUI(scene, stage, "Treballador");
                    }
                }
            }
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con = new ConnexioBD();
		userField.setText("admin");
		passwordField.setText("admin");
	}

	@FXML
	/**
	 * Tenca l'aplicació
	 */
	private void cmdExit(){
		System.exit(0);
	}
}