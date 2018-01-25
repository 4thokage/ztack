package pt.zenit.ztack.cayenne.modeler.layout;

import java.io.File;
import java.io.IOException;

import pt.zenit.ztack.cayenne.modeler.CayenneModeler;
import pt.zenit.ztack.cayenne.modeler.preferences.ModelerPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * SplashLayout: Controller da janela inicial da applicação
 * @author jsilvaro
 * @version 2018-01-25
 */
@SuppressWarnings("unused")
public class SplashLayout extends AbstractWindowLayout
{
    private static final Logger LOG = LoggerFactory.getLogger(SplashLayout.class);
    private static final String SPLASH_ICON_SIZE = "16px";

    @FXML
    private ListView<String> projectListView = new ListView<>();

    @FXML
    private Button newProjectButton = new Button();
    @FXML
    private Button openProjectButton = new Button();
    @FXML
    private Button appBuilderButton = new Button();

    public SplashLayout(final Stage initialStage) throws IOException
    {
        super(initialStage, "/layouts/SplashLayout.fxml");

        initializeStyle(StageStyle.DECORATED);
        setResizable(false);
    }

    public void initialize()
    {
        final ObservableList<String> projects =
            FXCollections.observableArrayList(ModelerPreferences.getLastProjFiles());

        projectListView.setItems(projects);
        projectListView.getSelectionModel().select(0);

        newProjectButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS_SQUARE, SPLASH_ICON_SIZE));
        openProjectButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER_OPEN, SPLASH_ICON_SIZE));
        appBuilderButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BOMB, SPLASH_ICON_SIZE));
    }

    public void onOpenClicked()
    {
        try
        {
            final File file = browseFile("Open Cayenne Model","Cayenne Projects","cayenne*.xml");

            if (file != null)
            {
                CayenneModeler.openProject(file.getAbsolutePath());
                hide();
            }
            else
            {
                LOG.info("Open canceled");
            }
        }
        catch (final Exception exception)
        {

            LOG.error("Could not load Cayenne model", exception);
        }
    }

    private File browseFile(String fileDialogTitle, String fileDescription, String fileExtension) {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(fileDialogTitle);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll
                (
                        new FileChooser.ExtensionFilter(fileDescription, fileExtension),
                        new FileChooser.ExtensionFilter("All Files", "*.*")
                );

        return fileChooser.showOpenDialog(getStage());
    }

    public void onNewClicked()
    {
        LOG.debug("open clicked");
    }

    public void onOpenProjectSelected(final MouseEvent event)
    {
        if (event.getClickCount() == 2 && projectListView.getItems().size() > 0) {
            openSelectedModel();
        }
    }

    public void onAppBuilderClicked() {
        try
        {
            final File file = browseFile("Open ZTack Builder Definition File","ZTack Builder Definition Files","ZBDF*.xlsm");

            if (file != null)
            {
                CayenneModeler.openProject(file.getAbsolutePath());
                hide();
            }
            else
            {
                LOG.info("Open canceled");
            }
        }
        catch (final Exception exception)
        {

            LOG.error("Could not load App Builder File", exception);
        }
    }

    public void onKeyTyped(final KeyEvent event)
    {
        if (event.getCharacter().equals("\r"))
            openSelectedModel();
    }

    private void openSelectedModel()
    {
        try
        {
            CayenneModeler.openProject(projectListView.getSelectionModel().getSelectedItem());
            hide();
        }
        catch (final Exception exception)
        {
            LOG.error("Could not load Selected Cayenne model", exception);
        }
    }
}
