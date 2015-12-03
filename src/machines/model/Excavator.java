package machines.model;

import machines.UI.Implementation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Nade_ on 30.11.2015.
 */
public class Excavator extends Machine {


    public void fillArrayEx(Object[][] array, GregorianCalendar curTime, GregorianCalendar endTime, Object[][] mach1, double constWork, double constRep) {
//        super.fillArray(array, curTime, endTime);
        int status;
        double rand;
        double rand1;
        double rand2;
        double difference;
        double wait;
        Calendar valMass;
        Calendar valMassCur;
        valMass = Calendar.getInstance();
        valMassCur = Calendar.getInstance();

        for (int i = 0; i < Implementation.ROW_MASS; i++) {
            if (curTime.getTime().after(endTime.getTime()) || curTime.getTime().equals(endTime.getTime())) {
                break;
            }
            array[i][0] = curTime.getTime();
            if (i==0 || array[i-1][2].equals(2)) {
                rand = getRandomNumb(constWork);
                status = 1; // status 1 - work
                curTime.add(Calendar.MINUTE, (int) (rand * 60));
//
//                array[i][1] = curTime.getTime();
//                array[i][2] = status;
//                array[i][3] = rand;
            } else {
                for (int m = 0; m < Implementation.ROW_MASS; m++) {
                    if (mach1[m][0] == null) break;
                    if (mach1[m][2].equals(2)) {
                        if ( (curTime.getTime().after((Date) mach1[m][0]) && curTime.getTime().before((Date) mach1[m][1])) || curTime.getTime().equals((Date) mach1[m][0]) ) {
                            valMass.setTime((Date) mach1[m][1]);
                            wait = (double) (valMass.getTimeInMillis() - curTime.getTimeInMillis()) / 60 / 60000;
                            wait = Math.round(wait * 10) / 10.0;
                            curTime.add(Calendar.MINUTE, (int) (wait * 60));
                            status = 3; // status 3 - wait
                            array[i][1] = curTime.getTime();
                            array[i][2] = status;
                            array[i][3] = wait;
                            i++;
                            array[i][0] = curTime.getTime();
                        }
                    }
                }
                rand = getRandomNumb(constRep);
                curTime.add(Calendar.MINUTE, (int) (rand * 60));
                status = 2; // status 2 - repair
                chek:
                for (int m = 0; m < Implementation.ROW_MASS; m++) {
                    if (mach1[m][0] == null) break;
                    if (mach1[m][2].equals(2)) {
                        if ( (curTime.getTime().after((Date) mach1[m][0]) && curTime.getTime().before((Date) mach1[m][1])) || curTime.getTime().equals((Date) mach1[m][0]) ) {
                            valMass.setTime((Date) mach1[m][0]);
                            valMassCur.setTime((Date) array[i][0]);
                            rand1 = (double) (valMass.getTimeInMillis() - valMassCur.getTimeInMillis()) / 60 / 60000;
                            rand1 = Math.round(rand1 * 10) / 10.0;
                            rand2 = (double) rand - rand1;
                            curTime.setTime((Date) mach1[m][0]);
                            status = 2; // status 2 - repair
                            array[i][1] = curTime.getTime();
                            array[i][2] = status;
                            array[i][3] = rand1;
                            i++;
                            array[i][0] = curTime.getTime();
                            array[i][1] = mach1[m][1];
                            array[i][2] = 3; // status 3 - wait
                            valMass.setTime((Date) mach1[m][1]);
                            wait = (double) (valMass.getTimeInMillis() - curTime.getTimeInMillis()) / 60 / 60000;
                            wait = Math.round(wait * 10) / 10.0;
                            array[i][3] = wait;
                            i++;
                            curTime.add(Calendar.MINUTE, (int) (wait * 60));
                            array[i][0] = mach1[m][1];
                            rand=rand2;
                            curTime.add(Calendar.MINUTE, (int) (rand2* 60));
                            continue chek;
                        }
                    }
                }
            }
//            curTime.add(Calendar.MINUTE, (int) (rand * 60));
            if (curTime.getTime().after(endTime.getTime()) && status == 1) {
                getDiffRand(rand, curTime, endTime);
            }
            array[i][1] = curTime.getTime();
            array[i][2] = status;
            array[i][3] = rand;
        }
    }
}
