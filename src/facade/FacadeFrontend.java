package facade;

import controller.frontend.MainController;
import controller.frontend.ScreensController;
import controller.frontend.StageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class FacadeFrontend {

    private static FacadeFrontend facade;

    private ScreensController screensController;
    private StageController stageController;
    private MainController mainController;
    
    private FacadeFrontend() {
        this.screensController = new ScreensController();
    }

    public static synchronized FacadeFrontend getInstance() {
        return (facade == null) ? facade = new FacadeFrontend() : facade;
    }

    public void initialize(Stage stage, Scenes scene) throws Exception {
        FXMLLoader loaderFXML = this.screensController.getLoaderFXML(scene);
        Parent loadedScreen = loaderFXML.load();
        this.mainController = loaderFXML.getController();
        this.stageController = new StageController(stage);
        this.stageController.changeMainStage(scene.getTitle(), loadedScreen);
    }

    public void changeScreean(Scenes scene) throws Exception {
        Parent loadedScreen = this.screensController.loadScreen(scene);
        this.stageController.changeMainStage(scene.getTitle(), loadedScreen);
    }

    public Parent getScreen(Scenes scene) throws Exception {
        return this.screensController.loadScreen(scene);
    }

    public double getStageHeigth() {
        return this.stageController.getStageY();
    }

    public double getStageWidth() {
        return this.stageController.getStageX();
    }

    public void showContentAuxStage(Scenes scene, String name) throws Exception {
        Parent content = this.screensController.loadScreen(scene);
        this.stageController.changeStageContent(name, scene.getTitle(), content);
    }

    public void addScreen(Scenes scene, Parent parent) throws Exception {
        this.screensController.addScreen(scene, parent);
    }

    public FXMLLoader getLoaderScreen(Scenes scene) {
        return this.screensController.getLoaderFXML(scene);
    }

}