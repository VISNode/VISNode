package visnode.repository;

import visnode.challenge.Mission;
import visnode.challenge.ChallengeDifficulty;
import visnode.challenge.MissionValue;
import visnode.challenge.MissionValueType;
import visnode.challenge.Challenge;

/**
 * The challenge repository
 */
public class MissionRepository {

     /**
     * Returns a challenge
     *
     * @param mission
     * @return Mission
     */
    public Mission getObjectsSegmentation(Challenge mission) {
        Mission challenge = new Mission();
        challenge.setId(1);
        challenge.setChallenge(mission);
        challenge.setName("Segmentação de objetos");
        challenge.setDescription("Execute a segmentação de objetos de uma imagem");
        challenge.setDifficulty(ChallengeDifficulty.EASY);
        challenge.addInput(valueImage(getFile("/challenges/objectsSegmentation/input.png")));
        challenge.addOutput(valueImage(getFile("/challenges/objectsSegmentation/output.png")));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/objectsSegmentation/problem.md");
        challenge.setXp(50);
        challenge.setLevel(1);
        return challenge;
    }
  
    /**
     * Returns a challenge
     *
     * @param mission
     * @return Mission
     */
    public Mission getDayOrNight(Challenge mission) {
        Mission challenge = new Mission();
        challenge.setId(2);
        challenge.setChallenge(mission);
        challenge.setName("Dia ou noite");
        challenge.setDescription("Duscubra se é dia ou noite");
        challenge.setDifficulty(ChallengeDifficulty.EASY);
        challenge.addInput(valueImage(getFile("/challenges/dayOrNight/input.jpg")));
        challenge.addInput(valueImage(getFile("/challenges/dayOrNight/night.jpg")));
        challenge.addOutput(value("1"));
        challenge.addOutput(value("2"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/dayOrNight/problem.md");
        challenge.setXp(75);
        challenge.setLevel(1);
        return challenge;
    }

    /**
     * Returns a challenge
     *
     * @param mission
     * @return Mission
     */
    public Mission getCoin(Challenge mission) {
        Mission challenge = new Mission();
        challenge.setId(3);
        challenge.setChallenge(mission);
        challenge.setName("Valor monetário");
        challenge.setDescription("Cálcule o valor das moedas");
        challenge.setDifficulty(ChallengeDifficulty.HARD);
        challenge.addInput(valueImage(getFile("/challenges/coin/input.jpg")));
        challenge.addOutput(value("R$ 2,90"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/coin/problem.md");
        challenge.setXp(100);
        challenge.setLevel(1);
        return challenge;
    }
  
    /**
     * Returns a challenge
     *
     * @param mission
     * @return Mission
     */
    public Mission getFungusPlant1(Challenge mission) {
        Mission challenge = new Mission();
        challenge.setId(4);
        challenge.setChallenge(mission);
        challenge.setName("Detecção de tratamento para fungos em plantas");
        challenge.setDescription("Identifique o tratamento ideal para plantas de acordo com o nível de contaminação");
        challenge.setDifficulty(ChallengeDifficulty.HARD);
        challenge.addInput(valueImage(getFile("/challenges/fungusPlant/ft1.png")));
        challenge.addOutput(value("MEDIA"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/fungusPlant/problem.md");
        challenge.setXp(75);
        challenge.setLevel(1);
        return challenge;
    }
    
    /**
     * Returns a challenge
     *
     * @param mission
     * @return Mission
     */
    public Mission getFungusPlant2(Challenge mission) {
        Mission challenge = new Mission();
        challenge.setId(5);
        challenge.setChallenge(mission);
        challenge.setName("Detecção de tratamento para fungos em plantas");
        challenge.setDescription("Identifique o tratamento ideal para plantas de acordo com o nível de contaminação");
        challenge.setDifficulty(ChallengeDifficulty.HARD);
        challenge.addInput(valueImage(getFile("/challenges/fungusPlant/ft1.png")));
        challenge.addInput(valueImage(getFile("/challenges/fungusPlant/ft2.png")));
        challenge.addInput(valueImage(getFile("/challenges/fungusPlant/ft3.png")));
        challenge.addInput(valueImage(getFile("/challenges/fungusPlant/ft4.png")));
        challenge.addOutput(value("MEDIA"));
        challenge.addOutput(value("MEDIA"));
        challenge.addOutput(value("INICIAL"));
        challenge.addOutput(value("GRAVE"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/fungusPlant/problem.md");
        challenge.setXp(25);
        challenge.setLevel(2);
        return challenge;
    }

    /**
     * Create a challenge value
     *
     * @param value
     * @return MissionValue
     */
    private MissionValue valueImage(String value) {
        return new MissionValue(MissionValueType.IMAGE, value);
    }

    /**
     * Create a challenge value
     *
     * @param value
     * @return MissionValue
     */
    private MissionValue value(String value) {
        return new MissionValue(MissionValueType.TEXT, value);
    }

    /**
     * Returns the resource file nae
     *
     * @param file
     * @return String
     */
    private String getFile(String file) {
        return getClass().getResource(file).getFile();
    }

}
