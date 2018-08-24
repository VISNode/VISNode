package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.stream.Collectors;
import visnode.challenge.Challenge;
import visnode.ws.WebService;
import visnode.ws.WebServiceException;
import visnode.ws.WebServiceQuery;

/**
 * Challenge repository
 */
public class ChallengeRepository {

    /** Instance */
    private static ChallengeRepository instance;
    private final MissionRepository challengeRepository = new MissionRepository();

    /**
     * Returns all missions
     *
     * @return {@code List<Challenge>}
     * @throws RepositoryException
     */
    public List<Challenge> getAll() throws RepositoryException {
        try {
            return WebService.get().
                    get("challenge").
                    get(new TypeToken<List<Challenge>>() {
                    }).stream().map((mission) -> {
                         mission.getMissions().stream().forEach((challenge) -> {
                            challenge.setChallenge(mission);
                        });
                        return mission;
                    }).collect(Collectors.toList());
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns all missions
     *
     * @param idChallenge
     * @return Challenge
     * @throws RepositoryException
     */
    public Challenge get(long idChallenge) throws RepositoryException {
        try {
            return WebService.get().
                    get("challenge",
                            WebServiceQuery.create().
                                    put("id", idChallenge)).
                    get(new TypeToken<List<Challenge>>() {
                    }).stream().map((mission) -> {
                         mission.getMissions().stream().forEach((challenge) -> {
                            challenge.setChallenge(mission);
                        });
                        return mission;
                    }).collect(Collectors.toList()).get(0);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns a mission
     *
     * @return Challenge
     */
    private Challenge getObjectsSegmentation() {
        Challenge mission = new Challenge();
        mission.setId(1);
        mission.setName("Segmentação de objetos");
        mission.setDescription("Execute a segmentação de objetos de uma imagem");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/objectsSegmentation/problem.md");
        mission.addMission(challengeRepository.getObjectsSegmentation(mission));
        return mission;
    }

    /**
     * Returns a mission
     *
     * @return Challenge
     */
    private Challenge getDayOrNight() {
        Challenge mission = new Challenge();
        mission.setId(2);
        mission.setName("Dia ou noite");
        mission.setDescription("Duscubra se é dia ou noite");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/dayOrNight/problem.md");
        mission.addMission(challengeRepository.getDayOrNight(mission));
        return mission;
    }

    /**
     * Returns a mission
     *
     * @return Challenge
     */
    private Challenge getCoin() {
        Challenge mission = new Challenge();
        mission.setId(3);
        mission.setName("Valor monetário");
        mission.setDescription("Cálcule o valor das moedas");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/coin/problem.md");
        mission.addMission(challengeRepository.getCoin(mission));
        return mission;
    }

    /**
     * Returns a mission
     *
     * @return Challenge
     */
    private Challenge getFungusPlant() {
        Challenge mission = new Challenge();
        mission.setId(4);
        mission.setName("Detecção de tratamento para fungos em plantas");
        mission.setDescription("Identifique o tratamento ideal para plantas de acordo com o nível de contaminação");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/fungusPlant/problem.md");
        mission.addMission(challengeRepository.getFungusPlant1(mission));
        mission.addMission(challengeRepository.getFungusPlant2(mission));
        return mission;
    }

    /**
     * Save a new mission
     *
     * @param mission
     * @throws RepositoryException
     */
    public void save(Challenge mission) throws RepositoryException {
        try {
            WebService.get().post("challenge", mission);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Save a new mission
     *
     * @param mission
     * @throws RepositoryException
     */
    public void update(Challenge mission) throws RepositoryException {
        try {
            WebService.get().post("challenge/" + mission.getId(), mission);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns the mission repository instance
     *
     * @return ChallengeUserRepository
     */
    public static ChallengeRepository get() {
        if (instance == null) {
            instance = new ChallengeRepository();
        }
        return instance;
    }

}
