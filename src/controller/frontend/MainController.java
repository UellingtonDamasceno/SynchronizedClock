package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import util.MaskFieldUtil;
import util.Settings.Connection;
import util.Settings.Scenes;

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
        MaskFieldUtil.numericField(this.txtKnownPort);
        MaskFieldUtil.numericField(this.txtThisPort);

        this.checkBoxDefault.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
//                this.txtKnownPort.setText(Connection.DEFAULT_PORT.toString());
                this.txtKnownServer.setText(Connection.DEFAULT_IP.toString());
            } else {
//                this.txtKnownPort.setText("");
                this.txtKnownServer.setText("");
            }
            this.checkBoxIsReference.setDisable(newValue);
//            this.txtKnownPort.setDisable(newValue);
            this.txtKnownServer.setDisable(newValue);
        });

        List<TextField> allFields = Arrays.asList(txtKnownPort, txtKnownServer, txtThisPort);
        allFields.stream().forEach((oneField) -> {
            oneField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (observable.getValue().isEmpty()) {
                    this.btnOK.setDisable(true);
                } else {
                    Optional<TextField> emptyTextField = allFields.stream()
                            .filter((currentTextField) -> {
                                return currentTextField.getText().isEmpty();
                            }).findAny();
                    this.btnOK.setDisable(emptyTextField.isPresent() && !this.checkBoxIsReference.isSelected());
                }
            });
        });

        this.checkBoxIsReference.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.txtKnownPort.setDisable(newValue);
            this.txtKnownServer.setDisable(newValue);
            this.checkBoxDefault.setDisable(newValue);
            
            if (newValue && !this.txtThisPort.getText().isEmpty()) {
                this.btnOK.setDisable(false);
            } else if (!newValue) {
                allFields.stream().filter((oneField) -> {
                    return oneField.getText().isEmpty();
                }).findAny().ifPresent((field) -> {
                    this.btnOK.setDisable(true);
                });
            }
        });

    }

    @FXML
    private void next(ActionEvent event) throws Exception {
        try {
            String serverIP = this.txtKnownServer.getText();
            String knownPort = this.txtKnownPort.getText();
            String thisPort = this.txtThisPort.getText();
            if (this.checkBoxIsReference.isSelected()) {
                FacadeBackend.getInstance().initialize(Integer.parseInt(thisPort));
            } else {
                FacadeBackend.getInstance()
                        .initialize(serverIP,
                                Integer.parseInt(knownPort),
                                Integer.parseInt(thisPort));
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacadeFrontend.getInstance().changeScreean(Scenes.CLOCK);
    }

}
