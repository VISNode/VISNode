package visnode.challenge;

import visnode.repository.ChallengeUserRepository;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import visnode.application.VISNode;
import visnode.commons.DynamicValue;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The challenge scope
 */
public class ChallengeController {

    /** The scope instance */
    private static ChallengeController instance;
    /** The challenge data */
    private Challenge challenge;
    /** Has challenge */
    private final BehaviorSubject<Boolean> has;
    /** The challenge run */
    private final ChallengeComparator comparator;
    /** Initial date */
    private Date dateInitial;

    private ChallengeController() {
        this.has = BehaviorSubject.createDefault(Boolean.FALSE);
        this.comparator = new ChallengeComparator();
        VISNode.get().getController().addNewProjectListener(() -> {
            end();
        });
    }

    /**
     * Returns true if had a challenge
     *
     * @return Observable
     */
    public Observable<Boolean> hasChallenge() {
        return has;
    }

    /**
     * Starts a challenge
     *
     * @param challenge
     */
    public void start(Challenge challenge) {
        this.challenge = challenge;
        this.dateInitial = new Date();
        has.onNext(Boolean.TRUE);
    }

    /**
     * Execute the challenge comparation
     *
     * @return boolean
     */
    public CompletableFuture<Boolean> comparate() {
        CompletableFuture<Boolean> future = new CompletableFuture();
        ChallengeUser challengeUser = ChallengeUserBuilder.create().
                user(UserController.get().getUserName()).
                challenge(getChallenge()).
                submission(VISNode.get().getModel().getNetwork()).
                dateInitial(dateInitial).
                dateFinal(new Date()).
                build();
        comparator.comparate(challenge, challengeUser).thenAccept((accepted) -> {
            try {
                if (!accepted) {
                    future.complete(false);
                    return;
                }
                ChallengeUserRepository.get().put(challengeUser);
                future.complete(true);
            } catch (RepositoryException ex) {
                Logger.getLogger(ChallengeController.class.getName()).log(Level.SEVERE, null, ex);
                future.complete(false);
            }
        });
        return future;
    }

    /**
     * Ends a challenge
     *
     */
    public void end() {
        this.challenge = null;
        has.onNext(Boolean.FALSE);
    }

    /**
     * Returns the challenge
     *
     * @return Challenge
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Returns the singleton instance
     *
     * @return ChallangeScope
     */
    public static ChallengeController get() {
        if (instance == null) {
            instance = new ChallengeController();
        }
        return instance;
    }

}
