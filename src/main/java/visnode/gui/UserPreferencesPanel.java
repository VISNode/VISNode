package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.application.Messages;
import visnode.application.VISNode;
import visnode.application.VISNodeConstants;
import visnode.commons.swing.WindowFactory;

/**
 * User preferences
 */
public class UserPreferencesPanel extends JPanel implements VISNodeConstants {

    /** Langue field */
    private JComboBox<String> language;
    /** Button - Ok */
    private JButton buttonOk;
    /** Button - Apply */
    private JButton buttonApply;

    /**
     * Creates the user preferences panel
     */
    public UserPreferencesPanel() {
        super();
        initGui();
        initEvents();
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
     * Initializes the events
     */
    private void initEvents() {
        buttonOk.addActionListener((ev) -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });
        buttonApply.addActionListener((ev) -> {
            String[] ln = ((String) language.getSelectedItem()).split("_");
            Locale locale = new Locale(ln[0], ln[1]);
            VISNode.get().getModel().getUserPreferences().setLocale(locale);
        });
    }

    /**
     * Builds the button panel
     *
     * @return JComponent
     */
    private JComponent buildButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.add(buildButtonOk());
        panel.add(buildButtonApply());
        return panel;
    }

    /**
     * Builds the ok button
     *
     * @return JComponent
     */
    private JComponent buildButtonOk() {
        buttonOk = new JButton("Ok");
        return buttonOk;
    }

    /**
     * Builds the apply button
     *
     * @return JComponent
     */
    private JComponent buildButtonApply() {
        buttonApply = new JButton();
        Messages.get().message("apply").subscribe((msg) -> {
            buttonApply.setText(msg);
        });
        return buttonApply;
    }

    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(buildLanguageLabel());
        panel.add(buildLanguage());
        return panel;
    }

    /**
     * Builds the language label
     *
     * @return JComponent
     */
    private JComponent buildLanguageLabel() {
        JLabel label = new JLabel();
        Messages.get().message("language").subscribe((msg) -> {
            label.setText(msg);
        });
        return label;
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

}
