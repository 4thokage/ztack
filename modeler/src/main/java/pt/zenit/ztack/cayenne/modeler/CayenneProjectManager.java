package pt.zenit.ztack.cayenne.modeler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.cayenne.modeler.project.CayenneProject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gere todos os projetos Cayenne
 */
public class CayenneProjectManager {
    private static final Logger LOG = LoggerFactory.getLogger(CayenneProjectManager.class);

    private static List<CayenneProject> projects = new ArrayList<>();

    /**
     * Retorna o Projeto apra o caminho indicado
     *
     * @param path
     * @return {@link CayenneProject} encontrado
     */
    static CayenneProject projectForPath(final String path) {
        CayenneProject project = null;

        try {
            project = addProject(new CayenneProject(path));
        } catch (final MalformedURLException exception)
        {
            // FIXME jsr: handle this better!
            LOG.error("Could not open project", exception);
        }


        return project;
    }

    public static List<CayenneProject> getProjects() {
        return projects;
    }

    /**
     * Adiciona um CayenneProject á lista de projetos {@link CayenneProjectManager#projects}
     * @param project Projeto a adicionar
     * @return {@link CayenneProject} adicionado
     */
    private static CayenneProject addProject(final CayenneProject project) {
        projects.add(project);

        return project;
    }

    public static CayenneProject removeProject(final CayenneProject project) {
        projects.remove(project);

        return project;
    }
}
