package menuAdmin.comandes;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de borrat de les comandes
 *
 */
public class BorrarComandesController implements Initializable {

    @FXML
    ListView<String> listComanda_borrar;
    @FXML
    TextField tfIdComanda;
    @FXML
    TextField tfTaula;
    @FXML
    TextField tfDia;
    @FXML
    AnchorPane apDades;
    @FXML
    Button btnBorrarComanda;

    private String strComandaSeleccionada;

    private List<String> listComandes = new ArrayList<String>();
    private ObservableList<String> obsListComandes = FXCollections.observableList( listComandes );

    ConnexioBD con = new ConnexioBD();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        omplirLlistaComandes();
        listComanda_borrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                strComandaSeleccionada = listComanda_borrar.getSelectionModel().getSelectedItem();
                mostrarDadesComanda();
                apDades.setVisible( true );
                
            }
        });
    }

    /**
     * Mostra totes les dades de una comanda
     */
    private void mostrarDadesComanda() {

        tfIdComanda.setText( strComandaSeleccionada );

        String sQuery = "SELECT dia FROM comanda WHERE id_comanda = " + strComandaSeleccionada;
        try {
            ResultSet rs = con.queryDB( sQuery );
            if( rs.next() ) {
                tfDia.setText(rs.getString("dia"));
            }

            sQuery = "SELECT t.nom AS nom FROM taula AS t INNER JOIN comanda AS c ON t.id_taula = c.id_taula WHERE id_comanda = " + strComandaSeleccionada;
            rs = con.queryDB( sQuery );
            if( rs.next() ) {
                tfTaula.setText(rs.getString("nom"));
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Ompla la llista de comandes
     */
    private void omplirLlistaComandes() {
        apDades.setVisible( false );
        obsListComandes.clear();

        String sQuery = "SELECT id_comanda FROM comanda";

        try {
            ResultSet rs = con.queryDB( sQuery );

            while ( rs.next() ) {
                obsListComandes.add( rs.getString( "id_comanda"));
            }

            rs.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        listComanda_borrar.setItems( obsListComandes );
    }

    @FXML
    /**
     * Borra tots els registres amb el id de la comanda a borrar
     */
    private void onClickBorrar( ActionEvent actionEvent ) {

        String sQuery = "DELETE FROM comandabeguda WHERE id_comanda = " + strComandaSeleccionada + ";";
        String sQuery1 = "DELETE FROM comandaaliment WHERE id_comanda = " + strComandaSeleccionada + ";";
        String sQuery2 = "DELETE FROM comanda WHERE id_comanda = " + strComandaSeleccionada + ";";

        try {
            con.execDB( sQuery );
            con.execDB( sQuery1 );
            con.execDB( sQuery2 );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        omplirLlistaComandes();

    }
}
