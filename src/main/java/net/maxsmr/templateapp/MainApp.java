package net.maxsmr.templateapp;

import net.maxsmr.templateapp.util.TranslationsUtil;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) throws Exception {

        log.info("Starting Hello JavaFX and Maven demonstration application");

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        String fxmlFile = "/fxml/hello.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 400, 200);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle(TranslationsUtil.getInstance().formatString("title_window_format", Constants.VERSION_NAME));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.sizeToScene();
    }
    @Override
    public void stop() throws Exception {
        log.info("Stopping Hello JavaFX and Maven demonstration application");
        super.stop();
    }

    private static boolean checkIfRunning() {
        boolean alreadyRunning;

        try {
            JUnique.acquireLock(Constants.APP_ID);
            alreadyRunning = false;
        } catch (AlreadyLockedException e) {
            alreadyRunning = true;
        }

        return alreadyRunning;
    }

    public static void main(String[] args) {
        if (checkIfRunning()) {
            System.exit(0);
            return;
        }

        launch(args);
    }
}
