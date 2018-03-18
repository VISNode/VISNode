package visnode.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import visnode.challenge.ChallengeUser;
import visnode.challenge.ChallengeUserBuilder;

/**
 * The challenge user repository
 */
public class ChallengeUserRepository {

    /** Instance */
    private static ChallengeUserRepository instance;
    /** Challenges list */
    private final List<ChallengeUser> data;

    private ChallengeUserRepository() {
        this.data = new ArrayList<>();
        put(ChallengeUserBuilder.create().
                user("Nicolau").
                challenge(2).
                submission("{\"output\":{\"input\":[],\"hashCode\":1484650984,\"position\":{\"x\":761,\"y\":50},\"connections\":[{\"leftNode\":912247267,\"rightAttribute\":\"value\",\"leftAttribute\":\"value\"}]},\"nodes\":[{\"input\":[{\"parameterType\":\"visnode.pdi.process.ImageInput\",\"parameterName\":\"file\",\"value\":{\"type\":\"visnode.commons.MultiFileInput\",\"data\":{\"file\":[\"/home/jonatabecker/NetBeansProjects/VISNode/target/classes/challenges/dayOrNight/input.jpg\"],\"index\":0}}}],\"hashCode\":529943671,\"position\":{\"x\":50,\"y\":50},\"processType\":\"visnode.pdi.process.InputProcess\",\"connections\":[]},{\"input\":[],\"hashCode\":1026140758,\"position\":{\"x\":253,\"y\":379},\"processType\":\"visnode.pdi.process.InformationProcess\",\"connections\":[{\"leftNode\":529943671,\"rightAttribute\":\"image\",\"leftAttribute\":\"image\"}]},{\"input\":[{\"parameterType\":\"visnode.commons.ScriptValue\",\"parameterName\":\"script\",\"value\":{\"value\":\"function process(avarege) {\\n\\treturn avarege \\u003e 20 ? 1 : 2;\\n}\"}}],\"hashCode\":912247267,\"position\":{\"x\":416,\"y\":101},\"processType\":\"visnode.pdi.process.ScriptProcess\",\"connections\":[{\"leftNode\":1026140758,\"rightAttribute\":\"input\",\"leftAttribute\":\"average\"}]}]}").
                build());
    }

    /**
     * Adds a value
     *
     * @param challengeUser
     */
    public void put(ChallengeUser challengeUser) {
        data.add(challengeUser);
    }

    /**
     * Returns true if has a value
     *
     * @param user
     * @param challenge
     * @return boolean
     */
    public boolean has(String user, int challenge) {
        return data.stream().
                anyMatch((it) -> it.getUser().equals(user) && it.getChallenge() == challenge);
    }

    /**
     * Returns the challenge
     *
     * @param challenge
     * @return {@code List<ChallengeUser>}
     */
    public List<ChallengeUser> get(int challenge) {
        return data.stream().
                filter((it) -> it.getChallenge() == challenge).
                collect(Collectors.toList());
    }

    /**
     * Returns the challenge user repository instance
     *
     * @return ChallengeUserRepository
     */
    public static ChallengeUserRepository get() {
        if (instance == null) {
            instance = new ChallengeUserRepository();
        }
        return instance;
    }

}
