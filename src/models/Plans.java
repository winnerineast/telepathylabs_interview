package models;

import java.util.HashSet;

public class Plans {
    public Plan[] plans;
    public double totalCost;
    public int iTotalNoofFeatures;
    public HashSet<String> totalFeatures;

    public Plans(Plan[] plans){
        this.plans = plans;
        this.totalCost = 0.0;
        this.iTotalNoofFeatures = 0;
        this.totalFeatures = new HashSet<>();
        for (Plan aPlan : plans) {
            for (Feature f : aPlan.Features)
                this.totalFeatures.add(f.Name);
            this.totalCost += aPlan.Cost;
        }
        this.iTotalNoofFeatures = this.totalFeatures.size();
    }
}
