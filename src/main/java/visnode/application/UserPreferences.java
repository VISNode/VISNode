package visnode.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User preferences
 */
public class UserPreferences {
    
    /** Maximum recent files */
    private static final int MAX_RECENTS = 10;
    /** Recent projects */
    private final List<String> recentProjects;
    /** Recent input files */
    private final List<String> recentInputFiles;

    /**
     * Creates a new set of user preferences
     */
    public UserPreferences() {
        this.recentProjects = new ArrayList<>();
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
        recentProjects.add(0, name);
        while (recentProjects.size() > MAX_RECENTS) {
            recentProjects.remove(MAX_RECENTS);
        }
    }

    /**
     * Get the recent projects
     * 
     * @return List
     */
    public List<File> getRecentProjects() {
        return recentProjects.stream().map(name -> new File(name)).collect(Collectors.toList());
    }

    /**
     * Adds a file as a recent inputs
     * 
     * @param file 
     */
    public void addRecentInput(File file) {
        String name = file.getAbsolutePath();
        if (recentInputFiles.contains(name)) {
            recentInputFiles.remove(name);
        }
        recentInputFiles.add(0, name);
        while (recentInputFiles.size() > MAX_RECENTS) {
            recentInputFiles.remove(MAX_RECENTS);
        }
    }

    /**
     * Get the recent inputs
     * 
     * @return List
     */
    public List<File> getRecentInput() {
        return recentInputFiles.stream().map(name -> new File(name)).collect(Collectors.toList());
    }
    
}
