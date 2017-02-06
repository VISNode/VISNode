package visnode.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 * Node container
 */
public class JNodeContainer extends JComponent {

    /**
     * Creates a new NodeContainer
     */
    public JNodeContainer() {
        super();
        initGui();
    }

    /**
     * Inititalizes the interface
     */
    private void initGui() {
        setLayout(null);
        setupDragSupport();
    }

    /**
     * Sets up the drag support
     */
    private void setupDragSupport() {
        DragSupport dragSupport = new DragSupport(this);
        dragSupport.setAllowDragPredicate((component) -> component instanceof JNode || component instanceof JConnectorPoint);
    }

    /** 
     * Inicia uma conexão
     * 
     * @param connectorPoint
     * @param e 
     */
    public void startConnection(JConnectorPoint connectorPoint, MouseEvent e) {
//        add(new JNodeConnection(connectorPoint, new MousePositionSupplier(connectorPoint, (ev) -> ev.getID() == MouseEvent.MOUSE_MOVED && e.getButton() == MouseEvent.BUTTON1)));
        add(new JNodeConnection(connectorPoint, new MousePositionSupplier(connectorPoint, (ev) -> true)));
        
        
        System.out.println("wow");
        
    }
    
}
