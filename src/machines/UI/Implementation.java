package machines.UI;

import machines.model.Excavator;
import machines.model.Machine;

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

        GregorianCalendar curTimeBuld = new GregorianCalendar(0, 0, 0, ST_LABOR_HH, ST_LABOR_MM, 0);
        GregorianCalendar curTimeEx = new GregorianCalendar(0, 0, 0, ST_LABOR_HH, ST_LABOR_MM, 0);
        GregorianCalendar endTime = new GregorianCalendar(0, 0, 0, ST_LABOR_HH + 16, ST_LABOR_MM, 0);
        Date ctB = curTimeBuld.getTime();
        Date ctE = curTimeEx.getTime();
        Date et = endTime.getTime();

        Object[][] buld = new Object[ROW_MASS][COLUMN_MASS];
        Object[][] ex1 = new Object[ROW_MASS][COLUMN_MASS];
        Object[][] ex2 = new Object[ROW_MASS][COLUMN_MASS];


        System.out.println("BULLDOZER: ");
        Machine bulldozer = new Machine();
        bulldozer.fillArray(buld, curTimeBuld, endTime);
        printArray(buld);

        System.out.println("EXCAVATOR: ");
        Excavator excavator1 = new Excavator();
        excavator1.fillArrayEx(ex1, curTimeEx, endTime,buld,M_WORK_EKS,M_REM_EKS_S3S6);
        printArray(ex1);

/*
        //Удалисть лишнее
        Object[][] test = new Object[2][2];
        GregorianCalendar testTime = new GregorianCalendar(0, 0, 0, 6,30 , 0);
        GregorianCalendar testTime1 = new GregorianCalendar(0, 0, 0, 6, 0, 0);
        GregorianCalendar testTime2 = new GregorianCalendar(0, 0, 0, 7, 0, 0);
        for (int i = 0; i < 2; i++) {
            test[i][0] = testTime1 .getTime();
            test[i][1] = testTime2 .getTime();
         }
        if(testTime.after((Date) test[0][0])){
            System.out.println("true");
        }
        else System.out.println("nifiga");
        Calendar cal = null;
        cal = Calendar.getInstance();
        cal.setTime((Date) test[0][0]);

        double difference;
        difference = (double) (cal.getTimeInMillis() - endTime.getTimeInMillis()) / 60 / 60000;
        System.out.println(cal.getTime());
        System.out.println(endTime.getTime());
        System.out.println(difference);
        //Удалисть лишнее
*/

    }

    private static void printArray(Object[][] array){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String st = null;
        for (int i = 0; i < ROW_MASS; i++) {

            for (int j = 0; j < COLUMN_MASS-2; j++) {
                if(array[i][j] == null){
                    return;
                }
                System.out.print(sdf.format(array[i][j]));
                System.out.print(" - ");
            }
            if (array[i][2].equals(1)) {
                st = "work";

            } else if (array[i][2].equals(2)) {
                st = "repair";

            } else if (array[i][2].equals(3)) {
                st = "wait";

            } else {
            }
            System.out.print(" :" + st);
            System.out.println(" (" + array[i][3] + ")");
        }
    }


}


