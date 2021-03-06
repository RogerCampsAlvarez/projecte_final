package administracio;

import inici.ConnexioBD;
import inici.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuTreballadorController implements Initializable{
    Pane root;
    Scene scene;
    Stage stage;
    ConnexioBD con = new ConnexioBD();

    @FXML
    private Button bDesconectar;
    @FXML
    private Button bBarra;
    @FXML
    private Button bCuina;
    @FXML
    private AnchorPane anchorPane1;


    @FXML
    /**
     * Obra en el anchor pane la gestió de la barra
     *
     */
    void cmdBarra(ActionEvent event) {
        canviarEstatEsperant();
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuTreballador/Barra.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    /**
     * Obra en el anchor pane la gestió de la cuina
     *
     */
    void cmdCuina(ActionEvent event) {
        canviarEstatEsperant();
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuTreballador/Cuina.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Desconnecta la sessió del usuari i et porta a la pastanya de login
     *
     */
    void cmdDesconectar(ActionEvent event) {
        canviarEstatEsperant();
        try {
            root = FXMLLoader.load(getClass().getResource("/inici/Login.fxml"));
            scene = new Scene(root);
            stage = (Stage) bDesconectar.getScene().getWindow();
            Util.openGUI(scene, stage, "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Canvia els plats i les begudes que estiguin en estat agafat en estat esperant
     *
     */
    void canviarEstatEsperant(){
        con.execDB("update comandabeguda set estat = 'esperant' where estat = 'agafat'");
        con.execDB("update comandaaliment set estat = 'esperant' where estat = 'agafat'");
    }
}