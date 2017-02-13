
package nicolas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import visnode.gui.IconFactory;

/**
 *
 * @author Nícolas Pohren
 */
public class Icons {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(0, 5, 3, 3));
        for (Map.Entry<String, String> entry : IconFactory.get().getMapping().entrySet()) {
            JButton but = new JButton(entry.getKey(), IconFactory.get().create(entry.getKey()));
            but.setBackground(Color.BLACK);
            but.setForeground(Color.WHITE);
            but.setFocusable(false);
            panel.add(but);
        }
        frame.add(new JScrollPane(panel));
        
        frame.setVisible(true);
        
    }
    
}
