package visnode.repository;

import java.util.ArrayList;
import java.util.List;
import visnode.challenge.Mission;

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
        List<Mission> list = new ArrayList<>();
        list.add(getObjectsSegmentation());
        list.add(getDayOrNight());
        list.add(getCoin());
        list.add(getFungusPlant());
        return list;
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
        mission.setXp(50);
        mission.setLevel(1);
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
        mission.setXp(75);
        mission.setLevel(1);
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
        mission.setXp(100);
        mission.setLevel(1);
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
        mission.setXp(100);
        mission.setLevel(2);
        mission.addChallange(challengeRepository.getFungusPlant1(mission));
        mission.addChallange(challengeRepository.getFungusPlant2(mission));
        return mission;
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
