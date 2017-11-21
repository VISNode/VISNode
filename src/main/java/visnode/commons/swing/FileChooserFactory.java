package visnode.commons.swing;

import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
     * @return SingleFileChooserBuilder
     */
    public static SingleFileChooserBuilder saveProject() {
        return new SingleFileChooserBuilder(
                new FileChooserBuilder()
                        .method(Method.SAVE)
                        .files()
                        .filter(FileFilterFactory.projectFileFilter())
                        .title("Save")
        );
    }

    /**
     * Creates a dialog for opening projects
     *
     * @return SingleFileChooserBuilder
     */
    public static SingleFileChooserBuilder openProject() {
        return new SingleFileChooserBuilder(
                new FileChooserBuilder()
                        .method(Method.OPEN)
                        .files()
                        .filter(FileFilterFactory.projectFileFilter())
                        .title("Open")
        );
    }

    /**
     * Creates a dialog for opening images
     *
     * @return SingleFileChooserBuilder
     */
    public static SingleFileChooserBuilder openImage() {
        return new SingleFileChooserBuilder(
                new FileChooserBuilder()
                        .method(Method.OPEN)
                        .files()
                        .filter(FileFilterFactory.inputFileFilter())
                        .title("Open")
        );
    }

    /**
     * Creates a dialog for opening images with multiple file selections
     *
     * @return MultiFileChooserBuilder
     */
    public static MultiFileChooserBuilder openImages() {
        return new MultiFileChooserBuilder(
                new FileChooserBuilder()
                        .method(Method.OPEN)
                        .files()
                        .filter(FileFilterFactory.inputFileFilter())
                        .title("Open")
        );
    }

    /**
     * Creates a dialog for exporting images
     *
     * @return FileChooserBuilder
     */
    public static SingleFileChooserBuilder exportImage() {
        return new SingleFileChooserBuilder(
                new FileChooserBuilder()
                        .method(Method.SAVE)
                        .files()
                        .filter(FileFilterFactory.exportFileFilter())
                        .title("Export")
        );
    }

    /**
     * Builder for file choosers with single files
     */
    public static class SingleFileChooserBuilder {

        /** File chooser */
        private final FileChooserBuilder builder;

        public SingleFileChooserBuilder(FileChooserBuilder builder) {
            this.builder = builder;
        }

        /**
         * Accepts the file chooser and calls the consumer when ready
         *
         * @param consumer
         */
        public void accept(Consumer<File> consumer) {
            try {
                if (builder.method == Method.OPEN) {
                    if (builder.chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        consumer.accept(builder.chooser.getSelectedFile());
                    }
                } else {
                    if (builder.chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File file = builder.addExtensionIfRequired(builder.chooser.getSelectedFile(), builder.chooser.getFileFilter());
                        if (!builder.chooser.getFileFilter().accept(file)) {
                            throw new InvalidOpenFileException();
                        }
                        if (!builder.checkOverride(file)) {
                            return;
                        }
                        consumer.accept(file);
                    }
                }
            } catch (Exception ex) {
                ExceptionHandler.get().handle(ex);
            }
        }

    }

    /**
     * Builder for file choosers with multi files
     */
    public static class MultiFileChooserBuilder {

        /** File chooser */
        private final FileChooserBuilder builder;

        public MultiFileChooserBuilder(FileChooserBuilder builder) {
            this.builder = builder;
            this.builder.chooser.setMultiSelectionEnabled(true);
        }

        /**
         * Accepts the file chooser and calls the consumer when ready
         *
         * @param consumer
         */
        public void accept(Consumer<File[]> consumer) {
            try {
                if (builder.method == Method.OPEN) {
                    if (builder.chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        consumer.accept(builder.chooser.getSelectedFiles());
                    }
                }
            } catch (Exception ex) {
                ExceptionHandler.get().handle(ex);
            }
        }

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
         * Selects only files
         *
         * @return FileChooserBuilder
         */
        private FileChooserBuilder files() {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            return this;
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

        /**
         * Adds the extension to the file if required
         *
         * @param file
         * @param fileFilter
         * @return File
         */
        private File addExtensionIfRequired(File file, FileFilter fileFilter) {
            String name = file.getName();
            // If there is an extension
            if (name.indexOf('.', name.length() - 4) >= 0) {
                return file;
            }
            if (fileFilter instanceof FileNameExtensionFilter) {
                FileNameExtensionFilter extensions = (FileNameExtensionFilter) fileFilter;
                return new File(file.getAbsolutePath() + '.' + extensions.getExtensions()[0]);
            }
            return file;
        }

    }

    /**
     * Method for the dialog
     */
    private static enum Method {
        OPEN, SAVE
    }

}
