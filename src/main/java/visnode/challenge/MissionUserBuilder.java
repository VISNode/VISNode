package visnode.challenge;

import java.util.Date;
import visnode.application.NodeNetwork;
import visnode.application.parser.NodeNetworkParser;
import visnode.user.User;

/**
 * Mission user factory
 */
public class MissionUserBuilder {

    /** Node network parser */
    private final NodeNetworkParser parser;
    /** Mission user instance */
    private final MissionUser missionUser;
    /** Mission experience calculator */
    private final MissionXpCalculator calculator;
    
    private MissionUserBuilder() {
        this.parser = new NodeNetworkParser();
        this.missionUser = new MissionUser();
        this.calculator = new MissionXpCalculator();
    }

    /**
     * Sets the user
     *
     * @param user
     * @return MissionUserBuilder
     */
    public MissionUserBuilder user(User user) {
        missionUser.setUser(user);
        return this;
    }

    /**
     * Sets the mission
     *
     * @param mission
     * @return MissionUserBuilder
     */
    public MissionUserBuilder mission(Mission mission) {
        missionUser.setChallenge(mission.getChallenge().getId());
        missionUser.setIdMission(mission.getId());
        missionUser.setLevel(mission.getLevel());
        missionUser.setXp(calculator.calculate(missionUser.getUser(), mission));
        return this;
    }

    /**
     * Sets the submission
     *
     * @param network
     * @return MissionUserBuilder
     */
    public MissionUserBuilder submission(NodeNetwork network) {
        missionUser.setSubmission(parser.toJson(network));
        return this;
    }

    /**
     * Sets the initial date
     *
     * @param dataInitial
     * @return MissionUserBuilder
     */
    public MissionUserBuilder dateInitial(Date dataInitial) {
        missionUser.setDateInitial(dataInitial);
        return this;
    }

    /**
     * Sets the final date
     *
     * @param dataFinal
     * @return MissionUserBuilder
     */
    public MissionUserBuilder dateFinal(Date dataFinal) {
        missionUser.setDateFinal(dataFinal);
        return this;
    }

    /**
     * Sets the submission
     *
     * @param submission
     * @return MissionUserBuilder
     */
    public MissionUserBuilder submission(String submission) {
        missionUser.setSubmission(submission);
        return this;
    }

    /**
     * Builds the user
     *
     * @return MissionUser
     */
    public MissionUser build() {
        return missionUser;
    }

    /**
     * Return the instance
     *
     * @return ChallengeUserFactory
     */
    public static MissionUserBuilder create() {
        return new MissionUserBuilder();
    }

}
