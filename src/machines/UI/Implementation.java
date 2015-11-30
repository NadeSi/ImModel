package machines.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nade_ on 28.11.2015.
 */
public class Implementation {

    public static final double M_WORK_BULD = 6;
    public static final double M_WORK_EKS = 4;
    public static final double M_REM_EKS_S3S6 = 0.25;
    public static final double M_REM_EKS_S6 = 1;
    public static final double M_REP_BULD_S3S6 = 1.5;
    public static final double M_REM_BULD_S6 = 2;

    public static final double LABOR_HOURS = 8.0;

    public static final int ST_LABOR_HH = 6;
    public static final int ST_LABOR_MM = 0;
    public static final int ROW_MASS = 50;
    public static final int COLUMN_MASS = 4;

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        GregorianCalendar curTime = new GregorianCalendar(1, 1, 1, ST_LABOR_HH, ST_LABOR_MM, 0);
//        GregorianCalendar endTime = (GregorianCalendar) curTime.clone();
////        endTime.add(Calendar.HOUR,16);
        GregorianCalendar endTime = new GregorianCalendar(1, 1, 1, ST_LABOR_HH + 16, ST_LABOR_MM, 0);

        Object[][] buld = new Object[ROW_MASS][COLUMN_MASS];
        Object[][] ex1 = new Object[ROW_MASS][COLUMN_MASS];
        Object[][] ex2 = new Object[ROW_MASS][COLUMN_MASS];

/*
        System.out.println("BULDOZER: ");
        System.out.print(sdf.format(calen.getTime()) + " - ");
*/

        double sumWork = 0;
        double sumRem = 0;
        double summ = 0;


        int status = 0;
        double randBuld = 0;
        double difference = 0;

        for (int i = 0; i < ROW_MASS ; i++) {
            if(curTime.getTime().after(endTime.getTime()) || curTime.getTime().equals(endTime.getTime())){
                break;
            }
            buld[i][0] = curTime.getTime();
            if ((i%2) == 0) {
                randBuld = getRandomNumb(M_WORK_BULD);
                //        sumWork =+ randWorkBuld;
                status = 1; // status - work
            }
            else{
                randBuld = getRandomNumb(M_REP_BULD_S3S6);
                status = 2; // status - repair
            }
            curTime.add(Calendar.MINUTE,(int)(randBuld * 60));
//        System.out.println(sdf.format(calen.getTime()) + " (" + randBuld + ")");
            if (curTime.getTime().after(endTime.getTime()) && status == 1) {
//                System.out.println("randBuld1: " + randBuld);
//                System.out.println("cur1: " + sdf.format(curTime.getTime()));
//                System.out.println(endTime.getTimeInMillis());

                difference = (double) (curTime.getTimeInMillis()-endTime.getTimeInMillis())/60/60000;
                int dif = (int)(difference * 60);
                curTime.add(Calendar.MINUTE,-dif);
                randBuld = Math.abs(Math.abs(randBuld) - Math.abs(difference));
                randBuld = Math.round(randBuld*10)/10.0;
//                System.out.println("cur2: " + sdf.format(curTime.getTime()));
//                System.out.println("end: " + sdf.format(endTime.getTime()));
//                System.out.println("randBuld2: " + randBuld);
//                System.out.println("Dif: " + difference);
            }
            buld[i][1] = curTime.getTime();
            buld[i][2] = status;
            buld[i][3] = randBuld;

        }
/*        GregorianCalendar enddTime = new GregorianCalendar(0, 0, 0, 23, ST_LABOR_MM + 30, 0);
        int m = enddTime.get(Calendar.HOUR_OF_DAY)*60 + enddTime.get(Calendar.MINUTE) ;
        int n = endTime.get(Calendar.HOUR_OF_DAY)*60 + endTime.get(Calendar.MINUTE) ;
//        int m = enddTime.get(Calendar.HOUR);
//        endTime.add(Calendar.MINUTE, m);
        System.out.println(sdf.format(enddTime.getTime()));
        System.out.println(sdf.format(endTime.getTime()));
        System.out.println((double)(m-n)/60);
        System.out.println(m);
        System.out.println(n);*/


        String st = null;
        for (int i = 0; i < ROW_MASS; i++) {

            for (int j = 0; j < COLUMN_MASS-2; j++) {
                if(buld[i][j] == null){
                    return;
                }
                System.out.print(sdf.format(buld[i][j]));
                System.out.print(" - ");
            }
            if (buld[i][2].equals(1)) {
                st = "work";

            } else if (buld[i][2].equals(2)) {
                st = "repair";

            } else if (buld[i][2].equals(3)) {
                st = "wait";

            } else {
            }
            System.out.print(" :" + st);
            System.out.println(" (" + buld[i][3] + ")");
        }
/*        double randRemBuldS6 = getRandomNumb(M_REM_BULD_S6);
        double randRemBuldS3S6 = getRandomNumb(M_REP_BULD_S3S6);*/

/*

        if ((sumWork =+ randWorkBuld + sumRem) < LABOR_HOURS*2) {
            
        }
*/

/*
        System.out.println("Работа Бульдозера:");
        System.out.println("Работа Бульдозера:");
*/


/*
        for (int i = 0; i < 20; i++) {
            System.out.println(getRandomNumb(4));
        }
*/

/*
        GregorianCalendar calen = new GregorianCalendar();
        calen.set(Calendar.HOUR, 8);
        calen.set(Calendar.MINUTE, 00);
        System.out.println(calen.get(Calendar.HOUR)+":"+calen.get(Calendar.MINUTE) );
        double qqqq = 1.5;
        int min = (int)(qqqq * 60);
        calen.add(Calendar.MINUTE,min);
        System.out.println(calen.get(Calendar.HOUR)+":"+calen.get(Calendar.MINUTE) + "\n min: " + min );
*/

    }

    private static double getRandomNumb(double M) {

        Random r = new Random();
        double exRand =  -M * Math.log(1 - r.nextDouble());
        return Math.round(exRand*10)/10.0;

    }
}


