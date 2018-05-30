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
        apDades.setVisible( false );
        listComanda_borrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                strComandaSeleccionada = listComanda_borrar.getSelectionModel().getSelectedItem();
                mostrarDadesComanda();
                apDades.setVisible( true );
                
            }
        });
    }

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

    private void omplirLlistaComandes() {
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
    private void onClickBorrar( ActionEvent actionEvent ) {

        String sQuery = "DELETE FROM comandabeguda WHERE id_comanda = " + strComandaSeleccionada + ";";
        String sQuery1 = "DELETE FROM comandaaliment WHERE id_comanda = " + strComandaSeleccionada + ";";
        String sQuery2 = "DELETE FROM comanda WHERE id_comanda = " + strComandaSeleccionada + ";";

        try {
            con.queryDB( sQuery );
            con.queryDB( sQuery1 );
            con.queryDB( sQuery2 );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}
