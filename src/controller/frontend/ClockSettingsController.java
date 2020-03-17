/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.frontend;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        
    }
    
  
}
