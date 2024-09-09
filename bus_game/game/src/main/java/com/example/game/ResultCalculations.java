package com.example.game;

import java.util.ArrayList;
import java.util.Collections;

public class ResultCalculations {

    // public static Double[] twoPlayerCalculations(int myBasicPrice, int myQualityPrice, int myAdvertising, int oppBasicPrice, int oppQualityPrice, int oppAdvertising){
    //     double myBasicCustomers = ((double)(oppBasicPrice))/(myBasicPrice) * App.basicCustomers/2;
    //     double myQualityCustomers = ((double)(myAdvertising))/(oppAdvertising) * App.qualityCustomers/2;
    //     // if(App.newEntrantDistruption){
    //     //     myBasicCustomers *= 0.5;
    //     //     myQualityCustomers *= 0.5;
    //     // }
    //     double numBasicRobots = myBasicCustomers/App.robotsMadePerPeriod;
    //     double numQualityRobots = myQualityCustomers/App.robotsMadePerPeriod;
    //     double robotRentalCost = (numBasicRobots+numQualityRobots)*App.robotsCostPerPeriod;
    //     double basicMaterialCost = numBasicRobots * 10 * App.costPerBasicDrone;
    //     double qualityMaterialCost = numQualityRobots * 10 * App.costPerQualityDrone;
    //     double qualityCost = myAdvertising + qualityMaterialCost;
    //     double revenue = (myBasicPrice * numBasicRobots * 10) + (myQualityPrice * numQualityRobots * 10);
    //     double cost = robotRentalCost + basicMaterialCost + qualityCost;
    //     double profit = revenue-cost;
    //     Double[] results = new Double[2];
    //     results[0] = revenue;
    //     results[1] = profit;
    //     return results;
    // }

    public static Double[] multiPlayerCalculations(int myBasicPrice, int myQualityPrice, int myAdvertising, int myBasicDroneRobots, int myQualityDroneRobots, ArrayList<Integer[]> oppDecisions){
        ArrayList<Integer> basicPrices = new ArrayList<Integer>();
        ArrayList<Integer> advertisingSpends = new ArrayList<Integer>();
        basicPrices.add(myBasicPrice);
        advertisingSpends.add(myAdvertising);
        for(Integer[] decision: oppDecisions){
            basicPrices.add(decision[0]);
            advertisingSpends.add(decision[2]);
        }
        Collections.sort(basicPrices);
        Collections.sort(advertisingSpends);
        int sumBasic = 0;
        int sumAdvertising = 0;
        for(int i = 0; i < basicPrices.size(); i++){
            sumBasic += basicPrices.get(i);
            sumAdvertising += advertisingSpends.get(i);
        }

        //calculating the inverses 
        ArrayList<Double> basicPricesInverted = new ArrayList<Double>();
        for(Integer i : basicPrices){
            double inverse = 1/(double) i;
            basicPricesInverted.add(inverse);
        }

        //sum of inverses
        double sumBasicInverses = 0;
        for(int i = 0; i < basicPricesInverted.size(); i++){
            sumBasicInverses += basicPricesInverted.get(i);
        }
        double myInverse = 1/(double) myBasicPrice;
        double myBasicScore = myInverse/sumBasicInverses * sumBasic;


        double myBasicCustomers = App.basicCustomers * myBasicScore / sumBasic;
        double myQualityCustomers = (double) (App.qualityCustomers) * myAdvertising / sumAdvertising;

        myBasicCustomers *= App.newEntrantPercentage/100.0;
        myQualityCustomers *= App.newEntrantPercentage/100.0;
        if(myQualityPrice > 500){
            myQualityCustomers = 0;
        }

        myBasicCustomers = Math.floor(myBasicCustomers * 100) / 100.0;
        myQualityCustomers = Math.floor(myQualityCustomers * 100) / 100.0;
        

        double basicRobotsNeeded = myBasicDroneRobots/App.basicDronesMadePerPeriod;
        double basicDronesAvailable = myBasicDroneRobots + App.lastUserInventory[0];
        double basicRevenues = 0.0;
        if(basicDronesAvailable > myBasicCustomers){
            basicRevenues = myBasicCustomers * myBasicPrice;
        }
        else{
            basicRevenues = basicDronesAvailable * myBasicPrice;
        }

        double basicCosts = basicRobotsNeeded * 200;
        double basicProfits = basicRevenues - basicCosts;

        double qualityRobotsNeeded = myQualityDroneRobots/App.qualityDronesMadePerPeriod;
        double qualityDronesAvailable = myQualityDroneRobots + App.lastUserInventory[1];
        double qualityRevenues = 0.0;
        if(qualityDronesAvailable > myQualityCustomers){
            qualityRevenues = myQualityCustomers * myQualityPrice;
        }
        else{
            qualityRevenues = qualityDronesAvailable * myQualityPrice;
        }

        double qualityCosts = qualityRobotsNeeded * 200 + myAdvertising;
        double qualityProfits = qualityRevenues - qualityCosts;

        double profit = basicProfits + qualityProfits;
        double revenue = basicRevenues + qualityRevenues;

        basicRevenues = Math.floor(basicRevenues * 100) / 100.0;
        basicProfits = Math.floor(basicProfits * 100) / 100.0;
        qualityRevenues = Math.floor(qualityRevenues * 100) / 100.0;
        qualityProfits = Math.floor(qualityProfits * 100) / 100.0;

        // System.out.println("Basic Customers: " + myBasicCustomers);
        // System.out.println("Quality Customers: " + myQualityCustomers);
        // System.out.println("Basic Revenue: " + basicRevenues);
        // System.out.println("Quality Revenue: " + qualityRevenues);
        // System.out.println("Basic Cost: " + basicCosts);
        // System.out.println("Quality Cost: " + qualityCosts);
        // System.out.println("Basic Profits: " + basicProfits);
        // System.out.println("Quality Profits: " + qualityProfits);
        // System.out.println("Basic Drones Available: " + basicDronesAvailable);
        // System.out.println("Quality Drones Available: " + qualityDronesAvailable);
        // System.out.println("Basic Drones Inventory: " + App.lastUserInventory[0]);
        // System.out.println("Quality Drones Inventory: " + App.lastUserInventory[1]);

        Double[] results = new Double[2];
        revenue = Math.floor(revenue * 100) / 100.0;
        profit = Math.floor(profit * 100) / 100.0;
        results[0] = revenue;
        results[1] = profit;
        return results;
    }

    public static Double[] multiPlayerExtraCalculations(int myBasicPrice, int myQualityPrice, int myAdvertising, int myBasicDroneRobots, int myQualityDroneRobots, ArrayList<Integer[]> oppDecisions){
        ArrayList<Integer> basicPrices = new ArrayList<Integer>();
        ArrayList<Integer> advertisingSpends = new ArrayList<Integer>();
        basicPrices.add(myBasicPrice);
        advertisingSpends.add(myAdvertising);
        for(Integer[] decision: oppDecisions){
            basicPrices.add(decision[0]);
            advertisingSpends.add(decision[2]);
        }
        Collections.sort(basicPrices);
        Collections.sort(advertisingSpends);
        int sumBasic = 0;
        int sumAdvertising = 0;
        for(int i = 0; i < basicPrices.size(); i++){
            sumBasic += basicPrices.get(i);
            sumAdvertising += advertisingSpends.get(i);
        }

        //calculating the inverses 
        ArrayList<Double> basicPricesInverted = new ArrayList<Double>();
        for(Integer i : basicPrices){
            double inverse = 1/(double) i;
            basicPricesInverted.add(inverse);
        }

        //sum of inverses
        double sumBasicInverses = 0;
        for(int i = 0; i < basicPricesInverted.size(); i++){
            sumBasicInverses += basicPricesInverted.get(i);
        }
        double myInverse = 1/(double) myBasicPrice;
        double myBasicScore = myInverse/sumBasicInverses * sumBasic;


        double myBasicCustomers = App.basicCustomers * myBasicScore / sumBasic;
        double myQualityCustomers = (double) (App.qualityCustomers) * myAdvertising / sumAdvertising;

        myBasicCustomers *= App.newEntrantPercentage/100.0;
        myQualityCustomers *= App.newEntrantPercentage/100.0;
        if(myQualityPrice > 500){
            myQualityCustomers = 0;
        }

        myBasicCustomers = Math.floor(myBasicCustomers * 100) / 100.0;
        myQualityCustomers = Math.floor(myQualityCustomers * 100) / 100.0;
        

        double basicRobotsNeeded = myBasicDroneRobots/App.basicDronesMadePerPeriod;
        double basicDronesAvailable = myBasicDroneRobots + App.lastUserInventory[0];
        double basicRevenues = 0.0;
        if(basicDronesAvailable > myBasicCustomers){
            basicRevenues = myBasicCustomers * myBasicPrice;
        }
        else{
            basicRevenues = basicDronesAvailable * myBasicPrice;
        }

        double basicCosts = basicRobotsNeeded * 200;
        double basicProfits = basicRevenues - basicCosts;

        double qualityRobotsNeeded = myQualityDroneRobots/App.qualityDronesMadePerPeriod;
        double qualityDronesAvailable = myQualityDroneRobots + App.lastUserInventory[1];
        double qualityRevenues = 0.0;
        if(qualityDronesAvailable > myQualityCustomers){
            qualityRevenues = myQualityCustomers * myQualityPrice;
        }
        else{
            qualityRevenues = qualityDronesAvailable * myQualityPrice;
        }

        double qualityCosts = qualityRobotsNeeded * 200 + myAdvertising;
        double qualityProfits = qualityRevenues - qualityCosts;

        double profit = basicProfits + qualityProfits;
        double revenue = basicRevenues + qualityRevenues;

        basicRevenues = Math.floor(basicRevenues * 100) / 100.0;
        basicProfits = Math.floor(basicProfits * 100) / 100.0;
        qualityRevenues = Math.floor(qualityRevenues * 100) / 100.0;
        qualityProfits = Math.floor(qualityProfits * 100) / 100.0;

        // System.out.println("Basic Customers: " + myBasicCustomers);
        // System.out.println("Quality Customers: " + myQualityCustomers);
        // System.out.println("Basic Revenue: " + basicRevenues);
        // System.out.println("Quality Revenue: " + qualityRevenues);
        // System.out.println("Basic Cost: " + basicCosts);
        // System.out.println("Quality Cost: " + qualityCosts);
        // System.out.println("Basic Profits: " + basicProfits);
        // System.out.println("Quality Profits: " + qualityProfits);
        // System.out.println("Basic Drones Available: " + basicDronesAvailable);
        // System.out.println("Quality Drones Available: " + qualityDronesAvailable);
        // System.out.println("Basic Drones Inventory: " + App.lastUserInventory[0]);
        // System.out.println("Quality Drones Inventory: " + App.lastUserInventory[1]);

        Double[] results = new Double[6];
        revenue = Math.floor(revenue * 100) / 100.0;
        profit = Math.floor(profit * 100) / 100.0;
        results[0] = revenue;
        results[1] = profit;
        results[2] = basicRevenues;
        results[3] = basicProfits;
        results[4] = qualityRevenues;
        results[5] = qualityProfits;
        return results;
    }


    public static Double[] multiPlayerCustomerCalculations(int myBasicPrice, int myQualityPrice, int myAdvertising, int myBasicDroneRobots, int myQualityDroneRobots, ArrayList<Integer[]> oppDecisions){
        ArrayList<Integer> basicPrices = new ArrayList<Integer>();
        ArrayList<Integer> advertisingSpends = new ArrayList<Integer>();
        basicPrices.add(myBasicPrice);
        advertisingSpends.add(myAdvertising);
        for(Integer[] decision: oppDecisions){
            basicPrices.add(decision[0]);
            advertisingSpends.add(decision[2]);
        }
        Collections.sort(basicPrices);
        Collections.sort(advertisingSpends);
        int sumBasic = 0;
        int sumAdvertising = 0;
        for(int i = 0; i < basicPrices.size(); i++){
            sumBasic += basicPrices.get(i);
            sumAdvertising += advertisingSpends.get(i);
        }

        //calculating the inverses 
        ArrayList<Double> basicPricesInverted = new ArrayList<Double>();
        for(Integer i : basicPrices){
            double inverse = 1/(double) i;
            basicPricesInverted.add(inverse);
        }

        //sum of inverses
        double sumBasicInverses = 0;
        for(int i = 0; i < basicPricesInverted.size(); i++){
            sumBasicInverses += basicPricesInverted.get(i);
        }
        double myInverse = 1/(double) myBasicPrice;
        double myBasicScore = myInverse/sumBasicInverses * sumBasic;


        double myBasicCustomers = App.basicCustomers * myBasicScore / sumBasic;
        double myQualityCustomers = (double) (App.qualityCustomers) * myAdvertising / sumAdvertising;

        if(myQualityPrice > 500){
            myQualityCustomers = 0;
        }

        
        myBasicCustomers *= App.newEntrantPercentage/100.0;
        myQualityCustomers *= App.newEntrantPercentage/100.0;
        

        myBasicCustomers = Math.floor(myBasicCustomers * 100) / 100.0;
        myQualityCustomers = Math.floor(myQualityCustomers * 100) / 100.0;
      
        Double [] customers = {myBasicCustomers, myQualityCustomers};
        return customers;
    }
}