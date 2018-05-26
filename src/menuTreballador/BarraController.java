package menuTreballador;

import inici.ConnexioBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objectes.Beguda;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BarraController {
    @FXML
    private Label Lnom;
    @FXML
    private Label Ltaula;
    @FXML
    private Label Ltipus;
    @FXML
    private Label Lhora;
    @FXML
    private TableView<Beguda> taula_begudes;
    @FXML
    private Button bRebreBegudes;
    @FXML
    private Button bBorrar;


    private List<Beguda> llista = new ArrayList<Beguda>();
    private ObservableList<Beguda> llistaBegudes = FXCollections.observableList(llista);
    Parent root;
    ConnexioBD con = new ConnexioBD();


    public void initialize(Stage primaryStage) throws Exception {
    }

    @FXML
    private void cmdRebreBegudes(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        buscarBD();
    }

    @FXML
    private void completarBeguda(ActionEvent actionEvent) {
        taula_begudes.getItems().remove(taula_begudes.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void clickItem(MouseEvent event) {
        Lnom.setText(taula_begudes.getSelectionModel().getSelectedItem().getNom());
        Ltaula.setText(taula_begudes.getSelectionModel().getSelectedItem().getTaula());
        Ltipus.setText(taula_begudes.getSelectionModel().getSelectedItem().getTipus());
        Lhora.setText(taula_begudes.getSelectionModel().getSelectedItem().getHora());
        System.out.println("hola");
    }


    private void buscarBD() throws ClassNotFoundException, SQLException {
        String taula = "";
        String beguda = "";
        String tipus = "";
        int id = 0;
        Date date;


        System.out.println("Buscant a la base de dades...");
        try {
            ResultSet rs = con.queryDB(
                    "select cb.id as id, b.nom as nombeguda, t.nom as nomtaula, b.tipus as tipus\n" +
                            "from comandabeguda cb\n" +
                            "inner join comanda c on cb.id_comanda = c.id_comanda\n" +
                            "inner join begudes b on cb.id_beguda = b.id_beguda\n" +
                            "inner join taula t on c.id_taula = t.id_taula\n" +
                            "where cb.estat = 'esperant'"
            );
            while (rs.next()) {
                date = new Date();
                id = rs.getInt("id");
                taula = rs.getString("nomtaula");
                beguda = rs.getString("nombeguda");
                tipus = rs.getString("tipus");
                //con.execDB("update comandabeguda set finalitzada = true where id = " + id + ";");
                con.execDB("update comandabeguda set estat = 'agafat' where id = " + id + ";");

                pujarATableview(id, taula, beguda, tipus, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ha acavat de buscar a la base de dades");
    }


    private void pujarATableview(int id, String taula, String beguda, String tipus, Date Dhora) {
        String time = modificarHora(Dhora);
        llistaBegudes.add(new Beguda(id, beguda, taula, tipus, time));
        taula_begudes.setItems(llistaBegudes);
    }


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


    void cmdBorrar(){
        int id = taula_begudes.getSelectionModel().getSelectedItem().getId();
        con.execDB("update comandabeguda set estat = 'finalitzat' where id = " + id + ";");
        taula_begudes.getItems().remove(taula_begudes.getSelectionModel().getSelectedItem());
    }
}