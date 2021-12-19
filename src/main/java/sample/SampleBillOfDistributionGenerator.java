package sample;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import model.BillOfDistribution;
import random.Random;

public class SampleBillOfDistributionGenerator {

    private final Integer minLeadTimeDays;
    private final Integer maxLeadTimeDays;
    private final Integer minFreightCost;
    private final Integer maxFreightCost;
    private final Integer leadTimeThresholdDays;
    private final Integer freightCostThresholdDays;

    @Inject
    public SampleBillOfDistributionGenerator(Integer minLeadTimeDays, Integer maxLeadTimeDays, Integer minFreightCost,
            Integer maxFreightCost, Integer leadTimeThresholdDays, Integer freightCostThresholdDays) {
        this.minLeadTimeDays = minLeadTimeDays;
        this.maxLeadTimeDays = maxLeadTimeDays;
        this.minFreightCost = minFreightCost;
        this.maxFreightCost = maxFreightCost;
        this.leadTimeThresholdDays = leadTimeThresholdDays;
        this.freightCostThresholdDays = freightCostThresholdDays;
    }

    public List<BillOfDistribution> generate(List<String> sources, List<List<String>> intermediates, List<String> destinations) {
        if (intermediates.size() == 0)
            return generateBills(sources, destinations);

        List<BillOfDistribution> bills = generateBills(sources, intermediates.get(0));
        bills.addAll(generateBills(intermediates.get(intermediates.size()-1), destinations));
        for (int i = 1; i < intermediates.size(); i++)
            bills.addAll(generateBills(intermediates.get(i-1), intermediates.get(i)));
        return bills;
    }

    private List<BillOfDistribution> generateBills(List<String> fromLoc, List<String> toLoc) {
        List<BillOfDistribution> bills = new ArrayList<>();
        for (String from: fromLoc) {
            for (String to: toLoc) {
                Integer leadTime = Random.nextInt(minLeadTimeDays, maxLeadTimeDays, leadTimeThresholdDays);
                Integer freightCost = Random.nextInt(minFreightCost, maxFreightCost, freightCostThresholdDays);
                BillOfDistribution bill = new BillOfDistribution(from, to, leadTime, freightCost);
                bills.add(bill);
            }
        }
        return bills;
    }
}
