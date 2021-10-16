package helpdesk;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the application settings.
 *
 * @author Paulo Gandra Sousa
 */
public class AppSettings {

    private static String action = "none";

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSettings.class);

    private static final String PROPERTIES_RESOURCE = "helpDesk.properties";
    private static final String REPOSITORY_FACTORY_KEY = "persistence.repositoryFactory";
    private static final String GRAMATICA_KEY = "interpretadorHelpdesk.interpretadorClass";
    private static final String UI_MENU_LAYOUT_KEY = "ui.menu.layout";
    private static final String PERSISTENCE_UNIT_KEY = "persistence.persistenceUnit";
    private static final String SCHEMA_GENERATION_KEY = "javax.persistence.schema-generation.database.action";
    private static final String ALGORITMO_ASSIGNACAO_TAREFAS_KEY = "core.tarefas.mecanismo.assignacaoTarefas";
    private static final String ALGORITMO_ASSIGNACAO_EXECUTOR_KEY = "core.tarefas.mecanismo.assignacaoExecutor";

    private final Properties applicationProperties = new Properties();

    public AppSettings() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream propertiesStream = this.getClass().getClassLoader()
                .getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (propertiesStream != null) {
                this.applicationProperties.load(propertiesStream);
            } else {
                throw new FileNotFoundException(
                        "property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
            }
        } catch (final IOException exio) {
            setDefaultProperties();

            LOGGER.warn("Loading default properties", exio);
        }
    }

//    private void saveProperties() {
//        try {
//            URL url = this.getClass().getClassLoader()
//                    .getResource(PROPERTIES_RESOURCE);
//            File file = Paths.get(url.toURI()).toFile();
//            OutputStream outputStream = new FileOutputStream(file);
//            if (outputStream != null) {
//                this.applicationProperties.store(outputStream, "properties");
//                outputStream.close();
//                loadProperties();
//            } else {
//                throw new FileNotFoundException(
//                        "property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
//            }
//        } catch (final IOException | URISyntaxException exio) {
//            setDefaultProperties();
//            LOGGER.warn("Loading default properties", exio);
//        }
//    }

    private void setDefaultProperties() {
        this.applicationProperties.setProperty(REPOSITORY_FACTORY_KEY,
                "persistence.impl.jpa.JpaRepositoryFactory");
        this.applicationProperties.setProperty(UI_MENU_LAYOUT_KEY, "horizontal");
        this.applicationProperties.setProperty(PERSISTENCE_UNIT_KEY, "helpdeskAsServicePU");
    }

    public Boolean isMenuLayoutHorizontal() {
        return "horizontal"
                .equalsIgnoreCase(this.applicationProperties.getProperty(UI_MENU_LAYOUT_KEY));
    }

    public String getPersistenceUnitName() {
        return this.applicationProperties.getProperty(PERSISTENCE_UNIT_KEY);
    }

    public String getRepositoryFactory() {
        return this.applicationProperties.getProperty(REPOSITORY_FACTORY_KEY);
    }

    public String getInterpretadorClass() {
        return this.applicationProperties.getProperty(GRAMATICA_KEY);
    }

    public String getAlgoritmoAssignacaoTarefas() {
        return this.applicationProperties.getProperty(ALGORITMO_ASSIGNACAO_TAREFAS_KEY);
    }

    public String getAlgoritmoAssignacaoExecutor() {
        return this.applicationProperties.getProperty(ALGORITMO_ASSIGNACAO_EXECUTOR_KEY);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Map getExtendedPersistenceProperties() {
        final Map ret = new HashMap();
//        ret.put(SCHEMA_GENERATION_KEY,
//                this.applicationProperties.getProperty(SCHEMA_GENERATION_KEY));
        ret.put(SCHEMA_GENERATION_KEY,
                action);
        return ret;
    }

    public String getProperty(String prop) {
        return this.applicationProperties.getProperty(prop);
    }

    public void setAction(String value) {
        action = value;
        //applicationProperties.setProperty(prop, value);
        //saveProperties();
    }
}
