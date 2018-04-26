package models;

import java.util.ArrayList;

public class Plan {
    public String Name;
    public double Cost;
    public Feature[] Features;
    public Feature[] selectedFeatures;
    public int iNoofFeatures;
    public boolean bUsed;

    public Plan(String strName, double dbCost, String strFeatures) {
        String[] stringArray = strFeatures.split(",");
        Feature[] featureList = new Feature[stringArray.length];
        for (int i=0; i < stringArray.length; i++)
            featureList[i] = new Feature(stringArray[i]);
        this.Name = strName;
        this.Cost = dbCost;
        this.Features = featureList;
        this.iNoofFeatures = featureList.length;
        this.bUsed = false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name:[");
        result.append(this.Name).append("],Cost:[");
        result.append(this.Cost).append("],Features:[");
        if(this.Features.length==1)
            result.append(this.Features[0].Name).append("]");
        else
            for (int i=0; i < this.Features.length; i++)
                if (i == (this.Features.length-1))
                    result.append(this.Features[i].Name).append("]");
                else
                    result.append(this.Features[i].Name).append(",");
        return result.toString();
    }

    public void parseSelectedFeatures(Feature[] selectedFeatures) {
        ArrayList<Feature> tmplist = new ArrayList<>();

        for (Feature f : this.Features)
            for (Feature fs : selectedFeatures)
                if (f.Name.equals(fs.Name)){
                    tmplist.add(f);
                    break;
                }

        this.selectedFeatures = new Feature[tmplist.size()];
        this.selectedFeatures = tmplist.toArray(this.selectedFeatures);
        /*System.out.print("=========filter selected Features:");
        for(Feature f : this.selectedFeatures)
            System.out.print(f.Name + ",");
        System.out.println();*/
    }
}
