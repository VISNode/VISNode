package nicolas;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import visnode.gui.JNode;
import visnode.gui.JNodeConnector;
import visnode.gui.JNodeContainer;

public class TestNodeContainer extends JFrame {

    public TestNodeContainer() {
        super();
        setSize(1604, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JNodeContainer container = new JNodeContainer();
        buildContainer(container);
        container.setBorder(BorderFactory.createEtchedBorder());
        
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(container);
        
    }
    
    public static void main(String[] args) {
        new TestNodeContainer().setVisible(true);
    }

    
    private void buildContainer(JNodeContainer container) {
        
        JNode image1 = new JNode();
        image1.setBounds(50, 50, 150, 200);
        JNodeConnector image1c1 = new JNodeConnector(JNodeConnector.Configuration.RIGHT);
        image1.add(image1c1);
        container.add(image1);
        
        JNode mask = new JNode();
        mask.setBounds(50, 350, 150, 200);
        JNodeConnector maskc1 = new JNodeConnector(JNodeConnector.Configuration.RIGHT);
        mask.add(maskc1);
        container.add(mask);

        
        JNode blur = new JNode();
        blur.setBounds(350, 50, 150, 200);
        JNodeConnector blurc1 = new JNodeConnector(JNodeConnector.Configuration.LEFT);
        blur.add(blurc1);
        blur.add(new JNodeConnector(JNodeConnector.Configuration.LEFT));
        blur.add(new JNodeConnector(JNodeConnector.Configuration.RIGHT));
        container.add(blur);
        
        
        JNode cut = new JNode();
        cut.setBounds(350, 350, 150, 200);
        JNodeConnector cutc1 = new JNodeConnector(JNodeConnector.Configuration.LEFT);
        cut.add(cutc1);
        JNodeConnector cutc2 = new JNodeConnector(JNodeConnector.Configuration.LEFT);
        cut.add(cutc2);
        cut.add(new JNodeConnector(JNodeConnector.Configuration.RIGHT));
        container.add(cut);
        
        image1c1.connectTo(blurc1);
        
        image1c1.connectTo(cutc1);
        maskc1.connectTo(cutc2);
        
    }
    
}
