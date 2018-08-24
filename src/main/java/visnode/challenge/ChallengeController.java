package visnode.challenge;

import visnode.repository.MissionUserRepository;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.io.File;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import visnode.application.VISNode;
import visnode.commons.MultiFileInput;
import visnode.repository.RepositoryException;
import visnode.user.UserController;

/**
 * The mission scope
 */
public class ChallengeController {

    /** The scope instance */
    private static ChallengeController instance;
    /** The mission data */
    private Mission mission;
    /** Has mission */
    private final BehaviorSubject<Boolean> has;
    /** The mission run */
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
     * Returns true if had a mission
     *
     * @return Observable
     */
    public Observable<Boolean> hasMission() {
        return has;
    }

    /**
     * Starts a mission
     *
     * @param mission
     */
    public void start(Mission mission) {
        this.mission = mission;
        this.dateInitial = new Date();
        has.onNext(Boolean.TRUE);
    }

    /**
     * Execute the mission comparation
     *
     * @return boolean
     */
    public CompletableFuture<Boolean> comparate() {
        CompletableFuture<Boolean> future = new CompletableFuture();
        MissionUser missionUser = MissionUserBuilder.create().
                user(UserController.get().getUser()).mission(getMission()).
                submission(VISNode.get().getModel().getNetwork()).
                dateInitial(dateInitial).
                dateFinal(new Date()).
                build();
        comparator.comparate(mission, missionUser).thenAccept((accepted) -> {
            try {
                if (!accepted) {
                    missionUser.setStatusError();
                    MissionUserRepository.get().put(missionUser);
                    future.complete(false);
                    return;
                }
                missionUser.setStatusSuccess();
                MissionUserRepository.get().put(missionUser);
                future.complete(true);
            } catch (RepositoryException ex) {
                Logger.getLogger(ChallengeController.class.getName()).log(Level.SEVERE, null, ex);
                future.complete(false);
            }
        });
        return future;
    }

    /**
     * Open the project
     *
     * @param submission
     * @param mission
     */
    public void openProject(String submission, Mission mission) {
        VISNode.get().getController().open(submission);
        VISNode.get().getModel().getNetwork().
                setInput(new MultiFileInput(mission.getInputFiles().toArray(new File[]{})));
    }

    /**
     * Ends a mission
     *
     */
    public void end() {
        this.mission = null;
        has.onNext(Boolean.FALSE);
    }

    /**
     * Returns the mission
     *
     * @return Mission
     */
    public Mission getMission() {
        return mission;
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
