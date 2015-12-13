package machines.model;

import machines.UI.Implementation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Nade_ on 30.11.2015.
 */
public class Excavator extends Machine {
    double wait;
    boolean flag = false;


    public void fillArrayEx(Object[][] array, GregorianCalendar curTime, GregorianCalendar endTime, Object[][] mach1, double constWork, double constRep) {
//        super.fillArray(array, curTime, endTime);
        int status;
        double wait;
        double rand;
        double rand1;
        double rand2;
        double difference;

        Calendar valMass;
        Calendar valMassCur;
        valMass = Calendar.getInstance();
        valMassCur = Calendar.getInstance();

        for (int i = 0; i < Implementation.ROW_MASS; i++) {
            if (curTime.getTime().after(endTime.getTime()) || curTime.getTime().equals(endTime.getTime())) {
                break;
            }
            array[i][0] = curTime.getTime();
            if (i == 0 || array[i - 1][2].equals(2)) {
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
                        if ((curTime.getTime().after((Date) mach1[m][0]) && curTime.getTime().before((Date) mach1[m][1])) || curTime.getTime().equals((Date) mach1[m][0])) {
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
                        if ((curTime.getTime().after((Date) mach1[m][0]) && curTime.getTime().before((Date) mach1[m][1])) || curTime.getTime().equals((Date) mach1[m][0])) {
                            valMass.setTime((Date) mach1[m][0]);
                            valMassCur.setTime((Date) array[i][0]);
                            rand1 = (double) (valMass.getTimeInMillis() - valMassCur.getTimeInMillis()) / 60 / 60000;
                            rand1 = Math.round(rand1 * 10) / 10.0;
                            rand2 = rand - rand1;
                            rand2 = Math.round(rand2 * 10) / 10.0;
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
                            rand = rand2;
                            curTime.add(Calendar.MINUTE, (int) (rand2 * 60));
                            continue chek;
                        }
                    }
                }
            }
//            curTime.add(Calendar.MINUTE, (int) (rand * 60));
            if (curTime.getTime().after(endTime.getTime()) && status == 1) {
                rand = getDiffRand(rand, curTime, endTime,array[i][0]);
            }
            array[i][1] = curTime.getTime();
            array[i][2] = status;
            array[i][3] = rand;
        }
    }


    public void fillArrayEx2(Object[][] array, GregorianCalendar curTime, GregorianCalendar endTime, Object[][] mach1, Object[][] mach2, double constWork, double constRep) {
//        super.fillArray(array, curTime, endTime);
        int status;
        double rand;
        double rand1;
        double rand2;
        double rand11;
        double rand12;
        double difference;
//        double wait;
        double waitM;
        double waitN;
        Calendar valMass;
        Calendar valMassCur;
        valMass = Calendar.getInstance();
        valMassCur = Calendar.getInstance();

        for (int i = 0; i < Implementation.ROW_MASS; i++) {
            if (curTime.getTime().after(endTime.getTime()) || curTime.getTime().equals(endTime.getTime())) {
                break;
            }
            array[i][0] = curTime.getTime();
            if (i == 0 || array[i - 1][2].equals(2)) {
                rand = getRandomNumb(constWork);
                status = 1; // status 1 - work
                curTime.add(Calendar.MINUTE, (int) (rand * 60));
//
//                array[i][1] = curTime.getTime();
//                array[i][2] = status;
//                array[i][3] = rand;
            } else {
                wait = 0;
//                cCheck:
                for (int m = 0; m < Implementation.ROW_MASS; m++) {
                    if (mach1[m][0] == null) break;
                    if (mach1[m][2].equals(2)) {
                        if ((curTime.getTime().after((Date) mach1[m][0]) && curTime.getTime().before((Date) mach1[m][1])) || curTime.getTime().equals((Date) mach1[m][0])) {
                            valMass.setTime((Date) mach1[m][1]);
                            waitM = (double) (valMass.getTimeInMillis() - curTime.getTimeInMillis()) / 60 / 60000;
                            waitM = Math.round(waitM * 10) / 10.0;
                            curTime.add(Calendar.MINUTE, (int) (waitM * 60));
                            wait += waitM;

                        }
                    }
                    for (int n = 0; n < Implementation.ROW_MASS; n++) {
                        if (mach2[n][0] == null) break;
                        if (mach2[n][2].equals(2)) {
                            if ((curTime.getTime().after((Date) mach2[n][0]) && curTime.getTime().before((Date) mach2[n][1])) || curTime.getTime().equals((Date) mach2[n][0])) {
                                valMass.setTime((Date) mach2[n][1]);
                                waitN = (double) (valMass.getTimeInMillis() - curTime.getTimeInMillis()) / 60 / 60000;
                                waitN = Math.round(waitN * 10) / 10.0;
                                curTime.add(Calendar.MINUTE, (int) (waitN * 60));
                                wait += waitN;
//                                continue cCheck;
                            }
                        }
                    }
                }
                if (wait != 0) {
                    status = 3; // status 3 - wait
                    array[i][1] = curTime.getTime();
                    array[i][2] = status;
                    array[i][3] = wait;
                    i++;
                    array[i][0] = curTime.getTime();
                }

                rand = getRandomNumb(constRep);
                curTime.add(Calendar.MINUTE, (int) (rand * 60));
                status = 2; // status 2 - repair
                check:
                for (int m = 0; m < Implementation.ROW_MASS; m++) {
                    if (mach1[m][0] == null) break;
                    if (mach1[m][2].equals(2)) {
                        if ((curTime.getTime().after((Date) mach1[m][0]) && curTime.getTime().before((Date) mach1[m][1])) || curTime.getTime().equals((Date) mach1[m][0])) {
                            valMass.setTime((Date) mach1[m][0]);
                            valMassCur.setTime((Date) array[i][0]);
                            rand1 = (double) (valMass.getTimeInMillis() - valMassCur.getTimeInMillis()) / 60 / 60000;
                            rand1 = Math.round(rand1 * 10) / 10.0;
                            rand2 = rand - rand1;
                            rand2 = Math.round(rand2 * 10) / 10.0;
                            curTime.setTime((Date) mach1[m][0]);

                            for (int n = 0; n < Implementation.ROW_MASS; n++) {
                                if (mach2[n][0] == null) break;
                                if (mach2[n][2].equals(2)) {
                                    if ((curTime.getTime().after((Date) mach2[n][0]) && curTime.getTime().before((Date) mach2[n][1])) || curTime.getTime().equals((Date) mach2[n][0]) || curTime.getTime().equals((Date) mach2[n][1])) {
                                        valMass.setTime((Date) mach2[n][0]);
//                                        valMassCur.setTime((Date) array[i][0]);
                                        rand11 = (double) (valMass.getTimeInMillis() - valMassCur.getTimeInMillis()) / 60 / 60000;
                                        rand11 = Math.round(rand11 * 10) / 10.0;
                                        rand12 = (double) rand1 - rand11;
                                        rand12 = Math.round(rand12 * 10) / 10.0;
                                        curTime.setTime((Date) mach2[n][0]);
                                        rand1 = rand11;
                                        rand2 += rand12;
                                    }
                                }
                            }
                            status = 2; // status 2 - repair
                            array[i][1] = curTime.getTime();
                            array[i][2] = status;
                            array[i][3] = rand1;
                            i++;
                            array[i][0] = curTime.getTime();

                            valMass.setTime((Date) mach1[m][1]);
                            wait = (double) (valMass.getTimeInMillis() - curTime.getTimeInMillis()) / 60 / 60000;
                            wait = Math.round(wait * 10) / 10.0;
                            curTime.setTime((Date) mach1[m][1]);

                            for (int n = 0; n < Implementation.ROW_MASS; n++) {
                                if (mach2[n][0] == null) break;
                                if (mach2[n][2].equals(2)) {
                                    if ((curTime.getTime().after((Date) mach2[n][0]) && curTime.getTime().before((Date) mach2[n][1])) || curTime.getTime().equals((Date) mach2[n][0]) || curTime.getTime().equals((Date) mach2[n][1])) {
                                        valMass.setTime((Date) mach2[n][1]);
//                                        valMassCur.setTime((Date) array[i][0]);
                                        waitN = (double) (valMass.getTimeInMillis() - curTime.getTimeInMillis()) / 60 / 60000;
                                        waitN = Math.round(waitN * 10) / 10.0;
                                        wait += waitN;
                                        curTime.setTime((Date) mach2[n][1]);
                                    }
                                }
                            }
                            array[i][1] = curTime.getTime();
                            array[i][2] = 3; // status 3 - wait
                            array[i][3] = wait;
                            i++;
                            array[i][0] = curTime.getTime();
                            rand = rand2;
                            curTime.add(Calendar.MINUTE, (int) (rand2 * 60));
                            continue check;
                        }
                    }
                }
            }
//            curTime.add(Calendar.MINUTE, (int) (rand * 60));
            if (curTime.getTime().after(endTime.getTime()) && status == 1) {
                rand = getDiffRand(rand, curTime, endTime,array[i][0]);
            }
            array[i][1] = curTime.getTime();
            array[i][2] = status;
            array[i][3] = rand;
        }
    }
}

