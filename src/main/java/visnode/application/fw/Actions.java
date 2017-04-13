package visnode.application.fw;

import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import visnode.application.ActionNew;
import visnode.application.ActionOpen;
import visnode.application.ActionSave;
import visnode.application.ActionSelectImage;

/**
 * Application actions
 */
public class Actions {

    /** Actions */
    private final Map<Class, Action> actions;

    /**
     * Creates the actions
     */
    public Actions() {
        actions = new HashMap<>();
        loadActions();
    }
    
    /**
     * Builds the menu bar
     * 
     * @return JMenuBar
     */
    public JMenuBar buildMenuBar() {
        JMenu file = new JMenu("File");
        file.add(get(ActionNew.class));
        file.add(get(ActionOpen.class));
        file.add(get(ActionSave.class));
        JMenu input = new JMenu("Input");
        input.add(get(ActionSelectImage.class));
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(input);
        return menuBar;
    }

    /**
     * Loads the actions
     */
    private void loadActions() {
        actions.put(ActionNew.class, new ActionNew());
        actions.put(ActionOpen.class, new ActionOpen());
        actions.put(ActionSave.class, new ActionSave());
        actions.put(ActionSelectImage.class, new ActionSelectImage());
    }
    
    /**
     * Returns an existing action
     * 
     * @param type
     * @return Action
     */
    public Action get(Class type) {
        return actions.get(type);
    }
    
}
