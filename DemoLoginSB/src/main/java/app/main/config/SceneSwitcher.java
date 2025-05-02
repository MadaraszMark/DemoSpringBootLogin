package app.main.config;

import app.main.DemoLoginSbApplication;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneSwitcher {

    public static void switchSceneWithFade(Node currentNode, String fxmlPath, String windowTitle) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), currentNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
                
                loader.setControllerFactory(DemoLoginSbApplication.getContext()::getBean);

                Parent newRoot = loader.load();

                Stage stage = (Stage) currentNode.getScene().getWindow();
                Scene newScene = new Scene(newRoot);

                stage.setTitle(windowTitle);
                stage.setScene(newScene);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        fadeOut.play();
    }
}
