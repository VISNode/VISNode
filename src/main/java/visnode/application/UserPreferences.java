package visnode.application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User preferences
 */
public class UserPreferences {
    
    /** Recent projects */
    private final Set<String> recentProjects;
    /** Recent input files */
    private final List<String> recentInputFiles;

    /**
     * Creates a new set of user preferences
     */
    public UserPreferences() {
        this.recentProjects = new LinkedHashSet<>();
        this.recentInputFiles = new ArrayList<>();
    }

    /**
     * Adds a file as a recent project
     * 
     * @param file 
     */
    public void addRecentProject(File file) {
        String name = file.getAbsolutePath();
        if (recentProjects.contains(name)) {
            recentProjects.remove(name);
        }
        recentProjects.add(name);
    }

    /**
     * Get the recent projects
     * 
     * @return List
     */
    public List<File> getRecentProjects() {
        List<File> files = recentProjects.stream().map(name -> new File(name)).collect(Collectors.toList());
        Collections.reverse(files);
        return files;
    }
    
}
