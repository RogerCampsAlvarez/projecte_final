package menuAdmin.aliments;

import combos.Categoria;
import combos.Ordre;
import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import combos.Tipus;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CrearAlimentsController implements Initializable{
    @FXML
    TextField tbNom;
    @FXML
    TextArea taDescripcio;
    @FXML
    TextField tbPreu;
    @FXML
    ComboBox cbOrdre;
    @FXML
    ComboBox cbCategoria;

    ConnexioBD con = new ConnexioBD();
    Categoria c = new Categoria();
    Ordre o= new Ordre();
    String nom = "";
    String descrpcio;
    Double preu;
    String imatge;
    String ordre;
    String categoria;
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;
    int cont = 0;


    @FXML
    public void cmdGuardar() throws SQLException {
        nom = tbNom.getText();
        descrpcio = taDescripcio.getText();
        imatge = "";
        ordre = cbOrdre.getSelectionModel().getSelectedItem().toString();
        categoria = cbCategoria.getSelectionModel().getSelectedItem().toString();
        //preu = tbPreu.getText();
        rs = con.queryDB("select nom from aliments");

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

                //comprova que el combo categoria estigui seleccionat
                if (!cbCategoria.getSelectionModel().isEmpty()){
                    //comprova que el combo ordre estigui seleccionat
                    if (!cbOrdre.getSelectionModel().isEmpty()){
                        //si el camp per entrar el nom no està buit
                        if (nom != null){
                            con.execDB("Insert into aliments (nom, descripcio, preu, imatge, ordre, categoria) values ('" + nom + "','" + descrpcio + "'," + preu + ",'" + imatge + "','" + ordre + "','" + categoria + "');");
                            alertconfirm.setTitle("Guardat");
                            alertconfirm.setHeaderText("Aliment guardat amb exit!");
                            alertconfirm.show();
                            //tbNom.setText("");
                        }
                        else{
                            alerterror.setTitle("Error");
                            alerterror.setHeaderText("L'aliment ha de tenir un nom.");
                            alerterror.show();
                        }
                    }
                    else{
                        alerterror.setTitle("Error");
                        alerterror.setHeaderText("El combo ordre ha d'estar seleccionat amb algun valor.");
                        alerterror.show();
                    }
                }
                else{
                    alerterror.setTitle("Error");
                    alerterror.setHeaderText("El combo categoria ha d'estar seleccionat amb algun valor.");
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
            alerterror.setHeaderText("L'aliment està repetit.");
            alerterror.show();
            cont = 0;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> ordre =
                FXCollections.observableArrayList(o.getOrdre());
        cbOrdre.getItems().addAll(ordre);

        ObservableList<String> categoria =
                FXCollections.observableArrayList(c.getCategoria());
        cbCategoria.getItems().addAll(categoria);
    }
}
