package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Challenge Puzzle Pane
 */
public class ChallengePuzzlePane extends JPanel {

    /** Challenge */
    private final Challenge challenge;
    /** Missions completed */
    private final int missionCompleted;

    /**
     * Creates a challenge
     */
    private ChallengePuzzlePane(Challenge challenge, int missionCompleted) {
        this.challenge = challenge;
        this.missionCompleted = missionCompleted;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     *
     * @param challenge
     * @param missionCompleted
     */
    public static void showDialog(Challenge challenge, int missionCompleted) {
        WindowFactory.modal().title("Recompensas").create((container) -> {
            container.add(new ChallengePuzzlePane(challenge, missionCompleted));
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildPreferences());
        add(buildButtons(), BorderLayout.SOUTH);
    }

    /**
     * Initializes the events
     */
    private void initEvents() {

    }
    
    /**
     * Build the buttons
     * 
     * @return JComponent
     */
    private JComponent buildButtons() {
        JButton button = new JButton("Ir para o desafio", IconFactory.get().create("fa:forward"));
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(ChallengePuzzlePane.this).dispose();
        });
        JComponent panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
    
    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        if (!challenge.isPaymentAvailable()) {
            return new JPanel();
        }
        BufferedImage image = challenge.getPaymentBuffered();
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight() + 45));
        return new Panel(challenge.getPuzzle(), image);
    }

    /**
     * Puzzle panel
     */
    public class Panel extends JPanel {
        
        /** Puzzle */
        private final ChallengePuzzle puzzle;
        /** Image */
        private final BufferedImage buff;

        public Panel(ChallengePuzzle puzzle, BufferedImage buff) {
            this.puzzle = puzzle;
            this.buff = buff;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(buff, 0, 0, this);
            int posX;
            int posY = 0;
            for (int y = 0; y < puzzle.getPieces()[0].length; y++) {
                posX = 0;
                int height = 0;
                for (int x = 0; x < puzzle.getPieces().length; x++) {
                    ChallengePuzzlePiece piece = puzzle.getPieces()[x][y];
                    if (piece == null) {
                        continue;
                    }
                    int width = (int) Math.floor(piece.getWidth() * buff.getWidth());
                    height = (int) Math.floor(piece.getHeight() * buff.getHeight());
                    Polygon p = ChallengePuzzleFactory.createPolygon(piece, width, height, posX, posY);
                    if (piece.getLabel() > missionCompleted) {
                        g2d.setColor(Color.WHITE);
                        g2d.fillPolygon(p);
                    }
                    g2d.setColor(Color.GRAY);
                    g2d.drawPolygon(p);
                    posX += width;
                }
                posY += height;
            }

        }

    }

}