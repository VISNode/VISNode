package visnode.application;

/**
 * Invalid file to open
 */
public class InvalidOpenFileException extends Exception {

    public InvalidOpenFileException() {
        super("the file is not valid!");
    }

}
