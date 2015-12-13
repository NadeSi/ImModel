package machines.model;

import machines.UI.Implementation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Nade_ on 28.11.2015.
 */
public class Machine {
     public  double sumWork;
     public double sumRep;
     public double sumWait;
     public int cost;
     public int income;

     public Object[][] massif = new Object[Implementation.ROW_MASS][Implementation.COLUMN_MASS];
     public GregorianCalendar curTime = new GregorianCalendar(0, 0, 0, Implementation.ST_LABOR_HH, Implementation.ST_LABOR_MM, 0);


     public static double sumValueArray(Object[][] array,double sum, int status){
          for (int i = 0; i < Implementation.ROW_MASS; i++) {
               if(array[i][2] == null) return sum;
               if (array[i][2].equals(status)) {
                    sum +=  (double)array[i][3];
                    sum = Math.round(sum * 10) / 10.0;
               }
          }
          return sum;
     }

     public void fillArray(Object[][] array, GregorianCalendar curTime, GregorianCalendar endTime,double constWork, double constRep) {
          int status;
          double rand;


          for (int i = 0; i < Implementation.ROW_MASS; i++) {
               if (curTime.getTime().after(endTime.getTime()) || curTime.getTime().equals(endTime.getTime())) {
                    break;
               }
               array[i][0] = curTime.getTime();
               if ((i % 2) == 0) {
                    rand = getRandomNumb(constWork);
                    status = 1; // status - work
               } else {
                    rand = getRandomNumb(constRep);
                    status = 2; // status - repair
               }
               curTime.add(Calendar.MINUTE, (int) (rand * 60));
//        System.out.println(sdf.format(calen.getTime()) + " (" + randBuld + ")");
               if (curTime.getTime().after(endTime.getTime()) && status == 1) {
                    rand = getDiffRand(rand, curTime, endTime,array[i][0]);
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

     public static double getDiffRand(double rand, GregorianCalendar curTime, GregorianCalendar endTime, Object preDate) {
          Calendar preCal;
          preCal = Calendar.getInstance();
//          double difference = getDifference(curTime,endTime);
//          int dif = (int) (difference * 60);
//          curTime.add(Calendar.MINUTE, -dif);
          curTime.setTime(endTime.getTime());
//          rand = Math.abs(rand - difference);
          preCal.setTime((Date) preDate);
          Date end = endTime.getTime();
          rand = (double) (endTime.getTimeInMillis() - preCal.getTimeInMillis()) / 60 / 60000;
          return Math.round(rand * 10) / 10.0;
     }

//     public static double getDifference(Calendar curTime, Calendar endTime) {
//          double difference;
//          difference = (double) (curTime.getTimeInMillis() - endTime.getTimeInMillis()) / 60 / 60000;
//          return difference;
//     }
}



