package pt.zenit.ztack.builder.gui.controller;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.gui.Composer;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller para elementos da barra superior (menu) na janela gráfica do {@link Composer}
 */
public class MenuController implements Initializable {

    public static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final HostServices hostServices;

    public MenuController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private MenuBar menuBar;

    @FXML
    private void handleNewFileAction(final ActionEvent event)
    {
        createNewFile();
    }

    @FXML
    private void handleOpenFileAction(final ActionEvent event)
    {
        openFile(false, Constants.ComposerFiles.DBUTILS_FILE.getFileDescription(), Constants.ComposerFiles.DBUTILS_FILE.getFileExtension());
    }

    @FXML
    private void handleDeployAction(final ActionEvent event) {
        openFile(false, Constants.ComposerFiles.COMPILE_BAT.getFileDescription(), Constants.ComposerFiles.COMPILE_BAT.getFileExtension());
    }

    @FXML
    private void handleManualAction(final ActionEvent event) {
        openFile(true, null, null );
    }

    @FXML
    private void handleCloseAction(final ActionEvent event) {
        closeApplication();
    }

    /**
     * Vai tratar dos inputs recebidos na janela da aplicação
     *
     * @param event {@link InputEvent}
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown())
            {
                switch (keyEvent.getCode()){
                    case N: createNewFile();
                    break;
                    case O: openFile(false, Constants.ComposerFiles.DBUTILS_FILE.getFileDescription(), Constants.ComposerFiles.DBUTILS_FILE.getFileExtension());
                    break;
                    case S: saveCurrentFile();
                    break;
                    case F1: showSettingsDialog();
                    break;
                    case P: openFile(true,null, null );
                    break;
                    case Q: closeApplication();
                    break;
                    default: closeApplication();
                    break;
                }
            }
        }
    }

    private void showSettingsDialog() {
        closeApplication();
    }

    private void saveCurrentFile() {
        closeApplication();
    }

    private void createNewFile() {
        closeApplication();
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        menuBar.setFocusTraversable(true);
    }

    /**
     * Abre um ficheiro existente no sistema com a descrição
     * e terminação passados por parametros, se a flag isManual for true, então abre o manual de utilização com localização pré definida
     *
     * @param isManual {@link Boolean} que indica se o ficheiro é ou não o manual de utilização
     * @param fileExtensionDescription {@link String} descrição do tipo do ficheiro
     * @param fileExtension {@link String} extensão (terminação) do ficheiro
     */
    private void openFile(boolean isManual, String fileExtensionDescription, String fileExtension) {
        if(isManual) {
            File file = new File(Constants.DefaultValues.PDF_MANUAL_PATH.getValue());
            hostServices.showDocument(file.getAbsolutePath());
        } else {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter(fileExtension, fileExtensionDescription);
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
            hostServices.showDocument(file.getAbsolutePath());
        }
    }

    private void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

}
