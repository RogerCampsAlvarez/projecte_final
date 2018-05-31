package administracio;

import inici.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Menu de les opcions del adrministrador
 *
 */
public class MenuAdministracioController implements Initializable{
    Pane root;
    Scene scene;
    Stage stage;
    int var = 0;

    @FXML
    private Button bRegistrarUsuari;
    @FXML
    private Button bDesconectar;
    @FXML
    private Button bGestionarComandes;
    @FXML
    private Button bGestionarTaules;
    @FXML
    private Button bGestionarAliments;
    @FXML
    private Button bGestionarBegudes;
    @FXML
    private Button bComptabilitat;
    @FXML
    private AnchorPane anchorPane1;

    //botons crear, borrar, modificar
    @FXML
    private Button bVisualitzarComandes;
    @FXML
    private Button bCrearTaules;
    @FXML
    private Button bCrearAliments;
    @FXML
    private Button bCrearBegudes;
    @FXML
    private Button bModificarTaules;
    @FXML
    private Button bModificarAliments;
    @FXML
    private Button bModificarBegudes;
    @FXML
    private Button bBorrarComandes;
    @FXML
    private Button bBorrarTaules;
    @FXML
    private Button bBorrarAliments;
    @FXML
    private Button bBorrarBegudes;




    @FXML
    /**
     * Obra la pestanya de registrar usuari en la escena
     */
    void cmdRegistrarUsuari(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("/inici/Registre.fxml"));
            scene = new Scene(root);
            stage = (Stage) bRegistrarUsuari.getScene().getWindow();
            Util.openGUI(scene, stage, "Registre");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Obra la pestanya de gestionar comandes en el anchor pane i mostra i oculta els botons pertinents
     */
    void cmdGestionarComandes(ActionEvent event) {
        visibilitatBotonsComandes(true);
        visibilitatBotonsAliments(false);
        visibilitatBotonsBegudes(false);
        visibilitatBotonsTaules(false);

        if (var != 0){
            recarregarEscena();
        }
        visibilitatBotonsComandes(true);
        visibilitatBotonsAliments(false);
        visibilitatBotonsBegudes(false);
        visibilitatBotonsTaules(false);
    }

    @FXML
    /**
     * Obra la pestanya de gestionar taules en el anchor pane i mostra i oculta els botons pertinents
     */
    void cmdGestionarTaules(ActionEvent event) {
        visibilitatBotonsComandes(false);
        visibilitatBotonsAliments(false);
        visibilitatBotonsBegudes(false);
        visibilitatBotonsTaules(true);

        if (var != 0){
            recarregarEscena();
        }
    }

    @FXML
    /**
     * Obra la pestanya de gestionar aliments en el anchor pane i mostra i oculta els botons pertinents
     */
    void cmdGestionarAliments(ActionEvent event) {
        visibilitatBotonsComandes(false);
        visibilitatBotonsAliments(true);
        visibilitatBotonsBegudes(false);
        visibilitatBotonsTaules(false);

        if (var != 0){
            recarregarEscena();
        }
    }

    @FXML
    /**
     * Obra la pestanya de gestionar begudes en el anchor pane i mostra i oculta els botons pertinents
     */
    void cmdGestionarBegudes(ActionEvent event) {
        visibilitatBotonsComandes(false);
        visibilitatBotonsAliments(false);
        visibilitatBotonsBegudes(true);
        visibilitatBotonsTaules(false);

        if (var != 0){
            recarregarEscena();
        }
    }

    @FXML
    /**
     * Obra la pestanya de gestionar comptabilitat en el anchor pane i mostra i oculta els botons pertinents
     */
    void cmdComptabilitat(ActionEvent event) {
        visibilitatBotonsComandes(false);
        visibilitatBotonsAliments(false);
        visibilitatBotonsBegudes(false);
        visibilitatBotonsTaules(false);

        if (var != 0){
            recarregarEscena();
        }

        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/comptabilitat/Comptabilitat.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Desconecta la sessió de l'usuari i et porta a la pestanya de login
     */
    void cmdDesconectar(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/inici/Login.fxml"));
            scene = new Scene(root);
            stage = (Stage) bDesconectar.getScene().getWindow();
            Util.openGUI(scene, stage, "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mostra o amaga els botons de comandes
     * @param estat
     */
    void visibilitatBotonsComandes(boolean estat){
        bVisualitzarComandes.setVisible(estat);
        bBorrarComandes.setVisible(estat);
    }

    /**
     * Mostra o amaga els botons de taules
     * @param estat
     */
    void visibilitatBotonsTaules(boolean estat){
        bCrearTaules.setVisible(estat);
        bModificarTaules.setVisible(estat);
        bBorrarTaules.setVisible(estat);
    }

    /**
     * Mostra o amaga els botons de aliments
     * @param estat
     */
    void visibilitatBotonsAliments(boolean estat){
        bCrearAliments.setVisible(estat);
        bModificarAliments.setVisible(estat);
        bBorrarAliments.setVisible(estat);
    }

    /**
     * Mostra o amaga els botons de begudes
     * @param estat
     */
    void visibilitatBotonsBegudes(boolean estat){
        bCrearBegudes.setVisible(estat);
        bModificarBegudes.setVisible(estat);
        bBorrarBegudes.setVisible(estat);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visibilitatBotonsComandes(false);
        visibilitatBotonsAliments(false);
        visibilitatBotonsBegudes(false);
        visibilitatBotonsTaules(false);
        var = 0;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de crear aliments
     *
     */
    public void cmdCrearAliments(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/aliments/CrearAliments.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de modificar aliments
     *
     */
    public void cmdModificarAliments(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/aliments/ModificarAliments.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de borrar aliments
     *
     */
    public void cmdBorrarAliments(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/aliments/BorrarAliments.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de crear begudes
     *
     */
    public void cmdCrearBegudes(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/begudes/CrearBegudes.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML

    /**
     * Obra en el mateix anchor pane la penstanya de modificar begudes
     *
     */
    public void cmdModificarBegudes(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/begudes/ModificarBegudes.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de borrar begudes
     *
     */
    public void cmdBorrarBegudes(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/begudes/BorrarBegudes.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de visualitzar comandes
     *
     */
    public void cmdVisualitzarComandes(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/comandes/VisualitzarComandes.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de borrar comandes
     *
     */
    public void cmdBorrarComandes(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/comandes/BorrarComandes.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de crear taules
     *
     */
    public void cmdCrearTaules(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/taules/CrearTaules.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de modificar taules
     *
     */
    public void cmdModificarTaules(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/taules/ModificarTaules.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    @FXML
    /**
     * Obra en el mateix anchor pane la penstanya de borrar taules
     *
     */
    public void cmdBorrarTaules(ActionEvent event){
        try {
            anchorPane1.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/menuAdmin/taules/BorrarTaules.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }

    /**
     * Buida tot el contingut del anchor pane per poder fer que el anchor pane quedi en blanc
     */
    void recarregarEscena(){
        try {
            root = FXMLLoader.load(getClass().getResource("/administracio/MenuAdministracio.fxml"));
            scene = new Scene(root);
            stage = (Stage) bGestionarAliments.getScene().getWindow();
            Util.openGUI(scene, stage, "Administracio");
        } catch (IOException e) {
            e.printStackTrace();
        }
        var++;
    }
}