package executor;

import static dagger.DRPModule.SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.DaggerDRPComponent;
import model.BillOfDistribution;
import model.Demand;
import model.OnHandInventory;
import planner.ResourcePlanner;
import sample.SampleAttributeListGenerator;
import sample.SampleBillOfDistributionGenerator;
import sample.SampleDemandGenerator;
import sample.SampleInventoryGenerator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DRPGeneratedInputExecutor {

    @Inject
    @Named(SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER)
    ResourcePlanner resourcePlanner;

    @Inject
    SampleBillOfDistributionGenerator sampleBillOfDistributionGenerator;

    @Inject
    SampleInventoryGenerator sampleInventoryGenerator;

    @Inject
    SampleDemandGenerator sampleDemandGenerator;

    @Inject
    SampleAttributeListGenerator sampleAttributeListGenerator;

    public DRPGeneratedInputExecutor() {
        DaggerDRPComponent.create().inject(this);
    }

    public void generateInputAndExecute(Integer numberOfFactories, List<String> intermediateNames, List<Integer> intermediateNumbers,
            Integer numberOfCustomers) {

        List<String> sources = sampleAttributeListGenerator.generate("Factory", numberOfFactories);
        List<List<String>> intermediates = new ArrayList<>();
        for (int i = 0; i < intermediateNames.size(); i++) {
            List<String> intermediate = sampleAttributeListGenerator.generate(intermediateNames.get(i), intermediateNumbers.get(i));
            intermediates.add(intermediate);
        }

        List<String> destinations = sampleAttributeListGenerator.generate("Customer", numberOfCustomers);

        List<BillOfDistribution> bills = sampleBillOfDistributionGenerator.generate(sources, intermediates, destinations);
        List<OnHandInventory> inventories = sampleInventoryGenerator.generate(sources, intermediates);
        List<Demand> demands = sampleDemandGenerator.generate(destinations);

        resourcePlanner.plan(bills, inventories, demands);
    }
}
