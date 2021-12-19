package dagger;

import javax.inject.Named;

import model.OnHandInventory;
import planner.ResourcePlanner;
import planner.SingleNodeShortestPathPlanner;
import provider.FileInputProvider;
import model.BillOfDistribution;
import sample.SampleBillOfDistributionGenerator;
import sample.SampleDemandGenerator;
import sample.SampleInventoryGenerator;

import com.univocity.parsers.common.processor.BeanListProcessor;

@Module
public class DRPModule {

    public static final String BILL_OF_DISTRIBUTION_PROVIDER = "BILL_OF_DISTRIBUTION_PROVIDER";
    public static final String ON_HAND_INVENTORY_PROVIDER = "ON_HAND_INVENTORY_PROVIDER";

    public static final String SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER = "SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER";

    @Provides
    @Named(BILL_OF_DISTRIBUTION_PROVIDER)
    public static FileInputProvider<BillOfDistribution> provideBillOfDistributionProvider() {
        return new FileInputProvider<>(new BeanListProcessor<>(BillOfDistribution.class));
    }

    @Provides
    @Named(ON_HAND_INVENTORY_PROVIDER)
    public static FileInputProvider<OnHandInventory> provideOnHandInventoryProvider() {
        return new FileInputProvider<>(new BeanListProcessor<>(OnHandInventory.class));
    }

    @Provides
    @Named(SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER)
    public static ResourcePlanner provideSingleNodeShortestPathResourcePlanner() {
        return new SingleNodeShortestPathPlanner();
    }

    @Provides
    public static SampleBillOfDistributionGenerator provideSampleBillOfDistributionGenerator() {
        return new SampleBillOfDistributionGenerator(4, 20, 1500, 15000, 2, 250);
    }

    @Provides
    public static SampleInventoryGenerator provideSampleInventoryGenerator() {
        return new SampleInventoryGenerator(1500, 5000, 500);
    }

    @Provides
    public static SampleDemandGenerator provideSampleDemandGenerator() {
        return new SampleDemandGenerator(500, 10000, 100, 3);
    }
}
