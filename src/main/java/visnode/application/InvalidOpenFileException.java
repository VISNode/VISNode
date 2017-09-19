package visnode.application;

/**
 * Invalid file to open
 */
public class InvalidOpenFileException extends RuntimeException {

    
    public InvalidOpenFileException(Exception cause) {
        super(cause);
    }
    
    public InvalidOpenFileException() {
        super("the file is not valid!");
    }

}
