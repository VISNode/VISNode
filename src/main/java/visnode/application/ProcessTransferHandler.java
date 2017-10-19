package visnode.application;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.COPY;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.ProcessNode;

/**
 * Process transfer handler
 */
public class ProcessTransferHandler extends TransferHandler {

    /**
     * Creates a new Process transfer handler
     */
    public ProcessTransferHandler() {

    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        return info.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        try {
            if (!info.isDrop()) {
                return false;
            }
            if (!(info.getComponent() instanceof NetworkEditor)) {
                return false;
            }
            // Check for String flavor
            if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return false;
            }
            // Get the string that is being dropped.
            Transferable t = info.getTransferable();
            String data;
            try {
                data = (String) t.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                return false;
            }
            NetworkEditor editor = (NetworkEditor) info.getComponent();
            NodeNetwork network = editor.getModel();
            Node node = new ProcessNode(Class.forName(data));
            EditNodeDecorator dec = new EditNodeDecorator(node);
            dec.setPosition(info.getDropLocation().getDropPoint());
            network.add(dec);
            return true;
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
        return false;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    /**
     * Creates the transferable
     *
     * @param c
     * @return Transferable
     */
    @Override
    protected Transferable createTransferable(JComponent c) {
        JList list = (JList) c;
        Object[] values = list.getSelectedValues();
        String buff = "";
        for (int i = 0; i < values.length; i++) {
            Class val = (Class) values[i];
            buff = val.getName();
        }
        return new StringSelection(buff);
    }

}
