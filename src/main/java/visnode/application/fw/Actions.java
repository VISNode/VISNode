package visnode.application.fw;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import visnode.application.ActionAboutVisnode;
import visnode.application.ActionCopyNode;
import visnode.application.ActionDeleteSelectedNodes;
import visnode.application.ActionDirectOpen;
import visnode.application.ActionExport;
import visnode.application.ActionNew;
import visnode.application.ActionOpen;
import visnode.application.ActionPasteNode;
import visnode.application.ActionSave;
import visnode.application.ActionSaveAs;
import visnode.application.ActionSelectWindow;
import visnode.application.ActionUserPreferences;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.challenge.ChallengeMenu;

/**
 * Application actions
 */
public class Actions {

    /** Actions */
    private final Map<Class, Action> actions;
    /** Challenge menu */
    private final ChallengeMenu challengeMenu;
    
    /**
     * Creates the actions
     */
    public Actions() {
        actions = new HashMap<>();
        challengeMenu = new ChallengeMenu();
        loadActions();
    }
    
    /**
     * Builds the menu bar
     * 
     * @return JMenuBar
     */
    public JMenuBar buildMenuBar() {
        JMenu file = new JMenu();
        Messages.get().message("file").subscribe((msg) -> {
            file.setText(msg);
        });
        file.add(get(ActionNew.class));
        file.addSeparator();
        file.add(get(ActionOpen.class));
        file.add(buildReopenProjectMenu());
        file.addSeparator();
        file.add(get(ActionSave.class));
        file.add(get(ActionSaveAs.class));
        file.add(get(ActionExport.class));
        JMenu edit = new JMenu();
        Messages.get().message("edit").subscribe((msg) -> {
            edit.setText(msg);
        });
        edit.add(get(ActionCopyNode.class));
        edit.add(get(ActionPasteNode.class));
        edit.addSeparator();
        edit.add(get(ActionDeleteSelectedNodes.class));
        edit.addSeparator();
        edit.add(get(ActionUserPreferences.class));
        JMenu view = new JMenu();
        Messages.get().message("view").subscribe((msg) -> {
            view.setText(msg);
        });
        JMenu rendering = new JMenu();
        Messages.get().message("rendering").subscribe((msg) -> {
            rendering.setText(msg);
        });
        rendering.add(get(ActionSelectWindow.class));
        view.add(rendering);
        JMenu challenge = new JMenu();
        Messages.get().message("challenge").subscribe((msg) -> {
            challenge.setText(msg);
        });
        challengeMenu.getMenu().forEach((item) -> {
            challenge.add(item);
        });
        JMenu help = new JMenu();
        Messages.get().message("help").subscribe((msg) -> {
            help.setText(msg);
        });
        help.add(get(ActionAboutVisnode.class));
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(help);
        return menuBar;
    }

    /**
     * Builds the reopen project menu
     * 
     * @return JMenu
     */
    private JMenu buildReopenProjectMenu() {
        JMenu menu = new JMenu();
        Messages.get().message("openRecent").subscribe((msg) -> {
            menu.setText(msg);
        });
        VISNode.get().getController().addRecentProjectListener((projects) -> {
            menu.removeAll();
            for (File project : projects) {
                menu.add(new ActionDirectOpen(project));
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
        actions.put(ActionExport.class, new ActionExport());
        actions.put(ActionUserPreferences.class, new ActionUserPreferences());
        actions.put(ActionDeleteSelectedNodes.class, new ActionDeleteSelectedNodes());
        actions.put(ActionCopyNode.class, new ActionCopyNode());
        actions.put(ActionPasteNode.class, new ActionPasteNode());
        actions.put(ActionAboutVisnode.class, new ActionAboutVisnode());
        actions.put(ActionSelectWindow.class, new ActionSelectWindow());
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
