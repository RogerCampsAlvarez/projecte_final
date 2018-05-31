package menuAdmin.taules;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import objectes.Taula;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de modificació de les taules
 *
 */
public class ModificarTaulesController implements Initializable{
    @FXML
    TextField tbNom;
    @FXML
    TableView<Taula> taula_taules;

    private List<Taula> llista = new ArrayList<Taula>();
    private ObservableList<Taula> llistaTaules = FXCollections.observableList(llista);
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
        tbNom.setText(taula_taules.getSelectionModel().getSelectedItem().getNom());
    }

    /**
     * Busca tots els camps de una select a la base de dades
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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

    /**
     * puja tots els camps dels parametres a la table view
     * @param idTaula
     * @param nomTaula
     */
    private void pujarATableview(int idTaula, String nomTaula) {
        llistaTaules.add(new Taula(idTaula, nomTaula));
        taula_taules.setItems(llistaTaules);
    }

    /**
     * Borra tots els camps de la table view
     */
    private void borrarTableView(){
        llistaTaules.clear();
        taula_taules.setItems(llistaTaules);
    }

    @FXML
    /**
     * Guarda, si son correctes, tots els camps del formulari a la base de dades
     */
    public void cmdGuardar() throws SQLException {
        //si no s'ha seleccionat res
        if (!taula_taules.getSelectionModel().isEmpty()){

            String nom = tbNom.getText();
            int id_antic = taula_taules.getSelectionModel().getSelectedItem().getId();
            int cont = 0;


            rs = con.queryDB("select nom from taula");

            while (rs.next()){
                if (rs.getString("nom").equals(nom)){
                    cont++;
                }
            }

            //si el nom a entrar no està repetit
            if (cont == 0){
                //si el nom no està buit
                if (!tbNom.equals("")){
                    con.execDB("update taula set nom = '" + nom + "' where id_taula = " + id_antic + ";");
                    alertconfirm.setTitle("Guardat");
                    alertconfirm.setHeaderText("Taula modificada amb exit!");
                    alertconfirm.show();
                    borrarTableView();
                    borrarCampsFormulari();
                    try {
                        buscarBD();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    alerterror.setTitle("Error");
                    alerterror.setHeaderText("El valor a guardar està buit.");
                    alerterror.show();
                }
            }
            else {
                alerterror.setTitle("Error");
                alerterror.setHeaderText("El nom està repetit.");
                alerterror.show();
            }
        }
        else{
            alerterror.setTitle("Error");
            alerterror.setHeaderText("Seleccionar taula a modificar");
            alerterror.show();
        }
    }

    /**
     * Borra tots els camps del formulari
     *
     */
    private void borrarCampsFormulari(){
        tbNom.setText("");
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
    }
}