package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import visnode.commons.swing.WindowFactory;

/**
 * Challenge error success
 */
public class ChallengeSuccessMessagePanel extends JPanel {

    /** Mission */
    private final Mission mission;

    /**
     * Creates a challenge
     */
    private ChallengeSuccessMessagePanel(Mission mission) {
        this.mission = mission;
        initGui();
        initEvents();
    }

    /**
     * Shows the dialog
     *
     * @param mission
     */
    public static void showDialog(Mission mission) {
        WindowFactory.modal().title("Mensagem").create((container) -> {
            container.add(new ChallengeSuccessMessagePanel(mission));
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        add(buildPreferences(), BorderLayout.NORTH);
        if (mission.getChallenge().isPaymentAvailable()) {
            add(new Panel(mission.getChallenge().getPuzzle(), mission.getChallenge().getPaymentBuffered()));
        }
    }

    /**
     * Initializes the events
     */
    private void initEvents() {

    }

    /**
     * Builds the preferences panel
     *
     * @return JComponent
     */
    private JComponent buildPreferences() {
        return new JLabel("The output is correct! :)");
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

                    if (piece.getLabel() == mission.getLevel()) {
                        Polygon p = ChallengePuzzleFactory.createPolygon(piece, width, height, 0, 0);
                        g2d.setClip(p);
                        g2d.drawImage(buff, -posX, -posY, this);
                        return;
                    }

                    posX += width;
                }
                posY += height;
            }

        }

    }

}
