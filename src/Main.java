import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

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
        for (Feature aFeature : selectedFeatures) {
            System.out.print(aFeature.Name + " ");
        }
        System.out.println();

        System.out.println("find combinations of 1-N plans that fulfil those features");
        Solutioner solutioner = new Solutioner();
        ArrayList<Plan[]> resultPlans = solutioner.findCombinationOfPlansWantedbyUsers(allPlans, selectedFeatures);
        for (Plan[] aPlan : resultPlans) {
            int i = 0;
            for (Plan p : aPlan) {
                if (i == (aPlan.length - 1))
                    System.out.print(p.toString());
                else
                    System.out.print(p.toString() + "   ");
            }
            System.out.println();
        }

        System.out.println("select the cheapest combination(s).");
        ArrayList<Plans> cheapestPlans = solutioner.selectCheapestPlans(resultPlans);

        for (Plans aPlan : cheapestPlans) {
            Plan[] aPlanList = Arrays.copyOf(aPlan.plans, aPlan.plans.length);
            for (Plan ap : aPlanList)
                System.out.print(ap.toString() + ":");
            System.out.println();
        }

        //!!! To be frankly I spent some time to understand "fully populated tree" because the sample tree looks not like
        // a fully populated tree I believe. And I saw both binary and trinary nodes in sample tree, so I wondered that
        // if I should consider of more high-nary nodes scenario, but I don't have much time to work on this topic, hence
        // I make a lite solution for time being.
        System.out.println("Question2 Each Node represents an element of a tree and specifies a list of immediate children. " +
                "The 'Children' property lists all children (in order) but the 'Right' property is set to null. Suppose " +
                "you are given the root of a fully populated tree (i.e. a Node instance called rootNode). Write code in " +
                "Java to set the 'Right' property so that each node is linked to right siblings without using a queue " +
                "or stack (do not use recursive calls). Make sure to test your code with the sample tree below.  \n" +
                "To get you started:\n" +
                "public class Node\n" +
                "{\n" +
                "                public Node[] Children;\n" +
                "                public Node Right;\n" +
                "}\n" +
                "Node rootNode;\n");
        System.out.println("=================================================================");
        System.out.println("Solution:");
        Node nTree = mockDataGenerator.getTree();

        // Scan through the tree and mark each node's (x,y).
        if (nTree.Children == null){
            System.out.println("This is a seed instead of tree");
            return;
        }

        Node nParentNode = nTree;
        Node nCurrentNode = nTree;
        nCurrentNode.iX = 1;
        nCurrentNode.iY = 0;
        Node nNextRightNode = null;
        ArrayList<Node>  listNodes = new ArrayList<>();
        HashMap<XnY, Node> listXnYNodes = new HashMap<>();
        int iMaxX = 0;
        int iMaxY = 0;

        //identify the next root which is nearest right of current's children[0] if it exists.
        while (nCurrentNode != null) {
            if (nCurrentNode.Children != null) {
                if (nCurrentNode.Children.length >= 2) {
                    nNextRightNode = nCurrentNode.Children[1];
                    nParentNode = nCurrentNode;
                    break;
                } else {
                    nCurrentNode = nCurrentNode.Children[0];
                    continue; // we need to continue in case there are another two + children nodes in next level node.
                }
            } else
                break;// we could trust such break because this is a fully populated tree, the depth of left and deep
                      // iteration is the same as the depth of the tree.
        }// end - while

        //reset current root.
        nCurrentNode = nTree;
        listNodes.add(nCurrentNode);
        while (nCurrentNode != null) {
            if (nCurrentNode.Children != null) {
                int i = nCurrentNode.iX; //it's not started from 1 always.
                for (Node n : nCurrentNode.Children) {
                    n.iX = i++;
                    n.iY = nCurrentNode.iY + 1;

                    if (n.iX > iMaxX)
                        iMaxX = n.iX;
                    if (n.iY > iMaxY)
                        iMaxY = n.iY;

                    listNodes.add(n);
                    XnY xny = new XnY(n.iX, n.iY);
                    listXnYNodes.put(xny, n);
                }
                nCurrentNode = nCurrentNode.Children[0];
            } else {//if it runs through the left and deep iteration, we will start to move current root to the right
                if (nNextRightNode == null)
                    break;

                //Set next right node as new root and run through the same process
                nCurrentNode = nNextRightNode;
                if(nCurrentNode.iX < nParentNode.Children.length ) {
                    nNextRightNode = nParentNode.Children[nCurrentNode.iX];
                }
                else
                    nNextRightNode = null;
            }
        }

        for (int i=1; i <= iMaxY; i++) {
            for (int j = 1; j <= iMaxX; j++) {
                //run through the tree level by level from left to right.
                XnY xny = new XnY(j, i);
                XnY xnyRight = new XnY(j+1, i);
                XnY currentXnY = containsKey(xny, listXnYNodes.keySet());
                XnY currentXnYRight = containsKey(xnyRight, listXnYNodes.keySet());

                if (currentXnY!=null){
                    if(currentXnYRight!=null){
                        Node nCurNode = listXnYNodes.get(currentXnY);
                        Node nCurRightNode = listXnYNodes.get(currentXnYRight);
                        nCurNode.Right = nCurRightNode;
                    }
                }
            }
        }

        System.out.println(nTree.toString());
        for (Node n : listXnYNodes.values())
            System.out.println(n.toString());
    }

    private static XnY containsKey(XnY xny, Set<XnY> xnies) {
        for(XnY h : xnies){
            if ((h.X==xny.X)&&(h.Y==xny.Y))
                return h;
        }
        return null;
    }
}
