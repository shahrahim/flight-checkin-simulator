package util;
import java.util.Random;

public class RandomGenerator {
   
    private static Random fRandom = new Random();

    private static final Integer VARIANCE = 3;

    public static Integer getSanitizedNumber(Integer mean) {
        Integer randomNum = getGaussian(mean);

        Integer santizedNum;

        if(randomNum == 0) {
            santizedNum = mean;
        } else if(randomNum < 0) {
            santizedNum = randomNum * -1;
        } else {
            santizedNum = randomNum;
        }

        return santizedNum;
    }

    public static Integer getRandomNumberWithinRange(Integer min, Integer max) {
        return fRandom.nextInt(max) + min;
    }

    private static Integer getGaussian(Integer aMean){
        return (int)(aMean + fRandom.nextGaussian() * VARIANCE);
    }

} 