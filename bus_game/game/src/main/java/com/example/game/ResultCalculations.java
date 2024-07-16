package com.example.game;

public class ResultCalculations {

    public static Double[] twoPlayerCalculations(int myBasicPrice, int myQualityPrice, int myAdvertising, int oppBasicPrice, int oppQualityPrice, int oppAdvertising){
        double myBasicCustomers = ((double)(oppBasicPrice))/myBasicPrice * 100;
        double myQualityCustomers = ((double)(myAdvertising))/oppAdvertising * 100;
        if(App.newEntrantDistruption){
            myBasicCustomers *= 0.5;
            myQualityCustomers *= 0.5;
        }
        double numBasicRobots = myBasicCustomers/App.robotsMadePerPeriod;
        double numQualityRobots = myQualityCustomers/App.robotsMadePerPeriod;
        double robotRentalCost = (numBasicRobots+numQualityRobots)*App.robotsCostPerPeriod;
        double basicMaterialCost = numBasicRobots * 10 * App.costPerBasicDrone;
        double qualityMaterialCost = numQualityRobots * 10 * App.costPerQualityDrone;
        double qualityCost = myAdvertising + qualityMaterialCost;
        double revenue = (myBasicPrice * numBasicRobots * 10) + (myQualityPrice * numQualityRobots * 10);
        double cost = robotRentalCost + basicMaterialCost + qualityCost;
        double profit = revenue-cost;
        Double[] results = new Double[2];
        results[0] = revenue;
        results[1] = profit;
        return results;
    }
}