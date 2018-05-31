package menuAdmin.comandes;

import com.sun.org.apache.xpath.internal.operations.Bool;
import combos.Estat;
import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
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
    TableView taula_comandes;
    @FXML
    TableView taula_aliments;
    @FXML
    TableView taula_begudes;

    Estat e = new Estat();
    List<Comanda> llista_comandes = new ArrayList<Comanda>();
    ObservableList<Comanda> llistaComandes = FXCollections.observableList(llista_comandes);
    List<Plat> llista_aliments = new ArrayList<Plat>();
    ObservableList<Plat> llistaAliments = FXCollections.observableList(llista_aliments);
    List<Beguda> llista_begudes = new ArrayList<Beguda>();
    ObservableList<Beguda> llistaBegudes = FXCollections.observableList(llista_begudes);
    ConnexioBD con = new ConnexioBD();
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;



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
        int id_comanda;
        Date dia;
        String nom_taula;
        Boolean finalitzada_comanda;

        if (finalitzada){
            System.out.println("Buscant a la base de dades...");
            try {
                rs = con.queryDB(
                        "select c.id_comanda as id_comanda, c.dia as dia, c.finalitzada as finalitzada, t.nom as nom_taula " +
                                "from comanda c inner join taula t on c.id_taula = t.id_taula " +
                                "where c.finalitzada = true order by dia"
                );
                while (rs.next()) {
                    id_comanda = rs.getInt("id_comanda");
                    dia = rs.getDate("dia");
                    nom_taula = rs.getString("nom_taula");
                    finalitzada_comanda = rs.getBoolean("finalitzada");

                    pujarATableviewComandes(id_comanda, dia, nom_taula, finalitzada_comanda);
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
                        "select c.id_comanda as id_comanda, c.dia as dia, c.finalitzada as finalitzada, t.nom as nom_taula " +
                                "from comanda c inner join taula t on c.id_taula = t.id_taula " +
                                "where c.finalitzada = false order by dia"
                );
                while (rs.next()) {
                    id_comanda = rs.getInt("id_comanda");
                    dia = rs.getDate("dia");
                    nom_taula = rs.getString("nom_taula");
                    finalitzada_comanda = rs.getBoolean("finalitzada");

                    pujarATableviewComandes(id_comanda, dia, nom_taula, finalitzada_comanda);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Ha acavat de buscar a la base de dades");
        }
    }


    private void pujarATableviewComandes(int id_comanda, Date dia, String nom_taula, boolean finalitzada_comanda) {
        llista_comandes.add(new Comanda(id_comanda, dia, nom_taula, finalitzada_comanda));
        taula_comandes.setItems(llistaComandes);
    }

    private void borrarTableViews(){
        //llistaBegudes.clear();
        //taula_begudes.setItems(llistaBegudes);
        //llista_comandes.clear();
        //taula_comandes.setItems(llistaComandes);
        //llista_aliments.clear();
        //taula_aliments.setItems(llistaAliments);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> estat =
                FXCollections.observableArrayList(e.getEstat());
        cbEstat.getItems().addAll(estat);
    }
}
