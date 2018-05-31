package menuTreballador;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objectes.Plat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuinaController {
    @FXML
    private Label Lnom;
    @FXML
    private Label Ltaula;
    @FXML
    private Label Lordre;
    @FXML
    private Label Lcategoria;
    @FXML
    private Label Lhora;
    @FXML
    private TableView<Plat> taula_aliments;
    @FXML
    private Button bRebreMenjar;
    @FXML
    private Button bBorrar;


    private List<Plat> llista = new ArrayList<Plat>();
    private ObservableList<Plat> llistaAliments = FXCollections.observableList(llista);
    Parent root;
    ConnexioBD con = new ConnexioBD();


    public void initialize(Stage primaryStage) throws Exception {
    }

    @FXML
    /**
     * S'activa al clickar el boto de rebre plats
     */
    private void cmdRebrePlats(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        buscarBD();
    }

    @FXML
    /**
     * S'activa al clickar el boto de completar plat
     */
    private void completarPlat(ActionEvent actionEvent) {
        int id = taula_aliments.getSelectionModel().getSelectedItem().getId();
        con.execDB("update comandaaliment set estat = 'finalitzat' where id = " + id + ";");
        taula_aliments.getItems().remove(taula_aliments.getSelectionModel().getSelectedItem());
    }

    @FXML
    /**
     * Mostra en el formulari les dades del item clickat
     */
    private void clickItem(MouseEvent event) {
        Lnom.setText(taula_aliments.getSelectionModel().getSelectedItem().getNom());
        Ltaula.setText(taula_aliments.getSelectionModel().getSelectedItem().getTaula());
        Lordre.setText(taula_aliments.getSelectionModel().getSelectedItem().getOrdre());
        Lcategoria.setText(taula_aliments.getSelectionModel().getSelectedItem().getCategoria());
        Lhora.setText(taula_aliments.getSelectionModel().getSelectedItem().getHora());
        System.out.println("hola");
    }

    /**
     * Busca a la base de dades les dades necessaries
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void buscarBD() throws ClassNotFoundException, SQLException {
        String taula = "";
        String aliment = "";
        String ordre = "";
        String categoria = "";
        int id = 0;
        Date date;


        System.out.println("Buscant a la base de dades...");
        try {
            ResultSet rs = con.queryDB(
                    "select ca.id as id, a.nom as nomaliment, t.nom as nomtaula, a.ordre as ordre, a.categoria as categoria\n" +
                            "from comandaaliment ca\n" +
                            "inner join comanda c on ca.id_comanda = c.id_comanda\n" +
                            "inner join aliments a on ca.id_aliment = a.id_aliment\n" +
                            "inner join taula t on c.id_taula = t.id_taula\n" +
                            "where ca.estat = 'esperant'"
            );
            while (rs.next()) {
                date = new Date();
                id = rs.getInt("id");
                taula = rs.getString("nomtaula");
                aliment = rs.getString("nomaliment");
                categoria = rs.getString("categoria");
                ordre = rs.getString("ordre");
                //con.execDB("update comandabeguda set finalitzada = true where id = " + id + ";");
                con.execDB("update comandaaliment set estat = 'agafat' where id = " + id + ";");

                pujarATableview(id, taula, aliment, categoria, ordre, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");
    }

    /**
     * Puja un objecta a la table view
     * @param id
     * @param taula
     * @param aliment
     * @param categoria
     * @param ordre
     * @param Dhora
     */
    private void pujarATableview(int id, String taula, String aliment, String categoria, String ordre, Date Dhora) {
        String time = modificarHora(Dhora);
        llistaAliments.add(new Plat(id, aliment, taula, ordre, categoria, time));
        taula_aliments.setItems(llistaAliments);
    }

    /**
     * Modifica l'hora per formatar-la be
     * @param Dhora
     * @return
     */
    private String modificarHora(Date Dhora) {
        String hora = Integer.toString(Dhora.getHours());
        String minuts;
        String time;

        if (Dhora.getMinutes() < 10) {
            minuts = "0" + Dhora.getMinutes();
        } else {
            minuts = Integer.toString(Dhora.getMinutes());
        }

        time = hora + ":" + minuts;


        return time;
    }
}
