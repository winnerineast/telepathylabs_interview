import models.Feature;
import models.Node;
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

    public Node getTree() {
        Node root = new Node("root");
        Node n11 = new Node("n11");
        Node n12 = new Node("n12");
        Node n13 = new Node("n13");
        Node n21 = new Node("n21");
        Node n22 = new Node("n22");
        Node n23 = new Node("n23");

        root.Children = new Node[3];
        root.Children[0] = n11;
        root.Children[1] = n12;
        root.Children[2] = n13;

        n11.Children = new Node[2];
        n11.Children[0] = n21;
        n11.Children[1] = n22;

        n12.Children = null;

        n13.Children = new Node[1];
        n13.Children[0] = n23;

        n21.Children = null;

        n22.Children = null;

        n23.Children = null;

        return root;
    }
}
