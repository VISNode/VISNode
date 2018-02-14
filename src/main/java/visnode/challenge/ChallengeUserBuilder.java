package visnode.challenge;

import visnode.application.NodeNetwork;
import visnode.application.parser.NodeNetworkParser;

/**
 * Challenge user factory
 */
public class ChallengeUserBuilder {

    /** Node network parser */
    private final NodeNetworkParser parser;
    /** Challenge user instance */
    private final ChallengeUser challengeUser;

    private ChallengeUserBuilder() {
        this.parser = new NodeNetworkParser();
        this.challengeUser = new ChallengeUser();
    }

    /**
     * Sets the user
     *
     * @param user
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder user(String user) {
        challengeUser.setUser(user);
        return this;
    }

    /**
     * Sets the challenge
     *
     * @param challenge
     * @return ChallengeUserBuilder
     */
    public ChallengeUserBuilder challenge(int challenge) {
        challengeUser.setChallenge(challenge);
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
