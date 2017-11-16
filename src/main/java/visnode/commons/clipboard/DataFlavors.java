package visnode.commons.clipboard;

import java.awt.datatransfer.DataFlavor;

/**
 * Data flavors for the clipboard
 */
public class DataFlavors {

    /**
     * String data flavor
     * 
     * @return DataFlavor
     */
    public static DataFlavor string() {
        return DataFlavor.stringFlavor;
    }
    
    /**
     * Java Object data flavor
     * 
     * @return DataFlavor
     */
    public static DataFlavor object() {
        return new DataFlavor(Object.class, "object/x-java-object");
    }
    
}
