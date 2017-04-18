package nicolas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.*;
import visnode.application.ExceptionHandler;
import visnode.gui.JNode;
import visnode.gui.JNodeConnector;
import visnode.gui.JNodeContainer;

public class TestNodeContainer extends JFrame {

    // http://alvinalexander.com/java/java-uimanager-color-keys-list
    
    private static SubstanceSkin skin = new GraphiteSkin();
    
    public TestNodeContainer() {
        super();
        setSize(1604, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JNodeContainer container = new JNodeContainer();
        buildContainer(container);
        container.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildThemeControl(), BorderLayout.NORTH);
        getContentPane().add(container);
        getContentPane().add(buildOtherControls(), BorderLayout.SOUTH);
        
    }
    
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new SubstanceLookAndFeel(skin) {});
        SwingUtilities.invokeLater(() -> {
            new TestNodeContainer().setVisible(true);
        });
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
        
        image1c1.getRightConnector().connectTo(blurc1.getLeftConnector());
        
        image1c1.getRightConnector().connectTo(cutc1.getLeftConnector());
        maskc1.getRightConnector().connectTo(cutc2.getLeftConnector());
        
    }

    private Component buildOtherControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.add(new JLabel("Label"));
        panel.add(new JTextField(20));
        panel.add(new JCheckBox());
        panel.add(new JRadioButton());
        panel.add(new JButton("Click me!"));
        return panel;
    }

    private Component buildThemeControl() {
        JPanel panel = new JPanel(new GridLayout(0, 14));
        panel.add(buildSkinButton(new AutumnSkin()));
        panel.add(buildSkinButton(new BusinessBlackSteelSkin()));
        panel.add(buildSkinButton(new BusinessBlueSteelSkin()));
        panel.add(buildSkinButton(new BusinessSkin()));
        panel.add(buildSkinButton(new CeruleanSkin()));
        panel.add(buildSkinButton(new ChallengerDeepSkin()));
        panel.add(buildSkinButton(new CremeCoffeeSkin()));
        panel.add(buildSkinButton(new CremeSkin()));
        panel.add(buildSkinButton(new DustCoffeeSkin()));
        panel.add(buildSkinButton(new DustSkin()));
        panel.add(buildSkinButton(new EmeraldDuskSkin()));
        panel.add(buildSkinButton(new GeminiSkin()));
        panel.add(buildSkinButton(new GraphiteAquaSkin()));
        panel.add(buildSkinButton(new GraphiteGlassSkin()));
        panel.add(buildSkinButton(new GraphiteSkin()));
        panel.add(buildSkinButton(new MagellanSkin()));
        panel.add(buildSkinButton(new MarinerSkin()));
        panel.add(buildSkinButton(new MistAquaSkin()));
        panel.add(buildSkinButton(new MistSilverSkin()));
        panel.add(buildSkinButton(new ModerateSkin()));
        panel.add(buildSkinButton(new NebulaBrickWallSkin()));
        panel.add(buildSkinButton(new NebulaSkin()));
        panel.add(buildSkinButton(new OfficeBlack2007Skin()));
        panel.add(buildSkinButton(new OfficeBlue2007Skin()));
        panel.add(buildSkinButton(new OfficeSilver2007Skin()));
        panel.add(buildSkinButton(new RavenSkin()));
        panel.add(buildSkinButton(new SaharaSkin()));
        panel.add(buildSkinButton(new TwilightSkin()));
        return panel;
    }
    
    private JComponent buildSkinButton(SubstanceSkin skin) {
        return new JButton(new AbstractAction(skin.getClass().getSimpleName().replace("Skin", "")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestNodeContainer.skin = skin;
                dispose();
                try {
                    main(new String[] {});
                
                } catch(Exception ex) {
                    ExceptionHandler.get().handle(ex);
                }
            }
        });
    }
    
}
