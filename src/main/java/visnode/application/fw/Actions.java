package visnode.application.fw;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import visnode.application.ActionAboutVisnode;
import visnode.application.ActionDeleteSelectedNodes;
import visnode.application.ActionDirectOpen;
import visnode.application.ActionDirectOpenImage;
import visnode.application.ActionNew;
import visnode.application.ActionOpen;
import visnode.application.ActionSave;
import visnode.application.ActionSaveAs;
import visnode.application.ActionSelectImage;
import visnode.application.ActionSelectWebCam;
import visnode.application.Messages;
import visnode.application.VISNode;

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
        JMenu file = new JMenu(Messages.get().message("file"));
        file.add(get(ActionNew.class));
        file.addSeparator();
        file.add(get(ActionOpen.class));
        file.add(buildReopenProjectMenu());
        file.addSeparator();
        file.add(get(ActionSave.class));
        file.add(get(ActionSaveAs.class));
        JMenu input = new JMenu(Messages.get().message("input"));
        input.add(get(ActionSelectImage.class));
        input.add(buildReopenInputMenu());
        file.addSeparator();
        input.add(get(ActionSelectWebCam.class));
        JMenu edit = new JMenu(Messages.get().message("edit"));
        edit.add(get(ActionDeleteSelectedNodes.class));
        JMenu help = new JMenu(Messages.get().message("help"));
        help.add(get(ActionAboutVisnode.class));
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(input);
        menuBar.add(help);
        return menuBar;
    }

    /**
     * Builds the reopen project menu
     * 
     * @return JMenu
     */
    private JMenu buildReopenProjectMenu() {
        JMenu menu = new JMenu(Messages.get().message("openRecent"));
        VISNode.get().getController().addRecentProjectListener((projects) -> {
            menu.removeAll();
            for (File project : projects) {
                menu.add(new ActionDirectOpen(project));
            }
        });
        return menu;
    }

    /**
     * Builds the reopen input menu
     * 
     * @return JMenu
     */
    private JMenu buildReopenInputMenu() {
        JMenu menu = new JMenu(Messages.get().message("openRecentInput"));
        VISNode.get().getController().addRecentInputListener((inputs) -> {
            menu.removeAll();
            for (File file : inputs) {
                menu.add(new ActionDirectOpenImage(file));
            }
        });
        return menu;
    }

    /**
     * Loads the actions
     */
    private void loadActions() {
        actions.put(ActionNew.class, new ActionNew());
        actions.put(ActionOpen.class, new ActionOpen());
        actions.put(ActionSave.class, new ActionSave());
        actions.put(ActionSaveAs.class, new ActionSaveAs());
        actions.put(ActionSelectImage.class, new ActionSelectImage());
        actions.put(ActionSelectWebCam.class, new ActionSelectWebCam());
        actions.put(ActionDeleteSelectedNodes.class, new ActionDeleteSelectedNodes());
        actions.put(ActionAboutVisnode.class, new ActionAboutVisnode());
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
