package visnode.application.fw;

import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import visnode.application.ActionDeleteSelectedNodes;
import visnode.application.ActionNew;
import visnode.application.ActionOpen;
import visnode.application.ActionSave;
import visnode.application.ActionSelectImage;
import visnode.application.ActionSelectWebCam;

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
        file.addSeparator();
        file.add(get(ActionOpen.class));
        file.addSeparator();
        file.add(get(ActionSave.class));
        JMenu input = new JMenu("Input");
        input.add(get(ActionSelectImage.class));
        input.add(get(ActionSelectWebCam.class));
        JMenu edit = new JMenu("Edit");
        edit.add(get(ActionDeleteSelectedNodes.class));
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
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
        actions.put(ActionSelectWebCam.class, new ActionSelectWebCam());
        actions.put(ActionDeleteSelectedNodes.class, new ActionDeleteSelectedNodes());
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
