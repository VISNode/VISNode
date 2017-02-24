package jonata;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import visnode.commons.Image;
import visnode.commons.ImageFactory;
import visnode.executor.InputNode;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.GrayscaleProcess;
import visnode.pdi.process.InformationProcess;
import visnode.pdi.process.ThresholdProcess;

/**
 *
 */
public class TesteConection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new TesteConection().exec();
    }

    public Image process(Image image) {

        InputNode input = new InputNode(image);
        
        ProcessNode grayScale = new ProcessNode(GrayscaleProcess.class);
        grayScale.addConnection("image", input, "image");

        ProcessNode information = new ProcessNode(InformationProcess.class);
        information.addConnection("image", grayScale, "image");
        
        ProcessNode threshold = new ProcessNode(ThresholdProcess.class);
        threshold.addConnection("threshold", information, "average");
        threshold.addConnection("image", grayScale, "image");

        OutputNode out = new OutputNode();
        out.addConnection("image", threshold, "image");
        return (Image) out.getAttribute("image");
    }

    public BufferedImage getBuffered(Image image) {
        BufferedImage buff = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r, g, b;
                if (image.getChannelCount() == Image.CHANNELS_RGB) {
                    r = image.get(Image.CHANNEL_RED, x, y);
                    g = image.get(Image.CHANNEL_GREEN, x, y);
                    b = image.get(Image.CHANNEL_BLUE, x, y);
                } else {
                    r = image.get(0, x, y);
                    g = image.get(0, x, y);
                    b = image.get(0, x, y);
                }
                buff.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return buff;
    }

    public void exec() throws IOException {
        BufferedImage r = ImageIO.read(new File(getClass().getResource("/lena.jpg").getFile()));
        Image image = ImageFactory.buildRGBImage(r);

        JFrame frame = new JFrame();
        frame.setSize(1024, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JPanel pLeft = new JPanel();
        ImageIcon ico = new ImageIcon();
        ico.setImage(getBuffered(image));
        JLabel l = new JLabel();
        l.setIcon(ico);
        pLeft.add(l);
        JPanel pRight = new JPanel();
        ImageIcon ico2 = new ImageIcon(getBuffered(process(image)));
        JLabel l2 = new JLabel();
        l2.setIcon(ico2);
        pRight.add(l2);
        panel.add(pLeft);
        panel.add(pRight);
        frame.add(panel);
        frame.setVisible(true);
    }

}
