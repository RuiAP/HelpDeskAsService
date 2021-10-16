package helpdesk;

/**
 * A "global" static class with the application registry of well known objects
 *
 */
public class Application {

    public static final String VERSION = "1.0.0";
    public static final String COPYRIGHT = "Grupo 2NB_1";

    private static final AppSettings SETTINGS = new AppSettings();

    public static AppSettings settings() {
        return SETTINGS;
    }

    private Application() {
        // private visibility to ensure singleton & utility
    }
}
