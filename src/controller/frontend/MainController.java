package controller.frontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Clock;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class MainController implements Initializable {

    @FXML
    private Label lblClock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Clock clock = new Clock();
        clock.getSimpleTimeProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(()->{
                lblClock.setText(newValue.toString());
            });
        });
        clock.initialize();
    }

}
