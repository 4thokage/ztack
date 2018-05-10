package pt.zenit.ztack.cayenne.modeler.utility;

import java.io.File;

/**
 * CayenneUserDirHelper representa uma pasta onde é guardada toda a informação relacionada com Projetos Cayenne
 * na maquina local. Normalmente em <code>$HOME/.cayenne</code .
 */
public class CayenneUserDirHelper {

    private static final String CAYENNE_DIR = ".cayenne";

    /**
     * Nome da property alternativa
     * Cayenne User Directory (em vez do default: "$HOME/.cayenne").
     *
     * @since 1.1
     */
    private static final String ALT_USER_DIR_PROPERTY = "cayenne.userdir";

    private File cayenneUserDir;

    private static CayenneUserDirHelper sharedInstance;

    public static CayenneUserDirHelper getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new CayenneUserDirHelper();
        }
        return sharedInstance;
    }

    /**
     * Constructor for CayenneUserDirHelper.
     */
    private CayenneUserDirHelper() {
        super();

        File tmpDir;
        String dirName = System.getProperty(ALT_USER_DIR_PROPERTY);

        if (dirName != null) {
            tmpDir = new File(dirName);
        }
        else {
            File homeDir = new File(System.getProperty("user.home"));
            tmpDir = new File(homeDir, CAYENNE_DIR);
        }

        if (tmpDir.exists() && !tmpDir.isDirectory()) {
            tmpDir = null;
        }
        else if (tmpDir.exists() && !tmpDir.canRead()) {
            tmpDir = null;
        }
        else if (!tmpDir.exists()) {
            tmpDir.mkdirs();
            if (!tmpDir.exists()) {
                tmpDir = null;
            }
        }

        cayenneUserDir = tmpDir;
    }

    /**
     * Returns a directory object where all user Cayenne-related configuration is stored.
     * May return null if the directory is not accessible for whatever reason.
     */
    public File getDirectory() {
        return cayenneUserDir;
    }

    /**
     * Return false if the directory is not accessible for any reason at least for
     * reading.
     */
    public boolean canRead() {
        return cayenneUserDir != null;
    }

    /**
     * Return false if the directory is not accessible for any reason at least for
     * reading.
     */
    public boolean canWrite() {
        return cayenneUserDir != null && cayenneUserDir.canWrite();
    }

    public File resolveFile(String name) {
        return new File(cayenneUserDir, name);
    }
}
