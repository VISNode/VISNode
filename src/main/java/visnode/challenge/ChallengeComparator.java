package visnode.challenge;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.paim.commons.Image;
import visnode.application.ExceptionHandler;
import visnode.application.InputReader;
import visnode.application.NodeNetwork;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.DynamicValue;
import visnode.executor.OutputNode;

/**
 * Executes the challenge comparation
 */
public class ChallengeComparator {

    /** Node network parser */
    private final NodeNetworkParser parser;

    public ChallengeComparator() {
        this.parser = new NodeNetworkParser();
    }

    /**
     * Execute the challenge comparation
     *
     * @param challange
     * @return boolean
     */
    public CompletableFuture<Boolean> comparate(Challenge challange, ChallengeUser challengeUser) {
        CompletableFuture<Boolean> future = new CompletableFuture();
        NodeNetwork nodeNetwork = parser.fromJson(challengeUser.getSubmission());
        OutputNode outputNode = nodeNetwork.getOutputNode();
        outputNode.execute().thenAccept((output) -> {
            if (output == null) {
                future.complete(false);
                return;
            }
            if (challange.getOutput().isTypeImage()) {
                future.complete(comparateImage(challange, output));
                return;
            }
            future.complete(comprateObject(challange, output));

        });
        return future;
    }

    /**
     * Execute the challenge comparation to images
     *
     * @param challange
     * @param output
     * @return boolean
     */
    public boolean comparateImage(Challenge challange, DynamicValue<Object> output) {
        if (!output.isImage()) {
            return false;
        }
        try {
            Image base = new InputReader().read(new File(challange.getOutput().getValue()));
            int[][][] expected = base.getData();
            int[][][] result = output.get(Image.class).getData();
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

    /**
     * Execute the challenge comparation to object
     *
     * @param challange
     * @param output
     * @return boolean
     */
    private boolean comprateObject(Challenge challange, DynamicValue output) {
        return challange.getOutput().getValue().equals(String.valueOf(output.get()));
    }

}
