package menuAdmin.comptabilitat;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import objectes.CaixaDiaria;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de comptabilitat
 *
 */
public class ComptabilitatController implements Initializable {

    @FXML
    Button btnCaixaAvui;
    @FXML
    TableView<CaixaDiaria> taulaCaixa;
    @FXML
    DatePicker calendariCaixa;
    @FXML
    Button btnCaixaDia;

    private List<CaixaDiaria> listCaixa = new ArrayList<>();
    private ObservableList<CaixaDiaria> obsListCaixa = FXCollections.observableList(listCaixa);
    ConnexioBD con = new ConnexioBD();

    @FXML
    /**
     * Calcula la caixa
     */
    private void cmdCalcularCaixa( ActionEvent actionEvent ) {
        obsListCaixa.clear();
        Date todaysDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dia = df.format( todaysDate );
        double caixa = 0.00d;

        String sQuery = "SELECT SUM(preu) AS caixa FROM aliments AS a INNER JOIN comandaaliment AS ca ON a.id_aliment = ca.id_aliment INNER JOIN comanda AS c ON ca.id_comanda = c.id_comanda WHERE c.dia = now()::date";

        try {
            ResultSet rs = con.queryDB( sQuery );
            if( rs.next() ) {
                caixa += rs.getDouble("caixa");
            }
            sQuery = "SELECT SUM(preu) AS caixa FROM begudes AS b INNER JOIN comandabeguda AS cb ON b.id_beguda = cb.id_beguda INNER JOIN comanda AS c ON cb.id_comanda = c.id_comanda WHERE c.dia = now()::date";
            rs = con.queryDB( sQuery );
            if( rs.next() ) {
                caixa += rs.getDouble("caixa");
            }

            rs.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        System.out.println( dia + " " + String.valueOf( caixa ) );

        obsListCaixa.add( new CaixaDiaria( dia , String.valueOf( caixa ) ));
        taulaCaixa.setItems(obsListCaixa);

    }

    @FXML
    /**
     * Calcular caixa del dia seleccionat
     */
    private void cmdCalcularCaixaDia( ActionEvent actionEvent ) {
        obsListCaixa.clear();
        String dia = calendariCaixa.getValue().toString();
        double caixa = 0.00d;

        String sQuery = "SELECT SUM(preu) AS caixa FROM aliments AS a INNER JOIN comandaaliment AS ca ON a.id_aliment = ca.id_aliment INNER JOIN comanda AS c ON ca.id_comanda = c.id_comanda WHERE c.dia = '" + dia + "'::date";

        try {
            ResultSet rs = con.queryDB( sQuery );
            if( rs.next() ) {
                caixa += rs.getDouble("caixa");
            }
            sQuery = "SELECT SUM(preu) AS caixa FROM begudes AS b INNER JOIN comandabeguda AS cb ON b.id_beguda = cb.id_beguda INNER JOIN comanda AS c ON cb.id_comanda = c.id_comanda WHERE c.dia = '" + dia + "'::date";
            rs = con.queryDB( sQuery );
            if( rs.next() ) {
                caixa += rs.getDouble("caixa");
            }

            rs.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println( dia + " " + String.valueOf( caixa ) );

        obsListCaixa.add( new CaixaDiaria( dia , String.valueOf( caixa ) ));
        taulaCaixa.setItems(obsListCaixa);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
