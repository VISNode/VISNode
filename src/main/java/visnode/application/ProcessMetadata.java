
package visnode.application;

import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import visnode.pdi.Process;

/**
 *
 * @author Process metadata
 */
public class ProcessMetadata {

    /** Name */
    private String name;
    /** Description */
    private String description;

    public ProcessMetadata() {
        name = "";
        description = "";
    }
    
    /**
     * Returns the metadata from the class
     * 
     * @param process
     * @return ProcessMetadata
     */
    public static ProcessMetadata fromClass(Class<? extends Process> process) {
        try {
            InputStream stream = ProcessMetadata.class.getResourceAsStream('/' + process.getName().replace('.', '/') + ".json");
            return new GsonBuilder().create().fromJson(new InputStreamReader(stream), ProcessMetadata.class);
        } catch (Exception e) {
            ProcessMetadata metadata = new ProcessMetadata();
            String name = process.getSimpleName();
            name = name.replaceFirst("Process$", "");
            name = name.replaceAll("([a-z])([A-Z])", "$1 $2");
            metadata.setName(name);
            return metadata;
        }
    }

    /**
     * Sets the name
     * 
     * @param name 
     */
    private void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the description
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     * 
     * @param description 
     */
    private void setDescription(String description) {
        this.description = description;
    }
    
}
