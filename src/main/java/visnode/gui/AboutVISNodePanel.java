package visnode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import visnode.application.ExceptionHandler;
import visnode.application.VISNodeConstants;
import com.github.rxsling.Labels;
import visnode.commons.swing.WindowFactory;

/**
 * About VISNode
 */
public class AboutVISNodePanel extends JPanel implements VISNodeConstants {

    /**
     * Creates about information panel
     */
    public AboutVISNodePanel() {
        super();
        initGui();
    }

    /**
     * Shows the dialog
     */
    public static void showDialog() {
        WindowFactory.modal().title("About VISNode").create((container) -> {
            container.add(new AboutVISNodePanel());
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        add(buildIcon(), BorderLayout.WEST);
        add(buildInfo());
    }

    /**
     * Builds the tool icon
     *
     * @return JComponent
     */
    private JComponent buildIcon() {
        JLabel icon = new JLabel();
        try {
            icon.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/VISNode_64.png"))));
            icon.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 15));
        } catch (IOException ex) {
            Logger.getLogger(AboutVISNodePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return icon;
    }

    /**
     * Builds the tool information
     *
     * @return JComponent
     */
    private JComponent buildInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("VISNode");
        title.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        JLabel version = new JLabel(VERSION);
        version.setBackground(Color.red);
        
//        github = new JButton();
//        github.setIcon(IconFactory.get().create("fa:github"));
//        github.setText(GITHUB_URL);
//        github.setBorder(BorderFactory.createEmptyBorder());
//        github.setBorderPainted(false);
//        github.setContentAreaFilled(false);
//        github.setFocusPainted(false);
//        github.setOpaque(false);
        info.add(title);
        info.add(version);
        info.add(Labels.create(GITHUB_URL).icon(IconFactory.get().create("fa:github")).onClick((ev) -> {
            try {
                Desktop.getDesktop().browse(new URL(GITHUB_URL).toURI());
            } catch (IOException | URISyntaxException e) {
                ExceptionHandler.get().handle(e);
            }
        }));
        return info;
    }

}
