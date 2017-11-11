package visnode.commons.clipboard;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Transferable for Java Objects
 */
public class ObjectTransferable implements Transferable {

    /** Transferable value */
    private final Object value;

    /** 
     * Creates a new Object Transferable
     * 
     * @param value
     */
    public ObjectTransferable(Object value) {
        this.value = value;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { DataFlavors.object() };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DataFlavors.object()) || flavor.equals(DataFlavors.string());
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(DataFlavors.object())) {
            return value;
        } else if (flavor.equals(DataFlavors.string())) {
            return value.toString();
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
