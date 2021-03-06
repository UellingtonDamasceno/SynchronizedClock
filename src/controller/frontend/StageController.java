package controller.frontend;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.Settings.Icons;

/**
 *
 * @author Uellington Damasceno
 */
public class StageController {

    private Map<String, Stage> stages;
    private final Pane container;

    public StageController(Stage mainStage) {
        this.stages = new HashMap();
        this.container = new Pane();
        this.stages.put("mainStage", mainStage);
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image(Icons.MAIN_ICON.getIconPath()));
    }
    
    public double getStageY(){
        return this.stages.get("mainStage").getHeight();
    }
    public double getStageX(){
        return this.stages.get("mainStage").getWidth();
    }
    
    /**
     * Solução de engenharia para esse momento eheheh 
     * Lembrar de desenvolver uma estrategia automatica;
     * @param offset 
     */
    public void incrementStageWidth(double offset){
        double width = this.stages.get("mainStage").getWidth();
        this.stages.get("mainStage").setMinWidth(width+offset);
        this.stages.get("mainStage").setMaxWidth(width+offset);
    }
    
    public void changeMainStage(String title, Parent content) {
        this.changeStageContent("mainStage", title, content);
    }

    public void changeStageContent(String stageName, String title,Parent newContent) {
        Stage stage;
        if (this.stages.containsKey(stageName)) {
            stage = stages.get(stageName);
        } else {
            stage = new Stage();
            stages.put(stageName, stage);
        }
        stage.setScene(new Scene(newContent));
        stage.setTitle(title);
        stage.show();
    }

}
