package controller.frontend;

import facade.FacadeBackend;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import util.Settings.Icons;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class ClockSettingsController implements Initializable {

    @FXML
    private ComboBox<Integer> comboBoxDefaultTime;
    @FXML
    private ComboBox<TimeUnit> comboBoxTimeUnit;
    @FXML
    private Slider sliderOffset;
    @FXML
    private ToggleButton toggleButonState;
    @FXML
    private ImageView imageViewState;
    @FXML
    private VBox vboxRootType;
    @FXML
    private VBox vboxSync;
    @FXML
    private VBox vboxOffset;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.imageViewState.setImage(new Image(Icons.OK.getIconPath()));
        this.toggleButonState.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.imageViewState.setImage(new Image(Icons.OK.getIconPath()));
            } else {
                this.imageViewState.setImage(new Image(Icons.CANCEL.getIconPath()));
            }
        });

        this.comboBoxDefaultTime.getItems().addAll(Arrays.asList(1, 2, 3, 5, 10));
        this.comboBoxDefaultTime.getSelectionModel().selectFirst();
        this.comboBoxDefaultTime.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    TimeUnit selectedItem = this.comboBoxTimeUnit.getSelectionModel().getSelectedItem();
                    FacadeBackend.getInstance()
                            .updateSyncInterval(newValue, selectedItem);
                });

        this.comboBoxTimeUnit.getItems()
                .addAll(Arrays.asList(TimeUnit.values()).stream()
                        .skip(3)
                        .limit(3)
                        .collect(Collectors.toList()));
        this.comboBoxTimeUnit.getSelectionModel().selectFirst();
        this.comboBoxTimeUnit.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            Integer selectedItem = this.comboBoxDefaultTime
                                    .getSelectionModel()
                                    .getSelectedItem();
                            FacadeBackend.getInstance()
                                    .updateSyncInterval(selectedItem, newValue);
                        });
        if (FacadeBackend.getInstance().isReference()) {
            this.vboxRootType.getChildren().remove(this.vboxSync);
        } else {
            this.vboxRootType.getChildren().remove(this.vboxOffset);
        }
    }

    public void setReference(boolean isReference) {
        if (isReference) {
            Platform.runLater(() -> {
                this.vboxRootType.getChildren().remove(this.vboxSync);
                this.vboxRootType.getChildren().add(this.vboxOffset);
            });
        } else {
            Platform.runLater(() -> {
                this.vboxRootType.getChildren().remove(this.vboxOffset);
                this.vboxRootType.getChildren().add(this.vboxSync);
            });
        }
    }
}
