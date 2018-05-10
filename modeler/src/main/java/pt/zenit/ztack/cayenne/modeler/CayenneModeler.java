package pt.zenit.ztack.cayenne.modeler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.cayenne.configuration.server.ServerModule;
import org.apache.cayenne.di.DIBootstrap;
import org.apache.cayenne.di.Module;
import pt.zenit.ztack.cayenne.modeler.di.Injection;
import pt.zenit.ztack.cayenne.modeler.layout.MainWindowLayout;
import pt.zenit.ztack.cayenne.modeler.layout.PreferencesLayout;
import pt.zenit.ztack.cayenne.modeler.layout.SplashLayout;
import pt.zenit.ztack.cayenne.modeler.project.CayenneProject;
import org.apache.cayenne.project.ProjectModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.stage.Stage;

/** Classe principal do submodulo Ztack: Modeler */
public class CayenneModeler extends Application
{
    private static final Logger LOG = LoggerFactory.getLogger(CayenneModeler.class);

    private static PreferencesLayout preferencesLayout;


    /**
     * Mostra o SplashScreen {@link SplashLayout}
     * @param primaryStage Stage de entrada
     * @throws IOException se o ficheiro do layout (fxml) não for encontrado no disco
     */
    @Override
    public void start(final Stage primaryStage) throws IOException {
        LOG.info("Starting modeler...");

        SplashLayout splashLayout = new SplashLayout(primaryStage);

        splashLayout.show();
    }

    /** Abre o projeto encontrado no caminho indicado
     *
     * @param path String com o caminho para o projectp
     * @throws IOException se não encontrar o projeto (ex: caminho invalido)
     */
    public static void openProject(final String path) throws IOException {
        final CayenneProject cayenneProject = CayenneProjectManager.projectForPath(path);

        if (cayenneProject != null)
        {
            openProject(cayenneProject);
        }
        else
        {
            LOG.error("Couldn't open Cayenne Model Project.");
        }
    }

    /**
     * Abre o Projecto {@link CayenneProject} em argumento
     * @param cayenneProject o Projeto cayenne que vai ser aberto
     * @throws IOException se não encontrar o projeto (ex: caminho invalido)
     */
    public static void openProject(final CayenneProject cayenneProject) throws IOException {
        // TODO: jsr - talvez se deva guardar este valor noutro lado?
        final MainWindowLayout mainWindow = new MainWindowLayout();

        mainWindow.show();
        mainWindow.displayCayenneProject(cayenneProject);
    }

    /**
     * Abre a janela das preferencias do Modeler
     * @throws Exception se o ficheiro JavaFX (fxml) indicado no construtor {@link PreferencesLayout} não for encontrado no disco
     */
    public static void openPreferences() throws IOException {
        if (preferencesLayout == null)
            preferencesLayout = new PreferencesLayout();

        preferencesLayout.show();
    }


    /**
     * Adiciona novo(s) modulo ao processo
     * @param modules Lista de {@link Module} a adicionar
     * @return lista de {@link Module} adicionados
     */
    private static Collection<Module> appendModules(final Collection<Module> modules)
    {
        modules.add(new ServerModule("CayenneModeler"));
        modules.add(new ProjectModule());

        return modules;
    }

    /**
     * Ponto de entrada da aplicação (FX App)
     * @param args
     */
    public static void main(final String[] args)
    {
        Injection.setInjector(DIBootstrap.createInjector(appendModules(new ArrayList<>())));

        launch(args);
    }
}
