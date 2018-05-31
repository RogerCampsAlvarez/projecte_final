package menuAdmin.begudes;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import objectes.Beguda;
import combos.Tipus;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de modificació de les begudes
 *
 */
public class ModificarBegudesController implements Initializable{
    @FXML
    TextField tbNom;
    @FXML
    TextField tbPreu;
    @FXML
    ComboBox cbTipus;
    @FXML
    TableView<Beguda> taula_begudes;

    Tipus t = new Tipus();
    List<Beguda> llista = new ArrayList<Beguda>();
    ObservableList<Beguda> llistaBegudes = FXCollections.observableList(llista);
    ConnexioBD con = new ConnexioBD();
    Alert alerterror = new Alert(Alert.AlertType.ERROR);
    Alert alertconfirm = new Alert(Alert.AlertType.CONFIRMATION);
    ResultSet rs;


    @FXML
    /**
     * S'executa al clickar un item de la table view
     *
     */
    private void clickItem(MouseEvent event) {
        tbNom.setText(taula_begudes.getSelectionModel().getSelectedItem().getNom());
        tbPreu.setText(taula_begudes.getSelectionModel().getSelectedItem().getPreu());
        cbTipus.setValue(taula_begudes.getSelectionModel().getSelectedItem().getTipus());
    }

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
                    "select * from begudes order by nom"
            );
            while (rs.next()) {
                id = rs.getInt("id_beguda");
                nom = rs.getString("nom");
                preu = rs.getDouble("preu");
                tipus = rs.getString("tipus");

                pujarATableview(id, nom, preu.toString(), tipus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");
    }

    /**
     * Puja tots els camps dels parametres a la table view
     * @param idBeguda
     * @param nomBeguda
     * @param preuBeguda
     * @param tipusBeguda
     */
    private void pujarATableview(int idBeguda, String nomBeguda, String preuBeguda, String tipusBeguda) {
        llistaBegudes.add(new Beguda(idBeguda, nomBeguda, preuBeguda, tipusBeguda));
        taula_begudes.setItems(llistaBegudes);
    }

    /**
     * Borra tots els camps de la table view
     */
    private void borrarTableView(){
        llistaBegudes.clear();
        taula_begudes.setItems(llistaBegudes);
    }

    @FXML
    /**
     * Guarda, si son correctes, tots els camps del formulari a la base de dades
     */
    public void cmdGuardar() throws SQLException {
        if (!taula_begudes.getSelectionModel().isEmpty()) {

            String nom = tbNom.getText();
            String preu = tbPreu.getText();
            String tipus = cbTipus.getSelectionModel().getSelectedItem().toString();
            int id_antic = taula_begudes.getSelectionModel().getSelectedItem().getId();
            int cont = 0;
            System.out.println(nom + "\n" + preu + "\n" + tipus + "\n" + id_antic);


            rs = con.queryDB("select nom from begudes");

            while (rs.next()) {
                if (rs.getString("nom").equals(nom)) {
                    cont++;
                }
            }

            //si el nom a entrar no està repetit
            if (cont == 0) {
                //si cap valor esta buit
                if (!tbNom.equals("") || !tbPreu.equals("") || !cbTipus.getSelectionModel().isEmpty()) {
                    con.execDB("update begudes set nom = '" + nom + "', preu = " + preu + ", tipus = '" + tipus + "'  where id_beguda = " + id_antic + ";");
                    alertconfirm.setTitle("Guardat");
                    alertconfirm.setHeaderText("Beguda modificada amb exit!");
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

    /**
     * Borra tots els camps del formulari
     *
     */
    private void borrarCampsFormulari(){
        tbNom.setText("");
        tbPreu.setText("");
        cbTipus.setValue(null);
    }

    @Override
    /**
     * Inicialitza l'aplicació buscant els camps que van a la table view i inicialitza els combos del formulari
     */
    public void initialize(URL location, ResourceBundle resources) {
        try {
            buscarBD();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> tipus =
                FXCollections.observableArrayList(t.getTipus());
        cbTipus.getItems().addAll(tipus);
    }
}
