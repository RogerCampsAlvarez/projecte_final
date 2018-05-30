package menuAdmin.begudes;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import objectes.Tipus;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CrearBegudesController implements Initializable{
    @FXML
    TextField tbNom;
    @FXML
    TextField tbPreu;
    @FXML
    ComboBox cbTipus;

    ConnexioBD con = new ConnexioBD();
    Tipus t = new Tipus();
    String nom = "";
    Double preu;
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;
    int cont = 0;


    @FXML
    public void cmdGuardar() throws SQLException {
        nom = tbNom.getText();
        //preu = tbPreu.getText();
        rs = con.queryDB("select nom from begudes");

        while (rs.next()){
            if (rs.getString("nom").equals(nom)){
                cont++;
            }
        }

        //si no hi ha cap nom que es repeteixi amb aquest es guardara a la base de dades
        if (cont == 0){
            //comprova si el preu està ben escrit
            //if (preu.contains("[0-9]*[.]{1}[0-9]{2}")){
            try{
                preu = Double.parseDouble(tbPreu.getText());

                //comprova que el combo estigui seleccionat
                if (!cbTipus.getSelectionModel().isEmpty()){
                    //si el camp per entrar el nom no està buit
                    if (nom != null){
                        con.execDB("Insert into begudes (nom, preu, tipus) values ('" + nom + "'," + preu + ",'" + cbTipus.getSelectionModel().getSelectedItem().toString() + "');");
                        alertconfirm.setTitle("Guardat");
                        alertconfirm.setHeaderText("Beguda guardada amb exit!");
                        alertconfirm.show();
                        //tbNom.setText("");
                    }
                    else{
                        alerterror.setTitle("Error");
                        alerterror.setHeaderText("La beguda ha de tenir un nom.");
                        alerterror.show();
                    }
                }
                else{
                    alerterror.setTitle("Error");
                    alerterror.setHeaderText("El combo tipus ha d'estar seleccionat amb algun valor.");
                    alerterror.show();
                }
            }
            catch(NumberFormatException e){
                alerterror.setTitle("Error");
                alerterror.setHeaderText("El preu no es valid, ha de ser escrit de manera que hi hagi un numero, un punt, i acavat en dos decimals. Per exemple: 12.50 o 2.00");
                alerterror.show();
            }
        }
        else{
            alerterror.setTitle("Error");
            alerterror.setHeaderText("La beguda està repetida.");
            alerterror.show();
            cont = 0;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> tipus =
                FXCollections.observableArrayList(t.getTipus());
        cbTipus.getItems().addAll(tipus);
    }
}