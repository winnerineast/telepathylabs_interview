import models.Feature;
import models.Plan;
import models.Plans;

import java.util.*;

public class Solutioner {
    public ArrayList<Plan[]> findCombinationOfPlansWantedbyUsers(Plan[] allPlans, Feature[] selectedFeatures) {
        ArrayList<Plan[]> resultPlanCombinationList = new ArrayList<>();
        ArrayList<Plan[]> potentialCombinationPlanList = new ArrayList<>();

        for (Plan aPlan : allPlans) {
            aPlan.parseSelectedFeatures(selectedFeatures);
            Plan[] tmpPlanList = new Plan[1];
            tmpPlanList[0] = aPlan;
            //Set A: Single plan to fulfil the purpose.
            if (aPlan.selectedFeatures.length == selectedFeatures.length) 
                resultPlanCombinationList.add(tmpPlanList);
            else //Set B: combination plans to fulfil the purpose.
            if (aPlan.selectedFeatures.length > 0){
                potentialCombinationPlanList.add(tmpPlanList);
            }
        }

        // find out all possible combinations of candidate plans.
        Plan[] tmpPlanList = new Plan[potentialCombinationPlanList.size()];
        int i=0;
        for (Plan[] p : potentialCombinationPlanList)
            tmpPlanList[i++] = p[0];

        for (Plan[] plist : this.getAllCombinationPlans(tmpPlanList)){
            HashMap<String, Plan> masks = new HashMap<>();
            for (Feature f : selectedFeatures)
                masks.put(f.Name, null);

            for (Plan p : plist){
                for (Feature f : p.selectedFeatures){
                    if(masks.containsKey(f.Name))
                        masks.put(f.Name, p);
                }
            }
            if(!masks.containsValue(null))
                resultPlanCombinationList.add(plist);
        }

        // !!!! Before return the result list, there is something to be discussed.
        // If you think "all possible combinations" mean "all" combinations in the manner of Math,
        // resultPlanCombinationList is right answer.
        // In real life, some of combinations are wired such as
        // Name:[P7],Cost:[34.0],Features:[F1,G1] Name:[P8],Cost:[39.0],Features:[F1,H1] Name:[P12],Cost:[25.0],Features:[E1,G1,H1]
        // So I decided to add a process to remove all wired combinations.
        resultPlanCombinationList = this.trimResultList(resultPlanCombinationList, selectedFeatures.length);

        return resultPlanCombinationList;
    }

    private ArrayList<Plan[]> trimResultList(ArrayList<Plan[]> resultPlanCombinationList, int iSelectedFeatures) {
        ArrayList<Plan[]> finalList = new ArrayList<>();

        for (Plan[] pList : resultPlanCombinationList){
            int iActualNoofSelectedFeatures = 0;
            for (Plan p : pList){
                iActualNoofSelectedFeatures += p.selectedFeatures.length;
            }
            // that means you have overlapping features in some plans.
            if (iActualNoofSelectedFeatures == iSelectedFeatures)
                finalList.add(pList);
        }
        return finalList;
    }

    public void printPlans(ArrayList<Plan[]> listPlanList){
        for (Plan[] planList : listPlanList)
            for (Plan aPlan : planList) {
                System.out.print(aPlan.Name + ":" + Double.toString(aPlan.Cost) + ":");
                for (Feature f: aPlan.Features)
                    System.out.print(f.Name+",");
                System.out.println();
            }
    }


    public ArrayList<Plans> selectCheapestPlans(ArrayList<Plan[]> resultPlans) {
        ArrayList<Plans> tmpList = new ArrayList<>();

        for (Plan[] p : resultPlans)
            tmpList.add(new Plans(p));

        tmpList.sort(new Comparator<Plans>() {
            public int compare(Plans c1, Plans c2) {
                if (c1.totalCost > c2.totalCost)
                    return 1;
                else if (c1.totalCost < c2.totalCost)
                    return -1;
                else
                    return 0;
            }
        });

        double stopCost = tmpList.get(0).totalCost;
        int iStopIndex = tmpList.size();
        for (int i=0; i < tmpList.size(); i++)
            if(stopCost < tmpList.get(i).totalCost) {
                iStopIndex = i;
                break;
            }

        ArrayList<Plans> subList = new ArrayList<Plans>(tmpList.subList(0,iStopIndex));

        return subList;
    }

    private ArrayList<Plan[]> getAllCombinationPlans(Plan[] planList) {
        int nCnt = planList.length;
        ArrayList<Plan[]> resultList = new ArrayList<>();

        //int nBit = (0xFFFFFFFF >>> (32 - nCnt));
        int nBit = 1<<nCnt;
        for (int i = 1; i <= nBit; i++) {
            ArrayList<Plan> tmpList = new ArrayList<>();
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    tmpList.add(planList[j]);
                    //System.out.print(planList[j].toString()+" ");
                }
            }

            Plan[] tmpPlanArray = new Plan[tmpList.size()];
            tmpList.toArray(tmpPlanArray);
            resultList.add(tmpPlanArray);
            //System.out.println();
        }
        return resultList;
    }
}
