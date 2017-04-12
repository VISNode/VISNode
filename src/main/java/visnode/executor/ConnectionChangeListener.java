
package visnode.executor;

import java.util.EventListener;

/**
 * Node connection listener
 */
public interface ConnectionChangeListener extends EventListener {

    /**
     * A connection change
     * 
     * @param evt 
     */
    public void connectionChanged(ConnectionChangeEvent evt);
    
}
