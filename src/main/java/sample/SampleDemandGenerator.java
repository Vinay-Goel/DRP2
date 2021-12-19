package sample;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import model.Demand;
import random.Random;

public class SampleDemandGenerator {

    private final Integer minDemand;
    private final Integer maxDemand;
    private final Integer demandThreshold;
    private final Integer days;

    @Inject
    public SampleDemandGenerator(Integer minDemand, Integer maxDemand, Integer demandThreshold, Integer days) {
        this.minDemand = minDemand;
        this.maxDemand = maxDemand;
        this.demandThreshold = demandThreshold;
        this.days = days;
    }

    public List<Demand> generate(List<String> destinations) {
        List<Demand> demands = new ArrayList<>();
        for (String destination: destinations) {
            for (Integer day = 1; day <= days; day++) {
                Integer demandValue = Random.nextInt(minDemand, maxDemand, demandThreshold);
                Demand demand = Demand.builder()
                        .location(destination)
                        .demand(demandValue)
                        .day(day)
                        .build();
                demands.add(demand);
            }
        }
        return demands;
    }
}
