package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.application.VISNodeConstants;
import com.github.rxsling.Buttons;
import com.github.rxsling.Labels;
import com.github.rxsling.Panel;
import visnode.commons.swing.WindowFactory;

/**
 * User preferences
 */
public class UserPreferencesPanel extends Panel implements VISNodeConstants {

    /** Langue field */
    private JComboBox<String> language;
    /** Theme */
    private JComboBox<Theme> theme;

    /**
     * Creates the user preferences panel
     */
    public UserPreferencesPanel() {
        super();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        WindowFactory.modal().
                title(Messages.get().message("preferences")).
                create((container) -> {
                    container.add(new UserPreferencesPanel());
                }).
                setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 150));
        add(buildPreferences(), BorderLayout.NORTH);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Builds the button panel
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.add(Buttons.create("Ok").onClick((ev) -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        }));
        panel.add(Buttons.create().text(Messages.get().message("apply")).onClick((ev) -> {
            String[] ln = ((String) language.getSelectedItem()).split("_");
            Locale locale = new Locale(ln[0], ln[1]);
            VISNode.get().getModel().getUserPreferences().setLocale(locale);
            VISNode.get().getModel().getUserPreferences().setTheme((Theme) theme.getSelectedItem());
            SwingUtilities.getWindowAncestor(this).dispose();
        }));
        return panel;
    }

    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(Labels.create().text(Messages.get().message("language")));
        panel.add(buildLanguage());
        panel.add(Labels.create().text("Theme"));
        panel.add(buildSkin());
        return panel;
    }

    /**
     * Builds the language field
     *
     * @return JComponent
     */
    private JComponent buildLanguage() {
        language = new JComboBox<>();
        language.addItem("pt_BR");
        language.addItem("en_US");
        VISNode.get().getModel().getUserPreferences().getLocaleSubject().subscribe((locale) -> {
            language.getModel().setSelectedItem(locale.toString());
        });
        return language;
    }

    /**
     * Builds the skin field
     *
     * @return JComponent
     */
    private JComponent buildSkin() {
        theme = new JComboBox<>();
        for (Theme s : Theme.values()) {
            theme.addItem(s);
        }
        theme.getModel().setSelectedItem(VISNode.get().getModel().getUserPreferences().getTheme());
        return theme;
    }

}
