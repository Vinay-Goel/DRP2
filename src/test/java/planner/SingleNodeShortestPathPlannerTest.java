package planner;

import java.util.Arrays;
import java.util.List;

import model.BillOfDistribution;
import model.Demand;
import model.OnHandInventory;
import model.network.Network;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import sample.SampleBillOfDistributionGenerator;
import sample.SampleDemandGenerator;
import sample.SampleInventoryGenerator;
import sample.SampleAttributeListGenerator;

@RunWith(MockitoJUnitRunner.class)
public class SingleNodeShortestPathPlannerTest {

    SampleAttributeListGenerator sampleAttributeListGenerator;
    SampleBillOfDistributionGenerator sampleBillOfDistributionGenerator;
    SampleInventoryGenerator sampleInventoryGenerator;
    SampleDemandGenerator sampleDemandGenerator;

    @InjectMocks
    SingleNodeShortestPathPlanner resourcePlanner;

    @Before
    public void init() {
        sampleAttributeListGenerator = new SampleAttributeListGenerator();
        sampleBillOfDistributionGenerator = new SampleBillOfDistributionGenerator(4, 20, 1500, 15000, 2, 250);
        sampleInventoryGenerator = new SampleInventoryGenerator(1500, 5000, 500);
        sampleDemandGenerator = new SampleDemandGenerator(500, 10000, 100, 3);
    }

    @Test
    public void test1() {
        List<String> sources = sampleAttributeListGenerator.generate("Factory", 1);
        List<String> warehouses = sampleAttributeListGenerator.generate("Warehouse", 1);
        List<String> deposits = sampleAttributeListGenerator.generate("Deposit", 2);
        List<List<String>> intermediates = Arrays.asList(warehouses, deposits);
        List<String> destinations = sampleAttributeListGenerator.generate("Customer", 3);
        List<BillOfDistribution> bills = sampleBillOfDistributionGenerator.generate(sources, intermediates, destinations);
        List<OnHandInventory> inventories = sampleInventoryGenerator.generate(sources, intermediates);
        List<Demand> demands = sampleDemandGenerator.generate(destinations);

        Network network = resourcePlanner.plan(bills, inventories, demands);
        System.out.println(network);
    }
}