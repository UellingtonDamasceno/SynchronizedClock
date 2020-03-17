package controller.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;
import util.Settings.Scenes;

/**
 *
 * @author Uellington Damasceno
 */
public class ScreensController {

    private final Map<Scenes, Pair<Parent, Object>> allScreens;

    private Scenes lastScreenLoaded;

    public ScreensController() {
        this.lastScreenLoaded = null;
        this.allScreens = new HashMap();
    }

    public FXMLLoader getLoaderFXML(Scenes scene) {
        return new FXMLLoader(getClass().getResource(scene.toString()));
    }

    public Pair<Parent, Object> getOrLoadScreen(Scenes scene) throws Exception {
        return (scene.isCache()) ? loadScreenInCache(scene) : loadScreen(scene);
    }

    public Pair<Parent, Object> loadScreen(Scenes scene) throws IOException {
        FXMLLoader loader = this.getLoaderFXML(scene);
        Parent screenLoaded = loader.load();
        Object controller = loader.getController();
        return new Pair(screenLoaded, controller);
    }

    public Pair<Parent, Object> addScreen(Scenes scene) throws Exception {
        Pair<Parent, Object> sceneAndController = this.loadScreen(scene);
        return this.addScreen(scene, sceneAndController);
    }

    public Pair<Parent, Object> addScreen(Scenes scene, Parent content, Object controller) throws Exception {
        if (scene != null && content != null) {
            allScreens.put(scene, new Pair(content, controller));
            return this.loadScreen(scene);
        } else {
            throw new Exception("Id ou Conteudo nulo");
        }
    }

    public Pair<Parent, Object> addScreen(Scenes key, Pair<Parent, Object> value) throws Exception {
        if (key == null) {
            throw new Exception("ID é nulo.");
        } else if (value == null) {
            throw new Exception("Value é nulo.");
        } else if (value.getKey() == null) {
            throw new Exception("Cena é nula.");
        } else if (value.getValue() == null) {
            throw new Exception("O controlador é nulo.");
        } else {
            this.allScreens.put(key, value);
            return value;
        }
    }

    public Pair<Parent, Object> getSceneAndController(Scenes scene) throws Exception {
        if (scene != null) {
            if (allScreens.containsKey(scene)) {
                return allScreens.get(scene);
            } else {
                return this.getOrLoadScreen(scene);
            }
        } else {
            throw new Exception("Id da tela é nulo");
        }
    }

    private Pair<Parent, Object> loadScreenInCache(Scenes scene) throws Exception {
        if (!(lastScreenLoaded == scene) && !allScreens.containsKey(scene)) {
            this.addScreen(scene);
        }
        this.lastScreenLoaded = scene;
        return this.getSceneAndController(scene);
    }
}
