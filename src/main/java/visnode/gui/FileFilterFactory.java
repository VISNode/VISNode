
package visnode.gui;

import java.util.ArrayList;
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
            new FileNameExtensionFilter("VISNode projects", "vnp")
        );
    }

    /**
     * Creates the input file filter
     * 
     * @return FileFilter
     */
    public static FileFilterList inputFileFilter() {
        return new FileFilterList(
            new FileNameExtensionFilter("Input files", "jpg", "png", "dcm")
        );
    }

    /**
     * Creates the export file filter
     * 
     * @return FileFilter
     */
    public static FileFilterList exportFileFilter() {
        return new FileFilterList(
            new FileNameExtensionFilter("All output files", "jpg", "png")
        );
    }
                
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
