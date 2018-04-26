import java.util.ArrayList;
import java.util.Arrays;
import models.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Question 1 - Finding the best plan:\n" +
                "             Let's say a cloud service offers different plans. For example, Office 365 has different plans called E1, F1\n" +
                "             , etc. Each plan has different features such as voice, email, archiving, etc. Now let say the user selects a\n" +
                "             set of features he/she wants. The goal is to write code in Java that finds the combination of plans that\n" +
                "             offers all selected features at the lowest price. Note that in some cases, it will be just one plan, but\n" +
                "             in other cases you will need 2 or more plans to get all the features you want.\n" +
                "To get you started:\n" +
                "class Feature { public string Name; }\n" +
                "             \t\tclass Plan { public string Name; public double Cost; public Feature[] Features; }\n" +
                "            \t\tPlan[] allPlans;            // this is the list of plans available instantiated as per the above\n" +
                "Feature[] selectedFeatures;       // this is the list of features the user wants -> find combinations of 1-N plans that\n" +
                "                                  // fulfil those features -> select the cheapest combination(s).");

        System.out.println("=================================================================");
        System.out.println("Solution:");
        System.out.println("Create mock data generator to prepare all data used for testing.");
        MockDataGenerator mockDataGenerator = new MockDataGenerator();

        System.out.println("Mock all plan with random values.");
        Plan[] allPlans = mockDataGenerator.getMockPlans();
        for (Plan aPlan : allPlans)
            System.out.println(aPlan.toString());

        System.out.println("Mock selected features.");
        Feature[] selectedFeatures = mockDataGenerator.getSelectedFeatures();
        for (Feature aFeature : selectedFeatures){
            System.out.print(aFeature.Name+" ");
        }
        System.out.println();

        System.out.println("find combinations of 1-N plans that fulfil those features");
        Solutioner solutioner = new Solutioner();
        ArrayList<Plan[]> resultPlans = solutioner.findCombinationOfPlansWantedbyUsers(allPlans, selectedFeatures);
        for (Plan[] aPlan : resultPlans) {
            int i=0;
            for (Plan p : aPlan) {
                if(i==(aPlan.length-1))
                    System.out.print(p.toString());
                else
                    System.out.print(p.toString()+"   ");
            }
            System.out.println();
        }

        System.out.println("select the cheapest combination(s).");
        ArrayList<Plans> cheapestPlans = solutioner.selectCheapestPlans(resultPlans);

        for (Plans aPlan : cheapestPlans){
            Plan[] aPlanList = Arrays.copyOf(aPlan.plans, aPlan.plans.length);
            for (Plan ap : aPlanList)
                System.out.print(ap.toString()+":");
            System.out.println();
        }
    }
}
