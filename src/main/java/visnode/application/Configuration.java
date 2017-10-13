package visnode.application;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * System configurations
 */
public class Configuration {

    /** Valid locale */
    private static final List LOCALES = Arrays.asList("pt-BR", "en-US");
    /** Configuration instance */
    private static Configuration instance;
    /** System locale */
    private Locale locale;
    
    /**
     * Creates a new system configuration
     */
    private Configuration() {
        this.locale = new Locale("en", "US");
        if (LOCALES.stream().
                anyMatch((l)-> l.equals(Locale.getDefault().toLanguageTag()))) {
            this.locale = Locale.getDefault();
        }
    }
    
    /**
     * Returns the system locale
     * 
     * @return Locale
     */
    public Locale getLocale() {
        return locale;
    }
    
    /**
     * Returns true if the locale is pt-BR
     * 
     * @return boolean
     */
    public boolean isLocalePtBR() {
        return getLocale().getLanguage().equals("pt");
    }
    
    /**
     * Returns the configuration instance
     * 
     * @return Configuration
     */
    public static Configuration get() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
    
    
}
