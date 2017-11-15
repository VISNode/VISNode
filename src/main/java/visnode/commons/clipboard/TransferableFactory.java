package visnode.commons.clipboard;

import java.awt.datatransfer.Transferable;

/**
 * Factory for transferables
 */
public class TransferableFactory {
    
    public static Transferable create(Object object) {
        return new ObjectTransferable(object);
    }
    
}
