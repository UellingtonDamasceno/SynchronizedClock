package controller.frontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class MainController implements Initializable {

    @FXML
    private TextField txtKnownServer;
    @FXML
    private TextField txtKnownPort;
    @FXML
    private TextField txtThisPort;
    @FXML
    private CheckBox checkBoxDefault;
    @FXML
    private CheckBox checkBoxIsReference;
    @FXML
    private Button btnOK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void next(ActionEvent event) {
    }

}
