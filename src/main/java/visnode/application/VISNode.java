package visnode.application;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import visnode.application.fw.Actions;
import visnode.commons.swing.WindowFactory;

/**
 * Main class application
 */
public class VISNode {

    /** Application instance */
    private static VISNode instance;
    /** Application model */
    private final VISNodeModel model;
    /** Application controller */
    private VISNodeController controller;
    /** Actions */
    private Actions actions;
    /** Main window */
    private MainPanel panel;
    /** Frame */
    private JFrame frame;
    
    /**
     * Creates a new application
     */
    public VISNode() {
        this.model = new VISNodeModel();
    }

    /**
     * Returns the application instance
     *
     * @return VISNode
     */
    public static VISNode get() {
        if (instance == null) {
            instance = new VISNode();
        }
        return instance;
    }

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        VISNode.get().start(args);
    }

    /**
     * Starts the application
     *
     * @param args
     */
    public void start(String[] args) {
        model.setUserPreferences(new UserPreferencesPersistor().load());
        setupLookAndFeel();
        buildAndShowWindow();
        parseArgs(args);
    }

    /**
     * Parse the args
     * 
     * @param args 
     */
    private void parseArgs(String[] args) {
        if (args.length > 0) {
            getController().open(new File(args[0]));
        }
    }

    /**
     * Sets up the LookAndFeel
     */
    private void setupLookAndFeel() {
        model.getUserPreferences().getThemeSubject().subscribe((skin) -> {
            try {
                UIManager.setLookAndFeel(new SubstanceLookAndFeel(
                        (SubstanceSkin) skin.getTheme().newInstance()
                ) {
                });
                if (frame != null) {
                    frame.repaint();
                }
            } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
                ExceptionHandler.get().handle(e);
            }
        });
    }

    /**
     * Builds and shows the main window
     */
    private void buildAndShowWindow() {
        SwingUtilities.invokeLater(() -> {
            buildWindow().setVisible(true);
        });
    }

    /**
     * Builds the main window
     */
    private JFrame buildWindow() {
        frame = WindowFactory.mainFrame()
                .title("VISNode")
                .menu(VISNode.get().getActions().buildMenuBar())
                .size(1024, 768)
                .maximized()
                .interceptClose(() -> {
                    new UserPreferencesPersistor().persist(model.getUserPreferences());
                    int result = JOptionPane.showConfirmDialog(panel, Messages.get().singleMessage("app.closing"), null, JOptionPane.YES_NO_OPTION);
                    return result == JOptionPane.YES_OPTION;
                })
                .create((container) -> {
                    panel = new MainPanel(model);
                    container.add(panel);
                });
        return frame;
    }

    /**
     * Returns the model
     *
     * @return VISNodeModel
     */
    public VISNodeModel getModel() {
        return model;
    }

    /**
     * Returns the controller
     *
     * @return VISNodeController
     */
    public VISNodeController getController() {
        if (controller == null) {
            controller = new VISNodeController(this.model);
        }
        return controller;
    }

    /**
     * Returns the current network editor
     *
     * @return NetworkEditor
     */
    public NetworkEditor getNetworkEditor() {
        return panel.getNetworkEditor();
    }

    /**
     * Returns the main window
     *
     * @return MainPanel
     */
    public MainPanel getMainPanel() {
        return panel;
    }

    /**
     * Returns the actions for the application
     *
     * @return Actions
     */
    public Actions getActions() {
        if (actions == null) {
            actions = new Actions();
        }
        return actions;
    }

}
