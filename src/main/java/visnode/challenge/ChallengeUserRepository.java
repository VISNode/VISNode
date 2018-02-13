package visnode.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The challenge user repository
 */
public class ChallengeUserRepository {

    /** Challenges list */
    private final List<ChallengeUser> data;

    public ChallengeUserRepository() {
        this.data = new ArrayList<>();
    }

    public void put(ChallengeUser challengeUser) {
        data.add(challengeUser);
    }

    public boolean has(String user, int challenge) {
        return data.stream().
                anyMatch((it) -> it.getUser().equals(user) && it.getChallenge() == challenge);
    }

    public List<ChallengeUser> get(int challenge) {
        return data.stream().
                filter((it) -> it.getChallenge() == challenge).
                collect(Collectors.toList());
    }

}
