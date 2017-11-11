package visnode.commons.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Utility class for manipulating the Clipboard
 */
public class Clipboard implements ClipboardOwner {
    
    /** Instance */
    private static Clipboard instance;
    /** Systems clipboard */
    private final java.awt.datatransfer.Clipboard clipboard;

    /**
     * 
     */
    private Clipboard() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    /**
     * Returns the instance
     * 
     * @return Clipboard
     */
    public static Clipboard get() {
        if (instance == null) {
            instance = new Clipboard();
        }
        return instance;
    }
    
    /**
     * Adds an object to the clipboard
     * 
     * @param object 
     */
    public void put(Object object) {
        clipboard.setContents(TransferableFactory.create(object), this);
    }
    
    /**
     * Returns the value of the clipboard
     * 
     * @param <T>
     * @param type
     * @return T
     */
    public <T> T get(Class<T> type) {
        return (T) getValue();
    }
    
    /**
     * Returns the value of the clipboard
     * 
     * @return Object
     */
    private Object getValue() {
        try {
            return clipboard.getContents(this).getTransferData(DataFlavors.object());
        } catch (UnsupportedFlavorException | IOException e) {
            
        }
        return null;
    }
    
    /**
     * Checks if the value of the clipboard is convertable to the specified type
     * 
     * @param type
     * @return boolean
     */
    public boolean isSupported(Class type) {
        Object value = getValue();
        return type.isAssignableFrom(value.getClass());
    }

    @Override
    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable contents) {
    }
    
}
