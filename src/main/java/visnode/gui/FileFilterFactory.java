
package visnode.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * File filter Factory
 */
public class FileFilterFactory {

    /**
     * Creates a project file filter
     * 
     * @return FileFilter
     */
    public static FileFilterList projectFileFilter() {
        return new FileFilterList(
            byExtension("VISNode projects", "vnp")
        );
    }

    /**
     * Creates the input file filter
     * 
     * @return FileFilter
     */
    public static FileFilterList inputFileFilter() {
        return new FileFilterList(
            byExtension("Input files", "jpg", "png", "gif", "dcm")
        );
    }

    /**
     * Creates the export file filter
     * 
     * @return FileFilter
     */
    public static FileFilterList exportFileFilter() {
        return new FileFilterList(
            byExtension("All output files", "jpg", "png")
        );
    }
    
    /**
     * Creates a filter by extension
     * 
     * @param name
     * @param extensions
     * @return FileNameExtensionFilter
     */
    private static FileNameExtensionFilter byExtension(String name, String... extensions) {
        String extensionsList = String.join(",", Arrays.asList(extensions)
                .stream()
                .map(string -> "." + string)
                .collect(Collectors.toList()));
        return new FileNameExtensionFilter(name + " (" + extensionsList + ')', extensions);
    }
                
    /**
     * List of file filters
     */
    public static class FileFilterList extends ArrayList<FileFilter> {

        public FileFilterList(FileFilter... filters) {
            super();
            for (FileFilter filter : filters) {
                add(filter);
            }
        }
        
        /**
         * Applies the file filter list
         * 
         * @param chooser 
         */
        public void apply(JFileChooser chooser) {
            for (FileFilter choosableFileFilter : chooser.getChoosableFileFilters()) {
                chooser.removeChoosableFileFilter(choosableFileFilter);
            }
            for (FileFilter fileFilter : this) {
                chooser.addChoosableFileFilter(fileFilter);
            }
        }
        
    }
    
}
