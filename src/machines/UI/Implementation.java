package machines.UI;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import machines.model.Excavator;
import machines.model.Machine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nade_ on 28.11.2015.
 */
public class Implementation {

    public static final String PARAM = "E://Nade//FileDiplomaJava//Java_ImModel_Proj//inputParam.txt";

//    public static final double P = readFromTheFile(PARAM,0);

    public static final double M_WORK_BULD = readFromTheFile(PARAM,0);
    public static final double M_WORK_EXC = readFromTheFile(PARAM,1);
    public static final double M_REP_EXC_S3S6 = readFromTheFile(PARAM,2);
    public static final double M_REP_EXC_S6 = readFromTheFile(PARAM,3);
    public static final double M_REP_BULD_S3S6 = readFromTheFile(PARAM,4);
    public static final double M_REP_BULD_S6 = readFromTheFile(PARAM,5);

    public static final int COST_INACTIV_EXC = (int) readFromTheFile(PARAM,6);
    public static final int COST_INACTIV_BULD = (int) readFromTheFile(PARAM,7);
    public static final int INCOME_EXC = (int) readFromTheFile(PARAM,8);
    public static final int INCOME_BULD = (int) readFromTheFile(PARAM,9);
    public static final int SALARY_S3 = (int) readFromTheFile(PARAM,10);
    public static final int SALARY_S6 = (int) readFromTheFile(PARAM,11);

    public static final int LABOR_HOURS = (int) readFromTheFile(PARAM,12);

    public static final int ST_LABOR_HH = (int) readFromTheFile(PARAM,13);
    public static final int ST_LABOR_MM = (int) readFromTheFile(PARAM,14);

    public static final int DAYS = (int) readFromTheFile(PARAM,15);
    public static final int DAY_TO_PRINT = (int) readFromTheFile(PARAM,16);

    /*public static final double M_WORK_BULD = 6;
    public static final double M_WORK_EXC = 4;
    public static final double M_REP_EXC_S3S6 = 0.25;
    public static final double M_REP_EXC_S6 = 1;
    public static final double M_REP_BULD_S3S6 = 1.5;
    public static final double M_REP_BULD_S6 = 2;

    public static final int COST_INACTIV_EXC = 500;
    public static final int COST_INACTIV_BULD = 300;
    public static final int INCOME_EXC = 500;
    public static final int INCOME_BULD = 300;
    public static final int SALARY_S3 = 60;
    public static final int SALARY_S6 = 100;


    public static final double LABOR_HOURS = 8.0;

    public static final int ST_LABOR_HH = 6;
    public static final int ST_LABOR_MM = 0;

    public static final int DAYS = 100;
    public static final int DAY_TO_PRINT = 10;*/

    public static final int ROW_MASS = 50;
    public static final int COLUMN_MASS = 4;

    private static int totalIncome;
    private static int totalIncomeDay;
    private static int totalCost;
    private static int totalCostDay;
    private static int salary;
    private static int salaryDay;
    private static int profitS6;
    private static int profitS6S3;
    private static int profitDay;
    private static double sumRep;
    private static double tSumRep;
    private static double sumWork;
    private static double tSumWork;
    private static double sumWait;
    private static double tSumWait;



    public static void main(String[] args) throws ParseException, IOException {

        GregorianCalendar endTime = new GregorianCalendar(0, 0, 0, ST_LABOR_HH + (LABOR_HOURS * 2), ST_LABOR_MM, 0);

//        Для S6 +S3
        totalIncome =0;
        totalCost =0;
        tSumRep=0;
        tSumWork=0;
        tSumWait=0;

        for (int k = 0; k < DAYS; k++) {
            Machine bulldozer = new Machine();
            Excavator excavator1 = new Excavator();
            Excavator excavator2 = new Excavator();

            bulldozer.fillArray(bulldozer.massif, bulldozer.curTime, endTime,M_WORK_BULD,M_REP_BULD_S3S6);
            calculatParameters(bulldozer,INCOME_BULD,COST_INACTIV_BULD);

            excavator1.fillArrayEx(excavator1.massif, excavator1.curTime, endTime,bulldozer.massif, M_WORK_EXC, M_REP_EXC_S3S6);
            calculatParameters(excavator1,INCOME_EXC ,COST_INACTIV_EXC);

            excavator2.fillArrayEx2(excavator2.massif, excavator2.curTime, endTime,bulldozer.massif,excavator1.massif, M_WORK_EXC, M_REP_EXC_S3S6);
            calculatParameters(excavator2,INCOME_EXC ,COST_INACTIV_EXC);

            totalIncomeDay = bulldozer.income + excavator1.income + excavator2.income;
            sumRep = Math.round((bulldozer.sumRep + excavator1.sumRep + excavator2.sumRep) * 10) / 10.0;
            sumWork = Math.round((bulldozer.sumWork + excavator1.sumWork + excavator2.sumWork) * 10) / 10.0;
            sumWait = Math.round((bulldozer.sumWait  + excavator1.sumWait + excavator2.sumWait) * 10) / 10.0;
            salaryDay = (int) (SALARY_S3 * sumRep + SALARY_S6 * sumRep);
            totalCostDay = salaryDay + bulldozer.cost + excavator1.cost  + excavator2.cost ;
            profitDay = totalIncomeDay - totalCostDay;

            totalIncome += totalIncomeDay;
            totalCost += totalCostDay;
            profitS6S3 += profitDay;
            tSumWork += sumWork;
            tSumWait += sumWait;
            tSumRep += sumRep;

            if (k == DAY_TO_PRINT) {
                System.out.println("S6+S3");
                printDay(bulldozer, excavator1, excavator2,DAY_TO_PRINT);
            }


        }
        totalIncome = totalIncome / DAYS;
        totalCost = totalCost / DAYS;
        profitS6S3 = profitS6S3 / DAYS;
        tSumWork = Math.round((tSumWork / DAYS) * 10) / 10.0;
        tSumWait = Math.round((tSumWait / DAYS) * 10) / 10.0;
        tSumRep = Math.round((tSumRep / DAYS) * 10) / 10.0;

        System.out.println("\nFor " + DAYS + " days:");
        System.out.println("Суммарное время работы: " + translateDuring((double)tSumWork)
                +"\nСуммарное время ожидания: " + translateDuring((double)tSumWait)
                +"\nСуммарное время ремонта: " + translateDuring((double)tSumRep)
                +"\nДоходы: " + totalIncome
                +"\nЗатраты : " + totalCost
                +"\nЧистая прибыль: " + profitS6S3);

//        Для S6
        totalIncome =0;
        totalCost =0;
        tSumRep=0;
        tSumWork=0;
        tSumWait=0;

        for (int k = 0; k < DAYS; k++) {
            Machine bulldozer = new Machine();
            Excavator excavator1 = new Excavator();
            Excavator excavator2 = new Excavator();

            bulldozer.fillArray(bulldozer.massif, bulldozer.curTime, endTime,M_WORK_BULD,M_REP_BULD_S6);
            calculatParameters(bulldozer,INCOME_BULD,COST_INACTIV_BULD);

            excavator1.fillArrayEx(excavator1.massif, excavator1.curTime, endTime,bulldozer.massif, M_WORK_EXC, M_REP_EXC_S6);
            calculatParameters(excavator1,INCOME_EXC ,COST_INACTIV_EXC);

            excavator2.fillArrayEx2(excavator2.massif, excavator2.curTime, endTime,bulldozer.massif,excavator1.massif, M_WORK_EXC, M_REP_EXC_S6);
            calculatParameters(excavator2,INCOME_EXC ,COST_INACTIV_EXC);

            totalIncomeDay = bulldozer.income + excavator1.income + excavator2.income;
            sumRep = Math.round((bulldozer.sumRep + excavator1.sumRep + excavator2.sumRep) * 10) / 10.0;
            sumWork = Math.round((bulldozer.sumWork + excavator1.sumWork + excavator2.sumWork) * 10) / 10.0;
            sumWait = Math.round((bulldozer.sumWait  + excavator1.sumWait + excavator2.sumWait) * 10) / 10.0;
            salaryDay = (int) (SALARY_S6 * sumRep);
            totalCostDay = salaryDay + bulldozer.cost + excavator1.cost  + excavator2.cost ;
            profitDay = totalIncomeDay - totalCostDay;

            totalIncome += totalIncomeDay;
            totalCost += totalCostDay;
            profitS6 += profitDay;
            tSumWork += sumWork;
            tSumWait += sumWait;
            tSumRep += sumRep;

            if (k == DAY_TO_PRINT) {
                System.out.println("\nS6");
                printDay(bulldozer,excavator1,excavator2,DAY_TO_PRINT);
            }


        }
        totalIncome = totalIncome / DAYS;
        totalCost = totalCost / DAYS;
        profitS6 = profitS6 / DAYS;
        tSumWork = Math.round((tSumWork / DAYS) * 10) / 10.0;
        tSumWait = Math.round((tSumWait / DAYS) * 10) / 10.0;
        tSumRep = Math.round((tSumRep / DAYS) * 10) / 10.0;

        System.out.println("\nFor " + DAYS + " days:");
        System.out.println("Суммарное время работы: " + translateDuring((double)tSumWork)
                +"\nСуммарное время ожидания: " + translateDuring((double)tSumWait)
                +"\nСуммарное время ремонта: " + translateDuring((double)tSumRep)
                +"\nДоходы: " + totalIncome
                +"\nЗатраты : " + totalCost
                +"\nЧистая прибыль: " + profitS6);

        if (profitS6 >= profitS6S3) {
            System.out.println("\nУволить слесаря 3-го разряда");
        }
        else System.out.println("\nНе увольнять слесаря 3-го разряда");



    }

    public static double readFromTheFile(String fileName,int i) {

        try {
            ArrayList<ArrayList> fileTable = new ArrayList();
            fileLoading(fileName, fileTable,i);

            double p = Double.parseDouble( (String) fileTable.get(i).get(0));

            return p;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static ArrayList<ArrayList> fileLoading(String fileName, ArrayList<ArrayList> fileTable,int i) throws IOException {

        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        StringTokenizer tokens;
        {
            String line;
            while ((line = fileReader.readLine()) != null) {
                ArrayList tableLine = new ArrayList();
                tokens = new StringTokenizer(line, "-");

                while (tokens.hasMoreTokens()) {
                    tableLine.add(tokens.nextToken());
                }

                fileTable.add(tableLine);
            }
        }
//        for (int i = 0; i < fileTable.size(); i++) {
//                System.out.print(fileTable.get(i).get(0) + " ");
//            System.out.println("");
//        }
        return fileTable;
    }

    private static void calculatParameters(Machine machine, int incomConst, int costInst){
        machine.sumWork = Machine.sumValueArray(machine.massif,machine.sumWork,1);
        machine.sumRep = Machine.sumValueArray(machine.massif,machine.sumRep,2);
        machine.sumWait = Machine.sumValueArray(machine.massif,machine.sumWait,3);
        machine.income = (int) (incomConst * machine.sumWork);
        machine.cost = (int)(costInst * (machine.sumRep + machine.sumWait));
    }

    private static void printArray(Object[][] array) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String st = null;
        for (int i = 0; i < ROW_MASS; i++) {

            for (int j = 0; j < COLUMN_MASS - 2; j++) {
                if (array[i][j] == null) {
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
//            System.out.println(" (" + array[i][3] + ")");
            System.out.println(" (" + translateDuring((double)array[i][3]) + ")");
        }
    }

    public static String  translateDuring(double number){
        int numberMin;
        int res = (int)number; //целая часть
//        double res2 = number - res; //дробная часть

            numberMin = (int) (number * 60);
        int res2 = numberMin - 60*res;
            return (res + "," + res2);
    }

    private static void printDay(Machine bulldozer,Excavator excavator1,Excavator excavator2,int DAY_TO_PRINT){
            System.out.println(DAY_TO_PRINT + " day:");
            System.out.println("BULLDOZER: ");
            printArray(bulldozer.massif);

            System.out.println("EXCAVATOR1: ");
            printArray(excavator1.massif);

            System.out.println("EXCAVATOR2: ");
            printArray(excavator2.massif);

            System.out.println("\nBULLDOZER: "
                    + "\nРабота: " + translateDuring((double)bulldozer.sumWork)
                    + "\nРемонт: " + translateDuring((double)bulldozer.sumRep)
                    + "\nОжидание: " + translateDuring((double)bulldozer.sumWait)
                    + "\nДоходы: " + bulldozer.income
                    + "\nЗатраты: " + bulldozer.cost);
            System.out.println("\nEXCAVATOR1: "
                    + "\nРабота: " + translateDuring((double)excavator1.sumWork)
                    + "\nРемонт: " + translateDuring((double)excavator1.sumRep)
                    + "\nОжидание: " + translateDuring((double)excavator1.sumWait)
                    + "\nДоходы: " + excavator1.income
                    + "\nЗатраты: " + excavator1.cost);
            System.out.println("\nEXCAVATOR2: "
                    + "\nРабота: " + translateDuring((double)excavator2.sumWork)
                    + "\nРемонт: " + translateDuring((double)excavator2.sumRep)
                    + "\nОжидание: " + translateDuring((double)excavator2.sumWait)
                    + "\nДоходы: " + excavator2.income
                    + "\nЗатраты: " + excavator2.cost);
            System.out.println("\nСуммарное время работы: " + translateDuring((double)sumWork)
                    +"\nСуммарное время ожидания: " + translateDuring((double)sumWait)
                    +"\nСуммарное время ремонта: " + translateDuring((double)sumRep)
                    +"\nДоходы за день: " + totalIncomeDay
                    +"\nЗатраты за день: " + totalCostDay
                    +"\nЧистая прибыль за день: " + profitDay);

        }


}





