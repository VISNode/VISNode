package visnode.challenge;

import java.io.File;
import java.io.IOException;
import org.paim.commons.Image;
import visnode.application.ExceptionHandler;
import visnode.application.InputReader;

/**
 * Executes the challenge comparation
 */
public class ChallengeComparator {

    /**
     * Execute the challenge comparation
     *
     * @param challange
     * @param output
     * @return boolean
     */
    public boolean comparate(Challenge challange, Image output) {
        try {
            Image base = new InputReader().read(new File(challange.getOutput()));
            int [][][] expected = base.getData();
            int [][][] result = output.getData();
            for (int channel = 0; channel < expected.length; channel++) {
                for (int x = 0; x < expected[channel].length; x++) {
                    for (int y = 0; y < expected[channel][x].length; y++) {
                        if (expected[channel][x][y] != result[channel][x][y]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return false;
    }
}
