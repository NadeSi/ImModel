package machines.model;

import machines.UI.Implementation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Nade_ on 28.11.2015.
 */
public class Machine {

     public void fillArray(Object[][] array, GregorianCalendar curTime, GregorianCalendar endTime) {
          int status;
          double rand;


          for (int i = 0; i < Implementation.ROW_MASS; i++) {
               if (curTime.getTime().after(endTime.getTime()) || curTime.getTime().equals(endTime.getTime())) {
                    break;
               }
               array[i][0] = curTime.getTime();
               if ((i % 2) == 0) {
                    rand = getRandomNumb(Implementation.M_WORK_BULD);
                    status = 1; // status - work
               } else {
                    rand = getRandomNumb(Implementation.M_REP_BULD_S3S6);
                    status = 2; // status - repair
               }
               curTime.add(Calendar.MINUTE, (int) (rand * 60));
//        System.out.println(sdf.format(calen.getTime()) + " (" + randBuld + ")");
               if (curTime.getTime().after(endTime.getTime()) && status == 1) {
                    getDiffRand(rand, curTime, endTime);
               }
               array[i][1] = curTime.getTime();
               array[i][2] = status;
               array[i][3] = rand;
//               curTime.add(Calendar.MINUTE, 1);
          }
     }

     public static double getRandomNumb(double M) {

          Random r = new Random();
          double exRand;
          do exRand = -M * Math.log(1 - r.nextDouble());
          while (exRand < 0.1);
          return Math.round(exRand * 10) / 10.0;

     }

     public static double getDiffRand(double rand, GregorianCalendar curTime, GregorianCalendar endTime) {
          double difference = getDifference(curTime,endTime);
          int dif = (int) (difference * 60);
          curTime.add(Calendar.MINUTE, -dif);
          rand = Math.abs(Math.abs(rand) - Math.abs(difference));
          return Math.round(rand * 10) / 10.0;
     }

     public static double getDifference(Calendar curTime, Calendar endTime) {
          double difference;
          difference = (double) (curTime.getTimeInMillis() - endTime.getTimeInMillis()) / 60 / 60000;
          return difference;
     }
}



