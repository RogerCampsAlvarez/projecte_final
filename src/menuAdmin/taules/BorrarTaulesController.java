package menuAdmin.taules;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import objectes.Taula;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BorrarTaulesController implements Initializable{
    @FXML
    TableView<Taula> taula_taules;

    private List<Taula> llista = new ArrayList<Taula>();
    private ObservableList<Taula> llistaTaules = FXCollections.observableList(llista);
    ConnexioBD con = new ConnexioBD();
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;
    int id_taula;
    String nom_taula;
    int count = 0;


    private void buscarBD() throws ClassNotFoundException, SQLException {
        int id;
        String nom;
        System.out.println("Buscant a la base de dades...");
        try {
            rs = con.queryDB(
                    "select * from taula order by nom"
            );
            while (rs.next()) {
                id = rs.getInt("id_taula");
                nom = rs.getString("nom");

                pujarATableview(id, nom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");
    }


    private void pujarATableview(int idTaula, String nomTaula) {
        llistaTaules.add(new Taula(idTaula, nomTaula));
        taula_taules.setItems(llistaTaules);
    }

    private void borrarTableView(){
        llistaTaules.clear();
        taula_taules.setItems(llistaTaules);
    }

    @FXML
    public void cmdBorrar(){
        //si no s'ha seleccionat res
        if (!taula_taules.getSelectionModel().isEmpty()){
            id_taula = taula_taules.getSelectionModel().getSelectedItem().getId();
            nom_taula = taula_taules.getSelectionModel().getSelectedItem().getNom();

            con.execDB("delete from taula where id_taula = " + id_taula + ";");
            alertconfirm.setTitle("Borrat");
            alertconfirm.setHeaderText("Taula borrada amb exit!");
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
            alerterror.setHeaderText("Seleccionar taula a borrar");
            alerterror.show();
        }

        count = 1;
    }

    @FXML
    public void cmdRecuperarUltim(){
        //encara no s'ha recuperat l'última taula borrada
        if (count == 1){
            con.execDB("insert into taula (nom) values ('" + nom_taula + "');");
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
            alerterror.setHeaderText("No hi ha cap taula a recuperar.");
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