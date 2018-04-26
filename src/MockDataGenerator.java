import models.Feature;
import models.Plan;

public class MockDataGenerator {

    public Plan[] getMockPlans() {
        Plan[] planList = new Plan[14];

        planList[0] = new Plan("P0",10.0, "E1");
        planList[1] = new Plan("P1",15.0, "F1");
        planList[2] = new Plan("P2",20.0, "G1");
        planList[3] = new Plan("P3",25.0, "H1");
        planList[4] = new Plan("P4",24.0, "E1,F1");
        planList[5] = new Plan("P5",29.0, "E1,G1");
        planList[6] = new Plan("P6",34.0, "E1,H1");
        planList[7] = new Plan("P7",34.0, "F1,G1");
        planList[8] = new Plan("P8",39.0, "F1,H1");
        planList[9] = new Plan("P9",34.0, "G1,H1");
        planList[10] = new Plan("P10",24.0, "E1,F1,G1");
        planList[11] = new Plan("P11",29.0, "E1,F1,H1");
        planList[12] = new Plan("P12",25.0, "E1,G1,H1");
        planList[13] = new Plan("P13",10.0, "E1,F1,G1,H1");
        return planList;
    }

    public Feature[] getSelectedFeatures() {
        Feature[] featureList = new Feature[2];
        featureList[0] = new Feature("E1");
        featureList[1] = new Feature("F1");
        return featureList;
    }
}
