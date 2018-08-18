package visnode.challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import visnode.commons.swing.WindowFactory;
import visnode.gui.IconFactory;

/**
 * Challenge message success
 */
public class MissionSuccessMessagePanel extends JPanel {

    /** Thumbnail max size */
    private static final int MAX_SIZE = 300;
    /** Window width */
    private static final int PAGE_WIDTH = 400;
    /** Mission */
    private final Mission mission;

    /**
     * Creates a challenge
     */
    private MissionSuccessMessagePanel(Mission mission) {
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
            container.add(new MissionSuccessMessagePanel(mission));
        }).setVisible(true);
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PAGE_WIDTH, 545));
        add(buildPreferences(), BorderLayout.NORTH);
        if (mission.getChallenge().isPaymentAvailable()) {
            add(new Panel(mission.getChallenge().getPuzzle(), mission.getChallenge().getPaymentBuffered()));
        }
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
        JButton button = new JButton("Voltar para o desafio", IconFactory.get().create("fa:forward"));
        button.addActionListener((evt) -> {
            SwingUtilities.getWindowAncestor(MissionSuccessMessagePanel.this).dispose();
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
        JLabel icon = new JLabel(IconFactory.get().create("fa:smile-o", 110), SwingConstants.CENTER);
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JPanel text = new JPanel();
        text.setLayout(new GridLayout(4, 1));
        text.add(new JLabel("Parabens! Missão realizada com sucesso!", SwingConstants.CENTER));
        text.add(new JLabel("Abaixo está sua recompensa por finalizar esta missão!", SwingConstants.CENTER));
        text.add(new JLabel("Continue completando missões para receber", SwingConstants.CENTER));
        text.add(new JLabel("a recompensa completa!", SwingConstants.CENTER));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(icon, BorderLayout.NORTH);
        panel.add(text);
        return panel;
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
            int posY = 30;
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
                        int center = (PAGE_WIDTH / 2) - (width / 2);
                        float scale = 1f;
                        int size = Math.max(width, height);
                        if (size > MAX_SIZE) {
                            scale = (float) MAX_SIZE / size;
                        }
                        Polygon p = ChallengePuzzleFactory.createPolygon(piece, width, height, center, 30);
                        g2d.scale(scale, scale);
                        g2d.setClip(p);
                        g2d.drawImage(buff, -posX + center, -posY + 30, this);
                        return;
                    }
                    posX += width;
                }
                posY += height;
            }

        }

    }

}
