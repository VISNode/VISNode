package visnode.application;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Class for persisting user preferences
 */
public class UserPreferencesPersistor {
    
    /**
     * Persists the user preferences
     * 
     * @param preferences 
     */
    public void persist(UserPreferences preferences) {
        createPathIfNotExist();
        String json = new GsonBuilder().create().toJson(preferences);
        try (PrintWriter writer = new PrintWriter(getPreferencesFile())) {
            writer.print(json);
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
    }
    
    /**
     * Loads the user preferences
     * 
     * @return UserPreferences
     */
    public UserPreferences load() {
        if (preferencesFileExists()) {
            try { 
                FileReader fileReader = new FileReader(getPreferencesFile());
                return new GsonBuilder().create().fromJson(fileReader, UserPreferences.class);
            } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
                ExceptionHandler.get().handle(e);
            }
        }
        return new UserPreferences();
    }
    
    /**
     * Checks if the preference file exists
     * 
     * @return boolean
     */
    private boolean preferencesFileExists() {
        return new File(getPreferencesFile()).exists();
    }
    
    /**
     * Returns the preferences path
     * 
     * @return String
     */
    private String getPreferencesPath() {
        return System.getProperty("user.home") + "/.visnode/";
    }
    
    /**
     * Returns the preferences file
     * 
     * @return String
     */
    private String getPreferencesFile() {
        return getPreferencesPath() + "preferences.json";
    }

    /**
     * Creates the path if it doesn't exist
     */
    private void createPathIfNotExist() {
        File path = new File(getPreferencesPath());
        if (!path.exists()) {
            path.mkdir();
        }
    }
    
}
