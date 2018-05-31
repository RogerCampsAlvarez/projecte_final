package menuAdmin.aliments;

import combos.Categoria;
import combos.Ordre;
import combos.Tipus;
import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import objectes.Aliments;
import objectes.Beguda;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModificarAlimentsController implements Initializable{
    @FXML
    TextField tbNom;
    @FXML
    TextField tbPreu;
    @FXML
    TextArea taDescripcioo;
    @FXML
    ComboBox cbOrdre;
    @FXML
    ComboBox cbCategoriaa;
    @FXML
    TableView<Aliments> taula_aliments;



    Ordre o = new Ordre();
    Categoria c = new Categoria();
    List<Aliments> llista = new ArrayList<Aliments>();
    ObservableList<Aliments> llistaAliments = FXCollections.observableList(llista);
    ConnexioBD con = new ConnexioBD();
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;


    @FXML
    private void clickItem(MouseEvent event) {
        tbNom.setText(taula_aliments.getSelectionModel().getSelectedItem().getNom());
        tbPreu.setText(taula_aliments.getSelectionModel().getSelectedItem().getPreu());
        taDescripcioo.setText(taula_aliments.getSelectionModel().getSelectedItem().getDescripcio());
        cbOrdre.setValue(taula_aliments.getSelectionModel().getSelectedItem().getOrdre());
        cbCategoriaa.setValue(taula_aliments.getSelectionModel().getSelectedItem().getCategoria());
    }


    private void buscarBD() throws ClassNotFoundException, SQLException {
        int id;
        String nom;
        String preu;
        String descripcio;
        String ordre;
        String categoria;

        System.out.println("Buscant a la base de dades...");
        try {
            rs = con.queryDB(
                    "select * from aliments order by nom"
            );
            while (rs.next()) {
                id = rs.getInt("id_aliment");
                nom = rs.getString("nom");
                preu = rs.getString("preu");
                descripcio = rs.getString("descripcio");
                ordre = rs.getString("ordre");
                categoria = rs.getString("categoria");

                pujarATableview(id, nom, descripcio, preu, ordre, categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");
    }


    private void pujarATableview(int idAliment, String nomAliment, String descripcioAliment, String preuAliment, String ordreAliment, String categoriaAliment) {
        llistaAliments.add(new Aliments(idAliment, nomAliment, descripcioAliment, preuAliment, ordreAliment, categoriaAliment));
        taula_aliments.setItems(llistaAliments);
    }

    private void borrarTableView(){
        llistaAliments.clear();
        taula_aliments.setItems(llistaAliments);
    }

    @FXML
    public void cmdGuardar() throws SQLException {
        if (!taula_aliments.getSelectionModel().isEmpty()) {

            String nom = tbNom.getText();
            String preu = tbPreu.getText();
            String descripcio = taDescripcioo.getText();
            String ordre = cbOrdre.getSelectionModel().getSelectedItem().toString();
            String categoria = cbCategoriaa.getSelectionModel().getSelectedItem().toString();
            int id_antic = taula_aliments.getSelectionModel().getSelectedItem().getId();
            int cont = 0;


            rs = con.queryDB("select nom from aliments where id_aliment != " + id_antic + ";");

            while (rs.next()) {
                if (rs.getString("nom").equals(nom)) {
                    cont++;
                }
            }

            //si el nom a entrar no està repetit
            if (cont == 0) {
                //si cap valor esta buit
                if (!tbNom.equals("") || !tbPreu.equals("") || taDescripcioo.equals("") || !cbOrdre.getSelectionModel().isEmpty() || !cbCategoriaa.getSelectionModel().isEmpty()) {
                    con.execDB("update aliments set nom = '" + nom + "', descripcio = '" + descripcio + "', preu = " + preu + ", ordre = '" + ordre + "', categoria = '" + categoria + "'  where id_aliment = " + id_antic + ";");
                    alertconfirm.setTitle("Guardat");
                    alertconfirm.setHeaderText("Aliment modificada amb exit!");
                    alertconfirm.show();
                    borrarTableView();
                    borrarCampsFormulari();
                    try {
                        buscarBD();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    alerterror.setTitle("Error");
                    alerterror.setHeaderText("Cap valor pot estar buit.");
                    alerterror.show();
                }
            } else {
                alerterror.setTitle("Error");
                alerterror.setHeaderText("El nom està repetit.");
                alerterror.show();
            }
        }
        else {
            alerterror.setTitle("Error");
            alerterror.setHeaderText("Seleccionar beguda a modificar");
            alerterror.show();
        }
    }

    private void borrarCampsFormulari(){
        tbNom.setText("");
        tbPreu.setText("");
        taDescripcioo.setText("");
        cbOrdre.setValue(null);
        cbCategoriaa.setValue(null);
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

        ObservableList<String> ordre =
                FXCollections.observableArrayList(o.getOrdre());
        cbOrdre.getItems().addAll(ordre);

        ObservableList<String> categoria =
                FXCollections.observableArrayList(c.getCategoria());
        cbCategoriaa.getItems().addAll(categoria);
    }
}