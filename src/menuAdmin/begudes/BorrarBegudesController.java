package menuAdmin.begudes;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import objectes.Beguda;
import objectes.Taula;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de borrat de les begudes
 *
 */
public class BorrarBegudesController implements Initializable{
    @FXML
    TableView<Beguda> taula_begudes;

    private List<Beguda> llista = new ArrayList<Beguda>();
    private ObservableList<Beguda> llistaBegudes = FXCollections.observableList(llista);
    ConnexioBD con = new ConnexioBD();
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;
    int id_beguda;
    String nom_beguda;
    String preu_beguda;
    String tipus_beguda;
    int count = 0;

    /**
     * Busca tots els camps de una select a la base de dades
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void buscarBD() throws ClassNotFoundException, SQLException {
        int id;
        String nom;
        Double preu;
        String tipus;
        System.out.println("Buscant a la base de dades...");
        try {
            rs = con.queryDB(
                    "select * from begudes WHERE NOT id_beguda IN ( SELECT id_beguda FROM comandabeguda )"
            );
            while (rs.next()) {
                id = rs.getInt("id_beguda");
                nom = rs.getString("nom");
                preu = rs.getDouble("preu");
                tipus = rs.getString("tipus");

                pujarATableview(id, nom, preu, tipus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");
    }

    /**
     * Puja tots els els camps a la table view
     * @param idBeguda
     * @param nomBeguda
     * @param preuBeguda
     * @param tipusBeguda
     */
    private void pujarATableview(int idBeguda, String nomBeguda, Double preuBeguda, String tipusBeguda) {
        llistaBegudes.add(new Beguda(idBeguda, nomBeguda, preuBeguda.toString(), tipusBeguda));
        taula_begudes.setItems(llistaBegudes);
    }

    /**
     * Borra tots els elements de la table view
     */
    private void borrarTableView(){
        llistaBegudes.clear();
        taula_begudes.setItems(llistaBegudes);
    }

    @FXML
    /**
     * Borra el item seleccionat
     */
    public void cmdBorrar(){
        //si no s'ha seleccionat res
        if (!taula_begudes.getSelectionModel().isEmpty()){
            id_beguda = taula_begudes.getSelectionModel().getSelectedItem().getId();
            nom_beguda = taula_begudes.getSelectionModel().getSelectedItem().getNom();
            preu_beguda = taula_begudes.getSelectionModel().getSelectedItem().getPreu();
            tipus_beguda = taula_begudes.getSelectionModel().getSelectedItem().getTipus();

            con.execDB("delete from begudes where id_beguda = " + id_beguda + ";");
            alertconfirm.setTitle("Borrat");
            alertconfirm.setHeaderText("Beguda borrada amb exit!");
            alertconfirm.show();
            borrarTableView();
            try {
                buscarBD();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            alerterror.setTitle("Error");
            alerterror.setHeaderText("Seleccionar beguda a borrar");
            alerterror.show();
        }

        count = 1;
    }

    @FXML
    /**
     * Recupera ultim objecte borrat i el inserta a la base de dades
     */
    public void cmdRecuperarUltim(){
        //encara no s'ha recuperat l'última taula borrada
        if (count == 1){
            con.execDB("insert into begudes (nom, preu, tipus) values ('" + nom_beguda + "', " + preu_beguda + ", '" + tipus_beguda +"');");
            borrarTableView();
            try {
                buscarBD();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            alerterror.setTitle("Error");
            alerterror.setHeaderText("No hi ha cap beguda a recuperar.");
            alerterror.show();
        }

        count = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            buscarBD();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
