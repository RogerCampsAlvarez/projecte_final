package menuAdmin.comandes;

import combos.Estat;
import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import objectes.Beguda;
import objectes.Comanda;
import objectes.Plat;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class VisualitzarComandesController implements Initializable{
    @FXML
    ComboBox cbEstat;
    @FXML
    ListView<String> listComandes;
    @FXML
    ListView<String> listAliment;
    @FXML
    ListView<String> listBeguda;
    @FXML
    Label lTaula;
    @FXML
    Label lDia;

    Estat e = new Estat();
    List<String> llista_comandes = new ArrayList<String>();
    ObservableList<String> obsListComandes = FXCollections.observableList(llista_comandes);
    List<String> llista_aliment = new ArrayList<String>();
    ObservableList<String> obsListAliment = FXCollections.observableList(llista_aliment);
    List<String> llista_beguda = new ArrayList<String>();
    ObservableList<String> obsListBeguda = FXCollections.observableList(llista_beguda);
    ConnexioBD con = new ConnexioBD();
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;

    private String strIdComanda;


    @FXML
    public void cmdBuscarEstat() throws SQLException, ClassNotFoundException {
        //borrarTableViews();

        //si l'estat es finalitzat
        if (cbEstat.getSelectionModel().getSelectedItem().toString().equals("Finalitzat")){
            buscarComandes(true);
        }
        //si l'estat es no finalitzat
        else{
            buscarComandes(false);
        }
    }

    private void buscarComandes(boolean finalitzada) throws ClassNotFoundException, SQLException {
        obsListComandes.clear();

        if (finalitzada){
            System.out.println("Buscant a la base de dades...");
            try {
                rs = con.queryDB(
                        "select c.id_comanda as id_comanda, t.nom as nom_taula " +
                                "from comanda c inner join taula t on c.id_taula = t.id_taula " +
                                "where c.finalitzada = true order by dia"
                );
                while (rs.next()) {
                    String strComanda = rs.getString( "id_comanda");

                    obsListComandes.add( strComanda);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Ha acavat de buscar a la base de dades");
        }
        else {
            System.out.println("Buscant a la base de dades...");
            try {
                rs = con.queryDB(
                        "select c.id_comanda as id_comanda, t.nom as nom_taula " +
                                "from comanda c inner join taula t on c.id_taula = t.id_taula " +
                                "where c.finalitzada = false order by dia"
                );
                while (rs.next()) {
                    String strComanda = rs.getString( "id_comanda");

                    obsListComandes.add( strComanda);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            listComandes.setItems( obsListComandes );
            System.out.println("Ha acavat de buscar a la base de dades");
        }
    }


    private void pujarATableviewComandes(String strComanda) {
        llista_comandes.add( strComanda );
        listComandes.setItems(obsListComandes);
    }

    private void borrarTableViews(){
        //llistaBegudes.clear();
        //taula_begudes.setItems(llistaBegudes);
        //llista_comandes.clear();
        //taula_comandes.setItems(obsListComandes);
        //llista_aliments.clear();
        //taula_aliments.setItems(llistaAliments);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> estat =
                FXCollections.observableArrayList(e.getEstat());
        cbEstat.getItems().addAll(estat);

        listComandes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                strIdComanda = listComandes.getSelectionModel().getSelectedItem();
                mostrarDades();
                mostrarAliments();
                mostrarBegudes();

            }
        });
    }

    private void mostrarBegudes() {
        obsListBeguda.clear();
        System.out.println("Buscant a la base de dades...");
        String sQuery = "SELECT nom FROM begudes AS b INNER JOIN comandabeguda AS cb ON b.id_beguda = cb.id_beguda WHERE cb.id_comanda = " + strIdComanda;
        try {
            ResultSet rs = con.queryDB( sQuery);
            while (rs.next()) {
                String strBeguda = rs.getString( "nom");

                obsListBeguda.add( strBeguda );
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");

        listBeguda.setItems( obsListBeguda );
    }

    private void mostrarAliments() {
        obsListAliment.clear();
        System.out.println("Buscant a la base de dades...");
        String sQuery = "SELECT nom FROM aliments AS a INNER JOIN comandaaliment AS ca ON a.id_aliment = ca.id_aliment WHERE ca.id_comanda = " + strIdComanda;
        try {
            ResultSet rs = con.queryDB( sQuery);
            while (rs.next()) {
                String strAliment = rs.getString( "nom");

                obsListAliment.add( strAliment );
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");

        listAliment.setItems( obsListAliment );
    }

    private void mostrarDades() {

        String sQuery = "SELECT t.nom, c.dia FROM taula AS t, comanda AS c WHERE t.id_taula = c.id_taula AND c.id_comanda =" + strIdComanda;

        try {
            ResultSet rs = con.queryDB( sQuery );
            if( rs.next()) {
                lTaula.setText( rs.getString( "nom" ) );
                lDia.setText( rs.getString("dia") );

            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}
