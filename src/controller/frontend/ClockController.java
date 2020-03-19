package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Pair;
import util.Settings.Images;
import util.Settings.Scenes;
import view.ClockView;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class ClockController implements Initializable {

    private ClockView clockView;
    @FXML
    private BorderPane bordePane;
    @FXML
    private StackPane stackPaneClock;
    @FXML
    private ImageView imageViewBackground;
    @FXML
    private Button btnSettings;

    private Pair<Parent, Object> settings;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.clockView = new ClockView(Color.AZURE, Color.rgb(50, 50, 50));

        this.clockView.setTranslateX(40);
        this.clockView.setTranslateY(32.50);
        this.clockView.getTransforms().add(new Scale(0.83f, 0.83f, 0, 0));

        this.imageViewBackground.setImage(new Image(Images.DIGITAL_CLOCK_BACKGROUND.getPath()));

        this.stackPaneClock.getChildren().add(this.clockView);

        try {
            FacadeFrontend.getInstance().addScreen(Scenes.CLOCK_SETTINGS);
            this.settings = FacadeFrontend.getInstance().getSceneAndController(Scenes.CLOCK_SETTINGS);
        } catch (Exception ex) {
            Logger.getLogger(ClockController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.btnSettings.setOnMouseClicked((event) -> {
            if (this.bordePane.getRight() == null) {
                this.bordePane.setRight(settings.getKey());
                FacadeFrontend.getInstance().incrementStageWidth(300);
            } else {
                FacadeFrontend.getInstance().incrementStageWidth(-300);
                this.bordePane.setRight(new Pane());
            }
        });
        if (!FacadeBackend.getInstance().isReference()) {
            FacadeBackend.getInstance().initializeSynchronization();
        }
    }
}
