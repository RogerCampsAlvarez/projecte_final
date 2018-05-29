package menuAdmin.taules;

import inici.ConnexioBD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ModificarTaulesController {
    @FXML
    TextField tbNom;

    ConnexioBD con = new ConnexioBD();
    String nom = "";
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;
    int cont = 0;


    @FXML
    public void cmdGuardar() throws SQLException {
        nom = tbNom.getText();
        rs = con.queryDB("select nom from taula");

        while (rs.next()){
            if (rs.getString("nom") == nom){
                cont++;
            }
        }

        //si no hi ha cap nom que es repeteixi amb aquest es guardara a la base de dades
        if (cont == 0){
            //si el camp per entrar el nom no està buit
            if (nom != null){
                con.execDB("Insert into taula (nom) values (" + tbNom.getText() + ");");
                alertconfirm.setTitle("Guardat");
                alertconfirm.setHeaderText("Taula guardada amb exit!");
                alertconfirm.show();
                tbNom.setText("");
            }
            else{
                alerterror.setTitle("Error");
                alerterror.setHeaderText("La taula ha de tenir un nom.");
                alerterror.show();
            }
        }
        else{
            alerterror.setTitle("Error");
            alerterror.setHeaderText("La taula està repetida.");
            alerterror.show();
        }
    }
}