package pt.zenit.ztack.builder.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.gui.controller.HostServicesControllerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Ponto de entrada da parte gráfica da aplicação.
 */
public class Composer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        /** Carrega o ficheiro de configuração gráfica da aplicação */
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.DefaultValues.FXML_FILE_PATH.getValue()));

        loader.setControllerFactory(new HostServicesControllerFactory(getHostServices()));
        loader.setResources(ResourceBundle.getBundle("gui/Composer/Composer", Locale.forLanguageTag("pt")));
        final Parent fxmlRoot = loader.load();

        primaryStage.setTitle(Constants.DefaultValues.APP_TITLE.getValue());
        primaryStage.setScene(new Scene(fxmlRoot));
        primaryStage.show();
    }

}
