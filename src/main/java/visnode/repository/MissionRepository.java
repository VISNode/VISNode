package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.stream.Collectors;
import visnode.challenge.Mission;
import visnode.ws.WebService;
import visnode.ws.WebServiceException;

/**
 * Mission repository
 */
public class MissionRepository {

    /** Instance */
    private static MissionRepository instance;
    private final ChallengeRepository challengeRepository = new ChallengeRepository();

    /**
     * Returns all missions
     *
     * @return {@code List<Mission>}
     * @throws RepositoryException
     */
    public List<Mission> getAll() throws RepositoryException {
        try {
            return WebService.get().
                    get("mission").
                    get(new TypeToken<List<Mission>>() {
                    }).stream().map((mission) -> {
                         mission.getChallenges().stream().forEach((challenge) -> {
                            challenge.setMission(mission);
                        });
                        return mission;
                    }).collect(Collectors.toList());
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns a mission
     *
     * @return Mission
     */
    private Mission getObjectsSegmentation() {
        Mission mission = new Mission();
        mission.setId(1);
        mission.setName("Segmentação de objetos");
        mission.setDescription("Execute a segmentação de objetos de uma imagem");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/objectsSegmentation/problem.md");
        mission.addChallange(challengeRepository.getObjectsSegmentation(mission));
        return mission;
    }

    /**
     * Returns a mission
     *
     * @return Mission
     */
    private Mission getDayOrNight() {
        Mission mission = new Mission();
        mission.setId(2);
        mission.setName("Dia ou noite");
        mission.setDescription("Duscubra se é dia ou noite");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/dayOrNight/problem.md");
        mission.addChallange(challengeRepository.getDayOrNight(mission));
        return mission;
    }

    /**
     * Returns a mission
     *
     * @return Mission
     */
    private Mission getCoin() {
        Mission mission = new Mission();
        mission.setId(3);
        mission.setName("Valor monetário");
        mission.setDescription("Cálcule o valor das moedas");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/coin/problem.md");
        mission.addChallange(challengeRepository.getCoin(mission));
        return mission;
    }

    /**
     * Returns a mission
     *
     * @return Mission
     */
    private Mission getFungusPlant() {
        Mission mission = new Mission();
        mission.setId(4);
        mission.setName("Detecção de tratamento para fungos em plantas");
        mission.setDescription("Identifique o tratamento ideal para plantas de acordo com o nível de contaminação");
        mission.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/fungusPlant/problem.md");
        mission.addChallange(challengeRepository.getFungusPlant1(mission));
        mission.addChallange(challengeRepository.getFungusPlant2(mission));
        return mission;
    }

    /**
     * Save a new mission
     *
     * @param mission
     * @throws RepositoryException
     */
    public void save(Mission mission) throws RepositoryException {
        try {
            WebService.get().post("mission", mission);
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
    public void update(Mission mission) throws RepositoryException {
        try {
            WebService.get().post("mission/" + mission.getId(), mission);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns the mission repository instance
     *
     * @return ChallengeUserRepository
     */
    public static MissionRepository get() {
        if (instance == null) {
            instance = new MissionRepository();
        }
        return instance;
    }

}
