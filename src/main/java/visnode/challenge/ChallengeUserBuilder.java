package visnode.challenge;

import java.util.Date;
import visnode.application.NodeNetwork;
import visnode.application.parser.NodeNetworkParser;
import visnode.user.User;

/**
 * Challenge user factory
 */
public class ChallengeUserBuilder {

    /** Node network parser */
    private final NodeNetworkParser parser;
    /** Challenge user instance */
    private final ChallengeUser challengeUser;
    /** Challenge experience calculator */
    private final ChallengeXpCalculator calculator;
    
    private ChallengeUserBuilder() {
        this.parser = new NodeNetworkParser();
        this.challengeUser = new ChallengeUser();
        this.calculator = new ChallengeXpCalculator();
    }

    /**
     * Sets the user
     *
     * @param user
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder user(User user) {
        challengeUser.setUser(user);
        return this;
    }

    /**
     * Sets the challenge
     *
     * @param challenge
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder challenge(Challenge challenge) {
        challengeUser.setChallenge(challenge.getId());
        challengeUser.setXp(calculator.calculate(challengeUser.getUser(), challenge));
        return this;
    }

    /**
     * Sets the submission
     *
     * @param network
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder submission(NodeNetwork network) {
        challengeUser.setSubmission(parser.toJson(network));
        return this;
    }

    /**
     * Sets the initial date
     *
     * @param dataInitial
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder dateInitial(Date dataInitial) {
        challengeUser.setDateInitial(dataInitial);
        return this;
    }

    /**
     * Sets the final date
     *
     * @param dataFinal
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder dateFinal(Date dataFinal) {
        challengeUser.setDateFinal(dataFinal);
        return this;
    }

    /**
     * Sets the submission
     *
     * @param submission
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder submission(String submission) {
        challengeUser.setSubmission(submission);
        return this;
    }

    /**
     * Builds the user
     *
     * @return ChallengeUser
     */
    public ChallengeUser build() {
        return challengeUser;
    }

    /**
     * Return the instance
     *
     * @return ChallengeUserFactory
     */
    public static ChallengeUserBuilder create() {
        return new ChallengeUserBuilder();
    }

}
