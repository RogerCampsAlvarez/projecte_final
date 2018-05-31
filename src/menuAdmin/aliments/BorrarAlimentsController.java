package menuAdmin.aliments;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de borrat dels aliments
 *
 */
public class BorrarAlimentsController implements Initializable {
    @FXML
    ListView<String> listPlats_borrar;
    @FXML
    Button bEliminarPlat;

    private String strPlatSeleccionat;

    private List<String> listPlat = new ArrayList<String>();
    private ObservableList<String> obsListPlat = FXCollections.observableList(listPlat);

    ConnexioBD con = new ConnexioBD();


    /**
     * Ompla la llista dels plats a la list view
     */
    private void omplirLlistPlats() {
        bEliminarPlat.setVisible( false );
        obsListPlat.clear();

        System.out.println("Buscant a la base de dades...");

        String sQuery = "SELECT nom FROM aliments WHERE NOT id_aliment IN ( SELECT id_aliment FROM comandaaliment )";
        try {
            ResultSet rs = con.queryDB( sQuery );
            while (rs.next()) {
                obsListPlat.add( rs.getString("nom"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");

        listPlats_borrar.setItems( obsListPlat );
    }

    @FXML
    /**
     * Borra el aliment seleccionat
     *
     */
    public void cmdBorrar(){

        String sQuery = "DELETE FROM aliments WHERE nom = '" + strPlatSeleccionat + "';";

        try {
            con.execDB( sQuery );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        omplirLlistPlats();

    }

    @FXML
    /**
     * S'executa al clickar al botó d'actualitzar
     *
     */
    public void onClickActualitzar(ActionEvent actionEvent ) {
        omplirLlistPlats();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        omplirLlistPlats();
        listPlats_borrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                strPlatSeleccionat = listPlats_borrar.getSelectionModel().getSelectedItem();
                bEliminarPlat.setVisible( true );

            }
        });
    }
}
