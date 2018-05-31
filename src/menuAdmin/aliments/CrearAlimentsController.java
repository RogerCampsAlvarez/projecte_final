package menuAdmin.aliments;

import combos.Categoria;
import combos.Ordre;
import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import combos.Tipus;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de creació dels aliments
 *
 */
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
    @FXML
    GridPane gridPane;
    @FXML
    Button bBuscarImg;

    File fileImg;
    FileChooser fc = new FileChooser();
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
    /**
     * Guarda l'aliment entrat a la base de dades
     *
     */
    public void cmdGuardar() throws SQLException {
        nom = tbNom.getText();
        descrpcio = taDescripcio.getText();
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
                            clearFormulari();
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

    /**
     * Neteja els camps del formulari
     *
     */
    private void clearFormulari(){
        tbNom.setText("");
        tbPreu.setText("");
        taDescripcio.setText("");
        cbOrdre.setValue(null);
        cbCategoria.setValue(null);
    }

    @FXML
    /**
     * S'executa al clickar el boto de buscar la imatge
     *
     */
    private void cmdBuscarImg(){
        fileImg = fc.showOpenDialog(null);
        imatge = encodeFileToBase64Binary(fileImg);
    }

    /**
     * Transforma el fitxer a base 64
     *
     * @param file
     * @return
     */
    private String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encode(bytes).toString();
        } catch (Exception e) {
            alerterror.setTitle("Error");
            alerterror.setHeaderText("Arxiu incorrecte");
            alerterror.show();
        }

        return encodedfile;
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
