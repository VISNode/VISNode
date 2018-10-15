package visnode.challenge;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.paim.commons.BinaryImage;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import visnode.application.NodeNetwork;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.DynamicValue;
import visnode.commons.MultiFileInput;
import visnode.executor.OutputNode;

/**
 * Executes the challenge comparation
 */
public class ChallengeComparator {

    /** Max image error */
    private static final int MAX_ERROR = 1;
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
    public CompletableFuture<Boolean> comparate(Mission challenge, MissionUser challengeUser) {
        CompletableFuture<Boolean> future = new CompletableFuture();
        File[] files = challenge.getInputFiles().stream().map((file) -> {
            return file;
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
    private CompletableFuture<Boolean> comparate(File input, MissionValue challengeValue, MissionUser challengeUser) {
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
    public boolean comparateImage(MissionValue challengeValue, DynamicValue<Object> output) {
        if (!output.isImage()) {
            return false;
        }
        Image base = ImageFactory.buildRGBImage(challengeValue.getValueBufferedImage());
        Image outputImage = output.get(Image.class);
        int[][][] expected = base.getData();
        int[][][] result = outputImage.getData();
        int error = 0;
        int chennels = Math.min(expected.length, result.length);
        for (int channel = 0; channel < chennels; channel++) {
            for (int x = 0; x < expected[channel].length; x++) {
                for (int y = 0; y < expected[channel][x].length; y++) {
                    int resultValue = result[channel][x][y];
                    if ((output.is(BinaryImage.class) || outputImage.getPixelValueRange().isBinary()) && resultValue == 1) {
                        resultValue = 255;
                    }
                    if (Math.abs(expected[channel][x][y] - resultValue) > 30) {
                        error++;
                    }
                }
            }
        }
        if (error > MAX_ERROR) {
            return false;
        }
        return true;
    }

    /**
     * Execute the challenge comparation to object
     *
     * @param challangeValue
     * @param output
     * @return boolean
     */
    private boolean comprateObject(MissionValue challangeValue, DynamicValue output) {
        return challangeValue.getValue().equals(String.valueOf(output.get()));
    }

}
