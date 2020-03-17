package facade;

import controller.frontend.MainController;
import controller.frontend.ScreensController;
import controller.frontend.StageController;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;
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
        Pair<Parent, Object> loadedScreen = this.screensController.getOrLoadScreen(scene);
        this.mainController = (MainController) loadedScreen.getValue();
        this.stageController = new StageController(stage);
        this.stageController.changeMainStage(scene.getTitle(), loadedScreen.getKey());
    }

    public void changeScreean(Scenes scene) throws Exception {
        Parent loadedScreen = this.screensController.getOrLoadScreen(scene).getKey();
        this.stageController.changeMainStage(scene.getTitle(), loadedScreen);
    }

    public Pair<Parent, Object> getSceneAndController(Scenes scene) throws Exception {
        return this.screensController.getOrLoadScreen(scene);
    }

    public double getStageHeigth() {
        return this.stageController.getStageY();
    }

    public double getStageWidth() {
        return this.stageController.getStageX();
    }

    public void incrementStageWidth(double offset){
        this.stageController.incrementStageWidth(offset);
    }
    
    public void showContentInAuxStage(Scenes scene, String stageName) throws Exception {
        Parent content = this.screensController.getOrLoadScreen(scene).getKey();
        this.stageController.changeStageContent(stageName, scene.getTitle(), content);
    }

    public void addScreen(Scenes scene) throws Exception {
        this.screensController.addScreen(scene);
    }
    
}
