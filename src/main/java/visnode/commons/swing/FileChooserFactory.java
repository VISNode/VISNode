/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.swing;

import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import visnode.application.ExceptionHandler;
import visnode.application.InvalidOpenFileException;
import visnode.gui.FileFilterFactory;

/**
 * File chooser factory
 */
public class FileChooserFactory {
    
    /**
     * Creates a dialog for saving projects
     * 
     * @return FileChooserBuilder
     */
    public static FileChooserBuilder saveProject() {
        return new FileChooserBuilder()
                .method(Method.SAVE)
                .filter(FileFilterFactory.projectFileFilter())
                .title("Save");
    }
    
    /**
     * Creates a dialog for opening projects
     * 
     * @return FileChooserBuilder
     */
    public static FileChooserBuilder openProject() {
        return new FileChooserBuilder()
                .method(Method.OPEN)
                .filter(FileFilterFactory.projectFileFilter())
                .title("Open");
    }
    
    /**
     * Creates a dialog for opening images
     * 
     * @return FileChooserBuilder
     */
    public static FileChooserBuilder openImage() {
        return new FileChooserBuilder()
                .method(Method.OPEN)
                .filter(FileFilterFactory.inputFileFilter())
                .title("Open");
    }
    
    /**
     * Creates a dialog for exporting images
     * 
     * @return FileChooserBuilder
     */
    public static FileChooserBuilder exportImage() {
        return new FileChooserBuilder()
                .method(Method.SAVE)
                .filter(FileFilterFactory.exportFileFilter())
                .title("Export");
    }
    
    /**
     * Builder for file choosers
     */
    public static class FileChooserBuilder {
        
        /** Chooser */
        private final JFileChooser chooser;
        /** Method for the chooser */
        private Method method;

        /**
         * Creates a new Chooser Builder
         */
        public FileChooserBuilder() {
            chooser = new JFileChooser();
        }
        
        /**
         * Sets the method for the dialog
         * 
         * @param method
         * @return FileChooserBuilder
         */
        private FileChooserBuilder method(Method method) {
            this.method = method;
            return this;
        }
        
        /**
         * Sets the filter
         * 
         * @param filter
         * @return FileChooserBuilder
         */
        private FileChooserBuilder filter(FileFilterFactory.FileFilterList filter) {
            filter.apply(chooser);
            return this;
        }
        
        /**
         * Sets the dialog title
         * 
         * @param title
         * @return FileChooserBuilder
         */
        private FileChooserBuilder title(String title) {
            chooser.setDialogTitle(title);
            return this;
        }

        /**
         * Acepts the file chooser and calls the consumer when ready
         * 
         * @param consumer 
         */
        public void accept(Consumer<File> consumer) {
            try {
                if (method == Method.OPEN) {
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        consumer.accept(chooser.getSelectedFile());
                    }
                } else {
                    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        if (!chooser.getFileFilter().accept(chooser.getSelectedFile())) {
                            throw new InvalidOpenFileException();
                        }
                        if (!checkOverride(chooser.getSelectedFile())) {
                            return;
                        }
                        consumer.accept(chooser.getSelectedFile());
                    }
                }
            } catch (InvalidOpenFileException ex) {
                ExceptionHandler.get().handle(ex);
            }
        }
        
        /**
         * Checks file override
         * 
         * @param file
         * @return boolean
         */
        private boolean checkOverride(File file) {
            if (!file.exists()) {
                return true;
            }
            int r = JOptionPane.showConfirmDialog(null, "The file " + file + " already exists. Do you wish to override it?");
            return r == JOptionPane.OK_OPTION;
        }
        
    }
    
    /**
     * Method for the dialog
     */
    private static enum Method {
        OPEN, SAVE
    }
    
}
