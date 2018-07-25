package visnode.challenge;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.paim.commons.Image;
import visnode.application.ExceptionHandler;
import visnode.application.InputReader;
import visnode.application.NodeNetwork;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.DynamicValue;
import visnode.commons.MultiFileInput;
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
     * @param challenge
     * @param challengeUser
     * @return boolean
     */
    public CompletableFuture<Boolean> comparate(Challenge challenge, ChallengeUser challengeUser) {
        CompletableFuture<Boolean> future = new CompletableFuture();
        File[] files = challenge.getInput().stream().map((file) -> {
            return new File(file.getValue());
        }).toArray(File[]::new);
        List<CompletableFuture<Boolean>> stream = challenge.
                getOutput().
                stream().
                map((value) -> comparate(
                        files[challenge.getOutput().indexOf(value)], 
                        value, 
                        challengeUser
                )).
                collect(Collectors.toList());
        CompletableFuture.allOf(
                stream.stream().toArray(CompletableFuture[]::new)
        ).thenAccept((accepted) -> {
            future.complete(!stream.stream().
                    filter((ft) -> !ft.join()).
                    findFirst().
                    isPresent());
        });
        return future;
    }

    /**
     * Execute the challenge comparation
     *
     * @param challengeValue
     * @param challengeUser
     * @return boolean
     */
    private CompletableFuture<Boolean> comparate(File input, ChallengeValue challengeValue, ChallengeUser challengeUser) {
        CompletableFuture<Boolean> future = new CompletableFuture();
        NodeNetwork nodeNetwork = parser.fromJson(challengeUser.getSubmission());
        nodeNetwork.getInputNode().getOutput("image").subscribe((it) -> {
            nodeNetwork.setInput(new MultiFileInput(input));
            OutputNode outputNode = nodeNetwork.getOutputNode();
            outputNode.execute().thenAccept((output) -> {
                if (output == null) {
                    future.complete(false);
                    return;
                }
                if (challengeValue.isTypeImage()) {
                    future.complete(comparateImage(challengeValue, output));
                    return;
                }
                future.complete(comprateObject(challengeValue, output));

            });
        }).dispose();
        return future;
    }

    /**
     * Execute the challenge comparation to images
     *
     * @param challengeValue
     * @param output
     * @return boolean
     */
    public boolean comparateImage(ChallengeValue challengeValue, DynamicValue<Object> output) {
        if (!output.isImage()) {
            return false;
        }
        try {
            Image base = new InputReader().read(new File(challengeValue.getValue()));
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
     * @param challangeValue
     * @param output
     * @return boolean
     */
    private boolean comprateObject(ChallengeValue challangeValue, DynamicValue output) {
        return challangeValue.getValue().equals(String.valueOf(output.get()));
    }

}
