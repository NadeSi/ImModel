package machines.UI;

import java.util.Random;

/**
 * Created by Nade_ on 28.11.2015.
 */
public class Implementation {

    public static final int M = 4;

    public static void main(String[] args) {
        Random random = new Random();


        for (int i = 0; i < 20; i++) {
            System.out.println(getRandomNumb(M));
        }


    }

    private static double getRandomNumb(int M) {

        Random r = new Random();
        double exRan =  -M * Math.log(1 - r.nextDouble());
        return exRan;

    }
}


